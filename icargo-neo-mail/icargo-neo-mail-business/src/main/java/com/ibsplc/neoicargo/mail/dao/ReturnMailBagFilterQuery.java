package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM;
/** 
 * @author a-1936
 */
@Slf4j
public class ReturnMailBagFilterQuery extends PageableNativeQuery<MailbagVO> {
	private String baseQuery;
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;

	/** 
	* changed by A-5216 to enable last link and total record count for Jira Id: ICRD-21098 and ScreenId MTK009
	*/
	public ReturnMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			String baseQuery) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), mailbagMapper, PersistenceController.getEntityManager().currentSession());
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}

	public ReturnMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			String baseQuery, int pageSize) {
		super(pageSize, -1, mailbagMapper,PersistenceController.getEntityManager().currentSession());
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}
	public String getNativeQuery() {
		log.debug("INSIDE THE FILTER QUERY getNativeQuery()");
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
		ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
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
		ZonedDateTime reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		ZonedDateTime transportServWindow=mailbagEnquiryFilterVO.getTransportServWindow();
		StringBuilder builder = new StringBuilder(baseQuery);
		int index = 0;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
				.equals(mailbagEnquiryFilterVO.getTransitFlag());
		String transitJoins = null;
		//Modified filter query by A-7794 as part of ICRD-197479
		/*
		 * Added By Karthick V as the part of the AirNewzealand Cr
		 */
		//Modified filter query by A-7794 as part of ICRD-197479
		if (isTransitFlag) {//A-9092 as a part of IASCB-74752
			if(carditPresent != null
					&& MailConstantsVO.FLAG_YES.equals(carditPresent)){
				builder.append(" FROM  MALMST MST INNER JOIN SHRCTYMST CTY1  ");
			}else{
				builder.append(" FROM  MALMST MST INNER JOIN SHRCTYMST CTY1 ");
			}
			transitJoins = new StringBuilder(" ON CTY1.CMPCOD = MST.CMPCOD ")
					.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ")
					.append(" INNER JOIN SHRCTYMST CTY2 ")
					.append(
							"  ON CTY2.CMPCOD = MST.CMPCOD ").append(
							"  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC)    ")
					.toString();//A-9092 as a part of IASCB-74752
		}else if(carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)){
			builder.append(" FROM MALMST MST ");//A-9092 as a part of IASCB-74752
		}else{
			builder.append(" FROM MALMST MST ");
		}
		if (isTransitFlag) {
			builder.append(transitJoins);
		}
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			builder.append(" INNER JOIN MALCDTRCP RCP   ");//A-9092 as a part of IASCB-74752
		}
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			builder.append(" ON MST.CMPCOD = RCP.CMPCOD AND ").append(
					" MST.MALIDR = RCP.RCPIDR  ");//A-9092 as a part of IASCB-74752
		}//A-9092
		//Added for ICRD-133697 starts
		builder.append(" INNER JOIN MALEXGOFCMST EXGOFC")
				.append(" ON MST.ORGEXGOFC=EXGOFC.EXGOFCCOD")
				.append(" AND MST.CMPCOD  =EXGOFC.CMPCOD")
				.append(" LEFT OUTER JOIN MALPOADTL POADTL ")
				.append(" ON POADTL.CMPCOD = EXGOFC.CMPCOD ")
				.append(" AND POADTL.POACOD = EXGOFC.POACOD ")
				.append(" AND POADTL.PARCOD ='UPUCOD'")
				.append("AND TRUNC(current_date) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");//A-9092 as a part of IASCB-74752
		//Added for ICRD-133697 ends
		builder.append(" WHERE ");
		//Removed as part of icrd-226771
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
		log.debug( "The CurrentStatus is Returned");
		if (scanFromDate != null) {
			this.setParameter(++index, Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		if (scanToDate != null) {
			this.setParameter(++index, Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
			this.setParameter(++index, Integer.parseInt(year));
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
			String rqdDlvTime=reqDeliveryTime.format(DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
				}else{
					builder.append(" AND MST.REQDLVTIM = ?");
				}
				this.setParameter(++index, Timestamp.valueOf(
						(mailbagEnquiryFilterVO.getReqDeliveryTime().toLocalDateTime())));
			}
		}
		//Added for ICRD-214795 ends
		//Added for IASCB-35785 starts
		if(transportServWindow != null) {
			String transportServWinTime=transportServWindow.format(DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(transportServWinTime!=null){
				if(transportServWinTime.contains("0000")){
					builder.append(" AND TRUNC(MST.TRPSRVENDTIM) = ?");
				}else{
					builder.append(" AND MST.TRPSRVENDTIM = ?");
				}
				this.setParameter(++index,transportServWindow);
			}
		}
//Added for IASCB-35785 ends
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
		return builder.toString();
	}
}
