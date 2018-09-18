package org.jqassistant.contrib.plugin.plsql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;


public class test {
	
    @Test
    public void parseFile() {
        try {
        	
//        	String plsql = "CREATE OR REPLACE FUNCTION TOTALCUSTOMERS \n" + 
//        			"RETURN NUMBER IS \n" + 
//        			"   TOTAL NUMBER(2) := 0; \n" + 
//        			"BEGIN \n" + 
//        			"   SELECT COUNT(*) INTO TOTAL \n" + 
//        			"   FROM CUSTOMERS; \n" + 
//        			"    \n" + 
//        			"   RETURN TOTAL; \n" + 
//        			"END; ";
//        	
//        	String plsql = "CREATE OR REPLACE PROCEDURE GREETINGS \n" + 
//        			"AS\n" + 
//        			"MESSAGE VARCHAR2(20):= 'Hello, World!';\n" + 
//        			"RANDI INT:=10;\n" + 
//        			"BEGIN \n" + 
//        			"   DBMS_OUTPUT.PUT_LINE(MESSAGE); \n"+
//        			"	SELECT * FROM P; \n"+
//        			"END; \n" + 
//        			"/";
        	
//        	String plsql = "CREATE OR REPLACE TRIGGER DISPLAY_SALARY_CHANGES\n" + 
//        			"BEFORE DELETE OR INSERT OR UPDATE ON CUSTOMERS\n" + 
//        			"FOR EACH ROW\n" + 
//        			"WHEN (NEW.ID > 0)\n" + 
//        			"DECLARE\n" + 
//        			"   SAL_DIFF NUMBER;\n" + 
//        			"	RANDI INT := 10; \n" +
//        			"BEGIN\n" + 
//        			"   SAL_DIFF := :NEW.SALARY  - :OLD.SALARY;\n" + 
//        			"END;";
        	
        	String plsql = "DECLARE message VARCHAR2(20):= 'Hello, World!';\n" + 
        			"randi INT:=10;\n" + 
        			"BEGIN DBMS_OUTPUT.PUT_LINE(meesage); \n" + 
        			"SELECT * FROM P;\n" + 
        			"END;";
        	
//        	String plsql = "DECLARE \n" + 
//        			"   C_ADDR CUSTOMERS.ADDRESS%TYPE; \n" + 
//        			"   CURSOR C_CUSTOMERS IS \n" + 
//        			"      SELECT ID, NAME, ADDRESS FROM CUSTOMERS;\n" + 
//        			"BEGIN\n" + 
//        			"   OPEN C_CUSTOMERS;\n" + 
//        			"   CLOSE C_CUSTOMERS;\n" + 
//        			"END; ";
        	
            InputStream inputStream = new ByteArrayInputStream(plsql.getBytes(StandardCharsets.UTF_8));
            Lexer lexer = new PlSqlLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            PlSqlParser parser = new PlSqlParser(tokenStream);            
            
            ParseTree t = parser.sql_script();
            
            graphParse(t, "");
                  
            assertThat(1, equalTo(1));
            		
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
         }
    }
    
    public void parse(ParseTree tree) {
    	
    	String className = tree.getClass().getSimpleName();
    	
    	System.out.println(className);
    	System.out.println("ChildrenCount: "+tree.getChildCount());
    	
    	
//    	System.out.println("Name: "+className);
    	
//    	switch (className) {
//    	case "Anonymous_blockContext":
//    		System.out.println("Anonymous Block: ");
//    		System.out.println(tree.getText());
//    		System.out.println("");
//    		break; 
//    	case "Seq_of_declare_specsContext":
//    		System.out.println("DeclareBlock: ");
//    		System.out.println(tree.getText());
//    		System.out.println("");
//    		break;
//    	case "Seq_of_statementsContext":
//    		System.out.println("BeginBlock: ");
//    		System.out.println(tree.getText());
//    		System.out.println("");
//    		break;    		
//    	case "Variable_declarationContext":
//    		System.out.println("Variable: ");
//    		System.out.println(tree.getText());
//    		System.out.println("Name: "+ tree.getChild(0).getText()); //IdentifierContext
//    		System.out.println("Data Type: "+tree.getChild(1).getText()); //Type_specContext
//    		System.out.println("Value: "+tree.getChild(2).getText()); //AtomContext!!
//    		System.out.println("");
//    		break;
//    		
    	
    	System.out.println("Name: "+className);
    	
    	switch (className) {
    	case "Anonymous_blockContext":
    		System.out.println("Anonymous Block: ");
    		System.out.println(tree.getText());
    		System.out.println("");
    		break; 
    	case "Seq_of_declare_specsContext":
    		System.out.println("DeclareBlock: ");
    		System.out.println(tree.getText());
    		System.out.println("");
    		break;
    	case "Seq_of_statementsContext":
    		System.out.println("BeginBlock: ");
    		System.out.println(tree.getText());
    		System.out.println("");
    		break;    		
    	case "Variable_declarationContext":
    		System.out.println("Variable: ");
    		System.out.println(tree.getText());
    		System.out.println("Name: "+ tree.getChild(0).getText()); //IdentifierContext
    		System.out.println("Data Type: "+tree.getChild(1).getText()); //Type_specContext
    		System.out.println("Value: "+tree.getChild(2).getText()); //AtomContext!!
    		System.out.println("");
    		break;
    		
    	}
    	
    	
//    	if (tree.getClass().getSimpleName().equals("Regular_idContext")) {
//    		System.out.println(tree.getChild(0).getText());
//    	}
//    	
//    	if (tree.getClass().getSimpleName().equals("IdentifierContext")) {
//    		System.out.println(tree.getText());
//    	}
//    	
//    	if (className.equals("Variable_declarationContext")) {
//    		System.out.println(tree.getText());
//    	}
    	
    	if (tree.getChildCount() > 0) {
    	
	    	int treeChildrenCount = tree.getChildCount();
	    	
	    	for (int x = 0; x < treeChildrenCount; x++) {
//	    		System.out.println(x);
//	    		System.out.println(tree.getChild(x).getClass().getSimpleName());
	    		//System.out.println(x);
	    		//System.out.println(tree.getChild(x).getClass().getSimpleName());
	    		parse(tree.getChild(x));
	    	}
	    		
    	}	
    	
    	
    }
    
    public void graphParse(ParseTree tree, String str) {
    	
    	str += " -> " + tree.getClass().getSimpleName();
    	
    	for (int x = 0; x < tree.getChildCount(); x++) {
    		
    		graphParse(tree.getChild(x), str);
    	}
    	if (tree.getChildCount() == 0) { 
    		System.out.println(str);
    	
    	}
    }

}
