/*
 * CRAAccountingProxy.java Created on June 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFileLogVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-2391
 *
 */
@Module("cra")
@SubModule("accounting")
public class CRAAccountingProxy extends ProductProxy{
	private static final String SERVICE_NAME = "CRA_ACCOUNTING";
	
	

	 /**
	 * @param accountingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public String reverseAccountingDetails(AccountingFilterVO accountingFilterVO) 
		
	    throws SystemException, ProxyException {
		
		 return despatchRequest("reverseAccountingDetails", accountingFilterVO);	
	}
	 

	 public SAPInterfaceFileLogVO saveSAPInterfaceFileLogs(SAPInterfaceFilterVO sapInterfaceFilterVO)
			    throws ProxyException, SystemException
			  {
			    try {
			      return despatchRequest("saveSAPInterfaceFileLogs", sapInterfaceFilterVO );
			    } catch (Exception e) {
			      throw new SystemException(e.getMessage());
			    }
			  }

			  public void updateInterfaceLogDetails(SAPInterfaceFileLogVO sapInterfaceFileLogVO, String status)
			    throws ProxyException, SystemException
			  {
			    despatchRequest("updateInterfaceLogDetails", sapInterfaceFileLogVO, status );
			  }
			  
	 public SAPInterfaceFileLogVO saveSAPInterfaceFileLog(SAPInterfaceFileLogVO sapInterfaceFileLogVO)
			 throws ProxyException, SystemException
		     {
			 	return despatchRequest("saveSAPInterfaceFileLog", sapInterfaceFileLogVO);
			 }

}



