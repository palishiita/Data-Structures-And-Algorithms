import java.util.LinkedList;

public class KMPnew {
/*
    public LinkedList<Integer> validShifts(String pattern, String text)  {

        if (pattern == null || text == null)
            throw new NullPointerException();
        if (pattern.length() == 0 || text.length() == 0)
            return new LinkedList<>();

        LinkedList<Integer> result = new LinkedList<>();

        int patternLength = pattern.length();
        int textLength = text.length();

        // create lps[] that will hold the longest prefix suffix values for pattern
        int[] longestPrefixSuffix = new int[patternLength];
        // Preprocess the pattern (calculate lps[] array)
        computeLongestPrefixSuffixArray(pattern, patternLength, longestPrefixSuffix);

        int j = 0; // index for pattern[]
        int i = 0; // index for text[]
        while (i < textLength) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == patternLength) {
                result.add(i - j); // pattern found at index
                j = longestPrefixSuffix[j - 1];
            }

            // mismatch after j matches
            else if (i < textLength && pattern.charAt(j) != text.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters, they will match anyway
                if (j != 0) {
                    j = longestPrefixSuffix[j - 1];
                }
                else {
                    i = i + 1;
                }
            }
        }
    }

    public void computeLongestPrefixSuffixArray(String pattern, int patternLength, int[] longestPrefixSuffix) {
        // length of the previous longest prefix suffix
        int arrayLength = 0;
        int i = 1;
        longestPrefixSuffix[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to patternLength-1
        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(arrayLength)) {
                arrayLength++;
                longestPrefixSuffix[i] = arrayLength;
            }
            else { // (pat[i] != pat[arrayLength])
                // The idea is similar to search step.
                if (arrayLength != 0) {
                    arrayLength = longestPrefixSuffix[arrayLength - 1];
                } else // if (arrayLength == 0){
                    longestPrefixSuffix[i] = arrayLength;
            }
            i++;
        }
    }
 */

        /*
    public LinkedList<Integer> validShifts(String pattern, String text) {

        if (pattern == null || text == null)
            throw new NullPointerException();
        if (pattern.length() == 0 || text.length() == 0)
            return new LinkedList<>();

        LinkedList<Integer> result = new LinkedList<>();

        int patternLength = pattern.length();
        int textLength = text.length();

        int[] prefix = prefixFunction(pattern);

        int q = 0;
        for (int i = 0; i < textLength; i++) {
            while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
                q = prefix[q];
            }
            if (pattern.charAt(q) == text.charAt(i)) {
                q++;
            }
            if (q == patternLength) {
                result.add(i-patternLength+1);
                q = 0; // comparing the text again
            }
        }
        return result;
    }
     */

    /*
    public int[] prefixFunction(String pattern) {
        // initializing
        int[] prefixArray = new int[pattern.length()];

        // creating a new hashset (checking rep
        HashSet<Character> set = new HashSet();
        set.add(pattern.charAt(0));

        prefixArray[0] = 0;// character at initial position is always 0

        int count = 0;

        // traversing the array from second position
        for (int i = 1; i < pattern.length(); i++) {
            // if character is repeated in array
            if (set.contains(pattern.charAt(i))) {
                count++;
                prefixArray[i] = count;
            }
            else {
            // if character in not repeated in array its value = 0
                count = 0;
                prefixArray[i] = count;
                set.add(pattern.charAt(i));
            }

        }
        return prefixArray;
    }
    */
}


