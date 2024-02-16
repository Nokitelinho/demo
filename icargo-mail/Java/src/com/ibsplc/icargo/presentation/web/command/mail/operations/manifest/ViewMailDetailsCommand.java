/*
 * ViewMailDetailsCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
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
public class ViewMailDetailsCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ViewMailDetailsCommand","execute");
    	  
    	MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
		
		ContainerDetailsVO containerDetailsVO = 
	        	((ArrayList<ContainerDetailsVO>)mailManifestVO.getContainerDetails()).get(Integer.parseInt(mailManifestForm.getParentContainer()));
	   
        Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
        containerDetailsVOs.add(containerDetailsVO);
          if(containerDetailsVO.getDesptachDetailsVOs() == null){
        	 Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
        	 int flag = 0;
        	 if(dsnVOs != null && dsnVOs.size() > 0){
	        	for(DSNVO dsnVO:dsnVOs){
	        		if(dsnVO.getMailbags() != null && dsnVO.getMailbags().size()>0){
	        			flag = 1;
	        		}
	        	}
        	 }
        	 if(flag == 0){
	        	   try {
	        		    containerDetailsVOs = new MailTrackingDefaultsDelegate().findMailbagsInContainerForManifest(containerDetailsVOs);
	               }catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     	       }
        	 }
          }
        ContainerDetailsVO containerDtlsVO = ((ArrayList<ContainerDetailsVO>)containerDetailsVOs).get(0);
        ContainerDetailsVO updatedContainerDtlsVO = new ContainerDetailsVO();
        Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
        try{
        if(mailManifestVO.getContainerDetails() != null && mailManifestVO.getContainerDetails().size() > 0){
        	for(ContainerDetailsVO contDtlsVO:mailManifestVO.getContainerDetails()){
        		if(contDtlsVO.getContainerNumber().equals(containerDtlsVO.getContainerNumber())){
        			contDtlsVO.setDesptachDetailsVOs(containerDtlsVO.getDesptachDetailsVOs());
        			Collection<DSNVO> serverdsnVOs = containerDtlsVO.getDsnVOs();
        			Collection<DSNVO> dsnVOs = contDtlsVO.getDsnVOs();
        			 if(serverdsnVOs != null && serverdsnVOs.size() > 0){
    		        	for(DSNVO dsn:dsnVOs){
    		        		for(DSNVO serverdsn:serverdsnVOs){
    		        			if(dsn.getOriginExchangeOffice().equals(serverdsn.getOriginExchangeOffice())
		   			       			&& dsn.getDestinationExchangeOffice().equals(serverdsn.getDestinationExchangeOffice())
		   			       			&& dsn.getMailCategoryCode().equals(serverdsn.getMailCategoryCode())
		   			       			&& dsn.getMailSubclass().equals(serverdsn.getMailSubclass())
		   			       			&& dsn.getDsn().equals(serverdsn.getDsn())
		   			       			&& dsn.getYear() == serverdsn.getYear()){
    		        				dsn.setMailbags(serverdsn.getMailbags());
    		        			}
    		        		}
    		        	}
    		        }
        			contDtlsVO.setDsnVOs(dsnVOs);
        			newContainerDetailsVOs.add(contDtlsVO);
       	    		BeanHelper.copyProperties(updatedContainerDtlsVO,contDtlsVO);
        		}else{
        			newContainerDetailsVOs.add(contDtlsVO);
        		}
        	}
        }
        }catch(SystemException systemException){
    		systemException.getMessage();
    	}
        mailManifestVO.setContainerDetails(newContainerDetailsVOs);
        mailManifestSession.setMailManifestVO(mailManifestVO);
        
        String[] selected = mailManifestForm.getSelectDSN();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");
		
	    int cnt=0;
	    int count = 0;
        int primaryKeyLen = primaryKey.length;
        Collection<DSNVO> dsnVOs = updatedContainerDtlsVO.getDsnVOs();
        Collection<DespatchDetailsVO> ddVOs = updatedContainerDtlsVO.getDesptachDetailsVOs();
        Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
        Collection<DespatchDetailsVO> despatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
        
        if (dsnVOs != null && dsnVOs.size() != 0) {
       	for (DSNVO dsnVO : dsnVOs) {
       		String primaryKeyFromVO = String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			if("Y".equals(dsnVO.getPltEnableFlag())){
       				Collection<MailbagVO> mbVOs = dsnVO.getMailbags();
       				if (mbVOs != null && mbVOs.size() != 0) {
       					mailbagVOs.addAll(mbVOs);
       				}
       			}else{
       				if (ddVOs != null && ddVOs.size() != 0) {
       			       	for (DespatchDetailsVO ddVO : ddVOs) {
       			       		if(ddVO.getOriginOfficeOfExchange().equals(dsnVO.getOriginExchangeOffice())
       			       			&& ddVO.getDestinationOfficeOfExchange().equals(dsnVO.getDestinationExchangeOffice())
       			       			//&& ddVO.getMailClass().equals(dsnVO.getMailClass())
       			       			//added by anitha
       			       			&& ddVO.getMailCategoryCode().equals(dsnVO.getMailCategoryCode())
       			       			&& ddVO.getMailSubclass().equals(dsnVO.getMailSubclass())
       			       			&& ddVO.getDsn().equals(dsnVO.getDsn())
       			       			&& ddVO.getYear() == dsnVO.getYear()){
       			       			despatchDetailsVOs.add(ddVO);
       			       		
       			       		}
       			       	}
       				}
       			}
       			cnt++;
       		}
       		count++;
       	  }
       	}
        
        
        /**
    	* selestTab in js, is to identify which tab to be shown.
    	*/
        if (despatchDetailsVOs != null && despatchDetailsVOs.size() != 0) {
        	mailManifestForm.setSelectTab("D");
        }
        if (mailbagVOs != null && mailbagVOs.size() != 0) {
        	if(!"D".equals(mailManifestForm.getSelectTab())){
        	     mailManifestForm.setSelectTab("M");
        	}
        }
       
      
        updatedContainerDtlsVO.setMailDetails(mailbagVOs);
        updatedContainerDtlsVO.setDesptachDetailsVOs(despatchDetailsVOs); 
      mailManifestSession.setContainerDetailsVO(updatedContainerDtlsVO);
       
	  invocationContext.target = TARGET;
      log.exiting("ViewMailDetailsCommand","execute");
    	
   }

}
