/************************************************************************
 *
 * Finds the median of an array by implementing a modified quicksort to
 * sort the array as few times as possible.
 *
 * @author(s) Tanner Smith, Jonathan Stephens
 * @edu.uwp.cs.340.course CSCI 340 - Computer Science II
 * @edu.uwp.cs.340.section001
 * @edu.uwp.cs.340.assignment02
 * @bugs none (that we are aware of)
 *
 ***********************************************************************/

package edu.uwp.cs.csci340.assignment.a02.program2;

import java.util.Arrays;

public class FindMedian
{
    /**
     * findMedian - This method finds the median value of the input array by partially
     * applying the pivot technique from quicksort until the pivot index is the middle
     * of the array, which results in a much faster time to find the median than
     * sorting the entire array.
     *
     * @param array array you want the median of. NOTE: THE ARRAY IS MODIFIED
     * @return the median of the input array
     */
    public static double findMedian(int[] array)
    {
        double median;
        int j = 0; //Index of pivot
        int low = 0; //Low index
        int high = array.length - 1; //Index of High
        int middle = (array.length / 2) + 1; //Middle value of the array
        while (j != middle) //Repeat until pivot value is the center of the array
        {
            j = partition(array, low, high); //Algs4j partition method from Quick
            if (j < middle)
            {
                /*
                 *   The partition is skewed to the left
                 *   Visual:
                 *   [XXXXX-J-L-XXXXXXXXX-H]
                 *   We want to now partition the right side where
                 *   L represents the new low and H represents the new High
                 *   and X represents random a data element
                 */
                low = j + 1;
            } else if (j > middle)
            {
                /*
                 *   The partition is skewed to the right
                 *   Visual:
                 *   [L-XXXXXXXXXXXXX-H-J-XXXXX]
                 *   We want to now partition the right side where
                 *   L represents the new low and H represents the new High
                 *   and X represents random a data element
                 */
                high = j - 1;
            }
        }
        if (array.length % 2 == 0) //Even
        {
            /*
            If the array is even the while loop will end on finding the right middle index J
            We aren't guaranteed that the left middle index (J-1) is the correct value
            So we only sort that last partition to keep the amount of required sorting to a minimum
            Visual:
            [XXXXXXXXX-L-xxxxxx-J-XXXXXX-H-XXXXXXX]
                      sort ^ here
             */
            Arrays.sort(array, low, j);
            median = ((double) array[j] + (double) array[j - 1]) / 2;
        } else //Odd
        {
            median = array[j];
        }
        return median;
    }


    /**
     * Partition - COPIED FROM ALGS4J with minor modifications
     * partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
     *
     * @param a  pass through array
     * @param lo low end of the partition
     * @param hi high end of the partition
     * @return index j
     */
    private static int partition(int[] a, int lo, int hi)
    {
        int i = lo;
        int j = hi + 1;
        int v = a[lo];
        while (true)
        {

            // find item on lo to swap
            while (a[++i] < v)
            {
                if (i == hi) break;
            }

            // find item on hi to swap
            while (v < a[--j])
            {
                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            }

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    /**
     * exch - COPIED FROM ALGS4J with minor modifications
     * exchanges a[i] and a[j]
     *
     * @param a - pass through array
     * @param i - element 1 index to be swapped
     * @param j - element 2 index to be swapped
     */
    private static void exch(int[] a, int i, int j)
    {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
