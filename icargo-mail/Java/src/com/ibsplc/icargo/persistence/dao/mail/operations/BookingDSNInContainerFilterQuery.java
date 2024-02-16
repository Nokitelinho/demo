/*
 * BookingDSNInContainerFilterQuery.java Created on OCT 05, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 * 
 */
public class BookingDSNInContainerFilterQuery  extends NativeQuery {

	/**
	 * The Local logger Instance for this Class.
	 */
	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/**
	 * The base Query
	 */
	private String baseQuery;
	/**
	 * The mailbagPage
	 */
	private Collection<ContainerVO> containerVOs;
	
	private static final String HYPHEN = "-";

	public BookingDSNInContainerFilterQuery(Collection<ContainerVO> containerVOs, String baseQuery)
			throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.containerVOs = containerVOs;
	}

	/**
	 * This method is used to append the Query Dynamically 
	 * @return
	 */
	public String getNativeQuery() {
		log.entering("Inside the BookingDSNInContainerFilterQuery","getNativeQuery()");

		StringBuilder builder = new StringBuilder(baseQuery);
		int index = 0;
		String key = null;
		builder.append(" WHERE ")
		.append(" MST.CMPCOD || '-' || MST.CONNUM || '-' || ")
		.append(" MST.FLTCARIDR || '-' || MST.FLTNUM || '-' || " )
		.append(" MST.FLTSEQNUM || '-' || MST.SEGSERNUM IN ( ");
		boolean isFirstEntry = true;
		for(ContainerVO containerVO : containerVOs){
			key = new StringBuilder()
			.append(containerVO.getCompanyCode()).append(HYPHEN)
			.append(containerVO.getContainerNumber()).append(HYPHEN)
			.append(containerVO.getCarrierId()).append(HYPHEN)
			.append(containerVO.getFlightNumber()).append(HYPHEN)
			.append(containerVO.getFlightSequenceNumber()).append(HYPHEN)
			.append(containerVO.getSegmentSerialNumber()).toString();
			if(isFirstEntry){
				builder.append(" ? ");
				isFirstEntry = false;
			} else{
				builder.append(", ? ");
			}
			
			this.setParameter(++index, key);
		}
		builder.append(" ) ");
		return builder.toString();
	}
}
