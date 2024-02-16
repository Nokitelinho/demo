/*
 * DamagedDSNSession.java Created on July 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface DamagedDSNSession extends ScreenSession {

	 /**
     * @return DamagedDSNVO
     */
	DamagedDSNVO getDamagedDSNVO();
    
    /**
     * @param damagedMailbagVOs
     */
    void setDamagedDSNVO(DamagedDSNVO damagedDSNVO);
    
	 /**
     * @return DespatchDetailsVO
     */
    DespatchDetailsVO getDespatchDetailsVO();
    
    /**
     * @param despatchDetailsVO
     */
    void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO);
}
