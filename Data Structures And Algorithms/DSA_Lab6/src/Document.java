import java.util.Arrays;
import java.util.ListIterator;
import java.util.Scanner;

public class Document {

    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<>();
        load(scan);
    }

    // load(scan)
    public void load(Scanner scan) {
        String documentLink = "link=";
        String documentEnd = "eod";
        String line = scan.nextLine().toLowerCase();
        while(!line.equalsIgnoreCase(documentEnd)) { // loading line links here!
            String[] arr = line.split(" ");
            for(String word:arr) {
                if(word.startsWith(documentLink)) { // look for link
                    String links = word.substring(documentLink.length()); //words with link= loaded!

                    Link Links = createLink(links);
                    if(Links!=null) { // extract the letters after link=
                        link.add(Links);
                    }

                }
            }
            line = scan.nextLine().toLowerCase();
        }
    }

    // accept only small letters, capital letter, digits nad '_' (but not on the beginning)
    // id: zero, ggg, etc. (consists of document name and link=name)
    public static boolean isCorrectId(String id) {
        if (id.toLowerCase().length() == 0)
            return false;
        if ((id.toLowerCase().charAt(0) < 'a' || id.toLowerCase().charAt(0) > 'z'))
            return false;
        for (int i = 1; i < id.toLowerCase().length(); i++) {
            if (!(id.toLowerCase().charAt(i) >= 'a' && id.toLowerCase().charAt(i) <= 'z' ||
                    id.toLowerCase().charAt(i) >= '0' && id.toLowerCase().charAt(i) <= '9' ||
                    id.toLowerCase().charAt(i) == '_' ))
                return false;
        }
        return true;
    }

    public static Link creatingNewLink(String id, int weight) {
        if(!isCorrectId(id.toLowerCase())) {
            return null;
        } else {
            return new Link(id.toLowerCase(), weight);
        }
    }

    // check if number in parentheses is correct (not negative, not decimal ie integer, not alphabet)
    // final link is concatenation of correctID and correct number in parentheses
    public static Link createLink(String link) throws NumberFormatException {
        if(link.length()==0) return null;
        // -1 if no parenthesis
        int open_parenthesis = link.indexOf('(');
        int close_parenthesis = link.indexOf(')');
        if(open_parenthesis>0 && close_parenthesis>open_parenthesis && close_parenthesis==link.length()-1) {
            //(123) => 123 1 = start_index and 3 end_index for substring
            String stringNumber = link.substring(open_parenthesis+1, close_parenthesis);
            try {
                int num = Integer.parseInt(stringNumber);
                if (num < 1)
                    return null;
                return creatingNewLink(link.substring(0, open_parenthesis), num);
            } catch (NumberFormatException Exception) {
                return null;
            }
        }
        return creatingNewLink(link,1);
    }

    // Normal and reverse order: first line must display 10 links and
    // the next line will display rest of the links
    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder("Document: " + name);
        int numOfLinks = 10;
        for (Link linkElem : link) {
            if (numOfLinks == 10) {
                retStr.append("\n"); // add links to new line after 10 links
                numOfLinks = 0;
            } else
                retStr.append(" "); // continue display if not and add space
            retStr.append(linkElem.toString());
            numOfLinks++;
        }
        return retStr.toString();
    }

    public String toStringReverse() {
        StringBuilder retStr= new StringBuilder("Document: " + name);
        int numOfLinks=0;
        ListIterator<Link> iter=link.listIterator();
        while(iter.hasNext())
            iter.next();
        while(iter.hasPrevious()){
            if(numOfLinks%10 == 0)
                retStr.append("\n");
            else
                retStr.append(" ");
            retStr.append(iter.previous().toString());
            numOfLinks++;
        }
        return retStr.toString();
    }


    // array of integer: weights taken from links of the document
    public int[] getWeights() {
        int[] array = new int[link.size];
        for (int i = 0; i < link.size(); i++) {
            Link currentData = link.get(i);
            array[i] = currentData.getWeight();
        }
        return array;
    }

    public static void showArray(int[] arr) {
        if (arr == null)
            return;
        if(arr.length > 0) {
            System.out.print(arr[0]);
            for(int i = 1; i < arr.length; i++)
                System.out.print(" " + arr[i]);

            System.out.println();
        }
    }

    public void bubbleSort(int[] array) {
        showArray(array);
        for (int i = 0; i < array.length-1; i++) {
            for (int j = array.length-1; j > i; j-- ) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
            showArray(array);
        }
    }

    public void insertSort(int[] arr) {
        showArray(arr);
        for (int i = arr.length - 2; i >= 0; --i) {
            int current = arr[i];
            int j = i + 1;
            while (j <= arr.length - 1 && arr[j] < current) {
                arr[j - 1] = arr[j];
                j = j + 1;
            }
            arr[j - 1] = current;
            showArray(arr);
        }
    }

    public void selectSort (int[] arr) {
        showArray(arr);
        for (int i = arr.length-1; i > 0; i--) {
            int max_index = i;
            for (int j = 0; j < i; j++)
                if (arr[j] > arr[max_index])
                    max_index = j;
            int temp = arr[max_index];
            arr[max_index] = arr[i];
            arr[i] = temp;
            showArray(arr);
        }
    }

    // Merge two sorted sub-arrays array[left…mid] and array[mid+1…right]
    public void merge(int[] arr, int[] temp, int left, int middle, int right) {
        int k = left, i = left, j = middle + 1;
        // loop till no elements are left in the left and right runs
        while (i <= middle && j <= right) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // copy remaining elements
        while (i <= middle) {
            temp[k++] = arr[i++];
        }
        // no need to copy the second half (since remaining items are already sorted in temporary array)
        // copy back to the original array to reflect sorted order
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }
    }
    // Iteratively sort sub-array arr[low…high] using a temporary array
    public void iterativeMergeSort(int[] arr) {
        showArray(arr);
        if(arr == null || arr.length <=1)
            return;
        int low = 0;
        int high = arr.length-1;
        // sort array arr[] using a temporary array "temp"
        int[] temp = Arrays.copyOf(arr, arr.length);
        // divide the array into blocks of "width"
        for (int width = 1; width <= high-low; width = 2 * width) {
            for (int i = low; i < high; i = i + 2 * width) {
                int left; left = i;
                int middle = i + width - 1;
                int right = Integer.min(i + 2 * width - 1, high);
                merge(arr, temp, left, middle, right);
            }
            showArray(arr);
        }
    }

    // A function to do counting sort of arr[] according to the digit represented by placeValue.
    public void countSort(int[] arr, int placeValue) {
        int[] outputArray = new int[arr.length];
        int[] digitCount = new int[10]; // digits 0-9
        Arrays.fill(digitCount, 0); // fill 9 places with 0's
        int i;
        // digitCount every placeValue
        for (i = 0; i < arr.length; i++) {
            digitCount[(arr[i] / placeValue) % 10]++;
        }
        // digitCount[i] contains actual position of this digit in outputArray[]
        // sum up index values with predecessor values
        for (i = 1; i < 10; i++) {
            digitCount[i] += digitCount[i - 1];
        }
        // mapping index's to the values to create outputArray storing sorted values
        for (i = arr.length - 1; i >= 0; i--) {
            outputArray[digitCount[(arr[i] / placeValue) % 10] - 1] = arr[i]; // decrement digitCount array which shows indices of sorted array
            digitCount[(arr[i] / placeValue) % 10]--; // traversing backwards
        }
        // Copy outputArray to original arr[] (sorted numbs decimals place [0th, 10th, 100th])
        for (i = 0; i < arr.length; i++) {
            arr[i] = outputArray[i];
        }
        showArray(outputArray);
    }

    public void radixSort(int[] arr) {
        showArray(arr);
        if(arr == null || arr.length == 0)
            return;
        //  place value = 10^i
        // weight => placeValue [weight 1-999 => placeValue 1-100]
        for (int placeValue = 1; placeValue <= 999; placeValue = placeValue * 10) {
            countSort(arr, placeValue);
        }
    }

}


// iterativeMergeSort time complexity
// countSort time complexity = O(n+k) n = num of elem (7), k == b = range of elem (10): https://www.youtube.com/watch?v=0B33As8jPgo [stable algorithm]
// radixSort time complexity = O(d*(n+b)), d = num of steps (depending on PlaceValue), n = num of elem, b = range of elem https://www.youtube.com/watch?v=XiuSW_mEn7g