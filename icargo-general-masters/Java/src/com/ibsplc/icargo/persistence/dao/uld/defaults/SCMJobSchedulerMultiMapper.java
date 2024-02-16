package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SCMJobSchedulerMultiMapper implements MultiMapper<String> {
	
	private Log log = LogFactory.getLogger("ULD DEFAULTS");

	private Collection<String> airportGroup;
	
	SCMJobSchedulerMultiMapper(Collection<String> airportgroup) {
		this.airportGroup = airportgroup;
	}

	@Override
	public List<String> map(ResultSet rs) throws SQLException {
		log.entering("SCMJobSchedulerMultiMapper", "inside the SCMJobSchedulerMultiMapper controller ");
		
		List<String> airportCodes = new ArrayList<>();
		
		 
		while(rs.next()){
			String airportName = rs.getString("AIRPORT");
			airportCodes.addAll(airportGroup);
			for(String airport : airportGroup) {
				if(airport.equals(airportName)) {
					airportCodes.remove(airport);
				}
			}
		}
		return airportCodes;

}
}
