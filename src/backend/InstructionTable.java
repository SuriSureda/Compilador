/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.Instruction.Code;
import java.util.ArrayList;

/**
 *
 * @author soyjo
 */
public class InstructionTable {
    
    private ArrayList<Instruction> table;

    public InstructionTable(ArrayList<Instruction> table) {
        this.table = table;
    }
    
    // public Code opCode;
    // public String op1, op2, dest;
    public void addInstruction(Code opCode, String op1, String op2, String dest){
        table.add(new Instruction(opCode, op1, op2, dest));
    }
    
    public void eraseInstruction(int i){
        table.remove(i);
    }

    public ArrayList<Instruction> getTable() {
        return table;
    }

    public void setTable(ArrayList<Instruction> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "InstructionTable{" + "table=" + table + '}';
    }
    
}
