package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class semanticError {
    private static BufferedWriter out;
    private final String PATH = "output\\semanticError.txt";

    public semanticError(){
        try {
            out = new BufferedWriter(new FileWriter(PATH));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


}
