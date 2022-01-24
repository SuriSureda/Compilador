package SymbolsTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SymbolsTable {
    private int scope;
    private ArrayList<Integer> scopeTable;
    private HashMap<String, Description> descriptionTable;
    private ArrayList<Expansion> expansionTable;
    private static BufferedWriter out;
    private final String SYMBOLS_TABLE_PATH = "output\\SymbolsTableData.txt";

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
            int expIndex = scopeTable.get(scope);
            scopeTable.add(scope, expIndex  + 1);
            Expansion exp = new Expansion(oldDescription);
            expansionTable.add(expIndex, exp);
        }
        // write into description table
        Description newDesc = new Description(id, type, scope);
        descriptionTable.put(id, newDesc);
    }

    public void addParam(String idFun, String idParamBack, String idParam, Type type){
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
            scopeTable.set(scope, idxe + 1);
            ParamExpansion exp = new ParamExpansion(type, idFun, idParamBack, idParam, -1, -1);
            expansionTable.add(idxe, exp);
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
        if(!descriptionTable.containsKey(id)){
            //first check if is param inside expansion
            for(Expansion expansion : expansionTable){
                if(expansion instanceof ParamExpansion){
                    ParamExpansion param = (ParamExpansion) expansion;
                    if(param.getParamId().equals(id)){
                        return param.getType();
                    }
                }
            }
            return null;
        }
        return descriptionTable.get(id).getType();
    }

    public int getNumParams(String idFun){
        Description funDes = descriptionTable.get(idFun);
        int count = 0;
        // CHECK TYPE
        try {
            if(funDes == null){
                throw new Exception(idFun + "function not found");
                
            }
            if(funDes.getType().getType() != Type.TYPE.dfun){
                throw new Exception(idFun + "is not a function");
            }

            int idxe = funDes.getFirst();

            while(idxe != -1){
                count++;
                idxe = ((ParamExpansion)expansionTable.get(idxe)).getNext();
            }

            return count;
        }catch(Exception e){
            // 
        }
        return -1;
    }

    public Type getParam(String idFun, int index){
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
            int pos = index;

            while(idxe != -1 && pos > 0){
                idxe = ((ParamExpansion)expansionTable.get(idxe)).getNext();
                pos--;
            }

            if(idxe == -1){
                throw new Exception(idFun + "has" + " param at index" + index + " does not exist");
            }

            return expansionTable.get(idxe).getType();
        }catch(Exception e){
            // 
        }
        return null;
    }

    public ArrayList<Expansion> getParams(String idFun) {
        Description funDes = descriptionTable.get(idFun);
        // CHECK TYPE
        try {
            if(funDes == null){
                throw new Exception(idFun + "function not found");
                
            }
            if(funDes.getType().getType() != Type.TYPE.dfun){
                throw new Exception(idFun + "is not a function");
            }

            ArrayList<Expansion> params = new ArrayList<Expansion>();
            int idxe = funDes.getFirst();

            while(idxe != -1){
                params.add(expansionTable.get(idxe));
                idxe = ((ParamExpansion)expansionTable.get(idxe)).getNext();
            }

            return params;
        }catch(Exception e){
            // 
        }
        return null;
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

    public void saveTableInFile() {

        try {
            out = new BufferedWriter(new FileWriter(SYMBOLS_TABLE_PATH, false));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Header
        String result = "-----------------------------------------------\n"
                      + "---------------- SYMBOLS TABLE DATA -----------: \n"
                      + "-----------------------------------------------\n";

        // Description table data
        result += "-----------------------------------------------\n";
        result += "-----------DESCRIPTION TABLE  -------------\n";
        for(String key: this.descriptionTable.keySet()) {
            Description desc = this.descriptionTable.get(key);
            result += desc.toString() +"\n";
        } 
        result += "-----------------------------------------------\n";

       // Scope table data
        result += "-----------------------------------------------\n";
        result += "--------SCOPE INFO : " + this.scope+ " --------\n";
        

        for (int i = 0; i < this.scopeTable.size(); i++) {
            result+= "scope:" + i + ", pointing at: " + scopeTable.get(i) + " value\n";
        }
        result += "-----------------------------------------------\n";

        // Expansion table data
        result += "-----------------------------------------------\n";
        result += "-------------EXPANSION TABLE-------------------\n";
        for (int i = 0; i < this.expansionTable.size(); i++) {
            result += this.expansionTable.get(i).toString() + "\n";
        }
        result += "-----------------------------------------------\n";

        // ENDING
        result +="-----------------------------------------------\n";
        result +="               All Data Shown!!!             \n";
        result +="-----------------------------------------------\n";

        try {
            out.write(result); 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    }


