package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author 4072
 */
@Slf4j
public class PADetailsMultiMapper implements MultiMapper<HashMap<String, String>> {
	private static final String CLASS_NAME = "MailPaDetailMultiMapper";

	public List<HashMap<String, String>> map(ResultSet rs) throws SQLException {
		List<HashMap<String, String>> paDetailMaps = new ArrayList<HashMap<String, String>>();
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		HashMap<String, String> paDetailMap = new HashMap<String, String>();
		while (rs.next()) {
			if (!paDetailMap.containsKey(rs.getString("MALORULD"))) {
				paDetailMap.put(rs.getString("MALORULD"), rs.getString("POACOD"));
			} else {
				paDetailMap.remove(rs.getString("MALORULD"));
			}
		}
		paDetailMaps.add(paDetailMap);
		return paDetailMaps;
	}
}
