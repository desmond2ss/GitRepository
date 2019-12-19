package com.agile.EventAction;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class KJBRequestDetailImport implements IEventAction {

	public static void main(String[] args) throws Exception {
		loginClass login = new loginClass();
		IAgileSession session = login.login();
		IChange change = (IChange) session.getObject(IChange.OBJECT_TYPE, "KJB0000008385");
		change.setValue(Integer.valueOf(2000025512), "");
		// String fullFileName = "D:/KS进口报关明细.xls";
		// InputStream is = new FileInputStream(fullFileName);

		ITable fileTable = change.getTable(ChangeConstants.TABLE_ATTACHMENTS);
		ITwoWayIterator it = fileTable.getTableIterator();
		String message = "";
		InputStream content = null;
		while (it.hasNext()) {
			IRow row = (IRow) it.next();
			String fileName = row.getValue(Integer.valueOf(1046)).toString();
			if ("KS进口报关申请单明细.xls".equals(fileName)) {
				content = ((IAttachmentFile) row).getFile();
			}
		}
		if (content == null) {
			message = "请确认进口明细表名，格式是否正确或已上传！";
		} else {
			String result = readExcel(content);
			change.setValue(Integer.valueOf(2000025512), result);
			content.close();
		}
		System.out.println(message);
	}

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo request) {
		IObjectEventInfo objectEventInfo = (IObjectEventInfo) request;
		ActionResult actionResult = null;
		String message = "";
		try {
			IDataObject dataObject = objectEventInfo.getDataObject();
			IChange change = (IChange) dataObject;
			change.setValue(Integer.valueOf(2000025512), "");
			ITable fileTable = change.getTable(ChangeConstants.TABLE_ATTACHMENTS);
			ITwoWayIterator it = fileTable.getTableIterator();
			InputStream content = null;
			while (it.hasNext()) {
				IRow row = (IRow) it.next();
				String fileName = row.getValue(Integer.valueOf(1046)).toString();
				if ("KS进口报关申请单明细.xls".equals(fileName)) {
					content = ((IAttachmentFile) row).getFile();
				}
			}
			if (content == null) {
				message = "请确认进口报关明细表名，格式是否正确或已上传！";
			} else {
				String result = readExcel(content);
				change.setValue(Integer.valueOf(2000025512), result);
				try {
					content.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				message = "进口报关明细已生成！";
			}
			System.out.println(message);
		} catch (APIException e) {
			e.printStackTrace();
		}
		actionResult = new ActionResult(0,message);
		return new EventActionResult(request,actionResult);
	}

	public static String readExcel(InputStream in) {
		HSSFWorkbook wb;
		StringBuffer sb = null;
		try {
			wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			int row = sheet.getLastRowNum();
			sb = new StringBuffer(
					"<table border='1' cellspacing='0' cellpadding='0'><tr><th>Agile签核单号</th><th>流水码</th><th>采购凭证号</th><th>行项目编号</th><th>物料</th>"
							+ "<th>采购订单数量</th><th>原产国</th><th>净重</th><th>总价</th><th>货币码</th></tr>");
			System.out.println("row:"+row);
			for (int i = 1; i <= row; i++) {
				HSSFRow rowCell = sheet.getRow(i);
				HSSFCell cell1 = rowCell.getCell(0);
				cell1.setCellType(cell1.CELL_TYPE_STRING);
				HSSFCell cell2 = rowCell.getCell(1);
				cell2.setCellType(cell2.CELL_TYPE_STRING);
				HSSFCell cell3 = rowCell.getCell(2);
				cell3.setCellType(cell3.CELL_TYPE_STRING);
				HSSFCell cell4 = rowCell.getCell(3);
				cell4.setCellType(cell4.CELL_TYPE_STRING);
				HSSFCell cell5 = rowCell.getCell(4);
				cell5.setCellType(cell5.CELL_TYPE_STRING);
				HSSFCell cell6 = rowCell.getCell(5);
				cell6.setCellType(cell6.CELL_TYPE_STRING);
				HSSFCell cell7 = rowCell.getCell(6);
				cell7.setCellType(cell7.CELL_TYPE_STRING);
				HSSFCell cell8 = rowCell.getCell(7);
				cell8.setCellType(cell8.CELL_TYPE_STRING);
				HSSFCell cell9 = rowCell.getCell(8);
				cell9.setCellType(cell9.CELL_TYPE_STRING);
				HSSFCell cell10 = rowCell.getCell(9);
				cell10.setCellType(cell10.CELL_TYPE_STRING);
				sb.append("<tr><td>" + cell1 + "</td><td>" + cell2 + "</td><td>" + cell3 + "</td><td>" + cell4
						+ "</td><td>" + cell5 + "</td><td>" + cell6 + "</td><td>" + cell7 + "</td><td>" + cell8
						+ "</td><td>" + cell9 + "</td><td>" + cell10 + "</td><td></tr>");
			}
			sb.append("</table>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
