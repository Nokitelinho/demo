/*
 * StockControlDefaultsPersistenceConstants.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

/**
 * @author A-1358
 *
 */
public class StockControlDefaultsPersistenceConstants {
    /**
     * 
     */
    public static final String MODULE_NAME = "stockcontrol.defaults";
    //added by a-4443 for icrd-3024 
    public static final String STOCKCONTROL_DEFAULTS_FINDTRANSITSTOCKS = "stockcontrol.defaults.findTransitStocks";
   
    public static final String STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGESFROMTRANSIT = "stockcontrol.defaults.findBlackListRangesFromTransit";
	//Added by A-5175 for icrd-20959 starts
    public static final String STOCKCONTROL_DEFAULTS_DENSE_RANK_QUERY = "SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    
    public static final String STOCKCONTROL_DEFAULTS_SUFFIX_QUERY = ") RESULT_TABLE";
    
    public static final String STOCKCONTROL_DEFAULTS_ROWNUM_QUERY = "SELECT RESULT_TABLE.*,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM (";
    //Added by A-5175 for icrd-20959 ends
    //Added by A-6858 for ICRD-234889
  	public static final String STOCKCONTROL_DEFAULTS_FIND_AWB_USAGEDETAILS="stockcontrol.defaults.findawbusagedetails";
  	
	public static final String STOCKCONTROL_DEFAULTS_FIND_FINDTOTALDOCS = "stockcontrol.defaults.findTotalNoOfDocuments";
  	
	public static final String STOCKCONTROL_DEFAULTS_SAVE_STOCK_DETAILS = "stockcontrol.defaults.saveStockDetails";
	
	public static final String STOCKCONTROL_DEFAULTS_FIND_STOCK_DETAILS = "stockcontrol.defaults.findStockDetails";
	
	public static final String STOCKCONTROL_DEFAULTS_SAVE_STOCK_RANGE_UTILISATION_LOG="stockcontrol.defaults.saveStockRangeUtilisationLog";

}
