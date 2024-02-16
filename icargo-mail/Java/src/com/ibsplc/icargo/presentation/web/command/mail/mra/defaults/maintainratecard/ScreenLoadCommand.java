/*
 * ScreenLoadCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Maintain UPURateCard ScreenloadCommand");
	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String KEY_BILLING_TYPE_ONETIME = "mra.gpabilling.ratestatus";
	private static final String KEY_CARD_LEVEL_ONETIME = "mail.mra.ratecar.orgdstlevel";
	private static final String BLANK = "";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	
    	LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String companyCode = logonAttributes.getCompanyCode();
    	
    	MaintainUPURateCardSession session=null;
    	MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
    	
   		session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CARD_LEVEL_ONETIME);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		if(errors != null && errors.size() > 0  ){
   			invocationContext.addAllError(errors);
   			invocationContext.target = ACTION_SUCCESS;
   			
   			return;
   		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
	
		if((form.getPageFlag()!=null)&&form.getPageFlag().trim().length()>0 && ("fromOtherPages").equals(form.getPageFlag())){
		if(session.getRateCardDetails()!=null){
			log.log(Log.INFO,"Inside Rate card Session present");
			RateCardVO rateCardVO=session.getRateCardDetails();
			
			form.setRateCardId(rateCardVO.getRateCardID());
			form.setDescription(rateCardVO.getRateCardDescription());
			String status=null;
			Collection<OneTimeVO> oneTimeVOs=session.getOneTimeVOs().get(KEY_BILLING_TYPE_ONETIME);
			for(OneTimeVO oneTimeVO:oneTimeVOs){
				if(oneTimeVO.getFieldValue().equals(rateCardVO.getRateCardStatus())){
					status = oneTimeVO.getFieldDescription();
				}
			}
			form.setStatus(status);
			if(RateCardVO.OPERATION_FLAG_INSERT.equals(rateCardVO.getOperationFlag())){
				form.setScreenStatus("new");
				form.setListStatus("N");
				form.setOpFlag("I");
			}
			else{
				form.setListStatus(rateCardVO.getRateCardStatus());
				form.setScreenStatus("list");
			}
		
			if(rateCardVO.getValidityStartDate()!=null){
   				String fromdat=rateCardVO.getValidityStartDate().toDisplayDateOnlyFormat();
   				form.setValidFrom(fromdat);
   			}
   			if(rateCardVO.getValidityEndDate()!=null){
   				String todat=rateCardVO.getValidityEndDate().toDisplayDateOnlyFormat();
   				form.setValidTo(todat);
   			}
   			/* CHANGE ADDED BY INDU FOR BUG MRA3 STARTED*/
   			LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			if(!BLANK.equals(form.getValidTo())&& form.getValidTo().trim().length()>0){
				LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				toDate.setDate(form.getValidTo());
				if(toDate.before(currentDate)){
					form.setStatus("Expired");
				}
			}
			/* CHANGE ADDED BY INDU FOR BUG MRA3 ENDS*/
			form.setMialDistFactor(String.valueOf(rateCardVO.getMailDistanceFactor()));
			form.setSvTkm(String.valueOf(rateCardVO.getCategoryTonKMRefOne()));
			form.setSalTkm(String.valueOf(rateCardVO.getCategoryTonKMRefTwo()));
			form.setAirmialTkm(String.valueOf(rateCardVO.getCategoryTonKMRefThree()));
			log.log(Log.INFO,
					"\n\nrate card VO in Maintain UPU Rate Screen load-->",
					rateCardVO);

		}
		}
		else{
			form.setRateCardId("");
	   		form.setStatus("");
	   		form.setDescription("");
	   		form.setValidFrom("");
	   		form.setValidTo("");
	   		form.setMialDistFactor("");
	   		form.setSvTkm("");
	   		form.setSalTkm("");
	   		form.setAirmialTkm("");
	   		session.removeRateLineDetails();
		  form.setScreenStatus("screenload");
		}

    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

    }


}
