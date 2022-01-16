package Symbols;

import SymbolsTable.Type.SUBJACENTTYPE;

public class SymbolFunReturn extends SymbolBase{

	private String var_id;
	private SUBJACENTTYPE subType;

	public SymbolFunReturn() {
		super("Symbol Function Return", 0);
		this.subType = SUBJACENTTYPE.st_null;
	}

	public SymbolFunReturn(String var_id, SUBJACENTTYPE subType) {
		super("Symbol Function Return", 0);
		this.var_id = var_id;
		this.subType = subType;
	}

	public String getVarId() {
		return this.var_id;
	}

	public SUBJACENTTYPE getSubType() {
		return subType;
	}
}
