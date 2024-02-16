package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class TransferManifestPrintCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");

	   /**
	    * TARGET
	    */

	   
	   private static final String TRFMFT_REPORT_ID = "RPTOPS066";

		private static final String PRODUCTCODE = "mail";
		private static final String BUNDLE = "transferMailManifestResources";
		private static final String SUBPRODUCTCODE = "operations";
		private static final String ACTION = "generateTransferManifestReportForMail";

		
	public void execute(ActionContext actionContext) 
			throws BusinessDelegateException, CommandInvocationException {
 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();

		TransferManifestVO transferManifestVO=null;
		transferManifestVO=
				mailinboundModel.getTransferManifestVO();
		ReportSpec reportSpec = getReportSpec();    
		reportSpec.setReportId(TRFMFT_REPORT_ID);    
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		//reportSpec.setHttpServerBase(invocationContext.httpServerBase);  
		reportSpec.setPreview(true);
		reportSpec.addFilterValue(transferManifestVO);    
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);          
		try {
			generateReport(actionContext);
			log.log(Log.FINE, "REPORT GENERATE", reportSpec);
		} catch (CommandInvocationException e) {
			log.log(Log.FINE, "REPORT ERROR", reportSpec);
		}
		log.log(Log.FINE, "REPORT EXIT", reportSpec);
				
	}
	}
