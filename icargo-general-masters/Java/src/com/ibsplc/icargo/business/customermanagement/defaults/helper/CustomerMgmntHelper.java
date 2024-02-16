package com.ibsplc.icargo.business.customermanagement.defaults.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibm.icu.text.DecimalFormat;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.excel.ExcelConstants;
import com.ibsplc.xibase.util.excel.xlsx.CellPropertyVO;
import com.ibsplc.xibase.util.excel.xlsx.XlsxFont;
import com.ibsplc.xibase.util.excel.xlsx.XlsxWriter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

public class CustomerMgmntHelper {

	private static final Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	private static final String UNPAID = "UNPAID";
	private static final String SHORTPAY = "SHORT PAY";
	private static final String OVERPAID = "OVERPAID";
	private static final String ITEM = "Item";
	private static final String ITEMS = "Items";
	private static final String EMPTY = "";

	private CustomerMgmntHelper() {
	}

	public static byte[] createWorkBookBytesForExcelReport(List<CustomerInvoiceAWBDetailsVO> invoiceAWBDetailsVOs) {
		byte[] data = null;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

		XlsxWriter xlsxWriter = new XlsxWriter("Statement Of Account.xlsx", "Cover page");
		createCoverSheet(xlsxWriter, invoiceAWBDetailsVOs, currentDate);
		createDetailSheet(xlsxWriter, invoiceAWBDetailsVOs, currentDate);

		try {
			data = xlsxWriter.getWorkBookAsBytes();
		} catch (FileNotFoundException e) {
			log.log(Log.SEVERE, "FileNotFoundException");
		} catch (IOException e) {
			log.log(Log.SEVERE, "IOException");
		}
		return data;
	}

