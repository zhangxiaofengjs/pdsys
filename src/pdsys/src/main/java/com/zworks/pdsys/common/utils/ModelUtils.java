package com.zworks.pdsys.common.utils;

import java.util.List;

import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.UnitModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/30
 */
public class ModelUtils {
	public static int pnRowSize(PnModel pn) {
		List<PnBOMRelModel> pnBOMRels = pn.getPnBOMRels();
		List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
		
		if(ListUtils.isNullOrEmpty(pnClsRels)) {
			if(ListUtils.isNullOrEmpty(pnBOMRels)) {
				//无子类,共通材，自己占一行
				return 1;
			}
			
			return pnBOMRels.size();
		}
		
		int count = ListUtils.isNullOrEmpty(pnBOMRels)?1:pnBOMRels.size();
		for(PnPnClsRelModel pnClsRel : pnClsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();
			List<PnClsBOMRelModel> bomRels = pnCls.getPnClsBOMRels();
			
			if(ListUtils.isNullOrEmpty(bomRels)) {
				//无原包材，按子类占一行算
				count++;
			} else {
				count += bomRels.size();
			}
		}
		
		return count;
	}
	
	public static String unitStr(UnitModel unit) {
		if(unit == null) {
			return "";
		}
		
		if(StringUtils.isNullOrEmpty(unit.getSubName())) {
			return unit.getName();
		}
		
		return unit.getName() + "(" + unit.getRatio() + unit.getSubName() + ")";
	}
}
