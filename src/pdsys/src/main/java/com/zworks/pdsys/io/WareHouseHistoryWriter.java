package com.zworks.pdsys.io;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.enumClass.BOMType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;

@Component
public class WareHouseHistoryWriter {
	private CellStyle cellStyle;
	private CellStyle cellStyleHeader;
	
	public void writeEntryPn(List<WareHouseEntryPnModel> list, String tempFilePath) {
		OutputStream out = null;
		int rowNo = 0;
		int columnNo = 0;
		try {
			out = new FileOutputStream(tempFilePath);
			 
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			initStyle(wb);
			
			Row row = sheet.createRow(rowNo++);
			SetCell(row, columnNo++, "品目", true);
			SetCell(row, columnNo++, "名称", true);
			SetCell(row, columnNo++, "单位", true);
			SetCell(row, columnNo++, "入库数量", true);
			
			for(WareHouseEntryPnModel whePn : list) {
				PnModel pn = whePn.getPn();
				UnitModel unit = pn.getUnit();
				
				row = sheet.createRow(rowNo++);
				
				columnNo = 0;
				SetCell(row, columnNo++, pn.getPn());
				SetCell(row, columnNo++, pn.getName());
				SetCell(row, columnNo++, unit.getName());
				SetCell(row, columnNo++, whePn.getProducedNum());
			}
			
			autoSizeColumn(sheet);
			wb.write(out);
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(out != null) {
				try {
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void initStyle(Workbook wb) {
		cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		Font f = wb.createFont();
		f.setFontName("Microsoft YaHei");
		f.setFontHeightInPoints((short)10);
		cellStyle.setFont(f);

		cellStyleHeader = wb.createCellStyle();
		cellStyleHeader.cloneStyleFrom(cellStyle);
		cellStyleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setFillForegroundColor(IndexedColors.GOLD.index);
		Font f2 = wb.createFont();
		f2.setFontName("Microsoft YaHei");
		f2.setFontHeightInPoints((short)10);
		f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyleHeader.setFont(f2);
	}

	private void SetCell(Row row, int columnNo, Object value) {
		SetCell(row, columnNo, value, false);
	}
	
	private void SetCell(Row row, int columnNo, Object value, boolean isHeader) {
		Cell c = row.createCell(columnNo);
		c.setCellStyle(isHeader?this.cellStyleHeader:cellStyle);
		c.setCellValue(value.toString());
	}

	private void autoSizeColumn(Sheet sheet) {
		Row row = sheet.getRow(0);
		int col = row.getPhysicalNumberOfCells();
		for(int i = 0; i < col; i++) {
			sheet.autoSizeColumn(i, true);
		}
	}
	
	public void writeDeliveryPn(List<WareHouseDeliveryPnModel> list, String tempFilePath) {
		OutputStream out = null;
		int rowNo = 0;
		int columnNo = 0;
		try {
			out = new FileOutputStream(tempFilePath);
			 
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			initStyle(wb);
			
			Row row = sheet.createRow(rowNo++);
			SetCell(row, columnNo++, "品目", true);
			SetCell(row, columnNo++, "名称", true);
			SetCell(row, columnNo++, "单位", true);
			SetCell(row, columnNo++, "出库数量", true);
			
			for(WareHouseDeliveryPnModel whdPn : list) {
				PnModel pn = whdPn.getPn();
				UnitModel unit = pn.getUnit();
				
				row = sheet.createRow(rowNo++);
				
				columnNo = 0;
				SetCell(row, columnNo++, pn.getPn());
				SetCell(row, columnNo++, pn.getName());
				SetCell(row, columnNo++, unit.getName());
				SetCell(row, columnNo++, whdPn.getProducedNum());
			}
			
			autoSizeColumn(sheet);
			wb.write(out);
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(out != null) {
				try {
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	public void writeDeliveryBOM(List<WareHouseDeliveryBOMModel> list, String tempFilePath) {
		OutputStream out = null;
		int rowNo = 0;
		int columnNo = 0;
		try {
			out = new FileOutputStream(tempFilePath);
			 
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			initStyle(wb);
			
			Row row = sheet.createRow(rowNo++);
			SetCell(row, columnNo++, "K", true);
			SetCell(row, columnNo++, "品番", true);
			SetCell(row, columnNo++, "名称", true);
			SetCell(row, columnNo++, "单位", true);
			SetCell(row, columnNo++, "出库数量", true);
			SetCell(row, columnNo++, "关联订单", true);
			
			for(WareHouseDeliveryBOMModel whdBOM : list) {
				BOMModel bom = whdBOM.getBom();
				UnitModel unit = bom.getUnit();
				
				row = sheet.createRow(rowNo++);
				
				columnNo = 0;
				SetCell(row, columnNo++, bom.getType()==BOMType.RAW.ordinal()?"原":"包");
				SetCell(row, columnNo++, bom.getPn());
				SetCell(row, columnNo++, bom.getName());
				SetCell(row, columnNo++, unit.getName());
				SetCell(row, columnNo++, whdBOM.getNum());
				SetCell(row, columnNo++, whdBOM.getWareHouseDelivery().getNo());
			}
			
			autoSizeColumn(sheet);
			wb.write(out);
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(out != null) {
				try {
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	public void writeEntryBOM(List<WareHouseEntryBOMModel> list, String tempFilePath) {
		OutputStream out = null;
		int rowNo = 0;
		int columnNo = 0;
		try {
			out = new FileOutputStream(tempFilePath);
			 
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			initStyle(wb);
			
			Row row = sheet.createRow(rowNo++);
			SetCell(row, columnNo++, "K", true);
			SetCell(row, columnNo++, "品番", true);
			SetCell(row, columnNo++, "名称", true);
			SetCell(row, columnNo++, "单位", true);
			SetCell(row, columnNo++, "入库数量", true);
			SetCell(row, columnNo++, "关联订单", true);
			
			for(WareHouseEntryBOMModel wheBOM : list) {
				BOMModel bom = wheBOM.getBom();
				UnitModel unit = bom.getUnit();
				
				row = sheet.createRow(rowNo++);
				
				columnNo = 0;
				SetCell(row, columnNo++, bom.getType()==BOMType.RAW.ordinal()?"原":"包");
				SetCell(row, columnNo++, bom.getPn());
				SetCell(row, columnNo++, bom.getName());
				SetCell(row, columnNo++, unit.getName());
				SetCell(row, columnNo++, wheBOM.getNum());
				SetCell(row, columnNo++, wheBOM.getWareHouseEntry().getNo());
			}
			
			autoSizeColumn(sheet);
			wb.write(out);
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(out != null) {
				try {
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
