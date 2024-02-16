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
package com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author 204569
 *
 */
public class PrintMailtagCommand extends AbstractPrintCommand{

	
	private static final Log LOGGER = LogFactory.getLogger("OPERATIONS PrintMailtagCommand");
		
	   /**
	    * TARGET
	    */
	    private static final String REPORT_ID = "RPRMAL001";
		private static final String MODULE_NAME = "mail.operations";
		 private static final String SCREEN_ID = "mailtracking.defaults.consignment";
		private static final String SELECTED = "SELECTED";
		private static final String ALL = "ALL";
		private static final String ALLMAILBAGS = "ALLMAILBAGS";
		private static final String PRINTER_OFFICE_CODE = "admin.user.printerofficecode";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param ActionContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(ActionContext actionContext)
	            throws CommandInvocationException {
	    	
	    	LOGGER.entering("PrintMailtagCommand","execute");
	    	  
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
			
			
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate()
						.findConsignmentDocumentDetails(consignmentFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
		if (consignmentDocumentVO != null && !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()) {
			Collection<MailInConsignmentVO> mailVOs = new ArrayList<>();
			if (SELECTED.equals(maintainConsignmentModel.getConsignment().getPrintMailTag())
					&& !ALL.equals(consignmentSession.getSelectedIndexes())) {
				int[] selectedIndexes = maintainConsignmentModel.getConsignment().getSelectedMailbagIndex();
				for (int index : selectedIndexes) {
					mailVOs.add(consignmentDocumentVO.getMailInConsignmentVOs().get(index));
				}

			}

			if ((ALLMAILBAGS.equals(maintainConsignmentModel.getConsignment().getPrintMailTag())
					&& ALL.equals(consignmentSession.getSelectedIndexes()))
					|| (ALL.equals(consignmentSession.getSelectedIndexes())
							&& SELECTED.equals(maintainConsignmentModel.getConsignment().getPrintMailTag()))
					|| (ALLMAILBAGS.equals(maintainConsignmentModel.getConsignment().getPrintMailTag())
							&& !ALL.equals(consignmentSession.getSelectedIndexes()))) {
				mailVOs.addAll(consignmentDocumentVO.getMailInConsignmentVOs());
			}
			  int [] space = { 6, 12, 13, 15, 16, 20, 23, 24, 25 };
			for (MailInConsignmentVO mailbagVO : mailVOs) {
				getReportSpec().setReportId(REPORT_ID);
				String mailbagIdIntegrated=	spaceintegration(mailbagVO.getMailId(), space);
				getReportSpec().addApiPrintData("labelType", "MAILBAG");
				
				getReportSpec().addApiPrintData("mailBagId", mailbagIdIntegrated);
				if (Objects.nonNull(logonAttributes.getUserParameterMap()) &&
						logonAttributes.getUserParameterMap().containsKey(PRINTER_OFFICE_CODE)) {
		    	getReportSpec().addApiPrintData("officeCode", logonAttributes.getUserParameterMap().get(PRINTER_OFFICE_CODE));
		    	}
				getReportSpec().addApiPrintData("port", logonAttributes.getAirportCode());
				generateReport(actionContext);

			}}
			LOGGER.exiting("PrintMailtagCommand", "execute");
	    }
	    
	    static String spaceintegration(String s, int []sp)
	    {
	      int mailBagLength = s.length();
	      int spaceLength = sp.length;
	      int l = 0;
	      int r = 0;
	      String res = newstr(mailBagLength + spaceLength, ' ');
	   
	      
	      for (int i = 0; i < mailBagLength + spaceLength; i++) {
	   
	        if (l < spaceLength && i == sp[l] + l){
	          l++;
	        }
	        else{
	          res = res.substring(0,i)+s.charAt(r++)+res.substring(i+1);
	      }
	      }
	   
	     
	      return res;
	    }
	 
	 static String newstr(int i, char c) {
		  
		    StringBuilder stringBuilder = new StringBuilder("");
		    for (int j = 0; j < i; j++) {
		     
		    	stringBuilder.append(c);
		    }
		    return stringBuilder.toString();
		  }

}
