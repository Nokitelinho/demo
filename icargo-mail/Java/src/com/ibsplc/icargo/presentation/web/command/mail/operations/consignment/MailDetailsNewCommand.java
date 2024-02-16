/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.MailDetailsNewCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	17-Jul-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */ 
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;
 

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;


import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentMailPopUpVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailDetailsNewCommand  extends BaseCommand{

	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
	   private static final String TARGET = "success_new";
	   private static final String TARGET_FAIL = "failure_new";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	
		log.entering("MailDetailsNewCommand","execute");
	     ConsignmentForm form = (ConsignmentForm)invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);    
		 LinkedHashMap<String, ConsignmentMailPopUpVO> multipleMailDetailsMap=null;//Modified by a-7871 for ICRD-220439
		 ConsignmentMailPopUpVO consignmentMailPopUpVO = new ConsignmentMailPopUpVO();
		MailInConsignmentVO mailVO = new MailInConsignmentVO();
		
		 String keys=new StringBuilder().append(form.getOrginOfficeOfExchange()).append(form.getDestOfficeOfExchange()).
				 append(form.getMailCategory()).append(form.getMailClassType()).append(form.getMailYear()).
				 append(form.getMailDsn()).append(form.getHighestNumberIndicator()).append(form.getRegisteredIndicator()).append(form
					.getRsnRangeFrom()).append(form.getRsnRangeTo()).toString();
		 
		 int  displayPageNum = Integer.parseInt(form
					.getDisplayPopupPage());
		 
		 if("NEW".equals(form.getActionName()))

		 {       multipleMailDetailsMap= (LinkedHashMap<String, ConsignmentMailPopUpVO>) consignmentSession.getMultipleMailDetailsMap();                     
			 consignmentMailPopUpVO.setOrginOfficeOfExchange(form.getOrginOfficeOfExchange());
			 consignmentMailPopUpVO.setDestOfficeOfExchange(form.getDestOfficeOfExchange());
			 consignmentMailPopUpVO.setMailCategory(form.getMailCategory());
			 consignmentMailPopUpVO.setMailClassType(form.getMailClassType());
			 consignmentMailPopUpVO.setMailSubClass(form.getMailSubClass());
			 consignmentMailPopUpVO.setMailDsn(form.getMailDsn());
			 consignmentMailPopUpVO.setMailYear(form.getMailYear());
			 consignmentMailPopUpVO.setHighestNumberIndicator(form.getHighestNumberIndicator());
			 consignmentMailPopUpVO.setRegisteredIndicator(form.getRegisteredIndicator());
			 consignmentMailPopUpVO.setRsnRangeFrom(form.getRsnRangeFrom());
			 consignmentMailPopUpVO.setRsnRangeTo(form.getRsnRangeTo());  
			/* if(form.getRsnRangeTo()!=null && form.getRsnRangeTo().trim().length()>0 ){//added by A-7371 for ICRD-221978  // commented as this is handled in AddMultipleMailOKCommand
				 consignmentMailPopUpVO.setRsnRangeTo(form.getRsnRangeTo());      
				 }else
				 {//setting RsnRangeTo with RsnRangeFrom value for working of a concept written in populateMailDetails in AddMultipleOKCommand  
				 consignmentMailPopUpVO.setRsnRangeTo(form.getRsnRangeFrom());
				 }
			*/
			 if(multipleMailDetailsMap!=null){
					multipleMailDetailsMap.put(keys, consignmentMailPopUpVO);
						}
						else{
						multipleMailDetailsMap=new LinkedHashMap<String, ConsignmentMailPopUpVO>();
						multipleMailDetailsMap.put(keys,consignmentMailPopUpVO);
						}
		 consignmentSession.setMultipleMailDetailsMap(multipleMailDetailsMap);
		  consignmentSession.setConsignmentMailPopUpVO(consignmentMailPopUpVO);	
		 form.setRsnRangeFrom(null);
		 form.setRsnRangeTo(null);
		//form.setDisplayPopupPage(String.valueOf(displayPageNum+1));  
		// form.setTotalViewRecords(String.valueOf(consignmentSession.getMultipleMailDetailsMap().size()));
		// form.setDisplayPage(String.valueOf(displayPageNum+1));
		form.setDisplayPopupPage(String.valueOf(displayPageNum+1));//Added by a-7871 for ICRD-220439
		form.setTotalViewRecords(String.valueOf(consignmentSession.getMultipleMailDetailsMap().keySet().size()+1));	
		 form.setLastPopupPageNum(form.getTotalViewRecords());
		 }	 
		 
		 
	
	 if("NEXT".equals(form.getActionName()))  
	 { 
		 //form.setLastPopupPageNum(form.getTotalViewRecords());			 
         consignmentMailPopUpVO.setOrginOfficeOfExchange(form.getOrginOfficeOfExchange());
		 consignmentMailPopUpVO.setDestOfficeOfExchange(form.getDestOfficeOfExchange());
		 consignmentMailPopUpVO.setMailCategory(form.getMailCategory());
		 consignmentMailPopUpVO.setMailClassType(form.getMailClassType());
		 consignmentMailPopUpVO.setMailSubClass(form.getMailSubClass());
		 consignmentMailPopUpVO.setMailDsn(form.getMailDsn());
		 consignmentMailPopUpVO.setMailYear(form.getMailYear());
		 consignmentMailPopUpVO.setHighestNumberIndicator(form.getHighestNumberIndicator());
		 consignmentMailPopUpVO.setRegisteredIndicator(form.getRegisteredIndicator());
		 consignmentMailPopUpVO.setRsnRangeFrom(form.getRsnRangeFrom());
		 consignmentMailPopUpVO.setRsnRangeTo(form.getRsnRangeTo());
		 multipleMailDetailsMap=(LinkedHashMap<String, ConsignmentMailPopUpVO>) consignmentSession.getMultipleMailDetailsMap();
		 
		 if(multipleMailDetailsMap!=null){
				multipleMailDetailsMap.put(keys, consignmentMailPopUpVO);
					}
					else{
					multipleMailDetailsMap=new LinkedHashMap<String, ConsignmentMailPopUpVO>();
					multipleMailDetailsMap.put(keys,consignmentMailPopUpVO);      
					}
	
	 consignmentSession.setMultipleMailDetailsMap(multipleMailDetailsMap);
		Collection<String> mailbagkeys = new ArrayList<String>();
		if(multipleMailDetailsMap!=null)
		{
		mailbagkeys.addAll(multipleMailDetailsMap.keySet());
		
		//Collection<String> mailbagkeys = multipleMailDetailsMap.keySet();
		 
		String selectedKey = String.valueOf(displayPageNum);
		Collection<ConsignmentMailPopUpVO> consignmentMailPopUpVOs=new ArrayList<ConsignmentMailPopUpVO>();
		consignmentMailPopUpVOs=multipleMailDetailsMap.values();
		int pageNumber=0;        
		ConsignmentMailPopUpVO selectedConsignmentMailPopUpVO=null;
		for(ConsignmentMailPopUpVO consignmentMailVO:consignmentMailPopUpVOs){
			pageNumber++;
			if(pageNumber==Integer.parseInt(selectedKey)){      
				selectedConsignmentMailPopUpVO=consignmentMailVO;   
				break;
			}
			
		}
		if(selectedConsignmentMailPopUpVO!=null){           
        form.setOrginOfficeOfExchange(selectedConsignmentMailPopUpVO.getOrginOfficeOfExchange());
        form.setDestOfficeOfExchange(selectedConsignmentMailPopUpVO.getDestOfficeOfExchange());
        form.setMailYear(selectedConsignmentMailPopUpVO.getMailYear());
        form.setMailCategory(selectedConsignmentMailPopUpVO.getMailCategory());
        form.setMailClassType(selectedConsignmentMailPopUpVO.getMailClassType());
        form.setMailSubClass(selectedConsignmentMailPopUpVO.getMailSubClass());
        form.setMailDsn(selectedConsignmentMailPopUpVO.getMailDsn());
        form.setHighestNumberIndicator(selectedConsignmentMailPopUpVO.getHighestNumberIndicator());
        form.setRegisteredIndicator(selectedConsignmentMailPopUpVO.getRegisteredIndicator());
        form.setRsnRangeFrom(selectedConsignmentMailPopUpVO.getRsnRangeFrom());      
        form.setRsnRangeTo(selectedConsignmentMailPopUpVO.getRsnRangeTo());          
      //Added by a-7871 for ICRD-220439 starts 
        consignmentSession.getConsignmentMailPopUpVO().setRsnRangeFrom(selectedConsignmentMailPopUpVO.getRsnRangeFrom());
        consignmentSession.getConsignmentMailPopUpVO().setRsnRangeTo(selectedConsignmentMailPopUpVO.getRsnRangeTo());
      //Added by a-7871 for ICRD-220439 ends
		}   
		} 
		form.setTotalViewRecords(String.valueOf(consignmentSession.getMultipleMailDetailsMap().keySet().size()));
		form.setLastPopupPageNum(form.getTotalViewRecords());  
	 }
	// form.setLastPopupPageNum(String.valueOf(displayPageNum+1));
	// form.setDisplayPopupPage(String.valueOf(displayPageNum+1));  
		invocationContext.target = TARGET;
        log.exiting("MailDetailsNewCommand","execute");
		
	}

}
