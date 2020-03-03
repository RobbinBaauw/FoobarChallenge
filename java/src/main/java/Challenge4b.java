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
        Set<Set<Integer>> allowedIndices = powerSetOfLength(times.length - 2);

        int[] bestIndicesSoFar = new int[0];
        outer: for (Set<Integer> currentAllowedIndices : allowedIndices) {
            for (List<Integer> ordering : permute(currentAllowedIndices)) {
                ordering.add(shortestPaths.length - 1);

                int currentIndex = 0;
                int totalCost = 0;
                for (int newIndex : ordering) {
                    totalCost += shortestPaths[currentIndex][newIndex];
                    currentIndex = newIndex;
                }

                if (totalCost <= times_limit) {
                    int[] currentAllowedIndicesArr = currentAllowedIndices.stream().mapToInt(x -> x - 1).sorted().toArray();

                    if (bestIndicesSoFar.length < currentAllowedIndicesArr.length) {
                        bestIndicesSoFar = currentAllowedIndicesArr;
                        continue outer;
                    } else if (bestIndicesSoFar.length == currentAllowedIndicesArr.length) {
                        for (int i = 0; i < currentAllowedIndicesArr.length; i++) {
                            if (bestIndicesSoFar[i] < currentAllowedIndicesArr[i]) {
                                continue outer;
                            }
                        }
                        bestIndicesSoFar = currentAllowedIndicesArr;
                    }
                }
            }
        }

        return bestIndicesSoFar;
    }

    // Bellman-ford
    private static int[][] findShortestPaths(int[][] times) {
        int[][] Opt = Arrays.stream(times)
            .map(int[]::clone)
            .toArray(int[][]::new);

        boolean hasNegativeCycles = false;
        outer: for (int iteration = 0; iteration < times.length + 2; iteration++) {
            for (int from = 0; from < times.length; from++) {
                for (int to = 0; to < times.length; to++) {
                    if (from != to) {
                        for (int other = 0; other < times.length; other++) {
                            if (other != from && other != to) {
                                int min = Math.min(Opt[from][to], Opt[from][other] + Opt[other][to]);
                                if (iteration == times.length && min != Opt[from][to]) {
                                    hasNegativeCycles = true;
                                    continue outer;
                                }

                                if (iteration == times.length + 1 && hasNegativeCycles) {
                                    Opt[from][to] = 0;
                                } else if (iteration < times.length) {
                                    Opt[from][to] = min;
                                }
                            }
                        }
                    }
                }
            }
        }

        return Opt;
    }

    // Thanks StackOverflow :)
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

    private static Set<Set<Integer>> powerSetOfLength(int amountOfBunnies) {

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
