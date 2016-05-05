package test;

import com.jakebellotti.io.SQLInsertQueryBuilder;

public class InsertQueryBuilderTest {
	
	public static void main(String[] args) {
		SQLInsertQueryBuilder qb = new SQLInsertQueryBuilder("tblMovieEntry");
		qb.insert("fldNumber", 5);
		qb.insert("fldString", "hello's");
		
		System.out.println(qb.generateQuery());
	}

}
