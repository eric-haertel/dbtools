package com.google.dbtools.plpgsqlparser;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SetTest extends Base{
	
	static final Logger logger = Logger.getLogger( SetTest.class );
	
	static final String SET_SIMPLE = "/set_simple.sql";
	
	@Test
	public void testSetSimple(){
		
		try {
			this.parse(SET_SIMPLE, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
}