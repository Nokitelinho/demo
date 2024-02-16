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
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Consignment;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;

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
	    public void execute(ActionContext actionContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("PrintMailtagCommand","execute");
	    	  
	    	MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();
	    	ConsignmentSession consignmentSession =
	        		getScreenSession(MODULE_NAME,SCREEN_ID);
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(maintainConsignmentModel.getConsignment().getConsignmentNumber().toUpperCase());
			consignmentFilterVO.setPaCode(maintainConsignmentModel.getConsignment().getPaCode().toUpperCase());
			consignmentFilterVO.setPageNumber(Integer.parseInt(maintainConsignmentModel.getDisplayPage()));
			
			ConsignmentDocumentVO consignmentDocumentVO = null;
			Consignment consignment = new Consignment();
			List<ErrorVO> errors = null;
			
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate()
						.findConsignmentDocumentDetails(consignmentFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			
			ArrayList<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
			if(SELECTED.equals(maintainConsignmentModel.getConsignment().getPrintMailTag()) && !ALL.equals(consignmentSession.getSelectedIndexes()))
		    {
				 int[] selectedIndexes = maintainConsignmentModel.getConsignment().getSelectedMailbagIndex();
			      for (int index : selectedIndexes)
			    	  {
			    	  mailVOs.add(consignmentDocumentVO.getMailInConsignmentVOs().get(index));
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
				    	//newMailbagVO.setWeight(mailbagVO.getStatedWeight());
				    	newMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,mailbagVO.getStatedWeight().getSystemValue(),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
				    	newMailbagVO.setStrWeight(mailbagVO.getStrWeight());
				    	mailbagVOs.add(newMailbagVO);
				}
			}
					
			if((ALLMAILBAGS.equals(maintainConsignmentModel.getConsignment().getPrintMailTag()) && ALL.equals(consignmentSession.getSelectedIndexes())) || (ALL.equals(consignmentSession.getSelectedIndexes()) && SELECTED.equals(maintainConsignmentModel.getConsignment().getPrintMailTag())) || (ALLMAILBAGS.equals(maintainConsignmentModel.getConsignment().getPrintMailTag()) && !ALL.equals(consignmentSession.getSelectedIndexes())))
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
				    	//newMailbagVO.setWeight(mailbagVO.getStatedWeight());
				    	newMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,mailbagVO.getStatedWeight().getSystemValue(),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
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
				generateReport(actionContext);
				return;                                                                                                        
			}                                                
			                                      
	    }                                                                


}
