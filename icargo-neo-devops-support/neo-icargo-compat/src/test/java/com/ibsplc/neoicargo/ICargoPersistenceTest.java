/*
 * ICargoPersistenceTest.java Created on 25/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo;

import com.ibsplc.icargo.business.admin.user.vo.*;
import com.ibsplc.neoicargo.business.admin.user.User;
import com.ibsplc.neoicargo.business.admin.user.UserController;
import com.ibsplc.neoicargo.business.admin.user.vo.UserCompositeData;
import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.context.tenant.TenantContext;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author jens
 */
@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICargoPersistenceTest {

    @Autowired
    private TenantContext tenantContext;

    static final Logger logger = LoggerFactory.getLogger(ICargoPersistenceTest.class);

    @BeforeEach
    public void contextUtil() {
        ContextUtil util = new ContextUtil(this.tenantContext.getDelegate(), Mockito.mock(ObjectProvider.class));
        RequestContext context = new RequestContext();
        context.setContextMap(new HashMap<>(2));
        util.setContext(context);
    }

    @Test
    @Order(1)
    void contextLoad() {
        logger.info("Context Loaded !!");
    }

    @Test
    @Order(2)
    void testEntityInsert() throws SystemException, RemoveException, CreateException {
        UserVO vo = new UserVO();
        vo.setCompanyCode("AV");
        vo.setUserCode("FOO");
        vo.setUserFirstName("FOO");
        vo.setUserLastName("BAR");
        vo.setAgentCode("AGT");
        vo.setLastUpdateUser("FOO");
        vo.setDefaultLanguage("en_US");
        UserAllowableOfficesVO officesVO = new UserAllowableOfficesVO();
        officesVO.setCompanyCode("AV");
        officesVO.setOperationFlag("I");
        officesVO.setUserCode("FOO");
        officesVO.setOfficeCode("FOO");
        vo.setUserAllowableOfficesVO(Collections.singletonList(officesVO));

        UserAllowableStationsVO stationsVO = new UserAllowableStationsVO();
        stationsVO.setCompanyCode("AV");
        stationsVO.setUserCode("FOO");
        stationsVO.setAddlRoles("FOO");
        stationsVO.setStationCode("FOO");
        stationsVO.setOperationFlag("I");
        vo.setUserAllowableStationsVO(Collections.singletonList(stationsVO));

        UserParameterVO parameterVO = new UserParameterVO();
        parameterVO.setCompanyCode("AV");
        parameterVO.setOperationFlag("I");
        parameterVO.setParameterCode("foo");
        parameterVO.setParameterValue("bar");
        parameterVO.setLastUpdateUser("FOO");
        vo.setUserParameterVOs(Collections.singletonList(parameterVO));

        UserCompositeData data = new UserCompositeData("John Doe", 69);

        Assertions.assertDoesNotThrow(() -> tenantContext.getBean(UserController.class).saveUser(vo, data));
    }



    @Test
    @Order(3)
    void testQuery() throws SystemException {
        Assertions.assertDoesNotThrow(() -> {
            UserVO userVO = tenantContext.getBean(UserController.class).listUser("AV", "FOO");
        });

        UserFilterVO filterVO = new UserFilterVO();
        filterVO.setCompanyCode("av");
        Collection<UserVO> userVOs = tenantContext.getBean(UserController.class).listUsers(filterVO);
        Assertions.assertFalse(userVOs.isEmpty());
    }


}
