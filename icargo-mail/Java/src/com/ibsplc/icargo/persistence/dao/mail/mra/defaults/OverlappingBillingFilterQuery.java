/*
 * OverlappingBillingFilterQuery.java Created on Mar 06, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1872 Class for constructing the query for finding all the billing
 *         lines
 */
/*
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Mar 6, 2007 A-1872 Initial draft
 * 
 */
public class OverlappingBillingFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private String baseQuery;

	private BillingLineVO billingLineVO;

	private String billingLineStatus;

	/**
	 * 
	 * @param baseQuery
	 * @param billingLineVO
	 * @param billingLineStatus
	 * @throws SystemException
	 */
	public OverlappingBillingFilterQuery(String baseQuery,
			BillingLineVO billingLineVO, String billingLineStatus)
			throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.billingLineVO = billingLineVO;
		this.billingLineStatus = billingLineStatus;
	}

	/**
	 * Method constructs query for getting all the billing lines with the
	 * provided billinLineStatus
	 * 
	 * @return String
	 * 
	 */
	public String getNativeQuery() {
		StringBuilder queryStringBuilder = new StringBuilder(baseQuery);
		log.log(Log.INFO, "<:queryStringBuilder<START>:>", queryStringBuilder.toString());
		int index = 0;
		int i=0;
		// int andCounter = 0;
		int billingParamtersSize = 0;
		this.setParameter(++index, billingLineVO.getCompanyCode());
		log.log(Log.INFO, "<:billingLineStatus:>", billingLineStatus);
		if (billingLineStatus != null && billingLineStatus.trim().length() > 0) {
			queryStringBuilder.append(" AND LIN.BLGLINSTA = ? ");
			this.setParameter(++index, billingLineStatus);
		}
		//Added for ICRD-162403
		if(billingLineVO.getValidityStartDate()!=null&&
				billingLineVO.getValidityEndDate()!=null){
			queryStringBuilder.append(" AND (? BETWEEN TRUNC(LIN.VLDSTRDAT) AND TRUNC(LIN.VLDENDDAT)");
			queryStringBuilder.append(" OR ? < TRUNC(LIN.VLDENDDAT) OR ? BETWEEN TRUNC(LIN.VLDSTRDAT) AND TRUNC(LIN.VLDENDDAT))");
			this.setParameter(++index, billingLineVO.getValidityStartDate());
			this.setParameter(++index, billingLineVO.getValidityStartDate());
			this.setParameter(++index, billingLineVO.getValidityEndDate());
			
		}
		if(billingLineVO.getRevenueExpenditureFlag()!=null)
				{
			queryStringBuilder.append(" AND LIN.REVEXPFLG = ? ");
			this.setParameter(++index, billingLineVO.getRevenueExpenditureFlag());
		
		}
		//Added for ICRD-162403
		//commented for BUG MRA5 by indu starts
		/*if (billingLineVO.getPoaCode() != null
				&& !"".equals(billingLineVO.getPoaCode())) {
			queryStringBuilder.append(" AND LIN.BILPTYPOACOD = ?");
			this.setParameter(++index, billingLineVO.getPoaCode());
		} else*/
		//commented for BUG MRA5 by indu ends
//		commented for MRA194 by Muralee
		/* if (billingLineVO.getAirlineCode() != null
				&& !"".equals(billingLineVO.getAirlineCode())) {
			queryStringBuilder.append(" AND LIN.BILPTYARLCOD = ?");
			this.setParameter(++index, billingLineVO.getAirlineCode());
		}*/
//		commented for BUG INT MRA427 by A-3434 
		/*if (billingLineVO.getRevenueExpenditureFlag() != null
				&& !"".equals(billingLineVO.getRevenueExpenditureFlag())) {
			queryStringBuilder.append(" AND LIN.REVEXPFLG = ?");
			this.setParameter(++index, billingLineVO
					.getRevenueExpenditureFlag());
		}*/
		if (billingLineVO != null
				&& billingLineVO.getBillingLineParameters() != null
				&& billingLineVO.getBillingLineParameters().size() > 0) {
			billingParamtersSize = billingLineVO.getBillingLineParameters()
					.size();
			log
					.log(Log.INFO, "<:BILLINGLINEPARS(SIZE):>",
							billingParamtersSize);
			for (BillingLineParameterVO billingLineParameterVO : billingLineVO
					.getBillingLineParameters()) {
				/*
				 * if ( andCounter < billingParamtersSize ) {
				 * queryStringBuilder.append( " AND"); } else { log.log(
				 * Log.INFO, "<:AND APPENDING STOPPED AS ANDCOUNTER
				 * -:>"+andCounter); }
				 */
				// andCounter++;
				queryStringBuilder.append(" AND").append(" EXISTS")
						.append(" (");
				queryStringBuilder.append(
						" SELECT 'V' FROM MALMRABLGMTXLINPAR PAR WHERE ").append(
						" LIN.CMPCOD = PAR.CMPCOD ").append(" AND").append(
						" LIN.BLGLINSEQNUM = PAR.BLGLINSEQNUM").append(" AND")
						.append(" LIN.BLGMTXCOD = PAR.BLGMTXCOD")
						.append(" AND").append(" PAR.PARCOD = ? ");
				this.setParameter(++index, billingLineParameterVO
						.getParameterCode());
				queryStringBuilder.append(" AND pkg_frmwrk.Fun_String_Check(?, PAR.PARVAL, ',') > 0 ");//Modified for ICRD-162403
				this.setParameter(++index, billingLineParameterVO
						.getParameterValue());
				queryStringBuilder.append(" AND PAR.EXCFLG = ? ");
				this.setParameter(++index, billingLineParameterVO
						.getExcludeFlag());
				queryStringBuilder.append(" ) ");
			}
			
			queryStringBuilder.append(" AND").append(" NOT EXISTS")
			.append(" (");
				queryStringBuilder.append(
						" SELECT 'V' FROM MALMRABLGMTXLINPAR PAR WHERE ").append(
						" LIN.CMPCOD = PAR.CMPCOD ").append(" AND").append(
						" LIN.BLGLINSEQNUM = PAR.BLGLINSEQNUM").append(" AND")
						.append(" LIN.BLGMTXCOD = PAR.BLGMTXCOD")
						.append(" AND").append(" PAR.PARCOD  NOT IN( ");
				for (BillingLineParameterVO billingLineParameterVO : billingLineVO
						.getBillingLineParameters()) {
					i++;
					queryStringBuilder.append("'");
					queryStringBuilder.append( billingLineParameterVO.getParameterCode());
					queryStringBuilder.append("'");
					if(i!= billingParamtersSize){
					queryStringBuilder.append(" , ");
					}
				}
				
				queryStringBuilder.append(" )");
				queryStringBuilder.append(" AND PAR.PARVAL IS NOT  null ");
				
				queryStringBuilder.append(" ) ");
		}
		return queryStringBuilder.toString();
	}
}
