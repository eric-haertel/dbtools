package com.google.dbtools.plpgsqlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.google.dbtools.plpgsqlparser.entities.old.Statement;
import junit.framework.Assert;

import org.apache.log4j.Logger;

public class Base {
	
	static private final Logger logger = Logger.getLogger( Base.class );

	
	public void parse( File file, Boolean enableTracing) throws ParserTestException{

		try {
			this.parse( new FileInputStream( file ), enableTracing );
		} catch (FileNotFoundException e) {
			logger.error(e.getLocalizedMessage());
		}
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
			PlpgsqlParser parser = new PlpgsqlParser( is );
			if ( enableTracing ){
				parser.enable_tracing();
			} else {
				parser.disable_tracing();
			}
			parser.parse();
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail( e.getLocalizedMessage() );
		}
		return null;
	}
}