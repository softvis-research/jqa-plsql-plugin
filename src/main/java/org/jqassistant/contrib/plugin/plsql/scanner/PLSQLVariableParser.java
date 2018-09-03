package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.DataTypeDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.DeclareBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.ValueDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.VariableDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLVariableParser {
	
    private final Store store;
    private final DeclareBlockDescriptor decBlockDescriptor;

    PLSQLVariableParser(final Store store, final DeclareBlockDescriptor decBlockDescriptor) {
        this.store = store;
        this.decBlockDescriptor = decBlockDescriptor;
    }
    
    void parseTree(ParseTree tree) {   	
    	
    	for (int x = 0; x < tree.getChildCount(); x++) {
    		
    		//init ParseTree
    		ParseTree treeWalker;
    		treeWalker = tree;
    		treeWalker = treeWalker.getChild(x).getChild(0); //declaredVariable(x)->Variable_declaration   		
			
			VariableDescriptor varDescriptor = store.create(VariableDescriptor.class);
			decBlockDescriptor.getVariables().add(varDescriptor);
			varDescriptor.setName(treeWalker.getChild(0).getText()); //Variable_declaration->Identifier
			
			System.out.println("Added Variable. (" + treeWalker.getChild(0).getText() +")");
    		
    		for (int y = 0; y < treeWalker.getChildCount(); y++) {
    			
    			String className = treeWalker.getChild(y).getClass().getSimpleName();
    			
    			switch (className) {
    			
    			case "Type_specContext":
    				
    				DataTypeDescriptor dtDescriptor = store.create(DataTypeDescriptor.class);    				
    				varDescriptor.setDataType(dtDescriptor);
    				dtDescriptor.setDataType(treeWalker.getChild(y).getText());
    				
    				System.out.println("Added DataType. ("+treeWalker.getChild(y).getText()+")");
    				
    				break;
    				
    			case "Default_value_partContext":
    				
    				ParseTree t2 = treeWalker.getChild(y).getChild(1);
    				
    				while(t2.getChildCount() > 0) {
    					t2 = t2.getChild(0);    					
    				}
    				
    				ValueDescriptor valDescriptor = store.create(ValueDescriptor.class);
    				varDescriptor.setValue(valDescriptor);
    				valDescriptor.setValue(t2.getText());
    				
    				System.out.println("Added Value. ("+t2.getText()+")");
    				
    				break;
    			
    			}
    			
    			
    		}
    		
    		
    	}

    	
    }

}