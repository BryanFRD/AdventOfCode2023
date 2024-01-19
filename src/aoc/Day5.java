package aoc;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {

    private static final List<Long> seeds = new ArrayList<>();
    private static final Map<Category, List<RangeSource>> categories = new HashMap<>();

    public static void decodeList(List<String> lines){
        Pattern categoryPattern = Pattern.compile("[a-z -]+:");
        Pattern numbersPattern = Pattern.compile("\\d+");

        Category currentCategory = null;
        for(String line : lines) {
            if(line.isBlank())
                continue;

            Matcher categoryMatcher = categoryPattern.matcher(line);

            if(categoryMatcher.find()) {
                currentCategory = Category.getMapFromName(categoryMatcher.group());

                if(currentCategory != Category.SEEDS) {
                    categories.put(currentCategory, new ArrayList<>());
                    continue;
                }

                Matcher numbersMatcher = numbersPattern.matcher(line);

                while (numbersMatcher.find()) {
                    seeds.add(Long.parseLong(numbersMatcher.group()));
                }
                continue;
            }

            Matcher numbersMatcher = numbersPattern.matcher(line);

            long[] lineNumbers = new long[3];
            int i = 0;
            while (numbersMatcher.find()) {
                lineNumbers[i++] = Long.parseLong(numbersMatcher.group());
            }

            categories.get(currentCategory).add(new RangeSource(lineNumbers[0], lineNumbers[1], lineNumbers[2]));
        }

    }

    public static long convertSourceToDestinationCategory(long source, Category category) {
        List<RangeSource> rangeSources = categories.get(category);

        RangeSource rangeSource = rangeSources.stream().filter(rs -> rs.isInSourceRange(source)).findFirst().orElse(null);

        if(rangeSource == null)
            return source;

        return rangeSource.getDestinationFromSource(source);
    }

    public static long getLocationFromSeed(long seed) {
        long location = seed;

        for(Category category : Category.values()) {
            if(category == Category.SEEDS)
                continue;

            location = convertSourceToDestinationCategory(location, category);
        }

        return location;
    }

    public static long getLowestLocationFromSeedAndRange(long seed, long range) {
        System.out.println("Part 2: Checking seed: " + seed + " with range: " + range);

        long lowestLocation = -1;
        for(long i = seed; i < seed + range; i++) {
            long location = getLocationFromSeed(i);
            if(lowestLocation == -1 || location < lowestLocation)
                lowestLocation = location;
        }

        return lowestLocation;
    }

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day5");

        decodeList(lines);

        List<Long> locations = seeds.stream().map(Day5::getLocationFromSeed).collect(Collectors.toList());
        long lowestLocation = locations.stream().min(Long::compareTo).orElse(0L);

        System.out.println("------------- Day 5 -------------");

        List<Long> locationsFromSeedAndRange = new ArrayList<>();
        for(int i = 0; i < seeds.size(); i += 2){
            locationsFromSeedAndRange.add(getLowestLocationFromSeedAndRange(seeds.get(i), seeds.get(i + 1)));
        }
        long lowestLocationFromSeedAndRange = locationsFromSeedAndRange.stream().min(Long::compareTo).orElse(0L);

        System.out.println("part 1: " + lowestLocation);
        System.out.println("part 2: " + lowestLocationFromSeedAndRange);
        System.out.println("---------------------------------");
    }

    enum Category {
        SEEDS("seeds:"),
        SEED_TO_SOIL("seed-to-soil map:"),
        SOIL_TO_FERTILIZER("soil-to-fertilizer map:"),
        FERTILIZER_TO_WATER("fertilizer-to-water map:"),
        WATER_TO_LIGHT("water-to-light map:"),
        LIGHT_TO_TEMPERATURE("light-to-temperature map:"),
        TEMPERATURE_TO_HUMIDITY("temperature-to-humidity map:"),
        HUMIDITY_TO_LOCATION("humidity-to-location map:");

        private String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Category getMapFromName(String name) {
            return Arrays.stream(Category.values()).filter(category -> category.getName().equals(name)).findFirst().orElse(null);
        }

    }

    static class RangeSource {

        private long source;
        private long destination;
        private long range;

        public RangeSource(long destination, long source, long range) {
            this.destination = destination;
            this.source = source;
            this.range = range;
        }

        public boolean isInSourceRange(long value) {
            return value >= source && value <= source + range - 1;
        }

        public RangeSource getDestinationFromRangeSource(List<RangeSource> rangeSource) {
            return rangeSource.stream().filter(rs -> rs.isInSourceRange(destination)).findFirst().orElse(null);
        }

        public long getDestinationFromSource(long value) {
            if(!isInSourceRange(value))
                return value;

            return destination + (value - source);
        }

        public long getSource() {
            return source;
        }

        public long getDestination() {
            return destination;
        }

        public long getRange() {
            return range;
        }

    }

}
