/*
 * MRARejectionMemoDetailsMapper.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2391
 *
 */
public class MRARejectionMemoDetailsMapper implements Mapper<RejectionMemoVO>{
	private Log log = LogFactory.getLogger("MRARejectionMemoDetailsMapper");
	private static final String BLGCURCOD = "USD";

	/**
	 * @param rs
	 * @return RejectionMemoVO
	 * @exception SQLException
	 *
	 */

	public RejectionMemoVO map(ResultSet rs) throws SQLException {
		
		log.entering("MRARejectionMemoDetailsMapper---------", "Map Method");
		
		
		
		RejectionMemoVO rejectionMemoVO 	= new RejectionMemoVO();
		try{
			rejectionMemoVO.setCompanycode(rs.getString("CMPCOD") );
			rejectionMemoVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
			rejectionMemoVO.setAirlineName(rs.getString("ARLNAM"));
			rejectionMemoVO.setMemoCode( rs.getString("MEMCOD" ));
			rejectionMemoVO.setAirlineCode(rs.getString("TOOARLCOD" ));
			rejectionMemoVO.setInwardInvoiceNumber(rs.getString("INWINVNUM" ));
			LocalDate  inwDate= rs.getDate("INWINVDAT") == null ? null :
				new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("INWINVDAT"));
			rejectionMemoVO.setInwardInvoiceDate(inwDate);
			rejectionMemoVO.setInwardClearancePeriod(rs.getString("INWCLRPRD"));
			rejectionMemoVO.setOutwardInvoiceNumber( rs.getString("OUTINVNUM" ));
			LocalDate  outDate= rs.getDate("OUTINVDAT") == null ? null :
				new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("OUTINVDAT"));
			rejectionMemoVO.setOutwardInvoiceDate(outDate);
			rejectionMemoVO.setOutwardClearancePeriod(rs.getString("OUTCLRPRD"));
			rejectionMemoVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			rejectionMemoVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
			rejectionMemoVO.setClearanceCurrencyCode(rs.getString("CLRCURCOD"));			
			rejectionMemoVO.setContractBilledAmount(rs.getDouble("CRTBLDAMT") );
			rejectionMemoVO.setContractAcceptedAmount(rs.getDouble("CRTAPTAMT") );
			rejectionMemoVO.setContractRejectedAmount(rs.getDouble("CRTREJAMT") );
			rejectionMemoVO.setClearanceBilledAmount(rs.getDouble("CLRBLDAMT") );
			rejectionMemoVO.setClearanceAcceptedAmount(rs.getDouble("CLRAPTAMT") );
			rejectionMemoVO.setClearanceRejectedAmount(rs.getDouble("CLRREJAMT") );
			Money billedAmt = CurrencyHelper.getMoney(BLGCURCOD);
			billedAmt.setAmount(rs.getDouble("BLGBLDAMT"));
			Money acceptedAmt = CurrencyHelper.getMoney(BLGCURCOD);
			acceptedAmt.setAmount(rs.getDouble("BLGAPTAMT"));
			Money rejectedAmt = CurrencyHelper.getMoney(BLGCURCOD);
			rejectedAmt.setAmount(rs.getDouble("BLGREJAMT"));
			rejectionMemoVO.setBillingBilledAmount(billedAmt);
			rejectionMemoVO.setBillingAcceptedAmount(acceptedAmt);
			rejectionMemoVO.setBillingRejectedAmount(rejectedAmt);
			rejectionMemoVO.setContractBillingExchangeRate(rs.getDouble("CRTBLGEXGRAT") ); 
			rejectionMemoVO.setBillingClearanceExchangeRate(rs.getDouble("BLGCLREXGRAT") );
			rejectionMemoVO.setRequestAuthorisationIndicator(rs.getString("REQAUTIND") );
			rejectionMemoVO.setRequestAuthorisationReference(rs.getString("REQAUTREF") );
			LocalDate  reqDate= rs.getDate("REQAUTDAT") == null ? null :
				new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("REQAUTDAT"));
			rejectionMemoVO.setRequestAuthorisationDate(reqDate);
			rejectionMemoVO.setDuplicateBillingIndicator(rs.getString("DUPBILIND") );
			rejectionMemoVO.setDuplicateBillingInvoiceNumber(rs.getString("DUPBILINVNUM") );
			LocalDate  dupDate= rs.getDate("DUPBILINVDAT") == null ? null :
				new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("DUPBILINVDAT"));
			rejectionMemoVO.setDuplicateBillingInvoiceDate(dupDate);
			rejectionMemoVO.setChargeNotCoveredByContractIndicator(rs.getString("CHGNOTCRTIND") );
			rejectionMemoVO.setChargeNotConvertedToContractIndicator(rs.getString("CHGNOTCONCRTIND") );
			rejectionMemoVO.setNoApprovalIndicator(rs.getString("NOAPRVIND") );
			rejectionMemoVO.setNoReceiptIndicator(rs.getString("NORCPTIND") );
			rejectionMemoVO.setIncorrectExchangeRateIndicator(rs.getString("INCORTEXGIND") );
			rejectionMemoVO.setOtherIndicator(rs.getString("OTHIND") );
			rejectionMemoVO.setMemoStatus(rs.getString("MEMSTA") );
			rejectionMemoVO.setRemarks(rs.getString("RMK") );
			rejectionMemoVO.setOutTimeLimitsForBillingIndicator(rs.getString("OUTTIMLMTIND") );
			rejectionMemoVO.setProvisionalAmount(rs.getDouble("PROVAMTLSTCUR") );  //Modified as part of ICRD_265471
			rejectionMemoVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
			if(RejectionMemoVO.FLAG_YES.equals(rs.getString("PARVAL"))){
			rejectionMemoVO.setDsn(rs.getString("DSN"));
			}else{
				rejectionMemoVO.setBillingBasis(rs.getString("BLGBAS"));
			}
			rejectionMemoVO.setOrigin(rs.getString("ORGCOD"));
			rejectionMemoVO.setDestination(rs.getString("DSTCOD"));
			rejectionMemoVO.setSectorFrom(rs.getString("SECFRM"));
			rejectionMemoVO.setSectorTo(rs.getString("SECTOO"));
     		LocalDate  memoDate= rs.getDate("MEMDAT") == null ? null :
				new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("MEMDAT"));
			rejectionMemoVO.setRejectedDate(memoDate);
			if(rs.getTimestamp("LSTUPDTIM")!=null){
				rejectionMemoVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
	        }
			
			rejectionMemoVO.setAttachmentIndicator(rs.getString("ATHIND"));//Added For ICRD-265471
			
		   }catch(CurrencyException e) {
	        throw new SQLException(e.getErrorCode());
	    }
		
		log.exiting("MRARejectionMemoDetailsMapper", "Map Method");
		log.log(Log.FINE, "Map Method", rejectionMemoVO);
		return rejectionMemoVO;
	}


}
