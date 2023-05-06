import java.util.Scanner;

public class Document implements IWithName {
    public String name;
    public BST<Link> link;

    public Document(String name) {
        this.name = name.toLowerCase();
        link = new BST<>();
    }

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new BST<>();
        load(scan);
    }

    public void load(Scanner scan) {
        String documentLink = "link=";
        String documentEnd = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(documentEnd)) {
            String[] arr = line.split(" ");
            for (String word : arr) {
                if (word.startsWith(documentLink)) {
                    String l = word.substring(documentLink.length());
                    Link createdLink = createLink(l);
                    // createdLink = g(10), add createdLink to link (BST)
                    link.add(createdLink); // error
                }
            }
            line = scan.nextLine().toLowerCase();
        }
    }

    public static boolean isCorrectId(String id) {
        if (id.toLowerCase().length() == 0)
            return false;
        if ((id.toLowerCase().charAt(0) < 'a' || id.toLowerCase().charAt(0) > 'z'))
            return false;
        for (int i = 1; i < id.toLowerCase().length(); i++) {
            if (!(id.toLowerCase().charAt(i) >= 'a' && id.toLowerCase().charAt(i) <= 'z' ||
                    id.toLowerCase().charAt(i) >= '0' && id.toLowerCase().charAt(i) <= '9' ||
                    id.toLowerCase().charAt(i) == '_'))
                return false;
        }
        return true;
    }

    static Link createLink(String link) throws NumberFormatException {
        if (link.length() == 0) return null;
        int open_parenthesis = link.indexOf('(');
        int close_parenthesis = link.indexOf(')');
        if (open_parenthesis > 0 && close_parenthesis > open_parenthesis && close_parenthesis == link.length() - 1) {
            String stringNumber = link.substring(open_parenthesis + 1, close_parenthesis);
            try {
                int num = Integer.parseInt(stringNumber);
                if (num < 1)
                    return null;
                return creatingNewLink(link.substring(0, open_parenthesis), num);
            } catch (NumberFormatException Exception) {
                return null;
            }
        }
        return creatingNewLink(link, 1);
    }

    static Link creatingNewLink(String id, int weight) {
        if (!isCorrectId(id.toLowerCase()))
            return null;
        return new Link(id.toLowerCase(), weight);
    }

    private static final int MODVALUE = 100000000;
    public static final int[] sequence = {7, 11, 13, 17, 19};

    // hashcode implementation
    @Override
    public int hashCode() {
        if (name.length() == 0)
            return 0;
        int[] HashCodeLetter = getHashCodeLetter(name);
        int newHashCode = HashCodeLetter[0];
        int index = 0;
        for (int i = 1; i < HashCodeLetter.length; i++) {
            newHashCode = (newHashCode * sequence[index]) + HashCodeLetter[i];
            newHashCode = newHashCode % MODVALUE;
            index++;
            if (index > sequence.length - 1)
                index = 0;
        }
        return newHashCode;
    }
    // hashcode array
    public static int[] getHashCodeLetter(String nameStr) {
        int[] charHashLetter = new int[nameStr.length()];
        for (int i = 0; i < nameStr.length(); i++) {
            charHashLetter[i] = nameStr.charAt(i);
        }
        return charHashLetter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringInOrder();
        return retStr;
    }

    public String toStringPreOrder() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringPreOrder();
        return retStr;
    }

    public String toStringPostOrder() {
        String retStr="Document: "+name+"\n";
        retStr+=link.toStringPostOrder();
        return retStr;
    }

}

/**
 * inorder traversal: traverse left -> visit node -> traverse right (triangle)
 * inorder successor: smallest value greater than elem i.e. next nodeElem during inorder traversal
 * next greater elem of any elem is the leftmost node in the right subtree of that node [has right child]
 * 3 cases for successor:
 * node has right sub-tree
 * - successor is the most left node/ leaf.
 * - if no left node then it is the right node.
 * node has no right sub-tree, and it is right child
 * - right child will point to parent and parent (unless parent is root itself) will point to its parent
 * node has no right sub-tree, and it is left child
 * - left child will point to parent
 **/