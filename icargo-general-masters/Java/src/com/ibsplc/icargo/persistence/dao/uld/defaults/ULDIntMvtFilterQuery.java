/*
 * ULDIntMvtFilterQuery.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2412
 *
 */
public class ULDIntMvtFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	private ULDIntMvtHistoryFilterVO uldIntMvtHistoryFilterVO;

	private String query;

	/**
	 * @param uldIntMvtHistoryFilterVO
	 * @param baseQry
	 * @throws SystemException
	 */
	public ULDIntMvtFilterQuery(
			ULDIntMvtHistoryFilterVO uldIntMvtHistoryFilterVO, String baseQry)
			throws SystemException {
		super();

		this.uldIntMvtHistoryFilterVO = uldIntMvtHistoryFilterVO;
		this.query = baseQry;

	}

	
	@Override
	public String getNativeQuery() {
		log.entering("ULDIntMvtFilterQuery", "getNativeQuery");
		StringBuilder stringBuilder = new StringBuilder().append(query);
		int index = 0;
		this.setParameter(++index, uldIntMvtHistoryFilterVO.getCompanyCode()
				.toUpperCase());
		stringBuilder.append(" AND DTL.ULDNUM = ? ");
		
		this.setParameter(++index, uldIntMvtHistoryFilterVO.getUldNumber()
				.toUpperCase());		
		if (uldIntMvtHistoryFilterVO.getFromDate() != null){				
			log.log(Log.FINE, "From Date-->", uldIntMvtHistoryFilterVO.getFromDate());
			LocalDate fromDate = uldIntMvtHistoryFilterVO.getFromDate();			
			stringBuilder.append(" AND ( TRUNC(DTL.MVTDAT) >= ? )");
			this.setParameter(++index, fromDate);
			
		}
		if (uldIntMvtHistoryFilterVO.getToDate() != null){
			log.log(Log.FINE, "To Date-->", uldIntMvtHistoryFilterVO.getToDate());
			LocalDate toDate = uldIntMvtHistoryFilterVO.getToDate();
			stringBuilder.append(" AND ( TRUNC(DTL.MVTDAT) <= ? ) ");
			this.setParameter(++index, toDate);
		}
		
		if(ULDAirportLocationVO.FACLITY_AGENTLOC.equals(uldIntMvtHistoryFilterVO.getReasonForMvt())){
			stringBuilder.append("AND DTL.CNT = ? ");
			this.setParameter(++index, ULDAirportLocationVO.FACLITY_AGENTLOC);
			
		}else if(ULDAirportLocationVO.FACLITY_REPAIRDOC.equals(uldIntMvtHistoryFilterVO.getReasonForMvt())){
			stringBuilder.append("AND DTL.CNT = ? ");
			this.setParameter(++index, ULDAirportLocationVO.FACLITY_REPAIRDOC);
		}
		stringBuilder.append("ORDER BY DTL.MVTDAT DESC");
		
		log.log(Log.FINE, "stringBuilder-->", stringBuilder.toString());
		log.exiting("ULDMovementFilterQuery", "getNativeQuery");
		return stringBuilder.toString();
	}

}
