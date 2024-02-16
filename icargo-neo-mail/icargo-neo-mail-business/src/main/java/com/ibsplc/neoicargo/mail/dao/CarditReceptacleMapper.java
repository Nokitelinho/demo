package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.CarditReceptacleVO;

public class CarditReceptacleMapper implements Mapper<CarditReceptacleVO> {
	public CarditReceptacleVO map(ResultSet rs) throws SQLException {
		CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
		carditReceptacleVO.setCarditType(rs.getString("CDTTYP"));
		carditReceptacleVO.setCarditKey(rs.getString("CDTKEY"));
		return carditReceptacleVO;
	}
}
