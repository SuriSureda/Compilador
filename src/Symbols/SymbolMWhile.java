package Symbols;

public class SymbolMWhile extends SymbolBase {

	private int label;

	public SymbolMWhile(int label) {
		super("Symbol M_While", 0);
		this.label = label;
	}
	
	public int getLabel() {
		return this.label;
	}

}