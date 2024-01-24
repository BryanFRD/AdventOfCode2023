package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Part 1: Récupérer les numéros gagnants de chaque carte et calculer leur valeur
 * Exemple:
 * Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
 * Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
 * Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
 * Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
 * Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
 * Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
 *
 * La carte 1 a 5 numéros gagnants: (41, 48, 83, 86, 17) et 8 numéros: (83, 86, 6, 31, 17, 9, 48, 53)
 * On a alors 4 numéros gagnants: (48, 83, 17, 86) ce qui nous donne un valeur de 8 -> 1 * 2 * 2 * 2
 * 1 numéro gagnant = 1 point et pour chaque numéro gagnant suivant on multiplie par 2
 * etc ...
 *
 * On additionne donc les valeurs de chaque jeu et on obtient 8 + 2 + 2 + 1 = 13
 *
 * Part 2: On ne gagne pas de points pour les numéros gagnants mais des copies de carte
 * Exemple:
 * Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
 * Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
 * Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
 * Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
 * Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
 * Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
 *
 * la carte 1 a 4 numéros gagnants: (48, 83, 17, 86) donc on gagne une copie pour les jeux 2, 3, 4 et 5
 * la carte 2 a 2 numéros gagnants: (61, 32) donc on gagne une copie pour les jeux 3 et 4
 * ET comme on avait gagné une copie pour le jeu 2, on regagne une copie pour le jeu 3 et 4
 * etc...
 *
 * Ensuite on addition les copies de chaque carte et on obtient 1 + 2 + 4 + 8 + 14 + 1 = 30
 */
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
