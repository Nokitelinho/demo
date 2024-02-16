/*
 * FindCCAMapper.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class FindCCAMapper implements Mapper<CCAdetailsVO> {
		
	private Log log = LogFactory.getLogger("FindCCAMapper");
	
 

	/**
	 * @author A-3251
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public CCAdetailsVO map(ResultSet rs) throws SQLException {
				
			CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();			
			cCAdetailsVO.setCcaRefNumber(rs.getString("CCANUM"));
			cCAdetailsVO.setCcaType(rs.getString("MCATYP"));
			cCAdetailsVO.setIssuingParty(rs.getString("ISSPARTY"));			
			if(rs.getDate("ISSDAT")!=null){				
				cCAdetailsVO.setIssueDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("ISSDAT")).toDisplayDateOnlyFormat());
				cCAdetailsVO.setIssueDat(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getTimestamp("ISSDAT")));
		  		}	
			if(rs.getString("REVCTRCURCOD")!=null && rs.getString("REVCTRCURCOD").trim().length()>0){
				Money mailChg=null;
				Money revMailChg=null;
				 Money surChg=null;
				 Money revSurChg=null;
				 Money grossWeightChg=null;
				 Money grossRevWeightChg=null;
				 Money otherChgGrossWeight = null;
				 Money otherChgRevGrossWeight = null;
				try {
					mailChg = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					revMailChg = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					surChg=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					revSurChg=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					grossWeightChg=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					grossRevWeightChg=CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					otherChgGrossWeight = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
					otherChgRevGrossWeight = CurrencyHelper.getMoney(rs.getString("REVCTRCURCOD"));
				} catch (CurrencyException e) {
					// TODO Auto-generated catch block					
				}
				//Modified by A-7794 as part of ICRD-271248
				mailChg.setAmount(rs.getDouble("WGTCHGCTR"));
				revMailChg.setAmount(rs.getDouble("REVWGTCHGCTR"));
				surChg.setAmount(rs.getDouble("OTHCHGCTR"));
				revSurChg.setAmount(rs.getDouble("REVOTHCHGCTR"));
				//cCAdetailsVO.setRevChgGrossWeight(mailChg);
				//cCAdetailsVO.setChgGrossWeight(revMailChg);
				
				cCAdetailsVO.setMailChg(mailChg);
				cCAdetailsVO.setRevMailChg(revMailChg);
				cCAdetailsVO.setSurChg(surChg);
				cCAdetailsVO.setRevSurChg(revSurChg);
				//grossWeightChg.setAmount(mailChg.getAmount()+surChg.getAmount());
				//grossRevWeightChg.setAmount(revMailChg.getAmount()+revSurChg.getAmount());
				grossWeightChg.setAmount(mailChg.getAmount());
				grossRevWeightChg.setAmount(revMailChg.getAmount());
				otherChgGrossWeight.setAmount(surChg.getAmount());
				otherChgRevGrossWeight.setAmount(revSurChg.getAmount());
				cCAdetailsVO.setRevChgGrossWeight(grossRevWeightChg);
				cCAdetailsVO.setChgGrossWeight(grossWeightChg);
				cCAdetailsVO.setOtherChgGrossWgt(otherChgGrossWeight);
				cCAdetailsVO.setOtherRevChgGrossWgt(otherChgRevGrossWeight);
			}
			//Added by A-4809 to displat netValue in MCA Starts
			double revAmount =0;
			double orgAmount=0;
			double revTaxD=0.0;
			double taxD=0;
			double othCharge = 0.0;
			double revOthCharge = 0.0;
			revAmount = rs.getDouble("REVWGTCHGCTR");
			orgAmount = rs.getDouble("WGTCHGCTR");
			revTaxD = rs.getDouble("REVSRVTAX");
			taxD=rs.getDouble("SRVTAX");
			othCharge = rs.getDouble("OTHCHGCTR");
			revOthCharge = rs.getDouble("REVOTHCHGCTR");
			//Added by A-6991 for ICRD-214861 Starts
			BigDecimal revtaxsum = new BigDecimal(0);
			BigDecimal othrchgsum = new BigDecimal(0);
			BigDecimal diffAmount = new BigDecimal(0);
			revtaxsum =new BigDecimal(revAmount+revTaxD+revOthCharge);
			othrchgsum=new BigDecimal(orgAmount+taxD+othCharge);
			diffAmount =revtaxsum.subtract(othrchgsum, MathContext.DECIMAL32);
			//double diffAmount=(revAmount+revTaxD+revOthCharge)-(orgAmount+taxD+othCharge);
			log.log(Log.FINE, "difference amnt calculated---->",diffAmount);
			cCAdetailsVO.setDiffAmount(diffAmount.doubleValue());
			//Added by A-6991 for ICRD-214861 Ends
			//Added by A-4809 to displat netValue in MCA Ends
			cCAdetailsVO.setCcaStatus(rs.getString("MCASTA"));
			cCAdetailsVO.setBillingStatus(rs.getString("BLGSTA"));
			cCAdetailsVO.setDsnNo(rs.getString("DSN"));
			cCAdetailsVO.setBillingBasis(rs.getString("MALIDR"));
			//Added by A-7794 as part of MRA revamp
			cCAdetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			cCAdetailsVO.setRate(rs.getDouble("RAT"));
			cCAdetailsVO.setRevisedRate(rs.getDouble("REVRAT"));
			
			
			//A-8061 Added for ICRD-289296 begin
			cCAdetailsVO.setRevGpaCode(rs.getString("REVGPACOD"));
			cCAdetailsVO.setGpaCode(rs.getString("GPACOD"));
			cCAdetailsVO.setGrossWeight(rs.getDouble("DSPWGT"));
			cCAdetailsVO.setRevGrossWeight(rs.getDouble("REVGRSWGT"));
			//A-8061 Added for ICRD-289296 end
			cCAdetailsVO.setRevContCurCode(rs.getString("REVCTRCURCOD"));
			
			//A-9002 Added for IASCB-22920 begin
			cCAdetailsVO.setDisplayWgtUnit(rs.getString("DSPWGTUNT"));
			//Ends
			
			//Commented by A-7794 as part of MRA revamp
			/*if (rs.getDate("DSPDATE") != null) {				
				cCAdetailsVO.setDsnDate(new LocalDate(
						LocalDate.NO_STATION,Location.NONE, rs.getDate("DSPDATE"))
				.toDisplayDateOnlyFormat());
				
				cCAdetailsVO.setDsDate(new LocalDate(
						LocalDate.NO_STATION,Location.NONE, rs.getDate("DSPDATE")));
			}*/
			if(rs.getDate("BILPRDFRM")!=null ){
				cCAdetailsVO.setBillingPeriodFrom( new LocalDate(
						LocalDate.NO_STATION,Location.NONE, rs.getDate("BILPRDFRM"))
				.toDisplayDateOnlyFormat());
			}
			if(rs.getDate("BILPRDTOO")!=null ){
				cCAdetailsVO.setBillingPeriodTo( new LocalDate(
						LocalDate.NO_STATION,Location.NONE, rs.getDate("BILPRDTOO"))
				.toDisplayDateOnlyFormat());
			}

			return cCAdetailsVO;

		 

	}

}
