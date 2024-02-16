/*
 * ULDDefaultsProxy.java Created on Jul 8, 2005
 *
 * Copyright 2023 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.proxy.ULDDefaultsProxy.java
 *	This class is used as proxy
 */
@Module("uld")
@SubModule("defaults")
public class ULDDefaultsProxy extends ProductProxy {
	/**
	 * 
	 * 	Method		:	ULDDefaultsProxy.findBestFitULDAgreement
	 *	Added on 	:	24-Apr-2023
	 * 	Used for 	:	finding best fit uld agreement
	 *	Parameters	:	@param uldTransactionDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	ULDAgreementVO
	 */
	public ULDAgreementVO findBestFitULDAgreement(ULDTransactionDetailsVO uldTransactionDetailsVO)
			throws SystemException , ProxyException{
				return despatchRequest("findBestFitULDAgreement", uldTransactionDetailsVO);
			}

}
