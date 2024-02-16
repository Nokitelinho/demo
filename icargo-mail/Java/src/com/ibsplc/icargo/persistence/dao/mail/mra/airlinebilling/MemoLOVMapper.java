/*
 * MemoLOVMapper.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;import java.sql.SQLException;import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;import com.ibsplc.xibase.util.log.Log;import com.ibsplc.xibase.util.log.factory.LogFactory;
/** *@author A-2521 * 
 * Mapper for getting  Invoices */
public class MemoLOVMapper implements Mapper<MemoLovVO> {
	private Log log = LogFactory.getLogger("MemoLOVMapper");		
	/**	 * @param rs	 * @throws SQLException	 */
	public MemoLovVO map(ResultSet rs) throws SQLException {		
		log.entering("MemoLOVMapper---------", "Map Method");				MemoLovVO memoLovVO 	= new MemoLovVO();				memoLovVO.setInvoiceNumber( rs.getString("INVNUM" ));		memoLovVO.setAirlineCode( rs.getString("ARLCOD" ));		memoLovVO.setClearancePeriod( rs.getString("CLRPRD" ));		memoLovVO.setMemoCode( rs.getString("MEMCOD" ));				log.exiting("MemoLOVMapper", "Map Method");				return memoLovVO;
	}
}
