/*
 * UserAllowableStations.java Created on Jun 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserAllowableStationsVO;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserDAO;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import javax.persistence.*;
import java.util.Collection;

/**
 * The class describles the allowable stations asociated with the user
 *
 * @author A-1417
 */

@Table(name = "ADMUSRALLSTN")
@Entity
public class UserAllowableStations {

    private static final String CLASS_NAME = "UserAllowableStations";

    private static final String MODULE_NAME = "admin.user";

    private static final Log LOG = LogFactory.getLogger(MODULE_NAME);
    /**
     * This field denotes the defaut role group of the user
     */
    private String stationDefaultRoleGroup;
    /**
     * The role groups associated with the station
     */
    /**
     * primary key class of the user allowable stations entity
     */
    private UserAllowableStationsPK userAllowableStationsPK;


    /**
     * @return Returns the stationDefaultRoleGroup.
     */
    @Column(name = "DEFROLGRP")
    public String getStationDefaultRoleGroup() {
        return stationDefaultRoleGroup;
    }

    /**
     * The stationDefaultRoleGroup to set.
     */
    public void setStationDefaultRoleGroup(String stationDefaultRoleGroup) {
        this.stationDefaultRoleGroup = stationDefaultRoleGroup;
    }

    /**
     * @return Returns the userAllowableStationsPK.
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
            @AttributeOverride(name = "userCode", column = @Column(name = "USRCOD")),
            @AttributeOverride(name = "stationCode", column = @Column(name = "STNCOD"))})
    public UserAllowableStationsPK getUserAllowableStationsPK() {
        return userAllowableStationsPK;
    }

    /**
     * @param userAllowableStationsPK The userAllowableStationsPK to set.
     */
    public void setUserAllowableStationsPK(
            UserAllowableStationsPK userAllowableStationsPK) {
        this.userAllowableStationsPK = userAllowableStationsPK;
    }


    /**
     * Default Constructor
     */
    public UserAllowableStations() {
    }

    /**
     * Overloaded Constructor
     *
     * @param userAllowableStationsVO
     * @throws CreateException
     * @throws SystemException
     */
    public UserAllowableStations(UserAllowableStationsVO userAllowableStationsVO)
            throws CreateException, SystemException {
        LOG.entering(CLASS_NAME, "Constructor");

        UserAllowableStationsPK allowableStationsPK = new UserAllowableStationsPK();
        allowableStationsPK.setCompanyCode(userAllowableStationsVO
                .getCompanyCode());
        allowableStationsPK.setUserCode(userAllowableStationsVO
                .getUserCode());
        allowableStationsPK.setStationCode(userAllowableStationsVO
                .getStationCode());
        setUserAllowableStationsPK(allowableStationsPK);
        setStationDefaultRoleGroup(userAllowableStationsVO.getStationDefaultRoleGroup());

        PersistenceController.getEntityManager().persist(this);
        LOG.exiting(CLASS_NAME, "Constructor");
    }

    /**
     * used for finding the User object from the database based on the primary
     * key values uses the Persistence Controller find method
     *
     * @param companyCode
     * @param userCode
     * @param stationCode
     * @return UserAllowableStations
     * @throws SystemException
     * @throws FinderException
     */
    public static UserAllowableStations find(String companyCode, String userCode, String stationCode)
            throws SystemException, FinderException {

        UserAllowableStations userAllowableStations = null;
        UserAllowableStationsPK userAllowableStationsPk = new UserAllowableStationsPK();
        userAllowableStationsPk.setCompanyCode(companyCode);
        userAllowableStationsPk.setUserCode(userCode);
        userAllowableStationsPk.setStationCode(stationCode);
        return PersistenceController.getEntityManager().find(UserAllowableStations.class, userAllowableStationsPk);
    }
    //Added by Sharika
    //starts

    /**
     * @param companyCode
     * @param userCode
     * @return
     * @throws SystemException
     */
    public static Collection<UserAllowableStationsVO> findUserAllowableStationDetails(String companyCode, String userCode)
            throws SystemException {
        LOG.entering(CLASS_NAME, " findUserAllowableStationsDetails ");
        Collection<UserAllowableStationsVO> userAllowableStationsVOs = null;
        try {
            AdminUserDAO adminUserDAO = AdminUserDAO.class
                    .cast(PersistenceController.getEntityManager().getQueryDAO(
                            AdminUserPersistenceConstants.MODULE_NAME));
            userAllowableStationsVOs = adminUserDAO.findUserAllowableStationsDetails(companyCode, userCode);

        } catch (PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode());
        }
        LOG.exiting(CLASS_NAME, " findUserDetails ");
        return userAllowableStationsVOs;
    }
    //ends

    /**
     * used for updating the station details
     *
     * @param userAllowableStationsVO
     * @throws CreateException
     * @throws SystemException
     * @throws RemoveException
     */
    public void update(UserAllowableStationsVO userAllowableStationsVO) throws SystemException, CreateException,
            RemoveException {
        LOG.entering(CLASS_NAME, " update");

        setStationDefaultRoleGroup(userAllowableStationsVO.getStationDefaultRoleGroup());
        LOG.exiting(CLASS_NAME, " update");
    }

    /**
     * This method removes the UserAllowableStations
     *
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove() throws RemoveException, SystemException {
        LOG.entering(CLASS_NAME, " remove ");
        PersistenceController.getEntityManager().remove(this);
        LOG.exiting(CLASS_NAME, " remove ");
    }

}
