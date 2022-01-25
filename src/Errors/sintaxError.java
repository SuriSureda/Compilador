package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class sintaxError {
    private static BufferedWriter out;
    private final String PATH = "output\\sintaxError.txt";

    public sintaxError(){
        try {
            out = new BufferedWriter(new FileWriter(PATH));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
