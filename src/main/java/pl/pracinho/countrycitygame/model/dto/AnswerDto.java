package pl.pracinho.countrycitygame.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pracinho.countrycitygame.model.enums.Category;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class AnswerDto {

    private Map<Category, String> answers;

    public AnswerDto() {
        answers = new HashMap<>();
    }
}
