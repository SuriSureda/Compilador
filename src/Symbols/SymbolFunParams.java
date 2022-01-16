package Symbols;

import java.util.ArrayList;
import Sin.Param;
public class SymbolFunParams extends SymbolBase {

	private ArrayList<Param> params = null;

	public SymbolFunParams() {
		super("Symbol Function Params", 0);
	}

	public SymbolFunParams(ArrayList<Param> params) {
		super("Symbol Function Params", 0);
		this.params = params;
	}

	public ArrayList<Param> getParams() {
		return this.params;
	}
}
