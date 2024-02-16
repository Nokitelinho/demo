package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundLyinglistGroupFilterQuery extends PageableNativeQuery<MailbagVO> {

	//Added by A-5220 for ICRD-21098 ends
		private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

		/**
		 * The searchContainerFilterVO
		 */
		private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;

		/**
		 * The baseQuery
		 */
		private String baseQuery1;
		private String baseQuery2;

		/**
		 * The Constructor the Class
		 * 
		 * @param searchContainerFilterVO
		 * @param baseQuery
		 * @throws SystemException
		 */

		/*public CarditMailsFilterQuery(
				CarditEnquiryFilterVO carditEnquiryFilterVO, String baseQuery)
				throws SystemException {
			super();
			this.carditEnquiryFilterVO = carditEnquiryFilterVO;
			this.baseQuery = baseQuery;
		}*/
		//Added by A-5220 for ICRD-21098 starts
		public OutboundLyinglistGroupFilterQuery(OutboundCarditGroupMapper mapper,
				MailbagEnquiryFilterVO mailbagEnquiryFilterVO, String baseQuery1,String baseQuery2)
				throws SystemException {
			super(0,mapper);
			this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
			this.baseQuery1 = baseQuery1;
			this.baseQuery2 = baseQuery2;
		}
		//Added by A-5220 for ICRD-21098 ends

		/**
		 * @author a-1936
		 * @return
		 * 
		 */
		public String getNativeQuery() {
			 String companyCode = this.mailbagEnquiryFilterVO.getCompanyCode();
			    String ooe = this.mailbagEnquiryFilterVO.getOoe();
			    String doe = this.mailbagEnquiryFilterVO.getDoe();
			    String mailCategoryCode = this.mailbagEnquiryFilterVO.getMailCategoryCode();
			    String mailSubclass = this.mailbagEnquiryFilterVO.getMailSubclass();
			    String year = this.mailbagEnquiryFilterVO.getYear();
			    String despatchSerialNumber = this.mailbagEnquiryFilterVO
			      .getDespatchSerialNumber();
			    String receptacleSerialNumber = this.mailbagEnquiryFilterVO
			      .getReceptacleSerialNumber();
			    String scanPort = this.mailbagEnquiryFilterVO.getScanPort();
			    LocalDate scanFromDate = this.mailbagEnquiryFilterVO.getScanFromDate();
			    LocalDate scanToDate = this.mailbagEnquiryFilterVO.getScanToDate();
			    String damageFlag = this.mailbagEnquiryFilterVO.getDamageFlag();
			    String scanUser = this.mailbagEnquiryFilterVO.getScanUser();
			    String containerNumber = this.mailbagEnquiryFilterVO.getContainerNumber();
			    String flightNumber = this.mailbagEnquiryFilterVO.getFlightNumber();
			    LocalDate flightDate = this.mailbagEnquiryFilterVO.getFlightDate();
			    int carrierId = this.mailbagEnquiryFilterVO.getCarrierId();
			    String fromCarrierCode = this.mailbagEnquiryFilterVO.getFromCarrier();
			    
			    String consigmentNumber = this.mailbagEnquiryFilterVO.getConsigmentNumber();
			    String upuCode = this.mailbagEnquiryFilterVO.getUpuCode();
			    
			    String carditPresent = this.mailbagEnquiryFilterVO.getCarditPresent();
			    String mailbagId = this.mailbagEnquiryFilterVO.getMailbagId();
			    LocalDate reqDeliveryTime = this.mailbagEnquiryFilterVO.getReqDeliveryTime();
			    StringBuilder builder = new StringBuilder(this.baseQuery1);
			    StringBuilder finalQuery = null;
			    StringBuilder destinationBuilder = null;
			    StringBuilder historyBuilder = null;
			    StringBuilder flightBuild = null;
			    boolean isFlight = true;
			    boolean isTransitFlag = "Y"
			      .equals(this.mailbagEnquiryFilterVO.getTransitFlag());
			    boolean isUnion = false;
			    Date flightSqlDate = null;
			    int index = 0;
			    this.log.log(3, new Object[] { "The Damaged Status is ", damageFlag });
			    if (flightDate != null) {
			      flightSqlDate = flightDate.toSqlDate();
			    }
			    String flightDateString = String.valueOf(flightSqlDate);
			    boolean fromExportList = false;
			    if ("exportlist".equals(this.mailbagEnquiryFilterVO.getFromExportList())) {
			      fromExportList = true;
			    }
			    flightBuild = new StringBuilder();
			    if (flightSqlDate != null) {
			      flightBuild.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
			    }
			    if ((scanUser != null) && (scanUser.trim().length() > 0)) {
			      flightBuild.append(" AND SCNUSR LIKE '%").append(scanUser).append(
			        "%'");
			    }
			    if ((flightNumber != null) && (flightNumber.trim().length() > 0)) {
			      flightBuild.append("  AND FLTNUM = ? ");
			    }
			    if (carrierId > 0) {
			      flightBuild.append(" AND FLTCARIDR = ? ");
			    }
			    String flightBuilder = flightBuild.toString();
			    
			    historyBuilder = new StringBuilder(",MALHIS HIS");
			    historyBuilder.append(",MALPOADTL POADTL")
			      .append(",MALEXGOFCMST EXGOFC");
			    if ((carditPresent != null) && 
			      ("Y".equals(carditPresent))) {
			      historyBuilder.append(",MALCDTRCP RCP ");
			    }
			    historyBuilder.append(" WHERE ");
			    if ((carditPresent != null) && 
			      ("Y".equals(carditPresent))) {
			      historyBuilder.append(" MST.CMPCOD = RCP.CMPCOD AND ").append(
			        " MST.MALIDR = RCP.RCPIDR AND ");
			    }
			    historyBuilder.append(" MST.MALSEQNUM = HIS.MALSEQNUM AND ").append(" MST.CMPCOD = HIS.CMPCOD  AND ").append(" HIS.MALHISIDR = ").append(" ( SELECT MAX(MALHISIDR) ").append(" FROM MALHIS WHERE MALSEQNUM=HIS.MALSEQNUM AND").append(
			      " CMPCOD = MST.CMPCOD AND MALSTA IN (? ");
			    if ("ACP".equals(this.mailbagEnquiryFilterVO.getCurrentStatus())) {
			      historyBuilder.append(" , ? , ?  ");
			    }
			    historyBuilder.append(" ) ");
			    if ((scanPort != null) && (scanPort.trim().length() > 0)) {
			      historyBuilder.append(" AND  SCNPRT = ? ").toString();
			    }
			    historyBuilder.append(flightBuilder);
			    if ((containerNumber != null) && (containerNumber.trim().length() > 0)) {
			      historyBuilder.append(" AND CONNUM = ? ");
			    }
			    String history = " ) AND  ";
			    
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
			    if (((carrierId > 0) || (carrierId == 0)) && (
			      (flightNumber == null) || (flightNumber.trim().length() == 0)))
			    {
			      this.log.log(5, "Constructing Destination Builder");
			      destinationBuilder = new StringBuilder();
			      if (carrierId == 0) {
			        isUnion = true;
			      }
			      if ("TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			      {
			        if (isUnion) {
			          destinationBuilder.append(this.baseQuery1);
			        }
			        destinationBuilder.append(", '' POAFLG, 0 LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM");
			        if (carrierId > 0) {
			          destinationBuilder.append(",HIS.FLTCARCOD");
			        }
			        if (isUnion) {
			          destinationBuilder.append(" , '' ACPSTA , '' ARRSTA, '' TRAFLG ").append(",( SELECT ( DECODE ( APHCODUSE, 2, TWOAPHCOD, THRAPHCOD ) )  FROM SHRARLMST  WHERE CMPCOD = HIS.CMPCOD  AND ARLIDR = HIS.FLTCARIDR) FLTCARCOD").append(", NULL,  '' FRMCARCOD ");
			        }
			        destinationBuilder.append("  FROM  MALMST MST ").append(history).append(" (HIS.FLTNUM IS NULL or HIS.FLTNUM = '-1')  AND ");
			        destinationBuilder.append(" MST.SCNPRT = HIS.SCNPRT AND ")
			          .append(" MST.CMPCOD= EXGOFC.CMPCOD")
			          .append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD")
			          .append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ")
			          .append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ")
			          .append("AND POADTL.PARCOD(+) ='UPUCOD' AND ")
			          .append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ");
			      }
			      else
			      {
			        if (isUnion) {
			          destinationBuilder.append(this.baseQuery1);
			        }
			        destinationBuilder.append(",CONMST.POAFLG,CONMST.LEGSERNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.SEGSERNUM");
			        if (isUnion) {
			          destinationBuilder.append(" ,'' ACPSTA,'' ARRSTA,'' TRAFLG ").append(
			            " ,ULD.FLTCARCOD, NULL,ARPULD.FRMCARCOD ");
			        }
			        destinationBuilder.append(" FROM  MALMST MST,MALARPULDDTL ARPULD, MALARPULD ULD,MALFLTCON CONMST ").append(history).append(" HIS.CMPCOD=ARPULD.CMPCOD ").append(" AND HIS.MALSEQNUM=ARPULD.MALSEQNUM ").append(" AND HIS.CONNUM=ARPULD.CONNUM ").append(" AND HIS.SCNPRT= ARPULD.ARPCOD ").append(" AND HIS.FLTCARIDR=ARPULD.FLTCARIDR ").append(" AND HIS.FLTCARIDR = ULD.FLTCARIDR ").append(" AND HIS.SCNPRT = ULD.ARPCOD ").append(" AND HIS.CMPCOD = ULD.CMPCOD ").append(" AND ULD.ULDNUM = ARPULD.ULDNUM ").append(" AND ULD.CMPCOD=CONMST.CMPCOD ").append(" AND ULD.FLTCARIDR = CONMST.FLTCARIDR ").append(" AND ULD.ARPCOD= CONMST.ASGPRT ").append(" AND HIS.CONNUM = CONMST.CONNUM  AND").append(" CONMST.FLTNUM = '-1'  AND ").append(" MST.CMPCOD= EXGOFC.CMPCOD").append(" AND MST.ORGEXGOFC= EXGOFC.EXGOFCCOD").append(" AND EXGOFC.CMPCOD= POADTL.CMPCOD(+) ").append(" AND EXGOFC.POACOD= POADTL.POACOD(+) ").append("AND POADTL.PARCOD(+) ='UPUCOD' AND ").append(" TRUNC(SYSDATE) BETWEEN TRUNC(POADTL.VLDFRM(+)) AND TRUNC(POADTL.VLDTOO(+)) AND ");
			      }
			    }
			    if ((carrierId > 0) && (flightNumber != null) && 
			      (flightNumber.trim().length() > 0) && (flightDate != null))
			    {
			      this.log.log(5, "FLIGHT ACCEPTED");
			      builder.append(finalQuery);
			    }
			    else if ((carrierId > 0) && (
			      (flightNumber == null) || (flightNumber.trim().length() == 0)))
			    {
			      this.log.log(5, "DESTINATION ACCEPTED");
			      builder.append(destinationBuilder);
			      isFlight = false;
			    }
			    else
			    {
			      this.log.log(5, " BOTH FLIGHT AND DESTINATION ACCEPTED");
			      builder.append(finalQuery);
			    }
			    if ("ACP".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			    {
			      setParameter(++index, "ACP");
			      setParameter(++index, "ASG");
			      if ((fromExportList) && (containerNumber.startsWith("OFL"))) {
			        setParameter(++index, "OFL");
			      } else {
			        setParameter(++index, "TRA");
			      }
			    }
			    else if ("TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			    {
			      setParameter(++index, "TRA");
			    }
			    if ((scanPort != null) && (scanPort.trim().length() > 0)) {
			      setParameter(++index, scanPort);
			    }
			    if (flightSqlDate != null) {
			      setParameter(++index, flightDateString);
			    }
			    if ((flightNumber != null) && (flightNumber.trim().length() > 0)) {
			      setParameter(++index, flightNumber);
			    }
			    if (carrierId > 0) {
			      setParameter(++index, Integer.valueOf(carrierId));
			    }
			    if ((containerNumber != null) && (containerNumber.trim().length() > 0)) {
			      setParameter(++index, containerNumber);
			    }
			    if ((companyCode != null) && (companyCode.trim().length() > 0))
			    {
			      builder.append("  MST.CMPCOD=? ");
			      setParameter(++index, companyCode);
			    }
			    if (scanFromDate != null)
			    {
			      builder.append(" AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) >= ? ");
			      setParameter(++index, Integer.valueOf(Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			    }
			    if (scanToDate != null)
			    {
			      builder.append("  AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) <= ? ");
			      setParameter(++index, Integer.valueOf(Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			    }
			    if ((ooe != null) && (ooe.trim().length() > 0))
			    {
			      builder.append("  AND MST.ORGEXGOFC=? ");
			      setParameter(++index, ooe);
			    }
			    if ((doe != null) && (doe.trim().length() > 0))
			    {
			      builder.append("  AND MST.DSTEXGOFC= ? ");
			      setParameter(++index, doe);
			    }
			    if ((mailCategoryCode != null) && (mailCategoryCode.trim().length() > 0))
			    {
			      builder.append(" AND MST.MALCTG=? ");
			      setParameter(++index, mailCategoryCode);
			    }
			    if ((mailSubclass != null) && (mailSubclass.trim().length() > 0))
			    {
			      builder.append(" AND MST.MALSUBCLS = ? ");
			      setParameter(++index, mailSubclass);
			    }
			    if ((year != null) && (year.trim().length() > 0))
			    {
			      builder.append(" AND MST.YER= ?  ");
			      setParameter(++index, year);
			    }
			    if ((despatchSerialNumber != null) && 
			      (despatchSerialNumber.trim().length() > 0))
			    {
			      builder.append(" AND MST.DSN= ? ");
			      setParameter(++index, despatchSerialNumber);
			    }
			    if ((receptacleSerialNumber != null) && 
			      (receptacleSerialNumber.trim().length() > 0))
			    {
			      builder.append(" AND MST.RSN= ? ");
			      setParameter(++index, receptacleSerialNumber);
			    }
			    if ((consigmentNumber != null) && 
			      (consigmentNumber.trim().length() > 0))
			    {
			      builder.append("  AND MST.CSGDOCNUM=? ");
			      setParameter(++index, consigmentNumber);
			    }
			    if ((upuCode != null) && 
			      (upuCode.trim().length() > 0))
			    {
			      builder.append("  AND POADTL.PARVAL=? ");
			      setParameter(++index, upuCode);
			    }
			    if ((mailbagId != null) && 
			      (mailbagId.trim().length() > 0))
			    {
			      builder.append("  AND MST.MALIDR=? ");
			      setParameter(++index, mailbagId);
			    }
			    if (reqDeliveryTime != null)
			    {
			      String rqdDlvTime = reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			      if (rqdDlvTime != null)
			      {
			        if (rqdDlvTime.contains("0000")) {
			          builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
			        } else {
			          builder.append(" AND MST.REQDLVTIM = ?");
			        }
			        setParameter(++index, reqDeliveryTime);
			      }
			    }
			    if (isFlight)
			    {
			      this.log.log(5, "THE STATUS IS MAIL_STATUS_ACCEPTED");
			      builder.append(" AND SEG.ACPSTA =  'Y'   ");
			      if ((this.mailbagEnquiryFilterVO.getFromScreen() != null) && ("MAILEXPORTLIST".equals(this.mailbagEnquiryFilterVO.getFromScreen())))
			      {
			        builder.append(" AND CONMST.CONNUM=?   ");
			        setParameter(++index, containerNumber);
			      }
			    }
			    if ((damageFlag != null) && ("Y".equals(damageFlag)))
			    {
			      builder.append(" AND MST.DMGFLG = ? ");
			      setParameter(++index, damageFlag);
			    }
			    if ((carditPresent != null) && 
			      ("N".equals(carditPresent)))
			    {
			      builder.append("  AND  NOT EXISTS  ");
			      builder.append(" ( SELECT 1 FROM MALCDTRCP WHERE  ");
			      builder.append("  CMPCOD =  MST.CMPCOD AND ");
			      builder.append("  RCPIDR =  MST.MALIDR ) ");
			    }
			    if (isTransitFlag)
			    {
			      builder.append("  AND   EXISTS  ");
			      builder.append(" ( SELECT 1 FROM SHRCTYMST CTY1 WHERE  ");
			      builder.append("  CTY1.CMPCOD = MST.CMPCOD ");
			      builder.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ");
			      builder.append("  AND ( CTY1 .SRVARPCOD IS NULL OR  CTY1 .SRVARPCOD  <>  ? )   ) ");
			      setParameter(++index, this.mailbagEnquiryFilterVO.getScanPort());
			      builder.append("  AND   EXISTS  ");
			      builder.append(" ( SELECT 1 FROM  SHRCTYMST CTY2  WHERE  ");
			      builder.append("  CTY2.CMPCOD = MST.CMPCOD ");
			      builder.append("  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) ");
			      builder.append("  AND (  CTY2 .SRVARPCOD IS NULL OR  CTY2 .SRVARPCOD  <>  ?  ) )  ");
			      setParameter(++index, this.mailbagEnquiryFilterVO.getScanPort());
			    }
			    if ((isFlight) || (isUnion))
			    {
			      if ((fromCarrierCode != null) && (fromCarrierCode.trim().length() > 0))
			      {
			        builder.append(" AND SEG.FRMCARCOD = ? ");
			        setParameter(++index, fromCarrierCode);
			      }
			    }
			    else if ((fromCarrierCode != null) && 
			      (fromCarrierCode.trim().length() > 0)) {
			      if (!"TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			      {
			        builder.append(" AND ARPULD.FRMCARCOD = ? ");
			        setParameter(++index, fromCarrierCode);
			      }
			    }
			    if (isUnion)
			    {
			    
			      builder.append(" UNION ").append("(");
			      builder.append(destinationBuilder);
			      if ("ACP".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			      {
			        setParameter(++index, "ACP");
			        setParameter(++index, "ASG");
			        setParameter(++index, "OFL");
			      }
			      else if ("TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			      {
			        setParameter(++index, "TRA");
			      }
			      if ((scanPort != null) && (scanPort.trim().length() > 0)) {
			        setParameter(++index, scanPort);
			      }
			      if (flightSqlDate != null) {
			        setParameter(++index, flightDateString);
			      }
			      if ((flightNumber != null) && (flightNumber.trim().length() > 0)) {
			        setParameter(++index, flightNumber);
			      }
			      if (carrierId > 0) {
			        setParameter(++index, Integer.valueOf(carrierId));
			      }
			      if ((containerNumber != null) && (containerNumber.trim().length() > 0)) {
			        setParameter(++index, containerNumber);
			      }
			      if ((companyCode != null) && (companyCode.trim().length() > 0))
			      {
			        builder.append("  MST.CMPCOD=? ");
			        setParameter(++index, companyCode);
			      }
			      if ((ooe != null) && (ooe.trim().length() > 0))
			      {
			        builder.append("  AND MST.ORGEXGOFC=? ");
			        setParameter(++index, ooe);
			      }
			      if (scanFromDate != null)
			      {
			        builder.append(" AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) >= ? ");
			        setParameter(++index, Integer.valueOf(Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			      }
			      if (scanToDate != null)
			      {
			        builder.append("  AND TO_NUMBER(TO_CHAR(MST.SCNDAT,'YYYYMMDD')) <= ? ");
			        setParameter(++index, Integer.valueOf(Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			      }
			      if ((doe != null) && (doe.trim().length() > 0))
			      {
			        builder.append("  AND MST.DSTEXGOFC= ? ");
			        setParameter(++index, doe);
			      }
			      if ((mailCategoryCode != null) && 
			        (mailCategoryCode.trim().length() > 0))
			      {
			        builder.append(" AND MST.MALCTG=? ");
			        setParameter(++index, mailCategoryCode);
			      }
			      if ((mailSubclass != null) && (mailSubclass.trim().length() > 0))
			      {
			        builder.append(" AND MST.MALSUBCLS = ? ");
			        setParameter(++index, mailSubclass);
			      }
			      if ((year != null) && (year.trim().length() > 0))
			      {
			        builder.append(" AND MST.YER= ?  ");
			        setParameter(++index, year);
			      }
			      if ((despatchSerialNumber != null) && 
			        (despatchSerialNumber.trim().length() > 0))
			      {
			        builder.append(" AND MST.DSN= ? ");
			        setParameter(++index, despatchSerialNumber);
			      }
			      if ((receptacleSerialNumber != null) && 
			        (receptacleSerialNumber.trim().length() > 0))
			      {
			        builder.append(" AND MST.RSN= ? ");
			        setParameter(++index, receptacleSerialNumber);
			      }
			      if ((consigmentNumber != null) && 
			        (consigmentNumber.trim().length() > 0))
			      {
			        builder.append("  AND MST.CSGDOCNUM=? ");
			        setParameter(++index, consigmentNumber);
			      }
			      if ((upuCode != null) && 
			        (upuCode.trim().length() > 0))
			      {
			        builder.append("  AND POADTL.PARVAL=? ");
			        setParameter(++index, upuCode);
			      }
			      if ((mailbagId != null) && 
			        (mailbagId.trim().length() > 0))
			      {
			        builder.append("  AND MST.MALIDR=? ");
			        setParameter(++index, mailbagId);
			      }
			      if (reqDeliveryTime != null)
			      {
			        String rqdDlvTime = reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			        if (rqdDlvTime != null)
			        {
			          if (rqdDlvTime.contains("0000")) {
			            builder.append(" AND TRUNC(MST.REQDLVTIM) = ?");
			          } else {
			            builder.append(" AND MST.REQDLVTIM = ?");
			          }
			          setParameter(++index, reqDeliveryTime);
			        }
			      }
			      if ((damageFlag != null) && 
			        ("Y".equals(damageFlag)))
			      {
			        builder.append(" AND MST.DMGFLG = ? ");
			        setParameter(++index, damageFlag);
			      }
			      if ((fromCarrierCode != null) && 
			        (fromCarrierCode.trim().length() > 0)) {
			        if (!"TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))
			        {
			          builder.append(" AND ARPULD.FRMCARCOD = ? ");
			          setParameter(++index, fromCarrierCode);
			        }
			      }
			      if ((carditPresent != null) && 
			        ("N".equals(carditPresent)))
			      {
			        builder.append("  AND  NOT EXISTS  ");
			        builder.append(" ( SELECT 1 FROM MALCDTRCP WHERE  ");
			        builder.append("  CMPCOD =  MST.CMPCOD AND ");
			        builder.append("  RCPIDR =  MST.MALIDR ) ");
			      }
			      if (isTransitFlag)
			      {
			        builder.append("  AND   EXISTS  ");
			        builder.append(" ( SELECT 1 FROM SHRCTYMST CTY1 WHERE  ");
			        builder.append("  CTY1.CMPCOD = MST.CMPCOD ");
			        builder.append("  AND CTY1.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.ORGEXGOFC) ");
			        builder.append("  AND (   CTY1.SRVARPCOD  IS  NULL  OR  CTY1.SRVARPCOD <>  ?  ) ) ");
			        setParameter(++index, this.mailbagEnquiryFilterVO.getScanPort());
			        builder.append("  AND   EXISTS  ");
			        builder.append(" ( SELECT 1 FROM SHRCTYMST CTY2 WHERE  ");
			        builder.append("  CTY2.CMPCOD = MST.CMPCOD ");
			        builder.append("  AND CTY2.CTYCOD = (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MST.CMPCOD  AND EXGOFCCOD = MST.DSTEXGOFC) ");
			        builder.append("  AND  (  CTY2.SRVARPCOD  IS  NULL OR CTY2 .SRVARPCOD <>  ?  ) )");
			        setParameter(++index, this.mailbagEnquiryFilterVO.getScanPort());
			      }
			      if (("ACP".equals(this.mailbagEnquiryFilterVO.getCurrentStatus())) || 
			        ("TRA".equals(this.mailbagEnquiryFilterVO.getCurrentStatus()))) {
			        builder.append(")ORDER BY CMPCOD,MALIDR,ORGEXGOFC,DSTEXGOFC,YER,DSN )");
			      } else {
			        builder.append(")");
			      }
			      
			      builder.append(" ");
				  builder.append(this.baseQuery2);
			    }
			   
			return builder.toString();
		}

	}
