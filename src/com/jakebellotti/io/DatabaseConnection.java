package com.jakebellotti.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.derby.jdbc.EmbeddedDriver;

import com.jakebellotti.DatabaseTableConstants;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public class DatabaseConnection {
	//st.execute(sql, Statement.RETURN_GENERATED_KEYS);
	//ResultSet keys = st.getGeneratedKeys();

	private static final String DATABASE_LOC = "./data/db/";
	private static final Logger logger = new Logger(DatabaseConnection.class); 
	private Connection conn;

	public void connect() {
		try {
			DriverManager.registerDriver(new EmbeddedDriver());
			conn = DriverManager.getConnection(getConnectionString());
			createRequiredTables();
		} catch (SQLException e) {
			if(DerbyUtils.anotherInstanceRunningInExceptionSeries(e)) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Another instance running");
				errorAlert.setContentText("Another instance of this program is running. Closing program now.");
				errorAlert.showAndWait();
				System.exit(0);
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the required tables for this program to run.
	 * @return
	 */
	public void createRequiredTables() {
		logger.println(createTable(DatabaseTableConstants.createMovieListEntryTable()));
	}
	
	/**
	 * A method made specifically for creating tables, will check to see if they exist.
	 * @param table
	 * @return
	 */
	public boolean createTable(String tableDeclaration) {
		try {
			conn.createStatement().execute(tableDeclaration);
			return true;
		} catch (SQLException e) {
			if(DerbyUtils.tableAlreadyExists(e)) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean query(String query) {
		try {
			return conn.createStatement().execute(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Optional<ResultSet> executeQuery(String query) {
		try {
			ResultSet results = conn.createStatement().executeQuery(query);
			return Optional.of(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public String getConnectionString() {
		return "jdbc:derby:" + DATABASE_LOC + ";create=true";
	}

	public String getShutdownString() {
		return "jdbc:derby:;shutdown=true";
	}

//	executeQuery("SELECT * FROM SYS.SYSTABLES").ifPresent(rs->{
//	System.out.println("Got result set.");
//	try {
//		while(rs.next()) {
//			
//			System.out.println(rs.getString(2));
//		}
//	} catch (Exception e1) {
//		e1.printStackTrace();
//	}
//});;
	
}
