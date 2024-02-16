/*
 * SurchargeGPAC66MultiMapper.java Created on Jul 16, 2015
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 16, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 16, 2015	     A-5255		First draft
 */

public class SurchargeGPAC66MultiMapper implements MultiMapper<SurchargeBillingDetailVO>{
private CN51CN66FilterVO cn51CN66FilterVO=null;
private Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
	/**
	 * Constructor
	 * @param cn51CN66FilterVO
	 */
	public SurchargeGPAC66MultiMapper(CN51CN66FilterVO cn51CN66FilterVO){
		this.cn51CN66FilterVO=cn51CN66FilterVO;
	}
	/**
	  * @author A-5255
	  * @param arg0
	  * @return
	  * @throws SQLException
	  * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	  */
	
	@Override
	public List<SurchargeBillingDetailVO> map(ResultSet rs) throws SQLException {
		String surchargeBillingDetailsKey = null;
		Map<String, SurchargeBillingDetailVO> surchargeBillingDetailMap = new HashMap<String, SurchargeBillingDetailVO>();
		SurchargeBillingDetailVO surchargeBillingDetailVO=null;
		log.log(Log.FINE, cn51CN66FilterVO);
		while (rs.next()) {
			surchargeBillingDetailsKey= new StringBuilder()
			.append(rs.getString("CMPCOD"))
			.append(rs.getString("INVNUM"))
			.append(rs.getString("MALSEQNUM"))
			.append(rs.getString("MALIDR"))//modified for icrd-256997
			.append(rs.getString("GPACOD"))
			.append(rs.getString("CSGSEQNUM"))
			.append(rs.getString("CSGDOCNUM"))
			.append(rs.getString("CHGTYP")).toString();
			if (!surchargeBillingDetailMap
					.containsKey(surchargeBillingDetailsKey)) {
				surchargeBillingDetailVO=new SurchargeBillingDetailVO();
				surchargeBillingDetailVO.setCompanyCode(rs.getString("CMPCOD"));
				surchargeBillingDetailVO.setInvoiceNumber(rs.getString("INVNUM"));
				surchargeBillingDetailVO.setSequenceNumber(rs.getLong("MALSEQNUM"));
				surchargeBillingDetailVO.setBillingBasis(rs.getString("MALIDR"));//modified for icrd-256997
				surchargeBillingDetailVO.setGpaCode(rs.getString("GPACOD"));
				surchargeBillingDetailVO.setChargeHead(rs.getString("CHGTYP"));
				surchargeBillingDetailVO.setApplicableRate(rs.getDouble("APLRAT"));
				if(rs.getString("STLCUR")!=null &&rs.getString("STLCUR").trim().length()>0){
					Money amtctr;
					try {
						amtctr = CurrencyHelper.getMoney(rs.getString("STLCUR"));
						amtctr.setAmount(rs.getDouble("CHGAMTBLGCUR"));//modified for icrd-256997
						surchargeBillingDetailVO.setChargeAmt(amtctr);
					} catch (CurrencyException e) {
						e.getErrorCode();
					}
					
				}
				
				surchargeBillingDetailVO.setConsigneeDocumentNumber(rs.getString("CSGDOCNUM"));
				surchargeBillingDetailVO.setConsigneeSequenceNumber(rs.getString("CSGSEQNUM"));
				surchargeBillingDetailMap.put(surchargeBillingDetailsKey, surchargeBillingDetailVO);
			}
		}
		return new ArrayList(surchargeBillingDetailMap.values());
	}

}
