/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolsTable;

import SymbolsTable.Type.TYPE;

/**
 *
 * @author soyjo
 */

/*
* Description table will be using an arrayList
*/
public class Description {
    private String id;
    private Type type;
    private int scope;
    private int first = -1;

    public Description(String id, Type type, int scope) {
        this.id = id;
        this.type = type;
        this.scope = scope;
    }

    public Description(Expansion exp){
        this.id = exp.getId();
        this.type = exp.getType();
        this.scope = exp.getScope();
    }

    public String getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public int getScope() {
        return this.scope;
    }

    public int getFirst() {
        return this.first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    @Override
    public String toString() {
        String result = "ID : " + this.id + " MTYPE: " + this.type.getType() + " SUBJACENT TYPE: " + this.type.getSubType() + " SCOPE: " + this.scope;
        if(type.getType() == TYPE.dfun){
            result +=" FIRST: "+this.first;
        }
        return result; 
    }
}