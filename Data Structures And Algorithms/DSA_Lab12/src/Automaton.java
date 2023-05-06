import java.util.LinkedList;

// O(pattern.length^3|alphabets|)

public class Automaton implements IStringMatcher {

    // char value for alphabets
    static int NoOfChars = 256;

    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {

        if (pattern == null || text == null)
            throw new NullPointerException();
        if (pattern.length() == 0 || text.length() == 0)
            return new LinkedList<>();

        // returns result linkedList of all indices where there is occurrences of pattern in the text
        LinkedList<Integer> result = new LinkedList<>();

        int patternLength = pattern.length();
        int textLength = text.length();

        // initializing and creating the transition table where column = pattern.length and row = all alphabets
        int[][] TransitionTable = new int[patternLength + 1][NoOfChars];
        computeTransitionTable(pattern, patternLength, TransitionTable);

        // linked list of matched pattern traversing through the text is created here (result)!!
        int i, state = 0;
        for (i = 0; i < textLength; i++) {
            state = TransitionTable[state][text.charAt(i)];
            if (state == patternLength) {
                // Pattern found at index
                result.add(i - patternLength + 1);
            }
        }
        return result;

    }

    // builds Transition table which represents Finite Automata for a given pattern
    // x = alphabets
    // state size = patternLength+1
    public static void computeTransitionTable(String pattern, int patternLength, int[][] TransitionTable) {
        int state, alphabets;
        for (state = 0; state <= patternLength; ++state) {
            for (alphabets = 0; alphabets < NoOfChars; ++alphabets) {
                TransitionTable[state][alphabets] = getNextState(pattern, patternLength, state, alphabets);
            }
        }
    }

    public static int getNextState(String pattern, int patternLength, int state, int character) {

        // match
        if (state < patternLength && character == pattern.charAt(state)) {
            return state + 1;
        }
        int nextState, i;
        // nextState finally contains the longest prefix which is also suffix
        // Start from the largest possible value and stop when you find a prefix which is also suffix

        // longest prefix which is also a suffix
        // initializing next to current
        for (nextState = state; nextState > 0; nextState--) {
            // we are going backwards and checking if state is equal
            if (pattern.charAt(nextState - 1) == character) {
                for (i = 0; i < nextState - 1; i++)
                    if (pattern.charAt(i) != pattern.charAt(state - nextState + 1 + i))
                        break;
                if (i == nextState - 1)
                    return nextState;
            }
        }
        return 0;
    }

}