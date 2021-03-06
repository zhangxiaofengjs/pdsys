package com.zworks.pdsys.common.utils;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtils {
	private static DecimalFormat df = new DecimalFormat("0.00000");
	public static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            value = getValueFromCellType(cell, cell.getCellType());
        } else {
            value = "";
        }
        return value.trim();
    }
	
	private static String getValueFromCellType(Cell cell, int cellType) {
		String value;
		switch (cellType) {
		    case Cell.CELL_TYPE_NUMERIC:            //表示数值
		        value =  df.format(cell.getNumericCellValue());
		        break;
		    case Cell.CELL_TYPE_STRING:            //表示字符串
		        value = cell.getStringCellValue();
		        break;
		    case Cell.CELL_TYPE_FORMULA: // 公式
		    	cellType = cell.getCachedFormulaResultType();
		    	return getValueFromCellType(cell, cellType);
		    case Cell.CELL_TYPE_BLANK:            //表示空白
		        value = "";
		        break;
		    case Cell.CELL_TYPE_BOOLEAN:            //表示boolean
		        value = cell.getBooleanCellValue() + "";
		        break;
		    default:        //表示其他
		        value = "";
		        break;
		}
		return value;
	}
}
