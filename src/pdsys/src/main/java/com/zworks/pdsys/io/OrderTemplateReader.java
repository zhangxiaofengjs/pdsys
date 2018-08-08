package com.zworks.pdsys.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.CustomerService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.UnitService;
import com.zworks.pdsys.services.UserService;

@Component
public class OrderTemplateReader {
	@Autowired
    private CustomerService customerService;
	@Autowired
	private UserService userService;
	@Autowired
	private PnService pnService;
	@Autowired
	private UnitService unitService;
	
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
			Set<String> pnSet = new HashSet<String>();
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
					if(!pnSet.add(strPn)) {
						throw new PdsysException("重复JAN");
					}
					if(StringUtils.isNullOrEmpty(strPn)){
						throw new PdsysException("JAN不正确");
					}
					
					Float num = StringUtils.toFloat(ExcelUtils.getCellValue(row.getCell(2)));
					if(num == 0){
						throw new PdsysException("订购量不正确");
					}
					
					String priceUnitName = ExcelUtils.getCellValue(row.getCell(3));
					if(StringUtils.isNullOrEmpty(priceUnitName)){
						throw new PdsysException("价格单位不正确");
					}
					
					Float price = StringUtils.toFloat(ExcelUtils.getCellValue(row.getCell(4)));
					if(price == 0){
						throw new PdsysException("价格不正确");
					}
					
					PnModel pn = new PnModel();
					pn.setPn(strPn);
					pn = pnService.queryOne(pn);
					if(pn == null) {
						throw new PdsysException("JAN不存在:" + strPn);
					}
					
					UnitModel unit = new UnitModel();
					unit.setName(priceUnitName);
					unit = unitService.queryOne(unit);
					if(unit == null) {
						throw new PdsysException("单价单位不存在:" + priceUnitName);
					}
					
					OrderPnModel oPn = new OrderPnModel();
					oPn.setPn(pn);
					oPn.setNum(num);
					oPn.setPrice(price);
					oPn.setPriceUnit(unit);
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
