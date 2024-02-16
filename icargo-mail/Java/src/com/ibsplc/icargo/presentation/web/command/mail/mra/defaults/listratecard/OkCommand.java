/*
 * OkCommand.java Created on Feb 10, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-5166
 *
 */
public class OkCommand extends BaseCommand{
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");
	
	private static final String CLASS_NAME = "OkCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String ACTION_SUCCESS = "ok_success";
	
	private static final String ACTION_FAILURE = "ok_failure";
	private static final String TKM_VALUES_SAME="mailtracking.mra.defaults.copyrate.msg.wrn.sametkmvalues";
	public static final String RATELINE_EXIST = "mailtracking.mra.defaults.ratelinesexist";
	
	/**
	 * Method to implement the copying of rate card
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
    	errors = validateForm(listUPURateCardForm, errors);
    	if(errors !=null && errors.size()>0){
    		invocationContext.addAllError(errors);
    		listUPURateCardForm.setCopyFlag("false");   
    		invocationContext.target = ACTION_FAILURE;
    		return;  
    	}

    	RateCardVO rateCardVO = listUPURateCardSession.getSelectedRateCardVO();
    	
    	LocalDate fromdate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	LocalDate todate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

    	rateCardVO.setNewRateCardID(listUPURateCardForm.getRateCardIDNew());
    	rateCardVO.setNewMailDistFactor(Double.parseDouble(listUPURateCardForm.getMailDistFactor()));
    	rateCardVO.setNewCategoryTonKMRefOne(Double.parseDouble(listUPURateCardForm.getSvTkm()));
    	rateCardVO.setNewCategoryTonKMRefTwo(Double.parseDouble(listUPURateCardForm.getSalTkm()));
    	rateCardVO.setNewCategoryTonKMRefThree(Double.parseDouble(listUPURateCardForm.getAirmialTkm()));
    	rateCardVO.setNewValidStartDate(fromdate.setDate(listUPURateCardForm.getValidFrom()));
    	rateCardVO.setNewValidEndDate(todate.setDate(listUPURateCardForm.getValidTo()));
    	listUPURateCardForm.setCopyFlag("true");
    	try {
    		new MailTrackingMRADelegate().saveCopyRateCard(rateCardVO);
    	} catch (BusinessDelegateException e) {
    	
    		errors.addAll(handleDelegateException(e));
    		
    	}   
    	if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		
   		listUPURateCardForm.setCopyFlag("false");   
    		invocationContext.target = ACTION_FAILURE;
    	}else{
    		ErrorVO error = null;
		Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
		error = new ErrorVO("mailtracking.mra.defaults.ratecardcopied");
         error.setErrorDisplayType(ErrorDisplayType.INFO);
         saveerrors.add(error);
		invocationContext.addAllError(saveerrors);
    	invocationContext.target = ACTION_SUCCESS;  
    	log.exiting(CLASS_NAME, "execute");  
    	}
    }   
  
    private Collection<ErrorVO> validateForm(ListUPURateCardForm form, 
    		Collection<ErrorVO> errors){
    	Collection<ErrorVO> finerrors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;  
    	if(form.getSvTkm()!=null && form.getSvTkm().trim().length()>0 && 
    			form.getSalTkm()!=null && form.getSalTkm().trim().length()>0 && 
    			form.getAirmialTkm()!=null && form.getAirmialTkm().trim().length()>0)	{
	   		if(Double.parseDouble(form.getSvTkm())==Double.parseDouble(form.getSalTkm()) || 
	   				Double.parseDouble(form.getSvTkm())==Double.parseDouble(form.getAirmialTkm()))	{
   			//form.setScreenStatus("new");
			//form.setStatus("New");
			error=new ErrorVO(TKM_VALUES_SAME);
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			errors.add(error);
	   		}
   		}
    	if(!form.getValidFrom().equals(form.getValidTo())){

    		if (!DateUtilities.isLessThan(form.getValidFrom(), form.getValidTo(),
    		"dd-MMM-yyyy")) {
    			error = new ErrorVO(
    			"mailtracking.mra.defaults.err.fromDateGreaterThanToDate");
    			error.setErrorDisplayType(ErrorDisplayType.ERROR);
    			finerrors.add(error);    
    		}
    	} 
    	if(("0").equals(form.getMailDistFactor()) || ("0.0").equals(form.getMailDistFactor())){
    		error = new ErrorVO("mailtracking.mra.defaults.err.maildistfactornull");
    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		finerrors.add(error);
    	}
    	if(("0").equals(form.getSvTkm()) || ("0.0").equals(form.getSvTkm())){
    		error = new ErrorVO("mailtracking.mra.defaults.err.emstkmnull");
    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		finerrors.add(error);  
    	}   
    	if(("0").equals(form.getSalTkm())|| ("0.0").equals(form.getSalTkm())){
    		error = new ErrorVO("mailtracking.mra.defaults.err.saltkmnull");
    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		finerrors.add(error);
    	}
    	if(("0").equals(form.getAirmialTkm()) || ("0.0").equals(form.getAirmialTkm())){
    		error = new ErrorVO("mailtracking.mra.defaults.err.airmaltkmnull");
    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		finerrors.add(error);
    	}
   	return finerrors;
    }
}
     