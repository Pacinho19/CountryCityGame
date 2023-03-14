package pl.pracingo.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.exception.GameNotFoundException;
import pl.pracingo.countrycitygame.model.entity.memory.Game;
import pl.pracingo.countrycitygame.repository.game.GameRepository;
import pl.pracingo.countrycitygame.utils.LettersUtils;
import pl.pracingo.countrycitygame.utils.RandomUtils;

import java.util.Collections;
import java.util.List;
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

        return  availableLetters.get(
                RandomUtils.getRandomInt(availableLetters.size())
        );
    }
}