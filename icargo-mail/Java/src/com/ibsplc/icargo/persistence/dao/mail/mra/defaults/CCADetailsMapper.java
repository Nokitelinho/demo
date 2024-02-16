/*
 * CCADetailsMapper.java Created on July 15,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

/**
 * @author A-3447,A-3227 RENO K ABRAHAM
 */
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447,A-3227 RENO K ABRAHAM
 */

public class CCADetailsMapper implements MultiMapper<CCAdetailsVO> {

	private static final String CLASS_NAME = "CCADetailsMapper";
	private static final String FLAG_YES = "Y";
	private static final String ACTULDWGTUNT = "ACTULDWGTUNT";
	
	
	
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * @author A-3447,A-3227
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<CCAdetailsVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "CCADetailsMapper");
		List<CCAdetailsVO> cCAdetailsVOs= new ArrayList<CCAdetailsVO>();
		CCAdetailsVO cCAdetailsVO = null;
		DSNRoutingVO dSNRoutingVO = null;
		Collection<DSNRoutingVO> dSNRoutingVOs = null;
		Map<String,CCAdetailsVO> cCAdetailsMap=  new HashMap<String,CCAdetailsVO>();
		String Key = null;
		//Modified by A-7794 as part of MRA revamp
		while (rs.next()) {
			Key = new StringBuilder()
			.append(rs.getString("CMPCOD")).append("-")
			.append(rs.getString("MALIDR")).append("-")
			.append(rs.getString("MALSEQNUM")).append("-")
			.append(rs.getString("POACOD")).toString();

			
			if(!cCAdetailsMap.containsKey(Key)){		
				cCAdetailsVO = new CCAdetailsVO();		
				dSNRoutingVOs = new ArrayList<DSNRoutingVO>();
				dSNRoutingVO = new DSNRoutingVO();
				
				dSNRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
				dSNRoutingVO.setBillingBasis(rs.getString("MALIDR"));
				dSNRoutingVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				if (rs.getDate("FLTDAT") != null) {
					dSNRoutingVO.setDepartureDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("FLTDAT")));
				}
				dSNRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
				dSNRoutingVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
				dSNRoutingVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
				dSNRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
				dSNRoutingVO.setPol(rs.getString("POL"));
				dSNRoutingVO.setPou(rs.getString("POU"));
				
				dSNRoutingVOs.add(dSNRoutingVO);
				cCAdetailsVO.setDsnRoutingVOs(dSNRoutingVOs);
				cCAdetailsVO.setSerialNumber(rs.getInt("SERNUM"));
				cCAdetailsVO.setUsrccanum(rs.getString("MCAREFNUM"));
				cCAdetailsVO.setCcaRefNumber(rs.getString("MCAREFNUM"));
				cCAdetailsVO.setBillingBasis(rs.getString("MALIDR"));
				cCAdetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				cCAdetailsVO.setCompanyCode(rs.getString("CMPCOD"));						
				cCAdetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
				cCAdetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
				cCAdetailsVO.setPoaCode(rs.getString("POACOD"));
				//Added as part of IASCB-860
				cCAdetailsVO.setMcaReasonCodes(rs.getString("MCARSNCOD"));
				if(rs.getString("ISSPARTY")!=null && rs.getString("ISSPARTY").length()>0){
					cCAdetailsVO.setIssuingParty(rs.getString("ISSPARTY"));
				}
				/*else if(rs.getString("UPDBILLTO")!=null && rs.getString("UPDBILLTO").length()>0){
					cCAdetailsVO.setIssuingParty(rs.getString("UPDBILLTO"));					
				}*/
				cCAdetailsVO.setUpdBillTo(rs.getString("UPDBILTOOPOA"));
				cCAdetailsVO.setUpdBillToIdr(rs.getInt("BILTOOARLIDR"));
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setAirlineCode(rs.getString("ARLCOD"));
				//cCAdetailsVO.setLocation(rs.getString("LOCCOD"));
				cCAdetailsVO.setCcaType(rs.getString("MCATYP"));
				cCAdetailsVO.setCcaStatus(rs.getString("MCASTA"));
				cCAdetailsVO.setOrigin(rs.getString("DSNORGCOD"));
				cCAdetailsVO.setDestination(rs.getString("DSNDSTCOD"));
				cCAdetailsVO.setOriginCode(rs.getString("ORGARPCOD"));
				cCAdetailsVO.setDestnCode(rs.getString("DSTARPCOD"));
				cCAdetailsVO.setCategory(rs.getString("CATCOD"));
				cCAdetailsVO.setSubClass(rs.getString("SUBCLS"));
				cCAdetailsVO.setDsnNo(rs.getString("DSN"));
				if ("Y".equals(rs.getString("POAFLG")) && FLAG_YES.equals(rs.getString("CNTRATENBPOA"))
						&& rs.getString(ACTULDWGTUNT) != null && !rs.getString(ACTULDWGTUNT).isEmpty()
						&& rs.getDouble("ACTULDWGT") != 0) {
					cCAdetailsVO.setGrossWeight(rs.getDouble("ACTULDWGT"));
					cCAdetailsVO.setDisplayWgtUnit(rs.getString(ACTULDWGTUNT));
				} else {
					cCAdetailsVO.setGrossWeight(rs.getDouble("DSPWGT"));
					cCAdetailsVO.setDisplayWgtUnit(rs.getString("DSPWGTUNT"));
				}
				
				cCAdetailsVO.setPayFlag(rs.getString("PAYFLG"));
				//Added by A-9002 for IASCB-22920	
			
				cCAdetailsVO.setDisplayWeightUnit(cCAdetailsVO.getDisplayWgtUnit());
				cCAdetailsVO.setRateLineWeightUnit(rs.getString("UNTCOD"));
				cCAdetailsVO.setAutoMca(rs.getString("AUTMCA"));
				if(rs.getString("CTRCURCOD")!=null && !rs.getString("CTRCURCOD").isEmpty()){
				Money grossWgtCharge = null;
				Money grossOtherCharge = null;
				Money dueArl = null;
				Money duePostDbt = null;
				Money revChgGrossWeight = null;
				Money otherRevChgGrossWgt = null;
				Money revDueArl = null;
				
				try {
					//Added as part of ICRD-343570
					if(rs.getString("CTRCURCOD")!=null){
					grossWgtCharge = CurrencyHelper.getMoney(rs.getString("CTRCURCOD")); // Modified by A-8399 as part of ICRD-305527
					grossOtherCharge= CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
					}	
                    //Added as part of ICRD-343570
					if(rs.getDouble("CHGWGT")!=0.0)
					grossWgtCharge.setAmount(rs.getDouble("CHGWGT"));					
					//Added as part of ICRD-343570
                    if(rs.getDouble("CHGWGT")!=0.0)
					grossOtherCharge.setAmount(rs.getDouble("OTHCHG"));
					cCAdetailsVO.setChgGrossWeight(grossWgtCharge);
					cCAdetailsVO.setOtherChgGrossWgt(grossOtherCharge);
					//Added as part of ICRD-343570
                    if(rs.getString("CTRCURCOD")!=null)
					dueArl = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
                    //Added as part of ICRD-343570
					if(rs.getDouble("DUEARL")!=0.0)
					dueArl.setAmount(rs.getDouble("DUEARL"));
					cCAdetailsVO.setDueArl(dueArl);
                    //Added as part of ICRD-343570 
					if(rs.getString("CTRCURCOD")!=null)
					duePostDbt = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
                    //Added as part of ICRD-343570
					if(rs.getDouble("DUEPSTDBT")!=0.0)
					duePostDbt.setAmount(rs.getDouble("DUEPSTDBT"));					
					cCAdetailsVO.setDuePostDbt(duePostDbt);					
					if(rs.getString("REVCTRCURCOD")==null||rs.getString("REVCTRCURCOD").trim().length()==0){//MODIFIED FOR 	ICRD-312154	
						//Added as part of ICRD-343570
                      if(rs.getString("CTRCURCOD")!=null){
						revChgGrossWeight=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						otherRevChgGrossWgt = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						revDueArl = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						}
					}else{
					revChgGrossWeight = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					otherRevChgGrossWgt = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					revDueArl = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
				}
                    //Added as part of ICRD-343570
					if(rs.getDouble("REVWGTCHGCTR")!=0.0)
					revChgGrossWeight.setAmount(rs.getDouble("REVWGTCHGCTR"));					
					cCAdetailsVO.setRevChgGrossWeight(revChgGrossWeight);
				
					//Added by A-8527 for ICRD-293398 starts 
					if(rs.getDouble("REVOTHCHGCTR")!=0){
					otherRevChgGrossWgt.setAmount(rs.getDouble("REVOTHCHGCTR"));					
					}else{
                        //Added as part of ICRD-343570
						if(rs.getDouble("OTHCHG")!=0.0)
						otherRevChgGrossWgt.setAmount(rs.getDouble("OTHCHG"));
					}
					//Added by A-8527 for ICRD-293398 Ends
					cCAdetailsVO.setOtherRevChgGrossWgt(otherRevChgGrossWgt);
					 //Added as part of ICRD-343570
					if(rs.getDouble("REVDUEARL")!=0.0)
					revDueArl.setAmount(rs.getDouble("REVDUEARL"));					
					cCAdetailsVO.setRevDueArl(revDueArl);
					
				} catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
					e.getErrorCode();
				}		
				}
				//8331cCAdetailsVO.setActualULDWeight(rs.getDouble("ACTULDWGT"));
			//8331	cCAdetailsVO.setActualULDWeightUnit(rs.getString("ACTULDWGTUNT"));
				cCAdetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
				cCAdetailsVO.setGpaCode(rs.getString("GPACOD"));
				cCAdetailsVO.setGpaName(rs.getString("GPANAM"));
				cCAdetailsVO.setRevGrossWeight(rs.getDouble("REVGRSWGT"));	
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setRevOrgCode(rs.getString("REVORGCOD"));
				//cCAdetailsVO.setRevDStCode(rs.getString("REVDSTCOD"));				
				cCAdetailsVO.setRevDuePostDbt(rs.getDouble("REVDUEPSTDBT"));
				cCAdetailsVO.setRevGpaCode(rs.getString("REVGPACOD"));
				cCAdetailsVO.setRevGpaName(rs.getString("REVGPANAM"));
				//cCAdetailsVO.setRevContCurCode(rs.getString("REVCTRCURCOD"));
				cCAdetailsVO.setCcaRemark(rs.getString("MCARMK"));
				cCAdetailsVO.setGrossWeightChangeInd(rs.getString("GRSWGTIND"));
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setWriteOffInd(rs.getString("WRTOFFIND"));
				cCAdetailsVO.setGpaChangeInd(rs.getString("GPACHGIND"));
				cCAdetailsVO.setWeightChargeChangeInd(rs.getString("WGTCHGIND"));
				//Commented by A-7794 as part of MRA revamp
				//cCAdetailsVO.setDoeChangeInd(rs.getString("EXGOFCIND"));
				//cCAdetailsVO.setGpaArlIndicator(rs.getString("ARLGPAIND"));
				cCAdetailsVO.setCurrChangeInd(rs.getString("CURCHGIND"));
				cCAdetailsVO.setSectFrom(rs.getString("SECFRM"));
				cCAdetailsVO.setSectTo(rs.getString("SECTOO"));
				
				//Added by A-7929 as part of ICRD-132548 starts ----
				if(rs.getDouble("RAT") != 0){
				cCAdetailsVO.setRate(rs.getDouble("RAT"));
				}else{
					cCAdetailsVO.setRate(rs.getDouble("APLRAT"));
				}
				if(rs.getDouble("REVRAT") == 0){
					cCAdetailsVO.setRevisedRate(rs.getDouble("APLRAT"));
				}else
				cCAdetailsVO.setRevisedRate(rs.getDouble("REVRAT"));
				//Added by A-7929 as part of ICRD-132548 ends ----

				
				
              //added as part of ICRD-292873 starts
				if(rs.getString("REVCTRCURCOD")==null||rs.getString("REVCTRCURCOD").trim().length()==0){
					cCAdetailsVO.setContCurCode(rs.getString("CTRCURCOD"));
					cCAdetailsVO.setRevContCurCode(rs.getString("CTRCURCOD"));
				}
				else{	
					//Modified by A-7794 as part of ICRD-301109
					cCAdetailsVO.setContCurCode(rs.getString("CTRCURCOD"));
					cCAdetailsVO.setRevContCurCode(rs.getString("REVCTRCURCOD"));
				}
				//added as part of ICRD-292873 ends

				Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
			     if(lstUpdTime != null) {
			    	 cCAdetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
			     }
				cCAdetailsVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));	
				if (rs.getDate("BLGPRDTOO") != null) {
					cCAdetailsVO.setBillingPeriodTo(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("BLGPRDTOO"))
					.toDisplayDateOnlyFormat());
				}
				if (rs.getDate("BLGPRDFRM") != null) {
					cCAdetailsVO.setBillingPeriodFrom(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs.getDate("BLGPRDFRM"))
					.toDisplayDateOnlyFormat());
				}
				if (rs.getDate("ISSDAT") != null) {
					cCAdetailsVO.setIssueDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("ISSDAT"))
					.toDisplayDateOnlyFormat());
					cCAdetailsVO.setIssueDat(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getTimestamp("ISSDAT"))
					);
				}
				if (rs.getDate("DSNDAT") != null) {
					cCAdetailsVO.setDsnDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT"))
					.toDisplayDateOnlyFormat());
					
					cCAdetailsVO.setDsDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT")));
				}
				cCAdetailsVO.setTax(rs.getDouble("SRVTAX"));
				cCAdetailsVO.setRevTax(rs.getDouble("REVSRVTAX"));
				if(rs.getString("CTRCURCOD")!=null && !rs.getString("CTRCURCOD").isEmpty()){
				Money netAmount=null;
				Money revNetAmount= null;
				Money differenceAmount=null;
				Money valCharges = null;
				//Added by A-6991 for ICRD-208114
				BigDecimal revtaxsum = new BigDecimal(0);
				BigDecimal othrchgsum = new BigDecimal(0);
				BigDecimal diffamnt = new BigDecimal(0);
				BigDecimal wtChrgeInCTR = new BigDecimal(0);
				wtChrgeInCTR = new BigDecimal(rs.getDouble("CHGWGT"));
				cCAdetailsVO.setWtChgAmtinCTR(wtChrgeInCTR.doubleValue());
				if(rs.getString("CTRCURCOD")!=null){
					try {
						netAmount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						revNetAmount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						differenceAmount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
						valCharges=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
					} catch (CurrencyException e) {
						// TODO Auto-generated catch block						
					}
					netAmount.setAmount((rs.getDouble("NETAMT")));
					revNetAmount.setAmount((rs.getDouble("REVNETAMT")));
					valCharges.setAmount(rs.getDouble("VALCHGCTR"));
					cCAdetailsVO.setValChgUpdAmount(valCharges);
					cCAdetailsVO.setNetAmount(netAmount);		
					cCAdetailsVO.setRevNetAmount(revNetAmount);
					
					
					/*if(rs.getDouble("REVWGTCHGCTR")==0 && rs.getString("MCAREFNUM")==null)
					{
						differenceAmount.setAmount(0);
					}
					else
					{*/
						if(rs.getString("MCAREFNUM")!=null){
						revtaxsum= new BigDecimal(rs.getDouble("REVWGTCHGCTR") +rs.getDouble("REVOTHCHGCTR")+ rs.getDouble("REVSRVTAX"));
						othrchgsum= new BigDecimal(rs.getDouble("CHGWGT") +rs.getDouble("OTHCHG")+ rs.getDouble("SRVTAX"));
						diffamnt=revtaxsum.subtract(othrchgsum, MathContext.DECIMAL32);
					
							/*differenceAmount.setAmount( ( rs.getDouble("REVCHGWGT") +rs.getDouble("REVOTHCHG")+ rs.getDouble("REVSRVTAX") )-( rs.getDouble("CHGWGT") +rs.getDouble("OTHCHG")+ rs.getDouble("SRVTAX") ));*/
						differenceAmount.setAmount(diffamnt.doubleValue());//Commented and Modified for ICRD-208114 
					//}
					}
					
					
					cCAdetailsVO.setDifferenceAmount(differenceAmount);
					
				}
				}
				cCAdetailsVO.setTds(rs.getDouble("TDS"));
				cCAdetailsVO.setRevTds(rs.getDouble("REVTDS"));
				cCAdetailsVO.setCountryCode(rs.getString("CNTCOD"));
				cCAdetailsVO.setBlgDtlSeqNum(rs.getInt("SERNUM"));
				cCAdetailsVO.setBillingStatus(rs.getString("BLGSTA"));
				cCAdetailsMap.put(Key, cCAdetailsVO);
				
			}else{
				cCAdetailsVO=cCAdetailsMap.get(Key);
				dSNRoutingVOs=cCAdetailsVO.getDsnRoutingVOs();				
				dSNRoutingVO = new DSNRoutingVO();
				
				dSNRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
				dSNRoutingVO.setBillingBasis(rs.getString("MALIDR"));
				dSNRoutingVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				if (rs.getDate("FLTDAT") != null) {
					dSNRoutingVO.setDepartureDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE, rs.getDate("FLTDAT")));
				}
				dSNRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
				dSNRoutingVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
				dSNRoutingVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
				dSNRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
				dSNRoutingVO.setPol(rs.getString("POL"));
				dSNRoutingVO.setPou(rs.getString("POU"));
				
				if(dSNRoutingVO!=null && !dSNRoutingVOs.contains(dSNRoutingVO)){
					dSNRoutingVOs.add(dSNRoutingVO);
				}				
				cCAdetailsVO.setDsnRoutingVOs(dSNRoutingVOs);
				
			}
			
		}
		
				if(cCAdetailsMap!=null){//modified as part of ICRD-293512
					for (CCAdetailsVO cCADetailsVO:cCAdetailsMap.values()){
						cCAdetailsVOs.add(cCADetailsVO);
			}
			
		}
	
			
		return cCAdetailsVOs;
	}
}
