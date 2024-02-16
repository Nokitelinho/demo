
/*
 * MailBagFilterQuery.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Date;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author a-1936 Karthick V This Filter Query is used to append the Query
 *         Dynamically in case of the Status Being the Assigned ,Accepted and
 *         the Transferred.. Modified By Karthick V as the part of the NCA Mail
 *         Tracking CR..
 */
public class MailBagFilterQuery extends PageableNativeQuery<MailbagVO> {

	/**
	 * The Local logger Instance for this Class.
	 */
	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/**
	 * The base Query
	 */
	private String baseQuery;

	/**
	 * The mailbagEnquiryFilterVO
	 */
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;

	/**
	 * @author A-5216 The constructor
	 * @param baseQuery
	 * @param mailbagEnquiryFilterVO
	 * @throws SystemException
	 */
	/** changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	public MailBagFilterQuery(
			MailbagMapper mailbagMapper ,MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),mailbagMapper);
		this.baseQuery = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}
	/**
	 * @author A-8353
	 * @param mailbagMapper
	 * @param mailbagEnquiryFilterVO
	 * @param baseQuery
	 * @param fromScreen
	 * @throws SystemException
	 */
		public MailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
				String baseQuery, String fromScreen) throws SystemException {
			super(mailbagEnquiryFilterVO.getPageSize(),-1,mailbagMapper);
			this.baseQuery = baseQuery;
			this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		}

	/**
	 * This method is used to append the Query Dynamically when the Status is
	 * ACCEPTED..
	 *
	 * @return
	 */
	public String getNativeQuery() {
		log.entering("Inside the AcceptedMailBagFilterQuery",
				"getNativeQuery()");
		log.log(Log.FINE,"THE  LATEST ACCEPTED MAIL BAG ENQUIRY FILTER QUERY");

		String companyCode = mailbagEnquiryFilterVO.getCompanyCode();

		String ooe = mailbagEnquiryFilterVO.getOoe();
		String doe = mailbagEnquiryFilterVO.getDoe();
		String mailCategoryCode = mailbagEnquiryFilterVO.getMailCategoryCode();
		String mailSubclass = mailbagEnquiryFilterVO.getMailSubclass();
		String year = mailbagEnquiryFilterVO.getYear();
		String despatchSerialNumber = mailbagEnquiryFilterVO
				.getDespatchSerialNumber();
		String receptacleSerialNumber = mailbagEnquiryFilterVO
				.getReceptacleSerialNumber();
		String scanPort = mailbagEnquiryFilterVO.getScanPort();
		LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
		String damageFlag = mailbagEnquiryFilterVO.getDamageFlag();
		String scanUser = mailbagEnquiryFilterVO.getScanUser();
		String containerNumber = mailbagEnquiryFilterVO.getContainerNumber();
		String flightNumber = mailbagEnquiryFilterVO.getFlightNumber();
		LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
		int carrierId = mailbagEnquiryFilterVO.getCarrierId();
		String fromCarrierCode = mailbagEnquiryFilterVO.getFromCarrier();
		String carrierCode = mailbagEnquiryFilterVO.getCarrierCode();
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
		String serviceLevel = mailbagEnquiryFilterVO.getServiceLevel();
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		LocalDate transportServWinTime =mailbagEnquiryFilterVO.getTransportServWindow();
		StringBuilder builder = new StringBuilder(baseQuery);
		String onTimeDeliveryFlag = mailbagEnquiryFilterVO.getOnTimeDelivery();
		
		String originAirportCode = mailbagEnquiryFilterVO.getOriginAirportCode();
		String destinationAirportCode =  mailbagEnquiryFilterVO.getDestinationAirportCode();
		 
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
		.equals(mailbagEnquiryFilterVO.getTransitFlag());
		Date flightSqlDate = null;
		int index = 0;
		log.log(Log.FINE, "The Damaged Status is ", damageFlag);
		

		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			builder.append(" INNER JOIN MALCDTRCP RCP ON MST.CMPCOD = RCP.CMPCOD AND MST.MALIDR = RCP.RCPIDR ");
		}
		//Added for ICRD-133967 starts
		if (upuCode != null
				&& upuCode.trim().length() > 0) {
		builder.append(" INNER JOIN MALEXGOFCMST EXGOFC")
		.append(" ON MST.ORGEXGOFC=EXGOFC.EXGOFCCOD")
		.append(" AND MST.CMPCOD  =EXGOFC.CMPCOD")
		.append(" LEFT OUTER JOIN MALPOADTL POADTL ")
		.append(" ON POADTL.CMPCOD = EXGOFC.CMPCOD ")
		.append(" AND POADTL.POACOD = EXGOFC.POACOD ")
		.append(" AND POADTL.PARCOD ='UPUCOD'")
		.append("AND TRUNC(current_date) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");//A-9092 as a part of IASCB-74752
		}
		//Added for ICRD-133967 ends
		builder.append(" WHERE ");
		boolean isContinued = false;
		if (scanUser != null && scanUser.trim().length() > 0) {
			builder.append(" MST.SCNUSR LIKE '%").append(scanUser).append("%'");
			isContinued = true;
		}
		//Added by A-5945 for ICRD-88823 starts
		if (scanFromDate != null ) {
			if(isContinued){
				builder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.SCNDAT),'YYYYMMDD')) >= ? ");
			}else{
				builder.append(" TO_NUMBER(TO_CHAR(TRUNC(HIS.SCNDAT),'YYYYMMDD')) >= ? ");
				isContinued = true;
			}
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		if (scanToDate != null ) {
			if(isContinued){
				builder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.SCNDAT),'YYYYMMDD'))   <= ?");
			}else{
				builder.append(" TO_NUMBER(TO_CHAR(TRUNC(HIS.SCNDAT),'YYYYMMDD'))   <= ? ");
				isContinued = true;
			}
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		//Added by A-5945 for ICRD-88823 ends
		if (flightNumber != null && !flightNumber.isEmpty()&&!MailConstantsVO.DESTN_FLT_STR.equals(flightNumber)) {
			if(isContinued){
				builder.append(" AND SEGDTL.FLTNUM = ? ");
			}else{
				builder.append(" SEGDTL.FLTNUM = ? ");
				isContinued = true;
			}
			this.setParameter(++index, flightNumber);
		}


		if(flightDate!=null&&!MailConstantsVO.DESTN_FLT_STR.equals(flightNumber)){
			flightSqlDate = flightDate.toSqlDate();
			String flightDateString = String.valueOf(flightSqlDate);
			if(isContinued){
				builder	.append(" AND  TRUNC(HIS.FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
			}else{
				builder	.append(" TRUNC(HIS.FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
				isContinued = true;
			}
			this.setParameter(++index, flightDateString);
		}
		if(carrierCode != null && carrierCode.trim().length() > 0) {
			if(isContinued){
				builder.append(" AND HIS.FLTCARCOD  = ? ");
			}else {
				builder.append(" HIS.FLTCARCOD  = ? ");
				isContinued = true;
			}
			this.setParameter(++index, carrierCode);
		}

		if (carrierId > 0) {
			if(isContinued){
				builder.append(" AND SEGDTL.FLTCARIDR = ? ");
			}else{
				builder.append(" SEGDTL.FLTCARIDR = ? ");
				isContinued = true;
			}
			this.setParameter(++index, carrierId);
		}

		if (scanPort != null && scanPort.trim().length() > 0) {
			if(isContinued){
				builder.append(" AND HIS.SCNPRT = ? ");
			}else{
				builder.append(" HIS.SCNPRT = ? ");
				isContinued = true;
			}
			this.setParameter(++index, scanPort);
		}

		if (containerNumber != null && containerNumber.trim().length() > 0) {
			if(isContinued){
				builder.append(" AND COALESCE(SEGDTL.CONNUM,MST.CONNUM) = ? ");
				builder.append(" AND MST.MALSTA <> 'RTN' ");
			}else{
				builder.append(" COALESCE(SEGDTL.CONNUM,MST.CONNUM) = ? ");//A-9092 as a part of IASCB-74752
				builder.append(" AND MST.MALSTA <> 'RTN' ");
				isContinued = true;
			}
			this.setParameter(++index, containerNumber);
		}
		if (companyCode != null && companyCode.trim().length() > 0) {
			if(isContinued){
				builder.append(" AND MST.CMPCOD=? ");
			}else{
				builder.append(" MST.CMPCOD = ? ");
				isContinued = true;
			}
			this.setParameter(++index, companyCode);
		}

		if (ooe != null && ooe.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.ORGEXGOFC=? ");
			}else{
				builder.append(" MST.ORGEXGOFC = ? ");
				isContinued = true;
			}
			this.setParameter(++index, ooe);
		}

		if (doe != null && doe.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.DSTEXGOFC=? ");
			}else{
				builder.append(" MST.DSTEXGOFC = ? ");
				isContinued = true;
			}
			this.setParameter(++index, doe);
		}
		
		if (originAirportCode != null && originAirportCode.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.ORGCOD=? ");
			}else{
				builder.append(" MST.ORGCOD = ? ");
				isContinued = true;
			}
			this.setParameter(++index, originAirportCode);
		}

		if (destinationAirportCode != null && destinationAirportCode.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.DSTCOD=? ");
			}else{
				builder.append(" MST.DSTCOD = ? ");
				isContinued = true;
			}
			this.setParameter(++index, destinationAirportCode);
		}
		
		if (mailCategoryCode != null && mailCategoryCode.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.MALCTG=? ");
			}else{
				builder.append(" MST.MALCTG = ? ");
				isContinued = true;
			}
			this.setParameter(++index, mailCategoryCode);
		}

		if (mailSubclass != null && mailSubclass.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.MALSUBCLS=? ");
			}else{
				builder.append(" MST.MALSUBCLS = ? ");
				isContinued = true;
			}
			this.setParameter(++index, mailSubclass);
		}

		if (year != null && year.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.YER=? ");
			}else{
				builder.append(" MST.YER = ? ");
				isContinued = true;
			}
			this.setParameter(++index, Integer.parseInt(year));//A-9092 as a part of IASCB-74752
		}

		if (despatchSerialNumber != null
				&& despatchSerialNumber.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.DSN=? ");
			}else{
				builder.append(" MST.DSN = ? ");
				isContinued = true;
			}
			this.setParameter(++index, despatchSerialNumber);
		}

		if (receptacleSerialNumber != null
				&& receptacleSerialNumber.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.RSN=? ");
			}else{
				builder.append(" MST.RSN = ? ");
				isContinued = true;
			}
			this.setParameter(++index, receptacleSerialNumber);
		}
		//Added for ICRD-133967 starts
		if (consigmentNumber != null
				&& consigmentNumber.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.CSGDOCNUM=? ");
			}else{
				builder.append(" MST.CSGDOCNUM = ? ");
				isContinued = true;
			}
			this.setParameter(++index, consigmentNumber);
		}
		if (upuCode != null
				&& upuCode.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND POADTL.PARVAL=? ");
			}else{
				builder.append(" POADTL.PARVAL = ? ");
				isContinued = true;
			}
			this.setParameter(++index, upuCode);
		}
		//Added for ICRD-133967 ends
		//Added for ICRD-214795 starts
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(isContinued){
					if(rqdDlvTime.contains("0000")){
						builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
					}else{
						builder.append(" AND MST.REQDLVTIM = ?");
						
					}
				}else{
					if(rqdDlvTime.contains("0000")){
						builder.append(" TRUNC(MST.REQDLVTIM) = ?");
					}else{
						builder.append(" MST.REQDLVTIM = ?");
						
					}
				}
				this.setParameter(++index,reqDeliveryTime);
			}
		}
		//Added for IASCB-35785 starts
				if(transportServWinTime != null) {
					String transportServWin=transportServWinTime.toDisplayFormat("yyyyMMddHHmm");
					if(transportServWin!=null){
						if(isContinued){
							if(transportServWin.contains("0000")){
								builder.append(" AND TRUNC(MST.TRPSRVENDTIM) = ?");
							}else{
								builder.append(" AND MST.TRPSRVENDTIM = ?");
							}
						}else{
							if(transportServWin.contains("0000")){
								builder.append(" TRUNC(MST.TRPSRVENDTIM) = ?");
							}else{
								builder.append(" MST.TRPSRVENDTIM = ?");
							}
						}
						this.setParameter(++index,transportServWinTime);
					}
				}
		//Added for ICRD-214795 ends
		if (mailbagId != null && mailbagId.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.MALIDR=? ");
			}else{
				builder.append(" MST.MALIDR = ? ");
				isContinued = true;
			}
			this.setParameter(++index, mailbagId);      
		}
		//Added by A-8164 for mailinbound starts
		if(mailbagEnquiryFilterVO.getMailBagsToList()!=null   
				&& mailbagEnquiryFilterVO.getMailBagsToList().size()>0){    
			String mailbagAppend="AND MST.MALIDR IN ("; 
			for(int i=1;i<mailbagEnquiryFilterVO.getMailBagsToList().size();i++){
				mailbagAppend=new StringBuffer().append(mailbagAppend).append("?,").toString();
			}
			mailbagAppend=new StringBuffer().append(mailbagAppend).append("?)").toString();
			builder.append(mailbagAppend);
			for(String mailbag:mailbagEnquiryFilterVO.getMailBagsToList()){
				this.setParameter(++index, mailbag);
			}
		}
		//Added by A-8164 for mailinbound ends
		if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
			if(isContinued){
				builder.append("  AND MST.DMGFLG=? ");
			}else{
				builder.append(" MST.DMGFLG = ? ");
				isContinued = true;
			}
			this.setParameter(++index, damageFlag);
		}
		if (serviceLevel != null && serviceLevel.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.MALSRVLVL = ? ");
			}else{
				builder.append(" AND MST.MALSRVLVL = ? ");
				isContinued = true;
			}
			this.setParameter(++index, serviceLevel);
		}
		if (onTimeDeliveryFlag != null && onTimeDeliveryFlag.trim().length() > 0) {
			if(isContinued){
				builder.append("  AND MST.ONNTIMDLVFLG = ? ");
			}else{
				builder.append(" AND MST.ONNTIMDLVFLG = ? ");
				isContinued = true;
			}
			this.setParameter(++index, onTimeDeliveryFlag);
		}

		if (carditPresent != null
				&& MailConstantsVO.FLAG_NO.equals(carditPresent)) {
			if(isContinued){
				builder.append("  AND  NOT EXISTS  ");
			}else{
				builder.append("   NOT EXISTS  ");
				isContinued = true;
			}
			builder.append(" ( SELECT 1 FROM MALCDTRCP WHERE  ");
			builder.append("  CMPCOD =  MST.CMPCOD AND ");
			builder.append("  RCPIDR =  MST.MALIDR ) ");
		}

		if(isTransitFlag){
			if(isContinued){
				 builder.append("  AND   EXISTS  ");
			}else{
				 builder.append("  EXISTS  ");
				isContinued = true;
			}
			 builder.append(" ( SELECT 1 FROM SHRCTYMST CTY1 WHERE  ");
			 builder.append("  CTY1.CMPCOD = MST.CMPCOD  ");
			 builder.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ");
			 builder.append("  AND ( CTY1 .SRVARPCOD IS NULL OR  CTY1 .SRVARPCOD  <>  ? )   ) ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			 builder.append("  AND   EXISTS  ");
			 builder.append(" ( SELECT 1 FROM  SHRCTYMST CTY2  WHERE  ");
			 builder.append("  CTY2.CMPCOD = MST.CMPCOD  ");
			 builder.append("  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) ");
			 builder.append("  AND (  CTY2 .SRVARPCOD IS NULL OR  CTY2 .SRVARPCOD  <>  ?  ) )  ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
		}

		return builder.toString();
	}

}

