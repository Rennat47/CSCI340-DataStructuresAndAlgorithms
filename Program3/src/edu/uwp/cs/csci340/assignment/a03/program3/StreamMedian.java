/************************************************************************
 *
 * StreamMedian takes in a stream of integers and returns their median value
 *
 * @author(s) Tanner Smith, Jonathan Stephens
 * @edu.uwp.cs.340.course CSCI 340 - Data Structures and Algorithms
 * @edu.uwp.cs.340.section001
 * @edu.uwp.cs.340.assignment03
 * @bugs none (that we are aware of)
 *
 ***********************************************************************/
package edu.uwp.cs.csci340.assignment.a03.program3;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StreamMedian
{
    private PriorityQueue<Integer> bigger; //Min-heap to hold the larger half of numbers
    private PriorityQueue<Integer> smaller; //Max-heap to hold the smaller half of numbers

    /**
     * Initializes the two priority queues
     */
    public StreamMedian()
    {
        bigger = new PriorityQueue<Integer>();
        smaller = new PriorityQueue<Integer>(new Comparator<Integer>()
        {
            /*
             * Overrides the compare method to flip the Comparator results
             * in order to implement a Max-Heap
             */
            public int compare(Integer x, Integer y)
            {
                return x.compareTo(y) * -1;
            }
        });
    }
    /**
     * insert() - inserts the next integer into the smaller of the two heaps, if they are equal in size
     * smaller gets the next element. Then compares the two values on top of both heaps and swaps the elements
     * into the other heap if necessary
     *
     * @param i - next integer to be added to the stream
     */
    public void insert(Integer i)
    {
        if (smaller.size() <= bigger.size()) //Smaller is less than or equal to bigger in size
        {
            smaller.add(i);
        } else //Bigger is larger in size
        {
            bigger.add(i);
        }
        /*
         * Try catch block for first pass
         * Bigger will throw a null pointer exception when the first element is added
         */
        try
        {
            if (smaller.peek() > bigger.peek()) //Check if values need to swap
            {
                //Swap values
                int temp = smaller.poll();
                smaller.add(bigger.poll());
                bigger.add(temp);
            }
        } catch (NullPointerException e) //Prevent crash on first pass
        {
            //Do nothing
        }
    }

    /**
     * getMedian() - returns average of 2 middle elements if total size is even, otherwise returns largest element in smaller
     *
     * @return current median value of the data stream
     */
    public double getMedian()
    {
        double median; //element to return
        if (bigger.size() == smaller.size()) //checks to see if bigger + smaller is even
        {
            median = (double) (bigger.peek() + smaller.peek()) / 2; //average of 2 middle elements
        } else //if bigger + smaller is odd
        {
            median = smaller.peek(); //largest element in smaller
        }
        return median;
    }
}
