import java.util.*;

public class Graph {

    // fields
    int[][] arr;
    // Collection to map Document name to index of vertex
    HashMap<String, Integer> nameToVertexIndex;
    HashMap<Integer, String> vertexIndexToName;
    // linkedList used for mapping document object (and its name) to an index and vice versa
    LinkedList<Document> documents;

    // constructor
    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];
        nameToVertexIndex = new HashMap<>();
        vertexIndexToName = new HashMap<>();
        documents = new LinkedList<>();

        // connecting document name with its "link"
        // eg: document a has links c(1) and b(4)
        int i = 0;
        for (Document document : internet.values()) { // returns values for keys in ascending order
            documents.add(document); // putting it in the linked list
            nameToVertexIndex.put(document.name, i); // putting it in the hashmap
            vertexIndexToName.put(i, document.name);
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
                Link link = document.link.get(vertexIndexToName.get(j)); // returning link
                if (link != null) {
                    // graph has a link with document name
                    arr[i][j] = link.weight;
                } else {
                    // graph has no link with document name
                    arr[i][j] = -1;
                }
            }
        }
    }

    public String bfs(String start) {
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
        if (retStr.length() > 0)
            return retStr.substring(0, retStr.length() - 2).trim();
        return null;
    }

    // helper function - get Document object using its reference name: b, c, d etc...
    // check if the document name exists
    private Document getDocument(String name) {
        for (Document doc : documents) {
            if (doc.getName().equals(name)) // document object
                return doc;
        }
        return null;
    }

    String stringDFS = "";
    public String dfs(String start) {
        if (start == null || start.isBlank())
            throw new IllegalArgumentException("vertex cannot be null");
        this.stringDFS = "";
        Document doc = getDocument(start); // helper function
        if (doc != null) {
            boolean[] visited = new boolean[documents.size()]; // size of linked list of documents
            dfsRecursive(visited, doc);
            stringDFS = (stringDFS.substring(0, stringDFS.length() - 2));
            return stringDFS;
        } else {
            return null;
        }
    }


    private void dfsRecursive(boolean[] visited, Document docName) {
        visited[documents.indexOf(docName)] = true;
        stringDFS += docName.getName() + ", ";
        Link[] links = docName.getLinks();
        for (Link link : links) {
            Document linked_doc = getDocument(link.ref);
            int doc_index = documents.indexOf(linked_doc);
            if (doc_index != -1 && !visited[doc_index]) {
                dfsRecursive(visited, documents.get(doc_index));
            }
        }
    }

    public int connectedComponents() {
        DisjointSetForest connectedComponents = new DisjointSetForest(arr.length);
        for (int i = 0; i < arr.length; i++)
            connectedComponents.makeSet(i);
        for (int row = 0; row < arr.length; row++) {
            for (int column = 0; column < arr.length; column++) {
                if (arr[row][column] != 0 && arr[row][column] != -1) {
                    if (connectedComponents.findSet(row) != connectedComponents.findSet(column)) {
                        connectedComponents.union(row, column);
                    }
                }
            }
        }
        return connectedComponents.countSets();
    }

    /**
     For every vertex lexicographically sorted there will be one line of the format:
     documents linked list contains all the vertices with document object and its link
     1. <startVertex>=0
     2. no path to <vertexName>
     3. <startVertex>-><vertex1>->...-><vertexName>=<weightOfPath>
     **/

    public String DijkstraSSSP(String startVertexStr) {

        if (startVertexStr == null || startVertexStr.isBlank())
            throw new IllegalArgumentException("vertex cannot be null");
        if (nameToVertexIndex.get(startVertexStr) == null)
            return null;

        // ** initializing **
        // start index of the string startVertexStr
        Integer start = nameToVertexIndex.get(startVertexStr);
        // boolean array to track vertices for which minimum cost is already found
        boolean[] visitedVertex = new boolean[documents.size()];
        // we are declaring the starting vertex visited at first
        visitedVertex[start] = true;
        // arr contains path or minimum distance to each vertex
        int[] minDis = new int[documents.size()];
        // predecessor of current vertex
        String[] predecessor = new String[documents.size()];
        // fill the distances to inf
        Arrays.fill(minDis, Integer.MAX_VALUE);
        // min distance at start is always 0
        minDis[start] = 0;

        // there is a direct edge from start vertex to any other vertex
        for (int i = 0; i < documents.size(); i++) {
            if (arr[start][i] != -1) {
                minDis[i] = arr[start][i];// adjacency matrix - min distance updated
                predecessor[i] = startVertexStr;
            }
        }

        // accept the start index
        for (int j = 0; j < documents.size()-1; j++) {
            // checking in matrix if there is connection
            int min = Integer.MAX_VALUE;
            int minVertexIndex = -1;
            for (int i = 0; i < documents.size(); i++) {
                // startVertex now is already visited!
                //check min distance and min vertex is !visited
                if (minDis[i] < min && !visitedVertex[i]) {
                    min = minDis[i];
                    minVertexIndex = i;
                }
            }
            if (minVertexIndex == -1) {
                break;
            }

            visitedVertex[minVertexIndex] = true; // after for loop the vertex is visited

            for (int k = 0; k < documents.size(); k++) {
                // changing the distances (better path from start vertex to remaining)
                // adding the weights so updating min weight
                if (!visitedVertex[k] && arr[minVertexIndex][k] != -1 && minDis[k] > minDis[minVertexIndex] + arr[minVertexIndex][k]) {
                    minDis[k] = minDis[minVertexIndex] + arr[minVertexIndex][k];
                    predecessor[k] = vertexIndexToName.get(minVertexIndex); // predecessor
                }
            }
        }

        // printing string
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < documents.size(); i++) {
            // if no vertex is visited
            if (!visitedVertex[i]) {
                retStr.append("no path to ").append(vertexIndexToName.get(i)).append("\n");
            }
            // using stack of vertex names to print
            else {
                Stack<String> vertexNames = new Stack<>();
                int k = i;
                // if 'i' is not start index and has a link (connected component)
                if (i != start && minDis[i] != Integer.MAX_VALUE) {
                    while (k != start) {
                        vertexNames.push(predecessor[k]);
                        k = nameToVertexIndex.get(predecessor[k]);
                    }
                    // <predecessor1> -> <predecessor2>
                    while (!vertexNames.isEmpty()) {
                        String name = vertexNames.pop(); // predecessor and update current
                        retStr.append(name).append("->");
                    }
                    // <current2> = weight
                    retStr.append(vertexIndexToName.get(i)).append("=").append(minDis[i]).append("\n");
                }
                // if 'i' is start index
                else if (i == start) {
                    retStr.append(vertexIndexToName.get(start)).append("=0\n");
                }
            }
        }

        return retStr.toString();
    }



}