package Lab2;
// doc[currentDocNo] is null -> fix that
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    static Scanner scan; // for input stream

    public static void main(String[] args) {

        System.out.println("START");
        scan = new Scanner(System.in);
        Document [] doc = null;
        int currentDocNo = 0;
        int maxNo = -1;
        boolean halt=false;

        while(!halt) {

            /* If a line is empty or starts from ‘#’ sign, the line has to be ignored. In any other case, the program
            should print "!" and write a copy of introduced line and then, depending on the command follow the correct
            procedure/function. */

            String line = scan.nextLine();

            // empty line and comment line - read next line
            if(line.length()==0 || line.charAt(0)=='#')
                continue;

            // copy line to output (it is easier to find a place of a mistake)
            System.out.println("!"+line);
            String[] word = line.split(" ");

            // go n - start with array of the length n
            /* go <nMax>: Main program will create an array of nMax Documents (without creation the documents). The
            lists are numbered from 0 like an array of documents. Default current position is the number 0. */
            if(word[0].equalsIgnoreCase("go") && word.length==2) {
                maxNo=Integer.parseInt(word[1]);
                doc = new Document[maxNo];
                continue;
            }

            //ch - change index
            /* ch <n> Main program will choose a position of a number n, and all the following functions will operate on
            this position in the table (most often on the links field of the selected object, it is ensured in the tests
            that such an object will be constructed earlier). There is 0 <= n < nMax */
            if(word[0].equalsIgnoreCase("ch") && word.length==2) {
                currentDocNo=Integer.parseInt(word[1]);
                continue;
            }

            // ld documentName -> implement in the document
            /* Main program will call a constructor of a document with parameters name and scan for current position, in
            order to load the document according to the same rules as for the previous list. If there is a document in
            its current position, it will be overwritten. Detected links, in the order read from the document, are to be
            saved to the links list. */
            if(word[0].equalsIgnoreCase("ld") && word.length==2) {
                doc[currentDocNo]=new Document(word[1],scan);
                continue;
            }

            // show --> implement
            /* Main program will write on the screen the result of calling toString() for the current document. The first
            line has to present text “Document: <name>”, the rest of lines – every link in separated line. */
            if(word[0].equalsIgnoreCase("show") && word.length==1) {
                System.out.println(doc[currentDocNo].toString());
                continue;
            }

            // clear
            /* Main program will call clear() for the link list of the current document */
            if(word[0].equalsIgnoreCase("clear") && word.length==1) {
                doc[currentDocNo].links.clear();
                continue;
            }

            // size
            /* Main program will call size() for the link list of the current document and write on the screen returned
            value in one line.*/
            if(word[0].equalsIgnoreCase("size") && word.length==1) {
                System.out.println(doc[currentDocNo].links.size());
                continue;
            }

            // add str
            /* add <str> Main program will call add(new Link(str)) for the link list of the current document. The result
            of the addition will be printed on one line */
            if(word[0].equalsIgnoreCase("add") && word.length==2) {
                System.out.println(doc[currentDocNo].links.add(new Link(word[1])));
                continue;
            }

            // addi index str
            /* addi <index> <str> Main program will call add(index, new Link(str)) for the link list of the current
            document. It will print the result or "error" if the method throws an appropriate exception. */
            if(word[0].equalsIgnoreCase("addi") && word.length==3) {
                int index=Integer.parseInt(word[1]);
                try {
                    doc[currentDocNo].links.add(index, new Link(word[2]));
                }
                catch (NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }

            // get index
            /* get <index> Main program will call get(index) for the link list of the current document and write on the
            screen field ref from returned Link object or "error" if the method throws an appropriate exception */
            if(word[0].equalsIgnoreCase("get") && word.length==2) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].links.get(index);
                    System.out.println(l.ref);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }

            // set index str
            /* set <index> <str> Main program will call set(index, new Link(str)) for the link list of the current
            document and write on the screen field ref from returned Link object or "error" if the method throws an
            appropriate exception. */
            if(word[0].equalsIgnoreCase("set") && word.length==3) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].links.set(index,new Link(word[2]));
                    System.out.println(l.ref);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }

            // index str
            /* index <str> Main program will call index(str) the link list of the current document and write on the
            screen returned value in one line. */
            if(word[0].equalsIgnoreCase("index") && word.length==2) {
                int index=doc[currentDocNo].links.indexOf(new Link(word[1]));
                System.out.println(index);
                continue;
            }

            // remi index
            /* remi <index> Main program will call remove(index) for the link list of the current document and write on
            the screen field ref from returned Link object or "error" if the method throws an appropriate exception */
            if(word[0].equalsIgnoreCase("remi") && word.length==2) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].links.remove(index);
                    System.out.println(l.ref);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }

            // rem str
            /* Main program will call remove(str) for the link list of the current document and write on the screen
            returned value in one line. */
            if(word[0].equalsIgnoreCase("rem") && word.length==2) {
                System.out.println(doc[currentDocNo].links.remove(new Link(word[1])));
                continue;
            }

            // ha
            /* ha Main program will end the execution, writing as the last line “END OF EXECUTION”. Every test ends with
             this line.*/
            if (word[0].equalsIgnoreCase("ha") && word.length==1) {
                halt=true;
                continue;
            }

            System.out.println("Wrong command");
        }

        System.out.println("END OF EXECUTION");
        scan.close();

    }

}
