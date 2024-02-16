/*
 * SaveTempCustomerCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1496
 *
 */
public class SaveTempCustomerCommand  extends BaseCommand {
	
	

    private static final String SAVE_SUCCESS = "save_success";	
    private static final String SAVE_FAILURE = "save_failure";
    private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID ="customermanagement.defaults.maintaintempcustomerform";
	private static final String BLANK = "";
   /**
    * @param invocationContext
    * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
    	ListtempCustomerSession listtempCustomerSession = getScreenSession(MODULE, SCREENID);
    	MaintainTempCustomerForm maintainTempCustomerForm = 
			(MaintainTempCustomerForm) invocationContext.screenModel;
    	
    	String tempId = "";
    	Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
    	String companyCode = logonAttributes.getCompanyCode();
    	LocalDate date=new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    	
    	
    	
    	
    	//if(listtempCustomerSession.getTempCustomerDetails() !=null ){
    	if(("update").equals(maintainTempCustomerForm.getOperationMode())){
    		TempCustomerVO tempCustomervo= listtempCustomerSession.getTempCustomerDetails();
        		
        	//tempCustomervo.setCompanyCode(companyCode);    	
        	tempCustomervo.setCreatedDate(date);
    	if(!BLANK.equals(maintainTempCustomerForm.getCustomerName())){
			if(tempCustomervo.getTempCustName()==null){
				tempCustomervo.setTempCustName(maintainTempCustomerForm.getCustomerName().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getTempCustName().equals(maintainTempCustomerForm.getCustomerName().toUpperCase())){
				tempCustomervo.setTempCustName(maintainTempCustomerForm.getCustomerName().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			 if(tempCustomervo.getTempCustName() != null && 
					 !tempCustomervo.getTempCustName().equals(maintainTempCustomerForm.getCustomerName().toUpperCase())){
				tempCustomervo.setTempCustName(maintainTempCustomerForm.getCustomerName().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
		
    	if(!BLANK.equals(maintainTempCustomerForm.getStation())){
			if(tempCustomervo.getStation()==null){
				tempCustomervo.setStation(maintainTempCustomerForm.getStation().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getStation().equals(maintainTempCustomerForm.getStation().toUpperCase())){
				tempCustomervo.setStation(maintainTempCustomerForm.getStation().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getStation() !=null &&
					!tempCustomervo.getStation().equals(maintainTempCustomerForm.getStation().toUpperCase())){
				tempCustomervo.setStation(maintainTempCustomerForm.getStation().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getAddress() != null) && (!BLANK.equals(maintainTempCustomerForm.getAddress()))){
			if(tempCustomervo.getAddress()==null){
				tempCustomervo.setAddress(maintainTempCustomerForm.getAddress().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getAddress().equals(maintainTempCustomerForm.getAddress().toUpperCase())){
				tempCustomervo.setAddress(maintainTempCustomerForm.getAddress().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getAddress() != null &&
					!tempCustomervo.getAddress().equals(maintainTempCustomerForm.getAddress().toUpperCase())){
				tempCustomervo.setAddress(maintainTempCustomerForm.getAddress().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	if((maintainTempCustomerForm.getEmailId() != null) && (!BLANK.equals(maintainTempCustomerForm.getEmailId()))){
			if(tempCustomervo.getEmailAddress()==null){
				tempCustomervo.setEmailAddress(maintainTempCustomerForm.getEmailId());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getEmailAddress().equals(maintainTempCustomerForm.getEmailId())){
				tempCustomervo.setEmailAddress(maintainTempCustomerForm.getEmailId());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getEmailAddress() != null &&
					!tempCustomervo.getEmailAddress().equals(maintainTempCustomerForm.getEmailId())){
				tempCustomervo.setEmailAddress(maintainTempCustomerForm.getEmailId());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}    	
    	if((maintainTempCustomerForm.getPhoneNo() != null) && (!BLANK.equals(maintainTempCustomerForm.getPhoneNo()))){
			if(tempCustomervo.getPhoneNumber()==null){
				tempCustomervo.setPhoneNumber(maintainTempCustomerForm.getPhoneNo());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getPhoneNumber().equals(maintainTempCustomerForm.getPhoneNo())){
				tempCustomervo.setPhoneNumber(maintainTempCustomerForm.getPhoneNo());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getPhoneNumber() != null && 
					!tempCustomervo.getPhoneNumber().equals(maintainTempCustomerForm.getPhoneNo())){
				tempCustomervo.setPhoneNumber(maintainTempCustomerForm.getPhoneNo());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}  
    	if((maintainTempCustomerForm.getRemark() != null) && (!BLANK.equals(maintainTempCustomerForm.getRemark()))){
			if(tempCustomervo.getRemarks()==null){
				tempCustomervo.setRemarks(maintainTempCustomerForm.getRemark().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getRemarks().equals(maintainTempCustomerForm.getRemark().toUpperCase())){
				tempCustomervo.setRemarks(maintainTempCustomerForm.getRemark().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getRemarks() !=null &&
					!tempCustomervo.getRemarks().equals(maintainTempCustomerForm.getRemark().toUpperCase())){
				tempCustomervo.setRemarks(maintainTempCustomerForm.getRemark().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		} 
    	
    	if((maintainTempCustomerForm.getCountryCode() != null) && (!BLANK.equals(maintainTempCustomerForm.getCountryCode()))){
			if(tempCustomervo.getCountryCode() == null){
				tempCustomervo.setCountryCode(maintainTempCustomerForm.getCountryCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getCountryCode().equals(maintainTempCustomerForm.getCountryCode().toUpperCase())){
				tempCustomervo.setCountryCode(maintainTempCustomerForm.getCountryCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getCountryCode() != null &&
					!tempCustomervo.getCountryCode().equals(maintainTempCustomerForm.getCountryCode().toUpperCase())){
				tempCustomervo.setCountryCode(maintainTempCustomerForm.getCountryCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getCityCode() != null) && (!BLANK.equals(maintainTempCustomerForm.getCityCode()))){
			if(tempCustomervo.getCityCode() == null){
				tempCustomervo.setCityCode(maintainTempCustomerForm.getCityCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getCityCode().equals(maintainTempCustomerForm.getCityCode().toUpperCase())){
				tempCustomervo.setCityCode(maintainTempCustomerForm.getCityCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getCityCode() != null &&
					!tempCustomervo.getCityCode().equals(maintainTempCustomerForm.getCityCode().toUpperCase())){
				tempCustomervo.setCityCode(maintainTempCustomerForm.getCityCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getZipCode() != null) && (!BLANK.equals(maintainTempCustomerForm.getZipCode()))){
			if(tempCustomervo.getZipCode() == null){
				tempCustomervo.setZipCode(maintainTempCustomerForm.getZipCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getZipCode().equals(maintainTempCustomerForm.getZipCode().toUpperCase())){
				tempCustomervo.setZipCode(maintainTempCustomerForm.getZipCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getZipCode() != null &&
					!tempCustomervo.getZipCode().equals(maintainTempCustomerForm.getZipCode().toUpperCase())){
				tempCustomervo.setZipCode(maintainTempCustomerForm.getZipCode().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getAddressTwo() != null) && (!BLANK.equals(maintainTempCustomerForm.getAddressTwo()))){
			if(tempCustomervo.getAddressTwo() == null){
				tempCustomervo.setAddressTwo(maintainTempCustomerForm.getAddressTwo().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getAddressTwo().equals(maintainTempCustomerForm.getAddressTwo().toUpperCase())){
				tempCustomervo.setAddressTwo(maintainTempCustomerForm.getAddressTwo().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getAddressTwo() != null &&
					!tempCustomervo.getAddressTwo().equals(maintainTempCustomerForm.getAddressTwo().toUpperCase())){
				tempCustomervo.setAddressTwo(maintainTempCustomerForm.getAddressTwo().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getMobileNumber() != null) && (!BLANK.equals(maintainTempCustomerForm.getMobileNumber()))){
			if(tempCustomervo.getMobileNumber() == null){
				tempCustomervo.setMobileNumber(maintainTempCustomerForm.getMobileNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getMobileNumber().equals(maintainTempCustomerForm.getMobileNumber().toUpperCase())){
				tempCustomervo.setMobileNumber(maintainTempCustomerForm.getMobileNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getMobileNumber() != null &&
					!tempCustomervo.getMobileNumber().equals(maintainTempCustomerForm.getMobileNumber().toUpperCase())){
				tempCustomervo.setMobileNumber(maintainTempCustomerForm.getMobileNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getFaxNumber() != null) && (!BLANK.equals(maintainTempCustomerForm.getFaxNumber()))){
			if(tempCustomervo.getFaxNumber() == null){
				tempCustomervo.setFaxNumber(maintainTempCustomerForm.getFaxNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getFaxNumber().equals(maintainTempCustomerForm.getFaxNumber().toUpperCase())){
				tempCustomervo.setFaxNumber(maintainTempCustomerForm.getFaxNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getFaxNumber() != null &&
					!tempCustomervo.getFaxNumber().equals(maintainTempCustomerForm.getFaxNumber().toUpperCase())){
				tempCustomervo.setFaxNumber(maintainTempCustomerForm.getFaxNumber().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if((maintainTempCustomerForm.getState() != null) && (!BLANK.equals(maintainTempCustomerForm.getState()))){
			if(tempCustomervo.getState() == null){
				tempCustomervo.setState(maintainTempCustomerForm.getState().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(!tempCustomervo.getState().equals(maintainTempCustomerForm.getState().toUpperCase())){
				tempCustomervo.setState(maintainTempCustomerForm.getState().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getState() != null && 
					!tempCustomervo.getState().equals(maintainTempCustomerForm.getState().toUpperCase())){
				tempCustomervo.setState(maintainTempCustomerForm.getState().toUpperCase());
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}
    	
    	if(maintainTempCustomerForm.getActive() != null) {
			if(tempCustomervo.getActiveStatus() == null) {
				tempCustomervo.setActiveStatus("A");
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}else if(tempCustomervo.getActiveStatus() != null) {
				tempCustomervo.setActiveStatus("A");
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		}else {
			if(tempCustomervo.getActiveStatus() != null){
				tempCustomervo.setActiveStatus("I");
				tempCustomervo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
		} 
    	//tempCustomervo.setTempCustCode(maintainTempCustomerForm.getTempId());
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate = new CustomerMgmntDefaultsDelegate();
		listtempCustomerSession.setTempCustomerDetails(tempCustomervo);
		
    	Collection<ErrorVO> errorvos=new ArrayList<ErrorVO>();
    	errorvos = validateStation(maintainTempCustomerForm);
    	if(errorvos!=null && errorvos.size() > 0) {
       		
    		//listtempCustomerSession.removeTempCustomerDetails();
   		
   		invocationContext.addAllError(errorvos);
   		invocationContext.target = SAVE_FAILURE;
   		return;
    	}
    	/*errorvos = validateCustomer(maintainTempCustomerForm);
    	if(errorvos!=null && errorvos.size() > 0) {
   		
    		//listtempCustomerSession.removeTempCustomerDetails();
   		
   		invocationContext.addAllError(errorvos);
   		invocationContext.target = SAVE_FAILURE;
   		return;
    	}*/
    	
