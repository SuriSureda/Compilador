package Symbols;

public class SymbolMEnd extends SymbolBase{

	private int label;

	public SymbolMEnd(int label) {
		super("Symbol M_End", 0);
		this.label = label;
	}
	
	public int getLabel() {
		return this.label;
	}
}
