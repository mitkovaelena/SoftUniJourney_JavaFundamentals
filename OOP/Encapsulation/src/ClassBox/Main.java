package ClassBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Double l = Double.parseDouble(reader.readLine());
        Double w = Double.parseDouble(reader.readLine());
        Double h = Double.parseDouble(reader.readLine());
        try {
            Box box = new Box(l, w, h);
            System.out.println(box.toString());
        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        }
    }

}
