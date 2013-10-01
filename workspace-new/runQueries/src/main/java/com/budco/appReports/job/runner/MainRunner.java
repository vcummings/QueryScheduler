package com.budco.appReports.job.runner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.budco.appReports.helper.Email;
import com.budco.appReports.util.DatabaseUtil;
import com.budco.appReports.util.FileUtil;

public class MainRunner {

	final static String SOURCE_DATABASE_PROPERTIES = "C:\\QueryScheduler\\jobs\\cfg\\database.src.properties";
	final static String SOURCE_QUERY = "C:\\QueryScheduler\\jobs\\cfg\\sourceQuery.sql";

	public static void main(String[] args) throws Exception {
		runReports();
	}

	public static void runReports() throws Exception {
		String sourceReportingQuery = FileUtil.readFile(SOURCE_QUERY);
		Connection sourceConn = DatabaseUtil.openConnection(SOURCE_DATABASE_PROPERTIES);

		Statement statement = sourceConn.createStatement();
		ResultSet rs = statement.executeQuery(sourceReportingQuery);

		while (rs.next()) {
			Date runDate = new Date();
			String newstring = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss").format(runDate);
			System.out.println("[" + newstring + "] " + rs.getString("targetDb").toUpperCase() + ": " + rs.getString("title"));
			String queryString = rs.getString("query");
			String dataSource = rs.getString("targetDb");
			Email eMail = setEmail(rs);

			ReportRunner.runReport(queryString, eMail, dataSource);
		}
		DatabaseUtil.closeConnection(sourceConn);
	}

	private static Email setEmail(ResultSet rs) throws SQLException {
		Email eMail = new Email();
		eMail.setAddress(rs.getString("emailTo"));
		eMail.setSubject(rs.getString("emailSubject"));
		eMail.setBody(rs.getString("emailBody"));
		eMail.setFrom(rs.getString("emailSender"));
		eMail.setFileName(rs.getString("fileName"));
		return eMail;
	}
}
