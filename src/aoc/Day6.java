package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {

    private static List<Long> times = new ArrayList<>();
    private static List<Long> distances = new ArrayList<>();

    public static void decodeList(List<String> lines) {
        Pattern numbersPattern = Pattern.compile("\\d+");

        for(String line : lines) {
            String[] parts = line.split(":");

            if(parts[0].equals("Time")) {
                Matcher matcher = numbersPattern.matcher(parts[1]);

                while(matcher.find()) {
                    times.add(Long.parseLong(matcher.group()));
                }
            } else if(parts[0].equals("Distance")) {
                Matcher matcher = numbersPattern.matcher(parts[1]);

                while(matcher.find()) {
                    distances.add(Long.parseLong(matcher.group()));
                }
            }
        }
    }
    public static void decodeListP2(List<String> lines) {
        times = new ArrayList<>();
        distances = new ArrayList<>();

        for(String line : lines) {
            String[] parts = line.split(":");

            if(parts[0].equals("Time")) {
                String time = String.join("", parts[1].split(" +"));
                times.add(Long.parseLong(time));
            } else if(parts[0].equals("Distance")) {
                String distance = String.join("", parts[1].split(" +"));
                distances.add(Long.parseLong(distance));
            }
        }
    }

    public static List<Long> getAvailableWays() {
        List<Long> availableWays = new ArrayList<>();

        for(int i = 0; i < times.size(); i++) {
            long time = times.get(i);
            long distance = distances.get(i);
            long numberOfWays = 0;

            for(long j = 0; j < time; j++) {
                long t = j * (time - j);
                if (t > distance)
                    numberOfWays++;
            }

            availableWays.add(numberOfWays);
        }

        return availableWays;
    }

    public static void execute() {
        List<String> lines = utils.FileUtils.getArrayFromDocument("day6");

        decodeList(lines);

        long sumP1 = getAvailableWays().stream().reduce(1L, (a, b) -> a * b);

        decodeListP2(lines);

        long sumP2 = getAvailableWays().stream().reduce(1L, (a, b) -> a * b);

        System.out.println("------------- Day 6 -------------");
        System.out.println("part 1: " + sumP1);
        System.out.println("part 2: " + sumP2);
        System.out.println("---------------------------------");
    }

}
