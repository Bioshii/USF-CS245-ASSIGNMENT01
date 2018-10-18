import java.util.ArrayList;
import java.util.Arrays;

public class HybridSort implements SortingAlgorithm{

    public void sort (int[] arr) {
        hybridSort(arr, 128 );
    }

    public void hybridSort(int[] arr, int run_size) {
        // 2 Array Lists. They will hold respective values for the start and end of a run.
        // Even are starts, Odd are Ends
        ArrayList<Integer> runs = new ArrayList<>();
        ArrayList<Integer> betweenRuns = new ArrayList<>();
        ArrayList<Integer> combined = new ArrayList<>();


        // Getting the Runs
        getRuns(arr, runs, run_size);


        // Sorting the segments
        segmentSort(arr, runs, betweenRuns, run_size);


        // Merging Segments
        // -------------------------------------------------------------------------------------------------------------
        int runsIndex = 0;
        int betweenRunsIndex = 0;


        while (betweenRunsIndex < betweenRuns.size() && runsIndex < runs.size()) {
            if (betweenRuns.get(betweenRunsIndex) <= runs.get(runsIndex)) {
                combined.add(betweenRuns.get(betweenRunsIndex++));
            } else {
                combined.add(runs.get(runsIndex++));
            }
        }

        while (betweenRunsIndex < betweenRuns.size()) {
            combined.add(betweenRuns.get(betweenRunsIndex++));
        }

        while (runsIndex < runs.size()) {
            combined.add(runs.get(runsIndex++));
        }

        // Time to sort
        while (combined.size() > 2) {

            int combineIndex = 0;
            while ((combined.size() - combineIndex) / 4 > 0 && combineIndex < combined.size()) {
                merge(Arrays.copyOfRange(arr, combined.get(combineIndex), combined.get(combineIndex+1)+1), Arrays.copyOfRange(arr, combined.get(combineIndex+2), combined.get(combineIndex+3)+1), arr, combined.get(combineIndex));
                combineIndex += 4;
            }



            for (int i = 0; i < combined.size(); i+=4) {
                if ((i+2) < combined.size()) {
                    combined.set(i + 1, -7);
                    combined.set(i + 2, -7);
                }
            }


            int index = 0;
            while (index < combined.size()) {
                if (combined.get(index) == -7) {
                    combined.remove(index);
                } else {
                    ++index;
                }
            }

        }

    }

    public void getRuns(int[] arr, ArrayList<Integer> runs, int run_size) {
        int index = 0;          // Index is where we are looking at in the arra
        int runStartIndex = 0;  // runStartIndex is where the current run we are trying starts
        int runEndIndex = 0;    // runEndIndex is where the current run we are trying ends

        // While loop that loops through array helping inspect runs.

        while (index < arr.length - 1) {
            if (arr[index] <= arr[index + 1]) {
                runEndIndex++; // Run Index is always starts being equal to the runStartIndex. If the next value is greater, increment
            } else if ((runEndIndex - runStartIndex) + 1 >= run_size) { // +1 is necessary to get the correct length
                // Add indexes to array lists
                runs.add(runStartIndex);
                runs.add(runEndIndex);

                // Get ready to check the next potential runs
                runStartIndex = index + 1;
                runEndIndex = runStartIndex;
            } else {
                // Get ready to check the next potential runs
                runStartIndex = index + 1;
                runEndIndex = runStartIndex;
            }

            index++;
        }

        if ((runEndIndex - runStartIndex) + 1 >= run_size) { // +1 is necessary to get the correct length
            // Add indexes to array lists
            runs.add(runStartIndex);
            runs.add(runEndIndex);
        }
    }

