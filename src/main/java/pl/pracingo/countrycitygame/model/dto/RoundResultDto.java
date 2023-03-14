package pl.pracingo.countrycitygame.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pracingo.countrycitygame.model.enums.Category;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class RoundResultDto {

    private Character letter;

    @Setter
    private LinkedHashMap<Category, List<CategoryResultDto>> categoryResultMap;

    public RoundResultDto(Character letter) {
        this.letter = letter;
        this.categoryResultMap = new LinkedHashMap<>();
    }

    public void addCategoryResult(Category category, List<CategoryResultDto> results) {
        categoryResultMap.put(category, results);
    }
}
