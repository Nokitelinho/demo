/**
 * CCACreateMapper.java Created on June 14,2012
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class CCACreateMapper  implements MultiMapper<CCAdetailsVO> {

	/**
	 * class name
	 */
	private static final String CLASS_NAME = "CCACreateMapper";

	/**
	 * Logger
	 */

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<CCAdetailsVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		List<CCAdetailsVO> cCAdetailsVOs= new ArrayList<CCAdetailsVO>();
		CCAdetailsVO cCAdetailsVO = null;
		Map<String,CCAdetailsVO> cCAdetailsMap=  new HashMap<String,CCAdetailsVO>();
		String Key = null;

		while (rs.next()) {
			Key = new StringBuilder()
			.append(rs.getString("CMPCOD")).append("-")
			.append(rs.getString("MALIDR")).append("-")
			.append(rs.getString("CSGDOCNUM")).append("-")
			.append(rs.getString("CSGSEQNUM")).append("-")
			.append(rs.getString("POACOD")).toString();


			if(!cCAdetailsMap.containsKey(Key)){		
				cCAdetailsVO = new CCAdetailsVO();	

				cCAdetailsVO.setBillingBasis(rs.getString("MALIDR"));
				cCAdetailsVO.setCompanyCode(rs.getString("CMPCOD"));						
				cCAdetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));//added By A-7371 as part of ICRD-257661 
				cCAdetailsVO.setPoaCode(rs.getString("POACOD"));
				cCAdetailsVO.setCcaType(rs.getString("MCATYP"));
				cCAdetailsVO.setUpdBillTo(rs.getString("UPDBILTOOPOA"));
				cCAdetailsVO.setUpdBillToIdr(rs.getInt("UPDBILTOOIDR"));
				//Code added by Manish for IASCB-39047 start
				cCAdetailsVO.setDisplayWeightUnit(rs.getString("DSPWGTUNT"));
				cCAdetailsVO.setRateLineWeightUnit(rs.getString("UNTCOD"));
				//Code added by Manish for IASCB-39047 end
				if(rs.getString("ISSPARTY")!=null && rs.getString("ISSPARTY").length()>0){
					cCAdetailsVO.setIssuingParty(rs.getString("ISSPARTY"));
				}
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setAirlineCode(rs.getString("ARLCOD"));
				//cCAdetailsVO.setLocation(rs.getString("LOCCOD"));
				//cCAdetailsVO.setCcaStatus(rs.getString("CCASTA"));
				cCAdetailsVO.setOrigin(rs.getString("DSNORGCOD"));
				cCAdetailsVO.setDestination(rs.getString("DSNDSTCOD"));
				cCAdetailsVO.setOriginCode(rs.getString("ORGARPCOD"));
				cCAdetailsVO.setDestnCode(rs.getString("DSTARPCOD"));
				cCAdetailsVO.setCategory(rs.getString("CATCOD"));
				cCAdetailsVO.setSubClass(rs.getString("SUBCLS"));
				cCAdetailsVO.setDsnNo(rs.getString("DSN"));

				cCAdetailsVO.setUpdBillTo(rs.getString("UPDBILTOOPOA"));
				cCAdetailsVO.setSectFrom(rs.getString("SECFRM"));
				cCAdetailsVO.setSectTo(rs.getString("SECTOO"));
				Money grossWgtCharge = null;
				Money revGrossWeightCharge=null;
				 Money otherChgGrossWeight = null;
				 Money otherChgRevGrossWeight = null;
				Money netAmt = null;
				Money revNetAmt=null;
				Money valCharges=null;
				//Modified by A-4809 for BUG ICRD-18489 ...Starts
				try {
					if("A".equals(rs.getString("MCASTA"))){
					grossWgtCharge = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					otherChgGrossWeight= CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					netAmt=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					valCharges=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					grossWgtCharge.setAmount(rs.getDouble("REVWGTCHGCTR"));	
					otherChgGrossWeight.setAmount(rs.getDouble("REVOTHCHGCTR"));
					netAmt.setAmount(rs.getDouble("REVNETAMT"));
					revGrossWeightCharge=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					otherChgRevGrossWeight =CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					revGrossWeightCharge.setAmount(rs.getDouble("REVWGTCHGCTR"));
					otherChgRevGrossWeight.setAmount(rs.getDouble("REVOTHCHGCTR"));
					valCharges.setAmount(rs.getDouble("VALCHGCTR"));
					cCAdetailsVO.setValChgUpdAmount(valCharges);
					cCAdetailsVO.setChgGrossWeight(grossWgtCharge);
					cCAdetailsVO.setRevChgGrossWeight(revGrossWeightCharge);
					cCAdetailsVO.setOtherChgGrossWgt(otherChgGrossWeight);
					cCAdetailsVO.setOtherRevChgGrossWgt(otherChgRevGrossWeight);
					cCAdetailsVO.setRevNetAmount(netAmt);
					cCAdetailsVO.setNetAmount(netAmt);
					}
					else{
						grossWgtCharge = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						otherChgGrossWeight= CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						otherChgRevGrossWeight =CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						netAmt=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						grossWgtCharge.setAmount(rs.getDouble("CHGWGT"));	
						otherChgGrossWeight.setAmount(rs.getDouble("OTHCHG"));
						netAmt.setAmount(rs.getDouble("NETAMT"));
						revGrossWeightCharge=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						revGrossWeightCharge.setAmount(rs.getDouble("CHGWGT"));
						valCharges=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						valCharges.setAmount(rs.getDouble("VALCHGCTR"));
						otherChgRevGrossWeight.setAmount(rs.getDouble("OTHCHG"));
						cCAdetailsVO.setValChgUpdAmount(valCharges);
						cCAdetailsVO.setChgGrossWeight(grossWgtCharge);
						cCAdetailsVO.setRevChgGrossWeight(revGrossWeightCharge);
						cCAdetailsVO.setOtherChgGrossWgt(otherChgGrossWeight);
						cCAdetailsVO.setOtherRevChgGrossWgt(otherChgRevGrossWeight);
						cCAdetailsVO.setRevNetAmount(netAmt);
						cCAdetailsVO.setNetAmount(netAmt);						
					}
					
				}
				catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!!\n\n");
					e.getErrorCode();
				}	
				if("A".equals(rs.getString("MCASTA"))){ 
				cCAdetailsVO.setGpaCode(rs.getString("REVGPACOD"));
				cCAdetailsVO.setGpaName(rs.getString("REVGPANAM"));
				cCAdetailsVO.setRevGrossWeight(rs.getDouble("REVGRSWGT"));		
				cCAdetailsVO.setRevGpaCode(rs.getString("REVGPACOD"));
				cCAdetailsVO.setRevGpaName(rs.getString("REVGPANAM"));
				cCAdetailsVO.setRevContCurCode(rs.getString("REVCTRCURCOD"));
				cCAdetailsVO.setContCurCode(rs.getString("REVCTRCURCOD"));
				cCAdetailsVO.setGrossWeight(rs.getDouble("REVGRSWGT"));
				cCAdetailsVO.setTax(rs.getDouble("REVSRVTAX"));
				cCAdetailsVO.setRevTax(rs.getDouble("REVSRVTAX"));
				cCAdetailsVO.setTds(rs.getDouble("REVTDS"));
				cCAdetailsVO.setRevTds(rs.getDouble("REVTDS"));
				
				cCAdetailsVO.setRate(rs.getDouble("REVRAT"));
				cCAdetailsVO.setRevisedRate(rs.getDouble("REVRAT"));
				
				}else{
				cCAdetailsVO.setGpaCode(rs.getString("GPACOD"));
				cCAdetailsVO.setGpaName(rs.getString("GPANAM"));
				cCAdetailsVO.setRevGrossWeight(rs.getDouble("GRSWGT"));		
				cCAdetailsVO.setRevGpaCode(rs.getString("GPACOD"));
				cCAdetailsVO.setRevGpaName(rs.getString("GPANAM"));
				cCAdetailsVO.setRevContCurCode(rs.getString("CTRCURCOD"));
				cCAdetailsVO.setContCurCode(rs.getString("CTRCURCOD"));
				cCAdetailsVO.setGrossWeight(rs.getDouble("GRSWGT"));
				cCAdetailsVO.setTax(rs.getDouble("SRVTAX"));
				cCAdetailsVO.setRevTax(rs.getDouble("SRVTAX"));
				cCAdetailsVO.setTds(rs.getDouble("TDS"));
				cCAdetailsVO.setRevTds(rs.getDouble("TDS"));
				
				cCAdetailsVO.setRate(rs.getDouble("RAT"));
				cCAdetailsVO.setRevisedRate(rs.getDouble("RAT"));
				
				
				
				}
				//Modified by A-4809 for BUG ICRD-18489 ...Ends
				cCAdetailsVO.setSectFrom(rs.getString("SECFRM"));
				cCAdetailsVO.setSectTo(rs.getString("SECTOO"));
				Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
				if(lstUpdTime != null) {
					cCAdetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
				}
				cCAdetailsVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));	
				if (rs.getDate("BILPRDTOO") != null) {
					cCAdetailsVO.setBillingPeriodTo(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("BILPRDTOO"))
					.toDisplayDateOnlyFormat());
				}
				if (rs.getDate("BILPRDFRM") != null) {
					cCAdetailsVO.setBillingPeriodFrom(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs.getDate("BILPRDFRM"))
					.toDisplayDateOnlyFormat());
				}
				if (rs.getDate("DSNDAT") != null) {
					cCAdetailsVO.setDsnDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT"))
					.toDisplayDateOnlyFormat());

					cCAdetailsVO.setDsDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT")));
				}
				
				cCAdetailsVO.setCountryCode(rs.getString("CNTCOD"));
				cCAdetailsVO.setBlgDtlSeqNum(rs.getInt("SERNUM"));
				cCAdetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
				cCAdetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
				cCAdetailsVO.setBillingStatus(rs.getString("BLGSTA"));
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setPayFlag(rs.getString("PAYFLG"));
				cCAdetailsVO.setIssueDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).toDisplayDateOnlyFormat());
				cCAdetailsVO.setIssueDat(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				cCAdetailsMap.put(Key, cCAdetailsVO);



			}

		}
		if(cCAdetailsVO!=null){
			cCAdetailsVOs.add(cCAdetailsVO);
		}

		return cCAdetailsVOs;
	}
}