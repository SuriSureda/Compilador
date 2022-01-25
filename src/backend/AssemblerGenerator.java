/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.*;
import SymbolsTable.Type.SUBJACENTTYPE;
import SymbolsTable.Type.TYPE;
import backend.Instruction.Code;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soyjo
 */
// This class provides the assembler instructions which are the equivalent
// of the program made by the user.
//
// IMPORTANT: The assembler chosen is GAS 
public class AssemblerGenerator {

    // Write the resulting code
    private BufferedWriter writer;
    private final String PATH = "output\\AssemblerCode_NOT_Optimized.s";
    private final String PATH_OPTIMIZED = "output\\AssemblerCode_Optimized.s";
    // Symbol Table
    private SymbolsTable symbolsTable;
    // TS + TV
    private Backend backend;

    private C3a_generator c3a_g;
    // List of instructions
    private ArrayList<String> assemblyInstructions;

    public AssemblerGenerator(SymbolsTable symbolTable, Backend backend, C3a_generator c3a_g) {
        //this.writer = writer;
        this.symbolsTable = symbolTable;
        this.backend = backend;
        this.c3a_g = c3a_g;
        assemblyInstructions = new ArrayList<String>();
    }

    public void generateAssembler(boolean optimized) {
        try{
            File file;
            if(optimized){
                file = new File(PATH_OPTIMIZED);
            }else{
                file = new File(PATH);
            }
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writeHead();
            ArrayList<Instruction> instructions = c3a_g.getInstructions();
            for(int i = 0; i<instructions.size();i++){
                Instruction ins = instructions.get(i);
                if(i<instructions.size()-1){
                    Instruction next = instructions.get(i+1);
                    toAssembly(ins, next);
                }else{
                    toAssembly(ins, null);
                }
            }
            writeBottom();
            for(String inst : assemblyInstructions){
                writer.write(inst);
            }
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: CANNOT WRITE IN FILE");
        } catch (IOException e) {
            System.out.println("ERROR: CANNOT CREATE ASSEMBLY FILE");
        }
    }

    private void writeLine(String input) {
        assemblyInstructions.add(input + "\n");
    }

    public void writeC3A_Comment(Instruction instruction){
        assemblyInstructions.add("# " + instruction.toString().replace("\n", "\n# ") + "\n");
    }

    // Generates the header of program
    private void writeHead() {
        writeLine(".global main");
        /* C functions declaration */
        writeLine(".extern printf, scanf, exit");
        writeLine(".data");
        declareVariables();
        writeLine(".text");
    }
    
    private void declareVariables() {
        for (int i = 0; i < backend.getVarTable().size(); i++) {

            Variable var = backend.getVarTable().get(i);

            switch (var.getType()) {
                case st_number:
                case st_boolean:
                    writeLine(var.getName() + ": .quad 0");
                    break;
                case st_string:
                    writeLine(var.getName() + ": .asciz " +"\"" + ((StrVariable) var).getValue() +"\"");
                case st_null:
                    break;
                default:
                    break;
            }
        }

        //Print formats for int and boolean.
        writeLine("format_int: .asciz \"%d\"");
        writeLine("true_label : .asciz \"true\"");
        writeLine("false_label : .asciz \"false\"");

    }

    private void writePrintBoolFunction() {
        writeLine("print_bool :");
        writeLine("cmp $0,%rdi");
        writeLine("je print_false");
        writeLine("mov $true_label, %rdi");
        writeLine("jmp print_bool_val");
        writeLine("print_false : mov $false_label, %rdi");
        writeLine("print_bool_val : xor %rax, %rax");
        writeLine("call printf");
        writeLine("ret");
    }

    private void writeBottom() {
        writeLine("# exit");
        writeLine("call exit\n");
        writeLine("\n# auxiliar functions");
        writeCMPFunctions();
        writePrintBoolFunction();
    }

