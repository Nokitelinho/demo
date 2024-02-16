/*
 * SurchargeCCADetailsMapper.java Created on Jul 9, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-5255 
 * @version	0.1, Jul 9, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 9, 2015	     A-5255		First draft
 */

public class SurchargeCCADetailsMapper implements Mapper<SurchargeCCAdetailsVO>{

	/**
	  * @author A-5255
	  * @param arg0
	  * @return
	  * @throws SQLException
	  * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	  * 
	  */
	
	private MaintainCCAFilterVO maintainCCAFilterVO;
	public SurchargeCCADetailsMapper(MaintainCCAFilterVO maintainCCAFilterVO) {
		this.maintainCCAFilterVO = maintainCCAFilterVO;
	}
	@Override
	public SurchargeCCAdetailsVO map(ResultSet rs) throws SQLException {
		//Map<String, SurchargeCCAdetailsVO> surchargeCCAdetailsVOMap = new HashMap<String, SurchargeCCAdetailsVO>();
		//String surchargeCCAdetailsKey = null;
		//Collection<SurchargeCCAdetailsVO> surchargeProrationDetailsVOs = new ArrayList<SurchargeCCAdetailsVO>();
		SurchargeCCAdetailsVO surchargeCCAdetailsVO = null;
		//while (rs.next()) {
			try {
				/*surchargeCCAdetailsKey = new StringBuilder()
						.append(rs.getString("CMPCOD"))
						.append(rs.getInt("BLGBAS"))
						.append(rs.getString("CSGDOCNUM"))
						.append(rs.getString("CSGSEQNUM"))
						.append(rs.getString("CCAREFNUM"))
						.append(rs.getString("CHGTYP")).toString();
				if (!surchargeCCAdetailsVOMap
						.containsKey(surchargeCCAdetailsKey)) {*/
					surchargeCCAdetailsVO=new SurchargeCCAdetailsVO();
					surchargeCCAdetailsVO.setCompanyCode(rs.getString("CMPCOD"));
					surchargeCCAdetailsVO.setBillingBasis(rs.getString("BLGBAS"));
					surchargeCCAdetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
					surchargeCCAdetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
					surchargeCCAdetailsVO.setChargeHeadName(rs.getString("CHGCOD"));
					//Added by A-7540
					surchargeCCAdetailsVO.setSurchareOrgRate(rs.getDouble("ORGRAT"));
					surchargeCCAdetailsVO.setSurchargeRevRate(rs.getDouble("REVRAT"));
					
					
					
					
					
					if (maintainCCAFilterVO.getBaseCurrency() != null) {
						Money orginalCharge = CurrencyHelper
								.getMoney(maintainCCAFilterVO.getBaseCurrency());
						Money revisedCharge = CurrencyHelper
								.getMoney(maintainCCAFilterVO.getBaseCurrency());
						
						//orginalCharge.setAmount(rs.getDouble("ORGCHGAMT"));
						orginalCharge.setAmount(rs.getDouble("ORGCHGAMTCTR"));//Modified by A-7929 as part of ICRD-267635
						revisedCharge.setAmount(rs.getDouble("REVCHGAMTCTR"));//Modified by A-7929 as part of ICRD-267635
						//Modified as part of ICRD-143976

						surchargeCCAdetailsVO
								.setOrgSurCharge(orginalCharge);
						surchargeCCAdetailsVO.setRevSurCharge(revisedCharge);
						
					}
					//surchargeProrationDetailsVOs.add(surchargeCCAdetailsVO);
					//surchargeCCAdetailsVOMap.put(surchargeCCAdetailsKey, surchargeCCAdetailsVO);
				//}
			} catch (CurrencyException e) {
				e.getErrorCode();
			}
		//}
		return surchargeCCAdetailsVO;
	}

	
	

}
