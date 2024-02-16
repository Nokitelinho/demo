/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookingFilterQuery.java
 *
 *	Created by	:	a-7779
 *	Created on	:	24-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations;


import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

/**
 * Java file : com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.
 * MailBookingFilterQuery.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-7779 :
 * 24-Aug-2017 : Draft
 */
public class MailBookingFilterQuery extends
		PageableNativeQuery<MailBookingDetailVO> {

	private MailBookingFilterVO mailBookingFilterVO;
	private String baseQuery;
	
	/*public MailBookingFilterQuery(int pageSize, int recordCount,
			Mapper<MailBookingDetailVO> mapper, String finalQuery,
			MailBookingFilterVO mailBookingFlightFilterVO)
			throws SystemException {
		super(pageSize, recordCount, mapper);
		baseQuery = finalQuery;
		this.mailBookingFilterVO = mailBookingFlightFilterVO;
	}
	
	public MailBookingFilterQuery(int pageSize, int rowCount, String query,
			MailBookingMapper mailBookingMapper,
			MailBookingFilterVO mailBookingFilterVO) throws SystemException {
		super(pageSize, rowCount, mailBookingMapper);
		baseQuery = query;
		this.mailBookingFilterVO = mailBookingFilterVO;
	}*/
	
	public MailBookingFilterQuery(MailBookingMapper mapper,
            MailBookingFilterVO mailBookingFilterVO, String baseQuery)
            throws SystemException {
     super(mailBookingFilterVO.getPageSize(),mapper);
     this.mailBookingFilterVO = mailBookingFilterVO;
     this.baseQuery = baseQuery;
	}


	public String getNativeQuery() {
		
		int index = 0;
		setParameter(++index, mailBookingFilterVO.getCompanyCode());
		StringBuilder queryString = (new StringBuilder()).append(baseQuery);
		if (mailBookingFilterVO.getMailScc() != null
				&& mailBookingFilterVO.getMailScc().trim().length() > 0) {
			if (mailBookingFilterVO.getMailScc().contains(",")){
			
			String[] sccs = mailBookingFilterVO.getMailScc().split(","); 
			int count=0;
			if (sccs!=null )
			count=sccs.length;
			
			queryString.append("AND ( ");              
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<count;i++){
				sb.append("BKGMST.SCCCOD like ");
				sb.append("'%");
				sb.append(sccs[i]); 
				sb.append("%'"); 
				if(count>i+1){
					sb.append(" or ");       
				}     
			}
			sb.append(")");      
			queryString.append(sb.toString());     
			}
			
			else { 
		    	
		    	
		     
			StringBuilder sb = new StringBuilder();
			sb.append("AND BKGMST.SCCCOD like '%");  
			sb.append(mailBookingFilterVO.getMailScc()); 
			sb.append("%'");      
			queryString.append(sb.toString());
			}
			              
			     
			//setParameter(++index, mailBookingFilterVO.getMailScc()); 
				           
		}else {   
			
			if (mailBookingFilterVO.getMailSccFromSyspar().contains(",")){
				
				String[] sccs = mailBookingFilterVO.getMailSccFromSyspar().split(","); 
				int count=0;
				if (sccs!=null )
				count=sccs.length;  
				
				queryString.append("AND ( ");        
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<count;i++){
					sb.append("BKGMST.SCCCOD like ");
					sb.append("'%");
					sb.append(sccs[i]); 
					sb.append("%'"); 
					if(count>i+1){
						sb.append(" or ");       
					}     
				}
				sb.append(")");      
				queryString.append(sb.toString());     
				}
			else { 
		    	
		    	
			     
				StringBuilder sb = new StringBuilder();
				sb.append("AND BKGMST.SCCCOD like '%");  
				sb.append(mailBookingFilterVO.getMailSccFromSyspar()); 
				sb.append("%'");      
				queryString.append(sb.toString());
				}
			//queryString.append("AND (BKGMST.SCCCOD like '%MAL%' or BKGMST.SCCCOD like '%EMS%' or BKGMST.SCCCOD like '%AMT%' )");
		}
		
		if (mailBookingFilterVO.getMasterDocumentNumber() != null
				&& mailBookingFilterVO.getMasterDocumentNumber().trim()
						.length() > 0) {
			queryString.append("AND BKGMST.MSTDOCNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getMasterDocumentNumber());
		}
		if (mailBookingFilterVO.getShipmentPrefix() != null
				&& mailBookingFilterVO.getShipmentPrefix().trim().length() > 0) {
			queryString.append("AND BKGMST.SHPPFX = ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentPrefix());
		}
		if(mailBookingFilterVO.getBookingFrom()!=null){
			queryString.append("AND trunc(BKGMST.BKGDAT) >= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFrom().toDisplayDateOnlyFormat());//modified by A-7371 as part of ICRD-229099
		}
		if(mailBookingFilterVO.getBookingTo()!=null){
			queryString.append("AND trunc(BKGMST.BKGDAT) <= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingTo().toDisplayDateOnlyFormat());
		}
		
		if (mailBookingFilterVO.getProduct() != null
				&& mailBookingFilterVO.getProduct().trim().length() > 0) {
			queryString.append("AND BKGMST.PRDCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getProduct());
		}
		if (mailBookingFilterVO.getOrginOfBooking() != null
				&& mailBookingFilterVO.getOrginOfBooking().trim().length() > 0) {
			queryString.append("AND BKGMST.ORGCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getOrginOfBooking());
		}
		if (mailBookingFilterVO.getDestinationOfBooking() != null
				&& mailBookingFilterVO.getDestinationOfBooking().trim()
						.length() > 0) {
			queryString.append("AND BKGMST.DSTCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getDestinationOfBooking());
		}
		if (mailBookingFilterVO.getStationOfBooking() != null
				&& mailBookingFilterVO.getStationOfBooking().trim().length() > 0) {
			queryString.append("AND BKGMST.STNCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getStationOfBooking());
		}
		if(mailBookingFilterVO.getShipmentDate()!=null){
			queryString.append("AND BKGMST.SHPDAT = ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentDate());
		}
		if (mailBookingFilterVO.getAgentCode() != null
				&& mailBookingFilterVO.getAgentCode().trim().length() > 0) {
			queryString.append("AND BKGMST.AGTCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getAgentCode());
		}
		if (mailBookingFilterVO.getCustomerCode() != null
				&& mailBookingFilterVO.getCustomerCode().trim().length() > 0) {
			queryString.append("AND BKGMST.RATCUSCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getCustomerCode());
		}
		if (mailBookingFilterVO.getBookingUserId() != null
				&& mailBookingFilterVO.getBookingUserId().trim().length() > 0) {
			queryString.append("AND BKGMST.LSTUPDUSR = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingUserId());
		}
		if (mailBookingFilterVO.getBookingStatus() != null
				&& mailBookingFilterVO.getBookingStatus().trim().length() > 0) {
			queryString.append("AND SHPMST.SHPSTA = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingStatus());
		}
		if (mailBookingFilterVO.getBookingCarrierCode() != null
				&& mailBookingFilterVO.getBookingCarrierCode().trim().length() > 0) {
			queryString.append("AND BKGFLTDTL.FLTCARCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingCarrierCode());
		}
		if (mailBookingFilterVO.getBookingFlightNumber() != null
				&& mailBookingFilterVO.getBookingFlightNumber().trim().length() > 0) {
			queryString.append("AND BKGFLTDTL.FLTNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightNumber());
		}
		if(mailBookingFilterVO.getBookingFlightFrom()!=null
				&& mailBookingFilterVO.getBookingFlightFrom().trim().length() > 0){//modified by A-7371 as part of ICRD-229099
			queryString.append("AND BKGFLTDTL.FLTDAT >= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightFrom());
		}
		if(mailBookingFilterVO.getBookingFlightTo()!=null
				&& mailBookingFilterVO.getBookingFlightTo().trim().length() > 0){
			queryString.append("AND BKGFLTDTL.FLTDAT <= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightTo());
		}
		
		
		//queryString.append("AND BKGFLTDTL.APRCOD = ? "); intermediate airport
		queryString.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		return queryString.toString();
	}

}
