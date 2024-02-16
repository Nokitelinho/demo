/*
 * UserMapper.java Created on Jun 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.*;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.business.admin.user.vo.UserCompositeData;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author A-1954
 */
public class UserMapper implements MultiMapper<UserVO> {

    private static final String MODULE_NAME = "admin.user";

    private static final Log LOG = LogFactory.getLogger(MODULE_NAME);
    static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    private static final String EMPTY_STRING = "";

    // List<UserVO>
    // Multimapper returns a list, from that first element UserVO needs to be
    // taken

    /**
     * This method maps the DB columns to VO fields
     * If no rows are retrived from the resultset ,
     * then an empty Collection is sent
     *
     * @param resultSet
     * @return List<UserVO>
     * @throws SQLException
     * @author A-1954
     */
    public List<UserVO> map(ResultSet resultSet) throws SQLException {
        LOG.entering("UserMapper", " map ");

        UserVO userVO = null;
        HashMap<String, UserVO> userMap = new HashMap<String, UserVO>();
        Collection<String> roleGroupKeys = new ArrayList<String>();
        HashMap<String, UserAllowableStationsVO> stationsMap = new HashMap<String, UserAllowableStationsVO>();
        HashMap<String, UserAllowableOfficesVO> officesMap = new HashMap<String, UserAllowableOfficesVO>();

        HashMap<String, ExhaustedGraceLoginVO> excLoginMap = new HashMap<String, ExhaustedGraceLoginVO>();
        HashMap<String, UserFavouriteVO> favScreenMap = new HashMap<String, UserFavouriteVO>();
        HashMap<String, UserParameterVO> userParameterMap = new HashMap<String, UserParameterVO>();
        Collection<UserVO> userVoColl = null;
        String userKey = "";
        /* changed by kiran */
        /* previously the key was constrcuted in the format - <cmpcod>-<usrcod>-<additionalrolegroupForStation>
         * now the key structed is modified to include station - <cmpcod>-<usrcod>-<station>-<additionalrolegroupForStation>
         */
        String roleGroupKey = "";
        String stationsKey = "";
        String officesKey = "";
        String favScreenKey = "";
        String excLogKey = "";
        String userParameterKey = "";

        String parameterCode = null;

        while (resultSet.next()) {

            userKey = new StringBuilder().append(resultSet.getString("CMPCOD"))
                    .append(resultSet.getString("USRCOD")).toString();

            if (!userMap.containsKey(userKey)) {
                //Blob blob = resultSet.getBlob("CMPDTATWO");
                //logger.info("Composite Data read {}", blob);
                //UserCompositeData data = PersistenceUtils.getObject(blob);
                byte[] bytes = resultSet.getBytes("CMPDTATWO");
                logger.info("Composite Data read {}", bytes.length);
                UserCompositeData data = PersistenceUtils.getObject(bytes);
                logger.info("UserCompositeData deserialized {}", data);
                userVO = new UserVO();
                userVO
                        .setUserRoleGroupDetailsVO(new ArrayList<UserRoleGroupDetailsVO>());
                userVO
                        .setUserAllowableOfficesVO(new ArrayList<UserAllowableOfficesVO>());
                userVO
                        .setUserAllowableStationsVO(new ArrayList<UserAllowableStationsVO>());
                userVO.setUserParameterVOs(new ArrayList<UserParameterVO>());


                userVO.setExhaustedGraceLoginVOs(new ArrayList<ExhaustedGraceLoginVO>());
                userVO.setUserFavouriteVOs(new ArrayList<UserFavouriteVO>());

                userVO.setCompanyCode(resultSet.getString("CMPCOD"));
                userVO.setUserCode(resultSet.getString("USRCOD"));

                userVO.setUserFirstName(resultSet.getString("FSTNAM"));
                userVO.setUserMiddleName(resultSet.getString("MDLNAM"));
                userVO.setUserLastName(resultSet.getString("LSTNAM"));

                //Added by Ratheesh
                userVO.setEmployeeId(resultSet.getString("EMPID"));
                userVO.setCompanyName(resultSet.getString("CMPNAM"));
                userVO.setDepartmentName(resultSet.getString("DEPNAM"));

                userVO.setUserPersonalAddress(resultSet.getString("PERADD"));

                userVO.setUserPersonalEmailAddress(decryptData(resultSet
                        .getString("PEREML")));
                userVO.setUserPersonalFaxNumber(decryptData(resultSet.getString("PERFAX")));
                userVO.setUserPersonalMobileNumber(decryptData(resultSet
                        .getString("PERMOBNUM")));
                userVO.setUserPersonalPhoneNumber(decryptData(resultSet
                        .getString("PERPHN")));
                userVO.setUserOfficialEmailAddress(decryptData(resultSet
                        .getString("OFFEML")));
                userVO.setUserOfficialFaxNumber(decryptData(resultSet.getString("OFFFAX")));
                userVO.setUserOfficialMobileNumber(decryptData(resultSet
                        .getString("OFFMOBNUM")));
                userVO.setUserOfficialPhoneNumber(decryptData(resultSet
                        .getString("OFFPHN")));

                userVO.setUserPersonalZipCode(resultSet.getString("PERZIPCOD"));

                userVO.setUserOfficialAddress(resultSet.getString("OFFADD"));

                userVO.setUserOfficialZipCode(resultSet.getString("OFFZIPCOD"));

                userVO.setUserRemarks(resultSet.getString("USRRMK"));

                userVO.setMaximumSessions(resultSet.getInt("NUMSES"));

                if (resultSet.getDate("PWDEXPDAT") != null) {
                    userVO.setPaswordExpiryDate(new LocalDate(
                            LocalDate.NO_STATION, Location.NONE, resultSet
                            .getDate("PWDEXPDAT")));
                }

                userVO.setRemainingGraceLogins(resultSet.getInt("NUMGRCLOG"));

                //Added by Ratheesh for bug 54438
                userVO.setHasToChangePassword(resultSet.getString("PWDCHGREQFLG"));

                userVO.setIsAccountDisabled(resultSet.getString("ACCDISFLG"));
                if (resultSet.getDate("ACCFRMDAT") != null) {
                    userVO.setAccountDisableStartDate(new LocalDate(
                            LocalDate.NO_STATION, Location.NONE, resultSet
                            .getDate("ACCFRMDAT")));
                }
                if (resultSet.getDate("ACCENDDAT") != null) {
                    userVO.setAccountDisableEndDate(new LocalDate(
                            LocalDate.NO_STATION, Location.NONE, resultSet
                            .getDate("ACCENDDAT")));
                }
                userVO.setAccountDisablingRemarks(resultSet
                        .getString("ACCDISRMK"));

                userVO
                        .setUserDefaultRoleGroup(resultSet
                                .getString("DEFROLGRP"));
                userVO.setUserDefaultOffice(resultSet.getString("DEFOFF"));
                userVO.setUserDefaultStation(resultSet.getString("DEFSTN"));
                userVO.setUserDefaultAirport(resultSet.getString("DEFARP"));

                userVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
                //userVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, resultSet.getDate("LSTUPDTIM")));
                userVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, resultSet.getTimestamp("LSTUPDTIM")));

                userVO.setUserPassword(resultSet.getString("USRPWD"));

                // added by Kiran for CR TIACT720
                userVO.setInchargeName(resultSet.getString("INCHGNAM"));
                // added by Kiran for CR TIACT720 ends

                /* Added by Ganesh Starts */
                userVO.setCustomerCompanyCode(resultSet.getString("CUSCMPCOD"));
                userVO.setCustomerCode(resultSet.getString("CUSCOD"));
                userVO.setDeliveryRequestEnabled(resultSet.getString("DLVREQFLG"));
                /* Added by Ganesh Ends */


                userVO.setJobTitle(resultSet.getString("JOBTIL"));
                userVO.setDefaultAuthority(resultSet.getString("DEFAUT"));
                userVO.setUserDefaultWarehouse(resultSet.getString("DEFWHS"));
                userVO.setDisabledUserId(resultSet.getString("DISUSRCOD"));
                userVO.setDisabledUserName(resultSet.getString("DISUSRNAM"));

                userVO.setStartupScreenId(resultSet.getString("STRSCRIDR"));
                userVO.setStartupScreenName(resultSet.getString("STRMODNAM"));

                //Added by Ratheesh
                userVO.setDefaultLanguage(resultSet.getString("DEFLNG"));
                userVO.setDefaultUserType(resultSet.getString("USRTYP"));
                // Modified as part of  ICRD-163887. Changed CUSCOD to ADMUSRPORAHCMPG table value
                //userVO.setCustomerCode(resultSet.getString("CUSCOD"));
                userVO.setCustomerCode(resultSet.getString("CUSCODES"));

                if (resultSet.getDate("CRTDAT") != null) {
                    userVO.setCreatedOn(new LocalDate(LocalDate.NO_STATION,
                            Location.NONE, resultSet.getDate("CRTDAT")));
                }
                userVO.setIsAlertMessageEnabled(resultSet.getString("MSGALTFLG"));

                //For CR HA-83
                if (resultSet.getDate("LSTPWDUPDTIM") != null) {
                    userVO.setLastPasswordUpdatedTime(new LocalDate(LocalDate.NO_STATION,
                            Location.NONE, resultSet.getDate("LSTPWDUPDTIM")));
                }

                userVO.setUserTitle(resultSet.getString("USRTTL"));
                userVO.setUserOfficeCity(resultSet.getString("OFFCTYNAM"));
                userVO.setUserOfficeCountry(resultSet.getString("OFFCNTNAM"));
                userVO.setSalesRep(resultSet.getString("SALREPFLG"));

                if (resultSet.getString("PORUSRTYP") != null) {
                    PortalUserDetailsVO portalDetails = new PortalUserDetailsVO();
                    portalDetails.setPortalUserType(resultSet.getString("PORUSRTYP"));
                    userVO.setPortalDetails(portalDetails);
                }

                //userVO.setPrimaryContactFlag(resultSet.getString("PRICNTFLG"));
		    	
		    	/*userVO.setPortalUserType(resultSet.getString("PORUSRTYP"));
		    	
		    	if(userVO.getPortalUserType()!=null){
		    		UserPortalDetailsVO portalDetails=new UserPortalDetailsVO();
		    		portalDetails.setMaxNumberOfChildUsers(resultSet.getInt("MAXCLDUSRCNT"));
		    		portalDetails.setNumberOfChildUsers(resultSet.getInt("CLDUSRCNT"));
		    		portalDetails.setPortalDefaultRoleGroupCode(resultSet.getString("PORDEFROLGRP"));
		    		portalDetails.setCreatedByUser(resultSet.getString("CRTUSR"));
		    		portalDetails.setPrimaryContactFlag(resultSet.getString("PRICNTFLG"));
		    		userVO.setPortalDetails(portalDetails);
		    	}	*/

                userMap.put(userKey, userVO);
            } else {
                // the userKey found
                userVO = userMap.get(userKey);
            }
            // checking whethere the field in the Station Code is null
            // and using the key to add the hash map
			/*String stationCode = resultSet.getString("ALLSTN");
			if(stationCode != null){
				stationsKey = new StringBuilder().append(userKey).append(
						stationCode).toString();
				if(! stationsMap.containsKey(stationsKey)){
					UserAllowableStationsVO stationsVO = new UserAllowableStationsVO();
					
					stationsVO.setCompanyCode(resultSet.getString("CMPCOD"));
					stationsVO.setUserCode(resultSet.getString("USRCOD"));
					stationsVO.setStationCode(resultSet.getString("ALLSTN"));
					stationsVO.setStationDefaultRoleGroup(resultSet.getString("STNDEFROL"));
					
					stationsMap.put(stationsKey, stationsVO);
					userVO.getUserAllowableStationsVO().add(stationsVO);				
				}
			}*/

