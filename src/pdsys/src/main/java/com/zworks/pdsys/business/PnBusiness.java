package com.zworks.pdsys.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.business.form.beans.RelateBOMFormBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.services.OrderPnService;
import com.zworks.pdsys.services.PnClsService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliverySemiPnService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/21
 */
@Service
public class PnBusiness {
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private OrderPnService orderPnService;
	@Autowired
	private PnService pnService;
	@Autowired
	private PnClsService pnClsService;
	@Autowired
	private WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	private WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	private WareHouseDeliverySemiPnService wareHouseDeliverySemiPnService;
	
	public void update(PnModel pn) {
		if(StringUtils.isNullOrEmpty(pn.getPn()) ||
			StringUtils.isNullOrEmpty(pn.getName())) {
			throw new PdsysException("品番或者名称不能为空");
		}
		
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = pnService.queryOne(p);
		if(p == null) {
			throw new PdsysException("该品番不存在");
		}
		
		PnModel p2 = new PnModel();
		p2.setPn(pn.getPn());
		p2 = pnService.queryOne(p2);
		if(p2 != null) {
			if(p2.getId() != pn.getId()) {
				throw new PdsysException("同品番的品目已经存在:" + pn.getPn());
			}
		}
		
		pnService.update(pn);
	}
	
	private void checkUsed(PnModel pn, boolean isCheckWhPn, boolean isCheckOrder) {
		//库存使用中？
		if(isCheckWhPn) {
			wareHousePnService.checkUsedPn(pn);
		}
		//入出库单使用中？
		wareHouseEntryPnService.checkUsedPn(pn);
		wareHouseDeliveryPnService.checkUsedPn(pn);
		
		//订单使用中？
		if(isCheckOrder) {
			List<OrderPnModel> orderpns = orderPnService.queryByPn(pn.getPn());
			if(!ListUtils.isNullOrEmpty(orderpns)) {
				throw new PdsysException(String.format("订单:%s 使用中.", orderpns.get(0).getOrder().getNo()));
			}
		}
	}

	private void checkPnClsUsed(PnClsModel pnCls) {
		wareHouseSemiPnService.checkUsedPnCls(pnCls);
		wareHouseEntrySemiPnService.checkUsedPnCls(pnCls);
		wareHouseDeliverySemiPnService.checkUsedPnCls(pnCls);
	}
	
	@Transactional
	public void delete(PnModel pn) {
		PnModel pnDb = pnService.queryOne(pn);
		if(pnDb == null) {
			throw new PdsysException("不存在的品目，刷新后再试");
		}

		//检测自己使用过？
		checkUsed(pnDb, true, true);

		//删除本体关联
		pnService.deletePnCls(pnDb);
		//删除原包材关联
		pnService.deleteBOM(pnDb);
		//删除自己
		pnService.delete(pnDb);
		
		//检测本体使用过？若不再使用删除本体
		deletePnClsCore(pnDb);
	}
	
	@Transactional
	public void updatePnCls(PnModel pn) {
		PnPnClsRelModel pnClsRel = null;//预想只更新一个本体

		//更新本体的名称和单位
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRelTmp : clsRels) {
			PnClsModel pnCls = pnClsRelTmp.getPnCls();
			pnClsService.update(pnCls);
			
			pnClsRel = pnClsRelTmp;
		}
		if(pnClsRel == null) {
			return;
		}
		
		PnModel pnDb = pnService.queryOne(pn);
		if(pnDb == null) {
			throw new PdsysException("不存在的品目，刷新后再试");
		}
		
		//修改本体使用量
		for(PnPnClsRelModel pnClsRelTmp : pnDb.getPnClsRels()) {
			if(pnClsRelTmp.getPnCls().getId() == pnClsRel.getPnCls().getId()) {
				if(pnClsRel.getNum() != pnClsRelTmp.getNum()) {
					checkUsed(pn, false, false);//因为涉及到入出库时原包材计算，所以已经使用过不能修改
					//修改配比
					pnService.updatePnCls(pn);
				}
			}
		}
	}
	
	@Transactional
	public void deletePnCls(PnModel pn) {
		//检测是否使用中，因为涉及到入出库计算则不能随便删除
		//库存原本就存在，只要不是入库进去的就OK所以不check
		checkUsed(pn, false, false);
		deletePnClsCore(pn);
	}
	
	private void deletePnClsCore(PnModel pn) {
		List<PnPnClsRelModel> clsRels = pn.getPnClsRels();
		for(PnPnClsRelModel pnClsRel : clsRels) {
			PnClsModel pnCls = pnClsRel.getPnCls();

			checkPnClsUsed(pnCls);
			pnClsService.delete(pnCls);
		}
		pnService.deletePnCls(pn);
	}
	
	public void deleteBOM(PnModel pn) {
		//检测PN有无使用，设计到入出库计算
		checkUsed(pn, false, false);
		
		pnService.deleteBOM(pn);
	}

	@Transactional
	public void changeBOM(RelateBOMFormBean formBean) {
		BOMModel bom = new BOMModel();
		bom.setId(formBean.getBomId());
		
		if("change".equals(formBean.getType())) {
			PnModel pn = new PnModel();
			pn.setId(formBean.getPnId());
			
			PnBOMRelModel pnBOM = new PnBOMRelModel();
			pnBOM.setBom(bom);
			pnBOM.setUseNum(formBean.getUseNum());
			
			List<PnBOMRelModel> bomRels = new ArrayList<PnBOMRelModel>();
			bomRels.add(pnBOM);
		
			pn.setPnBOMRels(bomRels);
			
			//删除原BOM
			deleteBOM(pn);
			
			//追加新BOM
			bom.setId(formBean.getNewBomId());
			addBOM(pn);
		}
	}

	private boolean existsBOM(PnModel pn) {
		PnModel p = new PnModel();
		p.setId(pn.getId());
		p = pnService.queryOne(pn);
		
		if(p == null) {
			return false;
		}

		List<PnBOMRelModel> bomRels = p.getPnBOMRels();
		if(bomRels == null) {
			return false;
		}
		List<PnBOMRelModel> targetBomRels = pn.getPnBOMRels();
		if(targetBomRels == null) {
			return false;
		}
		
		for(PnBOMRelModel bomRel : bomRels) {
			BOMModel bom = bomRel.getBom();
			
			//寻找同样的
			for(PnBOMRelModel tgtbomRel : targetBomRels) {
				BOMModel tmpbom = tgtbomRel.getBom();
				if(bom.getId() ==  tmpbom.getId()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void addBOM(PnModel pn) {
		if(existsBOM(pn)) {
			//已经存在该原包材 则做更新
			throw new PdsysException("已经存在该原包材");
		}
		pnService.addBOM(pn);
	}
}
