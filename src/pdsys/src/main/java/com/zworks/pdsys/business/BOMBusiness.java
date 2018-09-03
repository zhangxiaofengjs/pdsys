package com.zworks.pdsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.PnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/09/03
 */
@Service
public class BOMBusiness {
	@Autowired
	private BOMService bomService;
	@Autowired
	private PnService pnService;
	public void update(BOMModel bom) {
		BOMModel b = new BOMModel();
		b.setId(bom.getId());
		b = bomService.queryOne(b);
		if(b == null) {
			throw new PdsysException("该品番不存在");
		}
		
		if(b.getType() != bom.getType()) {
			//@todo 因为涉及到原包材消耗的计算，暂时只能更新未使用过的BOM种类
			List<PnModel> pns = pnService.queryByBOMId(bom.getId());
			if(!ListUtils.isNullOrEmpty(pns)) {
				String str = "";
				for(PnModel pn : pns) {
					str += "<br>" + pn.getPn();
				}
				throw new PdsysException("该品番已经和品目关联:" + str);
			}
		}
		
		bomService.update(bom);
	}
}
