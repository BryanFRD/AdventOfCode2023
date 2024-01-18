package utils;

import java.util.List;

public class NumberUtils {

    private static final List<String> numbers = List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    public static int decodeWordToInt(String word) {
        if(word == null)
            return 0;

        if(word.matches("\\d")) {
            return Integer.parseInt(word);
        } else if(numbers.contains(word)) {
            return numbers.indexOf(word);
        }

        return 0;
    }

}
