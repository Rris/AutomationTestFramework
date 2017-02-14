package fsoft.com.vn.automationtestframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadScript.
 */
public class ReadScript {

	/**
	 * Read excel data.
	 *
	 * @param file
	 *            the file
	 * @param numColumn
	 *            the num column
	 * @return the object[][]
	 */
	@SuppressWarnings("deprecation")
	public static Object[][] readExcelData(List<File> files, int numColumn) throws IOException {

		Object[][] arrayExcelData = null;
		List<String> listInputParam;
		List<String> listOutputParam;
		int length = 0;
		int k = 0;
		for (File url : files) {
			FileInputStream fileInputStream = new FileInputStream(url);

			// Create Workbook instance holding reference to .xlsx file
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet("TC01_UI");

			length += sheet.getLastRowNum();
		}
		arrayExcelData = new Object[length][numColumn];
		for (File url : files) {
			try {
				FileInputStream fileInputStream = new FileInputStream(url);

				// Create Workbook instance holding reference to .xlsx file
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheet("TC01_UI");

				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();
				
				String titleScript;
				Cell cell;
				int numInputParam;
				int numOutputParam;
				String title;
				for (int i = 0; i < sheet.getLastRowNum(); i++) {
					
					for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
						titleScript = String.valueOf(sheet.getRow(0).getCell(j)).trim();
						cell = sheet.getRow(i + 1).getCell(j);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						switch (titleScript) {
						case "Keyword":
							arrayExcelData[k][0] = cell.getStringCellValue();
							break;
						case "Locator ID":
							arrayExcelData[k][1] = cell.getStringCellValue();
							break;
						case "Locator String":
							arrayExcelData[k][2] = cell.getStringCellValue();
							break;
						case "Input Value":
							arrayExcelData[k][3] = cell.getStringCellValue();

							break;
						case "Row #":
							arrayExcelData[k][4] = cell.getStringCellValue();
							break;
						case "Column #":
							arrayExcelData[k][5] = cell.getStringCellValue();
							break;
						case "Input Query":
							arrayExcelData[k][6] = cell.getStringCellValue();
							break;
						case "Expected":
							arrayExcelData[k][7] = cell.getStringCellValue();
							break;
						case "#Input Param":
							if (!"".equals(cell.getStringCellValue())) {
								listInputParam = new ArrayList<String>();
								numInputParam = Integer.parseInt(cell.getStringCellValue());
								for (int j2 = 1; j2 <= numInputParam; j2++) {
									title = String.format("Input Param %s", j2);
									for (int j3 = 0; j3 < sheet.getRow(0).getLastCellNum(); j3++) {
										titleScript = String.valueOf(sheet.getRow(0).getCell(j3)).trim();
										cell = sheet.getRow(i + 1).getCell(j3);
										cell.setCellType(Cell.CELL_TYPE_STRING);
										if (title.equals(titleScript)) {
											listInputParam.add(cell.getStringCellValue());
											break;
										}
									}
								}
								arrayExcelData[k][8] = listInputParam;
							} else {
								arrayExcelData[k][8] = new ArrayList<String>();
							}
							break;
						case "#Output Param":
							if (!"".equals(cell.getStringCellValue())) {
								listOutputParam = new ArrayList<String>();
								numOutputParam = Integer.parseInt(cell.getStringCellValue());
								for (int j2 = 1; j2 <= numOutputParam; j2++) {
									title = String.format("Output Param %s", j2);
									for (int j3 = 0; j3 < sheet.getRow(0).getLastCellNum(); j3++) {
										titleScript = String.valueOf(sheet.getRow(0).getCell(j3)).trim();
										cell = sheet.getRow(i + 1).getCell(j3);
										cell.setCellType(Cell.CELL_TYPE_STRING);
										if (title.equals(titleScript)) {
											listOutputParam.add(cell.getStringCellValue());
											break;
										}
									}
								}
								arrayExcelData[k][9] = listOutputParam;
							} else {
								arrayExcelData[k][9] = new ArrayList<String>();
							}
							break;
						default:
							break;
						}
					}
					k++;
				}

				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return arrayExcelData;
	}

	/**
	 * Store data return.
	 *
	 * @param file
	 *            the file
	 * @param rowNumber
	 *            the row number
	 * @param outputParam
	 *            the output param
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void store_data_return(File file, int rowNumber, List<String> outputParam)
			throws FileNotFoundException, IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheet("TC01_UI");
		int colNumber = sheet.getRow(0).getLastCellNum();
		String titleScript;
		String title;
		int lengthOutput = outputParam.size();
		Cell cell;
		int indexOP = 1;
		for (int i = 0; i < colNumber; i++) {
			titleScript = String.valueOf(sheet.getRow(0).getCell(i)).trim();
			if ("#Output Param".equals(titleScript)) {
				cell = sheet.getRow(rowNumber).getCell(i);
				cell.setCellValue(String.valueOf(lengthOutput));
				break;
			}
		}
		for (String item : outputParam) {
			title = String.format("Output Param %s", indexOP);
			for (int i = 0; i < colNumber; i++) {
				titleScript = String.valueOf(sheet.getRow(0).getCell(i)).trim();
				if (title.equals(titleScript)) {
					cell = sheet.getRow(rowNumber).getCell(i);
					cell.setCellValue(item);
					break;
				}
			}
			indexOP++;
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Map<String, String>> load_multi_data(File file, String expected) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		// Create Workbook instance holding reference to .xlsx file
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheet(expected);
		Cell cell;

		Map<String, String> mapMultiData;
		List<Map<String, String>> listMultiData = new ArrayList<Map<String, String>>();
		Map<String, String> mapTitle;
		List<Map<String, String>> listTitle = new ArrayList<Map<String, String>>();
		int lengthList = sheet.getLastRowNum();
		int numCol = sheet.getRow(0).getLastCellNum();
		int indexRun = 0;
		String titleMultiData;
		for (int i = 0; i < numCol; i++) {
			titleMultiData = String.valueOf(sheet.getRow(0).getCell(i)).trim();
			if ("Run".equals(titleMultiData)) {
				indexRun = i;
			} else {
				mapTitle = new HashMap<String, String>();
				mapTitle.put("index", String.valueOf(i));
				mapTitle.put("title", titleMultiData);
				listTitle.add(mapTitle);
			}
		}
		
		for (int i = 0; i < lengthList; i++) {
			if ("Y".equals(String.valueOf(sheet.getRow(i + 1).getCell(indexRun)).trim())) {
				mapMultiData = new HashMap<String, String>();
		 		for (Map<String, String> map : listTitle) {
					cell = sheet.getRow(i + 1).getCell(Integer.parseInt(map.get("index")));
					cell.setCellType(Cell.CELL_TYPE_STRING);
					mapMultiData.put(map.get("title"), cell.getStringCellValue());
				}
				listMultiData.add(mapMultiData);
			}
		}
		return listMultiData;
	}
	
	public static void main(String[] args) throws IOException {
		/*List<Map<String, String>> l = load_multi_data(new File("D:\\Selenium\\Data\\test-data-1.xlsx"), "Multi_Data");
		for (Map<String, String> map : l) {
			System.out.println(map.get("Search"));
		}*/
		
	}
}
