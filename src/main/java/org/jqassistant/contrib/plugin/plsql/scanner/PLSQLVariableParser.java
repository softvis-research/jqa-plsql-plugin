package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.CursorDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.DataTypeDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.DeclareBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.ValueDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.VariableDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.SubprogramDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLVariableParser<Parent> {
	
    private final Store store;
    
    private final Parent parent;
    
    PLSQLVariableParser(final Store store, final Parent parent) {
    	this.store = store;
    	this.parent = parent;
    	
    }
    
    void parseTree(ParseTree tree) {   	
    	
    	for (int x = 0; x < tree.getChildCount(); x++) {
    		
    		//init ParseTree
    		ParseTree treeWalker;
    		treeWalker = tree;
    		treeWalker = treeWalker.getChild(x).getChild(0); //declaredVariable(x)->Variable_declaration || ->Cursor_declaration
    		
    		if (treeWalker != null) {
    		
	    		switch(treeWalker.getClass().getSimpleName()) {
	    		
	    		case("Variable_declarationContext"):
				
					VariableDescriptor varDescriptor = store.create(VariableDescriptor.class);
					
					if (parent.toString().startsWith("DeclareBlockDescriptor")) {
						
						((DeclareBlockDescriptor) parent).getVariables().add(varDescriptor);
						
					}
					
					if (parent.toString().startsWith("SubprogramDescriptor")) {
						
						((SubprogramDescriptor) parent).getVariables().add(varDescriptor);
						
					}			
					
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
		    		break;
		    		
	    		case("Cursor_declarationContext"):
	    			
	    			
	    			CursorDescriptor curDescriptor = store.create(CursorDescriptor.class);
	    			
				if (parent.toString().startsWith("DeclareBlockDescriptor")) {
					
					((DeclareBlockDescriptor) parent).getCursors().add(curDescriptor);
					
				}
				
				if (parent.toString().startsWith("SubprogramDescriptor")) {
					
					((SubprogramDescriptor) parent).getCursors().add(curDescriptor);
					
				}
				
				for (int z = 0; z < treeWalker.getChildCount(); z++ ) {
					
					if (treeWalker.getChild(z).getClass().getSimpleName().equals("IdentifierContext")) {
						
						curDescriptor.setName(treeWalker.getChild(z).getText()); //Cursor_declaration->Identifier
						
						System.out.println("Added Cursor. (" + treeWalker.getChild(z).getText() +")");
						
					}
					
				}
				
				break;
		    		
	    		}
	    		
	    		
	    		
	    		
	    	}
	
	    	
	    }
    	
    } 	

}