package com.google.dbtools.plpgsqlparser;

import com.google.dbtools.plpgsqlparser.entities.Comment;
import com.google.dbtools.plpgsqlparser.entities.DatabaseObject;
import com.google.dbtools.plpgsqlparser.entities.Function;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO: describe here
 * <p/>
 * User: Eric Härtel
 * Date: 17.07.12
 */
public class ParseManagerTest extends FileIteratorBase {

	private static final Logger logger = Logger.getLogger( ParseManagerTest.class );
	
	private static final String DUMP_PATH = "productive_dumps";
	private static final String FUNCTION_FILE = "/function_simple.sql";
	
	private static final String FUNCTION_NAME = "test_2";
	private static final String TEST_COMMENT = "TEST_COMMENT";
	
	@Test
	public void testLiveDumpParsing() throws ParserTestException {
		Set<File> files = this.getResources( DUMP_PATH );

		Set<DatabaseObject> object = new HashSet<DatabaseObject>();
		
		ParseManager pm = ParseManager.getInstance();
		pm.clean();
		Integer counter = 0;
		for( File f : files ){
			counter++;
			logger.debug( counter + " processing resources: " + f.getAbsolutePath() );

			InputStream is = null;
			try {
				is = new FileInputStream( f );
			} catch (FileNotFoundException e) {
				logger.error(e.getLocalizedMessage());
				throw new ParserTestException( "could not load file: " + f.getAbsolutePath() );
			}
			if ( is == null ){
				throw new ParserTestException( "could not load file: " + f.getAbsolutePath() );
			}
			pm.parse( is );
		}

	}

	@Test
	public void testFunctionParsing() throws ParserTestException {

		Set<Function> functions = this.getFunctions();

		logger.debug( "starting search of function") ;
		Iterator<Function> functionIterator = functions.iterator();
		DatabaseObject object;

		boolean found = false;
		while ( functionIterator.hasNext() ){
			object = functionIterator.next();
			logger.debug("found object: " + object.getName());
			if( object.getName().equals( FUNCTION_NAME ) ){
				found = true;
				logger.debug( "function " + FUNCTION_NAME + " found" );
			}
		}
		if ( !found ){
			logger.info( "function " + FUNCTION_NAME + " not found" );
			Assert.fail( "function " + FUNCTION_NAME + " not found" );
		}
	}

	@Test
	public void testDocCommentParsing() throws ParserTestException {

		Set<Function> functions = this.getFunctions();
		
		logger.info( "Start comment search" );
		Iterator<Function> functionIterator = functions.iterator();
		DatabaseObject object;

		boolean found = false;
		while ( functionIterator.hasNext() ){
			object = functionIterator.next();
			logger.debug("found object: " + object.getName());
			Comment comment = object.getDocComment();

			if( comment != null && comment.toString().equals( TEST_COMMENT ) ){
				found = true;
				logger.debug( "function " + FUNCTION_NAME + " has comment '" + TEST_COMMENT + "'" );
			} else {
				logger.info( object.getDocComment() + " != " + TEST_COMMENT );
			}
		}
		if ( !found ){
			logger.info( "comment for function " + FUNCTION_NAME + " not found" );
			Assert.fail( "comment for function " + FUNCTION_NAME + " not found" );
		}
	}
	
	protected Set<Function> getFunctions() throws ParserTestException {
		ParseManager pm = ParseManager.getInstance();
		pm.clean();

		InputStream is = Base.class.getResourceAsStream( FUNCTION_FILE);
		if ( is == null ){
			throw new ParserTestException( "could not load file: " + FUNCTION_FILE );
		}
		pm.parse( is );

		return pm.getFunctions();

	}
}
