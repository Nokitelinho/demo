/*
 * ChangeStatusCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author A-2521
 */
public class ChangeStatusCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ChangeStatusCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String CHGSTS_SUCCESS = "chgstatus_success";

	private static final String CHGSTS_FAILURE = "chgstatus_failure";

	private static final String KEY_CHANGESTATUS_FAILURE = 
		"mailtracking.mra.defaults.listupuratecard.changestatusfailed";

	private static final String BLANK = "";

	/**
	 * Method to get the selected VOs for changing status
	 * add keeping those in session
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateCardForm listUPURateCardForm
    	= (ListUPURateCardForm)invocationContext.screenModel;

    	ListUPURateCardSession listUPURateCardSession =
    		(ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;
    	Page<RateCardVO> rateCardVOs = listUPURateCardSession.getRateCardVOs();
    	Page<RateCardVO> selRateCardVOs =  new Page<RateCardVO>(
				new ArrayList<RateCardVO>(),0,0,0,0,0,false);

    	String[] rowId = listUPURateCardForm.getRowCount();
    	String selectedId = null;

    	if(rowId != null && rateCardVOs != null){

    		for(int i=0; i < rowId.length; i++){
    			selectedId = rowId[i];
    			selRateCardVOs.add(rateCardVOs.get(Integer.parseInt( selectedId )));
    		}

    		listUPURateCardSession.setSelectedRateCardVOs(selRateCardVOs);
    		listUPURateCardForm.setChangeStatusFlag("true");
    		listUPURateCardSession.setRateCardFilterVO(populateFilterVO(listUPURateCardForm));
    		invocationContext.target = CHGSTS_SUCCESS;
    	}else{
    		
    		errors.add(new ErrorVO(KEY_CHANGESTATUS_FAILURE));
    		listUPURateCardForm.setChangeStatusFlag("false");
    		invocationContext.addAllError(errors);
    		invocationContext.target = CHGSTS_FAILURE;
    	}
    	
    	log.exiting(CLASS_NAME,"execute");
		return;

    }


    /**
     * populates ratecardfilter to populate  the form fields
     * 
     * @param form
     * @return
     */
    private RateCardFilterVO populateFilterVO(ListUPURateCardForm form){

    	log.entering(CLASS_NAME,"populateFilterVO");
    	RateCardFilterVO filterVO = new RateCardFilterVO();

    	filterVO.setCompanyCode(form.getCompanyCode());
    	filterVO.setRateCardStatus(form.getRateCardStatus());
    	filterVO.setRateCardID(form.getRateCardID());
    	filterVO.setStartDate(convertToDate( form.getStartDate() ));
    	filterVO.setEndDate(convertToDate(form.getEndDate()));
    	log.log(Log.INFO, "selected filterVO-->", filterVO);
		log.exiting(CLASS_NAME,"populateFilterVO");

    	return filterVO;
    }


    /**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}

}
