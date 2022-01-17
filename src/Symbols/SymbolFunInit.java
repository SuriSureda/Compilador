package Symbols;

import SymbolsTable.Type.SUBJACENTTYPE;

public class SymbolFunInit extends SymbolBase{

	private String fun_id;
	private SUBJACENTTYPE return_type;

	public SymbolFunInit(String fun_id, SUBJACENTTYPE return_type) {
		super("Symbol Function Initalization", 0);
		this.return_type = return_type;
		this.fun_id = fun_id;
	}
	
	public SUBJACENTTYPE getSubType() {
		return this.return_type;
	}

	public String getFunId() {
		return this.fun_id;
	}
}
