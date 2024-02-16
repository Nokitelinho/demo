/*
 * SurchargeProrationDetailsMapper.java Created on Jul 7, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 7, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 7, 2015 A-5255
 * First draft
 */

public class SurchargeProrationDetailsMapper implements
		MultiMapper<SurchargeProrationDetailsVO> {
	private ProrationFilterVO prorationFilterVO;
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	public SurchargeProrationDetailsMapper(ProrationFilterVO prorationFilterVO) {
		this.prorationFilterVO = prorationFilterVO;
	}

	/**
	 * @author A-5255
	 * @param arg0
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */

	@Override
	public List<SurchargeProrationDetailsVO> map(ResultSet rs)
			throws SQLException {
		log.entering("SurchargeProrationDetailsMapper", "map");
		Map<String, SurchargeProrationDetailsVO> surchargeProrationDetailsVOMap = new HashMap<String, SurchargeProrationDetailsVO>();
		String surchargeProrationDetailsKey = null;
		Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = new ArrayList<SurchargeProrationDetailsVO>();
		SurchargeProrationDetailsVO surchargeProrationDetailsVO = null;
		while (rs.next()) {
			try {
				surchargeProrationDetailsKey = new StringBuilder()
						.append(rs.getString("CMPCOD"))
						.append(rs.getString("CSGSEQNUM"))
						.append(rs.getString("CSGDOCNUM"))
						.append(rs.getString("POACOD"))
						.append(rs.getString("BLGBAS"))
						.append(rs.getString("SERNUM"))
						.append(rs.getString("CHGCOD")).toString();
				if (!surchargeProrationDetailsVOMap
						.containsKey(surchargeProrationDetailsKey)) {

					surchargeProrationDetailsVO = new SurchargeProrationDetailsVO();
					surchargeProrationDetailsVO.setCompanyCode(rs
							.getString("CMPCOD"));

					surchargeProrationDetailsVO.setSequenceNumber(rs
							.getInt("SERNUM"));
					surchargeProrationDetailsVO.setCsgSeqNumber(rs
							.getInt("CSGSEQNUM"));
					surchargeProrationDetailsVO.setCsgDocumentNumber(rs
							.getString("CSGDOCNUM"));
					surchargeProrationDetailsVO.setBillingBasis(rs
							.getString("BLGBAS"));
					surchargeProrationDetailsVO.setPoaCode(rs
							.getString("POACOD"));

					surchargeProrationDetailsVO.setChargeHead(rs
							.getString("CHGCOD"));//Modified for icrd-254289

					Money amtOTHUSD = CurrencyHelper
							.getMoney(MRAConstantsVO.CURRENCY_USD);
					amtOTHUSD.setAmount(rs.getDouble("CHGAMTUSD"));//Modified for icrd-254289
					surchargeProrationDetailsVO.setProrationAmtInUsd(amtOTHUSD);
					
					if (prorationFilterVO.getBaseCurrency() != null) {
						surchargeProrationDetailsVO.setBaseCurrency(prorationFilterVO.getBaseCurrency());
						Money amtothbase = CurrencyHelper
								.getMoney(prorationFilterVO.getBaseCurrency());
						amtothbase.setAmount(rs.getDouble("CHGAMTBAS"));//Modified for icrd-254289
						surchargeProrationDetailsVO
								.setProrationAmtInBaseCurr(amtothbase);

						Money amtothsdr = CurrencyHelper
								.getMoney(MRAConstantsVO.CURRENCY_XDR);
						amtothsdr.setAmount(rs.getDouble("CHGAMTXDR"));//Modified for icrd-254289
						surchargeProrationDetailsVO
								.setProrationAmtInSdr(amtothsdr);

						Money amtothctr = CurrencyHelper
								.getMoney(prorationFilterVO.getBaseCurrency());
						amtothctr.setAmount(rs.getDouble("CHGAMTCTR"));//Modified for icrd-254289
						surchargeProrationDetailsVO
								.setProrationValue(amtothctr);
						Money totalAmtOTHUSD= CurrencyHelper
								.getMoney(MRAConstantsVO.CURRENCY_USD);
						Money totalAmtothbase= CurrencyHelper
								.getMoney(prorationFilterVO.getBaseCurrency());
						Money totalAmtothsdr= CurrencyHelper
								.getMoney(MRAConstantsVO.CURRENCY_XDR);
						Money totalAmtothctr= CurrencyHelper
								.getMoney(prorationFilterVO.getBaseCurrency());
						totalAmtOTHUSD.setAmount(rs.getDouble("CHGVALUSDS"));//Modified for icrd-254289
						totalAmtothbase.setAmount(rs.getDouble("CHGVALBASS"));//Modified for icrd-254289
						totalAmtothsdr.setAmount(rs.getDouble("CHGVALSDRS"));//Modified for icrd-254289
						totalAmtothctr.setAmount(rs.getDouble("CHGVALS"));//Modified for icrd-254289
						surchargeProrationDetailsVO.setTotalProrationAmtInUsd(totalAmtOTHUSD);
						surchargeProrationDetailsVO.setTotalProrationAmtInSdr(totalAmtothsdr);
						surchargeProrationDetailsVO.setTotalProrationAmtInBaseCurr(totalAmtothbase);
						surchargeProrationDetailsVO.setTotalProrationValue(totalAmtothctr);
						
						if(rs.getString("SURCHGRAT")!=null){
						surchargeProrationDetailsVO.setSurchargeRate(rs.getDouble("SURCHGRAT"));
						surchargeProrationDetailsVO.setSurchargeRevisedRate(rs.getDouble("REVSURCHGRAT"));
						}
						
						
					}
					surchargeProrationDetailsVOs.add(surchargeProrationDetailsVO);
					surchargeProrationDetailsVOMap.put(
							surchargeProrationDetailsKey,
							surchargeProrationDetailsVO);

				}
			} catch (CurrencyException e) {
				e.getErrorCode();
			}
		}
		return (ArrayList<SurchargeProrationDetailsVO>) surchargeProrationDetailsVOs;
	}
}
