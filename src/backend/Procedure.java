/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type.SUBJACENTTYPE;

/**
 *
 * @author Antoni Payeras
 */

// Information that procedure table is using (TP)
public class Procedure {
    private static int NV = 0;

    private String name;    // its name
    private int nv;         // variable number
    private int nparams;
    private int size;       // memory used
    private int offset;     // offset
    private SUBJACENTTYPE type;      // type

    public Procedure(String name, int nparams, int size, int offset, SUBJACENTTYPE type) {
        this.name = name;
        this.nv = NV++;
        this.nparams = nparams;
        this.size = size;
        this.offset = offset;
        this.type = type;
    }


//        
//    @Override
//    public String toString() {
//        return "Procedure{" + "profunditat=" + depth + 
//                ", nom=" + name + ", numParametres=" + totalPar + 
//                ", tam=" + size + ", nv=" + nv + '}';
//    }

    public String getName() {
        return name;
    }


    public int getNv() {
        return nv;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
