import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class Challenge3cTest {

    @Test
    public void test() {
        Assert.assertEquals(5, Challenge3c.solution("15"));
    }

    @Test
    public void test2() {
        Assert.assertEquals(2, Challenge3c.solution("4"));
    }

    @Test
    public void test3() {
        Assert.assertEquals(1363, Challenge3c.solution("24864614303596598796920782309286869334179515047412664361939260524782400586952573653427496778458408122213829317031586643370178179242347769424387121267005601410980150407734404624333036170522800562803603312477364455808169441147216360466057845743012443128764877808647238350865776243635470705613942345600623021221"));
    }
}
