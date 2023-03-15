package pl.pracinho.countrycitygame.model.enums;

import lombok.RequiredArgsConstructor;
import pl.pracinho.countrycitygame.tools.CheckFunction;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public enum Category {

    Country(CheckFunction.getInstance().COUNTRY),
    City(CheckFunction.getInstance().CITY);

    private final Function<String, Boolean> checkFunction;

    public boolean check(Character character, String value) {
        if (!value.startsWith(String.valueOf(character))) return false;
        return checkFunction.apply(value);
    }

    public static List<String> getCategoriesNames() {
        return Arrays.stream(Category.values())
                .map(Category::name)
                .toList();
    }
}
