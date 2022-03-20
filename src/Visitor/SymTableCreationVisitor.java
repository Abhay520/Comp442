package Visitor;

import Node.*;
import SymbolTable.*;

import java.io.File;
import java.io.PrintWriter;

public class SymTableCreationVisitor extends Visitor{
    private String m_outputfilename;
    public SymTableCreationVisitor(String outputFileName){
        this.m_outputfilename = outputFileName;
    }
    @Override
    public void visit(AddOpNode p_node) {

    }

    @Override
    public void visit(AndNode p_node) {

    }

    @Override
    public void visit(AParamsNode p_node) {

    }

    @Override
    public void visit(ArithExprNode p_node) {

    }

    @Override
    public void visit(ArraySizeNode p_node) {

    }

    @Override
    public void visit(AssignNode p_node) {

    }

    @Override
    public void visit(AssignStatNode p_node) {

    }

    @Override
    public void visit(DivNode p_node) {

    }

    @Override
    public void visit(DotNode p_node) {

    }

    @Override
    public void visit(ExprNode p_node) {

    }

    @Override
    public void visit(FactorNode p_node) {

    }

    @Override
    public void visit(FloatNumNode p_node) {

    }

    @Override
    public void visit(FuncDefNode p_node) {

    }

    @Override
    public void visit(FParamNode p_node) {

    }

    @Override
    public void visit(FParamsListNode p_node) {

    }

    @Override
    public void visit(FuncCallStatNode p_node) {

    }

    @Override
    public void visit(FuncDeclNode p_node) {

    }

    @Override
    public void visit(IdNode p_node) {

    }

    @Override
    public void visit(IfStatNode p_node) {

    }

    @Override
    public void visit(ImplDefNode p_node) {

    }

    @Override
    public void visit(ImplFuncDefNode p_node) {

    }

    @Override
    public void visit(IndiceListNode p_node) {

    }

    @Override
    public void visit(IntNumNode p_node) {

    }

    @Override
    public void visit(InherListNode p_node) {

    }

    @Override
    public void visit(MemberListNode p_node) {

    }

    @Override
    public void visit(MinusNode p_node) {

    }

    @Override
    public void visit(MultNode p_node) {

    }

    @Override
    public void visit(MultOpNode p_node) {

    }

    @Override
    public void visit(Node p_node) {

    }

    @Override
    public void visit(NotFactorNode p_node) {

    }

    @Override
    public void visit(OrNode p_node) {

    }

    @Override
    public void visit(PlusNode p_node) {

    }

    @Override
    public void visit(ProgNode p_node){
        p_node.m_symtab = new SymTab(0,"global", null);
        // propagate accepting the same visitor to all the children
        // this effectively achieves Depth-First AST Traversal
        for (Node child : p_node.getChildren() ) {
            //make all children use this scopes' symbol table
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
        if (!this.m_outputfilename.isEmpty()) {
            File file = new File(this.m_outputfilename);
            try (PrintWriter out = new PrintWriter(file)){
                out.println(p_node.m_symtab);
            }
            catch(Exception e){
                e.printStackTrace();}
        }
    };

    @Override
    public void visit(ReadStatNode p_node) {

    }

    @Override
    public void visit(RelExprNode p_node) {

    }

    @Override
    public void visit(RelOpNode p_node) {

    }

    @Override
    public void visit(ReturnStatNode p_node) {

    }

    @Override
    public void visit(SignNode p_node) {

    }

    @Override
    public void visit(SignFactorNode p_node) {

    }

    @Override
    public void visit(StatementNode p_node) {

    }

    @Override
    public void visit(StructDeclNode p_node) {

    }

    @Override
    public void visit(StatBlockNode p_node) {
        // propagate accepting the same visitor to all the children
        // this effectively achieves Depth-First AST Traversal
        for (Node child : p_node.getChildren() ) {
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
    }

    @Override
    public void visit(TermNode p_node) {

    }

    @Override
    public void visit(TypeNode p_node) {

    }

    @Override
    public void visit(VarDeclNode p_node) {

    }

    @Override
    public void visit(VarDeclOrStatNode p_node) {

    }

    @Override
    public void visit(VisibilityNode p_node) {

    }

    @Override
    public void visit(WhileStatNode p_node) {

    }

    @Override
    public void visit(WriteStatNode p_node) {

    }

}
