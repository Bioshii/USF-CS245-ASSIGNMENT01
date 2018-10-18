

import java.util.Random;

/**
 * Assignment01Test
 * A class which manages various sorting algorithms and reports the timing of them.
 * This main class contains some basic code to get started.
 *
 * The Assignment01Test is a modified version of a class by name Practice04Test, originally written by David Guy Brizan
 * @author dgbrizan
 *
 */
public class Assignment01Test {

    protected int [] arr;                  // This is the array to be sorted.
    protected SortingFactory factory;      // This creates different sorting algorithms.
    protected String [] algorithms = {"hybridsort", "mergesort"}; // Algos to use.


    /**
     * Default Constructor
     */
    public Assignment01Test() {
        createNewArray(10000);
        factory = new SortingFactory();
    }

    /**
     * Sets the array and populates it
     * @param size
     */
    protected void createNewArray(int size) {
        arr = new int[size];
        populateArrayRandomly();
    }


    /**
     * Changes the size of the array.
     * @param newSize
     */
    public void changeArraySize(int newSize) {
        createNewArray(newSize);
    }


    /**
     * Populates the array with random values.
     */
    protected void populateArrayRandomly() {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            // arr[i] = r.nextInt();
            arr[i] = r.nextInt(100);
        }
        System.out.println();
    }


    /**
     * Checks whether the array is sorted.
     * @return true if sorted; false otherwise.
     */
    protected boolean isSorted(int [] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i] > arr[i+1])
                return false;
        }
        return true;
    }


    /**
     * Prints the array size and whether sorted or not.
     * @param // array to be considered.
     */
    public void printStatus(int [] arr) {
        System.out.print(arr.length + "\t");
        if (isSorted(arr))
            System.out.println("[OK]");
        else
            System.out.println("[XX] -- not sorted");
    }


    /**
     * Makes a copy of the array. This helps to compare sorting algorithms.
     * @return a copy of the internal array.
     */
    public int [] copyArray() {
        int [] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }


    /**
     * Iterates over the array variable "algorithms", instantiates each and determines the timing.
     * Sends that to stdout.
     */
    public void printSortingTiming() {
        // Objective:
        // 1) Feed "algorithms" variable to the factory in order to get a sorting algorithm:
        for (String algo : algorithms) {
            try {
                SortingAlgorithm sort = factory.getSortingAlgorithm(algo);
                // System.out.println("----------------------------------------------------");
                // System.out.println("algorithm: " + algo);
                System.out.print(algo + "\t");
                // For each algorithm:
                // a) Copy the array
                int [] copy = copyArray();
                // b) Have the algorithm sort the copy ... while timing it.
                long start = System.currentTimeMillis();
                sort.sort(copy);
                // System.out.println("Sorting took: " + (System.currentTimeMillis() - start) + " ms.");
                long total_time = System.currentTimeMillis() - start;
                System.out.print(total_time + " ms.\t");
                if (total_time < 1000) {
                    System.out.print("\t");
                }
                // c) Check for correctness
                printStatus(copy);
                // System.out.println("----------------------------------------------------");
            }
            catch (Exception e) {
                System.out.println("Unable to instantiate sorting algorithm " + algo);
            }
        }
    }


    /**
     * main: try 10 different array sizes; make
     * @param args
     */
    public static void main(String[] args) {

        Assignment01Test timing = new Assignment01Test();
        int [] sizes = {50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000};
        //int [] sizes = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
        //int [] sizes = {20};

        for (int size : sizes) {

            timing.changeArraySize(size);
            // timing.printStatus();
            timing.printSortingTiming();
            System.out.println("----------------------------------------------------");
        }

    }

}
