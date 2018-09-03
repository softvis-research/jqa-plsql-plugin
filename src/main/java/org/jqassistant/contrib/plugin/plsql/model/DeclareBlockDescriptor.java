package org.jqassistant.contrib.plugin.plsql.model;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;


@Label("DeclareBlock")

public interface DeclareBlockDescriptor extends PLSQLDescriptor {
	
	@Relation("DECLARES")
	List<CursorDescriptor> getCursors();
	
	@Relation("DECLARES")
	List<VariableDescriptor> getVariables();

}
