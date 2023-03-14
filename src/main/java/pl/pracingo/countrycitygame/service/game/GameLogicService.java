package pl.pracingo.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.exception.GameNotFoundException;
import pl.pracingo.countrycitygame.model.entity.memory.Game;
import pl.pracingo.countrycitygame.repository.game.GameRepository;

@RequiredArgsConstructor
@Service
public class GameLogicService {

    private final GameRepository gameRepository;

    public Game findById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId))
                ;
    }

}