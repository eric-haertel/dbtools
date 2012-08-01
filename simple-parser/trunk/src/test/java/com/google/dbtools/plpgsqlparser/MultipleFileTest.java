package com.google.dbtools.plpgsqlparser;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * This test should verify if multiple files created from a real live project can be parsed.
 * 
 * @author Eric Härtel (eric.haertel@gmail.de)
 */
public class MultipleFileTest extends FileIteratorBase{

	private static final Logger logger = Logger.getLogger( MultipleFileTest.class );

	static final String PRODUCTIVE_DIRECTORY = "productive_dumps";
	
	@Test
	public void parseFilesInDirectory(){

		Set<File> files = this.getResources( PRODUCTIVE_DIRECTORY );

		Integer counter = 0;
		
		for( File f : files ){
			counter++;
			logger.debug( counter + " processing resources: " + f.getAbsolutePath() );
			try {
				this.parse( f , false );
			} catch (ParserTestException e) {
				logger.error(e.getLocalizedMessage());
			}
		}	
	}

}
