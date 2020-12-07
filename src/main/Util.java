package main;

public class Util {

     public static char convertRank(int x) {
        switch (x) {
            case 0:
                return'a';
            case 1:
                return'b';
            case 2:
                return'c';
            case 3:
                return'd';
            case 4:
                return'e';
            case 5:
                return'f';
            case 6:
                return'g';
            case 7:
                return'h';
        }
        return ' ';
    }

    public static int convertFile(int y) {
        return y += 1;
    }
}