            parameterCode = resultSet.getString("PARCOD");
            if (parameterCode != null) {
                userParameterKey = new StringBuilder().append(userKey).append(parameterCode).toString();
                if (!userParameterMap.containsKey(userParameterKey)) {
                    UserParameterVO userParameterVO = new UserParameterVO();
                    userParameterVO.setCompanyCode(resultSet.getString("CMPCOD"));
                    userParameterVO.setParameterCode(resultSet.getString("PARCOD"));
                    userParameterVO.setParameterValue(resultSet.getString("PARVAL"));
                    userParameterVO.setParameterDescription(resultSet.getString("PARDES"));
                    if (resultSet.getString("PARLOV") != null
                            && !EMPTY_STRING.equals(resultSet.getString("PARLOV").trim())) {
                        userParameterVO.setParameterLovData(Arrays.asList(resultSet
                                .getString("PARLOV").split(",")));
                        userParameterVO.setIsParameterLOV(UserParameterVO.FLAG_YES);
                    }
                    userParameterMap.put(userParameterKey, userParameterVO);
                    userVO.getUserParameterVOs().add(userParameterVO);
                }
            }

            // checking whether the field in the roleGroup Code is null
            // and using the key to add the hash map
			/*String roleGroupCode = resultSet.getString("ROLGRPCOD");
			String roleStationCode = resultSet.getString("ALLSTN");
			if(roleGroupCode  != null && roleStationCode!=null){
				
				 * modified by kiran
				 
				roleGroupKey = new StringBuilder().append(stationsKey).append(
						roleGroupCode).toString();
				if(! roleGroupKeys.contains(roleGroupKey)){
					UserAllowableStationsVO stationsVO = stationsMap.get(stationsKey);
					Collection<String> additionalRoles = stationsVO.getAdditionalRoleGroups();
					if(additionalRoles==null){
						additionalRoles = new ArrayList<String>();
						stationsVO.setAdditionalRoleGroups(additionalRoles);
					}
					additionalRoles.add(roleGroupCode);
					roleGroupKeys.add(roleGroupKey);
				}			
			
			}
			*/

