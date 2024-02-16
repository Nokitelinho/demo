/*
 * RateLineDetailsMapper.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
//TODO Mapper<RateLineVO>
public class RateLineDetailsMapper implements Mapper<RateLineVO> {

	private static final String CLASS_NAME = "RateLineDetailsMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public RateLineVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"Mapper");

    	RateLineVO rateLineVO=new RateLineVO();

    	rateLineVO.setCompanyCode(rs.getString("CMPCOD"));
    	rateLineVO.setRateCardID(rs.getString("RATCRDCOD"));
    	rateLineVO.setRatelineSequenceNumber(rs.getInt("RATLINSEQNUM"));
    	//rateLineVO.setRatelineStatus(rs.getString("RATLINSTA"));
    	rateLineVO.setOrigin(rs.getString("ORGCOD"));
    	rateLineVO.setDestination(rs.getString("DSTCOD"));
    	rateLineVO.setIataKilometre(rs.getDouble("IATKLM"));
    	rateLineVO.setMailKilometre(rs.getDouble("MALKLM"));
    	if("A".equals(rs.getString("ORGDSTLVL"))){
    		rateLineVO.setOrgDstLevel("ARP");
    	}else{
    		rateLineVO.setOrgDstLevel("CITY");
    	}
    	rateLineVO.setRateInSDRForCategoryRefOne(rs.getDouble("CATONEXDRCURRAT"));
    	rateLineVO.setRateInSDRForCategoryRefTwo(rs.getDouble("CATTWOXDRCURRAT"));
    	//rateLineVO.setRateInSDRForSubclassRefTwo(rs.getDouble("SUBSDRTWO"));
    	rateLineVO.setRateInSDRForCategoryRefThree(rs.getDouble("CATTHRXDRCURRAT"));
    	rateLineVO.setRateInBaseCurrForCategoryRefOne(rs.getDouble("CATONEBASCURRAT"));
    	rateLineVO.setRateInBaseCurrForCategoryRefTwo(rs.getDouble("CATTWOBASCURRAT"));
    	//rateLineVO.setRateInBaseCurrForSubclassRefTwo(rs.getDouble("SUBBASCURTWO"));
    	rateLineVO.setRateInBaseCurrForCategoryRefThree(rs.getDouble("CATTHRBASCURRAT"));
    	if(rs.getDate("VLDSTRDAT")!=null){
    		rateLineVO.setValidityStartDate(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getDate("VLDSTRDAT")));
        	}
    	//Calendar currentDate = Calendar.getInstance();
    	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	if(rs.getDate("VLDENDDAT")!=null)
    		{
    			rateLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("VLDENDDAT")));
    		}
    		//Commented by A-4809 as Suggested by Tobin
    		/*if(currentDate.after(rateLineVO.getValidityEndDate())) {
				rateLineVO.setRatelineStatus("E");
			} else */
				rateLineVO.setRatelineStatus(rs.getString("RATLINSTA"));
    	
        //	}
    	rateLineVO.setOverlapflag(rs.getString("OVERLAPFLG"));
    	rateLineVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
    	
    	log.log(Log.FINE, "(LSTUPDTIM)", rs.getTimestamp("LSTUPDTIM"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
    		rateLineVO.setLastUpdateTime(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
        	}
        return rateLineVO;
    }

}
