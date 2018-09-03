package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("BeginBlock")

public interface BeginBlockDescriptor extends PLSQLDescriptor {
	
	@Relation("CONTAINS")
	SQLStatementDescriptor getSQLStatement();
	
	@Relation("CALLS")
	SubprogramDescriptor getSubprogram();
	
//	@Relation("CALLS")
//	PackageDescriptor getPackage();

}