	private static void createCoverSheet(XlsxWriter xlsxWriter, List<CustomerInvoiceAWBDetailsVO> invoiceAWBDetailsVOs,
			LocalDate currentDate) {
		int rowNum = 0;
		int colNum = 0;
		Object[][] dataArray = new Object[20][10];
		CustomerInvoiceAWBDetailsVO invoiceVO = invoiceAWBDetailsVOs.get(0);

		colNum++;
		dataArray[rowNum][colNum + 2] = "Statement of Account";

		rowNum++;
		dataArray[rowNum][colNum] = "Customer Details";
		dataArray[rowNum][colNum + 1] = EMPTY;
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = EMPTY;
		dataArray[rowNum][colNum + 5] = EMPTY;

		rowNum++;
		dataArray[rowNum][colNum] = "Customer Code:";
		dataArray[rowNum][colNum + 1] = invoiceVO.getCustomerCode();
		dataArray[rowNum][colNum + 2] = "Customer Name:";
		dataArray[rowNum][colNum + 3] = invoiceVO.getCustomerName();
		dataArray[rowNum][colNum + 4] = "Customer Account Number:";
		dataArray[rowNum][colNum + 5] = invoiceVO.getCustomerAccountNumber();

		rowNum++;
		dataArray[rowNum][colNum] = "Customer Address";
		dataArray[rowNum][colNum + 1] = invoiceVO.getCustomerAddress();
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = "Accounting Point of Contact:";
		dataArray[rowNum][colNum + 5] = invoiceVO.getName();

		rowNum++;
		dataArray[rowNum][colNum] = "Street:";
		dataArray[rowNum][colNum + 1] = getString(invoiceVO.getStreet());
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = "Name:";
		dataArray[rowNum][colNum + 5] = getString(invoiceVO.getName());

		rowNum++;
		dataArray[rowNum][colNum] = "Location:";
		dataArray[rowNum][colNum + 1] = getString(invoiceVO.getLocation());
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = "Email:";
		dataArray[rowNum][colNum + 5] = getString(invoiceVO.getEmail());

		rowNum++;
		dataArray[rowNum][colNum] = "City:";
		dataArray[rowNum][colNum + 1] = getString(invoiceVO.getCity());
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = "Telephone:";
		dataArray[rowNum][colNum + 5] = getString(invoiceVO.getTelephone());

		rowNum++;
		dataArray[rowNum][colNum] = "State:";
		dataArray[rowNum][colNum + 1] = getString(invoiceVO.getState());
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = EMPTY;
		dataArray[rowNum][colNum + 5] = EMPTY;

		rowNum++;
		dataArray[rowNum][colNum] = "Primary email:";
		dataArray[rowNum][colNum + 1] = getString(invoiceVO.getPrimaryEmail());
		dataArray[rowNum][colNum + 2] = EMPTY;
		dataArray[rowNum][colNum + 3] = EMPTY;
		dataArray[rowNum][colNum + 4] = EMPTY;
		dataArray[rowNum][colNum + 5] = EMPTY;

		rowNum++;
		rowNum++;
		colNum = 3;
		dataArray[rowNum][colNum] = "Open Items as " + invoiceVO.getCurrentDate();
		int overpaidCount = 0;
		int shortpaidCount = 0;
		int unpaidCount = 0;
		double overpaidAmt = 0.0;
		double shortpaidAmt = 0.0;
		double unpaidAmt = 0.0;

		for (CustomerInvoiceAWBDetailsVO awbVO : invoiceAWBDetailsVOs) {
			String status = awbVO.getStatus();

			switch (status) {
			case OVERPAID:
				overpaidCount++;
				overpaidAmt = overpaidAmt + awbVO.getAmountDue();
				break;
			case SHORTPAY:
				shortpaidCount++;
				shortpaidAmt = shortpaidAmt + awbVO.getAmountDue();
				break;
			case UNPAID:
				unpaidCount++;
				unpaidAmt = unpaidAmt + awbVO.getAmountDue();
				break;
			default:
				break;
			}
		}

		DecimalFormat numberFormat = new DecimalFormat("#.00");
		int totalCount = overpaidCount + shortpaidCount + unpaidCount;
		double totalAmt = overpaidAmt + shortpaidAmt + unpaidAmt;

		if (overpaidCount > 0) {
			rowNum++;
			colNum = 2;
			dataArray[rowNum][colNum] = String.valueOf(overpaidCount);
			if (overpaidCount > 1) {
				colNum++;
				dataArray[rowNum][colNum] = ITEMS;
			} else {
				colNum++;
				dataArray[rowNum][colNum] = ITEM;
			}
			colNum++;
			dataArray[rowNum][colNum] = "Overpaid";
			colNum++;
			dataArray[rowNum][colNum] = numberFormat.format(overpaidAmt);
			colNum++;
			dataArray[rowNum][colNum] = invoiceVO.getCurrency();
		}

		if (shortpaidCount > 0) {
			rowNum++;
			colNum = 2;
			dataArray[rowNum][colNum] = String.valueOf(shortpaidCount);
			if (shortpaidCount > 1) {
				colNum++;
				dataArray[rowNum][colNum] = ITEMS;
			} else {
				colNum++;
				dataArray[rowNum][colNum] = ITEM;
			}
			colNum++;
			dataArray[rowNum][colNum] = "Short Pay";
			colNum++;
			dataArray[rowNum][colNum] = numberFormat.format(shortpaidAmt);
			colNum++;
			dataArray[rowNum][colNum] = invoiceVO.getCurrency();
		}

		if (unpaidCount > 0) {
			rowNum++;
			colNum = 2;
			dataArray[rowNum][colNum] = String.valueOf(unpaidCount);
			if (unpaidCount > 1) {
				colNum++;
				dataArray[rowNum][colNum] = ITEMS;
			} else {
				colNum++;
				dataArray[rowNum][colNum] = ITEM;
			}
			colNum++;
			dataArray[rowNum][colNum] = "Unpaid";
			colNum++;
			dataArray[rowNum][colNum] = numberFormat.format(unpaidAmt);
			colNum++;
			dataArray[rowNum][colNum] = invoiceVO.getCurrency();
		}

		rowNum++;
		colNum = 2;
		dataArray[rowNum][colNum] = String.valueOf(totalCount);
		colNum++;
		dataArray[rowNum][colNum] = ITEMS;
		colNum++;
		dataArray[rowNum][colNum] = "Total Balance";
		colNum++;
		dataArray[rowNum][colNum] = numberFormat.format(totalAmt);
		colNum++;
		dataArray[rowNum][colNum] = invoiceVO.getCurrency();

		rowNum = 16;
		colNum = 1;
		dataArray[rowNum][colNum] = currentDate.toDisplayFormat(TimeConvertor.ADVANCED_DATE_FORMAT);

		Map<String, CellPropertyVO> specificStyles = getCoverSheetSpecificStyles(xlsxWriter);
		XlsxFont font = xlsxWriter.getFont();
		font.setFontName(ExcelConstants.FONT_ARIAL);
		font.setFontHeight((short) 10);
		CellPropertyVO commonPropertyVO = new CellPropertyVO();
		commonPropertyVO.setFont(font);
		commonPropertyVO.setWrapText(true);
		commonPropertyVO.setBorderStyle(ExcelConstants.BORDER_THIN);

		xlsxWriter.setData(dataArray, xlsxWriter.getFirstSheet(), 0, rowNum, 7, commonPropertyVO, specificStyles);
	}

