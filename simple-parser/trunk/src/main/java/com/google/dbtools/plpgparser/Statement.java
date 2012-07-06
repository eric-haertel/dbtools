package com.google.dbtools.plpgparser;

/** 
 * 
 * @author Eric Härtel (eric.haertel@gmail.com)
 *
 */
public interface Statement {
	
	
	/**
	 * @return the type of the statement
	 */
	public StatementType getType();
	
}
