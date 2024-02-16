/*
 * UserAllowableOfficeMapper.java Created on Sep 20, 2005
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ibsplc.icargo.business.admin.user.vo.UserAllowableOfficesVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * UserAllowableOfficeMapper -> a mapper class for mapping the user allowable offices
 * used in the Switch Role Screen to fetch the user allowable offices on screenloading
 * 
 * @author A-2049
 *
 */
public class UserAllowableOfficeMapper implements MultiMapper<UserAllowableOfficesVO> {

	
	private Log log;
	
	private boolean isFirstResult;
	
	public UserAllowableOfficeMapper() {
		log = LogFactory.getLogger("ADMIN:USER:UserAllowableOfficeMapper");	
		isFirstResult = true;
	}
	
	
	
	
	
	/**
	 * iterrates through the result Set ...if the first row returned has STNCOD set as '***' then add only those rows
	 * which has STNCOD set as '****' ...this ensures that only allowable offices for the user adds to the collection
	 * if the first row has a valid STNCOD value then this means that direct offices are not there and the offices
	 * are shown based upon the allowable station code coming under the user
	 * @author A-2049
	 * @param resultSet -- the ResultSet set by framework
	 * @return allowableOffices -- a collection which contains only userAllowableOffices
	 * 
	 */
	public List<UserAllowableOfficesVO> map( ResultSet resultSet ) throws SQLException {
		log.entering("<:UserAllowableOfficeMapper:>","<:map:>");
		
		List<UserAllowableOfficesVO> allowableOffices = null;		
		Set<UserAllowableOfficesVO> dataSet = null;
		//boolean onlyOfficesForUser = false;
		//boolean onlyOfficesForStation = false;
			
		if(resultSet != null ){

			//allowableOffices = new ArrayList<UserAllowableOfficesVO>();
			dataSet = new HashSet<UserAllowableOfficesVO>();
			int resultSetCount = 0;
			
			
			while(resultSet.next()) {
				
				//allowableOffices.add(getUserAllowableOfficesFromResult(resultSet));
				dataSet.add(getUserAllowableOfficesFromResult(resultSet));
				resultSetCount++;
				
				/*String stationCode = resultSet.getString("STNCOD"); 
				
				if(isFirstResult){
					
					
					allowableOffices = new ArrayList<UserAllowableOfficesVO>();
					allowableOffices.add(getUserAllowableOfficesFromResult(resultSet));
										
					log.log(Log.FINE," <: from the first result station Code got :> "+stationCode);
						
						
						if(("***").equalsIgnoreCase(stationCode) ) {
						log.log(Log.FINE," <: first result is direct ::: so onlyOfficesForUser  is allowed :> ");
						onlyOfficesForUser = true;
						onlyOfficesForStation = false;
					}
						else if( ("DEFAULT".equals(stationCode)) ) {
							log.log(Log.FINE," <: first result is default ::: so onlyDefaultOfficesForUser  is allowed :> ");
							onlyOfficesForUser = false;
							onlyOfficesForStation = false;
						}
						else{						
							log.log(Log.FINE," <: first result is indirect ::: so onlyOfficesForStation is allowed :> ");
							onlyOfficesForStation = true;
							onlyOfficesForUser = false;		
						}
					isFirstResult = false;
				
						
										
						
				
				}else{
					
					// enters if not first result
					
					if(("DEFAULT").equals(stationCode)) {
							log.log(Log.FINE," <: adding a default allowable offices :> ");
							allowableOffices.add(getUserAllowableOfficesFromResult(resultSet));
					}else {
						
							if(onlyOfficesForUser && ("***").equalsIgnoreCase(stationCode) ){
					log.log(Log.FINE," <: adding a direct allowable offices :> ");
					allowableOffices.add(getUserAllowableOfficesFromResult(resultSet));
				
							}else if(onlyOfficesForStation && !("***").equals(stationCode) ) {
					log.log(Log.FINE," <: adding a indirect allowable office :> ");
					allowableOffices.add(getUserAllowableOfficesFromResult(resultSet));
				}
				
					}
					
										
				}*/
				

				
				
			}// end of loop	
			
			if ( dataSet != null ) {
				log.log(Log.FINE,"<:Actual Result Set Size :>"+resultSetCount);
				log.log(Log.FINE,"<:Constrcuted List Size :>"+dataSet.size());
				allowableOffices = new ArrayList<UserAllowableOfficesVO>(dataSet.size());
				allowableOffices.addAll(dataSet);
			}else{
				log.log(Log.FINE,"<:  NO RECORDS FOUND @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ :>");
			}
			
		}// end of if

		
		log.exiting("<:UserAllowableOfficeMapper:>","<:map:>");
		return allowableOffices;
	}
	
	/**
	 * method to poupate a vo
	 * @author A-2049
	 * @param resultSet
	 * @return 
	 * @throws SQLException
	 */
	private UserAllowableOfficesVO getUserAllowableOfficesFromResult( ResultSet resultSet ) throws SQLException {
		UserAllowableOfficesVO resultVO = new UserAllowableOfficesVO();
		resultVO.setCompanyCode(resultSet.getString("CMPCOD"));
		resultVO.setOfficeCode(resultSet.getString("OFFCOD"));
		resultVO.setStationCode(resultSet.getString("STNCOD"));
		resultVO.setUserCode(resultSet.getString("USRCOD"));
		return resultVO;
	}

}
