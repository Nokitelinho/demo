/*
 * StockRangeHistoryUtilFilterQuery.java Created on Jun 06, 2012
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
public class StockRangeHistoryUtilFilterQuery extends
	PageableNativeQuery<StockRangeHistoryVO> {
	//Added by A-5220 for ICRD-20959 ends

	private StockRangeFilterVO stockRangeFilterVO;

	private String baseQuery;

	private String status;
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	private boolean isOracleDataSource;

	//Added by A-5220 for ICRD-20959 starts
	public StockRangeHistoryUtilFilterQuery(Mapper<StockRangeHistoryVO> mapper, StockRangeFilterVO stockRangeFilterVO, String baseQuery,
			String status , boolean isOracleDataSource) throws SystemException {

		// calling the base class constructor
		super(stockRangeFilterVO.getTotalRecordsCount(), mapper);

		this.stockRangeFilterVO = stockRangeFilterVO;

		this.baseQuery = baseQuery;

		this.status = status;
		this.isOracleDataSource = isOracleDataSource;
	}
	public StockRangeHistoryUtilFilterQuery(int pageSize,Mapper<StockRangeHistoryVO> mapper, StockRangeFilterVO stockRangeFilterVO, String baseQuery,
			String status, boolean isOracleDataSource) throws SystemException {

		// calling the base class constructor
		super(pageSize,stockRangeFilterVO.getTotalRecordsCount(), mapper);

		this.stockRangeFilterVO = stockRangeFilterVO;

		this.baseQuery = baseQuery;

		this.status = status;
		this.isOracleDataSource = isOracleDataSource;
	}
	//Added by A-5220 for ICRD-20959 ends

	/**
	 * Overriding the getNativeQuery method to append the rest of the query
	 * based on filter criteria
	 */
	public String getNativeQuery() {
		StringBuilder query = new StringBuilder(baseQuery);
		String status = stockRangeFilterVO.getStatus();
		LocalDate startDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true);
		String Flag = stockRangeFilterVO.getRangeType();
		long stRange = 0;
		long edrange = 0 ;
		if (StockAllocationVO.MODE_NEUTRAL.equals(Flag)) {
			Flag = "N";
		} else {
			Flag = "Y";
		}
		if ((StockAllocationVO.MODE_UNUSED).equalsIgnoreCase(status)) {

			int index = 0;
			this.setParameter(++index, stockRangeFilterVO.getCompanyCode());
			log.log(Log.FINE, "\n\n----------INSIDE F---");

			if (stockRangeFilterVO.getAirlineIdentifier() != 0) {
				query.append(" AND RNG.ARLIDR = ?");
				this.setParameter(++index, stockRangeFilterVO
						.getAirlineIdentifier());

			}

			if ((stockRangeFilterVO.getEndRange() == null || stockRangeFilterVO
					.getEndRange().trim().length() == 0)
					&& (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
							.getStartRange().trim().length() > 0)) {
				query
						.append(" AND  (RNG.ASCSTARNG >= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
				String startRange=stockRangeFilterVO.getStartRange();
				if(startRange.length()>7){
				startRange=startRange.substring(0, startRange.length() - 1); 
				}
				stRange = findLong(startRange);			
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, stRange);
			}
			if ((stockRangeFilterVO.getStartRange() == null || stockRangeFilterVO
					.getStartRange().trim().length() == 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append(" AND  (RNG.ASCENDRNG <= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
				String endRange=stockRangeFilterVO.getEndRange();
				if(endRange.length()>7){
				endRange=endRange.substring(0, endRange.length() - 1); 
				}
				edrange = findLong(endRange);
				this.setParameter(++index, edrange);
				this.setParameter(++index, edrange);
			}
			if ((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
					.getStartRange().trim().length() > 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append("  AND ( ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG) OR ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG)OR ( RNG.ASCSTARNG >= ? AND RNG.ASCENDRNG <= ? ) )");
				String startRange=stockRangeFilterVO.getStartRange();
				if(startRange.length()>7){
				startRange=startRange.substring(0, startRange.length() - 1); 
				}
				stRange = findLong(startRange);	
				String endRange=stockRangeFilterVO.getEndRange();
				if(endRange.length()>7){
				endRange=endRange.substring(0, endRange.length() - 1); 
				}
				edrange = findLong(endRange);
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
			}
			/*
			 * if (stockRangeFilterVO.getRangeType() != null &&
			 * stockRangeFilterVO.getRangeType().trim().length()>0) {
			 * query.append(" AND RNG.MNLFLG= ?");
			 * this.setParameter(++index,Flag); }
			 */
			if (stockRangeFilterVO.getFromStockHolderCode() != null
					&& stockRangeFilterVO.getFromStockHolderCode().trim()
							.length() > 0) {
				query.append("AND RNG.STKHLDCOD=?");
				this.setParameter(++index, stockRangeFilterVO
						.getFromStockHolderCode());
			}
			//A-5249 from ICRD-105821
			if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
					PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
					stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
					stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
				String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
				query.append(" AND (STK.STKHLDCOD IN (");
				boolean isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append(") OR STK.STKAPRCOD IN (");
				isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append("))");				
				
			}
			//A-5249 from ICRD-105821 END
			if (stockRangeFilterVO.getDocumentType() != null
					&& stockRangeFilterVO.getDocumentType().trim().length() > 0 ) {
				query.append(" AND RNG.DOCTYP= ?");
				this
						.setParameter(++index, stockRangeFilterVO
								.getDocumentType());
			}
			if (stockRangeFilterVO.getDocumentSubType() != null
					&& stockRangeFilterVO.getDocumentSubType().trim().length() > 0) {
				query.append(" AND RNG.DOCSUBTYP= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getDocumentSubType());
			}

			if (stockRangeFilterVO.getStartDate() != null
					&& stockRangeFilterVO.getEndDate() != null) {
				if(isOracleDataSource) {
					query.append(" AND TRUNC(RNG.RNGACPDAT) BETWEEN ? AND ? ");
					}else {
						query.append(" AND date_trunc('day', rng.rngacpdat) BETWEEN ? AND ? ");
					}
				this.setParameter(++index, stockRangeFilterVO.getStartDate());
				this.setParameter(++index, stockRangeFilterVO.getEndDate());
				log.log(Log.INFO, "stockRangeFilterVO.getStartDate(): "+stockRangeFilterVO.getStartDate());
				log.log(Log.INFO, "stockRangeFilterVO.getEndDate(): "+stockRangeFilterVO.getEndDate());

			}
			if (stockRangeFilterVO.getAccountNumber() != null
					&& stockRangeFilterVO.getAccountNumber().trim().length() > 0) {
				query
						.append("AND RNG.STKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
				this.setParameter(++index, stockRangeFilterVO
						.getAccountNumber());
			}

		}

		if (("").equals(status)) {
			int index = 0;
			this.setParameter(++index, stockRangeFilterVO.getCompanyCode());
			if (stockRangeFilterVO.getAirlineIdentifier() != 0) {
				query.append(" AND RNG.ARLIDR = ?");
				this.setParameter(++index, stockRangeFilterVO
						.getAirlineIdentifier());

			}
			if ((stockRangeFilterVO.getEndRange() == null || stockRangeFilterVO
					.getEndRange().trim().length() == 0)
					&& (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
							.getStartRange().trim().length() > 0)) {
				query
						.append(" AND  (RNG.ASCSTARNG >= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
				
				stRange = findLong(stockRangeFilterVO.getStartRange());				
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, stRange);
			}
			if ((stockRangeFilterVO.getStartRange() == null || stockRangeFilterVO
					.getStartRange().trim().length() == 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append(" AND  (RNG.ASCENDRNG <= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
				
				edrange = findLong(stockRangeFilterVO.getEndRange());
				this.setParameter(++index, edrange);
				this.setParameter(++index, edrange);
			}
			if ((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
					.getStartRange().trim().length() > 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append("  AND ( ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG) OR ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG)OR ( RNG.ASCSTARNG >= ? AND RNG.ASCENDRNG <= ? ) )");
				
				stRange = findLong(stockRangeFilterVO.getStartRange());
				edrange = findLong(stockRangeFilterVO.getEndRange());
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
			}
			/*
			 * if (stockRangeFilterVO.getRangeType() != null &&
			 * stockRangeFilterVO.getRangeType().trim().length()>0) {
			 * query.append(" AND RNG.MNLFLG= ?");
			 * this.setParameter(++index,Flag); }
			 */
			if (stockRangeFilterVO.getFromStockHolderCode() != null
					&& stockRangeFilterVO.getFromStockHolderCode().trim()
							.length() > 0) {
				query.append("AND RNG.STKHLDCOD=?");
				this.setParameter(++index, stockRangeFilterVO
						.getFromStockHolderCode());
			}
			//A-5249 from ICRD-105821
			if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
					PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
					stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
					stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
				String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
				query.append(" AND (STK.STKHLDCOD IN (");
				boolean isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append(") OR STK.STKAPRCOD IN (");
				isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append("))");				
				
			}
			//A-5249 from ICRD-105821 END
			if (stockRangeFilterVO.getDocumentType() != null
					&& stockRangeFilterVO.getDocumentType().trim().length() > 0) {
				query.append(" AND RNG.DOCTYP= ?");
				this
						.setParameter(++index, stockRangeFilterVO
								.getDocumentType());
			}
			if (stockRangeFilterVO.getDocumentSubType() != null
					&& stockRangeFilterVO.getDocumentSubType().trim().length() > 0) {
				query.append(" AND RNG.DOCSUBTYP= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getDocumentSubType());
			}
			if (stockRangeFilterVO.getStartDate() != null
					&& stockRangeFilterVO.getEndDate() != null) {
				if(isOracleDataSource) {
					query.append(" AND TRUNC(RNG.RNGACPDAT) BETWEEN ? AND ? ");
					}else {
						query.append(" AND date_trunc('day', rng.rngacpdat) BETWEEN ? AND ? ");
					}
				this.setParameter(++index, stockRangeFilterVO.getStartDate());
				this.setParameter(++index, stockRangeFilterVO.getEndDate());

			}
			if (stockRangeFilterVO.getAccountNumber() != null
					&& stockRangeFilterVO.getAccountNumber().trim().length() > 0) {
				query
						.append("AND RNG.STKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
				this.setParameter(++index, stockRangeFilterVO
						.getAccountNumber());
			}

			log.log(Log.FINE, "\n\n----------INSIDE UNION---");
			query.append("UNION ");

			/*query
					.append("SELECT DISTINCT UTLHIST.FRMSTKHLDCOD AS STKHLDCOD,UTLHIST.RNGTYP AS MNLFLG,UTLHIST.DOCTYP,UTLHIST.DOCSUBTYP,UTLHIST.STARNG,UTLHIST.ENDRNG,UTLHIST.NUMDOC AS DOCNUM,UTLHIST.TXNDAT AS STKDAT,NVL(UTLHIST.STATUS,'U') AS STATUS,ARLMST.AWBCHKDGT FROM STKRNGTXNHIS UTLHIST,SHRARLMST ARLMST");*/
			//Added by A-5220 for ICRD-20959 starts
			query
			.append("SELECT DISTINCT UTLHIST.FRMSTKHLDCOD AS STKHLDCOD,UTLHIST.RNGTYP AS MNLFLG,UTLHIST.DOCTYP,UTLHIST.DOCSUBTYP,UTLHIST.STARNG,UTLHIST.ENDRNG,UTLHIST.NUMDOC AS DOCNUM,UTLHIST.TXNDAT AS STKDAT,COALESCE(UTLHIST.STATUS,'U') AS STATUS,ARLMST.AWBCHKDGT FROM SHRARLMST ARLMST ,STKRNGTXNHIS UTLHIST LEFT OUTER JOIN STKHLDSTK STK ON STK.ARLIDR   = UTLHIST.ARLIDR")
					.append(" AND STK.CMPCOD   = UTLHIST.CMPCOD AND STK.STKHLDCOD = UTLHIST.FRMSTKHLDCOD AND STK.DOCSUBTYP = UTLHIST.DOCSUBTYP AND STK.DOCTYP = UTLHIST.DOCTYP WHERE ARLMST.CMPCOD = UTLHIST.CMPCOD AND ARLMST.ARLIDR=UTLHIST.ARLIDR AND UTLHIST.CMPCOD=? AND UTLHIST.ARLIDR=?    ");

			//query.append(" WHERE UTLHIST.CMPCOD=?");

			this.setParameter(++index, stockRangeFilterVO.getCompanyCode());

			/*query.append(" AND ARLMST.ARLIDR=UTLHIST.ARLIDR");

			query.append(" AND UTLHIST.ARLIDR=?");*/
			//Added by A-5220 for ICRD-20959 ends

			this.setParameter(++index, stockRangeFilterVO
					.getAirlineIdentifier());

			query.append(" AND UTLHIST.STATUS=?");

			this.setParameter(++index, StockAllocationVO.MODE_USED);

			if ((stockRangeFilterVO.getEndRange() == null || stockRangeFilterVO
					.getEndRange().trim().length() == 0)
					&& (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
							.getStartRange().trim().length() > 0)) {
				query
						.append(" AND  (UTLHIST.ASCSTARNG >= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
				stRange = findLong(stockRangeFilterVO.getStartRange());
				this.setParameter(++index, stRange);
				this.setParameter(++index, stRange);
			}

			if ((stockRangeFilterVO.getStartRange() == null || stockRangeFilterVO
					.getStartRange().trim().length() == 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append(" AND  (UTLHIST.ASCENDRNG <= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
				edrange = findLong(stockRangeFilterVO.getEndRange());
				this.setParameter(++index, edrange);
				this.setParameter(++index, edrange);
			}

			if ((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
					.getStartRange().trim().length() > 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append("  AND ( ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG) OR ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG)OR ( UTLHIST.ASCSTARNG >= ? AND UTLHIST.ASCENDRNG <= ? ) )");
				stRange = findLong(stockRangeFilterVO.getStartRange());
				edrange = findLong(stockRangeFilterVO.getEndRange());
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
			}

			if (stockRangeFilterVO.getFromStockHolderCode() != null
					&& stockRangeFilterVO.getFromStockHolderCode().trim()
							.length() > 0) {
				query.append(" AND UTLHIST.FRMSTKHLDCOD= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getFromStockHolderCode());
			}

			//A-5249 from ICRD-105821
			if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
					PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
					stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
					stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
				String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
				query.append(" AND (STK.STKHLDCOD IN (");
				boolean isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append(") OR STK.STKAPRCOD IN (");
				isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append("))");				
				
			}
			//A-5249 from ICRD-105821 END
			/*
			 * if (stockRangeFilterVO.getRangeType() != null &&
			 * stockRangeFilterVO.getRangeType().trim().length()>0) {
			 * query.append(" AND UTLHIST.RNGTYP= ?");
			 * this.setParameter(++index, stockRangeFilterVO.getRangeType()); }
			 */

			if (stockRangeFilterVO.getStatus() != null
					&& stockRangeFilterVO.getStatus().trim().length() > 0) {
				query.append(" AND UTLHIST.STATUS= ?");
				this.setParameter(++index, stockRangeFilterVO.getStatus());
			}
			if (stockRangeFilterVO.getDocumentType() != null
					&& stockRangeFilterVO.getDocumentType().trim().length() > 0 ) {
				query.append(" AND UTLHIST.DOCTYP= ?");
				this
						.setParameter(++index, stockRangeFilterVO
								.getDocumentType());
			}
			if (stockRangeFilterVO.getDocumentSubType() != null
					&& stockRangeFilterVO.getDocumentSubType().trim().length() > 0) {
				query.append(" AND UTLHIST.DOCSUBTYP= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getDocumentSubType());
			}

			log.log(Log.FINE, "\n\n----startdate--------------"
					+ stockRangeFilterVO.getStartDate());
			log.log(Log.FINE, "\n\n----enddate--------------"
					+ stockRangeFilterVO.getEndDate());
			if (stockRangeFilterVO.getStartDate() != null
					&& stockRangeFilterVO.getEndDate() != null) {

				String startDateStr = new StringBuilder().append(
						stockRangeFilterVO.getStartDate()
								.toDisplayDateOnlyFormat()).append(" ").append(
						"00:00").toString();
				String endDateStr = new StringBuilder().append(
						stockRangeFilterVO.getEndDate()
								.toDisplayDateOnlyFormat()).append(" ").append(
						"23:59").toString();
				startDate.setDateAndTime(startDateStr, true);
				endDate.setDateAndTime(endDateStr, true);
				query.append(" AND UTLHIST.TXNDAT >= ? ");
				query.append(" AND UTLHIST.TXNDAT <= ? ");
				this.setParameter(++index, startDate);
				this.setParameter(++index, endDate);

			}
			if (stockRangeFilterVO.getAccountNumber() != null
					&& stockRangeFilterVO.getAccountNumber().trim().length() > 0) {
				query
						.append("AND UTLHIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
				this.setParameter(++index, stockRangeFilterVO
						.getAccountNumber());
			}

		}
		if ((StockAllocationVO.MODE_USED).equalsIgnoreCase(status)) {

			int index = 0;
			this.setParameter(++index, stockRangeFilterVO.getCompanyCode());

			this.setParameter(++index, stockRangeFilterVO
					.getAirlineIdentifier());

			log.log(Log.FINE, "\n\n----------INSIDE U---");

			if ((stockRangeFilterVO.getEndRange() == null || stockRangeFilterVO
					.getEndRange().trim().length() == 0)
					&& (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
							.getStartRange().trim().length() > 0)) {
				query
						.append("  AND  (UTLHIST.ASCSTARNG >= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
				stRange = findLong(stockRangeFilterVO.getStartRange());
				this.setParameter(++index, stRange);
				this.setParameter(++index, stRange);
			}

			if ((stockRangeFilterVO.getStartRange() == null || stockRangeFilterVO
					.getStartRange().trim().length() == 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append(" AND  (UTLHIST.ASCENDRNG <= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
				edrange = findLong(stockRangeFilterVO.getEndRange());
				this.setParameter(++index, edrange);
				this.setParameter(++index, edrange);
			}

			if ((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO
					.getStartRange().trim().length() > 0)
					&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO
							.getEndRange().trim().length() > 0)) {
				query
						.append("  AND ( ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG) OR ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG)OR ( UTLHIST.ASCSTARNG >= ? AND UTLHIST.ASCENDRNG <= ? ) )");
				stRange = findLong(stockRangeFilterVO.getStartRange());
				edrange = findLong(stockRangeFilterVO.getEndRange());
				
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
				this.setParameter(++index, stRange);
				this.setParameter(++index, edrange);
			}

			if (stockRangeFilterVO.getFromStockHolderCode() != null
					&& stockRangeFilterVO.getFromStockHolderCode().trim()
							.length() > 0) {
				query.append(" AND UTLHIST.FRMSTKHLDCOD= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getFromStockHolderCode());
			}
			//A-5249 from ICRD-105821
			if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
					PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
					stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
					stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
				String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
				query.append(" AND (STK.STKHLDCOD IN (");
				boolean isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append(") OR STK.STKAPRCOD IN (");
				isFirst = true;
				for(String val : levelValues){
					if(!isFirst){
						query.append(", ");	
					}
					query.append("?");	
					this.setParameter(++index, val);
					isFirst = false;
				}
				query.append("))");				
				
			}
			//A-5249 from ICRD-105821 END

			/*
			 * if (stockRangeFilterVO.getRangeType() != null &&
			 * stockRangeFilterVO.getRangeType().trim().length()>0) {
			 * query.append(" AND UTLHIST.RNGTYP= ?");
			 * this.setParameter(++index, stockRangeFilterVO.getRangeType()); }
			 */

			if (stockRangeFilterVO.getStatus() != null
					&& stockRangeFilterVO.getStatus().trim().length() > 0) {
				query.append(" AND UTLHIST.STATUS= ?");
				this.setParameter(++index, stockRangeFilterVO.getStatus());
			}
			if (stockRangeFilterVO.getDocumentType() != null
					&& stockRangeFilterVO.getDocumentType().trim().length() > 0 ) {
				query.append(" AND UTLHIST.DOCTYP= ?");
				this
						.setParameter(++index, stockRangeFilterVO
								.getDocumentType());
			}
			if (stockRangeFilterVO.getDocumentSubType() != null
					&& stockRangeFilterVO.getDocumentSubType().trim().length() > 0) {
				query.append(" AND UTLHIST.DOCSUBTYP= ?");
				this.setParameter(++index, stockRangeFilterVO
						.getDocumentSubType());
			}

			log.log(Log.FINE, "\n\n----startdate--------------"
					+ stockRangeFilterVO.getStartDate());
			log.log(Log.FINE, "\n\n----enddate--------------"
					+ stockRangeFilterVO.getEndDate());
			if (stockRangeFilterVO.getStartDate() != null
					&& stockRangeFilterVO.getEndDate() != null) {
				String startDateStr = new StringBuilder().append(
						stockRangeFilterVO.getStartDate()
								.toDisplayDateOnlyFormat()).append(" ").append(
						"00:00").toString();
				String endDateStr = new StringBuilder().append(
						stockRangeFilterVO.getEndDate()
								.toDisplayDateOnlyFormat()).append(" ").append(
						"23:59").toString();
				startDate.setDateAndTime(startDateStr, true);
				endDate.setDateAndTime(endDateStr, true);
				query.append(" AND UTLHIST.TXNDAT >= ? ");
				query.append(" AND UTLHIST.TXNDAT <= ? ");
				this.setParameter(++index, startDate);
				this.setParameter(++index, endDate);

			}
			if (stockRangeFilterVO.getAccountNumber() != null
					&& stockRangeFilterVO.getAccountNumber().trim().length() > 0) {
				query
						.append("AND UTLHIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
				this.setParameter(++index, stockRangeFilterVO
						.getAccountNumber());
			}

		}
		//Modified by A-5220 for ICRD-20959 starts
		query.append(" ORDER BY STKHLDCOD,MNLFLG,STATUS,STKDAT");       
		query.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-20959 ends
		log.log(Log.INFO, "**query: "+query.toString());
		return query.toString();
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
