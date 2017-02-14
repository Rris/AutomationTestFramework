package fsoft.com.vn.automationtestframework;

import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

// TODO: Auto-generated Javadoc
/**
 * The Class TestNGClass.
 */
public class TestNGClass {

	/** The se impl. */
	SeleniumImpl seImpl;

	/** The url. */
	List<File> url;

	/** The i. */
	int index = 0;

	int i = 0;

	boolean flag = false;

	boolean flag1 = false;

	List<Map<String, String>> listMultiData;

	List<Object[]> listLoop;

	/**
	 * Verify automation test.
	 *
	 * @param keyWord
	 *            the key word
	 * @param locatorID
	 *            the locator ID
	 * @param locatorString
	 *            the locator string
	 * @param inputValue
	 *            the input value
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @param listInput
	 *            the list input
	 * @param listOutput
	 *            the list output
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws NumberFormatException
	 *             the number format exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test(dataProvider = "dp")
	public void verifyAutomationTest(Object[] object)
			throws FileNotFoundException, IOException, NumberFormatException, InterruptedException {
		String keyWord = (String) object[0];
		String locatorID = (String) object[1];
		String locatorString = (String) object[2];
		String inputValue = (String) object[3];
		String row = (String) object[4];
		String col = (String) object[5];
		String inputQuery = (String) object[6];
		String expected = (String) object[7];
		List<String> listInput = (List<String>) object[8];
		List<String> listOutput = (List<String>) object[9];
		if (flag1 && listMultiData.get(index).get(inputValue) != null) {
			inputValue = listMultiData.get(index).get(inputValue);
		}
		switch (keyWord) {
		case "setup_browser":
			seImpl.setup_browser(inputValue);
			break;
		case "explicit_wait":
			Assert.assertTrue(seImpl.explicit_wait(inputValue));
			break;
		case "load_page":
			Assert.assertTrue(seImpl.load_page(inputValue));
			break;
		case "click_element":
			Assert.assertTrue(seImpl.click_element(locatorID, locatorString));
			break;
		case "enter_input":
			Assert.assertTrue(seImpl.enter_input(locatorID, locatorString, inputValue));
			break;
		case "select_drop_down":
			Assert.assertTrue(seImpl.select_drop_down(locatorID, locatorString, inputValue));
			break;
		case "verify_element_present":
			Assert.assertTrue(seImpl.verify_element_present(locatorID, locatorString, inputValue));
			break;
		case "enter_input_hidden":
			Assert.assertTrue(seImpl.enter_input_hidden(locatorID, locatorString, inputValue));
			break;
		case "count_drop_down":
			Assert.assertTrue(seImpl.count_drop_down(locatorID, locatorString, inputValue));
			break;
		case "verify_text":
			Assert.assertTrue(seImpl.verify_text(locatorID, locatorString, inputValue));
			break;
		case "verify_field_text":
			Assert.assertTrue(seImpl.verify_field_text(locatorID, locatorString, inputValue));
			break;
		case "verify_text_contains":
			Assert.assertTrue(seImpl.verify_text_contains(locatorID, locatorString, inputValue));
			break;
		case "verify_table_cell_text":
			Assert.assertTrue(seImpl.verify_table_cell_text(locatorID, locatorString, inputValue, row, col));
			break;
		case "table_row_sel":
			List<String> l_table_row_sel = new ArrayList<String>();
			l_table_row_sel.add(String.valueOf(seImpl.table_row_sel(locatorID, locatorString, listInput.get(0), col)));
			/*
			 * ReadScript.store_data_return(new File(url), i, l_table_row_sel);
			 */
			break;
		case "store_cell_data":
			List<String> l_store_cell_data = new ArrayList<String>();
			l_store_cell_data
					.add(String.valueOf(seImpl.store_cell_data(locatorID, locatorString, col, listInput.get(0))));
			/*
			 * ReadScript.store_data_return(new File(url), i,
			 * l_store_cell_data);
			 */
			break;
		case "switch_alert_ok":
			Assert.assertTrue(seImpl.switch_alert_ok());
			break;
		case "compare_JSAlert_msg":
			Assert.assertTrue(seImpl.compare_JSAlert_msg(inputValue));
			break;
		case "page_scroll":
			Assert.assertTrue(seImpl.page_scroll(inputValue));
			break;
		case "screen_shot":
			Assert.assertTrue(seImpl.screen_shot(inputValue));
			break;
		case "wait_time":
			seImpl.wait_time(inputValue);
			break;
		case "store_data":
			String result = seImpl.store_data(locatorID, locatorString);
			List<String> l_store_data = new ArrayList<String>();
			l_store_data.add(result);
			 ReadScript.store_data_return(url.get(0), i, l_store_data); 
			break;
		case "load_multi_data":
			listMultiData = seImpl.load_multi_data(inputQuery, expected);
			break;
		case "start_loop":
			flag = true;
			flag1 = true;
			listLoop = new ArrayList<Object[]>();
			break;
		case "end_loop":
			flag = false;
			loop();
			break;
		default:
			break;
		}
		if (flag) {
			listLoop.add(object);
		}
		i++;
	}

	/**
	 * Dp.
	 *
	 * @return the object[][]
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 */

	public void loop() throws IOException, NumberFormatException, InterruptedException {
		for (int i = 1; i < listMultiData.size(); i++) {
			index = i;
			for (Object[] objects : listLoop) {
				verifyAutomationTest(objects);
			}
		}
		flag1 = false;
	}



	@DataProvider
	public Object[][] dp() throws IOException {
		Object[][] arrayList = ReadScript.readExcelData(url, 10);
		return arrayList;
	}

	/**
	 * Before class.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@BeforeClass
	public void beforeClass() {
		seImpl = new SeleniumImpl();
		url = new ArrayList<File>();

		String line = "";
		try {
			FileReader fr = new FileReader("../file-selected.txt");
			BufferedReader input = new BufferedReader(fr);
			while ((line = input.readLine()) != null) {
				url.add(new File(line));
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * After class.
	 *
	 * @param result
	 *            the result
	 */
	@AfterClass
	public void afterClass() {
		try {
			File file = new File("../file-selected.txt");
				file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
