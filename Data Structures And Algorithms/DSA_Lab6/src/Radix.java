
// Radix sort Java implementation
import java.util.*;

class Radix {

    public static void showArray(int[] arr) {
        if(arr.length>0) {
            System.out.print(arr[0]);
            for(int i=1;i<arr.length;i++)
                System.out.print(" "+arr[i]);
        }
        System.out.println();
    }

    public void countSort(int[] arr, int decimalPlace) {
        int[] outputArray = new int[arr.length];

        int[] count = new int[10];
        Arrays.fill(count, 0);

        int i;

        for (i = 0; i < arr.length; i++) {
            count[(arr[i] / decimalPlace) % 10]++;
        }
        for (i = 1; i < 10; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (i = arr.length - 1; i >= 0; i--) {
            outputArray[count[(arr[i] / decimalPlace) % 10] - 1] = arr[i];
            count[(arr[i] / decimalPlace) % 10]--;
        }
        for (i = 0; i < arr.length; i++) {
            arr[i] = outputArray[i];
        }
        showArray(outputArray);
    }

    // Radix Sort
    public void radixSort(int[] arr) {
        if(arr == null)
            return;
        showArray(arr);
        // weight => placeValue [weight 1-999 => placeValue 1-100]
        for (int placeValue = 1; placeValue < 1000; placeValue = placeValue * 10)
            countSort(arr, placeValue);
    }

    public static void main(String[] args) {
        int[] array = {20, 7, 10, 3, 8, 4, 1, 900 };
        Radix ob2 = new Radix();
        ob2.radixSort(array);
        System.out.println();
    }

}
