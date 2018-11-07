package com.zworks.pdsys.common.enumClass;

public class ROLE {
	public static String ADMIN = "admin";
	public static String EDIT_ORDER = "e_order";
	public static String EDIT_PURCHASE = "e_purchase";
	public static String APPROVAL_PURCHASE = "app_purchase";
	public static String EDIT_WAREHOUSE_ENTRY_PN = "e_warehouse_entry_pn";
	public static String EDIT_WAREHOUSE_ENTRY_BOM = "e_warehouse_entry_bom";
	public static String EDIT_WAREHOUSE_ENTRY_DEVICE = "e_warehouse_entry_device";
	public static String EDIT_WAREHOUSE_DELIVERY_PN = "e_warehouse_delivery_pn";
	public static String EDIT_WAREHOUSE_DELIVERY_BOM = "e_warehouse_delivery_bom";
	public static String EDIT_WAREHOUSE_DELIVERY_DEVICE = "e_warehouse_delivery_device";
	public static String EDIT_DEVICE = "e_device";
	public static String EDIT_USER = "e_user";
	public static String EDIT_MASTER = "e_master";
	public static String EDIT_WAREHOUSE_BOM = "e_warehouse_bom";
	public static String EDIT_WAREHOUSE_PN = "e_warehouse_pn";
	
	public static String[] ROLES = new String[] {
		ADMIN,
		EDIT_ORDER,
		EDIT_PURCHASE,
		APPROVAL_PURCHASE,
		EDIT_WAREHOUSE_ENTRY_PN,
		EDIT_WAREHOUSE_ENTRY_BOM,
		EDIT_WAREHOUSE_ENTRY_DEVICE,
		EDIT_WAREHOUSE_DELIVERY_PN,
		EDIT_WAREHOUSE_DELIVERY_BOM,
		EDIT_WAREHOUSE_DELIVERY_DEVICE,
		EDIT_DEVICE,
		EDIT_USER,
		EDIT_MASTER,
		EDIT_WAREHOUSE_BOM,
		EDIT_WAREHOUSE_PN,
	};
}
