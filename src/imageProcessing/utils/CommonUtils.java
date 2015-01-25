package imageProcessing.utils;

/**
 * Created by nayef on 1/26/15.
 */
public class CommonUtils {


    /**
     * @param array
     * @return the index of array which contains the maximum value
     */
    public static int getIndexOfMaxVal(double[] array) {

        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[maxIndex] < array[i]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    /**
     * @param array
     * @return returns a 1D array from given 2D array
     */
    public static int[] convert2DArrayTo1D(int[][] array) {

        int[] oneD = new int[array.length * array[0].length];
        int index = 0;
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                oneD[index++] = array[y][x];
            }
        }

        return oneD;
    }

}
