/*
 * AddMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AddMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
   private static final String TARGET = "success";
   
	 /**   
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddMailCommand","execute");
    	  
    	ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	ConsignmentDocumentVO consignmentDocVO = consignmentSession.getConsignmentDocumentVO();
    	Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocVO.getMailInConsignmentVOs();
    	/*
    	 * Controlling Pagination Values
    	 */
    	int _mailCount = 0;
    	if(mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			_mailCount = mailInConsignmentVOs.getTotalRecordCount();
		}
    	int _pageToBeDisplayed = 1;
    	if(consignmentForm.getLastPageNum() != null && 
    			consignmentForm.getLastPageNum().trim().length() > 0) {
    		int _lst_Pag_Num = 0;
    		try {
    			_lst_Pag_Num = Integer.parseInt(consignmentForm.getLastPageNum());
    			/*
    			 * For the first time, without activating Pagination tag, 
    			 * if user go for adding mail, The LastPageNumber Set by framework
    			 * wont work, so incrementing the same here
    			 */
    			if(_lst_Pag_Num == 0) {
					_lst_Pag_Num = _lst_Pag_Num+2;
				}
    		} catch (NumberFormatException e) {
    			log.log(Log.FINE, "----NumberFormatException---------",
						consignmentForm.getLastPageNum());
    		}
    		/*
    		 * Finding the page to displayed.
    		 */
    		int _Last_Page_Num = _mailCount/MailConstantsVO.MAX_PAGE_LIMIT + 1;	
    		_pageToBeDisplayed = ((_mailCount+1)%MailConstantsVO.MAX_PAGE_LIMIT) == 0 ? (_lst_Pag_Num) : _Last_Page_Num;
    		
			consignmentForm.setDisplayPage(String.valueOf(_pageToBeDisplayed));
			consignmentForm.setLastPageNum(String.valueOf(_pageToBeDisplayed));
    	}
    	/*
    	 * Finding the New Page, with the Last Page Number, found using the above equation.
    	 */
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setConsignmentNumber(consignmentForm.getConDocNo().toUpperCase());
		consignmentFilterVO.setPaCode(consignmentForm.getPaCode().toUpperCase());
		consignmentFilterVO.setPageNumber(_pageToBeDisplayed);
		if(_mailCount > -1){
			consignmentFilterVO.setTotalRecords(_mailCount);
		}else {
			consignmentFilterVO.setTotalRecords(-1);
		}
		try {
			consignmentDocumentVO = new MailTrackingDefaultsDelegate().
			        findConsignmentDocumentDetails(consignmentFilterVO);
					
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		
		List<MailInConsignmentVO> _mailInConsignment = new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Page<MailInConsignmentVO> _mailInConsignment_Page = consignmentDocumentVO.getMailInConsignmentVOs();
		if (_mailInConsignment_Page == null) {			
			if(mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
				/*
				 * Copying the last VO details to create the new VO in the New Page.
				 */
	    		try {
					BeanHelper.copyProperties(mailInConsignmentVO, mailInConsignmentVOs.get(mailInConsignmentVOs.size()-1));
				} catch (SystemException e) {
					log.log(Log.FINE, "---SystemException-----While---using--BeanHelper.copyProperties()");
				}
			}
	    	mailInConsignmentVO.setOperationFlag("I");
	    	mailInConsignmentVO.setStatedBags(0);
	    	//mailInConsignmentVO.setStatedWeight(0);
	    	mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0)); //Added by A-7550
	    	mailInConsignmentVO.setReceptacleSerialNumber(null); 
	    	mailInConsignmentVO.setMailSequenceNumber(0);
	    	mailInConsignmentVO.setHighestNumberedReceptacle("");
	    	mailInConsignmentVO.setRegisteredOrInsuredIndicator("");
	    	
	    	/*
	    	 * Creating a Page, with the new VO added to the list
	    	 */
			_mailInConsignment.add(mailInConsignmentVO);			
	    	int _startIndex = ((_pageToBeDisplayed-1) * MailConstantsVO.MAX_PAGE_LIMIT)+1;
	    	int _endIndex = _pageToBeDisplayed *  MailConstantsVO.MAX_PAGE_LIMIT;
			_mailInConsignment_Page = 
				new Page<MailInConsignmentVO>(_mailInConsignment , 
						_pageToBeDisplayed ,
						MailConstantsVO.MAX_PAGE_LIMIT ,
						_mailInConsignment.size(),
						_startIndex,
						_endIndex,
						false,
						_mailCount);
		}else {
			/*
			 * Copying the last VO details to add to the existing Page.
			 */
    		try {
				BeanHelper.copyProperties(mailInConsignmentVO, _mailInConsignment_Page.get(_mailInConsignment_Page.size()-1));
			} catch (SystemException e) {
				log.log(Log.FINE, "---SystemException-----While---using--BeanHelper.copyProperties()");
			}
			
	    	mailInConsignmentVO.setOperationFlag("I");
	    	mailInConsignmentVO.setStatedBags(0);
	    	//mailInConsignmentVO.setStatedWeight(0);	
	    	mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0));	 //Added by A-7550   	
	    	mailInConsignmentVO.setReceptacleSerialNumber(null);  
	    	mailInConsignmentVO.setMailSequenceNumber(0);
	    	mailInConsignmentVO.setHighestNumberedReceptacle("");
	    	mailInConsignmentVO.setRegisteredOrInsuredIndicator("");	
	    	/*
	    	 * Updating the existing Page with the Copied VO
	    	 */
	    	_mailInConsignment_Page.add(mailInConsignmentVO);
	    	
			if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
				int totalRecords = consignmentDocumentVO.getMailInConsignmentVOs().getTotalRecordCount();
				consignmentSession.setTotalRecords(totalRecords);
			}
    	}
    	consignmentDocumentVO.setMailInConsignmentVOs(_mailInConsignment_Page);
		consignmentDocumentVO.setOperationFlag("U");
    	consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
    	consignmentForm.setTableFocus("M");
		consignmentForm.setAfterPopupSaveFlag("Y");
        consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
        invocationContext.target = TARGET;
        log.exiting("AddMailCommand","execute");
    }       
}
