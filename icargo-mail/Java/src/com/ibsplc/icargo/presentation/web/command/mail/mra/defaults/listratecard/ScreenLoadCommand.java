/*
 * ScreenLoadCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");
	

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String SCREEN_SUCCESS = "screen_success";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";

	private static final String STATUS_VIEWSCREEN = "viewrateline";

	private static final String CHGSTATUS_SCREEN = "changestatus";

	private static final String BLANK = "";


	/**
	 * Method that loads the rate card depending on filter criteria given
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	Map oneTimeHashMap 							= null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		//Page<RateCardVO> rateCardVOs = new Page<RateCardVO>(new ArrayList<RateCardVO>(),0,0,0,0,0,false);
		Collection<ErrorVO> errors = null;
		ListUPURateCardSession listUPURateCardSession =
			(ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);

		ListUPURateCardForm listUPURateCardForm
    	= (ListUPURateCardForm)invocationContext.screenModel;

		if(STATUS_VIEWSCREEN.equals(listUPURateCardForm.getViewStatus())){
			log.log(Log.INFO,"/n/n******** STATUS_VIEWSCREEN.size()**");
			listUPURateCardForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			populateFormFields(listUPURateCardForm, listUPURateCardSession.getRateCardFilterVO());

		}else if(CHGSTATUS_SCREEN.equals(listUPURateCardForm.getViewStatus())){

			errors = listUPURateCardSession.getErrorVOs();

			if(errors != null && errors.size() > 0){

				invocationContext.addAllError(errors);
			}
			populateFormFields(listUPURateCardForm, listUPURateCardSession.getRateCardFilterVO());

		}else {

			listUPURateCardSession.removeAllAttributes();
			//listUPURateCardSession.removeSelectedRateCardVOs();
			oneTimeActiveStatusList.add(STATUS_ONETIME);

			try {
				oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues
	    							(getApplicationSession().getLogonVO().getCompanyCode(), oneTimeActiveStatusList);
				listUPURateCardSession.setStatusOneTime(
						(HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap );
			
			} catch (BusinessDelegateException e) {

				e.getMessage();
				handleDelegateException( e );
			}
			
			listUPURateCardForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

			
		}

		invocationContext.target = SCREEN_SUCCESS;
    }

    /**
     * populates form fields
     * 
     * @param form
     * @param rateCardFilterVO
     * @return
     */
    private ListUPURateCardForm populateFormFields(
    		ListUPURateCardForm form, RateCardFilterVO rateCardFilterVO){

    	if(rateCardFilterVO != null){


    		form.setRateCardID(rateCardFilterVO.getRateCardID());
        	form.setRateCardStatus(rateCardFilterVO.getRateCardStatus());
        	form.setStartDate(checkValue(rateCardFilterVO.getStartDate()));
        	form.setEndDate(checkValue(rateCardFilterVO.getEndDate()));
    	}

    	return form;

    }

    /**
	 * Checks obj, and converts it to suitable string for display in form
	 * @param obj
	 * @return
	 */
	private String checkValue(LocalDate obj){

		log.entering(CLASS_NAME, "checkValue");

		String returnStr = BLANK;

		if(obj != null){
			returnStr = obj.toDisplayFormat();
		}

		log.log(Log.FINE, "returnStr", returnStr);
		return returnStr;
	}
}
