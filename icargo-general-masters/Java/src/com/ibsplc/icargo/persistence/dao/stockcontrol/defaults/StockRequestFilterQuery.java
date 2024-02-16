/*
 * StockRequestFilterQuery.java Created on Feb 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsSqlDAO.StockRequestMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class StockRequestFilterQuery extends PageableNativeQuery<StockRequestVO> {

	private StockRequestFilterVO stockRequestFilterVO;

	private String baseQuery;
	private Log log = LogFactory.getLogger("RANGE FILTER QUERY");
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";
	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	private boolean isOracleDataSource;
	/**
	 * Constructor
	 * @param stockRequestFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	/* changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
	public StockRequestFilterQuery(StockRequestMapper stockRequestMapper,StockRequestFilterVO stockRequestFilterVO,String baseQuery,boolean isOracleDataSource)
	throws SystemException {
		super(stockRequestFilterVO.getTotalRecords(),stockRequestMapper);
		log = LogFactory.getLogger("RANGE FILTER QUERY");
		this.stockRequestFilterVO = stockRequestFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;
	}
	
	public StockRequestFilterQuery(int pageSize,StockRequestMapper stockRequestMapper,StockRequestFilterVO stockRequestFilterVO,String baseQuery,boolean isOracleDataSource)
	throws SystemException {
		super(pageSize, stockRequestFilterVO.getTotalRecords(),stockRequestMapper);
		log = LogFactory.getLogger("RANGE FILTER QUERY");
		this.stockRequestFilterVO = stockRequestFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;
	}
	
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = stockRequestFilterVO.getCompanyCode();
		String requestRefNumber = stockRequestFilterVO.getRequestRefNumber();
		String stockHolderCode = stockRequestFilterVO.getStockHolderCode();
		String status =  stockRequestFilterVO.getStatus();
		String documentType = stockRequestFilterVO.getDocumentType();
		String documentSubType = stockRequestFilterVO.getDocumentSubType();
		String stockHolderType = stockRequestFilterVO.getStockHolderType();
		String isManual = (stockRequestFilterVO.isManual())?
				StockRequestFilterVO.FLAG_YES:StockRequestFilterVO.FLAG_NO;
		String awbPrefix=stockRequestFilterVO.getAirlineIdentifier();
		log.log(Log.FINE, "\n\n$$$$stockRequestFilterVO$$$",
				stockRequestFilterVO);
		log.log(Log.FINE, "\n\nisManual", isManual);
		String approver= stockRequestFilterVO.getApprover();
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		if (companyCode != null && companyCode.trim().length()!=0) {
			stringBuilder.append("  AND stkreqmst.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
		}
		if (requestRefNumber != null && requestRefNumber.trim().length()!=0) {
			stringBuilder.append("  AND stkreqmst.REQREFNUM = ? ");
			this.setParameter(++index, requestRefNumber);
		}
		if (stockHolderCode != null && stockHolderCode.trim().length()!=0) {
			stringBuilder.append("  AND UPPER(stkreqmst.STKHLDCOD) = ? ");
			this.setParameter(++index, stockHolderCode.toUpperCase());
		}
		if (documentType != null && documentType.trim().length()!=0) {
			stringBuilder.append("  AND stkreqmst.DOCTYP = ? ");
			this.setParameter(++index, documentType);
		}
		if (documentSubType != null && documentSubType.trim().length()!= 0
				&& !(documentSubType.equals(StockRequestFilterVO.FLAG_ALL))){
			stringBuilder.append(" AND stkreqmst.DOCSUBTYP = ? " );
			this.setParameter(++index, documentSubType);
		}
		if (isManual != null && isManual.trim().length()!= 0) {
			stringBuilder.append(" AND stkreqmst.MNLSTKFLG = ? ");
			this.setParameter(++index, isManual);
		}
		if(isOracleDataSource) {
			if (stockRequestFilterVO.getToDate() != null) {
				stringBuilder.append(" AND TRUNC(stkreqmst.REQDAT) <= ? ");
				this.setParameter(++index, stockRequestFilterVO.getToDate());
			}
			if (stockRequestFilterVO.getFromDate() != null) {
				stringBuilder.append(" AND TRUNC(stkreqmst.REQDAT) >= ? ");
				this.setParameter(++index, stockRequestFilterVO.getFromDate());
				}
		}else{
			if (stockRequestFilterVO.getToDate() != null) {
				stringBuilder.append(" AND date_trunc ('day',stkreqmst.REQDAT) <= ? ");
				this.setParameter(++index, stockRequestFilterVO.getToDate());
			}
			if (stockRequestFilterVO.getFromDate() != null) {
				stringBuilder.append(" AND date_trunc ('day',stkreqmst.REQDAT) >= ? ");
				this.setParameter(++index, stockRequestFilterVO.getFromDate());
			}
		}
		if(stockRequestFilterVO.isAllocateCall()){
			if (status != null && status.trim().length()!= 0 && !(status.equals(StockRequestFilterVO.FLAG_ALL))){
				stringBuilder.append(" AND stkreqmst.REQSTA = ? " );
				this.setParameter(++index, status);
			}
			else if(status != null && status.trim().length()!= 0 && status.equals(StockRequestFilterVO.FLAG_ALL)){
				stringBuilder.append(" AND stkreqmst.REQSTA IN ('N','L','A' )");
			}
		}
		else {
			if (status != null && status.trim().length()!= 0 && !(status.equals(StockRequestFilterVO.FLAG_ALL))){
				String stockStatus[] = status.split(",");
				StringBuilder statusCodes = new StringBuilder();
				for (String statusCode : stockStatus) {
					statusCodes.append("'").append(statusCode).append("',"); 
				} 
				if(stockStatus.length > 1){
					stringBuilder.append(" AND stkreqmst.REQSTA IN ("+ statusCodes.substring(0,statusCodes.length()-1)+")");
				}else{
					stringBuilder.append(" AND stkreqmst.REQSTA = ? " );
					this.setParameter(++index, status);
				}
			}
		}
		if (stockHolderType != null && stockHolderType.trim().length()!=0) {
			stringBuilder.append("  AND stkhldmst.STKHLDTYP = ? ");
			this.setParameter(++index, stockHolderType);
		}
		if (approver != null && approver.trim().length()!=0) {
			stringBuilder.append("  AND UPPER(stkhldstk.STKAPRCOD) = ? ");
			this.setParameter(++index, approver.toUpperCase());
		}
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			stringBuilder.append(" AND stkhldstk.ARLIDR=? ");
			this.setParameter(++index, Integer.parseInt(awbPrefix));
		}
		//A-5249 from ICRD-105821

		if(PRVLG_RUL_STK_HLDR.equals(stockRequestFilterVO.getPrivilegeRule())&&
				PRVLG_LVL_STKHLD.equals(stockRequestFilterVO.getPrivilegeLevelType())&&
				stockRequestFilterVO.getPrivilegeLevelValue()!=null && 
				stockRequestFilterVO.getPrivilegeLevelValue().trim().length()>0){
			String[] levelValues = stockRequestFilterVO.getPrivilegeLevelValue().split(",");
			stringBuilder.append(" AND (stkhldstk.STKHLDCOD IN (");
			boolean isFirst = true;
			for(String val : levelValues){
				if(!isFirst){
					stringBuilder.append(", ");	
				}
				stringBuilder.append("?");	
				this.setParameter(++index, val);
				isFirst = false;
			}
			stringBuilder.append(") OR stkhldstk.STKAPRCOD IN (");
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
		stringBuilder.append(" ORDER BY RANK") ;//Added by A-7396 for ICRD-274149
	
		//A-5249 from ICRD-105821 END
		if (stockRequestFilterVO.getRequestCreatedBy() != null && stockRequestFilterVO.getRequestCreatedBy().size() > 0){
			StringBuilder requestCreatedBy = new StringBuilder();
			for (String createdBy : stockRequestFilterVO.getRequestCreatedBy()) {
				requestCreatedBy.append("'").append(createdBy).append("',"); 
			} 
			stringBuilder.append(" AND stkreqmst.REQCRTUSR IN ("+ requestCreatedBy.substring(0,requestCreatedBy.length()-1)+")");
		}
		return stringBuilder.toString();
	}
	
	

}