            // checking whethere the field in the Office Code is null
            // and using the key to add the hash map
			/*String officeCode = resultSet.getString("OFFCOD");
			if(officeCode!=null) {
				officesKey = new StringBuilder().append(userKey).append(
						officeCode).toString();
				if(! officesMap.containsKey(officesKey)){
					UserAllowableOfficesVO officesVO = new UserAllowableOfficesVO();
					
					officesVO.setCompanyCode(resultSet.getString("CMPCOD"));
					officesVO.setUserCode(resultSet.getString("USRCOD"));
					officesVO.setOfficeCode(resultSet.getString("OFFCOD"));
					
					officesMap.put(officesKey, officesVO);
					userVO.getUserAllowableOfficesVO().add(officesVO);
				}
			}*/

            // Added newly
            // checking whethere the field in the fav Screen Code is null
            // and using the key to add the hash map
			
			/*String screenCode = resultSet.getString("SCRIDR");
			if (screenCode != null) {
				favScreenKey = new StringBuilder().append(userKey).append(
						screenCode).toString();
				if (!favScreenMap.containsKey(favScreenKey)) {
					UserFavouriteVO userFavouriteVO = new UserFavouriteVO();
					userFavouriteVO
							.setScreenCode(resultSet.getString("SCRIDR"));
					userFavouriteVO.setScreenName(resultSet
							.getString("FAVMODNAM"));
					favScreenMap.put(favScreenKey, userFavouriteVO);
					userVO.getUserFavouriteVOs().add(userFavouriteVO);
				}
			}*/

            // checking whether the field in the ExhaustedGraceLogin is null
            // and using the key to add the hash map
			/*int graceLogNo = resultSet.getInt("GRCLOG");
			if (graceLogNo >0) {
				excLogKey = new StringBuilder().append(userKey).append(
						graceLogNo).toString();
				if (!excLoginMap.containsKey(excLogKey)) {
					ExhaustedGraceLoginVO exhaustedGraceLoginVO = new ExhaustedGraceLoginVO();
					exhaustedGraceLoginVO.setGraceLoginSequenceNumber(resultSet
							.getInt("GRCLOG"));

					if (resultSet.getDate("LOGDAT") != null) {
						
						exhaustedGraceLoginVO.setGraceLoginDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE, resultSet
										.getTimestamp("LOGDAT")));
					}

					excLoginMap.put(excLogKey, exhaustedGraceLoginVO);
					userVO.getExhaustedGraceLoginVOs().add(
							exhaustedGraceLoginVO);
			 }
			}*/


        }

        userVoColl = userMap.values();
        LOG.exiting("UserMapper", " map ");
        return new ArrayList<UserVO>(userVoColl);
    }

    public String decryptData(String value) {
        return value;
    }
}
