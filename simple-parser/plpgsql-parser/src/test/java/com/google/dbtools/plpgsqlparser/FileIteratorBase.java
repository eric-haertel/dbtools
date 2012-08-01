package com.google.dbtools.plpgsqlparser;

import org.apache.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides the ability to iterate recursively in a directory to collect all files and return them
 * <p/>
 * User: Eric Härtel
 * Date: 17.07.12
 */
public class FileIteratorBase extends Base {

	private static final Logger logger = Logger.getLogger( FileIteratorBase.class );

	public Set<File> getResources( String path ){

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

	public Set<File> getFiles( File directory ){

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
