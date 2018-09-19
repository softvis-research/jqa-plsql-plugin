package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.BeginBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.DeclareBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.ExceptionBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLAnonBlockParser {
	
    private final Store store;
    private final PLSQLBlockDescriptor blockDescriptor;
    private static final Logger LOGGER = LoggerFactory.getLogger(PLSQLSourceParser.class);

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
        		        		
        		LOGGER.info("Added DeclareBlock.");
        		
        		//Add all declared variables from the Declare Block.
        		
        		final PLSQLVariableParser<DeclareBlockDescriptor> varParser = new PLSQLVariableParser<>(store, decBlockDescriptor);
        		varParser.parseTree(tree.getChild(x)); //Seq_of_declare_specs -> Declare_specs
        		
        		break;

        	case "Seq_of_statementsContext":
        		
        		BeginBlockDescriptor begBlockDescriptor = store.create(BeginBlockDescriptor.class);
        		blockDescriptor.setBeginBlock(begBlockDescriptor); 

        		LOGGER.info("Added BeginBlock.");
        		
        		final PLSQLBeginBlockParser bbParser = new PLSQLBeginBlockParser(store, begBlockDescriptor);
        		bbParser.parseTree(tree.getChild(x));
        		
        		break;
        	
        	case "Exception_handlerContext":
        		
        		ExceptionBlockDescriptor excBlockDescriptor = store.create(ExceptionBlockDescriptor.class);
        		blockDescriptor.setExceptionBlock(excBlockDescriptor);
        		
        		LOGGER.info("Added ExceptionBlock.");
        		
        		break;
        		
        	case "Trigger_blockContext":
        		
        		boolean addedDeclareBlock = false;
        		
        		for (int y=0; y < tree.getChild(x).getChildCount(); y++) {
        			
        			String classNameTrigger = tree.getChild(x).getChild(y).getClass().getSimpleName();
        			
        			switch (classNameTrigger) {
        			
        			case("Declare_specContext"):
        				
        				if (addedDeclareBlock == false) {
        					
        					addedDeclareBlock = true;
        					
        	        		DeclareBlockDescriptor decBlockDescriptorTr = store.create(DeclareBlockDescriptor.class);
        	        		blockDescriptor.setDeclareBlock(decBlockDescriptorTr);
        	        		
        	        		LOGGER.info("Added DeclareBlock.");
        	        		
        	        		final PLSQLVariableParser<DeclareBlockDescriptor> varParserTr = new PLSQLVariableParser<>(store, decBlockDescriptorTr);
        	        		varParserTr.parseTree(tree.getChild(x)); //Seq_of_declare_specs -> Declare_specs
        					
        				}
        			
        			break;			   				

        			case("BodyContext"):
        				
        				for (int z=0; z < tree.getChild(x).getChild(y).getChildCount(); z++) {
        					
        					String classNameTrigger2 = tree.getChild(x).getChild(y).getChild(z).getClass().getSimpleName();
                			
        					switch (classNameTrigger2) {
        					
        		        	case "Seq_of_statementsContext":
        		        		
        		        		BeginBlockDescriptor begBlockDescriptorTr = store.create(BeginBlockDescriptor.class);
        		        		blockDescriptor.setBeginBlock(begBlockDescriptorTr);
        		        		
        		        		LOGGER.info("Added BeginBlock.");
        		        		
        		        		final PLSQLBeginBlockParser bbParserTr = new PLSQLBeginBlockParser(store, begBlockDescriptorTr);
        		        		bbParserTr.parseTree(tree.getChild(x));
        		        		
        		        		break;
        		        	
        		        	case "Exception_handlerContext":
        		        		
        		        		ExceptionBlockDescriptor excBlockDescriptorTr = store.create(ExceptionBlockDescriptor.class);
        		        		blockDescriptor.setExceptionBlock(excBlockDescriptorTr);
        		        		
        		        		LOGGER.info("Added ExceptionBlock.");
        		        	
        		        		
        		        		break;

        					}
        					
        				}
        				
        				break;
        			
        			
        			}
        			
        			
        		}
        		
        		break;
        		
        	
        		
        	}
    		
    		
    	}

    	
    }

}
