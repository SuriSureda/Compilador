/*
* Autor: Jose Ramon
* Fecha: 02/11
*/

package Lex;                         // PAQUETE AL QUE PERTENECE
import java_cup.runtime.Symbol;      // CLASE SYMBOL PARA LOS TOKENS DE CUP
import Sin.ParserSym;
import Lex.Tokens.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;

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

    public void writeToken(Tokens token){
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
        writeToken(new Tokens(Tokens.Token.EOF,yyline,yycolumn));
        out.write("-- All token data shown! --");
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

ID              = (({LETTER}{LETTER})({LETTER}|{DIGIT})*)   // ok
NUMBER          = ("0" | [1-9]{DIGIT}*)                     // ok
STRING          = \" [^\"]* \"                              // ok
BOOL            = ("true" | "false")                        // ok

// PALABRAS RESERVADAS

// Declaraciones
DCONST          = ("CONST")     //ok

// Operadores

OP_ARITHMETICAL = ("+"|"-"|"*"|"/"|"%") //ok
OP_RELATIONAL   = ("=="|"!="|"<"|">"|"<="|">=") //ok
OP_LOGICAL      = ("&&"|"||"|"!")   //ok
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
INST_CALL       = ("call")      //ok    //*
INST_MAIN       = ("main")      //ok    //*

// Entrada/salida

INSTR_IN        = ("input") //ok
INSTR_OUT       = ("output") //ok

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

%%                              //FINAL DE OPCIONES 

// COMIENZO DE REGLAS SINTÁCTICAS

// chuleta -> {writeToken(this.yytext());return symbol(ParserSym.RINTEGER, yychar, yyline, yytext());}

// PROXIMAMENTE EN CINES
{BLANK}              {/*Ignore*/}
{COMMENT}            {/*Ignore*/}

{INST_IF}            {
                        Tokens token = new Tokens(Tokens.Token.INST_IF,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.inst_if, yytext());
                     }
{INST_ELSE}          {
                        Tokens token = new Tokens(Tokens.Token.INST_ELSE,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.inst_else, yytext());
                     }
{INST_ELIF}          {
                        Tokens token = new Tokens(Tokens.Token.INST_ELSE,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.inst_elif, yytext());
                    }   

{INST_WHILE}         {
                        Tokens token = new Tokens(Tokens.Token.INST_WHILE,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.inst_while, yytext());
                     }
/*                     
{INST_FOR}           {
                        Tokens token = new Tokens(Tokens.Token.INST_FOR,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.inst_for, yytext());
                    }

{INST_SWITCH}        {
                      Tokens token = new Tokens(Tokens.Token.INST_SWITCH,yyline,yycolumn);
                      writeToken(token);
                      return symbol(ParserSym.inst_switch, yytext());
                      }
{INST_CASE}          {
                      Tokens token = new Tokens(Tokens.Token.INST_CASE,yyline,yycolumn);
                      writeToken(token);
                      return symbol(ParserSym.inst_case, yytext());
                      }
{INST_BREAK}         {
                      Tokens token = new Tokens(Tokens.Token.INST_BREAK,yyline,yycolumn);
                      writeToken(token);
                      return symbol(ParserSym.inst_break, yytext());
                      }
{INST_DEFAULT}       {
                      Tokens token = new Tokens(Tokens.Token.INST_DEFAULT,yyline,yycolumn);
                      writeToken(token);
                      return symbol(ParserSym.inst_default, yytext());
                      }
*/

{INST_FUNCTION}      {
                     Tokens token = new Tokens(Tokens.Token.INST_FUNCTION,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.inst_function, yytext());
                     }
{INST_RETURN}        {
                     Tokens token = new Tokens(Tokens.Token.INST_RETURN,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.inst_return, yytext());
                     }
{INST_CALL}          {
                     Tokens token = new Tokens(Tokens.Token.INST_CALL,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.inst_call, yytext());
                     }
{INST_MAIN}          {
                     Tokens token = new Tokens(Tokens.Token.INST_MAIN,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.inst_main, yytext());
                     }     
//==============================================================================
{INSTR_IN}           {
                     Tokens token = new Tokens(Tokens.Token.INSTR_IN,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.instr_in, yytext());
                     }
{INSTR_OUT}          {
                     Tokens token = new Tokens(Tokens.Token.INSTR_OUT,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.instr_out, yytext());
                     }
//==============================================================================
{LPAREN}             {
                     Tokens token = new Tokens(Tokens.Token.LPAREN,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.lparen, yytext());
                     }
{RPAREN}             {
                     Tokens token = new Tokens(Tokens.Token.RPAREN,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.rparen, yytext());
                     }    
{LBRACKET}           {
                     Tokens token = new Tokens(Tokens.Token.LBRACKET,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.lbracket, yytext());
                     }
{RBRACKET}           {
                     Tokens token = new Tokens(Tokens.Token.RBRACKET,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.rbracket, yytext());
                     }
{NEXTINTR}           {
                     Tokens token = new Tokens(Tokens.Token.NEXTINSTR,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.nextinstr, yytext());
                     }
{SEPARATOR}          {
                     Tokens token = new Tokens(Tokens.Token.SEPARATOR,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.separator, yytext());
                     }
{TWO_POINTS}          {
                     Tokens token = new Tokens(Tokens.Token.TWO_POINTS,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.two_points, yytext());
                     }
//==============================================================================
{OP_ARITHMETICAL}    {
                        Tokens token = new Tokens(Tokens.Token.OP_ARITHMETICAL,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.op_arithmetical, yytext());
                     }
{OP_RELATIONAL}      {
                     Tokens token = new Tokens(Tokens.Token.OP_RELATIONAL,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.op_relational, yytext());
                     }
{OP_LOGICAL}         {
                     Tokens token = new Tokens(Tokens.Token.OP_LOGICAL,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.op_logical, yytext());
                     }
{OP_ASSIG}           {
                     Tokens token = new Tokens(Tokens.Token.OP_ASSIG,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.op_assig, yytext());
                     }
//==============================================================================
{BOOL}               {
                     Tokens token = new Tokens(Tokens.Token.BOOL,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.bool, yytext());
                     }
{DCONST}             {
                     Tokens token = new Tokens(Tokens.Token.DCONST,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.dconst, yytext());
                     }
//==============================================================================
{ID}                 {
                     Tokens token = new Tokens(Tokens.Token.ID,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.id, yytext());
                     }
{NUMBER}             {
                     Tokens token = new Tokens(Tokens.Token.NUMBER,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.number, yytext());
                     }
{STRING}             {
                        Tokens token = new Tokens(Tokens.Token.STRING,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.string, yytext());
                     }
//==============================================================================
//==============================================================================

/*
{SPC_INC}            {
                        Tokens token = new Tokens(Tokens.Token.SPC_INC,yyline,yycolumn);
                        writeToken(token);
                        return symbol(ParserSym.spc_inc);
                     }
{SPC_DEC}            {
                     Tokens token = new Tokens(Tokens.Token.SPC_DEC,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.spc_dec);
                     }
                     
                     
{SPC_ASGINC}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGINC,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.spc_asginc);
                     }
{SPC_ASGDEC}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGDEC,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.spc_asgdec);
                     }
{SPC_ASGDIV}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGDIV,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.spc_asgdiv);
                     }
{SPC_ASGMUL}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGMUL,yyline,yycolumn);
                     writeToken(token);
                     return symbol(ParserSym.spc_asgmul);
                     }
*/
//==============================================================================
[^]                  {
                        Tokens token = new Tokens(Tokens.Token.ERROR,yyline,yycolumn);
                        writeToken(token);
                        System.out.println("[Lexical error]:" + "[" + getLine() + ":" + getColumn() + "]" + " Unkown symbol: "+"'"+this.yytext()+"'");
                        Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TOKENS_ERROR_PATH, true), "utf-8"));
                        w.write("[Lexical error]:" + "[" + getLine() + ":" + getColumn() + "]" + " Unkown symbol: "+"'"+this.yytext()+"'"+".\n");
                        w.close();

                        return symbol(ParserSym.error);
                     }
//==============================================================================


// ERROR -> Hay que crear un fichero con los errores...

/*
[^]                  { Writer error = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(TOKENS_ERROR_PATH, true), "utf-8"));
                       error.write(" [ERROR: Unkown symbol detected]\n LEXEME: [" + this.yytext() +"]\n AT LINE: [" + yyline +"]\n AT COLUMN:[" + yycolumn +"]\n");
                       error.close();
                       return symbol(ParserSym.ERROR, this.yytext()); // ESTA LINEA A LO MEJOR SOBRA. COMPROBAR
                     }
*/
