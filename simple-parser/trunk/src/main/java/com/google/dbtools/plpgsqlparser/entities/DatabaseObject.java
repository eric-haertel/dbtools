package com.google.dbtools.plpgsqlparser.entities;

/**
 * @author Eric Härtel
 * @date 06.07.12
 */
public class DatabaseObject {
	
	Comment docComment;
	
	String schema;
	
	String name;

	public Comment getDocComment() {
		return docComment;
	}

	public void setDocComment(Comment docComment) {
		this.docComment = docComment;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
