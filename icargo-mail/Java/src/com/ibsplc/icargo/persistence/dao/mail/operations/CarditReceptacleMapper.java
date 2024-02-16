package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class CarditReceptacleMapper implements Mapper<CarditReceptacleVO> {
	
	public CarditReceptacleVO map(ResultSet rs) throws SQLException {
		CarditReceptacleVO carditReceptacleVO	= new CarditReceptacleVO();
	
			
			carditReceptacleVO.setCarditType(rs.getString("CDTTYP"));
			carditReceptacleVO.setCarditKey(rs.getString("CDTKEY"));
			 
		
		
		return carditReceptacleVO;
		
	}

	

}
   