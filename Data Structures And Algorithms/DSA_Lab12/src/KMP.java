import java.util.LinkedList;

// O(m+n) m = pattern.length and n = text.length

public class KMP implements IStringMatcher {

    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {

        if (pattern == null || text == null)
            throw new NullPointerException();
        if (pattern.length() == 0 || text.length() == 0)
            return new LinkedList<>();

        LinkedList<Integer> result = new LinkedList<>();

        int patternLength = pattern.length();
        int textLength = text.length();

        // creating lps[] array that will hold the longest prefix suffix values for pattern (filled with 0's)
        int[] longestPrefixSuffix = new int[patternLength];
        // Preprocess the pattern and calculate values of each index in lps[] array
        computeLongestPrefixSuffixArray(pattern, patternLength, longestPrefixSuffix);


        int j = 0; // index for pattern[] (smaller)
        int i = 0; // index for text[] (greater)

        while (i < textLength) {

            // traversing text at pattern window until there is a mismatch found
            // move to next window of same pattern length, and it starts with same letter as in pattern.charAt(0)
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            // when no miss-match pattern found in the window!!
            if (j == patternLength) {
                result.add(i - j); // pattern is found at (i-j)th index
                j = longestPrefixSuffix[j - 1];
            }

            // miss-match!!
            // mismatch after j matches
            else if (i < textLength && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = longestPrefixSuffix[j - 1];
                } else {
                    i++; // move and check next match
                }
            }

        }
        return result;
    }


    // creating preprocessed array
    /*
    1. kmp 2 3
    a b a b a c
    0 0 1 2 1 0
    2. kmp 1 1
    a b c d e f
    0 0 0 0 0 0
    */

    // we are pre-processing the array here!!
    public void computeLongestPrefixSuffixArray(String pattern, int patternLength, int[] longestPrefixSuffix) {

        // length of the previous longest prefix suffix
        int lpsArrayLength = 0;

        longestPrefixSuffix[0] = 0; // value of character at initial position = 0 because it has no prefix or suffix

        int i = 1;
        // loop traverses prefixSuffix[i] array for i = 1 to patternLength-1
        while (i < patternLength) {

            // ------- we compare char of string with previous position -------

            // if character is repeated in array
            if (pattern.charAt(i) == pattern.charAt(lpsArrayLength)) {
                lpsArrayLength++; // incrementing and traversing the lps array
                longestPrefixSuffix[i] = lpsArrayLength;
            }

            // if next char is not same as previous (no repetition)
            else if (pattern.charAt(i) != pattern.charAt(lpsArrayLength)) {
                // this char is found before but there is break in repetition!
                if (lpsArrayLength != 0) { // lpsArrayLength > 0
                    lpsArrayLength = longestPrefixSuffix[lpsArrayLength - 1];
                }
                // this char is never found in the array before!
                else if (lpsArrayLength == 0) {
                    longestPrefixSuffix[i] = lpsArrayLength; // i.e. final value at this position = 0
                }
                i++;
            }
        }
    }

}