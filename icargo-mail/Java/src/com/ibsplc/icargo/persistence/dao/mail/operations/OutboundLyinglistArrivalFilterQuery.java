package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundLyinglistArrivalFilterQuery  extends PageableNativeQuery<MailbagVO> {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private String baseQuery;
	
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;
	
	/** 
	 * This is to identify if the query is to fetch summary or grouped list from Mail Outbound screen
	 */
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
	/**
	 * 
	 * To get the grouped details
	 *
	 */
	public OutboundLyinglistArrivalFilterQuery(
			OutboundCarditGroupMapper groupMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),groupMapper);
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag=summaryFlag;
	}
	
	/**
	 * 
	 * To get the summary details
	 *
	 */
	public OutboundLyinglistArrivalFilterQuery(
			OutboundCarditSummaryMapper summaryMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),summaryMapper);
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag=summaryFlag;
	}
	/**
	 * This method is used to append the Query dynamically based on the
	 * Different Filter Conditions.. Note:This Filter Query is to be used when
	 * the status is Arrived,Not Arrived,Delivered,Not Delivered.
	 */
	public String getNativeQuery() {
		log.entering("INSIDE THE FILTER QUERY", "getNativeQuery()");
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
		LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
		String damageFlag = mailbagEnquiryFilterVO.getDamageFlag();
		String scanUser = mailbagEnquiryFilterVO.getScanUser();
		String containerNumber = mailbagEnquiryFilterVO.getContainerNumber();
		String flightNumber = mailbagEnquiryFilterVO.getFlightNumber();
		LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
		int carrierId = mailbagEnquiryFilterVO.getCarrierId();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		// Added to determine whether the Cardit is Present For a mailBag or Not
		// ADDED AS A PART OF NCA CR
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
	    StringBuilder finalQuery = null;
		StringBuilder builder = new StringBuilder(baseQuery);
	    StringBuilder historyBuilder = null;
	    StringBuilder flightBuild = null;
	    Date flightSqlDate = null;
	    String transitJoins = null;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
				.equals(mailbagEnquiryFilterVO.getTransitFlag());
	    int index = 0;
	    if (flightDate != null) {
	      flightSqlDate = flightDate.toSqlDate();
	    }
	    String flightDateString = String.valueOf(flightSqlDate);
	    
		log.log(Log.FINE, "THE DAMAGE STATUS IS ", damageFlag);
		/*
		 * START ADDED BY RENO K ABRAHAM
		 */
	    flightBuild = new StringBuilder();
	    if (flightSqlDate != null) {
			flightBuild
					.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
	    }
		if (scanUser != null && scanUser.trim().length() > 0) {
	      flightBuild.append(" AND SCNUSR LIKE '%").append(scanUser).append(
	        "%'");
	    }

		if (flightNumber != null && flightNumber.trim().length() > 0) {
	      flightBuild.append("  AND FLTNUM = ? ");
	    }
	    if (carrierId > 0) {
	      flightBuild.append(" AND FLTCARIDR = ? ");
	    }
	    String flightBuilder = flightBuild.toString();
	    
		// END
	    historyBuilder = new StringBuilder(",MALHIS HIS");
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
	      historyBuilder.append(",MALCDTRCP RCP ");
	    }

		if (isTransitFlag) {
	      historyBuilder.append(", SHRCTYMST CTY1, SHRCTYMST CTY2  ");
			transitJoins = new StringBuilder("  CTY1.CMPCOD = MST.CMPCOD ")
					.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ")
					.append("  AND CTY2.CMPCOD = MST.CMPCOD ")
					.append(
							"  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) AND   ")
					.toString();
	    }
	    historyBuilder.append(",MALMST MST ");
	    if (!"ARR".equals(currentStatus)) {
		historyBuilder.append(" LEFT OUTER JOIN MALARPULDDTL ARPULD")
		.append(" ON MST.MALSEQNUM = ARPULD.MALSEQNUM")
		.append(" AND MST.CMPCOD = ARPULD.CMPCOD")
		.append(" AND MST.FLTCARIDR = ARPULD.FLTCARIDR")
		//added for icrd-92604
		.append(" AND MST.SCNPRT    = ARPULD.ARPCOD")
		.append(" AND MST.CONNUM      = ARPULD.CONNUM ");
		}
		//Added for ICRD-133697 starts
		/*historyBuilder.append(" INNER JOIN MALEXGOFCMST EXGOFC")
		.append(" ON MST.ORGEXGOFC=EXGOFC.EXGOFCCOD")
		.append(" AND MST.CMPCOD  =EXGOFC.CMPCOD")*/
		if(upuCode != null && upuCode.trim().length() >0){
		historyBuilder.append(" LEFT OUTER JOIN MALPOADTL POADTL ")
		.append(" ON POADTL.CMPCOD = MST.CMPCOD ")
		.append(" AND POADTL.POACOD = MST.POACOD ")
		.append(" AND POADTL.PARCOD ='UPUCOD'")
		.append("AND TRUNC(SYSDATE) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");
		}
		//Added for ICRD-133697 ends
		//Modified filter query by A-7794 as part of ICRD-197479
	    historyBuilder.append(" WHERE ");
		 if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
	      historyBuilder.append(" MST.CMPCOD = RCP.CMPCOD AND ").append(
	        " MST.MALIDR = RCP.RCPIDR AND ");
	    }
	    if (isTransitFlag) {
	      historyBuilder.append(transitJoins);
	    }

		historyBuilder.append(" MST.MALSEQNUM = HIS.MALSEQNUM AND ").append(
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

		String history = historyBuilder.append(" ) AND  ").toString();
		/*
		 * This part will be appended to the Base Query when flightDetails not
		 * Present AND destination not present..
		 *
		 */
	    finalQuery = new StringBuilder().append(
	      ",MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM,")
	      .append("SEG.ACPSTA,SEG.ARRSTA, SEG.TRAFLG ,SEG.FRMCARCOD")
	      .append(",HIS2.FLTCARCOD, HIS2.FLTDAT, (SELECT POAFLG FROM MALFLTCON CONMST WHERE MST.FLTCARIDR = CONMST.FLTCARIDR")
	      .append(" AND MST.FLTNUM  = CONMST.FLTNUM AND MST.FLTSEQNUM   = CONMST.FLTSEQNUM AND MST.CONNUM = CONMST.CONNUM")
				.append(" AND MST.CMPCOD = CONMST.CMPCOD AND ROWNUM = 1) AS POAFLG, (SELECT DISTINCT 'Y' FROM MALHIS WHERE MALSEQNUM = MST.MALSEQNUM AND CMPCOD =MST.CMPCOD AND SCNPRT =MST.SCNPRT ")
                .append("AND MALSTA ='ACP' ) AS ACPFLG,")
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
				finalQuery.append("  FROM  MALULDSEGDTL SEG,").append(
						" MALHIS HIS2 ").append(history)
				.append(" MST.CMPCOD=SEG.CMPCOD AND ").append(
	      " MST.MALSEQNUM=SEG.MALSEQNUM");

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
					" HIS.CONNUM=SEG.ULDNUM ");//Modified for ICRD-156462
	    }

		// for carrier code and flight date
		finalQuery
				.append(" AND  HIS2.MALSEQNUM = MST.MALSEQNUM ")
				.append(" AND  HIS2.CMPCOD = MST.CMPCOD ")
				//.append(" AND SEG.CMPCOD=CONMST.CMPCOD ")
				//.append(" AND SEG.CONNUM = CONMST.CONNUM ")
				//.append(" AND SEG.FLTCARIDR=CONMST.FLTCARIDR ")
				//.append(" AND SEG.FLTNUM=CONMST.FLTNUM ")
				//.append(" AND SEG.FLTSEQNUM=CONMST.FLTSEQNUM ")
				.append(
						" AND HIS2.MALHISIDR = (SELECT MAX (MALHISIDR)  FROM MALHIS ")
				.append(" WHERE CMPCOD = MST.CMPCOD ")
				.append(" AND MALSEQNUM = MST.MALSEQNUM ")
				.append(
	      " AND MALSTA NOT IN ('74','6','24','48','21','41','42','43','82','57') ) AND ");
	    
	    builder.append(finalQuery);

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
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
	    }
	    if (scanToDate != null) {
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
	    }
		/*
		 * START ADDED BY RENO K ABRAHAM
		 */
	    if (flightSqlDate != null) {
			this.setParameter(++index, flightDateString);
		}

		if (flightNumber != null && flightNumber.trim().length() > 0) {
			this.setParameter(++index, flightNumber);
		}
	    if (carrierId > 0) {
			this.setParameter(++index, carrierId);
		}

		if (containerNumber != null && containerNumber.trim().length() > 0) {
			this.setParameter(++index, containerNumber);
		}

		// END
		if (companyCode != null && companyCode.trim().length() > 0) {
	      builder.append("  SEG.CMPCOD=? ");
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
	      String rqdDlvTime = reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
	        if (rqdDlvTime.contains("0000")) {
	          builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
	        } else {
	          builder.append(" AND MST.REQDLVTIM = ?");
	        }
			this.setParameter(++index,reqDeliveryTime);
			}
		}
		//Added for ICRD-214795 ends
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
			log.log(Log.INFO, "THE STATUS IS DELIVERED");
			/*
			 * Note: In MTKMALULDSEG a Mail Bag will be marked as
			 * Acceppted(ACPSTA='Y') Only if it is Pure Accepted MailTag (i.e)
			 * MailTag is not found as the Part Of Found Shipments... If it is a
			 * part of Found Shipment then ACPSTA is NULL in MTKMALULDSEG
			 *
			 */
	      builder.append(" AND SEG.DLVSTA = 'Y'  ");
	      builder.append(" AND MST.MALSTA =  ?  ");
	      builder.append(" AND MST.SCNPRT =  ?  ");
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_DELIVERED);
			this.setParameter(++index, scanPort);
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
		if(this.summaryFlag) {
			builder.append(")");
		}
		else {
			builder.append(") MST group by DSTCOD");
		}
	    return builder.toString();

	}
}