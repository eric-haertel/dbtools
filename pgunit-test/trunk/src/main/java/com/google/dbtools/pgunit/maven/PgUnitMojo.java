/**
 * 
 */
package com.google.dbtools.pgunit.maven;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.postgresql.util.PGmoney;

/**
 * 
 * 
 * @author Eric Härtel (eric.haertel@gmail.com)
 * 
 * 11.12.2011
 *
 * @goal run
 */
public class PgUnitMojo extends AbstractMojo {
	
	
	
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

	private Connection connection;
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		// TODO
		//	- read properties file
		//	- db connection
		this.initConnection();
		//	- install pgunit
		
		// 	- install PgUnit
		//	- run tests
		//	- check output for errors

	}
	
	/** establishes the database connection.
	 * 
	 * @throws MojoExecutionException
	 */
	private void initConnection() throws MojoExecutionException{
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch ( ClassNotFoundException e ) {
			this.getLog().error( e );
			throw new MojoExecutionException( "postgres driver not found: " + e.getLocalizedMessage() );
		}
		
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:postgresql://" 
							+ this.databaseHost 
							+ ":" 
							+ this.databasePort
							+ "/" 
							+ this.databaseName,
			        this.databaseUser, 
			        this.databasePassword
			      );
		} catch ( SQLException e ) {
			this.getLog().error( e );
			throw new MojoExecutionException( "could not connect: " + e.getLocalizedMessage() );
		}
	}

	private void installSql(){
		InputStream sqlStream = PgUnitMojo.class.getResourceAsStream( "/dklab_pgunit_2008-11-09.sql" );
		
		//this.connection.createStatement();
	}
}

















