/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

/**
 *
 * @author soyjo
 */
public class SymbolPosNeg extends SymbolBase{
    

    private boolean negative;

    public SymbolPosNeg(boolean negative) {
        super("Symbol Positive/Negative ", 0);
        this.negative = negative;
    }

    public boolean isNegative() {
        return this.negative;
    }
    
}
