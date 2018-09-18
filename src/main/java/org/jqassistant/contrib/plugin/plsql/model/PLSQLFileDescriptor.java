package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("File")

public interface PLSQLFileDescriptor extends PLSQLDescriptor, FileDescriptor, NamedDescriptor {
	
	String getName();
	void setName(String name);
	
	@Relation("CONTAINS")
	PLSQLBlockDescriptor getPLSQLBlock();
	void setPLSQLBlock(PLSQLBlockDescriptor plsqlBlock);
	
	@Relation("CONTAINS")
	TriggerDescriptor getTrigger();
	void setTrigger(TriggerDescriptor trigger);
	
	@Relation("CONTAINS")
	SubprogramDescriptor getSubprogram();
	void setSubprogram(SubprogramDescriptor subprogram);
	
}
