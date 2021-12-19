/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolsTable;

/**
 *
 * @author soyjo
 */
public class Type {

    private TYPE type;
    private SUBJACENTTYPE subType;
    
    // dtype
    private int size;
    private int lowLimit;
    private int highLimit;

    public Type(TYPE type, SUBJACENTTYPE subType, int size, int lowLimit, int highLimit){
        this.type = type;
        this.subType = subType;
        this.size = size;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public TYPE getType(){
        return this.type;
    }

    public SUBJACENTTYPE getSubType(){
        return this.subType;
    }

    public int getSize() {
        return size;
    }

    public int getLowLimit() {
        return lowLimit;
    }

    public int getHighLimit() {
        return highLimit;
    }

    // dvar, dfun, darg
    private String nameType;

    public Type(TYPE type, String nameType){
        this.type = type;
        this.nameType = nameType;
    }

    public String getTypeName(){
        return this.nameType;
    }

    // dconst
    private Object value;

    public Type(TYPE type, String nameType, Object value) {
        this.type = type;
        this.nameType = nameType;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }
    

    /* ENUMS */
    public static enum TYPE {
        idnull,
        dtype,
        dconst,
        dvar,
        dfun,
        darg
    }

    public static enum SUBJACENTTYPE {
        st_boolean,
        st_integer,
        st_string,
        st_null,
    }
}
