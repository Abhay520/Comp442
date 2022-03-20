package LexAnalyser;

import java.io.*;

public class LexDriver {
    public static void main(String[] args) {
        LexAnalyser lex = new LexAnalyser("C:\\Users\\Admin\\IdeaProjects\\Comp442\\Input\\lexFile");
        lex.readFromFile();
        lex.outputToFile(
         "C:\\Users\\Admin\\IdeaProjects\\Comp442\\Output\\src.outtoken",
        "C:\\Users\\Admin\\IdeaProjects\\Comp442\\Output\\src.outerrors");
    }
}
