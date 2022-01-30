/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type.SUBJACENTTYPE;

/*
 *
 * @author Antoni Payeras Munar
 */

// Information that Variable table will be using (TV)
public class Variable {

    private String name;    // variable name
    private int idParent;   // parent id
    private int offset;     // offset
    private int size;       // space occupation
    private SUBJACENTTYPE type;      // type
    private boolean isParam;

    public Variable(String name,int idParent, int offset, int tam, SUBJACENTTYPE type, boolean isParam) {
        this.name = name;
        this.idParent = idParent;
        this.offset = offset;
        this.size = tam;
        this.type = type;
        this.isParam = isParam;
    }

    public SUBJACENTTYPE getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getAssemblerDir() {
        if(isParam) {
            return this.offset + "(%rbp)";
        }
        // - offset
        return (-this.offset) + "(%rbp)";
    }

    @Override
    public String toString() {
        return this.name+ "\ttam : "+this.size+"\ttipus_subjacent :"+this.type+"\tidFun : "+this.idParent+"\toffset : "+getAssemblerDir();
    }
}
