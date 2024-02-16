/*
 * RateLineErrorFilterQuery.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * @author A-1556
 * @author A-2270
 *
 */
public class RateLineErrorFilterQuery extends NativeQuery {

	private String baseQuery;

	public static final String ACTIVE = "A";

	//private RateLineVO rateLineVO;
	private Collection<RateLineVO> rateLineVos = new ArrayList<RateLineVO>();

	/**
	 *
	 * @param baseQuery
	 * @param rateAuditFilterVO
	 * @throws SystemException
	 *
	 */
	public RateLineErrorFilterQuery(String baseQuery,
			Collection<RateLineVO> rateLineVos) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.rateLineVos = rateLineVos;
	}

	/**
	 *
	 * @return String
	 *
	 */
	public String getNativeQuery() {
		StringBuilder stringBuilder = new StringBuilder(baseQuery);
		int index = 0;
		this.setParameter(++index,rateLineVos.iterator().next().getCompanyCode());
		this.setParameter(++index,RateLineVO.ACTIVE);
		String buffer = "";
		for(RateLineVO rateLineVo : rateLineVos ){
			if(("").equals(buffer)) {
				stringBuilder.append(" AND ((( " );//Changed as part of ICRD-117884
				buffer = "N";
			}else {
				stringBuilder.append(" OR ( ");
			}
			if(rateLineVo.getOrigin()!=null && rateLineVo.getOrigin().length()>0){
			stringBuilder.append(" LIN.ORGCOD = ?  ");
			this.setParameter(++index,rateLineVo.getOrigin());
			}
			//Added as part of ICRD-117884 starts
			if(rateLineVo.getDestination()!=null && rateLineVo.getDestination().length()>0){
				stringBuilder.append("OR LIN.ORGCOD  = ? ) ");	
				this.setParameter(++index,rateLineVo.getDestination());
			 }
			//Added as part of ICRD-117884 ends
			if(rateLineVo.getDestination()!=null && rateLineVo.getDestination().length()>0){
				stringBuilder.append(" AND ( LIN.DSTCOD = ?  ");		//Changed as part of ICRD-117884
				this.setParameter(++index,rateLineVo.getDestination());
			 }
			//Added as part of ICRD-117884 starts
			if(rateLineVo.getOrigin()!=null && rateLineVo.getOrigin().length()>0){
				stringBuilder.append(" OR  LIN.DSTCOD  = ? ) ");
				this.setParameter(++index,rateLineVo.getOrigin());
				}
			//Added as part of ICRD-117884 ends
//			if(rateLineVo.getRateCardID()!=null && rateLineVo.getRateCardID().length()>0){
//				stringBuilder.append(" AND LIN.RATCRDCOD = ?  ");
//				this.setParameter(++index,rateLineVo.getRateCardID());
//			 }
			if(rateLineVo.getValidityStartDate()!=null){
				stringBuilder.append(" AND ( ? <=  LIN.VLDENDDAT AND " );
				this.setParameter(++index,rateLineVo.getValidityStartDate());
			 }
			if(rateLineVo.getValidityEndDate()!=null){
				stringBuilder.append(" ? >=  LIN.VLDSTRDAT  )" );
				this.setParameter(++index,rateLineVo.getValidityEndDate());
			 }

		}
		stringBuilder.append(" ))"); //Modified by a-7871 for ICRD-223130
		return stringBuilder.toString();
	}
}






