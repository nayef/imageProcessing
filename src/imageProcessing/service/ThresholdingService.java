package imageProcessing.service;

import imageProcessing.utils.CommonUtils;

/**
 * @author nayef
 * This class is used for thresholding gray level image
 * and get binary image.
 */

public class ThresholdingService {


    /**
     * @param grayScaleValues
     * @return the threshold value
     */
    public double getOtsuThreshold(int[] grayScaleValues) {

        int[] n = getHistogram(grayScaleValues);
        double[] p = getProbabilities(n, grayScaleValues.length);
        double[] Wo = getWo(p);
        double W = getW(p);
        double[] W1 = getW1(Wo, W);
        double UT = getUT(p);
        double[] Ut = getUt(p);
        double[] Uo = getUo(Ut, Wo);
        double[] U1 = getU1(UT, Ut, Uo);
        double sigmaSqrT = getSigmaSqrT(UT, p);
        double[] sigmaSqrBt = getSigmaSqrBt(Wo, W1, U1, Uo);
        double[] eta = getEta(sigmaSqrBt, sigmaSqrT);

        return CommonUtils.getIndexOfMaxVal(eta);

    }

    /**
     * @param grayScale2DImage
     * @return a 2D array after Bernsen’s local gray level thresholding.
     */
    public int[][] getLocalThresholdedData(final int[][] grayScale2DImage, boolean removeGhosts) {

        int[][] fMax = getFMax(grayScale2DImage);
        int[][] fMin = getFMin(grayScale2DImage);
        int[][] g = getG(fMax, fMin);
        int[][] b = getB(grayScale2DImage, g, removeGhosts, fMax, fMin);
        return b;

    }

    private int[] getHistogram(int[] grayScaleValues) {
        int[] histogram = new int[256];

        for (int index = 0; index < grayScaleValues.length; index++) {
            histogram[grayScaleValues[index]]++;
        }
        return histogram;
    }

    private double[] getProbabilities(int[] histogram, int totalPixels) {

        double[] probability = new double[histogram.length];

        for (int index = 0; index < probability.length; index++) {
            probability[index] = ((double) histogram[index]) / ((double) totalPixels);
        }

        return probability;
    }

    private double[] getWo(double[] probability) {

        double[] Wo = new double[probability.length];
        Wo[0] = probability[0];

        for (int index = 1; index < Wo.length; index++) {
            Wo[index] = Wo[index - 1] + probability[index];
        }

        return Wo;
    }

    private double getW(double[] probability) {

        double W = 0;

        for (int index = 0; index < probability.length; index++) {
            W += probability[index];
        }

        return W;
    }

    private double[] getW1(double[] Wo, double W) {

        double[] W1 = new double[Wo.length];

        for (int index = 0; index < W1.length; index++) {
            W1[index] = W - Wo[index];
        }

        return W1;
    }

    private double getUT(double[] probability) {

        double UT = 0;

        for (int index = 0; index < probability.length; index++) {
            UT += (((double) index) * probability[index]);
        }

        return UT;

    }

    private double[] getUt(double[] probability) {

        double[] Ut = new double[probability.length];

        Ut[0] = 0;
        for (int index = 1; index < probability.length; index++) {
            Ut[index] = Ut[index - 1] + (((double) index) * probability[index]);
        }

        return Ut;
    }

    private double[] getUo(double[] Ut, double[] Wo) {

        double[] Uo = new double[Ut.length];

        for (int index = 0; index < Ut.length; index++) {
            Uo[index] = Ut[index] / Wo[index];
        }

        return Uo;

    }

    private double[] getU1(double UT, double[] Ut, double[] Uo) {

        double[] U1 = new double[Ut.length];

        for (int index = 0; index < U1.length; index++) {
            U1[index] = (UT - Ut[index]) / (1 - Uo[index]);
        }

        return U1;

    }

    private double getSigmaSqrT(double UT, double[] probability) {

        double sigmaSqrT = 0;

        for (int index = 0; index < probability.length; index++) {
            sigmaSqrT += (Math.pow((index - UT), 2) * probability[index]);
        }

        return sigmaSqrT;

    }

    private double[] getSigmaSqrBt(double[] Wo, double[] W1, double[] U1, double[] Uo) {
        double sigmaSqrBt[] = new double[Wo.length];

        for (int index = 0; index < sigmaSqrBt.length; index++) {
            sigmaSqrBt[index] = Wo[index] * W1[index] * Math.pow((U1[index] - Uo[index]), 2);
        }

        return sigmaSqrBt;
    }

    private double[] getEta(double[] sigmaSqrBt, double sigmaSqrT) {
        double eta[] = new double[sigmaSqrBt.length];
        for (int index = 0; index < sigmaSqrBt.length; index++) {
            eta[index] = sigmaSqrBt[index] / sigmaSqrT;
        }
        return eta;
    }

    private int[][] getFMax(int[][] grayScale2DImage) {

        int[][] fMax = new int[grayScale2DImage.length][grayScale2DImage[0].length];

        for (int y = 0; y < fMax.length; y++) {
            for (int x = 0; x < fMax[y].length; x++) {
                fMax[y][x] = getMaxNeighbour(grayScale2DImage, y, x);
            }
        }
        return fMax;
    }

