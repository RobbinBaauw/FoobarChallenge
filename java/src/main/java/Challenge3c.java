import java.math.BigInteger;
import java.util.HashMap;

public class Challenge3c {

    private static final HashMap<BigInteger, Integer> cache = new HashMap<>();
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static int solution(String x) {
        return solution(new BigInteger(x));
    }

    public static int solution(BigInteger n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        if (n.compareTo(BigInteger.ONE) <= 0) {
            return 0;
        }

        int closest2Power = (int) Math.round(Math.log(n.doubleValue()) / Math.log(2));

        BigInteger movingToPowerCost = BigInteger.valueOf(2).pow(closest2Power).subtract(n).abs().add(BigInteger.valueOf(closest2Power));

        int totalCost;
        if (n.mod(TWO).compareTo(BigInteger.ZERO) != 0) {
            int addingCost = 2 + solution(n.add(BigInteger.ONE).divide(TWO));
            int minusCost = 2 + solution(n.subtract(BigInteger.ONE).divide(TWO));

            totalCost = movingToPowerCost.min(BigInteger.valueOf(Math.min(addingCost, minusCost))).intValue();
        } else {
            int dividingCost = 1 + solution(n.divide(TWO));
            totalCost = movingToPowerCost.min(BigInteger.valueOf(dividingCost)).intValue();
        }

        cache.put(n, totalCost);

        return totalCost;
    }
}
