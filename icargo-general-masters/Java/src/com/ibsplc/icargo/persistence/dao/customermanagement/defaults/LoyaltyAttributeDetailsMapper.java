/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;

/**
 * @author A-1883
 *
 */
public class LoyaltyAttributeDetailsMapper implements 
					Mapper<LoyaltyAttributeVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return LoyaltyAttributeVO
	 * @throws SQLException
	 */
	public LoyaltyAttributeVO map(ResultSet resultSet) 
		throws SQLException {
		log.entering("ListLoyaltyProgrammesMultiMapper","Map");
		LoyaltyAttributeVO loyaltyAttributeVO = new LoyaltyAttributeVO();
		loyaltyAttributeVO.setAttribute(resultSet.getString("ATR"));
		loyaltyAttributeVO.setUnit(resultSet.getString("UNT"));
		loyaltyAttributeVO.setUnitDescription(resultSet.getString("UNTDES"));
		log.exiting("ListLoyaltyProgrammesMultiMapper","Map");
		return loyaltyAttributeVO;
		
	}
}
