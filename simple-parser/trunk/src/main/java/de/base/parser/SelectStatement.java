package de.base.parser;

import java.util.List;


/**
 * @author Eric Härtel (eric.haertel@gmail.com)
 *
 */
public class SelectStatement implements Statement{

	private StringBuilder content;
	
	private List<String> selectList;
	
	private FromClause fromClause;


	/* (non-Javadoc)
	 * @see de.base.parser.Statement#getType()
	 */
	public StatementType getType() {
		return StatementType.SELECT;
	}

	public List<String> getSelectList() {
		return selectList;
	}

	public void setSelectList( List<String> selectList ) {
		this.selectList = selectList;
	}

	public FromClause getFromClause() {
		return fromClause;
	}

	public void setFromClause( FromClause fromClause ) {
		this.fromClause = fromClause;
	}
}
