package com.ibsplc.neoicargo.mailmasters.dao;


import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CityForOEMapper implements MultiMapper<HashMap<String,String>> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLASS_NAME = "CityForOEMapper";
	private List<HashMap<String,String>> cityForOEMap = new ArrayList<HashMap<String,String>>();

	public List<HashMap<String,String>> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		HashMap<String,String> cityForOE = new HashMap<String, String>();
		while(rs.next()) {
			if(!cityForOE.containsKey(rs.getString("EXGOFCCOD"))) {
				cityForOE.put( rs.getString("EXGOFCCOD"),rs.getString("CTYCOD"));
			}
		}
		cityForOEMap.add(cityForOE);
		return cityForOEMap;
	}

}
