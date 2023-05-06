import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static Scanner scan; // for input stream

    public static void main(String[] args) {
        System.out.println("START");
        scan=new Scanner(System.in);
        Document[] doc=null;
        int currentDocNo=0;
        int maxNo=-1;
        boolean halt=false;
        while(!halt) {
            String line=scan.nextLine();
            // empty line and comment line - read next line
            if(line.length()==0 || line.charAt(0)=='#')
                continue;
            // copy line to output (it is easier to find a place of a mistake)
            System.out.println("!"+line);
            String word[]=line.split(" ");
            // go n - start with array of the length nMax documents (without creation the documents)
            // go 10
            if(word[0].equalsIgnoreCase("go") && word.length==2) {
                maxNo=Integer.parseInt(word[1]);
                doc = new Document[maxNo];
                continue;
            }
            //ch - change index
            // program chooses a position of a number n, and all next functions will operate on this position in the array.
            // There is 0<=n<nMax
            if(word[0].equalsIgnoreCase("ch") && word.length==2) {
                currentDocNo=Integer.parseInt(word[1]);
                continue;
            }
            /* program calls a constructor of a document with parameters name and scan for current position.
            If there is a document on the current position it will be overwritten.
            The name has to be a correct identifier. */
            // ld documentName
            if(word[0].equalsIgnoreCase("ld") && word.length==2) {
                if(Document.isCorrectId(word[1]))
                    doc[currentDocNo]=new Document(word[1],scan);
                else
                    System.out.println("incorrect ID");
                continue;
            }
            // ha
            if(word[0].equalsIgnoreCase("ha") && word.length==1) {
                halt=true;
                continue;
            }
            // clear
            if(word[0].equalsIgnoreCase("clear") && word.length==1) {
                doc[currentDocNo].link.clear();
                continue;
            }
            // show
            if(word[0].equalsIgnoreCase("show") && word.length==1) {
                System.out.println(doc[currentDocNo].toString());
                continue;
            }
            // reverse
            if(word[0].equalsIgnoreCase("reverse") && word.length==1) {
                System.out.println(doc[currentDocNo].toStringReverse());
                continue;
            }
            // size
            if(word[0].equalsIgnoreCase("size") && word.length==1) {
                System.out.println(doc[currentDocNo].link.size());
                continue;
            }
            // add str
            if(word[0].equalsIgnoreCase("add") && word.length==2) {
                System.out.println(doc[currentDocNo].link.add(new Link(word[1])));
                continue;
            }
            // adding at index str
            if(word[0].equalsIgnoreCase("addi") && word.length==3) {
                int index=Integer.parseInt(word[1]);
                try {
                    doc[currentDocNo].link.add(index, new Link(word[2]));
                }
                catch (NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }
            // get index
            //program calls get(index) for current document and write on the screen field ref from returned Link object.
            // If the index is incorrect, write in one line “error”.
            if(word[0].equalsIgnoreCase("get") && word.length==2) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].link.get(index);
                    System.out.println(l.ref);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }
            // set index str
            if(word[0].equalsIgnoreCase("set") && word.length==3) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].link.set(index,new Link(word[2]));
                    System.out.println(l.ref);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }
            // index str
            if(word[0].equalsIgnoreCase("index") && word.length==2) {
                int index=doc[currentDocNo].link.indexOf(new Link(word[1]));
                System.out.println(index);
                continue;
            }
            // remove at an index
            if(word[0].equalsIgnoreCase("remi") && word.length==2) {
                int index=Integer.parseInt(word[1]);
                try {
                    Link l=doc[currentDocNo].link.remove(index);
                    System.out.println(l);
                }
                catch(NoSuchElementException e) {
                    System.out.println("error");
                }
                continue;
            }
            // remove str
            if(word[0].equalsIgnoreCase("rem") && word.length==2) {
                System.out.println(doc[currentDocNo].link.remove(new Link(word[1])));
                continue;
            }
            // remove all str
            if(word[0].equalsIgnoreCase("remall") && word.length==2) {
                doc[currentDocNo].link.removeAll(new Link(word[1]));
                continue;
            }
            // addl <indexOfListArray>
            // program has to call add(secondList) for current document and the second list it has to be a document of
            // the number listNo from the array of documents.
            if(word[0].equalsIgnoreCase("addl") && word.length==2) {
                int number=Integer.parseInt(word[1]);
                doc[currentDocNo].link.add(doc[number].link);
                continue;
            }
            System.out.println("Wrong command");
        }
        System.out.println("END OF EXECUTION");
        scan.close();
    }
// text link=ggg and link=cc link=a link=e
/*
go 10
ld zero
link=GGG(5) link=eeee(15)
link=gGg(4)
adsad link=abc link=cos(30) link=cos(1) link=wrong(-1) link=wrong(asdf)
link=wrong(1.23) link=ok(123) link=kkk
eod
show
reverse
ch 1
ld first
correct link=cos(15) link=zzz link=cos(12) link=eee(1) link=eeee(14)
this is link=wrong(12 o yeah.
eod
show
reverse
addl 0
show
reverse
ch 0
show
reverse
ch 2
ld 3ddfg
ld ABC
and LiNk=Abc(4)
eod
show
ch 1
show
remall cos
show
ha
*/


}
