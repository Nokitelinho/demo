package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class FetchMailindicatorMapper implements MultiMapper<OperationalFlightVO> {

	@Override
	public List<OperationalFlightVO> map(ResultSet rs) throws SQLException {
		
		List<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		
		while(rs.next()) {
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
			operationalFlightVO.setFlightNumber(rs.getString("FLTNUM"));
			operationalFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			operationalFlightVOs.add(operationalFlightVO);
		}
		return operationalFlightVOs;  
		
	}

}
