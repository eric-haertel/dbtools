package com.google.dbtools.plpgsqlparser.entities.old;

import com.google.dbtools.plpgsqlparser.entities.old.StatementType;

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
