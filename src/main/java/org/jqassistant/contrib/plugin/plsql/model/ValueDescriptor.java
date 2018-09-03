package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

@Label("Value")
public interface ValueDescriptor extends PLSQLDescriptor {
	
	@Property("Value")
	String getValue();
	void setValue(String value);	

}
