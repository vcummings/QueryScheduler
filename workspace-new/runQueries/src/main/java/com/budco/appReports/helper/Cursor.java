package com.budco.appReports.helper;

public class Cursor {
	private int columnCount = 0;
	private int rowCount = 0;
    private String [] columnName;
    private String [] columnTitle;
    private String [] columnType;

	public int getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public String[] getColumnName() {
		return columnName;
	}
	public String getColumnName(int row) {
		return columnName[row];
	}
	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}
	public String[] getColumnTitle() {
		return columnTitle;
	}
	public String getColumnTitle(int row) {
		return columnTitle[row];
	}
	public void setColumnTitle(String[] columnTitle) {
		this.columnTitle = columnTitle;
	}
	public String[] getColumnType() {
		return columnType;
	}
	public String getColumnType(int row) {
		return columnType[row];
	}
	public void setColumnType(String[] columnType) {
		this.columnType = columnType;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public void incrementRowCount() {
		this.rowCount++;
	}
}
