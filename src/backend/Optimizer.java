/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.ArrayList;

import backend.Instruction;
import backend.InstructionTable;

/**
 *
 * @author soyjo
 */
// In this class we are going to find a few procedures that will highly increase
// both quality and speed of our assembler programs.
public class Optimizer {

    private ArrayList<Instruction> instructionSet;
    private Instruction current, next;
    private boolean someChangeOcurred = true;

    public Optimizer(ArrayList<Instruction> instructionSet) {
        this.instructionSet = instructionSet;
    }

    public ArrayList<Instruction> optimize() {
        while (someChangeOcurred) {
            someChangeOcurred = false;
            for (int i = 0; i < instructionSet.size() - 1; i++) {
                current = instructionSet.get(i);
                next = instructionSet.get(i + 1);
                String operator = current.getOpCode().toString();

                switch (operator) {
                    case ("add"):
                    case ("sub"):
                    case ("mod"):
                    case ("div"):
                    case ("copy"):
                    case ("prod"):
                        deferredAssignment(i);
                }
            }
        }

        return this.instructionSet;
    }

    private void deferredAssignment(int i) {
        if (current.getDest().equals(next.getOp1()) && next.getOpCode().toString() != "param") {

            if (current.getOp2() == null) {
                Instruction novainstruccio = new Instruction(Instruction.Code.copy, current.getOp1(),  next.getDest(), next.getDest() + " = " + current.getOp1());
                instructionSet.add(i, novainstruccio);
                instructionSet.remove(i + 1);
                instructionSet.remove(i + 1);
                someChangeOcurred = true;
            }
            if (current.getOp2() != null) {
                Instruction novainstruccio = new Instruction(current.getOpCode(), current.getOp1(), current.getOp2(), next.getDest() + " = " + current.getOp1() + " " + current.signeOperador() + " " + current.getOp2());
                instructionSet.add(i, novainstruccio);
                instructionSet.remove(i + 1);
                instructionSet.remove(i + 1);
                someChangeOcurred = true;
            }

        }
    }
}

