package com.zworks.pdsys.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.PnService;

/**
 * @author zhangxiaofeng
 * 成品半成品模板读入功能,Excel 分5列： 其中成品数量1个编号只需输入1次数量，其余空白
 * 编号	货名	本体名	成品数量	半成品数量
 * 4978446505051	ドット柄ペーパーカップ20個	水点印刷杯 大红	0	20700
 * 4978446505051	ドット柄ペーパーカップ20個	水点印刷杯 蓝色		45800
 * 4978446505051	ドット柄ペーパーカップ20個	水点印刷杯 绿色		128310
 * 4978446505051	ドット柄ペーパーカップ20個	水点印刷杯 玖红		92810
 */
@Component
@Scope("prototype")
public class WareHousePnSemiPnTemplateReader {
	public static final int TYPE_PN = 0;
	public static final int TYPE_SEMIPN = 0;
	
	@Autowired
	private PnService pnService;
	
	private Map<String, WareHousePnModel> whpns = new HashMap<String, WareHousePnModel>();
	private Map<String, WareHouseSemiPnModel> whsemipns = new HashMap<String, WareHouseSemiPnModel>();
	
	/**
	 * @param filePath
	 * @param type WareHousePnSemiPnTemplateReader#TYPE_PN,TYPE_SEMIPN
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void read(String filePath, int type) {
		whpns.clear();
		whsemipns.clear();

		int rowNo = 0;
		try {
			InputStream is = null;
			
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null) {
				throw new PdsysException("没找到 sheet");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String strpn = ExcelUtils.getCellValue(row.getCell(idx++));
	           	@SuppressWarnings("unused")
				String name = ExcelUtils.getCellValue(row.getCell(idx++));
	            String clsName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String strnum = ExcelUtils.getCellValue(row.getCell(idx++));
	            String strseminum = ExcelUtils.getCellValue(row.getCell(idx++));

	            PnModel pn = new PnModel();
	            if("".equals(strpn)) {
	            	break;
	            }
	            
	            pn.setPn(strpn);
	            pn  = pnService.queryOne(pn);
	            if(pn == null) {
	            	throw new PdsysException("不存在的编号:" + strpn);
	            }
		            
	            if(type == TYPE_PN) {
		            float num = -1;
		            if(!"".equals(strnum)) {
		            	num = Float.parseFloat(strnum);
		            }
		            
		            if(num != -1) {
		            	if(whpns.containsKey(strpn)) {
		            		throw new PdsysException("重复编号：" + strpn);
		            	}
		            	
			            WareHousePnModel whPn = new WareHousePnModel();
			            whPn.setPn(pn);
			            whPn.setProducedNum(num);
			            whpns.put(strpn, whPn);
		            }
	            }
	            
	            if(type == TYPE_SEMIPN) {
		            float seminum = -1;
		            if(!"".equals(strseminum)) {
		            	seminum = Float.parseFloat(strseminum);
		            } else {
		            	if(!StringUtils.isNullOrEmpty(clsName)) {
		            		throw new PdsysException("未设定本体数量");
		            	}
		            }
		            
		            if(seminum != -1) {
		            	List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
		            	PnPnClsRelModel pnclsRel = null;
		            	for(PnPnClsRelModel pnClsRelTmp : pnClsRels) {
		            		String defName = pnClsRelTmp.getPnCls().getName().replace(" ", "").replace("　", "");
		            		String defCmpName = clsName.replace(" ", "").replace("　", "");
		            		if(defName.equals(defCmpName)) {
		            			pnclsRel = pnClsRelTmp;
		            			break;
		            		}
		            	}
		            	
		            	if(pnclsRel == null) {
		            		throw new PdsysException(strpn + "中不存在本体：" + clsName);
		            	}
		            	
		            	String key = strpn+"@@@" + clsName;
		            	if(whsemipns.containsKey(key)) {
		            		throw new PdsysException("重复本体：" + strpn + " " + clsName);
		            	}
		            	
			            WareHouseSemiPnModel whsemipn = new WareHouseSemiPnModel();
			            whsemipn.setPn(pn);
			            whsemipn.setPnClsRel(pnclsRel);
			            whsemipn.setNum(seminum);
			            whsemipns.put(key, whsemipn);
		            }
	            }
	        }
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}

	public Collection<WareHousePnModel> getWhpns() {
		return whpns.values();
	}

	public Collection<WareHouseSemiPnModel> getWhsemipns() {
		return whsemipns.values();
	}
}