    public void toAssembly(Instruction instruction, Instruction nextInstruction) {
        writeC3A_Comment(instruction);
        switch (instruction.getOpCode()) {
            // .global main
            // 
            // Pop %rsp
            // Place:
            case skip:
                skipInstruction(instruction);
                break;
            case rtn:
                returnInstruction(instruction);
                break;
            // "jump X place"
            case go_to:
                writeLine("jmp " + instruction.getDest());
                break;
            // Arithmetical expressions
            // Sume
            case add:
                calculateSumRes(instruction, "add");
                break;
            // Substract
            case sub:
                calculateSumRes(instruction, "sub");
                break;
            // Modul
            case mod:
                calculateDivision(instruction, "idiv", 1);
                break;
            // Product
            case prod:
                calculateMulu(instruction, "imul");
                break;
            // Division
            case div:
                calculateDivision(instruction, "idiv", 2);
                break;
            // Funtion related expressions
            case call:
                callInstruction(instruction);
                break;
            case param:
                paramInstruction(instruction, nextInstruction);
                break;
            // Preamble expression
            case pmb:
                pmbInstruction(instruction);
                break;
            // Copy expression
            case copy:
                copyInstruction(instruction);
                break;

            // In order to obtain which branch is we are using an auxiliar method
            // called substract Jump
            case jump_cond : 
                jumpCondInstruction(instruction);
                break;
            // Lower than
            case LT:
            // Lower/equals
            case LE:
            // Equals
            case EQ:
            // Negative
            case NE:
            // Greater/equals
            case GE:
            // Greater
            case GT:
                substractCMP(instruction, instruction.getOpCode());
                break;
            case and:
            case or:
                logicalInstruction(instruction);
                break;
            case not:
            case neg:
                unaryInstruction(instruction);
                break;
            case input:
                inputInstruction(instruction);
                break;
            case output:
                outputInstruction(instruction);
                break;
            default:
                break;
        }
        writeLine("");
    }

    private void jumpCondInstruction(Instruction instruction){
        writeLine("cmp $1,"+instruction.getOp1());
        writeLine("je "+instruction.getDest());
    }

    private void inputInstruction(Instruction instruction){
        writeLine("push %rbp");
        writeLine("xor %rax, %rax");
        writeLine("mov $format_int, %rdi");
        writeLine("leaq "+instruction.getDest()+"(%rip), %rsi");
        writeLine("call scanf");
        writeLine("pop %rbp");
    }

    private void unaryInstruction(Instruction instruction){
        writeLine("mov "+ instruction.getOp1() + ", %rdi");
        writeLine(instruction.getOpCode() + " %rdi");
        writeLine("mov %rdi, " + instruction.getDest());
    }

    private void logicalInstruction(Instruction instruction){
        // AND or OR
        writeLine("mov " + instruction.getOp1() + ", %rdi");
        writeLine("mov " + instruction.getOp2() + ", %rax");
        writeLine(instruction.getOpCode() + " %rax, %rdi");
        writeLine("mov %rdi, " + instruction.getDest());
    }

    private void outputInstruction(Instruction instruction){
        /* when we call output, op1 stores type of dest in string format */
        if(instruction.getOp1().equals(SUBJACENTTYPE.st_number.toString())){
            writeLine("mov $format_int, %rdi");
            writeLine("mov "+instruction.getDest()+", %rsi");
            writeLine("xor %rax, %rax");
            writeLine("call printf");
        }
        if(instruction.getOp1().equals(SUBJACENTTYPE.st_string.toString())){
            writeLine("mov $"+instruction.getDest()+", %rdi");
            writeLine("xor %rax, %rax");
            writeLine("call printf");
        }
        if(instruction.getOp1().equals(SUBJACENTTYPE.st_boolean.toString())){
            writeLine("mov "+instruction.getDest()+", %rdi");
            writeLine("call print_bool");
        }
        
    }
    
    // Auxiliar method for the skip Instruction
    private void skipInstruction(Instruction instruction) {
        writeLine(instruction.getDest() + ":");
    }

    // Auxiliar method for return Instruction
    private void returnInstruction(Instruction instruction) {
        // is function with return value, op1 register that stores return value
        if (instruction.getOp1() != null) {
            writeLine("# Moving function result into %rax");
            writeLine("mov " + instruction.getOp1() + ", %rax");
        }

        writeLine("mov %rbp, %rsp       # Restaurar valor inicial de RSP.");
        writeLine("pop %rbp             # Restaurar valor inicial de RBP.");
        writeLine("ret");
    }

