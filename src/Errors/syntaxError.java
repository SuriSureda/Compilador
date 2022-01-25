package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class syntaxError extends Exception{
    private static BufferedWriter out = null;
    private final String PATH = "output\\syntaxError.txt";

    public syntaxError(String error){
        super(error);
        if (out== null){
            try {
                out = new BufferedWriter(new FileWriter(PATH));
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        try {
            out.write(error);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}