	private static Map<String, CellPropertyVO> getCoverSheetSpecificStyles(XlsxWriter xlsxWriter) {
		Map<String, CellPropertyVO> style = new HashMap<>();

		XlsxFont font1 = xlsxWriter.getFont();
		font1.setFontName(ExcelConstants.FONT_ARIAL);
		font1.setBoldweight(true);
		font1.setFontHeight((short) 16);
		CellPropertyVO property1 = new CellPropertyVO();
		property1.setColumnWidth(35);
		property1.setFont(font1);

		XlsxFont font2 = xlsxWriter.getFont();
		font2.setFontName(ExcelConstants.FONT_ARIAL);
		font2.setBoldweight(true);
		font2.setFontHeight((short) 12);
		CellPropertyVO property2 = new CellPropertyVO();
		property2.setColumnWidth(25);
		property2.setFont(font2);

		XlsxFont font3 = xlsxWriter.getFont();
		font3.setFontName(ExcelConstants.FONT_ARIAL);
		font3.setBoldweight(true);
		font3.setFontHeight((short) 12);
		CellPropertyVO property3 = new CellPropertyVO();
		property3.setColumnWidth(35);
		property3.setFont(font3);

		CellPropertyVO property4 = new CellPropertyVO();
		property4.setColumnWidth(25);

		CellPropertyVO property5 = new CellPropertyVO();
		property5.setColumnWidth(35);

		String key0 = "0" + "#" + "3";
		style.put(key0, property1);
		String key1 = "1" + "#" + "1";
		style.put(key1, property2);
		String key2 = "1" + "#" + "2";
		style.put(key2, property4);
		String key3 = "1" + "#" + "3";
		style.put(key3, property5);
		String key4 = "1" + "#" + "4";
		style.put(key4, property4);
		String key5 = "1" + "#" + "5";
		style.put(key5, property4);
		String key6 = "1" + "#" + "6";
		style.put(key6, property4);
		String key7 = "10" + "#" + "3";
		style.put(key7, property3);

		return style;
	}

