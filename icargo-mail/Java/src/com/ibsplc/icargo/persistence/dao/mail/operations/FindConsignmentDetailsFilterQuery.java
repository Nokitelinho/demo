/*
 * FindConsignmentDetailsFilterQuery.java Created on Sept 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class is used to append the Query dynamically.
 *
 * @author a-2667
 *
 */
//Modified as part of bug ICRD-145482 by A-5526 -to enable last link and correct total records count
public class FindConsignmentDetailsFilterQuery extends PageableNativeQuery<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * The searchContainerFilterVO
	 */
	private CarditEnquiryFilterVO carditEnquiryFilterVO;

	/**
	 * The baseQuery
	 */
	private String baseQuery;

	/**
	 * The Constructor the Class
	 *
	 * @param searchContainerFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	//Modified as part of bug ICRD-145482 by A-5526 -to enable last link and correct total records count
	public FindConsignmentDetailsFilterQuery(
			CarditEnquiryFilterVO carditEnquiryFilterVO, String baseQuery,FindConsignmentDetailsMapper findConsignmentDetailsMapper)
			throws SystemException {
		//Modified as part of bug ICRD-145482 by A-5526 -to enable last link and correct total records count
		super(carditEnquiryFilterVO.getTotalRecordsCount(), findConsignmentDetailsMapper);
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}

	/**
	 * @author a-1936
	 * @return
	 *
	 */
	public String getNativeQuery() {
		log.entering("FindConsignmentDetailsFilterQuery", "Inside the NativeQuery");
		String companyCode = carditEnquiryFilterVO.getCompanyCode();
		log.log(Log.FINE, "Company Code-------------->>>>>>>>>>", companyCode);
		String ooe = carditEnquiryFilterVO.getOoe();
		String doe = carditEnquiryFilterVO.getDoe();
		String mailCategoryCode =carditEnquiryFilterVO.getMailCategoryCode();
		//String mailClass = carditEnquiryFilterVO.getMailClass();
		String mailSubclass = carditEnquiryFilterVO.getMailSubclass();
		String year = carditEnquiryFilterVO.getYear();
		String despatchSerialNumber = carditEnquiryFilterVO.getDespatchSerialNumber();
		String receptacleSerialNumber = carditEnquiryFilterVO.getReceptacleSerialNumber();
		String consignmentDocument= carditEnquiryFilterVO.getConsignmentDocument();
		String paoCode = carditEnquiryFilterVO.getPaoCode();
		LocalDate fromDate = carditEnquiryFilterVO.getFromDate();
		LocalDate toDate = carditEnquiryFilterVO.getToDate();
		String carrierCode = carditEnquiryFilterVO.getCarrierCode();
		String flightNumber = carditEnquiryFilterVO.getFlightNumber();
		LocalDate flightDate = carditEnquiryFilterVO.getFlightDate();
		String pol = carditEnquiryFilterVO.getPol();
		String uldNumber = carditEnquiryFilterVO.getUldNumber();
		String mailStatus=carditEnquiryFilterVO.getMailStatus();
		String mailbagId=carditEnquiryFilterVO.getMailbagId();
		StringBuilder builder = new StringBuilder(baseQuery);
		if((carrierCode != null && carrierCode.trim().length() > 0) ||
				(flightNumber != null && flightNumber.trim().length() > 0) ||
				(flightDate != null) || (pol != null && pol.trim().length() > 0)){
			builder.append(" LEFT OUTER JOIN MALCSGRTG RTG ");
			builder.append(" ON MST.CMPCOD = RTG.CMPCOD ");
			builder.append(" AND MST.CSGDOCNUM = RTG.CSGDOCNUM ");
			builder.append(" AND MST.CSGSEQNUM = RTG.CSGSEQNUM ");
			builder.append(" AND MST.POACOD = RTG.POACOD ");
		}
		//added by A-7371 as part of  ICRD-266799 
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			
			 builder.append(" INNER JOIN MALFLT ASGFLT ");
			 builder.append(" ON ASGFLT.CMPCOD  = MALMST.CMPCOD ");
			 builder.append(" AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR ");
			 builder.append(" AND ASGFLT.FLTNUM    = MALMST.FLTNUM ");
			 builder.append(" AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM ");
			
		}
		builder.append(" WHERE MST.CSGDOCNUM NOT LIKE 'ACM%' ");
		builder.append(" AND MST.CMPCOD = ? ");
		int index = 0;
		this.setParameter(++index,companyCode);
		if(ooe!=null && ooe.trim().length() > 0)
		{
			builder.append("AND MALMST.ORGEXGOFC = ?");
			this.setParameter(++index,ooe);
		}
		if(doe!=null && doe.trim().length() > 0)
		{
			builder.append("AND MALMST.DSTEXGOFC = ?");
			this.setParameter(++index,doe);
		}
		if(mailCategoryCode!=null && mailCategoryCode.trim().length() > 0)
		{
			builder.append("AND MALMST.MALCTG = ?");
			this.setParameter(++index,mailCategoryCode);
		}
		if(mailSubclass!=null && mailSubclass.trim().length() > 0)
		{
			builder.append("AND MALMST.MALSUBCLS = ?");
			this.setParameter(++index,mailSubclass);
		}
		if(year!=null && year.trim().length() > 0)
		{
			builder.append("AND MALMST.YER = ?");
			this.setParameter(++index,year);
		}

		if(despatchSerialNumber!=null && despatchSerialNumber.trim().length() > 0)
		{
			builder.append("AND MALMST.DSN = ?");
			this.setParameter(++index,despatchSerialNumber);
		}
		if(receptacleSerialNumber!=null && receptacleSerialNumber.trim().length() > 0)
		{
			builder.append("AND MALMST.RSN = ?");
			this.setParameter(++index,receptacleSerialNumber);
		}
		if(consignmentDocument!=null && consignmentDocument.trim().length() > 0)
		{
			builder.append("AND MALMST.CSGDOCNUM = ?");
			this.setParameter(++index,consignmentDocument);
		}

		if(paoCode!=null && paoCode.trim().length() > 0)
		{
			builder.append("AND MALMST.POACOD = ?");
			this.setParameter(++index,paoCode);
		}

		if(fromDate!=null)
		{
			builder.append(" AND TRUNC(MST.CSGDAT) >=  TO_DATE(?, 'yyyy-MM-dd')");
			this.setParameter(++index,fromDate.toSqlDate().toString());
		}

		if(toDate!=null)
		{
			builder.append(" AND TRUNC(MST.CSGDAT) <=  TO_DATE(?, 'yyyy-MM-dd')");
			this.setParameter(++index,toDate.toSqlDate().toString());
		}

		
		//commented by A-7371 as part of ICRD-266799 starts
		/*if(carrierCode!=null && carrierCode.trim().length() > 0)
		{
			builder.append("AND RTG.FLTCARCOD = ?");
			this.setParameter(++index,carrierCode);
		}
		if(flightNumber!=null && flightNumber.trim().length() > 0)
		{
			builder.append("AND RTG.FLTNUM = ?");
			this.setParameter(++index,flightNumber);
		}
		if(flightDate!=null)
		{
			builder.append("AND RTG.FLTDAT = TO_DATE(?, 'yyyy-MM-dd')");
			this.setParameter(++index,flightDate.toSqlDate().toString());
		}*/
		//commented by A-7371 as part of ICRD-266799 ends
		
		//added by A-7371 as part of  ICRD-266799 starts
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			if(carrierCode!=null && carrierCode.trim().length() > 0)
			{
				builder.append("AND ASGFLT.FLTCARCOD = ?");
				this.setParameter(++index,carrierCode);
			}
			if(flightNumber!=null && flightNumber.trim().length() > 0)
			{
				builder.append("AND ASGFLT.FLTNUM = ?");
				this.setParameter(++index,flightNumber);
			}
			if(flightDate!=null)
			{
				builder.append("AND TRUNC(ASGFLT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				this.setParameter(++index,flightDate.toSqlDate().toString());
			}
		}
	else{
		if(carrierCode!=null && carrierCode.trim().length() > 0)
		{
			builder.append("AND RTG.FLTCARCOD = ?");
			this.setParameter(++index,carrierCode);
		}
		if(flightNumber!=null && flightNumber.trim().length() > 0)
		{
			builder.append("AND RTG.FLTNUM = ?");
			this.setParameter(++index,flightNumber);
		}
		if(flightDate!=null)
		{
			builder.append("AND TRUNC(RTG.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
			this.setParameter(++index,flightDate.toSqlDate().toString());
		}
	  }//added by A-7371 as part of  ICRD-266799 ends
		if(pol!=null && pol.trim().length()>0)
		{
			builder.append("AND RTG.POL = ?");
			this.setParameter(++index,pol);
		}
		if(uldNumber!=null && uldNumber.trim().length()>0){

			builder.append("AND MALMST.CONNUM = ?");
			this.setParameter(++index,uldNumber);
		}
		//done by indu for 50204
		if(mailStatus!=null && mailStatus.trim().length()>0){
			if("ACP".equals(mailStatus)){
				builder.append(" AND MALMST.MALSTA <> 'NEW' ");
			}
			else if("CAP".equals(mailStatus)){
				builder.append(" AND MALMST.MALSTA = 'NEW' ");

			}
		}
		//Added for ICRD-205027 starts
		if(mailbagId!=null && mailbagId.trim().length()>0){

			builder.append("AND MALMST.MALIDR = ?");
			this.setParameter(++index,mailbagId);
		}
		//Added for ICRD-205027 ends
		
		
		if(carditEnquiryFilterVO.getShipmentPrefix() != null&&carditEnquiryFilterVO.getShipmentPrefix().trim().length()>0)
		{
			builder.append("AND MALMST.SHPPFX = ?");
			this.setParameter(++index,carditEnquiryFilterVO.getShipmentPrefix());
		}
		if(carditEnquiryFilterVO.getDocumentNumber()!=null && carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0)
		{
			builder.append("AND MALMST.MSTDOCNUM = ?");
			this.setParameter(++index,carditEnquiryFilterVO.getDocumentNumber());
		}

		builder.append(" ORDER BY MST.POACOD,MST.CSGDOCNUM,MST.CSGDAT,MALMST.CONNUM,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC, ");
		builder.append(" MALMST.YER,MALMST.DSN,MALMST.RSN,MALMST.MALCTG, MALMST.MALCLS, MALMST.MALSUBCLS,MALMST.MALIDR ");
		return builder.toString();
	}
}
