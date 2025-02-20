package ru.sterus.tpo.lab1.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class to represent a Fibonacci Heap
class FibonacciHeap {
    // Class to represent a node in the Fibonacci Heap
    static class FibonacciHeapNode {
        int key;
        int degree;
        FibonacciHeapNode parent;
        FibonacciHeapNode child;
        FibonacciHeapNode left;
        FibonacciHeapNode right;
        boolean mark;

        // Constructor to initialize a new node
        public FibonacciHeapNode(int key) {
            this.key = key;
            this.degree = 0;
            this.mark = false;
            this.parent = null;
            this.child = null;
            this.left = this;
            this.right = this;
        }
    }
    // Pointer to the minimum node in the heap
    private FibonacciHeapNode minNode;

    // Total number of nodes in the heap
    private int nodeCount;

    // Constructor to initialize an empty Fibonacci Heap
    public FibonacciHeap() {
        minNode = null;
        nodeCount = 0;
    }

    // Insert a new node into the Fibonacci Heap
    public void insert(int key) {
        FibonacciHeapNode node = new FibonacciHeapNode(key);
        if (minNode != null) {
            // Add the new node to the root list
            addToRootList(node);
            if (key < minNode.key) {
                // Update the minNode pointer if necessary
                minNode = node;
            }
        } else {
            // Set the new node as the minNode if the heap was empty
            minNode = node;
        }
        nodeCount++;
    }

    // Add a node to the root list of the Fibonacci Heap
    private void addToRootList(FibonacciHeapNode node) {
        node.left = minNode;
        node.right = minNode.right;
        minNode.right.left = node;
        minNode.right = node;
    }

    // Find the minimum node in the Fibonacci Heap
    public FibonacciHeapNode findMin() {
        return minNode;
    }

    // Extract the minimum node from the Fibonacci Heap
    public FibonacciHeapNode extractMin() {
        FibonacciHeapNode min = minNode;
        if (min != null) {
            if (min.child != null) {
                // Add the children of the minNode to the root list
                addChildrenToRootList(min);
            }

            // Remove the minNode from the root list
            removeNodeFromRootList(min);
            if (min == min.right) {
                // Set minNode to null if it was the only node in the root list
                minNode = null;
            }
            else {
                minNode = min.right;
                // Consolidate the trees in the root list
                consolidate();
            }
            nodeCount--;
        }
        return min;
    }

    // Add the children of a node to the root list
    private void addChildrenToRootList(FibonacciHeapNode min) {
        FibonacciHeapNode child = min.child;
        do {
            FibonacciHeapNode nextChild = child.right;
            child.left = minNode;
            child.right = minNode.right;
            minNode.right.left = child;
            minNode.right = child;
            child.parent = null;
            child = nextChild;
        } while (child != min.child);
    }

    // Remove a node from the root list
    private void removeNodeFromRootList(FibonacciHeapNode node) {
        node.left.right = node.right;
        node.right.left = node.left;
    }

    // Consolidate the trees in the root list
    private void consolidate() {
        int arraySize = ((int) Math.floor(Math.log(nodeCount) / Math.log(2.0))) + 1;
        List<FibonacciHeapNode> array = new ArrayList<>(Collections.nCopies(arraySize, null));
        List<FibonacciHeapNode> rootList = getRootList();

        for (FibonacciHeapNode node : rootList) {
            int degree = node.degree;
            while (array.get(degree) != null) {
                FibonacciHeapNode other = array.get(degree);
                if (node.key > other.key) {
                    FibonacciHeapNode temp = node;
                    node = other;
                    other = temp;
                }

                // Link two trees of the same degree
                link(other, node);
                array.set(degree, null);
                degree++;
            }
            array.set(degree, node);
        }

        minNode = null;
        for (FibonacciHeapNode node : array) {
            if (node != null) {
                if (minNode == null) {
                    minNode = node;
                } else {

                    // Add the node back to the root list
                    addToRootList(node);
                    if (node.key < minNode.key) {
                        minNode = node;
                    }
                }
            }
        }
    }

    // Get a list of all root nodes
    private List<FibonacciHeapNode> getRootList() {
        List<FibonacciHeapNode> rootList = new ArrayList<>();
        if (minNode != null) {
            FibonacciHeapNode current = minNode;
            do {
                rootList.add(current);
                current = current.right;
            } while (current != minNode);
        }
        return rootList;
    }

    // Link two trees of the same degree
    private void link(FibonacciHeapNode y, FibonacciHeapNode x) {
        // Remove y from the root list
        removeNodeFromRootList(y);
        y.left = y.right = y;
        y.parent = x;

        if (x.child == null) {
            // Make y a child of x
            x.child = y;
        } else {
            y.right = x.child;
            y.left = x.child.left;
            x.child.left.right = y;
            x.child.left = y;
        }
        x.degree++;
        y.mark = false;
    }
}