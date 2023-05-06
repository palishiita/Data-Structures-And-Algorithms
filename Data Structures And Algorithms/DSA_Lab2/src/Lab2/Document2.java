package Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document2 {

    private static final Pattern linkExtract = Pattern.compile("\\s*link=(?<link>[A-Za-z]+\\w*)");
    private static final Pattern linkValidator = Pattern.compile("^(?<link>[A-Za-z]+\\w*)$");
    private static final Pattern eodExtract = Pattern.compile("^\\s*eod\\s*$");

    public String name;
    public OneWayLinkedList<Link> links; /// linked list of the type Link (class)

    // Document constructor
    public Document2(String name, Scanner scan) {
        // TODO
        if (name == null)
            throw new IllegalArgumentException();
        this.name = name;
        load(scan);
    }

    public void load(Scanner scan) {
        if (scan == null)
            throw new NullPointerException();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (isEod(line))
                break;
            parseLinks(line);
        }
    }

    static boolean isEod(String text) {
        Matcher matcher = eodExtract.matcher(text);
        return matcher.find();
    }

    void parseLinks(String input) {
        Matcher matcher = linkExtract.matcher(input);
        while (matcher.find()){
            String link = matcher.group("link").toLowerCase(Locale.ROOT);
            this.links.add(new Link(link));
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
        StringBuilder result = new StringBuilder();
        result.append("Document: ").append(name);
        for (Link link: links) {
            result.append("\n");
            result.append(link.ref);
        }

        return result.toString();
    }
}
