package aoc;

import utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

        List<CamelCard> finalCamelCards = camelCards;
        int totalP1 = IntStream.range(1, camelCards.size() + 1).reduce(0, (a, b) -> a + finalCamelCards.get(b - 1).getBid() * b);

        camelCards.forEach(CamelCard::determineHighestCardUsingJoker);
        camelCards = camelCards.stream().sorted().collect(Collectors.toList());

        List<CamelCard> finalCamelCardsP2 = camelCards;
        int totalP2 = IntStream.range(1, camelCards.size() + 1).reduce(0, (a, b) -> a + finalCamelCardsP2.get(b - 1).getBid() * b);

        finalCamelCardsP2.forEach(camelCard -> System.out.println(camelCard.cards + " - " + camelCard.type));

        System.out.println("------------- Day 7 -------------");
        System.out.println("part 1: " + totalP1);
        System.out.println("part 2: " + totalP2);
        System.out.println("---------------------------------");
    }

    enum Type {

        FIVE_OF_A_KIND("([AKQJT98765432])(\\1%1$s){4}"),
        FOUR_OF_A_KIND("([AKQJT98765432])(\\1%1$s){3}"),
        FULL_HOUSE("([AKQJT98765432])(\\1%1$s){2}([AKQJT98765432])(\\3%1$s)|([AKQJT98765432])(\\5%1$s)([AKQJT98765432])(\\7%1$s){2}"),
        THREE_OF_A_KIND("([AKQJT98765432])(\\1%1$s){2}[^AKQJT98765432\\d]*|[^AKQJT98765432]([AKQJT98765432])(\\3%1$s){2}[^AKQJT98765432\\d]*"),
        TWO_PAIR("([AKQJT98765432])(\\1%1$s)([AKQJT98765432])(\\3%1$s)|([AKQJT98765432])(\\5%1$s)[AKQJT98765432]([AKQJT98765432])(\\7%1$s)"),
        ONE_PAIR("([AKQJT98765432])(\\1%1$s)"),
        HIGH_CARD("([AKQJT98765432])(?!\\1)([AKQJT98765432])(?!\\1|\\2)([AKQJT98765432])(?!\\1|\\2|\\3)([AKQJT98765432])(?!\\1|\\2|\\3|\\4)([AKQJT98765432])");
        private final Pattern pattern;
        private final Pattern jokerPattern;

        Type(String regex) {
            this.pattern = Pattern.compile(String.format(regex, ""));
            this.jokerPattern = Pattern.compile(String.format(regex, "|J"));
        }

    }

    static class CamelCard implements Comparable<CamelCard> {

        private static final List<String> cardValues = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");
        private static final List<String> jokerCardValues = List.of("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J");
        private boolean useJoker = false;
        private final String cards;
        private final int bid;
        private Type type = Type.HIGH_CARD;

        public CamelCard(String line) {
            String[] parts = line.split(" +");
            this.cards = parts[0];
            this.bid = Integer.parseInt(parts[1]);

            this.type = determineHighestType(this.cards);
        }

        public void determineHighestCardUsingJoker(){
            if(!cards.contains("J")){
                return;
            }

            this.useJoker = true;

            String cardsWithoutJoker = Arrays.stream(cards.replace("J", "").split("")).sorted().collect(Collectors.joining());
            Type previousType = this.type;
            for(int i = 0; i < cardsWithoutJoker.length() + 1; i++){
                if(this.type == Type.FIVE_OF_A_KIND)
                    break;

                String cardWithJoker = StringUtils.insertStringAt(cardsWithoutJoker, "J".repeat(cards.length() - cardsWithoutJoker.length()), i);
                Type determinedType = determineHighestType(cardWithJoker);
                if(determinedType.ordinal() < this.type.ordinal()){
                    this.type = determinedType;
                }
            }
        }

        public Type determineHighestType(String cards){
            if(!this.useJoker)
                cards = Arrays.stream(cards.split("")).sorted().collect(Collectors.joining());

            for (Type type : Type.values()) {
                Pattern patternToUse = useJoker ? type.jokerPattern : type.pattern;

                if (patternToUse.matcher(cards).find()) {
                    return type;
                }
            }

            return Type.HIGH_CARD;
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

            List<String> cardValuesToUse = this.useJoker ? CamelCard.jokerCardValues : CamelCard.cardValues;

            for(int i = 0; i < thisCards.length; i++){
                int cardCompare = cardValuesToUse.indexOf(thisCards[i]) - cardValuesToUse.indexOf(oCards[i]);
                if(cardCompare != 0){
                    return cardCompare > 0 ? -1 : 1;
                }
            }

            System.out.println(this.cards + " COMPARED TO " + o.cards);

            return 0;
        }

    }

}
