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

public class OutboundlyinglistOffloadedFilterQuery extends PageableNativeQuery<MailbagVO> {
	/**
	 * The Logger Instance
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * The base Query
	 */
	private String baseQuery;
	
	/**
	 * The mailBagEnquiryFilterVO
	 */
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
	public OutboundlyinglistOffloadedFilterQuery(
			OutboundCarditGroupMapper groupMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),groupMapper);
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag= summaryFlag;
	}
	
	/**
	 * 
	 * To get the summary details
	 *
	 */
	
	public OutboundlyinglistOffloadedFilterQuery(
			OutboundCarditSummaryMapper summaryMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery,boolean summaryFlag)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),summaryMapper);
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.baseQuery = baseQuery;
		this.summaryFlag= summaryFlag;
	}
	/**
	 * This method is used to append the Query dynamically based on the
	 * Different Filter Conditions.. Note: This filter Query will be used wen
	 * the status is either NotUplifted or offloaded.
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
	    StringBuilder notUpliftedOffloadedQuery = null;
		StringBuilder builder = new StringBuilder(baseQuery);
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
		log.log(Log.FINE, "The Damage Status ", damageFlag);
		/*
		 * Query tat will be appended wen the status is OFFLOADED or Not
		 * Uplifted Also checks whether the Cardit Status is Y or N if Y fetch
		 * all those mailBags for which CARDITS has been Received(Implemented by
		 * Join of MTKMALMST and MTKCDTRCP) if N fetch all those mailBags for
		 * which CARDITS has not been Received(Implemented by NOT EXISTS sub
		 * Query)
		 */
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(currentStatus)){
			log.log(Log.INFO, "Constructing the Offloaded Query");
	      notUpliftedOffloadedQuery = new StringBuilder()
	        .append(" ,MST.FLTNUM,MST.FLTSEQNUM,MST.FLTCARIDR,MST.SEGSERNUM,HIS.FLTCARCOD, HIS.FLTDAT ")
	        .append(", (SELECT LEGSERNUM FROM  MALFLTCON CONMST ")
	        .append(" WHERE  MST.FLTCARIDR = CONMST.FLTCARIDR")
	        .append(" AND MST.FLTNUM = CONMST.FLTNUM")
	        .append(" AND MST.FLTSEQNUM = CONMST.FLTSEQNUM")
	        .append(" AND MST.CONNUM = CONMST.CONNUM")
	        .append(" AND MST.SCNPRT = CONMST.ASGPRT")
	        .append(" AND MST.CMPCOD = CONMST.CMPCOD) AS LEGSERNUM ")
	        .append(", (SELECT POAFLG FROM MALFLTCON CONMST")
	        .append(" WHERE MST.CMPCOD = CONMST.CMPCOD")
	        .append(" AND MST.FLTCARIDR = CONMST.FLTCARIDR")
	        .append(" AND MST.FLTNUM = CONMST.FLTNUM")
	        .append(" AND MST.FLTSEQNUM = CONMST.FLTSEQNUM")
	        .append(" AND MST.CONNUM = CONMST.CONNUM")
	        .append(" AND MST.SCNPRT = CONMST.ASGPRT)");
			//.append(" AND CONMST.LEGSERNUM = -1")
			//.append(" AND CONMST.SEGSERNUM = -1)")


			notUpliftedOffloadedQuery.append(" AS POAFLG, (SELECT DISTINCT 'Y' FROM MALHIS  WHERE MALSEQNUM = MST.MALSEQNUM AND CMPCOD  AND SCNPRT =MST.SCNPRT AND MALSTA ='ACP') AS ACPFLG FROM MALMST MST,MALHIS HIS,FLTOPRLEG FLTOPR,MALPOADTL POADTL,MALEXGOFCMST EXGOFC ");

			if (carditPresent != null
					&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
				log.log(Log.FINE, "The carditPresent flag is ", carditPresent);
	        notUpliftedOffloadedQuery.append(",MALCDTRCP RCP ");
	      }

			/*
			 * Added By Karthick V as the part of the AirNewzealand Cr
			 */
			if (isTransitFlag) {
				log.log(Log.FINE, "The Transit flag is ", isTransitFlag);
	        notUpliftedOffloadedQuery.append(", SHRCTYMST CTY1, SHRCTYMST CTY2  ");
	        
				transitJoins = new StringBuilder("  CTY1.CMPCOD = MST.CMPCOD ")
						.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ")
						 .append(
								"  AND CTY2.CMPCOD = MST.CMPCOD ").append(
								"  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) AND   ")
				 .toString();
	      }
	      notUpliftedOffloadedQuery.append(" WHERE ");

			if (carditPresent != null
					&& MailConstantsVO.FLAG_YES.equals(carditPresent)) {
				notUpliftedOffloadedQuery
						.append("MST.CMPCOD = RCP.CMPCOD AND ").append(
	          "MST.MALIDR = RCP.RCPIDR AND ");
	      }
	      if (isTransitFlag) {
	        notUpliftedOffloadedQuery.append(transitJoins);
	      }

			notUpliftedOffloadedQuery
					.append(" MST.MALSEQNUM =HIS.MALSEQNUM AND ")
					.append(" MST.CMPCOD=HIS.CMPCOD  AND ")
					.append(" MST.SCNPRT=HIS.SCNPRT ")
					.append(" AND MST.CMPCOD= EXGOFC.CMPCOD")
					.append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
					.append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
					.append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
					.append(" AND POADTL.PARCOD(+) ='UPUCOD' ")
					.append(" AND TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) ")
					.append("AND MST.CMPCOD = FLTOPR.CMPCOD(+)")
				    .append("AND MST.FLTNUM = FLTOPR.FLTNUM(+)")
				    .append("AND MST.FLTSEQNUM=FLTOPR.FLTSEQNUM(+)")
				    .append("AND MST.SEGSERNUM =FLTOPR.LEGSERNUM(+)")
				    .append("AND FLTOPR.ATD IS NULL")
					.append(" AND HIS.MALHISIDR =")
					.append("(select max(MALHISIDR) FROM  MALHIS ")
					.append(" WHERE  MALSEQNUM = HIS.MALSEQNUM ")
					.append(" AND CMPCOD = MST.CMPCOD ");
		//} by A-5219
		/*
		 * OFFloaded and all other status except ACCEPTED applicable only for
		 * ther Flight ...In case of OFFLOADED Take the Flight Details From the
		 * MTKMALMST join on MTKMALMST AND MTKMALHIS
		 *
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
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(currentStatus)) {
	        builder.append(notUpliftedOffloadedQuery).append(
	          " AND  MALSTA IN  (?) ");
			if (scanPort != null && scanPort.trim().length() > 0) {
	          builder.append(" AND SCNPRT= ? ");
	        }
	        if (scanFromDate != null) {
				builder
						.append(" AND TO_NUMBER(TO_CHAR(SCNDAT,'YYYYMMDD')) >= ? ");
	        }
	        if (scanToDate != null) {
				builder
						.append(" AND TO_NUMBER(TO_CHAR(SCNDAT,'YYYYMMDD')) <= ? ");
	        }
			/*START
			 * ADDED BY RENO K ABRAHAM
			 */
	        builder.append(flightBuilder);
			if (containerNumber != null && containerNumber.trim().length() > 0) {
				builder
						.append(" AND CONNUM = ? ");
	        }
			//END
	        builder.append(" )   AND ");

		}

		/*
		 * NotUPlifted means all those mailBags Accepted\Manifested
		 * 1.(i.e)Latest Destination Assigned MailBags and Offloaded MailBags
		 * TODO 2.Manifested(Flight Assigned MailBag and Not Departed) 3.Take
		 * the Union of (1) and (2)
		 */
		/*if (MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equals(currentStatus)) {
			builder.append(notUpliftedOffloadedQuery).append(
					" AND  MALSTA IN  ('").append(
					MailConstantsVO.MAIL_STATUS_ACCEPTED).append("','").append(
					MailConstantsVO.MAIL_STATUS_OFFLOADED).append("','").append(
					MailConstantsVO.MAIL_STATUS_ASSIGNED).append("','").append(
					MailConstantsVO.MAIL_STATUS_TRANSFERRED).append("','").append(
					MailConstantsVO.MAIL_STATUS_DAMAGED).append("'").append(
					" ) ");
			if (scanPort != null && scanPort.trim().length() > 0) {
				builder.append(" AND  SCNPRT = ?");
	      }
	      if (scanFromDate != null) {
				builder
						.append(" AND TO_NUMBER(TO_CHAR(SCNDAT,'YYYYMMDD')) >= ? ");
	      }
	      if (scanToDate != null) {
				builder
						.append(" AND TO_NUMBER(TO_CHAR(SCNDAT,'YYYYMMDD')) <= ? ");
			}
			START
			 * ADDED BY RENO K ABRAHAM
			builder.append(flightBuilder);
			if (containerNumber != null && containerNumber.trim().length() > 0) {
				builder
						.append(" AND CONNUM = ? ");
			}
			//END
			builder.append("	) AND ").append(" (MST.FLTNUM ='-1' OR MST.FLTNUM=HIS.FLTNUM AND MST.FLTSEQNUM=HIS.FLTSEQNUM AND MST.SEGSERNUM=HIS.SEGSERNUM)AND ").append(
					"(MST.MALSTA='").append(
					MailConstantsVO.MAIL_STATUS_ACCEPTED).append("'").append(
					"OR").append(" MST.MALSTA='").append(
					MailConstantsVO.MAIL_STATUS_OFFLOADED).append("'").append(
					"OR").append(" MST.MALSTA='").append(
					MailConstantsVO.MAIL_STATUS_DAMAGED).append("'").append(
					"OR").append(" MST.MALSTA='").append(
							MailConstantsVO.MAIL_STATUS_TRANSFERRED).append("'").append(
					"OR").append(" MST.MALSTA='").append(
					MailConstantsVO.MAIL_STATUS_ASSIGNED).append("'").append(
					") AND ");
			log.log(Log.INFO, "The CurrentStatus is NotUplited");
		}*/
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(currentStatus)) {
			this.setParameter(++index, MailConstantsVO.MAIL_STATUS_OFFLOADED);
		}
		if (scanPort != null && scanPort.trim().length() > 0) {
			this.setParameter(++index, scanPort);
		}
		if (scanFromDate != null) {
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		if (scanToDate != null) {
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}
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
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(currentStatus)) {
	        builder.append(" AND MST.MALSTA = ?   ");
			this.setParameter(++index, currentStatus);
			log.log(Log.INFO, "THE STATUS IS OFFLOADED");
	      }

		if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
	        builder.append(" AND MST.DMGFLG = ? ");
			this.setParameter(++index, damageFlag);
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
			builder.append(" AND MST.CONNUM = ? ");
			this.setParameter(++index, containerNumber);
		}
		if (flightNumber != null && flightNumber.trim().length() > 0) {
			builder.append("  AND HIS.FLTNUM = ? ");
			this.setParameter(++index, flightNumber);
		}
		if (carrierId > 0) {
			builder.append(" AND HIS.FLTCARIDR = ? ");
			this.setParameter(++index, carrierId);
		}
		 */
		/**
		 * Added to fetch all the MailBags Only for which the cardit has not
		 * been Received
		 *
		 */
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

		} else if(MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equals(currentStatus)){
	      StringBuilder upliftedQuery = new StringBuilder("");
	      upliftedQuery.append("WITH HIS AS (SELECT CMPCOD, MALSEQNUM, MALHISIDR, FLTCARCOD, FLTCARIDR, ")
	        .append(" FLTNUM, FLTSEQNUM, SEGSERNUM, FLTDAT, SCNPRT, SCNDAT, CONNUM, CONTYP, MALSTA, ")
	        .append(" MAX(MALHISIDR) OVER (PARTITION BY MALSEQNUM) MAXHISIDR FROM MALHIS WHERE CMPCOD  = ? ")
	        .append(" AND MALSTA IN ('ACP','OFL','ASG','TRA','DMG') AND SCNPRT = ? AND ")
	        .append(" TO_NUMBER(TO_CHAR(TRUNC(SCNDAT),'YYYYMMDD')) between  ? and ? ORDER BY SCNDAT ")
	        .append(" ) SELECT MST.CMPCOD, MST.MALIDR, MST.ORGEXGOFC, MST.DSTEXGOFC, MST.YER, MST.DSN, MST.MALCLS, ")
	        .append(" MST.MALCTG MALCTGCOD, MST.MALSUBCLS, MST.SCNPRT, MST.SCNDAT, MST.CONNUM CONNUM, MST.SCNUSR, ")
	        .append(" MST.MALSTA, MST.OPRSTA, MST.CONTYP, MST.POU, MST.WGT, MST.CSGDOCNUM, MST.CSGSEQNUM, MST.POACOD, ")
	        .append(" MST.DMGFLG, MST.LSTUPDTIM , MST.FLTNUM, MST.FLTSEQNUM, MST.FLTCARIDR, MST.SEGSERNUM, HIS.FLTCARCOD, ")
	        .append(" HIS.FLTDAT, CON.LEGSERNUM, CON.POAFLG FROM HIS INNER JOIN MALMST MST ON MST.MALSEQNUM = HIS.MALSEQNUM ")
	        .append(" AND MST.CMPCOD = HIS.CMPCOD AND MST.SCNPRT = HIS.SCNPRT ");
			if(carditPresent != null && MailConstantsVO.FLAG_YES.equals(carditPresent)){
				upliftedQuery.append(" INNER JOIN MALCDTRCP RCP ON ")
				.append(" MST.CMPCOD = RCP.CMPCOD AND MST.MALIDR = RCP.RCPIDR ");
			}
			if (upuCode != null
					&& upuCode.trim().length() > 0) {
				upliftedQuery.append(" INNER JOIN MALPOADTL POADTL ON MST.CMPCOD =  POADTL.CMPCOD AND ")
				.append(" MST.POACOD = POADTL.POACOD ")
				.append(" AND PARCOD= 'UPUCOD' ");
			}
	      if (isTransitFlag) {
				upliftedQuery.append(" INNER JOIN SHRCTYMST CTY1 ON CTY1.CMPCOD = MST.CMPCOD AND ")
				.append(" CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST  WHERE CMPCOD = MST.CMPCOD ")
				.append(" AND EXGOFCCOD = MST.ORGEXGOFC) INNER JOIN SHRCTYMST CTY2 ON CTY2.CMPCOD = MST.CMPCOD AND ")
				.append(" CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST  WHERE CMPCOD = MST.CMPCOD AND EXGOFCCOD = MST.ORGEXGOFC) ");
	      }
			
			upliftedQuery.append(" LEFT OUTER JOIN MALFLTCON CON ON CON.FLTCARIDR = MST.FLTCARIDR ")
			.append(" AND CON.FLTNUM = MST.FLTNUM AND CON.FLTSEQNUM = MST.FLTSEQNUM AND CON.CONNUM = MST.CONNUM ")
			.append(" AND cON.ASGPRT = MST.SCNPRT AND CON.CMPCOD = MST.CMPCOD WHERE MAXHISIDR = MALHISIDR ")
			.append(" AND ( MST.FLTSEQNUM = -1 OR EXISTS (SELECT 1 FROM FLTOPRLEG LEG WHERE LEG.CMPCOD = HIS.CMPCOD ")
			.append(" AND LEG.FLTCARIDR = HIS.FLTCARIDR AND LEG.FLTNUM = HIS.FLTNUM AND LEG.FLTSEQNUM = HIS.FLTSEQNUM ")
			.append(" AND LEG.LEGSERNUM = HIS.SEGSERNUM AND LEG.ATD IS NULL ) ) ");
	      
	      builder = new StringBuilder(" SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY ROWNUM ) AS RANK FROM ( ");
	      builder.append(upliftedQuery.toString());
			this.setParameter(++index, companyCode);
			this.setParameter(++index, scanPort);
			this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
			this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
			
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
			
			if (flightDate != null && flightDateString != null && flightDateString.trim().length() > 0 ) {
				builder
				.append(" AND  TRUNC(HIS.FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
				this.setParameter(++index, flightDateString);
			}
			if (scanUser != null && scanUser.trim().length() > 0) {
	        builder.append(" AND MST.SCNUSR LIKE '%").append(scanUser).append(
	          "%'");
	      }

			if (flightNumber != null && flightNumber.trim().length() > 0) {
	        builder.append("  AND HIS.FLTNUM = ? ");
				this.setParameter(++index, flightNumber);
	      }

			if (carrierId > 0) {
	        builder.append(" AND HIS.FLTCARIDR = ? ");
				this.setParameter(++index, carrierId);
	      }
			if (damageFlag != null && MailConstantsVO.FLAG_YES.equals(damageFlag)) {
	        builder.append(" AND MST.DMGFLG = ? ");
				this.setParameter(++index, damageFlag);
	      }
			//Added for ICRD-133967 starts
			if (consigmentNumber != null
					&& consigmentNumber.trim().length() > 0) {
	        builder.append("  AND MST.CSGDOCNUM=? ");
				this.setParameter(++index, consigmentNumber);
	      }
			
			if (upuCode != null
					&& upuCode.trim().length() > 0) {
	        builder.append(" AND POADTL.PARVAL = ? ");
				this.setParameter(++index, upuCode);
	      }
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
			if(isTransitFlag){
	        builder.append(" AND CTY1.SRVARPCOD <> ? ");
				this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
	        builder.append(" AND CTY2.SRVARPCOD <> ? ");
				this.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
	      }
			
			if (containerNumber != null && containerNumber.trim().length() > 0) {
	        builder.append("  AND MST.CONNUM = ? ");
				this.setParameter(++index, containerNumber);
	      }
			
			if (carditPresent != null
					&& MailConstantsVO.FLAG_NO.equals(carditPresent)) {
	        builder.append("  AND  NOT EXISTS  ");
	        builder.append(" ( SELECT 1 FROM MALCDTRCP WHERE  ");
	        builder.append("  CMPCOD =  MST.CMPCOD AND ");
	        builder.append("  RCPIDR =  MST.MALIDR ) ");
	      }
			
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