package SymbolTable;

import java.util.ArrayList;

public class MemberVarEntry extends VarEntry {

    public String m_visibility;

    public MemberVarEntry(String p_kind, String p_type, String p_name, ArrayList<Integer> p_dims,
                          String p_visibility){
        super(p_kind, p_type, p_name, null);
        m_dims = p_dims;
        m_visibility = p_visibility;
    }

    public String toString(){
        if(m_dims.size() != 0){
            StringBuilder dimensions = new StringBuilder();
            for(Integer i: m_dims){
                dimensions.append("[");
                if(i != null){
                    dimensions.append(i);
                }
                dimensions.append("]");
            }
            return "| " + m_kind + "          | " + m_name + "           | " + m_type + dimensions
                    + "             | " + m_visibility;
        }
        //data      | b           | float                             | private
        else return "| " + m_kind + "          | " + m_name + "           | " + m_type
                + "             | " + m_visibility;
    }
}
