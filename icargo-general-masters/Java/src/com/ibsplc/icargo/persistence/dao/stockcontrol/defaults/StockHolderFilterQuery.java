/*
 * StockHolderFilterQuery.java Created on Feb 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

/**
 * 
 * @author A-1754
 * 
 */
public class StockHolderFilterQuery extends
		PageableNativeQuery<StockHolderDetailsVO> {

	private StockHolderFilterVO stockHolderFilterVO;

	private String baseQuery;

	/**
	 * Constructor
	 * 
	 * @param stockHolderFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	
	//Added by A-5175 for CR ICRD-20959 starts
	/*
	 * public StockHolderFilterQuery(StockHolderFilterVO
	 * stockHolderFilterVO,String baseQuery) throws SystemException { super();
	 * this.stockHolderFilterVO = stockHolderFilterVO; this.baseQuery =
	 * baseQuery; }
	 */

	public StockHolderFilterQuery(int totalRecordCount,
			StockControlDefaultsSqlDAO.StockHolderMapper stockHolderMapper,
			StockHolderFilterVO stockHolderFilterVO, String baseQuery)
			throws SystemException {
		super(totalRecordCount, stockHolderMapper);
		this.stockHolderFilterVO = stockHolderFilterVO;
		this.baseQuery = baseQuery;
	}
	//Added by A-5175 for CR ICRD-20959 ends

	/**
	 * This method is used to create query string
	 * 
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = stockHolderFilterVO.getCompanyCode();
		String stockHolderCode = stockHolderFilterVO.getStockHolderCode();
		String documentType = stockHolderFilterVO.getDocumentType();
		String documentSubType = stockHolderFilterVO.getDocumentSubType();
		String stockHolderType = stockHolderFilterVO.getStockHolderType();
		int priority = stockHolderFilterVO.getStockHolderPriority();
		String airlineIdentifier = stockHolderFilterVO.getAirlineIdentifier();

		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		if (companyCode != null && companyCode.trim().length() != 0) {
			stringBuilder.append("  AND stkhldstk.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
		}
		if (documentType != null && documentType.trim().length() != 0) {
			stringBuilder.append("  AND stkhldstk.DOCTYP = ? ");
			this.setParameter(++index, documentType);
		}
		if (stockHolderCode != null && stockHolderCode.trim().length() != 0) {
			stringBuilder.append("  AND stkhldstk.STKAPRCOD = ? ");
			this.setParameter(++index, stockHolderCode);
		}
		if (documentSubType != null && documentSubType.trim().length() != 0) {
			stringBuilder.append(" AND stkhldstk.DOCSUBTYP = ? ");
			this.setParameter(++index, documentSubType);
		}
		if (priority > 0) {
			stringBuilder.append(" AND stkhldtyppry.STKHLDPRY >= ? ");
			this.setParameter(++index, priority);
		}
		if (airlineIdentifier != null && airlineIdentifier.trim().length() > 0) {
			stringBuilder.append(" AND stkhldstk.ARLIDR = ?::NUMERIC ");
			this.setParameter(++index, airlineIdentifier);
		}

		// Added By Venkatesa Perumal S
		stringBuilder.append(" AND STKHLDMST.STKHLDTYP=?  ");
		this.setParameter(++index, stockHolderType);

		if (stockHolderCode != null && stockHolderCode.trim().length() > 0) {
			stringBuilder.append(" UNION ALL SELECT ");
			stringBuilder.append(" stkhldmst.CMPCOD AS CMPCOD,");
			stringBuilder.append(" stkhldmst.STKHLDTYP AS STKHLDTYP, ");
			stringBuilder.append(" stkhldmst.STKHLDCOD AS STKHLDCOD,");
			stringBuilder.append(" stkhldstk.DOCTYP AS DOCTYP ,");
			stringBuilder.append(" stkhldstk.DOCSUBTYP AS DOCSUBTYP, ");
			stringBuilder.append(" arl.THRNUMCOD AS THRNUMCOD, ");
			stringBuilder.append(" stkhldstk.ORDLVL AS ORDLVL, ");
			stringBuilder.append(" stkhldstk.ORDQTY AS ORDQTY ,");
			stringBuilder.append(" stkhldstk.ORDALT AS ORDALT,");
			stringBuilder.append(" stkhldstk.AUTREQFLG AS AUTREQFLG,");
			stringBuilder.append(" stkhldstk.STKAPRCOD AS STKAPRCOD,");
			stringBuilder.append(" stkhldmst.LSTUPDTIM AS LSTUPDTIM,");
			stringBuilder.append(" stkhldmst.LSTUPDUSR AS LSTUPDUSR ");
			stringBuilder
					.append("  FROM  STKHLDSTK stkhldstk,  STKHLDMST stkhldmst,SHRARLMST arl   ");
			stringBuilder
					.append(" WHERE  stkhldstk.CMPCOD = stkhldmst.CMPCOD AND ");
			stringBuilder
					.append(" stkhldstk.STKHLDCOD = stkhldmst.STKHLDCOD AND ");
			stringBuilder.append(" stkhldstk.CMPCOD = arl.CMPCOD AND ");
			stringBuilder.append(" stkhldstk.ARLIDR = arl.ARLIDR ");

			if (companyCode != null && companyCode.trim().length() != 0) {
				stringBuilder.append("  AND stkhldstk.CMPCOD = ? ");
				this.setParameter(++index, companyCode);
			}
			if (documentType != null && documentType.trim().length() != 0) {
				stringBuilder.append("  AND stkhldstk.DOCTYP = ? ");
				this.setParameter(++index, documentType);
			}
			if (stockHolderCode != null && stockHolderCode.trim().length() != 0) {
				stringBuilder.append("  AND stkhldstk.STKHLDCOD = ? ");
				this.setParameter(++index, stockHolderCode);
			}
			if (documentSubType != null && documentSubType.trim().length() != 0) {
				stringBuilder.append(" AND stkhldstk.DOCSUBTYP = ? ");
				this.setParameter(++index, documentSubType);
			}
			if (stockHolderType != null && stockHolderType.trim().length() != 0) {
				stringBuilder.append("  AND stkhldmst.STKHLDTYP  = ? ");
				this.setParameter(++index, stockHolderType);
			}
			if (airlineIdentifier != null
					&& airlineIdentifier.trim().length() > 0) {
				stringBuilder.append(" AND stkhldstk.ARLIDR = ?::NUMERIC ");
				this.setParameter(++index, airlineIdentifier);
			}
		}
		return stringBuilder.toString();
	}

}
