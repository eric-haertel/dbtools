package com.google.dbtools.plpgsqlparser;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FunctionTest extends Base{
	
	static final Logger logger = Logger.getLogger( FunctionTest.class );
	
	static final String FUNCTION_SIMPLE = "/function_simple.sql";
	static final String FUNCTION_WITH_SET = "/function_with_set.sql";
	static final String FUNCTION_PARAMETER = "/function_parameter.sql";
	static final String FUNCTION_WITH_COMMENT = "/function_with_comment.sql";
	static final String FUNCTION_DECLARATION = "/function_declaration.sql";
	static final String FUNCTION_EXPRESSION = "/function_expression.sql";
	static final String FUNCTION_BLOCK = "/function_block.sql";
	static final String FUNCTION_SELECT = "/function_select.sql";
	static final String FUNCTION_CONDITION = "/function_condition.sql";

	static final String FUNCTION_COMPLEX_EXPRESSION = "/function_complex_expression.sql";
	static final String FUNCTION_COMPLEX_BUILTIN_TYPES = "/function_complex_builtin_types.sql";
	
	static final String FUNCTION_ARRAY = "/function_array.sql";

	
	@Test
	public void testFunctionSimple(){
		
		try {
			this.parse(FUNCTION_SIMPLE, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
	
	@Test
	public void testFunctionWithSet(){
		
		try {
			this.parse(FUNCTION_WITH_SET, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionParameter(){
		
		try {
			this.parse(FUNCTION_PARAMETER, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionWithComment(){
		
		try {
			this.parse(FUNCTION_WITH_COMMENT, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
	
	@Test
	public void testFunctionDeclaration(){
		
		try {
			this.parse(FUNCTION_DECLARATION, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

    @Test
    public void testFunctionExpression(){

        try {
            this.parse(FUNCTION_EXPRESSION, false);
        } catch ( ParserTestException e ) {
            Assert.fail( e.getLocalizedMessage() );
        }
    }

	@Test
	public void testFunctionComplexExpressions(){

		try {
			this.parse(FUNCTION_COMPLEX_EXPRESSION, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionBlock(){

		try {
			this.parse(FUNCTION_BLOCK, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionSelect(){

		try {
			this.parse(FUNCTION_SELECT, false );
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionComplex(){

		try {
			this.parse(FUNCTION_COMPLEX_BUILTIN_TYPES, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
	
	@Test
	public void testFunctionArray(){

		try {
			this.parse(FUNCTION_ARRAY, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}

	@Test
	public void testFunctionCondition(){

		try {
			this.parse(FUNCTION_CONDITION, false);
		} catch ( ParserTestException e ) {
			Assert.fail( e.getLocalizedMessage() );
		}
	}
}
