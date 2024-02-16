/*
 * UserParameter.java Created on Sep 05, 2013
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserParameterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import javax.persistence.*;
import java.util.Calendar;

@Table(name = "ADMUSRPAR")
@Entity
public class UserParameter {
    private static final String CLASS_NAME = "UserParameter";

    private static final String MODULE_NAME = "admin.user";

    private static final Log LOG = LogFactory.getLogger(MODULE_NAME);

    private UserParameterPK userParameterPK;

    private String paramterValue;

    private String lastUpdateUser;

    private Calendar lastUpdateTime;

    /**
     * @return the userParameterPK
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
            @AttributeOverride(name = "userCode", column = @Column(name = "USRCOD")),
            @AttributeOverride(name = "parameterCode", column = @Column(name = "PARCOD"))})
    public UserParameterPK getUserParameterPK() {
        return userParameterPK;
    }

    /**
     * @param userParameterPK the userParameterPK to set
     */
    public void setUserParameterPK(UserParameterPK userParameterPK) {
        this.userParameterPK = userParameterPK;
    }

    /**
     * @return the paramterValue
     */
    @Column(name = "PARVAL")
    public String getParamterValue() {
        return paramterValue;
    }

    /**
     * @param paramterValue the paramterValue to set
     */
    public void setParamterValue(String paramterValue) {
        this.paramterValue = paramterValue;
    }

    /**
     * @return the lastUpdateUser
     */
    @Column(name = "LSTUPDUSR")
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser the lastUpdateUser to set
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return the lastUpdateTime
     */
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LSTUPDTIM")
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     *
     */
    public UserParameter() {

    }

    /**
     * @param userParameterVO
     * @throws SystemException
     * @throws CreateException
     */
    public UserParameter(String userCode, UserParameterVO userParameterVO) throws CreateException, SystemException {
        populatePrimaryKey(userCode, userParameterVO);
        populateAttributes(userParameterVO);
        //Persisting
        PersistenceController.getEntityManager().persist(this);
    }

    /**
     * @param userParameterVO
     */
    private void populatePrimaryKey(String userCode, UserParameterVO userParameterVO) {
        UserParameterPK userParameterPK = new UserParameterPK();
        userParameterPK.setCompanyCode(userParameterVO.getCompanyCode());
        userParameterPK.setUserCode(userCode);
        userParameterPK.setParameterCode(userParameterVO.getParameterCode());
        this.setUserParameterPK(userParameterPK);
    }

    /**
     * @param userParameterVO
     */
    private void populateAttributes(UserParameterVO userParameterVO) {
        this.setParamterValue(userParameterVO.getParameterValue());
        this.setLastUpdateUser(userParameterVO.getLastUpdateUser());
    }

    /**
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove() throws RemoveException, SystemException {
        LOG.entering("UserParameter", " remove ");
        PersistenceController.getEntityManager().remove(this);
        LOG.exiting("UserParameter", " remove ");
    }

    /**
     * Method to find a persistent <code>UserParameter</code>
     *
     * @param companyCode
     * @param userId
     * @param parameterCode
     * @return
     * @throws SystemException
     * @throws FinderException
     * @author A-2394
     */
    public static UserParameter find(String companyCode, String userId, String parameterCode)
            throws SystemException, FinderException {
        UserParameterPK pk = new UserParameterPK();
        pk.setCompanyCode(companyCode);
        pk.setUserCode(userId);
        pk.setParameterCode(parameterCode);
        return PersistenceController.getEntityManager().find(UserParameter.class, pk);
    }

}
