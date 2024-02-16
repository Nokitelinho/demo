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
 * @author a-1936 Karthick V This Filter Query is used to append the QueryDynamically in case of the Status Being the Assigned ,Accepted and the Transferred.. Modified By Karthick V as the part of the NCA Mail Tracking CR..
 */
@Slf4j
public class AcceptedMailBagFilterQuery extends PageableNativeQuery<MailbagVO> {
	/** 
	* The base Query
	*/
	private String baseQuery;
	private static boolean isRank;
	public static final String rankQuery = "SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY RESULT_TABLE.CMPCOD, RESULT_TABLE.MALIDR, RESULT_TABLE.ORGEXGOFC, RESULT_TABLE.DSTEXGOFC,RESULT_TABLE.YER, RESULT_TABLE.DSN, RESULT_TABLE.MALCLS, RESULT_TABLE.MALCTGCOD) RANK FROM ("
			.toString();
	public static final String suffixQuery = ") ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN )".toString();
	/** 
	* The mailbagEnquiryFilterVO
	*/
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;
	public static boolean isRank() {
		return isRank;
	}
	public static void setRank(boolean isRank) {
		AcceptedMailBagFilterQuery.isRank = isRank;
	}

	/** 
	* changed by A-5216 to enable last link and total record count for Jira Id: ICRD-21098 and ScreenId MTK009
	*/
	public AcceptedMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			String baseQuery) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), mailbagMapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public AcceptedMailBagFilterQuery(MailbagMapper mailbagMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			String baseQuery, int pageSize) {
		super(pageSize, -1, mailbagMapper,PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}
	public String getNativeQuery() {
		log.debug("Inside the AcceptedMailBagFilterQuery getNativeQuery()");
		log.debug("THE  LATEST ACCEPTED MAIL BAG ENQUIRY FILTER QUERY");
		String companyCode = mailbagEnquiryFilterVO.getCompanyCode();
		String ooe = mailbagEnquiryFilterVO.getOoe();
		String doe = mailbagEnquiryFilterVO.getDoe();
		String originAirportCode = mailbagEnquiryFilterVO.getOriginAirportCode();
		String destinationAirportCode =  mailbagEnquiryFilterVO.getDestinationAirportCode();
		String mailCategoryCode = mailbagEnquiryFilterVO.getMailCategoryCode();
		String mailSubclass = mailbagEnquiryFilterVO.getMailSubclass();
		String year = mailbagEnquiryFilterVO.getYear();
		String despatchSerialNumber = mailbagEnquiryFilterVO
				.getDespatchSerialNumber();
		String receptacleSerialNumber = mailbagEnquiryFilterVO
				.getReceptacleSerialNumber();
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
		String fromCarrierCode = mailbagEnquiryFilterVO.getFromCarrier();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		// Added to determine whether the Cardit is Present For a mailBag or Not
		// ADDED AS A PART OF NCA CR
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();//Added for ICRD-205027
		ZonedDateTime reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		ZonedDateTime transportServWindow=mailbagEnquiryFilterVO.getTransportServWindow();
		StringBuilder builder = new StringBuilder(baseQuery);
		StringBuilder finalQuery = null;
		StringBuilder destinationBuilder = null;
		StringBuilder historyBuilder = null;
		StringBuilder flightBuild=null;
		boolean isFlight = true;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
				.equals(mailbagEnquiryFilterVO.getTransitFlag());
		boolean isUnion = false;
		ZonedDateTime flightSqlDate = null;
		int index = 0;
		log.debug( "The Damaged Status is {}", damageFlag);
		if (flightDate != null) {
			flightSqlDate=flightDate;
		}
		//String flightDateString = String.valueOf(flightSqlDate);
		boolean fromExportList=false;
		if("exportlist".equals(mailbagEnquiryFilterVO.getFromExportList())){
			fromExportList=true;
		}
		/*
		 *
		 * 1.The different StringBuilder instances created Below will be
		 * Dynamically appended to the baseQuery based on the Filter Conditions
		 * along with the Flight\Destibation Option)
		 *
		 * 2.This String Builder Instance Contains the join conditions of the
		 * MailMaster and History say MTKMALMST and MTKMALHIS ....This has to be
		 * appended to the Base Query For all Status Other than Returned Since
		 * wen say that the mailBag has been Returned it is returned to Postal
		 * Authority and not kept in our System..
		 *
		 * 3.Also checks whether the Cardit Status is Y or N if Y fetch all
		 * those mailBags for which CARDITS has been Received(Implemented by
		 * Join of MTKMALMST and MTKCDTRCP) if N fetch all those mailBags for
		 * which CARDITS has not been Received( Implemented by NOT EXISTS sub
		 * Query) Note : When the carditStatus is All all mailBags irrespective
		 * of their Cardit Status has to be selected ..
		 */
		/*START
		 * ADDED BY RENO K ABRAHAM
		 */
		flightBuild=new StringBuilder();
		if (flightSqlDate != null) {
			flightBuild
					.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.FLTDAT),'YYYYMMDD')) = ?  ");
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
		//END
		historyBuilder = new StringBuilder("INNER JOIN MALHIS HIS");//A-9092 as a part of IASCB-74752
		historyBuilder.append(" ON ");
		historyBuilder.append(" MST.MALSEQNUM = HIS.MALSEQNUM AND ").append(
				" MST.CMPCOD = HIS.CMPCOD  AND ").append(" HIS.MALHISIDR = ").append(
				" ( SELECT MAX(MALHISIDR) ").append(
				" FROM MALHIS WHERE MALSEQNUM=HIS.MALSEQNUM AND").append(
				" CMPCOD = MST.CMPCOD AND MALSTA IN (? ");
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(//Changed by A-8164 for ICRD-343119
				mailbagEnquiryFilterVO.getCurrentStatus())) {
			historyBuilder.append(" , ? , ?  ");
		}
		historyBuilder.append(" ) ");
		if (scanPort != null && scanPort.trim().length() > 0) {
			historyBuilder.append(" AND  SCNPRT = ? ").toString();
		}
		//if (scanFromDate != null) {
		//	historyBuilder
		//			.append(" AND SCNDAT >= ? ");
		//}
		//if (scanToDate != null) {
		//	historyBuilder
		//			.append(" AND SCNDAT <= ? ");
		//}
		/* START
		 * ADDED BY RENO K ABRAHAM
		 */
		historyBuilder.append(flightBuilder);
		if (containerNumber != null && containerNumber.trim().length() > 0) {
			historyBuilder.append(" AND CONNUM = ? ");
		}
		//END
		///A-9092 as a part of IASCB-74752
		String history = historyBuilder.append(" )").toString();
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			historyBuilder.append(" INNER JOIN MALCDTRCP RCP ");
		}
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
			historyBuilder.append(" ON MST.CMPCOD = RCP.CMPCOD AND ").append(
					" MST.MALIDR = RCP.RCPIDR ");
		}
		/*
		 * This part will be appended to the base Query when the 1.Flight
		 * Details are not Present and 2.The Destination Details are not
		 * present.
		 *
		 */
		finalQuery =
				new StringBuilder()
						.append(
								",CONMST.POAFLG,CONMST.LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM,")
						.append("SEG.ACPSTA,SEG.ARRSTA, SEG.TRAFLG ")
						.append(",HIS.FLTCARCOD, TO_CHAR(HIS.FLTDAT,'ddMMyyyy'),SEG.FRMCARCOD ")
						.append("  FROM  MALMST MST ")//A-9092 as a part of IASCB-74752
						.append(history)
						.append("INNER JOIN MALULDSEGDTL SEG")
						.append(" ON HIS.CMPCOD=SEG.CMPCOD AND ")
						.append(" HIS.MALSEQNUM=SEG.MALSEQNUM AND ")
						.append(" HIS.MALSEQNUM=SEG.MALSEQNUM AND ")
						.append(" HIS.FLTCARIDR=SEG.FLTCARIDR AND ")
						.append(" HIS.FLTNUM=SEG.FLTNUM AND ")
						.append(" HIS.FLTSEQNUM=SEG.FLTSEQNUM AND ")
						.append(" HIS.SEGSERNUM=SEG.SEGSERNUM  AND ")
						.append(" HIS.CONNUM=SEG.CONNUM ")
						.append("INNER JOIN MALFLTCON CONMST")
						.append(" ON SEG.CMPCOD=CONMST.CMPCOD ")
						.append(" AND SEG.CONNUM = CONMST.CONNUM ")
						.append(" AND SEG.FLTCARIDR = CONMST.FLTCARIDR ")
						.append(" AND SEG.FLTNUM= CONMST.FLTNUM ")
						.append(" AND SEG.FLTSEQNUM= CONMST.FLTSEQNUM ")
						.append(" AND SEG.SEGSERNUM= CONMST.SEGSERNUM   ")
						.append("INNER JOIN MALEXGOFCMST EXGOFC")
						.append(" ON MST.CMPCOD= EXGOFC.CMPCOD")
						.append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
						.append(" LEFT OUTER JOIN MALPOADTL POADTL")
						.append(" ON EXGOFC.CMPCOD= POADTL.CMPCOD ")
						.append(" AND EXGOFC.POACOD= POADTL.POACOD ")
						.append(" AND POADTL.PARCOD ='UPUCOD' AND ")
						.append(" TRUNC(current_date) BETWEEN TRUNC(POADTL.VLDFRM) AND TRUNC(POADTL.VLDTOO) WHERE ");//A-9092
		/*
		 * If the status is being ACCEPTED/Assigned /Transffered and destination
		 * Details are given.. Query to be appended in case of ACP+Destination.
		 */
		if ((carrierId > 0 || carrierId == 0)
				&& (flightNumber == null || flightNumber.trim().length() == 0)) {
			log.debug( "Constructing Destination Builder");
			destinationBuilder = new StringBuilder();
			if (carrierId == 0) {
				isUnion = true;
			}
			/*
			 * Note:- In case of the status Being Transfered and Flight Present
			 * then all the MailBags Transferred To that Flight has to be
			 * Displayed..
			 *
			 * In case of the status Being Transfered and Other Carrier Present
			 * then all the MailBags Transferred To that Carrier has to be
			 * Displayed..
			 *
			 * If no Flight and no Other Carrier Details are Present then the
			 * Union of both the Transferred to Flight and the Carrier Details
			 * has to be Dispalyed ..
			 *
			 * Note:- 1. While Querying for the Transfer to Carrier there is no
			 * need to include the MALARPULD as we are under the assumption that
			 * we wonty make an Outbound Carrier Assignment for all Mail Bags
			 * that has been Transfered to other Carriers ..
			 *
			 * 2. The carrier Code specified will always be the Other Carrier
			 * ...
			 */
			if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				if (isUnion) {
					destinationBuilder.append(this.baseQuery);
				}
				destinationBuilder
						.append(", '' POAFLG, 0 LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM");
				if (carrierId > 0) {
					destinationBuilder.append(",HIS.FLTCARCOD");
				}
				if (isUnion) {
					destinationBuilder.append(
									" , '' ACPSTA , '' ARRSTA, '' TRAFLG ")
							.append( ",( SELECT ( CASE WHEN APHCODUSE = 2 THEN TWOAPHCOD ELSE THRAPHCOD END )  FROM SHRARLMST  WHERE CMPCOD = HIS.CMPCOD  AND ARLIDR = HIS.FLTCARIDR) FLTCARCOD")
							.append(", NULL,  '' FRMCARCOD ");
				}
				destinationBuilder.append("  FROM  MALMST MST ").append(
						history).append(" AND (HIS.FLTNUM IS NULL or HIS.FLTNUM = '-1')  AND ");//A-9092 as a part of IASCB-74752
				destinationBuilder.append(" MST.SCNPRT = HIS.SCNPRT  ")
						.append("INNER JOIN MALEXGOFCMST EXGOFC")
						.append(" ON MST.CMPCOD= EXGOFC.CMPCOD")
						.append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
						.append(" LEFT OUTER JOIN MALPOADTL POADTL")
						.append(" ON EXGOFC.CMPCOD= POADTL.CMPCOD ")
						.append(" AND EXGOFC.POACOD= POADTL.POACOD ")
						.append("AND POADTL.PARCOD ='UPUCOD' AND ")
						.append(" TRUNC(current_date) BETWEEN TRUNC(POADTL.VLDFRM) AND TRUNC(POADTL.VLDTOO) WHERE ");
			} else {
				if (isUnion) {
					destinationBuilder.append(this.baseQuery);
				}
				destinationBuilder
						.append(",CONMST.POAFLG,CONMST.LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM");
				if (isUnion) {
					destinationBuilder.append(
							" ,'' ACPSTA,'' ARRSTA,'' TRAFLG ").append(
							" ,COALESCE(ULD.FLTCARCOD,HIS.FLTCARCOD)FLTCARCOD, NULL,ARPULD.FRMCARCOD ");
				}
				destinationBuilder
//						.append(
//								" FROM  MALMST MST,MALARPULDDTL ARPULD, MALARPULD ULD,MALFLTCON CONMST ")
//						.append(history)
//						.append(" HIS.CMPCOD=ARPULD.CMPCOD ")
//						.append(" AND HIS.MALSEQNUM=ARPULD.MALSEQNUM ")
//						.append(" AND HIS.CONNUM=ARPULD.CONNUM ")
//						.append(" AND HIS.SCNPRT= ARPULD.ARPCOD ")
//						.append(" AND HIS.FLTCARIDR=ARPULD.FLTCARIDR ")
//						.append(" AND HIS.FLTCARIDR = ULD.FLTCARIDR ")
//						.append(" AND HIS.SCNPRT = ULD.ARPCOD ")
//						.append(" AND HIS.CMPCOD = ULD.CMPCOD ")
//						.append(" AND ULD.ULDNUM = ARPULD.ULDNUM ")
//						.append(" AND ULD.CMPCOD=CONMST.CMPCOD ")
//						.append(" AND ULD.FLTCARIDR = CONMST.FLTCARIDR ")
//						.append(" AND ULD.ARPCOD= CONMST.ASGPRT ")
//						.append(" AND HIS.CONNUM = CONMST.CONNUM  AND")
//						//.append(" AND MST.FLTNUM = '-1'  AND ")
//				        .append(" CONMST.FLTNUM = '-1'  AND ")
//				        .append(" MST.CMPCOD= EXGOFC.CMPCOD")
//				        .append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
//				        .append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
//				        .append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
//				        .append("AND POADTL.PARCOD(+) ='UPUCOD' AND ")
//				         .append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ")
						.append("FROM MALMST MST INNER JOIN MALHIS HIS ON MST.MALSEQNUM  = HIS.MALSEQNUM AND MST.CMPCOD    = HIS.CMPCOD AND HIS.MALHISIDR = (SELECT MAX(MALHISIDR)  FROM MALHIS WHERE MALSEQNUM=HIS.MALSEQNUM AND CMPCOD     = MST.CMPCOD")
						.append( " AND MALSTA    IN ( ")
						.append("? ");
				if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
						mailbagEnquiryFilterVO.getCurrentStatus())) {
					destinationBuilder.append(" , ? , ?  ");
				}
				destinationBuilder.append(" ) ");
				if (scanPort != null && scanPort.trim().length() > 0) {
					destinationBuilder.append(" AND  SCNPRT = ? ");
				}
				if (containerNumber != null && containerNumber.trim().length() > 0) {
					destinationBuilder.append(" AND CONNUM = ? ");
				}
				if (flightSqlDate != null) {
					destinationBuilder
							.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.FLTDAT),'YYYYMMDD')) = ? ");
				}
				if (scanUser != null && scanUser.trim().length() > 0) {
					destinationBuilder.append(" AND SCNUSR LIKE '%").append(scanUser).append(
							"%'");
				}
				if (flightNumber != null && flightNumber.trim().length() > 0) {
					destinationBuilder.append("  AND FLTNUM = ? ");
				}
				if (carrierCode != null && carrierCode.trim().length() > 0) {
					destinationBuilder.append(" AND FLTCARCOD  = ? ");
				}
				if (carrierId > 0) {
					destinationBuilder.append(" AND FLTCARIDR = ? ");
				}
				destinationBuilder.append(" ) ");
				if (carditPresent != null
						&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
					destinationBuilder.append(" INNER JOIN MALCDTRCP RCP ON MST.CMPCOD = RCP.CMPCOD AND MST.MALIDR = RCP.RCPIDR ");
				}
				destinationBuilder.append("LEFT OUTER JOIN MALARPULDDTL ARPULD ON HIS.CMPCOD     =ARPULD.CMPCOD AND HIS.MALSEQNUM =ARPULD.MALSEQNUM AND HIS.CONNUM    =ARPULD.CONNUM AND HIS.SCNPRT    = ARPULD.ARPCOD ")
						.append("AND HIS.FLTCARIDR =ARPULD.FLTCARIDR LEFT OUTER JOIN MALARPULD ULD ON HIS.FLTCARIDR = ULD.FLTCARIDR AND HIS.SCNPRT   = ULD.ARPCOD AND HIS.CMPCOD   = ULD.CMPCOD AND ULD.ULDNUM   = ARPULD.ULDNUM LEFT OUTER JOIN MALFLTCON CONMST ")
						.append("ON ULD.CMPCOD     =CONMST.CMPCOD AND ULD.FLTCARIDR = CONMST.FLTCARIDR AND ULD.ARPCOD    = CONMST.ASGPRT AND HIS.CONNUM    = CONMST.CONNUM AND CONMST.FLTNUM = '-1' ")
						.append("INNER JOIN MALEXGOFCMST EXGOFC  ON MST.CMPCOD     = EXGOFC.CMPCOD AND MST.ORGEXGOFC = EXGOFC.EXGOFCCOD LEFT OUTER JOIN MALPOADTL POADTL ON EXGOFC.CMPCOD  = POADTL.CMPCOD ")
						.append("AND EXGOFC.POACOD = POADTL.POACOD AND POADTL.PARCOD ='UPUCOD' AND TRUNC(CURRENT_DATE) BETWEEN TRUNC(POADTL.VLDFRM) AND TRUNC(POADTL.VLDTOO) ")
						.append("WHERE (ARPULD.CMPCOD IS NOT NULL OR MST.MALSTA='ACP') AND");
			}
		}
		/*
		 * The status is Accepted and if Filter 1.contains Flight- Take Flight
		 * Accepted MailBags. 2.contains Destination -Take Destination Accepted
		 * MailBags. 3.No Flight and Destination - A Union of both Flight
		 * Accepted and Destination Accepted MailBags has to be taken ..
		 *
		 */
		if (carrierId > 0 && flightNumber != null
				&& flightNumber.trim().length() > 0 && flightDate != null) {
			log.debug( "FLIGHT ACCEPTED");
			builder.append(finalQuery);
		} else if (carrierId > 0
				&& (flightNumber == null || flightNumber.trim().length() == 0)) {
			log.debug( "DESTINATION ACCEPTED");
			builder.append(destinationBuilder);
			isFlight = false;
		} else {
			log.debug( " BOTH FLIGHT AND DESTINATION ACCEPTED");
			builder.append(finalQuery);
		}
	/*	if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(//Added by A-8164 for ICRD-343119
				 mailbagEnquiryFilterVO.getCurrentStatus()) && "MAIL_OUTBOUND".equals(mailbagEnquiryFilterVO.getFromScreen())){
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ACCEPTED);
		}*/
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
				mailbagEnquiryFilterVO.getCurrentStatus())) {
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ASSIGNED);
			if(fromExportList&& containerNumber.startsWith("OFL")){
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_OFFLOADED);
			}
			else{
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			}
		}else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(
				mailbagEnquiryFilterVO.getCurrentStatus())) {
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		}
		if (scanPort != null && scanPort.trim().length() > 0) {
			this.setParameter(++index, scanPort);
		}
		//if (scanFromDate != null) {
		//	this.setParameter(++index, scanFromDate);
		//}
		//if (scanToDate != null) {
		//	this.setParameter(++index, scanToDate);
		//}
		/*START
		 * ADDED BY RENO K ABRAHAM
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
		//END
		if (companyCode != null && companyCode.trim().length() > 0) {
			builder.append("  MST.CMPCOD=? ");
			this.setParameter(++index, companyCode);
		}
		if (scanFromDate != null) {
			builder.append(" AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) >= ? ");
			this.setParameter(++index, Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		if (scanToDate != null) {
			builder.append("  AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) <= ? ");
			this.setParameter(++index, Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
		//Added by A-8164 for ICRD-343119
		/*if(scanPort != null && "MAIL_OUTBOUND".equals(mailbagEnquiryFilterVO.getFromScreen())
				&& scanPort.trim().length() > 0){
			builder.append("   AND MST.SCNPRT=? ");
			this.setParameter(++index, scanPort);
		}*/
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
		//added by a-7779 for ICRD-342042 starts
		if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null && mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
			builder.append("  AND MST.ONNTIMDLVFLG = ? ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
		}
		//added by a-7779 for ICRD-342042 ends
		if (isFlight) {
			log.debug( "THE STATUS IS MAIL_STATUS_ACCEPTED");
			builder.append(" AND SEG.ACPSTA =  'Y'   ");
			/*
			 * Added By Karthick V as the part  of the  ANZ Mail Tracking CR
			 * Actually there is no need to show the Mailbags with the status Arrived and
			 * Delivered when the filter is for the Axp ..
			 */
			//Commented for ICRD-233262
			//builder.append(" AND SEG.ARRSTA <> 'Y'   ");
			//builder.append(" AND SEG.DLVSTA <> 'Y'   ");
			// Added by A-6991 for ICRD-210494
			if(this.mailbagEnquiryFilterVO.getFromScreen()!=null && "MAILEXPORTLIST".equals(this.mailbagEnquiryFilterVO.getFromScreen())){
				builder.append(" AND CONMST.CONNUM=?   ");
				this.setParameter(++index, containerNumber);
			}
		}
		if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
			builder.append(" AND MST.DMGFLG = ? ");
			this.setParameter(++index, damageFlag);
		}
		/*	Commented by Reno K Abraham
		if (flightSqlDate != null) {
			builder
					.append(" AND TO_NUMBER(TO_CHAR(TRUNC(HIS.FLTDAT),'YYYYMMDD')) = ?  ");
			this.setParameter(++index, Integer.parseInt(flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		}
		if (scanUser != null && scanUser.trim().length() > 0) {
			builder.append(" AND MST.SCNUSR LIKE '%").append(scanUser).append(
					"%'");
		}
		if (containerNumber != null && containerNumber.trim().length() > 0) {
			builder.append(" AND HIS.CONNUM = ? ");
			this.setParameter(++index, containerNumber);
		}
		if (flightNumber != null && flightNumber.trim().length() > 0) {
			builder.append("  AND HIS.FLTNUM = ? ");
			this.setParameter(++index, flightNumber);
		}
		if (carrierId > 0) {
			builder.append(" AND HIS.FLTCARIDR = ? ");
			this.setParameter(++index, carrierId);
		}*/
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
			builder.append("  AND   EXISTS  ");
			builder.append(" ( SELECT 1 FROM SHRCTYMST CTY1 WHERE  ");
			builder.append("  CTY1.CMPCOD = MST.CMPCOD ");
			builder.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ");
			builder.append("  AND ( CTY1 .SRVARPCOD IS NULL OR  CTY1 .SRVARPCOD  <>  ? )   ) ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			builder.append("  AND   EXISTS  ");
			builder.append(" ( SELECT 1 FROM  SHRCTYMST CTY2  WHERE  ");
			builder.append("  CTY2.CMPCOD = MST.CMPCOD ");
			builder.append("  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) ");
			builder.append("  AND (  CTY2 .SRVARPCOD IS NULL OR  CTY2 .SRVARPCOD  <>  ?  ) )  ");
			this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
		}
		/*
		 * Added By Paulson As the part of the NCA Mail Tracking CR
		 *
		 *
		 */
		if (isFlight || isUnion) {
			if (fromCarrierCode != null && fromCarrierCode.trim().length() > 0) {
				builder.append(" AND SEG.FRMCARCOD = ? ");
				this.setParameter(++index, fromCarrierCode);
			}
		} else {
			if (fromCarrierCode != null
					&& fromCarrierCode.trim().length() > 0
					&& !MailConstantsVO.MAIL_STATUS_TRANSFERRED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				builder.append(" AND ARPULD.FRMCARCOD = ? ");
				this.setParameter(++index, fromCarrierCode);
			}
		}
		/*
		 * Enters here when the Status is Accepted + No Flight or Destination
		 * Given In this case a UNION of FlightDetails+Destination Details has
		 * to be shown.. Repeat the whole set of the FilterConditions so that
		 * the filterConditions will be provided for the Query for the
		 * Destination Part also where Union is Required
		 *
		 */
		if (isUnion) {
			if(!AcceptedMailBagFilterQuery.isRank()){
				StringBuilder denseRank=new StringBuilder(AcceptedMailBagFilterQuery.rankQuery);
				builder=denseRank.append(builder);
				setRank(true);
			}
			builder.append(" UNION ").append("(");
			builder.append(destinationBuilder);
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
					mailbagEnquiryFilterVO.getCurrentStatus())) {
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ACCEPTED );
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ASSIGNED );
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_OFFLOADED );
			}else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(
					mailbagEnquiryFilterVO.getCurrentStatus())) {
				this.setParameter(++index, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			}
			if (scanPort != null && scanPort.trim().length() > 0) {
				this.setParameter(++index, scanPort);
			}
			//	if (scanFromDate != null) {
			//		this.setParameter(++index, scanFromDate);
			//	}
			//	if (scanToDate != null) {
			//		this.setParameter(++index, scanToDate);
			//	}
			/*START
			 * ADDED BY RENO K ABRAHAM
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
			//END
			if (companyCode != null && companyCode.trim().length() > 0) {
				builder.append("  MST.CMPCOD=? ");
				this.setParameter(++index, companyCode);
			}
			if (ooe != null && ooe.trim().length() > 0) {
				builder.append("  AND MST.ORGEXGOFC=? ");
				this.setParameter(++index, ooe);
			}
			if (scanFromDate != null) {
				builder.append(" AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) >= ? ");
				this.setParameter(++index, Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
			}
			if (scanToDate != null) {
				builder.append("  AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) <= ? ");
				this.setParameter(++index, Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
			if (mailCategoryCode != null
					&& mailCategoryCode.trim().length() > 0) {
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
					this.setParameter(++index,Timestamp.valueOf(
							(mailbagEnquiryFilterVO.getReqDeliveryTime().toLocalDateTime())));
				}
			}
			//Added for ICRD-214795 ends
			//added by a-7779 for ICRD-342042 starts
			if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null && mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
				builder.append("  AND MST.ONNTIMDLVFLG = ? ");
				this.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
			}
			//added by a-7779 for ICRD-342042 ends
			if (damageFlag != null
					&& MailConstantsVO.FLAG_YES.equals(damageFlag)) {
				builder.append(" AND MST.DMGFLG = ? ");
				this.setParameter(++index, damageFlag);
			}
			/*	Commented by Reno K Abraham
			if (scanUser != null && scanUser.trim().length() > 0) {
				builder.append(" AND MST.SCNUSR LIKE '%").append(scanUser)
						.append("%'");
			}
			if (containerNumber != null && containerNumber.trim().length() > 0) {
				builder.append(" AND MST.CONNUM = ? ");
				this.setParameter(++index, containerNumber);
			}*/
			if (fromCarrierCode != null
					&& fromCarrierCode.trim().length() > 0
					&& !(MailConstantsVO.MAIL_STATUS_TRANSFERRED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus()))) {
				builder.append(" AND ARPULD.FRMCARCOD = ? ");
				this.setParameter(++index, fromCarrierCode);
			}
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
				builder.append("  AND   EXISTS  ");
				builder.append(" ( SELECT 1 FROM SHRCTYMST CTY1 WHERE  ");
				builder.append("  CTY1.CMPCOD = MST.CMPCOD ");
				builder.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ");
				builder.append("  AND (   CTY1.SRVARPCOD  IS  NULL  OR  CTY1.SRVARPCOD <>  ?  ) ) ");
				this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
				builder.append("  AND   EXISTS  ");
				builder.append(" ( SELECT 1 FROM SHRCTYMST CTY2 WHERE  ");
				builder.append("  CTY2.CMPCOD = MST.CMPCOD ");
				builder.append("  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) ");
				builder.append("  AND  (  CTY2.SRVARPCOD  IS  NULL OR CTY2 .SRVARPCOD <>  ?  ) )");
				this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			}
			if("ACP".equals(this.mailbagEnquiryFilterVO.getCurrentStatus())
					|| "TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus())){
				builder.append(")ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN )RESULT_TABLE");
			}
			else{
				builder.append(")");
			}
		}
		if(this.mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(this.mailbagEnquiryFilterVO.getFromScreen()) &&
				this.mailbagEnquiryFilterVO.getFlightNumber()==null)
		{
			builder.append(")RESULT_TABLE");
		}
		return builder.toString();
	}
}
