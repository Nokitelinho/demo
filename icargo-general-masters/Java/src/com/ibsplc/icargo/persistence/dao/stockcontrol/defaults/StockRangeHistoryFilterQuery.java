/*
 * StockRangeHistoryFilterQuery.java Created on Jun 06, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;

/**
 * @author A-2881
 *
 */
//Added by A-5220 for ICRD-20959 starts
public class StockRangeHistoryFilterQuery extends PageableNativeQuery<StockRangeHistoryVO> {
	//Added by A-5220 for ICRD-20959 ends

	private StockRangeFilterVO stockRangeFilterVO;

	private String baseQuery;
	
	private static final String	ZERO_VALUE = "0";
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";

	//Added by A-5220 for ICRD-20959 starts
	public StockRangeHistoryFilterQuery (Mapper<StockRangeHistoryVO> mapper, StockRangeFilterVO stockRangeFilterVO, String baseQuery )
	throws SystemException {

			// calling the base class constructor
		    super(stockRangeFilterVO.getTotalRecordsCount(), mapper);

			this.stockRangeFilterVO = stockRangeFilterVO;

			this.baseQuery = baseQuery;
	}
	//Added by A-5220 for ICRD-20959 ends
	/**
	 *  Overriding the  getNativeQuery method to append the rest of the query based on filter criteria
	 */
	public String getNativeQuery() {

		LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
    	LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
    	String  status = stockRangeFilterVO.getStatus();
    	if(StockAllocationVO.RECEIVED_ALLOCATION.equals(stockRangeFilterVO.getStatus())){
    		status=StockAllocationVO.MODE_ALLOCATE;
    	}
		if(StockAllocationVO.RECEIVED_TRANSFER.equals(stockRangeFilterVO.getStatus())){
			status=StockAllocationVO.MODE_TRANSFER;
		}
		if(StockAllocationVO.RECEIVED_RETURN.equals(stockRangeFilterVO.getStatus())){
			status=StockAllocationVO.MODE_RETURN;
		}

    	String Flag=stockRangeFilterVO.getRangeType();
    	if(StockAllocationVO.MODE_NEUTRAL.equals(Flag)){
    		Flag = "N";
    	}else{
    		Flag = "Y";
    	}


		int index = 0;
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);


		this.setParameter(++index, stockRangeFilterVO.getCompanyCode());

		if (stockRangeFilterVO.getAirlineIdentifier() != 0) {
			stringBuilder.append(" AND HIST.ARLIDR = ?");
			this.setParameter(++index, stockRangeFilterVO
					.getAirlineIdentifier());

		}

		long stRange = 0;
		long edrange = 0 ;	
			if(stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()!=0 ){
				stringBuilder.append("  AND ((? between ASCSTARNG and ASCENDRNG) OR (ASCSTARNG >= ?)) ");	
				stRange = findLong(stockRangeFilterVO.getStartRange());
				this.setParameter(++index,stRange);
				this.setParameter(++index,stRange);
			} 
			
			if(stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()!=0 ){
				stringBuilder.append("  AND ((? between ASCSTARNG and ASCENDRNG) OR (ASCENDRNG <= ?)) ");		
				edrange = findLong(stockRangeFilterVO.getEndRange());
				this.setParameter(++index,edrange);
				this.setParameter(++index,edrange);
			}
		
		//Added as part ICRD-76008
		if(stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()!=0 ) {
			stringBuilder.append(" AND ( CASE");
			stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL");
			this.setParameter(++index,stockRangeFilterVO.getStartRange() );
			stringBuilder.append(" THEN CASE ");
			stringBuilder.append(" WHEN NVL(REGEXP_REPLACE(STARNG,'([[:digit:]])'),0) = REGEXP_REPLACE(?,'([[:digit:]])') ");
			this.setParameter(++index,stockRangeFilterVO.getStartRange() );
			stringBuilder.append(" THEN 1  ELSE 0  END  ELSE  1 END) > 0");
		}
		if(stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()!=0 ){
			stringBuilder.append(" AND ( CASE");
			stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL");
			this.setParameter(++index,stockRangeFilterVO.getEndRange() );
			stringBuilder.append(" THEN CASE ");
			stringBuilder.append(" WHEN NVL(REGEXP_REPLACE(ENDRNG,'([[:digit:]])'),0) = REGEXP_REPLACE(?,'([[:digit:]])') ");
			this.setParameter(++index,stockRangeFilterVO.getEndRange() );
			stringBuilder.append(" THEN 1  ELSE 0  END  ELSE  1 END) > 0");
		}
		
