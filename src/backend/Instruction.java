/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Antoni Payeras
 */
// How the c3@ data is structured
public class Instruction {
    
    // All posible operations that our c3@ will be able to use
    public static enum Code {
        copy,
        add,
        sub,
        prod,
        div,
        mod,
        neg,
        and,
        or,
        not,
        skip,
        go_to, // UN-conditional jump
        if_LT, // <    jl
        if_LE, // <=   jle
        if_EQ, // =    je
        if_NE, // !=   jne
        if_GE, // >=   jge
        if_GT, // >    jg
        pmb,
        call,
        param,
        rtn,
        input, // System in
        output, // System print
    }

    public Code opCode;
    public String op1, op2, dest;

    public Instruction(Code opCode, String op1, String op2, String dest) {
        this.opCode = opCode;
        this.op1 = op1;
        this.op2 = op2;
        this.dest = dest;
    }

    public Code getOpCode() {
        return opCode;
    }

    public void setOpCode(Code opCode) {
        this.opCode = opCode;
    }
    
    public String signeOperador() {
        switch (this.opCode) {
            case add:
                return "+";
            case div:
                return "%";
            case sub:
                return "-";
            case mod:
                return "%";
            case prod:
                return "*";
            case if_LT:
                return "<";
            case if_LE:
                return "<=";
            case if_EQ:
                return "=";
            case if_NE:
                return "!=";
            case if_GE:
                return ">=";
            case if_GT:
                return ">";
        }
        return null;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public boolean isLiteral(String n) {
        return n.charAt(0) == '"' || n.matches("[0-9]+");
    }
    
    public boolean isLiteralOp1() {
        return op1.charAt(0) == '"' || op1.matches("[0-9]+");
    }
    
    public boolean isLiteralOp2() {
        return op2.charAt(0) == '"' || op2.matches("[0-9]+");
    }

    public boolean isInt(String n) {
        return n.matches("[0-9]+");
    }
    
    public boolean isIntOp1(){
        return op1.matches("[0-9]+");
    }
    
    public boolean isIntOp2(){
        return op2.matches("[0-9]+");        
    }

    public boolean isBoolean(String n) {
        return n.equals("true") || n.equals("false");
    }
    
    public boolean isBoolOp1(){
        return op1.matches("true") || op1.matches("false");
    }
    
    public boolean isString(String n) {
        return !isBoolean(n) && !isInt(n);
    }
    
    public boolean isStringOp1(){
        return !isIntOp1() && !isBoolOp1();
    }

}
