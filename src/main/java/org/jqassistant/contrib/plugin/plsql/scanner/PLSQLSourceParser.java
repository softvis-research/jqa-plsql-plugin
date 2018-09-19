package org.jqassistant.contrib.plugin.plsql.scanner;

import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.plsql.PlSqlLexer;
import org.jqassistant.contrib.plugin.plsql.PlSqlParser;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLFileDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.SubprogramDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.TriggerDescriptor;
import org.jqassistant.contrib.plugin.plsql.scanner.PLSQLProcedureParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

public class PLSQLSourceParser {

    private final Store store;
    private final PLSQLFileDescriptor fileDescriptor;
    private static final Logger LOGGER = LoggerFactory.getLogger(PLSQLSourceParser.class);

    PLSQLSourceParser(final Store store, final PLSQLFileDescriptor fileDescriptor) {
        this.store = store;
        this.fileDescriptor = fileDescriptor;
    }

    void parseFile(final FileResource item) {
    	
        try {
        	//ANTLR4 PLSQL Lexer & Parser
            InputStream inputStream = item.createStream();
            Lexer lexer = new PlSqlLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            PlSqlParser parser = new PlSqlParser(tokenStream);
            //Create ParseTree from SQL_Script
            ParseTree t = parser.sql_script();
            
            parseTree(t);
        
        } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e);
      }
        	
        }
    
    void parseTree(ParseTree tree) {
    	
    	String className = tree.getClass().getSimpleName();
    	
    	switch (className) {
    	//Anonymous PLSQL Block
    	case "Anonymous_blockContext":
    		
    		PLSQLBlockDescriptor blockDescriptor = store.create(PLSQLBlockDescriptor.class);
    		fileDescriptor.setPLSQLBlock(blockDescriptor);
    		LOGGER.info("Added PLSQLBlock.");
    		
    		blockDescriptor.setBlockString(tree.getText());		
    		//Add all children of anonymous blocks.
    		final PLSQLAnonBlockParser blockParser = new PLSQLAnonBlockParser(store, blockDescriptor);		
    		blockParser.parseTree(tree);
    		
    		return;	
    	
    	case "Create_procedure_bodyContext":
    		
    		for (int y = 0; y < tree.getChildCount(); y++) {
    			
    			if (tree.getChild(y).getClass().getSimpleName().equals("Procedure_nameContext")) {  				
	    			
	        		//Procedure subprogram
	        		SubprogramDescriptor spDescriptor = store.create(SubprogramDescriptor.class);
	        		fileDescriptor.setSubprogram(spDescriptor);
	        		LOGGER.info("Added Procedure (Subprogram).");
	        		
	        		spDescriptor.setProcedure(true);
	        		spDescriptor.setFunction(false);
	        		spDescriptor.setName(tree.getChild(y).getText());
	        		
	        		final PLSQLProcedureParser procParser = new PLSQLProcedureParser(store, spDescriptor);
	        		procParser.parseTree(tree);
	        		
	        		return;
    			}
    			
    		}
    	
    	case "Create_function_bodyContext":
    		
    		for (int y = 0; y < tree.getChildCount(); y++) {
    			
    			if (tree.getChild(y).getClass().getSimpleName().equals("Function_nameContext")) {  				
	    			
	        		//Procedure subprogram
	        		SubprogramDescriptor spDescriptor = store.create(SubprogramDescriptor.class);
	        		fileDescriptor.setSubprogram(spDescriptor);
	        		LOGGER.info("Added Function (Subprogram).");
	        		
	        		spDescriptor.setProcedure(false);
	        		spDescriptor.setFunction(true);
	        		spDescriptor.setName(tree.getChild(y).getText());
	        		
	        		final PLSQLFunctionParser funcParser = new PLSQLFunctionParser(store, spDescriptor);
	        		funcParser.parseTree(tree);
	        		
	        		return;
    			}
    			
    		}
    		
    	case "Create_triggerContext":
    		
    		for (int y = 0; y < tree.getChildCount(); y++) {
    			
    			if (tree.getChild(y).getClass().getSimpleName().equals("Trigger_nameContext")) {  				
	    			
	        		//Procedure subprogram
	        		TriggerDescriptor trDescriptor = store.create(TriggerDescriptor.class);
	        		fileDescriptor.setTrigger(trDescriptor);
	        		System.out.println("Added Trigger.");
	        		LOGGER.info("Added Trigger.");
	        		
	        		trDescriptor.setName(tree.getChild(y).getText());
	        		
	        		final PLSQLTriggerParser triggerParser = new PLSQLTriggerParser(store, trDescriptor);
	        		triggerParser.parseTree(tree);
	        		
	        		return;
    			}
    			
    		}
    		
    		
    	}
    	//if current Node has Children...
    	if (tree.getChildCount() > 0) {

	    	for (int x = 0; x < tree.getChildCount(); x++) {
	    		//...traverse every child of tree
	    		parseTree(tree.getChild(x));
	    	}
	    	
    	}
    
    
    }
    
   
}