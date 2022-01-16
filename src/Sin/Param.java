package Sin;

import SymbolsTable.Type;

public class Param {
	private String varId;
	private Type type;

	public Param(String varId, Type type) {
		this.varId = varId;
		this.type = type;
	}

	public String getVarId() {
		return this.varId;
	}

	public Type getType() {
		return this.type;
	}
}
