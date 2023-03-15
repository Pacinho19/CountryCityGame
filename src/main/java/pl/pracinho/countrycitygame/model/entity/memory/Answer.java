package pl.pracinho.countrycitygame.model.entity.memory;

import pl.pracinho.countrycitygame.model.enums.Category;

import java.util.Map;

public record Answer(Map<Category, String> answers, Player player) {
}
