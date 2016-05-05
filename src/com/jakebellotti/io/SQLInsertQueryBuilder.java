package com.jakebellotti.io;

import java.util.ArrayList;

/**
 * 
 * @author Jake Bellotti
 * @date 5-Apr-2016
 *
 */
public class SQLInsertQueryBuilder {

	private final String tableName;
	private final ArrayList<SQLQueryField> vars = new ArrayList<>();

	public SQLInsertQueryBuilder(String tableName) {
		this.tableName = tableName;
	}

	public void insert(String key, Object value) {
		this.vars.add(new SQLQueryField(key, value));
	}

	public void insertString(String key, Object value) {
		this.vars.add(new SQLQueryField(key, "" + value));
	}

	public String generateQuery() {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO " + tableName + " (");
		int count = 0;
		for (SQLQueryField s : vars) {
			builder.append(s.getKey());
			if ((count + 1) != vars.size()) {
				builder.append(", ");
			}
			count++;
		}
		builder.append(") ");
		builder.append("VALUES (");
		count = 0;

		for (SQLQueryField s : vars) {
			Object value = s.getValue();
			if (value instanceof String) {
				String s1 = ((String) value).replace("'", "''");
				builder.append("'" + s1 + "'");
			} else {
				builder.append(value);
			}
			if ((count + 1) != vars.size()) {
				builder.append(", ");
			}
			count++;
		}
		builder.append(")");
		return builder.toString();
	}

}
