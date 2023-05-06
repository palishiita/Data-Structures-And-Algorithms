import java.util.HashSet;
import java.util.LinkedList;

public class Class {
    /*
    /*
- https://www.youtube.com/watch?v=mFY0J5W8Udk
- linear o(n) + binary search o(log_n) slow
- search solution: faster if we know the index therefore hashing technique o(1)
  mapping document to hashtable (array index) using hash function but hashcode is limited ↓
- hash function h(x):  [customize this!]
  sequence: "7,11,13,17,19",7,11,13,19…
  result = multiply firstLetter(hashCode) by firstNumber(sequence) and add result to secondLetter(hashCode).
- collision: two or more elements are mapped at the same array_index in hashtable ↑
- collision solution: closed addressing or chaining or array of linked list HashTable [keys/documents are stored in each
  node of linkedList]
- array contains references referring to object of linkedList whose memory location is stored in the heap area
*/
/*
    public class HashTable {

        LinkedList[]arr; // use pure array

        private final static int defaultInitSize = 8;
        private int size;

        private final static double defaultMaxLoadFactor = 0.7;
        private final double maxLoadFactor;

        HashSet<String> nameHashTable = new HashSet<>();

        //constructor 1 [default]
        public HashTable() {
            this(defaultInitSize);
        }
        // constructor 2 [default]
        public HashTable(int size) {
            this(size,defaultMaxLoadFactor);
        }
        // constructor 3 [change]
        public HashTable(int initCapacity, double maxLF) {
            //this.size = initCapacity;
            this.maxLoadFactor = maxLF;
            this.arr = new LinkedList[size];
        }

        public boolean add(Object elem) {
            Document document = (Document) elem;
            // cannot add a document (elem) with a name which is still in the hashTable i.e. duplicates cannot be added
            if (nameHashTable.contains(document.name)) {
                return false;
            }
            // adding object based on hashcode to arr
            int index = elem.hashCode()%size;
            // every index in the array has a linked list with elem objects
            if (arr[index]==null) {
                arr[index] = new LinkedList();
                arr[index].add(elem);
                nameHashTable.add(document.getName());
                if (((double) nameHashTable.size() / (double) size) > maxLoadFactor)
                    doubleArray();
            }
            return true;
        }

 */

        /* If numberOfElements exceed loadFactor, arrayCapacity (initCapacity) has to be doubled using doubleArray()
        and elements have to be moved to proper linkedList based on hashCode().*/
    /*
        private void doubleArray() {
            nameHashTable.clear();
            LinkedList[] newArr = new LinkedList[size * 2];
            for (int i = 0; i < size; i++) {
                if (this.arr[i] == null)
                    continue;
                for (Object elem : this.arr[i]) {
                    if (newArr[elem.hashCode() % (size * 2)] == null)
                        newArr[elem.hashCode() % (size * 2)] = new LinkedList();
                    newArr[elem.hashCode() % (size * 2)].add(elem);
                    nameHashTable.add(((Document) elem).getName());
                }
            }
            this.arr = newArr;
            size *= 2;
        }

        public Object get(Object toFind) {
            for (int i = 0; i < size; i++) {
                if (this.arr[i]==null) {
                    continue;
                }
                for (Object object: arr[i]) {
                    if (object.equals(toFind)) {
                        return object;
                    }
                }
            }
            return null;
        }

     */

        /*
        - IndexOfArray then ":"
        - If LinkedList[]arr is !empty print " ", then the name of the first document in the list.
        - If there is next document print ", ", and name of second document.
        - Every position in the array in separated line.
        - To have access to name of the document cast type of element to the type IWithName.
         */
    /*
        @Override
        public String toString() {
            StringBuilder retStr = new StringBuilder();
            for (int i = 0; i < size; i++) {
                retStr.append(i).append(":");
                if (!(arr[i]==null)) {
                    retStr.append(" ");
                    for (int j = 0; j < arr[i].size(); j++) {
                        if (j < arr[i].size() - 1) {
                            retStr.append(((Document) arr[i].get(j)).getName()).append(", ");
                        } else {
                            retStr.append(((Document) arr[i].get(j)).getName());
                        }
                    }
                }
                retStr.append("\n");
            }
            return retStr.toString();
        }

    }
     */
}
