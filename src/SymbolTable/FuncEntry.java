package SymbolTable;
import java.util.ArrayList;

public class FuncEntry extends SymTabEntry {

    public ArrayList<VarEntry> m_params;

    public FuncEntry(String p_type, String p_name, ArrayList<VarEntry> p_params, SymTab p_table){
        super("function", p_type, p_name, p_table);
        m_params = p_params;
    }

    public String toString(){
        return m_kind + "  | " + m_name + "    | " + m_params + ": " + m_type;
    }
}
