package test;

public class conversion {
    public static void main(String[] args){
        int i = -100;
        char[] cs = String.valueOf(i).toCharArray();
        // string value of to char array included flag!!!
        for(char c:cs){
            System.out.print(c);
        }

    }
}
