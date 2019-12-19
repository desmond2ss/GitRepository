package com.agile.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.agile.Entries.KJBRequestDetail;

public class ReadExcel {
	
	public static List<KJBRequestDetail> readExcel(InputStream in) {
		HSSFWorkbook wb;
		List<KJBRequestDetail> list = new ArrayList<KJBRequestDetail>();
		try {
			wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			int row = sheet.getLastRowNum();
			System.out.println("sheetRow:"+row);
			for (int i = 1; i <= row; i++) {
				KJBRequestDetail detail = new KJBRequestDetail();
				HSSFRow rowCell = sheet.getRow(i);
				HSSFCell cell1 = rowCell.getCell(0);
				cell1.setCellType(cell1.CELL_TYPE_STRING);
				detail.setChangeNumer(cell1.toString());
				HSSFCell cell2 = rowCell.getCell(1);
				cell2.setCellType(cell2.CELL_TYPE_STRING);
				detail.setNumber(cell2.toString());
				HSSFCell cell3 = rowCell.getCell(2);
				cell3.setCellType(cell3.CELL_TYPE_STRING);
				detail.setPONumber(cell3.toString());
				HSSFCell cell4 = rowCell.getCell(3);
				cell4.setCellType(cell4.CELL_TYPE_STRING);
				detail.setRowNumber(cell4.toString());
				HSSFCell cell5 = rowCell.getCell(4);
				cell5.setCellType(cell5.CELL_TYPE_STRING);
				detail.setItemNumber(cell5.toString());
				HSSFCell cell6 = rowCell.getCell(5);
				cell6.setCellType(cell6.CELL_TYPE_STRING);
				detail.setPOQty(cell6.toString());
				HSSFCell cell7 = rowCell.getCell(6);
				cell7.setCellType(cell7.CELL_TYPE_STRING);
				detail.setCountryCode(cell7.toString());
				HSSFCell cell8 = rowCell.getCell(7);
				cell8.setCellType(cell8.CELL_TYPE_STRING);
				detail.setWeight(cell8.toString());
				HSSFCell cell9 = rowCell.getCell(8);
				cell9.setCellType(cell9.CELL_TYPE_STRING);
				detail.setPrice(cell9.toString());
				HSSFCell cell10 = rowCell.getCell(9);
				cell10.setCellType(cell10.CELL_TYPE_STRING);
				detail.setCurrencyCode(cell10.toString());
				list.add(detail);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
