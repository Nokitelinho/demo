/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1883
 *
 */
public class LoyaltyPointsMapper implements 
					Mapper<AirWayBillLoyaltyProgramVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return LoyaltyAttributeVO
	 * @throws SQLException
	 */
	public AirWayBillLoyaltyProgramVO map(ResultSet resultSet) 
		throws SQLException {
		log.entering("LoyaltyPointsMapper","Map");
		AirWayBillLoyaltyProgramVO loyaltyProgramVO = 
			new AirWayBillLoyaltyProgramVO();
		
		loyaltyProgramVO.setLoyaltyProgrammeCode(resultSet.getString("LTYPRGCOD"));
		loyaltyProgramVO.setLoyaltyAttribute(resultSet.getString("LTYATR"));
		loyaltyProgramVO.setLoyaltyAttributeUnit(resultSet.getString("LTYUNT"));
		loyaltyProgramVO.setPointsAccrued(resultSet.getDouble("PTSARD"));
		
		log.exiting("LoyaltyPointsMapper","Map");
		return loyaltyProgramVO;
		
	}
}
