package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutboundCarditGroupFilterQuery extends PageableNativeQuery<MailbagVO> {
	/** 
	* The searchContainerFilterVO
	*/
	private CarditEnquiryFilterVO carditEnquiryFilterVO;
	/** 
	* The baseQuery
	*/
	private String baseQuery1;
	private String baseQuery2;

	/** 
	* The Constructor the Class
	* @throws SystemException
	*/
	public OutboundCarditGroupFilterQuery(OutboundCarditGroupMapper mapper, CarditEnquiryFilterVO carditEnquiryFilterVO,
			String baseQuery1, String baseQuery2) {
		super(carditEnquiryFilterVO.getTotalRecordsCount(), mapper, PersistenceController.getEntityManager().currentSession());
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
	}

	public OutboundCarditGroupFilterQuery(OutboundCarditGroupMapper mapper, CarditEnquiryFilterVO carditEnquiryFilterVO,
			String baseQuery1, String baseQuery2, int pageSize) {
		super(pageSize, -1, mapper,PersistenceController.getEntityManager().currentSession());
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
	}
	public String getNativeQuery() {
		log.debug("OutboundCarditGroupFilterQuery", "Inside the NativeQuery");
		String companyCode = carditEnquiryFilterVO.getCompanyCode();
		log.debug( "Company Code-------------->>>>>>>>>>", companyCode);
		String ooe = carditEnquiryFilterVO.getOoe();
		String doe = carditEnquiryFilterVO.getDoe();
		String mailCategoryCode =carditEnquiryFilterVO.getMailCategoryCode();
		String mailClass = carditEnquiryFilterVO.getMailClass();
		String mailSubclass = carditEnquiryFilterVO.getMailSubclass();
		String year = carditEnquiryFilterVO.getYear();
		String despatchSerialNumber = carditEnquiryFilterVO.getDespatchSerialNumber();
		String receptacleSerialNumber = carditEnquiryFilterVO.getReceptacleSerialNumber();
		String consignmentDocument= carditEnquiryFilterVO.getConsignmentDocument();
		String paoCode = carditEnquiryFilterVO.getPaoCode();
		ZonedDateTime fromDate = carditEnquiryFilterVO.getFromDate();
		ZonedDateTime toDate = carditEnquiryFilterVO.getToDate();
		String carrierCode = carditEnquiryFilterVO.getCarrierCode();
		String flightNumber = carditEnquiryFilterVO.getFlightNumber();
		ZonedDateTime flightDate = carditEnquiryFilterVO.getFlightDate();
		String pol = carditEnquiryFilterVO.getPol();
		String uldNumber = carditEnquiryFilterVO.getUldNumber();
		String mailStatus=carditEnquiryFilterVO.getMailStatus();
		String mailbagId = carditEnquiryFilterVO.getMailbagId();//Added for ICRD-205027
		ZonedDateTime reqDeliveryTime=carditEnquiryFilterVO.getReqDeliveryTime();
		//Added by A-7531 for icrd-192536
		String shipmentPrefix=carditEnquiryFilterVO.getShipmentPrefix();
		StringBuilder builder = new StringBuilder(baseQuery1);
		int index = 0;
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			builder.append(" INNER JOIN MALFLT ASGFLT ");
			builder.append(" ON ASGFLT.CMPCOD  = MALMST.CMPCOD ");
			builder.append(" AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR ");
			builder.append(" AND ASGFLT.FLTNUM    = MALMST.FLTNUM ");
			builder.append(" AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM ");
		}
		if(carditEnquiryFilterVO.isPendingResditChecked()) {
			builder.append(" INNER JOIN MALRDT MALRDT ");
			builder.append(" ON MALRDT.CMPCOD  = CSGDTL.CMPCOD ");
			builder.append(" AND MALRDT.MALSEQNUM = CSGDTL.MALSEQNUM ");
			builder.append("  AND MALRDT.PROSTA='Y' ");
			builder.append(" AND MALRDT.EVTCOD IN ('74','42','48','6','82','57','24','14','40','23','43','41','21') ");
			builder.append(" AND (MALRDT.RDTSND='Y' OR MALRDT.RDTSND='S') ");
		}
		if(companyCode!=null && companyCode.trim().length() > 0)
		{
			builder.append(" WHERE CSGMST.CMPCOD = ?");
			this.setParameter(++index,companyCode);
		}
		if(carditEnquiryFilterVO.getConsignmentDate()!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) = TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index, carditEnquiryFilterVO.getConsignmentDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));

		}
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
		if(year!=null && year.trim().length() > 0)
		{
			builder.append("AND MALMST.YER = ?");
			this.setParameter(++index,year);
		}
		if(despatchSerialNumber!=null && despatchSerialNumber.trim().length() > 0)
		{
			builder.append("AND  MALMST.DSN = ?");
			this.setParameter(++index,despatchSerialNumber);
		}
		if(receptacleSerialNumber!=null && receptacleSerialNumber.trim().length() > 0)
		{
			builder.append("AND MALMST.RSN = ?");
			this.setParameter(++index,receptacleSerialNumber);
		}
		if(consignmentDocument!=null && consignmentDocument.trim().length() > 0)
		{
			builder.append("AND CSGMST.CSGDOCNUM = ?");
			this.setParameter(++index,consignmentDocument);
		}
		if(mailSubclass != null && mailSubclass.trim().length() > 0) {
			builder.append(" AND MALMST.MALSUBCLS = ? ");
			this.setParameter(++index, mailSubclass);
		}
		if(paoCode!=null && paoCode.trim().length() > 0)
		{
			builder.append("AND MALMST.POACOD = ?");
			this.setParameter(++index,paoCode);
		}
		if(fromDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index,fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
		}
		if(toDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index, toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
		}
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
				this.setParameter(++index,flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
			}
		}
		else{
			if(carrierCode!=null && carrierCode.trim().length() > 0)
			{
				builder.append("AND TRT.FLTCARCOD = ?");
				this.setParameter(++index,carrierCode);
			}
			if(flightNumber!=null && flightNumber.trim().length() > 0)
			{
				builder.append("AND TRT.FLTNUM = ?");
				this.setParameter(++index,flightNumber);
			}
			if(flightDate!=null)
			{
				builder.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				this.setParameter(++index,flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
			}
		}
		if(pol!=null && pol.trim().length()>0)
		{
			builder.append("AND TRT.POL = ?");
			this.setParameter(++index,pol);
		}
		if(uldNumber!=null && uldNumber.trim().length()>0)
		{
			builder.append("AND CSGDTL.ULDNUM = ?");
			this.setParameter(++index,uldNumber);
		}
		//Added for ICRD-205027 starts
		if(mailbagId != null&&mailbagId.trim().length()>0) {
			builder.append(" AND MALMST.MALIDR = ? ");
			this.setParameter(++index,mailbagId);
		}
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT));
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					builder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					builder.append(" AND MALMST.REQDLVTIM = ?");
				}
				this.setParameter(++index,reqDeliveryTime);
			}
		}
		//Added by A-8149 for ICRD-259045 ends
		//added by a-7531 for icrd-192536
		if(shipmentPrefix != null&&shipmentPrefix.trim().length()>0)
		{
			builder.append("AND MALMST.SHPPFX = ?");
			this.setParameter(++index,shipmentPrefix);
		}
		if(carditEnquiryFilterVO.getDocumentNumber()!=null && carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0)
		{
			builder.append("AND MALMST.MSTDOCNUM = ?");
			this.setParameter(++index,carditEnquiryFilterVO.getDocumentNumber());
		}
		/**
		 * As per new table structure always data present in MALMST
		 */
		if(mailStatus!=null && mailStatus.trim().length()>0){
			if("ACP".equals(mailStatus)){//modified by A-7371 as part of ICRD-251859
				builder.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			}
			else if("CAP".equals(mailStatus)){
				builder.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}//a7531
		//Added by A-8176 for ICRD-228739
		if(carditEnquiryFilterVO.getMailOrigin() !=null && carditEnquiryFilterVO.getMailOrigin().trim().length()>0) {
			builder.append("AND MALMST.ORGCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMailOrigin());
		}
		if(carditEnquiryFilterVO.getMaildestination()!=null && carditEnquiryFilterVO.getMaildestination().trim().length()>0) {
			builder.append("AND MALMST.DSTCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMaildestination());
		}
		builder.append("  ) MST GROUP BY cmpcod, DSTCOD)");
		builder.append(" ");
		builder.append(this.baseQuery2);
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			builder.append(" INNER JOIN MALFLT ASGFLT ");
			builder.append(" ON ASGFLT.CMPCOD  = MALMST.CMPCOD ");
			builder.append(" AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR ");
			builder.append(" AND ASGFLT.FLTNUM    = MALMST.FLTNUM ");
			builder.append(" AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM ");
		}
		if(carditEnquiryFilterVO.isPendingResditChecked()) {
			builder.append(" INNER JOIN MALRDT MALRDT ");
			builder.append(" ON MALRDT.CMPCOD  = CSGDTL.CMPCOD ");
			builder.append(" AND MALRDT.MALSEQNUM = CSGDTL.MALSEQNUM ");
			builder.append("  AND MALRDT.PROSTA='Y' ");
			builder.append(" AND MALRDT.EVTCOD IN ('74','42','48','6','82','57','24','14','40','23','43','41','21') ");
			builder.append(" AND (MALRDT.RDTSND='Y' OR MALRDT.RDTSND='S') ");
		}
		if(companyCode!=null && companyCode.trim().length() > 0)
		{
			builder.append(" WHERE CSGMST.CMPCOD = ?");
			this.setParameter(++index,companyCode);
		}
		if(carditEnquiryFilterVO.getConsignmentDate()!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) = TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index,carditEnquiryFilterVO.getConsignmentDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT)));

		}
		//A-8061 Added for ICRD-82434 ends
		//Modified by a-7531 for icrd-192536
		if(ooe!=null && ooe.trim().length() > 0)
		{
			builder.append("AND MALMST.ORGEXGOFC = ? ");
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
		if(year!=null && year.trim().length() > 0)
		{
			builder.append("AND MALMST.YER = ?");
			this.setParameter(++index,year);
		}

		if(despatchSerialNumber!=null && despatchSerialNumber.trim().length() > 0)
		{
			builder.append("AND  MALMST.DSN = ?");
			this.setParameter(++index,despatchSerialNumber);
		}
		if(receptacleSerialNumber!=null && receptacleSerialNumber.trim().length() > 0)
		{
			builder.append("AND MALMST.RSN = ?");
			this.setParameter(++index,receptacleSerialNumber);
		}
		if(consignmentDocument!=null && consignmentDocument.trim().length() > 0)
		{
			builder.append("AND CSGMST.CSGDOCNUM = ?");
			this.setParameter(++index,consignmentDocument);
		}
		if(mailSubclass != null && mailSubclass.trim().length() > 0) {

			builder.append(" AND MALMST.MALSUBCLS = ? ");
			this.setParameter(++index, mailSubclass);

		}
		//removed as part of icrd-192536
		/*
			if(paoCode!=null && paoCode.trim().length() > 0)
			{
				builder.append("AND CDTMST.SDRIDR = ?");
				this.setParameter(++index,paoCode);
			}
			*/
		if(paoCode!=null && paoCode.trim().length() > 0)
		{
			builder.append("AND MALMST.SDRIDR = ?");
			this.setParameter(++index,paoCode);
		}
		if(fromDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index,fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
		}

		if(toDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index,toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
		}
		//Modified by A-7540
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
				this.setParameter(++index,flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
			}
		}
		else{
			if(carrierCode!=null && carrierCode.trim().length() > 0)
			{
				builder.append("AND TRT.FLTCARCOD = ?");
				this.setParameter(++index,carrierCode);
			}
			if(flightNumber!=null && flightNumber.trim().length() > 0)
			{
				builder.append("AND TRT.FLTNUM = ?");
				this.setParameter(++index,flightNumber);
			}
			if(flightDate!=null)
			{
				builder.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				this.setParameter(++index,flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
			}
		}
		if(pol!=null && pol.trim().length()>0)
		{
			builder.append("AND TRT.POL = ?");
			this.setParameter(++index,pol);
		}
		if(uldNumber!=null && uldNumber.trim().length()>0)
		{
			builder.append("AND CSGDTL.ULDNUM = ?");
			this.setParameter(++index,uldNumber);
		}
		//Added for ICRD-205027 starts
		if(mailbagId != null&&mailbagId.trim().length()>0) {
			builder.append(" AND MALMST.MALIDR = ? ");
			this.setParameter(++index,mailbagId);
		}
		//Added for ICRD-205027 ends

		//Added by A-8149 for ICRD-259045 starts
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT));
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					builder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					builder.append(" AND MALMST.REQDLVTIM = ?");
				}
				this.setParameter(++index,reqDeliveryTime);
			}
		}
		//Added by A-8149 for ICRD-259045 ends

		//added by a-7531 for icrd-192536
		if(shipmentPrefix != null&&shipmentPrefix.trim().length()>0)
		{
			builder.append("AND MALMST.SHPPFX = ?");
			this.setParameter(++index,shipmentPrefix);
		}
		if(carditEnquiryFilterVO.getDocumentNumber()!=null && carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0)
		{
			builder.append("AND MALMST.MSTDOCNUM = ?");
			this.setParameter(++index,carditEnquiryFilterVO.getDocumentNumber());
		}
		/**
		 * As per new table structure always data present in MALMST
		 */
		if(mailStatus!=null && mailStatus.trim().length()>0){
			if("ACP".equals(mailStatus)){//modified by A-7371 as part of ICRD-251859
				builder.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			}
			else if("CAP".equals(mailStatus)){
				builder.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}//a7531
		//Added by A-8176 for ICRD-228739
		if(carditEnquiryFilterVO.getMailOrigin() !=null && carditEnquiryFilterVO.getMailOrigin().trim().length()>0) {
			builder.append("AND MALMST.ORGCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMailOrigin());
		}

		if(carditEnquiryFilterVO.getMaildestination()!=null && carditEnquiryFilterVO.getMaildestination().trim().length()>0) {
			builder.append("AND MALMST.DSTCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMaildestination());
		}
		builder.append("  ) MST GROUP BY cmpcod, DSTCOD");
		builder.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		log.debug( "FINAL query ", builder.toString());
		return builder.toString();
	}
}
