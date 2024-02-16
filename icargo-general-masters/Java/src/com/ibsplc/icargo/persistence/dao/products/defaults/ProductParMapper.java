/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.products.defaults.ProductParMapper.java
 *
 *	Created by	:	A-7740
 *	Created on	:	04-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.products.defaults.ProductParMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7740	:	04-Oct-2018	:	Draft
 */
public class ProductParMapper implements MultiMapper<HashMap<String,String>>  {
	private Log log = LogFactory.getLogger("PRODUCT Par MAPPER");
	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-7740 on 04-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	public List<HashMap<String,String>> map(ResultSet rs) throws SQLException {
		HashMap<String,String> prdMap = new HashMap<String,String>();
		while (rs.next()){
			prdMap.put(rs.getString("PARCOD"),rs.getString("PARVAL"));
		}
		List<HashMap<String,String>> prdList = new ArrayList<HashMap<String,String>>();
		prdList.add(prdMap);
		return prdList;
	}
}