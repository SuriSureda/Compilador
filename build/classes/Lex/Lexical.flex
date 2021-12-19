/*
* Autor: Jose Ramon
* Fecha: 02/11
*/


// CÓDIGO DE USUARIO
/*

*/
package Lex;                  // PAQUETE AL QUE PERTENECE
import static Lex.Tokens.*;   // ENUM DE TOKENS
import java.io.*;


%%                              //INICIO DE OPCIONES

%class LexScanner               //NOMBRE DE LA CLASE
%public                         //TIPO PUBLICO
%line                           //CONTEO LINEAS
%column                         //CONTEO COLUMNAS
%char                           //CONTEO DE CARACTERES
%type Tokens


%{
    public String lexeme;
%}


/*

// A CONTINUACIÓN GENERAREMOS LOS SIMBOLOS DE LA CLASE CUP
%{
    private Symbol symbol(int type){
        return new Symbol(type,yyline,yycolumn);
    }
    private Symbol symbol(int type, Object value){
        return new Symbol(type,yyline,yycolumn,value);
    }
%}

*/

// A CONTINUACIÓN DEFINIMOS LAS EXPRESIONES REGULARES DEL LENGUAJE

DIGIT           = [0-9]
LETTER          = [a-zA-Z]

BLANK           = (\n| \t| \r| " ")
COMMENT         = ("/*".*"*/") //OK 

ID              = {LETTER}{LETTER}({LETTER}|{DIGIT}|" ")* // El id mínimo debe comenzar con dos letras 
NUMBER          = ("0" | [1-9]{DIGIT}*)                     // Enteros
STRING          = \" [^\"]* \"                              // El String irá entre comillas dobles 
BOOL            = ("true" | "false")                        // Booleans

// PALABRAS RESERVADAS

// Declaraciones
DCONST          = ("CONST") 

// Operadores

OP_ARITHMETICAL = ("+"|"-"|"*"|"/"|"%")
OP_RELATIONAL   = ("=="|"!="|"<"|">"|"<="|">=")
OP_LOGICAL      = ("&&"|"||"|"!")
OP_ASSIG        = (":=")

// Operaciones

INST_IF         = ("if")
INST_ELSE       = ("else")
INST_ELIF       = ("elif")

INST_WHILE      = ("while") 
INST_FOR        = ("for")

INST_SWITCH     = ("switch")
INST_CASE       = ("case")
INST_BREAK      = ("break")
INST_DEFAULT    = ("default")

INST_FUNCTION   = ("function")
INST_RETURN     = ("return")
INST_CALL       = ("call")          //*
INST_MAIN       = ("main")          //*

// Entrada/salida

INSTR_IN        = ("input")
INSTR_OUT       = ("output")

// Operadores especiales

SPC_INC         = ("++")        //SPC = special INC= increase
SPC_DEC         = ("--")
SPC_ASGINC      = ("+=")
SPC_ASGDEC      = ("-=")
SPC_ASGDIV      = ("/=")
SPC_ASGMUL      = ("*=")

// Caracteres especiales

LPAREN          = ("(")
RPAREN          = (")")
LBRACKET        = ("{")
RBRACKET        = ("}")
NEXTINTR        = (";")
SEPARATOR       = (",")
TWO_POINT       = (":")

%%                              //FINAL DE OPCIONES 

// COMIENZO DE REGLAS SINTÁCTICAS

// chuleta -> {return symbol(ParserSym.RINTEGER, this.yytext());}

//==============================================================================
{BLANK}              {/*Ignore*/}
{COMMENT}            {/*Ignore*/}
//==============================================================================
{INST_IF}            {lexeme = yytext(); return Tokens.inst_if;}
{INST_ELSE}          {lexeme = yytext(); return Tokens.inst_else;}
{INST_ELIF}          {lexeme = yytext(); return Tokens.inst_elif;}

{INST_WHILE}         {lexeme = yytext(); return Tokens.inst_while;}
{INST_FOR}           {lexeme = yytext(); return Tokens.inst_for;}

{INST_SWITCH}        {lexeme = yytext(); return Tokens.inst_switch;}
{INST_CASE}          {lexeme = yytext(); return Tokens.inst_case;}
{INST_BREAK}         {lexeme = yytext(); return Tokens.inst_break;}
{INST_DEFAULT}       {lexeme = yytext(); return Tokens.inst_default;}

{INST_FUNCTION}      {lexeme = yytext(); return Tokens.inst_function;}
{INST_RETURN}        {lexeme = yytext(); return Tokens.inst_return;}
{INST_CALL}          {lexeme = yytext(); return Tokens.inst_call;}      //*
{INST_MAIN}          {lexeme = yytext(); return Tokens.inst_main;}      //*

{INSTR_IN}           {lexeme = yytext(); return Tokens.instr_in;}
{INSTR_OUT}          {lexeme = yytext(); return Tokens.instr_out;}

{LPAREN}             {lexeme = yytext(); return Tokens.lparen;}
{RPAREN}             {lexeme = yytext(); return Tokens.rparen;}
{LBRACKET}           {lexeme = yytext(); return Tokens.lbracket;}
{RBRACKET}           {lexeme = yytext(); return Tokens.rbracket;}
{NEXTINTR}           {lexeme = yytext(); return Tokens.nextinstr;}
{SEPARATOR}          {lexeme = yytext(); return Tokens.separator;}
{TWO_POINT}          {lexeme = yytext(); return Tokens.two_points;}

{OP_ARITHMETICAL}    {lexeme = yytext(); return Tokens.op_arithmetical;}
{OP_RELATIONAL}      {lexeme = yytext(); return Tokens.op_relational;}
{OP_LOGICAL}         {lexeme = yytext(); return Tokens.op_logical;}
{OP_ASSIG}           {lexeme = yytext(); return Tokens.op_assig;}

{BOOL}               {lexeme = yytext(); return Tokens.bool;}
{DCONST}             {lexeme = yytext(); return Tokens.dconst;}

{DIGIT}              {lexeme = yytext(); return Tokens.digit;}
{LETTER}             {lexeme = yytext(); return Tokens.character;}

{ID}                 {lexeme = yytext(); return Tokens.id;}
{NUMBER}             {lexeme = yytext(); return Tokens.number;}
{STRING}             {lexeme = yytext(); return Tokens.string;}

{SPC_INC}            {lexeme = yytext(); return Tokens.spc_inc;}
{SPC_DEC}            {lexeme = yytext(); return Tokens.spc_dec;}
{SPC_ASGINC}         {lexeme = yytext(); return Tokens.spc_asginc;}
{SPC_ASGDEC}         {lexeme = yytext(); return Tokens.spc_asgdec;}
{SPC_ASGDIV}         {lexeme = yytext(); return Tokens.spc_asgdiv;}
{SPC_ASGMUL}         {lexeme = yytext(); return Tokens.spc_asgmul;}

[^]                  {return Tokens.ERROR;}


