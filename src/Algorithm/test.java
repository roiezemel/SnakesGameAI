package Algorithm;

public class test {

    public static void main(String[] args){

        String value = "24.3 25.9 27.2 29.1 30.4 32.2 34.7 38.4 44.5 56.5 90.8 392.1";
        String[] li = value.split(" ");

        for(String i : li){
            System.out.println(1/(Double.parseDouble(i)));
        }

    }

}
