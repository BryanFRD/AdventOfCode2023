package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day4 {

    public static List<Integer> retrieveCardWinningNumbers(String input) {
        String removedCardName = input.replaceAll("Card \\d: +", "");
        String[] cards = removedCardName.split("\\| +");

        List<String> winningNumbers = List.of(cards[0].split(" +"));
        List<String> numbers = List.of(cards[1].split(" +"));

        return numbers.stream().filter(winningNumbers::contains).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static Map<Integer, Integer> retrieveCardsAndCopy(List<String> lines) {
        Map<Integer, Integer> cardsAndCopy = new HashMap<>();

        for(int i = 0; i < lines.size(); i++) {
            List<Integer> winningNumbers = retrieveCardWinningNumbers(lines.get(i));
            cardsAndCopy.putIfAbsent(i, 1);

            for(int j = 0; j < cardsAndCopy.getOrDefault(i, 1); j++) {
                for(int k = 1; k < winningNumbers.size() + 1; k++) {
                    cardsAndCopy.put(i + k, cardsAndCopy.getOrDefault(i + k, 1) + 1);
                }
            }
        }

        return cardsAndCopy;
    }

    public static int retrieveCardWorthiness(List<Integer> cardsWinningNumbers) {
        AtomicInteger worthiness = new AtomicInteger();

        cardsWinningNumbers.forEach(number -> {
            if(worthiness.get() == 0)
                worthiness.set(1);
            else
                worthiness.getAndUpdate(a -> a * 2);
        });

        return worthiness.get();
    }

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day4");

        int sumP1 = lines.stream().map(Day4::retrieveCardWinningNumbers).mapToInt(Day4::retrieveCardWorthiness).sum();
        int sumOfCardsAndCopies = retrieveCardsAndCopy(lines).values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("------------- Day 4 -------------");
        System.out.println("part 1: " + sumP1);
        System.out.println("part 2: " + sumOfCardsAndCopies);
        System.out.println("---------------------------------");
    }

}
