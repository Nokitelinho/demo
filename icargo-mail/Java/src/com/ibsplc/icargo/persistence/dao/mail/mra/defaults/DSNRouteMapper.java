/*
 * DSNRouteMapper.java Created on Sep,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class DSNRouteMapper implements MultiMapper<ProrationExceptionVO> {

	  private Log log = LogFactory.getLogger("MAILTRACKING MRA ");
	  /**
	     * @author A-3251
	     * @param rs
	     * @return List<RateAuditVO>
	     * @throws SQLException
	     */
	  public List<ProrationExceptionVO> map(ResultSet rs) throws SQLException {
		  	 log.entering("DSNRouteMapper", "map");
		  	List<ProrationExceptionVO> prorationExceptionVOs = new ArrayList<ProrationExceptionVO>();
		  	ProrationExceptionVO prorationExceptionVO = null;

		    	int counter=0;
		    	String route=null;
		    	String newRoute=null;

		  	while(rs.next()){
		  		prorationExceptionVO=new ProrationExceptionVO();
		  		if(rs.getString("POL")!=null){
		  		prorationExceptionVO.setSegmentOrigin(rs.getString("POL"));
		  		}
		  		if(rs.getString("POU")!=null){
		  		prorationExceptionVO.setSegmentDestination(rs.getString("POU"));
		  		}
		  		if(counter==0){

					route=new StringBuilder().append(prorationExceptionVO.getSegmentOrigin()).append("-").append((prorationExceptionVO.getSegmentDestination())).toString();
					counter++;
				}
				else{
					newRoute=new StringBuilder().append("-").append((prorationExceptionVO.getSegmentDestination())).toString();
					route=route.concat(newRoute);

				}
		  		prorationExceptionVO.setRoute(route);
		  	}



		  	 prorationExceptionVOs.add(prorationExceptionVO);
		  	 return prorationExceptionVOs;

}

}
