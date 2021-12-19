package SymbolsTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolsTable {
    private int scope;
    private ArrayList<Integer> scopeTable;
    private HashMap<String, Description> descriptionTable;
    private ArrayList<Expansion> expansionTable;

    public SymbolsTable() {
        reset();
    }

    public void add(String id, Type type) {

        Description oldDescription = descriptionTable.get(id);

        // if oldDes.scope > scope can override
        if(oldDescription != null && oldDescription.getScope() <= scope){
            if(oldDescription.getScope() == scope){
                // llançar error
                try {
                    throw new Exception(id + "cannot be added because it already exists in actual scope");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // move oldDescription to expansionTable
            int expIndex = scopeTable.get(scope) + 1;
            scopeTable.add(scope, expIndex);
            Expansion exp = new Expansion(oldDescription);
            expansionTable.add(expIndex, exp);
        }
        // write into description table
        Description newDesc = new Description(id, type, scope);
        descriptionTable.put(id, newDesc);
    }

    public void addParam(String idFun, String idParam, Type type){
        Description funDes = descriptionTable.get(idFun);
        // CHECK TYPE
        try {
            if(funDes == null){
                throw new Exception(idFun + "function not found");
                
            }
            if(funDes.getType().getType() != Type.TYPE.dfun){
                throw new Exception(idFun + "is not a function");
            }
        
            int idxe = funDes.getFirst();
            int idxep = -1;

            while(idxe != -1 && expansionTable.get(idxe).getId() != idParam){
                idxep = idxe;
                idxe = ((ParamExpansion)expansionTable.get(idxe)).getNext();
            }

            if(idxe != -1){
                throw new Exception(idParam + "already exists");
            }

            idxe = scopeTable.get(scope);
            idxe += 1 ;
            scopeTable.set(scope, idxe);
            ParamExpansion exp = new ParamExpansion(type, idFun, idParam, -1, -1);
            expansionTable.set(idxe, exp);
            if(idxep == -1){
                funDes.setFirst(idxe);
                descriptionTable.put(idFun, funDes);
            }else {
                ParamExpansion expP = (ParamExpansion)expansionTable.get(idxep);
                expP.setNext(idxe);
                expansionTable.set(idxep, expP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Type get(String id) {
        return descriptionTable.get(id).getType();
    }

    public void enterBlock() {
        scopeTable.add(scope + 1, scopeTable.get(scope));
        scope +=1;
    }

    public void leaveBlock() {
        if (scope == 0){
            try {
                throw new Exception("Compiler error : out of scope 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // remove out of scope variables
            // iterate over hashmap
            ArrayList<String> keys = new ArrayList<String>(descriptionTable.keySet());
            for(String key : keys){
                if(descriptionTable.get(key).getScope() > this.scope){
                    descriptionTable.remove(key);
                }
            }

            // move from expanstion to description
            int first = scopeTable.get(scope--);
            int last = scopeTable.get(scope);

            for (int i = first ; i > last; i--) {
                // not move coplex types like params
                if(expansionTable.get(i).getScope() != -1){
                    Description des = new Description(expansionTable.remove(i));
                    descriptionTable.put(des.getId(), des);
                }
            }
        }
    }

    public void reset() {
        scopeTable = new ArrayList<Integer>();
        descriptionTable = new HashMap<String, Description>();
        expansionTable = new ArrayList<Expansion>();
        scopeTable.add(0);
        scopeTable.add(0);
        scope  = 1;
    }
}
