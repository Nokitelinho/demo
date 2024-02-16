/*
 * SaveMultipleULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to save the details of the specified ULD 
 * 
 * @author A-2001
 */
public class SaveMultipleULDCommand extends BaseCommand {
    
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * 
	 */
	private Log log = LogFactory.getLogger("TARIFF");
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String SAVEULD_SUCCESS = "save_success";
	private static final String SAVEULD_FAILURE = "save_error";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		log.entering("SaveCommand", "execute");
    	MaintainULDSession maintainULDSession = 
    		getScreenSession(MODULE, SCREENID);
    	MultipleULDForm multipleULDForm = 
    							(MultipleULDForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ArrayList<String> uldNos = new ArrayList<String>();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String uldFormNos[] = multipleULDForm.getUldNos();
    	String uldOpFlags[] = multipleULDForm.getUldOpFlag();
    	//ArrayList<String> duplicateUldNumbers = new ArrayList<String>();
    	for(int j = 0; j < uldFormNos.length; j++) {
    		if(uldFormNos[j]!=null && uldFormNos[j].length()>0 && 
    				!ULDVO.OPERATION_FLAG_DELETE.equals(uldOpFlags[j]) && !"NOOP".equals(uldOpFlags[j])){
    			uldNos.add(uldFormNos[j].toUpperCase());
    		}
    	/*	if(j< uldFormNos.length-1) {
    			duplicateUldNumbers.add(uldFormNos[j]);
    		}
    		
    	}
    	String lastUldNumber = uldNos.get(uldNos.size()-1); 
    	if(duplicateUldNumbers.contains(lastUldNumber)) {
    		ErrorVO error = new ErrorVO("uld.defaults.duplicateuldnumbers",
    				new Object[]{lastUldNumber});
			errors.add(error);*/
    	}
    	maintainULDSession.setUldNumbers(uldNos);
    	ArrayList<String> uldSavedNumbersSaved = new ArrayList<String>();
     	if(maintainULDSession.getUldNumbers()!= null &&
     			maintainULDSession.getUldNumbers().size() > 0) {
     		ArrayList<String> uldNumbers = maintainULDSession.getUldNumbers();
     		ArrayList<String> duplicateuldNumbers = new ArrayList<String>();
     		for(String uldNumber : uldNumbers) {
     			if(!("").equals(uldNumber)) {
	     			if(uldSavedNumbersSaved.contains(uldNumber)) {
	     				if(!duplicateuldNumbers.contains(uldNumber)) {
	     					duplicateuldNumbers.add(uldNumber.toUpperCase());
	     				}
	       			}
	     			else {
		     			uldSavedNumbersSaved.add(uldNumber.toUpperCase());
	     			}
     			}
     		}
     		if(duplicateuldNumbers.size() > 0) {
     			 StringBuffer duplicates = new StringBuffer("");
     			for(String duplicate:duplicateuldNumbers) {
     				if(("").equals(duplicates.toString())) {
            			//alreadyGenerated = uldNo.toUpperCase();
     					duplicates.append(duplicate);
            		}
            		else {
            			duplicates.append(" , ");
            			duplicates.append(duplicate);
            		}
     			}
 	     		ErrorVO error = 
						new ErrorVO("uld.defaults.duplicateuldnumbers",
	     				new Object[]{duplicates.toString()});
				errors.add(error);
     		}
       	}
     	 if(errors != null &&
  				errors.size() > 0 ) {
  				invocationContext.addAllError(errors);
  				invocationContext.target = SAVEULD_FAILURE;
  				return;
  		}
     	if(uldSavedNumbersSaved != null
				&& uldSavedNumbersSaved.size() > 0) {
			Collection<ErrorVO> errorsUldNumber = new ArrayList<ErrorVO>();
			/*int size = uldSavedNumbersSaved.size();
			for(int i = 0; i < size ; i++) {
				try {
					
					new ULDDefaultsDelegate().validateULDFormat(
								logonAttributes.getCompanyCode(),
							    uldSavedNumbersSaved.get(i).toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					
					errorsUldNumber = 
						   handleDelegateException(businessDelegateException);
				}
				if(errorsUldNumber != null &&
						errorsUldNumber.size() > 0) {
					errors.addAll(errorsUldNumber);
				}
			}*/
			Collection<String> invalidNos = new ArrayList<String>();
			try {
				
				invalidNos = new ULDDefaultsDelegate().validateMultipleULDFormats(
							logonAttributes.getCompanyCode(),
							uldSavedNumbersSaved);
			} catch (BusinessDelegateException businessDelegateException) {
				
				errorsUldNumber = 
					   handleDelegateException(businessDelegateException);
			}
			if(errorsUldNumber != null &&
					errorsUldNumber.size() > 0) {
				errors.addAll(errorsUldNumber);
			}
			if(invalidNos != null) {
				if(invalidNos.size() > 0) {
	    			 StringBuffer invalidNumbers = new StringBuffer("");
	    			for(String invalidno:invalidNos) {
	    				if(("").equals(invalidNumbers.toString())) {
	           			//alreadyGenerated = uldNo.toUpperCase();
	    					invalidNumbers.append(invalidno);
		           		}
		           		else {
		           			invalidNumbers.append(" , ");
		           			invalidNumbers.append(invalidno);
		           		}
	    			}
		     		ErrorVO error = 
							new ErrorVO("uld.defaults.invaliduldformat",
		     				new Object[]{invalidNumbers.toString()});
					errors.add(error);
	    		}
			}
			/*if(errors == null || errors.size() == 0 ) {
				 StringBuffer mismatchedNumbers = new StringBuffer("");
				for(String uldNo:uldSavedNumbersSaved) {
					if(!isMatching(uldNo,multipleULDForm)) {
						if(("").equals(mismatchedNumbers.toString())) {
	           			//alreadyGenerated = uldNo.toUpperCase();
							mismatchedNumbers.append(uldNo);
		           		}
		           		else {
		           			mismatchedNumbers.append(" , ");
		           			mismatchedNumbers.append(uldNo);
		           		}
					}
				}
				if(!("").equals(mismatchedNumbers.toString())) {
					ErrorVO error = 
					    new ErrorVO(
					    		"uld.defaults.multipleuld.msg.err.mismacthtype",
					    		new Object[]{mismatchedNumbers.toString(),
					    			multipleULDForm.getUldType().toUpperCase(),
					    			multipleULDForm.getOwnerAirlineCode().toUpperCase()});
					errors.add(error);
				}
			}*/
		}
     	 if(errors != null &&
   				errors.size() > 0 ) {
   				invocationContext.addAllError(errors);
   				invocationContext.target = SAVEULD_FAILURE;
   				return;
   		}
     	 
     	maintainULDSession.setUldNumbersSaved(uldSavedNumbersSaved);
     	multipleULDForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
     	if(multipleULDForm.getStructuralFlag() == null || 
     			multipleULDForm.getStructuralFlag().trim().length() == 0) {
     		multipleULDForm.setStructuralFlag("firstOk");
    	}
        multipleULDForm.setOnloadStatusFlag("save");
        invocationContext.target = SAVEULD_SUCCESS;
		log.exiting("SaveCommand", "execute");
    }
   /* private boolean isMatching(String uldNumber, 
    										MultipleULDForm multipleULDForm) {
		int startIndex = 0;
		int endIndex = 0;
		boolean isValid = true;
		char charAtfourthPosition = uldNumber.charAt(3);
		char charAtlastthirdPosition = uldNumber.charAt(uldNumber.length()-3);
		try {
			Integer.parseInt(String.valueOf(charAtfourthPosition));
			startIndex = 3;
		}
		catch(NumberFormatException e) {
			startIndex = 4;
		}
		
		try {
			Integer.parseInt(String.valueOf(charAtlastthirdPosition));
			endIndex = uldNumber.length()-2;
		}
		catch(NumberFormatException e) {
			endIndex = uldNumber.length()-3;
		}
		String uldType = uldNumber.substring(0,startIndex).toUpperCase();
		String airlineCode = uldNumber.substring(
								endIndex,uldNumber.length()).toUpperCase();
		if(!(multipleULDForm.getUldType().toUpperCase().equals(uldType) && 
				multipleULDForm.getOwnerAirlineCode().toUpperCase().equals(airlineCode))) {
			isValid = false;
			
		}
		return isValid;
	}*/
}
