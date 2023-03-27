package edu.uwp.cs.csci340.assignment.a02.program2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.ThreeSumFast;
import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ThreeSumTimeTest
{

    public static void main(String[] args) throws FileNotFoundException
    {
        int size = 32000;
        System.out.println("Generating array of size: " + size);
        File input = new File("./src/1kints.txt"); //Creates a File object
        Scanner inputScanner = new Scanner(input);
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = inputScanner.nextInt();
        }
        inputScanner.close();
        int count = 0;
        System.out.println("Starting ThreeSum for array of size: " + size);
        Stopwatch timer = new Stopwatch();
        count = ThreeSumFast.count(array);
        double timeElapsed = timer.elapsedTime();
        System.out.println(timeElapsed + " seconds taken");
        System.out.println("Found " + count + " three sums");
    }

    public static int[] createRandomArray(int size) throws Throwable
    {
        Random r = new Random(50);
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
        {
            int nextInt = StdRandom.uniform(100000);

        }
        return array;
    }

}
