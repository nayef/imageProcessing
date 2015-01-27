package imageProcessing.service;

/**
 * Created by nayef on 1/27/15.
 * This class is used for smoothing binary images
 */
public class BinaryImageSmoothingService {

    public int[][] smoothBinaryImage(int[][] binaryImage) {

        boolean hasChange;
        int filterVal = 0;
        do {
            hasChange = false;
            for (int y = binaryImage.length - 2; y > 0; y--) {
                for (int x = binaryImage[y].length - 2; x > 0; x--) {
                    hasChange = hasChange || smoothPoint(binaryImage, y, x, filterVal);
                }
            }

            filterVal++;
            filterVal = filterVal % 4;

        } while (hasChange);

        return binaryImage;
    }

    private boolean smoothPoint(int[][] binaryImage, int y, int x, int filterVal) {

        if (filterVal == 0
                && binaryImage[y][x - 1] == binaryImage[y - 1][x - 1]
                && binaryImage[y - 1][x - 1] == binaryImage[y - 1][x]
                && binaryImage[y - 1][x] == binaryImage[y - 1][x + 1]
                && binaryImage[y - 1][x + 1] == binaryImage[y][x + 1]
                && binaryImage[y][x] != binaryImage[y][x - 1]) {

            binaryImage[y][x] = binaryImage[y][x - 1];
            return true;
        }

        if (filterVal == 1
                && binaryImage[y - 1][x] == binaryImage[y - 1][x + 1]
                && binaryImage[y - 1][x + 1] == binaryImage[y][x + 1]
                && binaryImage[y][x + 1] == binaryImage[y + 1][x + 1]
                && binaryImage[y + 1][x + 1] == binaryImage[y + 1][x]
                && binaryImage[y][x] != binaryImage[y - 1][x]) {

            binaryImage[y][x] = binaryImage[y - 1][x];
            return true;
        }

        if (filterVal == 2
                && binaryImage[y][x + 1] == binaryImage[y + 1][x + 1]
                && binaryImage[y + 1][x + 1] == binaryImage[y + 1][x]
                && binaryImage[y + 1][x] == binaryImage[y + 1][x - 1]
                && binaryImage[y + 1][x - 1] == binaryImage[y][x - 1]
                && binaryImage[y][x] != binaryImage[y][x + 1]) {

            binaryImage[y][x] = binaryImage[y][x + 1];
            return true;
        }

        if (filterVal == 3
                && binaryImage[y + 1][x] == binaryImage[y + 1][x - 1]
                && binaryImage[y + 1][x - 1] == binaryImage[y][x - 1]
                && binaryImage[y][x - 1] == binaryImage[y - 1][x - 1]
                && binaryImage[y - 1][x - 1] == binaryImage[y - 1][x]
                && binaryImage[y][x] != binaryImage[y + 1][x]) {

            binaryImage[y][x] = binaryImage[y + 1][x];
            return true;
        }

        return false;

    }
}
