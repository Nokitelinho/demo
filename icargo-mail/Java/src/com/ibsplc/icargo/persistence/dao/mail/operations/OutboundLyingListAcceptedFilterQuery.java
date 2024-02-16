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

public class OutboundLyingListAcceptedFilterQuery  extends PageableNativeQuery<MailbagVO> {

	/**
	 * The Local logger Instance for this Class.
	 */
	private Log log = LogFactory.getLogger("MailTracking_Defaults");
	/**
	 * The base Query
	 */
	private String baseQuery;
	
	private String outerQuery;
	private static boolean isRank;
	//public static String rankQuery = "SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY RESULT_TABLE.DSTCOD) RANK FROM (".toString();
	public static final String suffixQuery = ") ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN )".toString();
	/**
	 * The mailbagEnquiryFilterVO
	 */
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;
	
	
	/**
	 * This is to identify if the query is to fetch summary or grouped list from Mail Outbound screen
	 */
	private boolean summaryFlag;
	/**
	 * @author a-1936 The constructor
	 * @param baseQuery
	 * @param mailbagEnquiryFilterVO
	 * @throws SystemException
	 */

	public static boolean isRank() {
		return isRank;
	}
	public static void setRank(boolean isRank) {
		OutboundLyingListAcceptedFilterQuery.isRank = isRank;
	}
	/** changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	/**
	 * 
	 * To get the grouped details
	 *
	 */
	public OutboundLyingListAcceptedFilterQuery(
			OutboundCarditGroupMapper groupMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery, String outerQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),groupMapper);
		this.baseQuery = baseQuery;
		this.outerQuery=outerQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.summaryFlag=summaryFlag;
	}
	
	/**
	 * 
	 * To get the summary details
	 *
	 */
	public OutboundLyingListAcceptedFilterQuery(
			OutboundCarditSummaryMapper summaryMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery, String outerQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),summaryMapper);
		this.baseQuery = baseQuery;
		this.outerQuery=outerQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.summaryFlag=summaryFlag;
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
		String originAirportCode = mailbagEnquiryFilterVO.getOriginAirportCode();
		String destinationAirportCode = mailbagEnquiryFilterVO.getDestinationAirportCode();
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
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		// Added to determine whether the Cardit is Present For a mailBag or Not
		// ADDED AS A PART OF NCA CR
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();//Added for ICRD-205027
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		StringBuilder builder = new StringBuilder(outerQuery);
	    StringBuilder finalQuery = null;
	    StringBuilder destinationBuilder = null;
	    StringBuilder historyBuilder = null;
	    StringBuilder flightBuild = null;
	    boolean isFlight = true;
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
		.equals(mailbagEnquiryFilterVO.getTransitFlag());
	    boolean isUnion = false;
	    Date flightSqlDate = null;
	    int index = 0;
		log.log(Log.FINE, "The Damaged Status is ", damageFlag);
	    if (flightDate != null) {
	      flightSqlDate = flightDate.toSqlDate();
	    }
	    String flightDateString = String.valueOf(flightSqlDate);
	    boolean fromExportList = false;
		if("exportlist".equals(mailbagEnquiryFilterVO.getFromExportList())){
	      fromExportList = true;
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
	    
		//END
	    historyBuilder = new StringBuilder(",MALHIS HIS");
	    historyBuilder.append(",MALPOADTL POADTL")
	      .append(",MALEXGOFCMST EXGOFC");
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
	      historyBuilder.append(",MALCDTRCP RCP ");
	    }
	    historyBuilder.append(" WHERE ");
		if (carditPresent != null
				&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
	      historyBuilder.append(" MST.CMPCOD = RCP.CMPCOD AND ").append(
	        " MST.MALIDR = RCP.RCPIDR AND ");
	    }

		historyBuilder.append(" MST.MALSEQNUM = HIS.MALSEQNUM AND ").append(
				" MST.CMPCOD = HIS.CMPCOD  AND ").append(" HIS.MALHISIDR = ").append(
				" ( SELECT MAX(MALHISIDR) ").append(
				" FROM MALHIS WHERE MALSEQNUM=HIS.MALSEQNUM AND").append(
	      " CMPCOD = MST.CMPCOD AND MALSTA IN (? ");

		 if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
			 mailbagEnquiryFilterVO.getCurrentStatus())) {
	      historyBuilder.append(" , ? , ?  ");//Changed by A-8164 for ICRD-343119
			// historyBuilder.append(" , ?  ");
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
		String history = historyBuilder.append(" ) AND  ").toString();
	    
		/*
		 * This part will be appended to the base Query when the 1.Flight
		 * Details are not Present and 2.The Destination Details are not
		 * present.
		 *
		 */
		finalQuery = new StringBuilder()
				.append(
						",CONMST.POAFLG,CONMST.LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM,")
	      .append("SEG.ACPSTA,SEG.ARRSTA, SEG.TRAFLG ")
	      .append(",HIS.FLTCARCOD, HIS.FLTDAT,SEG.FRMCARCOD ")
	      .append("  FROM  MALMST MST , MALULDSEGDTL SEG,")
	      .append(" MALFLTCON CONMST ")
	      .append(history)
	      .append(" HIS.CMPCOD=SEG.CMPCOD AND ")
	      .append(" HIS.MALSEQNUM=SEG.MALSEQNUM AND ")
	      .append(" HIS.MALSEQNUM=SEG.MALSEQNUM AND ")
	      .append(" HIS.FLTCARIDR=SEG.FLTCARIDR AND ")
	      .append(" HIS.FLTNUM=SEG.FLTNUM AND ")
	      .append(" HIS.FLTSEQNUM=SEG.FLTSEQNUM AND ")
	      .append(" HIS.SEGSERNUM=SEG.SEGSERNUM  AND ")
	      .append(" HIS.CONNUM=SEG.CONNUM ")
	      .append(" AND SEG.CMPCOD=CONMST.CMPCOD ")
	      .append(" AND SEG.CONNUM = CONMST.CONNUM ")
	      .append(" AND SEG.FLTCARIDR = CONMST.FLTCARIDR ")
	      .append(" AND SEG.FLTNUM= CONMST.FLTNUM ")
	      .append(" AND SEG.FLTSEQNUM= CONMST.FLTSEQNUM ")
	      .append(" AND SEG.SEGSERNUM= CONMST.SEGSERNUM  AND ")
	      .append(" MST.CMPCOD= EXGOFC.CMPCOD")
	      .append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
	      .append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
	      .append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
	      .append(" AND POADTL.PARCOD(+) ='UPUCOD' AND ")
	      .append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ");

		/*
		 * If the status is being ACCEPTED/Assigned /Transffered and destination
		 * Details are given.. Query to be appended in case of ACP+Destination.
		 */
		if ((carrierId > 0 || carrierId == 0)
				&& (flightNumber == null || flightNumber.trim().length() == 0)) {
			log.log(Log.INFO, "Constructing Destination Builder");
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
							.append( ",( SELECT ( DECODE ( APHCODUSE, 2, TWOAPHCOD, THRAPHCOD ) )  FROM SHRARLMST  WHERE CMPCOD = HIS.CMPCOD  AND ARLIDR = HIS.FLTCARIDR) FLTCARCOD")
							.append(", NULL,  '' FRMCARCOD ");
	        }
				destinationBuilder.append("  FROM  MALMST MST ").append(
						history).append(" (HIS.FLTNUM IS NULL or HIS.FLTNUM = '-1')  AND ");
	        destinationBuilder.append(" MST.SCNPRT = HIS.SCNPRT AND ")
	          .append(" MST.CMPCOD= EXGOFC.CMPCOD")
	          .append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
	          .append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
	          .append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
	          .append("AND POADTL.PARCOD(+) ='UPUCOD' AND ")
	          .append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ");

			} else {

	        if (isUnion) {
	          destinationBuilder.append(this.baseQuery);
	        }

				destinationBuilder
						.append(",CONMST.POAFLG,CONMST.LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM");
	        if (isUnion) {
					destinationBuilder.append(
							" ,'' ACPSTA,'' ARRSTA,'' TRAFLG ").append(
	            " ,ULD.FLTCARCOD, NULL,ARPULD.FRMCARCOD ");
	        }
				destinationBuilder
						.append(
								" FROM  MALMST MST,MALARPULDDTL ARPULD, MALARPULD ULD,MALFLTCON CONMST ")
						.append(history)
						.append(" HIS.CMPCOD=ARPULD.CMPCOD ")
						.append(" AND HIS.MALSEQNUM=ARPULD.MALSEQNUM ")
						.append(" AND HIS.CONNUM=ARPULD.CONNUM ")
						.append(" AND HIS.SCNPRT= ARPULD.ARPCOD ")
						.append(" AND HIS.FLTCARIDR=ARPULD.FLTCARIDR ")
						.append(" AND HIS.FLTCARIDR = ULD.FLTCARIDR ")
						.append(" AND HIS.SCNPRT = ULD.ARPCOD ")
						.append(" AND HIS.CMPCOD = ULD.CMPCOD ")
						.append(" AND ULD.ULDNUM = ARPULD.ULDNUM ")
						.append(" AND ULD.CMPCOD=CONMST.CMPCOD ")
						.append(" AND ULD.FLTCARIDR = CONMST.FLTCARIDR ")
						.append(" AND ULD.ARPCOD= CONMST.ASGPRT ")
						.append(" AND HIS.CONNUM = CONMST.CONNUM  AND")
						//.append(" AND MST.FLTNUM = '-1'  AND ")
				        .append(" CONMST.FLTNUM = '-1'  AND ")
				        .append(" MST.CMPCOD= EXGOFC.CMPCOD")
				        .append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
				        .append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
				        .append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
				        .append("AND POADTL.PARCOD(+) ='UPUCOD' AND ")
				         .append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ");
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
			log.log(Log.INFO, "FLIGHT ACCEPTED");
	      builder.append(finalQuery);

		} else if (carrierId > 0
				&& (flightNumber == null || flightNumber.trim().length() == 0)) {
			log.log(Log.INFO, "DESTINATION ACCEPTED");
	      builder.append(destinationBuilder);
	      isFlight = false;
		} else {
			log.log(Log.INFO, " BOTH FLIGHT AND DESTINATION ACCEPTED");
	      builder.append(finalQuery);
	    }

		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
			mailbagEnquiryFilterVO.getCurrentStatus())) {
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_ASSIGNED);// A-8164 for ICRD-343119
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

		//END
		if (companyCode != null && companyCode.trim().length() > 0) {
	      builder.append("  MST.CMPCOD=? ");
			this.setParameter(++index, companyCode);
		}
		if (scanFromDate != null) {
			builder.append(" AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) >= ? ");
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		if (scanToDate != null) {
			builder.append("  AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) <= ? ");
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
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
		/*if(scanPort != null
				&& scanPort.trim().length() > 0){
			builder.append("   AND MST.SCNPRT=? ");
			this.setParameter(++index, scanPort);
		}*/
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
		if (isFlight) {
			log.log(Log.INFO, "THE STATUS IS MAIL_STATUS_ACCEPTED");
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
					.append(" AND  TRUNC(HIS.FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
			this.setParameter(++index, flightDateString);
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
			/*if(!AcceptedMailBagFilterQuery.isRank()){
				StringBuilder denseRank=new StringBuilder(AcceptedMailBagFilterQuery.rankQuery);
				builder=denseRank.append(builder);
				setRank(true);
			}*/
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
				this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
			}
			if (scanToDate != null) {
				builder.append("  AND TO_NUMBER(TO_CHAR(HIS.SCNDAT,'YYYYMMDD')) <= ? ");
				this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
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
				if(this.summaryFlag) {
					builder.append(")ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN )");
				}
				else {
					builder.append(")ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN ) MST group by DSTCOD)RESULT_TABLE");
				}
			}
			else{
				if(this.summaryFlag) {
					builder.append(") )");
				}
				else {
					builder.append(") MST group by DSTCOD)RESULT_TABLE"); 
				}
			}
		}
		if(this.mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(this.mailbagEnquiryFilterVO.getFromScreen()) && 
				this.mailbagEnquiryFilterVO.getFlightNumber()==null)
			{
	      builder.append(")");
	    }
		//Added by A-8164 for ICRD-343119
		/*if (isFlight && !isUnion) { 
			builder.append(")");
		}*/
		
	    return builder.toString();
	  }


}
