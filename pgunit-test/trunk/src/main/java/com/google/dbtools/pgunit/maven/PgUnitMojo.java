/**
 * 
 */
package com.google.dbtools.pgunit.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
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
	private String databaseName;

	/**
	 * @parameter expression="${testDirectory}" default-value="src/test/pgunit"
	 */
	private String testDirectory;

	private Connection connection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {

		// TODO
		// - read properties file
		// - db connection
		this.initConnection();
		this.installPgunit();
		this.installTests();

		// - run tests
		// - check output for errors

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

	private void installPgunit() throws MojoExecutionException {

		// drop pgunit schema if exists
		String dropSchema = "DROP SCHEMA pgunit CASCADE";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.execute( dropSchema );
		} catch ( SQLException e ) {
			this.getLog().error( e.getLocalizedMessage() );
		}

		InputStream sqlStream = PgUnitMojo.class.getResourceAsStream( PG_UNIT );
		String sqlString;
		try {
			sqlString = IOUtils.toString( sqlStream, "UTF-8" );
		} catch ( IOException e1 ) {
			throw new MojoExecutionException( "could not read PgUnit" );
		}

		try {
			statement = this.connection.createStatement();
			statement.execute( sqlString );
		} catch ( SQLException e ) {
			this.getLog().error( e.getLocalizedMessage() );
			throw new MojoExecutionException( "could not install PgUnit" );
		}
	}

	private void installTests() {
		File directory = new File( testDirectory );

		Set<File> sqlFiles = this.getFiles( directory, ".sql" );
		Statement statement;

		try {
			String testSql;
			statement = this.connection.createStatement();
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
}
