/*
 * ListRateAuditDtlsMultiMapper.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class ListRateAuditDtlsMultiMapper implements MultiMapper<RateAuditVO> {

	  private Log log = LogFactory.getLogger("MAILTRACKING MRA BILLINGLINE");
	  /**
	     * @author A-3251
	     * @param rs
	     * @return List<RateAuditVO>
	     * @throws SQLException
	     */
	  public  List<RateAuditVO> map(ResultSet rs) throws SQLException {
		  	 log.entering("ListRateAuditDtlsMultiMapper", "map");
		  	List<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		  	RateAuditVO rateAuditVO = new RateAuditVO();
		  	Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
		  	RateAuditDetailsVO rateAuditDetailsVO = null;
		    	
		  	
		  	 while(rs.next()) {
		  		
		  		rateAuditVO.setBasType(rs.getString("BASTYP"));
		  		rateAuditVO.setYear(rs.getInt("YER"));
		  		rateAuditVO.setBillingBasis(rs.getString("BLGBAS"));	
		  		rateAuditVO.setMailbagId(rs.getString("BLGBAS"));
		  		rateAuditVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		  		rateAuditVO.setDsn(rs.getString("DSN"));
			  	rateAuditVO.setDsnStatus(rs.getString("RATSTA"));
			  	rateAuditVO.setOrigin(rs.getString("ORGN"));
			  	rateAuditVO.setDestination(rs.getString("DEST"));
			  	rateAuditVO.setGpaCode(rs.getString("GPACOD"));
			  	rateAuditVO.setConDocNum(rs.getString("CONDOCNUM"));
			  	rateAuditVO.setRoute(rs.getString("ROTE"));	
			  	rateAuditVO.setCompanyCode(rs.getString("CMPCOD"));
			  	rateAuditVO.setConSerNum(rs.getInt("CSGSEQNUM"));
			  	rateAuditVO.setPcs(String.valueOf(rs.getInt("NPCS")));
			  	rateAuditVO.setCategory(rs.getString("MALCATCOD"));
			  	rateAuditVO.setSubClass(rs.getString("MALSUBCLS"));
			  	rateAuditVO.setUld(rs.getString("ULDNUM"));
			  	rateAuditVO.setInitialRate(rs.getDouble("BLGRAT"));
			  	rateAuditVO.setDsnaccsta(rs.getString("DSNACCSTA"));
			  	rateAuditVO.setHsn(rs.getString("HSN"));
			  	rateAuditVO.setRegInd(rs.getString("REGIND"));
			  	rateAuditVO.setRsn(rs.getString("RSN"));
			  	rateAuditVO.setPoaFlag(rs.getString("POAFLG"));
			  	rateAuditVO.setTransfercarcode(rs.getString("TRFCARCOD"));
			  	rateAuditVO.setOriginOE(rs.getString("ORGN"));
			  	rateAuditVO.setMailCompanyCode(rs.getString("CMPCOD"));
			  	rateAuditVO.setDestinationOE(rs.getString("DEST"));
			  	rateAuditVO.setOriginCityCode(rs.getString("SECFRM"));
			  	rateAuditVO.setDestinationCityCode(rs.getString("SECTOO"));
			  	rateAuditVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("DSNDAT")));
			  	
			  	rateAuditVO.setDsnDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("DSNDAT")));
			  	rateAuditVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));	
				Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
			     if(lstUpdTime != null) {
			    	 rateAuditVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
			     }
			  	
		  		rateAuditDetailsVO = new  RateAuditDetailsVO();
		  		rateAuditDetailsVO.setNoPieces(rs.getInt("NPCS"));
		  		rateAuditDetailsVO.setGrsWgt(rs.getDouble("GRWGT"));
		  		rateAuditDetailsVO.setCategory(rs.getString("MALCATCOD"));
		  		rateAuditDetailsVO.setSubclass(rs.getString("MALSUBCLS"));
		  		rateAuditDetailsVO.setUldno(rs.getString("ULDNUM"));
		  		rateAuditDetailsVO.setCarrierCode(rs.getString("CARCOD"));
		  		rateAuditDetailsVO.setFlightno(rs.getString("FLTNUM"));
		  		rateAuditDetailsVO.setRate(rs.getDouble("RATE"));
		  		rateAuditDetailsVO.setInitialRate(rs.getDouble("BLGRAT"));
		  		rateAuditDetailsVO.setPayFlag(rs.getString("PAYFLG")); 
		  		
		  		rateAuditDetailsVO.setAccsta(rs.getString("ACCSTA"));
		  		rateAuditDetailsVO.setAcctxnIdr(rs.getString("ACCTXNIDR"));
		  		rateAuditDetailsVO.setDueAirline(rs.getDouble("DUEARL"));
		  		rateAuditDetailsVO.setDuepoa(rs.getDouble("DUEPOA"));
		  		rateAuditDetailsVO.setProPercent(rs.getDouble("PROPRC"));
		  		rateAuditDetailsVO.setProValue(rs.getDouble("PROVAL"));
		  		rateAuditDetailsVO.setRevFlg(rs.getString("REVFLG"));
		  		rateAuditDetailsVO.setRemark(rs.getString("RMK"));
		  		
		  		
		  		Money presentWtCharge = null;
		  		Money audtdWgtCharge = null;
				try {
					presentWtCharge = CurrencyHelper.getMoney("NZD");
					presentWtCharge.setAmount(rs.getDouble("PRWTCHRG"));					
					rateAuditDetailsVO.setPrsntWgtCharge(presentWtCharge);
					audtdWgtCharge = CurrencyHelper.getMoney("NZD");
					audtdWgtCharge.setAmount(rs.getDouble("AUDWTCHRG"));
					rateAuditDetailsVO.setAudtdWgtCharge(audtdWgtCharge);
					if(rateAuditDetailsVO.getPayFlag()!=null && "R".equals(rateAuditDetailsVO.getPayFlag())){
						rateAuditVO.setPresentWtCharge(presentWtCharge);	
						rateAuditVO.setAuditedWtCharge(audtdWgtCharge);
						rateAuditVO.setUpdWt(String.valueOf(rateAuditDetailsVO.getGrsWgt()));
						rateAuditVO.setGrossWt(rateAuditDetailsVO.getGrsWgt());
					}
				} catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
					e.getErrorCode();
				}				
			
		  		rateAuditDetailsVO.setDiscrepancy(rs.getString("DISPCY"));
		  		rateAuditDetailsVO.setBillTO(rs.getString("BILLTO"));
		  		rateAuditDetailsVO.setApplyAudt(rs.getString("APPAUD"));
		  		rateAuditDetailsVO.setSerNum(rs.getInt("SERNUM"));		  		 
		  		rateAuditDetailsVO.setCsgDocNum(rs.getString("CONDOCNUM"));
		  		rateAuditDetailsVO.setCsgSeqNum(rs.getInt("CSGSEQNUM"));
		  		rateAuditDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
		  		rateAuditDetailsVO.setGpaCode(rs.getString("GPACOD"));
		  		rateAuditDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		  		rateAuditDetailsVO.setSecFrom(rs.getString("SECFRM"));
		  		rateAuditDetailsVO.setSecTo(rs.getString("SECTOO"));
		  		rateAuditDetailsVO.setGpaarlBillingFlag(rs.getString("GPAARLBLGFLG"));
		  		rateAuditDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
		  		rateAuditDetailsVO.setCarrierid(rs.getInt("FLTCARIDR"));
		  		rateAuditDetailsVO.setWgtChargeUSD(rs.getDouble("WGTCHGUSD"));
		  		rateAuditDetailsVO.setWgtChargeSDR(rs.getDouble("WGTCHGSDR"));
		  		rateAuditDetailsVO.setWgtChargeBAS(rs.getDouble("WGTCHGBAS"));
		  		rateAuditDetailsVO.setContCurCode(rs.getString("CTRCURCOD"));
		  		rateAuditDetailsVO.setUpdBillToIdr(rs.getInt("UPDBILTOOIDR"));
		  		if(rs.getDate("FLTDAT")!=null){
		  		rateAuditDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("FLTDAT")));
		  		}
		  		rateAuditDetailsVO.setFlightseqno(rs.getInt("FLTSEQNUM"));
		  		rateAuditDetailsVO.setProrationType(rs.getString("PROTYP"));
		  		rateAuditDetailsVO.setSegSerNo(rs.getInt("SEGSERNUM"));
		  		rateAuditDetailsVO.setSectStatus(rs.getString("SECSTA"));
		  		rateAuditDetailsVO.setCurrency(rs.getString("CTRCURCOD"));
		  		rateAuditDetailsVO.setFulChg(rs.getDouble("FULCHG"));
		  		rateAuditDetailsVO.setRatInd(rs.getString("RATIND"));
		  		rateAuditDetailsVO.setMailbagId(rs.getString("BLGBAS"));
		  		rateAuditDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		  		rateAuditDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		  		rateAuditDetailsVOs.add(rateAuditDetailsVO); 
		  		 
		  	 }
		  	
		  	rateAuditVO.setRateAuditDetails(rateAuditDetailsVOs);
		  	rateAuditVOs.add(rateAuditVO);
		  	 return rateAuditVOs;

}

}
