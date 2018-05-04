package com.zworks.pdsys.models;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 采购单详细
 * 
 * @author ZHAI
 * @date 2018-05-04 13:22:06
 */
@Alias("purchaseBomModel")
public class PurchaseBOMModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private PurchaseModel purchase;
	private BOMModel bom;
	private float num;
	private float price;
	private SupplierModel supplier;
	
	
	public PurchaseModel getPurchase() {
		return purchase;
	}
	public void setPurchase(PurchaseModel purchase) {
		this.purchase = purchase;
	}
	public BOMModel getBom() {
		return bom;
	}
	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public SupplierModel getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}
	
}
