/*
* Autor: Jose Ramon
* Fecha: 02/11
*/

package Lex;                         // PAQUETE AL QUE PERTENECE
import java_cup.runtime.Symbol;      // CLASE SYMBOL PARA LOS TOKENS DE CUP
import Sin.ParserSym;
import Lex.Token.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import Errors.*;

%%                              //INICIO DE OPCIONES
         
%class LexerCup   
%cup               
%public                         
%line                           
%column      
%full      

%init{
    try{
        out = new BufferedWriter(new FileWriter(TOKENS_PATH, true));
        out.write("--Token Data--\n");
    }catch(Exception e){
        System.out.println("Error writing Tokens : " + e);
        e.printStackTrace();
    }
%init}

%{
    public static final String TOKENS_PATH = "output\\Tokens.txt";

    public static final String TOKENS_ERROR_PATH = "output\\Error_Tokens.txt";

    private static BufferedWriter out;
    
    /**
    * closes tokesn file for a syntax error
    */
    public void closeTokensFile(int line, int column){
        try{
            out.write("Stopped processing tokens due to a syntax error on line : " + line + " and column : "+ column);
            out.close();
        }catch(Exception e){
            System.out.println("Error closing Tokens file : " + e);
            e.printStackTrace();
        }
    }
    
    public int getLine(){
        return yyline + 1;
    }

    public int getColumn(){
        return yycolumn + 1;
    }

    public void writeToken(Token token){
        try{
            out.write(token.toString());
        }catch(Exception e){
            System.out.println("Error writing Tokens : " + e);
            e.printStackTrace();
        }
    }

    private Symbol symbol(int type){
        return new Symbol(type,yyline,yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type,yyline,yycolumn,value);
    }
%}

%eof{
    try {
        writeToken(new Token(Token.Tokens.EOF,yyline,yycolumn, ""));
        out.write("\n\n-- All token data shown! --");
        out.close();
    }catch(Exception e){
        System.out.println("Error writing Tokens : " + e);
        e.printStackTrace();
    }
%eof}

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

// A CONTINUACIÓN DEFINIMOS LAS EXPRESIONES REGULARES DEL LENGUAJE

DIGIT           = [0-9]     //ok
LETTER          = [a-zA-Z]  //ok

BLANK           = (" "|\t|\r|\n)
COMMENT         = ("/*".*"*/") //OK 

ID              = (({LETTER})({LETTER}|{DIGIT})*)   // ok
NUMBER          = ("0" | [1-9]{DIGIT}*)                     // ok
STRING          = \" [^\"]* \"                              // ok
BOOL            = ("true" | "false")                        // ok

// PALABRAS RESERVADAS

// Declaraciones
DCONST          = ("const")     //ok

// Operadores

OP_ARITHMETICAL_B = ("+"|"-")
OP_ARITHMETICAL_C = ("*"|"/"|"%")
OP_RELATIONAL   = ("=="|"!="|"<"|">"|"<="|">=") //ok
OP_LOGICAL      = ("&&"|"||")   //ok
OP_LOGICAL_NOT  = ("!")
OP_ASSIG        = ("=")            //ok

// Operaciones

INST_IF         = ("if")    //ok
INST_ELSE       = ("else")  //ok
INST_ELIF       = ("elif")  //ok
    
INST_WHILE      = ("while") //ok
/*
INST_FOR        = ("for")   //ok

INST_SWITCH     = ("switch") //ok
INST_CASE       = ("case")  //ok
INST_BREAK      = ("break") //ok
INST_DEFAULT    = ("default") //ok
*/

INST_FUNCTION   = ("function") //ok
INST_RETURN     = ("return")    //ok
INST_MAIN       = ("main")      //ok    //*

// Entrada/salida

INSTR_IN        = ("read") //ok
INSTR_OUT       = ("print") //ok

// Operadores especiales

/*
SPC_INC         = ("++")     //ok   //SPC = special INC= increase
SPC_DEC         = ("--")     //ok
SPC_ASGINC      = ("+=")    //ok
SPC_ASGDEC      = ("-=")    //ok
SPC_ASGDIV      = ("/=")    //ok
SPC_ASGMUL      = ("*=")    //ok
*/

// Caracteres especiales

LPAREN          = ("(")     //ok
RPAREN          = (")")     //ok
LBRACKET        = ("{")     //ok
RBRACKET        = ("}")     //ok
NEXTINTR        = (";")     //ok
SEPARATOR       = (",")     //ok
TWO_POINTS       = (":")     //ok

%%                             
{BLANK}              {/*Ignore*/}
{COMMENT}            {/*Ignore*/}

{INST_IF}            {
                        Token token = new Token(Token.Tokens.INST_IF,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.inst_if, yytext());
                     }
{INST_ELSE}          {
                        Token token = new Token(Token.Tokens.INST_ELSE,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.inst_else, yytext());
                     }
{INST_ELIF}          {
                        Token token = new Token(Token.Tokens.INST_ELSE,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.inst_elif, yytext());
                    }   

{INST_WHILE}         {
                        Token token = new Token(Token.Tokens.INST_WHILE,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.inst_while, yytext());
                     }
