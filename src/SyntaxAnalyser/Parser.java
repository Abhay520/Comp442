package SyntaxAnalyser;
import LexAnalyser.*;
import Node.*;
import Visitor.SymTableCreationVisitor;

import java.io.*;
import java.util.*;

import static SyntaxAnalyser.SemanticAction.*;
import static LexAnalyser.LexAnalyser.*;

public class Parser {
    /**Parser table*/
    private final BiHashMap<String, String, ArrayList<String>> parserTable = new BiHashMap<>();
    private final HashMap<String, ArrayList<String>> firstSetTable = new HashMap<>();
    private final HashMap<String, ArrayList<String>> followSetTable = new HashMap<>();
    private final HashMap<String, SemanticAction> semanticTable= new HashMap<>();

    /**Terminal symbols*/
    private final ArrayList<String> terminalSymbols = new ArrayList<String>(
            Arrays.asList("private", "public", "id", "dot", "rpar", "lpar", "semi", "colon",
            "let", "float", "integer", "rcurbr", "lcurbr", "struct", "return", "write",
            "read", "while", "else", "then", "if", "minus", "plus", "void", "comma",
            "geq", "leq", "gt", "lt", "neq", "eq", "inherits", "and", "div", "mult",
            "rsqbr", "lsqbr", "impl", "arrow", "func", "not", "floatlit", "intlit", "equal",
            "or", "invalid", "eos")
    );

    /**Stack of semanticActions*/
    private final Stack<SemanticAction> semanticActionStack = new Stack<>();

