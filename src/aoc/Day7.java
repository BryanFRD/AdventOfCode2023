package aoc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day7");
        /*List<String> lines = List.of(
                "32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"
        );*/

        List<CamelCard> camelCards = lines.stream().map(CamelCard::new).sorted().collect(Collectors.toList());

        int totalP1 = IntStream.range(1, camelCards.size() + 1).reduce(0, (a, b) -> a + camelCards.get(b - 1).getBid() * b);

        System.out.println("------------- Day 7 -------------");
        System.out.println("part 1: " + totalP1);
        System.out.println("part 2: ");
        System.out.println("---------------------------------");
    }

    enum Type {

        FIVE_OF_A_KIND("([AKQJT98765432])\\1{4}"),
        FOUR_OF_A_KIND("([AKQJT98765432])\\1{3}"),
        FULL_HOUSE("([AKQJT98765432])\\1{2}([AKQJT98765432])\\2|([AKQJT98765432])\\3([AKQJT98765432])\\4{2}"),
        THREE_OF_A_KIND("([AKQJT98765432])\\1{2}[^AKQJT98765432\\d]*|[^AKQJT98765432]([AKQJT98765432])\\2{2}[^AKQJT98765432\\d]*"),
        TWO_PAIR("([AKQJT98765432])\\1([AKQJT98765432])\\2|([AKQJT98765432])\\3[AKQJT98765432]([AKQJT98765432])\\4"),
        ONE_PAIR("([AKQJT98765432])\\1"),
        HIGH_CARD("([AKQJT98765432])(?!\\1)([AKQJT98765432])(?!\\1|\\2)([AKQJT98765432])(?!\\1|\\2|\\3)([AKQJT98765432])(?!\\1|\\2|\\3|\\4)([AKQJT98765432])");
        private final Pattern pattern;

        Type(String regex) {
            this.pattern = Pattern.compile(regex);
        }

    }

    static class CamelCard implements Comparable<CamelCard> {

        private static final List<String> CARDS = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

        private final String cards;
        private final String sortedCards;
        private final int bid;
        private Type type = Type.HIGH_CARD;

        public CamelCard(String line) {
            String[] parts = line.split(" +");
            this.cards = parts[0];
            this.sortedCards = Arrays.stream(cards.split("")).sorted().collect(Collectors.joining());
            this.bid = Integer.parseInt(parts[1]);

            for (Type type : Type.values()) {
                if (type.pattern.matcher(sortedCards).find()) {
                    this.type = type;
                    break;
                }
            }
        }

        public String getCards() {
            return cards;
        }

        public int getBid() {
            return bid;
        }

        public Type getType() {
            return type;
        }


        @Override
        public int compareTo(CamelCard o) {
            int typeCompare = this.type.ordinal() - o.type.ordinal();

            if(typeCompare != 0){
                return typeCompare > 0 ? -1 : 1;
            }

            String[] thisCards = this.cards.split("");
            String[] oCards = o.cards.split("");

            for(int i = 0; i < thisCards.length; i++){
                int cardCompare = CARDS.indexOf(thisCards[i]) - CARDS.indexOf(oCards[i]);
                if(cardCompare != 0){
                    return cardCompare > 0 ? -1 : 1;
                }
            }

            return 0;
        }
    }

}
