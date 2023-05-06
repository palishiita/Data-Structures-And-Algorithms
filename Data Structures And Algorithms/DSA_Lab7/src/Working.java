import java.util.Arrays;
import java.util.Objects;

public class Working {

    String name;
    private static final int MODVALUE = 100000000;

    public static int []  getHashCodeLetter(String nameStr) {
        int[] charHashLetter = new int[nameStr.length()];
        for (int i = 0; i < nameStr.length(); i++) {
            charHashLetter[i] = nameStr.charAt(i);
        }
        return charHashLetter;
    }


    int [] sequence = {7, 11, 13, 19};
    @Override
    public int hashCode() {
        int[] HashCodeLetter = getHashCodeLetter(name);

        int newHashCode = HashCodeLetter[0];
        int index = 0;

        for (int i=1; i < HashCodeLetter.length; i++) {
            newHashCode = (newHashCode * sequence[index]) + HashCodeLetter[i];
            newHashCode = newHashCode % MODVALUE;
            index++;
            if (index > sequence.length - 1) {
                index = 0;
            }
        }
        return newHashCode;

    }

    public static void method(String elem) {
        int [] arr = new int[8];
        int size = arr.length;
        int index = elem.hashCode();
        System.out.println("hashcode: "+index);
        System.out.println("index: "+elem.hashCode()%8);
        System.out.println("index double: "+index%(size*2));
        System.out.println();
    }

    public static void main(String[] args) {
        // https://www.w3resource.com/java-exercises/basic/java-basic-exercise-41.php ascii table
        /*
        String str = "abcd";
        System.out.println(Objects.hash(str));
        System.out.println(str.hashCode());
        System.out.println((Arrays.toString(getHashCodeLetter(str))));
        System.out.println(str.hashCode());
        System.out.println();
         */
        method("xxx");
        method("qqq");
        method("second"); //*
        method("first");
        method("xz");
    }


}