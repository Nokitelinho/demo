/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.operations.shipment.ux.awbenquiry.PrintHistoryCommand.java
 *
 *	Created by	:	A-5498
 *	Created on	:	30-Jan-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.operations.shipment.ux.awbenquiry.PrintHistoryCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5498	:	30-Jan-2018	:	Draft
 */
public class DsnMailbagPrintCommand extends AbstractPrintCommand {
	private static final String REPORT_ID = "RPRMTK081";
	private Log log = LogFactory.getLogger("Mailbag Manifest");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "ux.outbound";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailManifest";
    @Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {	
		
		OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
		  Collection<ErrorVO> errors = new ArrayList();
		  LogonAttributes logonAttributes = getLogonAttribute();
		 // DefaultScreenSessionImpl screenSession= getScreenSession();
		  ResponseVO responseVO = new ResponseVO();
		  List<OutboundModel> results = new ArrayList();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
			operationalFlightVO =MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(), logonAttributes);
			ReportSpec reportSpec = getReportSpec();				
			reportSpec.setReportId(REPORT_ID);
			reportSpec.setProductCode(PRODUCTCODE);
			reportSpec.setSubProductCode(SUBPRODUCTCODE);
			reportSpec.setPreview(true);
			//reportSpec.setHttpServerBase(invocationContext.httpServerBase);	
			reportSpec.addFilterValue(MailConstantsVO.MANIFEST_DSN_MAILBAG);
			reportSpec.addFilterValue(operationalFlightVO);
			reportSpec.setResourceBundle("mailManifestResources");
			reportSpec.setAction(ACTION);
				
			
		
		try {
			generateReport(actionContext);
		} catch (CommandInvocationException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
