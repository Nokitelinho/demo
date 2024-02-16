/*
 * CarditEnquiryFilterQuery.java Created on Jan 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Jan 23, 2007			A-1739		Created
 */
public class CarditEnquiryFilterQuery extends NativeQuery {

	private String baseQuery;
	
	private CarditEnquiryFilterVO carditEnquiryFilterVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * @throws SystemException
	 */
	public CarditEnquiryFilterQuery(String baseQuery, 
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
	}

	/**
	 * TODO Purpose
	 * Jan 23, 2007, A-1739
	 * @return
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery#getNativeQuery()
	 */
	@Override
	public String getNativeQuery() {
		log.entering("CarditEnquiryFilterQuery", "getNativeQuery");
		
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		
		String consignDocNum = carditEnquiryFilterVO.getConsignmentDocument();
		LocalDate consignFromDate = carditEnquiryFilterVO.getFromDate();
		LocalDate consignToDate = carditEnquiryFilterVO.getToDate();
		
		String ooe = carditEnquiryFilterVO.getOoe();
		String doe = carditEnquiryFilterVO.getDoe();
		String catCode = carditEnquiryFilterVO.getMailCategoryCode();
		String mailClss = carditEnquiryFilterVO.getMailClass();
		String year = carditEnquiryFilterVO.getYear();
		String dsn = carditEnquiryFilterVO.getDespatchSerialNumber();
		String rsn = carditEnquiryFilterVO.getReceptacleSerialNumber();
				
		int idx = 0;
		setParameter(++idx,carditEnquiryFilterVO.getCompanyCode());
		setParameter(++idx,carditEnquiryFilterVO.getCarrierId());
		setParameter(++idx,carditEnquiryFilterVO.getFlightNumber());
		setParameter(++idx,carditEnquiryFilterVO.getFlightSequenceNumber());
		setParameter(++idx,carditEnquiryFilterVO.getLegSerialNumber());
		
		if(carditEnquiryFilterVO.getResdit()!=null && carditEnquiryFilterVO.getResdit().trim().length()>0){
		  setParameter(++idx,carditEnquiryFilterVO.getResdit());
		}
		if(consignDocNum != null) {
			queryBuilder.append(" AND CDTMST.CSGDOCNUM = ? ");
			setParameter(++idx, consignDocNum);
		}
		
		if(consignFromDate != null) {
			queryBuilder.append(" AND TRUNC(CDTMST.CDTRCVDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++idx, consignFromDate.toSqlDate().toString());			
		}
		
		if(consignToDate != null) {
			queryBuilder.append(" AND TRUNC(CDTMST.CDTRCVDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++idx, consignToDate.toSqlDate().toString());			
		}
		
		if(ooe != null) {
			queryBuilder.append(" AND CDTRCP.ORGEXGOFF = ? ");
			setParameter(++idx, ooe);
		}
		
		if(doe != null) {
			queryBuilder.append(" AND CDTRCP.DSTEXGOFF = ? ");
			setParameter(++idx, doe);
		}
		
		if(catCode != null) {
			queryBuilder.append(" AND CDTRCP.MALCTG = ? ");
			setParameter(++idx, catCode);
		}
		
		if(mailClss != null) {
			if(mailClss.contains(MailConstantsVO.MALCLS_SEP)) {
				queryBuilder.append(" AND SUBSTR(CDTRCP.MALSUBCLS, 0, 1) IN ( ");
				idx = setMultipleMailClass(mailClss, queryBuilder, this, idx);
			} else {
				queryBuilder.append(" AND SUBSTR(CDTRCP.MALSUBCLS, 0, 1) = ? ");
				setParameter(++idx, mailClss);
			}
		}
				
		if(year != null) {
			queryBuilder.append(" AND CDTRCP.DSPYER = ? ");
			setParameter(++idx, Integer.parseInt(year));
		}
		
		if(dsn != null) {
			queryBuilder.append(" AND CDTRCP.DSPSRLNUM = ? ");
			setParameter(++idx, dsn);
		}
		
		if(rsn != null) {
			queryBuilder.append(" AND CDTRCP.RCPSRLNUM = ? ");
			setParameter(++idx, rsn);
		}
		
		if(MailConstantsVO.FLAG_YES.equals(carditEnquiryFilterVO.getNotAccepted())) {
				if(MailConstantsVO.CARDITENQ_MODE_MAL.equals(
					carditEnquiryFilterVO.getSearchMode()) ||
						MailConstantsVO.CARDITENQ_MODE_DESP.equals(
							carditEnquiryFilterVO.getSearchMode())) {
					queryBuilder.append(" AND NOT EXISTS ( SELECT 1 FROM MTKMALMST MST ").
						append(" WHERE CMPCOD = CDTRCP.CMPCOD AND MALIDR = CDTRCP.RCPIDR)");
				} else if(MailConstantsVO.CARDITENQ_MODE_DOC.equals(
					carditEnquiryFilterVO.getSearchMode())) {
					queryBuilder.append(" AND EXISTS ( SELECT 1 FROM MTKCDTRCP RCP ").
								append( " WHERE RCP.CMPCOD = CDTMST.CMPCOD AND RCP.CDTKEY = CDTMST.CDTKEY ").
								append(" AND RCP.RCPIDR NOT IN ( SELECT MALIDR FROM MTKMALMST)) ");	
				}
				
		}
		if(MailConstantsVO.CARDITENQ_MODE_MAL.equals(
				carditEnquiryFilterVO.getSearchMode()) ){

			if(carditEnquiryFilterVO.getResdit()!=null && 
					carditEnquiryFilterVO.getResdit().trim().length()>0){
			 
				queryBuilder.append(" ORDER BY CDTRCP.RCPIDR, RDT2.MALEVTSEQ ");
			}
			
		}
		log.exiting("CarditEnquiryFilterQuery", "getNativeQuery");
		return queryBuilder.toString();
	}
	
	/**
	 * Splits and set the classes as each qry param
	 * Feb 7, 2007, A-1739
	 * @param mailClass
	 * @param stringBuilder
	 * @param query
	 * @param index
	 * @return 
	 */
	private int setMultipleMailClass(String mailClass, 
			StringBuilder stringBuilder, NativeQuery query, int index) {
		String[] mailClasses = mailClass.split(MailConstantsVO.MALCLS_SEP);		
		for(String mClass : mailClasses) {
			stringBuilder.append(" ?,");
			setParameter(++index, mClass);
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		stringBuilder.append( " ) ");		
		return index;
	}
}