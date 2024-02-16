package com.ibsplc.icargo.persistence.dao.addons.mail.operations;


import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

public class MailBookingFilterQuery extends
		PageableNativeQuery<MailBookingDetailVO> {

	private MailBookingFilterVO mailBookingFilterVO;
	private String baseQuery;	
	 private static final String DATE ="yyyyMMdd";
	public MailBookingFilterQuery(MailBookingMapper mapper,
            MailBookingFilterVO mailBookingFilterVO, String baseQuery)
            throws SystemException {
     super(mailBookingFilterVO.getPageSize(),mailBookingFilterVO.getTotalRecordsCount(),mapper);
     this.mailBookingFilterVO = mailBookingFilterVO;
     this.baseQuery = baseQuery;
	}


	@Override
	public String getNativeQuery() {
		
		int index = 0;
		setParameter(++index, mailBookingFilterVO.getCompanyCode());
		StringBuilder queryString = (new StringBuilder()).append(baseQuery);

		if(!StringUtils.isBlank(mailBookingFilterVO.getMailScc())){
			 validateMailScc(queryString);     
		}else {  
			validateMailSccFromSyspar(queryString);
		}
		
		if(!StringUtils.isBlank(mailBookingFilterVO.getMasterDocumentNumber())){
			queryString.append("AND BKGMST.MSTDOCNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getMasterDocumentNumber());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getShipmentPrefix())){
			queryString.append("AND BKGMST.SHPPFX = ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentPrefix());
		}
		if (mailBookingFilterVO.getBookingFrom() != null && (mailBookingFilterVO.getBookingTo() != null)) {
			queryString.append(" AND TO_NUMBER(TO_CHAR(BKGMST.BKGDAT,'YYYYMMDD'))  BETWEEN ? AND ? ");
			setParameter(++index, Integer.parseInt(mailBookingFilterVO.getBookingFrom().toStringFormat(DATE).substring(0, 8)) );        
	       setParameter(++index, Integer.parseInt(mailBookingFilterVO.getBookingTo().toStringFormat(DATE).substring(0, 8)));       
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getProduct())){
			queryString.append("AND BKGMST.PRDCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getProduct());
		}

		if(!StringUtils.isBlank(mailBookingFilterVO.getOrginOfBooking())){
			queryString.append("AND BKGMST.ORGCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getOrginOfBooking());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getStationOfBooking())){
			queryString.append("AND BKGMST.STNCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getStationOfBooking());
		}
		if(mailBookingFilterVO.getShipmentDate()!=null){
			queryString.append("AND BKGMST.SHPDAT = ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentDate());
		}
		
		if(!StringUtils.isBlank(mailBookingFilterVO.getBookingFlightTo())){
			queryString.append("AND BKGFLTDTL.FLTDAT <= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightTo());
		}
		
		if(!StringUtils.isBlank(mailBookingFilterVO.getAgentCode())){
			queryString.append("AND BKGMST.AGTCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getAgentCode());
		}

		if(!StringUtils.isBlank(mailBookingFilterVO.getCustomerCode())){
			queryString.append("AND BKGMST.RATCUSCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getCustomerCode());
		}
		StringBuilder finalQueryString= validationsForAppendingQuery(queryString,index);
		finalQueryString.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		return finalQueryString.toString();
	}

	private StringBuilder validateMailScc(StringBuilder queryString) {
		if (mailBookingFilterVO.getMailScc().contains(",")) {
			String[] sccs = mailBookingFilterVO.getMailScc().split(",");
			int count = 0;
			if (sccs != null){
				count = sccs.length;
			}	

			queryString.append("AND ( ");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < count; i++) {
				sb.append("BKGMST.SCCCOD like ");
				sb.append("'%");
				sb.append(sccs[i]);
				sb.append("%'");
				if (count > i + 1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
			queryString.append(sb.toString());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("AND BKGMST.SCCCOD like '%");
			sb.append(mailBookingFilterVO.getMailScc());
			sb.append("%'");
			queryString.append(sb.toString());
		}
		return queryString;
	}

	private StringBuilder validateMailSccFromSyspar(StringBuilder queryString) {
		if (mailBookingFilterVO.getMailSccFromSyspar().contains(",")){
			
			String[] sccs = mailBookingFilterVO.getMailSccFromSyspar().split(","); 
			int count=0;
			if (sccs!=null ){
				count=sccs.length;  
			}
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
		return queryString;
	}
	
	private StringBuilder validationsForAppendingQuery(StringBuilder queryString, int index) {
		if(!StringUtils.isBlank(mailBookingFilterVO.getBookingUserId())){
			queryString.append("AND BKGMST.LSTUPDUSR = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingUserId());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getBookingStatus())){
			queryString.append("AND SHPMST.SHPSTA = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingStatus());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getBookingFlightNumber())){
			queryString.append("AND BKGFLTDTL.FLTNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightNumber());
		}

		if(!StringUtils.isBlank(mailBookingFilterVO.getBookingFlightFrom())){
			queryString.append("AND BKGFLTDTL.FLTDAT >= ? ");
			setParameter(++index, mailBookingFilterVO.getBookingFlightFrom());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getShipmentStatus())){
			queryString.append("AND SHPMST.SHPSTA >= ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentStatus());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getDestinationOfBooking())){
			queryString.append("AND BKGMST.DSTCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getDestinationOfBooking());
		}
		return queryString;
	}
	
}
