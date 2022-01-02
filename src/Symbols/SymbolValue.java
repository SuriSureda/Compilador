package Symbols;

import SymbolsTable.Type.SUBJACENTTYPE;
import SymbolsTable.Type.TYPE;

public class SymbolValue extends SymbolBase {

	private TYPE type;
	private SUBJACENTTYPE subType;

	private String nameType;
	public boolean isConstant;
	private Object value;

	private String var_id;

	private boolean isString;
	private int stringSize;


	public SymbolValue() {
		super("value", 0);
		this.type = TYPE.dnull;
		this.subType = SUBJACENTTYPE.st_null;
		this.isConstant = false;
	}


	public SymbolValue(String var_id, SUBJACENTTYPE subType) {
		super("value", 0);
		this.type = TYPE.dnull;
		this.subType = subType;
		this.isConstant = false;
	}

	// Literall or constant
	public SymbolValue(String var_id, SUBJACENTTYPE subType, Object value) {
		super("value", 0);
		this.var_id = var_id;
		this.type = TYPE.dnull;
		this.subType = subType;
		this.isConstant = true;
		this.value = value;
	}

	public SymbolValue(String var_id, TYPE type, String nameType) {
		super("value", 0);
		this.var_id = var_id;
		this.type = type;
		this.nameType = nameType;
		this.isConstant = false;
	}

	public SymbolValue(String var_id, TYPE type, String nameType, Object value) {
		super("value", 0);
		this.var_id = var_id;
		this.type = type;
		this.nameType = nameType;
		this.isConstant = true;
		this.value = value;
	}

	public TYPE getType() {
		return this.type;
	}

	public String getVarId(){
		return this.var_id;
	}

	public SUBJACENTTYPE getSubType() {
		return this.subType;
	}

	public String getNameType() {
		return this.nameType;
	}


	public boolean getIsConst() {
		return this.isConstant;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean getIsString(){
		return this.isString;
	}

	public int getStringSize(){
		return this.stringSize;
	}

	// on set string size, we set isString to true
	public void setStringSize(int size) {
		this.stringSize = size;
		this.isString = true;
	}

}