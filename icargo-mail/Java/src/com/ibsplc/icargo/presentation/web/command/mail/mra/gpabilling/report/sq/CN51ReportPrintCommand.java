/*
 * CN51ReportPrintCommand.java Created on Mar 01,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report.sq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-10383
 *
 */
public class CN51ReportPrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String MODULE = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";
	
	private static final String REPORT_ID = "RPRMRA003";
	private static final String RESOURCE_BUNDLE_KEY = "listcn51cn66";
	
	private static final String ACTION = "generateCN51ReportSQ";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	/**
	 * execute method
	 *@author A-10383
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
				log.entering("MAILTRACKING_GPAILLING","Print Command entered");
				ListCN51CN66Form form = (ListCN51CN66Form)invocationContext.screenModel;
				ListCN51CN66Session session = getScreenSession(MODULE, SCREENID);
				
				String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
				
				log.log(Log.INFO, "gpaCode>>>", session.getCN51CN66FilterVO().getGpaCode().toUpperCase());
				log.log(Log.INFO, "inv num>>>", session.getCN51CN66FilterVO().getInvoiceNumber().toUpperCase());
				CN51CN66FilterVO filterVo = new CN51CN66FilterVO();
				filterVo.setCompanyCode(companyCode);
				filterVo.setGpaCode(session.getCN51CN66FilterVO().getGpaCode().toUpperCase());
				filterVo.setInvoiceNumber(session.getCN51CN66FilterVO().getInvoiceNumber().toUpperCase());
				filterVo.setAirlineCode(form.getAirlineCode());
				Collection<OneTimeVO> oneTimes = getOneTimeDetails();
				log.log(Log.INFO, "onetimevalues in command >>>>>>", oneTimes);
				ReportSpec reportSpec = getReportSpec();
				reportSpec.setReportId(REPORT_ID);
				reportSpec.setProductCode(form.getProduct());
				reportSpec.setSubProductCode(form.getSubProduct());
				reportSpec.setPreview(true);
				reportSpec.setHttpServerBase(invocationContext.httpServerBase);
				reportSpec.addFilterValue(filterVo);
				reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
				reportSpec.addExtraInfo(oneTimes);
				reportSpec.setAction(ACTION);
				
				generateReport();
				
				if(getErrors() != null && getErrors().isEmpty()){
					
					invocationContext.addAllError(getErrors());
					invocationContext.target = PRINT_UNSUCCESSFUL;
					return;
				}
				log.exiting("MRA_GPABILLING","PrintCommand exit");
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
			
				
				try {
					Collection<String> fieldValues = new ArrayList<String>();

					fieldValues.add(CATEGORY_ONETIME);
					oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
							getApplicationSession().getLogonVO().getCompanyCode(),
							fieldValues);
					

				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessageVO().getErrors();
					businessDelegateException.getMessage();
				}
				if(oneTimes != null ){
				oneTimeValues = oneTimes.get(CATEGORY_ONETIME);
				}
				log.exiting("PrintCommand", "getOneTimeDetails");
				return oneTimeValues;
			}
		}
