package SymbolTable;

public class ClassEntry extends SymTabEntry {

    public ClassEntry(String p_name,  SymTab p_subtable){
        super("class", p_name, p_name, p_subtable);
    }

    public String toString(){
        return  m_kind + " " + m_name + " " + m_subtable;
    }

}
