package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mailmasters.vo.MailSubClassVO;
import com.ibsplc.neoicargo.mailmasters.vo.USPSPostalCalendarVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** 
 * @author This class is used to Map the Resultset into MailSubClassVO
 */
public class InvoicPeriodDetailsMapper implements Mapper<USPSPostalCalendarVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public USPSPostalCalendarVO map(ResultSet rs) throws SQLException {

		USPSPostalCalendarVO uspsPostalCalendarVO = new USPSPostalCalendarVO();
		uspsPostalCalendarVO.setGpacod(rs.getString("GPACOD"));
		//TODO: Neo to correct the below code
//		uspsPostalCalendarVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PRDFRM")));
//		uspsPostalCalendarVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PRDTOO")));
		return uspsPostalCalendarVO;
	}
}
