/*
 * ListCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
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
public class ListCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ListScreenloadCommand");
	private static final String CLASS_NAME = "List Command";
	private static final String RATECARDID_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.ratecardidnotnull";
	private static final String DETAILS_NULL="mailtracking.mra.defaults.msg.err.detailsnull";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String DETAILS_FAILURE= "details_failure";
	private static final String BLANK= "";
	private static final String DECIMAL_FORMAT = "0.0000";
	private static final double DECIMAL_CEIL_VALUE = 0.001;
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error=null;
		RateCardVO ratecardvo=null;
		MaintainUPURateCardSession session=null;
		session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);		
		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String rateCardId=form.getRateCardId();
		if(!BLANK.equals(rateCardId)&& rateCardId.trim().length()>0){
			try{				
				ratecardvo=new RateCardVO();
				String displayPage=form.getDisplayPage();
				int pagenum=1;
				if(displayPage!=null && displayPage.trim().length()>0 ){    
				pagenum=Integer.parseInt(displayPage);                        
				}
				ratecardvo=new MailTrackingMRADelegate().findRateCardDetails(companyCode,rateCardId,pagenum);				
			}
			catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);
				
			}	
		}
		else{
			error=new ErrorVO(RATECARDID_MUST_NOT_BE_NULL);
			errors.add(error);
			
		}
		
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!RATECARDID_MUST_NOT_BE_NULL!= null");
			invocationContext.addAllError(errors);
			session.removeRateLineDetails();			
			invocationContext.target=DETAILS_FAILURE;
			return;
		}
		if(ratecardvo!=null){
			log.log(Log.FINE, "Ratecard VO from Server ", ratecardvo);
			setFormvalues(ratecardvo,form);			
			session.setRateCardDetails(ratecardvo);
			if(ratecardvo.getRateLineVOss()!=null){
				session.setRateLineDetails(ratecardvo.getRateLineVOss());
			}	
		}
			else{
				form.setScreenStatus("new");
				session.removeRateCardDetails();
				session.removeRateLineDetails();
				form.setStatus("New");
				error=new ErrorVO(DETAILS_NULL);
				errors.add(error);
			}			
		
		
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			session.removeRateLineDetails();			
			invocationContext.target=DETAILS_FAILURE;
			return;
		}
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("ListCommand", "execute");
		
	}
	
	/**
	 * 
	 * @param ratecardvo
	 * @param form
	 */
	private void setFormvalues(RateCardVO ratecardvo,MaintainUPURateCardForm form){
		

		int flag=0;		
		int cflag=0;
		form.setRateCardId(ratecardvo.getRateCardID());
		if(!BLANK.equals(ratecardvo.getRateCardStatus())&& ratecardvo.getRateCardStatus().trim().length()>0){			
			if(("C").equals(ratecardvo.getRateCardStatus())){				
				form.setStatus("Cancelled");
			}
			if(("I").equals(ratecardvo.getRateCardStatus())){				
				form.setStatus("Inactive");
			}
			if(("A").equals(ratecardvo.getRateCardStatus())){				
				form.setStatus("Active");
			}
			if(("N").equals(ratecardvo.getRateCardStatus())){				
				form.setStatus("New");
			}			
		}
		if(!BLANK.equals(ratecardvo.getRateCardDescription()) && ratecardvo.getRateCardDescription().trim().length()>0){
			form.setDescription(ratecardvo.getRateCardDescription());
		}
		if(ratecardvo.getValidityStartDate()!=null){
			String fromdat=ratecardvo.getValidityStartDate().toDisplayDateOnlyFormat();
			form.setValidFrom(fromdat);
		}
		if(ratecardvo.getValidityEndDate()!=null){
			String todat=ratecardvo.getValidityEndDate().toDisplayDateOnlyFormat();
			form.setValidTo(todat);
		}
		DecimalFormat formatter = new DecimalFormat(DECIMAL_FORMAT); 
		form.setListStatus(ratecardvo.getRateCardStatus());
		String str=String.valueOf(ratecardvo.getMailDistanceFactor());
		if(Double.valueOf(str) > DECIMAL_CEIL_VALUE){
			form.setMialDistFactor(str);
		}
		else{
			form.setMialDistFactor(formatter.format(ratecardvo.getMailDistanceFactor()));
		}
		str=String.valueOf(ratecardvo.getCategoryTonKMRefOne());
		if(Double.valueOf(str) > DECIMAL_CEIL_VALUE){
			form.setSvTkm(str);
		}else{
			form.setSvTkm(formatter.format(ratecardvo.getCategoryTonKMRefOne()));
		}
		str=String.valueOf(ratecardvo.getCategoryTonKMRefTwo());
		if(Double.valueOf(str) > 0.001){
			form.setSalTkm(str);
		}else{
			form.setSalTkm(formatter.format(ratecardvo.getCategoryTonKMRefTwo()));
		}
		str=String.valueOf(ratecardvo.getCategoryTonKMRefThree());
		if(Double.valueOf(str) > 0.001){
			form.setAirmialTkm(str);
		}else{
			form.setAirmialTkm(formatter.format(ratecardvo.getCategoryTonKMRefThree()));
		}
		form.setOpFlag(ratecardvo.getOperationFlag());
		LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if(!BLANK.equals(form.getValidTo())&& form.getValidTo().trim().length()>0){
			LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			toDate.setDate(form.getValidTo());
			if(toDate.before(currentDate)){
				form.setStatus("Expired");
			}
		}
		if(ratecardvo.getRateLineVOss()!=null){
			ArrayList<RateLineVO> ratelinevos=new ArrayList<RateLineVO>(ratecardvo.getRateLineVOss());
			for(int i=0;i<ratelinevos.size();i++){
			if(("C").equals(ratelinevos.get(i).getRatelineStatus())||
					("E").equals(ratelinevos.get(i).getRatelineStatus())){
				flag++;
			}
			if(("C").equals(ratelinevos.get(i).getRatelineStatus())){
				cflag++;
			}
			}
			if(flag==ratelinevos.size()&& cflag!=ratelinevos.size()){
				form.setStatus("Expired");
			}
		}
		form.setScreenStatus("list");
		
	}
	
}
