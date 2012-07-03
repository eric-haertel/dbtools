package de.base.parser;

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
public class MultipleFileTest extends Base{
	
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

	private Set<File> getResources( String path ){

		Class clazz = this.getClass();
		URL dirURL = clazz.getClassLoader().getResource( path );

		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			//	A file path: easy enough
			try {
				return this.getFiles( new File( dirURL.toURI() ) );
			} catch (URISyntaxException e) {
				logger.error( e.getLocalizedMessage() );
			}
		}
		return null;
	}

	private Set<File> getFiles( File directory ){
		
		Set<File> returnList = new HashSet<File>();
		
		for ( File f : directory.listFiles() ){
			if ( f.isFile() ){
				returnList.add( f );
			} else {
				returnList.addAll( this.getFiles( f ) );
			}
		}
		return returnList;
	}
}
