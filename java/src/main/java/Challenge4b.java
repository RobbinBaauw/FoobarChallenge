import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Challenge4b {
    public static int[] solution(int[][] times, int times_limit) {

        int[][] shortestPaths = findShortestPaths(times);
        Set<Set<Integer>> allowedIndices = getBunnyIndices(times.length - 2);

        int[] bestIndices = new int[0];
        outer: for (Set<Integer> currentAllowedIndices : allowedIndices) {
            for (List<Integer> ordering : permute(currentAllowedIndices)) {
                ordering.add(shortestPaths.length - 1);

                int currentIndex = 0;
                int totalCost = 0;
                for (int newIndex : ordering) {
                    totalCost += shortestPaths[currentIndex][newIndex];
                }

                if (totalCost <= times_limit) {
                    int[] currentAllowedIndicesArr = currentAllowedIndices.stream().mapToInt(x -> x - 1).sorted().toArray();

                    if (bestIndices.length < currentAllowedIndicesArr.length) {
                        bestIndices = currentAllowedIndicesArr;
                        continue outer;
                    } else if (bestIndices.length == currentAllowedIndicesArr.length) {
                        for (int i = 0; i < currentAllowedIndicesArr.length; i++) {
                            if (bestIndices[i] < currentAllowedIndicesArr[i]) {
                                continue outer;
                            }
                        }
                        bestIndices = currentAllowedIndicesArr;
                    }
                }
            }
        }

        return bestIndices;
    }

    private static int[][] findShortestPaths(int[][] times) {
        int[][] Opt = Arrays.stream(times)
            .map(int[]::clone)
            .toArray(int[][]::new);

        for (int ignore = 0; ignore < times.length; ignore++) {
            for (int from = 0; from < times.length; from++) {
                for (int to = 0; to < times.length; to++) {
                    if (from != to) {
                        for (int other = 0; other < times.length; other++) {
                            if (other != from && other != to) {
                                Opt[from][to] = Math.min(Opt[from][to], Opt[from][other] + Opt[other][to]);
                            }
                        }
                    }
                }
            }
        }

        return Opt;
    }

    private static Set<List<Integer>> permute(Collection<Integer> input) {
        Set<List<Integer>> output = new HashSet<>();
        if (input.isEmpty()) {
            output.add(new ArrayList<>());
            return output;
        }

        List<Integer> list = new ArrayList<>(input);
        int head = list.get(0);
        List<Integer> rest = list.subList(1, list.size());
        for (List<Integer> permutations : permute(rest)) {
            Set<List<Integer>> subLists = new HashSet<>();
            for (int i = 0; i <= permutations.size(); i++) {
                List<Integer> subList = new ArrayList<>(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }

    private static Set<Set<Integer>> getBunnyIndices(int amountOfBunnies) {

        Set<Integer> possibleIndices = IntStream
            .rangeClosed(1, amountOfBunnies)
            .boxed()
            .collect(Collectors.toSet());

        Set<Set<Integer>> powerSet = new HashSet<>();
        powerSet.add(new HashSet<>());

        for (Integer item : possibleIndices) {
            Set<Set<Integer>> newPs = new HashSet<>();

            for (Set<Integer> subset : powerSet) {
                newPs.add(subset);

                Set<Integer> newSubset = new HashSet<>(subset);
                newSubset.add(item);

                newPs.add(newSubset);
            }
            powerSet = newPs;
        }

        return powerSet;
    }
}
