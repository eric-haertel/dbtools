package com.google.dbtools.plpgparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

public class Base {
	
	static final Logger logger = Logger.getLogger( Base.class );

	
	public List<Statement> parse( File file, Boolean enableTracing) throws ParserTestException{

		try {
			return this.parse( new FileInputStream( file ), enableTracing );
		} catch (FileNotFoundException e) {
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}

	public List<Statement> parse( String resourcesPath, Boolean enableTracing) throws ParserTestException{

		InputStream is = Base.class.getResourceAsStream( resourcesPath);
		if ( is == null ){
			throw new ParserTestException( "could not load file: " + resourcesPath );
		}
		return this.parse( is, enableTracing );
	}

	public List<Statement> parse( InputStream is, Boolean enableTracing) throws ParserTestException{


		try {
			Simple simple = new Simple( is );
			if ( enableTracing ){
				simple.enable_tracing();
			} else {
				simple.disable_tracing();
			}
			List<Statement> sequence = simple.parseSimple();
			
//			for (Statement statement : sequence) {
//				logger.debug( "statement of type " + statement.getType() );
//			}
			return sequence;
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail( e.getLocalizedMessage() );
		}
		return null;
	}
}