    	Collection<ErrorVO> errorvosOne=new ArrayList<ErrorVO>();
    	errorvosOne = validateStationCode(maintainTempCustomerForm,companyCode);    	
    	if(maintainTempCustomerForm.getCountryCode().length()>0){
    	errorvosOne = validateCountryCode(maintainTempCustomerForm,companyCode );    
    	}
    	if(errorvosOne.size()==0 && maintainTempCustomerForm.getCityCode().length()>0 ){
    	errorvosOne = validateCityCode(maintainTempCustomerForm,companyCode );
    	}
    	if(errorvosOne!=null && errorvosOne.size() > 0) {
       		
    		//listtempCustomerSession.removeTempCustomerDetails();
   		
   		invocationContext.addAllError(errorvosOne);
   		invocationContext.target = SAVE_FAILURE;
   		return;
    	}
    	ArrayList<TempCustomerVO> selectedTempCust = new ArrayList<TempCustomerVO>();
    	selectedTempCust.add(tempCustomervo);
		
		try {
			tempId=customerMgmntDefaultsDelegate.saveTempCustomer(selectedTempCust);
		}catch(BusinessDelegateException e) {
//printStackTrrace()();
			handleDelegateException(e);
		}
    	
    	}else{
    		
    		TempCustomerVO tempCustomervoOne =  new TempCustomerVO();
    		String cmpCod= logonAttributes.getCompanyCode();
        	LocalDate createddat=new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
        	tempCustomervoOne.setCompanyCode(cmpCod);
        	tempCustomervoOne.setCreatedDate(createddat);
    		
    			if(maintainTempCustomerForm.getCustomerName().toUpperCase()!=null &&
    					maintainTempCustomerForm.getCustomerName().length()!=0 )
    				{
    				tempCustomervoOne.setTempCustName(maintainTempCustomerForm.getCustomerName().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getStation().toUpperCase()!=null &&
    					maintainTempCustomerForm.getStation().length()!=0 )
    				{
    				tempCustomervoOne.setStation(maintainTempCustomerForm.getStation().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getAddress().toUpperCase()!=null &&
    					maintainTempCustomerForm.getAddress().length()!=0 )
    				{
    				tempCustomervoOne.setAddress(maintainTempCustomerForm.getAddress().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getEmailId().toUpperCase()!=null &&
    					maintainTempCustomerForm.getEmailId().length()!=0 )
    				{
    				tempCustomervoOne.setEmailAddress(maintainTempCustomerForm.getEmailId().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getPhoneNo()!=null &&
    					maintainTempCustomerForm.getPhoneNo().length()!=0 )
    				{
    				tempCustomervoOne.setPhoneNumber(maintainTempCustomerForm.getPhoneNo());
    				}
    			if(maintainTempCustomerForm.getCountryCode().toUpperCase()!=null &&
    					maintainTempCustomerForm.getCountryCode().length()!=0 )
    				{
    				tempCustomervoOne.setCountryCode(maintainTempCustomerForm.getCountryCode().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getCityCode().toUpperCase()!=null &&
    					maintainTempCustomerForm.getCityCode().length()!=0 )
    				{
    				tempCustomervoOne.setCityCode(maintainTempCustomerForm.getCityCode().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getZipCode()!=null &&
    					maintainTempCustomerForm.getZipCode().length()!=0 )
    				{
    				tempCustomervoOne.setZipCode(maintainTempCustomerForm.getZipCode());
    				}
    			if(maintainTempCustomerForm.getAddressTwo().toUpperCase()!=null &&
    					maintainTempCustomerForm.getAddressTwo().length()!=0 )
    				{
    				tempCustomervoOne.setAddressTwo(maintainTempCustomerForm.getAddressTwo().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getMobileNumber()!=null &&
    					maintainTempCustomerForm.getMobileNumber().length()!=0 )
    				{
    				tempCustomervoOne.setMobileNumber(maintainTempCustomerForm.getMobileNumber());
    				}
    			if(maintainTempCustomerForm.getFaxNumber()!=null &&
    					maintainTempCustomerForm.getFaxNumber().length()!=0 )
    				{
    				tempCustomervoOne.setFaxNumber(maintainTempCustomerForm.getFaxNumber());
    				}
    			if(maintainTempCustomerForm.getState().toUpperCase()!=null &&
    					maintainTempCustomerForm.getState().length()!=0 )
    				{
    				tempCustomervoOne.setState(maintainTempCustomerForm.getState().toUpperCase());
    				}
    			if(maintainTempCustomerForm.getActive()== null){
    				tempCustomervoOne.setActiveStatus("I");
    			}else{
    				if("on".equals(maintainTempCustomerForm.getActive()))
    				{
    					tempCustomervoOne.setActiveStatus("A");
    				}
    				else
    				{	
    					tempCustomervoOne.setActiveStatus("I");
    				}
    			}
    			if(maintainTempCustomerForm.getRemark().toUpperCase()!=null &&
    					maintainTempCustomerForm.getRemark().length()!=0 )
    				{
    				tempCustomervoOne.setRemarks(maintainTempCustomerForm.getRemark().toUpperCase());
    				}
    			tempCustomervoOne.setOperationalFlag(AbstractVO.OPERATION_FLAG_INSERT);
    			CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate = new CustomerMgmntDefaultsDelegate();
    			listtempCustomerSession.setTempCustomerDetails(tempCustomervoOne);
    			
    	    	Collection<ErrorVO> errorvos=new ArrayList<ErrorVO>();
    	    	errorvos = validateStation(maintainTempCustomerForm);
    	    	if(errorvos!=null && errorvos.size() > 0) {
    	       		
    	    		//listtempCustomerSession.removeTempCustomerDetails();
    	   		
    	   		invocationContext.addAllError(errorvos);
    	   		invocationContext.target = SAVE_FAILURE;
    	   		return;
    	    	}
    	    	if(maintainTempCustomerForm.getCountryCode()!=null && !maintainTempCustomerForm.getCountryCode().isEmpty()) {
    	    		errorvos = validateCountryCode(maintainTempCustomerForm, companyCode);
    	    	if(errorvos!=null && !errorvos.isEmpty()) {
    	   		invocationContext.addAllError(errorvos);
    	   		invocationContext.target = SAVE_FAILURE;
    	   		return;
    	    	}
    	    	}
    	    	/*errorvos = validateCustomer(maintainTempCustomerForm);
    	    	if(errorvos!=null && errorvos.size() > 0) {
    	   		
    	    		//listtempCustomerSession.removeTempCustomerDetails();
    	   		
    	   		invocationContext.addAllError(errorvos);
    	   		invocationContext.target = SAVE_FAILURE;
    	   		return;
    	    	}*/
    	    	
    	    	Collection<ErrorVO> errorvosTwo=new ArrayList<ErrorVO>();
    	    	errorvosTwo = validateStationCode(maintainTempCustomerForm,companyCode);    	
    	    	if(errorvosTwo!=null && errorvosTwo.size() > 0) {
    	       		
    	    	//listtempCustomerSession.setTempCustomerDetails(null);
    	   		
    	   		invocationContext.addAllError(errorvosTwo);
    	   		invocationContext.target = SAVE_FAILURE;
    	   		return;
    	    	}
    	    	ArrayList<TempCustomerVO> selectedTempCustOne = new ArrayList<TempCustomerVO>();
    	    	selectedTempCustOne.add(tempCustomervoOne);
    			
    			try {
    				tempId=customerMgmntDefaultsDelegate.saveTempCustomer(selectedTempCustOne);
    			}catch(BusinessDelegateException e) {
//printStackTrrace()();
    				handleDelegateException(e);
    			}
    	}
    	if(listtempCustomerSession.getPageURL()==null){
    	maintainTempCustomerForm.setSaveStatus("success");
    	}
				
		/*try {
			tempId=customerMgmntDefaultsDelegate.saveTempCustomer(tempCustomervo1);
//printStackTrrace()();
		}*/
		if(tempId != null && tempId.trim().length()>0){
		if(!BLANK.equals(tempId)){
			
				Object[] array = new Object[]{tempId};				
				ErrorVO error  = new ErrorVO("customermanagement.defaults.tempcustreg.msg.info.tempid",array);						
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
		listtempCustomerSession.removeTempCustomerDetails();
		
		
		invocationContext.target=SAVE_SUCCESS;
		
		return;
		}
		}else{
			
			if(listtempCustomerSession.getPageURL()==null){
		    	maintainTempCustomerForm.setSaveStatus("success");
		    	}
			listtempCustomerSession.removeTempCustomerDetails();
		invocationContext.target=SAVE_FAILURE;
		}
		

}
    private Collection<ErrorVO> validateCityCode(
		MaintainTempCustomerForm maintainTempCustomerForm, String companyCode) {
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;	
		Collection<String> cityCodes = new ArrayList<String>();
		cityCodes.add(maintainTempCustomerForm.getCityCode().toUpperCase());
		if(cityCodes != null){
			AreaDelegate areaDelegate = new AreaDelegate();		
			try{
				areaDelegate.validateAirportCodes(companyCode, cityCodes);
			}catch(BusinessDelegateException businessDelegateException) {
				error =new ErrorVO("shared.area.invalidairport");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				}
		}	
		return errors;	
    }
	private Collection<ErrorVO> validateCountryCode(
		MaintainTempCustomerForm maintainTempCustomerForm, String companyCode) {
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;		
		Collection<String> countryCodes = new ArrayList<String>();
		countryCodes.add(maintainTempCustomerForm.getCountryCode().toUpperCase());
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateCountryCodes(logonAttributes.getCompanyCode(),countryCodes);
		} catch (BusinessDelegateException e) {
		error =new ErrorVO("shared.area.invalidcountry");
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		}
		return errors;
	}
    private Collection<ErrorVO> validateStation(MaintainTempCustomerForm maintainTempCustomerForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;		
		
		if("".equals(maintainTempCustomerForm.getStation())) {				
			error = new ErrorVO("customermanagement.defaults.stationmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}   
		if("".equals(maintainTempCustomerForm.getCustomerName())) {				
			error = new ErrorVO("customermanagement.defaults.customernamemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
    }
    /*private Collection<ErrorVO> validateCustomer(MaintainTempCustomerForm maintainTempCustomerForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		if(maintainTempCustomerForm.getCustomerName().toUpperCase().equals("")) {				
			error = new ErrorVO("customermanagement.defaults.customernamemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}   
		return errors;
    }*/
    private Collection<ErrorVO> validateStationCode(MaintainTempCustomerForm maintainTempCustomerForm,
			String companyCode) {
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		AreaValidationVO areaValidationVO = null; 
    	try {
			AreaDelegate delegate = new AreaDelegate();
			
			
			areaValidationVO = delegate.validateLevel(
					logonAttributes.getCompanyCode().toUpperCase(),"STN",maintainTempCustomerForm.getStation().toUpperCase());			
    	} catch (BusinessDelegateException e) {
    				
			errors = handleDelegateException(e);
		}
    	
     return errors;
   }
   
	
}
