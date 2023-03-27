package edu.uwp.cs.csci340.assignment.a02.program2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

import static edu.uwp.cs.csci340.assignment.a02.program2.FindMedian.findMedian;

public class FindMedianTest
{
    public static void main(String[] args) throws Throwable
    {
        double result;
        int[] size = {10, 50, 1000000, 5000000};
        // Sort and print a few small arrays to make certain the sort works
        SortandMedian(size[0], size[1], 10);
        // Sort and time larger arrays
        SortandMedian(size[2], size[3], 1000000);
        // Sort and time larger arrays
        SortandMedian(size[2] + 1, size[3] + 1, 1000000);
    }

    public static void SortandMedian(int start, int end, int inc) throws Throwable
    {
        double result;
        for (int size = start; size <= end; size += inc)
        {

            int[] array = createRandomArray(size);
            System.gc();

            long startTime = System.nanoTime();
            //Instructor's implementation of sort
//            Arrays.sort(array); // You will call findMedian method from edu.uwp.cs.csci340.assignment.a02.program2.FindMedian class
//            result = array[array.length / 2];
            /*
             * We will replace this with
             * result = edu.uwp.cs.csci340.assignment.a02.program2.FindMedian.findMedian(array);
             */
            result = findMedian(array);
            long endTime = System.nanoTime();

            System.out.println("Array size = " + size +
                    "\tMedian = " + result +
                    "\tSearch time = " +
                    (endTime - startTime) / 1000000000.0 + " seconds");
        }
    }

    // This method reads creates an array of random integers
    public static int[] createRandomArray(int size) throws Throwable
    {
        Random r = new Random(50);
        int[] array = new int[size];

        for (int i = 0; i < size; i++)
        {
            array[i] = StdRandom.uniform(100000);
        }

        return array;
    }
}
