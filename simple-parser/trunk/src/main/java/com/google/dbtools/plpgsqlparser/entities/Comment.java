package com.google.dbtools.plpgsqlparser.entities;

/**
 * Holds the JavaDoc like comment for an object
 *
 * TODO: parse keywords from appending text
 * <p/>
 * User: Eric Härtel
 * Date: 31.07.12
 */
public class Comment {

	static private String COMMENT_REGEX = "/\\*\\*|\\*/$";

	StringBuilder 	info = new StringBuilder();

	public void append( String comment ){
		this.info.append( comment.trim().replaceAll( COMMENT_REGEX, "") );
	}

	@Override
	public String toString(){
		return this.info.toString();
	}

}
