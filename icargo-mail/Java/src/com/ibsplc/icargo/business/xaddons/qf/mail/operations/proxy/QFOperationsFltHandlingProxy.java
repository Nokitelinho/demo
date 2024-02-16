/*
 * OperationsFltHandlingProxy.java Created on Aug 23, 2023
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.qf.mail.operations.proxy;


import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDWeighingDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *  
 * @author 10383
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		 Aug 23, 20237			A-10383		Created
 */

@Module("qfoperations")
@SubModule("flthandling")
public class QFOperationsFltHandlingProxy extends ProductProxy {
	 public OperationalULDWeighingDetailsVO validateAndEnrichActualWeightForMailContainer(OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVO) throws ProxyException, SystemException {
		 OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVOResult =null;
		 operationalULDWeighingDetailsVOResult =despatchRequest("validateAndEnrichActualWeightForMailContainer", operationalULDWeighingDetailsVO);
		 return operationalULDWeighingDetailsVOResult;
	 }
}