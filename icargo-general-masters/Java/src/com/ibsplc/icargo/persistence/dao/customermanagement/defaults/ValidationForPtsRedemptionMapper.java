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
 * 
 * @author a-1496
 *
 */
public class ValidationForPtsRedemptionMapper implements
		Mapper<LoyaltyProgrammeVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	/**
	 * @param resultSet
	 * @return LoyaltyProgrammeVO
	 * @throws SQLException
	 */
	public LoyaltyProgrammeVO map(ResultSet resultSet) throws SQLException {
		log.entering("ValidationForPtsRedemptionMapper", "Map");
		LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
		loyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet
				.getString("LTYPRGCOD"));
		loyaltyProgrammeVO.setExpiryDuration(resultSet.getString("EXPDUR"));
		loyaltyProgrammeVO.setExpiryPeriod(resultSet.getDouble("EXPPER"));
		if (resultSet.getDate("TOODAT") != null) {
			loyaltyProgrammeVO.setToDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("TOODAT")));
		}
		log.exiting("ValidationForPtsRedemptionMapper", "Map");
		return loyaltyProgrammeVO;

	}
}
