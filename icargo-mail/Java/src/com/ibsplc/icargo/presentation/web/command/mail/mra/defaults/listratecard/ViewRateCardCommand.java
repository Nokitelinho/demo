/*
 * ViewRateCardCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author A-2521
 */
public class ViewRateCardCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ViewRateCardCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String SCREENID_LISTRATELINE = "mailtracking.mra.defaults.viewupurate";

	private static final String VIEW_SUCCESS = "view_success";

	private static final String VIEW_FAILURE = "view_failure";

	private static final String BLANK = "";

	/**
	 * Method to implement the viewing of ratecard details
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

    	ListUPURateLineSession session =
    		(ListUPURateLineSession)getScreenSession(MODULE_NAME, SCREENID_LISTRATELINE);

    	Page<RateCardVO> rateCardVOs = listUPURateCardSession.getRateCardVOs();
    	RateCardVO rateCardVO = null;
    	String[] rowId = listUPURateCardForm.getRowCount();

    	String selectedId = rowId == null ? BLANK : rowId[0];
    	rateCardVO = BLANK.equals(selectedId) ? null :
    		rateCardVOs.get(Integer.parseInt( selectedId ));

    	log.log(Log.INFO, "selected RateCardVO-->", rateCardVO);
		session.setRateLineFilterVO(populateFilterVO(rateCardVO));
    	listUPURateCardSession.setRateCardFilterVO(populateFilterVO(listUPURateCardForm));

   		invocationContext.target = VIEW_SUCCESS;
		return;

//    	if(rowId != null && rateCardVOs != null){
//
//    		for(int i=0; i < rowId.length; i++){
//    			selectedId = rowId[i];
//    		}
//
//    		rateCardVO = rateCardVOs.get(Integer.parseInt( selectedId ));
//    		log.log(Log.INFO,"selected RateCardVO-->" + rateCardVO);
//    		populateFilterVO(rateCardVO);
//       		invocationContext.target = VIEW_SUCCESS;
//    		return;
//    	}
    }

    /**
     * populates filter to be passed to view screen
     *
     * @param rateCardVO
     * @return filterVO
     */
    private RateLineFilterVO populateFilterVO(RateCardVO rateCardVO){

    	log.entering(CLASS_NAME,"populateFilterVO");
    	RateLineFilterVO filterVO = new RateLineFilterVO();

    	if(rateCardVO != null){

    		filterVO.setCompanyCode(rateCardVO.getCompanyCode());
        	filterVO.setRateCardID(rateCardVO.getRateCardID());
//        	filterVO.setStartDate(rateCardVO.getValidityStartDate());
//        	filterVO.setEndDate(rateCardVO.getValidityEndDate());
    	}

    	log.log(Log.INFO, "selected filterVO-->", filterVO);
		log.exiting(CLASS_NAME,"populateFilterVO");
    	return filterVO;
    }

    /**
     * populates ratecardfilter 
     * 
     * @param form
     * @return
     */
    private RateCardFilterVO populateFilterVO(ListUPURateCardForm form){

    	log.entering(CLASS_NAME,"populateFilterVO");
    	RateCardFilterVO filterVO = new RateCardFilterVO();

    	filterVO.setCompanyCode(form.getCompanyCode());
    	filterVO.setRateCardID(form.getRateCardID());
    	filterVO.setRateCardStatus(form.getRateCardStatus());
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
