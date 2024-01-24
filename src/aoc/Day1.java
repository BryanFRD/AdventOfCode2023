package aoc;

import utils.FileUtils;
import utils.NumberUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part 1: Récupérer le premier et le dernier chiffre de chaque ligne, les combiner ensemble et les additionner
 * Exemple: 1234 -> 14
 *         123456 -> 16
 *         123456789 -> 19
 *         14 + 16 + 19 = 49
 *         Réponse: 49
 *
 * Part 2: Prendre en compte les mots
 * Exemple: 1234 -> 14
 *        one23eight56 -> 16
 *        123456sevenoneeight -> 18
 *        14 + 16 + 18 = 48
 *        Réponse: 48
 */
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
