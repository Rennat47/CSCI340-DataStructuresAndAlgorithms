/************************************************************************
 * @author(s) Tanner Smith, Jonathan Stephens
 * @edu.uwp.cs.340.course CSCI 340 - DataStructures and Algorithms
 * @edu.uwp.cs.340.section001
 * @edu.uwp.cs.340.assignment05
 * @bugs none
 ***********************************************************************/


import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

/**
 * Implements logic for a word ladder using the given files (words.3 to words.9)
 * This program only builds the graph up to point where it needs to
 */
public class WordLadder
{
    /**
     * Main method for testing word ladder with user input
     *
     * @param args not used
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable
    {
        String start, end; // the words on which the ladder is based
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the beginning word");
        start = in.next();
        System.out.println("Enter the ending word");
        end = in.next();
        // Check length of the words
        int length = start.length();
        if (length != end.length())
        {
            System.err.println("ERROR! Words not of the same length.");
            System.exit(1);
        }
        HashMap<String, ArrayList<String>> wordlist = new HashMap<>();
        breadthFirstSearch(wordlist, start, end);

    }

    /**
     * Assumes start and end are the same length
     * does a breadthFirstSearch of a graph, building the graph as necessary.
     * Only finds neighbors of words being searched.
     * The search finds the shortest word ladder between start and end then prints the path
     *
     * @param graph the graph being built
     * @param start the word being started with
     * @param end   the word trying to be reached
     */
    public static void breadthFirstSearch(HashMap<String, ArrayList<String>> graph, String start, String end)
    {
        //Flag boolean for when the word is found
        boolean found = false;
        //Queue used for BFS search
        Queue<String> searchQueue = new LinkedList<String>();
        //Hash Map to keep track of visited words. Hash map is more efficient that searching an array of values visited
        HashMap<String, Integer> visited = new HashMap<String, Integer>();
        /*
        Hash Map that keeps an inverse reference of how each word is reached
        for example if we go from dog --> fog the hash map will hold an entry:
        (fog, dog). This is used to trace our path back to the start word once the
        end word of the ladder is found
        */
        HashMap<String, String> pathTrace = new HashMap<String, String>();
        //initialize the Queue with the starting word
        searchQueue.add(start);
        visited.put(start, 1);
        //Main BFS Loop
        while (!searchQueue.isEmpty() && !found)
        {
            //Get next word
            String currentWord = searchQueue.remove();
            addNeighbors(currentWord, graph);
            //Add entries to the graph for the current word
            ArrayList<String> neighbors = graph.get(currentWord);
            for (String neighbor : neighbors)
            {
                //Check and queue all un-visited neighbors
                if (!visited.containsKey(neighbor))
                {
                    visited.put(neighbor, 1);
                    searchQueue.add(neighbor);
                    pathTrace.put(neighbor, currentWord);
                    //Check if the end word is found
                    if (neighbor.equalsIgnoreCase(end))
                    {
                        found = true;
                        break; //break out of for loop
                    }
                }
            }
        }
        if (!found) //If target word was not found, print an error and exit with code 2.
        {
            System.out.println("Target word was not found.");
            System.exit(2);
        } else //If target word was found
        {
            //Stack used for reversing the traced path
            Stack<String> path = new Stack<String>();
            String word = end;
            while (pathTrace.containsKey(word))
            {
                path.push(word);
                word = pathTrace.get(word);
            }
            //Print the path of the ladder
            System.out.print(start);
            while (!path.isEmpty())
            {
                System.out.print(" -> " + path.pop());
            }
        }
    }

    /**
     * Finds the words that have a 1 character difference from the passed in word and adds them to an ArrayList.
     * It then puts the ArrayList into a HashMap with the word as the key
     *
     * @param word  the word we're finding neighbors for
     * @param graph the HashMap we're adding the neighbors ArrayList to
     */
    public static void addNeighbors(String word, HashMap<String, ArrayList<String>> graph)
    {
        File wordFile = new File("./src/words." + word.length()); //List of words of the same length as word
        ArrayList<String> neighbors = new ArrayList<>(); //ArrayList of words neighboring word
        try
        {
            Scanner fileScanner = new Scanner(wordFile); //Reads wordFile
            while (fileScanner.hasNextLine())
            {
                String nextWord = fileScanner.nextLine();
                if (characterDifferenceAmount(word, nextWord) == 1)
                {
                    neighbors.add(nextWord);
                }
            }
            graph.put(word, neighbors);
            fileScanner.close();
        } catch (FileNotFoundException e)
        {
            System.err.println("A scanner error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Takes in two strings and compares their characters to get the number of characters that are different
     *
     * @param s1 first string to compare
     * @param s2 second string to compare
     * @return int representing amount of characters different between s1 and s2
     */
    public static int characterDifferenceAmount(String s1, String s2)
    {
        int diff = 0; //counts the number of different characters in s1 compared to s2
        char[] s1Arr = s1.toCharArray();
        char[] s2Arr = s2.toCharArray();
        for (int i = 0; i < s1.length(); i++)
        {
            if (s1Arr[i] != s2Arr[i])
            {
                diff++;
            }
        }
        return diff;
    }
}
