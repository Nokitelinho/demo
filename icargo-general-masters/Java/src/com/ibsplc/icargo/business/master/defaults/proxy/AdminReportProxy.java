/**
 *	Java file	: 	com.ibsplc.icargo.business.warehouse.defaults.proxy.WarehouseDefaultsProxy.java
 *
 *	Created by	:	A-5269
 *	Created on	:	May 20, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.master.defaults.proxy;

import com.ibsplc.icargo.business.admin.report.vo.ReportDataVO;
import com.ibsplc.icargo.business.admin.report.vo.ReportTemplateVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
@Module("admin")
@SubModule("report")
public class AdminReportProxy extends ProductProxy{

	/**
	 * 	Method		:	AdminReportProxy.findDetailsForReport
	 *	Added by 	:	A-7797 on 26-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param reportTemplateVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ReportDataVO
	 */
	public ReportDataVO findDetailsForReport(ReportTemplateVO reportTemplateVO)throws ProxyException, SystemException {
		return despatchRequest("findDetailsForReport", reportTemplateVO);
	}
	/**
	 * 	Method		:	AdminReportProxy.findReportTemplateDetails
	 *	Added by 	:	A-7797 on 26-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param reportID
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ReportTemplateVO
	 */
	public ReportTemplateVO findReportTemplateDetails(String reportID, String companyCode)throws ProxyException, SystemException {
		return despatchRequest("findReportTemplateDetails",reportID,companyCode);
	}
}