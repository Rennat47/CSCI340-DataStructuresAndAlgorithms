/************************************************************************
 * @author(s) Tanner Smith, Jonathan Stephens
 * @edu.uwp.cs.340.course CSCI 340 - DataStructures and Algorithms
 * @edu.uwp.cs.340.section001
 * @edu.uwp.cs.340.assignment04
 * @bugs none
 *
 ***********************************************************************/
package edu.uwp.cs.csci340.assignment.a04.program4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that implements a two-dimensional binary tree of points
 */
public class TwoDTree
{
    private TwoDTreeNode root;

    /**
     * Default constructor
     */
    public TwoDTree()
    {
        root = null;
    }

    /**
     * Constructor that iterates through an ArrayList of Points to add to the TwoDTree
     *
     * @param list List of points to be inserted into the TwoDTree
     */
    public TwoDTree(ArrayList<Point> list)
    {
        root = null;
        for (int i = 0; i < list.size(); i++)
        {
            insert(list.get(i));
        }
    }

    /**
     * insert a point into the tree
     *
     * @param p
     */
    public void insert(Point p)
    {
        //If the root doesn't exist make this point the root
        if (root == null)
        {
            root = new TwoDTreeNodeY(p);
        } else
        {
            root.addChild(p);
        }
    }

    /**
     * Search - searches if a point exits on the tree
     *
     * @param p the point to be searched for
     * @return ture if the point exits on the tree, false if it does not
     */
    public boolean search(Point p)
    {
        //Initialize current node to the root
        TwoDTreeNode currentNode = root;
        //Loop until a leaf is reached or point is found (leaf = null node)
        while (currentNode != null)
        {
            //Check to see if the current node is equal to the point
            if (currentNode.getPoint().x == p.x && currentNode.getPoint().y == p.y)
            {
                return true;
            }
            //Compare the node to the point
            if (currentNode.compareTo(p) >= 0)
            {
                //If current node is bigger than the search point continue down the left subtree
                currentNode = currentNode.getLeftChild();
            } else
            {
                //If current node is smaller than the search point continue down the right subtree
                currentNode = currentNode.getRightChild();
            }
        }
        //Reached a null node search failed
        return false;
    }

    /**
     * searchRange - forces p1 to act as a lower bound and p2 to act as an upper bound.
     * Follows 3 rules
     * if the current point is withing the range add it to the list
     * if the left child is greater than p1 (lower bound) traverse that child
     * if the right child is less than p2 (upper bound) traverse that child
     * Traversal is done using a stack and while loop
     *
     * @param p1 key 1 point
     * @param p2 key 2 point
     * @return List of points bounded by the rectangle of p1 and p2
     */
    public ArrayList<Point> searchRange(Point p1, Point p2)
    {
        //Initialize list of points to be returned
        ArrayList<Point> returnList = new ArrayList<Point>();
        //Stack used for traversal
        Stack<TwoDTreeNode> stack = new Stack<TwoDTreeNode>();
        //Node currently being examined
        TwoDTreeNode currentNode;
        //Ensure p1.x < p2.x and p1.y < p2.y
        sortPoints(p1, p2);
        if (root != null) //Prevent crash if called on empty tree
        {
            stack.push(root); //Initialize traversal stack with the root
        }
        while (!stack.isEmpty())
        {
            //Set current node to the top of the stack
            currentNode = stack.pop();
            //Check if the current point falls within the range and add it to the list
            if (checkPointRange(p1, p2, currentNode.getPoint()))
            {
                returnList.add(currentNode.getPoint());
            }
            //Check if the right sub-child is less than p2
            if (currentNode.compareTo(p2) <= 0 && currentNode.getRightChild() != null)
            {
                //Push right sub-child onto the stack
                stack.push(currentNode.getRightChild());
            }
            //Check if the left sub-child is greater than p1
            if (currentNode.compareTo(p1) >= 0 && currentNode.getLeftChild() != null)
            {
                //Push left sub-child onto the stack
                stack.push(currentNode.getLeftChild());
            }
        }
        //Return the list of points
        return returnList;
    }

    /**
     * sortPoints - swaps p1.x and p2.x and p1.y and p2.y such that p1.x < p2.x and p1.y < p2.y
     *
     * @param p1 point 1
     * @param p2 point 2
     */
    private void sortPoints(Point p1, Point p2)
    {
        if (p1.x > p2.x)
        {
            int temp = p1.x;
            p1.x = p2.x;
            p2.x = temp;
        }
        if (p1.y > p2.y)
        {
            int temp = p1.y;
            p1.y = p2.y;
            p2.y = temp;
        }
    }

    /**
     * checkPointRange - checks if a point falls withing a rectangle bound by two other points
     * assumes that lower.x < upper.x and lower.y < upper.y
     *
     * @param lower lower point bound
     * @param upper upper point bound
     * @param value value being evaluated
     * @return true if the point lies within the rectangle bounded by lower and upper
     */
    private boolean checkPointRange(Point lower, Point upper, Point value)
    {
        return (lower.x <= value.x && lower.y <= value.y && upper.x >= value.x && upper.y >= value.y);
    }

