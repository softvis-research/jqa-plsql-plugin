package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.BeginBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.DeclareBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.ExceptionBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLAnonBlockParser {
	
    private final Store store;
    private final PLSQLBlockDescriptor blockDescriptor;

    PLSQLAnonBlockParser(final Store store, final PLSQLBlockDescriptor blockDescriptor) {
        this.store = store;
        this.blockDescriptor = blockDescriptor;
    }
    
    void parseTree(ParseTree tree) {   	
    	
    	for (int x=0; x < tree.getChildCount(); x++) {
    		
    		String className = tree.getChild(x).getClass().getSimpleName();
    		
        	switch (className) {
        	
        	case "Seq_of_declare_specsContext":
        		
        		DeclareBlockDescriptor decBlockDescriptor = store.create(DeclareBlockDescriptor.class);
        		blockDescriptor.setDeclareBlock(decBlockDescriptor);
        		
        		System.out.println("Added DeclareBlock.");
        		
        		//Add all declared variables from the Declare Block.
        		
        		final PLSQLVariableParser varParser = new PLSQLVariableParser(store, decBlockDescriptor);
        		varParser.parseTree(tree.getChild(x)); //Seq_of_declare_specs -> Declare_specs
        		
        		break;
        	
        	case "Seq_of_statementsContext":
        		
        		BeginBlockDescriptor begBlockDescriptor = store.create(BeginBlockDescriptor.class);
        		blockDescriptor.setBeginBlock(begBlockDescriptor);
        		
        		System.out.println("Added BeginBlock.");
        		
        		break;
        	
        	case "Exception_handlerContext":
        		
        		ExceptionBlockDescriptor excBlockDescriptor = store.create(ExceptionBlockDescriptor.class);
        		blockDescriptor.setExceptionBlock(excBlockDescriptor);
        		
        		System.out.println("Added ExceptionBlock.");
        		
        		break;
        		
        	}
    		
    		
    	}

    	
    }

}