	private static void createDetailSheet(XlsxWriter xlsxWriter, List<CustomerInvoiceAWBDetailsVO> invoiceAWBDetailsVOs,
			LocalDate currentDate) {
		int rowNum = 0;
		int colNum = 0;
		Object[][] dataArray = new Object[2010][12];

		dataArray[rowNum][colNum + 10] = currentDate.toDisplayFormat(TimeConvertor.ADVANCED_DATE_FORMAT);
		rowNum++;
		dataArray[rowNum][colNum] = "Invoice No.";
		colNum++;
		dataArray[rowNum][colNum] = "Invoice Date";
		colNum++;
		dataArray[rowNum][colNum] = "Age Days";
		colNum++;
		dataArray[rowNum][colNum] = "AWB Number";
		colNum++;
		dataArray[rowNum][colNum] = "Origin";
		colNum++;
		dataArray[rowNum][colNum] = "Destination";
		colNum++;
		dataArray[rowNum][colNum] = "AWB Execution Date";
		colNum++;
		dataArray[rowNum][colNum] = "Status";
		colNum++;
		dataArray[rowNum][colNum] = "Original Debit Amount";
		colNum++;
		dataArray[rowNum][colNum] = "Amount Due";
		colNum++;
		dataArray[rowNum][colNum] = "Remarks";

		String invoiceNumber = "DUMMY";
		double amountDue = 0.0;
		double totalAmount = 0.0;
		for (CustomerInvoiceAWBDetailsVO awbVO : invoiceAWBDetailsVOs) {
			rowNum++;
			colNum = 0;

			/*
			 * Setting the awb details of an invoice in the if block. Individual invoice
			 * total is set in the else block along with the running awb
			 */
			if ((awbVO.getInvoiceNumber().equals(invoiceNumber)) || "DUMMY".equals(invoiceNumber)) {
				dataArray[rowNum][colNum] = awbVO.getInvoiceNumber();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getInvoiceDate();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAgeDays();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAwbNumber();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getOrigin();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getDestination();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAwbExecutionDate();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getStatus();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getActualBilledAmt();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAmountDue();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getRemarks();

				amountDue = amountDue + awbVO.getAmountDue();
				invoiceNumber = awbVO.getInvoiceNumber();
			} else {
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = EMPTY;
				colNum++;
				dataArray[rowNum][colNum] = amountDue;
				colNum++;
				dataArray[rowNum][colNum] = "Invoice Total";

				amountDue = 0.0;
				rowNum++;
				colNum = 0;
				dataArray[rowNum][colNum] = awbVO.getInvoiceNumber();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getInvoiceDate();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAgeDays();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAwbNumber();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getOrigin();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getDestination();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAwbExecutionDate();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getStatus();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getActualBilledAmt();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getAmountDue();
				colNum++;
				dataArray[rowNum][colNum] = awbVO.getRemarks();

				amountDue = amountDue + awbVO.getAmountDue();
				invoiceNumber = awbVO.getInvoiceNumber();
			}
			totalAmount = totalAmount + awbVO.getAmountDue();  
		}

		/* Individual invoice total of last iterated invoice starts */
		rowNum++;
		colNum = 9;
		dataArray[rowNum][colNum] = amountDue;
		colNum++;
		dataArray[rowNum][colNum] = "Invoice Total";
		/* Individual invoice total of last iterated invoice ends */
		rowNum++;
		colNum = 9;
		dataArray[rowNum][colNum] = totalAmount;
		colNum++;
		dataArray[rowNum][colNum] = "Grand Total";

		Map<String, CellPropertyVO> specificStyles = getDetailsSheetSpecificStyles(xlsxWriter);
		XlsxFont font = xlsxWriter.getFont();
		font.setFontName(ExcelConstants.FONT_ARIAL);
		font.setFontHeight((short) 8);
		CellPropertyVO commonPropertyVO = new CellPropertyVO();
		commonPropertyVO.setFont(font);
		commonPropertyVO.setWrapText(true);
		commonPropertyVO.setBorderStyle(ExcelConstants.BORDER_THIN);

		xlsxWriter.setData(dataArray, xlsxWriter.createSheet("Details"), 0, rowNum, 11, commonPropertyVO, specificStyles);
	}

	private static Map<String, CellPropertyVO> getDetailsSheetSpecificStyles(XlsxWriter excelWriter) {
		Map<String, CellPropertyVO> specificStyles = new HashMap<>();

		XlsxFont font = excelWriter.getFont();
		font.setFontName(ExcelConstants.FONT_ARIAL);
		font.setBoldweight(true);
		font.setFontHeight((short) 12);
		CellPropertyVO property = new CellPropertyVO();
		property.setColumnWidth(20);
		property.setFont(font);

		String key0 = "1" + "#" + "0";
		specificStyles.put(key0, property);
		String key1 = "1" + "#" + "1";
		specificStyles.put(key1, property);
		String key2 = "1" + "#" + "2";
		specificStyles.put(key2, property);
		String key3 = "1" + "#" + "3";
		specificStyles.put(key3, property);
		String key4 = "1" + "#" + "4";
		specificStyles.put(key4, property);
		String key5 = "1" + "#" + "5";
		specificStyles.put(key5, property);
		String key6 = "1" + "#" + "6";
		specificStyles.put(key6, property);
		String key7 = "1" + "#" + "7";
		specificStyles.put(key7, property);
		String key8 = "1" + "#" + "8";
		specificStyles.put(key8, property);
		String key9 = "1" + "#" + "9";
		specificStyles.put(key9, property);
		String key10 = "1" + "#" + "10";
		specificStyles.put(key10, property);
		String key11 = "1" + "#" + "11";
		specificStyles.put(key11, property);

		return specificStyles;
	}

	private static String getString(String input) {
		String output = null;
		if (Objects.nonNull(input)) {
			output = input;
		} else {
			output = EMPTY;
		}
		return output;
	}

}
