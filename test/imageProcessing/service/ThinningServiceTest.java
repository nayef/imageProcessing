package imageProcessing.service;

import imageProcessing.service.ThinningService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThinningServiceTest {

    ThinningService thinningService;

    @Before
    public void setUp() throws Exception {
        thinningService = new ThinningService();
    }

    @Test
    public void test_doZhangSuenThinning() {
        int[][] inputData = {
                {1, 1, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}};

        int[][] expectedData = {
                {1, 1, 0, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        int[][] resultData = thinningService.doZhangSuenThinning(inputData, false);
        assertArrayEquals(expectedData, resultData);

    }
}