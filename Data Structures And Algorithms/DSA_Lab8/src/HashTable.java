import java.util.LinkedList;

public class HashTable {
    // fields
    LinkedList<IWithName>[] arr;
    private final static int defaultInitSize = 8;
    private final static double defaultMaxLoadFactor = 0.7;
    private int size;
    private final double maxLoadFactor;
    private int ElemQuantity;
    // constructors
    public HashTable() {
        this(defaultInitSize);
    }
    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
    }
    public HashTable(int size, double maxLF) {
        this.size = size;
        this.maxLoadFactor = maxLF;
        this.arr = new LinkedList[this.size];
        this.ElemQuantity = 0;
    }

    public boolean add(Object elem) {
        if (elem == null)
            throw new NullPointerException();
        if (!(elem instanceof IWithName))
            return false;
        // doubling the array
        double currentLoadFactor = (double) (ElemQuantity + 1) / size;
        if (currentLoadFactor > maxLoadFactor) {
            doubleArray();
        }
        int index = (elem.hashCode()) % size;
        if (arr[index] == null) {
            arr[index] = new LinkedList();
        }
        // duplicates are not allowed in the hashtable
        for (Object oldElem : arr[index]) {
            if (oldElem.equals(elem)) {
                return false;
            }
        }
        arr[index].add((IWithName) elem);
        ElemQuantity++;
        return true;
    }

    private void doubleArray() {
        int newSize = arr.length * 2;
        LinkedList[] newArr = new LinkedList[newSize];
        for (LinkedList linkedList : arr) {
            if (linkedList == null)
                continue;
            for (Object name : linkedList) {
                int index = name.hashCode() % newSize;
                if (newArr[index] == null) {
                    newArr[index] = new LinkedList();
                }
                newArr[index].add(name);
            }
        }
        arr = newArr;
        size = arr.length;
    }

    public Object get(Object toFind) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == null)
                continue;
            for (Object name : arr[i]) {
                if ((name).equals(toFind))
                    return name;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            retStr.append(i).append(":");
            if (arr[i] != null && arr[i].size() > 0) {
                retStr.append(" ");
                for (int j = 0; j < arr[i].size(); j++) {
                    if (j < arr[i].size() - 1)
                        retStr.append((arr[i].get(j)).getName()).append(", ");
                    else
                        retStr.append((arr[i].get(j)).getName());
                }
            }
            retStr.append("\n");
        }
        return retStr.toString();
    }

}