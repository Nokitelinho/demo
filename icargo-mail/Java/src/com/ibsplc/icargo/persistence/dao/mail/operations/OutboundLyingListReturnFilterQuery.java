package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundLyingListReturnFilterQuery extends PageableNativeQuery<MailbagVO> {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private String baseQuery;

	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;
	private boolean summaryFlag;

	/**
	 * @author a-1936
	 * @param mailbagEnquiryFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	/** changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	public OutboundLyingListReturnFilterQuery(
			OutboundCarditGroupMapper groupMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),groupMapper);
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag=summaryFlag;
	}
	public OutboundLyingListReturnFilterQuery(
			OutboundCarditSummaryMapper summaryMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
					throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),summaryMapper);
	    this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag=summaryFlag;
	}

	/**
	 * This method is used to append the Query dynamically based on the
	 * Different Filter Conditions
	 */

	public String getNativeQuery() {
		log.entering("INSIDE THE FILTER QUERY", "getNativeQuery()");
		String companyCode = mailbagEnquiryFilterVO.getCompanyCode();
		String ooe = mailbagEnquiryFilterVO.getOoe();
		String doe = mailbagEnquiryFilterVO.getDoe();
		String originAirportCode = mailbagEnquiryFilterVO.getOriginAirportCode();
		String destinationAirportCode = mailbagEnquiryFilterVO.getDestinationAirportCode();
		String mailCategoryCode = mailbagEnquiryFilterVO.getMailCategoryCode();
		String mailSubclass = mailbagEnquiryFilterVO.getMailSubclass();
		String year = mailbagEnquiryFilterVO.getYear();
		String despatchSerialNumber = mailbagEnquiryFilterVO
				.getDespatchSerialNumber();
		String scanPort = mailbagEnquiryFilterVO.getScanPort();
		String receptacleSerialNumber = mailbagEnquiryFilterVO
				.getReceptacleSerialNumber();
		String currentStatus = mailbagEnquiryFilterVO.getCurrentStatus();
		LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
		String damageFlag = mailbagEnquiryFilterVO.getDamageFlag();
		String scanUser = mailbagEnquiryFilterVO.getScanUser();
		String containerNumber = mailbagEnquiryFilterVO.getContainerNumber();
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
		// Added to determine whether the Cardit is Present For a mailBag or Not
		// ADDED AS A PART OF NCA CR
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		StringBuilder builder = new StringBuilder(baseQuery);
		builder.append(",(SELECT DISTINCT 'Y' FROM MALHIS WHERE MALSEQNUM = MST.MALSEQNUM AND CMPCOD      =MST.CMPCOD AND SCNPRT      =MST.SCNPRT AND MALSTA      ='ACP' ) AS ACPFLG ");
		int index = 0;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
				.equals(mailbagEnquiryFilterVO.getTransitFlag());
		String transitJoins = null;
		
		//Modified filter query by A-7794 as part of ICRD-197479
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			builder.append(" FROM MALCDTRCP RCP   ");
		}

		/*
		 * Added By Karthick V as the part of the AirNewzealand Cr
		 */
		//Modified filter query by A-7794 as part of ICRD-197479
		if (isTransitFlag) {
			if(carditPresent != null
					&& MailConstantsVO.FLAG_YES.equals(carditPresent)){
				builder.append(", SHRCTYMST CTY1, SHRCTYMST CTY2,MALMST MST  ");
			}else{
				builder.append("FROM SHRCTYMST CTY1, SHRCTYMST CTY2, MALMST MST ");
			}
		  	 transitJoins = new StringBuilder("  CTY1.CMPCOD = MST.CMPCOD ")
					.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ")
					 .append(
							"  AND CTY2.CMPCOD = MST.CMPCOD ").append(
							"  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) AND   ")
			 .toString();
		}else if(carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)){
			builder.append(", MALMST MST ");
		}else{
			builder.append("FROM MALMST MST ");
		}

		//Added for ICRD-133697 starts
		builder.append(" INNER JOIN MALEXGOFCMST EXGOFC")
		.append(" ON MST.ORGEXGOFC=EXGOFC.EXGOFCCOD")
		.append(" AND MST.CMPCOD  =EXGOFC.CMPCOD")
		.append(" LEFT OUTER JOIN MALPOADTL POADTL ")
		.append(" ON POADTL.CMPCOD = EXGOFC.CMPCOD ")
		.append(" AND POADTL.POACOD = EXGOFC.POACOD ")
		.append(" AND POADTL.PARCOD ='UPUCOD'")
		.append("AND TRUNC(SYSDATE) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");
		//Added for ICRD-133697 ends
		builder.append(" WHERE ");
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			builder.append(" MST.CMPCOD = RCP.CMPCOD AND ").append(
					" MST.MALIDR = RCP.RCPIDR AND  ");
		}

		//Removed as part of icrd-226771
		if (isTransitFlag) {
			builder.append(transitJoins);
		}

		/*
		 * Added By Karthick V to include the Mandatory Field for the Enquiry]
		 * when the Status is Returned .. Retuened Means the Mail Bag is
		 * actually Returned to the Postal Authority It is not there in Our
		 * System...
		 */
		if (scanFromDate != null) {
			builder.append("  TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) >= ? ");
		}
		if (scanToDate != null) {
			builder.append(" AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) <= ? AND  ");
		}

		log.log(Log.INFO, "The CurrentStatus is Returned");

		if (scanFromDate != null) {
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		if (scanToDate != null) {
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}

		if (companyCode != null && companyCode.trim().length() > 0) {
			builder.append("  MST.CMPCOD=? ");
			this.setParameter(++index, companyCode);
		}

		if (ooe != null && ooe.trim().length() > 0) {
			builder.append("  AND MST.ORGEXGOFC=? ");
			this.setParameter(++index, ooe);
		}

		if (doe != null && doe.trim().length() > 0) {
			builder.append("  AND MST.DSTEXGOFC= ? ");
			this.setParameter(++index, doe);
		}
		
		if (originAirportCode!= null && originAirportCode.trim().length() > 0) {
			builder.append("  AND MST.ORGCOD=? ");
			this.setParameter(++index, originAirportCode);
		}
		
		if (destinationAirportCode!= null && destinationAirportCode.trim().length() > 0) {
			builder.append("  AND MST.DSTCOD=? ");
			this.setParameter(++index, destinationAirportCode);
		}
		
		if (mailCategoryCode != null && mailCategoryCode.trim().length() > 0) {
			builder.append(" AND MST.MALCTG=? ");
			this.setParameter(++index, mailCategoryCode);
		}

		if (mailSubclass != null && mailSubclass.trim().length() > 0) {
			builder.append(" AND MST.MALSUBCLS = ? ");
			this.setParameter(++index, mailSubclass);
		}

		if (year != null && year.trim().length() > 0) {
			builder.append(" AND MST.YER= ?  ");
			this.setParameter(++index, year);
		}

		if (despatchSerialNumber != null
				&& despatchSerialNumber.trim().length() > 0) {
			builder.append(" AND MST.DSN= ? ");
			this.setParameter(++index, despatchSerialNumber);
		}

		if (receptacleSerialNumber != null
				&& receptacleSerialNumber.trim().length() > 0) {
			builder.append(" AND MST.RSN= ? ");
			this.setParameter(++index, receptacleSerialNumber);
		}

		if (scanPort != null && scanPort.trim().length() > 0
				&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(currentStatus)) {
			builder.append(" AND MST.SCNPRT = ? ");
			this.setParameter(++index, scanPort);
		}

		builder.append(" AND MST.MALSTA = ?  ");
		this.setParameter(++index, MailConstantsVO.MAIL_STATUS_RETURNED);

		if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
			builder.append(" AND MST.DMGFLG = ? ");
			this.setParameter(++index, damageFlag);
		}

		if (scanUser != null && scanUser.trim().length() > 0) {
			builder.append(" AND MST.SCNUSR LIKE '%").append(scanUser).append(
					"%'");
		}

		if (containerNumber != null && containerNumber.trim().length() > 0) {
			builder.append(" AND MST.CONNUM = ? ");
			this.setParameter(++index, containerNumber);
		}
		//Added for ICRD-133967 starts
		if (consigmentNumber != null
				&& consigmentNumber.trim().length() > 0) {
			builder.append("  AND MST.CSGDOCNUM=? ");
			this.setParameter(++index, consigmentNumber);
		}
		if (upuCode != null
				&& upuCode.trim().length() > 0) {
			builder.append("  AND POADTL.PARVAL=? ");
			this.setParameter(++index, upuCode);
		}
		//Added for ICRD-133967 ends
		//Added for ICRD-205027 starts
		if (mailbagId != null && mailbagId.trim().length() > 0) {
			builder.append("  AND MST.MALIDR=? ");
			this.setParameter(++index, mailbagId);
		}
		//Added for ICRD-205027 ends
		//Added for ICRD-214795 starts
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
				}else{
					builder.append(" AND MST.REQDLVTIM = ?");
				}
				this.setParameter(++index,reqDeliveryTime);
			}
		}
		//Added for ICRD-214795 ends
		// Added to Fetch all the MailBags For which the Cardit has not been
		// Received ..
		if (carditPresent != null
				&& MailConstantsVO.FLAG_NO.equals(carditPresent)) {
			builder.append("  AND  NOT EXISTS  ");
			builder.append(" ( SELECT 1 FROM MALCDTRCP WHERE  ");
			builder.append("  CMPCOD =  MST.CMPCOD AND ");
			builder.append("  RCPIDR =  MST.MALIDR ) ");
		}

		
		if(isTransitFlag){
			builder.append(" AND CTY1.SRVARPCOD <> ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			builder.append(" AND CTY2.SRVARPCOD <> ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
		 }
		if(this.summaryFlag) {
			builder.append(")");
		}
		else {
			builder.append(")MST group by DSTCOD");
		}
		
		return builder.toString();

	}

}

