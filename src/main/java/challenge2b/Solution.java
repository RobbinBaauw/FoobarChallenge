package challenge2b;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static int solution(String n, int b) {
        final Map<String, Integer> solutionIterationMap = new HashMap<>();

        final int k = n.length();

        int i = 0;
        while (true) {
            char[] digitsAscending = n.toCharArray();
            Arrays.sort(digitsAscending);
            String y = new String(digitsAscending);
            int yInt = Integer.parseInt(y, b);

            for (int j = 0; j < digitsAscending.length / 2; j++) {
                char tmp = digitsAscending[j];
                int invertedIndex = digitsAscending.length - j - 1;
                digitsAscending[j] = digitsAscending[invertedIndex];
                digitsAscending[invertedIndex] = tmp;
            }

            String x = new String(digitsAscending);
            int xInt = Integer.parseInt(x, b);

            int z = xInt - yInt;
            n = Integer.toString(z, b);
            n = "0000000000".substring(0, k - n.length()) + n;
            if (solutionIterationMap.containsKey(n)) {
                return i - solutionIterationMap.get(n);
            }

            solutionIterationMap.put(n, i);
            i++;
        }
    }
}
