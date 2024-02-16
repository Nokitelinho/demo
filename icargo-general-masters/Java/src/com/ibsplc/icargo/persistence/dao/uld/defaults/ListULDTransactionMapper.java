/*
 * ListULDTransactionMapper.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject tos license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * This mapper is Used for Listing of ULD Transaction Details 
 */
public class ListULDTransactionMapper
	implements Mapper<ULDTransactionDetailsVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	
	/**
	 * @param resultSet
	 * @return ULDTransactionDetailsVO
	 * @throws SQLException
	 */
	public ULDTransactionDetailsVO map(ResultSet resultSet) 
	throws SQLException {
		
		ULDTransactionDetailsVO uLDTransactionDetailsVO = 
			new ULDTransactionDetailsVO();
		uLDTransactionDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
		// Added By ASHRAF
		uLDTransactionDetailsVO.setReturnPartyCode(resultSet.getString("RTNPTYCOD"));
		
		uLDTransactionDetailsVO.setTransactionType(
				resultSet.getString("TXNTYP"));
		uLDTransactionDetailsVO.setUldNumber(resultSet.getString("ULDNUM"));
		uLDTransactionDetailsVO.setTransactionRefNumber(
				resultSet.getInt("TXNREFNUM"));
		uLDTransactionDetailsVO.setTransactionStatus(
				resultSet.getString("TXNSTA"));
		uLDTransactionDetailsVO.setTransactionStationCode(
				resultSet.getString("TXNARPCOD"));
		
		Timestamp txnDate = resultSet.getTimestamp("TXNDAT");
		if(txnDate != null &&
				uLDTransactionDetailsVO.getTransactionStationCode() == null){
			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,txnDate);
			uLDTransactionDetailsVO.setTransactionDate(date);
			uLDTransactionDetailsVO.setStrTxnDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}else if(txnDate != null &&
				uLDTransactionDetailsVO.getTransactionStationCode() != null){
			LocalDate date = new LocalDate(uLDTransactionDetailsVO.getTransactionStationCode().toUpperCase(),
																Location.ARP,txnDate);
			uLDTransactionDetailsVO.setTransactionDate(date);
			uLDTransactionDetailsVO.setStrTxnDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}
		uLDTransactionDetailsVO.setPartyType(resultSet.getString("PTYTYP"));
		
		uLDTransactionDetailsVO.setToPartyCode(resultSet.getString("PTYCOD"));
		uLDTransactionDetailsVO.setToPartyIdentifier(resultSet.getInt("PTYIDR"));
		uLDTransactionDetailsVO.setToPartyName(resultSet.getString("PTYNAM"));

		uLDTransactionDetailsVO.setFromPartyCode(resultSet.getString("RTNPTYCOD"));
		uLDTransactionDetailsVO.setFromPartyIdentifier(resultSet.getInt("RTNPTYIDR"));
		uLDTransactionDetailsVO.setReturnStationCode(
				resultSet.getString("RTNARPCOD"));

		
		Timestamp returnDate = resultSet.getTimestamp("RTNDAT");
		if(returnDate != null 
				&& uLDTransactionDetailsVO.getReturnStationCode() == null){
			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,returnDate);
			uLDTransactionDetailsVO.setReturnDate(date);
			uLDTransactionDetailsVO.setStrRetDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}else if(returnDate != null 
				&& uLDTransactionDetailsVO.getReturnStationCode() != null){
			LocalDate date = new LocalDate(uLDTransactionDetailsVO.getReturnStationCode().toUpperCase(),
													Location.ARP,returnDate);
			uLDTransactionDetailsVO.setReturnDate(date);
			uLDTransactionDetailsVO.setStrRetDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}
		
	
		
		uLDTransactionDetailsVO.setDamageStatus(
				resultSet.getString("DMGSTA"));
		
		uLDTransactionDetailsVO.setCurrency(resultSet.getString("CURCOD"));
		uLDTransactionDetailsVO.setInvoiceRefNumber(
				resultSet.getString("INVREFNUM"));
		uLDTransactionDetailsVO.setTransactionRemark(
				resultSet.getString("TXNRMK"));
		uLDTransactionDetailsVO.setTransactionNature(
				resultSet.getString("TXNNAT"));
		uLDTransactionDetailsVO.setOperationalAirlineIdentifier(Integer.
				valueOf(resultSet.getString("PTYIDR")));
		uLDTransactionDetailsVO.setCapturedRefNumber(
				resultSet.getString("CPDREFNUM"));
		uLDTransactionDetailsVO.setReturnRemark(
				resultSet.getString("RTNRMK"));
		
		uLDTransactionDetailsVO.setTxStationCode(
				resultSet.getString("DSTAPTCOD"));
		
		Timestamp endLeaseDate = resultSet.getTimestamp("ENDLSEDAT");
		if(endLeaseDate != null 
				&& uLDTransactionDetailsVO.getTxStationCode() == null){
			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE, endLeaseDate);
			uLDTransactionDetailsVO.setLeaseEndDate(date);
			uLDTransactionDetailsVO.setStrLseEndDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}else if(endLeaseDate != null 
				&& uLDTransactionDetailsVO.getTxStationCode() != null){
			LocalDate date = new LocalDate(uLDTransactionDetailsVO.getTxStationCode().toUpperCase(),
													Location.ARP, endLeaseDate);
			uLDTransactionDetailsVO.setLeaseEndDate(date);
			uLDTransactionDetailsVO.setStrLseEndDate(
					date.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}
		
		uLDTransactionDetailsVO.setLastUpdateUser(
				resultSet.getString("LSTUPDUSR"));
		if(resultSet.getTimestamp("LSTUPDTIM") != null){
			uLDTransactionDetailsVO.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION , Location.NONE , resultSet.getTimestamp("LSTUPDTIM")));
		}
		uLDTransactionDetailsVO.setWaived(resultSet.getDouble("WVRAMT"));
		uLDTransactionDetailsVO.setControlReceiptNumber(
				resultSet.getString("CRN"));
		/*
		 * added by a-3278 for 28897 on 05Jan09
		 * a new CRN is maintained to save the latest and the old CRN seperately
		 */
		uLDTransactionDetailsVO.setReturnCRN(
				resultSet.getString("RTNCRN"));
		//a-3278 ends
		
		uLDTransactionDetailsVO.setWaived(resultSet.getDouble("WVRAMT"));
		uLDTransactionDetailsVO.setDemurrageRate(resultSet.getDouble("DMRRAT"));
		uLDTransactionDetailsVO.setDemurrageAmount(resultSet.getDouble("DMRAMT"));
		if(ULDTransactionDetailsVO.TO_BE_RETURNED.equals(uLDTransactionDetailsVO.getTransactionStatus())){
			uLDTransactionDetailsVO.setTaxes(resultSet.getDouble("TAXAMT")*resultSet.getDouble("DMRAMT")/100);
			uLDTransactionDetailsVO.setTotal(uLDTransactionDetailsVO.getDemurrageAmount()+ uLDTransactionDetailsVO.getTaxes());
		}else{
			uLDTransactionDetailsVO.setTaxes(resultSet.getDouble("TAXAMT"));
			uLDTransactionDetailsVO.setTotal(resultSet.getDouble("RAT"));
		}
		//Added by A-2412
		uLDTransactionDetailsVO.setUldConditionCode(resultSet.getString("CONCOD"));		
		uLDTransactionDetailsVO.setPoolOwnerFlag(resultSet.getString("POLOWN"));
		//added by a-3045 for CR QF1013 starts
		uLDTransactionDetailsVO.setMucIataStatus(resultSet.getString("MUCSNT"));
		uLDTransactionDetailsVO.setMucReferenceNumber(resultSet.getString("MUCREFNUM"));		
		Date mucDate = resultSet.getDate("MUCDAT");
		if(mucDate != null ){
			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,mucDate);
			uLDTransactionDetailsVO.setMucDate(date);
		}
		//added by a-3045 for CR QF1013 ends
		uLDTransactionDetailsVO.setAwbNumber(
				resultSet.getString("AWBRMK"));
		uLDTransactionDetailsVO.setEmptyStatus(
				resultSet.getString("EMTSTA"));
		//Added by A-4072 as part of CR ICRD-192300 Starts
		uLDTransactionDetailsVO.setOriginatorName(resultSet.getString("ORNNAM"));
		uLDTransactionDetailsVO.setDamageFlagFromScreen(resultSet.getString("DMGFLG"));         
		uLDTransactionDetailsVO.setDamageRemark(resultSet.getString("DMGRMK"));
		uLDTransactionDetailsVO.setOdlnCode(resultSet.getString("ODNCOD"));
		//added as part of ICRD-232684 by A-4393 starts 
		uLDTransactionDetailsVO.setRemainingDayToReturn(resultSet.getString("DAYREM"));
		//added as part of ICRD-232684 by A-4393 ends 
		//Added by A-4072 as part of CR ICRD-192300 end
		
		if(Objects.nonNull(uLDTransactionDetailsVO.getLeaseEndDate()) && Objects.nonNull(uLDTransactionDetailsVO.getTxStationCode())){
			long difference = uLDTransactionDetailsVO.getLeaseEndDate().findDifference(new LocalDate(uLDTransactionDetailsVO.getTxStationCode(), Location.ARP,false));
			long days = difference/(24*3600*1000);
			uLDTransactionDetailsVO.setRemainingDaysToEndLease(String.valueOf(days));
		}
		
		
		return uLDTransactionDetailsVO;
	}
}
