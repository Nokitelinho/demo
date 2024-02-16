/*
 * CN66DetailsFilterQuery.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2518
 * 
 */
public class CN66DetailsFilterQuery extends NativeQuery {

	private String baseQuery;

	private AirlineCN66DetailsFilterVO airlineCn66DetailsFilterVo;

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	/**
	 * @param baseQuery
	 * @param airlineCn66DetailsFilterVo
	 * @throws SystemException
	 */
	public CN66DetailsFilterQuery(String baseQuery,
			AirlineCN66DetailsFilterVO airlineCn66DetailsFilterVo)
			throws SystemException {
		this.baseQuery = baseQuery;
		this.airlineCn66DetailsFilterVo = airlineCn66DetailsFilterVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery#getNativeQuery()
	 */
	@Override
	/**
	 * @return String
	 */
	public String getNativeQuery() {
		int index = 0;
		StringBuilder str = new StringBuilder(baseQuery);
		this.setParameter(++index, airlineCn66DetailsFilterVo.getCompanyCode());
		this.setParameter(++index, airlineCn66DetailsFilterVo
				.getInvoiceRefNumber());
		this.setParameter(++index, airlineCn66DetailsFilterVo
				.getClearancePeriod().trim());
		this.setParameter(++index, airlineCn66DetailsFilterVo.getAirlineId());
		if (airlineCn66DetailsFilterVo.getCategory() != null
				&& airlineCn66DetailsFilterVo.getCategory().trim().length() > 0) {
			str.append(" AND CN66.MALCTGCOD = ?");
			this.setParameter(++index, airlineCn66DetailsFilterVo.getCategory()
					.trim());
		}
		if (airlineCn66DetailsFilterVo.getCarriageFrom() != null
				&& airlineCn66DetailsFilterVo.getCarriageFrom().trim().length() > 0) {
			str.append(" AND CN66.CARFRM = ?");
			this.setParameter(++index, airlineCn66DetailsFilterVo
					.getCarriageFrom().trim());
		}
		if (airlineCn66DetailsFilterVo.getCarriageTo() != null
				&& airlineCn66DetailsFilterVo.getCarriageTo().trim().length() > 0) {
			str.append((" AND CN66.CARTOO = ?"));
			this.setParameter(++index, airlineCn66DetailsFilterVo
					.getCarriageTo().trim());
		}
		if (airlineCn66DetailsFilterVo.getDespatchStatus() != null
				&& airlineCn66DetailsFilterVo.getDespatchStatus().trim()
						.length() > 0) {
			str.append(" AND CN66.MALSTA = ?");
			this.setParameter(++index, airlineCn66DetailsFilterVo
					.getDespatchStatus().trim());
		}
		if (airlineCn66DetailsFilterVo.getInterlineBillingType() != null
				&& airlineCn66DetailsFilterVo.getInterlineBillingType().trim()
						.length() > 0) {
			str.append(" AND CN66.INTBLGTYP = ?");
			this.setParameter(++index, airlineCn66DetailsFilterVo
					.getInterlineBillingType().trim());
		}
		//Added by A-7929 as part of ICRD-265471
		if (airlineCn66DetailsFilterVo.getBillingType() != null
				&& airlineCn66DetailsFilterVo.getBillingType().trim()
						.length() > 0) {
			str.append(" AND SMY.INTINVTYP = ?");
			this.setParameter(++index, airlineCn66DetailsFilterVo
					.getBillingType().trim());
		}
		log.log(Log.INFO, "<---------- QUERY ---------->", str.toString());
		return str.toString();
	}

}
