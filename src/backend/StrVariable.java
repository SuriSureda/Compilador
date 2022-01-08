import SymbolsTable.Type.SUBJACENTTYPE;

public class StrVariable extends Variable {

	String value;

	public StrVariable(String name, int code, int idParent, int offset, int size, String value){
		super(name, code, idParent, offset, size, SUBJACENTTYPE.st_string);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}