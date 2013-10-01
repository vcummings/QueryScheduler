package com.budco.appReports.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DatabaseUtil {

	public static void closeConnection(Connection connection) {
		try {
//			String result = getDb(connection);
			connection.close();
//			System.out.println(getFormattedDate() + "Closed connection:  " + result);
		} catch (SQLException ex) {
			System.err.println("Could not close database connection:  " + ex.getMessage());
		}
	}

	public static String getFormattedDate() {
		Date runDate = new Date();
		String newstring = new SimpleDateFormat("[MMM dd, yyyy, HH:mm:ss] ").format(runDate);
		return newstring;
	}

	public static Connection openConnection(String propertiesFile) throws Exception {
		Properties props = PropertyUtil.loadProperties(propertiesFile);

		Class.forName(props.getProperty("jdbcDriver"));
		Connection conn = DriverManager.getConnection(props.getProperty("jdbcUrl"), props.getProperty("dbUser"), props.getProperty("dbPassword"));

//		System.out.println(getFormattedDate() + "Opened connection:  " + getDb(conn));

		return conn;
	}

	@SuppressWarnings("unused")
	private static String getDb(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT ora_database_name db FROM dual");

		rs.next();
		String result = rs.getString("db");
		return result;
	}
}
