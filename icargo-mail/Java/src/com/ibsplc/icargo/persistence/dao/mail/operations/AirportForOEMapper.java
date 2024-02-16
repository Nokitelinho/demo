package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class AirportForOEMapper implements MultiMapper<HashMap<String,String>>{

	private static final String CLASS_NAME = "AirportForOEMapper";
	private List<HashMap<String,String>> airportForOEMap = new ArrayList<HashMap<String,String>>();
		
	@Override
	public List<HashMap<String, String>> map(ResultSet rs) throws SQLException {
		HashMap<String,String> airportForOE = new HashMap<String, String>();
		while(rs.next()) {
			if(!airportForOE.containsKey(rs.getString("EXGOFCCOD"))) {
				airportForOE.put( rs.getString("EXGOFCCOD"),rs.getString("ARPCOD"));
			}
		}
		airportForOEMap.add(airportForOE);
		return airportForOEMap;
	}

}
