package com.agile.EventAction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.agile.Entries.KJBRequestDetail;
import com.agile.Utils.ReadExcel;
import com.agile.Utils.loginClass;
import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.FileFolderConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IFileFolder;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ITwoWayIterator;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class KJBRequestDetailCheck_1025 implements IEventAction{
	
	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	
	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo request) {
		IObjectEventInfo objectEventInfo = (IObjectEventInfo) request;
		ActionResult actionResult = null;
		String msg = "";
		try {
			IDataObject dataObject = objectEventInfo.getDataObject();
			IChange change = (IChange) dataObject;
			msg = checkDetailSap(change,session);
			if("".equals(msg) || msg == null) {
				actionResult = new ActionResult(0, "OK");
			}else {
				actionResult = new ActionResult(-1, new Exception(msg));
			}
		} catch (APIException e) {
			e.printStackTrace();
		}
		return new EventActionResult(request,actionResult);
	}
	
	
	public static void main(String[] args) throws Exception {
		loginClass login = new loginClass();
		IAgileSession session = login.login();
		IChange change = (IChange) session.getObject(IChange.OBJECT_TYPE, "KJB0000008386");
		String msg = checkDetailSap(change,session);
		
		System.out.println("Main:"+ msg);
	}
	
	public static String checkDetailSap(IChange change,IAgileSession session) throws APIException {
		String msg = "";
		ITable fileTable = change.getTable(ChangeConstants.TABLE_ATTACHMENTS);
		ITwoWayIterator it = fileTable.getTableIterator();
		InputStream content = null;
		while (it.hasNext()) {
			IRow row = (IRow) it.next();
			String fileName = row.getValue(Integer.valueOf(1046)).toString();
			if ("KS进口报关申请单明细.xls".equals(fileName)) {
				String floderNumber = row.getValue(Integer.valueOf(6038)).toString();
				IFileFolder folder = (IFileFolder)session.getObject(IFileFolder.OBJECT_TYPE,floderNumber);
				ITable folderTable = folder.getTable(FileFolderConstants.TABLE_FILES);
				Iterator ite1 = folderTable.iterator();
				while(ite1.hasNext()) {
					IRow row1 = (IRow) ite1.next();
					String fileName_1 = row1.getValue(6303).toString();
					if("KS进口报关申请单明细.xls".equals(fileName_1)) {
						content = ((IAttachmentFile) row1).getFile();
					}
				}
			}
		}
		if (content == null) {
			msg = "请确认进口报关明细表名，格式是否正确或已上传！";
		} else {
			List<KJBRequestDetail> result = ReadExcel.readExcel(content);			
			try {
				content.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			change.refresh();
			msg = checkSAPDetail(result);
		}
		return msg;
	}
		
	public static String checkSAPDetail(List<KJBRequestDetail> list) {
		JCoDestination destination = null;
		String msg = "";
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
			JCoFunction function = destination.getRepository().getFunction("ZAGL_PO_CU_CHECK");
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
			msg += e.getMessage();
		}
		return msg;
	}
}
