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
public class SymbolArithOperation extends SymbolBase{

    private String var_id;
    private Integer value;
    private boolean isConst;

    public SymbolArithOperation(String var_id) {
        super("Symbol Arithmetical Operation", 0);
        this.var_id = var_id;
        this.isConst = false;
    }

    public SymbolArithOperation(String var_id, Integer value) {
        super("Symbol Arithmetical Operation", 0);
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

    public Integer getValue(){
        return this.value;
    }
    
}
