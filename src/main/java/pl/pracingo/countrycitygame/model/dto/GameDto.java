package pl.pracingo.countrycitygame.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.pracingo.countrycitygame.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GameDto {

    private String id;
    private GameStatus status;
    private List<String> players;
    private LocalDateTime startTime;
    private int playersCount;
    private Character currentLetter;
    private List<RoundResultDto> roundResults;
}