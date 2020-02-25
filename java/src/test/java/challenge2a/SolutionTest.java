package challenge2a;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    @Test
    public void test1() {
        assertEquals(3, Solution.solution(143));
    }

    @Test
    public void test2() {
        assertEquals(1, Solution.solution(10));
    }

    @Test
    public void test3() {
        assertEquals(0, Solution.solution(1));
    }

    @Test
    public void test4() {
        assertEquals(1, Solution.solution(7));
    }
}
