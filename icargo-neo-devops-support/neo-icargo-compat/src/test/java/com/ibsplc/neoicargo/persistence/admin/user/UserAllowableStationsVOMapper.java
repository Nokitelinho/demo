/*
 * UserAllowableStationsVOMapper.java Created on Jun 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.persistence.admin.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserAllowableStationsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3139
 *
 */
/*
 * The PageableNativeQuery is used to fetch the pages as part of bug fix for
 * ICRD-78284. Hence the Mapper class implements from MultiMapper interface
 * Author : A-6042
 */
public class UserAllowableStationsVOMapper implements
		MultiMapper<UserAllowableStationsVO> {
	
	private static final String MODULE_NAME = "admin.user";
	
	private Log log = LogFactory.getLogger(MODULE_NAME);
	
	/**
	 * To map DB ResultSet to PrivilegeGroupVOs
	 * @param resultSet
	 * return List<UserAllowableStationsVO>
	 * @throws SQLException
	 */
	public List<UserAllowableStationsVO> map(ResultSet resultSet) throws SQLException {
		
		log.entering(" UserAllowableStationsVOMapper ", " map ");
		
		Map<String,UserAllowableStationsVO> userAllowableStationsVOMap = new LinkedHashMap<String,UserAllowableStationsVO>();
		String prevUserId = "";
		String currentUserId = null;
		UserAllowableStationsVO userAllowableStationsVO = null;
		Collection<String> addnlRoleGrps = null;
		
		while(resultSet.next()){
			StringBuilder newPkString = new StringBuilder("");
			String companyCode = resultSet.getString("CMPCOD");
			String userCode = resultSet.getString("USRCOD");
			String stationCode = resultSet.getString("ALLSTN");
			newPkString.append(companyCode).append(userCode).append(stationCode);
			currentUserId = newPkString.toString();
			//If 5 rows are to be mapped to 1 row then only for the first row it enters 
			if(!prevUserId.equals(currentUserId)){
				
				if(userAllowableStationsVO != null){
					//Adding the parent to a map if the id are different
					//increment();
					if(userAllowableStationsVO.getStationCode()!=null && 
							userAllowableStationsVO.getStationDefaultRoleGroup()!=null){
						userAllowableStationsVOMap.put(prevUserId,userAllowableStationsVO);
					}
					
				}
				
				
				//Each time when the ids are different it creates a new parent and adds all its data
				userAllowableStationsVO = new UserAllowableStationsVO();
				userAllowableStationsVO.setCompanyCode(companyCode);
				userAllowableStationsVO.setUserCode(userCode);
				userAllowableStationsVO.setStationCode(stationCode);
				userAllowableStationsVO.setStationDefaultRoleGroup(resultSet.getString("STNDEFROL"));
				
				//Adding the first child
				addnlRoleGrps = new ArrayList<String>();
				if(resultSet.getString("ROLGRPCOD") != null){
					addnlRoleGrps.add(resultSet.getString("ROLGRPCOD"));
					userAllowableStationsVO.setAdditionalRoleGroups(addnlRoleGrps);
				}
							
			}else{
				addnlRoleGrps.add(resultSet.getString("ROLGRPCOD"));
			}
			prevUserId = currentUserId;
		}
		
		if(userAllowableStationsVO != null){
			//This part of the code is required to add the last parent
			//The last parent wont be added by the main loop
			//increment();
			if(userAllowableStationsVO.getStationCode()!=null && 
					userAllowableStationsVO.getStationDefaultRoleGroup()!=null){
				userAllowableStationsVOMap.put(currentUserId,userAllowableStationsVO);
			}
			
		}
		
		log.log(Log.INFO, " <<<Inside the mapper>>> ",
				new ArrayList<UserAllowableStationsVO>(userAllowableStationsVOMap.values()));
		log.exiting(" UserAllowableStationsVOMapper ", " map ");
		
		return new ArrayList<UserAllowableStationsVO>(userAllowableStationsVOMap.values());
		
	}
	
	/*StringBuilder newPkString = new StringBuilder("");
	 StringBuilder prevPkString = new StringBuilder("");
	 String prevPk = null;
	 String newPk = null;
	 while(hasNext()){
	 UserAllowableStationsVO stationsVO = new UserAllowableStationsVO();
	 String companyCode = resultSet.getString("CMPCOD");
	 String userCode = resultSet.getString("USRCOD");
	 String stationCode = resultSet.getString("ALLSTN");
	 prevPkString.append(companyCode).append(userCode).append(stationCode);
	 //prevPk = companyCode+""+userCode+""+stationCode;
	  prevPk = prevPkString.toString();
	  stationsVO.setCompanyCode(companyCode);
	  stationsVO.setUserCode(userCode);
	  stationsVO.setStationCode(stationCode);
	  stationsVO.setStationDefaultRoleGroup(resultSet.getString("STNDEFROL"));
	  Collection<String> additionalRoles = null;
	  String roleGroupCode = resultSet.getString("ROLGRPCOD");
	  if(roleGroupCode != null){
	  additionalRoles = new ArrayList<String>();
	  additionalRoles.add(roleGroupCode);
	  }
	  if(userAllowableStations.size() == 0){
	  stationsVO.setAdditionalRoleGroups(additionalRoles);
	  userAllowableStations.add(stationsVO);
	  increment();
	  }else{
	  Iterator it  = userAllowableStations.iterator();
	  boolean isDuplicate = false;
	  while (it.hasNext()) {
	  UserAllowableStationsVO newStationsVO = (UserAllowableStationsVO)it.next();
	  //newPk = newStationsVO.getCompanyCode()+""+newStationsVO.getUserCode()+""+newStationsVO.getStationCode();
	   newPkString.append(newStationsVO.getCompanyCode()).append(newStationsVO.getUserCode())
	   .append(newStationsVO.getStationCode());
	   newPk = newPkString.toString();
	   if(prevPk.equals(newPk)){
	   newStationsVO.getAdditionalRoleGroups().addAll(additionalRoles);
	   isDuplicate = true;
	   break;
	   }
	   }
	   if(!isDuplicate){
	   stationsVO.setAdditionalRoleGroups(additionalRoles);
	   userAllowableStations.add(stationsVO);
	   increment();
	   }
	   }
	   
	   }*/
	
}
