/*
 * ValidateHandlingAreaUserMapper.java Created on Aug 28, 2018
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

import com.ibsplc.icargo.business.admin.user.vo.UserParameterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * 
 * @author a-5505
 *
 */
public class ValidateHandlingAreaUserMapper implements MultiMapper<UserVO>{
	public List<UserVO> map(ResultSet rs) throws SQLException {
		UserVO userVO = null;
		String userKey = "";
		HashMap<String, UserVO> userMap = new HashMap<String, UserVO>();
		String parameterCode = null;
		String userParameterKey = "";
		HashMap<String, UserParameterVO> userParameterMap = new HashMap<String, UserParameterVO>();
		
		while(rs.next()){
			userKey = new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getString("USRCOD")).toString();
			if (!userMap.containsKey(userKey)) {
				userVO = new UserVO();
				userVO.setUserCode(rs.getString("USRCOD"));
				userMap.put(userKey, userVO);
			}else{
				userVO = userMap.get(userKey);
			}
			parameterCode = rs.getString("PARCOD");
			
			if(parameterCode != null){
				userParameterKey = new StringBuilder().append(userKey).append(parameterCode).toString();
				if(! userParameterMap.containsKey(userParameterKey)){
					UserParameterVO userParameterVO = new UserParameterVO();
					userParameterVO.setCompanyCode(rs.getString("CMPCOD"));
					userParameterVO.setParameterCode(rs.getString("PARCOD"));
					userParameterVO.setParameterValue(rs.getString("PARVAL")); 
					userParameterMap.put(userParameterKey, userParameterVO);
					if(userVO.getUserParameterVOs()==null){
						userVO.setUserParameterVOs(new ArrayList<UserParameterVO>());
					}
					userVO.getUserParameterVOs().add(userParameterVO);	
				}
			}
		}			
		return new ArrayList<UserVO>(userMap.values());
	}
}
