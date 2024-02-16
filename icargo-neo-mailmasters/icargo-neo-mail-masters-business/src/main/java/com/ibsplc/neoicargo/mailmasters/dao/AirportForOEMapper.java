package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirportForOEMapper implements MultiMapper<HashMap<String,String>> {
    private static final String CLASS_NAME = "AirportForOEMapper";
    private List<HashMap<String, String>> airportForOEMap = new ArrayList<HashMap<String, String>>();


    public List<HashMap<String, String>> map(ResultSet rs) throws SQLException {
        HashMap<String, String> airportForOE = new HashMap<String, String>();
        while (rs.next()) {
            if (!airportForOE.containsKey(rs.getString("EXGOFCCOD"))) {
                airportForOE.put(rs.getString("EXGOFCCOD"), rs.getString("ARPCOD"));
            }
        }
        airportForOEMap.add(airportForOE);
        return airportForOEMap;
    }
}
