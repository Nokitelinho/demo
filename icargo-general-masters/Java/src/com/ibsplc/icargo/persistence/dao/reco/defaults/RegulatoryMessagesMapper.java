package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class RegulatoryMessagesMapper implements MultiMapper<RegulatoryMessageVO>{

	@Override
	public List<RegulatoryMessageVO> map(ResultSet resultset)
			throws SQLException {
		List<RegulatoryMessageVO> regulatoryMessages= new ArrayList<RegulatoryMessageVO>();
		RegulatoryMessageVO regulatoryMessage=null;
		 while (resultset.next()) {
			 regulatoryMessage= new RegulatoryMessageVO();
			 regulatoryMessage.setCompanyCode(resultset.getString("CMPCOD"));
			 regulatoryMessage.setMessage(resultset.getString("MSGDES"));
			 regulatoryMessage.setRolGroup(resultset.getString("ROLGRPCOD"));
			 regulatoryMessage.setOperationFlag("U");
			 regulatoryMessage.setSerialNumber(new Integer(resultset.getString("SERNUM")).intValue());
			 if (resultset.getDate("STRDAT") != null) {
				 regulatoryMessage.setStartDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultset.getDate("STRDAT")));
			 }
			 if (resultset.getDate("ENDDAT") != null) {
				 regulatoryMessage.setEndDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultset.getDate("ENDDAT")));
			 }
			 if (resultset.getTimestamp("LSTUPDTIM") != null) {
				 regulatoryMessage.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultset.getTimestamp("LSTUPDTIM")));
			 }
			 if(resultset.getTimestamp("UPDTXNTIMUTC") != null) {
				GMTDate updatedTransactionTime = new GMTDate(resultset.getTimestamp("UPDTXNTIMUTC"));
				regulatoryMessage.setUpdatedTransactionTime(updatedTransactionTime);
			}
			 regulatoryMessages.add(regulatoryMessage);
		 }
		return regulatoryMessages;
	}

}
