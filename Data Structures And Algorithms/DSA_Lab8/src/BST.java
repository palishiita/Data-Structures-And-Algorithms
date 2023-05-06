public class BST<T extends Comparable<T>> {

    private class Node {
        // fields
        T value;
        Node left, right, parent;
        // constructors
        public Node(T elem) {
            value = elem;
        }
        public Node(T value, Node left, Node right, Node parent) {
            super();
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }


    // fields
    private Node root = null;
    public int size = 0;
    // constructor
    public BST() {
        this.root = null;
    }



    /**
     * If node to be added is already in BST [compareTo == 0] then no elem is added.
     * Elements inserted at:
     * 1)root [if BST is empty]  2)left sub-tree of parent    3)right sub-tree of parent
     **/

    public boolean add(T elem) {
        root = add(root, elem);
        size++;
        return true;
    }

    private Node add(Node rootNode, T elem) {
        // inserted at root
        if (rootNode == null) {
            rootNode = new Node(elem);

        } else {
            // comparing inserted element with root
            int comparison = elem.compareTo(rootNode.value);

            // inserted in left sub-tree of parent
            // if elem < root node (smaller values to root)
            if (comparison < 0) {
                Node leftChild = add(rootNode.left, elem);
                rootNode.left = leftChild;
                leftChild.parent = rootNode; // Set parent of root of left subtree
                rootNode.left.parent = rootNode;
            }

            // inserted in right sub-tree of parent
            // if elem < root node (smaller values to root)
            else if (comparison > 0) {
                Node rightChild = add(rootNode.right, elem);
                rootNode.right = rightChild;
                rightChild.parent = rootNode; // Set parent of root of right subtree
            }
        }
        return rootNode;
    }


    /** getElement implemented using findNode method **/
    public T getElement(T toFind) {
        Node node = findNode(root, toFind);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    public Node findNode(Node node, T toFind) {
        // no node inserted in BST
        if (node == null)
            return null;
        if (toFind.equals(node.value))
            return node;
        // left sub-tree
        if (toFind.compareTo(node.value) < 0 )
            return findNode(node.left, toFind);
        // right sub-tree
        // if (find.compareTo(node.value) > 0)
        return findNode(node.right, toFind);
    }


    /**
     * inorder traversal: left -> node -> right (triangle)
     * inorder successor: smallest value greater than elem i.e. next nodeElem during inorder traversal
     * 1) node has right sub-tree: traverse leftward + findMinimum
     * parent from "left" is always first unvisited
     **/

    public T successor(T elem) {
        Node successor = successor(root, elem);
        if (successor != null) {
            return successor.value;
        }
        return null;
    }

    private Node successor(Node node, T elem) {
        if (node == null) {
            return null;
        }
        else {
            // comparing element whose successor needs to be found with root
            int comparison = elem.compareTo(node.value);

            if (comparison == 0) {

                // node has right sub-tree
                if (node.right != null) {
                    // traversing to left most node
                    return findMinimumNode(node.right);
                }

                // after traversing to left most node, move to the predecessor **
                Node parent = node.parent;
                while (parent != null) {
                    if (node == parent.left)
                        return parent;
                    node = parent;
                    parent = parent.parent;
                }
                return null;
            }

            // if elem < node
            // no left sub-tree
            else if (comparison < 0) {
                return successor(node.left, elem);
            }

            // no right sub-tree
            else { // comparison > 0
                return successor(node.right, elem);
            }

        }
    }

    // minimum is the left most node in BST (left elements < root)
    private Node findMinimumNode(Node node) {
        if (node.left != null) {
            return findMinimumNode(node.left);
        }
        return node;
    }



    /**
     * 3 cases for deleting element in BST
     * 1) node with 0 children (leaf nodes)
     * 2) node with 1 child
     * 3) node with 2 children
     * If node to be deleted is not present in BST no node is deleted and print error
     **/

    public T remove(T elem) {
        // node that needs to be removed
        Node node = findNode(root, elem);

        if (node == null)
            return null;

        size--; // decrementing size

        // 0 child or leaf node
        if (node.left == null && node.right == null) {
            // if leaf node is root
            if (node == root) {
                root = null;
                return node.value;
            }
            // if leaf node is left or right child
            else {
                // left child leaf
                if (node == node.parent.left)
                    node.parent.left = null;
                // right child leaf
                if (node == node.parent.right)
                    node.parent.right = null;
            }
            return node.value; // node that is removed
        }

        // 1 child
        if (node.left == null || node.right == null) {
            // child is either in leftSubTree
            Node child = node.left;
            if (child == null)
                child = node.right; // or child is in rightSubTree
            // if child is root
            if (node == root) {
                root = child;
                child.parent = null;
                return node.value;
            }
            //
            else {
                // left child
                if (node == node.parent.left)
                    node.parent.left = child;
                // right child
                if (node == node.parent.right)
                    node.parent.right = child;
                // either parent of left or right child
                child.parent = node.parent;
            }
            return node.value;
        }

        // 2 children
        else if (node.left != null && node.right != null) {
            T oldNode = node.value;
            // successor of oldNode is deleted and its predecessor occupies its position **
            T successorNode = successor(oldNode);
            remove(successorNode);
            // size is decremented and node is removed
            // size is decremented twice, so we increase size
            size++;
            node.value = successorNode;
            return oldNode;
        }
        return node.value;
    }

    // size
    public int size() {
        return size;
    }

    // clear: remove the BST
    public void clear() {
        size = 0;
        root = null;
    }


    // show function implementation
    // traversing
    public static String getTraverseResult(StringBuilder builder){
        if (builder.length() == 0)
            return builder.toString();
        return builder.substring(0, builder.length()-2);
    }

    // <left> -> <root> -> <right> (triangle)
    public String toStringInOrder() {
        StringBuilder result = new StringBuilder();
        traverseInOrder(root, result);
        return getTraverseResult(result);
    }
    public void traverseInOrder(Node node, StringBuilder result){
        if (node != null){
            traverseInOrder(node.left, result);
            result.append(node.value).append(", ");
            traverseInOrder(node.right, result);
        }
    }

    // <root> -> <left> -> <right>
    public String toStringPreOrder() {
        StringBuilder result = new StringBuilder();
        traversePreOrder(root, result);
        return getTraverseResult(result);
    }
    private void traversePreOrder(Node node, StringBuilder result){
        if (node != null){
            result.append(node.value).append(", ");
            traversePreOrder(node.left, result);
            traversePreOrder(node.right, result);
        }
    }

    // <left> -> <right> -> <root>
    public String toStringPostOrder() {
        StringBuilder result = new StringBuilder();
        traversePostOrder(root, result);
        return getTraverseResult(result);
    }
    private void traversePostOrder(Node node, StringBuilder result){
        if (node != null){
            traversePostOrder(node.left, result);
            traversePostOrder(node.right, result);
            result.append(node.value).append(", ");
        }
    }

}