package Symbols;

import SymbolsTable.Type.TYPE;

public class SymbolIdVal extends SymbolBase {

	private String id;
	private TYPE type;
	private String typeName;

	private Object value;
	private boolean isConst;

	public SymbolIdVal(String id, TYPE type, String typeName) {
		super("Symbol Identifier Value", 0);
		this.id = id;
		this.type = type;
		this.typeName = typeName;
		this.isConst = false;
	}

	public SymbolIdVal(String id, TYPE type, String typeName, Object value) {
		super("Symbol Identifier Value", 0);
		this.id = id;
		this.type = type;
		this.typeName = typeName;
		this.isConst = true;
		this.value = value;
	}
	
	public String getId() {
		return this.id;
	}

	public TYPE getType() {
		return this.type;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean getIsConst(){
		return this.isConst;
	}
}
