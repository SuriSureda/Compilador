/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.Instruction;
import backend.InstructionTable;

/**
 *
 * @author soyjo
 */
// In this class we are going to find a few procedures that will highly increase
// both quality and speed of our assembler programs.
public class Optimizer {

    private InstructionTable table;
    private Instruction current, next;
    private boolean someChangeOcurred = true;

    public Optimizer(InstructionTable instructionSet) {
        this.table = instructionSet;
    }

    public void optimize() {
        while (someChangeOcurred) {
            someChangeOcurred = false;
            for (int i = 0; i < table.getTableSize() - 1; i++) {
                current = table.getInstruction(i);
                next = table.getInstruction(i + 1);
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
    }

    private void deferredAssignment(int i) {
//        if (current.getDest().equals(next.getOp1().toString()) && next.getOpCode().toString() != "param") {
//
//            if (current.getOp2().toString() == null) {
//
//                Instruction novainstruccio = new Instruction("copy", current.getOp1(), null, next.getDest(), next.getDest() + " = " + current.getOp1());
//                table.getInstructions().add(i, novainstruccio);
//                table.eraseInstruction(i + 1);
//                table.eraseInstruction(i + 1);
//                someChangeOcurred = true;
//            } else if (current.getOp2().toString() != null) {
//
//                Instruction novainstruccio = new Instruction(current.getOpCode().toString(), current.getOp1(),
//                        current.getOp2(), next.getDest(), next.getDest() + " = " + current.getOp1() + " " + current.signeOperador() + " " + current.getOp2());
//                table.addInstruction()(i, novainstruccio);
//                table.eraseInstruction(i + 1);
//                table.eraseInstruction(i + 1);
//                someChangeOcurred = true;
//            }
//
//        }
//    }
}
}
