package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class SecurityScreeningValidationsMapper implements MultiMapper<SecurityScreeningValidationVO> {

	public List<SecurityScreeningValidationVO> map(ResultSet rs) throws SQLException {
		
		ArrayList<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
	
		while (rs.next()) {
			SecurityScreeningValidationVO securityScreeningValidationVO = new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setCompanyCode(rs.getString("CMPCOD"));
			securityScreeningValidationVO.setValidationType(rs.getString("VLDIDR"));
			securityScreeningValidationVO.setErrorType(rs.getString("ERRTYP"));
			securityScreeningValidationVO.setOriginAirport(rs.getString("ORGARPCOD"));
			securityScreeningValidationVO.setOrgArpExcFlg(rs.getString("ORGARPCODFLG"));
			securityScreeningValidationVO.setDestinationAirport(rs.getString("DSTARPCOD"));
			securityScreeningValidationVO.setDestArpExcFlg(rs.getString("DSTARPCODFLG"));
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
	}
		return securityScreeningValidationVOs;

}
}
