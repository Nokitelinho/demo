/*
 * ReopenFlightCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;



import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2344
 *
 */
public class ReopenFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,ReopenFlightCommand");

	   /**
	    * TARGET
	    */
	   private static final String TARGET = "reopen_success";

	   private static final String MODULE_NAME = "mail.operations";
	   private static final String SCREEN_ID = "mailtracking.defaults.searchflight";

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering("ReopenFlightCommand","execute");
				    	log.log(Log.FINE, "\n\n ReopenFlightCommand---------->");
				    	SearchFlightForm searchFlightForm = (SearchFlightForm) invocationContext.screenModel;
						SearchFlightSession searchFlightSession = getScreenSession(MODULE_NAME,
								SCREEN_ID);
			//			String selectedRow = searchFlightForm.getSelectedRow();
			//			OperationalFlightVO operationalFlightVO = (OperationalFlightVO) (searchFlightSession
			//					.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
			//
			//	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			//	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
			//
			//
			//	    	log.log(Log.FINE, "\n\n operationalFlightVO----------> \n"+operationalFlightVO);
			//
			//	    	try{
			//	    		delegate.reopenFlight(operationalFlightVO);
			//	    	}catch(BusinessDelegateException businessDelegateException){
			//	    		errors = handleDelegateException(businessDelegateException);
			//	    	}
			//
						//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
						String[] selectedRows = searchFlightForm.getSelectedElements();
						OperationalFlightVO operationalFlightVO = null;
						Page<OperationalFlightVO> operationalFlightVOs = searchFlightSession.getOperationalFlightVOs();
						//OperationalFlightVO operationalFlightVO = (OperationalFlightVO) (searchFlightSession
							//	.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
						ArrayList<OperationalFlightVO> oplist= new ArrayList<OperationalFlightVO>();

						if (selectedRows != null && selectedRows.length > 0
									&& operationalFlightVOs != null && operationalFlightVOs.size() > 0){
								for (String row : selectedRows) {
									operationalFlightVO = operationalFlightVOs.get(Integer.parseInt(row));
									if(MailConstantsVO.OPERATION_OUTBOUND.equals(searchFlightForm.getFromScreen())){
										operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
											operationalFlightVO.setPol(searchFlightForm.getDepartingPort());
									} else if(MailConstantsVO.OPERATION_INBOUND.equals(searchFlightForm.getFromScreen())){
											operationalFlightVO.setPou(searchFlightForm.getArrivalPort());
											operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
									}
									oplist.add(operationalFlightVO);
								}
						}


						try{
							//delegate.reopenFlight(operationalFlightVO);
							new MailTrackingDefaultsDelegate().reopenFlight(oplist);
						} catch(BusinessDelegateException businessDelegateException){
							invocationContext.addAllError(handleDelegateException(businessDelegateException));
						}

						searchFlightForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = TARGET;

	}

}
