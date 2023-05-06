import java.util.ListIterator;
import java.util.Scanner;

public class Document{

    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<>();
        load(scan);
    }

    // load(scan)
    public void load(Scanner scan) {
        String documentLink = "link=";
        String documentEnd = "eod";
        String line = scan.nextLine().toLowerCase();
        while(!line.equalsIgnoreCase(documentEnd)) { // loading line links here!
            String[] arr = line.split(" ");
            for(String word:arr) {
                if(word.startsWith(documentLink)) { // look for link
                    String links = word.substring(documentLink.length()); //words with link= loaded!

                    Link Links = createLink(links);
                    if(Links!=null) { // extract the letters after link=
                        link.add(Links);
                    }

                }
            }
            line=scan.nextLine().toLowerCase();
        }
    }

    // accept only small letters, capital letter, digits nad '_' (but not on the beginning)
    // id: zero, ggg, etc. (consists of document name and link=name)
    public static boolean isCorrectId(String id) {
        if (id.toLowerCase().length() == 0)
            return false;
        if ((id.toLowerCase().charAt(0) < 'a' || id.toLowerCase().charAt(0) > 'z'))
            return false;
        for (int i = 1; i < id.toLowerCase().length(); i++) {
            if (!(id.toLowerCase().charAt(i) >= 'a' && id.toLowerCase().charAt(i) <= 'z' ||
                    id.toLowerCase().charAt(i) >= '0' && id.toLowerCase().charAt(i) <= '9' ||
                    id.toLowerCase().charAt(i) == '_' ))
                return false;
        }
        return true;
    }

    public static Link creatingNewLink(String id, int weight) {
        if(!isCorrectId(id.toLowerCase())) {
            return null;
        } else {
            return new Link(id.toLowerCase(), weight);
        }
    }

    // check if number in parentheses is correct (not negative, not decimal ie integer, not alphabet)
    // final link is concatenation of correctID and correct number in parentheses
    public static Link createLink(String link) throws NumberFormatException {
        if(link.length()==0) return null;
        // -1 if no parenthesis
        int open_parenthesis = link.indexOf('(');
        int close_parenthesis = link.indexOf(')');
        if(open_parenthesis>0 && close_parenthesis>open_parenthesis && close_parenthesis==link.length()-1) {
            //(123) => 123 1 = start_index and 3 end_index for substring
            String stringNumber = link.substring(open_parenthesis+1, close_parenthesis);
            try {
                int num = Integer.parseInt(stringNumber);
                if (num < 1)
                    return null;
                return creatingNewLink(link.substring(0, open_parenthesis), num);
            } catch (NumberFormatException Exception) {
                return null;
            }
        }
        return creatingNewLink(link,1);
    }

    // Normal and reverse order: first line must display 10 links and
    // the next line will display rest of the links
    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder("Document: " + name);
        int numOfLinks = 10;
        for (Link linkElem : link) {
            if (numOfLinks == 10) {
                retStr.append("\n"); // add links to new line after 10 links
                numOfLinks = 0;
            } else
                retStr.append(" "); // continue display if not and add space
            retStr.append(linkElem.toString());
            numOfLinks++;
        }
        return retStr.toString();
    }

    public String toStringReverse() {
        StringBuilder retStr= new StringBuilder("Document: " + name);
        int numOfLinks=0;
        ListIterator<Link> iter=link.listIterator();
        while(iter.hasNext())
            iter.next();
        while(iter.hasPrevious()){
            if(numOfLinks%10==0)
                retStr.append("\n");
            else
                retStr.append(" ");
            retStr.append(iter.previous().toString());
            numOfLinks++;
        }
        return retStr.toString();
    }
}