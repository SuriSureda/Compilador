/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type;

/**
 *
 * @author Antoni Payeras Munar
 */

// Information that Variable table will be using (TV)
public class Variable {

    private String name;    // variable name
    private int code;       // variable code id
    private int idParent;   // parent id
    private int offset;     // offset
    private int size;       // space occupation
    private Type type;      // type

    public Variable(String name, int code, int idParent, int offset, int tam, Type type) {
        this.name = name;
        this.code = code;
        this.idParent = idParent;
        this.offset = offset;
        this.size = tam;
        this.type = type;
    }

}
