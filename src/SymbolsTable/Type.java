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

    private String backendId = "";
    
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

    public void setBackendId(String backId) {
        this.backendId = backId;
    }

    public String getBackendId() {
        return this.backendId;
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
    private String typeName;

    public Type(TYPE type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    public Type(String backendId, TYPE type, String typeName){
        this.backendId = backendId;
        this.type = type;
        this.typeName = typeName;
    }

    public String getTypeName(){
        return this.typeName;
    }

    // dconst
    private Object value;

    public Type(TYPE type, String typeName, Object value) {
        this.type = type;
        this.typeName = typeName;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }
    

    /* ENUMS */
    public static enum TYPE {
        dnull,
        dtype,
        dconst,
        dvar,
        dfun,
        darg
    }

    public static enum SUBJACENTTYPE {
        st_boolean,
        st_number,
        st_string,
        st_null,
    }
}
