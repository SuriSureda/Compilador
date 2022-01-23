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

public class  Token{

    public enum Tokens {
        ID,
        DIGIT,
        CHARACTER,
        NUMBER,
        STRING,
        BOOL,
        DCONST,
        OP_RELATIONAL,
        OP_ARITHMETICAL,
        OP_LOGICAL,
        OP_ASSIG,
        INST_IF,
        INST_ELSE,
        INST_ELIF,
        INST_WHILE,
        /* INST_FOR,
        INST_SWITCH,
        INST_CASE,
        INST_BREAK,
        INST_DEFAULT, */
        INST_FUNCTION,
        INST_RETURN,
        INST_MAIN,
        INSTR_IN,
        INSTR_OUT,
       /*  SPC_INC,
        SPC_DEC,
        SPC_ASGINC,
        SPC_ASGDEC,
        SPC_ASGDIV,
        SPC_ASGMUL, */
        LPAREN,
        RPAREN,
        LBRACKET,
        RBRACKET,
        NEXTINSTR,
        SEPARATOR,
        TWO_POINTS,
        ERROR   ,
        EOF,
    }

    private Tokens id;
    private int line;
    private int column;
    private String lexeme;

    public Token(Tokens in, int line, int column, String lexeme){
        this.id = in;
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
    }

    @Override
    public String toString(){

        String result = "\t" + this.id.toString();

        result += "["+this.lexeme+"]";
        result += "("+this.line+":"+this.column+")";
        if(this.id == Tokens.NEXTINSTR || this.id == Tokens.RBRACKET || this.id == Tokens.LBRACKET){
            result += "\n\n";
        }else{
            result += " ";
        }
        
        return result;
    }
}

