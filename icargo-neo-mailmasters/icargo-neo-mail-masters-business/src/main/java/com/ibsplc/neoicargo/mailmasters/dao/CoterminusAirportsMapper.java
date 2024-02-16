package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.CoTerminusVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoterminusAirportsMapper implements Mapper<CoTerminusVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public CoTerminusVO map(ResultSet rs) throws SQLException {
		CoTerminusVO coterminusVO = new CoTerminusVO();
		coterminusVO.setCompanyCode(rs.getString("CMPCOD"));
		coterminusVO.setGpaCode(rs.getString("GPACOD"));
		coterminusVO.setCoAirportCodes(rs.getString("ARPCOD"));
		coterminusVO.setResditModes(rs.getString("RSDMOD"));
		coterminusVO.setTruckFlag(rs.getString("TRKFLG"));
		coterminusVO.setSeqnum(rs.getLong("SERNUM"));
		log.debug("" + "\n\n coterminusVO listed !! ----------> " + " " + coterminusVO.getCoAirportCodes()
				+ coterminusVO.getResditModes());
		return coterminusVO;
	}
}
