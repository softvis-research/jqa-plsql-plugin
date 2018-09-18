package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.BeginBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.ExceptionBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.SubprogramDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLProcedureParser {
	
    private final Store store;
    private final SubprogramDescriptor spDescriptor;

    PLSQLProcedureParser(final Store store, final SubprogramDescriptor spDescriptor) {
        this.store = store;
        this.spDescriptor = spDescriptor;
    }
    

	void parseTree(ParseTree tree) {   	
    	
    	for (int x=0; x < tree.getChildCount(); x++) {
    		
    		String className = tree.getChild(x).getClass().getSimpleName();
    		
        	switch (className) {
        	
        	case "Seq_of_declare_specsContext":
        		
        		//Add all declared variables from the Declare Block.
        		
        		final PLSQLVariableParser<SubprogramDescriptor> varParser = new PLSQLVariableParser<>(store, spDescriptor);
        		varParser.parseTree(tree.getChild(x)); //Seq_of_declare_specs -> Declare_specs
        		
        		break;
        	
        	case "BodyContext":		
        		
        		for (int y=0; y < tree.getChild(x).getChildCount(); y++) {
        			
        			if (tree.getChild(x).getChild(y).getClass().getSimpleName().equals("Seq_of_statementsContext")) {
        				
                		BeginBlockDescriptor begBlockDescriptor = store.create(BeginBlockDescriptor.class);
                		spDescriptor.setBeginBlock(begBlockDescriptor);
                		
                		System.out.println("Added BeginBlock.");
                		
                		final PLSQLBeginBlockParser bbParser = new PLSQLBeginBlockParser(store, begBlockDescriptor);
                		bbParser.parseTree(tree.getChild(x).getChild(y));
                		
                		break;        				
        			}
        			
        		}
        		
        		
        		break;
        	
        	case "Exception_handlerContext":
        		
        		ExceptionBlockDescriptor excBlockDescriptor = store.create(ExceptionBlockDescriptor.class);
        		spDescriptor.setExceptionBlock(excBlockDescriptor);
        		
        		System.out.println("Added ExceptionBlock.");
        		
        		break;
        		
        	}
    		
    		
    	}

    	
    }

}
