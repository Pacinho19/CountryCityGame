package pl.pracinho.countrycitygame.model.enums;

import lombok.RequiredArgsConstructor;
import pl.pracinho.countrycitygame.tools.CategoryFunction;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
public enum Category {

    Country(CategoryFunction.getInstance().SEARCH_COUNTRY, CategoryFunction.getInstance().SAVE_COUNTRY),
    City(CategoryFunction.getInstance().SEARCH_CITY, CategoryFunction.getInstance().SAVE_CITY),
    Name(CategoryFunction.getInstance().SEARCH_NAME, CategoryFunction.getInstance().SAVE_NAME),
    Animal(CategoryFunction.getInstance().SEARCH_ANIMAL, CategoryFunction.getInstance().SAVE_ANIMAL);

    private final Function<String, Boolean> checkFunction;
    private final BiConsumer<String, Boolean> saveFunction;

    public Boolean check(Character character, String value) {
        if (!value.toUpperCase().startsWith(String.valueOf(character))) return false;
        if (value.length() == 1) return false;
        return checkFunction.apply(value);
    }

    public static List<String> getCategoriesNames() {
        return Arrays.stream(Category.values())
                .map(Category::name)
                .toList();
    }

    public void save(String value, boolean correct) {
        saveFunction.accept(value, correct);
    }
}