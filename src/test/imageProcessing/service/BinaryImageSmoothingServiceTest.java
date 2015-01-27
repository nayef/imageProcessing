package test.imageProcessing.service;

import imageProcessing.service.BinaryImageSmoothingService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryImageSmoothingServiceTest {

    BinaryImageSmoothingService thinningService;

    @Before
    public void setUp() throws Exception {
        thinningService = new BinaryImageSmoothingService();
    }

    @Test
    public void testSmoothBinaryImage() throws Exception {

        int[][] inputData = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 0}
        };

        int[][] resultData = thinningService.smoothBinaryImage(inputData, false);

        int[][] expectedResult = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 0}
        };

        assertArrayEquals(expectedResult, resultData);
    }
}