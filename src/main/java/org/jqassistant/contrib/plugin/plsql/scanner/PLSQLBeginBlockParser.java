package org.jqassistant.contrib.plugin.plsql.scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.model.BeginBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.SQLStatementDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;

public class PLSQLBeginBlockParser {

    private final Store store;
    private final BeginBlockDescriptor bbd;
    private static final Logger LOGGER = LoggerFactory.getLogger(PLSQLSourceParser.class);

    PLSQLBeginBlockParser (final Store store, final BeginBlockDescriptor bbd) {
        this.store = store;
        this.bbd = bbd;
    }
    
    void parseTree(ParseTree tree) {
    	
    	for (int x=0; x < tree.getChildCount(); x++) {
    		
    		if (tree.getChild(x).getClass().getSimpleName().equals("StatementContext")) {
    			
    			switch(tree.getChild(x).getChild(0).getClass().getSimpleName()) {
    			
    			case("Function_callContext"):
    				//System.out.println("Looks like we have a Function!");
    				
    			break;
    			
    			case("Sql_statementContext"):
    				
    				SQLStatementDescriptor sqlStmnt = store.create(SQLStatementDescriptor.class);
    			
    				bbd.setSQLStatement(sqlStmnt);
    				
    				sqlStmnt.setSQLStatement(tree.getChild(x).getChild(0).getText());
    				LOGGER.info("Added SQLStatement.");
    			
    			break;
    			
    			
    			}
    			
    			
    			
    		}
    		
    	}
    	
    	
    	
    }
    
    
}
