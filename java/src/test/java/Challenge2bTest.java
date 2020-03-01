import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class Challenge2bTest {

    @Test
    public void test() {
        Assert.assertEquals(3, Challenge2b.solution("210022", 3));
    }

    @Test
    public void test2() {
        assertEquals(1, Challenge2b.solution("1211", 10));
    }
}
