/*
 * RefreshArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class RefreshArriveMailCommand extends BaseCommand  {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "refresh_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";  
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("RefreshArriveMailCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Map systemParameters = null;  
		SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();  
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		try {
			systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
		} catch (BusinessDelegateException e) {		
			e.getMessage();
		} //added by A_7540for ICRD-274933 ends
		AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}
    	ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
    	mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
    	mailArrivalForm.setPol(containerDetailsVO.getPol());
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
    		mailArrivalForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
    		}
    		else{
    			mailArrivalForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}

	  invocationContext.target = TARGET;
      log.exiting("RefreshArriveMailCommand","execute");

   }

    /**
     * @author A-7540
     * @return
     */
    private Collection<String> getSystemParameterCodes(){
		  Collection systemParameterCodes = new ArrayList();
		    systemParameterCodes.add("mail.operations.defaultcaptureunit");
		    return systemParameterCodes;
	  } 
	  /**
			 * @author A-7540
			 * @return stationParameterCodes
			 */
		  private Collection<String> getStationParameterCodes()
		  {
		    Collection stationParameterCodes = new ArrayList();
		    stationParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
		    return stationParameterCodes;
	    }	

}
