/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

import SymbolsTable.Type;

/**
 *
 * @author soyjo
 */
public class SymbolDeclarations extends SymbolBase{
    
    private Type type;
    private String var_id;

    public SymbolDeclarations() {
        super("SymbolDeclarations", 0);
    }

    public SymbolDeclarations(String var_id, Type type) {
        super("SymbolDeclarations", 0);
        this.type = type;
        this.var_id = var_id;
    }

    public Type getType() {
        return this.type;
    }

    public String getVarId() {
        return this.var_id;
    }
}
