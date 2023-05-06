import java.util.Arrays;

public class Merge {
    public static void showArray(int[] arr) {
        if(arr.length>0) {
            System.out.print(arr[0]);
            for(int i=1;i<arr.length;i++)
                System.out.print(" "+arr[i]);
        }
        System.out.println();
    }

    // Merge two sorted sub-arrays array[left…mid] and array[mid+1…right]
    public void merge(int[] arr, int[] arrTemp, int left, int middle, int right) {
        int k = left, i = left, j = middle + 1;
        // k = current index
        // traverse till no elements are left in the left and right runs
        while (i <= middle && j <= right) {
            if (arr[i] < arr[j]) {
                arrTemp[k++] = arr[i++];
            } else {
                // arrTemp is replaced by smaller previous index
                arrTemp[k++] = arr[j++];
            }
        }
        // swap and copy remaining elements
        while (i <= middle) {
            arrTemp[k++] = arr[i++];
        }
        // no need to copy the second half (since remaining items are already sorted in temporary array)
        // copy back to the original array to reflect sorted order
        for (i = left; i <= right; i++) {
            arr[i] = arrTemp[i];
        }
    }
    // Iteratively sort sub-array arr[low…high] using a temporary array
    public void iterativeMergeSort(int[] arr) {
        if(arr == null)
            return;
        showArray(arr);
        int Left = 0;
        int Right = arr.length-1;
        // sort array using a arrTemp
        int[] arrTemp = Arrays.copyOf(arr, arr.length);
        // divide the array into blocks of "width"
        for (int width = 1; width <= Right-Left; width = 2 * width) {
            for (int i = Left; i < Right; i = i + 2 * width) {
                int left;left = i;
                int middle = i + width - 1;
                int right = Integer.min(i + 2 * width - 1, Right);
                // merge subArray
                merge(arr, arrTemp, left, middle, right);
            }
            showArray(arr);
        }
    }

    public static void main(String[] args) {
        int[] array = {20, 7, 10, 3, 8, 4, 1};
        Merge ob2 = new Merge();
        ob2.iterativeMergeSort(array);
        System.out.println();
    }

}
