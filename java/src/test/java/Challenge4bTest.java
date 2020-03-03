import org.junit.Assert;
import org.junit.Test;

public class Challenge4bTest {
    @Test
    public void test() {
        int[][] times = {{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}};
        int[] solution = {0, 1};
        Assert.assertArrayEquals(solution, Challenge4b.solution(times, 3));
    }

    @Test
    public void test2() {
        int[][] times = {{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 2, -1}, {9, 3, 2, 0, -1}, {9, 3, 2, 2, 0}};
        int[] solution = {1, 2};
        Assert.assertArrayEquals(solution, Challenge4b.solution(times, 1));
    }

    @Test
    public void test3() {
        int max = 100;
        int[][] times = {
            {0, 6, 7, max, max, max, max},
            {max, 0, 1, -1, max, max, max},
            {max, 1, 0, max, -1, max, max},
            {max, max, max, 0, 5, -1, max},
            {max, max, max, 5, 0, max, 3},
            {max, max, max, max, max, 0, 2},
            {max, max, max, max, max, max, 0}
        };

        int[] solution = {0, 2, 4};
        Assert.assertArrayEquals(solution, Challenge4b.solution(times, 6));
    }
}
