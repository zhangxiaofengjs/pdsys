package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.IOUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.io.OrderTemplateReader;
import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnModel;

@Service
public class OrderService {
	@Autowired
    private OrderMapper orderMapper;
	@Autowired
    private OrderPnService orderPnService;
	@Autowired
    private FileService uploadService;
	@Autowired
	OrderTemplateReader reader;
	
	public List<OrderModel> queryList( OrderModel order ) {
		return orderMapper.queryList( order );
	}
	
	public OrderModel queryOne(OrderModel order) {
		List<OrderModel> list = queryList(order);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public void update(OrderModel order) {
		orderMapper.update( order );
	}

	public void add(OrderModel order) {
		orderMapper.add( order );
	}

	public boolean isAllPnDelivered(OrderModel o) {
		OrderModel order = queryOne(o);
		List<OrderPnModel> pns = order.getOrderPns();
		
		for(OrderPnModel pn : pns) {
			if(pn.getDeliveredNum() < pn.getNum()) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	public void importFile(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new PdsysException("选定的文件为空");
        }

        String tempPath = uploadService.saveTemp(mpFile);
        OrderModel order = null;
        try {
        	order = reader.read(tempPath);
        } catch(Exception e) {
        	throw new PdsysException(e.getMessage());
        }

        if(!SecurityContextUtils.isLoginUser(order.getUser())) {
        	throw new PdsysException("登录用户不是订单责任者！");
		}
        
        OrderModel o = new OrderModel();
        o.setNo(order.getNo());
        if(queryOne(order) != null) {
        	throw new PdsysException("已经存在的订单编号");
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
	}

	public void addAttachment(int orderId, MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行上传");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new PdsysException("选定的文件为空");
        }
		
        OrderModel o = new OrderModel();
        o.setId(orderId);
        o = queryOne(o);
        if(o == null) {
        	throw new PdsysException("不存在的订单编号");
        }
        
        String path = uploadService.saveOrderAttachment(o, mpFile);
        String name = IOUtils.fileName(path);
        o.setAttachment(name);
        o.getFilterCond().put("update_attachment", true);
        orderMapper.update(o);
	}

	public void deleteAttachment(OrderModel order) {
		OrderModel o = queryOne(order);
		if(o == null) {
        	throw new PdsysException("不存在的订单编号");
        }
		
		if(!SecurityContextUtils.isLoginUser(o.getUser())) {
        	throw new PdsysException("登录用户不是订单责任者！");
		}
		String attach = o.getAttachment();
		
		o.setAttachment(null);
        o.getFilterCond().put("update_attachment", true);
        orderMapper.update(o);
        
        o.setAttachment(attach);
		uploadService.deleteOrderAttachment(o);
	}
	
	public void downloadAttachment(int orderId, HttpServletResponse response) {
		OrderModel order = new OrderModel();
		order.setId(orderId);
		OrderModel o = queryOne(order);
		if(o == null) {
        	throw new PdsysException("不存在的订单编号");
        }
		
		String filePath = uploadService.getOrderAttachmentPath(o.getAttachment());
		uploadService.download(filePath, response);
	}
}
