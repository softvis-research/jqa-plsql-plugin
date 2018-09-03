package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Trigger")

public interface TriggerDescriptor extends PLSQLDescriptor, NamedDescriptor {
	
	String getName();
	void setName(String name);
	
	@Relation("CONTAINS")
	PLSQLBlockDescriptor getPLSQLBlock();

}
