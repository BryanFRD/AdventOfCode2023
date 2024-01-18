package aoc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

    private static final Pattern redPattern = Pattern.compile("(?=(\\d+) red)");
    private static final Pattern greenPattern = Pattern.compile("(?=(\\d+) green)");
    private static final Pattern bluePattern = Pattern.compile("(?=(\\d+) blue)");
    private static final Pattern idPattern = Pattern.compile("(?=(\\d+))");

    public static boolean isGamePossible(String input, int maxRed, int maxGreen, int maxBlue) {
        String[] games = input.split(";");

        boolean result = true;

        for(String game : games) {
            if (isColorCountHigherThan(maxRed, game, redPattern))
                result = false;

            if (isColorCountHigherThan(maxGreen, game, greenPattern))
                result = false;

            if (isColorCountHigherThan(maxBlue, game, bluePattern))
                result = false;
        }

        return result;
    }

    private static boolean isColorCountHigherThan(int maxColor, String game, Pattern pattern) {
        Matcher matcher = pattern.matcher(game);

        if(matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));

            return count > maxColor;
        }
        return false;
    }

    public static int retrieveGamesId(String input) {
        Matcher matcher = idPattern.matcher(input);

        if(matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }

    public static int retrievePowerOfMinimumColorNeeded(String input) {
        String[] games = input.split(";");

        int red = 1, green = 1, blue = 1;

        for(String game : games) {
            Matcher redMatcher = redPattern.matcher(game);
            Matcher greenMatcher = greenPattern.matcher(game);
            Matcher blueMatcher = bluePattern.matcher(game);

            if(redMatcher.find()) {
                int r = Integer.parseInt(redMatcher.group(1));
                red = red == 1 ? r : Math.max(red, r);
            }

            if(greenMatcher.find()) {
                int g = Integer.parseInt(greenMatcher.group(1));
                green = green == 1 ? g : Math.max(green, g);
            }

            if(blueMatcher.find()) {
                int b = Integer.parseInt(blueMatcher.group(1));
                blue = blue == 1 ? b : Math.max(b, blue);
            }
        }

        return red * green * blue;
    }

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day2");

        int sumP1 = lines.stream().filter(line -> isGamePossible(line, 12, 13, 14)).mapToInt(Day2::retrieveGamesId).sum();
        int sumP2 = lines.stream().mapToInt(Day2::retrievePowerOfMinimumColorNeeded).sum();

        System.out.println("------------- Day 2 -------------");
        System.out.println("part 1: " + sumP1);
        System.out.println("part 2: " + sumP2);
        System.out.println("---------------------------------");
    }

}
