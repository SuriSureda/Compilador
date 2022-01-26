package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SyntaxError extends Exception{
    private static BufferedWriter out = null;
    private final String PATH = "output\\syntaxError.txt";

    public SyntaxError(String error){
        super(error);
        if (out== null){
            try {
                out = new BufferedWriter(new FileWriter(PATH));
            } catch (Exception e) {
            }
        }
        try {
            out.write(error);
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
