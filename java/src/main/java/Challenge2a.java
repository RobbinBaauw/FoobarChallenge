public class Challenge2a {
    public static int solution(int total_lambs) {
        int mostGenerousAmount = (int) Math.floor(Math.log(total_lambs + 1) / Math.log(2));

        return getLeastGenerous(total_lambs) - mostGenerousAmount;
    }

    private static int getLeastGenerous(int total_lambs) {
        total_lambs -= 1;
        int leastGenerousAmount = 1;

        int previous = 1;
        int nextLambs = 1;

        while (nextLambs <= total_lambs) {
            total_lambs -= nextLambs;

            leastGenerousAmount++;

            int currNextLambs = nextLambs;
            nextLambs = nextLambs + previous;
            previous = currNextLambs;
        }

        return leastGenerousAmount;
    }
}
