package challenge1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    @Test
    public void test1() {
        assertEquals("Yeah! I can't believe Lance lost his job at the colony!!", Solution.solution("Yvzs! I xzm'g yvorvev Lzmxv olhg srh qly zg gsv xlolmb!!"));
    }

    @Test
    public void test2() {
        assertEquals("did you see last night's episode?", Solution.solution("wrw blf hvv ozhg mrtsg'h vkrhlwv?"));
    }
}
