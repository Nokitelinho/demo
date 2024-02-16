/*
 * CheckRoutingsForMailsFilterQuery.java Created on AUG 14,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class CheckRoutingsForMailsFilterQuery  extends NativeQuery {
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private String baseQuery;
	private OperationalFlightVO operationalFlightVO;
	private DSNVO dSNVO;
	private String type;
	private static final String MAILBAG="MAILBAG";
	private static final String DESPATCH_IN_ULD="DESPATCH_IN_ULD";
	private static final String DESPATCH_IN_AXP_BULK="DESPATCH_IN_AXP_BULK";
	private static final String DESPATCH_IN_MFT_BULK="DESPATCH_IN_MFT_BULK";
	private int index = 0;
	/**
	 * CheckRoutingsForMailsFilterQuery
	 * @throws SystemException
	 * @param baseQuery
	 * @param operationalFlightVO
	 * @param dSNVO
	 * @param type
	 */
	public CheckRoutingsForMailsFilterQuery(String baseQuery,OperationalFlightVO operationalFlightVO,DSNVO dSNVO,String type) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.operationalFlightVO = operationalFlightVO;
		this.dSNVO = dSNVO;
		this.type = type;
		
	}
	
	@Override
	public String getNativeQuery() {
		log.entering("CheckRoutingsForMailsFilterQuery", "getNativeQuery");
		StringBuilder builder = new StringBuilder(baseQuery);
		builder.append(" WHERE (SELECT COUNT (*) FROM ");		 
		builder.append(fillFilters(type));
		builder.append(" ) = (SELECT COUNT(*) FROM "); 	 
		builder.append(fillFilters(type));	
		builder.append(" AND EXISTS ( SELECT 1 FROM MALCSGDTL MAL WHERE")
				
				.append(" MST.CMPCOD = MAL.CMPCOD ")
				.append(" AND MST.MALSEQNUM = MAL.MALSEQNUM ")
				.append(" AND MST.CSGDOCNUM = MAL.CSGDOCNUM ")
				.append(" AND MST.CSGSEQNUM = MAL.CSGSEQNUM ")
				.append(" AND MST.POACOD    = MAL.POACOD ");
		
		builder.append(" AND EXISTS ( SELECT 1 ")
				.append(" FROM MALCSGRTG RTG ")
				.append(" WHERE MAL.CMPCOD = RTG.CMPCOD ")
				.append(" AND MAL.CSGDOCNUM = RTG.CSGDOCNUM ")
				//.append(" AND MAL.CSGSEQNUM = RTG.CSGSEQNUM ")
				.append(" AND MAL.POACOD = RTG.POACOD))) ");

		log.exiting("CheckRoutingsForMailsFilterQuery", "getNativeQuery");
		return builder.toString();
	}
	
	/**
	 * selectType
	 * @param type
	 * @return
	 */
	private String fillFilters(String type){
		String table=null;
		String companyCode = operationalFlightVO.getCompanyCode();
		int carrierId = operationalFlightVO.getCarrierId();
		String flightNumber = operationalFlightVO.getFlightNumber();
		long flightSequentialNumber = operationalFlightVO.getFlightSequenceNumber();
		String containerNumber = dSNVO.getContainerNumber();
		String dsn = dSNVO.getDsn();
		String originExchangeOffice = dSNVO.getOriginExchangeOffice();
		String destinationExchangeOffice = dSNVO.getDestinationExchangeOffice();
		int year = dSNVO.getYear();
		String mailCategoryCode = dSNVO.getMailCategoryCode();
		String mailSubclass = dSNVO.getMailSubclass();		
		
		if(MAILBAG.equals(type)){
			table = " MALULDSEGDTL SEG,MALMST MST ";			
		}
		StringBuilder filterBuilder=new StringBuilder().append(table);
		
		filterBuilder.append(" WHERE MST.CMPCOD = SEG.CMPCOD AND " +
				" MST.MALSEQNUM=SEG.MALSEQNUM " +
				" AND SEG.CMPCOD = ? ")
				 .append(" AND SEG.FLTCARIDR = ? ")
				 .append(" AND SEG.FLTNUM = ? ")
				 .append(" AND SEG.FLTSEQNUM = ? ");
		/*
		 * Commented as a part of AirNZ Bug
		 */
//		if(DESPATCH_IN_AXP_BULK.equals(type)){
//			filterBuilder.append(" AND SEG.CONNUM = ? ");
//		}else{
			filterBuilder.append(" AND SEG.ULDNUM = ? ");
//		}		
		filterBuilder.append(" AND MST.DSN = ? ")		
					 .append(" AND MST.ORGEXGOFC = ? ")
					 .append(" AND MST.DSTEXGOFC = ? ")
					 .append(" AND MST.YER = ? ")
					 .append(" AND MST.MALCTG= ? ")
					 .append(" AND MST.MALSUBCLS = ? ");
		

		this.setParameter(++index, companyCode);
		this.setParameter(++index, carrierId);
		this.setParameter(++index, flightNumber);
		this.setParameter(++index, flightSequentialNumber);
		this.setParameter(++index, containerNumber);
		this.setParameter(++index, dsn);
		this.setParameter(++index, originExchangeOffice);
		this.setParameter(++index, destinationExchangeOffice);
		this.setParameter(++index, year);
		this.setParameter(++index, mailCategoryCode);
		this.setParameter(++index, mailSubclass);
		
		return filterBuilder.toString();
	}

}
