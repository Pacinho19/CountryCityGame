package pl.pracinho.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.exception.GameNotFoundException;
import pl.pracinho.countrycitygame.model.dto.CategoryAnswerDto;
import pl.pracinho.countrycitygame.model.dto.CategoryResultDto;
import pl.pracinho.countrycitygame.model.dto.RoundResultDto;
import pl.pracinho.countrycitygame.model.entity.memory.Answer;
import pl.pracinho.countrycitygame.model.entity.memory.Game;
import pl.pracinho.countrycitygame.model.entity.memory.Player;
import pl.pracinho.countrycitygame.model.enums.Category;
import pl.pracinho.countrycitygame.repository.game.GameRepository;
import pl.pracinho.countrycitygame.utils.LettersUtils;
import pl.pracinho.countrycitygame.utils.RandomUtils;

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

    public RoundResultDto getLastRoundResult(Game game) {
        if (game.getResults().isEmpty()) return null;
        return game.getResults().get(0);
    }

    public boolean allPlayersReady(RoundResultDto lastRoundResult, LinkedList<Player> players) {
        if (lastRoundResult == null) return true;
        return players.stream()
                .allMatch(p -> lastRoundResult.getReadyPlayers().contains(p.getName()));
    }

    public boolean checkPlayerReady(Game game, String name) {
        RoundResultDto lastRoundResult = getLastRoundResult(game);
        if (lastRoundResult == null) return false;
        return lastRoundResult.getReadyPlayers().contains(name);
    }

    public String getLastRoundId(Game game) {
        RoundResultDto lastRoundResult = getLastRoundResult(game);
        if (lastRoundResult == null) return null;
        return lastRoundResult.getId();
    }

    public List<String> getReadyPlayers(Game game) {
        RoundResultDto lastRoundResult = getLastRoundResult(game);
        if (lastRoundResult == null) return Collections.emptyList();
        return lastRoundResult.getReadyPlayers().stream().toList();
    }
}