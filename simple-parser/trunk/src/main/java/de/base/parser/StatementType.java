package de.base.parser;

/**
 * @author Eric Härtel (eric.haertel@gmail.com)
 *
 */
public enum StatementType {
	
	SELECT,
	INSERT,
	UPDATE,
	DELETE,
	
	CREATE_TABLE,
	ALTER_TABLE,
	DROP_TABLE,
	
	CREATE_FUNCTION,
	ALTER_FUNCTION,
	DROP_FUNCTION
}
