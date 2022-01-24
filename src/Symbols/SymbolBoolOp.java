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
public class SymbolBoolOp extends SymbolBase{ 
    
    private String var_id;
    private Boolean value;
    private boolean isConst;

    public SymbolBoolOp(String var_id) {
        super("Symbol Boolean Operator", 0);
        this.var_id = var_id;
        this.isConst = false;
    }

    public SymbolBoolOp(String var_id, Boolean value) {
        super("Symbol Boolean Operator", 0);
        this.var_id = var_id;
        this.value = value;
        this.isConst = true;
    }

    public String getVarId(){
        return this.var_id;
    }

    public boolean getIsConst(){
        return this.isConst;
    }

    public Boolean getValue(){
        return this.value;
    }
}

