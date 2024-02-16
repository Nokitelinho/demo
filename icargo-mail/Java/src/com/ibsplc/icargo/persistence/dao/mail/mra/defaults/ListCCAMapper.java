/*
 * ListCCAMapper.java Created on Aug08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
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
 * 
 * @author a-3429
 * 
 */
public class ListCCAMapper implements Mapper<CCAdetailsVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCCAMapper";
	

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public CCAdetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		CCAdetailsVO   ccaDetailsVO =null;
		try {
		 ccaDetailsVO = new CCAdetailsVO();

		if (rs.getString("CMPCOD") != null) {
			ccaDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		}
		if (rs.getString("MALIDR") != null) {
			ccaDetailsVO.setBillingBasis(rs.getString("MALIDR"));
		}
		//Added by A-7794 as part of MRA revamp
		if (rs.getString("MALSEQNUM") != null) {
			ccaDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		}
		//Added by A-6991 for ICRD-211662 Starts
		if (rs.getString("BLGSTA") != null) {
			ccaDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
		}
		//Added by A-6991 for ICRD-211662 Ends
		if (rs.getString("CSGDOCNUM") != null) {
			ccaDetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
		}
			ccaDetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
		if (rs.getString("POACOD") != null) {
				ccaDetailsVO.setPoaCode(rs.getString("POACOD"));
		}
		if (rs.getString("DSN") != null) {
			ccaDetailsVO.setDsnNo(rs.getString("DSN"));
		}
		if (rs.getInt("SERNUM") != 0) {	//added for ICRD-282931
			ccaDetailsVO.setBlgDtlSeqNum(rs.getInt("SERNUM"));
		}
//		Date date = rs.getDate("DSPDATE");
//		if (date != null) {
//			ccaDetailsVO.setDsDate(new LocalDate(LocalDate.NO_STATION,
//					Location.NONE, date));
//			String dspDate = rs.getString("DSPDATE");
//			String[] dspdat = dspDate.split(" ");
//			ccaDetailsVO.setDsnDate(dspdat[0]);
//		}
		if (rs.getString("ORGEXGOFC") != null) {
			ccaDetailsVO.setOriginOE(rs.getString("ORGEXGOFC"));
		}
		if (rs.getString("DSTEXGOFC") != null) {
			ccaDetailsVO.setDestinationOE(rs.getString("DSTEXGOFC"));
		}
		if (rs.getString("MALCTGCOD") != null) {
			ccaDetailsVO.setCategoryCode(rs.getString("MALCTGCOD"));
		}
		if (rs.getString("YER") != null) {
			ccaDetailsVO.setYear(rs.getString("YER"));
		}
		if (rs.getString("RSN") != null) {
			ccaDetailsVO.setRsn(rs.getString("RSN"));
		}
		if (rs.getString("HSN") != null) {
			ccaDetailsVO.setHni(rs.getString("HSN"));
		}
		if (rs.getString("REGIND") != null) {
			ccaDetailsVO.setRegind(rs.getString("REGIND"));
		}
		if (rs.getString("MALSUBCLS") != null) {
			ccaDetailsVO.setSubClass(rs.getString("MALSUBCLS"));
		}
		if (rs.getString("REVGPACOD") != null) {
			ccaDetailsVO.setGpaCode(rs.getString("REVGPACOD"));
		}
		if (rs.getString("POANAM") != null) {
			ccaDetailsVO.setGpaName(rs.getString("POANAM"));
		}
		//Added by A-7540
		if(rs.getString("REVGRSWGT") != null) {
			ccaDetailsVO.setRevGrossWeight(rs.getDouble("REVGRSWGT"));
		}
		if(rs.getString("DSPWGTUNT") != null) {
			ccaDetailsVO.setDisplayWgtUnit(rs.getString("DSPWGTUNT"));
		}		
		if(rs.getString("REVCTRCURCOD") != null) {
			ccaDetailsVO.setRevContCurCode(rs.getString("REVCTRCURCOD"));
		}
		if (rs.getString("MCATYP") != null) {
			ccaDetailsVO.setCcaType(rs.getString("MCATYP"));
		}
		if (rs.getString("ISSPARTY") != null) {
			ccaDetailsVO.setIssuingParty(rs.getString("ISSPARTY"));
		}
		Date issueDat=rs.getDate("ISSDAT");
		if(issueDat!=null){
			ccaDetailsVO.setIssueDat(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, issueDat));
			
		}
		if (rs.getString("ISSDAT") != null) {
			String issueDate = rs.getString("ISSDAT");
			String[] issdat = issueDate.split(" ");
			ccaDetailsVO.setIssueDate(issdat[0]);
		}
		if (rs.getDate("DSNDAT") != null) {//added for ICRD-282931
			ccaDetailsVO.setDsnDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT"))
			.toDisplayDateOnlyFormat());
			
			ccaDetailsVO.setDsDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE, rs.getDate("DSNDAT")));
		}
		if(rs.getString("CTRCURCOD")!=null){
	    	
   		 Money amount = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
   		 double dueArl=rs.getDouble("DUEARL");
   		 double revDueArl=rs.getDouble("REVDUEARL");
   		 double netDueArl=revDueArl-dueArl;
   		 amount.setAmount(netDueArl);
   		 ccaDetailsVO.setRevDueArl(amount);
   		
   		 Money duepoa = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
   		 double duePstDbt=rs.getDouble("DUEPSTDBT");
   		 double revduePstDbt=rs.getDouble("REVDUEPSTDBT");
   		 double netDuePstDbt=revduePstDbt-duePstDbt;
   		 duepoa.setAmount(revduePstDbt);
   		 
   		 ccaDetailsVO.setRevDuePostDbtDisp(duepoa);
	     ccaDetailsVO.setRevDuePostDbt(netDuePstDbt);	    
   		 
   		 
	   }
		}
	     catch(CurrencyException e) {
	        throw new SQLException(e.getErrorCode());
	    }
	    
	    
		if (rs.getString("MCAREFNUM") != null) {
			ccaDetailsVO.setCcaRefNumber(rs.getString("MCAREFNUM"));
		}
		if (rs.getString("MCASTA") != null) {
			ccaDetailsVO.setCcaStatus(rs.getString("MCASTA"));
			log.log(Log.INFO, "CCASTATUS=====>", ccaDetailsVO.getCcaStatus());
		}
		if (rs.getString("MCAREFNUM") != null) {
			ccaDetailsVO.setUsrccanum(rs.getString("MCAREFNUM"));
			log.log(Log.INFO, "CCASTATUS=====>", ccaDetailsVO.getCcaStatus());
		}
		
		if (rs.getString("MCARMK") != null) {
			ccaDetailsVO.setCcaRemark(rs.getString("MCARMK"));
		}
		if(rs.getString("MCARSNCOD")!=null){
			ccaDetailsVO.setMcaReasonCodes(rs.getString("MCARSNCOD"));
		}
		ccaDetailsVO.setOriginCode(rs.getString("ORGARPCOD"));
		ccaDetailsVO.setDestnCode(rs.getString("DSTARPCOD"));
		//Added by A-4809 to displat netValue in MCA Starts
		double revAmount =0;
		double orgAmount=0;
		double revTaxD=0.0;
		double taxD=0;
		double othchg=0.0;
		double revOthChg=0.0;
		revAmount = rs.getDouble("REVWGTCHGCTR");
		orgAmount = rs.getDouble("WGTCHGCTR");
		revTaxD = rs.getDouble("REVSRVTAX");
		taxD=rs.getDouble("SRVTAX");
		//Added for ICRD-138988 starts
		othchg=rs.getDouble("OTHCHGCTR");
		revOthChg=rs.getDouble("REVOTHCHGCTR");
		//Added by A-6991 for ICRD-208114 Starts
		BigDecimal totRevAmt = new BigDecimal(0);
		BigDecimal orgGrsAmt = new BigDecimal(0);
		BigDecimal totRevOthChg = new BigDecimal(0);
		BigDecimal totNetAmt = new BigDecimal(0);
		BigDecimal diffAmount = new BigDecimal(0);
		totRevAmt =new BigDecimal(revAmount+revTaxD);
		orgGrsAmt =new BigDecimal(orgAmount+taxD);
		totRevOthChg = new BigDecimal(revOthChg-othchg);
		totNetAmt =totRevAmt.add(totRevOthChg);
		diffAmount =totNetAmt.subtract(orgGrsAmt, MathContext.DECIMAL32);
		//Added by A-6991 for ICRD-208114 Ends
		
		//double diffAmount=(revAmount+revTaxD)-(orgAmount+taxD)+(revOthChg-othchg);
		//Added for ICRD-138988 starts ends
		log.log(Log.FINE, "difference amnt calculated---->",diffAmount);
		ccaDetailsVO.setDiffAmount(diffAmount.doubleValue());
		//Added by A-4809 to displat netValue in MCA Ends
		Money chgGrossWgt=null;
		Money revChgGrossWgt=null;
		if(rs.getString("CTRCURCOD")!=null){
			try {
				chgGrossWgt=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				chgGrossWgt.setAmount(rs.getDouble("WGTCHGCTR"));
				ccaDetailsVO.setChgGrossWeight(chgGrossWgt);
				revChgGrossWgt=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				revChgGrossWgt.setAmount(rs.getDouble("REVWGTCHGCTR"));
				ccaDetailsVO.setRevChgGrossWeight(revChgGrossWgt);
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block				
			}
		}
		return ccaDetailsVO;
	}

}
