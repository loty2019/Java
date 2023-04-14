import stdlib.StdIn;
import stdlib.StdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPair {
    // Prints the closest pair of integers in a, separated by a space.
    private static void closestPair(int[] a) {
        Arrays.sort(a);
        int minGap = Integer.MAX_VALUE;
        String shortestPair = "";

        for (int i = 0; i < a.length - 1; i++) {
            int currentGap = a[i+1] - a[i];
            if (currentGap < minGap) {
                minGap = currentGap;
                shortestPair = a[i] + " " + a[i+1];
            }
        }
        StdOut.println(shortestPair);
    }

    // Entry point .
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        closestPair(a);
    }
}