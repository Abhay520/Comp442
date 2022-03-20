package Visitor;

import Node.*;

import java.beans.Visibility;
import java.util.Arrays;

/**
 * Visitor superclass. Can be either an interface or an abstract class.
 * Needs to have one visit method for each of the visit methods
 * implemented by any of its subclasses.
 *
 * This forces all its subclasses to implement all of them, even if they
 * are not concerned with processing of this particular subtype, creating
 * visit methods with a body whose only function is to propagate accept() to
 * all the children of the visited node.
 */

public abstract class Visitor {
    public abstract void visit(AddOpNode         p_node);
    public abstract void visit(AndNode           p_node);
    public abstract void visit(AParamsNode       p_node);
    public abstract void visit(ArithExprNode     p_node);
    public abstract void visit(ArraySizeNode     p_node);
    public abstract void visit(AssignNode        p_node);
    public abstract void visit(AssignStatNode    p_node);
    public abstract void visit(DivNode           p_node);
    public abstract void visit(DotNode           p_node);
    public abstract void visit(ExprNode          p_node);
    public abstract void visit(FactorNode        p_node);
    public abstract void visit(FloatNumNode      p_node);
    public abstract void visit(FuncDefNode       p_node);
    public abstract void visit(FParamNode        p_node);
    public abstract void visit(FParamsListNode   p_node);
    public abstract void visit(FuncCallStatNode  p_node);
    public abstract void visit(FuncDeclNode      p_node);
    public abstract void visit(IdNode            p_node);
    public abstract void visit(IfStatNode        p_node);
    public abstract void visit(ImplDefNode       p_node);
    public abstract void visit(ImplFuncDefNode   p_node);
    public abstract void visit(IndiceListNode    p_node);
    public abstract void visit(IntNumNode        p_node);
    public abstract void visit(InherListNode     p_node);
    public abstract void visit(MemberListNode    p_node);
    public abstract void visit(MinusNode         p_node);
    public abstract void visit(MultNode          p_node);
    public abstract void visit(MultOpNode        p_node);
    public abstract void visit(Node              p_node);
    public abstract void visit(NotFactorNode     p_node);
    public abstract void visit(OrNode            p_node);
    public abstract void visit(PlusNode          p_node);
    public abstract void visit(ProgNode          p_node);
    public abstract void visit(ReadStatNode      p_node);
    public abstract void visit(RelExprNode       p_node);
    public abstract void visit(RelOpNode         p_node);
    public abstract void visit(ReturnStatNode    p_node);
    public abstract void visit(SignNode          p_node);
    public abstract void visit(SignFactorNode    p_node);
    public abstract void visit(StatementNode     p_node);
    public abstract void visit(StructDeclNode    p_node);
    public abstract void visit(StatBlockNode     p_node);
    public abstract void visit(TermNode          p_node);
    public abstract void visit(TypeNode          p_node);
    public abstract void visit(VarDeclNode       p_node);
    public abstract void visit(VarDeclOrStatNode p_node);
    public abstract void visit(VisibilityNode    p_node);
    public abstract void visit(WhileStatNode     p_node);
    public abstract void visit(WriteStatNode     p_node);
}