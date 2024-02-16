/*
 * ULDTransactionFilterQuery.java Created on Jul 2, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1936
 *
 */
public class ULDTransactionFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("ULD");

	private TransactionFilterVO uldTransactionFilterVO;

	/**
	 *
	 */
	private String baseQuery;

	private boolean isOracleDateSource;
	/**
	 *
	 * @param uldListFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ULDTransactionFilterQuery(TransactionFilterVO uldTransactionFilterVO, String baseQuery, boolean isOracleDateSource)
			throws SystemException {
		super();
		this.uldTransactionFilterVO = uldTransactionFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDateSource = isOracleDateSource;
	}

	/**
	 * @return
	 */
	public String getNativeQuery(){
		log.entering("ULDTransactionFilterQuery", "getNativeQuery");
		String companyCode = uldTransactionFilterVO.getCompanyCode();
		StringBuilder sbd = new StringBuilder(baseQuery);
		int index = 0;
		this.setParameter(++index, companyCode);
		if (uldTransactionFilterVO.getUldNumber() != null && uldTransactionFilterVO.getUldNumber().trim().length() != 0) {
			sbd.append(" AND T.ULDNUM = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getUldNumber());
		}
		if (uldTransactionFilterVO.getTransactionType() != null && !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getTransactionType()))) {
			sbd.append(" AND T.TXNTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTransactionType());
		}
		if (uldTransactionFilterVO.getUldTypeCode() != null && uldTransactionFilterVO.getUldTypeCode().trim().length() != 0) {
			sbd.append(" AND T.ULDTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getUldTypeCode());
		}
		if (uldTransactionFilterVO.getPartyType() != null && uldTransactionFilterVO.getPartyType().trim().length() != 0) {
			sbd.append(" AND T.PTYTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getPartyType());
		}
		if (uldTransactionFilterVO.getFromPartyCode() != null && uldTransactionFilterVO.getFromPartyCode().trim().length() != 0) {
			sbd.append(" AND T.RTNPTYCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getFromPartyCode());
		}

		if (uldTransactionFilterVO.getToPartyCode() != null && uldTransactionFilterVO.getToPartyCode().trim().length() != 0) {
			sbd.append(" AND T.PTYCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getToPartyCode());
		}
		if (uldTransactionFilterVO.getTransactionStationCode() != null && uldTransactionFilterVO.getTransactionStationCode().trim().length() != 0) {
			sbd.append(" AND T.TXNARPCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTransactionStationCode());
		}
		if (uldTransactionFilterVO.getReturnedStationCode() != null && uldTransactionFilterVO.getReturnedStationCode().trim().length() != 0) {
			sbd.append(" AND T.RTNARPCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getReturnedStationCode());
		}
		if (uldTransactionFilterVO.getTransactionStatus() != null
				&& !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getTransactionStatus()))) {
			sbd.append("  AND T.TXNSTA =  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTransactionStatus());
		}
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			sbd.append("  AND T.TXNDAT >=  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			sbd.append("  AND T.TXNDAT <=  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
		}
		if (uldTransactionFilterVO.getReturnFromDate() != null) {
			sbd.append("  AND T.RTNDAT >= ? ");
			this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getReturnToDate() != null) {
			sbd.append("  AND T.RTNDAT <= ?  ");
			this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
		}
		//added by a-3045 for CR QF1142 starts
		if (uldTransactionFilterVO.getMucStatus() != null && 
				uldTransactionFilterVO.getMucStatus().trim().length() != 0) {
			if (("G").equals(uldTransactionFilterVO.getMucStatus())) {
				sbd.append("  AND (T.MUCSNT = 'S' OR T.MUCSNT = 'R' OR T.MUCSNT = 'U')");
			}else if(("R").equals(uldTransactionFilterVO.getMucStatus())){
				sbd.append("  AND T.MUCSNT = 'Q'");
			}else if(("N").equals(uldTransactionFilterVO.getMucStatus())){
				sbd.append("  AND T.MUCSNT = 'N'");
			}
			/*sbd.append("  AND T.MUCSNT =  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getMucIataStatus());*/
		}
		/*if (uldTransactionFilterVO.getMucDate() != null) {
			sbd.append("  AND T.MUCDAT <= ?  ");
			this.setParameter(++index, uldTransactionFilterVO.getMucDate().toCalendar());
		}*/
		if (uldTransactionFilterVO.getMucReferenceNumber() != null && 
				uldTransactionFilterVO.getMucReferenceNumber().trim().length() != 0) {
			sbd.append("  AND T.MUCREFNUM =  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getMucReferenceNumber());
		}
		//added by a-3045 for CR QF1142 ends
		//added by a-3045 for bug26528 starts,28658
		//Added by A-2052 for the bug 104612 starts
		//sbd.append("  AND  SUBSTR(T.RTNCRN, 1, 4) LIKE  ? ");
		//commented for ICRD-31853
		/*sbd.append("  AND  ( SUBSTR(T.RTNCRN, 1, 4) LIKE ? or SUBSTR(T.CRN, 1, 4) LIKE ? ) ");
		//Added by A-2052 for the bug 104612 ends
		if (uldTransactionFilterVO.getPrefixControlReceiptNo() != null && 
				uldTransactionFilterVO.getPrefixControlReceiptNo().trim().length() != 0) {
			StringBuilder preCrn = 
				new StringBuilder(uldTransactionFilterVO.getPrefixControlReceiptNo()).append("%-");		
			this.setParameter(++index,preCrn.toString());
			this.setParameter(++index,preCrn.toString());
		}else{
			this.setParameter(++index, "%-");
			this.setParameter(++index, "%-");
		}
		//Added by A-2052 for the bug 104612 starts
		//sbd.append("  AND SUBSTR(T.RTNCRN, 5, 1) LIKE  ? ");
		sbd.append( " AND ( SUBSTR(T.RTNCRN, 5, 1) LIKE ? or SUBSTR(T.CRN, 5, 1) LIKE ?)");
		//Added by A-2052 for the bug 104612 ends
		if (uldTransactionFilterVO.getMidControlReceiptNo() != null
				&& uldTransactionFilterVO.getMidControlReceiptNo().trim().length() != 0) {				
			this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
			this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
		}else{
			this.setParameter(++index,"%");
			this.setParameter(++index,"%");
		}
		//Added by A-2052 for the bug 104612 starts
		//sbd.append(" AND SUBSTR(T.RTNCRN, 6, 7) LIKE ?");
		sbd.append(" AND ( SUBSTR(T.RTNCRN, 6,7) LIKE ? or SUBSTR(T.CRN, 6,7) LIKE ?) ");
		//Added by A-2052 for the bug 104612 ends
		if (uldTransactionFilterVO.getControlReceiptNo() != null
				&& uldTransactionFilterVO.getControlReceiptNo().trim().length() != 0) {	
			StringBuilder sufCrn = new StringBuilder("%").append(uldTransactionFilterVO.getControlReceiptNo()).append("%");			
			this.setParameter(++index,sufCrn.toString());
			this.setParameter(++index,sufCrn.toString());
		}else{
			this.setParameter(++index,"%");
			this.setParameter(++index,"%");
		}*/
		//added by a-3045 for bug26528 ends		
		Collection<String> uldNumbers = uldTransactionFilterVO.getUldNumbers();
		int iCount = 0;
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount == 0) {
					sbd.append(" AND T.ULDNUM IN (  ? ");
					this.setParameter(++index, uldNum);
				} else {
					sbd.append(" , ? ");
					this.setParameter(++index, uldNum);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				sbd.append(" ) ");
			}
		}

		if("T".equals(uldTransactionFilterVO.getTransactionStatus()) ||
				TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getTransactionStatus()) ||
				uldTransactionFilterVO.getTransactionStatus() == null || uldTransactionFilterVO.getTransactionStatus().trim().length() == 0){
			String dbCurrentDate;
			if(isOracleDateSource){
				dbCurrentDate = "SYSDATE";
			}else{
				dbCurrentDate = "SYSDATE()";
			}
			//modifications as apart of buisness change in listing the loan borrow details on 24Mar09
				sbd.append(" UNION ALL ")
				  .append(" SELECT ULDNUM , CMPCOD, TXNREFNUM ,TXNTYP , TXNNAT , TXNSTA ,AWBRMK AS AWBNUM,")
				  .append(" EMTSTA AS EMTSTA ,TXNDAT, TXNARPCOD, PTYTYP, PTYCOD, PTYNAM, PTYIDR, RTNDAT ,")
				  .append(" RTNARPCOD , RTNPTYIDR ,AGRCURCOD , INVREFNUM , TXNRMK ,RTNRMK , RTNPTYCOD ,LSTUPDUSR ,")
				  .append(" LSTUPDTIM,DMGSTA , DSTAPTCOD , CRN , RTNCRN, CONCOD,POLOWN, MUCDAT, MUCREFNUM, MUCSNT ,")				
				  .append(" 0 AS WVRAMT,DMRRAT,CASE WHEN POLOWN = 'N' AND CONCOD <> 'ZZZ' THEN  TAXAMT ELSE 0 END TAXAMT ,")
				  .append(" CASE WHEN POLOWN = 'N' AND CONCOD <> 'ZZZ' THEN CASE WHEN DMRFQY = 'D'  THEN ")  
				  .append("  CASE ")
				   .append(" WHEN FRELONPRD = 0  AND ( to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) = 0 THEN  ")
				   .append(" DMRRAT WHEN FRELONPRD = 0   AND (to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) > 0 THEN  ")
				   .append("  (to_number(TO_CHAR(" )
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd'))   - to_number(TO_CHAR(txndat,'yyyymmdd')) ) * dmrrat")
				  .append("  WHEN ( to_number(TO_CHAR(" )
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) > frelonprd THEN  ")
				  .append(" ( ( to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) - frelonprd ) * dmrrat ")  
				  .append("  ELSE 0 END ")   
    			  .append("  WHEN DMRFQY = 'W' THEN ")
				   .append("   CASE  WHEN ( to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) >= frelonprd ")
				  .append("    THEN (FLOOR((((to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(TXNDAT,'yyyymmdd')) ))/ 7))+1 - FRELONPRD)* DMRRAT ")
				  .append(" ELSE 0 END ")
				  .append(" WHEN DMRFQY = 'F' THEN ")
				  .append("  CASE  WHEN ( to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) >= frelonprd ")
				  .append("   THEN (CEIL((((to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(TXNDAT,'yyyymmdd')) ))/ 14))+1- FRELONPRD)* DMRRAT ")
				  .append(" ELSE 0 END ")
				  .append(" WHEN DMRFQY = 'M' THEN ")
				   .append(" CASE WHEN ( to_number(TO_CHAR(")
				  .append(dbCurrentDate)
				  .append(",'yyyymmdd')) - to_number(TO_CHAR(txndat,'yyyymmdd')) ) >= frelonprd ");
				  if(isOracleDateSource){					  
					  sbd.append("THEN (TRUNC (MONTHS_BETWEEN (TRUNC(SYSDATE), (TRUNC(TXNDAT) - FRELONPRD)) )+1) * DMRRAT");  
				  }
				  else{
					  sbd.append("  THEN (TRUNC (MONTHS_BETWEEN (to_date(TO_CHAR(to_number(TO_CHAR(")
					  .append(dbCurrentDate)
					  .append(",'yyyymmdd'))),'yyyymmdd')  ,to_date(TO_CHAR(to_number(TO_CHAR(txndat,'yyyymmdd')) - FRELONPRD),'yyyymmdd')))+1) * DMRRAT ") ;					
				  }
				  sbd.append(" ELSE 0 END ")
				  .append(" END ")
				  .append("  WHEN POLOWN <> 'N' THEN 0 ")
				  .append(" END DMRAMT , 0 AS RAT FROM( ")  
				  .append("  SELECT MIN(PRI) OVER(PARTITION BY A.ULDNUM,A.TXNREFNUM)MINPRI,A.* FROM( ")
				  .append("  SELECT CASE  ")
				  .append("  WHEN AGRDTL.PTYTYP = TXN.PTYTYP AND  AGRDTL.PTYCOD = CASE WHEN ")
				  .append("  TXN.TXNTYP = 'L' THEN TXN.PTYCOD ELSE TXN.RTNPTYCOD END ")
				  .append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND AGRDTL.ARPCOD = TXN.TXNARPCOD ")				  
				  .append("  THEN 1  ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND AGRDTL.PTYCOD = CASE WHEN ")
				  .append("  TXN.TXNTYP = 'L' THEN TXN.PTYCOD ELSE TXN.RTNPTYCOD END ")
				  .append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND  ")
				  .append("  AGRDTL.ARPCOD = 'HDQ' ")
				  .append("  THEN 2 ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND AGRDTL.PTYCOD = CASE WHEN ")
				  .append("  TXN.TXNTYP = 'L' THEN TXN.PTYCOD ELSE TXN.RTNPTYCOD END ")
				  .append("  THEN 3  ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND  AGRDTL.PTYCOD IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS') ")
				  .append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND  ")
				  .append("  AGRDTL.ARPCOD = TXN.TXNARPCOD THEN 4 ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND AGRDTL.PTYCOD IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS') ")				  
				  .append("  AND TXN.TXNTYP = 'L' AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND  ")
				  .append("  AGRDTL.ARPCOD = 'HDQ' THEN 5  ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND AGRDTL.PTYCOD IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS') THEN 6 ")
				  .append("  WHEN 	AGRDTL.PTYTYP = TXN.PTYTYP AND  AGRDTL.PTYCOD = 'ALL' ")
				  .append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND  ")
				  .append("  AGRDTL.ARPCOD = TXN.TXNARPCOD THEN 7   ")
				  .append("  WHEN  	AGRDTL.PTYTYP = TXN.PTYTYP AND AGRDTL.PTYCOD = 'ALL'  ")
				  .append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND   ")
				  .append("  AGRDTL.ARPCOD = 'HDQ' THEN 8 ")
				  .append("   WHEN   AGRDTL.PTYCOD = 'ALL'  ")
				  .append("  THEN 9 ")
				  .append("  END PRI ,TXN.TXNREFNUM, ")
				  .append(" TXN.ULDNUM,TXN.CMPCOD,TXN.TXNTYP AS TXNTXNTYP, ")
				  .append(" TXN.TXNNAT,TXN.TXNSTA,TXN.AWBRMK,TXN.EMTSTA ,TXN.TXNDAT,TXN.TXNARPCOD,TXN.PTYTYP AS TXNPTYTYP, ")
				  .append(" TXN.PTYCOD AS TXNPTYCOD,TXN.PTYNAM AS TXN,PTYNAM,TXN.PTYIDR AS TXNPTYIDR,TXN.RTNDAT ,")
				  .append(" TXN.RTNARPCOD,TXN.RTNPTYIDR,AGRDTL.CURCOD ,TXN.INVREFNUM,TXN.TXNRMK,TXN.RTNRMK,TXN.PTYIDR,")
				  .append(" TXN.PTYCOD,TXN.PTYTYP,TXN.TXNTYP,TXN.RTNPTYCOD ,TXN.LSTUPDUSR , TXN.LSTUPDTIM ,TXN.DMGSTA ,  ")
				  .append(" TXN.DSTAPTCOD , TXN.CRN , TXN.RTNCRN, TXN.CONCOD,TXN.POLOWN, TXN.MUCDAT, TXN.MUCREFNUM,  ")
				  .append(" TXN.MUCSNT ,0 AS WVRAMT,SUBSTR(TXN.ULDNUM,1,3) AS ULDTYPCOD,TXN.TOTAMT AS TOTAMT,    ")
				   .append("	TO_NUMBER(FUN_GET_ULD_DEMURAGE_RATE(TXN.CMPCOD,TXN.ULDTYP,TXN.TXNARPCOD,TRUNC(" )
				  .append(dbCurrentDate)
				  .append("),'A',TXN.RTNPTYCOD,TXN.PTYTYP,TXN.PTYCOD,'DMRRAT'))DMRRAT,")		 
				  .append(" FUN_GET_ULD_DEMURAGE_RATE(TXN.CMPCOD,TXN.ULDTYP,TXN.TXNARPCOD,TRUNC(")
				  .append(dbCurrentDate)
				  .append("),'A',TXN.RTNPTYCOD,TXN.PTYTYP,TXN.PTYCOD,'DMRFQY') DMRFQY, ")
				  .append(" TO_NUMBER(FUN_GET_ULD_DEMURAGE_RATE(TXN.CMPCOD,TXN.ULDTYP,TXN.TXNARPCOD,TRUNC(")
				  .append(dbCurrentDate)
				  	.append("),'A',TXN.RTNPTYCOD,TXN.PTYTYP,TXN.PTYCOD,'DMRTAXAMT'))TAXAMT, ")
				  .append(" TO_NUMBER(FUN_GET_ULD_DEMURAGE_RATE(TXN.CMPCOD,TXN.ULDTYP,TXN.TXNARPCOD,TRUNC(")
				  .append(dbCurrentDate)
				  .append("),'A',TXN.RTNPTYCOD,TXN.PTYTYP,TXN.PTYCOD,'DMRFRELONPRD'))FRELONPRD, ")
				  .append(" FUN_GET_ULD_DEMURAGE_RATE(TXN.CMPCOD,TXN.ULDTYP,TXN.TXNARPCOD,TRUNC(" )
				  .append(dbCurrentDate)
				  .append("),'A',TXN.RTNPTYCOD,TXN.PTYTYP,TXN.PTYCOD,'DMRCURCOD')AGRCURCOD  ")
				  .append("  FROM ( SELECT AGR.CMPCOD,AGR.AGRMNTNUM ,AGR.PTYTYP , AGR.TXNTYP , AGR.PTYCOD ,AGR.FRELONPRD, ")
				  .append(" AGR.CURCOD  ,AGR.DMRRAT ,AGR.TAXAMT ,AGR.DMRFQY,DTL.DMRFQY DTLDMRFQY , ")
				  .append(" DTL.DMRRAT AS DTLDMRRAT ,AGR.AGRMNTFRMDAT,AGR.AGRMNTTOODAT, ")
				  .append(" DTL.AGRMNTFRMDAT DTLAGRMNTFRMDAT,DTL.AGRMNTTOODAT DTLAGRMNTTOODAT, ")
				  .append(" DTL.FRELONPRD AS  DTLFRELONPRD,DTL.TAXAMT DTLTAXAMT,")
				  .append(" DTL.CURCOD AS DTLCURCOD,DTL.ARPCOD AS ARPCOD,DTL.ULDTYPCOD ")
				  .append(" FROM ULDAGRMNT AGR LEFT OUTER JOIN ULDAGRMNTDTL DTL ")
				  .append(" ON AGR.CMPCOD = DTL.CMPCOD AND AGR.AGRMNTNUM = DTL.AGRMNTNUM ")
				  .append(" WHERE AGR.AGRMNTSTA = 'A' )AGRDTL , ULDTXNMST TXN ")
				  .append(" WHERE TXN.CMPCOD = AGRDTL.CMPCOD ")
				  .append(" AND AGRDTL.TXNTYP = TXN.TXNTYP ")
				 // .append(" AND TXN.POLOWN = 'N' AND TXN.CONCOD <> 'ZZZ' ")
				  .append("	AND (AGRDTL.PTYCOD = TXN.PTYCOD AND AGRDTL.PTYTYP = TXN.PTYTYP AND TXN.TXNTYP = 'L' ")
				  .append(" OR  AGRDTL.PTYCOD = TXN.RTNPTYCOD AND AGRDTL.PTYTYP = TXN.PTYTYP AND TXN.TXNTYP = 'B' ")
				  .append(" OR TXN.PTYTYP = 'A' AND AGRDTL.PTYCOD = 'ALL AIRLINES' ")
				  .append(" OR  TXN.PTYTYP = 'G' AND AGRDTL.PTYCOD = 'ALL AGENTS' ")
				  .append(" OR  TXN.PTYTYP = 'O' AND AGRDTL.PTYCOD = 'ALL OTHERS' ")
				  .append(" OR  AGRDTL.PTYTYP = 'L' AND AGRDTL.PTYCOD = 'ALL') ")
				  .append("   AND (TRUNC(TXN.TXNDAT) BETWEEN TRUNC(AGRDTL.AGRMNTFRMDAT) ")
				  .append(" AND COALESCE(TRUNC(AGRDTL.AGRMNTTOODAT),TO_DATE('01-DEC-2999','DD-MON-RRRR'))) ")
				  .append(" AND TXN.TXNSTA = 'T' ")
				   .append(" AND TXN.CMPCOD = ? ");	
				//modifications as apart of buisness change in listing the loan borrow details on 24Mar09 ends
				this.setParameter(++index, companyCode);				  
				if (uldTransactionFilterVO.getUldNumber() != null && uldTransactionFilterVO.getUldNumber().trim().length() != 0) {
					sbd.append(" AND TXN.ULDNUM = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getUldNumber());
				}
				if (uldTransactionFilterVO.getTransactionType() != null && !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getTransactionType()))) {
					sbd.append(" AND TXN.TXNTYP = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTransactionType());
				}
				if (uldTransactionFilterVO.getUldTypeCode() != null && uldTransactionFilterVO.getUldTypeCode().trim().length() != 0) {
					sbd.append(" AND TXN.ULDTYP = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getUldTypeCode());
				}
				if (uldTransactionFilterVO.getPartyType() != null && uldTransactionFilterVO.getPartyType().trim().length() != 0) {
					sbd.append(" AND TXN.PTYTYP = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getPartyType());
				}
				if (uldTransactionFilterVO.getFromPartyCode() != null && uldTransactionFilterVO.getFromPartyCode().trim().length() != 0) {
					sbd.append(" AND TXN.RTNPTYCOD = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getFromPartyCode());
				}
				if (uldTransactionFilterVO.getToPartyCode() != null && uldTransactionFilterVO.getToPartyCode().trim().length() != 0) {
					sbd.append(" AND TXN.PTYCOD = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getToPartyCode());
				}
				if (uldTransactionFilterVO.getTransactionStationCode() != null && uldTransactionFilterVO.getTransactionStationCode().trim().length() != 0) {
					sbd.append(" AND TXN.TXNARPCOD = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTransactionStationCode());
				}
				if (uldTransactionFilterVO.getReturnedStationCode() != null && uldTransactionFilterVO.getReturnedStationCode().trim().length() != 0) {
					sbd.append(" AND TXN.RTNARPCOD = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getReturnedStationCode());
				}
				if (uldTransactionFilterVO.getTxnFromDate() != null) {
					sbd.append("  AND TXN.TXNDAT >=  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
				}
				if (uldTransactionFilterVO.getTxnToDate() != null) {
					sbd.append("  AND TXN.TXNDAT <=  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
				}
				if (uldTransactionFilterVO.getReturnFromDate() != null) {
					sbd.append("  AND TXN.RTNDAT >= ? ");
					this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
				}
				if (uldTransactionFilterVO.getReturnToDate() != null) {
					sbd.append("  AND TXN.RTNDAT <= ?  ");
					this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
				}
				//added by a-3045 for CR QF1142 starts
				
				if (uldTransactionFilterVO.getMucStatus() != null
						&& uldTransactionFilterVO.getMucStatus().trim().length() != 0) {
					if (("G").equals(uldTransactionFilterVO.getMucStatus())) {
						sbd.append("  AND (TXN.MUCSNT = 'S' OR TXN.MUCSNT = 'R' OR TXN.MUCSNT = 'U')");
					}else if(("R").equals(uldTransactionFilterVO.getMucStatus())){
						sbd.append("  AND TXN.MUCSNT = 'Q'");
					}else if(("N").equals(uldTransactionFilterVO.getMucStatus())){
						sbd.append("  AND TXN.MUCSNT = 'N'");
					}
				}
				
				
				if ((uldTransactionFilterVO.getMucIataStatus() != null
						&& uldTransactionFilterVO.getMucIataStatus().trim().length() != 0) && 
							!("R".equals(uldTransactionFilterVO.getMucIataStatus())) && 
								!("S".equals(uldTransactionFilterVO.getMucIataStatus())) && 
									!("U".equals(uldTransactionFilterVO.getMucIataStatus()))) {
					sbd.append("  AND TXN.MUCSNT =  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getMucIataStatus());
				}
				//added by T-1917 for BUG ICRD-16046 starts
				if("R".equals(uldTransactionFilterVO.getMucIataStatus()) &&
						uldTransactionFilterVO.getMucIataStatus() != null){
					sbd.append(" AND TXN.MUCSNT = 'R'");
					}
				if("S".equals(uldTransactionFilterVO.getMucIataStatus()) &&
						uldTransactionFilterVO.getMucIataStatus() != null){
					sbd.append(" AND TXN.MUCSNT = 'S'");
					}
				if("U".equals(uldTransactionFilterVO.getMucIataStatus()) &&
						uldTransactionFilterVO.getMucIataStatus() != null){
					sbd.append(" AND TXN.MUCSNT = 'U'");
					}
				if (uldTransactionFilterVO.getMucDate() != null) {
					sbd.append("  AND TXN.MUCDAT = ?  ");
					this.setParameter(++index, uldTransactionFilterVO.getMucDate().toCalendar());
				}
				//added by t-1917 for BUG ICRD-16046 Ends				
				if (uldTransactionFilterVO.getMucReferenceNumber() != null
						&& uldTransactionFilterVO.getMucReferenceNumber().trim().length() != 0) {
					sbd.append("  AND TXN.MUCREFNUM =  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getMucReferenceNumber());
				}
				//added by a-3045 for CR QF1142 ends
				//added by a-3045 for bug26528 starts
				//commented for ICRD-31853
				/*sbd.append("  AND  SUBSTR(TXN.CRN, 1, 4) LIKE  ? ");
				if (uldTransactionFilterVO.getPrefixControlReceiptNo() != null && 
						uldTransactionFilterVO.getPrefixControlReceiptNo().trim().length() != 0) {
					StringBuilder preCrn = 
						new StringBuilder(uldTransactionFilterVO.getPrefixControlReceiptNo()).append("%-");		
					this.setParameter(++index,preCrn.toString());
				}else{
					this.setParameter(++index, "%-");
				}
				sbd.append("  AND SUBSTR(TXN.CRN, 5, 1) LIKE  ? ");
				if (uldTransactionFilterVO.getMidControlReceiptNo() != null	&& 
						uldTransactionFilterVO.getMidControlReceiptNo().trim().length() != 0) {				
					this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
				}else{
					this.setParameter(++index,"%");
				}
				sbd.append(" AND SUBSTR(TXN.CRN, 6, 7) LIKE ?");
				if (uldTransactionFilterVO.getControlReceiptNo() != null && 
						uldTransactionFilterVO.getControlReceiptNo().trim().length() != 0) {	
					StringBuilder sufCrn = 
						new StringBuilder("%").append(uldTransactionFilterVO.getControlReceiptNo()).append("%");			
					this.setParameter(++index,sufCrn.toString());
				}else{
					this.setParameter(++index,"%");
				}*/
				//added by a-3045 for bug26528 ends	
				iCount = 0;
				if (uldNumbers != null && uldNumbers.size() > 0) {
					for (String uldNum : uldNumbers) {
						if (iCount == 0) {
							sbd.append(" AND TXN.ULDNUM IN (  ? ");
							this.setParameter(++index, uldNum);
						} else {
							sbd.append(" , ? ");
							this.setParameter(++index, uldNum);
						}
						iCount = 1;
					}
					if (iCount == 1) {
						sbd.append(" ) ");
					}
				}
			sbd.append("  ORDER BY TXN.ULDNUM , TXN.TXNREFNUM)A)B WHERE B.PRI = B.MINPRI ");
				  
		}
		return sbd.toString();
	}
}
