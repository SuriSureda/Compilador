/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type;
import SymbolsTable.Type.SUBJACENTTYPE;
import java.io.BufferedWriter;
import java.io.File;
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
    private static final String PATH = "output\\Backend_Tables.txt";
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
    
    private static int tmp_n = 0;

    public Backend() {
        this.varTable = new ArrayList<>();
        this.procTable = new ArrayList<>();
        this.procTable.add(new Procedure("main", 0, 0, 0, 0, null));
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
        name = "@" + name + "_" + idParent;
        // We add the variable into the table
        varTable.add(new Variable(name, code, idParent, offset, size, type));

        return name;
    }

    // STRING VARIABLE
    public String addStrVar(String name, int code, int idParent, int offset, int size, String value) {
        // We add '@' for easier data manipulation
        name = "@" + name + "_" + idParent;
        // We add the variable into the table
        varTable.add(new StrVariable(name, code, idParent, offset, size, value));

        return name;
    }

    public String addTempVar(int code, int idParent, int offset, int size, SUBJACENTTYPE type) {
        // We add '@' for easier data manipulation
        String name = "@T_"+tmp_n;
        tmp_n++;
        // We add the variable into the table
        varTable.add(new Variable(name, code, idParent, offset, size, type));

        return name;
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
        
        return label;
    }

    public void storeTables() {
        String result = "---------------------------------------------\n"
                + "---------------- TABLES INFO ----------------\n"
                + "---------------------------------------------\n";
        
        result += "Variables:\n";
        for (int i = 0; i < varTable.size(); i++) {
            result += varTable.get(i) + "\n";
        }

        result += "\nProcedures:\n";
        for (int i = 0; i < procTable.size(); i++) {
            result += procTable.get(i) + "\n";
        }

        result += "\nLabels:\n";
        for (int i = 0; i < labelTable.size(); i++) {
            result += labelTable.get(i) + "\n";
        }

        try {
            // File Writter
            File file = new File(PATH);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.close();
        } catch (IOException ex) {
            System.out.println(" ERROR WRITING BACKEND TABLES");
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
