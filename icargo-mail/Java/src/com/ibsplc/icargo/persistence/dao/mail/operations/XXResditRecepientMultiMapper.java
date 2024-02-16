/*
 * XXResditRecepientMultiMapper.java Created on NOV 25, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-2521
 *
 */
public class XXResditRecepientMultiMapper implements
	MultiMapper<HashMap<String,String>> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLASS_NAME = "MailPaDetailMultiMapper";
	

	public List<HashMap<String,String>> map(ResultSet rs) throws SQLException {

		List<HashMap<String,String>> xxResditRecepientList = new ArrayList<HashMap<String,String>>();
	log.entering(CLASS_NAME, "map");
	HashMap<String,String> xxResditRecepientMap = new HashMap<String, String>();
	while(rs.next()) {
		if(!xxResditRecepientMap.containsKey(rs.getString("CDTKEY"))) {
			xxResditRecepientMap.put(rs.getString("CDTKEY"),rs.getString("ADRPTY"));
		}
	}
	xxResditRecepientList.add(xxResditRecepientMap);
	return xxResditRecepientList;
	}
}

