package org.jqassistant.contrib.plugin.plsql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLBlockDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.PLSQLFileDescriptor;
import org.jqassistant.contrib.plugin.plsql.model.VariableDescriptor;
import org.junit.Test;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;

public class PLSQLScannerTest extends AbstractPluginIT {

    @Test
    public void scanSQLFile() {
        store.beginTransaction();
        // Scan the test PLSQL file located as resource in the classpath
        File testFile = new File(getClassesDirectory(PLSQLScannerTest.class), "/test.sql");

        // Scan the PLSQL file and assert that the returned descriptor is a PLSQLFileDescriptor
        assertThat(getScanner().scan(testFile, "/test.sql", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PLSQLFileDescriptor.class));

        // Determine the PLSQLFileDescriptor by executing a Cypher query
        
        TestResult testResult = query("MATCH (plsqlFile:PLSQL:File) RETURN plsqlFile");
        
        List<PLSQLFileDescriptor> plsqlFiles = testResult.getColumn("plsqlFile");  
        
        assertThat(plsqlFiles.size(), equalTo(1));                  

        PLSQLFileDescriptor plsqlFile = plsqlFiles.get(0);
        assertThat(plsqlFile.getFileName(), equalTo("/test.sql"));     
        
        // Assert that there's only 1 Anonymous PLSQL Block.
        
        testResult = query("MATCH (a:PLSQLBlock)-[:CONTAINS]->(b) RETURN a,b");
        List<PLSQLBlockDescriptor> plsqlc = testResult.getColumn("a");
 
        assertThat(plsqlc.size(), equalTo(1));
        
        // Assert that there are 2 Variables declared by the DeclareBlock.
        
        testResult = query("MATCH (a:DeclareBlock)-[:DECLARES]->(b) RETURN a, b");
        List<VariableDescriptor> variables = testResult.getColumn("b");
        
        assertThat(variables.size(), equalTo(2));


        store.commitTransaction();
    }
    
    @Test //Procedure test
    public void scanSQLFileProc() {
        store.beginTransaction();
        // Scan the test PLSQL file located as resource in the classpath
        File testFile = new File(getClassesDirectory(PLSQLScannerTest.class), "/test_procedure.sql");

        // Scan the PLSQL file and assert that the returned descriptor is a PLSQLFileDescriptor
        assertThat(getScanner().scan(testFile, "/test_procedure.sql", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PLSQLFileDescriptor.class));

        assertThat(2, equalTo(2));

        store.commitTransaction();
    	
    }
    
}
// end::CSVScannerPluginTest[]
