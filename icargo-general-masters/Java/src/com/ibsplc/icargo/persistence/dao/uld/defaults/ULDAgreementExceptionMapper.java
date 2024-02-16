/*
 * ULDAgreementExceptionMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementExceptionVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ULDAgreementExceptionMapper implements Mapper<ULDAgreementExceptionVO>{

	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
   /**
    * @param rs
    * @return ULDAgreementExceptionVO
    * @throws SQLException
    */
    public ULDAgreementExceptionVO map(ResultSet rs)
    throws SQLException{
    	log.entering("ULDAgreementMapper","map");
    	ULDAgreementExceptionVO uldAgreementExceptionVO = new ULDAgreementExceptionVO();
    	uldAgreementExceptionVO.setAgreementNumber(rs.getString("AGRMNTNUM"));

    	Date datTwo = rs.getDate("AGRMNTFRMDAT");
    	if(datTwo != null){
    		uldAgreementExceptionVO.setAgreementFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datTwo));
    	}

    	Date datThree = rs.getDate("AGRMNTTOODAT");
    	if(datThree != null){
    		uldAgreementExceptionVO.setAgreementToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datThree));
    	}


    	log.log(Log.INFO, "!!!!!!!uldAgreementExceptionVO",
				uldAgreementExceptionVO);
		return uldAgreementExceptionVO;
    }

}
