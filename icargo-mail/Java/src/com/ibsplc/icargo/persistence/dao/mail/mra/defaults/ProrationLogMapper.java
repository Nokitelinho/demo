
	/*
	 * ProrationLogMapper.java Created on Sep 17, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

	import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 *
	 */
	public class ProrationLogMapper implements Mapper<MailProrationLogVO> {
		
		private DSNFilterVO dsnFilterVO;
		
		private Log log = LogFactory.getLogger("Proration log Mappeer");
		
		/**
	    *
	    * @param prorationFilterVO the prorationFilterVO to set
	    */
	   public ProrationLogMapper(DSNFilterVO dsnFilterVO) {
	       this.dsnFilterVO = dsnFilterVO;
	   }
		
	
		


		/**
		 * @author A-3229
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public MailProrationLogVO map(ResultSet rs) throws SQLException {

			
			log.entering("ProrationLogMapper", "map");	
			MailProrationLogVO mailProrationLogVO = new MailProrationLogVO();
			
			mailProrationLogVO.setBillingBasis(rs.getString("MALIDR"));
			mailProrationLogVO.setCompanyCode(rs.getString("CMPCOD"));
			mailProrationLogVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
			mailProrationLogVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
			mailProrationLogVO.setSerialNumber(rs.getInt("SERNUM"));
			//mailProrationLogVO.setSequenceNumber(rs.getInt("SEQNUM"));
			mailProrationLogVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailProrationLogVO.setPoaCode(rs.getString("POACOD"));
			
			mailProrationLogVO.setBlgStatus((rs.getString("MRASTA")));
			mailProrationLogVO.setVersionNo(rs.getInt("VERNUM"));// Added by A-8164 for ICRD-283073   
			mailProrationLogVO.setCarriageFrom(rs.getString("SECFRM"));
			mailProrationLogVO.setCarriageTo(rs.getString("SECTOO"));
			if (2 == rs.getInt("APHCODUSE")) {
				mailProrationLogVO.setCarrierCode(rs.getString("TWOAPHCOD"));
			} else if (3 == rs.getInt("APHCODUSE")) {
				mailProrationLogVO.setCarrierCode(rs.getString("THRAPHCOD"));
			}
			
			mailProrationLogVO.setTriggerPoint(rs.getString("TRGPNT"));
		  if(rs.getTimestamp("LSTUPDTIM")!=null){
			
			mailProrationLogVO.setDateTime(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,	
					rs.getTimestamp("LSTUPDTIM")));
		  }

			
			mailProrationLogVO.setUser(rs.getString("LSTUPDUSR"));
			mailProrationLogVO.setRemarks(rs.getString("RMK"));
			
			mailProrationLogVO.setPieces(rs.getInt("TOTPCS"));
			mailProrationLogVO.setWeight(rs.getDouble("UPDGRSWGT"));
			mailProrationLogVO.setProrationType(rs.getString("PROTYP"));
			mailProrationLogVO.setProrationFactor(rs.getInt("PROVAL"));
			mailProrationLogVO.setProrationPercentage(rs.getDouble("PROPRC")); 
			mailProrationLogVO.setPaymentFlag(rs.getString("PAYFLG"));
			mailProrationLogVO.setPostalAuthorityName(rs.getString("POANAM"));
			
			
			try {
				Money amtInUSD = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			
				amtInUSD.setAmount(rs.getDouble("WGTCHGUSD"));
			mailProrationLogVO.setWgtChargeUsd(amtInUSD);

			Money amtInbase = CurrencyHelper.getMoney(dsnFilterVO.getBaseCurrency());
			
			amtInbase.setAmount(rs.getDouble("WGTCHGBAS"));
			mailProrationLogVO.setWgtChargeBas(amtInbase);

			Money amtInXdr = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			amtInXdr.setAmount(rs.getDouble("WGTCHGXDR"));
			mailProrationLogVO.setWgtChargeSdr(amtInXdr);
			
			String ctrCurrency=rs.getString("CTRCURCOD");
			if(ctrCurrency!=null){
			Money amtInCtr = CurrencyHelper.getMoney(ctrCurrency);
			
			amtInCtr.setAmount(rs.getDouble("WGTCHGCTR"));
			mailProrationLogVO.setWgtCharge(amtInCtr);
			} 
			}catch (CurrencyException e) {
				e.getErrorCode();
			}
			return mailProrationLogVO;
	}
		
	}



