/*
 * GenerateRepairInvoiceSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;


import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1347
 *
 */
public interface GenerateRepairInvoiceSession extends ScreenSession {
	    
    /**
     * 
     * @return
     */
    ULDRepairVO getULDRepairVO();
    /**
     * 
     * @param uldRepairVO
     */
    void setULDRepairVO(ULDRepairVO uldRepairVO);

    
}
