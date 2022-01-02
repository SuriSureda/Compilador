/*
* Autor: Jose Ramon
* Fecha: 02/11
*/

package Lex;                         // PAQUETE AL QUE PERTENECE
import java_cup.runtime.Symbol;      // CLASE SYMBOL PARA LOS TOKENS DE CUP
import compilador.sym;
import Tokens.*;

%%                              //INICIO DE OPCIONES

%cup                            
%class LexerCup                 
%public                         
%line                           
%column                         
%full                           
%type java_cup.runtime.Symbol   

%{
    public static final String TOKENS_PATH = "src/output/Tokens.txt";

    public static final String TOKENS_ERROR_PATH = "src/output/Error_Tokens.txt";

    public static ArrayList<TokenLex> tokensArray = new ArrayList<TokenLex>();

        public int getLine(){
        return yyline + 1;
    }

    public int getColumn(){
        return yycolumn + 1;
    }

    public void writeTokens(){
        BufferedWriter out = new BufferedWriter(new FileWriter(TOKENS_PATH));
        out.write("--Token Data--\n");
        for(int i = 0; i<tokensArray.size();i++){
            out.write(tokensArray.get(i).toString());
        }
        out.write("-- All token data shown! --");
        out.close();
    }

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

{INST_IF}            {
                        Tokens token = new Tokens(Tokens.Token.INST_IF,yyline,yycolumn);
                        tokensArray.add(token);
                        return new Symbol(sym.inst_if, yychar, yyline, yytext());
                     }
{INST_ELSE}          {
                        Tokens token = new Tokens(Tokens.Token.INST_ELSE,yyline,yycolumn);
                        tokensArray.add(token);
                        return new Symbol(sym.inst_else, yychar, yyline, yytext());
                     }
{INST_ELIF}          {
                        Tokens token = new Tokens(Tokens.Token.INST_ELSE,yyline,yycolumn);
                        tokensArray.add(token);
                        return new Symbol(sym.inst_elif, yychar, yyline, yytext());
                    }   

{INST_WHILE}         {
                        Tokens token = new Tokens(Tokens.Token.INST_WHILE,yyline,yycolumn);
                        tokensArray.add(token);
                        return new Symbol(sym.inst_while, yychar, yyline, yytext());
                     }
{INST_FOR}           {
                        Tokens token = new Tokens(Tokens.Token.INST_FOR,yyline,yycolumn);
                        tokensArray.add(token);
                        return new Symbol(sym.inst_for, yychar, yyline, yytext());
                    }

{INST_SWITCH}        {
                      Tokens token = new Tokens(Tokens.Token.INST_SWITCH,yyline,yycolumn);
                      tokensArray.add(token);
                      return new Symbol(sym.inst_switch, yychar, yyline, yytext());
                      }
{INST_CASE}          {
                      Tokens token = new Tokens(Tokens.Token.INST_CASE,yyline,yycolumn);
                      tokensArray.add(token);
                      return new Symbol(sym.inst_case, yychar, yyline, yytext());
                      }
{INST_BREAK}         {
                      Tokens token = new Tokens(Tokens.Token.INST_BREAK,yyline,yycolumn);
                      tokensArray.add(token);
                      return new Symbol(sym.inst_break, yychar, yyline, yytext());
                      }
{INST_DEFAULT}       {
                      Tokens token = new Tokens(Tokens.Token.INST_DEFAULT,yyline,yycolumn);
                      tokensArray.add(token);
                      return new Symbol(sym.inst_default, yychar, yyline, yytext());
                      }

{INST_FUNCTION}      {
                     Tokens token = new Tokens(Tokens.Token.INST_FUNCTION,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.inst_function, yychar, yyline, yytext());
                     }
{INST_RETURN}        {
                     Tokens token = new Tokens(Tokens.Token.INST_RETURN,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.inst_return, yychar, yyline, yytext());
                     }
{INST_CALL}          {
                     Tokens token = new Tokens(Tokens.Token.INST_CALL,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.inst_call, yychar, yyline, yytext());
                     }
{INST_MAIN}          {
                     Tokens token = new Tokens(Tokens.Token.INST_MAIN,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.inst_main, yychar, yyline, yytext());
                     }      //*
//==============================================================================
{INSTR_IN}           {
                     Tokens token = new Tokens(Tokens.Token.INSTR_IN,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.instr_in, yychar, yyline, yytext());}
{INSTR_OUT}          {
                     Tokens token = new Tokens(Tokens.Token.INSTR_OUT,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.instr_out, yychar, yyline, yytext());
                     }
//==============================================================================
{LPAREN}             {
                     Tokens token = new Tokens(Tokens.Token.LPAREN,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.lparen, yychar, yyline, yytext());
                     }
{RPAREN}             {
                     Tokens token = new Tokens(Tokens.Token.RPAREN,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.rparen, yychar, yyline, yytext());
                     }    
{LBRACKET}           {
                     Tokens token = new Tokens(Tokens.Token.LBRACKET,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.lbracket, yychar, yyline, yytext());
                     }
{RBRACKET}           {
                     Tokens token = new Tokens(Tokens.Token.RBRACKET,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.rbracket, yychar, yyline, yytext());
                     }
{NEXTINTR}           {
                     Tokens token = new Tokens(Tokens.Token.NEXTINTR,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.nextinstr, yychar, yyline, yytext());
                     }
{SEPARATOR}          {
                     Tokens token = new Tokens(Tokens.Token.SEPARATOR,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.separator, yychar, yyline, yytext());}
{TWO_POINT}          {
                     Tokens token = new Tokens(Tokens.Token.TWO_POINT,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.two_points, yychar, yyline, yytext());}
//==============================================================================
{OP_ARITHMETICAL}    {
                     Tokens token = new Tokens(Tokens.Token.OP_ARITHMETICAL,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.op_arithmetical, yychar, yyline, yytext());
                     }
{OP_RELATIONAL}      {
                     Tokens token = new Tokens(Tokens.Token.OP_RELATIONAL,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.op_relational, yychar, yyline, yytext());}
{OP_LOGICAL}         {
                     Tokens token = new Tokens(Tokens.Token.OP_LOGICAL,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.op_logical, yychar, yyline, yytext());}
{OP_ASSIG}           {return new Symbol(sym.op_assig, yychar, yyline, yytext());}
//==============================================================================
{BOOL}               {
                     Tokens token = new Tokens(Tokens.Token.BOOL,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.bool, yychar, yyline, yytext());
                     }
{DCONST}             {
                     Tokens token = new Tokens(Tokens.Token.DCONST,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.dconst, yychar, yyline, yytext());}

//==============================================================================
{ID}                 {
                     Tokens token = new Tokens(Tokens.Token.ID,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.id, yychar, yyline, yytext());
                     }
{NUMBER}             {
                     Tokens token = new Tokens(Tokens.Token.NUMBER,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.number, yychar, yyline, yytext());
                     }
{STRING}             {
                     Tokens token = new Tokens(Tokens.Token.STRING,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.string, yychar, yyline, yytext());
                     }
//==============================================================================
//==============================================================================

{SPC_INC}            {
                     Tokens token = new Tokens(Tokens.Token.SPC_INC,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.spc_inc, yychar, yyline, yytext());
                     }
{SPC_DEC}            {
                     Tokens token = new Tokens(Tokens.Token.SPC_DEC,yyline,yycolumn);
                     tokensArray.add(token);return new Symbol(sym.spc_inc, yychar, yyline, yytext());
                     return new Symbol(sym.spc_dec, yychar, yyline, yytext());}
                     }
                     
