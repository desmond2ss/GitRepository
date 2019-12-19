package com.agile.Utils;


import java.util.List;

import com.agile.Entries.KJBRequestDetail;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class KJBRequest2SAP{

	public static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
		
	public static String detailTransferSAP(List<KJBRequestDetail> list) {
		JCoDestination destination = null;
		String msg = "";
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
			JCoFunction function = destination.getRepository().getFunction("ZAGL_PO_CU_SAVE");
			if (function == null) {
				throw new RuntimeException("AGL_PO_CUSTOMS_CHECK not found in SAP.");
			}
		      System.out.println("SAP—— agile infomation send to sap system ");
		      JCoTable T_ZCTMM128 = function.getTableParameterList().getTable("T_ZCTMM128");
		      System.out.println("123");
		      for (KJBRequestDetail detail : list) {
		    	  T_ZCTMM128.appendRow();
		    	  T_ZCTMM128.setValue("SEQ", detail.getNumber());
		    	  T_ZCTMM128.setValue("EBELN", detail.getPONumber());
		    	  T_ZCTMM128.setValue("EBELP", detail.getRowNumber());
		    	  T_ZCTMM128.setValue("MATNR", detail.getItemNumber());
		    	  T_ZCTMM128.setValue("MENGE", detail.getPOQty());
		    	  T_ZCTMM128.setValue("ASEQ", detail.getChangeNumer());
		    	  T_ZCTMM128.setValue("LAND1", detail.getCountryCode());
		    	  T_ZCTMM128.setValue("NTGEW", detail.getWeight());
		    	  T_ZCTMM128.setValue("NETPR", detail.getPrice());
		    	  T_ZCTMM128.setValue("WAERS", detail.getCurrencyCode());		    	  
			}
		      function.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}
}
