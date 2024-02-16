/*
 * ULDDefaultsReportHelper.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Lstd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject tos license terms.
 */
package com.ibsplc.icargo.presentation.report.helper.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ULDDefaultsReportHelper {

	private static final String ACTIVE = "A";

	private static final String INACTIVE = "I";

	private static final String DELETED = "D";

	private static final String ACTIVED = "Active";

	private static final String INACTIVED = "Inactive";

	private static final String DELETE = "Deleted";

	private static final String AIRLINE = "A";

	private static final String AGENT = "G";

	private static final String ALL = "L";

	private static final String ALLPARTY = "ALL";

	private static final String OTHERS = "O";

	private static final String AIRLINES = "Airline";

	private static final String AGENTS = "Agent";

	private static final String OTHER = "Others";

	private static final String LOAN = "L";

	private static final String BORROW = "B";

	private static final String TXNLOAN = "Loan";

	private static final String TXNBORROW = "BORROW";

	private static final String DAILY = "D";

	private static final String MONTHLY = "M";

	private static final String WEEKLY = "W";

	private static final String FORNIGHT = "F";

	private static final String FREQDAILY = "Daily";

	private static final String FREQMONTHLY = "Monthly";

	private static final String FREQWEEKLY = "Weekly";

	private Log log = LogFactory.getLogger("ULD");
	// private static Log log =
	// LogFactory.getLogger("UldDefaultsReportHelperone");

	private static final String FREQFORNIGHT = "Fortnightly";

	@Helper( { @Help(reportId = "RPTLST018", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = { "agreementDate" }) })
	public static String formatAgreementDate(Object value,
			Collection<Object> extraInfo) {

		String agreementDate = "";

		LocalDate localDate = (LocalDate) value;
		// log.log(Log.FINE,"\n\n\n helper class ---- localDate "+localDate);
		if ((value) != null) {
			agreementDate = localDate.toDisplayDateOnlyFormat();
		}
		// log.log(Log.FINE,"\n\n\n helper class ---- agreementDate
		// "+agreementDate);
		return agreementDate;
	}

	@Helper( { @Help(reportId = "RPTLST018", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = { "agreementFromDate" }) })
	public static String formatAgreementFromDate(Object value,
			Collection<Object> extraInfo) {

		String agreementFromDate = "";
		LocalDate localDate = (LocalDate) value;
		// log.log(Log.FINE,"\n\n\n helper class ---- localDate "+localDate);
		if ((value) != null) {
			agreementFromDate = localDate.toDisplayDateOnlyFormat();
		}
		// log.log(Log.FINE,"\n\n\n helper class ---- agreementFromDate
		// "+agreementFromDate);

		return agreementFromDate;
	}

	@Helper( { @Help(reportId = "RPTLST018", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = { "agreementToDate" }) })
	public static String formatAgreementToDate(Object value,
			Collection<Object> extraInfo) {

		String agreementToDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			agreementToDate = localDate.toDisplayDateOnlyFormat();
		}
		return agreementToDate;
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO" }, fieldNames = {
			"agreementDate", "agreementFromDate", "agreementToDate",
			"agreementFromDate", "agreementToDate" }) })
	/**
	 * @author A-2046
	 */
	public static String formatDate(Object value, Collection<Object> extraInfo) {
		String date = ReportConstants.EMPTY_STRING;
		if (value != null) {
			LocalDate agrDate = (LocalDate) value;
			return agrDate.toDisplayDateOnlyFormat();
		} else {
			return date;
		}
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"freeLoanPeriod", "demurrageRate", "tax", "freeLoanPeriod",
			"demurrageRate", "tax" }) })
	/**
	 * @author A-2046
	 */
	public static String formatPeriod(Object value, Collection<Object> extraInfo) {
		String period = ReportConstants.EMPTY_STRING;
		if (value != null) {
			period = String.valueOf(value);
			return period;
		} else {
			return period;
		}
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"agreementStatus", "agreementStatus" }) })
	/**
	 * @author A-2046
	 */
	public static String formatStatus(Object value, Collection<Object> extraInfo) {
		String status = ReportConstants.EMPTY_STRING;
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(ACTIVE, ACTIVED);
		hashMap.put(INACTIVE, INACTIVED);
		hashMap.put(DELETED, DELETE);
		if (value != null) {
			status = hashMap.get(value);
			return status;
		}
		return status;
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"partyType", "partyType" }) })
	/**
	 * @author A-2046
	 */
	public static String formatPartyType(Object value,
			Collection<Object> extraInfo) {
		String partyType = ReportConstants.EMPTY_STRING;
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(AIRLINE, AIRLINES);
		hashMap.put(AGENT, AGENTS);
		hashMap.put(ALL, ALLPARTY);
		hashMap.put(OTHERS, OTHER);
		if (value != null) {
			partyType = hashMap.get(value);
			return partyType;
		}
		return partyType;
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"fromPartyType", "fromPartyType" }) })
	/**
	 * @author A-4393
	 */
	public static String formatFromPartyType(Object value,
			Collection<Object> extraInfo) {
		String fromPartyType = ReportConstants.EMPTY_STRING;
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(AIRLINE, AIRLINES);
		hashMap.put(AGENT, AGENTS);
		hashMap.put(ALL, ALLPARTY);
		hashMap.put(OTHERS, OTHER);
		if (value != null) {
			fromPartyType = hashMap.get(value);
			return fromPartyType;
		}
		return fromPartyType;
	}
	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"txnType", "txnType" }) })
	/**
	 * @author A-2046
	 */
	public static String formatTxnType(Object value,
			Collection<Object> extraInfo) {
		String txnType = ReportConstants.EMPTY_STRING;
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(LOAN, TXNLOAN);
		hashMap.put(BORROW, TXNBORROW);
		if (value != null) {
			txnType = hashMap.get(value);
			return txnType;
		}
		return txnType;
	}

	@Helper( { @Help(reportId = "RPTOPR042", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO" }, fieldNames = {
			"demurrageFrequency", "demurrageFrequency" }) })
	/**
	 * @author A-2046
	 */
	public static String formatDemurrageFrequency(Object value,
			Collection<Object> extraInfo) {
		String demrrageFrequency = ReportConstants.EMPTY_STRING;
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(DAILY, FREQDAILY);
		hashMap.put(MONTHLY, FREQMONTHLY);
		hashMap.put(WEEKLY, FREQWEEKLY);
		hashMap.put(FORNIGHT, FREQFORNIGHT);
		if (value != null) {
			demrrageFrequency = hashMap.get(value);
			return demrrageFrequency;
		}
		return demrrageFrequency;
	}

	@Helper( { @Help(reportId = "RPTLST049", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO" }, fieldNames = {
			"maxQty", "minQty" }) })
	public static String formatQuantity(Object value,
			Collection<Object> extraInfo) {
		String quantity = ReportConstants.EMPTY_STRING;
		if (value != null) {
			quantity = String.valueOf(value);
			return quantity;
		} else {
			return quantity;
		}
	}

	@Helper( {
@Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" }, fieldNames = { "fromDate" }),
@Help(	reportId = "RPTLST229",voNames = {"com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO"},fieldNames = {"txnFromDate"}),
@Help(	reportId = "RPTLST229",voNames = {"com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO"},fieldNames = {"txnToDate"})
			}
	)
	public static String formatMvtFromDate(Object value,
			Collection<Object> extraInfo) {

		String fromDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			fromDate = localDate.toDisplayDateOnlyFormat();

		}
		return fromDate;
	}

	@Helper( { @Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" }, fieldNames = { "toDate" }) })
	public static String formatMvtToDate(Object value,
			Collection<Object> extraInfo) {

		String toDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			toDate = localDate.toDisplayDateOnlyFormat();

		}
		return toDate;
	}

	@Helper( { @Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO" }, fieldNames = { "flightDate" }) })
	public static String formatFlightDate(Object value,
			Collection<Object> extraInfo) {

		String flightDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			flightDate = localDate.toDisplayDateOnlyFormat();

		}
		return flightDate;
	}

	@Helper( { @Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO" }, fieldNames = { "isDummyMovement" }) })
	public static String formatMovementType(Object value,
			Collection<Object> extraInfo) {

		String dummyMvt = "Dummy Movement";
		String normalMvt = "Normal Movement";
		boolean movementType = (Boolean) value;
		if (movementType) {

			return dummyMvt;

		} else {

			return normalMvt;
		}

	}

	@Helper( { @Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO" }, fieldNames = { "overallStatus" })

	})
	/**
	 * Static method to perform the conversion of first onetime code to first
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatFirstOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(0);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST033", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO" }, fieldNames = { "content" }) })
	/**
	 * Static method to perform the conversion of second onetime code to second
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatSecondOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************2nd onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(1);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST004", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" }, fieldNames = { "damageReferenceNumber" }) })
	public static String formatNumber(Object value, Collection<Object> extraInfo) {

		String number = ReportConstants.EMPTY_STRING;
		if (value != null) {
			number = String.valueOf(value);
			return number;
		} else {
			return number;
		}
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = {
			//"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO" }, fieldNames = {
			"transactionType" })

	})
	/**
	 * Static method to perform the conversion of first onetime code to first
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatLoanFirstOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(0);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = {
			//"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO" }, fieldNames = {
			"partyType" })

	})
	/**
	 * Static method to perform the conversion of second onetime code to second
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatLoanSecondOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(1);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = {
			//"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO" }, fieldNames = {
			"transactionStatus"})

	})
	/**
	 * Static method to perform the conversion of third onetime code to third
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatLoanThirdOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(2);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO" }, fieldNames = { "accessoryCode" })

	})
	/**
	 * Static method to perform the conversion of fourth onetime code to fourth
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatLoanFourthOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(3);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}
//added by A-4443 for bug icrd-3608 starts
	@Helper( { @Help(reportId = "RPTLST048", voNames = {
			//"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO" }, fieldNames = {
			"mucStatus" })

	})
	/**
	 * Static method to perform the conversion of fifth onetime code to first
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatLoanFifthOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		 // log.log(Log.FINE, "\n\n\n\n*************5th onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(4);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}
	//added by A-4443 for bug icrd-3608 ends
	@Helper( { @Help(reportId = "RPTLST048", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO"}, fieldNames = {
			"uldNumber", "uldTypeCode", "accessoryCode", "partyType",
			"fromPartyCode", "toPartyCode", "returnedStationCode","baseCurrency" })

	})
	/**
	 * Static method to convert null values to empty string
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatEmptyValues(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************chek for null value********"
		// + code);

		String description = "";

		if (code != null) {
			description = code;
		}
		// log.log(Log.FINE, "\n\n\n\n******formatEmptyValues**** returned
		// value********"
		// + description);
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" }, fieldNames = { "invoiceStatus" })

	})
	public static String formatInvoiceStatus(Object value,
			Collection<Object> extraInfo) {

		String yes = "Yes";
		String no = "No";
		String invStatus = (String) value;
		if (("Y").equals(invStatus)) {
			return yes;

		} else {
			return no;
		}

	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" }, fieldNames = { "transactionDate" }) })
	public static String formatTransactionDate(Object value,
			Collection<Object> extraInfo) {

		String transactionDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			transactionDate = localDate.toDisplayDateOnlyFormat();

		}
		return transactionDate;
	}

	@Helper( { @Help(reportId = "RPTLST048", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" }, fieldNames = { "returnDate" }) })
	/**
	 * @author a-2046
	 */
	public static String formatReturnDate(Object value,
			Collection<Object> extraInfo) {

		String returnDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			returnDate = localDate.toDisplayDateOnlyFormat();

		}
		return returnDate;
	}
	
	@Helper( { @Help(reportId = "RPTLST048", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" }, fieldNames = { "totalDemmurage" }) })
	/**
	 * @author a-3278
	 * for bug 29941 on 11Dec08
	 * to include total demmurage in the report
	 */
	public static String formatTotalDemmurage(Object value,
			Collection<Object> extraInfo) {

		String totalDemmurage = String.valueOf(value);
		return totalDemmurage;

	}

	// Added By Deepthi
	@Helper( { @Help(reportId = "RPTLST034", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO" }, fieldNames = {
			"partyType", "partyType" })

	})
	/**
	 * Static method to perform the conversion of first onetime code to first
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListInvPartyOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(1);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}
	@Helper( { @Help(reportId = "RPTLST034", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO"}, fieldNames = {
			"currencyCode"})
	})
	
	public static String listCurrency(Object value,
			Collection<Object> extraInfo) {
	
		String currencyCode = String.valueOf(value);
		return currencyCode;
	}
	// Added By Deepthi

	@Helper( { @Help(reportId = "RPTLST034", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO",
			"com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO" }, fieldNames = {
			"transactionType", "transactionType" })

	})
	/**
	 * Static method to perform the conversion of first onetime code to first
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListInvFirstOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(0);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	// Added By Deepthi ends

	@Helper( { @Help(reportId = "RPTLST034", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO" }, fieldNames = { "invoicedDateFrom" }) })
	public static String formatInvFrmDate(Object value,
			Collection<Object> extraInfo) {

		String invoiceDateFrom = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			invoiceDateFrom = localDate.toDisplayDateOnlyFormat();

		}

		return invoiceDateFrom;
	}

	@Helper( { @Help(reportId = "RPTLST034", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO" }, fieldNames = { "invoicedDateTo" }) })
	public static String formatInvToDate(Object value,
			Collection<Object> extraInfo) {

		String invoiceDateTo = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			invoiceDateTo = localDate.toDisplayDateOnlyFormat();

		}

		return invoiceDateTo;
	}

	@Helper( { @Help(reportId = "RPTLST034", voNames = { "com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO" }, fieldNames = { "invoicedDate" }) })
	public static String formatInvDate(Object value,
			Collection<Object> extraInfo) {

		String invoicedDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			invoicedDate = localDate.toDisplayDateOnlyFormat();

		}

		return invoicedDate;
	}

	@Helper( { @Help(reportId = "RPTLST035", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO" }, fieldNames = { "damageRefNumbers" }) })
	public static String formatRefNumber(Object value,
			Collection<Object> extraInfo) {

		StringBuilder str = new StringBuilder();
		ArrayList<Integer> damageRefNo = (ArrayList<Integer>) value;
		// log.log(Log.FINE, "damageRefNO------>" + damageRefNo);
		int len = damageRefNo.size();
		for (int j = 0; j < len; j++) {
			str.append(damageRefNo.get(j));
			str.append(",");
		}
		String val = (str.substring(0, str.length() - 1)).toString();
		// log.log(Log.FINE, "val------" + val);

		return val;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO" }, fieldNames = {
			"uldRangeFrom", "uldRangeTo" }) })
	public static String formatULDRange(Object value,
			Collection<Object> extraInfo) {

		String range = ReportConstants.EMPTY_STRING;
		if (value != null) {
			range = String.valueOf(value);
			// log.log(Log.FINE, "Range------->" + range);
			//added by a-3045 for bug 23717 starts
			if(Integer.parseInt(range) > 0){
				return range;	
			}else{
				return "";
			}	
			//added by a-3045 for bug 23717 ends
		} else {
			// log.log(Log.FINE, "Range------->" + range);
			return range;
		}

	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO" }, fieldNames = {
			"overallStatus", "overallStatus" })

	})
	/**
	 * Static method to perform the conversion of First onetime code to First
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDFirstOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(0);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = { "com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO" }, fieldNames = { "cleanlinessStatus" })

	})
	/**
	 * Static method to perform the conversion of Second onetime code to Second
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDSecondOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(1);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO" }, fieldNames = {
			"damageStatus", "damageStatus" })

	})
	/**
	 * Static method to perform the conversion of Third onetime code to Third
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDThirdOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(2);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO" }, fieldNames = {
			"uldNature", "uldNature" })

	})
	/**
	 * Static method to perform the conversion of Fourth onetime code to Fourth
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDFourthOneTime(Object value,
			Collection<Object> extraInfo) {
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(3);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		return description;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = { "com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO" }, fieldNames = { "levelType" })

	})
	/**
	 * Static method to perform the conversion of Fifth onetime code to Fifth
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDFifthOneTime(Object value,
			Collection<Object> extraInfo) {
		//log.log(Log.FINE, "in formatListULDFifthOneTime"+value);
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(4);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						//log.log(Log.FINE, "in formatListULDFifthOneTime  code");
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		//log.log(Log.FINE, "in formatListULDFifthOneTime  description"+description);
		return description;
	}
	
	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO"}, fieldNames = {
			"content", "content" })

	})
	/**
	 * Static method to perform the conversion of Sixth onetime code to Sixth
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDSixthOneTime(Object value,
			Collection<Object> extraInfo) {
		//log.log(Log.FINE, "in formatListULDSixthOneTime"+value);
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(5);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						//log.log(Log.FINE, "in formatListULDSixthOneTime  code");
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		//log.log(Log.FINE, "in formatListULDSixthOneTime  description"+description);
		return description;
	}
	
	@Helper( { @Help(reportId = "RPTLST020", voNames = {			
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO"}, fieldNames = {
			"facilityType" })})
	/**
	 * Static method to perform the conversion of Seventh onetime code to Seventh
	 * onetime description
	 * 
	 * @param value
	 * @param extraInfo
	 */
	public static String formatListULDSeventhOneTime(Object value,
			Collection<Object> extraInfo) {
		//log.log(Log.FINE, "in formatListULDSeventhOneTime"+value);
		String code = (String) value;
		// log.log(Log.FINE, "\n\n\n\n*************1st onetime
		// conversion********"
		// + code);
		String description = "";
		Collection<OneTimeVO> oneTimeCollection = (Collection<OneTimeVO>) ((ArrayList<Object>) extraInfo)
				.get(6);
		if (oneTimeCollection != null) {
			if (oneTimeCollection.size() > 0) {
				for (OneTimeVO oneTimeVo : oneTimeCollection) {
					if (oneTimeVo.getFieldValue().equals(code)) {
						//log.log(Log.FINE, "in formatListULDSeventhOneTime  code");
						description = oneTimeVo.getFieldDescription();
					}
				}
			}
		}
		//log.log(Log.FINE, "in formatListULDSeventhOneTime  description"+description);
		return description;
	}
	
	@Helper( { @Help(reportId = "RPTLST020", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO",
			"com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO" }, fieldNames = {
			"lastMovementDate", "lastMovementDate" }) })
	public static String formatLastMvtDate(Object value,
			Collection<Object> extraInfo) {

		// System.out.println("ReportHelper.formatLastMvtDate()");
		String lstMvtDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			lstMvtDate = localDate.toDisplayDateOnlyFormat();

		}
		return lstMvtDate;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = { "com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO" }, fieldNames = { "fromDate" }) })
	public static String formatFromDate(Object value,
			Collection<Object> extraInfo) {
		String fromDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			fromDate = localDate.toDisplayDateOnlyFormat();

		}
		return fromDate;
	}

	@Helper( { @Help(reportId = "RPTLST020", voNames = { "com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO" }, fieldNames = { "toDate" }) })
	public static String formatToDate(Object value, Collection<Object> extraInfo) {
		String toDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			toDate = localDate.toDisplayDateOnlyFormat();

		}
		return toDate;
	}

	@Helper( { @Help(reportId = "RPTLST036", voNames = {
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO",
			"com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO" }, fieldNames = {
			"inStock", "nonOperational", "off", "systemAvailable", "maxQty", "minQty", "balance" }) })
	public static String formatMonitorStockFields(Object value,
			Collection<Object> extraInfo) {

		String field = ReportConstants.EMPTY_STRING;
		if (value != null) {
			field = String.valueOf(value);
			return field;
		} else {
			return field;
		}
	}

	@Helper( { @Help(reportId = "RPTOPR026", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO"

	}, fieldNames = { "damageReferenceNumber" }) })
	/**
	 * @author A-2046
	 */
	public static String formatDmgRefNo(Object value,
			Collection<Object> extraInfo) {
		String refNumber = ReportConstants.EMPTY_STRING;
		if (value != null) {
			refNumber = String.valueOf(value);
		}

		return refNumber;

	}

	@Helper( { @Help(reportId = "RPTOPR026", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" }, fieldNames = { "reportedDate" }) })
	/**
	 * 
	 */
	public static String formatDmgReportDate(Object value,
			Collection<Object> extraInfo) {

		String reportDate = ReportConstants.EMPTY_STRING;
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			reportDate = localDate.toDisplayDateOnlyFormat();

		}
		return reportDate;
	}

	/**
	 * @param value
	 * @param extraInfo
	 * @return
	 */
	@Helper( { @Help(reportId = "RPTLST213", voNames = { "com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO" }, 
			fieldNames = { "viewByNatureFlag" })})
	public static String formatBooleanValue(Object value,
			Collection<Object> extraInfo) {

		String stringValue = ReportConstants.EMPTY_STRING;

		if (value != null) {
			Boolean boolVal = (Boolean) value;
			if (boolVal) {
				stringValue = "Yes";
			} else {
				stringValue = "No";
			}
		}
		//log.log(Log.INFO,"ULDDefaults Report helper formatIntValue$$$$"+stringValue);
		return stringValue;
	}
	/**
	 * @param value
	 * @param extraInfo
	 * @return
	 */
	@Helper( { @Help(reportId = "RPTLST274",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" },fieldNames = { "noOfMovements" }),
			@Help(reportId = "RPTLST274",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" },fieldNames = { "noOfLoanTxns"}),
			@Help(reportId = "RPTLST274",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" },fieldNames = {"noOfTimesDmged"}),
			@Help(reportId = "RPTLST274",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" },fieldNames = { "noOfTimesRepaired" }),
			@Help(reportId = "RPTLST270",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" },fieldNames = { "noOfMovements" }),
			@Help(reportId = "RPTLST270",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" },fieldNames = { "noOfLoanTxns" }),
			@Help(reportId = "RPTLST270",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" },fieldNames = { "noOfTimesDmged" }),
			@Help(reportId = "RPTLST270",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" },fieldNames = { "noOfTimesRepaired" }),
			@Help(reportId = "RPTLST269",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfMovements" }),
			@Help(reportId = "RPTLST269",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfLoanTxns" }),
			@Help(reportId = "RPTLST269",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfTimesDmged" }),
			@Help(reportId = "RPTLST269",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfTimesRepaired" }),
			@Help(reportId = "RPTLST275",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfMovements" }),
			@Help(reportId = "RPTLST275",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfLoanTxns" }),
			@Help(reportId = "RPTLST275",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfTimesDmged" }),
			@Help(reportId = "RPTLST275",voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO" },fieldNames = { "noOfTimesRepaired" })
		})
	public static String formatIntValue(Object value,
			Collection<Object> extraInfo){
		//log.log(Log.INFO,"ULDDefaults Report hel[per"+value);
		String stringValue = "0";
		if(value!=null){
			Integer num = (Integer) value;
			//log.log(Log.INFO,"Number value"+num);
			stringValue = num.toString();
		}
		//log.log(Log.INFO,"ULDDefaults Report hel[per formatIntValue"+stringValue);
		return stringValue;
	}
	@Helper( { @Help(reportId = "RPTLST274", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" }, fieldNames = { "fromDate" }),
		@Help(reportId = "RPTLST274", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO" }, fieldNames = { "toDate" }),
		@Help(reportId = "RPTLST270", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" }, fieldNames = { "toDate" }),
		@Help(reportId = "RPTLST270", voNames = { "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO" }, fieldNames = { "fromDate" }),
		@Help(reportId = "RPTLST270", voNames = { "com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO" }, fieldNames = { "flightDate" })
		
		})
	public static String formatMvtDate(Object value,
			Collection<Object> extraInfo) {

		String flightDate = "";
		LocalDate localDate = (LocalDate) value;
		if ((value) != null) {
			flightDate = localDate.toDisplayDateOnlyFormat();

		}
		//log.log(Log.INFO,"ULDDefaults Report hel[per formatMvtDate"+flightDate);
		return flightDate;
	}

}
