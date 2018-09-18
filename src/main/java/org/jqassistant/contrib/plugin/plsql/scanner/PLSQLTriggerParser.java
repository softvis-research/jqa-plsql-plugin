package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.TriggerDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLTriggerParser {
	
    private final Store store;
    private final TriggerDescriptor trDescriptor;

    public PLSQLTriggerParser (final Store store, final TriggerDescriptor trDescriptor) {
        this.store = store;
        this.trDescriptor = trDescriptor;
    }
    

	void parseTree(ParseTree tree) {	
		
    	for (int x=0; x < tree.getChildCount(); x++) {
    		
    		if (tree.getChild(x).getClass().getSimpleName().equals("Trigger_bodyContext")) {
    			
				PLSQLBlockDescriptor blockDescriptor = store.create(PLSQLBlockDescriptor.class);
				
				trDescriptor.setPLSQLBlock(blockDescriptor);
				
				System.out.println("Added PLSQL Block.");
				
				final PLSQLAnonBlockParser blockParser = new PLSQLAnonBlockParser(store, blockDescriptor);    					
				blockParser.parseTree(tree.getChild(x));
				
				return;    			
    			
    		}
    		
    	}
    	
    	
    }

}