    // Auxiliar method which will be helping with the arithmetical calculations (sum and rest)
    private void calculateSumRes(Instruction instruction, String type) {
        writeLine("mov " + checkType(instruction, 1) + instruction.getOp1() + ", " + "%rdi");
        writeLine("mov " + checkType(instruction, 2) + instruction.getOp2() + ", " + "%rax");
        writeLine(type + " %rax" + ", %rdi");
        writeLine("mov %rdi, " + instruction.getDest());
    }

    // Auxiliar method which will help with the / and % operations
    private void calculateDivision(Instruction instruction, String type, int code) {
        writeLine("mov " + checkType(instruction, 1) + instruction.getOp1() + ", " + "%rdi");
        writeLine("mov " + checkType(instruction, 2) + instruction.getOp2() + ", " + "%rax");
        writeLine(type + " %rdi");
        checkDivisionStatus(instruction, code);
    }

    // Auxiliar method to caculate if the instruction is a division or a modulus
    private void checkDivisionStatus(Instruction instruction, int code) {
        if (code == 1) {
            //Division
            writeLine("mov %rax, " + instruction.getDest());
        } else if (code == 2) {
            //modulus
            writeLine("mov %rdx, " + instruction.getDest());
        }
    }

    // Mulu calculation
    private void calculateMulu(Instruction instruction, String type) {
        writeLine("mov " + checkType(instruction, 1) + instruction.getOp1() + ", " + "%rax");
        writeLine("mov " + checkType(instruction, 2) + instruction.getOp2() + ", " + "%rdi");
        writeLine(type + " %rax" + ", %rdi");
        writeLine("mov %rdi, " + instruction.getDest());
    }

    // Call Instruction
    private void callInstruction(Instruction instruction) {
        writeLine("xor %rax, %rax   # clean return register");
        writeLine("call " + instruction.getDest());
    }

    private void paramInstruction(Instruction instruction, Instruction nextInstruction) {
        writeLine("mov " + instruction.getOp1() + ",%rdx");
        writeLine("push %rdx");
    }

    private boolean declarationExists(String name) {
        for (int i = 0; i < assemblyInstructions.size() && !readLine(i).startsWith("# "); i++) {
            if (readLine(i).contains("skip")) {
                return false;
            }
            if (readLine(i).contains(name + ":") && readLine(i).charAt(0) == name.charAt(0)) {
                return true;
            }
        }
        return false;
    }

    private String readLine(int lineNumber) {
        return assemblyInstructions.get(lineNumber);
    }
    
    private void newIntegerGlobalVariable(String name, String value) {
        for (int i = 0; i < assemblyInstructions.size(); i++) {
            if (readLine(i).contains(".data") && !declarationExists(name)) {
                writeSpecificLine(i + 1, name + ": .quad " + value + "\n");
                break;
            }
        }
    }

    public void writeSpecificLine(int lineNumber, String codeToWrite) {
        assemblyInstructions.add(lineNumber, codeToWrite);
    }

    // Auxiliar method which indicates what kind of jump are we analyzing
    private void substractCMP(Instruction instruction, Code type) {
        writeLine("mov " + checkLiteral(instruction, 2) + ", %rdi");
        writeLine("mov " + checkLiteral(instruction, 1) +  ", %rsi");
        writeLine("xor %rax, %rax # clean return value register");
        String functionLabel = getCMPFunctionLabel(type);
        writeLine("call "+functionLabel);
        writeLine("mov %rax,"+instruction.getDest()+" # get return value");
    }

    // Auxiliar method that generates the copy Instruction
    private void copyInstruction(Instruction instruction) {
        if(instruction.getOp1().contains("return")){
            writeLine("mov %rax, "+instruction.getDest());
        }
        else if (instruction.isLiteralOp1()) {
            if(instruction.isBoolOp1()){
                if (instruction.getOp1().equals("true")) { //cert
                    writeLine("movl $1, " + instruction.getDest());
                } else { //false
                    writeLine("movl $0, " + instruction.getDest());
                }
            }else{
                writeLine("mov $" + instruction.getOp1() + ", %rdi");
                writeLine("mov " + "%rdi, " + instruction.getDest());
            }
        } else {
            writeLine("mov " + instruction.getOp1() + ", " + "%rdi");
            writeLine("mov " + "%rdi, " + instruction.getDest());
        }
    }

