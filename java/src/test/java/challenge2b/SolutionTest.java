package challenge2b;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    @Test
    public void test() {
        assertEquals(3, Solution.solution("210022", 3));
    }

    @Test
    public void test2() {
        assertEquals(1, Solution.solution("1211", 10));
    }
}
