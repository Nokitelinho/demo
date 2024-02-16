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
public class OutboundLyinglistFilterQuery extends PageableNativeQuery<MailbagVO> {
    
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
	
	private boolean summary;

	
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
	public OutboundLyinglistFilterQuery(
			OutboundCarditGroupMapper groupMapper ,MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery, boolean summary)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),groupMapper);
		this.baseQuery  = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.summary = summary;
	}
	
	public OutboundLyinglistFilterQuery(
			OutboundCarditSummaryMapper summaryMapper ,MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery, boolean summary)
			throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),summaryMapper);
		this.baseQuery = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.summary=summary;
	}
	/**
	 * @author A-8353
	 * @param mailbagMapper
	 * @param mailbagEnquiryFilterVO
	 * @param baseQuery
	 * @param fromScreen
	 * @throws SystemException
	 */
		public OutboundLyinglistFilterQuery(OutboundCarditGroupMapper groupMapper, MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
				String baseQuery, String fromScreen) throws SystemException {
			super(mailbagEnquiryFilterVO.getPageSize(),-1,groupMapper);
			this.baseQuery = baseQuery;
			this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		}
	public OutboundLyinglistFilterQuery(OutboundCarditGroupMapper groupMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery, boolean summary, int pageSize) throws SystemException {
		super(pageSize,-1,groupMapper);
		this.baseQuery  = baseQuery;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
		this.summary = summary;
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
		String carditPresent = mailbagEnquiryFilterVO.getCarditPresent();
		//Added for ICRD-133967 starts
		String consigmentNumber = mailbagEnquiryFilterVO.getConsigmentNumber();
		String upuCode = mailbagEnquiryFilterVO.getUpuCode();
		//Added for ICRD-133967 ends
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
		String serviceLevel = mailbagEnquiryFilterVO.getServiceLevel();
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		StringBuilder builder = new StringBuilder(baseQuery);
		String onTimeDeliveryFlag = mailbagEnquiryFilterVO.getOnTimeDelivery();
	    
		boolean isTransitFlag = MailConstantsVO.FLAG_YES
		.equals(mailbagEnquiryFilterVO.getTransitFlag());
	  	    Date flightSqlDate = null;
	  	    int index = 0;
		log.log(Log.FINE, "The Damaged Status is ", damageFlag);
		    






		    
			

























				











				


























			
		        
		        
		        builder.append(",MST.FLTCARIDR, MST.FLTNUM, MST.FLTSEQNUM,")
				.append(" MST.SEGSERNUM, HIS.FLTCARCOD, HIS.FLTDAT , ( SELECT MAX(POAFLG) FROM  MALFLTCON CONMST ")
				.append(" WHERE  MST.FLTCARIDR = CONMST.FLTCARIDR")
				.append(" AND MST.FLTNUM = CONMST.FLTNUM")
				.append(" AND MST.FLTSEQNUM = CONMST.FLTSEQNUM")
				.append(" AND MST.CONNUM = CONMST.CONNUM")
				//Modified as part of bug ICRD-95626 by A-5526 starts
				//.append(" AND MST.SCNPRT = CONMST.ASGPRT")
		.append(" AND MST.CMPCOD = CONMST.CMPCOD ) AS POAFLG, ( SELECT COALESCE(MAX(LEGSERNUM),0) FROM  MALFLTCON CONMST ")
				//Modified as part of bug ICRD-95626 by A-5526 ends
				.append(" WHERE  MST.FLTCARIDR = CONMST.FLTCARIDR")
				.append(" AND MST.FLTNUM = CONMST.FLTNUM")
				.append(" AND MST.FLTSEQNUM = CONMST.FLTSEQNUM")
				.append(" AND MST.CONNUM = CONMST.CONNUM")
		//Commented by A-7540 for ICRD-322594
	//	.append(" AND MST.SCNPRT = CONMST.ASGPRT")
				.append(" AND MST.CMPCOD = CONMST.CMPCOD) AS LEGSERNUM, (SELECT MAX('Y') FROM MALHIS ")		 //Added by A-5945 for ICRD-97716
				.append("WHERE MALSEQNUM = MST.MALSEQNUM")
				.append(" AND CMPCOD   =MST.CMPCOD")
				.append(" AND SCNPRT   =MST.SCNPRT")
		.append("  AND MALSTA='ARR' ) AS ARRSTA, (SELECT POL ")
		.append("FROM MALFLTSEG SEG")
		.append(" WHERE MST.FLTCARIDR = SEG.FLTCARIDR")
		.append(" AND MST.FLTNUM      = SEG.FLTNUM")
		.append(" AND MST.FLTSEQNUM   = SEG.FLTSEQNUM")
		.append(" AND MST.SEGSERNUM   = SEG.SEGSERNUM")
		.append(" AND MST.CMPCOD      = SEG.CMPCOD")
		.append(") AS POL,")
				.append(" (SELECT DISTINCT 'Y' FROM MALHIS ")
				.append(" WHERE MALSEQNUM = MST.MALSEQNUM")
				.append(" AND CMPCOD   =MST.CMPCOD")
				.append(" AND SCNPRT   =MST.SCNPRT")
		.append("  AND MALSTA='TRA') AS TRAFLG, ")
		//added to get the accepted mailbags in mail outbound lying list group query
		.append("(SELECT DISTINCT 'Y'")
	      .append(" FROM MALHIS")
	      .append(" WHERE MALSEQNUM = MST.MALSEQNUM")
	      .append(" AND CMPCOD      =MST.CMPCOD")
	      .append(" AND SCNPRT      =MST.SCNPRT")
	      .append(" AND MALSTA      ='ACP'")
	      .append(") AS ACPFLG")
		//.append(" ARPULD.CONNUM AS INVCONNUM,ARPULD.ULDNUM AS INVULDNUM ")
				.append(" FROM MALMST MST INNER JOIN MALHIS HIS")
				.append(" ON MST.CMPCOD = HIS.CMPCOD")
				.append(" AND MST.MALSEQNUM = HIS.MALSEQNUM")
				.append(" AND MST.MALSTA <> 'NEW' ")
				.append(" AND  HIS.MALHISIDR =")
				.append(" (SELECT MAX (MALHISIDR)  FROM MALHIS")
				.append(" WHERE MALSEQNUM = MST.MALSEQNUM")
				.append(" AND CMPCOD = MST.CMPCOD")
		.append(" AND  MALSTA IN ('ARR','DLV','ACP','NEW','TRA','OFL','DMG','CAN','ASG','BKD','RTN')")//ICRD-317222 wrong history details picking if resdit entry exist in history table 
		//.append("  AND MALSTA = MST.MALSTA ") //Commented by A-6991 for ICRD-214918
				.append(" AND SCNPRT = MST.SCNPRT ");
		//Commented by A-5945 for ICRD-88823 starts
		//.append(" AND SCNDAT >= ?")
		//.append(" AND SCNDAT <= ?").toString();
		//if (scanFromDate != null) {
		//	this.setParameter(++index, scanFromDate);
		//}
		//if (scanToDate != null) {
		//	this.setParameter(++index, scanToDate);
		//}
		//Commented by A-5945 for ICRD-88823 ends
//		if (flightDate != null) {
//			flightSqlDate = flightDate.toSqlDate();
//		}
//		String flightDateString = String.valueOf(flightSqlDate);
//		if (flightSqlDate != null) {
//			builder
//			.append(" AND  TRUNC(HIS.FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
//			this.setParameter(++index, flightDateString);
//		}
		      builder.append(" )").toString();
		/*builder.append(" LEFT OUTER JOIN MALARPULDDTL ARPULD ")
		.append(" ON MST.MALSEQNUM    = ARPULD.MALSEQNUM ")
		.append("AND MST.CONNUM      = ARPULD.CONNUM ")      //Added by A-5945 for ICRD-91101
		.append(" AND MST.CMPCOD     = ARPULD.CMPCOD ")
		.append(" AND MST.FLTCARIDR  = ARPULD.FLTCARIDR ")
		.append(" AND MST.SCNPRT     = ARPULD.ARPCOD ");*/
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
				.append(" AND TRUNC(SYSDATE) between TRUNC(POADTL.VLDFRM) and TRUNC(POADTL.VLDTOO)");
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
						builder.append(" AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) >= ? ");
					}else{
						builder.append(" TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) >= ? ");
						isContinued = true;
					}
					this.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
				}
				if (scanToDate != null ) {
					if(isContinued){
						builder.append(" AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD'))   <= ?");
					}else{
						builder.append(" TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD'))   <= ? ");
						isContinued = true;
					}
					this.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
				}
				//Added by A-5945 for ICRD-88823 ends
				if (flightNumber != null && flightNumber.trim().length() > 0) {
					if(isContinued){
						builder.append(" AND MST.FLTNUM = ? ");
					}else{
						builder.append(" MST.FLTNUM = ? ");
						isContinued = true;
					}
					this.setParameter(++index, flightNumber);
				}

				if(flightDate!=null){
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

				if (carrierId > 0) {
					if(isContinued){
						builder.append(" AND MST.FLTCARIDR = ? ");
					}else{
						builder.append(" MST.FLTCARIDR = ? ");
						isContinued = true;
					}
					this.setParameter(++index, carrierId);
				}

				if (scanPort != null && scanPort.trim().length() > 0) {
					if(isContinued){
						builder.append(" AND MST.SCNPRT = ? ");
					}else{
						builder.append(" MST.SCNPRT = ? ");
						isContinued = true;
					}
					this.setParameter(++index, scanPort);
				}

				if (containerNumber != null && containerNumber.trim().length() > 0) {
					if(isContinued){
						builder.append(" AND MST.CONNUM = ? ");
						builder.append(" AND MST.MALSTA!='RTN' ");
					}else{
						builder.append(" MST.CONNUM = ? ");
						builder.append(" AND MST.MALSTA!='RTN' ");
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
				
				if (originAirportCode!= null && originAirportCode.trim().length() > 0) {
					if(isContinued){
						builder.append("  AND MST.ORGCOD=? ");
					}else{
						builder.append(" MST.ORGCOD = ? ");
						isContinued = true;
					}
					this.setParameter(++index, originAirportCode);
				}
				
				if (destinationAirportCode!= null && destinationAirportCode.trim().length() > 0) {
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
					this.setParameter(++index, year);
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
		if(this.summary) {
			builder.append(")MST");
		}
		else {
			builder.append(")MST group by DSTCOD");
		}
	    return builder.toString();
	}

}
