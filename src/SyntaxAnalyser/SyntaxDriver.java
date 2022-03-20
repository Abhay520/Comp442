package SyntaxAnalyser;

import java.io.IOException;

public class SyntaxDriver {
    public static void main(String[] args){
        Parser parser = new Parser("Resources\\Table.txt",
                        "Resources\\FirstSetTable.txt",
                        "Resources\\FollowSetTable.txt",
                        "Resources\\SemanticTable.txt",
                        "Input\\lexFile",
                        "Output\\src.outdev",
                        "Output\\src.outdeverrors",
                        "Output\\src.outtoken",
                        "Output\\src.outerrors");
    }
}
