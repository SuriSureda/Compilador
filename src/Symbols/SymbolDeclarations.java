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

    public SymbolDeclarations() {
        super("SymbolDeclarations", 0);
    }

    public SymbolDeclarations(Type type) {
        super("SymbolDeclarations", 0);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
}
