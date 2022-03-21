package SymbolTable;
import java.util.ArrayList;

public class FuncEntry extends SymTabEntry {

    public ArrayList<String> m_params_type;

    public FuncEntry(String p_type, String p_name, ArrayList<String> p_params_type,SymTab p_table){
        super("function", p_type, p_name, p_table);
        m_params_type = p_params_type;
    }

    public String toString(){
        return m_kind + "  | " + m_name + "    | " + m_params_type + ": " + m_type
                + m_subtable;
    }
}
