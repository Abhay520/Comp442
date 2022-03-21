package SymbolTable;

import java.util.ArrayList;

public class SymTab {
    public String                 m_name       = null;
    public ArrayList<SymTabEntry> m_symlist    = null;
    public int                    m_size       = 0;
    public int                    m_tablelevel = 0;
    public SymTab                 m_uppertable = null;


    public SymTab(int p_level, SymTab p_uppertable){
        m_tablelevel = p_level;
        m_name = null;
        m_symlist = new ArrayList<>();
        m_uppertable = p_uppertable;
    }

    public SymTab(int p_level, String p_name, SymTab p_uppertable){
        m_tablelevel = p_level;
        m_name = p_name;
        m_symlist = new ArrayList<SymTabEntry>();
        m_uppertable = p_uppertable;
    }

    public void addEntry(SymTabEntry p_entry){
        m_symlist.add(p_entry);
    }

    public SymTabEntry lookupName(String p_tolookup) {
        SymTabEntry returnvalue = new SymTabEntry();
        boolean found = false;
        for( SymTabEntry rec : m_symlist) {
            if (p_tolookup.equals(rec.m_name)) {
                returnvalue = rec;
                found = true;
            }
        }
        if (!found) {
            if (m_uppertable != null) {
                returnvalue = m_uppertable.lookupName(p_tolookup);
            }
        }
        return returnvalue;
    }

    public String toString(){
        StringBuilder stringtoreturn = new StringBuilder();
        String prelinespacing = "";
        for (int i = 0; i < this.m_tablelevel; i++)
            prelinespacing += "|    ";
        stringtoreturn.append("\n").append(prelinespacing).append("=====================================================\n");
        stringtoreturn.append(prelinespacing).append(String.format("%-25s", "| table: " + m_name)).append("\n");//append(String.format("%-27s", " scope offset: " + m_size)).append("|\n");
        stringtoreturn.append(prelinespacing).append("=====================================================\n");
        for (SymTabEntry symTabEntry : m_symlist) {
            stringtoreturn.append(prelinespacing).append(symTabEntry.toString()).append('\n');
        }
        stringtoreturn.append(prelinespacing).append("=====================================================");
        return stringtoreturn.toString();
    }
}