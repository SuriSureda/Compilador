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


public class Expansion {

    private Type type;
    private String id;
    private int scope;

    public Expansion(Type type, String id, int scope){
        this.type = type;
        this.id = id;
        this.scope = scope;
    }

    public Expansion(Description description) {
        this.type = description.getType();
        this.id = description.getId();
        this.scope = description.getScope();
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

    @Override
    public String toString() {
        return "ID : " + this.id + " MTYPE: " + this.type.getType() + " SUBTYPE: " + this.type.getSubType() + " SCOPE: " + this.scope;
    }
}
