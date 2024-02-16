/*
 * AttachAWBCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AttachAWBCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AttachAWBCommand","execute");
    	  
    	MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
		
		ContainerDetailsVO containerDetailsVO = 
	        	((ArrayList<ContainerDetailsVO>)mailManifestVO.getContainerDetails()).get(Integer.parseInt(mailManifestForm.getParentContainer()));
		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
	    	try{
	    		BeanHelper.copyProperties(containerDtlsVO,containerDetailsVO);
	    	}catch(SystemException systemException){
	    		systemException.getMessage();
	    	}
		
	    String[] selected = mailManifestForm.getSelectDSN();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");
	
	    int cnt=0;
	    int count = 0;
        int primaryKeyLen = primaryKey.length;
        log.log(Log.INFO, "primaryKeyLen------------>>", primaryKeyLen);
		if(primaryKey != null && primaryKey.length > 0) {
        	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
        		new MailTrackingDefaultsDelegate();
        	
    	    boolean isFlightClosed = false;
        	try {
        		
        		isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(
        				mailManifestSession.getOperationalFlightVO());
    		  
    		}catch (BusinessDelegateException businessDelegateException) {
    			Collection<ErrorVO>errors = 
    				handleDelegateException(businessDelegateException);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET;
    			return;
    		}
    		
    		if(isFlightClosed) {
    			Object[] obj = {mailManifestVO.getFlightCarrierCode(),
    					mailManifestVO.getFlightNumber(),
    					mailManifestVO.getDepDate().toString().substring(0,11)};
    			invocationContext.addError(
    					new ErrorVO("mailtracking.defaults.err.flightclosed", obj));
    			invocationContext.target = TARGET;
    			return;
    		}
    		// Already Attached Validation
    		ArrayList<DSNVO> dsnVOs = (ArrayList<DSNVO>) containerDtlsVO
    				.getDsnVOs();
    		if (dsnVOs != null && dsnVOs.size() > 0) {
    			for (int i = 0; i < primaryKeyLen; i++) {
    				if (dsnVOs.get(Integer.parseInt(primaryKey[i])).getMasterDocumentNumber() != null
    						&& dsnVOs.get(Integer.parseInt(primaryKey[i])).getSequenceNumber() > 0) {
    					invocationContext
    							.addError(new ErrorVO(
    									"mailtracking.defaults.attachawb.msg.warn.alreadyattached"));
    					invocationContext.target = TARGET;
    					return;
    				}
    			}
    		}
        } else {
        	invocationContext.target = TARGET;
        	return;
        }
        Collection<DSNVO> dsnVOs = containerDtlsVO.getDsnVOs();
        Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
        
        String[] orgArr = new String[primaryKeyLen];
        String[] destArr = new String[primaryKeyLen];
        
        if (dsnVOs != null && dsnVOs.size() != 0) {
        	for (DSNVO dsnVO : dsnVOs) {
        		String primaryKeyFromVO = String.valueOf(count);
        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       			          equalsIgnoreCase(primaryKey[cnt].trim())) {
        			orgArr[cnt] = dsnVO.getOriginExchangeOffice();
        			destArr[cnt] = dsnVO.getDestinationExchangeOffice();
        			newDsnVOs.add(dsnVO);
        			cnt++;
        		}
        		count++;
       	  	}
       	}
        
        Collection<String> originColl = new ArrayList<String>();
        for(int i=0;i<orgArr.length;i++){
        	if(!originColl.contains(orgArr[i])){
        		originColl.add(orgArr[i]);
        	}
        }
        
	    try {
				/*
			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
			     * the inner collection contains the values in the order :
			     * 0.OFFICE OF EXCHANGE
			     * 1.CITY NEAR TO OE
			     * 2.NEAREST AIRPORT TO CITY
			     */
				groupedOECityArpCodes = 
					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
							logonAttributes.getCompanyCode(), originColl);
			}catch (BusinessDelegateException businessDelegateException) {
        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
	  	}
        
        int flag = 0;
        int errorFlag = 0;
        String airport = "";		
        String originOE=null;
		if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
			for(String ooe : originColl){
				for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
					if(cityAndArpForOE.size() == 3 && ooe.equals(cityAndArpForOE.get(0))) {
						if(flag == 0) { 
							airport = cityAndArpForOE.get(2);
							originOE=cityAndArpForOE.get(0);
							flag = 1;
						}else{
							if(!airport.equals(cityAndArpForOE.get(2))){
								errorFlag = 1;
								break;
							}
						}
						break;
					}			
				}
				if(errorFlag == 1) {
					break;
				}
			}
		}
        mailManifestForm.setPol(airport);
        if(errorFlag == 1){
    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.differentairports"));
        }else{
        
	        Collection<String> destnColl = new ArrayList<String>();
	        for(int i=0;i<destArr.length;i++){
	        	if(!destnColl.contains(destArr[i])){
	        		destnColl.add(destArr[i]);
	        	}
	        }
	        groupedOECityArpCodes = null;
	        
		    try {
				/*
			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
			     * the inner collection contains the values in the order :
			     * 0.OFFICE OF EXCHANGE
			     * 1.CITY NEAR TO OE
			     * 2.NEAREST AIRPORT TO CITY
			     */
				groupedOECityArpCodes = 
					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
							logonAttributes.getCompanyCode(), destnColl);
			}catch (BusinessDelegateException businessDelegateException) {
	        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
		  	}
	        
	        flag = 0;
	        errorFlag = 0;
	        airport = "";	
			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
				for(String doe : destnColl){
					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
						if(cityAndArpForOE.size() == 3 && doe.equals(cityAndArpForOE.get(0))) {
							if(flag == 0) { 
								airport = cityAndArpForOE.get(2);
								flag = 1;
							}else{
								if(!airport.equals(cityAndArpForOE.get(2))){
									errorFlag = 1;
									break;
								}
							}
							break;
						}			
					}
					if(errorFlag == 1) {
						break;
					}
				}
			}
	        mailManifestForm.setPou(airport);
	        if(errorFlag == 1){
	        	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.differentairports"));
	        }else{
	        	String agentCode=findAgentCodeForPA(logonAttributes.getCompanyCode(),originOE);
	        	if(agentCode!=null&&agentCode.trim().length()>0){
				        containerDtlsVO.setDsnVOs(newDsnVOs);
				        mailManifestSession.setContainerDetailsVO(containerDtlsVO);
		        mailManifestSession.setAgentCode(agentCode);
				        mailManifestForm.setOpenAttachAWB(FLAG_YES);
	        }else{
	        		invocationContext.addError(new ErrorVO("mailtracking.defaults.attachawb.agentcodenotmapped"));
        			invocationContext.target = TARGET;
        			return;
		        }
	        }
        }
        invocationContext.target = TARGET;
    	log.exiting("AttachAWBCommand","execute");
    	
    }
    /**
     * 
     * 	Method		:	AttachAWBCommand.findAgentCodeForPA
     *	Added by 	:	U-1267 on 07-Nov-2017
     * 	Used for 	:	ICRD-211205
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param originOE
     *	Parameters	:	@return 
     *	Return type	: 	String
     */
	private String findAgentCodeForPA(String companyCode,
			String originOE) {
		String paCode = null;
		String agentCode = null;
		if (originOE != null && originOE.trim().length() > 0) {
			try {
				paCode = new MailTrackingDefaultsDelegate()
						.findPAForOfficeOfExchange(companyCode, originOE);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		}
		if (paCode != null && paCode.trim().length() > 0) {
			try {
				agentCode = new MailTrackingDefaultsDelegate()
						.findAgentCodeForPA(companyCode, paCode);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		}

		return agentCode;
	}
       
}
