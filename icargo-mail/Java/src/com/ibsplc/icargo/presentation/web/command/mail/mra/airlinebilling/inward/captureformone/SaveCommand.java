/*
 * SaveCommand.java created on Aug 4, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class SaveCommand  extends BaseCommand{

	/**
	 * @param args
	 */
	private Log log = LogFactory.getLogger("MRA airlinebilling CLEARCOMMAND");
	private static final String SAVE_FAILURE = "save_failure";
	private static final String ALLFIELDS_MANDATORY = "mailtracking.mra.airlinebilling.inward.captureformone.allfieldsmandatory";
	private static final String MODULE = "mailtracking.mra.airlinebilling";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";
	private static final String INVVOS_NULL="mailtracking.mra.airlinebilling.inward.captureformone.invvosnotnull";
	private static final String SAVE_CONFIRM="mailtracking.mra.airlinebilling.inward.captureformone.saveconfirmed";
	private static final String SAVE_SUCCESS="save_success";
	private static final String INVALID_CURCODE = "mailtracking.mra.airlinebilling.inward.msg.error.invalidcurrencycode";

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	int len=0;
    	Money money1 =null;
    	Money money2 =null;
    	int del=0;
    	LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
    	 LocalDate frmDate[]=new LocalDate[10]; 
    		LocalDate currDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	CaptureMailFormOneForm form=(CaptureMailFormOneForm)invocationContext.screenModel;
    	CaptureFormOneSession captureFormOneSession=getScreenSession(MODULE,SCREENID);
    	InvoiceInFormOneVO formoneInvVO=null;
    	ArrayList<InvoiceInFormOneVO> formoneInvVOs = new ArrayList<InvoiceInFormOneVO>();
    	ArrayList<InvoiceInFormOneVO> sessionInvVOs = (ArrayList<InvoiceInFormOneVO>)captureFormOneSession.getFormOneInvVOs();
    	FormOneVO formOneVO=new FormOneVO();
    	String opFlags[]=form.getOperationFlag();
    	if(form.getInvNum()!=null){
    	 len=form.getInvNum().length;
    	}
    	log.log(Log.INFO, "len ", len);
		if(len==1){
    		ErrorVO err=new ErrorVO(INVVOS_NULL);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
			invocationContext.addAllError(errors);
			invocationContext.target=SAVE_FAILURE;
			return;
    	}
    	
    	if(sessionInvVOs!=null && sessionInvVOs.size()>0){
    	 currDate=sessionInvVOs.get(0).getLastUpdateTime();
    	}
    	if(form.getFormOneOpFlag()!=null && ("I").equals(form.getFormOneOpFlag())){
    		formOneVO.setOperationFlag("I");
    		form.setFormOneOpFlag("");
    	}	
    	try{
    	money1 = CurrencyHelper.getMoney("USD");
		money1.setAmount(0.0D);
    	money2=CurrencyHelper.getMoney("USD");
		money2.setAmount(0.0D);
    	for(int i=0;i<len;i++){
    	//	sessionInvVOs=(ArrayList<InvoiceInFormOneVO>)captureFormOneSession.getFormOneInvVOs();
       		if(opFlags[i]!=null){
       			if(!(("NOOP").equals(opFlags[i]))){
       			
       				log.log(Log.INFO, "form.getInvNum()[i] ", form.getInvNum(),
							i);
					log.log(Log.INFO, "form.getInvDate()[i] ", form.getInvDate(), i);
					log.log(Log.INFO, "form.getCurList()[i] ", form.getCurList(), i);
					log.log(Log.INFO, "form.getMisAmt()[i] ", form.getMisAmt(),
							i);
					log.log(Log.INFO, "form.getExgRate()[i] ", form.getExgRate(), i);
					log.log(Log.INFO, "form.getAmtUsd()[i] ", form.getAmtUsd(),
							i);
					log.log(Log.INFO, "operation flag ", form.getOperationFlag(), i);
				if(form.getInvNum()[i]!=null && form.getInvDate()[i]!=null && form.getCurList()[i]!=null &&
       					form.getMisAmt()[i]!=null &&form.getExgRate()[i]!=null &&form.getAmtUsd()[i]!=null){
       				if(("").equals(form.getInvNum()[i])|| ("").equals(form.getInvDate()[i]) 
       				   || ("").equals(form.getCurList()[i]) || ("").equals(form.getMisAmt()[i]) 
       				   || ("").equals(form.getExgRate()[i])   ||("").equals(form.getAmtUsd()[i])  ){
       				
       				ErrorVO err=new ErrorVO(ALLFIELDS_MANDATORY);
       				err.setErrorDisplayType(ErrorDisplayType.ERROR);
       				errors.add(err);
       				invocationContext.addAllError(errors);
       				invocationContext.target=SAVE_FAILURE;
       				return;
       				}
       				else{
       					formoneInvVO=new InvoiceInFormOneVO();
       					if(captureFormOneSession.getFormOneVO()!=null){
       						log.log(Log.INFO, "------1vo---",
									captureFormOneSession.getFormOneVO().getAirlineIdr());
							int airlineId=captureFormOneSession.getFormOneVO().getAirlineIdr();
       						formoneInvVO.setAirlineIdentifier(airlineId);
       						      					
       	   				}
       					if(!(("D").equals(form.getOperationFlag()[i]))){
       					try {
       						new CurrencyDelegate().validateCurrency(
       								logonAttributes.getCompanyCode(),
       								form.getCurList()[i].trim().toUpperCase());
       					} catch (BusinessDelegateException businessDelegateException) {
       						log.log(Log.INFO, "\n -------- Vaidation Failed---------");
       						ErrorVO error = new ErrorVO(INVALID_CURCODE);
       						error.setErrorDisplayType(ErrorDisplayType.ERROR);
       						invocationContext.addError(error);
       						invocationContext.target = SAVE_SUCCESS;
       						return;
       					}
       					}
       					
       					       					
       					formoneInvVO.setCompanyCode(logonAttributes.getCompanyCode());
       					//formoneInvVO.setAirlineIdentifier(captureFormOneSession.getFormOneVO().getAirlineIdr());
       					formoneInvVO.setClearancePeriod(form.getClearancePeriod());
       					formoneInvVO.setIntBlgTyp("I");
       					formoneInvVO.setClassType("M");
       					formoneInvVO.setInvStatus(form.getInvStatus()[i]);
       					formoneInvVO.setInvoiceNumber(form.getInvNum()[i]);
       					frmDate[i]=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
       					frmDate[i].setDate(form.getInvDate()[i]);
       					formoneInvVO.setInvoiceDate(frmDate[i]);
       					formoneInvVO.setLstCurCode(form.getCurList()[i]);
       					Money blgamt=CurrencyHelper.getMoney(form.getBlgCurCode());
    					blgamt.setAmount(Double.parseDouble(form.getAmtUsd()[i]));
       					formoneInvVO.setBillingTotalAmt(blgamt);
       					Money misamt=CurrencyHelper.getMoney(form.getBlgCurCode());
       					misamt.setAmount(Double.parseDouble(form.getMisAmt()[i]));
       					formoneInvVO.setTotMisAmt(misamt);
       					formoneInvVO.setExgRate(Double.parseDouble(form.getExgRate()[i]));
       					if(!("D").equals(opFlags[i])){
		   				   money1.plusEquals(formoneInvVO.getBillingTotalAmt());
		                   money2.plusEquals(formoneInvVO.getTotMisAmt());
       					}
                       formoneInvVO.setLastUpdateUser(logonAttributes.getCompanyCode());
                       formoneInvVO.setLastUpdateTime(currDate);
		               	
                   	   if(("I").equals(opFlags[i])){
                   		formoneInvVO.setOperationFlag("I");
                   	   }
                   	   if((("N").equals(opFlags[i]))){
                   		formOneVO.setOperationFlag("U");
                   		formoneInvVO.setOperationFlag("U");
                   	   }
                   	 if((("D").equals(opFlags[i]))){
                    		formOneVO.setOperationFlag("U");
                   		    del++;
                    		formoneInvVO.setOperationFlag("D");
                    	   }
       				  formoneInvVOs.add(formoneInvVO);
       				}
       			}
       			} else {
					len=len-1;
				}
       		}
    }
    	if(del==formoneInvVOs.size()){
    		formOneVO.setOperationFlag("D");
    	}
    	
    	
    			//formOneVO=new FormOneVO();
    			formOneVO.setCompanyCode(logonAttributes.getCompanyCode());
    			formOneVO.setAirlineIdr(captureFormOneSession.getFormOneVO().getAirlineIdr());
    			formOneVO.setClearancePeriod(form.getClearancePeriod());
    			formOneVO.setInterlineBillingType("I");
    			formOneVO.setClassType("M");
    			formOneVO.setAirlineCode(form.getAirlineCode());
    			formOneVO.setBillingTotalAmt(money1);
    			formOneVO.setMissAmount(money2);
    			formOneVO.setLastUpdateTime(currDate);
    			formOneVO.setInvoiceInFormOneVOs(formoneInvVOs);
    			formOneVO.setLastUpdateUser(logonAttributes.getCompanyCode());
    			formOneVO.setBillingCurrency(form.getBlgCurCode());
    			log.log(Log.INFO, "FORMONEVO IN SAVE COMMAND---- ", formOneVO);
				log.log(Log.INFO, "formoneInvVO ", formoneInvVO);
				if(len==0 && sessionInvVOs!=null && sessionInvVOs.size()>0){
    				 formOneVO.setOperationFlag("D");
    		    	}
    		
    		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
    		try {
    			delegate.saveFormOneDetails(formOneVO);
    		} catch (BusinessDelegateException e) {
    			errors=handleDelegateException(e);
    		}
    		captureFormOneSession.removeFormOneInvVOs();
    		captureFormOneSession.removeFormOneVO();
    		form.setAirlineCode("");
    		form.setClearancePeriod("");
    		form.setAirlineNo("");
    		form.setInvoiceStatus("");
    		money1 = CurrencyHelper.getMoney("USD");
    		money1.setAmount(0.0D);
    		form.setNetMiscAmountMoney(money1);
    		form.setNetUsdAmountMoney(money1);
    		ErrorVO err=new ErrorVO(SAVE_CONFIRM);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				invocationContext.addAllError(errors);
				invocationContext.target=SAVE_SUCCESS;
    	}
    	catch(CurrencyException e){
			e.getErrorCode();
		}
    	}
    	
    }      
 		
 	


