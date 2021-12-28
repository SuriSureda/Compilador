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
public class SymbolArithValue extends SymbolBase{
    
    private String var_id;
    private Object value;
    private boolean isConst;

    public SymbolArithValue(String var_id) {
        super("Symbol Arithmetical Value", 0);
        this.var_id = var_id;
        this.isConst = false;
    }

    public SymbolArithValue(String var_id, Object value) {
        super("Symbol Arithmetical Value", 0);
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

    public Object getValue(){
        return this.value;
    } 
}
