import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;

public class Document implements IWithName{
    // All operation have to be done in modulo to not exceed int type’s range
    private static final int MODVALUE = 100000000;
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public static final int [] sequence = {7, 11, 13, 17, 19};


    // Document Constructor 1
    public Document(String name) {
        this.name = name.toLowerCase();
    }

    // Document Constructor 2
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

    public static Link createLink(String link) throws NumberFormatException {
        if(link.length()==0) return null;
        int open_parenthesis = link.indexOf('(');
        int close_parenthesis = link.indexOf(')');
        if(open_parenthesis>0 && close_parenthesis>open_parenthesis && close_parenthesis==link.length()-1) {
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

    public static Link creatingNewLink(String id, int weight) {
        if(!isCorrectId(id.toLowerCase())) {
            return null;
        } else {
            return new Link(id.toLowerCase(), weight);
        }
    }

    @Override
    public String toString() {
        String retStr = "Document: " + name;
        int numOfLinks = 0;
        for(Link linkElem:link) {
            if(numOfLinks % 10 == 0) {
                retStr = retStr + "\n";  // add links to new line after 10 links
            } else {
                retStr = retStr + " "; // continue display if not and add space
            }
            retStr = retStr + linkElem.toString();
            numOfLinks++;
        }
        return retStr;
    }

    public String toStringReverse() {
        String retStr="Document: "+name;
        int numOfLinks = 0;
        ListIterator<Link> iter=link.listIterator();
        while(iter.hasNext())
            iter.next();
        while(iter.hasPrevious()){
            if(numOfLinks % 10 == 0)
                retStr = retStr + "\n";
            else
                retStr = retStr + " ";
            retStr= retStr + iter.previous().toString();
            numOfLinks++;
        }
        return retStr;
    }


    // getName IWithName interface
    @Override
    public String getName() {
        return name;
    }

    //equals() and hashCode() in Document class
    // compares name’s of documents

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Document)) return false;
        Document document = (Document) object;
        return Objects.equals(getName(), document.getName());
    }

    // Java's hashCode() function does the hashing
    // sequence: "7,11,13,17,19",7,11,13,19…
    // result = multiply firstLetter(hashCode) by firstNumber(sequence) and add result to secondLetter(hashCode).
    // “abcd”: 97*7+98=777 --> 777*11+99=8646 --> 8646*13+100=112498
    @Override
    public int hashCode() {
        if (name.length() == 0) {
            return 0;
        }

        int[] HashCodeLetter = getHashCodeLetter(name);
        int newHashCode = HashCodeLetter[0];
        int index = 0;
        for (int i=1; i < HashCodeLetter.length; i++) {
            newHashCode = (newHashCode * sequence[index]) + HashCodeLetter[i];
            newHashCode = newHashCode % MODVALUE;
            /*
            newHashCode = (newHashCode * sequence[index]) % MODVALUE;
            newHashCode= (newHashCode + HashCodeLetter[i]) % MODVALUE;
             */
            index++;

            if (index > sequence.length-1) {
                index = 0;
            }
        }
        return newHashCode;
    }

    // char returns hashcode
    public static int []  getHashCodeLetter(String nameStr) {
        int[] charHashLetter = new int[nameStr.length()];
        for (int i = 0; i < nameStr.length(); i++) {
            charHashLetter[i] = nameStr.charAt(i);
        }
        return charHashLetter;
    }

}