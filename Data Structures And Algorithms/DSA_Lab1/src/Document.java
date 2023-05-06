import java.util.Scanner;

/**
 * Write a procedure loadDocument(String name) which will load and analyze lines after lines searching for link in every line.
 * The link format is as follows:
 * - 5 characters “link=” (it can be mixed capital and small letters).
 * - After which there is a correct identifier. The correct identifier starts from letter (small or capital) followed
 * by zero or more occurrences of letters or digits or underline ‘_’.
 * - The procedure has to print subsequent identifiers, each one in a separated line. Before printing, the identifiers have to be changed to small letters.
 * - The document ends with line with the text “eod”, which means end of document.
 */

public class Document {

    /* after this.name
        String input = " ";
        File file = new File(name);
        if(file.exists() && !file.isDirectory()) {
            try (Scanner sc = new Scanner(file)) {
                while(sc.hasNext() && !input.equals("eod")) { // eod (at the end)
                    input = sc.next();
                    if(!input.contains("=")) {
                        String[] stringInput = input.split("[=]", 2);
                        if(stringInput[0].equalsIgnoreCase("link"))
                            if(stringInput[1] != null && correctLink(stringInput[1])) { //
                                String newResult = stringInput[1]; // correct string input
                                Link list = new Link(null); // creating obj of Link
                                list.ref = newResult;
                                links.add(list);
                            }
                    }
                }
            }catch (FileNotFoundException e) {
                System.out.println("error");
            }
        }
        else {
            load(scan);
        }

         */
    public static void loadDocument(Scanner scan) {
        String documentLink = "link=";
        String documentEnd = "eod";
        String name = scan.nextLine();
        while (!name.equalsIgnoreCase(documentEnd)) {
            String[] wordArray = name.split(" ");
            for (String word : wordArray) {
                if (word.startsWith(documentLink)) {
                    String link = word.substring(documentLink.length());
                    if (correctLink(link)) {
                        System.out.println(link);
                    }
                }
            }
            name = scan.nextLine().toLowerCase();
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

}
// https://pastebin.com/NwNzTBqz
// https://pastebin.com/qUmEbdVK