{SPC_ASGINC}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGINC,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.spc_asginc, yychar, yyline, yytext());
                     }
{SPC_ASGDEC}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGDEC,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.spc_asgdec, yychar, yyline, yytext());
                     }
{SPC_ASGDIV}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGDIV,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.spc_asgdiv, yychar, yyline, yytext());
                     }
{SPC_ASGMUL}         {
                     Tokens token = new Tokens(Tokens.Token.SPC_ASGMUL,yyline,yycolumn);
                     tokensArray.add(token);
                     return new Symbol(sym.spc_asgmul, yychar, yyline, yytext());
                     }
//==============================================================================
[^]                  {
                     Tokens token = new Tokens(Tokens.Token.ERROR,yyline,yycolumn);
                     tokensArray.add(token);

                     Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TOKENS_ERROR_PATH, true), "utf-8"));
                     w.write("[Lexical error]:" + "[" + getLine() + ":" + getColumn() + "]" + " Unkown symbol: "+"'"+this.yytext()+"'"+".\n");
                     w.close();

                     return new Symbol(sym.ERROR, yychar, yyline, yytext());
                     }
//==============================================================================


// ERROR -> Hay que crear un fichero con los errores...

/*
[^]                  { Writer error = new BufferedWriter( new OutputStreamWriter( new FileOutputStream("Output_with_errors.txt", true), "utf-8"));
                       error.write(" [ERROR: Unkown symbol detected]\n LEXEME: [" + this.yytext() +"]\n AT LINE: [" + yyline +"]\n AT COLUMN:[" + yycolumn +"]\n");
                       error.close();
                       return symbol(ParserSym.ERROR, this.yytext()); // ESTA LINEA A LO MEJOR SOBRA. COMPROBAR
                     }
*/
