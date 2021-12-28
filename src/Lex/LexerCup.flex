/*
* Autor: Jose Ramon
* Fecha: 02/11
*/

package Lex;                         // PAQUETE AL QUE PERTENECE
import java_cup.runtime.Symbol;      // CLASE SYMBOL PARA LOS TOKENS DE CUP
import compilador.sym;

%%                              //INICIO DE OPCIONES

%cup                            
%class LexerCup                 
%public                         
%line                           
%column                         
%full                           
%type java_cup.runtime.Symbol   

%{
    private Symbol symbol(int type){
        return new Symbol(type,yyline,yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type,yyline,yycolumn,value);
    }
%}

// A CONTINUACIÓN DEFINIMOS LAS EXPRESIONES REGULARES DEL LENGUAJE

DIGIT           = [0-9]     //ok
LETTER          = [a-zA-Z]  //ok

BLANK           = (" "|\t|\r|\n)
COMMENT         = ("/*".*"*/") //OK 

ID              = {LETTER}{LETTER}({LETTER}|{DIGIT}|" ")* // ok
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
OP_ASSIG        = (":=")            //ok

// Operaciones

INST_IF         = ("if")    //ok
INST_ELSE       = ("else")  //ok
INST_ELIF       = ("elif")  //ok
    
INST_WHILE      = ("while") //ok
INST_FOR        = ("for")   //ok

INST_SWITCH     = ("switch") //ok
INST_CASE       = ("case")  //ok
INST_BREAK      = ("break") //ok
INST_DEFAULT    = ("default") //ok

INST_FUNCTION   = ("function") //ok
INST_RETURN     = ("return")    //ok
INST_CALL       = ("call")      //ok    //*
INST_MAIN       = ("main")      //ok    //*

// Entrada/salida

INSTR_IN        = ("input") //ok
INSTR_OUT       = ("output") //ok

// Operadores especiales

SPC_INC         = ("++")     //ok   //SPC = special INC= increase
SPC_DEC         = ("--")     //ok
SPC_ASGINC      = ("+=")    //ok
SPC_ASGDEC      = ("-=")    //ok
SPC_ASGDIV      = ("/=")    //ok
SPC_ASGMUL      = ("*=")    //ok

// Caracteres especiales

LPAREN          = ("(")     //ok
RPAREN          = (")")     //ok
LBRACKET        = ("{")     //ok
RBRACKET        = ("}")     //ok
NEXTINTR        = (";")     //ok
SEPARATOR       = (",")     //ok
TWO_POINT       = (":")     //ok

%%                              //FINAL DE OPCIONES 

// COMIENZO DE REGLAS SINTÁCTICAS

// chuleta -> {writeToken(this.yytext());return symbol(ParserSym.RINTEGER, yychar, yyline, yytext());}

// PROXIMAMENTE EN CINES
{BLANK}              {/*Ignore*/}
{COMMENT}            {/*Ignore*/}

{INST_IF}            {return new Symbol(sym.inst_if, yychar, yyline, yytext());}
{INST_ELSE}          {return new Symbol(sym.inst_else, yychar, yyline, yytext());}
{INST_ELIF}          {return new Symbol(sym.inst_elif, yychar, yyline, yytext());}

{INST_WHILE}         {return new Symbol(sym.inst_while, yychar, yyline, yytext());}
{INST_FOR}           {return new Symbol(sym.inst_for, yychar, yyline, yytext());}

{INST_SWITCH}        {return new Symbol(sym.inst_switch, yychar, yyline, yytext());}
{INST_CASE}          {return new Symbol(sym.inst_case, yychar, yyline, yytext());}
{INST_BREAK}         {return new Symbol(sym.inst_break, yychar, yyline, yytext());}
{INST_DEFAULT}       {return new Symbol(sym.inst_default, yychar, yyline, yytext());}

{INST_FUNCTION}      {return new Symbol(sym.inst_function, yychar, yyline, yytext());}
{INST_RETURN}        {return new Symbol(sym.inst_return, yychar, yyline, yytext());}
{INST_CALL}          {return new Symbol(sym.inst_call, yychar, yyline, yytext());}
{INST_MAIN}          {return new Symbol(sym.inst_main, yychar, yyline, yytext());}      //*
//==============================================================================
{INSTR_IN}           {return new Symbol(sym.instr_in, yychar, yyline, yytext());}
{INSTR_OUT}          {return new Symbol(sym.instr_out, yychar, yyline, yytext());}
//==============================================================================
{LPAREN}             {return new Symbol(sym.lparen, yychar, yyline, yytext());}
{RPAREN}             {return new Symbol(sym.rparen, yychar, yyline, yytext());}    
{LBRACKET}           {return new Symbol(sym.lbracket, yychar, yyline, yytext());}
{RBRACKET}           {return new Symbol(sym.rbracket, yychar, yyline, yytext());}
{NEXTINTR}           {return new Symbol(sym.nextinstr, yychar, yyline, yytext());}
{SEPARATOR}          {return new Symbol(sym.separator, yychar, yyline, yytext());}
{TWO_POINT}          {return new Symbol(sym.two_points, yychar, yyline, yytext());}
//==============================================================================
{OP_ARITHMETICAL}    {return new Symbol(sym.op_arithmetical, yychar, yyline, yytext());}
{OP_RELATIONAL}      {return new Symbol(sym.op_relational, yychar, yyline, yytext());}
{OP_LOGICAL}         {return new Symbol(sym.op_logical, yychar, yyline, yytext());}
{OP_ASSIG}           {return new Symbol(sym.op_assig, yychar, yyline, yytext());}
//==============================================================================
{BOOL}               {return new Symbol(sym.bool, yychar, yyline, yytext());}
{DCONST}             {return new Symbol(sym.dconst, yychar, yyline, yytext());}

//==============================================================================
{ID}                 {return new Symbol(sym.id, yychar, yyline, yytext());}
{NUMBER}             {return new Symbol(sym.number, yychar, yyline, yytext());}
{STRING}             {return new Symbol(sym.string, yychar, yyline, yytext());}
//==============================================================================
//==============================================================================

{SPC_INC}            {return new Symbol(sym.spc_inc, yychar, yyline, yytext());}
{SPC_DEC}            {return new Symbol(sym.spc_dec, yychar, yyline, yytext());}
{SPC_ASGINC}         {return new Symbol(sym.spc_asginc, yychar, yyline, yytext());}
{SPC_ASGDEC}         {return new Symbol(sym.spc_asgdec, yychar, yyline, yytext());}
{SPC_ASGDIV}         {return new Symbol(sym.spc_asgdiv, yychar, yyline, yytext());}
{SPC_ASGMUL}         {return new Symbol(sym.spc_asgmul, yychar, yyline, yytext());}
//==============================================================================
[^]                  {/*return new Symbol(sym.ERROR, yychar, yyline, yytext());*/}
//==============================================================================


// ERROR -> Hay que crear un fichero con los errores...

/*
[^]                  { Writer error = new BufferedWriter( new OutputStreamWriter( new FileOutputStream("Output_with_errors.txt", true), "utf-8"));
                       error.write(" [ERROR: Unkown symbol detected]\n LEXEME: [" + this.yytext() +"]\n AT LINE: [" + yyline +"]\n AT COLUMN:[" + yycolumn +"]\n");
                       error.close();
                       return symbol(ParserSym.ERROR, this.yytext()); // ESTA LINEA A LO MEJOR SOBRA. COMPROBAR
                     }
*/
