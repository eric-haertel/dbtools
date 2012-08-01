package com.google.dbtools.plpgsqlparser.entities;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * TODO: describe here
 * <p/>
 * User: Eric Härtel
 * Date: 01.08.12
 */
public class CommentTest {

	static final Logger logger = Logger.getLogger( CommentTest.class );

	static private String COMMENT_REGEX = "/\\*\\*|\\*/$|[\\r|\\n]\\s*\\*";

	static final private String TEST_COMMENT = "/** \\r * test */";

	@Test
	public void testRegex(){
		String result = TEST_COMMENT.replaceAll(COMMENT_REGEX, "" ).trim();

		logger.info(result) ;

	}

}
