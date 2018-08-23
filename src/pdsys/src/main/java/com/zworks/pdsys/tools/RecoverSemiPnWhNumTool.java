package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.common.enumClass.BOMType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

/*
 * #67 导致半成品的现场数量多计算了，此为数据修正恢复程序，按理在release后只使用一次
 * */
@Component
@Scope("prototype")
public class RecoverSemiPnWhNumTool {
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private PnService pnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	
	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String end) throws InvalidFormatException, IOException{
		WareHouseEntrySemiPnModel semiePn = new WareHouseEntrySemiPnModel();
		List<WareHouseEntrySemiPnModel> whEntrySemiPns = wareHouseEntrySemiPnService.queryList(semiePn);
		
		Map<Integer, Float> usedBOM = new HashMap<Integer, Float>();
		for(WareHouseEntrySemiPnModel whEntrySemiPn : whEntrySemiPns) {
			PnModel pn = whEntrySemiPn.getPn();
			PnClsModel pnCls = whEntrySemiPn.getPnClsRel().getPnCls();
			pn = pnService.queryOne(pn);
			
			List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
			if(pnClsRels.size() == 1) {
				continue;//只有一个子类，不会计算错误，不做修正
			}
			
			List<PnBOMRelModel> pnBOMRels = pn.getPnBOMRels();
			if(pnBOMRels.size() == 0) {
				continue;//没有共通材，也不会计算错误
			}
			
			float count = 0;
			float sum = 0;
			for(PnPnClsRelModel pnClsRel : pnClsRels) {
				sum += pnClsRel.getNum();
				
				PnClsModel pnCls2 = pnClsRel.getPnCls();
				if(pnCls2.getId() == pnCls.getId()) {
					count = pnClsRel.getNum();
				}
			}
			
			for(PnBOMRelModel pnBOMRel : pnBOMRels) {
				BOMModel bom = pnBOMRel.getBom();
				if(bom.getType() == BOMType.RAW.ordinal()) {
					//计算原材(错误)
					float usedNumNG = pnBOMRel.getUseNum() / count * whEntrySemiPn.getNum(); //Release时不良品机能尚未引入，所以不计算不良品
					float usedNumOK = pnBOMRel.getUseNum() / sum * whEntrySemiPn.getNum(); //Release时不良品机能尚未引入，所以不计算不良品
					
					if(usedBOM.containsKey(bom.getId())) {
						usedBOM.put(bom.getId(), usedBOM.get(bom.getId()) + usedNumOK - usedNumNG);
					} else {
						usedBOM.put(bom.getId(), usedNumOK - usedNumNG);
					}
				}
			}
		}
		
		FileWriter writer=new FileWriter("c:/pdsys/recoversemipnwhnum.log");
		writer.write("BOMID\tBOMNAME\tWHBOM\tWHBOMEDITED\n");
		
		for(int id: usedBOM.keySet()) {
			BOMModel bom = new BOMModel();
			bom.setId(id);
			
			WareHouseBOMModel whBOM = new WareHouseBOMModel();
			whBOM.setBom(bom);
			
			whBOM = wareHouseBOMService.queryOne(whBOM);

			float deliveryRemainingNum = whBOM.getDeliveryRemainingNum();
			float toDeliveryRemainingNum = deliveryRemainingNum - usedBOM.get(id);
			
			whBOM.setDeliveryRemainingNum(toDeliveryRemainingNum);
			whBOM.putFilterCond("UPDATE_DELIVERYREMAINNUM", true);
			wareHouseBOMService.update(whBOM);
			
			writer.write(String.format("%s\t%s\t%s\t%s\n",
					id, 
					whBOM.getBom().getName(),
					deliveryRemainingNum,
					toDeliveryRemainingNum));
		}
		writer.close();
		return true;
	}
}
