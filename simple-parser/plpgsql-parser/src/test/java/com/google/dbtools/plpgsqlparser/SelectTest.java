package com.google.dbtools.plpgsqlparser;

import java.util.List;

import com.google.dbtools.plpgsqlparser.entities.old.SelectStatement;
import com.google.dbtools.plpgsqlparser.entities.old.Statement;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SelectTest extends Base{
	
	static final Logger logger = Logger.getLogger( SelectTest.class );
	
	static final String SIMPLE_SELECT = "/select_simple.sql";
	static final String SEQUENCE_SELECT = "/select_sequence.sql";
	static final String SELECT_EXPRESION = "/select_expresion.sql";
	static final String SELECT_WHERE = "/select_where.sql";
	
	@Test
	public void testSelectWithWhere(){
		
//		try {
//			this.parse( SELECT_WHERE, false );
//		} catch ( ParserTestException e ) {
//			Assert.fail( e.getLocalizedMessage() );
//
//		}
	}

//	@Test
	public void testSimpleSelect() {
		logger.info( "parsing " + SIMPLE_SELECT );
		List<Statement> sequence;
		try {
			sequence = this.parse(SIMPLE_SELECT, false);
		
			for ( Statement statement : sequence ) {
				logger.info( "statement of type " + statement.getType() );
				SelectStatement select = (SelectStatement) statement;
				List<String> selectList = select.getSelectList();
				for ( String string : selectList ) {
					logger.info( "select list entry: " + string );
				}
				logger.info( "\tfromClause: " + select.getFromClause().getContent() );
			}
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
		
//	@Test
	public void testSelectSequence(){
		logger.info( "parsing " + SEQUENCE_SELECT );
		try {
			this.parse(SEQUENCE_SELECT, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

//	@Test
	public void testSelectExpresion(){
		logger.info( "parsing " + SELECT_EXPRESION );
		try {
			this.parse( SELECT_EXPRESION, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

}
