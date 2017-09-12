package exercises;

import java.util.Arrays;
import java.util.Random;

import static java.lang.StrictMath.round;
import static java.lang.System.*;

/*
 *  Methods with array params and/or return value. Implement methods.
 *
 *  See:
 *  - MathMethods
 *  - ArrayMethods
 */
public class Ex1ArrayMethods {

    public static void main(String[] args) {
        new Ex1ArrayMethods().program();
    }

    final static Random rand = new Random();

    void program() {
        //int[] arr = {1, 2, 2, 5, 3, 2, 4, 2, 7};  // Hard coded test data

        // TODO uncomment one at a time and implement
        // Count occurrences of some element in arr
        //out.println(count(arr, 2) == 4);      // There are four 2's
        //out.println(count(arr, 7) == 1);

        // Generate array with 100 elements with 25% distribution of -1's and 1's (remaining will be 0)
        int[] arrgd = generateDistribution(100, 0.25, 0.25);
        out.println(Arrays.toString(arrgd));
        out.println(count(arrgd, 1) == 25);
        out.println(count(arrgd, -1) == 25);
        out.println(count(arrgd, 0) == 50);

        // Generate array with 14 elements with 40% 1's and 30% -1's
        /*int[] arr = generateDistribution(14, 0.4, 0.3);
        out.println(count(arr, 1) == 5);
        out.println(count(arr, -1) == 4);*/

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < 10; i++) {
            // Random reordering of arr, have to check by inspecting output
            shuffle(arr);
            out.println(Arrays.toString(arr));  // Does it look random?
        }

    }


    // ---- Write methods below this ------------
    int count(int[] arr, int value) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                count++;
            }
        }
        return count;
    }

    int randomNumber(int elements) {
        return rand.nextInt(elements);
    }

    int[] generateDistribution(int elements, double percent_1, double percent_n1) {
        int[] arrgd = getArr(100, 0);
        int numberOf1s = (int)Math.round(percent_1 * elements);
        int numberOfN1s = (int)Math.round(percent_n1 * elements);

        for (int i = 0; i < numberOf1s;) {
            int r1 = randomNumber(elements);
            if (arrgd[r1] == 0) {
                arrgd[r1] = 1;
                i++;
            }

        }

        for (int i = 0; i < numberOfN1s;) {
            int rn1 = randomNumber(elements);
            if (arrgd[rn1] == 0) {
                arrgd[rn1] = -1;
                i++;
            }

        }
        return arrgd;
    }

    int[] getArr(int size, int value) {
        int[] garr = new int[size];   // Create new array to return
        for (int i = 0; i < garr.length; i++) {
            garr[i] = value;
        }
        return garr;
    }

    int[] shuffle(int[] arr) {
        int i = 0;
        for (i = 0; i <= arr.length; i++) {
            arr[i] = arr[rand.nextInt(arr.length)];
        }
        return arr;
    }



}
