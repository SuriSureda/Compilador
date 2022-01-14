package compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Lex.LexerCup;
import Sin.Parser;

import java.io.IOException;
import java.io.Reader;

import java_cup.internal_error;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Main {
	private static final String WORK_DIR = System.getProperty("user.dir") + "\\src\\";
	
	private static final String LEX_DIR = WORK_DIR + "Lex\\";
	private static final String JFLEX_FILE = LEX_DIR + "LexerCup.flex";
	private static final String JFLEX_JAVA = "LexerCup.java";

	private static final String CUP_DIR = WORK_DIR + "Sin\\";
	private static final String CUP_FILE = CUP_DIR + "Sintax.cup";
	private static final String CUP_JAVA = "Parser.java";

	private static final String OUTPUT_DIR = "output\\";

	// All file data
	// ok cases
	private static final String FILE1 = "input.txt";
	private static final String FILE2 = "";
	private static final String FILE3 = "";
	// wrong cases
	private static final String FILE4 = "";
	private static final String FILE5 = "";
	private static final String FILE6 = "";

	public static void main(String[] args) {

		// generateJavaFiles();
		
		try {
			executeCompiler();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		};
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

	private static void executeCompiler() throws FileNotFoundException, Exception{
		//Clean all output files
		cleanOutputFiles();

		// Here we are executing the files specified 
		// We read the input.txt
		Reader reader = new BufferedReader(new FileReader(FILE1));
		// generate intermediate code
		LexerCup scanner = new LexerCup(reader);
		SymbolFactory sf = new ComplexSymbolFactory();
		Parser parser = new Parser(scanner, sf);
		// rezamos 3 ave marias, 5 padre nuestros y le hacemos una estatua a Andreu  para que todo funcione 
		parser.parse();
	}

	private static void cleanOutputFiles() {
		File output_dir = new File(OUTPUT_DIR);
		if(output_dir.isDirectory()){
			for(File file : output_dir.listFiles()){
				file.delete();
			}
		}
	}
}


