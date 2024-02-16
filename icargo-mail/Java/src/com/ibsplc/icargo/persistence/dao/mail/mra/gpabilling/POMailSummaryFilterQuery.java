/**
 * POMailSummaryFilterQuery.java Created on May 17,2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class POMailSummaryFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA_GPABilling");

	private String baseQuery;
	private BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO;
	/**
	 * 
	 * @param baseQuery
	 * @param billingSummaryDetailsFilterVO
	 * @throws SystemException
	 */
	public POMailSummaryFilterQuery(String baseQuery,BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.billingSummaryDetailsFilterVO = billingSummaryDetailsFilterVO;

	}
	/**
	 * @return String
	 */
	public String getNativeQuery() {
		log.entering("POMailSummaryFilterQuery", "getNativeQuery");
		int index=0;
		StringBuilder qry = new StringBuilder(baseQuery);
		this.setParameter(++index, billingSummaryDetailsFilterVO.getCompanyCode());
		this.setParameter(++index, billingSummaryDetailsFilterVO.getFromDate());
		this.setParameter(++index, billingSummaryDetailsFilterVO.getToDate());		
		if(billingSummaryDetailsFilterVO.getStationCode()!=null){

			qry.append(" WHERE CTYMST.CNTCOD= ? OR CTYMST.CTYCOD= ?");

			this.setParameter(++index, billingSummaryDetailsFilterVO.getStationCode());
			this.setParameter(++index, billingSummaryDetailsFilterVO.getStationCode());
		}
		qry.append(")GROUP BY C66DTL.SUBCLSGRP," +
				"C66DTL.MALCTGCOD, C66DTL.ORGCOD, C66DTL.DSTCOD,BLGDTL.BLGRAT, " +
				" C66DTL.CTRCURCOD,cty.ctypirdis,BLGDTL.APLRAT,  STLDTL.CHQNUM, STLDTL.CHQAMT, INVDTL.STLAMT, STLDTL.CHQDAT,STLDTL.RMK," +
		" STL.STLDAT, STL.STLCUR ,FLTNUMTAB.FLTNUMS ");
		//Added as part of icrd-19592 by a-4810
		qry.append(" ORDER BY  C66DTL.ORGCOD,C66DTL.DSTCOD");
		return qry.toString();
	}

}
