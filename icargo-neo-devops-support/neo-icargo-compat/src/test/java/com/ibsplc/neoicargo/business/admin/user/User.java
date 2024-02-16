/*
 * User.java Created on Jun 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.*;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.business.admin.user.vo.UserCompositeData;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserDAO;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Blob;
import java.util.*;

/**
 * @author A-1417
 * <p>
 * This class describes the user details
 */
@Table(name = "ADMUSRMST")
@Entity
public class User {

    private static final String CLASS_NAME = "User";

    private static final String MODULE_NAME = "admin.user";

    private static final Log LOG = LogFactory.getLogger(MODULE_NAME);
    static final Logger logger = LoggerFactory.getLogger(User.class);

    private static final int PASSWORD_LENGTH = 8;

    /**
     * Thi is the primary key class for the user entity
     */
    private UserPK userPK;

    /**
     * This field denotes the first name of the user
     */
    private String userFirstName;

    /**
     * This field denotes the middle name of the user
     */

    private String userMiddleName;

    /**
     * This field denotes the last name of the user
     */
    private String userLastName;

    /**
     * This field denotes the personal address details of the user
     */
    private String userPersonalAddress;

    /**
     * This field denotes the personal phone number
     */
    private String userPersonalPhoneNumber;

    /**
     * This field denotes the personal e-mail id of the user
     */
    private String userPersonalEmailAddress;

    /**
     * This field denotes the personal fax number
     */
    private String userPersonalFaxNumber;

    /**
     * This field denotes the personal mobile number
     */
    private String userPersonalMobileNumber;

    /**
     * This field denotes the personal zip code
     */
    private String userPersonalZipCode;

    /**
     * This field denotes the Official address details of the user
     */
    private String userOfficialAddress;

    /**
     * This field denotes the Official phone number
     */
    private String userOfficialPhoneNumber;

    /**
     * This field denotes the official e-mail id of the user
     */
    private String userOfficialEmailAddress;

    /**
     * This field denotes the Official fax number
     */
    private String userOfficialFaxNumber;

    /**
     * This field denotes the Official mobile number
     */
    private String userOfficialMobileNumber;

    /**
     * This field denotes the Official zip code
     */
    private String userOfficialZipCode;

    /**
     * This field denotes the remarks for the user.
     */
    private String userRemarks;

    /**
     * maximum number of sessions allowed for the user
     */
    private int maximumSessions;

    /**
     * This field denotes the grace logins remaining for the user after the
     * password expiry date.
     */
    private int graceLogins;

    /**
     * This field denotes the date at which password expires
     */
    private Calendar paswordExpiryDate;

    /**
     * This field indicates whether the user account is disabled or not
     */
    private String isAccountDisabled;

    /**
     * The field denotes the date from which user account is disabled
     */
    private Calendar accountDisableStartDate;

    /**
     * The field denotes the date till which the user account is disabled
     */
    private Calendar accountDisableEndDate;

    /**
     * The field denotes the reason for disabling the user account
     */
    private String accountDisablingRemarks;

    /**
     * This field denotes the defaut role group of the user
     */
    private String userDefaultRoleGroup;

    /**
     * This field denotes the defaut station of the user
     */
    private String userDefaultStation;

    /**
     * This field denotes the defaut office of the user
     */
    private String userDefaultOffice;

    /**
     * This field denotes the defaut airport of the user
     */
    private String userDefaultAirport;

    /**
     * Allowable offices for the user
     */
    private Set<UserAllowableOffices> userAllowableOffices;

    /**
     * Allowable stations for the user
     */
    private Set<UserAllowableStations> userAllowableStations;

    /**
     * The user who updates the message last
     */
    private String lastUpdateUser;

    /**
     * The time of last updation
     */
    private Calendar lastUpdateTime;

    /*
     * the user's password
     */
    private String userPassword;

    /**
     *
     */
    private String jobTitle;

    /**
     *
     */
    private String userDefaultWarehouse;

    /**
     *
     */
    private String defaultAuthority;

    //Added by Ratheesh(deflan,defusrtyp)

    /**
     *
     */
    private String defaultLanguage;
    /**
     *
     */
    private String defaultUserType;


    /**
     *
     */
    private String disabledUserId;

    /**
     *
     */
    private String startupScreenId;

    /**
     *
     */
    private Calendar creationDate;

    /**
     *
     */
    private String isAlertMessageEnabled;

    /**
     * This field denotes the grace logins remaining for the user
     */
    private int remGraceLogins;

    /**
     * This field denotes the grace logins remaining for the user
     */
    private int remSessions;

    private String hasToChangePassword;

    //Added by Ratheesh

    private String employeeId;

    //private String companyName;

    private String departmentName;

    private Calendar lastPasswordUpdatedTime;

    // Added By Kiran on 09-Dec-2009

    private int loginFailureCount;


    private Set<UserParameter> userParameters;


    private Calendar passwordRequestedTime;

    private String passwordToken;

    //private Blob compositeDataOne;

    private byte[] compositeDataTwo;


    /*@Lob()
    @Column(name = "CMPDTAONE", columnDefinition = "BYTEA")
    @Basic(fetch = FetchType.LAZY)
    public Blob getCompositeDataOne() {
        return compositeDataOne;
    }

    public void setCompositeDataOne(Blob profileImage) {
        this.compositeDataOne = profileImage;
    }
*/
    //@Lob()
    @Column(name = "CMPDTATWO", columnDefinition = "BYTEA")
    //@Type(type = "org.hibernate.type.BinaryType")
    public byte[] getCompositeDataTwo() {
        return compositeDataTwo;
    }

    public void setCompositeDataTwo(byte[] identityScan) {
        this.compositeDataTwo = identityScan;
    }

