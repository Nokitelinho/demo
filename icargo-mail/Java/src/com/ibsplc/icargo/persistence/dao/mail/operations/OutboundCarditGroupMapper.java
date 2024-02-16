package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundCarditGroupMapper implements Mapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @author a-2553 This method is used to set the values from the resultset
	 *         into Mapper
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the OutboundCarditGroupMapper");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setDestCityDesc(rs.getString("DSTCOD"));
		mailbagVO.setCount(rs.getInt("COUNT"));
		mailbagVO.setAcceptedBags(rs.getInt("ACCPCNT"));
		try {
			mailbagVO.setWeight(Measure.addMeasureValues(mailbagVO.getWeight(), new Measure(UnitConstants.MAIL_WGT,(rs.getDouble("TOTWGT")))));
			
			mailbagVO.setAcceptedWeight(Measure.addMeasureValues(mailbagVO.getAcceptedWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACCPWGT"))));
			}catch (UnitException e1) {
				log.log(Log.SEVERE, "UnitException",e1.getMessage());
		    }
		return mailbagVO;

	}

}

