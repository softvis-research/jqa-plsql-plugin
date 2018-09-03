package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("SQLStatement")
public interface SQLStatementDescriptor extends PLSQLDescriptor {
	
	String getSQLStatement();
	void setSQLStatement(String sqlStatement);

}
