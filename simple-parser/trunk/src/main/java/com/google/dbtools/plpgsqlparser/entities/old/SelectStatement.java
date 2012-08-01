package com.google.dbtools.plpgsqlparser.entities.old;

import java.util.List;


/**
 * @author Eric Härtel (eric.haertel@gmail.com)
 *
 */
public class SelectStatement implements Statement {

	private StringBuilder content;
	
	private List<String> selectList;
	
	private FromClause fromClause;


	/* (non-Javadoc)
	 * @see com.google.dbtools.plpgsqlparser.entities.old.Statement#getType()
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
