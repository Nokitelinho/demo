/*
 * ActivateCommand.java Created on Aug 9, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-2391
 * 
 */
public class ActivateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ActivateCommand";

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String RATELINE_EXISTS="mailtracking.mra.defaults.ratelinesexist";
	
	private static final String DETAILS_FAILURE= "details_failure";

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListUPURateLineForm form = (ListUPURateLineForm)invocationContext.screenModel;

		ListUPURateLineSession session = (ListUPURateLineSession)getScreenSession(MODULE, SCREENID);

		Collection<ErrorVO> errors = null;
		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
		
		for (String index : selectedIndexes) {
			rateLineVOs.add(session.getRateLineVOs().get(
					Integer.parseInt(index)));
		
		}
		try {
			new MailTrackingMRADelegate().activateRateLines(rateLineVOs,false);//Modified by a-7871 for ICRD-223130
		}  catch (BusinessDelegateException businessDelegateException) {
			for (String index : selectedIndexes) {
				session.getRateLineVOs().get(
						Integer.parseInt(index)).setRatelineStatus("N");
				log.log(Log.INFO, "ratelinestatus not changed ", session.getRateLineVOs().get(
						Integer.parseInt(index)).getRatelineStatus());
				rateLineVOs.add(session.getRateLineVOs().get(
						Integer.parseInt(index)));
			}
			//errors = handleDelegateException(businessDelegateException);
			errors=handleDelegateException(businessDelegateException);	
			errors = handleErrorMessage(errors);	
			//handles error data from server
			invocationContext.addAllError(errors);
			invocationContext.target = DETAILS_FAILURE;
			return;
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
		 * Creates errorvo collection to be displayed in screen
		 * 
		 * @param errors
		 * @return finErrors 		 
		 * */
		private Collection<ErrorVO> handleErrorMessage(Collection<ErrorVO> errors){
			
			log.entering(CLASS_NAME,"handleErrorMessage");
			
			Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();
			
			if(errors != null && errors.size() > 0){
				
				for(ErrorVO error:errors){
					
					RateLineErrorVO[] rateLineErrorVos = 
						new RateLineErrorVO[error.getErrorData() == null ? 0: error.getErrorData().length];
					
					if(RATELINE_EXISTS.equals(error.getErrorCode())){
						
						System.arraycopy(error.getErrorData(),0,
								rateLineErrorVos,0,error.getErrorData().length);
						
						for(RateLineErrorVO rateLineErrorVO :rateLineErrorVos){
							
							log.log(Log.INFO, "printing the errorVOS>>>>>>>>>",
									rateLineErrorVO);
							finErrors.add(new ErrorVO(RATELINE_EXISTS,
									new String[]{rateLineErrorVO.getNewRateCardID(),
									rateLineErrorVO.getCurrentRateCardID(),
									rateLineErrorVO.getOrigin(),
									rateLineErrorVO.getDestination(),
									rateLineErrorVO.getCurrentValidityStartDate().toDisplayFormat(),
									rateLineErrorVO.getCurrentValidityEndDate().toDisplayFormat()}
							));
						}
					}
				}
			}
				
			log.exiting(CLASS_NAME,"handleErrorMessage");
			return finErrors;
		}	




}
