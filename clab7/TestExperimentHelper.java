import org.junit.Test;
import static org.junit.Assert.*;

public class TestExperimentHelper {
    @Test
    public void someTest() {
        assertEquals(0, ExperimentHelper.optimalIPL(1));
        assertEquals(1, ExperimentHelper.optimalIPL(2));
        assertEquals(2, ExperimentHelper.optimalIPL(3));
        assertEquals(4, ExperimentHelper.optimalIPL(4));
        assertEquals(6, ExperimentHelper.optimalIPL(5));
        assertEquals(8, ExperimentHelper.optimalIPL(6));
        assertEquals(10, ExperimentHelper.optimalIPL(7));
        assertEquals(13, ExperimentHelper.optimalIPL(8));

        assertEquals(0, ExperimentHelper.optimalAverageDepth(1), 0.01);
        assertEquals(1.2, ExperimentHelper.optimalAverageDepth(5), 0.01);
        assertEquals(1.625, ExperimentHelper.optimalAverageDepth(8), 0.01);
    }
}
