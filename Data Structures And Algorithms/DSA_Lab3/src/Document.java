import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Document {

    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name;
        link = new TwoWayUnorderedListWithHeadAndTail<>();
        load(scan);
    }

    public void load(Scanner scan) {
        String Link = "link=";
        String eod = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(eod)) {
            String[] arr = line.split(" ");
            for (String word : arr) {
                if (word.startsWith(Link)) {
                    String links = word.substring(Link.length());
                    if (correctLink(links)) {
                        Link LinkedList = new Link(links);
                        link.add(LinkedList);
                    }
                }
            }
            line = scan.nextLine().toLowerCase();
        }
    }

    public static boolean correctLink(String link) {
        if (link.length() == 0)
            return false;
        if ((link.charAt(0) < 'a' || link.charAt(0) > 'z')) {
            return false;
        }
        for (int i = 1; i < link.length(); i++) {
            if (!(link.charAt(i) >= 'a' && link.charAt(i) <= 'z' ||
                    link.charAt(i) >= '0' && link.charAt(i) <= '9' ||
                    link.charAt(i) == '_'))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Document: " + name);
        String s;
        for (int i = 0; i < link.size(); i++) {
            s = link.get(i).ref;
            ret.append("\n").append(s);
        }
        return ret.toString();
    }

    public String toStringReverse() {
        String retStr = "Document: " + name;
        return retStr + link.toStringReverse();
    }

}


