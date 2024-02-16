/*
 * AcceptanceDetailsForCarrierFilterQuery.java Created on Jan 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-1936
 * This class is used to append the Query Dynamically 
 */

public  class  AcceptanceDetailsForCarrierFilterQuery extends NativeQuery {

	private String uldQuery;
	private String containerQuery;
	private String emptyContainerQuery;
	
	private OperationalFlightVO  operationalFlightVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * 
	 * @param uldQuery
	 * @param containerQuery
	 * @param emptyContainerQuery
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public AcceptanceDetailsForCarrierFilterQuery(String uldQuery,String containerQuery,
			String emptyContainerQuery,
			OperationalFlightVO  operationalFlightVO) throws SystemException {
		super();
		this.uldQuery = uldQuery;
		this.containerQuery=containerQuery;
		this.emptyContainerQuery=emptyContainerQuery;
		this.operationalFlightVO = operationalFlightVO;
	}

	/**
	 * 
	 */
	@Override
	public String getNativeQuery() {
		log.entering("MailAcceptanceForCarrierFilterQuery", "getNativeQuery");
		  int index=0;
		  StringBuilder queryBuilder = new StringBuilder(uldQuery);
		     this.setParameter(++index, operationalFlightVO.getCompanyCode());
		     this.setParameter(++index, operationalFlightVO.getCarrierId());
			 // Assigned airport
		     this.setParameter(++index, operationalFlightVO.getPol());
			 if(operationalFlightVO.getPou()!=null && operationalFlightVO.getPou().trim().length()>0){
				 queryBuilder.append(" AND MST.DSTCOD = ? ");
				 this.setParameter(++index, operationalFlightVO.getPou());
			 } else {
				 queryBuilder.append(" AND MST.DSTCOD IS NULL ");
			 }
		     queryBuilder.append(" ) ");
		     queryBuilder.append(" UNION ALL ");
		     
		     
		     queryBuilder.append(containerQuery);
		     this.setParameter(++index, operationalFlightVO.getCompanyCode());
		     this.setParameter(++index, operationalFlightVO.getCarrierId());
			 // Assigned airport
		     this.setParameter(++index, operationalFlightVO.getPol());
			 if(operationalFlightVO.getPou()!=null && operationalFlightVO.getPou().trim().length()>0){
				 queryBuilder.append(" AND MST.DSTCOD = ? ");
				 this.setParameter(++index, operationalFlightVO.getPou());
			 } else {
				 queryBuilder.append(" AND MST.DSTCOD IS NULL ");
			 }
		     queryBuilder.append(" ) ");
		     queryBuilder.append(" UNION ALL ");
		     
		     queryBuilder.append(emptyContainerQuery);
		     this.setParameter(++index, operationalFlightVO.getCompanyCode());
		     this.setParameter(++index, operationalFlightVO.getCarrierId());
			 // Assigned airport
		     this.setParameter(++index, operationalFlightVO.getPol());
			 if(operationalFlightVO.getPou()!=null && operationalFlightVO.getPou().trim().length()>0){
				 queryBuilder.append(" AND MST.DSTCOD = ? ");
				 this.setParameter(++index, operationalFlightVO.getPou());
			 } else {
				 queryBuilder.append(" AND MST.DSTCOD IS NULL ");
			 }
		     queryBuilder.append(" ) ");
		     queryBuilder.append(" ORDER BY CONNUM ,FRMCARCOD,DSN ");
		     
		log.exiting("MailAcceptanceForCarrierFilterQuery", "getNativeQuery");
		 return queryBuilder.toString();
	}
	
}