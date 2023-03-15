package pl.pracinho.countrycitygame.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pracinho.countrycitygame.model.enums.Category;

import java.util.*;

@Getter
public class RoundResultDto {

    private String id;
    @Setter
    private LinkedHashMap<Category, List<CategoryResultDto>> categoryResultMap;
    private Character letter;
    private Set<String> readyPlayers;

    public RoundResultDto(Character letter) {
        this.id = UUID.randomUUID().toString();
        this.letter = letter;
        this.categoryResultMap = new LinkedHashMap<>();
        this.readyPlayers = new HashSet<>();
    }

    public void addCategoryResult(Category category, List<CategoryResultDto> results) {
        categoryResultMap.put(category, results);
    }
}
