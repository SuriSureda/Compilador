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
public class SymbolCallBody extends SymbolBase{

    private String fun_id;
    private int fun_back_id;
    private int num_params;
    
    public SymbolCallBody() {
        super("Symbol Call Body", 0);
    }
    
    public int getNumParams() {
        return this.num_params;
    }

    public String getFunId(){
        return this.fun_id;
    }

    public int getFunBackId(){
        return this.fun_back_id;
    }
}
