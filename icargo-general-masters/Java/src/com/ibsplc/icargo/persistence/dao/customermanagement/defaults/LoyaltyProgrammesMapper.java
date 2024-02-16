/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class LoyaltyProgrammesMapper implements Mapper<LoyaltyProgrammeVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return LoyaltyProgrammeVO
	 * @throws SQLException
	 */
	public LoyaltyProgrammeVO map(ResultSet resultSet) 
	throws SQLException {
		log.entering("LoyaltyProgrammesMapper","Map");
		LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
		loyaltyProgrammeVO = new LoyaltyProgrammeVO();
		loyaltyProgrammeVO.setCompanyCode(
				resultSet.getString("CMPCOD"));
		loyaltyProgrammeVO.setLoyaltyProgrammeCode(
				resultSet.getString("LTYPRGCOD"));
		loyaltyProgrammeVO.setActiveStatus(
				resultSet.getString("ACTSTA"));
		loyaltyProgrammeVO.setEntryPoints(
				resultSet.getDouble("ENTPTS"));
		loyaltyProgrammeVO.setExpiryDuration(
				resultSet.getString("EXPDUR"));
		loyaltyProgrammeVO.setExpiryPeriod(
				resultSet.getDouble("EXPPER"));
		loyaltyProgrammeVO.setAttibute(
				resultSet.getString("ATR"));
		loyaltyProgrammeVO.setUnits(
				resultSet.getString("UNT"));
		loyaltyProgrammeVO.setAmount(resultSet.getDouble("AMT"));
		loyaltyProgrammeVO.setPoints(resultSet.getDouble("PTS"));
		Date fromDate = resultSet.getDate("FRMDAT");
		if(fromDate != null){
			loyaltyProgrammeVO.setFromDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,fromDate));
		}
		Date toDate = resultSet.getDate("TOODAT");
		if(toDate != null){
			 loyaltyProgrammeVO.setToDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,toDate));
		}
		
		loyaltyProgrammeVO.setLoyaltyProgrammeDesc(resultSet.getString("LTYPRGDES"));
		loyaltyProgrammeVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
		Date lstUpdTime = resultSet.getDate("LSTUPDTIM");
		if(lstUpdTime != null){
			loyaltyProgrammeVO.setLastUpdatedTime(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,lstUpdTime));
		}
		log.exiting("LoyaltyProgrammesMapper","Map");
		return loyaltyProgrammeVO;
		
	}
}
