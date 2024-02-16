/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.CRAMiscbillingProxy.java
 *
 *	Created by	:	a-8061
 *	Created on	:	17-Aug-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;


import com.ibsplc.icargo.business.cra.defaults.vo.CRAFlightFinaliseVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.CRAMiscbillingProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	17-Aug-2018	:	Draft
 */
@Module("cra")
@SubModule("miscbilling")
public class CRAMiscbillingProxy extends  ProductProxy{

	 private static final String SERVICE_NAME = "CRA_MISCBILLING";

	    private static final String CLASS_NAME = "CRAMiscbillingProxy";
	    private Log log = LogFactory.getLogger("MRA DEFAULTS");

    public void saveBlockSpaceAgreementDetails(CRAFlightFinaliseVO cRAFlightFinaliseVO)throws SystemException, ProxyException{
    	 log.entering(CLASS_NAME, "saveBlockSpaceAgreementDetails");
    	
    	 dispatchAsyncRequest("saveBlockSpaceAgreementDetails",false, cRAFlightFinaliseVO);	 
    }
}
