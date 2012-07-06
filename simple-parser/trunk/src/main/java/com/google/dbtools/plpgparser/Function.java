package com.google.dbtools.plpgparser;

import java.util.List;

/**
 * 
 * User: Eric Härtel
 * 
 * Date: 06.07.12
 * Time: 21:54
 */
public class Function {
	
	private String schema;
	
	private String name;

	private List<String> parameter;

	private String returnValue;

	private List<String> comment;

	// function calls
	// used tables

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getParameter() {
		return parameter;
	}

	public void setParameter(List<String> parameter) {
		this.parameter = parameter;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public List<String> getComment() {
		return comment;
	}

	public void setComment(List<String> comment) {
		this.comment = comment;
	}

}
