/*
 * StockRangeUtilisatonFilterQuery.java Created on Jun 14, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.Timestamp;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
/**
 *
 * @author A-1953
 *
 */
public class StockRangeUtilisatonFilterQuery extends NativeQuery {

	private StockDepleteFilterVO stockDepleteFilterVO;

	private String baseQuery;
	/**
	 * Constructor
	 * @param stockDepleteFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public StockRangeUtilisatonFilterQuery(StockDepleteFilterVO stockDepleteFilterVO,String baseQuery)
	throws SystemException {
		super();
		this.stockDepleteFilterVO = stockDepleteFilterVO;
		this.baseQuery = baseQuery;
	}
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = stockDepleteFilterVO.getCompanyCode();
		String stockHolderCode = stockDepleteFilterVO.getStockHolderCode();
		String documentType = stockDepleteFilterVO.getDocumentType();
		String documentSubType = stockDepleteFilterVO.getDocumentSubType();
		int airlineId = stockDepleteFilterVO.getAirlineId();
		GMTDate actualDate = stockDepleteFilterVO.getActualDate();
		Timestamp actualSqlDate = null;

		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		if (companyCode != null && companyCode.trim().length()!=0) {
			stringBuilder.append(" STK.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
		}
		if (documentType != null && documentType.trim().length()!=0) {
			stringBuilder.append("  AND STK.DOCTYP = ? ");
			this.setParameter(++index, documentType);
		}
		if (stockHolderCode != null && stockHolderCode.trim().length()!=0) {
			stringBuilder.append("  AND STK.STKHLDCOD = ? ");
			this.setParameter(++index, stockHolderCode); 
		}
		if (documentSubType != null && documentSubType.trim().length()!= 0){
			stringBuilder.append(" AND STK.DOCSUBTYP = ? " );
			this.setParameter(++index, documentSubType);
		}
		if (airlineId != 0){
			stringBuilder.append(" AND STK.ARLIDR= ? " );    
			this.setParameter(++index, airlineId);
		}
		if (actualDate!=null){
			stringBuilder.append(" AND STK.LSTUPDTIMUTC < ? " ); 
			actualSqlDate = actualDate.toSqlTimeStamp(); 
			actualSqlDate.setNanos(0);
			this.setParameter(++index,actualSqlDate );
		}  
		stringBuilder.append(" ORDER BY CMPCOD,ARLIDR,DOCTYP,DOCSUBTYP,STKHLDCOD ");
		return stringBuilder.toString();
	}

}
