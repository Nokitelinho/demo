/*
 * UserController.java Created on 27/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.business.admin.user.vo.UserCompositeData;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * @author jens
 */
@Component
@Transactional
public class UserController {

    public User saveUser(UserVO userVO, UserCompositeData data) throws SystemException, RemoveException, CreateException {
       return new User(userVO, data);
    }

    public UserVO listUser(String companyCode, String userCode) throws SystemException {
        return User.findUserDetails(companyCode, userCode);
    }

    public Collection<UserVO> listUsers(UserFilterVO filterVO) throws SystemException {
        return User.listUsers(filterVO);
    }
}
