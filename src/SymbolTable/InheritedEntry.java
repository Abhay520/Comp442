package SymbolTable;

import java.util.ArrayList;

public class InheritedEntry extends SymTabEntry {

    ArrayList<String> m_inherList;

    public InheritedEntry(ArrayList<String> p_inherList,  SymTab p_subtable){
        super("inheritedList", "inherit" , "inherit", p_subtable);
        m_inherList = p_inherList;
    }

    public String toString(){
        if(m_inherList.size() == 0){
            return  "| inherit   | none";
        }
        else{
            return "| inherit  |  " + m_inherList;
        }
    }

}