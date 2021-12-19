package SymbolsTable;

public class ParamExpansion extends Expansion {

	private int next;
	private String func_id;

	public ParamExpansion(Type type,  String func_id, String id, int scope, int next) {
		super(type, id, scope);
		this.next = next;
		this.func_id = func_id;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNext() {
		return this.next;
	}

	public String getFuncId() {
		return this.func_id;
	}
}
