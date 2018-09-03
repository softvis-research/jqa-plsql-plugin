package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;

@Label("Variable")

public interface VariableDescriptor extends PLSQLDescriptor, NamedDescriptor {
	
	String getName();
	void setName(String name);
	
	@Relation("HAS")
	ValueDescriptor getValue();
	void setValue(ValueDescriptor value);
	
	@Relation("OF_TYPE")
	DataTypeDescriptor getDataType();
	void setDataType(DataTypeDescriptor dataType);		
	
}
