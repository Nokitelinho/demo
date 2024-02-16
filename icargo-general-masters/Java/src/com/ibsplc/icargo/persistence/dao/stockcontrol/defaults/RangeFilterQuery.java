/*
 * RangeFilterQuery.java Created on Feb 23, 2006
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class RangeFilterQuery extends NativeQuery {

	private RangeFilterVO rangeFilterVO;
	private String baseQuery;
	private Log log = LogFactory.getLogger("RANGE FILTER QUERY");
	/**
	 * @param rangeFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public RangeFilterQuery(RangeFilterVO rangeFilterVO,String baseQuery)
	throws SystemException {
		super();
		this.rangeFilterVO = rangeFilterVO;
		this.baseQuery = baseQuery;
	}
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		String companyCode = rangeFilterVO.getCompanyCode();
		String stockHolderCode = rangeFilterVO.getStockHolderCode();
		String documentType = rangeFilterVO.getDocumentType();
		String documentSubType = rangeFilterVO.getDocumentSubType();
		String stRange = rangeFilterVO.getStartRange();
		String edRange = rangeFilterVO.getEndRange();
		int airlineIdentifier=rangeFilterVO.getAirlineIdentifier();
		long startRange = 0;
		long endrange = 0 ;
		log.log(Log.FINE, "\n\n@@@@@@@@rangeFilterVO@@@@@@", rangeFilterVO);
		if(stRange != null && stRange.trim().length()!=0 ){
			startRange = findLong(stRange);
			log.log(Log.FINE, "\n\n@@@@@@@@startRange@@@@@@", startRange);
		}
		if(edRange != null &&  edRange.trim().length()!=0){
			endrange = findLong(edRange);
			log.log(Log.FINE, "\n\n@@@@@@@@endrange@@@@@@", endrange);
		}
		String noOfDocs = rangeFilterVO.getNumberOfDocuments();
		String isManual = rangeFilterVO.isManual()? RangeFilterVO.FLAG_YES : RangeFilterVO.FLAG_NO;

		if(stRange != null && (edRange == null || edRange.trim().length() == 0) &&
				noOfDocs != null && noOfDocs.trim().length() != 0){
			stringBuilder.append(" ,STKHLDMST stkhldmst , STKRNG stkrng1 WHERE stkrng.CMPCOD = stkrng1.CMPCOD AND ");
			stringBuilder.append(" stkrng.STKHLDCOD = stkrng1.STKHLDCOD AND ");
			stringBuilder.append(" stkrng.DOCTYP = stkrng1.DOCTYP AND ");
			stringBuilder.append(" stkrng.DOCSUBTYP = stkrng1.DOCSUBTYP ");
			stringBuilder.append(" AND stkrng.CMPCOD = stkhldmst.CMPCOD and  stkrng.STKHLDCOD = stkhldmst.STKHLDCOD");
			stringBuilder.append(" AND stkrng.ARLIDR = stkrng1.ARLIDR ");
			if (companyCode != null && companyCode.trim().length()!=0) {

				stringBuilder.append(" AND  stkrng.CMPCOD = ? ");
				this.setParameter(++index, companyCode);
			}
			if (stockHolderCode != null && stockHolderCode.trim().length()!=0) {
				stringBuilder.append("  AND stkrng.STKHLDCOD = ? ");
				this.setParameter(++index, stockHolderCode);
			}
			if (rangeFilterVO.getAirlineIdentifier() != 0 ) {
				//stringBuilder.append("  AND STKRNG.ARLIDR = ? ");
				//this.setParameter(++index, rangeFilterVO.getAirlineIdentifier());
			}
			if (documentType != null && documentType.trim().length()!=0) {
				stringBuilder.append("  AND stkrng.DOCTYP = ? ");
				this.setParameter(++index, documentType);
			}
			if (documentSubType != null && documentSubType.trim().length()!= 0){
				stringBuilder.append(" AND stkrng.DOCSUBTYP = ? " );
				this.setParameter(++index, documentSubType);
			}
			if (isManual != null && isManual.trim().length()!= 0){
				stringBuilder.append(" AND stkrng.MNLFLG = ?  AND stkrng1.MNLFLG = ? " );
				this.setParameter(++index, isManual);
				this.setParameter(++index, isManual);
			}
			if(airlineIdentifier>0){
				stringBuilder.append(" AND stkrng.ARLIDR = ? " );
				this.setParameter(++index, airlineIdentifier);
			}
			/*	stringBuilder.append(" AND stkrng.STARNG >= ?  AND stkrng1.STARNG >= ? AND "+
			 "stkrng1.ROWID <= stkrng.ROWID	GROUP BY stkrng.CMPCOD, stkrng.STKHLDCOD,"+
			 "stkrng.DOCTYP,stkrng.DOCSUBTYP,stkrng.STARNG,stkrng.ENDRNG,stkrng.ROWID,"+
			 "stkrng.DOCNUM	HAVING sum(stkrng1.DOCNUM) < ? + stkrng.DOCNUM  ");		*/
			stringBuilder.append(" AND	stkrng1.ASCSTARNG <= stkrng.ASCSTARNG AND ((stkrng1.ASCSTARNG > ? OR stkrng1.ASCENDRNG > ? )");
			stringBuilder.append("OR (( ? BETWEEN STKRNG1.ASCSTARNG AND STKRNG1.ASCENDRNG )OR ( ? BETWEEN STKRNG1.ASCSTARNG AND STKRNG1.ASCENDRNG )) )");
			stringBuilder.append(" GROUP BY stkrng.CMPCOD, stkrng.STKHLDCOD, stkrng.DOCTYP,stkrng.DOCSUBTYP,stkrng.STARNG,stkrng.ENDRNG, stkrng.DOCNUM ");
			stringBuilder.append(" ,stkrng.ascstarng,stkhldmst.lstupdtim,stkrng.RNGSEQNUM, stkrng.ARLIDR");
			stringBuilder.append(" HAVING SUM(LEAST(stkrng1.ASCENDRNG - stkrng1.ASCSTARNG + 1, stkrng1.ASCENDRNG - ? + 1)) < ? + stkrng.DOCNUM ");
			this.setParameter(++index, startRange);
			this.setParameter(++index, startRange);
			this.setParameter(++index, startRange);
			this.setParameter(++index, startRange);
			this.setParameter(++index, startRange);
			this.setParameter(++index, Integer.parseInt(noOfDocs));
		}
		else{
			stringBuilder.append(",STKHLDMST stkhldmst WHERE ");
			stringBuilder.append("stkrng.CMPCOD = stkhldmst.CMPCOD and  stkrng.STKHLDCOD = stkhldmst.STKHLDCOD");
			if (companyCode != null && companyCode.trim().length()!=0) {
				stringBuilder.append(" AND stkrng.CMPCOD = ? ");
				this.setParameter(++index, companyCode);
			}
			if (stockHolderCode != null && stockHolderCode.trim().length()!=0) {
				stringBuilder.append("  AND stkrng.STKHLDCOD = ? ");
				this.setParameter(++index, stockHolderCode);
			}
			if (documentType != null && documentType.trim().length()!=0) {
				stringBuilder.append("  AND stkrng.DOCTYP = ? ");
				this.setParameter(++index, documentType);
			}
			if (documentSubType != null && documentSubType.trim().length()!= 0){
				stringBuilder.append(" AND stkrng.DOCSUBTYP = ? " );
				this.setParameter(++index, documentSubType);
			}
			if (isManual != null && isManual.trim().length()!= 0){
				stringBuilder.append(" AND stkrng.MNLFLG = ? " );
				this.setParameter(++index, isManual);
			}
			if(airlineIdentifier>0){
				stringBuilder.append(" AND stkrng.ARLIDR = ? " );
				this.setParameter(++index, airlineIdentifier);
			}
			if(stRange != null && edRange == null && noOfDocs == null ){
				stringBuilder.append("  AND stkrng.ASCSTARNG >= ? ");
				this.setParameter(++index, startRange);
			}
			else if(stRange != null && edRange != null ){
				stringBuilder.append("AND( (( stkrng.ascstarng  >= ? ) and (stkrng.ascendrng <= ? ))");
				stringBuilder.append(" OR ((? BETWEEN stkrng.ascstarng  AND stkrng.ascendrng) OR ");
				stringBuilder.append("(? BETWEEN stkrng.ascstarng  AND stkrng.ascendrng)) )");
				this.setParameter(++index, startRange);
				this.setParameter(++index, endrange);
				this.setParameter(++index, startRange);
				this.setParameter(++index, endrange);
			}
		}
		stringBuilder.append(" ORDER BY stkrng.ascstarng ASC ");
		return stringBuilder.toString();
	}
	/**
	 *
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
	 *
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
