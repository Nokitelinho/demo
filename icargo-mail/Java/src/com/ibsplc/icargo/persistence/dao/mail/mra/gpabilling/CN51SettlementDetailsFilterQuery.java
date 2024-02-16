/*
 * CN51SettlementDetailsFilterQuery.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class CN51SettlementDetailsFilterQuery extends NativeQuery  {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String CLASS_NAME = "CN51SummaryFilterQuery";
	private String baseQuery;
	private InvoiceSettlementFilterVO invoiceSettlementFilterVO;
	private LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
	/**
	 * 
	 * @param baseQuery
	 * @param invoiceSettlementFilterVO
	 * @throws SystemException
	 */
	public CN51SettlementDetailsFilterQuery(String baseQuery,InvoiceSettlementFilterVO invoiceSettlementFilterVO)throws SystemException{
		this.baseQuery=baseQuery;
		this.invoiceSettlementFilterVO=invoiceSettlementFilterVO;
	}
	/**
	 * @return 
	 */
	@Override
	public String getNativeQuery() {
		log.entering(CLASS_NAME,"getNativeQuery");
		int index=0;
		StringBuilder querySb=new StringBuilder(baseQuery);
		
		
		if(invoiceSettlementFilterVO.getCompanyCode()!=null&&invoiceSettlementFilterVO.getCompanyCode().trim().length()>0){
		    this.setParameter(++index,invoiceSettlementFilterVO.getCompanyCode());
		}else{
			this.setParameter(++index,logonAttributes.getCompanyCode());
		}
		if(invoiceSettlementFilterVO.getGpaCode()!=null && 
				invoiceSettlementFilterVO.getGpaCode().trim().length()>0){
			querySb.append(" AND POA.POACOD= ? ");
			this.setParameter(++index,invoiceSettlementFilterVO.getGpaCode());
		}
		if(invoiceSettlementFilterVO.getInvoiceNumber()!=null && 
				invoiceSettlementFilterVO.getInvoiceNumber().trim().length()>0){
			querySb.append(" AND C51.INVNUM= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getInvoiceNumber());
			
		}
		if(invoiceSettlementFilterVO.getSettlementStatus()!=null && invoiceSettlementFilterVO.getSettlementStatus().trim().length()>0){
			if("F".equals(invoiceSettlementFilterVO.getSettlementStatus())){
				querySb.append(" AND C51.STLSTA= 'P' ");
			}
			else{
			querySb.append(" AND C51.STLSTA= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getSettlementStatus());
			}
		}
		if(invoiceSettlementFilterVO.getFromDate()!=null ){
			querySb.append(" AND C51.BLGPRDFRM>= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getFromDate());
		}
		if(invoiceSettlementFilterVO.getToDate()!=null){
			querySb.append(" AND C51.BLGPRDTOO<= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getToDate());
		}
		if(invoiceSettlementFilterVO.getChequeNumber()!=null){
			querySb.append(" AND DTL.CHQNUM= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getChequeNumber());
		}
		//Added for appending settlement refernce no when print is clicked
		if(invoiceSettlementFilterVO.getSettlementReferenceNumber()!=null){
			querySb.append(" AND DTL.STLREFNUM= ?");
			this.setParameter(++index,invoiceSettlementFilterVO.getSettlementReferenceNumber());
		}
		querySb.append(" ORDER BY STLREFNUM,IDNO,INVNUM ");
		log.log(Log.INFO, "\n\nquery in CN51SettlementDetailsFilterQuery-->",
				querySb.toString());
		log.exiting(CLASS_NAME,"getNativeQuery");
		return querySb.toString();
	}

	}
