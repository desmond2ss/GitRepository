package com.agile.EventAction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.agile.Entries.KJBRequestDetail;
import com.agile.Utils.KJBRequest2FTP;
import com.agile.Utils.KJBRequest2SAP;
import com.agile.Utils.ReadExcel;
import com.agile.Utils.loginClass;
import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ITwoWayIterator;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;

public class KJBRequestDetailTransfer_1025 implements IEventAction{

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo request) {
		IObjectEventInfo objectEventInfo = (IObjectEventInfo) request;
		ActionResult actionResult = null;
		String msg = "";
		try {
			IDataObject dataObject = objectEventInfo.getDataObject();
			IChange change = (IChange) dataObject;
			msg = checkDetailFileTable(change);
			actionResult = new ActionResult(0, msg);
		} catch (APIException e) {
			e.printStackTrace();
		}
		return new EventActionResult(request,actionResult);
	}

	
	public static void main(String[] args) throws Exception {
		loginClass login = new loginClass();
		IAgileSession session = login.login();
		IChange change = (IChange) session.getObject(IChange.OBJECT_TYPE, "KJB0000008386");
		String result = checkDetailFileTable(change);
//		change.setValue(Integer.valueOf(2000003297),result);
		
	}
	
	public static String checkDetailFileTable(IChange change) throws APIException {
		String msg = "";
		ITable fileTable = change.getTable(ChangeConstants.TABLE_ATTACHMENTS);
		ITwoWayIterator it = fileTable.getTableIterator();	
		ByteArrayOutputStream baos = null;
		InputStream contentSpecial = null;
		String fileName = "";
		Boolean result = null;
		List<KJBRequestDetail> resultList = new ArrayList<KJBRequestDetail>();
		while (it.hasNext()) {
			IRow row = (IRow) it.next();
			fileName = row.getValue(Integer.valueOf(1046)).toString();
			String ftpFileName = change.getName()+"_"+fileName;
			if ("KS进口报关申请单明细.xls".equals(fileName)) {				
				InputStream content = ((IAttachmentFile) row).getFile();
				resultList = ReadExcel.readExcel(content);
				
//					baos = new ByteArrayOutputStream();  
//					byte[] buffer = new byte[1024];  
//					int len;  
//					try {
//						while ((len = content.read(buffer)) > -1 ) {  
//						    baos.write(buffer, 0, len);  
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}  
//					try {
//						baos.flush();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					InputStream content1 = new ByteArrayInputStream(baos.toByteArray());
//					result = KJBRequest2FTP.UploadFTP(ftpFileName,content1);
			}else if("其他贸易方式清单.xls".equals(fileName)) {				
					contentSpecial = ((IAttachmentFile) row).getFile();	
					result = KJBRequest2FTP.UploadFTP(ftpFileName,contentSpecial);
			}
		}
		if (baos == null) {
			msg = "请确认进口报关明细表名，格式是否正确或已上传！";
		} else{
			InputStream content2 = new ByteArrayInputStream(baos.toByteArray());
			resultList = ReadExcel.readExcel(content2);
			String msgSAP = KJBRequest2SAP.detailTransferSAP(resultList);
			msg += msgSAP + result;				
		}
			try {
				baos.close();
				contentSpecial.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		change.setValue(Integer.valueOf(2000003297),msg);
		System.out.println(msg);
		return msg;
		
	}
}
