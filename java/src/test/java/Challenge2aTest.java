import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Challenge2aTest {

    @Test
    public void test1() {
        assertEquals(3, Challenge2a.solution(143));
    }

    @Test
    public void test2() {
        assertEquals(1, Challenge2a.solution(10));
    }

    @Test
    public void test3() {
        assertEquals(0, Challenge2a.solution(1));
    }

    @Test
    public void test4() {
        assertEquals(1, Challenge2a.solution(7));
    }
}
