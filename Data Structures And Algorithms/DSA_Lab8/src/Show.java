public class Show {
     /*
    // <left> -> <root> -> <right>
    public String toStringInOrder() {
        if (size() == 0)
            return "";
        String inOrder = "";
        Stack<Node> nodeStack = new Stack<>();
        Node root = this.root;
        while (root != null || !nodeStack.empty()) {
            if (root == null) { // when we traverse root is null
                root = nodeStack.pop(); // we pop from left to right and print
                inOrder = inOrder + root.value + ", ";
                root = root.right;
            } else { // pushing first
                nodeStack.push(root);
                root = root.left;
            }
        }
        return inOrder.substring(0, inOrder.length()-2);
    }

    // <root> -> <left> -> <right>
    public String toStringPreOrder() {
        if (size() == 0)
            return "";
        String PreOrder = "";
        Stack<Node> nodeStack = new Stack<>();
        Node node = root;
        nodeStack.add(node);
        while (!nodeStack.empty()) {
            Node myNode = nodeStack.peek();
            if (myNode == null)
                continue;
            PreOrder = PreOrder + myNode.value + ", ";
            nodeStack.pop();
            if (myNode.right != null)
                nodeStack.push(myNode.right);
            if (myNode.left != null)
                nodeStack.push(myNode.left);
        }
        return PreOrder.substring(0, PreOrder.length() - 2);
    }

    // <left> -> <right> -> <root>
    public String toStringPostOrder() {
        /*
        if (size() == 0)
            return "";
        String PostOrder = "";
        Stack<Node> nodeStack1 = new Stack<>();
        Stack<Node> nodeStack2 = new Stack<>();
        Node node = root;
        nodeStack1.push(node);
        while (!nodeStack1.empty()) {
            Node temp = nodeStack1.pop();
            nodeStack2.push(temp);
            if (temp.left != null)
                nodeStack1.push(temp.left);
            if (temp.right != null)
                nodeStack1.push(temp.right);
        }
        while (!nodeStack2.isEmpty()) {
            Node temp = nodeStack2.pop();
            PostOrder = PostOrder + temp.value + ", ";
        }
        return PostOrder.substring(0, PostOrder.length() - 2);
    }
    */


}


    /*
    @Override
    public String toString() {
        String retStr = "Document: " + name;
        if (link.size() > 0)
            retStr += "\n";
        retStr += link.toStringInOrder();
        return retStr;
    }

    public String toStringPreOrder() {
        String retStr = "Document: " + name;
        if (link.size() > 0)
            retStr += "\n";
        retStr += link.toStringPreOrder();
        return retStr;
    }

    public String toStringPostOrder() {
        String retStr = "Document: " + name;
        if (link.size() > 0)
            retStr += "\n";
        retStr += link.toStringPostOrder();
        return retStr;
    }
     */