/**
 * 
 */
package com.google.dbtools.pgunit.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * 
 * @author Eric Härtel (eric.haertel@gmail.com)
 * 
 *         11.12.2011
 * 
 * @goal run
 */
public class PgUnitMojo extends AbstractMojo {

	private static final String PG_UNIT = "/dklab_pgunit_2008-11-09.sql";
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * @parameter expression="${dabaseHost}" default-value="localhost"
	 */
	private String databaseHost;

	/**
	 * @parameter expression="${dabasePort}" default-value="5432"
	 */
	private String databasePort;

	/**
	 * @parameter expression="${dabaseUser}"
	 */
	private String databaseUser;

	/**
	 * @parameter expression="${dabaseUser}"
	 */
	private String databasePassword;

	/**
	 * @parameter expression="${databaseName}"
	 */
	public String databaseName;

	/**
	 * @parameter expression="${testDirectory}" default-value="src/test/pgunit"
	 */
	private String testDirectory;

	/**
	 * @parameter expression="${testOutput}" default-value="target/pgunit/output.log"
	 */
	private String outputPath;
	
	
	/**
	 * @parameter expression="${propertiesFile}"
	 */
	private String propertiesFile;
	
			
	private Connection connection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {

		// read properties file
		if ( this.propertiesFile != null ){
			// parse properties
			Properties properties = new Properties();
			try {
			    properties.load( new FileInputStream( this.propertiesFile ) );
			} catch (IOException e) {
				this.getLog().error( e );
				throw new MojoExecutionException( e.getLocalizedMessage() );
			}

			// set properties
			for ( String key : properties.stringPropertyNames() ) {
				String value = properties.getProperty( key );
				 try {
					Field field = this.getClass().getDeclaredField( key );
					field.set( this, value );
				} catch ( IllegalArgumentException e ) {
					throw new MojoExecutionException( e.getLocalizedMessage() );
				} catch ( SecurityException e ) {
					throw new MojoExecutionException( e.getLocalizedMessage() );
				} catch ( IllegalAccessException e ) {
					throw new MojoExecutionException( e.getLocalizedMessage() );
				} catch ( NoSuchFieldException e ) {
					this.getLog().warn( e );
				}
			}
		}
		
		// db connection
		this.initConnection();
		this.getLog().info( "connection established" );
		
		// install PgUnit
		this.installPgunit();
		this.getLog().info( "PgUnit installed" );

		// install tests
		this.installTests();
		this.getLog().info( "tests installed" );

		// run test
		String testLog = this.runTest();
		this.getLog().info( "test run" );
		this.getLog().debug( testLog );
		
		// write log to file
		File logoutput = this.writeOutputFile( testLog );
		this.getLog().info( "output written" );
		
		// - check output for errors
		if ( !this.wasTestSuccessful( logoutput ) ){
			this.getLog().info( "test failures occured see file for details: " + logoutput.getAbsolutePath() );
			throw new MojoFailureException( "Failures in test!" );
		}

		this.dropPgunit();
	}

	/**
	 * establishes the database connection.
	 * 
	 * @throws MojoExecutionException
	 */
	private void initConnection() throws MojoExecutionException {

		try {
			Class.forName( "org.postgresql.Driver" );
		} catch ( ClassNotFoundException e ) {
			this.getLog().error( e );
			throw new MojoExecutionException( "postgres driver not found: "
					+ e.getLocalizedMessage() );
		}

		try {
			this.connection = DriverManager.getConnection( "jdbc:postgresql://"
					+ this.databaseHost + ":" + this.databasePort + "/"
					+ this.databaseName, this.databaseUser,
					this.databasePassword );
		} catch ( SQLException e ) {
			this.getLog().error( e );
			throw new MojoExecutionException( "could not connect: "
					+ e.getLocalizedMessage() );
		}
	}
	
