package pl.pracingo.countrycitygame.utils;

import java.util.List;

public class LettersUtils {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPRSTUWZ";

    public static final List<Character> LETTERS = ALPHABET.chars().mapToObj(i->(char)i).toList();
}
