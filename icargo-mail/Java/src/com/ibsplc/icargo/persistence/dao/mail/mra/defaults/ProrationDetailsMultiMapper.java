/*
 * ProrationDetailsMultiMapper.java Created on Mar 07, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2518
 *
 */
public class ProrationDetailsMultiMapper implements
		MultiMapper<ProrationDetailsVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ProrationDetailsMultiMapper";

	/**
	 * @param rs
	 * @return List<ProrationDetailsVO>
	 * @throws SQLException
	 */
	public List<ProrationDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		Collection<ProrationDetailsVO> prorationDetailsVos = new ArrayList<ProrationDetailsVO>();
		Map<String, ProrationDetailsVO> prorationDetailsMap = new HashMap<String, ProrationDetailsVO>();
		String prorationDetailsKey = null;
		ProrationDetailsVO prorationDetailsVo = null;
		while (rs.next()) {
			prorationDetailsKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("SERNUM")).append(
					rs.getString("BLGBASNUM")).toString();
			if (!prorationDetailsMap.containsKey(prorationDetailsKey)) {
				try{
				prorationDetailsVo = new ProrationDetailsVO();
				prorationDetailsVo.setBillingBasis(rs.getString("BASTYP"));
				prorationDetailsVo.setBillingBasisNumber(rs
						.getString("BLGBASNUM"));
				prorationDetailsVo.setCompanyCode(rs.getString("CMPCOD"));
				prorationDetailsVo.setConsigneeDocumentNumber(rs
						.getString("CSGDOCNUM"));
				prorationDetailsVo.setDestinationExchangeOffice(rs
						.getString("DSTEXGOFC"));
				prorationDetailsVo.setFlightCarrierIdentifier(rs
						.getInt("FLTCARIDR"));
				if (rs.getDate("FLTDAT") != null) {
					prorationDetailsVo.setFlightDate(new LocalDate(NO_STATION,
							NONE, rs.getDate("FLTDAT")));
				}
				prorationDetailsVo.setFlightNumber(rs.getString("FLTNUM"));
				prorationDetailsVo.setFlightSequenceNumber(rs
						.getInt("FLTSEQNUM"));
				prorationDetailsVo.setMailCategoryCode(rs
						.getString("MALCTGCOD"));
				prorationDetailsVo.setMailSubclass(rs.getString("MALSUBCLS"));
				prorationDetailsVo.setNumberOfPieces(rs.getInt("PCS"));
				prorationDetailsVo.setOriginExchangeOffice(rs
						.getString("ORGEXGOFC"));
				prorationDetailsVo.setPayableFlag(rs.getString("PAYFLG"));
				prorationDetailsVo.setPostalAuthorityCode(rs
						.getString("POACOD"));
				prorationDetailsVo.setPostalAuthorityName(rs
						.getString("POANAM"));
				if(rs.getString("BASCUR")!=null && rs.getString("BASCUR").trim().length()>0){
					Money amtBas=CurrencyHelper.getMoney(rs.getString("BASCUR"));
					amtBas.setAmount(rs.getDouble("PROAMTBAS"));
				prorationDetailsVo.setProrationAmtInBaseCurr(amtBas);
				}
				Money amtSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
				amtSdr.setAmount(rs.getDouble("PROAMTSDR"));
				prorationDetailsVo.setProrationAmtInSdr(amtSdr);
				Money amtUsd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
				amtUsd.setAmount(rs.getDouble("PROAMTUSD"));
				prorationDetailsVo.setProrationAmtInUsd(amtUsd);
				prorationDetailsVo.setProrationType(rs.getString("PROTYP"));
				prorationDetailsVo.setProrationPercentage(rs
						.getDouble("PROPRC"));
				prorationDetailsVo.setSectorFrom(rs.getString("SECFRM"));
				prorationDetailsVo.setSectorTo(rs.getString("SECTOO"));
				prorationDetailsVo.setSegmentSequenceNumber(rs
						.getInt("SEGSERNUM"));
				prorationDetailsVo.setSerialNumber(rs.getInt("SERNUM"));
				prorationDetailsVo.setCarrierCode(rs.getString(""));
				prorationDetailsVo.setWeight(rs.getDouble("WGT"));
				if (2 == rs.getInt("APHCODUSE")) {
					prorationDetailsVo
							.setCarrierCode(rs.getString("TWOAPHCOD"));
				} else if (3 == rs.getInt("APHCODUSE")) {
					prorationDetailsVo
							.setCarrierCode(rs.getString("THRAPHCOD"));
				}
				prorationDetailsVo.setProratedValue(rs.getDouble("PROVAL"));
				prorationDetailsMap
						.put(prorationDetailsKey, prorationDetailsVo);
				prorationDetailsVos.add(prorationDetailsVo);
				/*
				 * Modifed by A-2518 for displaying prorated amount in contract
				 * currency
				 */
				if(rs.getString("ARLCURCOD")!=null && rs.getString("ARLCURCOD").trim().length()>0){
					Money arlAmt=CurrencyHelper.getMoney(rs.getString("ARLCURCOD"));
					arlAmt.setAmount(rs.getDouble("PROAMTCTR"));
					prorationDetailsVo.setProratedAmtInCtrCur(arlAmt);
				}else if(rs.getString("GPACURCOD")!=null && rs.getString("GPACURCOD").trim().length()>0){
					Money gpaAmt=CurrencyHelper.getMoney(rs.getString("GPACURCOD"));
					gpaAmt.setAmount(rs.getDouble("PROAMTCTR"));
					prorationDetailsVo.setProratedAmtInCtrCur(gpaAmt);
				}
				}
				catch(CurrencyException e){
					e.getErrorCode();
				}



			}
		}
		log.exiting(CLASS_NAME, "map");
		return (ArrayList<ProrationDetailsVO>) prorationDetailsVos;
	}
}
