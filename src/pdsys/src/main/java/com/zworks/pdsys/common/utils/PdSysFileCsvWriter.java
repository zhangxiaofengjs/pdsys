package com.zworks.pdsys.common.utils;

public class PdSysFileCsvWriter extends PdSysFileWriter {
	private String separator = "\t";
	private String fieldChar = "\"";
	private boolean isNewRow = true;
	
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public String getFieldChar() {
		return fieldChar;
	}
	public void setFieldChar(String fieldChar) {
		this.fieldChar = fieldChar;
	}
	
	public void writeLine(String str, Object... args) {
		super.writeLine(str, args);
		isNewRow = true;
	}
	
	public void newRow() {
		this.writeLine("");
	}
	
	public void writeColumn(String columnStr, Object... args) {
		if(!isNewRow) {
			write(separator);
		}
		write(columnStr, args);
		isNewRow = false;
	}
}
