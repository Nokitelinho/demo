package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * This class is used to append the Query dynamically. 
 * @author a-2553
 */
public class CarditMailsFilterQuery extends PageableNativeQuery<MailbagVO> {
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
	* @param baseQuery
	* @throws SystemException
	*/
	public CarditMailsFilterQuery(CarditMailsMapper mapper, CarditEnquiryFilterVO carditEnquiryFilterVO,
			String baseQuery) {
		super(carditEnquiryFilterVO.getTotalRecordsCount(), mapper, PersistenceController.getEntityManager().currentSession());
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}

	/** 
	* Constructor	: 	@param mapper Constructor	: 	@param carditEnquiryFilterVO Constructor	: 	@param baseQuery Constructor	: 	@param fromScreen Constructor	: 	@throws SystemException Created by	:	A-8164 Created on	:	18-Sep-2019
	*/
	public CarditMailsFilterQuery(CarditMailsMapper mapper, CarditEnquiryFilterVO carditEnquiryFilterVO,
								  String baseQuery, String fromScreen) {
		super(carditEnquiryFilterVO.getPageSize(), -1, mapper,PersistenceController.getEntityManager().currentSession());
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}

	public String getNativeQuery() {

		String companyCode = carditEnquiryFilterVO.getCompanyCode();
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
		String mailbagId = carditEnquiryFilterVO.getMailbagId();
		ZonedDateTime reqDeliveryTime=carditEnquiryFilterVO.getReqDeliveryTime();
		ZonedDateTime transportServWindowTime=carditEnquiryFilterVO.getTransportServWindow();
		String shipmentPrefix=carditEnquiryFilterVO.getShipmentPrefix();
		String isAwbAttached=carditEnquiryFilterVO.getIsAWBAttached();
		StringBuilder builder = new StringBuilder(baseQuery);
		int index = 0;

		if(carditEnquiryFilterVO.getFromScreen()!=null &&
				carditEnquiryFilterVO.getFromScreen().trim().length()>0) {
			this.setParameter(++index,carditEnquiryFilterVO.getFromScreen());
		} else {
			this.setParameter(++index,"");
		}
		if(pol!=null && pol.trim().length()>0) {
			this.setParameter(++index,pol);
			this.setParameter(++index,pol);
		} else {
			String empty = "";
			this.setParameter(++index,empty);
			this.setParameter(++index,empty);
		}

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
			builder.append(" AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD')) = ? ");
			setParameter(++index, Integer.parseInt((carditEnquiryFilterVO.getConsignmentDate()).format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));

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
		if(carditEnquiryFilterVO.getPaoCode()!=null && carditEnquiryFilterVO.getPaoCode().trim().length() > 0)//A-8164 for ICRD-333302
		{
			builder.append("AND MALMST.POACOD = ?");
			this.setParameter(++index,carditEnquiryFilterVO.getPaoCode());
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
		if(paoCode!=null && paoCode.trim().length() > 0)
		{
			builder.append("AND MALMST.POACOD = ?");
			this.setParameter(++index,paoCode);
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


		if (fromDate != null && toDate != null) {
			builder.append(" AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD'))  BETWEEN ? AND ? ");
			setParameter(++index, Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
			setParameter(++index,Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));



		}


		if(MailConstantsVO.FLAG_YES.equals(isAwbAttached)){
			builder.append(" AND MALMST.MSTDOCNUM is not null ");
		}else if(MailConstantsVO.FLAG_NO.equals(isAwbAttached)){
			builder.append(" AND MALMST.MSTDOCNUM IS null ");
		}


		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			if(pol!=null && !pol.isEmpty()){
				builder.append("AND ASGFLT.ARPCOD = ?");
				this.setParameter(++index,pol);
			}
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
				builder.append(" AND TO_NUMBER(TO_CHAR(ASGFLT.FLTDAT,'YYYYMMDD')) = ? ");
				this.setParameter(++index,Integer.parseInt(flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
				builder.append(" AND TO_NUMBER(TO_CHAR(TRT.FLTDAT,'YYYYMMDD')) = ? ");
				this.setParameter(++index,Integer.parseInt(flightDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
				this.setParameter(++index, LocalDateMapper.toLocalDate(reqDeliveryTime));
			}
		}

		if(transportServWindowTime != null) {
			String transportServWindow=transportServWindowTime.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT));
			if(transportServWindow!=null){
				if(transportServWindow.contains("0000")){
					builder.append(" AND TRUNC(MALMST.TRPSRVENDTIM) = ?");
				}else{
					builder.append(" AND MALMST.TRPSRVENDTIM = ?");
				}
				this.setParameter(++index,LocalDateMapper.toLocalDate(transportServWindowTime));
			}
		}

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

		if(paoCode!= null && paoCode.trim().length()>0){
			builder.append("AND MALMST.POACOD = ?");
			this.setParameter(++index,paoCode);
		}

		if(mailStatus!=null && mailStatus.trim().length()>0){
			if("ACP".equals(mailStatus)){//modified by A-7371 as part of ICRD-251859
				builder.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			}
			else if("CAP".equals(mailStatus)){
				builder.append("AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}

		}
		if(carditEnquiryFilterVO.getMailOrigin() !=null && carditEnquiryFilterVO.getMailOrigin().trim().length()>0) {
			builder.append("AND MALMST.ORGCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMailOrigin());
		}
		if(carditEnquiryFilterVO.getMaildestination()!=null && carditEnquiryFilterVO.getMaildestination().trim().length()>0) {
			builder.append("AND MALMST.DSTCOD= ?");
			this.setParameter(++index,carditEnquiryFilterVO.getMaildestination());
		}
		builder.append(" GROUP BY MALMST.MALIDR,TRT.FLTCARCOD, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.YER, MALMST.DSN, MALMST.RSN, MALMST.MALCTG, MALMST.MALSUBCLS, MALMST.REGIND , ");
		builder.append(" MALMST.HSN, MALMST.WGT, MALMST.CMPCOD, MALMST.SHPPFX, MALMST.MSTDOCNUM, MALMST.ORGCOD, MALMST.DSTCOD, MALMST.MALSEQNUM, MALMST.VOL, MALMST.VOLUNT, ");
		builder.append(" CSGMST.CSGDOCNUM, CSGMST.CSGDAT, CSGMST.CSGDAT, MALMST.POACOD, CSGDTL.ULDNUM, MALMST.MALSTA, MALMST.DUPNUM, MALMST.SEQNUM, MALMST.DOCOWRIDR, MALMST.REQDLVTIM ");
		builder.append(" ORDER BY CSGMST.CSGDOCNUM,CSGMST.CSGDAT,CSGDTL.ULDNUM,MALMST.MALIDR ");
		builder.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);

		return builder.toString();
	}
	/**
	 * Splits and set the classes as each qry param
	 * Apr 17, 2008, A-2553
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