    private int getMaxNeighbour(int[][] grayScale2DImage, int y, int x) {
        int max = -1;
        //upper left
        if (y - 1 >= 0 && x - 1 >= 0 && max < grayScale2DImage[y - 1][x - 1]) {
            max = grayScale2DImage[y - 1][x - 1];
        }
        //directly up
        if (y - 1 >= 0 && max < grayScale2DImage[y - 1][x]) {
            max = grayScale2DImage[y - 1][x];
        }
        //uppper right
        if (y - 1 >= 0 && x + 1 < grayScale2DImage[y].length && max < grayScale2DImage[y - 1][x + 1]) {
            max = grayScale2DImage[y - 1][x + 1];
        }

        //right
        if (x + 1 < grayScale2DImage[y].length && max < grayScale2DImage[y][x + 1]) {
            max = grayScale2DImage[y][x + 1];
        }

        //lower right
        if (y + 1 < grayScale2DImage.length && x + 1 < grayScale2DImage[y].length && max < grayScale2DImage[y + 1][x + 1]) {
            max = grayScale2DImage[y + 1][x + 1];
        }

        //below
        if (y + 1 < grayScale2DImage.length && max < grayScale2DImage[y + 1][x]) {
            max = grayScale2DImage[y + 1][x];
        }

        //lower left
        if (y + 1 < grayScale2DImage.length && x - 1 >= 0 && max < grayScale2DImage[y + 1][x - 1]) {
            max = grayScale2DImage[y + 1][x - 1];
        }
        //left
        if (x - 1 >= 0 && max < grayScale2DImage[y][x - 1]) {
            max = grayScale2DImage[y][x - 1];
        }
        return max;
    }

    private int getMinNeighbour(int[][] grayScale2DImage, int y, int x) {
        int min = 256 + 100;
        //upper left
        if (y - 1 >= 0 && x - 1 >= 0 && min > grayScale2DImage[y - 1][x - 1]) {
            min = grayScale2DImage[y - 1][x - 1];
        }

        //directly up
        if (y - 1 >= 0 && min > grayScale2DImage[y - 1][x]) {
            min = grayScale2DImage[y - 1][x];
        }

        //uppper right
        if (y - 1 >= 0 && x + 1 < grayScale2DImage[y].length && min > grayScale2DImage[y - 1][x + 1]) {
            min = grayScale2DImage[y - 1][x + 1];
        }

        //right
        if (x + 1 < grayScale2DImage[y].length && min > grayScale2DImage[y][x + 1]) {
            min = grayScale2DImage[y][x + 1];
        }

        //lower right
        if (y + 1 < grayScale2DImage.length && x + 1 < grayScale2DImage[y].length && min > grayScale2DImage[y + 1][x + 1]) {
            min = grayScale2DImage[y + 1][x + 1];
        }

        //below
        if (y + 1 < grayScale2DImage.length && min > grayScale2DImage[y + 1][x]) {
            min = grayScale2DImage[y + 1][x];
        }

        //lower left
        if (y + 1 < grayScale2DImage.length && x - 1 >= 0 && min > grayScale2DImage[y + 1][x - 1]) {
            min = grayScale2DImage[y + 1][x - 1];
        }

        //left
        if (x - 1 >= 0 && min > grayScale2DImage[y][x - 1]) {
            min = grayScale2DImage[y][x - 1];
        }

        return min;
    }

    private int[][] getFMin(int[][] grayScale2DImage) {
        int[][] fMin = new int[grayScale2DImage.length][grayScale2DImage[0].length];

        for (int y = 0; y < fMin.length; y++) {
            for (int x = 0; x < fMin[y].length; x++) {
                fMin[y][x] = getMinNeighbour(grayScale2DImage, y, x);
            }
        }

        return fMin;
    }

    private int[][] getG(int[][] fMax, int[][] fMin) {
        int[][] g = new int[fMax.length][fMax[0].length];

        for (int y = 0; y < fMax.length; y++) {
            for (int x = 0; x < fMax[y].length; x++) {
                g[y][x] = (fMax[y][x] + fMin[y][x]) / 2;
                //notice,fMax[y][x]+fMin[y][x]
            }
        }
        return g;

    }


    private int[][] getB(int[][] grayScale2DImage, int[][] g, boolean removeGhosts, int[][] fMax, int[][] fMin) {
        if (removeGhosts) {
            return getBWithGhostRemoval(grayScale2DImage, g, fMax, fMin);
        } else {
            return getBWithoutGhostRemoval(grayScale2DImage, g);
        }
    }

    private int[][] getBWithGhostRemoval(int[][] grayScale2DImage, int[][] g, int[][] fMax, int[][] fMin) {

        int[][] b = new int[grayScale2DImage.length][grayScale2DImage[0].length];
        int c[][] = getC(fMax, fMin);

        int cStar = (int) getOtsuThreshold(CommonUtils.convert2DArrayTo1D(c));
        int fStar = (int) getOtsuThreshold(CommonUtils.convert2DArrayTo1D(g));

        for (int y = 0; y < grayScale2DImage.length; y++) {
            for (int x = 0; x < grayScale2DImage[y].length; x++) {
                if ((grayScale2DImage[y][x] < g[y][x] && c[y][x] > cStar)
                        || (grayScale2DImage[y][x]) < fStar && c[y][x] <= cStar) {
                    b[y][x] = 1;
                } else {
                    b[y][x] = 0;
                }
            }
        }

        return b;

    }

    private int[][] getBWithoutGhostRemoval(int[][] grayScale2DImage, int[][] g) {

        int[][] b = new int[grayScale2DImage.length][grayScale2DImage[0].length];

        for (int y = 0; y < grayScale2DImage.length; y++) {
            for (int x = 0; x < grayScale2DImage[y].length; x++) {
                if (grayScale2DImage[y][x] < g[y][x]) {
                    b[y][x] = 1;
                } else {
                    b[y][x] = 0;
                }
            }
        }

        return b;
    }

    private int[][] getC(int[][] fMax, int[][] fMin) {
        int[][] c = new int[fMax.length][fMax[0].length];

        for (int y = 0; y < fMax.length; y++) {
            for (int x = 0; x < fMax[y].length; x++) {
                c[y][x] = fMax[y][x] - fMin[y][x];
            }
        }

        return c;
    }

}