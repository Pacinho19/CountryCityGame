package pl.pracinho.countrycitygame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pracinho.countrycitygame.model.dto.UnknownAnswerDto;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UnknownAnswerInputDto {

    private List<UnknownAnswerDto> unknownAnswers;
}
