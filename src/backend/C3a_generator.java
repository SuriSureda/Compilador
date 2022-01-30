/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.Instruction.Code;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soyjo
 */
// NOTE: This class will generate the c3@ code which will be used 
//       in the future to build the assembler code
public class C3a_generator {

    // txt folder where we'll find the data
    private static final String PATH = "output\\c3_code.txt";
    private static final String PATH_OPTIMIZED = "output\\c3_code_optimized.txt";

    // other variables used
    private int variableNumber;
    private ArrayList<Instruction> instructions;

    // private Optimizer optimizer; NOT IMPLEMENTED 

    // Writer to save information
    private BufferedWriter writer;

    public C3a_generator(Backend backend) {
        variableNumber = 0;
        this.instructions = new ArrayList<Instruction>();
    }

    /* NOT IMPLEMENTED 
      public void optimize() {
        // optimizer = new Optimizer(instructions);
        // this.instructions = optimizer.optimize();
    } */

    // Add a new instruction
    public void generateC3aInstr(Code opCode, String op1, String op2, String dest) {

        Instruction inst = new Instruction(opCode, op1, op2, dest);
        instructions.add(inst);
    }

    // public void generateC3aInstr(int index, Code opCode, String op1, String op2, String dest) {
    //     instructions.add(index, new Instruction(opCode, op1, op2, dest));
    // }

    public void savec3aInFile(boolean optimized) {
        String result = "-----------------------------------------------\n"
                + "---------------- C3@ code list"
                + (optimized ? "optimized" : "")
                +" ----------------\n"
                + "-----------------------------------------------\n";
        for (int i = 0; i < instructions.size(); i++) {
            result += instructions.get(i) + "\n\n";
        }
        try {
            // File Writter
            File file;
            if(optimized){
                file = new File(PATH_OPTIMIZED);
            }else{
                file = new File(PATH);
            }
             
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.close();
        } catch (IOException ex) {
            System.out.println("ERROR WRITING C3@");
            Logger.getLogger(C3a_generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getVariableNumber() {
        return variableNumber;
    }

    public void setVariableNumber(int variableNumber) {
        this.variableNumber = variableNumber;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
}
