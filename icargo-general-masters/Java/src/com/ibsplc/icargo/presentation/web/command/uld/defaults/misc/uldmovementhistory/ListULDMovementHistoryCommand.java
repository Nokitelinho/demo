/*
 * ListULDMovementHistoryCommand.java Created on Feb 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the 
 * 	list button the ULDMovementHistory screen
 * 
 * @author A-2122
 */
public class ListULDMovementHistoryCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID =
								"uld.defaults.misc.listuldmovement";	
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String LISTSTATUS = "recorduld";

    

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
       
    	 log.entering("ListULDMovementHistoryCommand","execute");
    	 ApplicationSessionImpl applicationSession = getApplicationSession();
         LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
         String companyCode = logonAttributes.getCompanyCode().toUpperCase();
         ListULDMovementForm listULDMovementForm = 
 			(ListULDMovementForm) invocationContext.screenModel;
         ListULDMovementSession listUldMovementSession = 
 			getScreenSession(MODULE, SCREENID);
    	String uldNumber = listULDMovementForm.getUldNumber().toUpperCase();
    	ULDMovementFilterVO uldMovementFilterVO = 
    						listUldMovementSession.getUldMovementFilterVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	//Collection<ErrorVO> errorsv = new ArrayList<ErrorVO>();
    	
    	String displayPage= null;
    	displayPage=listULDMovementForm.getDisplayPage();
    	if("1".equals(listULDMovementForm.getDisplayPageFlag())){
    		displayPage="1";
    		listULDMovementForm.setDisplayPageFlag("");
    	}
    	int pageNumber = Integer.parseInt(displayPage); 
    	if(uldMovementFilterVO == null){
    		uldMovementFilterVO = new ULDMovementFilterVO();
    	}
    	
    	if(!(LISTSTATUS.equals(listUldMovementSession.getListStatus()))&&
		(!("recorduld".equals(listULDMovementForm.getStatusFlag())))) {
    		//errors = validateForm(listULDMovementForm,listUldMovementSession,companyCode);
    	   	updateFilterVO(listULDMovementForm,listUldMovementSession, uldMovementFilterVO,companyCode);
    	   	log.log(Log.INFO, "AFTER UPDATION FILTER VO IS ---->>",
					uldMovementFilterVO);
    	}
    	else {
    		listUldMovementSession.setListStatus("");
    		listULDMovementForm.setStatusFlag("");
    	}
    	
    	
    	
    	if(listUldMovementSession.getTotalRecords() > 0)
    	  {
    		uldMovementFilterVO.setTotalRecords(listUldMovementSession.getTotalRecords());
    		uldMovementFilterVO.setPageNumber(Integer.parseInt(listULDMovementForm.getDisplayPage()));
          }
    		
    	else{
    		uldMovementFilterVO.setTotalRecords(-1);
    	}
    		
    	listUldMovementSession.setUldMovementFilterVO(uldMovementFilterVO);
    	if (errors != null && errors.size() > 0) {	
    		 invocationContext.addAllError(errors);
          	 listULDMovementForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
          	listUldMovementSession.setUldMovementDetails(null);
          	 invocationContext.target = LIST_FAILURE;   
          	 return;
      	}
    	
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	Page<ULDMovementDetailVO> uldMovementDetailVOs =
    		new Page<ULDMovementDetailVO>
    			(new ArrayList<ULDMovementDetailVO>(), 0, 0, 0, 0, 0, false);
    	RequestDispatcher.startBatch();
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	try{
    		log.log(Log.FINE,"before calling delegate");
    		ULDDefaultsDelegate uLDDefaultsDelegate =new ULDDefaultsDelegate();
    		uldMovementDetailVOs = uLDDefaultsDelegate.findULDMovementHistory(
    				uldMovementFilterVO,pageNumber);
    		uldValidationVO = uLDDefaultsDelegate.
    								validateULD(companyCode,uldNumber);
       		log.log(Log.FINE, "uldValidationVO-->", uldValidationVO);
			log.log(Log.FINE,"after calling delegate");
    	   }catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"inside try...caught businessDelegateException");
        	businessDelegateException.getMessage();
        	error = handleDelegateException(businessDelegateException);
    	   }  
    	   try {
				BatchedResponse response[] = RequestDispatcher.executeBatch();
				log.log(Log.INFO, "Response length:--", response.length);
				log.log(Log.INFO, "Response 0( uldMovementDetailVOs)->",
						response[0].getReturnValue());
				log.log(Log.INFO, "Response 0( uldValidationVO)----->",
						response[1].getReturnValue());
				if(!response[0].hasError()) {						
					uldMovementDetailVOs =
					(Page<ULDMovementDetailVO>)response[0].getReturnValue();						
					log.log(Log.INFO, "uldMovementDetailVOs:---",
							uldMovementDetailVOs);
				}
				if(!response[1].hasError()) {
					uldValidationVO =
						(ULDValidationVO)response[1].getReturnValue();	
					log.log(Log.INFO, "uldValidationVO:---", uldValidationVO);
				}
    	   		}catch (DispatcherException dispatcherException) {
				dispatcherException.getMessage();
			}
    	   		listULDMovementForm.setListStatus("");
    	   		
    	    	if(uldValidationVO == null){
    				//Object[] obj = { "uldValidationVO" };
    //				log.log(Log.FINE,"vo null...uldValidationVO-->"+uldValidationVO);
    				ErrorVO errorValidation = new 
    							ErrorVO("uld.defaults.uldvalidationvonotfound");
    				errorValidation.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorValidation);
    		//		invocationContext.addAllError(errors);
    				listULDMovementForm.setScreenStatusFlag
    				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    				 if(uldMovementDetailVOs != null && 
    							uldMovementDetailVOs.size() > 0){
    					 listULDMovementForm.setScreenStatusFlag
    						(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    				 }
    				// listUldMovementSession.setUldMovementDetails(null);//commented by A-5844 for ICRD-138808
    				invocationContext.target = LIST_FAILURE;
    				//return;
    			}
    	    	else{
    	    		if(uldValidationVO.getCurrentStation()!= null){
    	    		listULDMovementForm.
    	    			setCurrentStn(uldValidationVO.getCurrentStation());
    	    		}
    	    		if(uldValidationVO.getOverallStatus()!= null){
    	    		listULDMovementForm.
    	    		setCurrentStatus(uldValidationVO.getOverallStatus().toUpperCase());
    	    		}
    	    		if(uldValidationVO.getOwnerAirlineCode()!= null){
    	    		listULDMovementForm.
    	    		        setOwnerCode(uldValidationVO.getOwnerAirlineCode());
    	    		}
    	    		if(uldValidationVO.getOwnerStation()!= null){
    	    		listULDMovementForm.
    	    		        setOwnerStation(uldValidationVO.getOwnerStation());
    	    		}    	    		
    	    	 }	    	   		
    	    	if(uldMovementDetailVOs == null 
    					|| (uldMovementDetailVOs != null && 
    							uldMovementDetailVOs.size() == 0)){
    				log.log(Log.FINE,"!!!inside resultList== null");
    				ErrorVO errorVO = new ErrorVO(
    						"uld.defaults.nouldmovementdetailsexists");
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
    				invocationContext.addAllError(errors);
    				listUldMovementSession.setUldMovementDetails(null);
    				listULDMovementForm.setScreenStatusFlag
    				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    				listUldMovementSession.setUldMovementDetails(null);
    				invocationContext.target = LIST_FAILURE;
    				 
    			  	}
    	    	else{
    	    		listULDMovementForm.setListStatus("N");
    				log.log(Log.FINE,"!!!inside resultList!= null");
    				log.log(Log.FINE, "@@@uldMovementDetailVOs.size--->",
    						uldMovementDetailVOs.size());
    				//Added by A-7794 as part of ICRD-226494
    				for(ULDMovementDetailVO vo : uldMovementDetailVOs){
    					if(MailConstantsVO.DESTN_FLT_STR.equals(vo.getFlightNumber())){
    						vo.setFlightNumber("");
    						vo.setFlightDate(null);
    					}
    				}
    				listUldMovementSession.setUldMovementDetails(uldMovementDetailVOs);
    				listUldMovementSession.setTotalRecords(uldMovementDetailVOs.getTotalRecordCount());
    				log.log(Log.FINE, "size->", listUldMovementSession.getUldMovementDetails().size());
    				new ICargoComponent().setScreenStatusFlag(
    		  				ComponentAttributeConstants.SCREEN_STATUS_VIEW);
    				
    			}
	
    	log.log(Log.INFO, "listUldMovementSession.getUldMovementFilterVO()",
				listUldMovementSession.getUldMovementFilterVO());
    	listULDMovementForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);    	
    	invocationContext.target = LIST_SUCCESS;	
    	log.exiting("ListULDMovementHistoryCommand","execute");
       }   
    
    
    private void updateFilterVO(ListULDMovementForm listULDMovementForm, ListULDMovementSession listUldMovementSession,
    		ULDMovementFilterVO uldMovementFilterVO,String companyCode){
    	log.entering("ListULDMovementHistoryCommand","updateFilterVO");
    	uldMovementFilterVO.setCompanyCode(companyCode);
    		String uldNumber =listULDMovementForm.
    								getUldNumber().toUpperCase();    
    		log.log(Log.INFO, " ULDNUMBER IS---------------------------->> ",
					uldNumber);
			if(uldNumber != null && 
    				uldNumber.trim().length() > 0){
    		uldMovementFilterVO.setUldNumber(uldNumber);
    	}
    	else {
			uldMovementFilterVO.setUldNumber("");
		}
    	
    	if(listUldMovementSession.getUldMovementFilterVO() !=null && listUldMovementSession.getUldMovementFilterVO().getFromDate() != null  
    			) {
    	
			/*	LocalDate frmDate = new LocalDate(getApplicationSession().
										getLogonVO().getAirportCode(),Location.ARP,false);
			   frmDate.setDate(listUldMovementSession.getUldMovementFilterVO().getFromDate());*/
				uldMovementFilterVO.setFromDate(listUldMovementSession.getUldMovementFilterVO().getFromDate());
					
    		
    	}
    	else {
    			uldMovementFilterVO.setFromDate(null);
    		}
    	if(listUldMovementSession.getUldMovementFilterVO() !=null && listUldMovementSession.getUldMovementFilterVO().getToDate() != null
    			) {
    		
				/*LocalDate toDate = new LocalDate(getApplicationSession().
										getLogonVO().getAirportCode(),Location.ARP,false);
				toDate.setDate(listUldMovementSession.getUldMovementFilterVO().getToDate());*/
				uldMovementFilterVO.setToDate(listUldMovementSession.getUldMovementFilterVO().getToDate());
					
    		
    	}
    	else{
    		uldMovementFilterVO.setToDate(null);
    	}
    	
    	log.log(Log.FINE, "uldMovementFilterVO------->", uldMovementFilterVO);
		log.exiting("ListULDMovementHistoryCommand","updateFilterVO");
   }
   
    
    
    
    
  
    	
    	    	
 }

