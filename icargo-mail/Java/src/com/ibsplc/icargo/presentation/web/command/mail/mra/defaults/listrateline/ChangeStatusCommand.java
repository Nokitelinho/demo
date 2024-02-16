/*
 * ChangeStatusCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ChangeStatusCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ChangeStatusCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String CHGSTS_SUCCESS = "chgstatus_success";

	private static final String CHGSTS_FAILURE = "chgstatus_failure";

	private static final String KEY_CHANGESTATUS_FAILURE = "mailtracking.mra.defaults.viewupurate.changestatus.failed";

	
	/**
	 * Method  implementing changing of rateline status
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateLineForm listUPURateLineForm = (ListUPURateLineForm)invocationContext.screenModel;

    	ListUPURateLineSession listUPURateLineSession =
    		                  (ListUPURateLineSession)getScreenSession(MODULE_NAME, SCREENID);

    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;

    	Page<RateLineVO> rateLineVOs = listUPURateLineSession.getRateLineVOs();
    	Page<RateLineVO>  selRateLineVOs = new Page<RateLineVO>(new
    			            ArrayList<RateLineVO>(),0,0,0,0,0,false);

    	String[] rowId = listUPURateLineForm.getRowId();
    	//log.log(Log.INFO,"row id in changestatus command" +listUPURateLineForm.getRowId());
    	String selectedId = null;

    	if(rowId != null && rateLineVOs != null){

    		for(int i=0; i < rowId.length; i++){
    			selectedId = rowId[i];
    			selRateLineVOs.add(rateLineVOs.get(Integer.parseInt( selectedId )));
    		}

    		log.log(Log.INFO, "selected RateLineVO <><><><><><><><><><>-->",
					selRateLineVOs);
			listUPURateLineSession.setSelectedRateLineVOs(selRateLineVOs);
    		listUPURateLineForm.setChangeStatusFlag("true");
    		listUPURateLineSession.setRateLineFilterVO(populateFilterVo(listUPURateLineForm));
    		invocationContext.target = CHGSTS_SUCCESS;
    		return;

    	}else{
    		errors.add(new ErrorVO(KEY_CHANGESTATUS_FAILURE));
    		listUPURateLineForm.setChangeStatusFlag("false");
    		invocationContext.target = CHGSTS_FAILURE;
    		return;
    	}

    }
	 
	 private RateLineFilterVO populateFilterVo(ListUPURateLineForm listUPURateLineForm){
		 RateLineFilterVO rateLineFilterVO = new RateLineFilterVO();
		 
		 rateLineFilterVO.setCompanyCode(listUPURateLineForm.getCompanyCode());
		 rateLineFilterVO.setRatelineStatus(listUPURateLineForm.getStatus());
		 rateLineFilterVO.setStartDate(convertToDate(listUPURateLineForm.getFromDate()));
		 rateLineFilterVO.setEndDate(convertToDate(listUPURateLineForm.getToDate()));
		 rateLineFilterVO.setRateCardID(listUPURateLineForm.getRateCardID());
		 rateLineFilterVO.setOrigin(listUPURateLineForm.getOrigin());
		 rateLineFilterVO.setDestination(listUPURateLineForm.getDestination());
		 
		 return rateLineFilterVO;
	 }
	 
	 /**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !("").equals(date)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}


}
