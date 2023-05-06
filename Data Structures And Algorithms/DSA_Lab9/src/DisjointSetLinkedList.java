import java.util.HashSet;
import java.util.Set;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

    private class Element {
        Element representative; // pointer to head
        Element next; // pointer to next item
        Element last; // pointer to tail
        int length; // size
        int value;
    }

    private static final int NULL = -1; // Next pointer of tail of linkedList = NULL

    Element[] arr;

    public DisjointSetLinkedList(int size) {
        if (size < 0)
            throw new NullPointerException();
        arr = new Element[size];
    }

    // To make a set with one Element object
    @Override
    public void makeSet(int item) {
        if (item < 0)
            throw new IllegalArgumentException();
        var element = new Element();
        element.representative = element; // item head points to item
        element.next = null; // item next points to "NULL"
        element.last = element; // item tail points to item.
        element.length = 1; // size = 1
        element.value = item;
        arr[item] = element;
    }

    // finding representative of item
    @Override
    public int findSet(int item) {
        if (arr[item] == null)
            return NULL;
        return arr[item].representative.value;
    }

    @Override
    public boolean union(int itemA, int itemB) {

        // finding heads/representative
        Element A_Rep = arr[itemB].representative;
        Element B_Rep = arr[itemA].representative;

        // if both have the same representative no union operation
        if (B_Rep.value == A_Rep.value)
            return false;

        // if size A > B
        // updating representatives or head of b
        if (A_Rep.length > B_Rep.length) {
            Element temp = B_Rep;
            B_Rep = A_Rep;
            A_Rep = temp;
        }

        // updating the last pointer or tail of a
        Element newLast = B_Rep.last;
        newLast.next = A_Rep;
        B_Rep.last = A_Rep.last;

        while (A_Rep != null) {
            B_Rep.length++; // incrementing the size
            A_Rep.representative = B_Rep;// updating the representative
            A_Rep = A_Rep.next;
        }

        return true;
    }


    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder("Disjoint sets as linked list:\n");
        Set<Integer> processed = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            Element element = arr[i].representative;
            if (!processed.contains(element.value)) {
                processed.add(element.value);
                while (element != null) {
                    retStr.append(element.value);
                    element = element.next;
                    if (element != null)
                        retStr.append(", ");
                }
                if (i < arr.length - 2)
                    retStr.append("\n");
            }
        }
        return retStr.toString();
    }

}