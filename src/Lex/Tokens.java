package Lex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author soyjo
 */

public class  Tokens{

    public enum Token {
        ID,
        DIGIT,
        CHARACTER,
        NUMBER,
        STRING,
        BOOL,
        VOID, // <- COMPROBAR
        DCONST,
        OP_RELATIONAL,
        OP_ARITHMETICAL,
        OP_LOGICAL,
        OP_ASSIG,
        INST_IF,
        INST_ELSE,
        INST_ELIF,
        INST_WHILE,
        INST_FOR,
        INST_SWITCH,
        INST_CASE,
        INST_BREAK,
        INST_DEFAULT,
        INST_FUNCTION,
        INST_RETURN,
        INST_CALL,
        INST_MAIN,
        ID_MAIN,
        INSTR_IN,
        INSTR_OUT,
        SPC_INC,
        SPC_DEC,
        SPC_ASGINC,
        SPC_ASGDEC,
        SPC_ASGDIV,
        SPC_ASGMUL,
        LPAREN,
        RPAREN,
        LBRACKET,
        RBRACKET,
        NEXTINSTR,
        SEPARATOR,
        TWO_POINTS,
        ERROR   
    }

    private Token id;
    private int line;
    private int column;

    public Tokens(Token in, int line, int column){
        this.id = in;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString(){
    
        String result = "";
        
        result = "TOKEN DETECTED: [" + this.id + "]\n";
        
        result += "AT LINE: [" + this.line + "] - AT COLUMN: [" + this.column + "]\n\r";
        
        return result;
    }
}

