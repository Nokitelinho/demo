/*
 * GenerateULDNumberCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.uld.ULDFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.MaintainULDSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to save the details of the specified ULD 
 * 
 * @author A-1347
 */
public class GenerateULDNumberCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("GENERATE MULTIPLE ULD");
	
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID = "uld.defaults.maintainuld";

	private static final String GENERATEULD_SUCCESS = "generateuldnumbers_success";
   
	private static final String GENERATEULD_FAILURE = "generateuldnumbers_failure";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		MaintainULDSessionImpl maintainULDSessionImpl = getScreenSession(
				MODULE, SCREENID);
		MultipleULDForm multipleULDForm = (MultipleULDForm) invocationContext.screenModel;
		
    	ArrayList<String> uldNos = maintainULDSessionImpl.getUldNumbers();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	errors = validateForm(multipleULDForm);
		if (uldNos == null) {
   		 	uldNos = new ArrayList<String>();
   	 	}
		if (uldNos != null && uldNos.size() > 0) {
	    	String uldFormNos[] = multipleULDForm.getUldNos();
			for (int j = 0; j < uldFormNos.length; j++) {

				/*added by A-2619 for bugfix begins*/
				if (uldFormNos[j] != null && uldFormNos[j].trim().length() != 0) {
					log.log(Log.FINE,
							"uldFormNos[j] inside  if loop --------->>",
							uldFormNos, j);
					uldNos.set(j, uldFormNos[j].toUpperCase());
				}
				/*added by A-2619 for bugfix ends*/
			}
		}
		if (errors != null && errors.size() > 0) {	    		
 				invocationContext.addAllError(errors);
 				invocationContext.target = GENERATEULD_FAILURE;
 				return;
 		}
        int noOfUnits = Integer.parseInt(multipleULDForm.getNoOfUnits());
        int startNo = Integer.parseInt(multipleULDForm.getStartNo());
        String uldType = multipleULDForm.getUldType();
        StringBuffer UldOwnerCode = new StringBuffer("");
        StringBuffer OwnerAirlineCode = new StringBuffer("");
        if(multipleULDForm.getUldOwnerCode()!= null && multipleULDForm.getUldOwnerCode().trim().length()!= 0){
        	UldOwnerCode.append(multipleULDForm.getUldOwnerCode());
        }else{
        	OwnerAirlineCode.append(multipleULDForm.getOwnerAirlineCode());
        }
		errors = new ArrayList<ErrorVO>();
       // String alreadyGenerated = "";
        StringBuffer alreadyGeneratedUlds = new StringBuffer("");
        int startNoSize = multipleULDForm.getStartNo().length();
		for (int i = 0; i < noOfUnits; i++) {
        	StringBuffer uldNo = new StringBuffer("");
        	String uldNoFinal = "";
        	StringBuffer appendUld = new StringBuffer("");
        	//uldType + Integer.toString(startNo++) + airlineCode;
			if (Integer.toString(startNo).length() < startNoSize) {
				int appendSize = startNoSize
						- Integer.toString(startNo).length();

				for (int j = 0; j < appendSize; j++) {
        			appendUld.append("0");
        		}
        		
        	}
        	uldNo.append(uldType);
    		uldNo.append(appendUld);
    		uldNo.append(Integer.toString(startNo));
    		uldNo.append(UldOwnerCode);
    		uldNo.append(OwnerAirlineCode);
    		//Added by A-7359 for ICRD-266770 starts here
    		uldNoFinal=ULDFormatter.formatULDNo(uldNo.toString());
    		//Added by A-7359 for ICRD-266770 ends here
			if (!uldNos.contains(uldNoFinal.toUpperCase())) {
        		uldNos.add(uldNoFinal.toUpperCase());
			} else {
				if (("").equals(alreadyGeneratedUlds.toString())) {
        			//alreadyGenerated = uldNo.toUpperCase();
        			alreadyGeneratedUlds.append(uldNoFinal.toUpperCase());
				} else {
        			alreadyGeneratedUlds.append(", ");
        			alreadyGeneratedUlds.append(uldNoFinal.toUpperCase());
        		}
        		
        	}
        	++startNo;
        }
		if (!("").equals(alreadyGeneratedUlds.toString())) {
         	 ErrorVO error = new ErrorVO("uld.defaults.uldalredygenerated",
					new Object[] { alreadyGeneratedUlds.toString() });
         	error.setErrorDisplayType(ErrorDisplayType.INFO);
         	errors.add(error);
 			invocationContext.addAllError(errors);
 				
 		}
        multipleULDForm.setOnloadStatusFlag("generate");
        invocationContext.target = GENERATEULD_SUCCESS;
    }
    
	private Collection<ErrorVO> validateForm(MultipleULDForm multipleULDForm) {
		log.entering("GenerateULDCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (multipleULDForm.getStartNo() == null
				|| multipleULDForm.getStartNo().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.startnomandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (multipleULDForm.getNoOfUnits() == null
				|| multipleULDForm.getNoOfUnits().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.noofunitsmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		} else if (Integer.parseInt(multipleULDForm.getNoOfUnits()) <= 0) {
			 error = new ErrorVO("uld.defaults.numberofcopieszero");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		log.exiting("GenerateULDCommand", "validateForm");
		return errors;
	}
}
