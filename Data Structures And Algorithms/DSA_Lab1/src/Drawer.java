public class Drawer {

    public static void drawPyramid(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= (n-i); j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= (i*2-1); k++) {
                System.out.print("X");
            }
            System.out.println();
        }
    }

    public static void drawChristmassTree(int n) {
        for(int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                for (int k = 1; k <= (n-j); k++) {
                    System.out.print(" ");
                }
                for (int l = 0; l != (j*2-1); l++) {
                    System.out.print("X");
                }
                System.out.println();
            }
        }
    }

}


