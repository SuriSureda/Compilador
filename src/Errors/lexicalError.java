package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class lexicalError{
    private static BufferedWriter out;
    private final String PATH = "output\\lexicalError.txt";

    public lexicalError(){
        try {
            out = new BufferedWriter(new FileWriter(PATH));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    
}