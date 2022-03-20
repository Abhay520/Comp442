package LexAnalyser;

public class Token {
    private final String type;
    private final String lexeme;
    private final int location;
    private boolean hasSemanticMeaning = false;
    public Token(String type, String lexeme, int location){
        this.type = type; this.lexeme = lexeme; this.location = location;
    }
    public Token(String type, String lexeme, int location, boolean hasSemanticMeaning){
        this.type = type; this.lexeme = lexeme; this.location = location;
        this.hasSemanticMeaning = hasSemanticMeaning;
    }
    public String getType(){
        return type;
    }
    public String getLexeme(){
        return lexeme;
    }
    public int getLocation(){
        return location;
    }
    public Boolean isInvalid(){
        return type.equals("invalidchar") || type.equals("invalidid") ||
                type.equals("invalidblock") || type.equals("") || type.equals("blockcmt")
                || type.equals("inlinecmt");
    }
    public String toString(){
        if(type.equals("")){
            return "";
        }
        else{
            return "[" + type + ", " + lexeme + ", " + location + "] ";
        }
    }
    public boolean hasSemanticMeaning(){
        return hasSemanticMeaning;
    }
}
