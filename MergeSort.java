import java.util.Arrays;
public class MergeSort implements SortingAlgorithm {

	public void sort (int[] arr) {
		int left = 0;
		int right = arr.length-1;
		mergeSort(arr, left, right);
	}

	public void mergeSort(int[] arr, int left, int right) {
		if (left == right) {
			return;
		}

		int mid = (left + right) / 2;

		mergeSort(arr, left, mid);
		mergeSort(arr, mid+1, right);
		merge(arr, left, right, mid);
	}


	public void merge(int[] arr, int left, int right, int mid) {
		merge(Arrays.copyOfRange(arr, left, mid+1), Arrays.copyOfRange(arr, mid+1, right+1), arr, left);
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
