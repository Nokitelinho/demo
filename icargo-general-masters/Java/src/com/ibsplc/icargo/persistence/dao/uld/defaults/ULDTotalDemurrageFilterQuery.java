/*
 * ULDTotalDemurrageFilterQuery.java Created on Jul 2, 2008
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
public class ULDTotalDemurrageFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("ULD");

	private TransactionFilterVO uldTransactionFilterVO;

	/**
	 * 
	 */
	private String baseQuery;

	/**
	 * 
	 * @param uldListFilterVO
	 * @param baseQuery	 
	 * @throws SystemException
	 */
	public ULDTotalDemurrageFilterQuery(
			TransactionFilterVO uldTransactionFilterVO, String baseQuery)
			throws SystemException {
		super();
		this.uldTransactionFilterVO = uldTransactionFilterVO;
		this.baseQuery = baseQuery;
	}

	/**
	 * @return
	 */
	public String getNativeQuery() {
		log.entering("ULDTotalDemurrageFilterQuery", "getNativeQuery");
		String companyCode = uldTransactionFilterVO.getCompanyCode();
		StringBuilder sbd = new StringBuilder(baseQuery);
		int index = 0;
		this.setParameter(++index, companyCode);
		if (uldTransactionFilterVO.getUldNumber() != null
				&& uldTransactionFilterVO.getUldNumber().trim().length() != 0) {
			sbd.append(" AND T.ULDNUM = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getUldNumber());
		}
		if (!(TransactionFilterVO.TRANSACTION_TYPE_ALL
				.equals(uldTransactionFilterVO.getTransactionType()))) {
			sbd.append(" AND T.TXNTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getTransactionType());
		}
		if (uldTransactionFilterVO.getUldTypeCode() != null
				&& uldTransactionFilterVO.getUldTypeCode().trim().length() != 0) {
			sbd.append(" AND T.ULDTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getUldTypeCode());
		}
		if (uldTransactionFilterVO.getPartyType() != null
				&& uldTransactionFilterVO.getPartyType().trim().length() != 0) {
			sbd.append(" AND T.PTYTYP = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getPartyType());
		}
		if (uldTransactionFilterVO.getFromPartyCode() != null
				&& uldTransactionFilterVO.getFromPartyCode().trim().length() != 0) {
			sbd.append(" AND T.RTNPTYCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getFromPartyCode());
		}

		if (uldTransactionFilterVO.getToPartyCode() != null
				&& uldTransactionFilterVO.getToPartyCode().trim().length() != 0) {
			sbd.append(" AND T.PTYCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getToPartyCode());
		}
		if (uldTransactionFilterVO.getTransactionStationCode() != null
				&& uldTransactionFilterVO.getTransactionStationCode().trim()
						.length() != 0) {
			sbd.append(" AND T.TXNARPCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getTransactionStationCode());
		}
		if (uldTransactionFilterVO.getReturnedStationCode() != null
				&& uldTransactionFilterVO.getReturnedStationCode().trim()
						.length() != 0) {
			sbd.append(" AND T.RTNARPCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getReturnedStationCode());
		}
		if (uldTransactionFilterVO.getTransactionStatus() != null
				&& !(TransactionFilterVO.TRANSACTION_TYPE_ALL
						.equals(uldTransactionFilterVO.getTransactionStatus()))) {
			sbd.append("  AND T.TXNSTA =  ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getTransactionStatus());
		}
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			sbd.append("  AND T.TXNDAT >=  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTxnFromDate()
					.toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			sbd.append("  AND T.TXNDAT <=  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getTxnToDate()
					.toCalendar());
		}
		if (uldTransactionFilterVO.getReturnFromDate() != null) {
			sbd.append("  AND T.RTNDAT >= ? ");
			this.setParameter(++index, uldTransactionFilterVO
					.getReturnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getReturnToDate() != null) {
			sbd.append("  AND T.RTNDAT <= ?  ");
			this.setParameter(++index, uldTransactionFilterVO.getReturnToDate()
					.toCalendar());
		}
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
		if ("T".equals(uldTransactionFilterVO.getTransactionStatus())
				|| TransactionFilterVO.TRANSACTION_TYPE_ALL
						.equals(uldTransactionFilterVO.getTransactionStatus())
				|| uldTransactionFilterVO.getTransactionStatus() == null
				|| uldTransactionFilterVO.getTransactionStatus().trim()
						.length() == 0) {
			sbd
					.append(" UNION ALL ")
					.append(" SELECT CASE ")
					.append("  WHEN C.POLOWN = 'N'  AND C.CONCOD <> 'ZZZ' AND TRUNC(SYSDATE) - TRUNC(C.TXNDAT) >= COALESCE(C.FRELONPRD,AGR3.FRELONPRD) THEN ")
					.append("  CASE ")
					.append("   WHEN COALESCE (C.DMRFQY, AGR3.DMRFQY )= 'D'  THEN   ")					
					.append(" (((( TRUNC(SYSDATE)-TRUNC(C.TXNDAT))+1-COALESCE(C.FRELONPRD,AGR3.FRELONPRD))*  ")					
					.append(" COALESCE(C.DMRRAT,AGR3.DMRRAT))*COALESCE(C.TAXAMT,AGR3.TAXAMT)/100)+ ")
					.append("  (  ((TRUNC(SYSDATE)-TRUNC(C.TXNDAT))+1-COALESCE(C.FRELONPRD,AGR3.FRELONPRD))*COALESCE(C.DMRRAT,AGR3.DMRRAT))  ")
					.append(" WHEN COALESCE (C.DMRFQY, AGR3.DMRFQY) ='W' THEN    ")					
					.append(" ((  (  (  FLOOR(  ( ( TRUNC(SYSDATE)- TRUNC(C.TXNDAT))/ 7)))+ 1- COALESCE(C.FRELONPRD,AGR3.FRELONPRD))  ")					
					.append(" * COALESCE(C.DMRRAT,AGR3.DMRRAT))*COALESCE(C.TAXAMT,AGR3.TAXAMT)/100)+ ")
					.append("  (  (  ( FLOOR(  ( ( TRUNC(SYSDATE)- TRUNC(C.TXNDAT))/ 7)))+ 1- COALESCE(C.FRELONPRD,AGR3.FRELONPRD))* COALESCE(C.DMRRAT,AGR3.DMRRAT))")
					.append("  WHEN COALESCE(C.DMRFQY,AGR3.DMRFQY) ='F' THEN ")
					.append(" (  ( ( FLOOR((  (  TRUNC(SYSDATE)- TRUNC(C.TXNDAT))/ 14))+ 1- COALESCE(C.FRELONPRD,AGR3.FRELONPRD)) ")
					.append("  * COALESCE(C.DMRRAT,AGR3.DMRRAT))*COALESCE(C.TAXAMT,AGR3.TAXAMT)/100)+ ")					
					.append("  ( ( FLOOR((  (  TRUNC(SYSDATE)- TRUNC(C.TXNDAT))/ 14))+ 1- COALESCE(C.FRELONPRD,AGR3.FRELONPRD)) ")
					.append("  * COALESCE(C.DMRRAT,AGR3.DMRRAT)) ")					
					.append("  WHEN COALESCE(C.DMRFQY,AGR3.DMRFQY) ='M'THEN   ")					
					.append("  (  ((  (  TRUNC(MONTHS_BETWEEN(SYSDATE,C.TXNDAT))- COALESCE(C.FRELONPRD,AGR3.FRELONPRD))+ 1)  ")					
					.append("  * COALESCE(C.DMRRAT,AGR3.DMRRAT))*COALESCE(C.TAXAMT,AGR3.TAXAMT)/100)+  ")					
					.append("  ((  (  TRUNC(MONTHS_BETWEEN(SYSDATE,C.TXNDAT))- COALESCE(C.FRELONPRD,AGR3.FRELONPRD))+ 1)")					
					.append("  * COALESCE(C.DMRRAT,AGR3.DMRRAT)) ELSE 0 END ")
					.append("  WHEN C.POLOWN <> 'N' THEN 0 ELSE 0 ")
					.append(" END RAT ")
					.append(" FROM (SELECT B.POLOWN,B.CONCOD,B.TXNTYP ,B.CMPCOD,B.TXNDAT,COALESCE (B.DMRFQY,AGR2.DMRFQY) DMRFQY,")
					.append(" COALESCE (B.FRELONPRD,AGR2.FRELONPRD) FRELONPRD,")
					.append(" COALESCE (B.DMRRAT,AGR2.DMRRAT) DMRRAT,COALESCE (B.TAXAMT, AGR2.TAXAMT) TAXAMT  ")
					.append(" FROM (SELECT A.ULDNUM , A.CMPCOD, A.TXNREFNUM ,A.TXNTYP , A.TXNNAT , A.TXNSTA ,")
					.append(" A.TXNDAT , A.TXNARPCOD , A.PTYTYP ,A.PTYCOD , A.PTYIDR , A.RTNDAT ,")
					.append(" A.RTNARPCOD , A.RTNPTYIDR , A.DMRAMT,A.CURCOD , A.INVREFNUM , A.TXNRMK ,")
					.append("	A.RTNRMK , A.RTNPTYCOD ,A.LSTUPDUSR , A.LSTUPDTIM,A.DMGSTA , A.DSTAPTCOD , ")
					.append(" A.CRN ,A.CONCOD,A.POLOWN,COALESCE(A.DMRFQY,AGR1.DMRFQY) DMRFQY,")
					.append(" COALESCE(A.FRELONPRD,AGR1.FRELONPRD) FRELONPRD,")
					.append(" COALESCE(A.DMRRAT,AGR1.DMRRAT) DMRRAT,COALESCE (A.TAXAMT, AGR1.TAXAMT) TAXAMT ")
					.append(" FROM (SELECT  TXN.ULDNUM , TXN.CMPCOD, TXN.TXNREFNUM ,")
					.append(" TXN.TXNTYP , TXN.TXNNAT , TXN.TXNSTA ,TXN.TXNDAT , ")
					.append(" TXN.TXNARPCOD , TXN.PTYTYP ,TXN.PTYCOD , TXN.PTYIDR , ")
					.append(" TXN.RTNDAT ,TXN.RTNARPCOD , TXN.RTNPTYIDR , TXN.DMRAMT,")
					.append(" TXN.CURCOD , TXN.INVREFNUM , TXN.TXNRMK ,TXN.RTNRMK , TXN.RTNPTYCOD ,")
					.append(" TXN.LSTUPDUSR , TXN.LSTUPDTIM,TXN.DMGSTA , TXN.DSTAPTCOD , ")
					.append(" TXN.CRN ,TXN.CONCOD,TXN.POLOWN,AGRDTL.DMRFQY,AGRDTL.FRELONPRD,AGRDTL.DMRRAT,")
					.append(" AGRDTL.TAXAMT")
					.append(" FROM ULDTXNMST TXN LEFT OUTER JOIN(")
					.append(" SELECT AGR.CMPCOD,AGR.AGRMNTSTA,AGR.PTYCOD,AGR.PTYTYP,AGR.TXNTYP,DTL.DMRFQY,")
					.append(" DTL.FRELONPRD,DTL.DMRRAT,DTL.AGRMNTFRMDAT,DTL.AGRMNTTOODAT,")
					.append(" DTL.TAXAMT,DTL.ULDTYPCOD,DTL.ARPCOD ")
					.append(" FROM ULDAGRMNT AGR,ULDAGRMNTDTL DTL")
					.append(" WHERE AGR.CMPCOD = DTL.CMPCOD ")
					.append(" AND AGR.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
			sbd
					.append(" AND AGR.AGRMNTNUM =DTL.AGRMNTNUM) AGRDTL ")
					.append(" ON TXN.CMPCOD = AGRDTL.CMPCOD")
					.append(" AND TXN.PTYCOD = AGRDTL.PTYCOD ")
					.append(" AND TXN.PTYTYP = AGRDTL.PTYTYP ")
					.append(" AND TXN.TXNTYP = AGRDTL.TXNTYP")
					.append(" AND AGRDTL.AGRMNTSTA = 'A' ")
					.append(
							"  AND (((TRUNC(TXN.TXNDAT) BETWEEN AGRDTL.AGRMNTFRMDAT AND AGRDTL.AGRMNTTOODAT) ")
					.append(
							" AND SYSDATE <= AGRDTL.AGRMNTTOODAT AND AGRDTL.AGRMNTTOODAT IS NOT NULL) OR ")
					.append(
							" (TRUNC(TXN.TXNDAT) >= AGRDTL.AGRMNTFRMDAT AND AGRDTL.AGRMNTTOODAT IS NULL)) ")
					.append(
							" AND SUBSTR(TXN.ULDNUM,0,3) = AGRDTL.ULDTYPCOD AND TXN.TXNARPCOD = AGRDTL.ARPCOD ")
					.append(" WHERE TXN.TXNSTA = 'T' ").append(
							" AND TXN.CMPCOD = ? ");

			this.setParameter(++index, companyCode);
			if (uldTransactionFilterVO.getUldNumber() != null
					&& uldTransactionFilterVO.getUldNumber().trim().length() != 0) {
				sbd.append(" AND TXN.ULDNUM = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getUldNumber());
			}
			if (!(TransactionFilterVO.TRANSACTION_TYPE_ALL
					.equals(uldTransactionFilterVO.getTransactionType()))) {
				sbd.append(" AND TXN.TXNTYP = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getTransactionType());
			}
			if (uldTransactionFilterVO.getUldTypeCode() != null
					&& uldTransactionFilterVO.getUldTypeCode().trim().length() != 0) {
				sbd.append(" AND TXN.ULDTYP = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getUldTypeCode());
			}
			if (uldTransactionFilterVO.getPartyType() != null
					&& uldTransactionFilterVO.getPartyType().trim().length() != 0) {
				sbd.append(" AND TXN.PTYTYP = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getPartyType());
			}
			if (uldTransactionFilterVO.getFromPartyCode() != null
					&& uldTransactionFilterVO.getFromPartyCode().trim()
							.length() != 0) {
				sbd.append(" AND TXN.RTNPTYCOD = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getFromPartyCode());
			}
			if (uldTransactionFilterVO.getToPartyCode() != null
					&& uldTransactionFilterVO.getToPartyCode().trim().length() != 0) {
				sbd.append(" AND TXN.PTYCOD = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getToPartyCode());
			}
			if (uldTransactionFilterVO.getTransactionStationCode() != null
					&& uldTransactionFilterVO.getTransactionStationCode()
							.trim().length() != 0) {
				sbd.append(" AND TXN.TXNARPCOD = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getTransactionStationCode());
			}
			if (uldTransactionFilterVO.getReturnedStationCode() != null
					&& uldTransactionFilterVO.getReturnedStationCode().trim()
							.length() != 0) {
				sbd.append(" AND TXN.RTNARPCOD = ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getReturnedStationCode());
			}
			if (uldTransactionFilterVO.getTxnFromDate() != null) {
				sbd.append("  AND TXN.TXNDAT >=  ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getTxnFromDate().toCalendar());
			}
			if (uldTransactionFilterVO.getTxnToDate() != null) {
				sbd.append("  AND TXN.TXNDAT <=  ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getTxnToDate().toCalendar());
			}
			if (uldTransactionFilterVO.getReturnFromDate() != null) {
				sbd.append("  AND TXN.RTNDAT >= ? ");
				this.setParameter(++index, uldTransactionFilterVO
						.getReturnFromDate().toCalendar());
			}
			if (uldTransactionFilterVO.getReturnToDate() != null) {
				sbd.append("  AND TXN.RTNDAT <= ?  ");
				this.setParameter(++index, uldTransactionFilterVO
						.getReturnToDate().toCalendar());
			}
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
			sbd
					.append(" )A ")
					.append("  LEFT OUTER JOIN ULDAGRMNT AGR1 ")
					.append(" ON A.CMPCOD = AGR1.CMPCOD ")
					.append(" AND A.PTYCOD = AGR1.PTYCOD ")
					.append(" AND A.PTYTYP = AGR1.PTYTYP ")
					.append(" AND A.TXNTYP = AGR1.TXNTYP ")
					.append(
							"  AND (((TRUNC(A.TXNDAT) BETWEEN AGR1.AGRMNTFRMDAT AND AGR1.AGRMNTTOODAT) ")
					.append(
							" AND SYSDATE <= AGR1.AGRMNTTOODAT AND  AGR1.AGRMNTTOODAT IS NOT NULL) OR ")
					.append(
							" (TRUNC(A.TXNDAT) >= AGR1.AGRMNTFRMDAT AND AGR1.AGRMNTTOODAT IS NULL))	")
					.append(" WHERE A.TXNSTA = 'T') B")
					.append("  LEFT OUTER JOIN ULDAGRMNT AGR2")
					.append(" ON B.CMPCOD = AGR2.CMPCOD ")
					.append(" AND B.PTYTYP = AGR2.PTYTYP ")
					.append(" AND B.TXNTYP = AGR2.TXNTYP")
					.append(" AND AGR2.AGRMNTSTA = 'A' ")
					.append(
							" AND (( B.PTYTYP = 'A' AND AGR2.PTYCOD = 'ALL AIRLINES') ")
					.append(
							" OR (B.PTYTYP = 'G' AND AGR2.PTYCOD = 'ALL AGENTS'))")
					.append(
							"  AND (( (TRUNC(B.TXNDAT) BETWEEN AGR2.AGRMNTFRMDAT AND AGR2.AGRMNTTOODAT)")
					.append(
							" AND SYSDATE <= AGR2.AGRMNTTOODAT AND  AGR2.AGRMNTTOODAT IS NOT NULL) OR  ")
					.append(
							" (TRUNC(B.TXNDAT) >= AGR2.AGRMNTFRMDAT AND AGR2.AGRMNTTOODAT IS NULL)) ")
					.append(" WHERE B.TXNSTA = 'T') C").append(
							" LEFT OUTER JOIN ULDAGRMNT AGR3 ").append(
							" ON C.CMPCOD = AGR3.CMPCOD ").append(
							" AND C.TXNTYP = AGR3.TXNTYP ").append(
							" AND AGR3.PTYTYP = 'L' AND AGR3.PTYCOD = 'ALL'  AND  AGR3.AGRMNTSTA = 'A' ");
					
		}
		sbd.append(" )X");
		return sbd.toString();
	}
}
