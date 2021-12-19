/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.SymbolsTable;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private final String PATH = "src/Output/AssemblerCode_NOT_Optimized.txt";
    // Symbol Table
    private SymbolsTable symbolTable;
    // TS + TV
    private Table table;
    // List of instructions
    private ArrayList<String> assemblyInstructions;

    private int nparams;
    private Instruction previousInstr;
    private boolean isFunction;
    private boolean paramIsString;

    private ArrayList<String> variablesFromKeyboard;

    public AssemblerGenerator(SymbolsTable symbolTable, Table table) {
        //this.writer = writer;
        this.symbolTable = symbolTable;
        this.table = table;
        previousInstr = new Instruction(null, null, null, null);
        variablesFromKeyboard = new ArrayList<>();

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH, true), StandardCharsets.UTF_8));
            assemblyInstructions = new ArrayList<>();
            writeHead();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: CANNOT WRITE IN FILE");
        }

    }

    private void writeLine(String input) {
        assemblyInstructions.add(input + "\n");
    }

    // Generates the header of program
    private void writeHead() {
        writeLine(".global _start");
        writeLine(".text");
        writeLine(".data");
        // declareVariables();
    }

    private void writeBottom() {

        //Write end of program. (Exit)
        /*Opción 1: Llamar función exit.*/
        writeLine("call exit");
    }

    public void toAssembly(Instruction instruction, Instruction nextInstruction) {
        switch (instruction.getOpCode().toString()) {
            // .global main
            // 
            // Pop %rsp
            // Place:
            case "skip":
                skipInstruction(instruction);
                break;
            case "rtn":
                returnInstruction(instruction);
                break;
            // "jump X place"
            case "go_to":
                writeLine("jmp " + instruction.getDest());
                break;
            // Arithmetical expressions
            // Sume
            case "add":
                calculateSumRes(instruction, "add");
                break;
            // Substract
            case "sub":
                calculateSumRes(instruction, "sub");
                break;
            // Modul
            case "mod":
                calculateDivision(instruction, "idiv", 1);
                break;
            // Product
            case "prod":
                calculateMulu(instruction, "imul");
                break;
            // Division
            case "div":
                calculateDivision(instruction, "idiv", 2);
                break;
            // Funtion related expressions
            case "call":
                callInstruction(instruction);
                break;
            case "param":
                paramInstruction(instruction, nextInstruction);
                break;
            // Preamble expression
            case "pmb":
                pmbInstruction(instruction);
                break;
            // Copy expression
            case "copy":
                copyInstruction(instruction);
                break;
            // In order to obtain which branch is we are using an auxiliar method
            // called substract Jump

            // Lower than
            case "if_LT":
                substractJump(instruction, "jl ");
                break;
            // Lower/equals
            case "if_LE":
                substractJump(instruction, "jle ");
                break;
            // Equals
            case "if_EQ":
                substractJump(instruction, "je ");
                break;
            // Negative
            case "if_NE":
                substractJump(instruction, "jne ");
                break;
            // Greater/equals
            case "if_GE":
                substractJump(instruction, "jge ");
                break;
            // Greater
            case "if_GT":
                substractJump(instruction, "jg ");
                break;
        }
        writeLine("");
        previousInstr = instruction;
    }
    
    // Auxiliar method for the skip Instruction
    private void skipInstruction(Instruction instruction) {
        if (lastProcedure(instruction.getDest())) {
            writeLine(".global main");
        }
        // Indicates if its the return of a method
        if (instruction.getDest().contains("Return")) {
            // Liberate the used memory
            writeLine("Liberate the used memory by the previous function");

            int offset = nparams * 8;
            writeLine("add $" + offset + ", %rsp");

            if (isFunction && previousInstr.getOp1() != null) {
                writeLine("pop" + previousInstr.getOp1());
            }
        } else {
            writeLine(instruction.getDest() + ":");
        }
    }

    // Auxiliar method for return Instruction
    private void returnInstruction(Instruction instruction) {
        if (isFunction) {
            writeLine("# Moure resultat de la funció a la pila");
            writeLine("mov " + instruction.getDest() + ", %rdi");
            writeLine("mov %rdi, " + (16 + (nparams * 8)) + "(%rbp)\n");
        }

        writeLine("mov %rbp, %rsp       # Restaurar valor inicial de RSP.");
        writeLine("pop %rbp             # Restaurar valor inicial de RBP.");
        writeLine("ret");
    }

    // Auxiliar method which will be helping with the arithmetical calculations (sum and rest)
    private void calculateSumRes(Instruction instruction, String type) {
        writeLine("mov " + checkType(instruction, 1) + instruction.getOp1() + ", " + "%rax");
        writeLine("mov " + checkType(instruction, 2) + instruction.getOp2() + ", " + "%rdi");
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
//        if ((symbolTable.existe(instruction.getDestination().toString()))
//                && ((symbolTable.get(instruction.getDestination().toString()).getTipo().toString().equalsIgnoreCase("funcion"))
//                || symbolTable.get(instruction.getDestination().toString()).getTipo().toString().equalsIgnoreCase("metodo"))) {
//            writeLine("call " + instruction.getDestination());
//        } else {
//
//            if (instruction.getDestination().toString().equals("llegir_teclat")) {
//                writeLine("call scanf");
//                writeLine("pop %rbp");
//
//            } else if (instruction.getDestination().toString().equals("imprimir")) {
//                writeLine("call printf");
//                writeLine("pop %rbx");
//            } else {
//                writeLine("call imprimirLogic");
//            }
//        }
    }

    private void paramInstruction(Instruction instruction, Instruction nextInstruction) {
        if (nextInstruction.getDest() != null && nextInstruction.getDest().equals("input")) {

            if (!declarationExists(instruction.getOp1())) {   //Si no esta declarada, hacerlo.
                newIntegerGlobalVariable(instruction.getOp1(), "0");
            }

            writeLine("push %rbp");
            writeLine("mov $0, %eax");
            writeLine("movabsq $format, %rdi         # Carregar format de string.");
            writeLine("movabsq $" + instruction.getOp1() + ", %rsi         # Carregar direcció per guardar l'entrada.");

        } else if (nextInstruction.getDest() != null && nextInstruction.getDest().equals("output")) {

            if (isStringDeclaration(instruction.getOp1())) {
                writeLine("push %rbx");
                writeLine("lea " + instruction.getOp1() + "(%rip), %rdi         # Carreguem la direcció de la variable.");
                writeLine("mov  $0, %esi");
                writeLine("xor %eax, %eax       # Netetjem el registre");
            } else {
                writeLine("push %rbx");
                writeLine("lea format(%rip), %rdi         # Carreguem la direcció de la variable.");
                writeLine("mov " + instruction.getOp1() + ", %esi");
                writeLine("xor %eax, %eax       # Netetjem el registre");
            }

        } else {
            writeLine("mov " + instruction.getOp1() + ",%rdx");
            writeLine("push %rdx");
        }
    }

    private boolean declarationExists(String name) {
        for (int i = 0; i < assemblyInstructions.size() && !readLine(i).startsWith("#"); i++) {
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

    private boolean isStringDeclaration(String name) {
        for (int i = 0; i < assemblyInstructions.size(); i++) {
            if (readLine(i).contains(name + ": .asciz")) {
                return true;
            }
        }
        return false;
    }

    // Auxiliar method which indicates what kind of jump are we analyzing
    private void substractJump(Instruction instruction, String type) {
        writeLine("mov " + checkLiteral(instruction, 1) + instruction.getOp1() + ", %edx");
        writeLine("mov " + checkLiteral(instruction, 2) + instruction.getOp2() + ", %ecx");
        writeLine("cmp %ecx, %edx");
        writeLine(type + instruction.getDest());
    }

    // Auxiliar method that generates the copy Instruction
    private void copyInstruction(Instruction instruction) {
        if (instruction.isLiteralOp1()) {
            writeLine("mov $" + instruction.getOp1() + ", %rdi");
            writeLine("mov " + "%rdi, " + instruction.getDest());
        } else if (instruction.isBoolOp1()) {
            if (instruction.getOp1().equals("cert")) { //cert
                writeLine("movl $1, " + instruction.getDest());
            } else { //false
                writeLine("movl $0, " + instruction.getDest());
            }
        } else if (instruction.isStringOp1() && !isCadenaTemporalVariable(instruction.toString())) {
            writeLine("# Asignació feta a l'apartat .data");
        } else {
            writeLine("mov " + instruction.getOp1() + ", " + "%rdi");
            writeLine("mov " + "%rdi, " + instruction.getDest());
        }
    }

    // Auxiliar method to check temporal String in the variable table (copyInstruction)
    // REVISAR STRING PORQUE NO SÉ MUY BIEN DE DÓNDE SALE
    private boolean isCadenaTemporalVariable(String name) {
        for (int i = 0; i < table.getVarTable().size(); i++) {
            if (table.getVarTable().get(i).equals(name) && table.getVarTable().get(i).equals("String")) {
                return true;
            }
        }
        return false;
    }

    private void pmbInstruction(Instruction instruction) {
//        if (!lastProcedure(instruction.getDest())) {
//            writeLine("push %rbp        # Guardem el registre que utilitzarem com a apuntador de la pila.");
//            writeLine("mov %rsp, %rbp");
//        }
//
//        //Declarar parametros del procedimiento como variables.
//        SymbolTable node = symbolTable.get(instruction.getDest());
//
//        if (node != null) {
//            if (node.getTipo().toString().equalsIgnoreCase("funcion")) {
//                isFunction = true;
//            } else {
//                isFunction = false;
//            }
//        }
//
//        if (node != null) {
//            for (int i = 0; i < node.getArgumentos().size(); i++) {
//                newIntegerGlobalVariable(node.getArgumentos().get(i).getId(), "0");
//            }
//
//            nparams = node.getArgumentos().size();
//
//            int index = 0;
//
//            writeLine("# Restaurar paràmetres");
//            for (int i = 0; i < nparams * 8; i += 8) {
//                writeLine("mov " + (i + 16) + "(%rbp), %rax");
//                writeLine("mov %rax, " + node.getArgumentos().get(index).getId());
//                index++;
//            }
//        }
    }

// Auxiliar method for checking if destination is the last instruction of the
// program
    private boolean lastProcedure(String dest) {
        int x = table.getVarTable().size() - 1;
        return table.getProcTable().get(x).toString().equals(dest);
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
            }
        } else {
            if (operand.isLiteralOp2()) {
                back = "$";
            }
        }

        return back;
    }
}
