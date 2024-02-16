/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.report.PrintMailtagCommand.java
 *
 *	Created by	:	A-7871
 *	Created on	:	06-Feb-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PrintMailtagCommand extends AbstractPrintCommand{

	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	    private static final String REPORT_ID = "RPRMTK085";
	    private static final String PRODUCTCODE = "mail";
		private static final String SUBPRODUCTCODE = "operations";
		private static final String ACTION = "generateMailTag";
		private static final String MODULE_NAME = "mail.operations";
		 private static final String SCREEN_ID = "mailtracking.defaults.consignment";
		private static final String NORMAL_REPORT_ERROR = "normal-report-error-jsp";
		private static final String SELECTED = "SELECTED";
		private static final String ALL = "ALL";
		private static final String ALLMAILBAGS = "ALLMAILBAGS";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("PrintMailtagCommand","execute");
	    	  
	    	ConsignmentForm consignmentForm = 
	    		(ConsignmentForm)invocationContext.screenModel;
	    	ConsignmentSession consignmentSession =
	        		getScreenSession(MODULE_NAME,SCREEN_ID);
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			
			ArrayList<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
			Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
			if(SELECTED.equals(consignmentForm.getPrintMailTag()) && !ALL.equals(consignmentSession.getSelectedIndexes()))
		    {
				 String[] selectedIndexes = consignmentSession.getSelectedIndexes().split("-");
			      for (String index : selectedIndexes)
			    	  {
			    	  mailVOs.add(consignmentDocumentVO.getMailInConsignmentVOs().get(Integer.parseInt(index)));
			    	  }
				 for(MailInConsignmentVO mailbagVO:mailVOs) {
					  MailbagVO newMailbagVO = new MailbagVO();
						newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				    	newMailbagVO.setArrivedFlag("N");
				    	newMailbagVO.setDeliveredFlag("N");
				    	newMailbagVO.setOperationalFlag(mailbagVO.getOperationFlag());
				    	newMailbagVO.setOoe(mailbagVO.getOriginExchangeOffice());
					    newMailbagVO.setDoe(mailbagVO.getDestinationExchangeOffice());
				    	newMailbagVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
				    	newMailbagVO.setMailClass(mailbagVO.getMailClass());
				    	newMailbagVO.setYear(mailbagVO.getYear());
				    	newMailbagVO.setDespatchSerialNumber(mailbagVO.getDsn());
				    	newMailbagVO.setReceptacleSerialNumber(mailbagVO.getReceptacleSerialNumber());
				    	newMailbagVO.setHighestNumberedReceptacle(mailbagVO.getHighestNumberedReceptacle());
				    	newMailbagVO.setRegisteredOrInsuredIndicator(mailbagVO.getRegisteredOrInsuredIndicator());
				    	newMailbagVO.setMailbagId(mailbagVO.getMailId());
				    	newMailbagVO.setWeight(mailbagVO.getStatedWeight());
				    	newMailbagVO.setStrWeight(mailbagVO.getStrWeight());
				    	mailbagVOs.add(newMailbagVO);
				}
			}
					
			if((ALLMAILBAGS.equals(consignmentForm.getPrintMailTag()) && ALL.equals(consignmentSession.getSelectedIndexes())) || (ALL.equals(consignmentSession.getSelectedIndexes()) && SELECTED.equals(consignmentForm.getPrintMailTag())) || (ALLMAILBAGS.equals(consignmentForm.getPrintMailTag()) && !ALL.equals(consignmentSession.getSelectedIndexes())))
			{
				  mailVOs.addAll(consignmentDocumentVO.getMailInConsignmentVOs());
			  for(MailInConsignmentVO mailbagVO:mailVOs) {
					  MailbagVO newMailbagVO = new MailbagVO();
						newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				    	newMailbagVO.setArrivedFlag("N");
				    	newMailbagVO.setDeliveredFlag("N");
				    	newMailbagVO.setOperationalFlag(mailbagVO.getOperationFlag());
				    	newMailbagVO.setOoe(mailbagVO.getOriginExchangeOffice());
				    	newMailbagVO.setDoe(mailbagVO.getDestinationExchangeOffice());
				    	newMailbagVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
				    	newMailbagVO.setMailClass(mailbagVO.getMailClass());
				    	newMailbagVO.setYear(mailbagVO.getYear());
				    	newMailbagVO.setDespatchSerialNumber(mailbagVO.getDsn());
				    	newMailbagVO.setReceptacleSerialNumber(mailbagVO.getReceptacleSerialNumber());
				    	newMailbagVO.setHighestNumberedReceptacle(mailbagVO.getHighestNumberedReceptacle());
				    	newMailbagVO.setRegisteredOrInsuredIndicator(mailbagVO.getRegisteredOrInsuredIndicator());
				    	newMailbagVO.setMailbagId(mailbagVO.getMailId());
				    	newMailbagVO.setWeight(mailbagVO.getStatedWeight());
				    	newMailbagVO.setStrWeight(mailbagVO.getStrWeight());
				    	mailbagVOs.add(newMailbagVO);
				  }
				
				
			}
			log.log(Log.FINE, "mailbagVOs to print :", mailbagVOs);
			
			if(mailbagVOs != null && mailbagVOs.size() > 0){                                  
				ReportSpec reportSpec = getReportSpec();				
				reportSpec.setReportId(REPORT_ID);
				reportSpec.setProductCode(PRODUCTCODE);
				reportSpec.setSubProductCode(SUBPRODUCTCODE);
				reportSpec.addFilterValue(mailbagVOs);
				reportSpec.setResourceBundle("printMailTagResources");
				reportSpec.setAction(ACTION);		
				generateReport();
				invocationContext.target = getTargetPage();
				return;                                                                                                        
			}                                                
			                    
			invocationContext.target = NORMAL_REPORT_ERROR;                   
	    }                                                                


}
