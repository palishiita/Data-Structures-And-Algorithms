import java.util.*;

public class Graph {

    // fields
    int[][] arr;
    // Collection to map Document name to index of vertex
    HashMap<String, Integer> nameToVertexIndex;
    // linkedList used for mapping document object (and its name) to an index and vice versa
    LinkedList<Document> documents;

    // constructor
    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];
        nameToVertexIndex = new HashMap<>();
        documents = new LinkedList<>();

        // connecting document name with its "link"
        // eg: document a has links c(1) and b(4)
        int i = 0;
        for (Document document : internet.values()) { // returns values for keys in ascending order
            documents.add(document); // putting it in the linked list
            nameToVertexIndex.put(document.name, i); // putting it in the hashmap
            i++;
        }

        // ** fills the graph's matrix -> constructing the adjacency matrix here **
        // go through all documents in the linked list where i = document_index
        for (i = 0; i < size; i++) {
            Document document = documents.get(i);
            for (int j = 0; j < size; j++) {
                // diagonal with same row and column index
                if (j == i) {
                    arr[i][j] = 0;
                    continue;
                }
                Link link = document.link.get(documents.get(j).name); // value using key = documents.get(j).name -> get(j) = document name at jth position (document name at jth position is the key)
                if (link != null) {
                    // graph has a link with document name
                    arr[i][j] = nameToVertexIndex.get(link.ref);
                } else {
                    // graph has no link with document name
                    arr[i][j] = -1;
                }
            }
        }
    }

    public String bfs(String start) {

        // checking boundary conditions
        if (start == null || start.isBlank())
            throw new IllegalArgumentException("vertex cannot be null");
        if (nameToVertexIndex.get(start) == null)
            return null;

        StringBuilder retStr = new StringBuilder(start).append(", ");

        // added to the queue (checking)
        Queue<Integer> queue = new LinkedList<>();
        queue.add(nameToVertexIndex.get(start)); // get the starting vertex from the hashmap

        // added to the final visited vertices (done)
        boolean[] visitedVertex = new boolean[documents.size()];
        visitedVertex[nameToVertexIndex.get(start)] = true;

        while (!queue.isEmpty()) {
            int i = queue.remove();
            for (int j = 0; j < documents.size(); j++) { // traversing linkedList
                int arr2D = arr[i][j];

                // they are not in a diagonal, there is a link, and is not visited before (condition)
                if (i != j && arr2D != -1 && !visitedVertex[arr2D]) {
                    queue.add(arr2D);
                    visitedVertex[arr2D] = true;
                    retStr.append(documents.get(arr2D).name).append(", ");
                }
            }
        }
        // remove ", "
        if (retStr.length() > 0)
            return retStr.substring(0, retStr.length() - 2).trim();
        return null;
    }

    // string needed during dfs implementation
    String strDFS = "";

    // helper function - get Document object using its reference name: b, c, d etc..
    // check if the document name exists
    private Document getDocument(String name) {
        for (Document d : this.documents) {
            if (d.getName().equals(name)) // document object
                return d;
        }
        return null;
    }

    public String dfs(String start) {

        // checking boundary conditions
        if (start == null || start.isBlank())
            throw new IllegalArgumentException("vertex cannot be null");

        this.strDFS = "";
        // we map the documents name to document object
        Document doc = getDocument(start); // helper function

        // if the document of given name exists in the graph
        if (doc != null) {
            boolean[] visited = new boolean[documents.size()]; // size of linked list of documents
            dfsRecursive(visited, doc);
            strDFS = (strDFS.substring(0, strDFS.length() - 2));
            return strDFS;
        } else {
            return null;
        }
    }


    private void dfsRecursive(boolean[] visited, Document docName) {

        visited[documents.indexOf(docName)] = true;

        strDFS += docName.getName() + ", ";

        Link[] links = docName.getLinks(); // converting the links into array c(1), b(4), etc..
        for (Link link : links) {
            Document linked_doc = getDocument(link.ref);
            int doc_index = documents.indexOf(linked_doc);
            // condition: linked list index cannot be 1 and is not visited earlier
            if (doc_index != -1 && !visited[doc_index]) {
                dfsRecursive(visited, documents.get(doc_index));
            }
        }
    }

    public int connectedComponents() {
        DisjointSetForest connectedComponents = new DisjointSetForest(arr.length);
        // initializing disjoint sets
        for (int i = 0; i < arr.length; i++)
            connectedComponents.makeSet(i);

        // initializing the two disjoint sets
        for (int row = 0; row < arr.length; row++) {
            for (int column = 0; column < arr.length; column++) {

                // for each existing link between nodes A and B, union A and B
                if (arr[row][column] != 0 && arr[row][column] != -1) {

                    // two items have different representatives
                    if (connectedComponents.findSet(row) != connectedComponents.findSet(column)) {
                        // row and column are in the same connected component if they have the same representative
                        connectedComponents.union(row, column);
                    }

                }
            }
        }
        return connectedComponents.countSets();
    }

}