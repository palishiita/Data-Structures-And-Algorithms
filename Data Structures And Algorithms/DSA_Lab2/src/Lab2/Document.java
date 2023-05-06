package Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Document {

    public String name;
    public OneWayLinkedList<Link> links; /// linked list of the type Link (class)

    // Document constructor
    public Document(String name, Scanner scan) {
        // TODO
        links = new OneWayLinkedList<>();
        this.name = name;
        String contents = " ";
        File file = new File(name+".txt");
        if(file.exists() && !file.isDirectory()) {
            try (Scanner sc = new Scanner(file)) {
                while(sc.hasNext() && !contents.equals("eod")) {
                    contents = sc.next();
                    if(!contents.contains("=")) {
                        String[] res = contents.split("[=]", 2); //if contains an = -> split
                        if(res[0].toUpperCase().equals("LINK")) //check if it starts by link (first string of the split)
                            if(res[1] != null && correctLink(res[1])) {
                                String aux = res[1];
                                Link list = new Link(null);
                                list.ref = aux;
                                links.add(list);
                            }
                    }
                }
            }catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else {
            load(scan);
        }
    }

    public void load(Scanner scan) {
        String marker = "link=";
        String endMarker = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(endMarker)) {
            String arr[] = line.split(" ");
            for (String word : arr) {
                if(word.startsWith(marker)) {
                    String link = word.substring(marker.length());
                    if(correctLink(link)) {
                        //System.out.println(link);
                        Link list = new Link(null);
                        list.ref = link;
                        links.add(list);
                    }
                }
            }
            line = scan.nextLine().toLowerCase(); // next loop
        }
    }

    // accept only small letters, capital letter, digits and '_' (but not on the beginning)
    public static boolean correctLink(String link) {
        if (link.length() == 0)
            return false;
        if ((link.charAt(0) < 'a' || link.charAt(0) > 'z')) {
            return false;
        }
        for (int i = 1; i < link.length(); i++) {
            if (!(link.charAt(i) >= 'a' && link.charAt(i) <= 'z' ||
                    link.charAt(i) >= '0' && link.charAt(i) <= '9' ||
                    link.charAt(i) == '_' ))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String ret = "Document: " + name + "\n";
        String element=null;
        for(int i=0;i<links.size();i++) {
            element=links.get(i).ref;
            ret = ret + element + "\n";
        }

        return ret;
    }

}
