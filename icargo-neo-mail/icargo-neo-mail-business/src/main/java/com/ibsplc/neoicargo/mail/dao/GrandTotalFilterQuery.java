package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * This class is used to append the Query dynamically. 
 * @author a-8061
 */
public class GrandTotalFilterQuery extends NativeQuery {
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
	public GrandTotalFilterQuery(CarditEnquiryFilterVO carditEnquiryFilterVO, String baseQuery) {
		super(PersistenceController.getEntityManager().currentSession());
		this.carditEnquiryFilterVO = carditEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}
//TODO: TO rewrite in Neo
public String getNativeQuery() {
	String companyCode = carditEnquiryFilterVO.getCompanyCode();
	String ooe = carditEnquiryFilterVO.getOoe();
	String doe = carditEnquiryFilterVO.getDoe();
	String mailCategoryCode =carditEnquiryFilterVO.getMailCategoryCode();
	String mailSubclass = carditEnquiryFilterVO.getMailSubclass();
	String year = carditEnquiryFilterVO.getYear();
	String despatchSerialNumber = carditEnquiryFilterVO.getDespatchSerialNumber();
	String receptacleSerialNumber = carditEnquiryFilterVO.getReceptacleSerialNumber();
	String consignmentDocument= carditEnquiryFilterVO.getConsignmentDocument();
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
	String shipmentPrefix=carditEnquiryFilterVO.getShipmentPrefix();
	String isAWBAttached=carditEnquiryFilterVO.getIsAWBAttached();
	String origin =carditEnquiryFilterVO.getMailOrigin();
	String destination =carditEnquiryFilterVO.getMaildestination();
	String paCode = carditEnquiryFilterVO.getPaoCode();
	StringBuilder builder = new StringBuilder(baseQuery);
	int index = 0;



	if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
		//added by a-7531 for icrd-265071
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
		setParameter(++index, Integer.parseInt(carditEnquiryFilterVO.getConsignmentDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
		builder.append(" AND CSGMST.CSGDOCNUM = ?");
		this.setParameter(++index,consignmentDocument);
	}
	if(mailSubclass != null && mailSubclass.trim().length() > 0) {

		builder.append(" AND MALMST.MALSUBCLS = ? ");
		this.setParameter(++index, mailSubclass);

	}
	if(paCode != null && paCode.trim().length() > 0){
		builder.append(" AND MALMST.POACOD = ? ");
		this.setParameter(++index, paCode);
	}

	/*	if(fromDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index, fromDate.toSqlDate().toString());
		}

		if(toDate!=null)
		{
			builder.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			setParameter(++index, toDate.toSqlDate().toString());

		}*/

	if(fromDate!=null && toDate!=null){


		builder.append(" AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD'))  BETWEEN ? AND ? ");
		setParameter(++index, Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));

	}

	//added by a-7531 for icrd-265071
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
	/////////////////////////
	if(origin!=null && origin.trim().length()>0)
	{

		builder.append("AND MALMST.ORGCOD = ?");
		this.setParameter(++index,origin);
	}


	if(destination!=null && destination.trim().length()>0)
	{

		builder.append("AND MALMST.DSTCOD = ?");
		this.setParameter(++index,destination);
	}



	//////////////////////////////
	if(uldNumber!=null && uldNumber.trim().length()>0)
	{
		builder.append("AND CSGDTL.ULDNUM = ?");
		this.setParameter(++index,uldNumber);
	}

	if(mailbagId != null&&mailbagId.trim().length()>0) {
		builder.append(" AND MALMST.MALIDR = ? ");
		this.setParameter(++index,mailbagId);
	}
	//added by a-7531 for icrd-265071
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
	//added by a-7779 for ICRD-342374 starts
	if(carditEnquiryFilterVO.getPaoCode()!= null && carditEnquiryFilterVO.getPaoCode().trim().length()>0){
		builder.append("AND MALMST.POACOD = ?");
		this.setParameter(++index,carditEnquiryFilterVO.getPaoCode());
	}
	//added by a-7779 for ICRD-342374 ends
	if(carditEnquiryFilterVO.getPaoCode()!=null && carditEnquiryFilterVO.getPaoCode().trim().length() > 0)//A-8164 for ICRD-333302
	{
		builder.append("AND MALMST.POACOD = ?");
		this.setParameter(++index,carditEnquiryFilterVO.getPaoCode());
	}


	if(mailStatus!=null && mailStatus.trim().length()>0){
		if("ACP".equals(mailStatus)){
			builder.append("AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN') ");
		}
		else if("CAP".equals(mailStatus)){

			builder.append("AND MALMST.MALSTA IN('NEW','BKD','CAN') ");
		}
	}
	if(isAWBAttached!=null && isAWBAttached.trim().length()>0){//Added by A-8164 for ICRD-333302
		if(MailConstantsVO.FLAG_YES.equals(isAWBAttached)){
			builder.append("AND MALMST.MSTDOCNUM  IS NOT NULL");
		}
		else{
			builder.append("AND MALMST.MSTDOCNUM  IS NULL");
		}
	}


	builder.append(" GROUP BY MALMST.MALIDR ");
	builder.append(" ORDER BY MALMST.MALIDR ");
	builder.append(" ) mst ");


	return builder.toString();
}
}
