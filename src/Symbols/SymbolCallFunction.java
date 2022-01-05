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
public class SymbolCallFunction extends SymbolBase{

    private String functionId;
    private String functionBackId;
    
    public SymbolCallFunction() {
        super("Symbol Call Function", 0);
    }
    
    public String getFunctionId() {
        return this.functionId;
    }

    public String getBackendId() {
        return this.functionBackId;
    }
}
