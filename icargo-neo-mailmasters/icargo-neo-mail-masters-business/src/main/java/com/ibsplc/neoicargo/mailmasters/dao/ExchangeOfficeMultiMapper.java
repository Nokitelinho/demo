/**
 * 	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ExchangeOfficeMultiMapper.java
 */
package com.ibsplc.neoicargo.mailmasters.dao;

import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ExchangeOfficeMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-6245	:	10-Jul-2017	:	Draft
 */
public class ExchangeOfficeMultiMapper implements MultiMapper<HashMap<String,String>> {
	
	public List<HashMap<String,String>> map(ResultSet rs) throws SQLException {
		
		HashMap<String,String> exchangeOfficeMap 
		= new HashMap<String,String>();
		String key=null;
		while(rs.next()){
			key=rs.getString("ARPCOD");
			if(Objects.nonNull(key) && !exchangeOfficeMap.containsKey(key)){
				exchangeOfficeMap.put(key, rs.getString("EXGOFCCOD"));
			}
		}
		List<HashMap<String,String>> map = 
				new ArrayList<HashMap<String,String>>();
			map.add(exchangeOfficeMap);
			return map;
		
	}

}
