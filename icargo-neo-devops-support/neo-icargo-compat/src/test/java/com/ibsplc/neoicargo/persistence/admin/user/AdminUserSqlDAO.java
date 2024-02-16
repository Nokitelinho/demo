/*
 * AdminUserSqlDAO.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.*;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.security.vo.UserOfficialDetailsVO;
import com.ibsplc.icargo.framework.security.vo.UserPersonalDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.BeanUtil;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author A-1954
 */
public class AdminUserSqlDAO extends AbstractQueryDAO implements AdminUserDAO {

    private final Supplier<Session> sessionSupplier = BeanUtil.lazyInit(() -> PersistenceController.getEntityManager().currentSession());
    static final Logger logger = LoggerFactory.getLogger(AdminUserSqlDAO.class);

    /**
     * static constant
     */
    private static final String CLASS_NAME = "AdminUserSqlDAO";

    private static final String MODULE_NAME = "admin.user";
    private static final String EMPTY_STRING = "";

    private Log log = LogFactory.getLogger(MODULE_NAME);

    /**
     * This method lists the user details
     *
     * @param companyCode
     * @param userCode
     * @return UserVO
     * @throws SystemException
     * @author A-1954
     */
    public UserVO findUserDetails(String companyCode, String userCode)
            throws SystemException {

        log.entering(" AdminUserSqlDAO ", " findUserDetails ");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILS);
        if (isOracleDataSource()) {
            query.append(" (SELECT LISTAGG(MPGCOD, ',') WITHIN GROUP (ORDER BY MPGCOD DESC)").append(" FROM ADMUSRPORAHCMPG MPG").append(" WHERE usrmst.CMPCOD   = MPG.CMPCOD	AND usrmst.USRCOD   = MPG.USRCOD")
                    .append(" AND MPG.MPGTYP='C') CUSCODES, usrmst.SALREPFLG").append(" FROM admusrmst usrmst  LEFT OUTER JOIN  admusrparmst parmst ON usrmst.cmpcod = parmst.cmpcod WHERE usrmst.cmpcod = ? AND usrmst.usrcod = ?");
        } else {
            query.append(" FROM admusrmst usrmst WHERE usrmst.cmpcod = ? AND usrmst.usrcod = ?");
        }
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        List<UserVO> userVOs = query.getResultList(new UserMapper());
        if (userVOs != null && userVOs.size() > 0) {
            return userVOs.get(0);
        }
        return null;
    }

    /**
     * @param companyCode
     * @return
     * @throws SystemException
     */
    private Collection<UserParameterVO> findUserParameterCodes(String companyCode) throws SystemException {
        Collection<UserParameterVO> userParameterVOs = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_PARAMETERS);
        query.setParameter(++index, companyCode);
        return query.getResultList(new UserParameterMapper());
    }

    /**
     * @author
     */
    class UserParameterMapper implements Mapper<UserParameterVO> {

        @Override
        public UserParameterVO map(ResultSet resultSet) throws SQLException {
            UserParameterVO parameterVO = new UserParameterVO();
            parameterVO.setCompanyCode(resultSet.getString("CMPCOD"));
            parameterVO.setParameterCode(resultSet.getString("PARCOD"));
            parameterVO.setParameterDescription(resultSet.getString("PARDES"));
            if (resultSet.getString("PARLOV") != null
                    && !EMPTY_STRING.equals(resultSet.getString("PARLOV").trim())) {
                parameterVO.setParameterLovData(Arrays.asList(resultSet
                        .getString("PARLOV").split(",")));
                parameterVO.setIsParameterLOV(UserParameterVO.FLAG_YES);
            }
            return parameterVO;
        }

    }


    /**
     * @param userFilterVO
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */
    public Collection<UserVO> listUsers(UserFilterVO userFilterVO) throws PersistenceException, SystemException {
        log.entering(" AdminUserSqlDAO ", " findUserDetails ");
        Query query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILS);
        query.append(" FROM admusrmst usrmst ");
        logger.info("ListUsers Query {}", query);
        List<UserVO> userVOs = query.getResultList(new UserMapper());
        return userVOs;
    }

    /**
     * This method lists the user details for print
     *
     * @param userEnquiryFilterVO
     * @return Collection<UserVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    public Collection<UserVO> printUserEnquiryDetails(
            UserEnquiryFilterVO userEnquiryFilterVO)
            throws PersistenceException, SystemException {

        log.entering("<: AdminUserSqlDAO :>", "<: findUserEnquiryDetails :>");
        int index = 0;
        //added by A-5223 for ICRD-23107 starts
        StringBuilder rankQuery = new StringBuilder();
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_DENSE_RANK_QUERY);
        rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.USRCOD )RANK FROM (");
        Query baseQuery = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY);
        rankQuery.append(baseQuery);
        PageableNativeQuery<UserVO> query = new PageableNativeQuery<UserVO>(userEnquiryFilterVO.getTotalRecords(), rankQuery.toString(), new UserEnquiryMapper(userEnquiryFilterVO), sessionSupplier.get());
        //added by A-5223 for ICRD-23107 ends
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());

        if (userEnquiryFilterVO.getAirportCode() != null
                && userEnquiryFilterVO.getAirportCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFARP = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getAirportCode());
        }
        if (userEnquiryFilterVO.getUserCode() != null
                && userEnquiryFilterVO.getUserCode().trim().length() > 0) {
            query.append(" AND USRMST.USRCOD = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getUserCode());
        }
        if (userEnquiryFilterVO.getWarehouseCode() != null
                && userEnquiryFilterVO.getWarehouseCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFWHS = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getWarehouseCode());
        }
        if (userEnquiryFilterVO.getStationCode() != null
                && userEnquiryFilterVO.getStationCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFSTN = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getStationCode());
        }

        if (userEnquiryFilterVO.getAccountStatus() != null
                && userEnquiryFilterVO.getAccountStatus().trim().length() > 0) {

            query.append(" AND USRMST.ACCDISFLG = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getAccountStatus());
        }

        if (userEnquiryFilterVO.getRemainingGraceLogin() != 0) {
            query.append(" AND USRMST.REMGRCLOG = ? ");
            query.setParameter(++index, userEnquiryFilterVO
                    .getRemainingGraceLogin());
        }
        if (userEnquiryFilterVO.getRoleGroup() != null
                && userEnquiryFilterVO.getRoleGroup().trim().length() > 0) {
            query.append(" AND USRMST.DEFROLGRP = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
        }

        /*
         * if(userEnquiryFilterVO.getAccountFromDate() != null ){ query.append("
         * AND USRMST.ACCFRMDAT >=? "); query.setParameter(++index,
         * userEnquiryFilterVO.getAccountFromDate()); }
         * if(userEnquiryFilterVO.getAccountToDate() != null ){ query.append("
         * AND USRMST.ACCENDDAT <=? "); query.setParameter(++index,
         * userEnquiryFilterVO.getAccountToDate()); }
         */

        if (userEnquiryFilterVO.getPwasswordFromDate() != null) {
            query.append(" AND USRMST.PWDEXPDAT >=? ");
            query.setParameter(++index, userEnquiryFilterVO
                    .getPwasswordFromDate());
        }
        if (userEnquiryFilterVO.getPwasswordToDate() != null) {
            query.append(" AND USRMST.PWDEXPDAT <=? ");
            query.setParameter(++index, userEnquiryFilterVO
                    .getPwasswordToDate());
        }
        query.append(AdminUserPersistenceConstants.ADMIN_USER_SUFFIX_QUERY);

        return query.getResultList(new UserEnquiryMapper(userEnquiryFilterVO));

    }

    //added by Nisha starts

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.persistence.dao.admin.user.AdminUserDAO#findUserAllowableStationsDetails(java.lang.String, java.lang.String, java.lang.String)
     */
    public Collection<UserAllowableStationsVO> findUserAllowableStationsDetails(String companyCode, String userCode)
            throws SystemException {

        log.entering(" AdminUserSqlDAO ", " findUserAllowableStationsDetails ");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery("admin.user.finduserallowablestations");
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        /* new changes made for the second query */
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        /* made for the newly added union all query */
        Collection<UserAllowableStationsVO> userAllowableStationsVOs = query.getResultList(new UserStationMapper());
        if (userAllowableStationsVOs != null && userAllowableStationsVOs.size() > 0) {
            log.exiting(" AdminUserSqlDAO ", " findUserAllowableStationsDetails ");
            return userAllowableStationsVOs;
        }
        log.exiting("\n\n AdminUserSqlDAO ", " findUserAllowableStationsDetails -> with UserAllowableStationsVO as NULL !!!\n\n");
        return null;

    }

    /**
     * Method to find the allowable offices for user...
     * If no offices have been allowed for the user..then the system will pick up
     * the allowable offices coming under the user allowed station
     * for the user
     *
     * @param companyCode -- the companyCode
     * @param userCode    -- the userCode
     * @return -- the collection of allowable offices
     * @throws SystemException
     * @throws PersistenceException
     * @author A-2049
     */
    public Collection<UserAllowableOfficesVO> findUserAllowableOffices(String companyCode, String userCode)
            throws SystemException, PersistenceException {
        log.entering("<: AdminUserSqlDAO :>", "<: findUserAllowableOffices :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery("admin.user.findUserAllowableOffices");
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        // required for the second query
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        /* **** new change made required for the third query **** */
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        log.log(Log.FINE, " <: PRINTING THE FINAL QUERY FOR EXECUTION :>",
                query.toString());
        Collection<UserAllowableOfficesVO> userAllowableOfficesVOs = query.getResultList(new UserAllowableOfficeMapper());
        log.log(Log.FINE, " <: priting the officeVOs available for user :> ",
                userAllowableOfficesVOs);
        log.exiting("<: AdminUserSqlDAO :>", "<: findUserAllowableOffices :>");
        return userAllowableOfficesVOs;
    }


    /* (non-Javadoc)
     * @see com.ibsplc.icargo.persistence.dao.admin.user.AdminUserDAO#findAllOffices(java.lang.String, java.lang.String)
     */
    public Collection<UserAllowableOfficesVO> findAllOffices(String companyCode, String userCode)
            throws SystemException {
        log.entering(" AdminUserSqlDAO ", " findAllOffices ");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery("admin.user.findalloffices");
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);

        List<UserAllowableOfficesVO> userAllowableOfficesVOs = query.getResultList(new UserOfficeMapper());
        if (userAllowableOfficesVOs != null && userAllowableOfficesVOs.size() > 0) {
            log.exiting(" AdminUserSqlDAO ", " findalloffices ");
            return userAllowableOfficesVOs;
        }
        log.exiting("\n\n AdminUserSqlDAO ", " findallofficess -> with UserAllowableOfficesVO as NULL !!!\n\n");
        return null;
    }

    /**
     * @author A-2408
     */
    class UserStationMapper implements MultiMapper<UserAllowableStationsVO> {


        /* (non-Javadoc)
         * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
         */
        public List<UserAllowableStationsVO> map(ResultSet rs) throws SQLException {

            List<UserAllowableStationsVO> userAllowableStations = new ArrayList<UserAllowableStationsVO>();

            while (rs.next()) {
                UserAllowableStationsVO userAllowableStationsVO = new UserAllowableStationsVO();
                userAllowableStationsVO.setCompanyCode(rs.getString("CMPCOD"));
                userAllowableStationsVO.setUserCode(rs.getString("USRCOD"));
                userAllowableStationsVO.setStationCode(rs.getString("STNCOD"));
                userAllowableStationsVO.setStationDefaultRoleGroup(rs.getString("DEFROLGRP"));

                userAllowableStations.add(userAllowableStationsVO);

            }// end of loop

            if (userAllowableStations != null) {
                log.log(Log.FINE, "<: Collection Size :>",
                        userAllowableStations.size());
            }


            return userAllowableStations;
        }

    }

    /**
     * @author A-2408
     */
    class UserOfficeMapper implements MultiMapper<UserAllowableOfficesVO> {

        /* (non-Javadoc)
         * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
         */
        public List<UserAllowableOfficesVO> map(ResultSet rs) throws SQLException {
            List<UserAllowableOfficesVO> userAllowableOfficesVOs = new ArrayList<UserAllowableOfficesVO>();

            while (rs.next()) {
                UserAllowableOfficesVO userAllowableOfficesVO = new UserAllowableOfficesVO();
                userAllowableOfficesVO.setCompanyCode(rs.getString("CMPCOD"));
                userAllowableOfficesVO.setUserCode(rs.getString("USRCOD"));
                userAllowableOfficesVO.setOfficeCode(rs.getString("OFFCOD"));
                userAllowableOfficesVOs.add(userAllowableOfficesVO);

            }
            return userAllowableOfficesVOs;
        }


    }

    //Added by Nisha ends
    // Added by Bejoy {starts}

    /**
     * This method get the logon details
     *
     * @param companyCode
     * @param userCode
     * @return LogonAttributes
     * @throws SystemException
     * @author A-1759
     */
    public LogonAttributes getUserDetailsForLogon(String companyCode, String userCode)
            throws SystemException {


        log.entering(" AdminUserSqlDAO ", " getUserDetailsForLogon ");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                isOracleDataSource() ? AdminUserPersistenceConstants.ADMIN_USER_GETUSERDETAILSFORLOGON : AdminUserPersistenceConstants.POSTGRES_ADMIN_USER_GETUSERDETAILSFORLOGON);
        query.setParameter(++index, userCode);
        query.setParameter(++index, companyCode);
        LogonAttributes logonAttributes = query.getResultList(new LogonAttributesMapper()).get(0);
        log.exiting(" AdminUserSqlDAO ", " getUserDetailsForLogon ");
        return logonAttributes;

    }

    /**
     * The mapper class for getLogonUserDetails
     *
     * @author A-1759
     */
    class LogonAttributesMapper implements MultiMapper<LogonAttributes> {
        /**
         * map method
         *
         */

        public List<LogonAttributes> map(ResultSet rs) throws SQLException {
            List<LogonAttributes> logonAttributesList = new ArrayList<LogonAttributes>();
            LogonAttributes logonAttributes = null;
            String parameterCode = null;
            String parameterValue = null;
            Map<String, String> userParameterMap = null;
            List<String> userKey = new ArrayList<String>();

            while (rs.next()) {
                String key = new StringBuilder(rs.getString("CMPCOD")).append("~").append(rs.getString("USRCOD")).toString();
                if (!userKey.contains(key)) {
                    userParameterMap = new HashMap<String, String>();
                    logonAttributes = new LogonAttributes();
                    logonAttributes.setCompanyCode(rs.getString("CMPCOD"));
                    logonAttributes.setUserId(rs.getString("USRCOD"));
                    logonAttributes.setUserName(rs.getString("FSTNAM"));
                    logonAttributes.setRoleGroupCode(rs.getString("USRDEFROLGRP"));
                    logonAttributes.setStationCode(rs.getString("DEFSTN"));
                    logonAttributes.setAirportCode(rs.getString("DEFARP"));
                    logonAttributes.setDefaultWarehouseCode(rs.getString("DEFWHS"));
                    logonAttributes.setDefaultAuthorityCode(rs.getString("DEFAUT"));
                    logonAttributes.setOwnAirlineIdentifier(rs.getInt("ARLIDR"));
                    String language = rs.getString("LANGUAGE_1");
                    if (language.indexOf("_") > 0) {
                        String[] locale = language.split("_");
                        logonAttributes.setLocale(new Locale(locale[0], locale[1]));
                    } else {
                        logonAttributes.setLocale(new Locale(language));
                    }
                    logonAttributes.setDateFormat(rs.getString("DATEFORMAT"));
                    logonAttributes.setTimeFormat(rs.getString("TIMEFORMAT"));
                    logonAttributes.setDateAndTimeFormat(rs.getString("DATEANDTIMEFORMAT"));

                    //Added for ICRD-113328
                    String stationDefaultRoleGroup = rs.getString("STNDEFROLGRP");
                    if (stationDefaultRoleGroup == null || stationDefaultRoleGroup.trim().length() <= 0) {
                        stationDefaultRoleGroup = logonAttributes.getRoleGroupCode();
                    }
                    logonAttributes.setDefaultRoleGroupAtStation(stationDefaultRoleGroup);

                    logonAttributes.setDefaultOfficeCode(rs.getString("DEFOFF"));

                    if (rs.getInt("APHCODUSE") == 2) {
                        logonAttributes.setOwnAirlineCode(rs.getString("TWOAPHCOD"));
                    } else {
                        logonAttributes.setOwnAirlineCode(rs.getString("THRAPHCOD"));
                    }
                    if (rs.getInt("NUMCODUSE") == 3) {
                        logonAttributes.setOwnAirlineNumericCode(rs.getString("THRNUMCOD"));
                    } else {
                        logonAttributes.setOwnAirlineNumericCode(rs.getString("FORNUMCOD"));
                    }
                    String userType = (rs.getString("STNUSRTYP") == null) ? rs.getString("USRTYP") : rs.getString("STNUSRTYP");
                    if (AdminUserPersistenceConstants.AIRLINE_USER.equals(userType)) {
                        logonAttributes.setAirlineUser(true);
                    } else if (AdminUserPersistenceConstants.GHA_USER.equals(userType)) {
                        logonAttributes.setGHAUser(true);
                    } else if (AdminUserPersistenceConstants.OWN_GSA_USER.equals(userType)) {
                        logonAttributes.setOwnSalesAgent(true);
                    } else if (AdminUserPersistenceConstants.GHA_GSA_USER.equals(userType)) {
                        logonAttributes.setGHAUser(true);
                        logonAttributes.setOwnSalesAgent(true);
                    } else if (AdminUserPersistenceConstants.OTHER_GSA_USER.equals(userType)) {
                        logonAttributes.setOtherSalesAgent(true);
                    }


                    String archiveRoleFlag = (rs.getString("STNARCROL") == null) ? rs.getString("ARCROL") : rs.getString("STNARCROL");
                    String defaultUserType = rs.getString("DEFUSRTYP");
                    if (AdminUserPersistenceConstants.NORMAL_USER.equals(defaultUserType)) {
                        logonAttributes.setNormalUser(true);
                    } else if (AdminUserPersistenceConstants.SYSTEM_USER.equals(defaultUserType)) {
                        logonAttributes.setSystemUser(true);
                    } else if (AdminUserPersistenceConstants.PORTAL_USER.equals(defaultUserType)) {
                        logonAttributes.setPortalUser(true);
                    } else if (AdminUserPersistenceConstants.HHT_USER.equals(defaultUserType)) {
                        logonAttributes.setHHTUser(true);
                    }
                    // Added by shadhim for Portal user details
                    //logonAttributes.setCustomerCode(rs.getString("CUSCOD"));
                    logonAttributes.setKnownShipperFlag(rs.getString("KNWSHP"));
                    // Commented by A-5290 for ICRD-59741
                    //logonAttributes.setIacNumber(rs.getString("CUSIACNUM"));
                    // Portal user details Ends

                    // Updation after changing logonAttributes

                    logonAttributes.setTargetAction(rs.getString("STRACTCOD"));
                    logonAttributes.setStartupScreen(rs.getString("STRMODNAM"));
                    //logonAttributes.setIsAlertMessageEnabled(rs.getString("MSGALTFLG"));
                    int no;
                    if (rs.getInt("NUMSES") > 0 || rs.getInt("NUMGRCLOG") > 0) {
                        if (rs.getInt("REMNUMSES") > 0) {
                            logonAttributes.setLoginType(AdminUserPersistenceConstants.NORMAL_LOGIN);
                            no = rs.getInt("REMNUMSES");
                            logonAttributes.setRemainingLoginSessions(no - 1);

                        } else if (rs.getInt("REMGRCLOG") >= 0) {
                            logonAttributes.setLoginType(AdminUserPersistenceConstants.GRACE_LOGIN);
                            no = rs.getInt("REMGRCLOG");
                            logonAttributes.setRemainingLoginSessions(no - 1);
                        } else {
                            logonAttributes.setRemainingLoginSessions(-1);

                        }

                    } else {
                        logonAttributes.setLoginType(AdminUserPersistenceConstants.NORMAL_LOGIN);
                    }
                    if (rs.getDate("PWDEXPDAT") != null) {

                        String currentDate = DateUtilities.getCurrentDate();//Format : dd-MM-yyyy
                        String currentDat = TimeConvertor.toStringFormat((TimeConvertor.getCurrentDate()), "dd-MMM-yyyy");//Format : dd-MMM-yyyy
                        String expDate = DateUtilities.format(rs.getDate("PWDEXPDAT"));//Format : dd-MM-yyyy
                        String expDat = TimeConvertor.toStringFormat((rs.getDate("PWDEXPDAT")), "dd-MMM-yyyy");    //Format : dd-MMM-yyyy

                        if (DateUtilities.isLessThan(expDate, currentDate)) {
                            if (rs.getInt("REMGRCLOG") >= 0) {
                                logonAttributes.setLoginType(AdminUserPersistenceConstants.GRACE_LOGIN);
                                no = rs.getInt("REMGRCLOG");
                                logonAttributes.setRemainingLoginSessions(no - 1);
                            } else {
                                logonAttributes.setRemainingLoginSessions(-1);
                            }
                            //Changed by Ratheesh for BUG - 90048.Checking if pwd policy is enabled and checking for alert period

                        }
                        if ("Y".equalsIgnoreCase(rs.getString("ISPWDPOLENB"))
                                && (rs.getInt("PWDALTPRD") != 0)) {
                            int differeceinDays = DateUtilities.getDifferenceInDays(currentDat, expDat);
                            int alertPeriod = rs.getInt("PWDALTPRD");
                            if (differeceinDays <= alertPeriod) {
                                logonAttributes.setExpiryPeriod(differeceinDays);
                            }

                        }
                    }


                    if (("Y".equals(rs.getString("MSGALTFLG"))) &&
                            ("Y".equals(rs.getString("SYSPARMSGALTFLG")))) {

                        logonAttributes.setAlertMessageEnabled(true);
                    } else {
                        logonAttributes.setAlertMessageEnabled(false);
                    }
                    if (("Y".equals(rs.getString("PWDCHGREQFLG"))) &&
                            ("Y".equals(rs.getString("ISSFRCPWDCHG")))) {
                        logonAttributes.setHasToChangePassword(true);
                    } else {
                        logonAttributes.setHasToChangePassword(false);
                    }

                    /* Added By Kiran for ANA specific CR # 1656 starts ******************************************************************************* */
                    logonAttributes.setRoleGroupImageParameter(rs.getString("IMGPAR"));
                    /* Added By Kiran for ANA specific CR # 1656 starts ******************************************************************************* */

                    /* Added for TIACT720 */

                    UserPersonalDetailsVO userPersonalDetailsVO = new UserPersonalDetailsVO();
                    userPersonalDetailsVO.setPersonalAddress(rs.getString("PERADD"));

                    userPersonalDetailsVO.setPersonalPhoneNo(decryptData(rs.getString("PERPHN")));
                    userPersonalDetailsVO.setPersonalEmailId(decryptData(rs.getString("PEREML")));
                    userPersonalDetailsVO.setPersonalFaxNo(decryptData(rs.getString("PERFAX")));
                    userPersonalDetailsVO.setPersonalMobileNo(decryptData(rs.getString("PERMOBNUM")));

                    userPersonalDetailsVO.setPersonalZipCode(rs.getString("PERZIPCOD"));
                    logonAttributes.setUserPersonalDetailsVO(userPersonalDetailsVO);

                    UserOfficialDetailsVO userOfficialDetailsVO = new UserOfficialDetailsVO();
                    userOfficialDetailsVO.setEmployeeId(rs.getString("EMPID"));
                    userOfficialDetailsVO.setCompanyName(rs.getString("CMPNAM"));
                    userOfficialDetailsVO.setDepartmentName(rs.getString("DEPNAM"));
                    userOfficialDetailsVO.setOfficialAddress(rs.getString("OFFADD"));

                    userOfficialDetailsVO.setOfficialPhoneNo(decryptData(rs.getString("OFFPHN")));
                    userOfficialDetailsVO.setOfficialEmailId(decryptData(rs.getString("OFFEML")));
                    userOfficialDetailsVO.setOfficialFaxNo(decryptData(rs.getString("OFFFAX")));
                    userOfficialDetailsVO.setOfficialMobileNo(decryptData(rs.getString("OFFMOBNUM")));
                    userOfficialDetailsVO.setOfficialZipCode(rs.getString("OFFZIPCOD"));
                    logonAttributes.setUserOfficialDetailsVO(userOfficialDetailsVO);
                    logonAttributes.setInchargeName(rs.getString("INCHGNAM"));

                    /* Added for TIACT720 */
                    /* Added by Ganesh Starts */
                    logonAttributes.setCustomerCompanyCode(rs.getString("CUSCMPCOD"));
                    //logonAttributes.setCustomerCode(rs.getString("CUSCOD"));
                    if (("Y".equals(rs.getString("DLVREQFLG"))) &&
                            ("Y".equals(rs.getString("DLVREQFLG")))) {
                        logonAttributes.setDeliveryRequestEnabled(true);
                    } else {
                        logonAttributes.setDeliveryRequestEnabled(false);
                    }
                    /* Added by Ganesh Ends */
                    // ICRD-12922 A-4789 03 July 2012 changes starts here
                    // Fetches a few system parameters and puts in a map.
                    Map<String, String> systemParams = new HashMap<String, String>();
                    systemParams.put("system.defaults.unit.weight", rs.getString("DFTWGTUNT"));
                    systemParams.put("system.defaults.unit.volume", rs.getString("DFTVOLUNT"));
                    systemParams.put("system.defaults.unit.currency", rs.getString("DFTCURUNT"));
                    systemParams.put("system.defaults.unit.length", rs.getString("DFTDIMUNT"));
                    systemParams.put(systemParams.get("system.defaults.unit.weight"), rs.getString("DFTWGTUNTDES"));
                    systemParams.put(systemParams.get("system.defaults.unit.volume"), rs.getString("DFTVOLUNTDES"));
                    systemParams.put(systemParams.get("system.defaults.unit.volume"), rs.getString("DFTVOLUNTDES"));
                    systemParams.put(systemParams.get("system.defaults.unit.length"), rs.getString("DFTDIMUNTDES"));

                    logonAttributes.setSystemParams(systemParams);

                    if ("Y".equals(rs.getString("ACCDISFLG"))) {
                        String fromDate = null;
                        String toDate = null;
                        Calendar toCal = null;
                        Calendar fromCal = null;
                        try {
                            Calendar curCal = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                            String currentDate = DateUtilities.getCurrentDate();//Format : dd-MM-yyyy
                            curCal.setTime(format.parse(currentDate));

                            if (rs.getDate("ACCFRMDAT") != null) {
                                fromDate = DateUtilities.format(rs.getDate("ACCFRMDAT"));//Format : dd-MM-yyyy
                                fromCal = Calendar.getInstance();
                                fromCal.setTime(format.parse(fromDate));
                            }
                            if (rs.getDate("ACCENDDAT") != null) {
                                toDate = DateUtilities.format(rs.getDate("ACCENDDAT"));//Format : dd-MM-yyyy
                                toCal = Calendar.getInstance();
                                toCal.setTime(format.parse(toDate));
                            }
                            if (fromDate != null && toDate != null) {
                                if (fromCal.getTimeInMillis() <= curCal
                                        .getTimeInMillis()
                                        && curCal.getTimeInMillis() <= toCal
                                        .getTimeInMillis()) {
                                    logonAttributes.setAccountDisabled(true);
                                }

                            } else if (fromDate != null) {
                                if (fromCal.getTimeInMillis() <= curCal.getTimeInMillis()) {
                                    logonAttributes.setAccountDisabled(true);
                                }

                            } else if (toDate != null) {

                                if (curCal.getTimeInMillis() <= toCal.getTimeInMillis()) {
                                    logonAttributes.setAccountDisabled(true);
                                }
                            } else {
                                logonAttributes.setAccountDisabled(true);
                            }


                        } catch (ParseException e) {
                            log.log(Log.FINE, "inside Catch block");
                        }

                        //logonAttributes.setAccountDisabled(true);
                    }
                    if (rs.getString("PORUSRTYP") != null) {
                        com.ibsplc.icargo.framework.security.vo.PortalUserDetailsVO portalDetails = new com.ibsplc.icargo.framework.security.vo.PortalUserDetailsVO();
                        portalDetails.setPortalUserType(rs.getString("PORUSRTYP"));
                        logonAttributes.setPortalDetails(portalDetails);
                    }
                    //logonAttributes.setPortalUserType(rs.getString("PORUSRTYP"));

                    /*Added by A-5233 for restricting message sending flag for user starts*/
                    parameterCode = rs.getString("PARCOD");
                    parameterValue = rs.getString("PARVAL");

                    if (parameterCode != null && !userParameterMap.containsKey(parameterCode)) {
                        userParameterMap.put(parameterCode, parameterValue);
                    }

                    logonAttributes.setUserParameterMap(userParameterMap);
                    /*Added by A-5233 for restricting message sending flag for user ends*/

                    logonAttributes.setCustomerCode(rs.getString("CUSCODES"));

                    logonAttributesList.add(logonAttributes);

                    userKey.add(key);
                } else {
                    /*Added by A-5233 for restricting message sending flag for user starts*/
                    parameterCode = rs.getString("PARCOD");
                    parameterValue = rs.getString("PARVAL");

                    if (parameterCode != null && !logonAttributes.getUserParameterMap().containsKey(parameterCode)) {
                        logonAttributes.getUserParameterMap().put(parameterCode, parameterValue);
                    }

                    /*Added by A-5233 for restricting message sending flag for user ends*/
                }
            }
            // ICRD-12922 A-4789 03 July 2012 changes ends here
            log.exiting(" AdminUserSqlDAO ", " getUserDetailsForLogon " + logonAttributes);
            return logonAttributesList;
        }

        public String decryptData(String value) {
            return value;
        }
    }

    private String getTimeZoneString() {
        StringBuilder timeZoneStringBlder = new StringBuilder();
        try {
            TimeZone timezonedefault = TimeZone.getDefault();

            //String zone=timezonedefault.getDisplayName();
            String zone = timezonedefault.getID();
            timeZoneStringBlder.append(zone);
        } catch (Exception ex) {
            log.log(Log.SEVERE, "ex" + ex.getMessage());
        }
        return timeZoneStringBlder.toString();
    }


    // Added by Bejoy {ends}

    /**
     * This method lists the user details
     *
     * @param userLovFilterVO
     * @return Page<UserLovVO>
     * @throws SystemException
     * @author A-1872
     */
    public Page<UserLovVO> findUserDetailsLov(UserLovFilterVO userLovFilterVO)
            throws PersistenceException, SystemException {
        log.entering("<: AdminUserSqlDAO :>", "<: findUserDetailsLov :>");
        String baseQry = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILSLOV_BASE);
        String appendQuery = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_NEXT);
        //Added by A-5220 for ICRD-32647 starts
        StringBuilder rankQuery = new StringBuilder();
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_ROWNUM_QUERY);
        rankQuery.append(baseQry);
        PageableNativeQuery<UserLovVO> userpgqry =
                new UserLovForPaginationFilterQuery(0, new UserLovMapper(),
                        userLovFilterVO, rankQuery.toString(), appendQuery);
        log.exiting("<: AdminUserSqlDAO :>", "<: findUserDetailsLov :>");
        //Added by A-5220 for ICRD-32647
        userpgqry.append(AdminUserPersistenceConstants.ADMIN_USER_SUFFIX_QUERY);
        return userpgqry.getPage(userLovFilterVO.getPageNumber());
    }

    /**
     * This method lists the user details
     *
     * @param userLovFilterVO
     * @return Collection<UserLovVO>
     * @throws SystemException
     * @throws PersistenceException
     * @author A-1833
     */
    public Collection<UserLovVO> findUsers(UserLovFilterVO userLovFilterVO)
            throws PersistenceException, SystemException {
        String baseQry = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILSLOV_BASE);
        String appendQuery = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILSLOV_NEXT);
        Query qry = new UserLovFilterQuery(userLovFilterVO, baseQry, appendQuery);
        return qry.getResultList(new UserLovMapper());
    }

    /**
     * This method lists the user details
     *
     * @param companyCode
     * @param rolegroupCode
     * @return Page<UserLovVO>
     * @throws SystemException
     * @author A-1872
     */
    public Collection<ValidUsersVO> validateUsers(
            Collection<String> userCodes, String companyCode, String rolegroupCode)
            throws PersistenceException, SystemException {
        log.entering("<: AdminUserSqlDAO :>", "<: validUserCodes :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATEUSERS);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, rolegroupCode);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, rolegroupCode);
        //List<String> validUsers = query.getResultList( getStringMapper("USRCOD") );
        List<ValidUsersVO> validUsers = query.getResultList(new ValidUserMapper());
        if (validUsers != null && validUsers.size() > 0) {

            return validUsers;
        }
        log.exiting("<: AdminUserSqlDAO :>", "<: validUserCodes :>");
        return null;
    }


    // Added by Sinoob starts

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param userCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findSpecificStaff(String companyCode,
                                                String airportCode, String warehouseCode, String userCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Collection<String> userIds = null;
        Query query = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDSPECIFICSTAFF);
        query.setParameter(++index, companyCode);
        //query.setParameter(++index, roleGroupCode);
        query.setParameter(++index, userCode);
        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND USRMST.DEFARP = ? ");
            query.setParameter(++index, airportCode);
        }
        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append("	AND USRMST.DEFWHS = ? ");
            query.setParameter(++index, warehouseCode);
        }
        userIds = query.getResultList(getStringMapper("USRMSTUSRCOD"));
        log.log(Log.FINER, "returning userIds : ", userIds);
        log.log(Log.FINE, "RETURN");
        return userIds;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param userCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findSpecificStaffOnline(String companyCode,
                                                      String airportCode, String warehouseCode, String userCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Query query = null;
        Collection<String> userIds = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDSPECIFICSTAFFONLINE);
        query.setParameter(++index, companyCode);
        //query.setParameter(++index, roleGroupCode);
        query.setParameter(++index, userCode);

        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND SESMST.ARPCOD = ? ");
            query.setParameter(++index, airportCode);
        }

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append("	AND SESMST.DEFWHSCOD = ? ");
            query.setParameter(++index, warehouseCode);
        }
        userIds = query.getResultList(getStringMapper("SESMSTUSRCOD"));
        log.log(Log.FINER, "returning userIds : ", userIds);
        log.log(Log.FINE, "RETURN");
        return userIds;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findUsersInRoleGroupOnline(String companyCode,
                                                         String airportCode, String warehouseCode, String roleGroupCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Collection<String> users = null;
        Query query = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDUSERSINROLEGROUP);
        query.setParameter(++index, companyCode);

        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND USR.DEFARP = ? ");
            query.setParameter(++index, airportCode);
        }

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append("	AND USR.DEFWHS = ? ");
            query.setParameter(++index, warehouseCode);
        }
        query.append(" AND USR.DEFROLGRP IN ( ");
        query.append(" SELECT DISTINCT SESMST.ROLGRPCOD SESMSTROLGRPCOD ")
                .append(" FROM ICGSESMST SESMST WHERE SESMST.CMPCOD = ? ")
                .append(" AND SESMST.ARPCOD = ? AND SESMST.ROLGRPCOD = ? ");
        query.setParameter(++index, companyCode);
        query.setParameter(++index, airportCode);
        query.setParameter(++index, roleGroupCode);

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append(" AND SESMST.DEFWHSCOD = ? ");
            query.setParameter(++index, warehouseCode);
        }

        query.append(" ) ");

        users = query.getResultList(getStringMapper("USRUSRCOD"));
        log.log(Log.FINER, "returning Collection : ", users);
        log.log(Log.FINE, "RETURN");
        return users;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findUsersInRoleGroup(String companyCode,
                                                   String airportCode, String warehouseCode, String roleGroupCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Collection<String> users = null;
        Query query = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDUSERSINROLEGROUP);
        query.setParameter(++index, companyCode);

        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND USR.DEFARP = ? ");
            query.setParameter(++index, airportCode);
        }

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append("	AND USR.DEFWHS = ? ");
            query.setParameter(++index, warehouseCode);
        }

        /*
         * Adding rolegroup criteria in WHERE clause
         * Changed for : ICRD-75605
         * Changed by : A-6042
         * Changed Date: 30-Jun-2014
         */
        if (roleGroupCode != null && roleGroupCode.trim().length() > 0) {
            query.append("	AND USR.DEFROLGRP = ? ");
            query.setParameter(++index, roleGroupCode);

            query.append(" union select distinct STN.usrcod from  ADMUSRALLSTN STN, admusrmst USR "
                    + "where STN.usrcod = USR.usrcod and USR.cmpcod=STN.cmpcod and"
                    + " STN.cmpcod=? AND STN.DEFROLGRP=?");
            query.setParameter(++index, companyCode);
            query.setParameter(++index, roleGroupCode);

            if (airportCode != null && airportCode.trim().length() > 0) {
                query.append("	AND USR.DEFARP = ? ");
                query.setParameter(++index, airportCode);
            }

            if (warehouseCode != null && warehouseCode.trim().length() > 0) {
                query.append("	AND USR.DEFWHS = ? ");
                query.setParameter(++index, warehouseCode);
            }
            query.append(" union select distinct ROL.usrcod from ADMSTNROLGRP ROL, admusrmst USR "
                    + "where ROL.usrcod = USR.usrcod and ROL.cmpcod=USR.cmpcod"
                    + " and ROL.cmpcod=? and ROL.ROLGRPCOD=?");
            query.setParameter(++index, companyCode);
            query.setParameter(++index, roleGroupCode);
            if (airportCode != null && airportCode.trim().length() > 0) {
                query.append("	AND USR.DEFARP = ? ");
                query.setParameter(++index, airportCode);
            }

            if (warehouseCode != null && warehouseCode.trim().length() > 0) {
                query.append("	AND USR.DEFWHS = ? ");
                query.setParameter(++index, warehouseCode);
            }
        }
        /*
         * ICRD-75605 changes end here.
         */

        users = query.getResultList(getStringMapper("USRUSRCOD"));
        log.log(Log.FINER, "returning Collection : ", users);
        log.log(Log.FINE, "RETURN");
        return users;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findRoleGroup(String companyCode,
                                            String airportCode, String warehouseCode, String roleGroupCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Collection<String> roleGroups = null;
        Query query = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FINDROLEGROUP);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, roleGroupCode);

        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND USR.DEFARP = ? ");
            query.setParameter(++index, airportCode);
        }

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append("	AND USR.DEFWHS = ?	");
            query.setParameter(++index, warehouseCode);
        }
        roleGroups = query.getResultList(getStringMapper("USRDEFROLGRP"));
        log.log(Log.FINER, "returning roleGroups : ", roleGroups);
        log.log(Log.FINE, "RETURN");
        return roleGroups;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findRoleGroupOnline(String companyCode,
                                                  String airportCode, String warehouseCode, String roleGroupCode)
            throws SystemException, PersistenceException {
        log.log(Log.FINE, "ENTRY");
        Collection<String> roleGroups = null;
        Query query = null;
        int index = 0;
        query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FINDROLEGROUPONLINE);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, roleGroupCode);

        if (airportCode != null && airportCode.trim().length() > 0) {
            query.append("	AND SESMST.ARPCOD = ? ");
            query.setParameter(++index, airportCode);
        }

        if (warehouseCode != null && warehouseCode.trim().length() > 0) {
            query.append(" AND SESMST.DEFWHSCOD = ? ");
            query.setParameter(++index, warehouseCode);
        }
        roleGroups = query.getResultList(getStringMapper("SESMSTROLGRPCOD"));
        log.log(Log.FINER, "returning roleGroups : ", roleGroups);
        log.log(Log.FINE, "RETURN");
        return roleGroups;
    }

    // Added by Sinoob ends

    /**
     * @param userLovFilterVO
     * @return Collection<UserLovVO>
     * @throws SystemException
     * @throws PersistenceException
     * @author A-1863
     */
    public Collection<UserLovVO> findUserDetailsByFilterLov(UserLovFilterVO
                                                                    userLovFilterVO) throws SystemException, PersistenceException {
        log.entering("AdminUserSqlDAO ", " findUserDetailsLov ");
        String baseQry = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILSLOV_BASE);
        Query qry = new UserDetailsLovFilterQuery(userLovFilterVO, baseQry);
        log.exiting("<: AdminUserSqlDAO :>", "<: findUserDetailsLov :>");
        return qry.getResultList(new UserLovMapper());
    }

    /**
     * @param userCodes
     * @param companyCode
     * @return Collection<ValidUsersVO>
     * @throws SystemException
     * @throws PersistenceException
     * @author A-1863
     */
    public Collection<ValidUsersVO> validateUsersWithoutRoleGroup(
            Collection<String> userCodes, String companyCode)
            throws SystemException, PersistenceException {
        log.entering("<: AdminUserSqlDAO :>", "<: validUserCodes :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATEUSERSWITOUTROLEGRP);
        query.setParameter(++index, companyCode);
        StringBuilder str = new StringBuilder("('");
        if (userCodes != null && userCodes.size() > 0) {
            for (String user : userCodes) {
                str.append(user).append("','");
            }
            str.delete(str.length() - 3, str.length());
        }
        str.append("')");
        query.append(str.toString());
        List<ValidUsersVO> validUsers = query.getResultList(new ValidUserMapper());
        if (validUsers != null && validUsers.size() > 0) {

            return validUsers;
        }
        log.exiting("<: AdminUserSqlDAO :>", "<: validUserCodes :>");
        return null;
    }

    /**
     * @param companyCode
     * @return Collection<ValidUsersVO>
     * @throws SystemException
     * @throws PersistenceException
     * @author A-1863
     */
    public Collection<UserRoleGroupDetailsVO> validateRoleGroup(
            Collection<String> roleGroupCodes, String companyCode)
            throws SystemException, PersistenceException {
        log.entering("<: AdminUserSqlDAO :>", "<: validateRoleGroup :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATEROLEGROUP);
        query.setParameter(++index, companyCode);
        StringBuilder str = new StringBuilder("('");
        if (roleGroupCodes != null && roleGroupCodes.size() > 0) {
            for (String user : roleGroupCodes) {
                str.append(user).append("','");
            }
            str.delete(str.length() - 3, str.length());
        }
        str.append("')");
        query.append(str.toString());
        List<UserRoleGroupDetailsVO> validroleGroups =
                query.getResultList(new Mapper<UserRoleGroupDetailsVO>() {
                    public UserRoleGroupDetailsVO map(ResultSet rs) throws SQLException {
                        UserRoleGroupDetailsVO detailsVO = new UserRoleGroupDetailsVO();
                        detailsVO.setRoleGroupCode(rs.getString("ROLGRPCOD"));
                        detailsVO.setRoleGroupName(rs.getString("ROLGRPNAM"));
                        return detailsVO;
                    }
                });
        if (validroleGroups != null && validroleGroups.size() > 0) {

            return validroleGroups;
        }
        log.exiting("<: AdminUserSqlDAO :>", "<: validateRoleGroup :>");
        return null;

    }

    /**
     *
     */
    public boolean checkUserValid(String companyCode, String userCode, String encryptedPassword)
            throws SystemException, PersistenceException {

        log.entering("<: AdminUserSqlDAO :>", "<: validUserCodes :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATEUSER);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        query.setParameter(++index, encryptedPassword);

        ValidUsersVO validUserVo = (ValidUsersVO) query.getSingleResult(new ValidUserMapper());
        log.log(Log.INFO, "validUserVO>>>>>>>>>>>", validUserVo);
        if (validUserVo != null) {
            return true;
        }
        log.exiting("<: AdminUserSqlDAO :>", "<: isUserValid :>");
        return false;
    }

    /**
     * @param userEnquiryFilterVO
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */

    public Page<UserVO> findUserEnquiryDetails(
            UserEnquiryFilterVO userEnquiryFilterVO) throws PersistenceException, SystemException {

        log.entering("<: AdminUserSqlDAO :>", "<: findUserEnquiryDetails :>");

        int index = 0;
        //added by A-5223 for ICRD-23107 starts
        StringBuilder rankQuery = new StringBuilder();
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_DENSE_RANK_QUERY);
        rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.USRCOD )RANK FROM (");


        Query baseQuery = null;

        boolean isDefRoleQuery = false;
        boolean isAddRoleQuery = false;

        // If filtering based on role group
        if (userEnquiryFilterVO.getRoleGroupType() != null &&
                userEnquiryFilterVO.getRoleGroupType().trim().length() > 0 &&
                userEnquiryFilterVO.getRoleGroup() != null &&
                userEnquiryFilterVO.getRoleGroup().trim().length() > 0) {

            if ("A".equals(userEnquiryFilterVO.getRoleGroupType())) {// Additional Rolegroup type selected
                isAddRoleQuery = true;
                baseQuery = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY_FOR_ADL_ROL);

            } else {//Default Role type selected
                isDefRoleQuery = true;
                baseQuery = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY_FOR_DEF_ROL);
            }

        } else {
            baseQuery = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY);
        }

        rankQuery.append(baseQuery);
        PageableNativeQuery<UserVO> query;
        if (userEnquiryFilterVO.getPageSize() > 0) {
            query = new PageableNativeQuery<UserVO>(userEnquiryFilterVO.getPageSize(), userEnquiryFilterVO.getTotalRecords(), rankQuery.toString(), new UserEnquiryMapper(userEnquiryFilterVO), sessionSupplier.get());
        } else {
            query = new PageableNativeQuery<UserVO>(userEnquiryFilterVO.getTotalRecords(), rankQuery.toString(), new UserEnquiryMapper(userEnquiryFilterVO), sessionSupplier.get());
        }

        //added by A-5223 for ICRD-23107 ends
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());


        if (isDefRoleQuery) {
            query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
            query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
        }

        if (isAddRoleQuery) {
            query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
        }


        if (userEnquiryFilterVO.getAirportCode() != null &&
                userEnquiryFilterVO.getAirportCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFARP = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getAirportCode());
        }
        if (userEnquiryFilterVO.getUserCode() != null &&
                userEnquiryFilterVO.getUserCode().trim().length() > 0) {
            query.append(" AND USRMST.USRCOD = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getUserCode());
        }
        if (userEnquiryFilterVO.getWarehouseCode() != null &&
                userEnquiryFilterVO.getWarehouseCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFWHS = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getWarehouseCode());
        }
        if (userEnquiryFilterVO.getStationCode() != null &&
                userEnquiryFilterVO.getStationCode().trim().length() > 0) {
            query.append(" AND USRMST.DEFSTN = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getStationCode());
        }

        if (userEnquiryFilterVO.getAccountStatus() != null &&
                userEnquiryFilterVO.getAccountStatus().trim().length() > 0) {

            query.append(" AND USRMST.ACCDISFLG = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getAccountStatus());
        }

        if (userEnquiryFilterVO.getRemainingGraceLogin() != 0
        ) {
            query.append(" AND USRMST.REMGRCLOG = ? ");
            query.setParameter(++index, userEnquiryFilterVO.getRemainingGraceLogin());
        }
			/*if(userEnquiryFilterVO.getRoleGroup() != null &&
					userEnquiryFilterVO.getRoleGroup().trim().length() > 0){
				query.append(" AND USRMST.DEFROLGRP = ? ");
				query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
			}*/

			/*if(userEnquiryFilterVO.getAccountFromDate() != null ){
				query.append(" AND USRMST.ACCFRMDAT >=? ");
				query.setParameter(++index, userEnquiryFilterVO.getAccountFromDate());
			}
			if(userEnquiryFilterVO.getAccountToDate() != null ){
				query.append(" AND USRMST.ACCENDDAT <=? ");
				query.setParameter(++index, userEnquiryFilterVO.getAccountToDate());
			}*/

        if (userEnquiryFilterVO.getPwasswordFromDate() != null) {
            query.append(" AND USRMST.PWDEXPDAT >=? ");
            query.setParameter(++index, userEnquiryFilterVO.getPwasswordFromDate());
        }
        if (userEnquiryFilterVO.getPwasswordToDate() != null) {
            query.append(" AND USRMST.PWDEXPDAT <=? ");
            query.setParameter(++index, userEnquiryFilterVO.getPwasswordToDate());
        }

        if (userEnquiryFilterVO.getCreationDate() != null) {
            query.append(" AND USRMST.CRTDAT >=? ");
            query.setParameter(++index, userEnquiryFilterVO.getCreationDate());
        }
        if (userEnquiryFilterVO.getFirstName() != null) {
            query.append(" AND USRMST.FSTNAM =? ");
            query.setParameter(++index, userEnquiryFilterVO.getFirstName());
        }
        if (userEnquiryFilterVO.getLastName() != null) {
            query.append(" AND USRMST.LSTNAM =? ");
            query.setParameter(++index, userEnquiryFilterVO.getLastName());
        }
        if (userEnquiryFilterVO.getCountry() != null) {
            query.append(" AND USRMST.OFFCNTNAM =? ");
            query.setParameter(++index, userEnquiryFilterVO.getCountry());
        }
        if (userEnquiryFilterVO.getCity() != null) {
            query.append(" AND USRMST.OFFCTYNAM =? ");
            query.setParameter(++index, userEnquiryFilterVO.getCity());
        }
        if (userEnquiryFilterVO.getPortalUserType() != null) {
            query.append(" AND USRMST.PORUSRTYP =? ");
            query.setParameter(++index, userEnquiryFilterVO.getPortalUserType());
        }
        if (userEnquiryFilterVO.getCustomerCode() != null) {
            query.append(" AND MPG.MPGCOD =? ");
            query.setParameter(++index, userEnquiryFilterVO.getCustomerCode());
        }
        if (userEnquiryFilterVO.getPortaldefaultRoleGroup() != null) {
            query.append(" AND USRMST.PORDEFROLGRP =? ");
            query.setParameter(++index, userEnquiryFilterVO.getPortaldefaultRoleGroup());
        }

        if (isDefRoleQuery || isAddRoleQuery) {
				
				/*if(isAddRoleQuery){// Additional Rolegroup type selected						
					query.append(" STNADLROL= ? ");
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					
				}
				else if(isDefRoleQuery){// Default Rolegroup type selected
					query.append(" STNDEFROL = ?  ");					
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					
				}*/

        } else {
            if (userEnquiryFilterVO.getRoleGroup() != null &&
                    userEnquiryFilterVO.getRoleGroup().trim().length() > 0) {
                query.append(" AND USRMST.DEFROLGRP = ? ");
                query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
            }
        }

			/*if(userEnquiryFilterVO.getRoleGroupType()!=null && 
					userEnquiryFilterVO.getRoleGroupType().trim().length()>0 ){
				
				if("A".equals(userEnquiryFilterVO.getRoleGroupType()) &&
						userEnquiryFilterVO.getRoleGroup() != null &&
						userEnquiryFilterVO.getRoleGroup().trim().length() > 0){// Additional Rolegroup type selected					
						query.append(" AND STNROLGRP.ROLGRPCOD =? ");
						query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());					
				
				}
				else if("D".equals(userEnquiryFilterVO.getRoleGroupType()) &&
						userEnquiryFilterVO.getRoleGroup() != null &&
						userEnquiryFilterVO.getRoleGroup().trim().length() > 0){// Default Rolegroup type selected	
					
					query.append(" AND ( ALLSTN.DEFROLGRP = ?  ");					
					query.append(" OR ( USRMST.DEFROLGRP= ? AND ALLSTN.DEFROLGRP IS NULL)  ");					
					query.append(" OR ( USRMST.DEFROLGRP= ? AND ALLSTN.STNCOD NOT IN ( USRMST.DEFSTN) ");
					query.append("  AND NOT EXISTS  ( SELECT 1 FROM ADMUSRALLSTN INNERTAB  ");
					query.append("  WHERE INNERTAB.CMPCOD = USRMST.CMPCOD  ");
					query.append("  AND INNERTAB.USRCOD = USRMST.USRCOD  ");
					query.append("  AND INNERTAB.DEFROLGRP NOT IN( ? )  ");
					query.append("  AND INNERTAB.STNCOD = USRMST.DEFSTN )");	
					query.append("    ) )");					 
					
					
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
					
									
				}
			}else if(userEnquiryFilterVO.getRoleGroup() != null &&
					userEnquiryFilterVO.getRoleGroup().trim().length() > 0){
				query.append(" AND USRMST.DEFROLGRP = ? ");
				query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());
			}*/


//			PageableQuery<UserVO> pgqry = new PageableQuery<UserVO>(query,new UserEnquiryMapper());

        if (userEnquiryFilterVO.getInActiveSince() != null) {
            query.append(" AND ( USRMST.LSTLOGINTIM < ? ");
            query.setParameter(++index, userEnquiryFilterVO.getInActiveSince());
            query.append(" OR USRMST.LSTLOGINTIM  IS NULL )");
        }

        query.append(AdminUserPersistenceConstants.ADMIN_USER_SUFFIX_QUERY);
        return query.getPage(userEnquiryFilterVO.getPageNumber());


    }

    public Collection<String> getAllUsersToDisable(String companyCode, int inActivePeriod)
            throws SystemException, PersistenceException {
        log.entering("AdminUserSqlDAO ", " getAllUsersToDisable ");
        int index = 0;
        //	LocalDate login = new LocalDate(LocalDate.NO_STATION,Location.NONE,false).;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FINDINACTIVEUSERS);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, inActivePeriod);
        //query.setParameter(++index,inActivePeriod);
        List<String> activeUsers = query.getResultList(getStringMapper("USRCOD"));
        if (activeUsers != null && activeUsers.size() > 0) {
            log.exiting(" AdminUserSqlDAO ", " getAllUsersToDisable ");
            return activeUsers;
        }
        log.exiting("\n\n AdminUserSqlDAO ", " getAllUsersToDisable -> with ValidUsersVO as NULL !!!\n\n");
        return null;
    }

    /**
     * This method lists the user details
     *
     * @param companyCode
     * @param rolegroupCodeOne
     * @param rolegroupCodeTwo
     * @return Page<UserLovVO>
     * @throws SystemException
     * @author A-2677
     */
    public Collection<ValidUsersVO> validateCashiers(
            Collection<String> userCodes, String companyCode, String rolegroupCodeOne, String rolegroupCodeTwo)
            throws PersistenceException, SystemException {
        log.entering("<: AdminUserSqlDAO :>", "<: validateCashiers :>");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATECASHIERS);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, rolegroupCodeOne);
        query.setParameter(++index, rolegroupCodeTwo);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, rolegroupCodeOne);
        query.setParameter(++index, rolegroupCodeTwo);
        List<ValidUsersVO> validUsers = query.getResultList(new ValidUserMapper());
        if (validUsers != null && validUsers.size() > 0) {

            return validUsers;
        }
        log.exiting("<: AdminUserSqlDAO :>", "<: validateCashiers :>");
        return null;
    }

    /**
     * @param companyCode
     * @param userCode
     * @param pageNumber
     * @return Page<UserAllowableStationsVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    public Page<UserAllowableStationsVO> findUserAllowableStationsVOPage(String companyCode, String userCode,
                                                                         int pageNumber, int absoluteIndex) throws SystemException, PersistenceException {

        log.entering(" AdminUserSqlDAO ", " findUserAllowableStationsVOPage ");
        int index = 0;

        StringBuilder rankQuery = new StringBuilder();
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_DENSE_RANK_QUERY);
        rankQuery.append("RESULT_TABLE.allstn,RESULT_TABLE.stndefrol )RANK FROM (");

        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FINDUSERALLOWABLESTATION_PAGES);

        rankQuery.append(query);
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_SUFFIX_QUERY);
		
		/*Collection<UserAllowableStationsVO> userAllowableStationsVOs = query.getResultList(new UserStationMapper());
		if(userAllowableStationsVOs != null && userAllowableStationsVOs.size() > 0){
			log.exiting(" AdminUserSqlDAO ", " findUserAllowableStationsPages ");
			return userAllowableStationsVOs;
		}*/

        /*
         * The PageableNativeQuery is used to fetch the pages as part of bug fix for ICRD-78284
         * Author : A-6042
         */
        int recordcount = 0;
        PageableNativeQuery<UserAllowableStationsVO> pageableQuery = new PageableNativeQuery<UserAllowableStationsVO>(recordcount, rankQuery.toString(), new UserAllowableStationsVOMapper(), sessionSupplier.get());
        pageableQuery.setParameter(++index, companyCode);
        pageableQuery.setParameter(++index, userCode);

        log.log(Log.INFO, "\npgqry ------------->>>>>>>>>", pageableQuery.toString());
        log.log(Log.INFO, "\nPageNum ------------->>>>>>>>>", pageNumber);

        Page<UserAllowableStationsVO> op = pageableQuery.getPage(pageNumber);

        log.log(Log.INFO,
                "\nfindUserAllowableStationsVOPage ------------->>>>>>>>>\n",
                op);
        log.exiting(" AdminUserSqlDAO ", " findUserAllowableStationsVOPage ");
        return op;

    }

    /**
     *
     */
    public void udapteUserDetails(String companyCode) throws PersistenceException, SystemException {
        Procedure procedure = getQueryManager().createNamedNativeProcedure(
                AdminUserPersistenceConstants.ADMIN_USER_UPDATEPROCEDURE);
        procedure.setParameter(1, companyCode);
        procedure.setOutParameter(2, SqlType.STRING);
        procedure.execute();
        log.log(Log.FINEST, "Out Put is ", procedure.getParameter(2));
    }

    //ICRD-57144 changes begins here

    /**
     * This method retrieves user parameter values from data store.
     *
     * @param logonAttributes logonAttributes for an authenticated user
     * @return Returns modified logonAttributes object with user parameter
     * details
     * @throws PersistenceException Throws wneh any exceptional condition occur while dealing
     *                              with database
     * @throws SystemException      Throw whenever an exceptional condition occur other than DB
     *                              related.
     * @author A-6042
     * @since ICRD-57144
     */
    @Override
    public LogonAttributes getUserParametersValueMap(LogonAttributes logonAttributes)
            throws PersistenceException, SystemException {
        log.entering("AdminUserSqlDAO", "getUserParametersValueMap");

        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_PARAMETERS_VALUE_MAP);

        int index = 0;

        query.setParameter(++index, logonAttributes.getCompanyCode());
        query.setParameter(++index, logonAttributes.getUserId());

        List<HashMap<String, String>> userParameters = query
                .getResultList(
                        new UserParametersValueMapper());

        if ((userParameters != null) && (userParameters.size() > 0)) {
            HashMap<String, String> parameterMap = userParameters.get(0);
            logonAttributes.setUserParameterMap(parameterMap);
        } else {
            logonAttributes.setUserParameterMap(null);
        }

        log.exiting("AdminUserSqlDAO", "getUserParametersValueMap");

        return logonAttributes;
    }

    /**
     * This mapper class maps all user parameters in a HashMap object.
     *
     * @author A-6042
     */
    class UserParametersValueMapper implements MultiMapper<HashMap<String, String>> {
        private static final String COL_PARAM_CODE = "PARCOD";
        private static final String COL_PARAM_VAL = "PARVAL";

        /**
         * Extracts User Parameter code and value from the input
         * resultSet object.
         *
         * @author A-6042
         */
        public List<HashMap<String, String>> map(final ResultSet resultSet)
                throws SQLException {
            List<HashMap<String, String>> userParameters = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> parameterMap = new HashMap<String, String>();
            while (resultSet.next()) {
                parameterMap.put(resultSet.getString(COL_PARAM_CODE),
                        resultSet.getString(COL_PARAM_VAL));
            }

            userParameters.add(parameterMap);

            return userParameters;
        }
    }
    //ICRD-57144 changes ends here


    public UserVO findUserDetailsForPortal(String companyCode, String userCode)
            throws PersistenceException, SystemException {
        log.entering(" AdminUserSqlDAO ", " findUserDetails ");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FINDUSERDETAILS);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, userCode);
        query.append(" AND USRMST.USRTYP = ? ");
        query.setParameter(++index, "P");
        List<UserVO> userVOs = query.getResultList(new UserMapper());

        if (userVOs != null && userVOs.size() > 0) {
            log.exiting(" AdminUserSqlDAO ", " findUserDetails ");
            return userVOs.get(0);
        } else {
            UserVO userVO = new UserVO();
            Collection<UserParameterVO> userParameterVOs = findUserParameterCodes(companyCode);
            userVO.setUserParameterVOs(userParameterVOs);
            return userVO;
        }
    }

    public Collection<ValidUsersVO> findUserDetailsForRoleGroups(
            Collection<String> userCodes, String companyCode, String rolegroupCode, String adminRoleGroupCode, String stationCode)
            throws PersistenceException, SystemException {
        log.entering("<: AdminUserSqlDAO :>", "<: findUserDetailsForRoleGroups :>");
        String baseQry = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY1);
        String appendQuery1 = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY2);
        String appendQuery2 = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY3);

        UserLovFilterVO userLovFilterVO = new UserLovFilterVO();
        userLovFilterVO.setCompanyCode(companyCode);
        ArrayList<String> rolegroups = new ArrayList<String>();


        //Added by A-4562 for ICRD-113918 starts
        String rolegroupCodes[];


        if (rolegroupCode != null && !rolegroupCode.isEmpty()) {
            rolegroupCodes = rolegroupCode.split(",");

            if (rolegroupCodes != null && rolegroupCodes.length > 0) {

                rolegroups.addAll(Arrays.asList(rolegroupCodes));

            }
        }
        String adminRoleGroupCodes[];

        if (adminRoleGroupCode != null && !adminRoleGroupCode.isEmpty()) {
            adminRoleGroupCodes = adminRoleGroupCode.split(",");

            if (adminRoleGroupCodes != null && adminRoleGroupCodes.length > 0) {

                rolegroups.addAll(Arrays.asList(adminRoleGroupCodes));

            }
        }
        //Added by A-4562 for ICRD-113918 ends
        userLovFilterVO.setRoleGroupCodes(rolegroups);
        userLovFilterVO.setStationCode(stationCode);
        Query query = new UserDetailsForRoleGroupsFilterQuery(userLovFilterVO, baseQry, appendQuery1, appendQuery2);
        Collection<ValidUsersVO> validUsersVOList = query.getResultList(new UserDetailsForRoleGroupsMapper());

        log.exiting("<: AdminUserSqlDAO :>", "<: findUserDetailsForRoleGroups :>");

        return validUsersVOList;
    }


    public Page<UserLovVO> findUserDetailsForRoleGroupsLov(
            UserLovFilterVO userLovFilterVO) throws PersistenceException,
            SystemException {
        log.entering("<: AdminUserSqlDAO :>", "<: findUserDetailsForRoleGroupsLov :>");

        String baseQry = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY1);
        String appendQuery1 = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY2);
        String appendQuery2 = getQueryManager().getNamedNativeQueryString(
                AdminUserPersistenceConstants.
                        ADMIN_USER_FINDUSERDETAILSLOV_QUERY3);

        StringBuilder rankQuery = new StringBuilder();
        rankQuery.append(AdminUserPersistenceConstants.ADMIN_USER_ROWNUM_QUERY);
        rankQuery.append(baseQry);
        PageableNativeQuery<UserLovVO> userpgqry =
                new UserDetailsForRoleGroupsPaginationFilterQuery(0, new UserDetailsForRoleGroupsLovMapper(),
                        userLovFilterVO, rankQuery.toString(), appendQuery1, appendQuery2);
        log.exiting("<: AdminUserSqlDAO :>", "<: findUserDetailsForRoleGroupsLov :>");

        userpgqry.append(AdminUserPersistenceConstants.ADMIN_USER_SUFFIX_QUERY);
        return userpgqry.getPage(userLovFilterVO.getPageNumber());
    }

    /**
     * @param companyCode
     * @param user
     * @return
     * @throws SystemException
     */
    private Collection<ExhaustedGraceLoginVO> getExhaustedGraceLoginDetails(
            String companyCode, String user) throws SystemException {
        Collection<ExhaustedGraceLoginVO> exhaustedGraceLoginVOs = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_EXHAUSTED_LOGIN);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, user);
        return query.getResultList(new GraceLoginMapper());
    }

    static class GraceLoginMapper implements MultiMapper<ExhaustedGraceLoginVO> {

        public List<ExhaustedGraceLoginVO> map(ResultSet rs)
                throws SQLException {
            List<ExhaustedGraceLoginVO> exhaustedGraceLoginVOs = new ArrayList<ExhaustedGraceLoginVO>();

            while (rs.next()) {
                ExhaustedGraceLoginVO exhaustedGraceLoginVO = new ExhaustedGraceLoginVO();
                exhaustedGraceLoginVO.setGraceLoginSequenceNumber(rs
                        .getInt("GRCLOG"));

                if (rs.getDate("LOGDAT") != null) {

                    exhaustedGraceLoginVO.setGraceLoginDate(new LocalDate(
                            LocalDate.NO_STATION, Location.NONE, rs
                            .getTimestamp("LOGDAT")));
                }

                exhaustedGraceLoginVOs.add(exhaustedGraceLoginVO);
            }
            return exhaustedGraceLoginVOs;
        }
    }

    /**
     * @param companyCode
     * @param user
     * @return
     * @throws SystemException
     */
    private Collection<UserFavouriteVO> getFavScreenDetails(
            String companyCode, String user) throws SystemException {
        Collection<UserFavouriteVO> favVOs = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_FAVOURITES);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, user);
        return query.getResultList(new UserFavouriteMapper());
    }

    /**
     * @author A-6203
     */
    static class UserFavouriteMapper implements Mapper<UserFavouriteVO> {

        public UserFavouriteVO map(ResultSet rs)
                throws SQLException {
            UserFavouriteVO userFavouriteVO = new UserFavouriteVO();
            userFavouriteVO.setScreenCode(rs.getString("SCRIDR"));
            userFavouriteVO.setScreenName(rs.getString("FAVMODNAM"));
            return userFavouriteVO;
        }
    }

    /**
     * @param companyCode
     * @param user
     * @return
     * @throws SystemException
     */
    private Collection<UserAllowableOfficesVO> getUserOffices(
            String companyCode, String user) throws SystemException {
        Collection<UserAllowableOfficesVO> userAllowableOfficesVOs = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_OFFICES_LIST);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, user);
        return query.getResultList(new UserOfficeMapper());
    }

    /**
     * @param companyCode
     * @param user
     * @return
     * @throws SystemException
     */
    private Collection<UserAllowableStationsVO> getUserAllowableStations(
            String companyCode, String user) throws SystemException {
        Collection<UserAllowableStationsVO> userAllowableStationsVOs = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                isOracleDataSource() ? AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_STATIONS : AdminUserPersistenceConstants.POSTGRES_ADMIN_USER_FIND_USER_STATIONS);
        query.setParameter(++index, companyCode);
        query.setParameter(++index, user);
        return query.getResultList(new UserAllowableStationsMapper());
    }

    /**
     * @author A-6203
     */
    static class UserAllowableStationsMapper implements Mapper<UserAllowableStationsVO> {

        public UserAllowableStationsVO map(ResultSet resultSet)
                throws SQLException {
            List<UserAllowableStationsVO> userAllowableStationsVOs = new ArrayList<UserAllowableStationsVO>();
            Collection<String> additionalRoles = null;
            UserAllowableStationsVO stationsVO = new UserAllowableStationsVO();
            stationsVO.setCompanyCode(resultSet.getString("CMPCOD"));
            stationsVO.setUserCode(resultSet.getString("USRCOD"));
            stationsVO.setStationCode(resultSet.getString("ALLSTN"));
            stationsVO.setStationDefaultRoleGroup(resultSet.getString("STNDEFROL"));

            if (resultSet.getString("ROLGRPCOD") != null) {
                additionalRoles = new ArrayList<String>();
                additionalRoles = Arrays.asList(resultSet.getString("ROLGRPCOD").split(","));
                stationsVO.setAdditionalRoleGroups(additionalRoles);
            }
            return stationsVO;
        }
    }


    /* (non-Javadoc)
     * @see com.ibsplc.icargo.persistence.dao.admin.user.AdminUserDAO#getPortalUserdetailsForLogon(com.ibsplc.icargo.framework.security.vo.LogonAttributes)
     */
    @Override
    public PortalUserDetailsVO getPortalUserdetails(
            String companyCode, String userCode) throws
            SystemException {
        // ADMIN_USER_FIND_PORTAL_USER_DEATILS_FOR_LOGON

        log.entering(" AdminUserSqlDAO ", " getPortalUserdetails");
        int index = 0;
        PortalUserDetailsVO portalVo = new PortalUserDetailsVO();
        Query query = getQueryManager().createNamedNativeQuery(AdminUserPersistenceConstants.ADMIN_USER_FIND_PORTAL_USER_DEATILS);
        query.setParameter(++index, userCode);
        query.setParameter(++index, companyCode);
        List<PortalUserDetailsVO> vos = query.getResultList(new PortaluserDetailsMapper());
        if (vos != null && vos.size() > 0) {
            portalVo = vos.get(0);
        }
        log.exiting(" AdminUserSqlDAO ", " getPortalUserdetailsForLogon ");
        return portalVo;
    }

    static class PortaluserDetailsMapper implements MultiMapper<PortalUserDetailsVO> {
        /**
         * map method
         */
        public List<PortalUserDetailsVO> map(ResultSet rs) throws SQLException {
            List<PortalUserDetailsVO> userList = new ArrayList<PortalUserDetailsVO>();
            PortalUserDetailsVO PortalUserDetailsVO = null;

            List<String> userKey = new ArrayList<String>();
            List<String> mappingKeys = new ArrayList<String>();
            List<String> milestoneKeys = new ArrayList<String>();
            List<String> addressKeys = new ArrayList<String>();
            List<String> prvKeys = new ArrayList<String>();

            PortalUserMappingVO mappingVo;
            PortalUserNotificationAddressVO addressVo;

            while (rs.next()) {
                String key = new StringBuilder(rs.getString("CMPCOD"))
                        .append("~").append(rs.getString("USRCOD")).toString();

                if (!userKey.contains(key)) {
                    userKey.add(key);
                    PortalUserDetailsVO = new PortalUserDetailsVO();
                    PortalUserDetailsVO.setPortalDefaultRoleGroupCode(rs.getString("PORDEFROLGRP"));
                    PortalUserDetailsVO.setPortalUserType(rs.getString("PORUSRTYP"));
                    PortalUserDetailsVO.setCreatedByUser(rs.getString("CRTUSR"));
                    PortalUserDetailsVO.setMaxNumberOfChildUsers(rs.getInt("MAXCLDUSRCNT"));
                    PortalUserDetailsVO.setNumberOfChildUsers(rs.getInt("CLDUSRCNT"));
                    PortalUserDetailsVO.setNotificationFormat(rs.getString("NFNFMT"));
                    PortalUserDetailsVO.setNotificationLanguage(rs.getString("NFNLNG"));
                    PortalUserDetailsVO.setShowUnsecuredInfoMessageFlag(rs.getString("SECINFMSGFLG"));
                    PortalUserDetailsVO.setUseStandardEmailFlag(rs.getString("STDEMLFLG"));
                    userList.add(PortalUserDetailsVO);
                }
                if (rs.getString("MPGTYP") != null && rs.getString("MPGCOD") != null) {
                    String mappingKey = new StringBuilder(rs.getString("MPGTYP")).append(rs.getString("MPGCOD")).toString();
                    if (!mappingKeys.contains(mappingKey)) {
                        mappingVo = new PortalUserMappingVO();
                        mappingVo.setMappingCode(rs.getString("MPGCOD"));
                        mappingVo.setMappingType(rs.getString("MPGTYP"));
                        if (PortalUserDetailsVO.getMappingVos() == null) {
                            PortalUserDetailsVO.setMappingVos(new ArrayList<PortalUserMappingVO>());
                        }
                        PortalUserDetailsVO.getMappingVos().add(mappingVo);
                        mappingKeys.add(mappingKey);
                    }
                }
                if (rs.getString("NTFADD") != null && rs.getString("NTFMOD") != null) {

                    String addressKey = new StringBuilder(rs.getString("NTFADD")).append(rs.getString("NTFMOD")).toString();
                    if (!addressKeys.contains(addressKey)) {
                        addressVo = new PortalUserNotificationAddressVO();
                        addressVo.setAddress(rs.getString("NTFADD"));
                        addressVo.setMode(rs.getString("NTFMOD"));
                        if (PortalUserDetailsVO.getNotificationAddress() == null) {
                            PortalUserDetailsVO.setNotificationAddress(new ArrayList<PortalUserNotificationAddressVO>());
                        }
                        PortalUserDetailsVO.getNotificationAddress().add(addressVo);
                        addressKeys.add(addressKey);
                    }
                }

                if (rs.getString("MILSTNCOD") != null) {
                    String milestoneKey = new StringBuilder(rs.getString("MILSTNCOD")).toString();
                    if (!milestoneKeys.contains(milestoneKey)) {
                        if (PortalUserDetailsVO.getNotificatonActions() == null) {
                            PortalUserDetailsVO.setNotificatonActions(new ArrayList<String>());
                        }
                        PortalUserDetailsVO.getNotificatonActions().add(rs.getString("MILSTNCOD"));
                        milestoneKeys.add(milestoneKey);
                    }
                }

            }
            //PortalUserDetailsVO.setPortalUserCustomerCodes(cusKeys);
            return userList;
        }
    }


    /**
     * Added as part of ICRD-204467
     *
     * @param userEnquiryFilterVO
     * @return
     */
    public List<UserVO> findAllHandlingAreaUsers(
            UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        log.entering(" AdminUserSqlDAO ", " findAllHandlingAreaUsers");
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_HANDLING_AREA_USERS);
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());
        query.setParameter(++index, userEnquiryFilterVO.getAirportCode());
        query.setParameter(++index, userEnquiryFilterVO.getWarehouseCode());
        query.setParameter(++index, userEnquiryFilterVO.getUserCode() + "%");
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());
        query.setParameter(++index, userEnquiryFilterVO.getAirportCode());
        query.setParameter(++index, userEnquiryFilterVO.getWarehouseCode());
        query.setParameter(++index, userEnquiryFilterVO.getUserCode() + "%");
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());
        query.setParameter(++index, userEnquiryFilterVO.getAirportCode());
        query.setParameter(++index, userEnquiryFilterVO.getWarehouseCode());
        query.setParameter(++index, userEnquiryFilterVO.getUserCode() + "%");
        List<UserVO> userCodes = query.getResultList(new Mapper<UserVO>() {
            public UserVO map(ResultSet rs) throws SQLException {
                UserVO vo = new UserVO();
                vo.setUserCode(rs.getString("USRCOD"));
                vo.setUserLastName(rs.getString("FSTNAM"));
                vo.setUserFirstName(rs.getString("LSTNAM"));
                return vo;
            }
        });
        log.exiting(" AdminUserSqlDAO ", " findAllHandlingAreaUsers");
        return userCodes;
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.admin.user.AdminUserDAO#findUserDetailsForCustomer(java.lang.String, java.lang.String)
     * Added by 			:	A-7364 on 20-Jun-2018
     * Used for 			:
     * Parameters			:	@param customerCode
     * Parameters			:	@param companyCode
     * Parameters			:	@return
     * Parameters			:	@throws SystemException
     */
    public UserVO findUserDetailsForCustomer(String customerCode, String companyCode)
            throws SystemException {
        log.entering(" AdminUserSqlDAO ", " findUserDetailsForCustomer");
        UserVO userVO = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_FIND_USER_FOR_CUSTOMER);
        query.setParameter(++index, customerCode);
        query.setParameter(++index, companyCode);
        List<UserVO> userVOs = query.getResultList(new Mapper<UserVO>() {
            public UserVO map(ResultSet rs) throws SQLException {
                UserVO vo = new UserVO();
                vo.setUserCode(rs.getString("USRCOD"));
                vo.setIsAccountDisabled(rs.getString("ACCDISFLG"));
                return vo;
            }
        });

        if (userVOs != null && userVOs.size() > 0) {
            userVO = userVOs.iterator().next();
        }

        log.exiting(" AdminUserSqlDAO ", " findUserDetailsForCustomer");
        return userVO;
    }

    /**
     * @author a-5505
     */
    public UserVO validateHandlingAreaUsers(UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        UserVO userVO = null;
        int index = 0;
        Query query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_VALIDATE_HANDLING_AREA_USER);
        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());
        query.setParameter(++index, userEnquiryFilterVO.getUserCode());
        List<UserVO> userVOs = query.getResultList(new ValidateHandlingAreaUserMapper());
        if (userVOs != null && userVOs.size() > 0) {
            userVO = userVOs.iterator().next();
        }
        return userVO;
    }

    /**
     *
     */
    public List<UserRoleGroupDetailsVO> getRoleDetailsForUser(UserEnquiryFilterVO userEnquiryFilterVO)
            throws SystemException {
        Query query = null;
        if ("D".equals(userEnquiryFilterVO.getRoleGroupType())) {
            query = getQueryManager().createNamedNativeQuery(
                    AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY_DEF_ROL_DETAILS);

        } else {
            query = getQueryManager().createNamedNativeQuery(
                    AdminUserPersistenceConstants.ADMIN_USER_USERENQUIRY_ADL_ROL_DETAILS);
        }

        int index = 0;

        query.setParameter(++index, userEnquiryFilterVO.getCompanyCode());
        query.setParameter(++index, userEnquiryFilterVO.getUserCode());
        query.setParameter(++index, userEnquiryFilterVO.getRoleGroup());

        List<UserRoleGroupDetailsVO> roles = query
                .getResultList(
                        new UserRolesMapper());
        return roles;
    }

    static class UserRolesMapper implements MultiMapper<UserRoleGroupDetailsVO> {

        /* (non-Javadoc)
         * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
         */
        public List<UserRoleGroupDetailsVO> map(ResultSet rs) throws SQLException {

            List<UserRoleGroupDetailsVO> userRoleGroupDetailsVOs = new ArrayList<UserRoleGroupDetailsVO>();

            while (rs.next()) {
                UserRoleGroupDetailsVO userRoleGroupDetailsVO = new UserRoleGroupDetailsVO();
                userRoleGroupDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
                userRoleGroupDetailsVO.setUserCode(rs.getString("USRCOD"));
                userRoleGroupDetailsVO.setStationCode(rs.getString("STNCOD"));
                userRoleGroupDetailsVO.setRoleGroupCode(rs.getString("ROLGRP"));

                userRoleGroupDetailsVOs.add(userRoleGroupDetailsVO);
            }
            return userRoleGroupDetailsVOs;
        }
    }

    @Override
    public List<UserRoleGroupDetailsVO> findRoleGroupUserEmailDetails(String companyCode, String roleGroupsToSendMail,
                                                                      String stations) throws SystemException {
        log.entering(" AdminUserSqlDAO ", " findRoleGroupUserEmailDetails");
        Query query = null;
        query = getQueryManager().createNamedNativeQuery(
                AdminUserPersistenceConstants.ADMIN_USER_EMAIL_DETAILS);
        int index = 0;
        query.setParameter(++index, companyCode);
        //query.setParameter(++index, roleGroupsToSendMail.substring(1, roleGroupsToSendMail.length()-1));
        if (roleGroupsToSendMail != null && roleGroupsToSendMail.length() > 0) {
            query.append(" AND DEFROLGRP IN (").append(roleGroupsToSendMail).append(")");
        }
        if (stations != null && stations.length() > 0) {
            query.append(" AND DEFSTN IN (").append(stations).append(")");
        }
        List<UserRoleGroupDetailsVO> userRoleGroupDetailsVOs = query
                .getResultList(
                        new UserEmailDetailsMapper());
        log.exiting(" AdminUserSqlDAO ", " findRoleGroupUserEmailDetails");
        return userRoleGroupDetailsVOs;
    }

    static class UserEmailDetailsMapper implements MultiMapper<UserRoleGroupDetailsVO> {
        /* (non-Javadoc)
         * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
         */
        public List<UserRoleGroupDetailsVO> map(ResultSet rs) throws SQLException {
            List<UserRoleGroupDetailsVO> userRoleGroupDetailsVOs = new ArrayList<>();
            while (rs.next()) {
                UserRoleGroupDetailsVO userRoleGroupDetailsVO = new UserRoleGroupDetailsVO();
                userRoleGroupDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
                userRoleGroupDetailsVO.setUserCode(rs.getString("USRCOD"));
                userRoleGroupDetailsVO.setRoleGroupCode(rs.getString("DEFROLGRP"));
                userRoleGroupDetailsVO.setUserOfficialEmailAddress(rs.getString("OFFEML"));
                userRoleGroupDetailsVOs.add(userRoleGroupDetailsVO);
            }
            return userRoleGroupDetailsVOs;
        }

    }

}
