package SymbolTable;

import java.util.ArrayList;

public class MemberFuncEntry extends FuncEntry{
    public String m_visibility;
    public MemberFuncEntry(String p_type, String p_name, ArrayList<VarEntry> p_params, String p_visibility, SymTab p_table){
        super(p_type, p_name, p_params, p_table);
        this.m_visibility = p_visibility;
    }
    public String toString(){
        ArrayList<String> paramTypes = new ArrayList<>();
        for(VarEntry entry : m_params){
            paramTypes.add(entry.m_type);
        }
        //function  | build       | (float, float): LINEAR            | public
        return "| " + m_kind + "  | " + m_name + "    | " + paramTypes + ": " + m_type + "    " + m_visibility
                + m_subtable;
    }
}
