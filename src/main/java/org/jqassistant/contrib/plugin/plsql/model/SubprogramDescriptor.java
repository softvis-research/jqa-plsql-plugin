package org.jqassistant.contrib.plugin.plsql.model;

import java.util.List;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Subprogram")

public interface SubprogramDescriptor extends PLSQLDescriptor, NamedDescriptor {
	
	String getName();
	void setName(String name);
	
	@Property("function")
	Boolean isFunction();
	void setFunction(Boolean functionSubprogram);
	
	@Property("procedure")
	Boolean isProcedure();
	void setProcedure(Boolean procedureSubprogram);
	

	@Relation("DECLARES")
	List<VariableDescriptor> getVariables();
	void setVariables(List<VariableDescriptor> variables);
	
	@Relation("CONTAINS")
	BeginBlockDescriptor getBeginBlock();
	
	@Relation("CONTAINS")
	ExceptionBlockDescriptor getExceptionBlock();
	
}
