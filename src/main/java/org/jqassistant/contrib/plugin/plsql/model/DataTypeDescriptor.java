package org.jqassistant.contrib.plugin.plsql.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

@Label("DataType")

public interface DataTypeDescriptor extends PLSQLDescriptor {
	
	@Property("DataType")
	String getDataType();
	void setDataType(String datatype);	

}
