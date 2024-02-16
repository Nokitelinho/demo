/**
 * AdminReportProxy.java Created on May 08, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.admin.report.vo.PrinterAssignmentMasterVO;
import com.ibsplc.icargo.business.admin.report.vo.ReportPublishJobVO;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-5500
 *
 */
@Module("admin")
@SubModule("report")
public class AdminReportProxy extends ProductProxy{
	
	
	
	private static final String MODULE_NAME = "AdminReportProxy";
    private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * @author a-5500
	 * @param companyCode
	 * @param reportID
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<PrinterAssignmentMasterVO> findPrinterDetailsForReport(String companyCode, String reportID) 
	throws ProxyException, SystemException { 
			log.entering(MODULE_NAME, "findPrinterDetailsForReport"); 
			return despatchRequest("findPrinterDetailsForReport", companyCode, reportID); 
	} 
	/**
	 * @author A-7540
	 * @param resditPublishJobScheduleVO
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 * @throws ProxyException
	 */
	public void publishReport(ReportPublishJobVO reportPublishJobVO)
			throws SystemException,ProxyException{
	{
		log.entering(MODULE_NAME, "publishReport");	
		try{
			despatchRequest("publishReport",reportPublishJobVO);     
		}catch (ProxyException e) {
			e.getMessage();
		}
	}	
	
  }
}
