package pl.pracingo.countrycitygame.model.entity.memory;

import pl.pracingo.countrycitygame.model.enums.Category;

import java.util.Map;

public record Answer(Map<Category, String> answers, Player player) {
}
