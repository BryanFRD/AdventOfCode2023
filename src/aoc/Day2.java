package aoc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part 1: On nous donne un sac de cube de trois couleurs différentes (rouge, vert, bleu)
 * On doit alors toutes les parties possible et additioner les numéros de chaque partie
 * Exemple: pour un sac donné de 3 rouge, 5 vert et 4 bleu
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 *
 * Seuls les jeux 1, 2 et 5 sont possibles car ils ne dépassent pas les limites de couleurs
 * donc la réponse est 1 + 2 + 5 = 8
 *
 * Part 2: Maintenant on doit multiplier la valeur de chaque couleurs les plus grandes de chaque partie
 * Exemple: pour un sac donné de 3 rouge, 5 vert et 4 bleu
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 *
 * pour la partie 1: 4 (red) * 6 (blue) * 2 (green) = 48
 * et les autres parties ont obtiens 12, 1560, 630, 36
 * donc la réponse est 48 + 12 + 1560 + 630 + 36 = 2286
 */
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
