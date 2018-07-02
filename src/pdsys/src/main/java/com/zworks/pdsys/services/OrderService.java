package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.common.utils.UploadFileUtils;
import com.zworks.pdsys.config.UploadConfig;
import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.tools.OrderTemplateReader;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	@Autowired
    private OrderPnService orderPnService;
	@Autowired
    private UploadService uploadService;
	@Autowired
	OrderTemplateReader reader;
	
	public List<OrderModel> queryList( OrderModel order ) {
		return orderMapper.queryList( order );
	}
	
	public void update(OrderModel order) {
		orderMapper.update( order );
	}
	
	public OrderModel queryOne(OrderModel order) {
		List<OrderModel> list = queryList(order);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public void add(OrderModel order) {
		orderMapper.add( order );
	}

	public boolean isAllPnDelivered(OrderModel o) {
		OrderModel order = queryOne(o);
		List<OrderPnModel> pns = order.getOrderPns();
		
		for(OrderPnModel pn : pns) {
			if(pn.getDeliveredNum() >= pn.getNum()) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	public JSONResponse importFile(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			return JSONResponse.error("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	return JSONResponse.error("选定的文件为空");
        }

        String tempPath = uploadService.saveTemp(mpFile);
        OrderModel order = null;
        try {
        	order = reader.read(tempPath);
        } catch(Exception e) {
        	return JSONResponse.error(e.getMessage());
        }

        if(!SecurityContextUtils.isLoginUser(order.getUser())) {
			return JSONResponse.error("登录用户不是订单责任者！");
		}
        
        OrderModel o = new OrderModel();
        o.setNo(order.getNo());
        if(queryOne(order) != null) {
        	return JSONResponse.error("已经存在的订单编号");
        }
        
        List<OrderPnModel> orderPns = order.getOrderPns();
        
        add(order);

        //这边要重新new一个order，赋予OrderId，否则发生Exception
        o = new OrderModel();
        o.setId(order.getId());
        
        for(OrderPnModel oPn : orderPns) {
        	oPn.setOrder(o);
        	orderPnService.add(oPn);
        }
 
        return JSONResponse.success().put("order", order);
	}
}
