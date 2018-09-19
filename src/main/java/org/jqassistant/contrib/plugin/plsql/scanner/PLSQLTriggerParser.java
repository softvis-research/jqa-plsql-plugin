package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.TriggerDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLTriggerParser {
	
    private final Store store;
    private final TriggerDescriptor trDescriptor;
    private static final Logger LOGGER = LoggerFactory.getLogger(PLSQLSourceParser.class);

    public PLSQLTriggerParser (final Store store, final TriggerDescriptor trDescriptor) {
        this.store = store;
        this.trDescriptor = trDescriptor;
    }
    

	void parseTree(ParseTree tree) {	
		
    	for (int x=0; x < tree.getChildCount(); x++) {
    		
    		if (tree.getChild(x).getClass().getSimpleName().equals("Trigger_bodyContext")) {
    			
				PLSQLBlockDescriptor blockDescriptor = store.create(PLSQLBlockDescriptor.class);
				
				trDescriptor.setPLSQLBlock(blockDescriptor);
				
				LOGGER.info("Added PLSQLBlock.");
				
				
				final PLSQLAnonBlockParser blockParser = new PLSQLAnonBlockParser(store, blockDescriptor);    					
				blockParser.parseTree(tree.getChild(x));
				
				return;    			
    			
    		}
    		
    	}
    	
    	
    }

}
