/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import SymbolsTable.Type;

/**
 *
 * @author Antoni Payeras
 */

// Information that procedure table is using (TP)
public class Procedure {

    private String name;    // its name
    private int nv;         // variable number
    private int depth;      // subprogram from comes
    private int size;       // memory used
    private int offset;     // offset
    private Type type;      // type

    public Procedure(String name, int nv, int depth, int size, int offset, Type type) {
        this.name = name;
        this.nv = nv;
        this.depth = depth;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getNv() {
        return nv;
    }

    public void setNv(int nv) {
        this.nv = nv;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
}
