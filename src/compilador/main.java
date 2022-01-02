/*
 * Last update: 21-11
 */
package compilador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Ramón Muñoz
 * 
 **/
public class main {
    
    // Lexical JFLEX analyzer
    final static String JFLEX = "Lexical.flex";
    
    // Lexical part of CUP
    final static String CUP_FLEX = "LexerCup.flex";
    
    // Sintax body of CUP
    final static String CUP = "Sintax.cup";
    
    // CUP symbols
    final static String SYMBOLS = "sym.java";
    
    // Sintax CUP generated
    /*
    *   Note: the name will be the same as the body previously defined
    */ 
    final static String SINTAX = "Sintax.java"; 
    
    // Txt file that will be analyzed
    final static String INPUT = "input.txt";
    
    // Menu scanner
    private static Scanner scanner;
    
    // Menu options
    private final static int LEX = 1;
    private final static int SIN = 2;
    private final static int SEM = 3;
    private final static int ASSEM = 4;
    private final static int OPT = 5;
    
    public static void main(String[] args) throws Exception {
        scanner = new java.util.Scanner(System.in);
        
        int menuOption = -1;
        
        while(menuOption != 0){
            menu();
            menuOption = scanner.nextInt();
            
            switch(menuOption){
                case LEX:
                    System.out.println("Starting Lex process!! ");
                    generate();
                    break;
                case SIN:
                    break;
                case SEM:
                    break;
                case ASSEM:
                    break;
                case OPT:
                    break;
                default:
                    System.out.println(" Wrong option chosen. Please try again.");
            }
            
        }
        
        
        
        
//        String path = "C:/Users/soyjo/OneDrive/Documentos/NetBeansProjects/Compilador/src/compilador/Lexical.flex";
//        generateLexer(path);
    }
    private static void generate(){
//        File archivo = new File(path);
//        JFlex.Main.generate(archivo);

/*
*   Getting the paths of the files needed (jflex, cup files).
*/
        String path1 = setAbsolutePath(JFLEX,"Lex");
        String path2 = setAbsolutePath(CUP_FLEX,"Lex");
        String [] paths = {"-parser","Sintax",setAbsolutePath(CUP,"Sin")};
        File file;
        
        System.out.println("*** Generating LexScanner.java ***");
        file = new File(path1);
        JFlex.Main.generate(file);
        
        System.out.println("*** Generatinig LexerCup.java ***");
        file = new File(path2);
        JFlex.Main.generate(file);
        
        System.out.println("*** Generating Sintax.cup");
        try {
            java_cup.Main.main(paths);
        } catch (IOException ex) {
            System.out.println(" ERROR SINTAX.CUP: I/0");
        } catch (Exception ex) {
            System.out.println(" ERROR SINTAX.CUP: LANG");
        }
/*
 *  Now we will check if the file was already created, if so, we will delete it      
*/        
        Path SymPath = Paths.get(setAbsolutePath(SYMBOLS,"compilador"));
        if (Files.exists(SymPath)){
            try {
                Files.delete(SymPath);
            } catch (IOException ex) {
                System.out.println(" ERROR: CANT DELETE FILE");
            }
        }
/*
 * Moving the created file
*/
        try {
            Files.move(Paths.get(setSimplePath(SYMBOLS)),
                    Paths.get(setAbsolutePath(SYMBOLS,"compilador")));
        } catch (IOException ex) {
            System.out.println("ERROR: CANT MOVE THE FILE");;
        }
/*
 *  Now we will check if the file was already created, if so, we will delete it      
*/         
        Path SintacticPath = Paths.get(setAbsolutePath(SINTAX,"compilador"));
        if (Files.exists(SintacticPath)){
            try {
                Files.delete(SintacticPath);
            } catch (IOException ex) {
                System.out.println(" ERROR: CANT DELETE FILE");
            }
        }
/*
 * Moving the created file
*/        
        try {
            Files.move(Paths.get(setSimplePath(SINTAX)),
                    Paths.get(setAbsolutePath(SINTAX,"compilador")));
        } catch (IOException ex) {
            System.out.println("ERROR: CANT MOVE THE FILE");;
        }        
    }
    
    private static String setSimplePath(String name){
        File file = new File(name);
        Path relativePath = Paths.get("");
        String newPath = "";
        newPath = relativePath.toAbsolutePath().toString()
                + File.separator + file.getName();
        return newPath;
    }
    
    private static String setAbsolutePath(String name, String path){
        File file = new File(name);
        Path relativePath = Paths.get("");
        String newPath = "";
        newPath = relativePath.toAbsolutePath().toString()
                + File.separator + "src" + File.separator
                + "" + path + "" + File.separator + file.getName();
        return newPath;
    }
    
    private static void menu(){
        System.out.println(" ----------------- Compiler Menu -----------------");
        System.out.println(" 1 - Lexical analysis");
        System.out.println(" 2 - Sintactic analysis");
        System.out.println(" 3 - Semantic analysis");               // Not implemented
        System.out.println(" 4 - Assembler code generator");        // Not implemented
        System.out.println(" 5 - Optimization code generator");     // Not implemented
        System.out.println(" -------------------------------------------------");
        System.out.print  (" Choose an option: ");
    }

        private static void executeLex(){
        
        // Remove all output files from other executions
        
        String filenameSourceCode = "src/compilador/input.txt";
        
        try {
            LexScanner scanner = new LexScanner(new FileReader(filenameSourceCode));
            // writeTokens();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
