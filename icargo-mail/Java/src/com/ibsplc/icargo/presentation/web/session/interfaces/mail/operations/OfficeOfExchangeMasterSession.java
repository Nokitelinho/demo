/*
 * OfficeOfExchangeMasterSession.java Created on June 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2047
 *
 */
public interface OfficeOfExchangeMasterSession extends ScreenSession {

    /**
     * @return Page<OfficeOfExchangeVO>
     */
    Page<OfficeOfExchangeVO> getOfficeOfExchangeVOs();
    
    /**
     * @param officeOfExchangeVOs
     */
    void setOfficeOfExchangeVOs(Page<OfficeOfExchangeVO> officeOfExchangeVOs);
    
    /**
     * @return OfficeOfExchangeVO
     */
    OfficeOfExchangeVO getOfficeOfExchangeVO();
    
    /**
     * @param officeOfExchangeVO
     */
    void setOfficeOfExchangeVO(OfficeOfExchangeVO officeOfExchangeVO);
}
