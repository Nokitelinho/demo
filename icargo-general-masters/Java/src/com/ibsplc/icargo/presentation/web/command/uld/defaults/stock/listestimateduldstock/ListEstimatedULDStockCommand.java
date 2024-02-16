/*
 * ListEstimatedULDStockCommand.java Created on Sep 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListEstimatedULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the estimated stock details  
 * @author A-2934
 */
public class ListEstimatedULDStockCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_error";
	private static final String LISTSTATUS = "noListForm";
    private static final String SCREEN_ID = 
    				"uld.defaults.stock.listestimateduldstock";
	private static final String MODULE_NAME = "uld.defaults";
	private AirlineValidationVO airlineValidationVO;


	
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListEstimatedULDStockCommand","execute");
        ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        ListEstimatedULDStockForm listEstimatedULDStockForm = 
			(ListEstimatedULDStockForm) invocationContext.screenModel;
        ListEstimatedULDStockSession listEstimatedULDStockSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
        EstimatedULDStockFilterVO estimatedULDStockFilterVO = 
            	 listEstimatedULDStockSession.getEstimatedULDStockFilterVO();
        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
        listEstimatedULDStockForm.setSelectFlag(0);
        Page<EstimatedULDStockVO>  estimatedULDStockVOs = null;
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
      	if(estimatedULDStockFilterVO == null || estimatedULDStockFilterVO.getAbsoluteIndex()==0 ){
			estimatedULDStockFilterVO = 
									new EstimatedULDStockFilterVO();
			if (listEstimatedULDStockForm.getUldType() != null
					&& listEstimatedULDStockForm.getUldType().length() > 0){
				estimatedULDStockFilterVO.setUldType(listEstimatedULDStockForm.getUldType());
			}
			if(listEstimatedULDStockForm.getAirlinecode()!=null && listEstimatedULDStockForm.getAirlinecode().trim().length()>0){
				estimatedULDStockFilterVO.setAirlineCode(listEstimatedULDStockForm.getAirlinecode());
			}
			}
		
	//	log.log(Log.FINE,listEstimatedULDStockForm.getAirlinecode());
		//log.log(Log.FINE,listEstimatedULDStockForm.getAirport());
		
		if ((LISTSTATUS.equals(listEstimatedULDStockSession.getListStatus()))) {
			listEstimatedULDStockForm.setAirport(estimatedULDStockFilterVO.getAirport());
		} else {
			listEstimatedULDStockSession.setListStatus("");
			listEstimatedULDStockForm.setStockdisplayPage("1");
			listEstimatedULDStockForm.setStockLastPageNum("0");
			listEstimatedULDStockSession.setListStatus("noListForm");

		}
		/*
		 * Added for BugFix by Latish 
		 */
		errors = validateAirportCodes(listEstimatedULDStockForm.getAirport(),logonAttributes.getCompanyCode());	
		errors.addAll(validateAirlineCode(listEstimatedULDStockForm.getAirlinecode(),logonAttributes.getCompanyCode()));	
		listEstimatedULDStockSession.setEstimatedULDStockFilterVO(estimatedULDStockFilterVO);
		String displayPage = listEstimatedULDStockForm.getStockdisplayPage();
		updateFilterVO(listEstimatedULDStockForm,listEstimatedULDStockSession, estimatedULDStockFilterVO,companyCode);
		
		
		
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!!!!!inside errors!= null");
		}else{
			log.log(Log.FINE,"!!!inside errors== null");
			estimatedULDStockVOs = findEstimatedULDStockList(
					estimatedULDStockFilterVO,displayPage);
	if(estimatedULDStockVOs == null || 
	   (estimatedULDStockVOs != null &&estimatedULDStockVOs.size()==0)){
				log.log(Log.FINE,"!!!inside resultList== null");
				listEstimatedULDStockSession.setEstimatedULDStockVOs(null);
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.stock.estimateduldstock.msg.err.noaccessorystockconfiglistexists");
				errorVO.setErrorDisplayType(ERROR);
				if(errors == null) {
							errors = new ArrayList<ErrorVO>();
				}
				errors.add(errorVO);
//				invocationContext.addAllError(errors);
			}
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
		}else{
			log.log(Log.FINE,"!!!inside resultList!= null");
			listEstimatedULDStockForm.
							setSelectFlag(estimatedULDStockVOs.size());
			listEstimatedULDStockForm.setListStatus(LIST_SUCCESS);
			listEstimatedULDStockSession.setEstimatedULDStockVOs(
					estimatedULDStockVOs);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_VIEW);
    		invocationContext.target = LIST_SUCCESS;
		
        log.exiting("ListEstimatedULDStockCommand","execute");
    }
    }
    
  private void updateFilterVO(ListEstimatedULDStockForm listEstimatedULDStockForm,
		  ListEstimatedULDStockSession listEstimatedULDStockSession,
		  EstimatedULDStockFilterVO estimatedULDStockFilterVO,String companyCode){
    	log.entering("ListEstimatedULDStockCommand","updateFilterVO");
    	estimatedULDStockFilterVO.setCompanyCode(companyCode);

    	String station = listEstimatedULDStockForm.getAirport();
    	if(station != null && station.trim().length() > 0){
    		estimatedULDStockFilterVO.setAirport(station.toUpperCase());
    	}
    	else{
    		estimatedULDStockFilterVO.setAirport("");
    	}
    	log.log(Log.INFO, "listEstimatedULDStockForm.getUldType()==========>",
				listEstimatedULDStockForm.getUldType());
		String uldTypeCode=listEstimatedULDStockForm.getUldType();
		if (uldTypeCode != null && uldTypeCode.trim().length() > 0
				/*&& estimatedULDStockFilterVO.getUldType() != null
				&& estimatedULDStockFilterVO.getUldType().length()>0*/) {
    		estimatedULDStockFilterVO.setUldType(uldTypeCode);
    	}
    	else{
    		estimatedULDStockFilterVO.setUldType("");
    	}
		String airlineCode =  listEstimatedULDStockForm.getAirlinecode();
		if(airlineCode != null && airlineCode.trim().length() > 0){
    		estimatedULDStockFilterVO.setAirlineCode(airlineCode.toUpperCase());
    		if(airlineValidationVO!=null && airlineValidationVO.getAirlineIdentifier()!=0){
    			estimatedULDStockFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
    		}    		
    	}
    	else{
    		estimatedULDStockFilterVO.setAirlineCode("");
    	}
		HashMap<String, String> indexMap = null;
	//	HashMap<String, String> finalMap = null;
		/*if (listEstimatedULDStockSession.getIndexMap() != null) {
			indexMap = listEstimatedULDStockSession.getIndexMap();
		}*/
		indexMap=listEstimatedULDStockSession.getIndexMap();
		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
    	int nAbsoluteIndex = 1;
		String displayPage = listEstimatedULDStockForm.getStockdisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		listEstimatedULDStockSession.setIndexMap(indexMap);
		estimatedULDStockFilterVO.setAbsoluteIndex(nAbsoluteIndex);
    	log.exiting("ListEstimatedULDStockCommand","updateFilterVO");
    }
    
   /**
    * 
    * @param airport
    * @param companyCode
    * @return
    */
   private Collection<ErrorVO> validateAirportCodes(String airport , String companyCode){
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();    	
    	if( (airport == null  || airport.trim().length() == 0)){
    		ErrorVO error =new ErrorVO("uld.defaults.stock.estimateduldstock.msg.err.airportmandatory");
    		error.setErrorDisplayType(ERROR);
    		if(errors == null) {
    			errors = new ArrayList<ErrorVO>();
    		}
    		errors.add(error);
    	}else{
    		AreaDelegate airportDelegate=new AreaDelegate();
    		Collection<String> airports = new ArrayList<String>();
    		Collection<ErrorVO> errorsAirport = null;
    		airports.add(airport);
    		Map<String, AirportValidationVO> airportMap=null;
    		try{
    			airportMap = airportDelegate.validateAirportCodes(companyCode,airports);
    		}catch(BusinessDelegateException businessDelegateException){
    		  errorsAirport=handleDelegateException(businessDelegateException);
    		  if(errors == null) {
    		   errors = new ArrayList<ErrorVO>();
    		  }
    		  errors.addAll(errorsAirport);
    		}
    		if(errors == null && (airportMap == null || airportMap.size() ==0)){
    			ErrorVO error =new ErrorVO("uld.defaults.stock.estimateduldstock.msg.err.invalidairport");
        		error.setErrorDisplayType(ERROR);
        		if(errors == null) {
        			errors = new ArrayList<ErrorVO>();
        		}
        		errors.add(error);
    		}
    		
    		
    	}
    	return errors;
    }
   private Collection<ErrorVO> validateAirlineCode(String airline , String companyCode){   	
	   	Collection<ErrorVO> errors  = new ArrayList<ErrorVO>();  	
	   	if( (airline == null  || airline.trim().length() == 0)){
	   		ErrorVO error =new ErrorVO("uld.defaults.stock.estimateduldstock.msg.err.airlinemandatory");
	   		error.setErrorDisplayType(ERROR);
	   		if(errors == null) {
	   			errors = new ArrayList<ErrorVO>();
	   		}
	   		errors.add(error);
	   	}else{
	   		
	   		AirlineValidationVO airlineVO = null;
	   		Collection<ErrorVO> errorsAirline = null;
	   		try{
	   			airlineVO = new AirlineDelegate().validateAlphaCode(companyCode,airline);
	   			airlineValidationVO = airlineVO;
	   		}catch(BusinessDelegateException businessDelegateException){
	   			errorsAirline=handleDelegateException(businessDelegateException);
	   		  if(errors == null) {
	   		   errors = new ArrayList<ErrorVO>();
	   		  }
	   		  errors.addAll(errorsAirline);
	   		}
	   		if(errors == null && (airlineVO == null)){
	   			ErrorVO error =new ErrorVO("uld.defaults.stock.estimateduldstock.msg.err.invalidairline");
	       		error.setErrorDisplayType(ERROR);
	       		if(errors == null) {
	       			errors = new ArrayList<ErrorVO>();
	       		}
	       		errors.add(error);
	   		}   		
    	}
    	return errors;
    }
    
    private Page<EstimatedULDStockVO> findEstimatedULDStockList(
    		EstimatedULDStockFilterVO estimatedULDStockFilterVO,
    		String displayPage){
    	log.entering("ListEstimatedULDStockCommand","findEstimatedULDStockList");
    	Page<EstimatedULDStockVO> estimatedULDStockVOs = null;
    	int pageNumber = Integer.parseInt(displayPage);
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
    		log.log(Log.FINE,"before calling delegate");
    		ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
    		estimatedULDStockVOs = 
    			uLDDefaultsDelegate.findEstimatedULDStockList(
    					estimatedULDStockFilterVO,pageNumber);
    		log.log(Log.FINE,"after calling delegate");
    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"inside findEstimatedULDStockList caught busDelgExc");
        	businessDelegateException.getMessage();
        	error = handleDelegateException(businessDelegateException);
    	}
    	log.log(Log.INFO, "estimatedULDStockVOs", estimatedULDStockVOs);
		log.exiting("ListEstimatedULDStockCommand","findAccessoryStockList");
    	return estimatedULDStockVOs;
    }
}
