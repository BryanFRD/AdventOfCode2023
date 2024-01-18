package aoc;

import utils.FileUtils;
import utils.NumberUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

    private static final Pattern partOnePattern = Pattern.compile("(?=(\\d))");
    private static final Pattern partTwoPattern = Pattern.compile("(?=(\\d|zero|one|two|three|four|five|six|seven|eight|nine))");

    public static int retrieveFirstAndLastDigitPartOne(String input) {
        return retrieveFirstAndLastDigit(input, partOnePattern);
    }

    public static int retrieveFirstAndLastDigitPartTwo(String input) {
        return retrieveFirstAndLastDigit(input, partTwoPattern);
    }

    public static int retrieveFirstAndLastDigit(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);

        String first = null, second = null;

        while(matcher.find()) {
            String match = matcher.group(1);

            if(first == null){
                first = match;
            }

            second = match;
        }

        return Integer.parseInt(String.format("%s%s", NumberUtils.decodeWordToInt(first), NumberUtils.decodeWordToInt(second)));
    }

    public static void execute(){
        List<String> lines = FileUtils.getArrayFromDocument("day1");

        int sumP1 = lines.stream().mapToInt(Day1::retrieveFirstAndLastDigitPartOne).sum();
        int sumP2 = lines.stream().mapToInt(Day1::retrieveFirstAndLastDigitPartTwo).sum();

        System.out.println("------------- Day 1 -------------");
        System.out.println("part 1: " + sumP1);
        System.out.println("part 2: " + sumP2);
        System.out.println("---------------------------------");
    }

}
