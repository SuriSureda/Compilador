package Symbols;

import SymbolsTable.Type.SUBJACENTTYPE;
import SymbolsTable.Type.TYPE;

public class SymbolValue extends SymbolBase {

	private TYPE type;
	private SUBJACENTTYPE subType;

	private String nameType;
	public boolean isConstant;
	private Object value;


	public SymbolValue() {
		super("value", 0);
		this.type = TYPE.dnull;
		this.subType = SUBJACENTTYPE.st_null;
		this.isConstant = false;
	}

	public SymbolValue(SUBJACENTTYPE subType) {
		super("value", 0);
		this.type = TYPE.dnull;
		this.subType = subType;
		this.isConstant = false;
	}

	public SymbolValue(SUBJACENTTYPE subType, Object value) {
		super("value", 0);
		this.type = TYPE.dnull;
		this.subType = subType;
		this.isConstant = true;
		this.value = value;
	}

	public SymbolValue(TYPE type, String nameType) {
		super("value", 0);
		this.type = type;
		this.nameType = nameType;
		this.isConstant = false;
	}

	public SymbolValue(TYPE type, String nameType, Object value) {
		super("value", 0);
		this.type = type;
		this.nameType = nameType;
		this.isConstant = true;
		this.value = value;
	}

	public TYPE getType() {
		return type;
	}

	public SUBJACENTTYPE getSubType() {
		return subType;
	}

	public String getNameType() {
		return nameType;
	}


	public boolean isConstant() {
		return isConstant;
	}

	public Object getValue() {
		return value;
	}

}