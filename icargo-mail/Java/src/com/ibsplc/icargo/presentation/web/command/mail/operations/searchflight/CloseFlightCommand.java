/*
 * CloseFlightCommand.java Created on June 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2344
 *
 */
public class CloseFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,CloseFlightCommand");

	   /**
	    * TARGET
	    */
	   private static final String CLOSE_SUCCESS = "close_success";
	   private static final String CLOSE_FAILURE = "close_failure";
	   private static final String EMPTY_ULDS = "empty_ulds";

	   private static final String MODULE_NAME = "mail.operations";
	   private static final String SCREEN_ID = "mailtracking.defaults.searchflight";
	   private static final String SCREEN_ID_ACCEPTANCE = "mailtracking.defaults.mailacceptance";





		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering("CloseFlightCommand","execute");
				    	log.log(Log.FINE, "\n\n CloseFlightCommand---------->");
				    	SearchFlightForm searchFlightForm = (SearchFlightForm) invocationContext.screenModel;
				    	MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID_ACCEPTANCE);
						SearchFlightSession searchFlightSession = getScreenSession(MODULE_NAME,
								SCREEN_ID);
						/*String selectedRow = searchFlightForm.getSelectedRow();
						OperationalFlightVO operationalFlightVO = (OperationalFlightVO) (searchFlightSession
								.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));

				    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
				    	searchFlightForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				    	log.log(Log.FINE, "\n\n operationalFlightVO----------> \n"+operationalFlightVO);
				    	try {

						    delegate.closeFlightForReconciliation(operationalFlightVO);


						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}*/
						//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
						//Collection<ErrorVO>errorVOs=new ArrayList<ErrorVO>();
						
						String[] selectedRows = searchFlightForm.getSelectedElements();
						OperationalFlightVO operationalFlightVO = null;
						Collection<ContainerDetailsVO> emptyContainers = new ArrayList<ContainerDetailsVO>();
						Page<OperationalFlightVO> operationalFlightVOs = searchFlightSession.getOperationalFlightVOs();
						//OperationalFlightVO operationalFlightVO = (OperationalFlightVO) (searchFlightSession
							//	.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
						ArrayList<OperationalFlightVO> oplist= new ArrayList<OperationalFlightVO>();

						if (selectedRows != null && selectedRows.length > 0
								&& operationalFlightVOs != null && operationalFlightVOs.size() > 0){
							for (String row : selectedRows) {
								operationalFlightVO = operationalFlightVOs.get(Integer.parseInt(row));
								if(MailConstantsVO.OPERATION_OUTBOUND.equals(searchFlightForm.getFromScreen())){
										operationalFlightVO.setPol(searchFlightForm.getDepartingPort());
								} else if(MailConstantsVO.OPERATION_INBOUND.equals(searchFlightForm.getFromScreen())){
										operationalFlightVO.setPou(searchFlightForm.getArrivalPort());
								}
								oplist.add(operationalFlightVO);
							}
//							if(MailConstantsVO.EXC_RECONCILE.equals(operationalFlightVO.getMailStatus())){
//								errorVOs.add(new ErrorVO("mailtracking.defaults.searchflight.reconsiledcannotbeclosed"));
//								invocationContext.addAllError(errorVOs);
//								invocationContext.target=EMPTY_ULDS;
//								return;
//							}
//							if(MailConstantsVO.EXC_FINALISE.equals(operationalFlightVO.getMailStatus())){
//								errorVOs.add(new ErrorVO("mailtracking.defaults.searchflight.finalisedcannotbeclosed"));
//								invocationContext.addAllError(errorVOs);
//								invocationContext.target=EMPTY_ULDS;
//								return;
//							}
						}
                         searchFlightSession.setOperationalFlightVO(operationalFlightVO);
						
                         if(MailConstantsVO.OPERATION_OUTBOUND.equals(searchFlightForm.getFromScreen())){ 
						try{
				    		emptyContainers = new MailTrackingDefaultsDelegate().findUnacceptedULDs(operationalFlightVO);
				    	}catch(BusinessDelegateException businessDelegateException){
				    		Collection<ErrorVO>errors = handleDelegateException(businessDelegateException);
				    	}
				    	if(emptyContainers != null && emptyContainers.size()>0){
				    		mailAcceptanceSession.setContainerDetailsVOs(emptyContainers);
				    		searchFlightForm.setUldsSelectedFlag("N");
				    		invocationContext.target = EMPTY_ULDS;
				    		return;
				    	}
                         }
				    	

						try{
							new MailTrackingDefaultsDelegate().closeFlightForReconciliation(oplist);
						} catch(BusinessDelegateException businessDelegateException){
							invocationContext.addAllError(handleDelegateException(businessDelegateException));
						}

    		invocationContext.target = CLOSE_SUCCESS;
	   	}

}
