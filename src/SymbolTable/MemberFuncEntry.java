package SymbolTable;

import java.util.ArrayList;

public class MemberFuncEntry extends FuncEntry{
    public String m_visibility;
    public MemberFuncEntry(String p_type, String p_name, ArrayList<String> p_params, String p_visibility, SymTab p_table){
        super(p_type, p_name, p_params, p_table);
        this.m_visibility = p_visibility;
    }
    public String toString(){
        //function  | build       | (float, float): LINEAR            | public
        return "| " + m_kind + "  | " + m_name + "    | " + m_params_type + ": " + m_type + "  | " + m_visibility
                + m_subtable;
    }
    /** Tests if two memberFuncs are equal
     *  Returns -1 if they have the diff name
     *  Returns -2 if they have the same reference ( is not supposed to happen)
     *  Returns 0 if they have same name AND same params
     *  Returns 1 if they have same name but different params
     */
    public int equal(Object otherObj) {
        if (this == otherObj) {
            return -2;
        }
        if (otherObj instanceof MemberFuncEntry) {
            MemberFuncEntry entry = (MemberFuncEntry) otherObj;
            //two member funcs are the same if they have the same name and same params
            if (entry.m_name.equals(this.m_name)) {
                boolean sameParams = true;
                if (entry.m_params_type.size() == this.m_params_type.size()) {
                    sameParams = true;
                    for (int i = 0; i < entry.m_params_type.size(); i++) {
                        if (!entry.m_params_type.get(i).equals(this.m_params_type.get(i))) {
                            sameParams = false;
                        }
                    }
                    if(sameParams)return 0;
                }
                return 1;
            }
        }
        return -1;
    }
}
