package Visitor;

import Node.*;
import SymbolTable.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SymTableCreationVisitor extends Visitor{
    public Integer m_tempVarNum     = 0;
    public String  m_outputfilename = "";

    public SymTableCreationVisitor(String p_filename) {
        this.m_outputfilename = p_filename;
    }

    public String getNewTempVarName(){
        m_tempVarNum++;
        return "t" + m_tempVarNum;
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
        p_node.m_symtabentry = p_node.m_symtab.lookupName(p_node.getChildren().get(0).getData());
        //member function
        if(p_node.m_symtabentry.m_name != null) {
            p_node.m_symtab = p_node.m_symtabentry.m_subtable;
            //for each fparam and varDeclOrStat
            for(Node child:p_node.getChildren()){
                child.m_symtab = p_node.m_symtab;
                child.accept(this);
            }
        }
        //free function
        else{
            String funcName = p_node.getChildren().get(0).getData();
            ArrayList<String> param_types = new ArrayList<>();
            for(Node fParam: p_node.getChildren().get(1).getChildren()){
                param_types.add(fParam.getChildren().get(0).getData());
            }
            String returnType = p_node.getChildren().get(2).getData();
            SymTab localTable = new SymTab(1,"::" + funcName, p_node.m_symtab);
            p_node.m_symtabentry = new FuncEntry(returnType, funcName, param_types, localTable);
            p_node.m_symtab.addEntry(p_node.m_symtabentry);
            p_node.m_symtab = localTable;
            //for each varDeclOrStat only
            for(int i = 3; i < p_node.getChildren().size();i++){
                Node child = p_node.getChildren().get(i);
                child.m_symtab = p_node.m_symtab;
                child.accept(this);
            }
        }
    }

    @Override
    public void visit(FParamNode p_node) {
        String paramName = p_node.getChildren().get(0).getData();
        String paramType = p_node.getChildren().get(1).getData();
        ArrayList<Integer> dimList = new ArrayList<>();
        for (Node dim : p_node.getChildren().get(2).getChildren()){
            if(dim.getClass() == EmptyArraySizeNode.class){
                dimList.add(null);
            }
            else{
                //TODO:TRY CATCH
                Integer dimval = Integer.parseInt(dim.getData());
                dimList.add(dimval);
            }
        }
        p_node.m_symtab.addEntry(new VarEntry("param",paramType, paramName, dimList));
    }

    @Override
    public void visit(FParamsListNode p_node) {
        for(Node child:p_node.getChildren()){
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
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
    public void visit(ImplDefNode p_node) {;
        p_node.m_symtabentry = p_node.m_symtab.lookupName(p_node.getChildren().get(0).getData());
        p_node.m_symtab = p_node.m_symtabentry.m_subtable;
        for (Node child : p_node.getChildren() ) {
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
    }

    @Override
    public void visit(ImplFuncDefNode p_node) {
        //p_node.m_symtabentry = p_node.m_symtab.lookupName(p_node.getChildren().get(0).getData());
        //p_node.m_symtab = p_node.m_symtabentry.m_subtable;
        for (Node child : p_node.getChildren() ) {
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
    }

    @Override
    public void visit(IndiceListNode p_node) {

    }

    @Override
    public void visit(IntNumNode p_node) {

    }

    @Override
    public void visit(InherListNode p_node) {
        ArrayList<String> idNames = new ArrayList<>();
        for(Node idNode: p_node.getChildren()){
            idNames.add(idNode.getData());
        }
        p_node.m_symtab.addEntry(new InheritedEntry(idNames, null));
    }

    @Override
    public void visit(MemberNode p_node) {
        String memberVisibility = p_node.getChildren().get(0).getData();
        Node funcOrVarDecl  = p_node.getChildren().get(1);
        if(funcOrVarDecl.getClass() == FuncDeclNode.class){
            String className = p_node.m_symtab.m_name;
            String funcName = funcOrVarDecl.getChildren().get(0).getData();
            String funcReturnType = funcOrVarDecl.getChildren().get(2).getData();
            SymTab localtable = new SymTab(2,className + "::" + funcName, p_node.m_symtab);
            ArrayList<String> paramTypeList = new ArrayList<>();
            for (Node param : funcOrVarDecl.getChildren().get(1).getChildren()){
                String paramName = param.getChildren().get(0).getData();
                StringBuilder paramType = new StringBuilder(param.getChildren().get(1).getData());
                ArrayList<String> dimList = new ArrayList<>();
                for (Node dim : param.getChildren().get(2).getChildren()){
                    if(dim.getClass() == EmptyArraySizeNode.class){
                        dimList.add(null);
                    }
                    else{
                        //TODO:TRY CATCH
                        dimList.add(dim.getData());
                    }
                }
                for(String index: dimList){
                    paramType.append("[");
                    if(index!=null){
                        paramType.append("index");
                    }
                    paramType.append("]");
                }
                paramTypeList.add(paramType.toString());
            }
            p_node.m_symtabentry = new MemberFuncEntry(funcReturnType, funcName, paramTypeList, memberVisibility, localtable);
            p_node.m_symtab.addEntry(p_node.m_symtabentry);
            p_node.m_symtab = localtable;
        }
        else if(funcOrVarDecl.getClass() == VarDeclNode.class){
            String varName = funcOrVarDecl.getChildren().get(0).getData();
            String varType = funcOrVarDecl.getChildren().get(1).getData();
            ArrayList<Integer> dimList = new ArrayList<>();
            for (Node dim : funcOrVarDecl.getChildren().get(2).getChildren()){
                if(dim.getClass() == EmptyArraySizeNode.class){
                    dimList.add(null);
                }
                else{
                    //TODO:TRY CATCH
                    Integer dimval = Integer.parseInt(dim.getData());
                    dimList.add(dimval);
                }
            }
            p_node.m_symtabentry = new MemberVarEntry("data", varType, varName, dimList, memberVisibility);
            p_node.m_symtab.addEntry(p_node.m_symtabentry);
        }
        else{
            System.out.println("Error at line 179 SymTableCreationVisitor.java");
            System.exit(1);
        }
    }

    @Override
    public void visit(MemberListNode p_node) {
        for (Node child : p_node.getChildren() ) {
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
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
    public void visit(ProgNode p_node) {
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
    }

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
        String className = p_node.getChildren().get(0).getData();
        SymTab localTable = new SymTab(1, className, p_node.m_symtab);
        p_node.m_symtabentry = new ClassEntry(className, localTable);
        p_node.m_symtab.addEntry(p_node.m_symtabentry);
        p_node.m_symtab = localTable;
        for (Node child : p_node.getChildren() ) {
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
    }

    @Override
    public void visit(StatBlockNode p_node) {

    }

    @Override
    public void visit(TermNode p_node) {

    }

    @Override
    public void visit(TypeNode p_node) {

    }

    @Override
    public void visit(VarDeclNode p_node) {
        String varName = p_node.getChildren().get(0).getData();
        String varType = p_node.getChildren().get(1).getData();
        ArrayList<Integer> dimList = new ArrayList<>();
        for (Node dim : p_node.getChildren().get(2).getChildren()){
            if(dim.getClass() == EmptyArraySizeNode.class){
                dimList.add(null);
            }
            else{
                //TODO:TRY CATCH
                Integer dimval = Integer.parseInt(dim.getData());
                dimList.add(dimval);
            }
        }
        p_node.m_symtabentry = new VarEntry("local", varType, varName, dimList);
        if(p_node.m_symtab !=null){
            p_node.m_symtab.addEntry(p_node.m_symtabentry);
        }
    }

    @Override
    public void visit(VarDeclOrStatNode p_node) {
        for(Node child:p_node.getChildren()){
            child.m_symtab = p_node.m_symtab;
            child.accept(this);
        }
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