    @Column(name = "LOGFALCNT")
    public int getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(int loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    // Added By Kiran on 09-Dec-2009

    /**
     * @return String
     */
    @Column(name = "LSTPWDUPDTIM")
    public Calendar getLastPasswordUpdatedTime() {
        return lastPasswordUpdatedTime;
    }

    /**
     * @param lastPasswordUpdatedTime
     */
    public void setLastPasswordUpdatedTime(Calendar lastPasswordUpdatedTime) {
        this.lastPasswordUpdatedTime = lastPasswordUpdatedTime;
    }

    /**
     *
     */
    @Column(name = "PWDCHGREQFLG")
    public String getHasToChangePassword() {
        return hasToChangePassword;
    }

    /**
     * @param hasToChangePassword
     */
    public void setHasToChangePassword(String hasToChangePassword) {
        this.hasToChangePassword = hasToChangePassword;
    }

    /**
     * @return
     */
    @Column(name = "REMGRCLOG")
    public int getRemGraceLogins() {
        return remGraceLogins;
    }

    /**
     * @param remGraceLogins
     */
    public void setRemGraceLogins(int remGraceLogins) {
        this.remGraceLogins = remGraceLogins;
    }

    /**
     * @return
     */
    @Column(name = "REMNUMSES")
    public int getRemSessions() {
        return remSessions;
    }

    /**
     * @param remSessions
     */
    public void setRemSessions(int remSessions) {
        this.remSessions = remSessions;
    }

    /**
     * @return
     */
    @Column(name = "DISUSRCOD")
    public String getDisabledUserId() {
        return disabledUserId;
    }

    public void setDisabledUserId(String disabledUserId) {
        this.disabledUserId = disabledUserId;
    }

    /**
     * @return
     */
    @Column(name = "DEFAUT")
    public String getDefaultAuthority() {
        return defaultAuthority;
    }

    public void setDefaultAuthority(String defaultAuthority) {
        this.defaultAuthority = defaultAuthority;
    }

    /**
     * @return
     */
    @Column(name = "MSGALTFLG")
    public String getIsAlertMessageEnabled() {
        return isAlertMessageEnabled;
    }

    public void setIsAlertMessageEnabled(String isAlertMessageEnabled) {
        this.isAlertMessageEnabled = isAlertMessageEnabled;
    }

    /**
     * @return
     */
    @Column(name = "JOBTIL")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return
     */
    @Column(name = "STRSCRIDR")
    public String getStartupScreenId() {
        return startupScreenId;
    }

    public void setStartupScreenId(String startupScreenId) {
        this.startupScreenId = startupScreenId;
    }

    /**
     * @return
     */
    @Column(name = "DEFWHS")
    public String getUserDefaultWarehouse() {
        return userDefaultWarehouse;
    }

    public void setUserDefaultWarehouse(String userDefaultWarehouse) {
        this.userDefaultWarehouse = userDefaultWarehouse;
    }

    /**
     * @return Returns the creation date.
     */
    @Column(name = "CRTDAT")
    @Temporal(TemporalType.DATE)
    public Calendar getCreationDate() {
        return creationDate;
    }

    /**
     *
     */
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return Returns the accountDisableEndDate.
     */
    @Column(name = "ACCENDDAT")
    @Temporal(TemporalType.DATE)
    public Calendar getAccountDisableEndDate() {
        return accountDisableEndDate;
    }

    /**
     * @param accountDisableEndDate The accountDisableEndDate to set.
     */
    public void setAccountDisableEndDate(Calendar accountDisableEndDate) {
        this.accountDisableEndDate = accountDisableEndDate;
    }

    /**
     * @return Returns the accountDisableStartDate.
     */
    @Column(name = "ACCFRMDAT")
    @Temporal(TemporalType.DATE)
    public Calendar getAccountDisableStartDate() {
        return accountDisableStartDate;
    }

    /**
     * @param accountDisableStartDate The accountDisableStartDate to set.
     */
    public void setAccountDisableStartDate(Calendar accountDisableStartDate) {
        this.accountDisableStartDate = accountDisableStartDate;
    }

    /**
     * @return Returns the accountDisablingRemarks.
     */
    @Column(name = "ACCDISRMK")
    public String getAccountDisablingRemarks() {
        return accountDisablingRemarks;
    }

    /**
     * @param accountDisablingRemarks The accountDisablingRemarks to set.
     */
    public void setAccountDisablingRemarks(String accountDisablingRemarks) {
        this.accountDisablingRemarks = accountDisablingRemarks;
    }

    /**
     * @return Returns the isAccountDisabled.
     */
    @Column(name = "ACCDISFLG")
    public String getIsAccountDisabled() {
        return isAccountDisabled;
    }

    /**
     * @param isAccountDisabled The isAccountDisabled to set.
     */
    public void setIsAccountDisabled(String isAccountDisabled) {
        this.isAccountDisabled = isAccountDisabled;
    }

    /**
     * @return Returns the maximumSessions.
     */
    @Column(name = "NUMSES")
    public int getMaximumSessions() {
        return maximumSessions;
    }

    /**
     * @param maximumSessions The maximumSessions to set.
     */
    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    /**
     * @return Returns the paswordExpiryDate.
     */
    @Column(name = "PWDEXPDAT")
    @Temporal(TemporalType.DATE)
    public Calendar getPaswordExpiryDate() {
        return paswordExpiryDate;
    }

    /**
     * @param paswordExpiryDate The paswordExpiryDate to set.
     */
    public void setPaswordExpiryDate(Calendar paswordExpiryDate) {
        this.paswordExpiryDate = paswordExpiryDate;
    }

    /**
     * @return Returns the remainingGraceLogins.
     */
    @Column(name = "NUMGRCLOG")
    public int getGraceLogins() {
        return graceLogins;
    }

    /**
     * The remainingGraceLogins to set.
     */
    public void setGraceLogins(int graceLogins) {
        this.graceLogins = graceLogins;
    }

    /**
     * @return Returns the userAllowableOffices.
     */
    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
            @JoinColumn(name = "USRCOD", referencedColumnName = "USRCOD", insertable = false, updatable = false)})
    public Set<UserAllowableOffices> getUserAllowableOffices() {
        return userAllowableOffices;
    }

    /**
     * @param userAllowableOffices The userAllowableOffices to set.
     */
    public void setUserAllowableOffices(
            Set<UserAllowableOffices> userAllowableOffices) {
        this.userAllowableOffices = userAllowableOffices;
    }

    /**
     * @return Returns the userAllowableStations.
     */
    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
            @JoinColumn(name = "USRCOD", referencedColumnName = "USRCOD", insertable = false, updatable = false)})
    public Set<UserAllowableStations> getUserAllowableStations() {
        return userAllowableStations;
    }

    /**
     * @param userAllowableStations The userAllowableStations to set.
     */
    public void setUserAllowableStations(
            Set<UserAllowableStations> userAllowableStations) {
        this.userAllowableStations = userAllowableStations;
    }

    /**
     * @return Returns the userDefaultAirport.
     */
    @Column(name = "DEFARP")
    public String getUserDefaultAirport() {
        return userDefaultAirport;
    }

    /**
     * @param userDefaultAirport The userDefaultAirport to set.
     */
    public void setUserDefaultAirport(String userDefaultAirport) {
        this.userDefaultAirport = userDefaultAirport;
    }

    /**
     * @return Returns the userDefaultOffice.
     */
    @Column(name = "DEFOFF")
    public String getUserDefaultOffice() {
        return userDefaultOffice;
    }

    /**
     * @param userDefaultOffice The userDefaultOffice to set.
     */
    public void setUserDefaultOffice(String userDefaultOffice) {
        this.userDefaultOffice = userDefaultOffice;
    }

    /**
     * @return Returns the userDefaultRoleGroup.
     */
    @Column(name = "DEFROLGRP")
    public String getUserDefaultRoleGroup() {
        return userDefaultRoleGroup;
    }

    /**
     * @param userDefaultRoleGroup The userDefaultRoleGroup to set.
     */
    public void setUserDefaultRoleGroup(String userDefaultRoleGroup) {
        this.userDefaultRoleGroup = userDefaultRoleGroup;
    }

    /**
     * @return Returns the userDefaultStation.
     */
    @Column(name = "DEFSTN")
    public String getUserDefaultStation() {
        return userDefaultStation;
    }

    /**
     * @param userDefaultStation The userDefaultStation to set.
     */
    public void setUserDefaultStation(String userDefaultStation) {
        this.userDefaultStation = userDefaultStation;
    }

    /**
     * @return Returns the userFirstName.
     */
    @Column(name = "FSTNAM")
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * @param userFirstName The userFirstName to set.
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * @return Returns the userLastName.
     */
    @Column(name = "LSTNAM")
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * @param userLastName The userLastName to set.
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * @return Returns the userMiddleName.
     */
    @Column(name = "MDLNAM")
    public String getUserMiddleName() {
        return userMiddleName;
    }

    /**
     * @param userMiddleName The userMiddleName to set.
     */
    public void setUserMiddleName(String userMiddleName) {
        this.userMiddleName = userMiddleName;
    }

    /**
     * @return Returns the userOfficialAddress.
     */
    @Column(name = "OFFADD")
    public String getUserOfficialAddress() {
        return userOfficialAddress;
    }

    /**
     * @param userOfficialAddress The userOfficialAddress to set.
     */
    public void setUserOfficialAddress(String userOfficialAddress) {
        this.userOfficialAddress = userOfficialAddress;
    }

    /**
     * @return Returns the userOfficialEmailAddress.
     */
    @Column(name = "OFFEML")
    public String getUserOfficialEmailAddress() {
        return userOfficialEmailAddress;
    }

    /**
     * @param userOfficialEmailAddress The userOfficialEmailAddress to set.
     */
    public void setUserOfficialEmailAddress(String userOfficialEmailAddress) {
        this.userOfficialEmailAddress = userOfficialEmailAddress;
    }

    /**
     * @return Returns the userOfficialFaxNumber.
     */
    @Column(name = "OFFFAX")
    public String getUserOfficialFaxNumber() {
        return userOfficialFaxNumber;
    }

    /**
     * @param userOfficialFaxNumber The userOfficialFaxNumber to set.
     */
    public void setUserOfficialFaxNumber(String userOfficialFaxNumber) {
        this.userOfficialFaxNumber = userOfficialFaxNumber;
    }

    /**
     * @return Returns the userOfficialMobileNumber.
     */
    @Column(name = "OFFMOBNUM")
    public String getUserOfficialMobileNumber() {
        return userOfficialMobileNumber;
    }

    /**
     * @param userOfficialMobileNumber The userOfficialMobileNumber to set.
     */
    public void setUserOfficialMobileNumber(String userOfficialMobileNumber) {
        this.userOfficialMobileNumber = userOfficialMobileNumber;
    }

    /**
     * @return Returns the userOfficialPhoneNumber.
     */
    @Column(name = "OFFPHN")
    public String getUserOfficialPhoneNumber() {
        return userOfficialPhoneNumber;
    }

    /**
     * @param userOfficialPhoneNumber The userOfficialPhoneNumber to set.
     */
    public void setUserOfficialPhoneNumber(String userOfficialPhoneNumber) {
        this.userOfficialPhoneNumber = userOfficialPhoneNumber;
    }

    /**
     * @return Returns the userOfficialZipCode.
     */
    @Column(name = "OFFZIPCOD")
    public String getUserOfficialZipCode() {
        return userOfficialZipCode;
    }

    /**
     * @param userOfficialZipCode The userOfficialZipCode to set.
     */
    public void setUserOfficialZipCode(String userOfficialZipCode) {
        this.userOfficialZipCode = userOfficialZipCode;
    }

    /**
     * @return Returns the userPersonalAddress.
     */
    @Column(name = "PERADD")
    public String getUserPersonalAddress() {
        return userPersonalAddress;
    }

    /**
     * @param userPersonalAddress The userPersonalAddress to set.
     */
    public void setUserPersonalAddress(String userPersonalAddress) {
        this.userPersonalAddress = userPersonalAddress;
    }

    /**
     * @return Returns the userPersonalEmailAddress.
     */
    @Column(name = "PEREML")
    public String getUserPersonalEmailAddress() {
        return userPersonalEmailAddress;
    }

    /**
     * @param userPersonalEmailAddress The userPersonalEmailAddress to set.
     */
    public void setUserPersonalEmailAddress(String userPersonalEmailAddress) {
        this.userPersonalEmailAddress = userPersonalEmailAddress;
    }

    /**
     * @return Returns the userPersonalFaxNumber.
     */
    @Column(name = "PERFAX")
    public String getUserPersonalFaxNumber() {
        return userPersonalFaxNumber;
    }

    /**
     * @param userPersonalFaxNumber The userPersonalFaxNumber to set.
     */
    public void setUserPersonalFaxNumber(String userPersonalFaxNumber) {
        this.userPersonalFaxNumber = userPersonalFaxNumber;
    }

    /**
     * @return Returns the userPersonalMobileNumber.
     */
    @Column(name = "PERMOBNUM")
    public String getUserPersonalMobileNumber() {
        return userPersonalMobileNumber;
    }

    /**
     * @param userPersonalMobileNumber The userPersonalMobileNumber to set.
     */
    public void setUserPersonalMobileNumber(String userPersonalMobileNumber) {
        this.userPersonalMobileNumber = userPersonalMobileNumber;
    }

    /**
     * @return Returns the userPersonalPhoneNumber.
     */
    @Column(name = "PERPHN")
    public String getUserPersonalPhoneNumber() {
        return userPersonalPhoneNumber;
    }

    /**
     * @param userPersonalPhoneNumber The userPersonalPhoneNumber to set.
     */
    public void setUserPersonalPhoneNumber(String userPersonalPhoneNumber) {
        this.userPersonalPhoneNumber = userPersonalPhoneNumber;
    }

    /**
     * @return Returns the userPersonalZipCode.
     */
    @Column(name = "PERZIPCOD")
    public String getUserPersonalZipCode() {
        return userPersonalZipCode;
    }

    /**
     * @param userPersonalZipCode The userPersonalZipCode to set.
     */
    public void setUserPersonalZipCode(String userPersonalZipCode) {
        this.userPersonalZipCode = userPersonalZipCode;
    }

    /**
     * @return Returns the userRemarks.
     */
    @Column(name = "USRRMK")
    public String getUserRemarks() {
        return userRemarks;
    }

    /**
     * @param userRemarks The userRemarks to set.
     */
    public void setUserRemarks(String userRemarks) {
        this.userRemarks = userRemarks;
    }

    /**
     * @return Returns the userPK.
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
            @AttributeOverride(name = "userCode", column = @Column(name = "USRCOD"))})
    public UserPK getUserPK() {
        return userPK;
    }

    /**
     * @param userPK The userPK to set.
     */
    public void setUserPK(UserPK userPK) {
        this.userPK = userPK;
    }

    /**
     * @return Returns the lastUpdateTime.
     */
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LSTUPDTIM")
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return Returns the lastUpdateUser.
     */
    @Column(name = "LSTUPDUSR")
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return Returns the userPassword.
     */
    @Column(name = "USRPWD")
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword The userPassword to set.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    //private String customerCompanyCode;

    private String customerCode;

    private String deliveryRequestEnabled;

    //A-7764 for ICRD-232218
    private String salesRep;

    @Column(name = "SALREPFLG")
    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    //private String inchargeName;
