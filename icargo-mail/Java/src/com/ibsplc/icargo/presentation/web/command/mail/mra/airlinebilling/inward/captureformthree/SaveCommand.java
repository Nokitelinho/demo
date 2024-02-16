/*
 * SaveCommand.java Created on Aug 6, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm3 SaveCommand");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "save_success";
	private static final String ACTION_FAILURE = "save_failure";
	
	private static final String CLEARANCE_PERIOD_MANDATORY="mailtracking.mra.airlinebilling.inward.msg.err.clearanceperiodmandatory";
	private static final String SAVE_SUCCESSFULLY="mailtracking.mra.airlinebilling.inward.msg.info.savesucessfully";
	private static final String INVALID_AIRLINECODE="mailtracking.mra.airlinebilling.inward.msg.info.invalidairline";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
    	CaptureMailFormThreeForm captureFormThreeForm=(CaptureMailFormThreeForm)invocationContext.screenModel;
    	Collection<AirlineForBillingVO> airlineForBillingVOS =new ArrayList<AirlineForBillingVO>();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
    	AirlineForBillingVO airlineForBillingVO=null;
    	Collection<AirlineForBillingVO> airlineForBillingVOss= new ArrayList<AirlineForBillingVO>();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String[] opFlag=captureFormThreeForm.getHiddenOperationFlag();
    	String[] airlineCode=captureFormThreeForm.getAirlineCode();
    	int[] airlineIdentifier=captureFormThreeForm.getAirlineIdentifier();
    	String[] airlineNumber=captureFormThreeForm.getAirlineNumber();
    	String[] misc=captureFormThreeForm.getMiscAmountInBilling();
    	String[] total=captureFormThreeForm.getTotalAmountInBilling();
    	String[] creditamount=captureFormThreeForm.getCreditAmountInBilling();
    	String[] netamount=captureFormThreeForm.getNetValueInBilling();
    	String[] status=captureFormThreeForm.getStatus();
    	String isValidAirline="true";
    	if(captureFormThreeForm.getClearancePeriod()==null || captureFormThreeForm.getClearancePeriod().length()<=0){
			errors.add(new ErrorVO(CLEARANCE_PERIOD_MANDATORY));	
		}
		if(errors != null && errors.size() > 0){			
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			return;
		}
    	try{
    		
    		log.log(Log.INFO, "----op Flag-", opFlag.length);
		if(opFlag!=null){
    		for(int i=0;i<opFlag.length-1;i++) {
				if(!"NOOP".equals(opFlag[i])){
					 airlineForBillingVO=new AirlineForBillingVO();					 
					 airlineForBillingVO.setCompanyCode(logonAttributes.getCompanyCode());
					 airlineForBillingVO.setClearancePeriod(captureFormThreeForm.getClearancePeriod());
					 AirlineDelegate airlineDelegate = new AirlineDelegate();
				
					 /**
					  * @author A-3447 for Int_MRA 13 Starts 
					  */
					 
					 AirlineValidationVO airlineValidationVO = null;
						try {
							airlineValidationVO = airlineDelegate.validateAlphaCode(
									getApplicationSession().getLogonVO()
									.getCompanyCode(),airlineCode[i].toUpperCase());
						} catch (BusinessDelegateException e) {
							handleDelegateException(e);
						}
						if(airlineValidationVO!=null){							
							 airlineForBillingVO.setAirlineIdentifier((airlineValidationVO.getAirlineIdentifier()));
						}
							
						else{
							
							captureFormThreeSession.removeAllAttributes();
							errors.add(new ErrorVO(INVALID_AIRLINECODE));
							isValidAirline="false";
							invocationContext.addAllError(errors);
							invocationContext.target = ACTION_FAILURE;
							return;
						}
					 
					/**
					 * @author A-3447 Int_MRA 13  ends 
					 */
				
					 if(airlineCode[i]!=null){
						 log.log(Log.INFO,airlineCode[i]);
					 airlineForBillingVO.setAirlineCode(airlineCode[i]);
					 }
					 if(airlineIdentifier[i]!=0){
						 airlineForBillingVO.setAirlineIdentifier(airlineIdentifier[i]); 
						 
					 }
					 if(airlineNumber[i]!=null){
						 airlineForBillingVO.setAirlineNumber(airlineValidationVO.getNumericCode());
						 
					 }
					 					
					 if(misc[i]!=null){
					 Money miscAmount= CurrencyHelper.getMoney("USD");
					 miscAmount.setAmount(Double.parseDouble(misc[i]));
					 airlineForBillingVO.setMiscAmountInBilling(miscAmount);
					 }
					 if(total[i]!=null){
					 Money totalAmount= CurrencyHelper.getMoney("USD");
					 totalAmount.setAmount(Double.parseDouble(total[i]));
					 airlineForBillingVO.setTotalAmountInBilling(totalAmount);
					 }
					 if(creditamount[i]!=null){
					 Money creditAmount= CurrencyHelper.getMoney("USD");
					 creditAmount.setAmount(Double.parseDouble(creditamount[i]));
					 airlineForBillingVO.setCreditAmountInBilling(creditAmount);
					 }
					 if(netamount[i]!=null){
					 Money netAmount= CurrencyHelper.getMoney("USD");
					 netAmount.setAmount(Double.parseDouble(netamount[i]));
					 airlineForBillingVO.setNetValueInBilling(netAmount);
					 }
					 if(status[i]!=null){
					 airlineForBillingVO.setStatus(status[i]);
					 }
					 if(opFlag[i]!=null){
					 airlineForBillingVO.setOperationFlag(opFlag[i]);
					 }
					 LocalDate ld = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
					 airlineForBillingVO.setLastUpdateTime(ld);
					 log.log(Log.INFO, "airlineForBillingVO-",
							airlineForBillingVO.getOperationFlag());
					airlineForBillingVOS.add(airlineForBillingVO);			
					 
				}
			
				
    		}
    		airlineForBillingVOss=updateOperationFlag(airlineForBillingVOS,captureFormThreeForm);
			 log.log(Log.INFO,
					"airlineForBillingVOss returned after updation---",
					airlineForBillingVOss);
    		}
    	}
    	catch(CurrencyException e) {
    		log.log(Log.FINE,  "CurrencyException");
        }
    	
    	if(("true").equals(isValidAirline)){
       	try {
		new MailTrackingMRADelegate().saveFormThreeDetails(airlineForBillingVOss);					
			
		} catch (BusinessDelegateException e) {
			captureFormThreeSession.removeAirlineForBillingVOs();
			errors=handleDelegateException( e );
			invocationContext.target = ACTION_FAILURE;
		}
    	}
		if(errors != null && errors.size() > 0){
			captureFormThreeSession.removeAirlineForBillingVOs();
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
		
		}
		else{
			
			captureFormThreeSession.removeAirlineForBillingVOs();
			Money creditMoney=null;
			Money miscMoney =null;
			Money totMoney=null;
			Money netMoney=null;
			try{
			creditMoney = CurrencyHelper.getMoney("USD");
			creditMoney.setAmount(0.0D);
			miscMoney = CurrencyHelper.getMoney("USD");
			miscMoney.setAmount(0.0D);
			totMoney = CurrencyHelper.getMoney("USD");
			totMoney.setAmount(0.0D);
			netMoney = CurrencyHelper.getMoney("USD");
			netMoney.setAmount(0.0D);
			}
			catch(CurrencyException e){
				   e.getErrorCode();
			   }
	    	captureFormThreeForm.setCreditTotalAmountInBillingMoney(creditMoney);
			captureFormThreeForm.setMiscTotalAmountInBillingMoney(miscMoney);
			captureFormThreeForm.setGrossTotalAmountInBillingMoney(totMoney);
			captureFormThreeForm.setNetTotalValueInBillingMoney(netMoney);
			errors.add(new ErrorVO(SAVE_SUCCESSFULLY));	
			invocationContext.addAllError(errors);
		}
		captureFormThreeForm.setClearancePeriod("");
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    	
    }
    
    
    
		
    /***Method for updating operation FLAG FLAGS FOR SAME PK AND D AND I
     * @author A-3447
     * @param captureFormThreeForm
     * @return
     */	
    private Collection<AirlineForBillingVO> updateOperationFlag(Collection<AirlineForBillingVO> airlineForBillingVOs,CaptureMailFormThreeForm captureFormThreeForm) {
    	log.entering(CLASS_NAME, "execute");
    	ArrayList<AirlineForBillingVO> vos = (ArrayList<AirlineForBillingVO>)	airlineForBillingVOs;
    	int numberOfElements = vos.size();
    	log.log(Log.INFO, "before removal-- ", vos.size());
		for(int i=numberOfElements-1;i>0;i--){
    		AirlineForBillingVO airlineForBillingOuterVO = vos.get(i);			
    		for(int j=i-1;j>=0;j--) {
    			AirlineForBillingVO airlineForBillingInnerVO = vos.get(j);
    			if(airlineForBillingOuterVO.getAirlineCode().equals(
    					airlineForBillingInnerVO.getAirlineCode())) {				
    				airlineForBillingOuterVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
    				vos.remove(j);

    			}


    		}
    	}

    	log.log(Log.INFO, "inside insert--- ", vos.size());
		log.exiting(CLASS_NAME, "updateOperationFlag");

    	return vos;
    }

}
