
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CoterminusAirportsMapper implements Mapper<CoTerminusVO>{
	private Log log = LogFactory.getLogger("CoterminusAirportsMapper");
	 /**
     * @param rs
     * @return 
     * @throws SQLException
     */
   public CoTerminusVO map(ResultSet rs) throws SQLException {
	   
	   CoTerminusVO coterminusVO=new CoTerminusVO();
	   coterminusVO.setCompanyCode(rs.getString("CMPCOD"));
	   coterminusVO.setGpaCode(rs.getString("GPACOD"));
	   coterminusVO.setCoAirportCodes(rs.getString("ARPCOD"));
	   coterminusVO.setResditModes(rs.getString("RSDMOD"));
	   coterminusVO.setTruckFlag(rs.getString("TRKFLG"));
	   coterminusVO.setSeqnum(rs.getLong("SERNUM"));
	   log.log(Log.FINE, "\n\n coterminusVO listed !! ----------> ", coterminusVO.getCoAirportCodes()+coterminusVO.getResditModes()+coterminusVO.getTruckFlag());
	   return coterminusVO;
	   
   }
}
