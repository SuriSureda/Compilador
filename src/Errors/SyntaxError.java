package Errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class SyntaxError extends Exception{
    private static BufferedWriter out = null;
    private final String PATH = "output\\syntaxError.txt";

    public SyntaxError(ComplexSymbol s, ArrayList<String> expectedName, boolean recovered){
        String error = "Syntax Error: "+ s.value+ "found in position ("+ s.left+1+":"+ s.right+1+")";
        if (expectedName != null){
            error += "\n Token Expected: ";
            for (String token: expectedName){
                error += token + " ";
            }
        }
        System.out.print(error);

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

    public static void closeFile(){
        try {
            if(out!=null){
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