    public void segmentSort(int[] arr, ArrayList<Integer> runs, ArrayList<Integer> betweenRuns, int run_size) {
        // Sorting the segments
        // -------------------------------------------------------------------------------------------------------------------------------

        // Get the beginning before runs [ 0 to start of first run]
        // -----------------------------------------------------------------
        // Loop through the amount of segments determined
        // Determine segment amount in beginning, if only 1 value, put that in the between runs by itself (Special Case)
        if (runs.size() > 0) {
            int segmentAmount = determineSegmentAmount(0, runs.get(0), run_size); //Check
            // When startSortIndex + run_size is equal to or greater than the next run start, subtract till 1 less
            int endHere;
            if (run_size >= runs.get(0)) {
                endHere = runs.get(0) - 1;
            } else {
                endHere = run_size - 1;
            }
            sortSegments(arr, 0, endHere, runs.get(0), segmentAmount, run_size, betweenRuns);
            if (segmentAmount == 0 && runs.get(0) != 0) {
                betweenRuns.add(0);
                betweenRuns.add(0);
            }

            // Test multiple segments at beginning of array


            // Get the inbetween Runs
            // --------------------------------------------------------------------
            // Loop through the in between runs
            for (int i = 1; i < runs.size() - 1; i+=2) {
                segmentAmount = determineSegmentAmount(runs.get(i) + 1, runs.get(i+1), run_size);
                if (runs.get(i)+1 + run_size - 1 >= runs.get(i+1)) {
                    endHere = runs.get(i+1) - 1;
                } else {
                    endHere = runs.get(i) + 1 + run_size - 1;
                }
                sortSegments(arr, runs.get(i)+1, endHere, runs.get(i+1), segmentAmount, run_size, betweenRuns);
            }




            // Get the after runs
            //-----------------------------------------------------------------------
            int lastStartHere = runs.get(runs.size() - 1) + 1;
            if (lastStartHere < arr.length) {
                if (arr.length - lastStartHere == 1) {
                    betweenRuns.add(arr.length - 1);
                    betweenRuns.add(arr.length - 1);
                } else {
                    segmentAmount = determineSegmentAmount(runs.get(runs.size() - 1) + 1, arr.length, run_size);
                    if (runs.get(runs.size() - 1) + run_size >= arr.length) {
                        endHere = arr.length - 1;
                    } else {
                        endHere = runs.get(runs.size() - 1) + run_size;
                    }
                    sortSegments(arr, runs.get(runs.size() - 1) + 1, endHere, arr.length, segmentAmount, run_size, betweenRuns);
                }
            }
        } else {

                    int segmentAmount = determineSegmentAmount(0, arr.length, run_size);
                    sortSegments(arr, 0, run_size-1, arr.length, segmentAmount, run_size, betweenRuns);
        }
    }

    public void insertionSort(int[] arr, int start, int end) {
        // End is the element you are not supposed to touch
        // Insertion sort to the last value given in end INCLUSIVE
        for (int i = start; i <= end; i++) {
            int valueAti = arr[i];
            int j = i - 1;

            while (j >= start && valueAti < arr[j]) {
                arr[j+1] = arr[j];
                j--;
            }

            arr[j+1] = valueAti;
        }
    }

    public int determineSegmentAmount(int start, int end, int run_size) {
        if ((end-start) == 0) { // If there is no gap whatsoever
            return 0;
        }  else if ((end - start) % run_size == 0) {      // If there is no remainder, do integer division sorts
            return (end - start) / run_size;
        } else {                                          // If there is a remainder, do above + 1 sorts to account for smaller portion
            return ((end-start) / run_size)+1;
        }

    }

    /**
     * This function sorts the amount of segments determined
     * @param arr This is the array being sorted
     * @param sortStartIndex This is the starting index of the first segment to be sorted
     * @param sortEndIndex This is the end index of the first segment to be sorted
     * @param stopHere The end segment should never be greater than this, and should default to this when reached
     * @param segmentAmount This is the amount of segments that need to be sorted
     * @param run_size This is the run size that is configured
     */
    public void sortSegments(int[] arr, int sortStartIndex, int sortEndIndex, int stopHere, int segmentAmount, int run_size, ArrayList<Integer> betweenRuns) {
        // Loop through the amount of segments determined
        for (int i = 0; i < segmentAmount; i++) {
            insertionSort(arr, sortStartIndex, sortEndIndex);
            // This is good
            betweenRuns.add(sortStartIndex);
            betweenRuns.add(sortEndIndex);
            sortStartIndex = sortEndIndex + 1;
            sortEndIndex += run_size;   // This index is exclusive

            // If the end index enters run defined range, set it to runEnd
            if (sortEndIndex >= stopHere || sortStartIndex >= stopHere) {
                sortEndIndex = stopHere - 1;
            }
        }
    }

    public void merge(int[] left, int[] right, int[] target, int startHere) {
        int index = startHere;
        int rightIndex = 0;
        int leftIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                target[index++] = left[leftIndex++];
            } else {
                target[index++] = right[rightIndex++];
            }
        }


        while (rightIndex < right.length) {
            target[index++] = right[rightIndex++];
        }
        while (leftIndex < left.length) {
            target[index++] = left[leftIndex++];
        }
    }

}
