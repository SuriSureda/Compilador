package compilador;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java_cup.internal_error;

public class Main {
	private static final String WORK_DIR = System.getProperty("user.dir") + "\\src\\";
	
	private static final String LEX_DIR = WORK_DIR + "Lex\\";
	private static final String JFLEX_FILE = LEX_DIR + "LexerCup.flex";
	private static final String JFLEX_JAVA = "LexerCup.java";

	private static final String CUP_DIR = WORK_DIR + "Sin\\";
	private static final String CUP_FILE = CUP_DIR + "Sintax.cup";
	private static final String CUP_JAVA = "Parser.java";

	// All file data
	private static final String FILE1 = "";
	private static final String FILE2 = "";
	private static final String FILE3 = "";


	public static void main(String[] args) {
		generateJavaFiles();
	}

	private static void generateJavaFiles() {
		generateFlexFile();
		try {
			generateCupFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateFlexFile() {
		File flexFile = new File(JFLEX_FILE);
		jflex.Main.generate(flexFile);
	}

	private static void generateCupFile() throws internal_error, IOException, Exception {
		String[] commands  = {"-parser", "Parser", CUP_FILE};
		java_cup.Main.main(commands);
		// generates on WorkDir folder Parser.java and ParserSym.java

		// MOVE FILES
		Path parser_o = Paths.get("Parser.java");
		Path parser_d = Paths.get(CUP_DIR + "Parser.java");

		// move parser
		Files.deleteIfExists(parser_d);
		Files.move(parser_o, parser_d);

		Path sym_o = Paths.get("ParserSym.java");
		Path sym_d = Paths.get(CUP_DIR + "ParserSym.java");

		// move parser symbols
		Files.deleteIfExists(sym_d);
		Files.move(sym_o, sym_d);
		
	}

	private static void executeCompiler(){
		// Here we are executing the files specified 
		
	}
}
