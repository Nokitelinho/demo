/*
 * SendResditMultiMapper Created on NOV 25, 2011
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
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4072
 *
 */
public class SendResditMultiMapper implements MultiMapper<CarditReferenceInformationVO> {
	private Log log=LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	public List<CarditReferenceInformationVO> map(ResultSet rs) throws SQLException {
		log.entering("SendResditMultiMapper","Map Method");	
		List<CarditReferenceInformationVO> carditRefInfoVos = new ArrayList<CarditReferenceInformationVO>();		
			while (rs.next()) {
			CarditReferenceInformationVO carditRefInfoVo= new CarditReferenceInformationVO();
			carditRefInfoVo.setCarditKey(rs.getString("CDTKEY"));
			carditRefInfoVo.setRefNumber(rs.getString("AHIRFFNUM"));		
			carditRefInfoVo.setContractRef(rs.getString("CNTRFF")); 
			carditRefInfoVo.setOrgin(rs.getString("ORGCOD"));
			carditRefInfoVo.setDestination(rs.getString("DSTCOD"));
			carditRefInfoVos.add(carditRefInfoVo);
			}
		log.exiting("SendResditMultiMapper","Map Method");			
	    return carditRefInfoVos; 
	}
}
