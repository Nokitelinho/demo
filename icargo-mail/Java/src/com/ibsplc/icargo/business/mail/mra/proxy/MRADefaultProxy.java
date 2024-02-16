/*
 * MRADefaultProxy.java Created on Jan 14, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import java.rmi.RemoteException;

import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFileLogVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-6792
 *
 */
@Module("mail")
@SubModule("mra")
public class MRADefaultProxy extends  ProductProxy {

    
    private static final String CLASS_NAME = "CRAInterfaceProxy";
    private Log log = LogFactory.getLogger("CRA_ACCOUNTING");
    
    public void sendSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO,SAPInterfaceFileLogVO sapInterfaceFileLogVO) throws RemoteException, SystemException,ProxyException{
		log.entering(CLASS_NAME, "sendSAPInterfaceFile");
		dispatchAsyncRequest("sendSAPInterfaceFile",false,interfaceFilterVO,sapInterfaceFileLogVO);
		log.exiting(CLASS_NAME, "sendSAPInterfaceFile");

	}
    
    /**
     * 	Method		:	MRADefaultProxy.findBillingScheduleDetails
     *	Added by 	:	A-8061 on 10-Jun-2021
     * 	Used for 	:
     *	Parameters	:	@param billingScheduleFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws ProxyException
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	Page<BillingScheduleDetailsVO>
     */
    public Page<BillingScheduleDetailsVO> findBillingScheduleDetails(BillingScheduleFilterVO billingScheduleFilterVO) throws ProxyException, SystemException{
    	return despatchRequest("findBillingType", billingScheduleFilterVO,0);
    }
    
    /**
     * 
     * 	Method		:	MRADefaultProxy.initiateTransactionLogForInvoiceGeneration
     *	Added by 	:	A-8061 on 10-Jun-2021
     * 	Used for 	:
     *	Parameters	:	@param invoiceTransactionLogVO
     *	Parameters	:	@return
     *	Parameters	:	@throws ProxyException
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	InvoiceTransactionLogVO
     */
    public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO)  throws ProxyException, SystemException {
		return despatchRequest("initiateTransactionLogForInvoiceGeneration",invoiceTransactionLogVO);
	} 
}

