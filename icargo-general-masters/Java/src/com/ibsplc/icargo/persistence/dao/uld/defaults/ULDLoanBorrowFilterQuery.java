/*
 * ULDLoanBorrowFilterQuery.java Created on Dec 23, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2883
 *
 */


public class ULDLoanBorrowFilterQuery extends PageableNativeQuery<ULDTransactionDetailsVO>{

	private Log log = LogFactory.getLogger("ULD");

	private TransactionFilterVO uldTransactionFilterVO;

	/**
	 *
	 */
	private String baseQuery;
	private String baseQuery2;
	private boolean isOracleDateSource;
	/**
	 *
	 * @param uldListFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ULDLoanBorrowFilterQuery( int totalRecordCount,Mapper<ULDTransactionDetailsVO> mapper,
			TransactionFilterVO uldTransactionFilterVO, String baseQuery,String baseQuery2, boolean isOracleDateSource)
			throws SystemException {
		super( totalRecordCount, mapper);
		this.uldTransactionFilterVO = uldTransactionFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDateSource = isOracleDateSource;
		this.baseQuery2= baseQuery2;
	}

	/**
	 * @return
	 */
	public String getNativeQuery(){
		log.entering("ULDLoanBorrowFilterQuery", "getNativeQuery");
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
		if (uldTransactionFilterVO.getPartyType() != null && uldTransactionFilterVO.getPartyType().trim().length() != 0 && !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getPartyType()))) {
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
		if (Objects.nonNull(uldTransactionFilterVO.getDesStation()) && !uldTransactionFilterVO.getDesStation().isEmpty()) {
			sbd.append(" AND T.DSTAPTCOD = ? ");
			this.setParameter(++index, uldTransactionFilterVO.getDesStation());
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
		if(Objects.equals("return", uldTransactionFilterVO.getLeaseOrReturn())){
			
			if (uldTransactionFilterVO.getReturnFromDate() != null) {
				sbd.append("  AND T.RTNDAT >= ? ");
				this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
			}
			if (uldTransactionFilterVO.getReturnToDate() != null) {
				sbd.append("  AND T.RTNDAT <= ?  ");
				this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
			}
		}else{
			if (uldTransactionFilterVO.getReturnFromDate() != null) {
				sbd.append("  AND T.ENDLSEDAT >= ? ");
				this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
			}
			if (uldTransactionFilterVO.getReturnToDate() != null) {
				sbd.append("  AND T.ENDLSEDAT <= ?  ");
				this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
			}
		}
		sbd.append(" AND ((SYSTEMPAR.PARVAL= 'Y' AND T.POLOWN !='Y') OR COALESCE(SYSTEMPAR.PARVAL,'N')='N') ");	
		//added by a-3045 for CR QF1142 starts
		if (uldTransactionFilterVO.getMucStatus() != null
				&& uldTransactionFilterVO.getMucStatus().trim().length() != 0) {
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
		if (uldTransactionFilterVO.getMucReferenceNumber() != null
				&& uldTransactionFilterVO.getMucReferenceNumber().trim().length() != 0) {
			sbd.append("  AND T.MUCREFNUM =  ? ");
			this.setParameter(++index, uldTransactionFilterVO.getMucReferenceNumber());
		}
		//added by a-3045 for CR QF1142 ends
		//added by a-3045 for bug26528 starts,28658	
		/* commented else part for considering the CRN as filter
		 * since no records are listed if CRN is null(bug 69584)
		 * */
		if (uldTransactionFilterVO.getPrefixControlReceiptNo() != null
				&& uldTransactionFilterVO.getPrefixControlReceiptNo().trim().length() != 0) {
			sbd.append("  AND  ( SUBSTR(T.RTNCRN, 1, 4) LIKE ? or SUBSTR(T.CRN, 1, 4) LIKE ? ) ");
			StringBuilder preCrn = new StringBuilder(uldTransactionFilterVO.getPrefixControlReceiptNo()).append("%-");		
			this.setParameter(++index,preCrn.toString());
			this.setParameter(++index,preCrn.toString());
		}/*else{			
			this.setParameter(++index, "%-");
		}*/		
		if (uldTransactionFilterVO.getMidControlReceiptNo() != null
				&& uldTransactionFilterVO.getMidControlReceiptNo().trim().length() != 0) {
			sbd.append("  AND ( SUBSTR(T.RTNCRN, 5, 1) LIKE ? or SUBSTR(T.CRN, 5, 1) LIKE ?)");
			this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
			this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
		}/*else{			
			this.setParameter(++index,"%");
		}*/		
		if (uldTransactionFilterVO.getControlReceiptNo() != null
				&& uldTransactionFilterVO.getControlReceiptNo().trim().length() != 0) {	
			sbd.append(" AND ( SUBSTR(T.RTNCRN, 6,7) LIKE ? or SUBSTR(T.CRN, 6,7) LIKE ?)");
			StringBuilder sufCrn = new StringBuilder("%").append(uldTransactionFilterVO.getControlReceiptNo()).append("%");			
			this.setParameter(++index,sufCrn.toString());
			this.setParameter(++index,sufCrn.toString());
		}/*else{			
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
			
			//modifications as apart of buisness change in listing the loan borrow details on 24Mar09
			sbd.append(" UNION ALL ").append(baseQuery2)   ;				  
				//modifications as apart of buisness change in listing the loan borrow details ends
				sbd .append(" AND TXN.TXNSTA = 'T' ");
				
				this.setParameter(++index, companyCode);	
				String complainceQuery = "";
			String dbCurrentDate;
			if(isOracleDateSource){
				dbCurrentDate = "SYSDATE";
					complainceQuery = "THEN (TRUNC (MONTHS_BETWEEN (TRUNC(SYSDATE), (TRUNC(TXNDAT) - FRELONPRD)) )) * DMRRAT";  
				  }
				  else{
					  dbCurrentDate = "SYSDATE()";
					  complainceQuery = "  THEN DATEDIFF ('mm',TRUNC(" + dbCurrentDate + " ),txndat- INTERVAL '1 days') * dmrrat ";
				  }
				  if(!Objects.equals(ULDDefaultsPersistenceConstants.FLAG_YES, uldTransactionFilterVO.getIsAgreementListingRequired())){
						this.setParameter(++index, companyCode);	
						sbd = new StringBuilder(sbd.toString().replaceAll(ULDDefaultsPersistenceConstants.CURDAT.toString(), dbCurrentDate));
					}
				  sbd = new StringBuilder(String.format(sbd.toString(), complainceQuery,dbCurrentDate));
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
				if (uldTransactionFilterVO.getPartyType() != null && uldTransactionFilterVO.getPartyType().trim().length() != 0 && !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(uldTransactionFilterVO.getPartyType()))) {
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
				if (Objects.nonNull(uldTransactionFilterVO.getDesStation()) && !uldTransactionFilterVO.getDesStation().isEmpty()) {
					sbd.append(" AND TXN.DSTAPTCOD = ? ");
					this.setParameter(++index, uldTransactionFilterVO.getDesStation());
				}
				if (uldTransactionFilterVO.getTxnFromDate() != null) {
					sbd.append("  AND TXN.TXNDAT >=  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
				}
				if (uldTransactionFilterVO.getTxnToDate() != null) {
					sbd.append("  AND TXN.TXNDAT <=  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
				}
				if(Objects.equals("R", uldTransactionFilterVO.getLeaseOrReturn())){
					if (Objects.nonNull(uldTransactionFilterVO.getReturnFromDate())) {
						sbd.append("  AND TXN.RTNDAT >= ? ");
						this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
					}
					if (Objects.nonNull(uldTransactionFilterVO.getReturnToDate())) {
						sbd.append("  AND TXN.RTNDAT <= ?  ");
						this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
					}
				}else{
					if (Objects.nonNull(uldTransactionFilterVO.getReturnFromDate())) {
						sbd.append("  AND TXN.ENDLSEDAT >= ? ");
						this.setParameter(++index, uldTransactionFilterVO.getReturnFromDate().toCalendar());
					}
					if (Objects.nonNull(uldTransactionFilterVO.getReturnToDate() )) {
						sbd.append("  AND TXN.ENDLSEDAT <= ?  ");
						this.setParameter(++index, uldTransactionFilterVO.getReturnToDate().toCalendar());
					}
				}
				sbd.append(" AND((SYSPAR.PARVAL= 'Y' AND TXN.POLOWN !='Y') OR COALESCE(SYSPAR.PARVAL,'N')='N') ");
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
				if (uldTransactionFilterVO.getMucIataStatus() != null
						&& uldTransactionFilterVO.getMucIataStatus().trim().length() != 0) {
					sbd.append("  AND TXN.MUCSNT =  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getMucIataStatus());
				}
				if (uldTransactionFilterVO.getMucDate() != null) {
					sbd.append("  AND TXN.MUCDAT = ?  ");
					this.setParameter(++index, uldTransactionFilterVO.getMucDate().toCalendar());
				}
				if (uldTransactionFilterVO.getMucReferenceNumber() != null
						&& uldTransactionFilterVO.getMucReferenceNumber().trim().length() != 0) {
					sbd.append("  AND TXN.MUCREFNUM =  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getMucReferenceNumber());
				}
				//added by a-3045 for CR QF1142 ends
				//added by a-3045 for bug26528 starts
				/* commented else part for considering the CRN as filter
				 * since no records are listed if CRN is null(bug 69584)
				 * */
				if (uldTransactionFilterVO.getPrefixControlReceiptNo() != null
						&& uldTransactionFilterVO.getPrefixControlReceiptNo().trim().length() != 0) {
					sbd.append("  AND  SUBSTR(TXN.CRN, 1, 4) LIKE  ? ");
					StringBuilder preCrn = new StringBuilder(uldTransactionFilterVO.getPrefixControlReceiptNo()).append("%-");		
					this.setParameter(++index,preCrn.toString());
				}/*else{					
					this.setParameter(++index, "%-");
				}*/				
				if (uldTransactionFilterVO.getMidControlReceiptNo() != null
						&& uldTransactionFilterVO.getMidControlReceiptNo().trim().length() != 0) {
					sbd.append("  AND SUBSTR(TXN.CRN, 5, 1) LIKE  ? ");
					this.setParameter(++index, uldTransactionFilterVO.getMidControlReceiptNo());
				}/*else{					
					this.setParameter(++index,"%");
				}*/				
				if (uldTransactionFilterVO.getControlReceiptNo() != null
						&& uldTransactionFilterVO.getControlReceiptNo().trim().length() != 0) {	
					sbd.append(" AND SUBSTR(TXN.CRN, 6, 7) LIKE ?");
					StringBuilder sufCrn = new StringBuilder("%").append(uldTransactionFilterVO.getControlReceiptNo()).append("%");			
					this.setParameter(++index,sufCrn.toString());
				}/*else{					
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
				if(Objects.equals("Y", uldTransactionFilterVO.getIsAgreementListingRequired())){
					//modifications as apart of buisness change in listing the loan borrow details on 24Mar09
					sbd.append("  ORDER BY TXN.ULDNUM , TXN.TXNREFNUM)A) RNK  ORDER BY RNK.TXNDAT DESC, RNK.ULDNUM");
				}else{
					sbd.append("  ORDER BY TXN.ULDNUM , TXN.TXNREFNUM)A)B WHERE B.PRI = B.MINPRI ")
					  .append(" ) RNK  ORDER BY RNK.TXNDAT DESC, RNK.ULDNUM");//Order clause added for ICRD-162318 by A-5117
				}
			
				  
		}else{
		sbd.append(" ) RNK");
		}
		sbd.append(" ) RESULT_TABLE");
		return sbd.toString();
	}
}
