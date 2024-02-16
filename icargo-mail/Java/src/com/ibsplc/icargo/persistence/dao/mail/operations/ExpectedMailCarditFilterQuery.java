/*
 * ExpectedMailCarditFilterQuery.java Created on MAR 10,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;



import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227
 *
 */
public class ExpectedMailCarditFilterQuery extends NativeQuery {

	private MailStatusFilterVO mailStatusFilterVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final  String DATE_FORMAT ="yyyyMMdd";
	/**
	 * 
	 */
	public ExpectedMailCarditFilterQuery(MailStatusFilterVO mailStatusFilterVO) throws SystemException {
		super();
		this.mailStatusFilterVO=mailStatusFilterVO;
		
	}

	@Override
	public String getNativeQuery() {
		log.entering("CarditEnquiryFilterQuery", "getNativeQuery");

		StringBuilder queryBuilder = new StringBuilder();
		StringBuilder filterQuery = new StringBuilder();
		LocalDate fromDate=mailStatusFilterVO.getFromDate();
		LocalDate toDate =mailStatusFilterVO.getToDate();
		//LocalDate flightDate=mailStatusFilterVO.getFlightDate();
		String flightNumber=mailStatusFilterVO.getFlightNumber();
		String pol=mailStatusFilterVO.getPol();
		String pou=mailStatusFilterVO.getPou();
		String pacode=mailStatusFilterVO.getPacode();
		Integer flightCarrierIdr=mailStatusFilterVO.getFlightCarrierid();
		Integer carrierIdr=mailStatusFilterVO.getCarrierid();
		//Integer legSerialNumber=mailStatusFilterVO.getLegSerialNumber();
	    //Long flightSequenceNumber=mailStatusFilterVO.getFlightSequenceNumber();
		
	
		int index = 0;
		
	
		
		queryBuilder.append(" SELECT RTG.POL,RTG.POU,RTG.FLTNUM,MALMST.MALIDR,MALMST.WGT,RTG.FLTDAT,")
					.append(" RTG.FLTCARCOD FLTCARCOD, CASE WHEN (CDTMST.CSGDOCNUM) IS NOT NULL ")
					.append(" THEN 'Y' ELSE 'N' END CDTAVL,MALMST.DSN ")
					.append(" FROM MALCSGMST CSGMST ")
					.append(" INNER JOIN MALCSGDTL MST ")
					.append("ON CSGMST.CMPCOD  = MST.CMPCOD AND CSGMST.CSGDOCNUM  = MST.CSGDOCNUM ")
					.append(" AND CSGMST.CSGSEQNUM  = MST.CSGSEQNUM ")
					.append(" AND NOT EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD = MST.CMPCOD ")
					.append(" AND MALMST.MALSEQNUM = MST.MALSEQNUM AND MALMST.POACOD = MST.POACOD AND MALMST.MALSTA <> 'NEW')")
					.append(" INNER JOIN MALMST MALMST ")
					.append(" ON MALMST.CMPCOD = MST.CMPCOD ")
					.append(" AND MALMST.MALSEQNUM = MST.MALSEQNUM AND MALMST.POACOD = MST.POACOD AND MALMST.MALSTA = 'NEW'")
					.append(" INNER JOIN MALCSGRTG RTG ")
					.append(" ON MST.CMPCOD  = RTG.CMPCOD AND MST.CSGDOCNUM = RTG.CSGDOCNUM ")
					.append(" AND MST.CSGSEQNUM = RTG.CSGSEQNUM ");
					
		
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_CARDIT)) {      
			queryBuilder.append(" INNER JOIN MALCDTMST CDTMST ");
		}else{
			queryBuilder.append(" LEFT OUTER JOIN MALCDTMST CDTMST ");
		}
		queryBuilder.append(" ON MST.CMPCOD = CDTMST.CMPCOD AND MST.CSGDOCNUM = CDTMST.CSGDOCNUM ");
		
		filterQuery
			.append(" WHERE MST.CMPCOD   = ? ");
			this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
		if (fromDate != null) {
			filterQuery
			
			.append(" AND to_number(to_char(csgmst.csgdat, 'YYYYMMDD')) >= ? ");
		
			this.setParameter(++index,Integer.parseInt(fromDate.toStringFormat(DATE_FORMAT).substring(0, 8)));
		}
		
		if (toDate != null) {
		filterQuery
				
			.append("AND to_number(to_char(csgmst.csgdat, 'YYYYMMDD')) <= ? ");
			
		this.setParameter(++index,Integer.parseInt(toDate.toStringFormat(DATE_FORMAT).substring(0, 8)));
		}

		if(pacode !=null && pacode.length() > 0){
			filterQuery.append(" AND CSGMST.POACOD = ? ");
			this.setParameter(++index, pacode);
		}

		if(carrierIdr != null && carrierIdr > 0){
			filterQuery.append(" AND RTG.FLTCARIDR = ? ");
			this.setParameter(++index, carrierIdr);
		}
		
		if(pol !=null && pol.length() > 0){
			filterQuery.append(" AND SUBSTR(MALMST.MALIDR,3,3) = ? ");
			this.setParameter(++index, pol);
		}
		
		if(pou !=null && pou.length() > 0){
			filterQuery.append(" AND SUBSTR(MALMST.MALIDR,9,3) = ? ");
			this.setParameter(++index, pou);
		}
		if(flightNumber != null && flightNumber.length() > 0 && flightCarrierIdr != null && flightCarrierIdr > 0){
			filterQuery .append(" AND RTG.FLTCARIDR = ?  ")
						.append(" AND RTG.FLTNUM = ? ");
			this.setParameter(++index, flightCarrierIdr);
			this.setParameter(++index, flightNumber);			
		}
		int caridr = carrierIdr!=null ? Integer.parseInt(carrierIdr.toString()):0;
		if(caridr!=0){
			filterQuery .append(" AND RTG.FLTCARIDR = ?  ");
			this.setParameter(++index, caridr);
		}
		queryBuilder.append(filterQuery);
		
		return queryBuilder.toString();
	}

}