/*                     
{INST_FOR}           {
                        Token token = new Token(Token.Tokens.INST_FOR,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.inst_for, yytext());
                    }

{INST_SWITCH}        {
                      Token token = new Token(Token.Tokens.INST_SWITCH,yyline,yycolumn, yytext());
                      writeToken(token);
                      return symbol(ParserSym.inst_switch, yytext());
                      }
{INST_CASE}          {
                      Token token = new Token(Token.Tokens.INST_CASE,yyline,yycolumn, yytext());
                      writeToken(token);
                      return symbol(ParserSym.inst_case, yytext());
                      }
{INST_BREAK}         {
                      Token token = new Token(Token.Tokens.INST_BREAK,yyline,yycolumn, yytext());
                      writeToken(token);
                      return symbol(ParserSym.inst_break, yytext());
                      }
{INST_DEFAULT}       {
                      Token token = new Token(Token.Tokens.INST_DEFAULT,yyline,yycolumn, yytext());
                      writeToken(token);
                      return symbol(ParserSym.inst_default, yytext());
                      }
*/

{INST_FUNCTION}      {
                     Token token = new Token(Token.Tokens.INST_FUNCTION,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.inst_function, yytext());
                     }
{INST_RETURN}        {
                     Token token = new Token(Token.Tokens.INST_RETURN,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.inst_return, yytext());
                     }
{INST_MAIN}          {
                     Token token = new Token(Token.Tokens.INST_MAIN,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.inst_main, yytext());
                     }     
//==============================================================================
{INSTR_IN}           {
                     Token token = new Token(Token.Tokens.INSTR_IN,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.instr_in, yytext());
                     }
{INSTR_OUT}          {
                     Token token = new Token(Token.Tokens.INSTR_OUT,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.instr_out, yytext());
                     }
//==============================================================================
{LPAREN}             {
                     Token token = new Token(Token.Tokens.LPAREN,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.lparen, yytext());
                     }
{RPAREN}             {
                     Token token = new Token(Token.Tokens.RPAREN,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.rparen, yytext());
                     }    
{LBRACKET}           {
                     Token token = new Token(Token.Tokens.LBRACKET,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.lbracket, yytext());
                     }
{RBRACKET}           {
                     Token token = new Token(Token.Tokens.RBRACKET,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.rbracket, yytext());
                     }
{NEXTINTR}           {
                     Token token = new Token(Token.Tokens.NEXTINSTR,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.nextinstr, yytext());
                     }
{SEPARATOR}          {
                     Token token = new Token(Token.Tokens.SEPARATOR,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.separator, yytext());
                     }
{TWO_POINTS}          {
                     Token token = new Token(Token.Tokens.TWO_POINTS,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.two_points, yytext());
                     }
//==============================================================================
{OP_ARITHMETICAL_B}    {
                        Token token = new Token(Token.Tokens.OP_ARITHMETICAL_B,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.op_arithmetical_b, yytext());
                     }
{OP_ARITHMETICAL_C} {
                        Token token = new Token(Token.Tokens.OP_ARITHMETICAL_C,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.op_arithmetical_c, yytext());
                    }
{OP_RELATIONAL}      {
                     Token token = new Token(Token.Tokens.OP_RELATIONAL,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.op_relational, yytext());
                     }
{OP_LOGICAL}         {
                     Token token = new Token(Token.Tokens.OP_LOGICAL,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.op_logical, yytext());
                     }
{OP_LOGICAL_NOT}         {
                     Token token = new Token(Token.Tokens.OP_LOGICAL_NOT,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.op_logical_not, yytext());
                     }
{OP_ASSIG}           {
                     Token token = new Token(Token.Tokens.OP_ASSIG,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.op_assig, yytext());
                     }
//==============================================================================
{BOOL}               {
                     Token token = new Token(Token.Tokens.BOOL,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.bool, yytext());
                     }
{DCONST}             {
                     Token token = new Token(Token.Tokens.DCONST,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.dconst, yytext());
                     }
//==============================================================================
{ID}                 {
                     Token token = new Token(Token.Tokens.ID,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.id, yytext());
                     }
{NUMBER}             {
                     Token token = new Token(Token.Tokens.NUMBER,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.number, yytext());
                     }
{STRING}             {
                        Token token = new Token(Token.Tokens.STRING,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.string, yytext());
                     }
//==============================================================================
//==============================================================================

/*
{SPC_INC}            {
                        Token token = new Token(Token.Tokens.SPC_INC,yyline,yycolumn, yytext());
                        writeToken(token);
                        return symbol(ParserSym.spc_inc);
                     }
{SPC_DEC}            {
                     Token token = new Token(Token.Tokens.SPC_DEC,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.spc_dec);
                     }
                     
                     
{SPC_ASGINC}         {
                     Token token = new Token(Token.Tokens.SPC_ASGINC,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.spc_asginc);
                     }
{SPC_ASGDEC}         {
                     Token token = new Token(Token.Tokens.SPC_ASGDEC,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.spc_asgdec);
                     }
{SPC_ASGDIV}         {
                     Token token = new Token(Token.Tokens.SPC_ASGDIV,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.spc_asgdiv);
                     }
{SPC_ASGMUL}         {
                     Token token = new Token(Token.Tokens.SPC_ASGMUL,yyline,yycolumn, yytext());
                     writeToken(token);
                     return symbol(ParserSym.spc_asgmul);
                     }
*/
//==============================================================================
[^]                  {
                        Token token = new Token(Token.Tokens.ERROR,yyline,yycolumn, yytext());
                        writeToken(token);
                        try{
                            throw new lexicalError("[Lexical error]:" + "[" + getLine() + ":" + getColumn() + "]" + " Unkown symbol: "+"'"+this.yytext()+"'");
                        } catch (DoesNotExistException ex) {
                            System.err.println("ERROR: " + ex.getMessage());
                        }
                        
                        return symbol(ParserSym.error);
                     }
//==============================================================================

