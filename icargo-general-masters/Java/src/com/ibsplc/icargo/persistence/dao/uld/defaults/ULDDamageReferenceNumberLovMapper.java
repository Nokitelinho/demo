/*
 * ULDDamageReferenceNumberLovMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ULDDamageReferenceNumberLovMapper implements Mapper<ULDDamageReferenceNumberLovVO>{

	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
   /**
    * @param rs
    * @return ULDDamageReferenceNumberLovVO
    * @throws SQLException
    */
    public ULDDamageReferenceNumberLovVO map(ResultSet rs)
    throws SQLException{
    	log.entering("ULDDamageReferenceNumberLovMapper","map");
		ULDDamageReferenceNumberLovVO damageReferenceNumberLovVO = 
			new ULDDamageReferenceNumberLovVO();
		damageReferenceNumberLovVO.setDamageReferenceNumber(rs.getLong("DMGREFNUM"));
		return damageReferenceNumberLovVO;
    }
}
