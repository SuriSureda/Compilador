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

    // other variables used
    private int variableNumber;
//    private int labelNumber;
//
//    private String temporalID = null;
//
//    private ArrayList<String> operands = new ArrayList<>();
    private ArrayList<Instruction> instructions;

//    private String currentSubprogram;
//    private String currentType = "";
//    private int offset = 0;
//    private boolean function = false;

    // Writer to save information
    private BufferedWriter writer;

    public C3a_generator(Backend backend) {
        variableNumber = 0;
        this.instructions = new ArrayList<Instruction>();
    }

    // Add a new instruction
    public void generateC3aInstr(Code opCode, String op1, String op2, String dest) {
        instructions.add(new Instruction(opCode, op1, op2, dest));
    }

    public void generateC3aInstr(int index, Code opCode, String op1, String op2, String dest) {
        instructions.add(index, new Instruction(opCode, op1, op2, dest));
    }

    public void savec3aInFile() {
        String result = "-----------------------------------------------\n"
                + "---------------- C3@ code list ----------------\n"
                + "-----------------------------------------------\n";
        for (int i = 0; i < instructions.size(); i++) {
            result += instructions.get(i) + "\n\n";
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
