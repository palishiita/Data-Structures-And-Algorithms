public class DisjointSetForest implements DisjointSetDataStructure {

    private class Element {
        // Element object with 2 fields
        int rank; // potential max height of tree
        int parent; // element parent pointer
    }

    // fields
    Element[] arr;
    private static final int NULL = -1;

    // constructor with size = number of items
    public DisjointSetForest(int size) {
        if (size < 0)
            throw new NullPointerException();
        // array stores fields of parent and rank
        arr = new Element[size];
        for (int item = 0; item < size; item++) {
            arr[item] = new Element();
            // updating fields of array
            arr[item].rank = 0;
            arr[item].parent = NULL;
        }
    }

    // Create set with single item of size 1
    @Override
    public void makeSet(int item) {
        if (item < 0)
            throw new IllegalArgumentException();
        arr[item].parent = item;
    }

    // Find representative of the set that item is an element of using path compression recursively
    @Override
    public int findSet(int item) {
        // if item is not the parent of itself then item is not the representative of his set
        if (arr[item].parent != item && arr[item].parent != NULL)
            // recursively call findSet on its parent and move item node directly
            // under the representative of this set
            arr[item].parent = findSet(arr[item].parent);
        return arr[item].parent;
    }

    // add tree with smaller rank to a tree with bigger rank
    @Override
    public boolean union(int itemA, int itemB) {
        // Find representatives of two sets
        int A_Root = findSet(itemA);
        int B_Root = findSet(itemB);

        // Elements are in the same set i.e. items have the same representatives , no need to unite anything
        if (A_Root == B_Root || A_Root == NULL || B_Root == NULL)
            return false;

        // rank of A_root > B_Root
        if (arr[A_Root].rank > arr[B_Root].rank)
            arr[B_Root].parent = A_Root;

        else {
            // rank of A_root < B_Root
            arr[A_Root].parent = B_Root;

            // rank of A_root == B_Root
            if (arr[A_Root].rank == arr[B_Root].rank) {
                arr[B_Root].rank += 1;
            }

        }
        return true;
    }

    // “x -> y”, x is an item and y is its parent (root is parent during path compression)
    public String toString() {
        StringBuilder retStr = new StringBuilder("Disjoint sets as forest:\n");
        for (int i = 0; i < arr.length; i++) {
            Element element = arr[i];
            retStr.append(i).append(" -> ").append(element.parent);
            if (i < arr.length - 1) {
                retStr.append("\n");
            }
        }
        return retStr.toString();
    }

}