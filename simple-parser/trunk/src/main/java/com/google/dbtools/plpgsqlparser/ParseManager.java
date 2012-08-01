package com.google.dbtools.plpgsqlparser;

import com.google.dbtools.plpgsqlparser.entities.DatabaseObject;
import com.google.dbtools.plpgsqlparser.entities.Function;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: describe here
 * <p/>
 * User: Eric Härtel
 * Date: 17.07.12
 */
public class ParseManager {

	static private Logger logger = Logger.getLogger( ParseManager.class );

	static private ParseManager instance = null;


	static ParseManager getInstance(){
		if ( instance == null ){
			instance = new ParseManager();
		}
		return instance;
	};

	private Set<DatabaseObject> objects = new HashSet<DatabaseObject>();
	private Set<Function> functions = new HashSet<Function>();

	private ParseManager(){
		
	}

	public void parse( InputStream is){

		PlpgsqlParser parser = new PlpgsqlParser( is );

		try {
			parser.disable_tracing();
			parser.parse();

			this.objects.addAll( parser.getObjects() );
			this.functions.addAll( parser.getFunctions() );
		} catch (ParseException e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public Set<Function> getFunctions(){
		return this.functions;
	}

	public Set<DatabaseObject> getObjects() {
		return objects;
	}

	public void clean(){
		this.objects = new HashSet<DatabaseObject>();
	}
}
