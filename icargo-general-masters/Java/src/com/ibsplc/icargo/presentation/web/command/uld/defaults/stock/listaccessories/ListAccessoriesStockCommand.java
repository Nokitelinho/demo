/*
 * ListAccessoriesStockCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the stock details of the specified 
 * accessory  
 * @author A-1347
 */
public class ListAccessoriesStockCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_error";
	private static final String LISTSTATUS = "noListForm";
    private static final String SCREEN_ID = 
    				"uld.defaults.stock.listaccessoriesstock";
	private static final String MODULE_NAME = "uld.defaults";


	
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListAccessoriesStockCommand","execute");
        ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        ListAccessoriesStockForm listAccessoriesStockForm = 
			(ListAccessoriesStockForm) invocationContext.screenModel;
        ListAccessoriesStockSession listAccessoriesStockSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
             AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO = 
        	listAccessoriesStockSession.getAccessoriesStockConfigFilterVO();
        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
        listAccessoriesStockForm.setSelectFlag(0);
        Page<AccessoriesStockConfigVO>  accessoryStockConfigList = null;
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(accessoriesStockConfigFilterVO == null){
			accessoriesStockConfigFilterVO = 
									new AccessoriesStockConfigFilterVO();
		}
		if(!(LISTSTATUS.equals(listAccessoriesStockSession.getListStatus()))) {
			errors = validateForm(listAccessoriesStockForm,listAccessoriesStockSession, companyCode);
			updateFilterVO(listAccessoriesStockForm,accessoriesStockConfigFilterVO, companyCode);	
		}
		else {
			listAccessoriesStockSession.setListStatus("");
			listAccessoriesStockForm.setDisplayPage("1");
			listAccessoriesStockForm.setLastPageNum("0");
			
		}
		
		// Added by A-5183 for < ICRD-22824 > Starts				
			if(ListAccessoriesStockForm .PAGINATION_MODE_FROM_LIST.equals(listAccessoriesStockForm.getNavigationMode())
					|| listAccessoriesStockForm.getNavigationMode() == null){
				
				accessoriesStockConfigFilterVO.setTotalRecordsCount(-1);
				listAccessoriesStockForm.setDisplayPage("1");
				
			}else if(ListAccessoriesStockForm .PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(listAccessoriesStockForm.getNavigationMode()))
			{				
				accessoriesStockConfigFilterVO.setTotalRecordsCount(listAccessoriesStockSession.getTotalRecordsCount());
								
			}		
			// Added by A-5183 for < ICRD-22824 > Ends
			
		listAccessoriesStockSession.
			setAccessoriesStockConfigFilterVO(accessoriesStockConfigFilterVO);
		String displayPage = listAccessoriesStockForm.getDisplayPage();
	//updateFilterVO(listAccessoriesStockForm, accessoriesStockConfigFilterVO);
		
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!!!!!inside errors!= null");
		}else{
			log.log(Log.FINE,"!!!inside errors== null");
			accessoryStockConfigList = findAccessoryStockList(
					accessoriesStockConfigFilterVO,displayPage);
	if(accessoryStockConfigList == null || 
	   (accessoryStockConfigList != null &&accessoryStockConfigList.size()==0)){
				log.log(Log.FINE,"!!!inside resultList== null");
				listAccessoriesStockSession.setAccessoriesStockConfigVOs(null);
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.noaccessorystockconfiglistexists");
				errorVO.setErrorDisplayType(ERROR);
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
			listAccessoriesStockForm.
							setSelectFlag(accessoryStockConfigList.size());
			listAccessoriesStockSession.setAccessoriesStockConfigVOs(
    		accessoryStockConfigList);
			listAccessoriesStockSession.setTotalRecordsCount(accessoryStockConfigList.getTotalRecordCount());
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_VIEW);
    		invocationContext.target = LIST_SUCCESS;
		
        log.exiting("ListAccessoriesStockCommand","execute");
    }
    }
    
  private void updateFilterVO(ListAccessoriesStockForm listAccessoriesStockForm,
    		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO, String companyCode){
    	log.entering("ListAccessoriesStockCommand","updateFilterVO");
    	accessoriesStockConfigFilterVO.setCompanyCode(companyCode);
    	String accessoryCode = listAccessoriesStockForm.getAccessoryCode();
    	if(accessoryCode != null && accessoryCode.trim().length() > 0){
    		accessoriesStockConfigFilterVO.setAccessoryCode(
    				accessoryCode.toUpperCase());
    	}
    	else{
    		accessoriesStockConfigFilterVO.setAccessoryCode("");
    	}
    	String airlineCode = listAccessoriesStockForm.getAirlineCode();
    	if(!(("").equals(airlineCode)) && airlineCode.trim().length() > 0){
    		accessoriesStockConfigFilterVO.setAirlineCode(airlineCode);
    		AirlineValidationVO airlineValidationVO = null;
    		try{
		    	airlineValidationVO =  new AirlineDelegate().validateAlphaCode(companyCode,airlineCode);
		    	accessoriesStockConfigFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
		    }catch(BusinessDelegateException businessDelegateException){
		    }
    	}
    	else{
    		accessoriesStockConfigFilterVO.setAirlineCode("");
    		accessoriesStockConfigFilterVO.setAirlineIdentifier(0);

    	}
    	String station = listAccessoriesStockForm.getStation();
    	if(station != null && station.trim().length() > 0){
    		accessoriesStockConfigFilterVO.setStationCode(
    				station.toUpperCase());
    	}
    	else{
    		accessoriesStockConfigFilterVO.setStationCode("");
    	}
    	log.exiting("ListAccessoriesStockCommand","updateFilterVO");
    }
    
    private Collection<ErrorVO> validateForm
    				(ListAccessoriesStockForm listAccessoriesStockForm,
    						ListAccessoriesStockSession listAccessoriesStockSession, String companyCode){
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;
    	String accessoryCode = 
    				listAccessoriesStockForm.getAccessoryCode().toUpperCase();
    	String station = listAccessoriesStockForm.getStation().toUpperCase();
    	String airlineCode=
    				listAccessoriesStockForm.getAirlineCode().toUpperCase();
    	log.log(Log.FINE,"!!!inside validateForm()");
    	if((accessoryCode == null || accessoryCode.trim().length() == 0)
    			&& (station == null  || station.trim().length() == 0)
    			&& (airlineCode == null || airlineCode.trim().length() == 0)){
    		error =new ErrorVO("uld.defaults.listaccessorystock.invalidfilter");
    		error.setErrorDisplayType(ERROR);
    		errors.add(error);
    	}
    	if(airlineCode != null && airlineCode.trim().length() > 0){
		    try{
		    	new AirlineDelegate().validateAlphaCode(companyCode,airlineCode);
		    }catch(BusinessDelegateException businessDelegateException){
		    	log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
		    	errors = handleDelegateException(businessDelegateException);			
		    }
		}
    	if(station != null && station.trim().length() > 0){
    		AirportValidationVO airportValidationVO = null;
    		AreaDelegate airportDelegate=new AreaDelegate();
    		Collection<String> stationColl = new ArrayList<String>();
    		Collection<ErrorVO> errorsAirport = new ArrayList<ErrorVO>();
    		Map<String,AirportValidationVO> voMap = null;
    		stationColl.add(station);
    		try{
    			voMap = 
    			airportDelegate.validateAirportCodes(companyCode,stationColl);
    		}catch(BusinessDelegateException businessDelegateException){
    			log.log(Log.FINE,"inside stationvalidation caught busDelgExcn");
    		errorsAirport=handleDelegateException(businessDelegateException);
    		}
    		if (errorsAirport != null && errorsAirport.size() > 0) {
				if(errors!= null){
					errors.addAll(errorsAirport);
				}
				else {
					errors=errorsAirport;
				}
		
    	}
    	}
    	listAccessoriesStockSession.setAccessoriesStockConfigVOs(null);
    	return errors;
    }
    private Page<AccessoriesStockConfigVO> findAccessoryStockList(
    		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
    		String displayPage){
    	log.entering("ListAccessoriesStockCommand","findAccessoryStockList");
    	Page<AccessoriesStockConfigVO> accessoriesStockConfigVOs = null;
    	int pageNumber = Integer.parseInt(displayPage);
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
    		log.log(Log.FINE,"before calling delegate");
    		ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
    		accessoriesStockConfigVOs = 
    			uLDDefaultsDelegate.findAccessoryStockList(
    				accessoriesStockConfigFilterVO,pageNumber);
    		log.log(Log.FINE,"after calling delegate");
    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"inside findAccessoryStockList caught busDelgExc");
        	businessDelegateException.getMessage();
        	error = handleDelegateException(businessDelegateException);
    	}
    	log.exiting("ListAccessoriesStockCommand","findAccessoryStockList");
    	return accessoriesStockConfigVOs;
    }
}
