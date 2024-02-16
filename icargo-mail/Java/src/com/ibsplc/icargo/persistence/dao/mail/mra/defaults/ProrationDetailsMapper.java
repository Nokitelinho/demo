/*
 * ProrationDetailsMapper.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2554
 *
 */
public class ProrationDetailsMapper implements Mapper<ProrationDetailsVO> {

	private ProrationFilterVO prorationFilterVO;

	private Log log = LogFactory.getLogger("ProrationDetails Mappeer");

	/**
    *
    * @param prorationFilterVO
    */
   public ProrationDetailsMapper(ProrationFilterVO prorationFilterVO) {
       this.prorationFilterVO = prorationFilterVO;
   }


	/**
	 * @author A-2554
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ProrationDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("ProrationDetailsMapper", "map");
		try {

			//Modified by A-7794 as part of MRA revamp

			ProrationDetailsVO prorationDetailsVO = new ProrationDetailsVO();

			prorationDetailsVO.setBillingBasis(rs.getString("MALIDR"));
			prorationDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			prorationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			prorationDetailsVO.setConsigneeDocumentNumber(rs.getString("CSGDOCNUM"));
			prorationDetailsVO.setConsigneeSequenceNumber(rs.getString("CSGSEQNUM"));
			prorationDetailsVO.setSerialNumber(rs.getInt("SERNUM"));

			prorationDetailsVO.setOriginExchangeOffice(rs
					.getString("ORGEXGOFC"));
			prorationDetailsVO.setDestinationExchangeOffice(rs
					.getString("DSTEXGOFC"));
			prorationDetailsVO.setPostalAuthorityCode(rs.getString("POACOD"));
			prorationDetailsVO.setPostalAuthorityName(rs.getString("POANAM"));
			prorationDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			prorationDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
			prorationDetailsVO.setTotalWeight(rs.getString("GRSWGT"));
			prorationDetailsVO.setRsn((rs.getString("RSN")));
			prorationDetailsVO.setDsn((rs.getString("DSN")));
			if (2 == rs.getInt("APHCODUSE")) {
				prorationDetailsVO.setCarrierCode(rs.getString("TWOAPHCOD"));
			} else if (3 == rs.getInt("APHCODUSE")) {
				prorationDetailsVO.setCarrierCode(rs.getString("THRAPHCOD"));
			}
			prorationDetailsVO.setSectorFrom(rs.getString("SECFRM"));
			prorationDetailsVO.setSectorTo(rs.getString("SECTOO"));
			prorationDetailsVO.setNumberOfPieces(rs.getInt("TOTPCS"));
			prorationDetailsVO.setWeight(rs.getDouble("UPDGRSWGT"));
			prorationDetailsVO.setProrationType(rs.getString("PROTYP"));
			prorationDetailsVO.setProrationFactor(rs.getInt("PROVAL"));
			prorationDetailsVO.setProrationPercentage(rs.getDouble("PROPRC"));
			prorationDetailsVO.setPayableFlag(rs.getString("PAYFLG"));
			prorationDetailsVO.setPostalAuthorityName(rs.getString("POANAM"));

			Money amtUSD = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			amtUSD.setAmount(rs.getDouble("WGTCHGUSD"));
			prorationDetailsVO.setProrationAmtInUsd(amtUSD);
			if(prorationFilterVO.getBaseCurrency()!=null){
			Money amtbase = CurrencyHelper.getMoney(prorationFilterVO.getBaseCurrency());
			amtbase.setAmount(rs.getDouble("WGTCHGBAS"));
			prorationDetailsVO.setProrationAmtInBaseCurr(amtbase);
			}
			if(prorationFilterVO.getBaseCurrency()!=null){
			Money amtsdr = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			amtsdr.setAmount(rs.getDouble("WGTCHGXDR"));
			prorationDetailsVO.setProrationAmtInSdr(amtsdr);

			}
			String ctrCurrency=rs.getString("CTRCURCOD");
			if(ctrCurrency!=null){//Added by A-5219 for ICRD-258240 start
			//if(prorationFilterVO.getBaseCurrency()!=null){
			Money amtctr = CurrencyHelper.getMoney(ctrCurrency);

			amtctr.setAmount(rs.getDouble("WGTCHG"));
			prorationDetailsVO.setProratedAmtInCtrCur(amtctr);
			}//Added by A-5219 for ICRD-258240 end
			
			/** Setting Other Currency Details**/
			Money amtOTHUSD = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			amtOTHUSD.setAmount(rs.getDouble("OTHCHGUSD"));
			prorationDetailsVO.setSurProrationAmtInUsd(amtOTHUSD);
			if(prorationFilterVO.getBaseCurrency()!=null){
				Money amtothbase = CurrencyHelper.getMoney(prorationFilterVO.getBaseCurrency());
				amtothbase.setAmount(rs.getDouble("OTHCHGBAS"));
				prorationDetailsVO.setSurProrationAmtInBaseCurr(amtothbase);
				
				Money amtothsdr = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
				amtothsdr.setAmount(rs.getDouble("OTHCHGXDR"));
				prorationDetailsVO.setSurProrationAmtInSdr(amtothsdr);
				//Modified by A-7794 as part of ICRD-267522
				Money amtothctr = CurrencyHelper.getMoney(ctrCurrency);
				amtothctr.setAmount(rs.getDouble("OTHCHGCTR"));
				prorationDetailsVO.setSurProratedAmtInCtrCur(amtothctr);
			}
			
			
			
			prorationDetailsVO.setCtrCurrencyCode(ctrCurrency);
			prorationDetailsVO.setSectorStatus(rs.getString("SECTSTA"));
			
			//prorationDetailsVO.setGpaarlBillingFlag(rs.getString("GPAARLBLGFLG"));
			//prorationDetailsVO.setRate(rs.getDouble("BLGRAT"));
			if(rs.getDate("RCVDAT")!=null)
			{
			prorationDetailsVO.setRecVDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("RCVDAT")));
			}
			prorationDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
			prorationDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			if(rs.getDate("FLTDAT")!=null){
			prorationDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("FLTDAT")));
			}
			prorationDetailsVO.setProratPercentage(rs.getString("PROPRC"));
		
			
			/*added by indu for log*/
			prorationDetailsVO.setAppAud(rs.getString("APPAUD"));
			prorationDetailsVO.setBillTo(rs.getString("BILTOOPTYCOD"));
			prorationDetailsVO.setGpaarlBillingFlag(rs.getString("BILTOOPTYTYP"));   //added by a-7531 for icrd- 273674
			prorationDetailsVO.setWgtChg(rs.getDouble("WGTCHGCTR"));
			prorationDetailsVO.setBlgSta(rs.getString("BLGSTA"));
			prorationDetailsVO.setAccSta(rs.getString("ACCSTA"));
			prorationDetailsVO.setAccTxnIdr(rs.getString("ACCTXNIDR"));
			prorationDetailsVO.setDueArl(rs.getDouble("DUEARL"));
			prorationDetailsVO.setDuePoa(rs.getDouble("DUEPOA"));
			prorationDetailsVO.setRevFlg(rs.getString("REVFLG"));
			prorationDetailsVO.setSegSerNum(rs.getInt("SEGSERNUM"));
			prorationDetailsVO.setBsaReference(rs.getString("BSAREF"));
			
			prorationDetailsVO.setDisplayWeightUnit(rs.getString("DSPWGTUNT"));
			prorationDetailsVO.setCodeShareIndicator(rs.getString("CODSHRIND"));
			
			
			return prorationDetailsVO;

		} catch (CurrencyException e) {
			e.getErrorCode();
		}

		return null;
	}

}
