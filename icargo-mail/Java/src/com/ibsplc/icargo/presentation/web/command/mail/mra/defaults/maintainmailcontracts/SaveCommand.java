/*
 * SaveCommand.java Created on Apr 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "SaveCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailcontracts";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String DETAILS_FAILURE="screenload_failure";
	private static final String SCREEN_NEW="new";
	private static final String BLANK="";
	private static final String NO_DATA_SAVE="mailtracking.mra.defaults.msg.err.nodataforsave";
	private static final String SCREEN_SCREENLOAD="screenload";
	public static final String DUPLICATE_MAILCONTRACT = "mailtracking.mra.defaults.duplicatemailcontract";
	private static final String SAVE_SUCCESS="mailtracking.mra.defaults.msg.err.mailcontractsavesuccess";
	private static final String INVALID_PAOCODE = "mailtracking.mra.defaults.maintainmailcontracts.err.invalidpoacode";
	private static final String FROMDATEMAN="mailtracking.mra.defaults.maintainmailcontracts.err.fromdatemandatory";
	private static final String TODATEMAN="mailtracking.mra.defaults.maintainmailcontracts.err.todatemandatory";
	
	private static final String FRMDATEGREATER="mailtracking.mra.defaults.maintainmailcontracts.err.fromdatemustnotbegreater";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	ArrayList<MailContractDetailsVO> latestContractDetails=null;
	Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	ErrorVO errorVO=null;
	//boolean isBillingPresent=false;
	//boolean isNewBillingPresent=false;
	boolean isSave=false;
	if(session.getMailContractDetails()!=null && session.getMailContractDetails().size()>0){
		latestContractDetails=new ArrayList<MailContractDetailsVO>(session.getMailContractDetails());
		
	}
	
	
	updateMailContractVO(form,session);
	errors.addAll(validateFormValues(form)); 
	
	if(session.getMailContractVO()!=null){
	session.getMailContractVO().setMailContractDetailsVos(latestContractDetails);
	errors.addAll(validateContractVO(session.getMailContractVO()));
	}
	 log.log(Log.INFO, "vo after upadtion", session.getMailContractVO());
	MailContractVO saveContractVO=new MailContractVO();
	
	if(session.getMailContractVO()!=null) {
		saveContractVO=session.getMailContractVO();
	}
	
	
	
	
	 if(errors!=null && errors.size()>0){
    	 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target=DETAILS_FAILURE;
			return;
		}
	 log.log(Log.INFO, "previous vo", saveContractVO);
	if(saveContractVO!=null){
		
			
			
		if(SCREEN_NEW.equals(form.getScreenStatus()))
		 {
			saveContractVO.setOperationFlag(OPERATION_FLAG_INSERT);
			//saveContractVO.setMailContractDetailsVos(latestContractDetails);
		}
		
		//for billing details
		String billingMatrix=form.getBillingMatrix();
		StringTokenizer destok = new StringTokenizer(billingMatrix,",");
		String[] billingIds=new String[billingMatrix.length()];
		int num=0;
		while(destok.hasMoreTokens()){

			billingIds[num] = destok.nextToken();
			num++;
		}
		ArrayList<String> bills=new ArrayList<String>();
		for(int i=0;i<billingIds.length;i++){
			if(billingIds[i]!=null && billingIds[i].trim().length()>0) {
				bills.add( billingIds[i].trim());
			}
		}
		
	if(bills!=null && bills.size()>0 && OPERATION_FLAG_INSERT.equals(saveContractVO.getOperationFlag())){
		saveContractVO.setBillingDetails(bills);//setting billing details
		saveContractVO.setAgreementStatus("A");
	}
	
	if(OPERATION_FLAG_INSERT.equals(saveContractVO.getOperationFlag())) {
		if(latestContractDetails!=null && latestContractDetails.size()>0){
	for(MailContractDetailsVO detailVO:latestContractDetails){
		detailVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		detailVO.setContractReferenceNumber(form.getContractRefNumber());
			}
		}
	}
	else{
		
			if(latestContractDetails!=null && latestContractDetails.size()>0){
			for(MailContractDetailsVO detailVO:latestContractDetails){
				if(OPERATION_FLAG_UPDATE.equals(detailVO.getOperationFlag())
						||OPERATION_FLAG_INSERT.equals(detailVO.getOperationFlag())
						||OPERATION_FLAG_DELETE.equals(detailVO.getOperationFlag())){
						
								saveContractVO.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
		}
		
		
		if(saveContractVO.getBillingDetails()!=null && saveContractVO.getBillingDetails().size()>0){
			//isBillingPresent=true;
			if(bills!=null && bills.size()>0){
				
			if(bills.containsAll(saveContractVO.getBillingDetails())){
				log.log(log.INFO,"inside contain all");
				if(bills.size()!=saveContractVO.getBillingDetails().size()){
					log.log(log.INFO,"inside asdasd");
					saveContractVO.setBillingMatrixModified(true);
					saveContractVO.setOperationFlag(OPERATION_FLAG_UPDATE);
				}else{
					saveContractVO.setBillingMatrixModified(false);
				}
			}else{
				log.log(log.INFO,"inside conasdfasdftain all");
				saveContractVO.setBillingMatrixModified(true);
				saveContractVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			}
		
		saveContractVO.setBillingDetails(bills);
			}else{
				isSave=true;
				saveContractVO.setBillingMatrixModified(true);
				saveContractVO.setOperationFlag(OPERATION_FLAG_UPDATE);
				saveContractVO.setBillingDetails(null);
			}
		}
	}
		
		
	}else{
		errorVO=new ErrorVO(NO_DATA_SAVE);
		errors.add(errorVO);
		
	}
	
	if(OPERATION_FLAG_INSERT.equals(saveContractVO.getOperationFlag())||
			OPERATION_FLAG_UPDATE.equals(saveContractVO.getOperationFlag())){
		isSave=true;
	}
	if(!(isSave)){
		errorVO=new ErrorVO(NO_DATA_SAVE);
		errors.add(errorVO);
	}
	log.log(Log.INFO, "vo for save", saveContractVO);
	if(errors!=null && errors.size()>0){
		  invocationContext.addAllError(errors);
		  invocationContext.target = DETAILS_FAILURE;
		  return;
	  }
	try{
		new MailTrackingMRADelegate().saveMailContract(saveContractVO);
		
	}
	catch(BusinessDelegateException businessDelegateException){
		errors=handleDelegateException(businessDelegateException);
		
		
	}
	if(errors!=null && errors.size()>0){
	errors = handleErrorMessage(errors);	//handles error data from server
	
	invocationContext.addAllError(errors);
	invocationContext.target = DETAILS_FAILURE;
	return;
	}else{
		clearFormValue(form);
		session.removeMailContractVO();
		session.removeMailContractDetails();
		form.setScreenStatus(SCREEN_SCREENLOAD);
		errorVO=new ErrorVO(SAVE_SUCCESS);
		errors.add(errorVO);
		invocationContext.addAllError(errors);
	}
	/*log.log(log.INFO,"errors size----------->"+errors.size());
	for(ErrorVO errorVo : errors){
		System.out.println(errorVo);		
	}*/
	  /*if(errors!=null && errors.size()>0){
		  invocationContext.addAllError(errors);
		  invocationContext.target = DETAILS_FAILURE;
		  return;
	  }*/
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
	 
	 /**
	 * @param form
	 * @param session
	 * @return
	 */
	private void updateMailContractVO(MaintainMailContractsForm form,MaintainMailContractsSession session){
		MailContractVO vo=new MailContractVO();
		
		if(session.getMailContractVO()!=null) {
			vo=session.getMailContractVO();
		}
		
			
		log.log(Log.INFO, "vo from session", vo);
		//MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		 String description=form.getDescription();
		 String paCode=form.getPaCode();
		 String airlineCode=form.getAirlineCode();
		 String agreementType=form.getAgreementType();
		 String agreementStatus=form.getAgreementStatus();
		 String fromDate=form.getFromDate();
		 String toDate=form.getToDate();
		 LocalDate frDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	     LocalDate tDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	   
		 if(vo!=null){
		 if(SCREEN_NEW.equals(form.getScreenStatus())){
			 vo.setContractReferenceNumber(form.getContractRefNumber());
			 vo.setVersionNumber(form.getVersion());
			 vo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		 }
		if(vo.getContractDescription()!=null ||!(BLANK.equals(description))){
			 if(!(description.equals(vo.getContractDescription()))){
				 vo.setContractDescription(description);
				 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&& 
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			 }
		}
		if(vo.getGpaCode()!=null || !(BLANK.equals(paCode))){
			 if(!(paCode.equals(vo.getGpaCode()))){
				 vo.setGpaCode(paCode);
				 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			 }
		}
		if(vo.getAirlineCode()!=null || !(BLANK.equals(airlineCode))){
			 if(!(airlineCode.equals(vo.getAirlineCode()))){
				 vo.setAirlineCode(airlineCode);
				 vo.setAirlineIdentifier(form.getAirlineidentifier());
				 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			 }
		}
		if(vo.getAgreementType()!=null || !(BLANK.equals(agreementType))){
			 
			 if(!(agreementType.equals(vo.getAgreementType()))){
				 vo.setAgreementType(agreementType);
				 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			 }
		}
		if(vo.getAgreementStatus()!=null || !(BLANK.equals(agreementStatus))){
			 if(!(agreementStatus.equals(vo.getAgreementStatus()))){
				 vo.setAgreementStatus(agreementStatus);
				 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			 }
		}
		if(vo.getValidFromDate()!=null || !(BLANK.equals(fromDate))){
			  if(fromDate!=null && fromDate.trim().length()>0){
				     frDate.setDate(fromDate);
				     if(!(frDate.equals(vo.getValidFromDate()))){
				    	 vo.setValidFromDate(frDate);
				    	 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
								 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
							vo.setOperationFlag(OPERATION_FLAG_UPDATE);
						}
				     }
			  } 
		}
		if(vo.getValidToDate()!=null || !(BLANK.equals(toDate))){
		     if(toDate!=null && toDate.trim().length()>0){
		     tDate.setDate(toDate);
		     if(!(tDate.equals(vo.getValidToDate()))){
		    	 vo.setValidToDate(tDate);
		    	 if(!(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))&&
						 !(SCREEN_NEW.equals(form.getScreenStatus()))) {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
		     }
		     }
		}
		 }
		 
		 session.setMailContractVO(vo);
		
	 }
	/**
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateFormValues(MaintainMailContractsForm form){
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		//Collection<ErrorVO> orgerrors = new ArrayList<ErrorVO>();
    	//Collection<ErrorVO> desterrors = new ArrayList<ErrorVO>();
    	
		if(form.getAirlineCode()!=null && form.getAirlineCode().trim().length()>0){
				
				try{
					AirlineValidationVO airlineValidationVO=new AirlineDelegate().validateAlphaCode
							(getApplicationSession().getLogonVO().getCompanyCode(),form.getAirlineCode().toUpperCase());
					form.setAirlineidentifier(airlineValidationVO.getAirlineIdentifier());
				}
				catch(BusinessDelegateException businessDelegateException){
					errors.addAll(handleDelegateException(businessDelegateException));
				}
			}
		//Map<String,StationVO> stations = null;
	/*	if(form.getOriginCode()!=null && form.getOriginCode().length>0){
		Collection<String> origins = Arrays.asList(form.getOriginCode());
    	Collection<String> checkorigins=new ArrayList<String>();
    	
    	
    	for(String org:origins) {
			if(!checkorigins.contains(org)) {
				checkorigins.add(org);
			}
		}
    
    	try {
			new AreaDelegate().validateStationCodes(getApplicationSession().getLogonVO().getCompanyCode(),checkorigins);
		} catch (BusinessDelegateException e) {
			
			orgerrors = handleDelegateException(e);
	
		}
    	if(orgerrors != null && orgerrors.size() >0) {
			Object[] obj = null;
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for(ErrorVO error:orgerrors) {
			log.log(Log.INFO,"ErrorVO---->>>"+error);
			obj = new Object[]{error.getErrorData()};
			if (error.getErrorCode().equals("shared.station.invalidstation")){
				Object[] codes = error.getErrorData();
			
				for (int count = 0; count< codes.length;count++){
					if (errorString.equals("")){
						errorString = String.valueOf(codes[count]);	
						codeArray.append(errorString);
					}else{
						errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
					}
					log.log(Log.FINE,"\n\n\nValue-->"+String.valueOf(codes[count]));
				}
				Object[] errorArray = {errorString};
				ErrorVO errorVO = new ErrorVO(
					"mra.defaults.mailcontract.msg.err.invalidorigin",
					errorArray);					
				errors.add(errorVO);	
			}
		}
    	}
			
		}
    	if(form.getDestinationCode()!=null && form.getDestinationCode().length>0){
    	Collection<String> destinations = Arrays.asList(form.getDestinationCode());
    	Collection<String> checkdests=new ArrayList<String>();
    	for(String des:destinations) {
			if(!checkdests.contains(des)) {
				checkdests.add(des);
			}
		}
		try {
			new AreaDelegate().validateStationCodes(getApplicationSession().getLogonVO().getCompanyCode(),checkdests);
		} catch (BusinessDelegateException e) {
			
			desterrors = handleDelegateException(e);
	
		}
		if(desterrors != null && desterrors.size() >0) {
			Object[] obj = null;
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for(ErrorVO error:desterrors) {
			log.log(Log.INFO,"ErrorVO---->>>"+error);
			obj = new Object[]{error.getErrorData()};
			if (error.getErrorCode().equals("shared.station.invalidstation")){
				Object[] codes = error.getErrorData();
				
				for (int count = 0; count< codes.length;count++){
					if (errorString.equals("")){
						errorString = String.valueOf(codes[count]);		
						codeArray.append(errorString);
					}else{
						errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
					}
					log.log(Log.FINE,"\n\n\nValue-->"+String.valueOf(codes[count]));
				}
				Object[] errorArray = {errorString};
				ErrorVO errorVO = new ErrorVO(
					"mra.defaults.mailcontract.msg.err.invaliddest",
					errorArray);					
				errors.add(errorVO);	
			}
		}
		}
		}*/
		if(BLANK.equals(form.getFromDate())){
			errors.add(new ErrorVO(FROMDATEMAN));
		}
		if(BLANK.equals(form.getToDate())){
			errors.add(new ErrorVO(TODATEMAN));
		}
		if((!BLANK.equals(form.getFromDate()))&& (!BLANK.equals(form.getToDate()))
				&&(form.getFromDate()!=null)&&(form.getToDate()!=null)){
			 LocalDate frrDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		     LocalDate tooDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		     frrDate.setDate(form.getFromDate());
		     tooDate.setDate(form.getToDate());
			 if(frrDate.isGreaterThan(tooDate)){
				 errors.add(new ErrorVO(FRMDATEGREATER));
			 }
		}
    	if(form.getPaCode()!=null&& form.getPaCode().trim().length()>0){
    		try{
    			PostalAdministrationVO paVO =new MailTrackingMRADelegate().findPACode(getApplicationSession().getLogonVO().getCompanyCode(),form.getPaCode().trim());
    		 if(paVO == null){
				 ErrorVO err = new ErrorVO(INVALID_PAOCODE);
				 errors.add(err);
				 log.log(Log.INFO, "Error", err.getErrorCode());
			 }
    		}
    		catch(BusinessDelegateException businessDelegateException){
    			errors.addAll(handleDelegateException(businessDelegateException));
    		}
    	}
    	/*if(form.getArrivalToDelivery()!=null && form.getArrivalToDelivery().length>0
    			&& form.getAcceptanceToDeparture()!=null && form.getAcceptanceToDeparture().length>0){
    		Collection<String> arrivals = Arrays.asList(form.getArrivalToDelivery());
    		Collection<String> accepts = Arrays.asList(form.getAcceptanceToDeparture());
        	Collection<String> checkslas=new ArrayList<String>();
        	Collection<ErrorVO> slaerrors = new ArrayList<ErrorVO>(); 
        	for(String arr:arrivals) {
    			if(!checkslas.contains(arr)) {
    				checkslas.add(arr);
    			}
    		}
        	for(String acc:accepts) {
    			if(!checkslas.contains(acc)) {
    				checkslas.add(acc);
    			}
    		}
        	try{
        		new MailTrackingMRADelegate().validateSLACodes(getApplicationSession().getLogonVO().getCompanyCode(),
        				checkslas);
        	}
        	catch(BusinessDelegateException businessDelegateException){
        		log.log(log.INFO,"inside catch");
        		slaerrors=handleDelegateException(businessDelegateException);
        		log.log(log.INFO,"after catch");
        	}
        	if(slaerrors != null && slaerrors.size() >0) {
    			Object[] obj = null;
    			StringBuilder codeArray = new StringBuilder();
    			String errorString = "";
    			for(ErrorVO error:slaerrors) {
    			log.log(Log.INFO,"ErrorVO---->>>"+error);
    			obj = new Object[]{error.getErrorData()};
    			if (error.getErrorCode().equals("mailtracking.mra.defaults.invalidslacode")){
    				Object[] codes = error.getErrorData();
    				
    				for (int count = 0; count< codes.length;count++){
    					if (errorString.equals("")){
    						errorString = String.valueOf(codes[count]);	
    						codeArray.append(errorString);
    					}else{
    						errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
    					}
    					log.log(Log.FINE,"\n\n\nValue-->"+String.valueOf(codes[count]));
    				}
    				Object[] errorArray = {errorString};
    				ErrorVO errorVO = new ErrorVO(
    					"mra.defaults.mailcontract.msg.err.invalidslacode",
    					errorArray);					
    				errors.add(errorVO);	
    			}
    		}
    		}
    	}*/
    	if(form.getBillingMatrix()!=null && form.getBillingMatrix().trim().length()>0){
    		String billingMatrixIds=form.getBillingMatrix();
    		StringTokenizer tok = new StringTokenizer(billingMatrixIds,",");
    		String[] billMtxIds=new String[billingMatrixIds.length()];
    		Collection<ErrorVO> billerrors = new ArrayList<ErrorVO>(); 
    		int num=0;
    		while(tok.hasMoreTokens()){

    			billMtxIds[num] = tok.nextToken();
    			num++;
    		}
    		ArrayList<String> billMtxs=new ArrayList<String>();
    		for(int i=0;i<billMtxIds.length;i++){
    			if(billMtxIds[i]!=null && billMtxIds[i].trim().length()>0) {
					billMtxs.add( billMtxIds[i].trim());
				}
    		}
    		if(billMtxs!=null && billMtxs.size()>0){
    			try{
    				new MailTrackingMRADelegate().validateBillingMatrixCodes(getApplicationSession().getLogonVO().getCompanyCode(),
    						billMtxs);
    			}
    			
    			catch(BusinessDelegateException businessDelegateException){
            		log.log(log.INFO,"inside catch");
            		billerrors=handleDelegateException(businessDelegateException);
            		log.log(log.INFO,"after catch");
            	}
    			if(billerrors != null && billerrors.size() >0) {
        			Object[] obj = null;
        			StringBuilder codeArray = new StringBuilder();
        			String errorString = "";
        			for(ErrorVO error:billerrors) {
        			log.log(Log.INFO, "ErrorVO---->>>", error);
					obj = new Object[]{error.getErrorData()};
        			if (("mailtracking.mra.defaults.invalidbillingmatrixcode").equals(error.getErrorCode())){
        				Object[] codes = error.getErrorData();
        				
        				for (int count = 0; count< codes.length;count++){
        					if (("").equals(errorString)){
        						errorString = String.valueOf(codes[count]);		
        						codeArray.append(errorString);
        					}else{
        						log.log(Log.FINE, "errorstring before-->",
										errorString);
								log.log(Log.FINE, "Value-->", String.valueOf(codes[count]));
								errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
        						log.log(Log.FINE, "errorstring after-->",
										errorString);
        					}
        					
        				}
        				Object[] errorArray = {errorString};
        				ErrorVO errorVO = new ErrorVO(
        					"mra.defaults.mailcontract.msg.err.invalidbillingmtxId",
        					errorArray);					
        				errors.add(errorVO);	
        			}
        		}
        		}
    		}
    		
    		
    	}
		return errors;
	}
	
	/**
	 * @param vo
	 * @return
	 */
	private Collection<ErrorVO> validateContractVO(MailContractVO vo){
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		Collection<ErrorVO> slaerrors = new ArrayList<ErrorVO>(); 
		Collection<ErrorVO> orgerrors = new ArrayList<ErrorVO>();
    	Collection<ErrorVO> desterrors = new ArrayList<ErrorVO>();
    	
		Collection<String> origins=new ArrayList<String>();
		Collection<String> destinations=new ArrayList<String>();
		Collection<String> Slas=new ArrayList<String>();
	
		
		
		
		if(vo!=null){
			if(vo.getMailContractDetailsVos()!=null && vo.getMailContractDetailsVos().size()>0){
				for(MailContractDetailsVO maildetailsvo:vo.getMailContractDetailsVos()){
					if(!(OPERATION_FLAG_DELETE.equals(maildetailsvo.getOperationFlag()))){
					origins.add(maildetailsvo.getOriginCode());
					destinations.add(maildetailsvo.getDestinationCode());
					
					if(maildetailsvo.getAcceptanceToDeparture()!=null && maildetailsvo.getAcceptanceToDeparture().trim().length()>0) {
						Slas.add(maildetailsvo.getAcceptanceToDeparture());
					}
					
					if(maildetailsvo.getArrivalToDelivery()!=null && maildetailsvo.getArrivalToDelivery().trim().length()>0) {
						Slas.add(maildetailsvo.getArrivalToDelivery());
					}
					}
			}
				try {
					new AreaDelegate().validateStationCodes(getApplicationSession().getLogonVO().getCompanyCode(),origins);
				} catch (BusinessDelegateException e) {
					
					orgerrors = handleDelegateException(e);
			
				}
		    	if(orgerrors != null && orgerrors.size() >0) {
					Object[] obj = null;
					StringBuilder codeArray = new StringBuilder();
					String errorString = "";
					for(ErrorVO error:orgerrors) {
					log.log(Log.INFO, "ErrorVO---->>>", error);
					obj = new Object[]{error.getErrorData()};
					if (("shared.station.invalidstation").equals(error.getErrorCode())){
						Object[] codes = error.getErrorData();
					
						for (int count = 0; count< codes.length;count++){
							if (("").equals(errorString)){
								errorString = String.valueOf(codes[count]);	
								codeArray.append(errorString);
							}else{
								errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
						}
						Object[] errorArray = {errorString};
						ErrorVO errorVO = new ErrorVO(
							"mra.defaults.mailcontract.msg.err.invalidorigin",
							errorArray);					
						errors.add(errorVO);	
					}
				}
		    	}
		    	try {
					new AreaDelegate().validateStationCodes(getApplicationSession().getLogonVO().getCompanyCode(),destinations);
				} catch (BusinessDelegateException e) {
					
					desterrors = handleDelegateException(e);
			
				}
				if(desterrors != null && desterrors.size() >0) {
					Object[] obj = null;
					StringBuilder codeArray = new StringBuilder();
					String errorString = "";
					for(ErrorVO error:desterrors) {
					log.log(Log.INFO, "ErrorVO---->>>", error);
					obj = new Object[]{error.getErrorData()};
					if (("shared.station.invalidstation").equals(error.getErrorCode())){
						Object[] codes = error.getErrorData();
						
						for (int count = 0; count< codes.length;count++){
							if (("").equals(errorString)){
								errorString = String.valueOf(codes[count]);		
								codeArray.append(errorString);
							}else{
								errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
						}
						Object[] errorArray = {errorString};
						ErrorVO errorVO = new ErrorVO(
							"mra.defaults.mailcontract.msg.err.invaliddest",
							errorArray);					
						errors.add(errorVO);	
					}
				}
				}
				try{
	        		new MailTrackingMRADelegate().validateSLACodes(getApplicationSession().getLogonVO().getCompanyCode(),
	        				Slas);
	        	}
	        	catch(BusinessDelegateException businessDelegateException){
	        		log.log(log.INFO,"inside catch");
	        		slaerrors=handleDelegateException(businessDelegateException);
	        		log.log(log.INFO,"after catch");
	        	}
	        	if(slaerrors != null && slaerrors.size() >0) {
	    			Object[] obj = null;
	    			StringBuilder codeArray = new StringBuilder();
	    			String errorString = "";
	    			for(ErrorVO error:slaerrors) {
	    			log.log(Log.INFO, "ErrorVO---->>>", error);
					obj = new Object[]{error.getErrorData()};
	    			if (("mailtracking.mra.defaults.invalidslacode").equals(error.getErrorCode())){
	    				Object[] codes = error.getErrorData();
	    				
	    				for (int count = 0; count< codes.length;count++){
	    					if (("").equals(errorString)){
	    						errorString = String.valueOf(codes[count]);	
	    						codeArray.append(errorString);
	    					}else{
	    						errorString = codeArray.append(",").append(String.valueOf(codes[count])).toString();
	    					}
	    					log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
	    				}
	    				Object[] errorArray = {errorString};
	    				ErrorVO errorVO = new ErrorVO(
	    					"mra.defaults.mailcontract.msg.err.invalidslacode",
	    					errorArray);					
	    				errors.add(errorVO);	
	    			}
	    		}
	    		}
				
		    	
		    	
			}
		}
		return errors;
	}
	/**
	 * @param form
	 */
	private void clearFormValue(MaintainMailContractsForm form){
		form.setDescription(BLANK);
		form.setPaCode(BLANK);
		form.setAirlineCode(BLANK);
		form.setAgreementStatus(BLANK);
		form.setAgreementType(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setBillingMatrix(BLANK);
	
	}
	/**
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> handleErrorMessage(Collection<ErrorVO> errors){
			
			log.entering(CLASS_NAME,"handleErrorMessage"+errors.size());
			
			Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();
			
			if(errors != null && errors.size() > 0){
				
				for(ErrorVO error:errors){
					log.log(Log.INFO,
							"inside errors not null error data>>>>>>>>>", error.getErrorData().length);
					MailContractVO[] mailContractVO = 
						new MailContractVO[error.getErrorData() == null ? 0: error.getErrorData().length];
					log.log(Log.INFO, "inside errors not null>>>>>>>>>",
							mailContractVO.length);
					log.log(Log.INFO,
							"inside errors not null error code>>>>>>>>>", error.getErrorCode());
					if(DUPLICATE_MAILCONTRACT.equals(error.getErrorCode())){
						log.log(Log.INFO,"inside duplicate>>>>>>>>>");
						System.arraycopy(error.getErrorData(),0,
								mailContractVO,0,error.getErrorData().length);
						
						for(MailContractVO vo :mailContractVO){
							
							log.log(Log.INFO, "printing the errorVOS>>>>>>>>>",
									vo);
							if(vo.getMailContractDetailsVos()!=null){
							for(MailContractDetailsVO detailVO:vo.getMailContractDetailsVos()){
							finErrors.add(new ErrorVO(DUPLICATE_MAILCONTRACT,
									new String[]{detailVO.getContractReferenceNumber(),
									detailVO.getOriginCode(),
									detailVO.getDestinationCode()}
							));
						}
						}
						}
					}
				}
			}
				
			log.exiting(CLASS_NAME,"handleErrorMessage"+finErrors.size());
			return finErrors;
		}
}
