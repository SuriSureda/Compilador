package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SymbolsTableError extends Exception{
    private static BufferedWriter out = null;
    private final String PATH = "output\\symbolTableError.txt";

    public SymbolsTableError(String error){
        super(error);
        if (out== null){
            try {
                out = new BufferedWriter(new FileWriter(PATH));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            out.write(error);
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}


