import java.util.*;

public class Main {
    static Scanner scan; // for input stream

    public static void main(String[] args) {
        System.out.println("START");
        scan = new Scanner(System.in);
        // documents will be stored in hash table and access to a document will be with its name
        // constructor 2
        HashTable hashTable = new HashTable(8); //[*]
        Document currentDoc = null;
        boolean halt = false;
        while (!halt) {
            String line = scan.nextLine();
            // empty line and comment line - read next line
            if (line.length() == 0 || line.charAt(0) == '#')
                continue;
            // copy line to output (it is easier to find a place of a mistake)
            System.out.println("!" + line);
            String[] word = line.split(" ");
            //getdoc name - change document to name [*]
            if (word[0].equalsIgnoreCase("getdoc") && word.length == 2) {
                currentDoc = (Document) hashTable.get(new Document(word[1]));
                continue;
            }
            // ld documentName
            if (word[0].equalsIgnoreCase("ld") && word.length == 2) {
                if (Document.isCorrectId(word[1])) {
                    currentDoc = new Document(word[1], scan);
                    if (!hashTable.add(currentDoc))
                        System.out.println("error");
                } else
                    System.out.println("incorrect ID");
                continue;
            }
            // ha
            if (word[0].equalsIgnoreCase("ha") && word.length == 1) {
                halt = true;
                continue;
            }
            // clear
            if (word[0].equalsIgnoreCase("clear") && word.length == 1) {
                if (currentDoc != null)
                    currentDoc.link.clear();
                else
                    System.out.println("no current document");
                continue;
            }
            // show
            if (word[0].equalsIgnoreCase("show") && word.length == 1) {
                if (currentDoc != null)
                    System.out.println(currentDoc.toString());
                else
                    System.out.println("no current document");
                continue;
            }
            // reverse
            if (word[0].equalsIgnoreCase("reverse") && word.length == 1) {
                if (currentDoc != null)
                    System.out.println(currentDoc.toStringReverse());
                else
                    System.out.println("no current document");
                continue;
            }
            // size
            if (word[0].equalsIgnoreCase("size") && word.length == 1) {
                if (currentDoc != null)
                    System.out.println(currentDoc.link.size());
                else
                    System.out.println("no current document");
                continue;
            }
            // add str
            if (word[0].equalsIgnoreCase("add") && word.length == 2) {
                if (currentDoc != null) {
                    Link link = Document.createLink(word[1]);
                    if (link == null)
                        System.out.println("error");
                    else
                        System.out.println(currentDoc.link.add(link));
                } else
                    System.out.println("no current document");
                continue;
            }
            // get index
            if (word[0].equalsIgnoreCase("get") && word.length == 2) {
                if (currentDoc != null) {
                    int index = Integer.parseInt(word[1]);
                    try {
                        Link l = currentDoc.link.get(index);
                        System.out.println(l.ref);
                    } catch (NoSuchElementException e) {
                        System.out.println("error");
                    }
                } else
                    System.out.println("no current document");
                continue;
            }
            // index str
            if (word[0].equalsIgnoreCase("index") && word.length == 2) {
                if (currentDoc != null) {
                    int index = currentDoc.link.indexOf(new Link(word[1]));
                    System.out.println(index);
                } else
                    System.out.println("no current document");

                continue;
            }
            // remi index
            if (word[0].equalsIgnoreCase("remi") && word.length == 2) {
                if (currentDoc != null) {
                    int index = Integer.parseInt(word[1]);
                    try {
                        Link l = currentDoc.link.remove(index);
                        System.out.println(l);
                    } catch (NoSuchElementException e) {
                        System.out.println("error");
                    }
                } else
                    System.out.println("no current document");
                continue;
            }
            // rem str
            if (word[0].equalsIgnoreCase("rem") && word.length == 2) {
                if (currentDoc != null) {
                    System.out.println(currentDoc.link.remove(new Link(word[1])));
                } else
                    System.out.println("no current document");
                continue;
            }
            // remall str
            if (word[0].equalsIgnoreCase("remall") && word.length == 2) {
                if (currentDoc != null) {
                    currentDoc.link.removeAll(new Link(word[1]));
                } else
                    System.out.println("no current document");
                continue;
            }
            // ht - show hashtable [*]
            if (word[0].equalsIgnoreCase("ht") && word.length == 1) {
                System.out.print(hashTable.toString());
                continue;
            }
            System.out.println("Wrong command");
        }
        System.out.println("END OF EXECUTION");
        scan.close();
    }
}

/*
show
ld first
link=a(10)
eod
add b(11)
show
ld second
link=c(3)
eod
add d(7)
show
getdoc first
show
reverse
getdoc second
show
reverse
getdoc xxx
show
ld xxx
eod
ld qqq
eod
ld asd
eod
ht
ld zx
eod
ht
ha
 */


/*
    public String toStringInOrder() {
        Node temp = root;
        String str = inOrder(temp);
        if (str!=null) {
            str = str.substring(0, str.length()-2);
        }
        SBInOrder.delete(0, SBInOrder.length());
        return str;
    }
    private String inOrder(Node node) {
        if (node == null)
            return null;
        inOrder(node.left);
        SBInOrder.append(node.value + ", ");
        inOrder(node.right);
        return node.value.toString();
    }
     */

    /*

    // visit node -> traverse left -> traverse right
    // g(10) e(11) a(10) f(10) k(12) i(10) n(10)
    public String toStringPreOrder() {
        return preOrder(root);
    }
    private String preOrder(Node node) {
        if (node == null)
            return null;
        else {
            // end node will have no successor element
            //if (successor(node, node.value) == null) {
            System.out.println(node.value + " ");
            //} else {
            //System.out.print(node.value + ", ");
            //}
            preOrder(node.left);
            preOrder(node.right);
        }
        return node.value.toString();
    }

    // traverse left -> traverse right -> visit node
    // a(10) f(10) e(11) i(10) n(10) k(12) g(10)
    public String toStringPostOrder() {
        return postOrderRec(root);
    }
    private String postOrderRec(Node node) {
        if (node == null)
            return null;
        else {
            postOrderRec(node.left);
            postOrderRec(node.right);
            // end node will have no successor element
            //if (successor(node, node.value) == null) {
            System.out.println(node.value + " ");
            //} else {
            //System.out.print(node.value + ", ");
            //}
        }
        return node.value.toString();
    }
    */