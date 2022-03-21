package SyntaxAnalyser;

import java.io.IOException;

public class SyntaxDriver {
    public static void main(String[] args){
        Parser parser = new Parser("Resources\\Table.txt",
                        "Resources\\FirstSetTable.txt",
                        "Resources\\FollowSetTable.txt",
                        "Resources\\SemanticTable.txt",
                        "Input\\test.src",
                        "Output\\test.outderivation",
                        "Output\\test.outderivationerrors",
                        "Output\\test.outtoken",
                        "Output\\test.outtokenerrors");
    }
}
