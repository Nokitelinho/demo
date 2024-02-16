/*
 * ChangeScanTimeOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ChangeScanTimeOkCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "ok_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String SCREEN_ID_ARR = "mailtracking.defaults.mailarrival";	
   private static final String SCREEN_ID_INV = "mailtracking.defaults.inventorylist";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ChangeScanTimeOkCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_ARR);
    	/*InventoryListSession inventoryListSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_INV);*/
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		errors = validateForm(mailAcceptanceForm);
    	if (errors != null && errors.size() > 0) {  
    		invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
    	}
    	
    	if("ARRIVE".equals(mailAcceptanceForm.getScanTimeFromScreen())){
    		
    		log.log(Log.FINE,
					"mailAcceptanceForm.getScanTimeFromScreen()....=",
					mailAcceptanceForm.getScanTimeFromScreen());
			MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
    		String scanDate = new StringBuilder(mailAcceptanceForm.getScanDate())
		       .append(" ").append(mailAcceptanceForm.getScanTime()).append(":00").toString();
    		LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			
			mailArrivalVO.setScanDate(sd.setDateAndTime(scanDate,false));
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			mailAcceptanceForm.setScanTimeFromScreen("ARRIVECLOSE");
    		
    	}else if("ARRIVEPOPUP".equals(mailAcceptanceForm.getScanTimeFromScreen())){
    		
    		log.log(Log.FINE,
					"mailAcceptanceForm.getScanTimeFromScreen()....=",
					mailAcceptanceForm.getScanTimeFromScreen());
			ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
	    	Collection<MailbagVO> mailDetailsVOs = containerDetailsVO.getMailDetails();
	    	String primaryKey = mailAcceptanceForm.getScanTimeFlag();
	    	String[] pkArr = primaryKey.split(",");
	  	    int cnt=0;
	  	    int count = 1;
	        int primaryKeyLen = pkArr.length;
	        if(mailDetailsVOs != null && mailDetailsVOs.size() != 0) {
	        	for (MailbagVO mailbagVO : mailDetailsVOs) {
	        		String primaryKeyFromVO = String.valueOf(count);
	        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	        				          equalsIgnoreCase(pkArr[cnt].trim())) {
	        			String scanDate = new StringBuilder(mailAcceptanceForm.getScanDate())
	        			       .append(" ").append(mailAcceptanceForm.getScanTime()).append(":00").toString();
	        			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	        			log.log(Log.FINE, "LocalDate sd...accept...=", sd);
						if(!"I".equals(mailbagVO.getOperationalFlag())){
							mailbagVO.setOperationalFlag("U");
							mailbagVO.setScreen(MailConstantsVO.CHANGE_SCAN_TIME);//Added For ICRD-140584
						}
	        			mailbagVO.setScannedDate(sd.setDateAndTime(scanDate,false));
	        			// //Added as part of Bug ICRD-129234 
	        			mailbagVO.setLatestScannedDate(sd.setDateAndTime(scanDate,false));    
	        			cnt++;
	        		}
	        		count++;
	        	}
	        }
			containerDetailsVO.setMailDetails(mailDetailsVOs);
			mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
			mailAcceptanceForm.setScanTimeFromScreen("ARRIVEPOPUPCLOSE");
    		
    	}else if("INVENTORY_LIST".equals(mailAcceptanceForm.getScanTimeFromScreen())){
    		
    		log.log(Log.FINE,
					"mailAcceptanceForm.getScanTimeFromScreen()....=",
					mailAcceptanceForm.getScanTimeFromScreen());
			String scanDate = new StringBuilder(mailAcceptanceForm.getScanDate())
		       .append(" ").append(mailAcceptanceForm.getScanTime()).append(":00").toString();
    		String str = mailAcceptanceForm.getStrToDelivery();
    		String pa = str.split("/")[2];
    		String selected = str.split("/")[1];
    		if("Y".equals(pa)){
    			str = new StringBuilder("C").append("/").append(selected).append("/").append(pa).toString();
    		}
	    	String selectContents = new StringBuilder().append(str).append("/").append(scanDate).toString();
	    	mailAcceptanceForm.setStrToDelivery(selectContents);
	    	mailAcceptanceForm.setScanTimeFromScreen("INVENTORY_LIST_CLOSE");
    		
    	}else{
    		
	    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
	    	Collection<MailbagVO> mailDetailsVOs = containerDetailsVO.getMailDetails();
	    	mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
	    	String primaryKey = mailAcceptanceForm.getScanTimeFlag();
	    	String[] pkArr = primaryKey.split(",");
	  	    int cnt=0;
	  	    int count = 1;
	        int primaryKeyLen = pkArr.length;
	        if(mailDetailsVOs != null && mailDetailsVOs.size() != 0) {
	        	for (MailbagVO mailbagVO : mailDetailsVOs) {
	        		String primaryKeyFromVO = String.valueOf(count);
	        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	        				          equalsIgnoreCase(pkArr[cnt].trim())) {
	        			String scanDate = new StringBuilder(mailAcceptanceForm.getScanDate())
	        			       .append(" ").append(mailAcceptanceForm.getScanTime()).append(":00").toString();
	        			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	        			log.log(Log.FINE, "LocalDate sd...accept...=", sd);
						if(!"I".equals(mailbagVO.getOperationalFlag())){
							mailbagVO.setOperationalFlag("U");
						}
	        			mailbagVO.setScannedDate(sd.setDateAndTime(scanDate,false));
	        			 //Added as part of Bug ICRD-129234 
	        			mailbagVO.setLatestScannedDate(sd.setDateAndTime(scanDate,false));
	        			cnt++;
	        		}
	        		count++;
	        	}
	        }
			containerDetailsVO.setMailDetails(mailDetailsVOs);
			mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
			mailAcceptanceForm.setScanTimeFromScreen("ACCEPTCLOSE");
    		
    	}
    	
    	invocationContext.target = TARGET;
    	
    	log.exiting("ChangeScanTimeOkCommand","execute");
    	
    }
    
    /**
	 * Method to validate form.
	 * @param mailAcceptanceForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailAcceptanceForm mailAcceptanceForm) {
		String scanDate = mailAcceptanceForm.getScanDate();
		String scanTime = mailAcceptanceForm.getScanTime();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(scanDate == null || ("".equals(scanDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.changescantime.scandateempty"));
		}
		if(scanTime == null || ("".equals(scanTime.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.changescantime.scantimeempty"));
		}
		return errors;
	}
       
}
