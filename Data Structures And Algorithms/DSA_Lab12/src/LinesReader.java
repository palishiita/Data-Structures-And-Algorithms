import java.util.Scanner;

public class LinesReader {

    String concatLines(int howMany, Scanner scanner) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i <= howMany; i++) {
            String nextLine = scanner.nextLine();
            stringBuffer.append(nextLine);
        }
        return stringBuffer.toString();
    }
}

