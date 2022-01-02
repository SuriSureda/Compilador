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
        id,
        digit,
        character,
        number,
        string,
        bool,
        Void, // <- comprobar
        dconst,
        op_relational,
        op_arithmetical,
        op_logical,
        op_assig,
        inst_if,
        inst_else,
        inst_elif,
        inst_while,
        inst_for,
        inst_switch,
        inst_case,
        inst_break,
        inst_default,
        inst_function,
        inst_return,
        inst_call,
        inst_main,
        id_main,
        instr_in,
        instr_out,
        spc_inc,
        spc_dec,
        spc_asginc,
        spc_asgdec,
        spc_asgdiv,
        spc_asgmul,
        lparen,
        rparen,
        lbracket,
        rbracket,
        nextinstr,
        separator,
        two_points,
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

