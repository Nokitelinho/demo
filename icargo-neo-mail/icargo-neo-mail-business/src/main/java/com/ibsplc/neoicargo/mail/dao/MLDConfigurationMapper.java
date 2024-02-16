package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MLDConfigurationVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-5526
 */
@Slf4j
public class MLDConfigurationMapper implements Mapper<MLDConfigurationVO> {
	private static final String CLASS_NAME = "MLDConfigurationMapper";

	/** 
	* @author A-5526
	* @param rs
	* @throws SQLException
	* @return SpaCodeLovVO implemented method for mapping result set with thevo
	*/
	public MLDConfigurationVO map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		MLDConfigurationVO mLDConfigurationVO = new MLDConfigurationVO();
		mLDConfigurationVO.setCompanyCode(rs.getString("CMPCOD"));
		mLDConfigurationVO.setAirportCode(rs.getString("ARPCOD"));
		mLDConfigurationVO.setAllocatedRequired(rs.getString("ALLREQFLG"));
		mLDConfigurationVO.setCarrierIdentifier(rs.getInt("CARIDR"));
		mLDConfigurationVO.setDeliveredRequired(rs.getString("DLVREQFLG"));
		mLDConfigurationVO.sethNDRequired(rs.getString("HNDREQFLG"));
		mLDConfigurationVO.setReceivedRequired(rs.getString("RECREQFLG"));
		mLDConfigurationVO.setUpliftedRequired(rs.getString("UPLREQFLG"));
		mLDConfigurationVO.setStagedRequired(rs.getString("STGREQFLG"));
		mLDConfigurationVO.setNestedRequired(rs.getString("NSTREQFLG"));
		mLDConfigurationVO.setReceivedFromFightRequired(rs.getString("RCFREQFLG"));
		mLDConfigurationVO.setTransferredFromOALRequired(rs.getString("TFDREQFLG"));
		mLDConfigurationVO.setReceivedFromOALRequired(rs.getString("RCTREQFLG"));
		mLDConfigurationVO.setReturnedRequired(rs.getString("RETREQFLG"));
		mLDConfigurationVO.setMldversion(rs.getString("MLDVER"));
		return mLDConfigurationVO;
	}
}
