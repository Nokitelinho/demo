/*
 * GenerateRepairInvoiceSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;


import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.GenerateRepairInvoiceSession;

/**
 * @author A-1347
 *
 */
public class GenerateRepairInvoiceSessionImpl extends AbstractScreenSession
		implements GenerateRepairInvoiceSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.damage.generaterepairinvoice";

	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}

   /**
    * @return
    */
    public ULDRepairVO getULDRepairVO() {
        return null;
    }
   /**
    * @param uldRepairVO
    */
    public void setULDRepairVO(ULDRepairVO uldRepairVO) {
    }
   
}
