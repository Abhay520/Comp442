package SymbolTable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class VarEntry extends SymTabEntry {

    public VarEntry(String p_kind, String p_type, String p_name, ArrayList<Integer> p_dims){
        super(p_kind, p_type, p_name, null);
        m_dims = p_dims;
    }

    public String toString(){
        if(this.m_kind.equals("fParam")){
            if(m_dims.size() != 0){
                StringBuilder dimensions = new StringBuilder();
                for(Integer i: m_dims){
                    dimensions.append("[");
                    if(i != null){
                        dimensions.append(i);
                    }
                    dimensions.append("]");
                }
                return  m_type + dimensions;
            }
            else return m_name + " : " + m_type;
        }
        return 	String.format("%-12s" , "| " + m_kind) +
                String.format("%-12s" , "| " + m_name) +
                String.format("%-12s"  , "| " + m_type) +
                //String.format("%-12"  , "| " + m_dims) +
                String.format("%-8s"  , "| " + m_size) +
                String.format("%-8s"  , "| " + m_offset)
                + "|";
    }
}
