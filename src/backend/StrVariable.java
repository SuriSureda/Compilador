package backend;

import SymbolsTable.Type.SUBJACENTTYPE;

public class StrVariable extends Variable {

	String value;

	public StrVariable(String name, int idParent, int size, String value){
		super(name, idParent, 0, size, SUBJACENTTYPE.st_string, false);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
	
	@Override
	public String getAssemblerDir() {
		return this.getName();
	}
}