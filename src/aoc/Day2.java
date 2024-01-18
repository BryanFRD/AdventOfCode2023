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

        boolean r = true;

        for(String game : games) {
            if (isColorCountHigherThan(maxRed, game, redPattern))
                r = false;

            if (isColorCountHigherThan(maxGreen, game, greenPattern))
                r = false;

            if (isColorCountHigherThan(maxBlue, game, bluePattern))
                r = false;
        }

        return r;
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

        int r = 1, g = 1, b = 1;

        for(String game : games) {
            Matcher redMatcher = redPattern.matcher(game);
            Matcher greenMatcher = greenPattern.matcher(game);
            Matcher blueMatcher = bluePattern.matcher(game);

            if(redMatcher.find()) {
                int red = Integer.parseInt(redMatcher.group(1));
                r = r == 1 ? red : Math.max(r, red);
            }

            if(greenMatcher.find()) {
                int green = Integer.parseInt(greenMatcher.group(1));
                g = g == 1 ? green : Math.max(g, green);
            }

            if(blueMatcher.find()) {
                int blue = Integer.parseInt(blueMatcher.group(1));
                b = b == 1 ? blue : Math.max(b, blue);
            }
        }

        return r * g * b;
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