    private void pmbInstruction(Instruction instruction) {
       if (!lastProcedure(instruction.getDest())) {
           writeLine("push %rbp        # Guardem el registre que utilitzarem com a apuntador de la pila.");
           writeLine("mov %rsp, %rbp");
       }

       //Declarar parametros del procedimiento como variables.
       String backFunId = instruction.getDest();
       String funId = backFunId.replace("PROC_", "");
       Type type = symbolsTable.get(funId);

        if (type == null || type.getType() != TYPE.dfun) {
            throw new Error("Invalid function");  
        }

        ArrayList<Expansion> params = symbolsTable.getParams(funId);

        for(Expansion param : params) {
            newIntegerGlobalVariable(param.getId(), "0");
        }

        int index = params.size() - 1;
        writeLine("# Restaurar paràmetres");
        for (int i = 0; i < params.size() * 8; i += 8) {
            writeLine("mov " + (i + 16) + "(%rbp), %rax");
            writeLine("mov %rax, " + params.get(index).getId());
            index--;
        }
    }

    // Auxiliar method for checking if destination is the last instruction of the
    // program
    private boolean lastProcedure(String dest) {
        int x = backend.getProcTable().size() - 1;
        return backend.getProcTable().get(x).toString().equals(dest);
    }

    private String checkType(Instruction operand, int type) {
        String back = "";
        if (type == 1) {
            if (operand.isIntOp1()) {
                back = "$";
            }
        } else {
            if (operand.isIntOp2()) {
                back = "$";
            }
        }
        return back;
    }

    private String checkLiteral(Instruction operand, int type) {
        String back = "";
        if (type == 1) {
            if (operand.isLiteralOp1()) {
                back = "$";
                if(operand.isBoolOp1() && operand.getOp1().equals("true")){
                    back += "1";
                }
                if(operand.isBoolOp1() && operand.getOp1().equals("false")){
                    back += "0";
                }
                
            }
            else{
                back = operand.getOp1();
            }
        } else {
            if (operand.isLiteralOp2()) {
                back = "$";
                if(operand.isBoolOp2() && operand.getOp2().equals("true")){
                    back += "1";
                }
                if(operand.isBoolOp2() && operand.getOp2().equals("false")){
                    back += "0";
                }
                
            }else{
                back = operand.getOp2();
            }
        }

        return back;
    }

    private String getCMPFunctionLabel(Code code){
        switch(code){
            case EQ:
                return "CMP_EQ";
            case GE:
                return "CMP_GE";
            case GT:
                return "CMP_GT";
            case LE:
                return "CMP_LE";
            case LT:
                return "CMP_LT";
            case NE:
                return "CMP_NE";
            default:
                return "";
        }
    }

    private void writeCMPFunctions(){
        writeEQ();
        writeNE();
        writeGT();
        writeGE();
        writeLE();
        writeLT();
    }

    private void writeLT() {
        writeLine("# boolean value assignation LT");
        writeLine("CMP_LT :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tjge CMP_LT_GE");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_LT_GE :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }

    private void writeLE() {
        writeLine("# boolean value assignation LE");
        writeLine("CMP_LE :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tjg CMP_LE_G");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_LE_G :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }

    private void writeGE() {
        writeLine("# boolean value assignation GE");
        writeLine("CMP_GE :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tjl CMP_GE_L");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_GE_L :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }

    private void writeGT() {
        writeLine("# boolean value assignation GT");
        writeLine("CMP_GT :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tjl CMP_GT_LE");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_GT_LE :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }

    private void writeNE() {
        writeLine("# boolean value assignation NE");
        writeLine("CMP_NE :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tje CMP_NE_E");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_NE_E :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }

    private void writeEQ() {
        writeLine("# boolean value assignation EQ");
        writeLine("CMP_EQ :");
        writeLine("\tcmp %rdi, %rsi");
        writeLine("\tjne CMP_EQ_NE");
        writeLine("\tmov $1, %rax");
        writeLine("\tret");
        writeLine("CMP_EQ_NE :");
        writeLine("\tmov $0, %rax");
        writeLine("\tret\n");
    }
}
