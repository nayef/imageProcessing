package imageProcessing.service;

import imageProcessing.utils.CommonUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author nayef
 *         This class is used for blurring gray level image.
 *         two default filters are provided filter9 and filter16
 */

public class BlurringService {
    public static final int[][] filter9 = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };

    public static final int[][] filter16 = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };

    /**
     * @param givenImage
     * @param filter           use filter9 or filter16
     * @param iterationNum     the bigger the number the blurrier
     * @param changeGivenImage decides whether or not the givenImage should be blurred or a new bufferedImage
     *                         will be returned with blurring results
     * @return Blurred Image
     */
    public BufferedImage getBlurredImage(BufferedImage givenImage, int[][] filter, int iterationNum, boolean changeGivenImage) {
        BufferedImage srcImage;

        if (changeGivenImage) {
            srcImage = givenImage;
        } else {
            srcImage = CommonUtils.cloneImage(givenImage);
        }

        int count = 0;
        Color tempColor;
        while (count < iterationNum) {

            for (int y = 1; y + 1 < srcImage.getHeight(); y++) {
                for (int x = 1; x + 1 < srcImage.getWidth(); x++) {
                    tempColor = getFilteredValue(srcImage, y, x, filter);
                    srcImage.setRGB(x, y, tempColor.getRGB());

                }
            }
            count++;
        }
        return srcImage;
    }

    private Color getFilteredValue(final BufferedImage givenImage, int y, int x, int[][] filter) {
        int r = 0, g = 0, b = 0;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {

                r += (filter[1 + j][1 + k] * (new Color(givenImage.getRGB(x + k, y + j))).getRed());
                g += (filter[1 + j][1 + k] * (new Color(givenImage.getRGB(x + k, y + j))).getGreen());
                b += (filter[1 + j][1 + k] * (new Color(givenImage.getRGB(x + k, y + j))).getBlue());
            }

        }
        r = r / sum(filter);
        g = g / sum(filter);
        b = b / sum(filter);
        return new Color(r, g, b);
    }

    private int sum(int[][] filter) {
        int sum = 0;
        for (int y = 0; y < filter.length; y++) {
            for (int x = 0; x < filter[y].length; x++) {
                sum += filter[y][x];
            }
        }
        return sum;
    }

}
