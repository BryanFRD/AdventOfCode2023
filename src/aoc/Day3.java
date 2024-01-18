package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final Pattern enginePartPattern = Pattern.compile("(\\d*\\d*\\d)");
    private static final Pattern engineSymbolPattern = Pattern.compile("[^.|\\d]");
    private static final Pattern engineGearPattern = Pattern.compile("[*]");

    public static List<Integer> retrieveEngineParts(List<String> lines) {
        List<Integer> engineParts = new ArrayList<>();

        for(int x = 0; x < lines.size(); x++) {
            Matcher matcher = engineSymbolPattern.matcher(lines.get(x));

            while(matcher.find()) {
                int y = matcher.start();

                engineParts.addAll(retrieveEnginePartAroundCoordinate(lines, x, y));
            }
        }

        return engineParts;
    }

    public static List<Integer> retrieveEnginePartAroundCoordinate(List<String> lines, int x, int y) {
        List<Integer> engineParts = new ArrayList<>();

        for(int xx = -1; xx < 2; xx++) {
            if(x + xx < 0 || x + xx >= lines.size())
                continue;

            Matcher matcher = enginePartPattern.matcher(lines.get(x + xx));
            while(matcher.find()){
                int start = matcher.start();
                int end = matcher.end() - 1;

                if(start > y + 1 || end < y - 1)
                    continue;

                engineParts.add(Integer.parseInt(matcher.group(1)));
            }
        }

        return engineParts;
    }

    public static List<Integer> retrieveGearsRatio(List<String> lines){
        List<Integer> engineParts = new ArrayList<>();

        for(int x = 0; x < lines.size(); x++) {
            Matcher matcher = engineGearPattern.matcher(lines.get(x));

            while(matcher.find()) {
                int y = matcher.start();


                List<Integer> enginePartsAroundCoordinate = retrieveEnginePartAroundCoordinate(lines, x, y);
                if(enginePartsAroundCoordinate.size() == 2){
                    engineParts.add(enginePartsAroundCoordinate.get(0) * enginePartsAroundCoordinate.get(1));
                }
            }
        }

        return engineParts;
    }

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day3");
        /*List<String> lines = List.of(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598..");*/

        int sumP1 = retrieveEngineParts(lines).stream().mapToInt(Integer::intValue).sum();
        int sumP2 = retrieveGearsRatio(lines).stream().mapToInt(Integer::intValue).sum();

        System.out.println("------------- Day 3 -------------");
        System.out.println("part 1: " + sumP1);
        System.out.println("part 2: " + sumP2);
        System.out.println("---------------------------------");
    }

}
