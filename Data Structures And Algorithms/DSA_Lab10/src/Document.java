import java.util.Scanner;
import java.util.*;

public class Document implements IWithName {

    // fields
    public String name; // Document name
    public SortedMap<String, Link> link; // collection of link objects

    // constructor 1
    public Document(String name) {
        this.name = name.toLowerCase();
        link = new TreeMap<>();
    }

    // constructor 2
    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TreeMap<>();
        load(scan);
    }

    // getting the links (links of the document object) and putting them array
    public Link[] getLinks() {
        Object [] ob = this.link.values().toArray();
        Link[] links = new Link[ob.length];
        int i = 0;
        for (Object o : ob) {
            Link l = (Link) o;
            links[i] = l;
            i++;
        }
        return links;
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
                    assert createdLink != null;
                    // TODO load class updated here!
                    link.put(createdLink.ref,createdLink);
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

    // for dfs
    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder("Document: " + name + "\n");
        // get a set of all values from collection and transform it into an array of objects
        Object[] links = link.values().toArray();
        for (int i = 0; i < links.length; i++) {
            if (i == links.length - 1) {
                retStr.append(links[i]);
            } else {
                retStr.append(links[i]).append(", ");
            }
        }
        return retStr.toString();
    }



    // getter
    @Override
    public String getName() {
        return name;
    }

    // hashcode
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}

