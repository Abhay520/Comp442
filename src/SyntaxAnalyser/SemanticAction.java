package SyntaxAnalyser;

import LexAnalyser.Token;
import Node.*;

import static LexAnalyser.LexAnalyser.*;

import java.util.Stack;

enum Type{EPSILON, SUBTREE, LEAF}

public class SemanticAction {
    private final String value;
    private final Type type;
    private int args = 0;
    SemanticAction(){
        this.value = "epsilon";
        this.type = Type.EPSILON;
    }
    SemanticAction(String value){
        this.value = value;
        this.type = Type.LEAF;
    }
    SemanticAction(String value, int args){
        this.value = value;
        this.args = args;
        this.type = Type.SUBTREE;
    }
    String getValue(){return value;}
    Type getType(){return type;}
    int getArgs(){return args;}
    public String toString(){
        return "Value : " + value + " args: " + args + "\n";
    }
    static void performAction(Stack<Node> astStack, SemanticAction semanticAction){
        switch (semanticAction.getValue()) {
            case "id" -> astStack.push(new IdNode(semanticTokenIterator.next().getLexeme()));
            case "type" -> astStack.push(new TypeNode(semanticTokenIterator.next().getLexeme()));
            case "intnum" -> astStack.push(new IntNumNode(semanticTokenIterator.next().getLexeme()));
            case "plus" -> astStack.push(new PlusNode(semanticTokenIterator.next().getLexeme()));
            case "minus" -> astStack.push(new MinusNode(semanticTokenIterator.next().getLexeme()));
            case "or" -> astStack.push(new OrNode(semanticTokenIterator.next().getLexeme()));
            case "mult" ->astStack.push(new MultNode(semanticTokenIterator.next().getLexeme()));
            case "div" -> astStack.push(new DivNode(semanticTokenIterator.next().getLexeme()));
            case "and" -> astStack.push(new AndNode(semanticTokenIterator.next().getLexeme()));
            case "floatNum" -> astStack.push(new FloatNumNode(semanticTokenIterator.next().getLexeme()));
            case "sign" -> astStack.push(new SignNode(semanticTokenIterator.next().getLexeme()));
            case "assignOp" -> astStack.push(new AssignNode(semanticTokenIterator.next().getLexeme()));
            case "prog" -> popUntilEpsilon(astStack, semanticAction, new ProgNode());
            case "structDecl" -> addChildren(astStack, semanticAction, new StructDeclNode());
            case "statement" -> addChildren(astStack, semanticAction, new StatementNode());
            case "impldef" -> addChildren(astStack, semanticAction, new ImplDefNode());
            case "inherList" -> popUntilEpsilon(astStack, semanticAction, new InherListNode());
            case "memberList" -> popUntilEpsilon(astStack, semanticAction, new MemberListNode());
            case "visibility" -> astStack.push(new VisibilityNode(semanticTokenIterator.next().getLexeme()));
            case "varDecl" -> addChildren(astStack, semanticAction, new VarDeclNode());
            case "funcDecl" -> addChildren(astStack, semanticAction, new FuncDeclNode());
            case "arrayList" -> popUntilEpsilon(astStack, semanticAction, new ArraySizeNode());
            case "fParamsList" -> popUntilEpsilon(astStack, semanticAction, new FParamsListNode());
            case "fParam" -> addChildren(astStack, semanticAction, new FParamNode());
            case "implFuncDef" -> popUntilEpsilon(astStack, semanticAction, new ImplFuncDefNode());
            case "funcDef" -> addChildren(astStack, semanticAction, new FuncDefNode());
            case "factor" -> addChildren(astStack, semanticAction, new FactorNode());
            case "varDeclOrStat" -> popUntilEpsilon(astStack, semanticAction, new VarDeclOrStatNode());
            case "returnStat" -> addChildren(astStack, semanticAction, new ReturnStatNode());
            case "writeStat" -> addChildren(astStack, semanticAction, new WriteStatNode());
            case "readStat" -> addChildren(astStack, semanticAction, new ReadStatNode());
            case "ifStat" -> addChildren(astStack, semanticAction, new IfStatNode());
            case "whileStat" -> addChildren(astStack, semanticAction, new WhileStatNode());
            case "assignStat" -> addChildren(astStack, semanticAction, new AssignStatNode());
            case "statBlock" -> popUntilEpsilon(astStack, semanticAction, new StatBlockNode());
            case "relExpr" -> addChildren(astStack, semanticAction, new RelExprNode());
            case "expr" -> addChildren(astStack, semanticAction, new ExprNode());
            case "relOp" -> astStack.push(new RelOpNode(semanticTokenIterator.next().getLexeme()));
            case "addOp" -> addChildren(astStack, semanticAction, new AddOpNode());
            case "term" -> addChildren(astStack, semanticAction, new TermNode());
            case "arithExpr" -> addChildren(astStack, semanticAction, new ArithExprNode());
            case "multOp" -> addChildren(astStack, semanticAction, new MultOpNode());
            case "notFactor" -> addChildren(astStack, semanticAction, new NotFactorNode());
            case "signFactor" -> addChildren(astStack, semanticAction, new StatBlockNode());
            case "indiceList" -> popUntilEpsilon(astStack, semanticAction, new IndiceListNode());
            case "aParams" -> popUntilEpsilon(astStack, semanticAction, new AParamsNode());
            case "dot" -> addChildren(astStack, semanticAction, new DotNode());
            case "funcCallStat" -> addChildren(astStack, semanticAction, new FuncCallStatNode());
            case "epsilon" -> astStack.push(new EpsilonNode());
            case "dotList" -> popUntilEpsilon(astStack, semanticAction, new DotListNode());
            case "emptyArraySize" -> astStack.push(new EmptyArraySizeNode());
            case "member" -> addChildren(astStack, semanticAction, new MemberNode());
            case "memberFunc" -> addChildren(astStack, semanticAction, new MemberFuncNode());
        }
    }

    public static void popUntilEpsilon(Stack<Node> astStack, SemanticAction semanticAction, Node parentNode) {
        Node top = astStack.peek();
        while(!"epsilon".equals(top.m_data)){
            astStack.pop();
            parentNode.addChild(top);
            top = astStack.peek();
        }
        if(!astStack.empty())astStack.pop();
        astStack.push(parentNode);
    }
    private static void addChildren(Stack<Node> astStack, SemanticAction semanticAction, Node parentNode){
        Node peek = astStack.peek();
        for(int i = 0; i < semanticAction.args; i++){
            parentNode.addChild(peek);
            astStack.pop();
            peek = astStack.peek();
        }
        astStack.push(parentNode);
    }
}
