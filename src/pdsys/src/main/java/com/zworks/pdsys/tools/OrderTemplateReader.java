package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.CustomerModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.CustomerService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.UserService;

@Component
public class OrderTemplateReader {
	@Autowired
    private CustomerService customerService;
	@Autowired
	private UserService userService;
	@Autowired
	private PnService pnService;
	
	public OrderModel read(String filePath) {
		int rowNo = 0;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null) {
				throw new PdsysException("没找到sheet");
			}
			
			OrderModel order = new OrderModel();
			List<OrderPnModel> orderPns = new ArrayList<OrderPnModel>();
			boolean lastRow = false;
			for(rowNo = 0; rowNo <= sheet.getLastRowNum() && !lastRow; rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				//订单编号, 顾客名称, 下单时间, 交货期限, 责任者, 备注
				switch(rowNo)
				{
				case 0:
					String no = ExcelUtils.getCellValue(row.getCell(1));
					if(no == null || no.isEmpty()) {
						throw new PdsysException("订单编号未填写");
					}
					order.setNo(no);
					break;
				case 1:
					String customName = ExcelUtils.getCellValue(row.getCell(1));
					CustomerModel customer = new CustomerModel();
					customer.setName(customName);
					customer = customerService.queryOne(customer);
					if(customer == null) {
						throw new PdsysException("顾客名称未填写或者不存在");
					}
					order.setCustomer(customer);
					break;
				case 2:
				{
					String orderDate = ExcelUtils.getCellValue(row.getCell(1));
					if(!StringUtils.isNullOrEmpty(orderDate)) {
						Date date = DateUtils.parse(orderDate);
						if(date == null) {
							throw new PdsysException("日期格式不正确");
						}
						order.setOrderDate(date);
					}
					break;
				}
				case 3:
				{
					String shipDate = ExcelUtils.getCellValue(row.getCell(1));
					if(!StringUtils.isNullOrEmpty(shipDate)) {
						Date date = DateUtils.parse(shipDate);
						if(date == null) {
							throw new PdsysException("日期格式不正确");
						}
						order.setShipDate(date);
					}
					break;
				}
				case 4:
					String userName = ExcelUtils.getCellValue(row.getCell(1));
					UserModel user = new UserModel();
					user.setName(userName);
					user = userService.queryOne(user);
					if(user == null) {
						throw new PdsysException("责任者未填写或者不存在");
					}
					order.setUser(user);
					break;
				case 5:
					String memo = ExcelUtils.getCellValue(row.getCell(1));
					order.setComment(memo);
					break;
				case 6:
				case 7:
					break;
				default:
					String strPn = ExcelUtils.getCellValue(row.getCell(0));
					if(StringUtils.isNullOrEmpty(strPn)) {
						lastRow = true;
						break;
					}
					Float num = StringUtils.toFloat(ExcelUtils.getCellValue(row.getCell(2)));
					if(StringUtils.isNullOrEmpty(strPn) || num == 0){
						throw new PdsysException("JAN或者订购量不正确");
					}
					
					PnModel pn = new PnModel();
					pn.setPn(strPn);
					pn = pnService.queryOne(pn);
					if(pn == null) {
						throw new PdsysException("Jan不存在:" + strPn);
					}
					
					OrderPnModel oPn = new OrderPnModel();
					oPn.setPn(pn);
					oPn.setNum(num);
					
					orderPns.add(oPn);
					break;
				}
			}
			
			order.setOrderPns(orderPns);
			return order;
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