	/** drops the PgUnit schema if it exists.
	 * 
	 * @throws MojoExecutionException
	 */
	private void dropPgunit() throws MojoExecutionException {
		try {
			String dropSchema = "DROP SCHEMA pgunit CASCADE";
			Statement statement = this.connection.createStatement();
			statement.execute( dropSchema );
		} catch ( SQLException e ) {
			this.getLog().warn( e.getLocalizedMessage() );
		}
	}

	private void installPgunit() throws MojoExecutionException {

		this.dropPgunit();
		
		InputStream sqlStream = PgUnitMojo.class.getResourceAsStream( PG_UNIT );
		String sqlString;
		try {
			sqlString = IOUtils.toString( sqlStream, "UTF-8" );
		} catch ( IOException e1 ) {
			throw new MojoExecutionException( "could not read PgUnit" );
		}

		try {
			Statement statement = this.connection.createStatement();
			statement.execute( sqlString );
		} catch ( SQLException e ) {
			this.getLog().error( e.getLocalizedMessage() );
			throw new MojoExecutionException( "could not install PgUnit" );
		}
	}

	private void installTests() {
		File directory = new File( testDirectory );

		Set<File> sqlFiles = this.getFiles( directory, ".sql" );

		try {
			String testSql;
			Statement statement = this.connection.createStatement();
			for ( File f : sqlFiles ) {
				FileInputStream fis;
				fis = new FileInputStream( f );
				testSql = IOUtils.toString( fis, "UTF-8" );
				statement.execute( testSql );
			}
		} catch ( FileNotFoundException e ) {
			this.getLog().error( e );
		} catch ( IOException e ) {
			this.getLog().error( e );
		} catch ( SQLException e ) {
			this.getLog().error( e );
		}

	}

	private Set<File> getFiles( File directory, String suffix ) {
		Set<File> files = new HashSet<File>();

		if ( directory.isDirectory() ) {
			for ( File f : directory.listFiles() ) {
				if ( f.isDirectory() ) {
					files.addAll( this.getFiles( f, suffix ) );
				} else if ( f.isFile() && f.getName().endsWith( suffix ) ) {
					files.add( f );
				}
			}
		}
		return files;
	}
	
	private String runTest() throws MojoExecutionException {
		StringBuilder logOutputBuilder = new StringBuilder();
		try {
			String runTests = "SELECT pgunit.testrunner(NULL)";
			Statement statement = this.connection.createStatement();
			statement.execute( runTests );
			
			SQLWarning sw = statement.getWarnings();
			while( sw != null ){
				logOutputBuilder.append( sw.getMessage() );
				logOutputBuilder.append( LINE_SEPARATOR );
				sw = sw.getNextWarning();
			}
			
		} catch ( SQLException e ) {
			this.getLog().error( e.getLocalizedMessage() );
			throw new MojoExecutionException( "could not drop PgUnit" );
		}
		return logOutputBuilder.toString();
	}
	
	private File writeOutputFile( String output ) throws MojoExecutionException{
		try {
			File f = new File( this.outputPath );
			f.getParentFile().mkdirs();
			f.createNewFile();
			FileWriter writer = new FileWriter( f );
			writer.append( output );
			writer.flush();
			writer.close();
			return f;
		} catch ( IOException e ) {
			this.getLog().error( e.getLocalizedMessage() );
			throw new MojoExecutionException( e.getLocalizedMessage() );
		}
		
	}
	
	/** searches for a line "FAILURE!"
	 * 
	 * @param file
	 * @return
	 * @throws MojoExecutionException
	 */
	private boolean wasTestSuccessful( File file ) throws MojoExecutionException{
		String re = "^FAILURES!$";
		
		try {
			LineIterator li = FileUtils.lineIterator( file );
			while ( li.hasNext()) {
				String line = li.nextLine();
				if (line.matches(re)) return false;
			}
			return true;
		} catch ( IOException e ) {
			this.getLog().error( e );
			throw new MojoExecutionException( e.getLocalizedMessage() );
		}
	}
}
