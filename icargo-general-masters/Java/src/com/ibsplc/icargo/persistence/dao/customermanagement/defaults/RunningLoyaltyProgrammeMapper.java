/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 *
 */
public class RunningLoyaltyProgrammeMapper  implements 
 			Mapper<LoyaltyProgrammeVO>{
	private Log log = LogFactory.getLogger("CUSTOMER_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return LoyaltyProgrammeVO
	 * @throws SQLException
	 */
	public LoyaltyProgrammeVO map(ResultSet resultSet) 
		throws SQLException {
		log.entering("RunningLoyaltyProgrammeMapper","Map");
		LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
		loyaltyProgrammeVO.setCompanyCode(resultSet.getString("CMPCOD"));
		loyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet.getString("LTYPRGCOD"));
		loyaltyProgrammeVO.setLoyaltyProgrammeDesc(resultSet.getString("LTYPRGDES"));
		loyaltyProgrammeVO.setFromDate(new LocalDate(
				LocalDate.NO_STATION,Location.NONE,
				resultSet.getDate("FRMDAT")));
		loyaltyProgrammeVO.setToDate(new LocalDate(
				LocalDate.NO_STATION,Location.NONE,
				resultSet.getDate("TOODAT")));
		log.exiting("RunningLoyaltyProgrammeMapper","Map");
		return loyaltyProgrammeVO;
		
	}

}
