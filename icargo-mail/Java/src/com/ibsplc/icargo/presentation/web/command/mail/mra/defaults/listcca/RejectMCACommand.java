/*
* RejectMCACommand.java Created on Jul 18, 2018
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-7540
 *
 */
public class RejectMCACommand extends BaseCommand{
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";
	private static final String LISTCCA_SCREEN = "mailtracking.mra.defaults.listcca";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String REJECT_SUCCESS = "reject_success";
	private static final String REJECTED = "R";
	private static final String REJECT_FAILURE = "reject_failure";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	log.entering("RejectMCACommand", "execute");
	MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
			MODULE_NAME, MAINTAINCCA_SCREEN);
	ListCCASession listccaSession = getScreenSession(MODULE_NAME, LISTCCA_SCREEN);
	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();				
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	ListCCAForm listCCAForm = (ListCCAForm) invocationContext.screenModel;
	Collection<CCAdetailsVO> ccaDetailVOs = listccaSession.getCCADetailsVOs();
    Collection<CCAdetailsVO> selectedCCADetailsVO = new ArrayList<CCAdetailsVO>();
    GPABillingEntriesFilterVO gpaBillingEntriesFiletVO = new GPABillingEntriesFilterVO();

    String[] selectedRows = listCCAForm.getSelectedRows();
    int size = selectedRows.length;
    
    int row = 0;
    int count=0;
    for (CCAdetailsVO ccaDetailsVO : ccaDetailVOs) {
               for (int j = 0; j < size; j++) {
                   		
                     if (row == Integer.parseInt(selectedRows[j])) {
                   		 if("N".equals(ccaDetailsVO.getCcaStatus())){
                                 count=count+1;
                                 ccaDetailsVO.setCcaStatus(REJECTED);
                                 ccaDetailsVO.setCcaType(REJECTED);
                                 selectedCCADetailsVO.add(ccaDetailsVO);
                               }                                              
                           }
                   		
                   	 }
                    row++;
                  }
					try {
						mailTrackingMRADelegate.saveAutoMCAdetails(selectedCCADetailsVO,gpaBillingEntriesFiletVO);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}  
        	Object[] obj = {count};
        	ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.listcca.msg.info.numofccaRej",obj);
        	errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
        	errors.add(errorVO);
        	invocationContext.addAllError(errors);
        	maintainCCASession.setCCAdetailsVOs(selectedCCADetailsVO);
        	invocationContext.target = REJECT_SUCCESS;

              }
	       } 
       
