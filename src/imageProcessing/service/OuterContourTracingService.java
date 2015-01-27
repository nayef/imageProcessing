package imageProcessing.service;

import imageProcessing.model.ContourTracingPoint;
import imageProcessing.model.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nayef on 1/27/15.
 * <p/>
 * This class is used for tracing outer contour
 * using square tracing algo for binary image
 * caution: border pixels are cleared before processing
 */
public class OuterContourTracingService {


    /**
     * @param srcImage
     * @return list of contour points
     */
    public List<? extends Point> getContourContourPoints(int[][] srcImage) {

        int[][] image = srcImage.clone();

        clearBorder(image);

        List<ContourTracingPoint> ContourTracingPoints = new LinkedList();
        ContourTracingPoint startingContourTracingPoint = getStartingContourTracingPoint(image);
        ContourTracingPoint currentContourTracingPoint = startingContourTracingPoint.getClone();

        do {
            if (image[currentContourTracingPoint.getY()][currentContourTracingPoint.getX()] == 1) {
                ContourTracingPoints.add(currentContourTracingPoint.getClone());
                currentContourTracingPoint.advanceToLeft();
            } else {
                currentContourTracingPoint.advanceToRight();
            }

        } while (!startingContourTracingPoint.equals(currentContourTracingPoint));

        return ContourTracingPoints;
    }

    private ContourTracingPoint getStartingContourTracingPoint(int[][] image) {

        for (int y = image.length - 1; y >= 0; y--) {
            for (int x = image[y].length - 1; x >= 0; x--) {
                if (image[y][x] == 1) {
                    return new ContourTracingPoint(x, y);
                }
            }
        }

        return null;
    }

    private void clearBorder(int[][] image) {

        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                if (y == 0 || x == 0 || y == image.length - 1 || x == image[y].length - 1) {
                    image[y][x] = 0;
                }
            }
        }
    }

}
