/*
 * PrintExternalMovementsCommand.java Created on 28 JUL 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3353
 *
 */
public class PrintExternalMovementsCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST267";
	private Log log = LogFactory.getLogger("PrintExternalMovementsCommand");
	private static final String GENERATE_FAILURE = "generate_failure";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String MODULENAME = "uld.defaults";
	private static final String SCREENID = "uld.defaults.misc.listuldmovement";
	private static final String PRODUCTCODE = "uld";
	private static final String SUBPRODUCTCODE = "defaults";
	private static final String ACTION = "printExternalMovementsReport";
	private static final String BUNDLE = "listuldmovementResources";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			
		 
	    log.log(Log.FINE, "Inside GenerateReportCommand---------->  ");
	    ListULDMovementForm listULDMovementForm = (ListULDMovementForm)invocationContext.screenModel;
	    
		 ApplicationSessionImpl applicationSession = getApplicationSession();
	        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	        String companyCode = logonAttributes.getCompanyCode().toUpperCase();
	        
	        String uldNumber = listULDMovementForm.getUldNumber().toUpperCase();
	    ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();				
   //	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    uldMovementFilterVO.setCompanyCode(companyCode);
	    uldMovementFilterVO.setPageNumber(Integer.parseInt(listULDMovementForm.getDisplayPage()));
	    if(uldNumber!=null && uldNumber.length()>0){
	    uldMovementFilterVO.setUldNumber(uldNumber);
	    }
		LocalDate fDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
	    if(listULDMovementForm.getFromDate()!=null && listULDMovementForm.getFromDate().trim().length()!=0){
	    uldMovementFilterVO.setFromDate(fDate.setDate(listULDMovementForm.getFromDate()));
	    }
	    LocalDate tDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
	    if(listULDMovementForm.getToDate()!=null && listULDMovementForm.getToDate().trim().length()!=0){
	    uldMovementFilterVO.setToDate(tDate.setDate(listULDMovementForm.getToDate()));
	    }
  
	    /*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		Collection<OneTimeVO> contents=new ArrayList<OneTimeVO>();
		if(oneTimes!=null){
			contents = oneTimes.get("uld.defaults.contentcodes");
		}
		
	    ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldMovementFilterVO);
		reportSpec.addExtraInfo(contents);
		reportSpec.setAction(ACTION);

		generateReport();
		//added by A-4443 for icrd-4490 starts
		if(getErrors() != null && getErrors().size() > 0){

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		//added by A-4443 for icrd-4490 ends
		invocationContext.target = getTargetPage();
    	log.log(Log.FINE, "uldMovementFilterVO------->", uldMovementFilterVO);
	log.exiting("ListULDMovementHistoryCommand","updateFilterVO");
  
	}
	
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode){
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;	
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("uld.defaults.contentcodes");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
