/*
 * UserEnquiryMapper.java Created on May 18,2007
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.PortalUserDetailsVO;
import com.ibsplc.icargo.business.admin.user.vo.PortalUserMappingVO;
import com.ibsplc.icargo.business.admin.user.vo.UserEnquiryFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2006
 * 
 */
public class UserEnquiryMapper implements MultiMapper<UserVO> {

	private static final String MODULE_NAME = "admin.user";

	private static final Log LOG = LogFactory.getLogger(MODULE_NAME);
	
	private UserEnquiryFilterVO userEnquiryFilterVO;
	
	public UserEnquiryMapper(UserEnquiryFilterVO userEnquiryFilterVO){
		this.userEnquiryFilterVO=userEnquiryFilterVO;
	}
	
	
	public List<UserVO> map(ResultSet resultSet) throws SQLException {
		List<UserVO> userList = new ArrayList<UserVO>();		
		List<String> userKey = new ArrayList<String>();	 
		List<String> mappingKeys = new ArrayList<String>(); 		
		
		List<String> roleGrpKeys = new ArrayList<String>(); 		
		List<String> additionalroleGrpKeys = new ArrayList<String>(); 
		
		PortalUserMappingVO mappingVo;
		UserVO userVO ;
		Map<String,UserVO> userMap=new HashMap<String,UserVO>();
	
		String filteredRole=this.userEnquiryFilterVO.getRoleGroup();
		
	while (resultSet.next()) {
		String key = new StringBuilder(resultSet.getString("CMPCOD"))
				.append("~").append(resultSet.getString("USRCOD")).toString();
						
		if (!userKey.contains(key)) {
			userVO = new UserVO();
			userKey.add(key);	
						
			userVO.setCompanyCode(resultSet.getString("CMPCOD"));
			userVO.setUserCode(resultSet.getString("USRCOD"));
			userVO.setUserFirstName(resultSet.getString("FSTNAM"));
			userVO.setUserMiddleName(resultSet.getString("MDLNAM"));
			userVO.setUserLastName(resultSet.getString("LSTNAM"));
			
			//userVO.setUserDefaultRoleGroup(resultSet.getString("DEFROLGRP"));
			//userVO.setDefaultRoleGroupAtStation(resultSet.getString("DEFSTNROLGRP"));
			
			userVO.setUserDefaultRoleGroup(resultSet.getString("USRDEFROLGRP"));
			
			//Setting number of stations other than default one
			userVO.setNumOfStations(resultSet.getInt("STNCNT"));
			
			userVO.setUserDefaultOffice(resultSet.getString("DEFOFF"));
			userVO.setUserDefaultStation(resultSet.getString("DEFSTN"));
			userVO.setUserDefaultAirport(resultSet.getString("DEFARP"));
			userVO.setUserDefaultWarehouse(resultSet.getString("DEFWHS"));

			userVO.setUserRemarks(resultSet.getString("USRRMK"));

			userVO.setMaximumSessions(resultSet.getInt("NUMSES"));
			
			if(resultSet.getDate("PWDEXPDAT") != null) {
				userVO.setPaswordExpiryDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
						resultSet.getDate("PWDEXPDAT")));
			}		
			userVO.setRemainingGraceLogins(resultSet.getInt("REMGRCLOG"));

			userVO.setIsAccountDisabled(resultSet.getString("ACCDISFLG"));
			
			if(resultSet.getDate("ACCFRMDAT") != null){
				userVO.setAccountDisableStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
						resultSet.getDate("ACCFRMDAT")));
			}
			if(resultSet.getDate("ACCENDDAT") != null) {
				userVO.setAccountDisableEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
						resultSet.getDate("ACCENDDAT")));
			}
			userVO.setAccountDisablingRemarks(resultSet.getString("ACCDISRMK"));	
			
			if(resultSet.getDate("CRTDAT") != null) {
				userVO.setCreatedOn(new LocalDate(LocalDate.NO_STATION,Location.NONE,
						resultSet.getDate("CRTDAT")));
			}
			if(resultSet.getDate("LSTLOGINTIM") != null) {
				userVO.setLastLoginTime(new LocalDate(LocalDate.NO_STATION,
						Location.NONE, resultSet.getTimestamp("LSTLOGINTIM")));
			}
					
			if(resultSet.getDate("LSTUPDTIM") != null) {
				userVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,
						resultSet.getDate("LSTUPDTIM")));
			}
			if(resultSet.getString("LSTUPDUSR") != null) {
				userVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			}
			if(resultSet.getString("DEPNAM") != null) {
				userVO.setDepartmentName(resultSet.getString("DEPNAM"));
			}
			
			if(resultSet.getString("PORUSRTYP")!=null){
				PortalUserDetailsVO portalDetailsVO=new PortalUserDetailsVO();
				portalDetailsVO.setPortalUserType(resultSet.getString("PORUSRTYP"));
				userVO.setPortalDetails(portalDetailsVO);
			}			
			
			if(resultSet.getString("PORDEFROLGRP")!=null){
				if(userVO.getPortalDetails()!=null)
					{
					userVO.getPortalDetails().setPortalDefaultRoleGroupCode(resultSet.getString("PORDEFROLGRP"));
					}
				else{
					PortalUserDetailsVO portalDetailsVO=new PortalUserDetailsVO();
					portalDetailsVO.setPortalDefaultRoleGroupCode(resultSet.getString("PORDEFROLGRP"));
					userVO.setPortalDetails(portalDetailsVO);
				}
			}	
			
			userList.add(userVO);
			userMap.put(key, userVO);
		} else{
			userVO=userMap.get(key); 
		}
		if( resultSet.getString("MPGTYP")!=null && resultSet.getString("MPGCOD")!=null){	
			String mappingKey = new StringBuilder(resultSet.getString("CMPCOD"))
			.append("~").append(resultSet.getString("USRCOD")).append("~").append(resultSet.getString("MPGTYP")).append("~").append(resultSet.getString("MPGCOD")).toString();
			if(!mappingKeys.contains(mappingKey)){
				PortalUserDetailsVO portalDetailsVO;
				mappingVo=new PortalUserMappingVO();
				mappingVo.setMappingCode(resultSet.getString("MPGCOD"));
				mappingVo.setMappingType(resultSet.getString("MPGTYP"));
				if(userVO.getPortalDetails()==null){
					portalDetailsVO = new PortalUserDetailsVO();
					userVO.setPortalDetails(portalDetailsVO);
				}else{
					portalDetailsVO=userVO.getPortalDetails();
				}					
				if(portalDetailsVO.getMappingVos()==null)
					{
					portalDetailsVO.setMappingVos(new ArrayList<PortalUserMappingVO>());
					}
				portalDetailsVO.getMappingVos().add(mappingVo);
				mappingKeys.add(mappingKey);
			}		
		}				
		
		/*if(resultSet.getString("STNCOD")!=null && resultSet.getString("STNDEFROL")!=null){
			String roleMappingKey = new StringBuilder(resultSet.getString("CMPCOD"))
			.append("~").append(resultSet.getString("USRCOD")).append("~").append(
					resultSet.getString("STNCOD")).append("~").append(resultSet.getString("STNDEFROL")).toString();
			if(filteredRole!=null && filteredRole.trim().length()>0 
					&& filteredRole.equals(resultSet.getString("STNDEFROL")) )
			if(!roleGrpKeys.contains(roleMappingKey) && 
					!(resultSet.getString("DEFSTN").equals(resultSet.getString("STNCOD")))){
				// No need to add the default rolegroup of default station again to list				
				UserRoleGroupDetailsVO rolVO=new UserRoleGroupDetailsVO();
				rolVO.setStationCode(resultSet.getString("STNCOD"));
				rolVO.setRoleGroupCode(resultSet.getString("STNDEFROL"));
				
				if(userVO.getUserRoleGroupDetailsVO()==null)
					userVO.setUserRoleGroupDetailsVO(new ArrayList<UserRoleGroupDetailsVO>());
				userVO.getUserRoleGroupDetailsVO().add(rolVO);
				roleGrpKeys.add(roleMappingKey);
			}		
		}
		if(resultSet.getString("STNADLROL")!=null && resultSet.getString("ROLSTN")!=null){
			String adlRoleMappingKey = new StringBuilder(resultSet.getString("CMPCOD"))
			.append("~").append(resultSet.getString("USRCOD")).append("~").append(
					resultSet.getString("ROLSTN")).append("~").append(resultSet.getString("STNADLROL")).toString();
			if(!additionalroleGrpKeys.contains(adlRoleMappingKey)){				
				UserRoleGroupDetailsVO rolVO=new UserRoleGroupDetailsVO();
				rolVO.setStationCode(resultSet.getString("ROLSTN"));
				rolVO.setRoleGroupCode(resultSet.getString("STNADLROL"));
				
				if(userVO.getAdditionalRoleGroupDetails()==null)
					userVO.setAdditionalRoleGroupDetails(new ArrayList<UserRoleGroupDetailsVO>());
				userVO.getAdditionalRoleGroupDetails().add(rolVO);
				additionalroleGrpKeys.add(adlRoleMappingKey);
			}		
		}*/
		
		
	}
	return userList;
	}
	
	
	/**
	 * This method maps the DB columns to VO fields
   	 * If no rows are retrived from the resultset ,
   	 * then an empty Collection is sent
   	 * 
   	 * @author A-1954
   	 * 
   	 * @param resultSet
	 * @return List<UserVO>
	 * @throws SQLException
	 */
	/*public UserVO map(ResultSet resultSet) throws SQLException {
		LOG.entering("UserEnquiryMapper", " map ");

		UserVO userVO = new UserVO() ;
		
		userVO.setCompanyCode(resultSet.getString("CMPCOD"));
		userVO.setUserCode(resultSet.getString("USRCOD"));
		userVO.setUserFirstName(resultSet.getString("FSTNAM"));
		userVO.setUserMiddleName(resultSet.getString("MDLNAM"));
		userVO.setUserLastName(resultSet.getString("LSTNAM"));
		
		userVO.setUserDefaultRoleGroup(resultSet.getString("DEFROLGRP"));
		userVO.setUserDefaultOffice(resultSet.getString("DEFOFF"));
		userVO.setUserDefaultStation(resultSet.getString("DEFSTN"));
		userVO.setUserDefaultAirport(resultSet.getString("DEFARP"));
		userVO.setUserDefaultWarehouse(resultSet.getString("DEFWHS"));

		userVO.setUserRemarks(resultSet.getString("USRRMK"));

		userVO.setMaximumSessions(resultSet.getInt("NUMSES"));
		
		if(resultSet.getDate("PWDEXPDAT") != null) {
			userVO.setPaswordExpiryDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("PWDEXPDAT")));
		}		
		userVO.setRemainingGraceLogins(resultSet.getInt("REMGRCLOG"));

		userVO.setIsAccountDisabled(resultSet.getString("ACCDISFLG"));
		
		if(resultSet.getDate("ACCFRMDAT") != null){
			userVO.setAccountDisableStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("ACCFRMDAT")));
		}
		if(resultSet.getDate("ACCENDDAT") != null) {
			userVO.setAccountDisableEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("ACCENDDAT")));
		}
		userVO.setAccountDisablingRemarks(resultSet.getString("ACCDISRMK"));
	   

			
	
		LOG.exiting("UserMapper", " map userVO "+userVO);
		return userVO;
	}*/
}
