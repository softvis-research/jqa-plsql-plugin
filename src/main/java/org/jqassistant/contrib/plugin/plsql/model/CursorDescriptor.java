package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;

@Label("Cursor")

public interface CursorDescriptor extends PLSQLDescriptor, NamedDescriptor {
	
	String getName();
	void setName(String name);
	
}
