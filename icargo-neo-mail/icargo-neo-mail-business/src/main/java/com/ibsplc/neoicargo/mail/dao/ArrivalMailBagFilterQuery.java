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
import java.util.Date;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM;
/** 
 * @author a-1936
 */
@Slf4j
public class ArrivalMailBagFilterQuery extends PageableNativeQuery<MailbagVO> {
	private String baseQuery;
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;

	/** 
	* changed by A-5216 to enable last link and total record count for Jira Id: ICRD-21098 and ScreenId MTK009
	*/
	public ArrivalMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			String baseQuery) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), mailbagMapper, PersistenceController.getEntityManager().currentSession());
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
	}

	public ArrivalMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
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
		String mailCategoryCode = mailbagEnquiryFilterVO.getMailCategoryCode();
		String mailSubclass = mailbagEnquiryFilterVO.getMailSubclass();
		String year = mailbagEnquiryFilterVO.getYear();
		String despatchSerialNumber = mailbagEnquiryFilterVO
				.getDespatchSerialNumber();
		String receptacleSerialNumber = mailbagEnquiryFilterVO
				.getReceptacleSerialNumber();
		String currentStatus = mailbagEnquiryFilterVO.getCurrentStatus();
		String scanPort = mailbagEnquiryFilterVO.getScanPort();
		ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
		String damageFlag = mailbagEnquiryFilterVO.getDamageFlag();
		String scanUser = mailbagEnquiryFilterVO.getScanUser();
		String containerNumber = mailbagEnquiryFilterVO.getContainerNumber();
		String flightNumber = mailbagEnquiryFilterVO.getFlightNumber();
		String carrierCode = mailbagEnquiryFilterVO.getCarrierCode();
		ZonedDateTime flightDate = mailbagEnquiryFilterVO.getFlightDate();
		int carrierId = mailbagEnquiryFilterVO.getCarrierId();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		ZonedDateTime reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		ZonedDateTime transportServWindow=mailbagEnquiryFilterVO.getTransportServWindow();
		// Added to determine whether the Cardit is Present For a mailBag or Not
		// ADDED AS A PART OF NCA CR
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
		StringBuilder finalQuery = null;
		StringBuilder builder = new StringBuilder(baseQuery);
		StringBuilder historyBuilder = null;
		StringBuilder flightBuild = null;
		ZonedDateTime flightSqlDate = null;
		String transitJoins = null;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
				.equals(mailbagEnquiryFilterVO.getTransitFlag());
		int index = 0;
		if (flightDate != null) {
			flightSqlDate=flightDate;
		}
		//String flightDateString = String.valueOf(flightSqlDate);
		log.debug( "THE DAMAGE STATUS IS  {} ", damageFlag);
		/*
		 * START ADDED BY RENO K ABRAHAM
		 */
		flightBuild = new StringBuilder();
		if (flightSqlDate != null) {
			flightBuild
					.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.FLTDAT),'YYYYMMDD')) = ?   ");
		}
		if (scanUser != null && scanUser.trim().length() > 0) {
			flightBuild.append(" AND SCNUSR LIKE '%").append(scanUser).append(
					"%'");
		}
		if (flightNumber != null && flightNumber.trim().length() > 0) {
			flightBuild.append("  AND FLTNUM = ? ");
		}
		if (carrierCode != null && carrierCode.trim().length() > 0) {
			flightBuild.append(" AND FLTCARCOD  = ? ");
		}
		if (carrierId > 0) {
			flightBuild.append(" AND FLTCARIDR = ? ");
		}
		String flightBuilder = flightBuild.toString();
		// END
		historyBuilder = new StringBuilder(" INNER JOIN MALHIS HIS ");//A-9092 as a part of IASCB-74752
		historyBuilder.append(" ON MST.MALSEQNUM = HIS.MALSEQNUM AND ").append(
						" MST.CMPCOD = HIS.CMPCOD  AND ").append(" HIS.MALHISIDR = ").append(
						" ( SELECT MAX(MALHISIDR) ").append(" FROM  MALHIS ")
				.append(" WHERE MALSEQNUM=HIS.MALSEQNUM AND ").append(
						" CMPCOD = MST.CMPCOD  ");
		//Added by A-5237 for ICRD-66553 starts
		/*if(currentStatus!=null && currentStatus.trim().length()>0){
			historyBuilder.append("AND MALSTA IN (?)");
		}*/
		//Added by A-5237 for ICRD-66553 ends
		if (!MailConstantsVO.MAIL_STATUS_NOTARRIVED
				.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			if (scanPort != null && scanPort.trim().length() > 0) {
				historyBuilder.append(" AND  SCNPRT = ? ").toString();
			}
		} else {
			historyBuilder.append(" AND  POU = ? ").toString();
		}
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(currentStatus)) {
			historyBuilder.append(" AND MALSTA IN (").append("'").append(MailConstantsVO.MAIL_STATUS_ARRIVED).append("')");
		}
		if (scanFromDate != null) {
			historyBuilder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(SCNDAT),'YYYYMMDD')) >= ? ");
		}
		if (scanToDate != null) {
			historyBuilder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(SCNDAT),'YYYYMMDD')) <= ? ");
		}
		/*
		 * EDIT START ADDED BY RENO K ABRAHAM
		 */
		historyBuilder.append(flightBuilder);
		if (containerNumber != null && containerNumber.trim().length() > 0) {
			historyBuilder.append(" AND CONNUM = ? ");
		}
		// EDIT END
		historyBuilder.append(" )  ") ;
		//A-9092 as a part of IASCB-74752
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			historyBuilder.append(" INNER JOIN MALCDTRCP RCP ");//A-9092 as a part of IASCB-74752
		}
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			historyBuilder.append("  ON MST.CMPCOD = RCP.CMPCOD AND ").append(
					" MST.MALIDR = RCP.RCPIDR AND ");
		}
		if (isTransitFlag) {
			historyBuilder.append(" INNER JOIN SHRCTYMST CTY1  ");//A-9092 as a part of IASCB-74752
			transitJoins = new StringBuilder("ON  CTY1.CMPCOD = MST.CMPCOD ")
					.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ")
					.append(" INNER JOIN SHRCTYMST CTY2 ")
					.append("  ON CTY2.CMPCOD = MST.CMPCOD ")
					.append(
							"  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC)   ")//A-9092 AND removed
					.toString();
		}
		if (isTransitFlag) {
			historyBuilder.append(transitJoins);//A-9092 as a part of IASCB-74752
		}
		historyBuilder.append(" INNER JOIN MALHIS HIS2 ")
				.append(" ON  HIS2.MALSEQNUM = MST.MALSEQNUM ")//A-9092 as a part of IASCB-74752
				.append(" AND  HIS2.CMPCOD = MST.CMPCOD ")
				.append(
						" AND HIS2.MALHISIDR = (SELECT MAX (MALHISIDR)  FROM MALHIS ")
				.append(" WHERE CMPCOD = MST.CMPCOD ")
				.append(" AND MALSEQNUM = MST.MALSEQNUM ")
				.append(
						" AND MALSTA NOT IN ('74','6','24','48','21','41','42','43','82','57') )  ");//A-9092 as a part of IASCB-74752
		if(!"ARR".equals(currentStatus)){
			historyBuilder.append(" LEFT OUTER JOIN MALARPULDDTL ARPULD")
					.append(" ON MST.MALSEQNUM = ARPULD.MALSEQNUM")
					.append(" AND MST.CMPCOD = ARPULD.CMPCOD")
					.append(" AND MST.FLTCARIDR = ARPULD.FLTCARIDR")
					//added for icrd-92604
					.append(" AND MST.SCNPRT    = ARPULD.ARPCOD")
					.append(" AND MST.CONNUM      = ARPULD.CONNUM ");
			//ADDED by A-8527 for IASCB-52766 starts
			if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)){
				historyBuilder.append(" LEFT OUTER JOIN MALULDSEGDTL SEG")
						.append("  ON MST.CMPCOD=SEG.CMPCOD AND ")
						.append(" MST.MALSEQNUM=SEG.MALSEQNUM")
						.append(" AND SEG.DLVSTA = 'Y'  ");
			}
			//ADDED by A-8527 for IASCB-52766 Ends
		}
		//Added for ICRD-133697 starts
		if(upuCode != null && upuCode.trim().length() >0){
			historyBuilder.append(" LEFT OUTER JOIN MALPOADTL POADTL ")
					.append(" ON POADTL.CMPCOD = MST.CMPCOD ")
					.append(" AND POADTL.POACOD = MST.POACOD ")
					.append(" AND POADTL.PARCOD ='UPUCOD'")
					.append("AND TRUNC(current_date) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");
		}
		//Added for ICRD-133697 ends
		//Modified filter query by A-7794 as part of ICRD-197479
		String history="";
		//ADDED by A-8527 for IASCB-52766 starts
		if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)){
			history = historyBuilder.append("  WHERE  ").toString();
		}
		else{
			//ADDED by A-8527 for IASCB-52766 Ends
			history = historyBuilder.append("  ").toString();
		}
		/*
		 * This part will be appended to the Base Query when flightDetails not
		 * Present AND destination not present..
		 *
		 */
		finalQuery = new StringBuilder().append(
						",MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM,")
				.append("SEG.ACPSTA,SEG.ARRSTA, SEG.TRAFLG ,SEG.FRMCARCOD")
				.append(",HIS2.FLTCARCOD, HIS2.FLTDAT, (SELECT MAX(POAFLG) FROM MALFLTCON CONMST WHERE MST.FLTCARIDR = CONMST.FLTCARIDR")//A-9092 Added MAX()
				.append(" AND MST.FLTNUM  = CONMST.FLTNUM AND MST.FLTSEQNUM   = CONMST.FLTSEQNUM AND MST.CONNUM = CONMST.CONNUM")
				.append(" AND MST.CMPCOD = CONMST.CMPCOD) AS POAFLG, ")//A-9092 as a part of IASCB-74752
				.append("(SELECT POL ")
				.append("FROM MALFLTSEG SEG")
				.append(" WHERE MST.FLTCARIDR = SEG.FLTCARIDR")
				.append(" AND MST.FLTNUM      = SEG.FLTNUM")
				.append(" AND MST.FLTSEQNUM   = SEG.FLTSEQNUM")
				.append(" AND MST.SEGSERNUM   = SEG.SEGSERNUM")
				.append(" AND MST.CMPCOD      = SEG.CMPCOD")
				.append(") AS POL,");
		if(!"ARR".equals(currentStatus))
		{
			finalQuery.append(" ARPULD.CONNUM AS INVCONNUM,ARPULD.ULDNUM AS INVULDNUM ");
		}
		else
		{
			finalQuery.append(" NULL INVCONNUM, NULL INVULDNUM ");
		}
		//ADDED by A-8527 for IASCB-52766 starts
		if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)){
			finalQuery.append("  FROM  ").append(" MALMST MST ").append(history);//A-9092 as a part of IASCB-74752
		}
		else{
			//ADDED by A-8527 for IASCB-52766 Ends
			finalQuery.append(" FROM MALMST MST ").append(history)
					.append(" INNER JOIN MALULDSEGDTL SEG ")
					.append(" ON MST.CMPCOD=SEG.CMPCOD AND ").append(
							" MST.MALSEQNUM=SEG.MALSEQNUM");
		}
		/*
		 *
		 * Need Not append the Flight Details which will cause issues when the
		 * Delivery is done from the Inventory
		 *
		 *
		 */
		if (!MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)) {
			finalQuery.append(" AND HIS.FLTCARIDR=SEG.FLTCARIDR AND ").append(
					" HIS.FLTNUM=SEG.FLTNUM AND ").append(
					" HIS.FLTSEQNUM=SEG.FLTSEQNUM AND ").append(
					" HIS.SEGSERNUM=SEG.SEGSERNUM  AND ").append(
					" HIS.CONNUM=SEG.ULDNUM   WHERE");//Modified for ICRD-156462 //A-9092 as a part of IASCB-74752
		}
		// for carrier code and flight date
		builder.append(finalQuery);//A-9092 as a part of IASCB-74752
		/*if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(currentStatus)) {
			log.log(Log.INFO, "THE STATUS IS ARRIVED ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
		} else if (MailConstantsVO.MAIL_STATUS_NOTARRIVED.equals(currentStatus)) {
			log.log(Log.INFO, "THE STATUS IS NOT ARRIVED ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ACCEPTED);
		} else if (MailConstantsVO.MAIL_STATUS_NOTDELIVERED
				.equals(currentStatus)) {
			log.log(Log.INFO, "THE STATUS IS NOT DELIVERED ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)) {
			log.log(Log.INFO, "THE STATUS IS DELIVERED ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_DELIVERED);
		}*/
		if (scanPort != null && scanPort.trim().length() > 0) {
			this.setParameter(++index, scanPort);
		}
		if (scanFromDate != null) {
			this.setParameter(++index, Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		if (scanToDate != null) {
			this.setParameter(++index, Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		/*
		 * START ADDED BY RENO K ABRAHAM
		 */
		if (flightSqlDate != null) {
			this.setParameter(++index, Integer.parseInt(flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		if (flightNumber != null && flightNumber.trim().length() > 0) {
			this.setParameter(++index, flightNumber);
		}
		if (carrierCode != null && carrierCode.trim().length() > 0) {
			this.setParameter(++index, carrierCode);
		}
		if (carrierId > 0) {
			this.setParameter(++index, carrierId);
		}
		if (containerNumber != null && containerNumber.trim().length() > 0) {
			this.setParameter(++index, containerNumber);
		}
		// END
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
			this.setParameter(++index, Integer.parseInt(year));//A-9092 as a part of IASCB-74752
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
		if (mailbagId != null
				&& mailbagId.trim().length() > 0) {
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
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(currentStatus)) {
			/**Modified by A-4809, arrived status on screen should list
			 all mailbags which were arrived in the port even though the current mail status is different
			 mail status shown will be the latest status*/
/*			builder.append(" AND MST.MALSTA=? ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);*/
			builder.append(" AND SEG.ARRSTA = 'Y'  ");
/*			if(scanPort!=null){
				builder.append(" AND MST.SCNPRT = ? ");
				this.setParameter(++index, scanPort);
			}*/
		} else if (MailConstantsVO.MAIL_STATUS_NOTARRIVED.equals(currentStatus)) {
			builder.append(" AND SEG.ACPSTA = 'Y'   ");
			builder
					.append(" AND ( SEG.ARRSTA  = 'N'  OR  SEG.ARRSTA IS NULL ) ");
			builder.append(" AND MST.MALSTA = 'ACP' ");
		} else if (MailConstantsVO.MAIL_STATUS_NOTDELIVERED
				.equals(currentStatus)) {
			/*
			 * Note: In MTKMALULDSEG a Mail Bag will be marked as
			 * Acceppted(ACPSTA='Y') Only if it is Pure Accepted MailTag (i.e)
			 * MailTag is not found as the Part Of Found Shipments... If it is a
			 * part of Found Shipment then ACPSTA is NULL in MTKMALULDSEG
			 *
			 */
			builder.append(" AND SEG.ARRSTA = 'Y'  ");
			builder.append(" AND ( SEG.DLVSTA = 'N' OR SEG.DLVSTA IS NULL )  ");
			builder.append(" AND MST.MALSTA <> ?  ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_DELIVERED);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(currentStatus)) {
			log.debug("THE STATUS IS DELIVERED");
			/*
			 * Note: In MTKMALULDSEG a Mail Bag will be marked as
			 * Acceppted(ACPSTA='Y') Only if it is Pure Accepted MailTag (i.e)
			 * MailTag is not found as the Part Of Found Shipments... If it is a
			 * part of Found Shipment then ACPSTA is NULL in MTKMALULDSEG
			 *
			 */
			//builder.append(" AND SEG.DLVSTA = 'Y'  ");
			builder.append(" AND MST.MALSTA =  ?  ");
			builder.append(" AND MST.SCNPRT =  ?  ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_DELIVERED);
			this.setParameter(++index, scanPort);
			//ADDED by A-8527 for IASCB-52766 starts
			if (flightNumber != null && flightNumber.trim().length() > 0) {
				builder.append(" AND MST.FLTNUM =  ?  ");
				this.setParameter(++index, flightNumber);
			}
			//ADDED by A-8527 for IASCB-52766 Ends
			if (carrierCode != null && carrierCode.trim().length() > 0) {
				builder.append(" AND HIS.FLTCARCOD  = ? ");
				this.setParameter(++index, carrierCode);
			}
		}
		if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
			builder.append(" AND MST.DMGFLG = ? ");
			this.setParameter(++index, damageFlag);
		}
		/*
		 * Commented by Reno K Abraham
		 *
		 * if (flightSqlDate != null) { builder .append(" AND TRUNC(HIS.FLTDAT) =
		 * to_date(?, 'yyyy-MM-dd') "); this.setParameter(++index,
		 * flightDateString); }
		 *
		 * if (scanUser != null && scanUser.trim().length() > 0) {
		 * builder.append(" AND MST.SCNUSR LIKE '%").append(scanUser).append(
		 * "%'"); }
		 *
		 * if (containerNumber != null && containerNumber.trim().length() > 0) {
		 * builder.append(" AND MST.CONNUM = ? "); this.setParameter(++index,
		 * containerNumber); }
		 *
		 * if (flightNumber != null && flightNumber.trim().length() > 0) {
		 * builder.append(" AND HIS.FLTNUM = ? "); this.setParameter(++index,
		 * flightNumber); }
		 *
		 * if (carrierId > 0) { builder.append(" AND HIS.FLTCARIDR = ? ");
		 * this.setParameter(++index, carrierId); }
		 */
		/*
		 * Added By Paulson as the part of the NCA Mail Trackig CR
		 *
		 *
		 */
		if (mailbagEnquiryFilterVO.getFromCarrier() != null
				&& mailbagEnquiryFilterVO.getFromCarrier().trim().length() > 0) {
			builder.append(" AND SEG.FRMCARCOD = ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getFromCarrier());
		}
		// Added to Fetch all the MailBags For which the Cardit has not been
		// Received ..
		//Modified filter query by A-7794 as part of ICRD-197479
		if (carditPresent != null
				&& MailConstantsVO.FLAG_NO.equals(carditPresent)) {
			builder.append("  AND  NOT EXISTS  ");
			builder.append(" ( SELECT 1 FROM MALCDTRCP CDTRCP WHERE  ");
			builder.append("  CDTRCP.CMPCOD =  MST.CMPCOD AND ");
			builder.append("  CDTRCP.RCPIDR =  MST.MALIDR ) ");
		}
		if (isTransitFlag) {
			builder.append(" AND CTY1.SRVARPCOD <> ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			builder.append(" AND CTY2.SRVARPCOD <> ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
		}
		//added by a-7779 for ICRD-342042 starts
		if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null && mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
			builder.append("  AND MST.ONNTIMDLVFLG = ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
		}
		//added by a-7779 for ICRD-342042 ends
		return builder.toString();
	}
}
