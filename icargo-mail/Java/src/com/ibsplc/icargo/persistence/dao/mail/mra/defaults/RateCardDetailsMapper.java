/*
 * RateCardDetailsMapper.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */


public class RateCardDetailsMapper implements Mapper<RateCardVO> {

	private static final String CLASS_NAME = "RateCardDetailsMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

    /**
     * @param rs
     * @return RateCardVO
     * @throws SQLException
     */
    public RateCardVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"map");
    	RateCardVO rateCardVO=new RateCardVO();
	    	rateCardVO.setCompanyCode(rs.getString("CMPCOD"));
	    	rateCardVO.setRateCardID(rs.getString("RATCRDCOD"));
	    	rateCardVO.setRateCardStatus(rs.getString("RATCRDSTA"));
	    	rateCardVO.setRateCardDescription(rs.getString("RATCRDDES"));
	    	if(rs.getDate("VLDSTRDAT")!=null){
	    		rateCardVO.setValidityStartDate
	    			(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("VLDSTRDAT")));
	    	}
	    	if(rs.getDate("VLDENDDAT")!=null){
	        	rateCardVO.setValidityEndDate
	        		(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("VLDENDDAT")));
	        }
	    	rateCardVO.setNumberOfRateLines(rs.getInt("TOTRATLIN"));
	    	rateCardVO.setMailDistanceFactor(rs.getDouble("MALDISFCT"));
	    	rateCardVO.setCategoryTonKMRefOne(rs.getDouble("CATONETKM"));
	    	rateCardVO.setCategoryTonKMRefTwo(rs.getDouble("CATTWOTKM"));
	    	rateCardVO.setCategoryTonKMRefThree(rs.getDouble("CATTHRTKM"));
	    	//rateCardVO.setSubclassTonKMRefTwo(rs.getDouble("SUBTKMTWO"));
	    	//rateCardVO.setExchangeRate(rs.getDouble("EXGRAT"));
	    	rateCardVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	    	if(rs.getTimestamp("LSTUPDTIM")!=null){
	        	rateCardVO.setLastUpdateTime
	        		(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
	        }
	    log.exiting(CLASS_NAME,"map");
	    return rateCardVO;
    }

}
