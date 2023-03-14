package pl.pracingo.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.exception.GameNotFoundException;
import pl.pracingo.countrycitygame.model.dto.CategoryAnswerDto;
import pl.pracingo.countrycitygame.model.dto.CategoryResultDto;
import pl.pracingo.countrycitygame.model.dto.RoundResultDto;
import pl.pracingo.countrycitygame.model.entity.memory.Answer;
import pl.pracingo.countrycitygame.model.entity.memory.Game;
import pl.pracingo.countrycitygame.model.entity.memory.Player;
import pl.pracingo.countrycitygame.model.enums.Category;
import pl.pracingo.countrycitygame.repository.game.GameRepository;
import pl.pracingo.countrycitygame.utils.LettersUtils;
import pl.pracingo.countrycitygame.utils.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GameLogicService {

    private final GameRepository gameRepository;

    public Game findById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId))
                ;
    }

    public Character drawLetter(List<Character> usedLetters) {
        List<Character> availableLetters = LettersUtils.LETTERS.stream()
                .filter(l -> !usedLetters.contains(l))
                .collect(Collectors.toList());

        return availableLetters.get(
                RandomUtils.getRandomInt(availableLetters.size())
        );
    }

    public Player getPlayerFromGame(String playerName, LinkedList<Player> players) {
        return players.stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(playerName + " does not participate in this game!"));

    }

    public boolean allPlayersFinished(Game game) {
        return game.getAnswers().get(game.getLetter()).size() == game.getPlayersCount();
    }

    public boolean checkPlayerCanAnswer(Game game, Player player) {
        return game.getAnswers().get(game.getLetter())
                .stream()
                .noneMatch(a -> a.player().getName().equals(player.getName()));
    }

    public void checkAnswers(Game game) {
        List<Answer> answers = game.getAnswers().get(game.getLetter());

        RoundResultDto roundResultDto = new RoundResultDto(game.getLetter());

        List.of(Category.values())
                .forEach(c -> calculateCategoryResult(c, roundResultDto, answers));

        game.addRoundResult(roundResultDto);
    }

    private void calculateCategoryResult(Category category, RoundResultDto roundResultDto, List<Answer> fullAnswers) {
        List<CategoryAnswerDto> categoryAnswers = fullAnswers.stream()
                .map(a -> new CategoryAnswerDto(a.answers().get(category), a.player().getName(), category.check(roundResultDto.getLetter(), a.answers().get(category))))
                .toList();

        List<CategoryAnswerDto> correctAnswers = categoryAnswers.stream()
                .filter(CategoryAnswerDto::correct)
                .toList();

        List<CategoryResultDto> categoryResultDtos = new ArrayList<>();

        categoryAnswers.stream()
                .filter(ca -> !ca.correct())
                .forEach(ca -> categoryResultDtos.add(new CategoryResultDto(ca.playerName(), ca.answer(), 0)));

        if (correctAnswers.size() == 1) {
            CategoryAnswerDto categoryAnswerDto = correctAnswers.get(0);
            categoryResultDtos.add(new CategoryResultDto(categoryAnswerDto.playerName(), categoryAnswerDto.answer(), 15));
        } else {
            getAnswersGroup(correctAnswers)
                    .forEach(answersByGroup ->
                            answersByGroup.forEach(ac -> categoryResultDtos.add(new CategoryResultDto(ac.playerName(), ac.answer(), answersByGroup.size() == 1 ? 10 : 5))));
        }

        categoryResultDtos.sort(Comparator.comparing(CategoryResultDto::playerName));
        roundResultDto.addCategoryResult(category, categoryResultDtos);
    }

    private Collection<List<CategoryAnswerDto>> getAnswersGroup(List<CategoryAnswerDto> correctAnswers) {
        return correctAnswers.stream()
                .collect(Collectors.groupingBy(CategoryAnswerDto::answer))
                .values();
    }

    private List<CategoryAnswerDto> filterAnswers(String playerName, List<CategoryAnswerDto> categoryAnswers) {
        return categoryAnswers.stream()
                .filter(ca -> !ca.playerName().equals(playerName))
                .toList();
    }
}