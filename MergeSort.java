import java.util.Arrays;
public class MergeSort implements SortingAlgorithm {

	public void sort (int[] arr) {
		int left = 0;
		int right = arr.length-1;
		mergeSort(arr, left, right);
	}

	public void mergeSort(int[] arr, int left, int right) {
		// Return when there is only one value
		if (left == right) {
			return;
		}

		// Find the split value
		int mid = (left + right) / 2;

		// Sort the left side, then right side, then merge the sides
		mergeSort(arr, left, mid);
		mergeSort(arr, mid+1, right);
		merge(arr, left, right, mid);
	}


	public void merge(int[] arr, int left, int right, int mid) {
		merge(Arrays.copyOfRange(arr, left, mid+1), Arrays.copyOfRange(arr, mid+1, right+1), arr, left);
	}

	public void merge(int[] left, int[] right, int[] target, int startHere) {
		// Start here correlates to where in the target array elements will begin being inserted
        int index = startHere;
        // 2 Indexes that keep up with what position it currently points to in respective arrays
        int rightIndex = 0;
        int leftIndex = 0;

        // While both indexes are less than their respsective lengths, keep comparing and putting values in the correct order
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                target[index++] = left[leftIndex++];
            } else {
                target[index++] = right[rightIndex++];
            }
        }

        // If there is anything left in either of the arrays, fill the rest of the target array up witih those elemetns
        while (rightIndex < right.length) {
            target[index++] = right[rightIndex++];
        }
        while (leftIndex < left.length) {
            target[index++] = left[leftIndex++];
        }
	}
}
