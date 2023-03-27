/**
 * This class reads an input file called input.txt and separates Integers, Doubles, and Strings.
 * Then it sorts and prints the Integers, Doubles, and Stings in reverse sorted order.
 * The class consists of just the main method
 *
 * @Author(s) Tanner Smith, Jonathan Stephens
 * @edu.uwp.cs.340.course CSCI 340 - Data Structures and Algorithms
 * @edu.uwp.cs.340.section 001
 * @edu.uwp.cs.340.assignment 1
 * @bugs none
 */
package edu.uwp.cs.csci340.assignment.a01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyIterableTester
{
    /**
     * Main implements the functionality described in the class header
     * @param args required to run. UNUSED
     */
    public static void main(String[] args)
    {
        ArrayList<String> stringList = new ArrayList<String>(); //ArrayList to hold String tokens
        ArrayList<Integer> integerList = new ArrayList<Integer>(); //ArrayList to hold Integer tokens
        ArrayList<Double> doubleList = new ArrayList<Double>(); //ArrayList to hold Double tokens

        try
        {
            File input = new File("./src/input.txt"); //Creates a File object
            Scanner inputScanner = new Scanner(input); //Creates a scanner to scan our File object
            while (inputScanner.hasNext()) //Loops while the file still has tokens to read in
            {
                if (inputScanner.hasNextInt()) //Checks if the next input is an int
                {
                    integerList.add(inputScanner.nextInt()); //adds int to integerList
                } else if (inputScanner.hasNextDouble()) //Checks if the next input is a double
                {
                    doubleList.add(inputScanner.nextDouble()); //adds double to doubleList
                } else //If the input isn't an int or double, it must be a string
                {
                    stringList.add(inputScanner.next()); //adds string to stringList
                }
            }
        } catch (FileNotFoundException e) //catches error for no available file
        {
            System.out.println("No file was found.");
        }

        //Converts all the ArrayLists into regular arrays and uses them as a parameter for the MyIterable constructor
        //MyIterable class sorts the data in reverse order within it's constructor
        MyIterable<String> stringTester = new MyIterable<String>(stringList.toArray(new String[stringList.size()]));
        MyIterable<Integer> integerTester = new MyIterable<Integer>(integerList.toArray(new Integer[integerList.size()]));
        MyIterable<Double> doubleTester = new MyIterable<Double>(doubleList.toArray(new Double[doubleList.size()]));

        //Below is the code to print out all the sorted data
        //Because MyIterable implements iterable we can use for each loops
        //Print out the Strings
        System.out.println("My Friends:");
        for (String s : stringTester)
        {
            System.out.print(s + " ");
        }
        //Print out the Integers
        System.out.println();
        System.out.println("----------+---------");
        System.out.println("My numbers:");
        for (Integer integer : integerTester)
        {
            System.out.print(integer + " ");
        }
        //Print out the Doubles
        System.out.println();
        System.out.println("----------+---------");
        System.out.println("My scores:");
        for (Double aDouble : doubleTester)
        {
            System.out.print(aDouble + " ");
        }
    }


}
