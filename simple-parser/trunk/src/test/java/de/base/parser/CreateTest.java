package de.base.parser;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eric Härtel (eric.haertel@gmail.com)
 * @date: 27.02.12
 * 
 */
public class CreateTest extends Base{
    
	static final String TYPE_CREATE = "/type_create.sql";
	static final String TABLE_CREATE = "/table_create.sql";

	@Test
    public void testTypeCreate(){
        try {
            this.parse(TYPE_CREATE, false);
        } catch ( ParserTestException e ) {
            Assert.fail(e.getLocalizedMessage());
        }

    }

	@Test
	public void testTableCreate(){
		try {
			this.parse(TABLE_CREATE, false);
		} catch ( ParserTestException e ) {
			Assert.fail(e.getLocalizedMessage());
		}

	}
}
