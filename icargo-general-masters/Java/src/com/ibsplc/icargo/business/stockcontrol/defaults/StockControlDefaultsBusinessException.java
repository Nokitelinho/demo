/*
 * StockControlDefaultsBusinessException.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1358
 *
 */
public class StockControlDefaultsBusinessException extends BusinessException {

	/**
	 *
	 */
	public static final String STOCK_FOR_APPROVER = "stockcontrol.defaults.stockforapprover";

	/**
	 *
	 */
	public static final String STOCK_NOT_FOUND = "stockcontrol.defaults.stocknotfound";

	/**
	 *
	 */
	public static final String INVALID_DOCNUM = "stockcontrol.defaults.invaliddocumentnumber";

	/**
	 *
	 */
	public static final String INVALID_STOCK_HOLDER = "stockcontrol.defaults.invalidstockholder";

	/**
	 *
	 */
	public static final String UNACCEPTABLE_DOCUMENTS_EXIST = "stockcontrol.defaults.unacceptabledocumentsexists";

	/**
	 *
	 */
	public static final String ENDRANGE_LESSTHAN_STARTRANGE = "stockcontrol.defaults.endrangelessthanstartrange";

	/**
	 *
	 */
	public static final String INVALID_STOCKHOLDERCODEFORAGENT = "stockcontrol.defaults.invalidstockholdercodeforagent";

	/**
	 *
	 */
	public static final String INVALID_STOCKHOLDERCODEFORSTATION = "stockcontrol.defaults.invalidstockholdercodeforstation";


	/**
	 *
	 */
	public static final String INVALID_STOCKHOLDERFORCREATENEW = "stockcontrol.defaults.invalidstockholderforcreatenew";

	/**
	 * this error code is appended with the exception, when there is
	 * already an agent code persisted and trying to save the same agent code
	 */
	public static final String DUPLICATE_AGENT_FOUND = "stockcontrol.defaults.duplicateagentfound";
	/**
	 * thrown when trying to remove a stockHolder to whom some agent is mapped
	 */
	public static final String AGENT_MAPPING_EXISTS = "stockcontrol.defaults.agentmappingexists";
	/**
	 * thrown when agent is not mapped to stockHolder
	 */
	public static final String AGENT_MAPPING_NOTFOUND = "stockcontrol.defaults.agentmappingnotfound";
	/**
	 * thrown when a specific AWB number is not found in any of the stock of
	 * any of the stockholder
	 */
	public static final String AWBNUMBER_NOTFOUND_INANYSTOCK = "stockcontrol.defaults.awbnumbernotfoundinanystock";
	/**
	 * when no stock is found for a specific stockHOlder
	 */
	public static final String STOCKHOLDER_STOCK_NOTFOUND = "stockcontrol.defaults.nostockfoundforstockholder";

	/**
	 * when the stockHolder contains some stock, but doesn't have any
	 * stock with the documentType as "AWB"
	 */
	public static final String STOCKHOLDER_AWBSTOCK_NOTFOUND = "stockcontrol.defaults.awbstocknotfoundforstockholder";
	/**
	 * if the stock holder has stocks and even AWB stocks , but he don't have
	 * the specific document number within the ranges
	 */
	public static final String STOCKHOLDER_AWB_NOT_EXISTING = "stockcontrol.defaults.awbnotexistinginanyrangeforstockholder";
	/**
	 * if the document number is found in some range of some stockHolder
	 * but that stock holder is not the same as the referred one
	 * i.e, if the document number belongs to some other stockHolder
	 */
	public static final String DOCUMENT_NOTOF_STOCKHOLDER = "stockcontrol.defaults.documentnotbelongingtostockholder";

	/**
	 *
	 */
	public static final String STOCKHOLDER_NOTFOUNDFOR_AGENT = "stockcontrol.defaults.stockholdernotfoundforagent";

	/**
	 *
	 */
	public static final String STOCKHOLDER_NOT_FOUND = "stockcontrol.defaults.stockholdernotfound";
	/**
	 *
	 */
	public static final String COURIER_NOTFOUND_INANYSTOCK = "stockcontrol.defaults.couriernotfoundinanystock";

	/**
	 * Extra-Baggage Ticket NOT found in any Stock
	 */
	public static final String EBT_NOTFOUND_INANYSTOCK = "stockcontrol.defaults.ebtnotfoundinanystock";

	/**
	 *
	 */
	public static final String COURIER_NOTWITH_STOCKHOLDER = "stockcontrol.defaults.couriernotavailablewithstockholder";
	/**
	 *
	 */
	public static final String EBT_NOTWITH_STOCKHOLDER = "stockcontrol.defaults.ebtnotavailablewithstockholder";
	/**
	 * @author a-1885
	 */
	public static final String HQ_ALREADY_EXIST = "stockcontrol.defaults.hqAlreadyExist";

	/*
	 * If the AWB which is reserved in one airport is trying to be captured
	 * at some other airport
	 *
	 */
	public static final String AWB_RESERVED_FOR_ANOTHER_AIRPORT = "stockcontrol.defaults.awbreservedforanotherairport";

	/**
	 * @author a-2870
	 */
	public static final String INVALID_STOCKHOLDERFORAGENTDETAILS = "stockcontrol.defaults.invalidstockholderforagentdetails";
	
	public static final String UTILISATION_EXISTS = "stockcontrol.defaults.utilisationexists";
	
	public static final String RANGES_WITHIN_STOCKINTRODUCTIONPERIOD = "stockcontrol.defaults.rangeswithinstockintroductionperiod";

	public static final String INVALID_AWB = "stockcontrol.defaults.awbnumberinvalid";

	public static final String AGENT_NOT_FOUND = "stockcontrol.defaults.agentnotfound";

	
	/**
	 *
	 */
	public StockControlDefaultsBusinessException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public StockControlDefaultsBusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/**
	 * @param errorCode
	 */
	public StockControlDefaultsBusinessException(String errorCode) {
		super(errorCode);
	}

	/**
	 * @param exception
	 */
	public StockControlDefaultsBusinessException(BusinessException exception) {
		super(exception);
	}

	/**
	 * @param errorCode
	 * @param exception
	 */
	public StockControlDefaultsBusinessException(String errorCode,BusinessException exception) {
		super(errorCode, exception);
	}

}
