package aoc;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 {

    private static final Pattern nodePattern = Pattern.compile("([0-9A-Z]{3}) = \\(([0-9A-Z]{3}), ([0-9A-Z]{3})\\)");

    public static void decodeLines(List<String> lines){
        lines.forEach(str -> {
            Matcher nodeMatcher = nodePattern.matcher(str);

            if(nodeMatcher.find()){
                new Node(nodeMatcher.group(1), nodeMatcher.group(2), nodeMatcher.group(3));
            }
        });
    }

    public static List<Node> getNext(List<Node> nodes, String direction){
        return nodes.stream().map(node -> node.next(direction)).collect(Collectors.toList());
    }

    public static void execute(){
        List<String> lines = utils.FileUtils.getArrayFromDocument("day8");
        /*List<String> lines = List.of(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)"
        );*/

        decodeLines(lines);

        Iterator<String> directions = Arrays.stream(lines.get(0).split("")).iterator();
        List<Node> nodes = List.of(Node.nodes.get("AAA"));
        int iteration1 = 0;

        while(nodes.stream().anyMatch(Node::isNotEnd)){
            if(!directions.hasNext())
                directions = Arrays.stream(lines.get(0).split("")).iterator();

            iteration1++;
            nodes = getNext(nodes, directions.next());
        }

        directions = Arrays.stream(lines.get(0).split("")).iterator();
        nodes = new ArrayList<>();
        int iteration2 = 0;
        for (Node node : Node.nodes.values()){
            if(node.name.charAt(2) == 'A')
                nodes.add(node);
        }

        List<String> test = new ArrayList<>();

        while(nodes.stream().anyMatch(Node::isNotEnd)){
            String nStr = nodes.toString();
            System.out.println(nStr + " - " + test.stream().filter(s -> s.equals(nStr)).count());
            test.add(nStr);
            if(!directions.hasNext())
                directions = Arrays.stream(lines.get(0).split("")).iterator();

            iteration2++;
            nodes = getNext(nodes, directions.next());
        }

        System.out.println("------------- Day 8 -------------");
        System.out.println("part 1: " + iteration1);
        System.out.println("part 2: " + iteration2);
        System.out.println("---------------------------------");
    }

    public static class Node {

        private static final Map<String, Node> nodes = new HashMap<>();

        private final String name, left, right;
        private Node leftNode, rightNode;

        public Node(String name, String left, String right){
            this.name = name;
            this.left = left;
            this.right = right;

            nodes.put(name, this);
        }

        public Node left(){
            if(leftNode == null)
                leftNode = nodes.get(left);

            return leftNode;
        }

        public Node right(){
            if(rightNode == null)
                rightNode = nodes.get(right);

            return rightNode;
        }

        public Node next(String direction){
            return direction.equals("L") ? left() : right();
        }

        public boolean isNotEnd(){
            return name.charAt(2) != 'Z';
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