    /**
     * Private inner interface
     * TwoDTreeNode - Interface for TwoDTreeNodeX and TwoDTreeNodeY
     */
    private interface TwoDTreeNode extends Comparable<Point>
    {
        /**
         * getPoint
         *
         * @return(s) the point
         */
        public Point getPoint();

        /**
         * addChild adds a child to the node
         *
         * @param point point to be added
         */
        public void addChild(Point point);

        /**
         * getLeftChild
         *
         * @return left child of this node
         */
        public TwoDTreeNode getLeftChild();

        /**
         * getRightChild
         *
         * @return right child of this node
         */
        public TwoDTreeNode getRightChild();

    }

    /**
     * TwoDTreeNodeX - Private class for tree nodes that compares based on x value
     */
    private class TwoDTreeNodeX implements TwoDTreeNode
    {
        //Holds point for node
        private Point p;
        //Holds left child of parent node
        private TwoDTreeNode leftChild;
        //Holds right child of parent node
        private TwoDTreeNode rightChild;

        /**
         * TwoDTreeNodeX - Constructor for tree node that compares based on x value
         *
         * @param point - Point to be stored in node
         */
        public TwoDTreeNodeX(Point point)
        {
            p = point;
        }

        /**
         * addChild - compares x values of points and checks if node is null (a leaf)
         * if x of point is <= parent, add as left child. Else add as right child
         *
         * @param point - Point of node being added
         */
        public void addChild(Point point)
        {
            if (point.x <= p.x)
            {
                if (leftChild == null)
                {
                    leftChild = new TwoDTreeNodeY(point);
                } else
                {
                    leftChild.addChild(point);
                }
            } else
            {
                if (rightChild == null)
                {
                    rightChild = new TwoDTreeNodeY(point);
                } else
                {
                    rightChild.addChild(point);
                }
            }
        }

        /**
         * getPoint - returns Point of node
         *
         * @return Point of node
         */
        public Point getPoint()
        {
            return p;
        }

        /**
         * getLeftChild - returns left child of parent
         *
         * @return left child node of parent node
         */
        public TwoDTreeNode getLeftChild()
        {
            return leftChild;
        }

        /**
         * getRightChild - returns right child of parent
         *
         * @return right child node of parent node
         */
        public TwoDTreeNode getRightChild()
        {
            return rightChild;
        }

        /**
         * compareTo - overrides compareTo to compare values of points (x value)
         *
         * @param point - Point being compared against parent
         * @return 1 if param Point is smaller, -1 if bigger, and 0 if equal
         */
        public int compareTo(Point point)
        {
            if (point.x < p.x)
            {
                return 1;
            } else if (point.x > p.x)
            {
                return -1;
            }
            return 0;
        }
    }

    /**
     * TwoDTreeNodeY - Private class for tree nodes that compares based on y value
     */
    private class TwoDTreeNodeY implements TwoDTreeNode
    {
        //Holds point for node
        private Point p;
        //Holds left child of parent node
        private TwoDTreeNode leftChild;
        //Holds right child of parent node
        private TwoDTreeNode rightChild;

        /**
         * TwoDTreeNodeY - Constructor for tree node that compares based on y value
         *
         * @param point - Point to be stored in node
         */
        public TwoDTreeNodeY(Point point)
        {
            p = point;
        }

        /**
         * addChild - compares y values of points and checks if node is null (a leaf)
         * if y of point is <= parent, add as left child. Else add as right child
         *
         * @param point - Point of node being added
         */
        public void addChild(Point point)
        {
            if (point.y <= p.y)
            {
                if (leftChild == null)
                {
                    leftChild = new TwoDTreeNodeX(point);
                } else
                {
                    leftChild.addChild(point);
                }
            } else
            {
                if (rightChild == null)
                {
                    rightChild = new TwoDTreeNodeX(point);
                } else
                {
                    rightChild.addChild(point);
                }
            }
        }

        /**
         * getPoint - returns Point of node
         *
         * @return Point of node
         */
        public Point getPoint()
        {
            return p;
        }

        /**
         * getLeftChild - returns left child of parent
         *
         * @return left child node of parent node
         */
        public TwoDTreeNode getLeftChild()
        {
            return leftChild;
        }

        /**
         * getRightChild - returns right child of parent
         *
         * @return right child node of parent node
         */
        public TwoDTreeNode getRightChild()
        {
            return rightChild;
        }

        /**
         * compareTo - overrides compareTo to compare values of points (y value)
         *
         * @param point - Point being compared against parent
         * @return 1 if param Point is smaller, -1 if bigger, and 0 if equal
         */
        public int compareTo(Point point)
        {
            if (point.y < p.y)
            {
                return 1;
            } else if (point.y > p.y)
            {
                return -1;
            }
            return 0;
        }
    }
}




