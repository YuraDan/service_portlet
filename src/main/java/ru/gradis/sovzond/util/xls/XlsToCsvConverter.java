package ru.gradis.sovzond.util.xls;

import java.io.*;
import java.util.Iterator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by donchenko-y on 18.10.16.
 */

public class XlsToCsvConverter {

	private static final Log log = LogFactoryUtil.getLog(XlsToCsvConverter.class);

	public static byte[] convertToCsv(byte[] inputFile, String ext) {
		// For storing data into CSV files
		StringBuffer data = new StringBuffer();
		Iterator<Row> rowIterator = null;
		try {
			// Get the workbook object for XLSX file
			// Get first sheet from the workbook
			switch (ext) {
				case "xls":
					rowIterator = WorkbookFactory.create(new ByteArrayInputStream(inputFile)).getSheetAt(0).iterator();
					break;
				case "xlsx":
					rowIterator = new XSSFWorkbook(new ByteArrayInputStream(inputFile)).getSheetAt(0).iterator();
			}
			Row row;
			Cell cell;
			// Iterate through each rows from first sheet
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							data.append(cell.getBooleanCellValue() + ";");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							data.append(cell.getNumericCellValue() + ";");
							break;
						case Cell.CELL_TYPE_STRING:
							data.append(cell.getStringCellValue() + ";");
							break;
						case Cell.CELL_TYPE_BLANK:
							data.append("" + ";");
							break;
						default:
							data.append(cell + ";");
					}
				}
				data.append("\r\n");
			}
			System.out.println("data = " + data.toString());
			return data.toString().getBytes();
		} catch (Exception ioe) {
			log.error(ioe);
			return null;
		}
	}

}
