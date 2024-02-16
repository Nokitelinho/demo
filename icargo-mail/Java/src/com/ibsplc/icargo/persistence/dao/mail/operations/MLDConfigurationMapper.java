/*
 * MLDConfigurationMapper.java Created on Dec 19, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5526
 * 
 */
public class MLDConfigurationMapper implements Mapper<MLDConfigurationVO> {
	private Log log = LogFactory.getLogger("MAIL");

	private static final String CLASS_NAME = "MLDConfigurationMapper";

	/**
	 * @author A-5526
	 * @param rs
	 * @throws SQLException
	 * @return SpaCodeLovVO implemented method for mapping result set with the
	 *         vo
	 */

	public MLDConfigurationVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		MLDConfigurationVO mLDConfigurationVO = new MLDConfigurationVO();
		mLDConfigurationVO.setCompanyCode(rs.getString("CMPCOD"));
		mLDConfigurationVO.setAirportCode(rs.getString("ARPCOD"));
		mLDConfigurationVO.setAllocatedRequired(rs.getString("ALLREQFLG"));
		//Commented as part of  bug ICRD-143797 by A-5526
		//mLDConfigurationVO.setCarrierCode(rs.getString("CARCOD"));
		mLDConfigurationVO.setCarrierIdentifier(rs.getInt("CARIDR"));
		mLDConfigurationVO.setDeliveredRequired(rs.getString("DLVREQFLG"));
		mLDConfigurationVO.sethNDRequired(rs.getString("HNDREQFLG"));
		mLDConfigurationVO.setReceivedRequired(rs.getString("RECREQFLG"));
		mLDConfigurationVO.setUpliftedRequired(rs.getString("UPLREQFLG"));
		
		//Added for CRQ ICRD-135130 by A-8061 starts

		mLDConfigurationVO.setStagedRequired(rs.getString("STGREQFLG"));
		mLDConfigurationVO.setNestedRequired(rs.getString("NSTREQFLG"));
		mLDConfigurationVO.setReceivedFromFightRequired(rs.getString("RCFREQFLG"));
		mLDConfigurationVO.setTransferredFromOALRequired(rs.getString("TFDREQFLG"));
		mLDConfigurationVO.setReceivedFromOALRequired(rs.getString("RCTREQFLG"));
		mLDConfigurationVO.setReturnedRequired(rs.getString("RETREQFLG"));
		mLDConfigurationVO.setMldversion(rs.getString("MLDVER"));
		//Added for CRQ ICRD-135130 by A-8061 end

		return mLDConfigurationVO;

	}
}
