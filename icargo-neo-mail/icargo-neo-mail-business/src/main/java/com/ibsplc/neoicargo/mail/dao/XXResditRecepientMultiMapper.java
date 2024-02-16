package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-2521
 */
@Slf4j
public class XXResditRecepientMultiMapper implements MultiMapper<HashMap<String, String>> {
	private static final String CLASS_NAME = "MailPaDetailMultiMapper";

	public List<HashMap<String, String>> map(ResultSet rs) throws SQLException {
		List<HashMap<String, String>> xxResditRecepientList = new ArrayList<HashMap<String, String>>();
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		HashMap<String, String> xxResditRecepientMap = new HashMap<String, String>();
		while (rs.next()) {
			if (!xxResditRecepientMap.containsKey(rs.getString("CDTKEY"))) {
				xxResditRecepientMap.put(rs.getString("CDTKEY"), rs.getString("ADRPTY"));
			}
		}
		xxResditRecepientList.add(xxResditRecepientMap);
		return xxResditRecepientList;
	}
}
