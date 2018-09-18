package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

/**
 * Represents an anonymous PLSQl block.
 */
@Label("PLSQLBlock")
public interface PLSQLBlockDescriptor extends PLSQLDescriptor {
	
	@Relation("CONTAINS")
	DeclareBlockDescriptor getDeclareBlock();	
	void setDeclareBlock(DeclareBlockDescriptor decBlockDescriptor);
	
	@Relation("CONTAINS")
	BeginBlockDescriptor getBeginBlock();
	void setBeginBlock(BeginBlockDescriptor begBlockDescriptor);
	
	@Relation("CONTAINS")
	ExceptionBlockDescriptor getExceptionBlock();
	void setExceptionBlock(ExceptionBlockDescriptor excBlockDescriptor);
	
	String getBlockString();
	
	void setBlockString(String blockString);
	
}