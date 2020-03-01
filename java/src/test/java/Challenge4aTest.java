import org.junit.Assert;
import org.junit.Test;

public class Challenge4aTest {

    @Test
    public void test() {
        int[] entrances = {0, 1};
        int[] exits = {4, 5};
        int[][] corridors = {{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
        Assert.assertEquals(16, Challenge4a.solution(entrances, exits, corridors));
    }

    @Test
    public void test2() {
        int[] entrances = {0};
        int[] exits = {3};
        int[][] corridors = {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}};
        Assert.assertEquals(6, Challenge4a.solution(entrances, exits, corridors));
    }
}
