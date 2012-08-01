package com.google.dbtools.plpgsqlparser.entities;

import java.util.List;

/**
 * @author Eric Härtel
 * 
 */
public class Function extends DatabaseObject{

	private List<String> parameter;

	private String returnValue;

	private List<String> comment;

	/* TODO
			function calls
			used tables
	 */


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
}