		if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
			//Added by A-2881 for ICRD-3082
			if(StockAllocationVO.RECEIVED_ALLOCATION.equals(stockRangeFilterVO.getStatus())
					||StockAllocationVO.RECEIVED_TRANSFER.equals(stockRangeFilterVO.getStatus())
					||StockAllocationVO.RECEIVED_RETURN.equals(stockRangeFilterVO.getStatus())){
				stringBuilder.append(" AND HIST.TOSTKHLDCOD= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getFromStockHolderCode());
			}else{
				stringBuilder.append(" AND HIST.FRMSTKHLDCOD= ?");
			this.setParameter(++index, stockRangeFilterVO
					.getFromStockHolderCode());
		}
		}
		/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
			query.append(" AND HIST.RNGTYP= ?");
			query.setParameter(++index, stockRangeFilterVO.getRangeType());
		}*/
		if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
			stringBuilder.append(" AND HIST.DOCTYP= ?");
			this.setParameter(++index, stockRangeFilterVO.getDocumentType());
		}
		if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
			stringBuilder.append(" AND HIST.DOCSUBTYP= ?");
			this.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
		}

		if (status!= null && status.trim().length()>0) {
			stringBuilder.append(" AND HIST.STATUS= ?");
			this.setParameter(++index,status);
		}else{
			//Added by A-2881 for ICRD-3082
			//Including all exisitng status
			if (stockRangeFilterVO.getAvailableStatus() != null
					&& !stockRangeFilterVO.getAvailableStatus().isEmpty()) {
				stringBuilder.append(" AND HIST.STATUS  IN (");
				int i = 0;
				for (String avl : stockRangeFilterVO.getAvailableStatus()) {
					if (i == 0) {
						stringBuilder.append("?");
					} else {
						stringBuilder.append(",?");
					}
					this.setParameter(++index, avl);
					i++;
				}
				stringBuilder.append(")");
			}


		}
		if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {

			stringBuilder.append("AND HIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");

			this.setParameter(++index, stockRangeFilterVO.getAccountNumber());
		}

		if (stockRangeFilterVO.getUserId() != null && stockRangeFilterVO.getUserId().trim().length()>0) {

			stringBuilder.append("AND HIST.USRCOD= ?");

			this.setParameter(++index, stockRangeFilterVO.getUserId());
		}
		log.log(Log.FINE,"\n\n----startdate--------------"+stockRangeFilterVO.getStartDate());
		log.log(Log.FINE,"\n\n----enddate--------------"+stockRangeFilterVO.getEndDate());

		if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {

			String startDateStr = new StringBuilder().append(stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat())
			.append(" ").append("00:00").toString();
			String endDateStr = new StringBuilder().append(stockRangeFilterVO.getEndDate().toDisplayDateOnlyFormat())
			.append(" ").append("23:59").toString();
			startDate.setDateAndTime(startDateStr,true);
			endDate.setDateAndTime(endDateStr,true);
			stringBuilder.append(" AND HIST.TXNDAT >= ? ");
			stringBuilder.append(" AND HIST.TXNDAT <= ? ");
	    	this.setParameter(++index,startDate);
	    	this.setParameter(++index,endDate);
			}
		
		//A-5249 from ICRD-105821
		if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
				PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
				stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
				stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
			String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
			stringBuilder.append(" AND (STK.STKHLDCOD IN (");
			boolean isFirst = true;
			for(String val : levelValues){
				if(!isFirst){
					stringBuilder.append(", ");	
				}
				stringBuilder.append("?");	
				this.setParameter(++index, val);
				isFirst = false;
			}
			stringBuilder.append(") OR STK.STKAPRCOD IN (");
			isFirst = true;
			for(String val : levelValues){
				if(!isFirst){
					stringBuilder.append(", ");	
				}
				stringBuilder.append("?");	
				this.setParameter(++index, val);
				isFirst = false;
			}
			stringBuilder.append("))");				
			
		}
		//A-5249 from ICRD-105821 END

		stringBuilder.append(" ORDER BY STKDAT ");
		//Added by A-5220 for ICRD-20959 starts
		stringBuilder.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		//Added by A-5220 for ICRD-20959 ends

	           return  stringBuilder.toString();

	}
	/**
	 * @author A-4777
	 * @param range
	 * @return
	 */
	private long findLong(String range){
		log.log(Log.FINE,"---------------Entering ascii convertion----->>>>");
		char[] sArray=range.toCharArray();
		long base=1;
		long sNumber=0;
		for(int i=sArray.length-1;i>=0;i--){
			sNumber+=base*calculateBase(sArray[i]);
			int value=sArray[i];
			if (value>57) {
				base*=26;
			} else {
				base*=10;
			}
		}
		return sNumber;
	}
	/**
	 * @author A-4777
	 * @param yChar
	 * @return
	 */
	private long calculateBase(char yChar){
		long xLong = yChar;
		long base=0;
		try{
			base=Integer.parseInt(""+yChar);
		}catch(NumberFormatException numberFormatException){
			base = xLong-65;
		}
		return base;
	}

}
