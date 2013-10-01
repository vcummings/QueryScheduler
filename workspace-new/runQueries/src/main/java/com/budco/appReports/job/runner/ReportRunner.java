package com.budco.appReports.job.runner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.budco.appReports.helper.Cursor;
import com.budco.appReports.helper.Email;
import com.budco.appReports.util.DatabaseUtil;
import com.budco.appReports.util.EmailUtil;
import com.budco.appReports.util.FileUtil;

public class ReportRunner {
	final static String TARGET_DATABASE_PROPERTIES = "C:\\QueryScheduler\\jobs\\cfg\\database.";

	public static void runReport(String query, Email email, String dataSource) throws Exception {
		Connection targetConn = DatabaseUtil.openConnection(TARGET_DATABASE_PROPERTIES + dataSource + ".properties");

		try {
			Statement statement = targetConn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			BufferedWriter outFile = FileUtil.createFile(email.getFileName());
			Cursor cursor = new Cursor();

	        while ( rs.next() ) {
				if ( rs.isFirst() ) {
					cursor = setMetaData(rs);
		            FileUtil.writeToFile(outFile, formatHeader(cursor));
				}
				cursor.incrementRowCount();
	            FileUtil.writeNewLineToFile(outFile, formatDetail(rs, cursor));
	        }

			FileUtil.closeFile(outFile);

			if ( cursor.getRowCount() > 0 ) {
				EmailUtil.send(email);
			}

			FileUtil.removeFile(email.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(targetConn);
		}
	}

	private static Cursor setMetaData(ResultSet rs) throws SQLException, IOException {
		Cursor cursorData = new Cursor();
		ResultSetMetaData recordSet = rs.getMetaData();

		cursorData.setColumnCount(recordSet.getColumnCount());
		String [] columnName = new String [cursorData.getColumnCount()];
		String [] columnTitle = new String [cursorData.getColumnCount()];
		String [] columnType = new String [cursorData.getColumnCount()];
		for (int i = 0; i < cursorData.getColumnCount(); i++) {
			columnName [i] = recordSet.getColumnName(i+1);
			columnTitle [i] = recordSet.getColumnLabel(i+1);
			columnType [i] = recordSet.getColumnTypeName(i+1);
		}
		cursorData.setColumnName(columnName);
		cursorData.setColumnTitle(columnTitle);
		cursorData.setColumnType(columnType);

		return cursorData;
	}

	private static String formatHeader(Cursor cursorData) {
		String result = "";

		for (int i = 0; i < cursorData.getColumnCount(); i++) {
			result += "\"" + cursorData.getColumnTitle(i) + "\",";
		}
		return result;
	}

	private static String formatDetail(ResultSet rs, Cursor cursorData) throws SQLException {
		String result = "";

		for (int i = 1; i <= cursorData.getColumnCount(); i++) {
			if ( cursorData.getColumnType(i-1).equalsIgnoreCase("NUMBER") ) {
				result += rs.getString(i) + ",";
			} else {
				result += "\"" + rs.getString(i) + "\",";
			} 
		}
		return result;
	}
}
