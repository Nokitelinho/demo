/*
 * PrintAllCommand.java Created on SEPT 18,2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ListCN51SessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5137
 *
 */
public class PrintAllCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String MODULE = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";
	
	private static final String REPORT_ID_CN51 = "RPRMTK083";
	private static final String RESOURCE_BUNDLE_KEY = "listcn51cn66";
	
	private static final String ACTION_CN51 = "generateCN51ReportFromCN51Cn66";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";	
	private static final String CN66_PRINT = "cn66_print";
	private static final String CN51_PRINT = "cn51_print";
	private static final String PRINTING_FINISHED = "FINISHED";
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	
	
	private static final String REPORT_ID_CN66 = "RPRMTK084";
	
	private static final String ACTION_CN66 = "generateCN66Report6E";


	
	
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("PrintAllCommand","execute");
		ListCN51Form form = (ListCN51Form)invocationContext.screenModel;		
		ListCN51SessionImpl session = (ListCN51SessionImpl)getScreenSession(
				MODULE, SCREENID);
		

		/*
		 * This block should be executed only once in the printing cycle 
    	 * for ICRD-12690 printALL reports
    	 * initial_print means printing is about to start and it will start with CN51 
    	 */
		if(form.getSelIdx()!=null&&form.getSelIdx().trim().length()>0){		
			ArrayList<String> selIdxs = (ArrayList<String>) TextFormatter.convertToCollection(form.getSelIdx(), ";");
			session.setPrintALLSelIdxs(selIdxs);			    	
			session.setCN51CN66Toggler(CN51_PRINT);			
			form.setSelIdx(null);
		}
	
		 Page<CN51SummaryVO> cN51SummaryVOS =  (Page<CN51SummaryVO>)session.getCN51SummaryVOs();
		 CN51SummaryVO cN51SummaryVO = null;
		 if(session.getPrintALLSelIdxs()!=null&&session.getPrintALLSelIdxs().size()>0&&cN51SummaryVOS!=null&&cN51SummaryVOS.size()>0){
		   cN51SummaryVO = cN51SummaryVOS.get(Integer.parseInt(session.getPrintALLSelIdxs().get(0)));
		   if(cN51SummaryVO!=null){
			if(CN51_PRINT.equalsIgnoreCase(session.getCN51CN66Toggler())){				
				log.log(Log.FINE,
						"GENERATING CN51 FOR invoice number -------->>",
						cN51SummaryVO.getInvoiceNumber().toUpperCase());
				String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
				CN51CN66FilterVO filterVo = new CN51CN66FilterVO();
				filterVo.setCompanyCode(companyCode);
				filterVo.setGpaCode(cN51SummaryVO.getGpaCode().toUpperCase());
				filterVo.setInvoiceNumber(cN51SummaryVO.getInvoiceNumber().toUpperCase());
				filterVo.setAirlineCode(form.getAirlinecode());
				filterVo.setPageNumber(Integer.parseInt(form.getDisplayPage()));
				Collection<OneTimeVO> oneTimes = getOneTimeDetails();
				ReportSpec reportSpec = getReportSpec();
				reportSpec.setReportId(REPORT_ID_CN51);
				reportSpec.setProductCode(form.getProduct());
				reportSpec.setSubProductCode(form.getSubProduct());
				reportSpec.setPreview(false);
				reportSpec.setHttpServerBase(invocationContext.httpServerBase);
				reportSpec.addFilterValue(filterVo);
				reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
				reportSpec.addExtraInfo(oneTimes);
				reportSpec.setAction(ACTION_CN51);	
				//set CN66 as the report to print for next iteration
				session.setCN51CN66Toggler(CN66_PRINT);
				generateReport();				
			}else{
				log.log(Log.FINE,
						"GENERATING CN66 FOR invoice number -------->>",
						cN51SummaryVO.getInvoiceNumber().toUpperCase());
				String companyCode = getApplicationSession().getLogonVO().getCompanyCode();	
				CN51CN66FilterVO filterVo = new CN51CN66FilterVO();
				filterVo.setCompanyCode(companyCode);
				filterVo.setGpaCode(cN51SummaryVO.getGpaCode().toUpperCase());
				filterVo.setInvoiceNumber(cN51SummaryVO.getInvoiceNumber().toUpperCase());
				filterVo.setAirlineCode(form.getAirlinecode());
				filterVo.setPageNumber(Integer.parseInt(form.getDisplayPage()));
				Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetailsFORCN66();
				ReportSpec reportSpec = getReportSpec();
				reportSpec.setReportId(REPORT_ID_CN66);
				reportSpec.setProductCode(form.getProduct());
				reportSpec.setSubProductCode(form.getSubProduct());
				reportSpec.setPreview(false);
				reportSpec.setHttpServerBase(invocationContext.httpServerBase);
				reportSpec.addFilterValue(filterVo);
				reportSpec.addExtraInfo(oneTimes);
				reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
				reportSpec.setAction(ACTION_CN66);
				//set CN51 as the report to print for next iteration
				session.setCN51CN66Toggler(CN51_PRINT);
				//remove the selection after both CN51 and CN66 is printed for that particular invoice
				session.getPrintALLSelIdxs().remove(0);
				if(session.getPrintALLSelIdxs()!=null&& session.getPrintALLSelIdxs().size()==0){
					reportSpec.setClientParameters(PRINTING_FINISHED);
				}else if(session.getPrintALLSelIdxs()==null){
					reportSpec.setClientParameters(PRINTING_FINISHED);
				}
				generateReport();			
		   }	
			
		   }
			
		 }
		
		if(getErrors() != null && getErrors().size() > 0){					
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.exiting("PrintAllCommand","execute");
		invocationContext.target = getTargetPage();
		
		
		
		
	}
	
	/**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public  Collection<OneTimeVO> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<OneTimeVO> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(CATEGORY_ONETIME);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);
			

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		if(oneTimes != null ){
		oneTimeValues = oneTimes.get(CATEGORY_ONETIME);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimeValues;
	}

	/**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetailsFORCN66() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(CATEGORY_ONETIME);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}
	
}




