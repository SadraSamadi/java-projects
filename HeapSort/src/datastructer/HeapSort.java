package datastructer;

/**
 * The Class HeapSort.
 */
public class HeapSort {

	/**
	 * Ascending.
	 *
	 * @param array
	 *            the array
	 */
	public static void ascending(int[] array) {
		int length = array.length;
		buildMaxHeap(array, length);
		for (int i = length - 1; i > 0; i--) {
			swap(array, 0, i);
			maxHeapify(array, i, 0);
		}
	}

	/**
	 * Builds the max heap.
	 *
	 * @param array
	 *            the array
	 * @param length
	 *            the length
	 */
	private static void buildMaxHeap(int[] array, int length) {
		for (int i = length / 2 - 1; i >= 0; i--)
			maxHeapify(array, length, i);
	}

	/**
	 * Max heapify.
	 *
	 * @param array
	 *            the array
	 * @param length
	 *            the length
	 * @param target
	 *            the target
	 */
	private static void maxHeapify(int[] array, int length, int target) {
		int left = 2 * target + 1;
		int right = 2 * target + 2;
		int largest = target;
		if (left < length && array[left] > array[largest])
			largest = left;
		if (right < length && array[right] > array[largest])
			largest = right;
		if (largest != target) {
			swap(array, target, largest);
			maxHeapify(array, length, largest);
		}
	}

	/**
	 * Descending.
	 *
	 * @param array
	 *            the array
	 */
	public static void descending(int[] array) {
		int length = array.length;
		buildMinHeap(array, length);
		for (int i = length - 1; i > 0; i--) {
			swap(array, 0, i);
			minHeapify(array, i, 0);
		}
	}

	/**
	 * Builds the min heap.
	 *
	 * @param array
	 *            the array
	 * @param length
	 *            the length
	 */
	private static void buildMinHeap(int[] array, int length) {
		for (int i = length / 2 - 1; i >= 0; i--)
			minHeapify(array, length, i);
	}

	/**
	 * Min heapify.
	 *
	 * @param array
	 *            the array
	 * @param length
	 *            the length
	 * @param target
	 *            the target
	 */
	private static void minHeapify(int[] array, int length, int target) {
		int left = 2 * target + 1;
		int right = 2 * target + 2;
		int smallest = target;
		if (left < length && array[left] < array[smallest])
			smallest = left;
		if (right < length && array[right] < array[smallest])
			smallest = right;
		if (smallest != target) {
			swap(array, target, smallest);
			minHeapify(array, length, smallest);
		}
	}

	/**
	 * Swap.
	 *
	 * @param array
	 *            the array
	 * @param index1
	 *            the index1
	 * @param index2
	 *            the index2
	 */
	private static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

}