// Added By A-7604 for ICRD-252060
    @Column(name = "CUSCOD")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
	
	/*@Column(name = "CUSCMPCOD")
	public String getCustomerCompanyCode() {
		return customerCompanyCode;
	}
	public void setCustomerCompanyCode(String customerCompanyCode) {
		this.customerCompanyCode = customerCompanyCode;
	}*/

    @Column(name = "DLVREQFLG")
    public String getDeliveryRequestEnabled() {
        return deliveryRequestEnabled;
    }

    public void setDeliveryRequestEnabled(String deliveryRequestEnabled) {
        this.deliveryRequestEnabled = deliveryRequestEnabled;
    }
	
	/*@Column(name = "INCHGNAM")
	public String getInchargeName() {
		return inchargeName;
	}
	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}*/

    /**
     * @return the userParameters
     */
    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
            @JoinColumn(name = "USRCOD", referencedColumnName = "USRCOD", insertable = false, updatable = false)})
    public Set<UserParameter> getUserParameters() {
        return userParameters;
    }

    /**
     * @param userParameters the userParameters to set
     */
    public void setUserParameters(Set<UserParameter> userParameters) {
        this.userParameters = userParameters;
    }

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Overloaded Constructor
     *
     * @param userVO
     * @throws CreateException
     * @throws SystemException
     */
    public User(UserVO userVO, UserCompositeData compositeData) throws CreateException, SystemException, RemoveException {
        LOG.entering(CLASS_NAME, " Constructor ");
        populatePK(userVO);
        populateAttributes(userVO, true);
        // explicit convert to blob
        Blob blob = PersistenceUtils.createBlob(compositeData);
        logger.info("Blob Created {}", blob);
        //setCompositeDataOne(blob);
        // implicit to blob
        byte[] bytes = PersistenceUtils.toBytes(compositeData);
        setCompositeDataTwo(bytes);

        PersistenceController.getEntityManager().persist(this);
        populateUserAllowableStations(userVO);
        populateUserAllowableOffices(userVO);
        populateUserParameters(userVO);
        if (userVO.getPortalDetails() != null) {
            populatePortalDetails(userVO);
        }
        LOG.exiting(CLASS_NAME, " Constructor ");

    }

    /**
     * populate the primary keys
     *
     * @param userVO
     */
    private void populatePK(UserVO userVO) {
        LOG.entering(CLASS_NAME, " populatePK ");
        UserPK userPk = new UserPK();
        userPk.setCompanyCode(userVO.getCompanyCode());
        userPk.setUserCode(userVO.getUserCode());
        this.setUserPK(userPk);
        LOG.exiting(CLASS_NAME, " populatePK ");
    }

    /**
     * @param userVO
     * @throws SystemException
     * @throws CreateException
     */
    private void populateUserParameters(UserVO userVO) throws CreateException, SystemException {
        UserParameter parameter = null;
        if (userVO.getUserParameterVOs() != null && !userVO.getUserParameterVOs().isEmpty()) {
            for (UserParameterVO userParameterVO : userVO.getUserParameterVOs()) {
                if (userParameterVO.getParameterValue() != null &&
                        userParameterVO.getParameterValue().trim().length() > 0) {
                    parameter = new UserParameter(userVO.getUserCode(), userParameterVO);
                    if (getUserParameters() == null) {
                        setUserParameters(new HashSet<UserParameter>());
                    }
                    getUserParameters().add(parameter);
                }
            }
        }
    }

    /**
     * Used for updating the UserFavourite.
     *
     * @param userVO
     * @throws CreateException
     * @throws RemoveException
     * @throws SystemException
     */
    private void updateUserParameters(UserVO userVO) throws CreateException, RemoveException, SystemException {
        if (UserVO.SOURCE_WEB.equals(userVO.getSource())) {
            if (userVO.getUserParameterVOs() != null) {
                // the collection of integers for removing the UserParameter  from the collection also using index
                List<Integer> indexColl = new ArrayList<Integer>();
                List<UserParameter> parColl = new ArrayList<UserParameter>();
                for (UserParameterVO userParameterVO : userVO.getUserParameterVOs()) {
                    if (UserParameterVO.OPERATION_FLAG_INSERT.equals(userParameterVO.getOperationFlag())) {
                        UserParameter userParameter = new UserParameter(userVO.getUserCode(), userParameterVO);
                        if (getUserParameters() == null) {
                            setUserParameters(new HashSet<UserParameter>());
                        }
                        getUserParameters().add(userParameter);
                    }
                    if (UserParameterVO.OPERATION_FLAG_UPDATE.equals(userParameterVO.getOperationFlag())) {
                        if (getUserParameters() != null) {
                            boolean isFound = false;
                            for (UserParameter userParameter : getUserParameters()) {
                                if (userParameter.getUserParameterPK().getParameterCode().equals(userParameterVO.getParameterCode())) {
                                    userParameter.setParamterValue(userParameterVO.getParameterValue());
                                    userParameter.setLastUpdateUser(userParameterVO.getLastUpdateUser());
                                    isFound = true;
                                    break;
                                }
                            }
                            if (!isFound && userParameterVO.getParameterValue() != null
                                    && userParameterVO.getParameterValue().trim().length() > 0) {
                                UserParameter userParameter = new UserParameter(userVO.getUserCode(), userParameterVO);
                                if (getUserParameters() == null) {
                                    setUserParameters(new HashSet<UserParameter>());
                                }
                                getUserParameters().add(userParameter);
                            }
                        }
                    } else if (UserParameterVO.OPERATION_FLAG_DELETE.equals(userParameterVO.getOperationFlag())) {
                        if (userPK != null) {
                            int index = 0;
                            for (UserParameter userParameter : getUserParameters()) {
                                UserParameterPK userParameterPK = userParameter.getUserParameterPK();
                                if (userPK.getCompanyCode().equals(userParameterPK.getCompanyCode())
                                        && userPK.getUserCode().equals(userParameterPK.getUserCode())
                                        && userParameterVO.getParameterCode().equals(userParameterPK.getParameterCode())) {
                                    userParameter.remove(); // for removing from tables
                                    indexColl.add(index); // for avoiding con-currenct Exception and to remove from collection
                                    parColl.add(userParameter);
                                }
                                index++;
                            }
                        }
                    }
                }
                // removing the userParameter from the collection also
                if (parColl != null) {
                    for (UserParameter userParameter : parColl) {
                        getUserParameters().remove(userParameter);
                    }
                }
                LOG.exiting(CLASS_NAME, " updateUserFavourite  -> for Updation");
            }
            //System.out.println("par"+getUserParameters().size());
        } else {// Web service call: Delete Insert the data
            try {
                if (this.userParameters != null
                        && !this.userParameters.isEmpty()) {
                    for (UserParameter userParameter : userParameters) {
                        PersistenceController.getEntityManager().remove(
                                userParameter);
                    }
                }

            } catch (RemoveException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            } catch (OptimisticConcurrencyException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            }
            populateUserParameters(userVO);
        }
    }


    /**
     * @throws RemoveException
     * @throws SystemException
     */
    private void removeUserParameters() throws RemoveException, SystemException {
        LOG.entering(CLASS_NAME, " removeUserParameters ");
        if (getUserParameters() != null) {
            for (UserParameter userParameter : getUserParameters()) {
                userParameter.remove();
            }
        }
        LOG.exiting(CLASS_NAME, " removeUserParameters ");
    }

    /**
     * @param unlockingRemarks
     */
    private void unlockUserAccount(String unlockingRemarks) {
        LOG.entering(CLASS_NAME, " unlockUserAccount ");
        LOG.log(Log.INFO, " Unlocked User Account for ", this.getUserPK().getUserCode());
        setIsAccountDisabled(AbstractVO.FLAG_NO);
        setAccountDisablingRemarks(unlockingRemarks);
        setLoginFailureCount(Integer.valueOf(0));
        //}
        LOG.exiting(CLASS_NAME, " unlockUserAccount ");
    }

    /**
     * @param lockingRemarks
     */
    private void lockUserAccount(String lockingRemarks) {

        if (lockingRemarks != null && lockingRemarks.trim().length() > 0) {
            LOG.log(Log.INFO, " Locking the Account for userCode ", this.getUserPK().getUserCode());
            setIsAccountDisabled(AbstractVO.FLAG_YES);
            setAccountDisablingRemarks(lockingRemarks);
        }

    }

    /**
     * @param isCreate
     * @param newStatus
     * @param remarks
     */
    private void updateUserAccount(boolean isCreate, String newStatus, String remarks) {
        //String statusToUpdate =  newStatus != null ? ( newStatus.equals(getIsAccountDisabled()) ? null : newStatus ) : null ;
		/*String statusToUpdate = null;
		if(newStatus != null && newStatus.equals(getIsAccountDisabled())) {
				statusToUpdate = null ;
		}else {
				statusToUpdate = newStatus ;
		}*/
        LOG.entering(CLASS_NAME, " updateUserAccount ");
        String statusToUpdate = (newStatus != null && newStatus.equals(getIsAccountDisabled())) ? null : newStatus;

        if (isCreate) {
            // Added By Kiran To fix a regression issue
            //setIsAccountDisabled(AbstractVO.FLAG_NO);
            setIsAccountDisabled(newStatus != null ? newStatus : AbstractVO.FLAG_NO);

        } else if (AbstractVO.FLAG_YES.equals(statusToUpdate)) {
            lockUserAccount(remarks);
        } else if (AbstractVO.FLAG_NO.equals(statusToUpdate)) {
            unlockUserAccount(remarks);
        }
        LOG.exiting(CLASS_NAME, " updateUserAccount ");
    }


    /**
     * populates the attributes
     *
     * @param userVO
     */
    private void populateAttributes(UserVO userVO, boolean isCreate) throws RemoveException,
            SystemException, CreateException {
        LOG.entering(CLASS_NAME, " populateAttributes");

        setHasToChangePassword(userVO.getHasToChangePassword());
        setAccountDisableEndDate(userVO.getAccountDisableEndDate());
        setAccountDisableStartDate(userVO.getAccountDisableStartDate());

        //Added by Kiran on 09-Dec-2009
        //setAccountDisablingRemarks(userVO.getAccountDisablingRemarks());
        //setIsAccountDisabled(userVO.getIsAccountDisabled());
        updateUserAccount(isCreate, userVO.getIsAccountDisabled(), userVO.getAccountDisablingRemarks());
        //	Added by Kiran on 09-Dec-2009 ends

        setLastUpdateUser(userVO.getLastUpdateUser());

        if (getGraceLogins() != userVO.getRemainingGraceLogins()) {
            LOG.log(Log.FINE, " getGraceLogins()>>>>>>>>>", getGraceLogins());
            setRemGraceLogins(userVO.getRemainingGraceLogins());
        }
        if (getMaximumSessions() != userVO.getMaximumSessions()) {
            LOG.log(Log.FINE, " getMaximumSessions()>>>>>>",
                    getMaximumSessions());
            setRemSessions(userVO.getMaximumSessions());
        }

        setMaximumSessions(userVO.getMaximumSessions());
        setGraceLogins(userVO.getRemainingGraceLogins());
        setPaswordExpiryDate(userVO.getPaswordExpiryDate());

        setUserDefaultAirport(userVO.getUserDefaultAirport());
        setUserDefaultOffice(userVO.getUserDefaultOffice());
        setUserDefaultRoleGroup(userVO.getUserDefaultRoleGroup());
        setUserDefaultStation(userVO.getUserDefaultStation());

        setUserFirstName(userVO.getUserFirstName());
        setUserLastName(userVO.getUserLastName());
        setUserMiddleName(userVO.getUserMiddleName());

        //Added by Ratheesh
        setEmployeeId(userVO.getEmployeeId());
        setDepartmentName(userVO.getDepartmentName());
        //setCompanyName(userVO.getCompanyName());

        //Added by Ratheesh
        setEmployeeId(userVO.getEmployeeId());
        setDepartmentName(userVO.getDepartmentName());
        //setCompanyName(userVO.getCompanyName());


        setUserOfficialEmailAddress(encryptData(userVO.getUserOfficialEmailAddress()));
        setUserOfficialFaxNumber(encryptData(userVO.getUserOfficialFaxNumber()));
        setUserOfficialMobileNumber(encryptData(userVO.getUserOfficialMobileNumber()));
        setUserOfficialPhoneNumber(encryptData(userVO.getUserOfficialPhoneNumber()));
        setUserPersonalEmailAddress(encryptData(userVO.getUserPersonalEmailAddress()));
        setUserPersonalMobileNumber(encryptData(userVO.getUserPersonalMobileNumber()));
        setUserPersonalPhoneNumber(encryptData(userVO.getUserPersonalPhoneNumber()));
        setUserPersonalFaxNumber(encryptData(userVO.getUserPersonalFaxNumber()));

        setUserOfficialAddress(userVO.getUserOfficialAddress());
        setUserOfficialZipCode(userVO.getUserOfficialZipCode());

        setUserPersonalAddress(userVO.getUserPersonalAddress());
        setUserPersonalZipCode(userVO.getUserPersonalZipCode());

        setUserRemarks(userVO.getUserRemarks());
        if (userVO.getUserPassword() != null) {
            setUserPassword(userVO.getUserPassword());
        }

        // New Fields
        setDefaultAuthority(userVO.getDefaultAuthority());
        setDisabledUserId(userVO.getDisabledUserId());
        setUserDefaultWarehouse(userVO.getUserDefaultWarehouse());
        setIsAlertMessageEnabled(userVO.getIsAlertMessageEnabled());
        setJobTitle(userVO.getJobTitle());
        setStartupScreenId(userVO.getStartupScreenId());
        setCreationDate(userVO.getCreatedOn());

        setLastUpdateTime(userVO.getLastUpdateTime());
        setDefaultLanguage(userVO.getDefaultLanguage());
        setDefaultUserType(userVO.getDefaultUserType());

        //setInchargeName(userVO.getInchargeName());
        // Added By A-7604 for ICRD-252060
        setCustomerCode(userVO.getCustomerCode());
        //setCustomerCompanyCode(userVO.getCustomerCompanyCode());
        setDeliveryRequestEnabled(userVO.getDeliveryRequestEnabled());

        setSalesRep(userVO.getSalesRep());

        setCreatedByUser("JOHN_DOE");

        setUserTitle(userVO.getUserTitle());
        setUserOfficialCountry(userVO.getUserOfficeCountry());
        setUserOfficialCity(userVO.getUserOfficeCity());

        LOG.exiting(CLASS_NAME, " populateAttributes");
    }

    private String encryptData(String value) {
        return value;
    }

    /**
     * @param userVO
     * @throws SystemException
     * @throws CreateException
     */
    private void updatePortalDetails(UserVO userVO)
            throws SystemException, CreateException, RemoveException {
        PortalUserDetailsVO portalDetails = userVO.getPortalDetails();
        if (portalDetails == null) {
            portalDetails = new PortalUserDetailsVO();
        }
        if (!UserVO.SOURCE_WEB.equals(userVO.getSource())) {
            // Set the portal details only if the call is not from icargo web screen :ADM007
            // (i.e from WS call only). Otherwise the data saved from portal
            // would be lost if we update the same user from ADM007.
            setUserTitle(userVO.getUserTitle());
            setUserOfficialCountry(userVO.getUserOfficeCountry());
            setUserOfficialCity(userVO.getUserOfficeCity());

            setPortalUserType(portalDetails.getPortalUserType());
            setMaxNumberOfChildUsers(portalDetails.getMaxNumberOfChildUsers());
            setPortalDefaultRoleGroupCode(portalDetails.getPortalDefaultRoleGroupCode());
            /**
             * CRQ ID:ICRD-162691 - A-5127 added to set the attributes useStandardEmailFlag, notificationLanguage,
             * notificationFormat and showUnsecuredInfoMessageFlag from VO to entity
             */
            setUseStandardEmailFlag(portalDetails.getUseStandardEmailFlag());
            setNotificationLanguage(portalDetails.getNotificationLanguage());
            setNotificationFormat(portalDetails.getNotificationFormat());
            setShowUnsecuredInfoMessageFlag(portalDetails.getShowUnsecuredInfoMessageFlag());
        } else {// save only the PortalUserType from web
            setPortalUserType(portalDetails.getPortalUserType());
            List<String> customersFromDB = null;
            if (userVO.getCustomerCode() != null && userVO.getCustomerCode().length() > 0) {
                List<String> customersFromScreen = Arrays.asList(userVO.getCustomerCode().split(","));
                if (!(customersFromDB != null && customersFromScreen != null &&
                        customersFromScreen.size() == customersFromDB.size() &&
                        customersFromScreen.containsAll(customersFromDB))) {
                    for (String customer : customersFromScreen) {
                        PortalUserMappingVO mappingVo = new PortalUserMappingVO();
                        mappingVo.setMappingCode(customer);
                        mappingVo.setMappingType("C");
                    }
                }
            } else if (customersFromDB != null && customersFromDB.size() > 0) {
            }

        }
    }


    /**
     * used to populate the allowable stations of the user
     *
     * @param userVO
     * @throws SystemException
     * @throws CreateException
     */
    private void populateUserAllowableStations(UserVO userVO)
            throws SystemException, CreateException {
        LOG.entering(CLASS_NAME, " populateUserAllowableStations ");
        Collection<UserRoleGroupDetailsVO> userRoleGroups = new ArrayList<UserRoleGroupDetailsVO>();
        UserAllowableStations allowableStations = null;
        List<UserAllowableStationsVO> allowedStationVoList = new ArrayList<UserAllowableStationsVO>();
        if (userVO.getUserAllowableStationsVOPage() != null) {
            LOG.log(Log.INFO,
                    "userVO.getUserAllowableStationsVOPage()**********", userVO.getUserAllowableStationsVOPage());
            allowedStationVoList.addAll(userVO.getUserAllowableStationsVOPage());
        } else if (userVO.getUserAllowableStationsVO() != null) {
            // Added for handling service calls
            allowedStationVoList.addAll(userVO.getUserAllowableStationsVO());
        }

        for (UserAllowableStationsVO userAllowableStationsVO : allowedStationVoList) {
            userAllowableStationsVO.setUserCode(userVO.getUserCode());
            LOG.log(Log.INFO, "userAllowableStationsVO**********",
                    userAllowableStationsVO);
            allowableStations = new UserAllowableStations(
                    userAllowableStationsVO);
            if (getUserAllowableStations() == null) {
                setUserAllowableStations(new HashSet<UserAllowableStations>());
            }
            getUserAllowableStations().add(allowableStations);
            // here the Role Groups are from additional role Groups maintained in User allowed Station
            Collection<String> additionalRoleGroups = userAllowableStationsVO.getAdditionalRoleGroups();
            if (additionalRoleGroups != null &&
                    additionalRoleGroups.size() > 0) {
                for (String roleGroupCode : additionalRoleGroups) {
                    UserRoleGroupDetailsVO userRoleGrpDetailsVO = new UserRoleGroupDetailsVO();
                    userRoleGrpDetailsVO.setCompanyCode(userAllowableStationsVO.getCompanyCode());
                    userRoleGrpDetailsVO.setUserCode(userAllowableStationsVO.getUserCode());
                    userRoleGrpDetailsVO.setStationCode(userAllowableStationsVO.getStationCode());
                    userRoleGrpDetailsVO.setRoleGroupCode(roleGroupCode);
                    userRoleGroups.add(userRoleGrpDetailsVO);
                }
            }

        }
        LOG.exiting(CLASS_NAME, " populateUserAllowableStations ");

    }


    /**
     * used to populate the allowable offices of the user
     *
     * @param userVO
     * @throws CreateException
     * @throws SystemException
     */
    private void populateUserAllowableOffices(UserVO userVO)
            throws SystemException, CreateException {

        LOG.entering(CLASS_NAME, " populateUserAllowableOffices ");

        UserAllowableOffices allowableOffices = null;
        if (userVO.getUserAllowableOfficesVO() != null) {
            for (UserAllowableOfficesVO userAllowableOfficesVO : userVO
                    .getUserAllowableOfficesVO()) {
                allowableOffices = new UserAllowableOffices(
                        userAllowableOfficesVO);
                if (getUserAllowableOffices() == null) {
                    setUserAllowableOffices(new HashSet<UserAllowableOffices>());
                }
                getUserAllowableOffices().add(allowableOffices);
            }
        }

        LOG.exiting(CLASS_NAME, " populateUserAllowableOffices ");
    }


    /**
     * used for finding the User object from the database based on the primary
     * key values uses the Persistence Controller find method
     *
     * @param companyCode
     * @param userCode
     * @return User
     * @throws SystemException
     * @throws FinderException
     */
    public static User find(String companyCode, String userCode)
            throws SystemException, FinderException {

        User user = null;
        UserPK userPk = new UserPK();
        userPk.setCompanyCode(companyCode);
        userPk.setUserCode(userCode);
        return PersistenceController.getEntityManager().find(User.class, userPk);
    }

    /**
     * used for setting the values already in the class to a VO and returning it
     *
     * @return
     */
    public UserVO retrieve() {
        LOG.entering(CLASS_NAME, " retrieve");
        UserVO userVO = new UserVO();
        UserPK userPk = getUserPK();
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.DATE, -1);

        userVO.setCompanyCode(userPk.getCompanyCode());
        userVO.setUserCode(userPk.getUserCode());

        userVO.setUserFirstName(getUserFirstName());
        userVO.setUserMiddleName(getUserMiddleName());
        userVO.setUserLastName(getUserLastName());

        //Added by Ratheesh
        userVO.setEmployeeId(getEmployeeId());
        userVO.setDepartmentName(getDepartmentName());
        //userVO.setCompanyName(getCompanyName());

        //Added by Ratheesh
        userVO.setEmployeeId(getEmployeeId());
        userVO.setDepartmentName(getDepartmentName());
        //userVO.setCompanyName(getCompanyName());

        userVO.setUserPersonalAddress(getUserPersonalAddress());
        userVO.setUserPersonalEmailAddress(getUserPersonalEmailAddress());
        userVO.setUserPersonalFaxNumber(getUserPersonalFaxNumber());
        userVO.setUserPersonalMobileNumber(getUserPersonalMobileNumber());
        userVO.setUserPersonalPhoneNumber(getUserPersonalPhoneNumber());
        userVO.setUserPersonalZipCode(getUserPersonalZipCode());

        userVO.setUserOfficialAddress(getUserOfficialAddress());
        userVO.setUserOfficialEmailAddress(getUserOfficialEmailAddress());
        userVO.setUserOfficialFaxNumber(getUserOfficialFaxNumber());
        userVO.setUserOfficialMobileNumber(getUserOfficialMobileNumber());
        userVO.setUserOfficialPhoneNumber(getUserOfficialPhoneNumber());
        userVO.setUserOfficialZipCode(getUserOfficialZipCode());

        userVO.setUserRemarks(getUserRemarks());

        userVO.setUserDefaultRoleGroup(getUserDefaultRoleGroup());
        userVO.setUserDefaultStation(getUserDefaultStation());
        userVO.setUserDefaultOffice(getUserDefaultOffice());
        userVO.setUserDefaultAirport(getUserDefaultAirport());

        userVO.setMaximumSessions(getMaximumSessions());
        userVO.setPaswordExpiryDate(new LocalDate(LocalDate.NO_STATION,
                Location.NONE, expiryDate, false));
        userVO.setRemainingGraceLogins(getGraceLogins());

        userVO.setIsAccountDisabled(getIsAccountDisabled());
        if (getAccountDisableStartDate() != null) {
            userVO.setAccountDisableStartDate(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE,
                    getAccountDisableStartDate(), true));
        }
        if (getAccountDisableEndDate() != null) {
            userVO.setAccountDisableEndDate(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, getAccountDisableEndDate(), true));
        }
        userVO.setAccountDisablingRemarks(getAccountDisablingRemarks());

        userVO.setLastUpdateUser(getLastUpdateUser());
        if (getLastUpdateTime() != null) {
            userVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, getLastUpdateTime(), true));
        }
        userVO.setUserPassword(getUserPassword());

        // New Fields
        userVO.setDefaultAuthority(getDefaultAuthority());
        userVO.setDisabledUserId(getDisabledUserId());
        userVO.setUserDefaultWarehouse(getUserDefaultWarehouse());
        userVO.setIsAlertMessageEnabled(getIsAlertMessageEnabled());
        userVO.setJobTitle(getJobTitle());
        userVO.setStartupScreenId(getStartupScreenId());

        if (getCreationDate() != null) {
            userVO.setCreatedOn(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, getCreationDate(), true));
        }

        LOG.exiting(CLASS_NAME, " retrieve");
        return userVO;

    }

    /**
     * used for updating the user details
     *
     * @param userVO
     * @throws CreateException
     * @throws SystemException
     * @throws RemoveException
     */
    public void update(UserVO userVO) throws SystemException, CreateException,
            RemoveException {
        LOG.entering(CLASS_NAME, " update");
        populateAttributes(userVO, false);
        updateUserAllowableStations(userVO);
        updateUserAllowableOffices(userVO);
        updateUserParameters(userVO);
        LOG.exiting(CLASS_NAME, " update");
    }

    /**
     * Used for updating the User Allowed stations. Delete or insert the
     * stations according to the operation flag
     *
     * @param userVO
     * @throws CreateException
     * @throws RemoveException
     * @throws SystemException
     */

    private void updateUserAllowableStations(UserVO userVO)
            throws CreateException, RemoveException, SystemException {
        if (UserVO.SOURCE_WEB.equals(userVO.getSource())) {

            List<UserAllowableStations> indexColl = new ArrayList<UserAllowableStations>();
            if (getUserAllowableStations() != null) {
                indexColl.addAll(getUserAllowableStations());
            }
            if (userVO.getUserAllowableStationsVOPage() != null) {
                for (UserAllowableStationsVO allowableStationsVO : userVO
                        .getUserAllowableStationsVOPage()) {
                    LOG.log(Log.INFO, "allowableStationsVO++++++++++++++*",
                            allowableStationsVO);
                    allowableStationsVO.setUserCode(userVO.getUserCode());
                    if (UserAllowableStationsVO.OPERATION_FLAG_INSERT
                            .equals(allowableStationsVO.getOperationFlag())) {
                        UserAllowableStations allowableStations = new UserAllowableStations(
                                allowableStationsVO);
                        if (getUserAllowableStations() == null) {
                            setUserAllowableStations(new HashSet<UserAllowableStations>());
                        }
                        getUserAllowableStations().add(allowableStations);
                    } else if (UserAllowableStationsVO.OPERATION_FLAG_DELETE
                            .equals(allowableStationsVO.getOperationFlag())) {
                        if (userPK != null) {

                            for (UserAllowableStations allowableStations : indexColl) {
                                UserAllowableStationsPK allowableStationsPK = allowableStations
                                        .getUserAllowableStationsPK();
                                if (userPK.getCompanyCode().equals(
                                        allowableStationsPK.getCompanyCode())
                                        && userPK.getUserCode().equals(
                                        allowableStationsPK.getUserCode())
                                        && allowableStationsVO.getStationCode()
                                        .equals(allowableStationsPK
                                                .getStationCode())) {

                                    allowableStations.remove();
                                    getUserAllowableStations().remove(
                                            allowableStations);
                                    // for removing from
                                    // tables
                                    // indexColl.add(allowableStations); // for
                                    // avoiding
                                    // concurrenct Exception
                                    // and to remove from
                                    // collection

                                }

                            }
                        }
                    } else if (UserAllowableStationsVO.OPERATION_FLAG_UPDATE
                            .equals(allowableStationsVO.getOperationFlag())) {
                        UserAllowableStations allowableStations = new UserAllowableStations(
                                allowableStationsVO);
                        allowableStations.update(allowableStationsVO);
                    }
                    updateUserRoleGroupDetails(allowableStationsVO);

                }
            }
            /*
             * The reason for chcking indexColl size is, checking
             * userVO.getUserAllowableStationsVO() will not give correct result, if
             * the webservice request doesn't have the allowed station. If index
             * coll has value, then the getUserAllowableStationsVOPage will not be
             * null if the request is triggered from screen
             */
            else if (userVO.getUserAllowableStationsVO() != null
                    || indexColl.size() > 0) {
                for (UserAllowableStations userAllowableStations : indexColl) {
                    userAllowableStations.remove();
                    getUserAllowableStations().remove(userAllowableStations);
                }
                List<UserRoleGroupDetailsVO> userRoleGroupList = new ArrayList<UserRoleGroupDetailsVO>();
                if (userVO.getUserAllowableStationsVO() != null) {
                    if (getUserAllowableStations() == null) {
                        setUserAllowableStations(new HashSet<UserAllowableStations>());
                    }
                    for (UserAllowableStationsVO allowableStationsVO : userVO
                            .getUserAllowableStationsVO()) {
                        UserAllowableStations allowableStations = new UserAllowableStations(
                                allowableStationsVO);
                        getUserAllowableStations().add(allowableStations);
                        Collection<String> additionalRoleGroups = allowableStationsVO
                                .getAdditionalRoleGroups();
                        if (additionalRoleGroups != null
                                && additionalRoleGroups.size() > 0) {
                            for (String additionalRoleGroup : additionalRoleGroups) {
                                UserRoleGroupDetailsVO roleGroupDetailsVO = new UserRoleGroupDetailsVO();
                                roleGroupDetailsVO.setCompanyCode(allowableStationsVO
                                        .getCompanyCode());
                                roleGroupDetailsVO.setUserCode(allowableStationsVO
                                        .getUserCode());
                                roleGroupDetailsVO.setStationCode(allowableStationsVO
                                        .getStationCode());
                                roleGroupDetailsVO
                                        .setRoleGroupCode(additionalRoleGroup);
                                userRoleGroupList.add(roleGroupDetailsVO);
                            }
                        }
                    }
                }
            }
        } else {// Web service Call

            try {
                if (this.userAllowableStations != null
                        && !this.userAllowableStations.isEmpty()) {
                    for (UserAllowableStations userAllowableStation : userAllowableStations) {
                        PersistenceController.getEntityManager().remove(
                                userAllowableStation);
                    }
                }

            } catch (RemoveException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            } catch (OptimisticConcurrencyException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            }
            populateUserAllowableStations(userVO);

        }

    }


    /**
     * used for updating the User Role Group Details. Delete or Insert the role
     * group details according to the operation flag
     *
     * @throws CreateException
     * @throws RemoveException
     * @throws SystemException
     */

    private void updateUserRoleGroupDetails(UserAllowableStationsVO userAllowableStationsVO)
            throws CreateException, RemoveException, SystemException {

        if (userAllowableStationsVO.getAdditionalRoleGroups() != null) {
            Collection<String> additionalRoleGroups = userAllowableStationsVO.getAdditionalRoleGroups();
            LOG.entering(CLASS_NAME, "updateUserRoleGroupDetails -> for Updation");
            // the collection of UserAllowableOffices for removing the roleGroupDetails from
            // the collection
            for (String additionalRoleGroup : additionalRoleGroups) {
                if (UserAllowableStationsVO.OPERATION_FLAG_INSERT
                        .equals(userAllowableStationsVO.getOperationFlag())) {
                    UserRoleGroupDetailsVO roleGroupDetailsVO = new UserRoleGroupDetailsVO();
                    roleGroupDetailsVO.setCompanyCode(userAllowableStationsVO.getCompanyCode());
                    roleGroupDetailsVO.setUserCode(userAllowableStationsVO.getUserCode());
                    roleGroupDetailsVO.setStationCode(userAllowableStationsVO.getStationCode());
                    roleGroupDetailsVO.setRoleGroupCode(additionalRoleGroup);
                } else if (UserAllowableStationsVO.OPERATION_FLAG_DELETE
                        .equals(userAllowableStationsVO.getOperationFlag())) {
                    if (userPK != null) {

                        int index = 0;
                    }
                }
            } // removing the roleGroupDetails from the collection also
            LOG.exiting(CLASS_NAME, " updateUserRoleGroupDetails -> for Updation");
        }
    }


    /**
     * Used for updating the User Allowed offices. Delete or insert the offices
     * according to the operation flag
     *
     * @param userVO
     * @throws CreateException
     * @throws RemoveException
     * @throws SystemException
     */
    private void updateUserAllowableOffices(UserVO userVO)
            throws CreateException, RemoveException, SystemException {

        if (UserVO.SOURCE_WEB.equals(userVO.getSource())) {

            if (userVO.getUserAllowableOfficesVO() != null) {
                LOG.entering(CLASS_NAME, " updateUserAllowableOffices  -> for Updation");
                List<UserAllowableOffices> deletedSet = new ArrayList<UserAllowableOffices>();

                for (UserAllowableOfficesVO allowableOfficesVO : userVO
                        .getUserAllowableOfficesVO()) {
                    if (UserAllowableOfficesVO.OPERATION_FLAG_INSERT
                            .equals(allowableOfficesVO.getOperationFlag())) {
                        UserAllowableOffices allowableOffices = new UserAllowableOffices(
                                allowableOfficesVO);
                        if (getUserAllowableOffices() == null) {
                            setUserAllowableOffices(new HashSet<UserAllowableOffices>());
                        }
                        getUserAllowableOffices().add(allowableOffices);
                    } else if (UserAllowableOfficesVO.OPERATION_FLAG_DELETE
                            .equals(allowableOfficesVO.getOperationFlag())) {
                        if (userPK != null) {
                            for (UserAllowableOffices allowableOffices : getUserAllowableOffices()) {
                                UserAllowableOfficesPK allowableOfficesPK = allowableOffices
                                        .getUserAllowableOfficesPK();
                                if (userPK.getCompanyCode()
                                        .equals(allowableOfficesPK.getCompanyCode())
                                        && userPK.getUserCode()
                                        .equals(allowableOfficesPK.getUserCode())
                                        && allowableOfficesVO
                                        .getOfficeCode()
                                        .equals(
                                                allowableOfficesPK.getOfficeCode())) {

                                    allowableOffices.remove();
                                    deletedSet.add(allowableOffices);

                                }

                            }
                        }
                    }
                }
                if (deletedSet != null && deletedSet.size() > 0) {
                    getUserAllowableOffices().removeAll(deletedSet);
                }
                LOG.exiting(CLASS_NAME, " updateUserAllowableOffices  -> for Updation");
            }
        } else {// Web service call: Delete Insert the data

            try {
                if (this.userAllowableOffices != null
                        && !this.userAllowableOffices.isEmpty()) {
                    for (UserAllowableOffices userAllowableOffice : userAllowableOffices) {
                        PersistenceController.getEntityManager().remove(
                                userAllowableOffice);
                    }
                }


            } catch (RemoveException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            } catch (OptimisticConcurrencyException e) {
                LOG.log(Log.FINE, "inside Catch block");
                throw new SystemException(e.getMessage());
            }
            populateUserAllowableOffices(userVO);

        }
    }

    /**
     * used for deleting the user details
     *
     * @throws SystemException
     * @throws RemoveException
     */
    public void remove() throws RemoveException, SystemException {
        LOG.entering(CLASS_NAME, " remove ");
        // removeUserRoleGroupDetails();
        removeUserAllowableStations();
        removeUserAllowableOffices();
        removeUserParameters();
        PersistenceController.getEntityManager().remove(this);
        LOG.exiting(CLASS_NAME, " remove ");
    }

    /**
     * this method removes the userAllowableStations associated with this class
     *
     * @throws RemoveException
     * @throws SystemException
     */
    private void removeUserAllowableStations() throws RemoveException,
            SystemException {
        LOG.entering(CLASS_NAME, " removeUserAllowableStations ");
        if (getUserAllowableStations() != null) {
            for (UserAllowableStations allowableStations : getUserAllowableStations()) {
                allowableStations.remove();
            }
        }
        LOG.exiting(CLASS_NAME, " removeUserAllowableStations ");
    }

    /**
     * this method removes the userAllowableOffices associated with this class
     *
     * @throws RemoveException
     * @throws SystemException
     */
    private void removeUserAllowableOffices() throws RemoveException,
            SystemException {
        LOG.entering(CLASS_NAME, " removeUserAllowableOffices ");
        if (getUserAllowableOffices() != null) {
            for (UserAllowableOffices allowableOffices : getUserAllowableOffices()) {
                allowableOffices.remove();
            }
        }
        LOG.exiting(CLASS_NAME, " removeUserAllowableOffices ");
    }

    /**
     * This method lists the user details
     *
     * @param companyCode
     * @param userCode
     * @return UserVO which contains the user details
     * @throws SystemException
     */
    public static UserVO findUserDetails(String companyCode, String userCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, " findUserDetails ");
        UserVO userVO = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVO = adminUserDAO.findUserDetails(companyCode, userCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " findUserDetails ");
        return userVO;
    }

    /**
     * @param userEnquiryFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<UserVO> printUserEnquiryDetails(
            UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        Collection<UserVO> userVos = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVos = adminUserDAO.printUserEnquiryDetails(userEnquiryFilterVO);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }

        return userVos;

    }

    /*
     * public String generatePassword(){ return new String("New Password"); }
     */

    /**
     * This method generates the password for the user
     *
     * @return String
     * @author A-2006
     */
    public static String generatePassword() throws SystemException {
        return "rahaysa-code";
    }

    /**
     * This method lists the user details
     *
     * @param userLovFilterVO
     * @return Page<UserVO> which contains the user details based on rolegroup
     * @throws SystemException
     */
    public static Page<UserLovVO> findUserDetailsLov(
            UserLovFilterVO userLovFilterVO) throws SystemException {
        LOG.entering(CLASS_NAME, "<: findUserDetailsLov :>");
        Page<UserLovVO> userVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVOs = adminUserDAO.findUserDetailsLov(userLovFilterVO);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "<: findUserDetailsLov :>");
        return userVOs;
    }

    /**
     * This method lists the user details
     *
     * @param userLovFilterVO
     * @return Collection<UserVO>
     * @throws SystemException
     * @author A-1833
     */
    public static Collection<UserLovVO> findUsers(
            UserLovFilterVO userLovFilterVO) throws SystemException {
        Collection<UserLovVO> userVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVOs = adminUserDAO.findUsers(userLovFilterVO);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        return userVOs;
    }

    /**
     * @param userCodes
     * @param companyCode
     * @param rolegroupCode
     * @return Collection<ValidUsersVO> of valid users
     * @throws SystemException
     */
    public static Collection<ValidUsersVO> validateUsers(
            Collection<String> userCodes, String companyCode,
            String rolegroupCode) throws SystemException {
        LOG.entering(CLASS_NAME, "<: validateUsers :>");
        Collection<ValidUsersVO> validUserCodes = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            validUserCodes = adminUserDAO.validateUsers(userCodes, companyCode,
                    rolegroupCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "<: validateUsers :>");
        return validUserCodes;
    }

    // Added by Bejoy {starts}

    /**
     * This method gets the logon details
     *
     * @param companyCode
     * @param userCode
     * @return LogonAttributes which contains the logon details
     * @throws SystemException
     */
    public static LogonAttributes findUserDetailsForLogon(String companyCode,
                                                          String userCode) throws SystemException {
        LOG.entering(CLASS_NAME, " getUserDetailsForLogon ");
        LogonAttributes logonAttributes = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            logonAttributes = adminUserDAO.getUserDetailsForLogon(companyCode,
                    userCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " getUserDetailsForLogon ");
        return logonAttributes;
    }

    // Added by Bejoy {ends}

    // Added by Sinoob starts

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @param isOnline
     * @return
     * @throws SystemException
     */
    public static Collection<String> findAssigneeByAllRoleGroup(
            String companyCode, String airportCode, String warehouseCode,
            String roleGroupCode, boolean isOnline) throws SystemException {
        Collection<String> assignees = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            if (isOnline) {
                assignees = adminUserDAO.findRoleGroupOnline(companyCode,
                        airportCode, warehouseCode, roleGroupCode);
            } else {
                assignees = adminUserDAO.findRoleGroup(companyCode,
                        airportCode, warehouseCode, roleGroupCode);
            }
        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getMessage(),
                    persistenceException);
        }
        return assignees;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param userCode
     * @param isOnline
     * @return
     * @throws SystemException
     */
    public static Collection<String> findAssigneeBySpecificStaff(
            String companyCode, String airportCode, String warehouseCode,
            String userCode, boolean isOnline) throws SystemException {
        Collection<String> assignees = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            if (isOnline) {
                assignees = adminUserDAO.findSpecificStaffOnline(companyCode,
                        airportCode, warehouseCode, userCode);
            } else {
                assignees = adminUserDAO.findSpecificStaff(companyCode,
                        airportCode, warehouseCode, userCode);
            }
        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getMessage(),
                    persistenceException);
        }
        return assignees;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param roleGroupCode
     * @param isOnline
     * @return
     * @throws SystemException
     */
    public static Collection<String> findAssigneeBySpecificRoleGroup(
            String companyCode, String airportCode, String warehouseCode,
            String roleGroupCode, boolean isOnline) throws SystemException {
        Collection<String> assignees = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            if (isOnline) {
                assignees = adminUserDAO.findUsersInRoleGroupOnline(
                        companyCode, airportCode, warehouseCode, roleGroupCode);
            } else {
                assignees = adminUserDAO.findUsersInRoleGroup(companyCode,
                        airportCode, warehouseCode, roleGroupCode);
            }
        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getMessage(),
                    persistenceException);
        }
        return assignees;
    }

    // Added by Sinoob ends

    /**
     * @param userLovFilterVO
     * @return Collection<UserLovVO>
     * @throws SystemException
     * @author A-1863
     */
    public static Collection<UserLovVO> findUserDetailsByFilterLov(
            UserLovFilterVO userLovFilterVO) throws SystemException {
        LOG.entering(CLASS_NAME, "findUserDetailsLov ");
        Collection<UserLovVO> userVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVOs = adminUserDAO.findUserDetailsByFilterLov(userLovFilterVO);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " findUserDetailsLov ");
        return userVOs;

    }

    /**
     * @param userCodes
     * @param companyCode
     * @return
     * @throws SystemException
     * @author A-1863
     */
    public static Collection<ValidUsersVO> validateUsersWithoutRoleGroup(
            Collection<String> userCodes, String companyCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, " validateUsersWithoutRoleGroup");
        Collection<ValidUsersVO> validUserCodes = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            validUserCodes = adminUserDAO.validateUsersWithoutRoleGroup(
                    userCodes, companyCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "validateUsersWithoutRoleGroup");
        return validUserCodes;

    }

    /**
     * @param roleGroupCodes
     * @param companyCode
     * @return Collection<UserRoleGroupDetailsVO>
     * @throws SystemException
     */
    public static Collection<UserRoleGroupDetailsVO> validateRoleGroup(
            Collection<String> roleGroupCodes, String companyCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, " validateRoleGroup");
        Collection<UserRoleGroupDetailsVO> validUserCodes = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            validUserCodes = adminUserDAO.validateRoleGroup(roleGroupCodes,
                    companyCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "validateRoleGroup");
        return validUserCodes;

    }

    /**
     * @param companyCode
     * @param userCode
     * @param encryptedPassword
     * @return
     * @throws SystemException
     */
    public static boolean checkUserValid(String companyCode, String userCode,
                                         String encryptedPassword) throws SystemException {
        LOG.entering(CLASS_NAME, " isUserValid");
        boolean isValid = false;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            isValid = adminUserDAO.checkUserValid(companyCode, userCode,
                    encryptedPassword);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }

        LOG.log(Log.INFO, "inside User>>>>>>>>>isValid>>>>>>>>>>", isValid);
        return isValid;
    }

    /**
     * @param userEnquiryFilterVO
     * @return
     * @throws SystemException
     */
    public static Page<UserVO> findUserEnquiryDetails(
            UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        Page<UserVO> userVos = null;


        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVos = adminUserDAO.findUserEnquiryDetails(userEnquiryFilterVO);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }

        return userVos;

    }

    /**
     * @param logonAttributes
     * @throws SystemException
     * @throws CreateException
     * @throws RemoveException
     */
    public void updateAfterLogin(LogonAttributes logonAttributes)
            throws SystemException, CreateException {

        LOG.log(Log.INFO, "logonAttributes.getLoginType()", logonAttributes.getLoginType());
        LOG.log(Log.INFO, "logonAttributes.getRemainingLoginSessions()",
                logonAttributes.getRemainingLoginSessions());
        if (!logonAttributes.isSystemUser() && logonAttributes.getRemainingLoginSessions() >= 0) {
            if (AdminUserPersistenceConstants.GRACE_LOGIN.equals(logonAttributes
                    .getLoginType())) {
                setRemGraceLogins(logonAttributes.getRemainingLoginSessions());
            } else if (AdminUserPersistenceConstants.NORMAL_LOGIN
                    .equals(logonAttributes.getLoginType())) {
                setRemSessions(logonAttributes.getRemainingLoginSessions());
            }
            LOG.log(Log.INFO, "getRemSessions", getRemSessions());
            LOG.log(Log.INFO, "getRemGraceLogins", getRemGraceLogins());
        }
        this.setLoginTime(new GMTDate(true));
    }

    /**
     * Method for finding the active users logged in for a period
     * @author A-2246
     * @param companyCode
     * @param userCode
     * @return
     * @throws SystemException

    public static Collection<ValidUsersVO> disableInactiveUsers(
    String companyCode) throws SystemException{
    Collection<ValidUsersVO> userVos = null;

    try {
    AdminUserDAO adminUserDAO = AdminUserDAO.class
    .cast(PersistenceController.getEntityManager().getQueryDAO(
    AdminUserPersistenceConstants.MODULE_NAME));
    userVos = adminUserDAO.disableInactiveUsers(companyCode);

    } catch (PersistenceException persistenceException) {
    throw new SystemException(persistenceException.getErrorCode());
    }
    return userVos;

    } */

    /**
     * @param companyCode
     * @param inActivePeriod
     * @return
     * @throws SystemException
     */
    public static Collection<String> getAllUsersToDisable(
            String companyCode, int inActivePeriod) throws SystemException {
        Collection<String> users = null;

        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            users = adminUserDAO.getAllUsersToDisable(companyCode, inActivePeriod);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        return users;

    }

    /**
     * @param companyCode
     * @param userCode
     * @param pageNum
     * @param absoluteIndex
     * @return
     * @throws SystemException
     */
    public static Page<UserAllowableStationsVO> findUserAllowableStationsVOPage(String companyCode, String userCode, int pageNum, int absoluteIndex)
            throws SystemException {
        LOG.entering(CLASS_NAME, " findUserAllowableStationsVOPage ");
        Page<UserAllowableStationsVO> userAllowableStationsVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userAllowableStationsVOs = adminUserDAO.findUserAllowableStationsVOPage(companyCode, userCode, pageNum, absoluteIndex);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " findUserAllowableStationsVOPage ");
        return userAllowableStationsVOs;
    }

    /**
     * @return Returns the defaultLanguage.
     */
    @Column(name = "DEFLNG")
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * @param defaultLanguage The defaultLanguage to set.
     */
    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * @return Returns the defaultUserType.
     */
    @Column(name = "USRTYP")
    public String getDefaultUserType() {
        return defaultUserType;
    }

    /**
     * @param defaultUserType The defaultUserType to set.
     */
    public void setDefaultUserType(String defaultUserType) {
        this.defaultUserType = defaultUserType;
    }
    /**
     * @return Returns the companyName.
     */
	/*@Column(name = "CMPNAM")
	public String getCompanyName() {
		return companyName;
	}
	*//**
     * @param companyName The companyName to set.
     *//*
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}*/

    /**
     * @return Returns the departmentName.
     */
    @Column(name = "DEPNAM")
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName The departmentName to set.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return Returns the employeeId.
     */
    @Column(name = "EMPID")
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId The employeeId to set.
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @param userCodes
     * @param companyCode
     * @param rolegroupCodeOne
     * @param rolegroupCodeTwo
     * @return Collection<ValidUsersVO> of valid users
     * @throws SystemException
     */
    public static Collection<ValidUsersVO> validateCashiers(
            Collection<String> userCodes, String companyCode,
            String rolegroupCodeOne, String rolegroupCodeTwo) throws SystemException {
        LOG.entering(CLASS_NAME, "<: validateCashiers :>");
        Collection<ValidUsersVO> validUserCodes = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            validUserCodes = adminUserDAO.validateCashiers(userCodes, companyCode,
                    rolegroupCodeOne, rolegroupCodeTwo);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "<: validateCashiers :>");
        return validUserCodes;
    }

    /**
     * @param companyCode
     * @throws SystemException
     */
    public static void updateUserDetails(String companyCode) throws SystemException {
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            adminUserDAO.udapteUserDetails(companyCode);
        } catch (PersistenceException e) {
            throw new SystemException(e.getErrorCode());
        }
    }

    /**
     * @return
     * @throws SystemException
     */
    private static AdminUserDAO constructDAO() throws SystemException {
        try {
            return AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
        } catch (PersistenceException e) {
            throw new SystemException(e.getErrorCode());
        }
    }

    /**
     * @param userFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<UserVO> listUsers(UserFilterVO userFilterVO) throws SystemException {
        try {
            return constructDAO().listUsers(userFilterVO);
        } catch (PersistenceException e) {
            throw new SystemException(e.getErrorCode());
        }
    }

    //ICRD-57144 changes begins here

    /**
     * This method retrieves user parameter details for a logged in user by its
     * user code and company code.
     *
     * @param logonAttributes Logon Attribute object contains usercode and company code
     * @return Returns modified logonAttributs object
     * @throws SystemException SystemException will be thrown if any unexpected condition
     *                         occur.
     * @author A-6042
     * @since ICRD-57144
     */
    public static LogonAttributes findUserParametersValueMap(
            LogonAttributes logonAttributes) throws SystemException {
        LOG.entering(" User BO class ", "findUserParametersValueMap");

        try {
            logonAttributes = constructDAO().getUserParametersValueMap(logonAttributes);
        } catch (PersistenceException e) {
            throw new SystemException(e.getErrorCode());
        }

        LOG.exiting(" User BO class ", "findUserParametersValueMap");
        return logonAttributes;
    }
    //ICRD-57144 changes ends here

    /**
     * @author A-1885
     */
    public static UserVO findUserDetailsForPortal(String companyCode, String userCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, " findUserDetails ");
        UserVO userVO = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVO = adminUserDAO.findUserDetailsForPortal(companyCode, userCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " findUserDetails ");
        return userVO;
    }

    /**
     * Find user details for role groups.
     *
     * @param userCodes the user codes
     * @return the collection
     * @throws SystemException the system exception
     * @author A-5497
     */
    public static Collection<ValidUsersVO> findUserDetailsForRoleGroups(Collection<String> userCodes, String companyCode, String rolegroupCode, String adminRoleGroupCode, String stationCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, "<: findUserDetailsForRoleGroups :>");
        Collection<ValidUsersVO> validateUsers = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            validateUsers = adminUserDAO.findUserDetailsForRoleGroups(userCodes, companyCode, rolegroupCode, adminRoleGroupCode, stationCode);
        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "<: findUserDetailsForRoleGroups :>");
        return validateUsers;
    }

    /**
     * Find user details for role groups lov.
     *
     * @param userLovFilterVO the user lov filter vo
     * @return the page
     * @throws SystemException the system exception
     * @author A-5497
     */
    public static Page<UserLovVO> findUserDetailsForRoleGroupsLov(
            UserLovFilterVO userLovFilterVO) throws SystemException {
        LOG.entering(CLASS_NAME, "<: findUserDetailsForRoleGroupsLov :>");
        Page<UserLovVO> userVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userVOs = adminUserDAO.findUserDetailsForRoleGroupsLov(userLovFilterVO);
        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, "<: findUserDetailsForRoleGroupsLov :>");
        return userVOs;
    }

    private String userTitle;
    private String userOfficialCity;
    private String userOfficialCountry;


    @Column(name = "OFFCTYNAM")
    public String getUserOfficialCity() {
        return userOfficialCity;
    }

    public void setUserOfficialCity(String userOfficialCity) {
        this.userOfficialCity = userOfficialCity;
    }

    @Column(name = "OFFCNTNAM")
    public String getUserOfficialCountry() {
        return userOfficialCountry;
    }

    public void setUserOfficialCountry(String userOfficialCountry) {
        this.userOfficialCountry = userOfficialCountry;
    }

    @Column(name = "USRTTL")
    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    @Column(name = "PWDREQTIM")
    public Calendar getPasswordRequestedTime() {
        return passwordRequestedTime;
    }

    public void setPasswordRequestedTime(Calendar passwordRequestedTime) {
        this.passwordRequestedTime = passwordRequestedTime;
    }

    @Column(name = "PWDTKN")
    public String getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }

    private String portalUserType;
    private String portalDefaultRoleGroupCode;
    private String createdByUser;
    private int maxNumberOfChildUsers;
    private int numberOfChildUsers;
    private Calendar loginTime;
    private String useStandardEmailFlag;
    private String notificationLanguage;
    private String notificationFormat;
    private String showUnsecuredInfoMessageFlag;

    /**
     * @return the portalUserType
     */
    @Column(name = "PORUSRTYP")
    public String getPortalUserType() {
        return portalUserType;
    }

    /**
     * @param portalUserType the portalUserType to set
     */
    public void setPortalUserType(String portalUserType) {
        this.portalUserType = portalUserType;
    }

    /**
     * @return the portalDefaultRoleGroupCode
     */
    @Column(name = "PORDEFROLGRP")
    public String getPortalDefaultRoleGroupCode() {
        return portalDefaultRoleGroupCode;
    }

    /**
     * @param portalDefaultRoleGroupCode the portalDefaultRoleGroupCode to set
     */
    public void setPortalDefaultRoleGroupCode(String portalDefaultRoleGroupCode) {
        this.portalDefaultRoleGroupCode = portalDefaultRoleGroupCode;
    }

    /**
     * @return the createdByUser
     */
    @Column(name = "CRTUSR")
    public String getCreatedByUser() {
        return createdByUser;
    }

    /**
     * @param createdByUser the createdByUser to set
     */
    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    /**
     * @return the maxNumberOfChildUsers
     */
    @Column(name = "MAXCLDUSRCNT")
    public int getMaxNumberOfChildUsers() {
        return maxNumberOfChildUsers;
    }

    /**
     * @param maxNumberOfChildUsers the maxNumberOfChildUsers to set
     */
    public void setMaxNumberOfChildUsers(int maxNumberOfChildUsers) {
        this.maxNumberOfChildUsers = maxNumberOfChildUsers;
    }

    /**
     * @return the numberOfChildUsers
     */
    @Column(name = "CLDUSRCNT")
    public int getNumberOfChildUsers() {
        return numberOfChildUsers;
    }

    /**
     * @param numberOfChildUsers the numberOfChildUsers to set
     */
    public void setNumberOfChildUsers(int numberOfChildUsers) {
        this.numberOfChildUsers = numberOfChildUsers;
    }

    /**
     * @return Returns the loginTime.
     */
    @Column(name = "LSTLOGINTIM")

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLoginTime() {
        return this.loginTime;
    }

    /**
     * @param loginTime The loginTime to set.
     */
    public void setLoginTime(Calendar loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * Getter for useStandardEmailFlag
     * Added by : a-5127 on Dec 8, 2016
     * Used for :
     */
    @Column(name = "STDEMLFLG")
    public String getUseStandardEmailFlag() {
        return useStandardEmailFlag;
    }

    /**
     * @param useStandardEmailFlag the useStandardEmailFlag to set
     *                             Setter for useStandardEmailFlag
     *                             Added by : a-5127 on Dec 8, 2016
     *                             Used for :
     */
    public void setUseStandardEmailFlag(String useStandardEmailFlag) {
        this.useStandardEmailFlag = useStandardEmailFlag;
    }

    /**
     * Getter for notificationLanguage
     * Added by : a-5127 on Dec 8, 2016
     * Used for :
     */
    @Column(name = "NFNLNG")
    public String getNotificationLanguage() {
        return notificationLanguage;
    }

    /**
     * @param notificationLanguage the notificationLanguage to set
     *                             Setter for notificationLanguage
     *                             Added by : a-5127 on Dec 8, 2016
     *                             Used for :
     */
    public void setNotificationLanguage(String notificationLanguage) {
        this.notificationLanguage = notificationLanguage;
    }

    /**
     * Getter for notificationFormat
     * Added by : a-5127 on Dec 8, 2016
     * Used for :
     */
    @Column(name = "NFNFMT")
    public String getNotificationFormat() {
        return notificationFormat;
    }

    /**
     * @param notificationFormat the notificationFormat to set
     *                           Setter for notificationFormat
     *                           Added by : a-5127 on Dec 8, 2016
     *                           Used for :
     */
    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
    }

    /**
     * Getter for showUnsecuredInfoMessageFlag
     * Added by : a-5127 on Dec 8, 2016
     * Used for :
     */
    @Column(name = "SECINFMSGFLG")
    public String getShowUnsecuredInfoMessageFlag() {
        return showUnsecuredInfoMessageFlag;
    }

    /**
     * @param showUnsecuredInfoMessageFlag the showUnsecuredInfoMessageFlag to set
     *                                     Setter for showUnsecuredInfoMessageFlag
     *                                     Added by : a-5127 on Dec 8, 2016
     *                                     Used for :
     */
    public void setShowUnsecuredInfoMessageFlag(String showUnsecuredInfoMessageFlag) {
        this.showUnsecuredInfoMessageFlag = showUnsecuredInfoMessageFlag;
    }

    /**
     * @param userVo
     * @throws SystemException
     * @throws CreateException
     */
    private void populatePortalDetails(UserVO userVo) throws SystemException, CreateException {
        PortalUserDetailsVO portalDetails = userVo.getPortalDetails();
        setMaxNumberOfChildUsers(portalDetails.getMaxNumberOfChildUsers());

        setPortalUserType(portalDetails.getPortalUserType());
        setPortalDefaultRoleGroupCode(portalDetails.getPortalDefaultRoleGroupCode());
        /**
         * CRQ ID:ICRD-162691 - A-5127 added to set the attributes useStandardEmailFlag, notificationLanguage,
         * notificationFormat and showUnsecuredInfoMessageFlag from VO to entity
         */
        setUseStandardEmailFlag(portalDetails.getUseStandardEmailFlag());
        setNotificationLanguage(portalDetails.getNotificationLanguage());
        setNotificationFormat(portalDetails.getNotificationFormat());
        setShowUnsecuredInfoMessageFlag(portalDetails.getShowUnsecuredInfoMessageFlag());
        //CRQ ID:ICRD-162691 - A-5127 added - end

        String companyCode = userVo.getCompanyCode();
        String userCode = userVo.getUserCode();
    }

    /**
     * @param userVO
     * @throws SystemException
     * @throws CreateException
     * @throws RemoveException
     */
    public void updateUserFromPortal(UserVO userVO) throws SystemException, CreateException,
            RemoveException {

        setUserTitle(userVO.getUserTitle());
        setUserOfficialCountry(userVO.getUserOfficeCountry());
        setUserOfficialCity(userVO.getUserOfficeCity());
        setUserFirstName(userVO.getUserFirstName());
        setUserLastName(userVO.getUserLastName());
        setUserOfficialAddress(userVO.getUserOfficialAddress());
        setUserOfficialMobileNumber(userVO.getUserOfficialMobileNumber());
        setUserOfficialPhoneNumber(userVO.getUserOfficialPhoneNumber());
        setUserOfficialZipCode(userVO.getUserOfficialZipCode());
        setUserOfficialEmailAddress(userVO.getUserOfficialEmailAddress());
        setUserPersonalEmailAddress(userVO.getUserPersonalEmailAddress());
        setUserPersonalMobileNumber(userVO.getUserPersonalMobileNumber());
        setUserPersonalPhoneNumber(userVO.getUserPersonalPhoneNumber());
        setDefaultLanguage(userVO.getDefaultLanguage());

        PortalUserDetailsVO portalDetails = userVO.getPortalDetails();
        if (portalDetails != null) { // if()

            /**
             * CRQ ID:ICRD-162691 - A-5127 added to set the attributes useStandardEmailFlag, notificationLanguage,
             * notificationFormat and showUnsecuredInfoMessageFlag from VO to entity
             */
            setUseStandardEmailFlag(portalDetails.getUseStandardEmailFlag());
            setNotificationLanguage(portalDetails.getNotificationLanguage());
            setNotificationFormat(portalDetails.getNotificationFormat());
            setShowUnsecuredInfoMessageFlag(portalDetails.getShowUnsecuredInfoMessageFlag());
            //CRQ ID:ICRD-162691 - A-5127 added - end
        }
    }

    /**
     * Added as part of ICRD-204467
     *
     * @param userEnquiryFilterVO
     * @return
     * @throws SystemException
     */
    public List<UserVO> findAllHandlingAreaUsers(
            UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        LOG.entering(CLASS_NAME, "findAllHandlingAreaUsers");
        return constructDAO().findAllHandlingAreaUsers(userEnquiryFilterVO);
    }

    /**
     * @param userEnquiryFilterVO
     * @return
     * @throws SystemException
     * @author a-5505
     */
    public static UserVO validateHandlingAreaUsers(
            UserEnquiryFilterVO userEnquiryFilterVO) throws SystemException {
        return constructDAO().validateHandlingAreaUsers(userEnquiryFilterVO);
    }


    /**
     * @param userEnquiryFilterVO
     * @return
     * @throws SystemException
     */
    public static List<UserRoleGroupDetailsVO> getRoleDetailsForUser(UserEnquiryFilterVO userEnquiryFilterVO)
            throws SystemException {
        return constructDAO().getRoleDetailsForUser(userEnquiryFilterVO);
    }

    /**
     * @param companyCode
     * @param roleGroupsToSendMail
     * @param stations
     * @return
     * @throws SystemException
     * @author A-5810
     */
    public static List<UserRoleGroupDetailsVO> findRoleGroupUserEmailDetails(String companyCode, String roleGroupsToSendMail,
                                                                             String stations) throws SystemException {
        LOG.entering(CLASS_NAME, "findRoleGroupUserEmailDetails");
        return constructDAO().findRoleGroupUserEmailDetails(companyCode, roleGroupsToSendMail, stations);
    }
}
