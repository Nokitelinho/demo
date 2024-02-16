/*
 * FindBlacklistedStockFilterQuery.java Created on Feb 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.util.Calendar;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class FindBlacklistedStockFilterQuery extends PageableNativeQuery<BlacklistStockVO>{
	private static final String AND_CASE = " AND ( CASE";
	private static final String THEN_CASE = " THEN CASE ";
	private static final String THEN_1_ELSE_0_END_ELSE_1_END_0 = " THEN 1  ELSE 0  END  ELSE  1 END) > 0";
	private Log log = LogFactory.getLogger("FIND BLACKLISTED STOCK FILTER QUERY");
	private StockFilterVO stockFilterVO;
	private String baseQuery;

	private boolean isOracleDataSource;
	/**
	 * @param stockFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public FindBlacklistedStockFilterQuery(Mapper<BlacklistStockVO> blacklistedStockMapper, StockFilterVO stockFilterVO,String baseQuery)
	throws SystemException {
		super(stockFilterVO.getTotalRecords(),blacklistedStockMapper);//Added by A-5214 as part from the ICRD-20959
		this.stockFilterVO = stockFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = !PersistenceUtils.isPostgreEDB();
	}
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		String companyCode = stockFilterVO.getCompanyCode();
		String documentType = stockFilterVO.getDocumentType();
		String documentSubType = stockFilterVO.getDocumentSubType();
		String startRange = stockFilterVO.getRangeFrom();
		String endrange = stockFilterVO.getRangeTo();
		int airlineIdentifier=stockFilterVO.getAirlineIdentifier();
		long stRange = 0;
		long edrange = 0 ;
		Calendar fromDate = null;
		Calendar toDate = null;
		if(stockFilterVO.getFromDate()!=null){
			fromDate=(stockFilterVO.getFromDate()).toCalendar();
		}
		if(stockFilterVO.getToDate()!=null){
			toDate = (stockFilterVO.getToDate()).toCalendar();
		}
		if (companyCode != null && companyCode.trim().length()!=0) {
			stringBuilder.append("  RNG.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
		}
		if (documentType != null && documentType.trim().length()!=0) {
			stringBuilder.append("  AND RNG.DOCTYP = ? ");
			this.setParameter(++index, documentType);
		}
		if (documentSubType != null && documentSubType.trim().length()!= 0
				&& !(documentSubType.equals(StockRequestFilterVO.FLAG_ALL))){
			stringBuilder.append(" AND RNG.DOCSUBTYP = ? " );
			this.setParameter(++index, documentSubType);
		}
		//Edited as part of ICRD-76008
			if(startRange != null && startRange.trim().length()!=0 ){
				stringBuilder.append("  AND ((? between RNG.ASCSTARNG and RNG.ASCENDRNG) OR (RNG.ASCSTARNG >= ?)) ");
				stRange = findLong(startRange);
				this.setParameter(++index,stRange);
				this.setParameter(++index,stRange);
			} 			
			if(endrange != null && endrange.trim().length()!=0 ){
				stringBuilder.append("  AND ((? between RNG.ASCSTARNG and RNG.ASCENDRNG) OR (RNG.ASCENDRNG <= ?)) ");
				edrange = findLong(endrange);
				this.setParameter(++index,edrange);
				this.setParameter(++index,edrange);
			}		
		if(airlineIdentifier>0){
			stringBuilder.append("  AND RNG.ARLIDR = ? ");
			this.setParameter(++index, airlineIdentifier);
		}
		if (toDate != null) {
			stringBuilder.append(" AND TRUNC(RNG.BLKLSTDAT) <= ? ");
			this.setParameter(++index, toDate);
		}
		if (fromDate != null) {
			stringBuilder.append(" AND TRUNC(RNG.BLKLSTDAT) >= ? ");
			this.setParameter(++index, fromDate);
		}
		stringBuilder.append("AND RNG.BLKSTA ='A'");	
		//Added as part ICRD-76008
		if(startRange != null && startRange.trim().length()!=0 ) {
			if(isOracleDataSource) {
				stringBuilder.append(AND_CASE);
				stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL");
				this.setParameter(++index, startRange);
				stringBuilder.append(THEN_CASE);
				stringBuilder.append(" WHEN NVL(REGEXP_REPLACE(STARNG,'([[:digit:]])'),0) = REGEXP_REPLACE(?,'([[:digit:]])') ");
				this.setParameter(++index, startRange);
				stringBuilder.append(THEN_1_ELSE_0_END_ELSE_1_END_0);
			}else{
				stringBuilder.append(AND_CASE);
				stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL or REGEXP_REPLACE(?,'([[:digit:]])') = '' ");
				this.setParameter(++index, startRange);
				this.setParameter(++index, startRange);
				stringBuilder.append(THEN_CASE);
				stringBuilder.append(" WHEN REGEXP_REPLACE(STARNG,'([[:digit:]])') = REGEXP_REPLACE(?,'([[:digit:]])') ");
				this.setParameter(++index, startRange);
				stringBuilder.append(THEN_1_ELSE_0_END_ELSE_1_END_0);
			}
		}
		if(endrange != null && endrange.trim().length()!=0 ){
			if(isOracleDataSource) {
				stringBuilder.append(AND_CASE);
				stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL");
				this.setParameter(++index, endrange);
				stringBuilder.append(THEN_CASE);
				stringBuilder.append(" WHEN NVL(REGEXP_REPLACE(ENDRNG,'([[:digit:]])'),0) = REGEXP_REPLACE(?,'([[:digit:]])') ");
				this.setParameter(++index, endrange);
				stringBuilder.append(THEN_1_ELSE_0_END_ELSE_1_END_0);
			}else{
				stringBuilder.append(AND_CASE);
				stringBuilder.append("  WHEN REGEXP_REPLACE(?,'([[:digit:]])') IS NOT NULL or REGEXP_REPLACE(?,'([[:digit:]])') = '' ");
				this.setParameter(++index,endrange );
				this.setParameter(++index,endrange );
				stringBuilder.append(THEN_CASE);
				stringBuilder.append(" WHEN REGEXP_REPLACE(ENDRNG,'([[:digit:]])') = REGEXP_REPLACE(?,'([[:digit:]])') ");
				this.setParameter(++index,endrange );
				stringBuilder.append(THEN_1_ELSE_0_END_ELSE_1_END_0);
			}
		}
		stringBuilder.append(") RESULT_TABLE");
		
		
		return stringBuilder.toString();
	}
	/**
	 * @author A-2434
	 * @param yChar
	 * @return long
	 * @exception NumberFormatException
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

	/** To get the numeric value of the string
	 * @author A-2434
	 * @param range
	 * @return Numeric value
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
}
