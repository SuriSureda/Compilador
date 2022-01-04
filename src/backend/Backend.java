/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type;
import SymbolsTable.Type.SUBJACENTTYPE;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 34630
 */
public class Backend {
    private static final String PATH = "src/Output/Tables.txt";
    // TV
    private ArrayList<Variable> varTable;
    // TP
    private ArrayList<Procedure> procTable;
    // TE
    private ArrayList<Label> labelTable;
    
    // Constants to calculate the memory usage          --- A lo mejor van en la clase GENERATOR
    public static final int BYTES_INT = 4;
    public static final int BYTES_CHAR = 2;
    public static final int BYTES_BOOL = 2;
    


    public Backend() {
        this.varTable = new ArrayList<>();
        this.procTable = new ArrayList<>();
        this.labelTable = new ArrayList<>();
    }

    // Adding a new VARIABLE into the table
//    private String name;    // variable name
//    private int code;       // variable code id
//    private int idParent;   // parent id
//    private int offset;     // offset
//    private int size;       // space occupation
//    private Type type;      // type
    public String addVar(String name, int code, int idParent, int offset, int size, SUBJACENTTYPE type) {
        // We add '@' for easier data manipulation
        name = "@" + name;
        // We add the variable into the table
        varTable.add(new Variable(name, code, idParent, offset, size, type));

        return name;
    }

    public String addTempVar(int code, int idParent, int offset, int size, SUBJACENTTYPE type) {
        // We add '@' for easier data manipulation
        String name = "@TEMPORAL_VARIABLE";
        // We add the variable into the table
        varTable.add(new Variable(name, code, idParent, offset, size, type));

        return "@TEMPORAL_VARIABLE";
    }

    // Adding a new PROCEDURE into the table
//    private String name;    // its name
//    private int nv;         // variable number
//    private int depth;      // subprogram from comes
//    private int size;       // memory used
//    private int offset;     // offset
//    private Type type;      // type
    public String addProc(String name, int nv, int depth, int size, int offset, Type type) {
        // We add the new procedure  
        procTable.add(new Procedure("PROC_" + name, nv, depth, size, offset, type));
        
        return name;
    }
    
    public String addLabel(){
        String label = "LABEL_" + labelTable.size();
        
        labelTable.add(new Label(label));
        
        return "" + (labelTable.size() - 1);
    }

    public void storeTables(String name) {
        String result = "---------------------------------------------\n"
                + "---------------- TABLES INFO ----------------\n"
                + "---------------------------------------------\n";

        for (int i = 0; i < varTable.size(); i++) {
            result += "ID: " + i + " => " + varTable.get(i) + "\n";
        }

        result += "\nProcedures:\n";
        for (int i = 0; i < procTable.size(); i++) {
            result += "ID: " + i + " => " + procTable.get(i) + "\n";
        }

        result += "\nLabels:\n";
        for (int i = 0; i < labelTable.size(); i++) {
            result += "ID: " + i + " => " + labelTable.get(i) + "\n";
        }

        try {
            // File Writter
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH));
            writer.write(result);
            writer.close();
        } catch (IOException ex) {
            System.out.println(" ERROR WRITING PROCES");
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Variable> getVarTable() {
        return varTable;
    }

    public ArrayList<Procedure> getProcTable() {
        return procTable;
    }
    
    public int getLastProcedureId(){
        return procTable.get(procTable.size() - 1).getNv();
    }
    public int getActualProcedure(){
        return procTable.size() - 1;
    }


}