    public Parser(
            String parserTablePath, String firstSetPath, String followSetPath,
                  String semanticTablePath,String srcFilePath, String outDevFilePath, String errorDevFilePath,
                  String tokenFilePath, String errorTokenFilePath){
        try{
            makeParserTable(parserTablePath);
            makeFirstSetTable(firstSetPath);
            makeFollowSetTable(followSetPath);
            makeSemanticTable(semanticTablePath);
            LexAnalyser lex = new LexAnalyser(srcFilePath);
            lex.readFromFile();
            lex.outputToFile(tokenFilePath, errorTokenFilePath);
            parse(outDevFilePath, errorDevFilePath);
            createAST();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private final Stack<String> stack = new Stack<>();
    private void inversePush(ArrayList<String> rule){
        for(int i = rule.size() -1; i >= 0; i--){
            if(!rule.get(i).equals( "epsilon")) stack.push(rule.get(i));
        }
    }
    private void changeDerivation(ArrayList<String> derivation, ArrayList<String> value){
        //Creating a new arrayList which does not have the segmantic actions
        ArrayList<String> value2 = new ArrayList<String>();
        //Removing epsilons
        for(int i = 0; i < value.size(); i++){
            if(value.get(i).equals("epsilon"))value.remove(i);
        }
        for(String it: value){
            if(it.charAt(0) != '-'){
                value2.add(it);
            };
        }
        for(int i = 0; i < derivation.size(); i++){
            if(derivation.get(i).equals(stack.peek())){
                derivation.remove(i);
                for(int j = 0; j < value2.size(); j++){
                    derivation.add(i + j, value2.get(j));
                }
                break;
            }
        }
    }
    private void makeParserTable(String parserTablePath) throws IOException{
        File file = new File(parserTablePath);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if(!line.equals("")){
                line  = line.replaceAll("\\s+"," ");
                String[] words = line.split(" ");
                //First word is the nonTerminal
                String nonTerminal = words[0];
                //Second word is the terminal
                String terminal = words[1];
                //The rest is the rule
                ArrayList<String> rule = new ArrayList<String>(Arrays.asList(words).subList(2, words.length));
                parserTable.put(nonTerminal, terminal, rule);
            }
        }
    }
    private void makeFirstSetTable(String firstSetTablePath) throws IOException{
        File file = new File(firstSetTablePath);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if(!(line == null)){
                line  = line.replaceAll("\\s{2,}"," ");
                String[] words = line.split(" ");
                //First word is the nonTerminal
                String nonTerminal = words[0];
                //The rest is the first Set
                ArrayList<String> rule = new ArrayList<String>(Arrays.asList(words).subList(1, words.length));
                firstSetTable.put(nonTerminal, rule);
            }
        }
    }
    private void makeFollowSetTable(String followSetTablePath) throws IOException{
        File file = new File(followSetTablePath);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if(!(line == null)){
                line  = line.replaceAll("\\s{2,}"," ");
                String[] words = line.split(" ");
                //First word is the nonTerminal
                String nonTerminal = words[0];
                //The rest is the first Set
                ArrayList<String> rule = new ArrayList<String>(Arrays.asList(words).subList(1, words.length));
                followSetTable.put(nonTerminal, rule);
            }
        }
    }
    private void makeSemanticTable(String semanticTablePath) throws IOException{
        File file = new File(semanticTablePath);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if(!line.equals("")){
                line  = line.replaceAll("\\s{2,}"," ");
                String[] words = line.split(" ");
                //First word is the key
                String key = words[0];
                //The rest is the Semantic Action
                SemanticAction semAction;
                if(words[1].equals("epsilon")){
                    semAction = new SemanticAction();
                }
                else if(words[1].equals("leaf")){
                    //words[2] is the semAction value
                    semAction = new SemanticAction(words[2]);
                }
                else{
                    semAction = new SemanticAction(words[2],Integer.parseInt(words[3]));
                }
                semanticTable.put(key, semAction);
            }
        }
    }
    public void parse(String outFilePath, String errorFilePath) throws IOException{
        File outFile = new File(outFilePath);
        File errorFile = new File(errorFilePath);
        PrintWriter outPw = new PrintWriter(outFile);
        PrintWriter errorPw = new PrintWriter(errorFile);
        ArrayList<String> derivation = new ArrayList<String>();
        stack.push("$");
        stack.push("START");
        derivation.add("START");
        boolean error = false;
        //Getting next valid token
        Token token = outputTokenIterator.next();
        while(token.isInvalid())token = outputTokenIterator.next();;
        while(!stack.peek().equals("$")) {
            String top = stack.peek();
            if (terminalSymbols.contains(top)) {
                if (top.equals(token.getType())) {
                    stack.pop();
                    //Getting next valid token
                    token = outputTokenIterator.next();
                    while(token.isInvalid())token = outputTokenIterator.next();;
                } else {
                    //if just end of file
                    if(token.getType().equals("EOF")){
                        stack.pop();
                    }
                    else{
                        token = skipError(errorPw, token);
                        error = true;
                    }
                }
            }
            else if (top.charAt(0) == '-') {
                SemanticAction semanticAction = semanticTable.get(top.substring(1));
                semanticActionStack.push(semanticAction);
                stack.pop();
            } else {
                if (parserTable.containsKeys(top, token.getType())) {
                    final ArrayList<String> rule = parserTable.get(top, token.getType());
                    //Change the derivation when popping nonTerminal and replace by its derivation
                    changeDerivation(derivation, rule);
                    outPw.println("START => " + derivation);
                    stack.pop();
                    inversePush(rule);
                }
                else {
                    //if just end of file
                    if(token.getType().equals("EOF")){
                        stack.pop();
                    }
                    else{
                        token = skipError(errorPw, token);
                        error = true;
                    }
                }
            }
        }
        derivation.remove(derivation.size()-1);
        outPw.println("START => " + derivation);
        outPw.close();
        errorPw.close();
        System.out.println("Syntax Analysis Complete!");
    }
    private Token skipError(PrintWriter errorPw, Token token){
        errorPw.println("Syntax error at " + token.getLocation() + " with token type " +
                token.getType() + " with value " + token.getLexeme());
        if(followSetTable.get(stack.peek()).contains(token.getType())){
            stack.pop();// Equivalent to top() -> EPSILON
        }
        else{
            while(!firstSetTable.get(stack.peek()).contains(token.getType()) ||
                       firstSetTable.get(stack.peek()).contains("epsilon") &&
                               !followSetTable.get(stack.peek()).contains(token.getType())){
                token = outputTokenIterator.next();
                while(token.isInvalid())token = outputTokenIterator.next();;
            }
        }
        return token;
    }
    /**Creating the AST and returning its root*/
    private Node createAST(){
        //Reversing the semAction stack
        ArrayList<SemanticAction> semArray =
                new ArrayList<>(semanticActionStack);
        semanticActionStack.clear();
        for(int i = semArray.size() -1; i >=0; i--){
            semanticActionStack.push(semArray.get(i));
        }
        Stack<Node> astStack = new Stack<Node>();
        //Perform semanticAction for each member of the semAction stack, and creating the AST in the meantime
        while(!semanticActionStack.empty()){
            SemanticAction action = semanticActionStack.pop();
            performAction(astStack, action);
        }
        try{
            PrintWriter pw = new PrintWriter(new File("Output/test.outAST"));
            astStack.peek().print(pw);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("AST formation done!");
        SymTableCreationVisitor symTableCreationVisitor = new SymTableCreationVisitor(
                "Output/test.outSymbolTable"
        );
        astStack.peek().accept(symTableCreationVisitor);
        System.out.println("OutSymbolTable formation done!");
        return astStack.peek();
    }
}
