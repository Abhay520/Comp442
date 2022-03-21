package SymbolTable;

import java.util.ArrayList;

public class VarEntry extends SymTabEntry {

    public VarEntry(String p_kind, String p_type, String p_name, ArrayList<Integer> p_dims){
        super(p_kind, p_type, p_name, null);
        m_dims = p_dims;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ").append(m_kind).append("    | ").append(m_name)
                .append("    | ").append(m_type);
        for(Integer i: m_dims){
            stringBuilder.append("[");
            if(i!=null){
                stringBuilder.append(i);
            }
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}
