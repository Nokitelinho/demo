/*
 * BillingLineChargeMultiMapper.java Created on Jul 1, 2015
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
import java.util.LinkedHashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 1, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 1, 2015 A-5255
 * First draft
 */

public class BillingLineChargeMultiMapper implements MultiMapper<BillingLineVO> {
	
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	/**
	 * @author A-5255
	 * @param arg0
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */

	@Override
	public List<BillingLineVO> map(ResultSet rs) throws SQLException {
		LinkedHashMap<String, BillingLineVO> billingLineMap = new LinkedHashMap<String, BillingLineVO>();
		LinkedHashMap<String, BillingLineDetailVO> billingLineDtlMap = new LinkedHashMap<String, BillingLineDetailVO>();
		LinkedHashMap<String, BillingLineChargeVO> billingLineChargeDtlMap = new LinkedHashMap<String, BillingLineChargeVO>();
		StringBuilder billingLineDtlStringBuffer = null;
		StringBuilder billingLineChgDtlStringBuffer = null;
		String blgLineKey = null;
		BillingLineVO billingLineVO = null;
		BillingLineDetailVO billingLineDetailVO = null;
		while (rs.next()) {
			blgLineKey = new StringBuilder().append(rs.getString("BLGMTXCOD"))
					.append(rs.getString("CMPCOD"))
					.append(rs.getInt("BLGLINSEQNUM")).toString();
			if (!billingLineMap.containsKey(blgLineKey)) {
				billingLineVO = new BillingLineVO();
				billingLineVO.setBillingMatrixId(rs.getString("BLGMTXCOD"));
				billingLineVO.setCompanyCode(rs.getString("CMPCOD"));
				billingLineVO.setBillingLineSequenceNumber(rs
						.getInt("BLGLINSEQNUM"));
				billingLineVO
						.setBillingLineDetails(new ArrayList<BillingLineDetailVO>());
				billingLineMap.put(blgLineKey, billingLineVO);
			}

			if (rs.getString("CHGTYP") != null) {
				billingLineDtlStringBuffer = new StringBuilder()
						.append(rs.getString("BLGMTXCOD"))
						.append(rs.getString("CMPCOD"))
						.append(rs.getInt("BLGLINSEQNUM"))
						.append(rs.getString("CHGTYP"));
				if (!billingLineDtlMap.containsKey(billingLineDtlStringBuffer
						.toString())) {
					billingLineDetailVO = new BillingLineDetailVO();
					billingLineDtlMap.put(
							billingLineDtlStringBuffer.toString(),
							billingLineDetailVO);
					billingLineDetailVO.setChargeType(rs.getString("CHGTYP"));
					billingLineDetailVO.setRatingBasis(rs.getString("RATBSS"));
					billingLineVO = billingLineMap.get(blgLineKey.toString());
					billingLineVO.getBillingLineDetails().add(
							billingLineDetailVO);
	                if (billingLineDetailVO.getChargeType() != null
							&& !"".equals(billingLineDetailVO.getChargeType())
							&& !"M".equals(billingLineDetailVO
									.getChargeType()) && rs.getString("RATBSS")!=null) {// modified a-7871 for ICRD-214766
						if(billingLineVO!=null){
							billingLineVO.setSurchargeIndicator("Y");
						}
					}   
				} else {
					billingLineDetailVO = billingLineDtlMap
							.get(billingLineDtlStringBuffer.toString());
				}
				// if(rs.getInt("FRMWGT") > 0){
				billingLineChgDtlStringBuffer = new StringBuilder()
						.append(rs.getString("BLGMTXCOD"))
						.append(rs.getString("CMPCOD"))
						.append(rs.getInt("BLGLINSEQNUM"))
						.append(rs.getString("CHGTYP"))
						.append(rs.getDouble("FRMWGT"));
				if (!billingLineChargeDtlMap
						.containsKey(billingLineChgDtlStringBuffer.toString())) {
					BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
						billingLineChargeVO.setApplicableRateCharge(rs.getDouble("APPRAT"));
						//Added as part of ICRD-150833 starts
						if(rs.getString("CURCOD")!=null){
	    				 Money aplRatChg=null;
	    					try {
	    						aplRatChg = CurrencyHelper
	    						.getMoney(rs.getString("CURCOD"));
	    					} catch (CurrencyException e) {
	    						log.log(Log.FINE, e.getMessage());
	    					}
	    					aplRatChg.setAmount(billingLineChargeVO.getApplicableRateCharge());
	    					billingLineChargeVO.setAplRatChg(aplRatChg);
						}
	    					//Added as part of ICRD-150833 ends
					billingLineChargeVO.setFrmWgt(rs.getDouble("FRMWGT"));
					//Added for ICRD-149264 starts
					if(rs.getString("RATTYP")!=null){
					billingLineChargeVO.setRateType(rs.getString("RATTYP"));
					}
					//Added for ICRD-149264 ends
					billingLineChargeDtlMap.put(
							billingLineChgDtlStringBuffer.toString(),
							billingLineChargeVO);
					if (billingLineDetailVO != null) {
						if (billingLineDetailVO.getBillingLineCharges() == null) {
							billingLineDetailVO
									.setBillingLineCharges(new ArrayList<BillingLineChargeVO>());
						}
						billingLineDetailVO.getBillingLineCharges().add(
								billingLineChargeVO);

					}
				}
				// }

			}
		}
		return new ArrayList<BillingLineVO>(billingLineMap.values());
	}

}
