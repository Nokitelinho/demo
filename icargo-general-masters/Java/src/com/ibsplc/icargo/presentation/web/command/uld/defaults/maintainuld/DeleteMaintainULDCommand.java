/*
 * DeleteMaintainULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to delete the details of the specified ULD
 *
 * @author A-2001
 */
public class DeleteMaintainULDCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("DeleteMaintainUld");
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	
	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREENID_LISTULD =
		"uld.defaults.listuld";
	
	private static final String MODULE_DAMAGE = "uld.defaults";

	private static final String SCREENID_DAMAGE =
		"uld.defaults.listdamagereport";
	
	private static final String MODULE_LISTREPAIR = "uld.defaults";

	private static final String SCREENID_LISTREPAIR =
		"uld.defaults.listrepairreport";
	
	private static final String DELETEULD_SUCCESS = "deleteuld_success";
	private static final String DELETEULD_FAILURE = "deleteuld_failure";
	private static final String DELETE_LISTCREATE = "delete_listcreate";
	private static final String DELETE_LISTDETAIL = "delete_listdetail";
	private static final String DELETE_LISTREPAIR = "delete_listrepair";
	private static final String DELETE_LISTDAMAGE = "delete_listdamage";
	private static final String DELETE_LOANBORROW = "delete_loanborrow";
	private static final String ULD_IS_OCCUPIED_AT_WAREHOUSE =
		"uld.defaults.uldisoccupiedatwarehouse";	
    
	private static final String ULD_EXISTS_AT_AIRPORT = "operations.flthandling.uldexistsatairport";

	private static final String ULD_EXISTS_IN_LOADPLAN = "operations.flthandling.uldexistsinloadplan";

	private static final String ULD_EXISTS_IN_MANIFEST = "operations.flthandling.uldexistsinmanifest";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode=logonAttributes.getCompanyCode();
		String airportCode=logonAttributes.getAirportCode();
		boolean isAirlineUser=logonAttributes.isAirlineUser();
    	MaintainULDForm maintainUldForm = 
    						(MaintainULDForm)invocationContext.screenModel;
    	MaintainULDSession maintainULDSession = 
    			getScreenSession(MODULE, SCREENID);
    	
    	ListULDSession listULDSession = 
	      	  (ListULDSession)getScreenSession(MODULE_LISTULD,SCREENID_LISTULD);
  	    ListRepairReportSession listRepairReportSession = 
    	  (ListRepairReportSession)getScreenSession(MODULE_LISTREPAIR,SCREENID_LISTREPAIR);
    	ListDamageReportSession listDamageReportSession = 
      	  (ListDamageReportSession)getScreenSession(MODULE_DAMAGE,SCREENID_DAMAGE);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	if(maintainULDSession.getULDMultipleVOs() != null &&
    			maintainULDSession.getULDMultipleVOs().size() > 0) {
    		
        	int currentPage = Integer.parseInt(
        							maintainUldForm.getCurrentPage());
        	int totalRecords = Integer.parseInt(
        							maintainUldForm.getTotalRecords());
        	String currentUldNumber = 
        		maintainULDSession.getUldNosForNavigation().get(currentPage-1);
        	ULDVO uldVO = 
        		maintainULDSession.getULDMultipleVOs().get(currentUldNumber);
        	uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
			uldVO.setCompanyCode(logonAttributes.getCompanyCode());
			ErrorVO err=null;
			err=validateULD(uldVO,compCode,airportCode,isAirlineUser);
			if(err!=null){
				invocationContext.addError(err);
    			invocationContext.target = DELETEULD_FAILURE;	
    			return;
			}
			
    		try {
				new ULDDefaultsDelegate().saveULD(uldVO);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
    		log.log(Log.FINE, "Inside delete uldVO------>>>", uldVO);
			if(errors != null &&
    			errors.size() > 0 ) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = DELETEULD_FAILURE;
    		}
    		maintainULDSession.getUldNosForNavigation().remove(currentPage - 1);
    		log
					.log(Log.FINE, "Inside delete currentPage------>>>",
							currentPage);
			log.log(Log.FINE, "Inside delete currentUldNumber------>>>",
					currentUldNumber);
			log.log(Log.FINE, "Inside delete getUldNosForNavigation------>>>",
					maintainULDSession.getUldNosForNavigation());
			maintainULDSession.getULDMultipleVOs().remove(currentUldNumber);
    		--totalRecords;
    		if(currentPage != 1) {
    			--currentPage;
    		}
    		maintainUldForm.setTotalRecords(Integer.toString(totalRecords));
    		maintainUldForm.setCurrentPage(Integer.toString(currentPage));
    		maintainUldForm.setDisplayPage(Integer.toString(currentPage));
    		maintainUldForm.setLastPageNum(Integer.toString(totalRecords));
    		if(maintainULDSession.getUldNosForNavigation()!=null &&
    				maintainULDSession.getUldNosForNavigation().size() > 0) {
    			int displayPage = Integer.parseInt(
						maintainUldForm.getDisplayPage());
    			String displayUldNumber = 
    			maintainULDSession.getUldNosForNavigation().get(displayPage-1);
    	    	ULDVO newUldVO = null;
    	    	if(maintainULDSession.getULDMultipleVOs().get(displayUldNumber) == null) {
    	    		try {
    	    			newUldVO = new ULDDefaultsDelegate().findULDDetails(
    	    					 logonAttributes.getCompanyCode(),
    	    					 displayUldNumber) ;
    	    		}
    	    		catch (BusinessDelegateException businessDelegateException) {
    	    			errors = handleDelegateException(businessDelegateException);
    	    		}
    	    		
    	    	}
    	    	else {
    	    		newUldVO = maintainULDSession.getULDMultipleVOs().
    	    									get(displayUldNumber);
    	    	}
    	    	if(errors != null &&
    					errors.size() > 0 ) {
    					invocationContext.addAllError(errors);
    					invocationContext.target = DELETEULD_FAILURE;
    					return;
    			}
    	    	newUldVO.setUldNumber(displayUldNumber);
    	    	loadMaintainULDForm(newUldVO,maintainUldForm);
    	    	HashMap<String,ULDVO> uldMultipleVOs = 
    	    					maintainULDSession.getULDMultipleVOs();
    	    	newUldVO.setLastUpdateUser(logonAttributes.getUserId());
    	    	newUldVO.setLastUpdateTime(
    	    				new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
    	    	uldMultipleVOs.put(displayUldNumber,newUldVO);
    	    	maintainUldForm.setScreenStatusFlag(
    					ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    			
    		}
    		else {
    			//clearForm(maintainUldForm);
    			if(("listcreate").equals(maintainUldForm.getScreenloadstatus())) {
    				listULDSession.setListStatus("noListForm");
    				invocationContext.target = DELETE_LISTCREATE;
    			}
    			else if(("listdetail").equals(maintainUldForm.getScreenloadstatus())) {
    				listULDSession.setListStatus("noListForm");
    				invocationContext.target = DELETE_LISTDETAIL;
    			}
    			else if(("ListRepairReport").equals(maintainUldForm.getScreenloadstatus())) {
    				listRepairReportSession.setScreenId("LISTREPAIR");
    				invocationContext.target = DELETE_LISTREPAIR;
    			}
    			else if(("ListDamageReport").equals(maintainUldForm.getScreenloadstatus())) {
    				listDamageReportSession.setScreenId("LISTDAMAGE");
    				invocationContext.target = DELETE_LISTDAMAGE;
    			}
    			else if(("LoanBorrow").equals(maintainUldForm.getScreenloadstatus())) {
    				
    				invocationContext.target = DELETE_LOANBORROW;
    			}
    			return;
    			
    		}
    		
    	}
    	else if(maintainULDSession.getULDVO() != null) {
	    	ULDVO uldVO = maintainULDSession.getULDVO();
	    	log.log(Log.FINE, "ULDVO befor delete-------->>>", uldVO);
			if(!(ULDVO.OPERATION_FLAG_INSERT.equals(
	    								uldVO.getOperationalFlag()))) {
		    	
	    			uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
	    			uldVO.setUldNumber(maintainUldForm.getUldNumber().toUpperCase());
	    			uldVO.setCompanyCode(logonAttributes.getCompanyCode());
		    		errors = new ArrayList<ErrorVO>();
		    		
		    		ErrorVO err=null;
					err=validateULD(uldVO,compCode,airportCode,isAirlineUser);
					if(err!=null){
						invocationContext.addError(err);
		    			invocationContext.target = DELETEULD_FAILURE;		
		    			return;
					}
					
		    		try {
	    				new ULDDefaultsDelegate().saveULD(uldVO);
	    			}
	    			catch(BusinessDelegateException businessDelegateException) {
	    				errors = handleDelegateException(businessDelegateException);
	       			}
		    		log.log(Log.FINE, "Inside delete uldVO------>>>", uldVO);
					if(errors != null &&
		    			errors.size() > 0 ) {
		    			invocationContext.addAllError(errors);
		    			invocationContext.target = DELETEULD_FAILURE;
		    		    return;
			    }	
		    }
    	  	clearForm(maintainUldForm);
    	   	maintainULDSession.setULDVO(null);
    	   	maintainULDSession.setUldNumbersSaved(null);
    	   	maintainUldForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	}
		
		ErrorVO error = new ErrorVO(
					"uld.defaults.maintainuld.deletedsuccessfully");
		error.setErrorDisplayType(ErrorDisplayType.STATUS);
     	errors = new ArrayList<ErrorVO>();
     	errors.add(error);
     	invocationContext.addAllError(errors);
		maintainUldForm.setStatusFlag("delete");
		invocationContext.target = DELETEULD_SUCCESS;	

    }

    private void loadMaintainULDForm(ULDVO uldVO,
    					MaintainULDForm maintainULDForm) {
    	maintainULDForm.setUldNumber(uldVO.getUldNumber());
		/*if(uldVO.getUldType() != null) {
			maintainULDForm.setUldType(uldVO.getUldType());
		}
		if(uldVO.getOwnerAirlineCode() != null) {
			maintainULDForm.setOwnerAirlineCode(
											uldVO.getOwnerAirlineCode());
		}*/
		if(uldVO.getUldContour() != null) {
			maintainULDForm.setUldContour(uldVO.getUldContour());
		}
		maintainULDForm.setDisplayTareWeight(Double.toString(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setTareWtMeasure(uldVO.getTareWeight());
		/*if(uldTypeVO.getTareWtUnit() != null) {
			maintainULDForm.setDisplayTareWeightUnit(
										uldTypeVO.getTareWtUnit());
		}*/
		maintainULDForm.setDisplayStructuralWeight(Double.toString(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setStructWeightMeasure(uldVO.getStructuralWeight());
		if(uldVO.getStructuralWeight() != null) {
			maintainULDForm.setDisplayStructuralWeightUnit(uldVO.getStructuralWeight().getDisplayUnit());
		}else {
			maintainULDForm.setDisplayStructuralWeightUnit("");
		}
		
		maintainULDForm.setBaseLengthMeasure(uldVO.getBaseLength());
		maintainULDForm.setDisplayBaseLength(Double.toString(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseWidthMeasure(uldVO.getBaseWidth());
		maintainULDForm.setDisplayBaseWidth(Double.toString(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseHeightMeasure(uldVO.getBaseHeight());
		maintainULDForm.setDisplayBaseHeight(Double.toString(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getRoundedDisplayValue():0.0));

		if(uldVO.getOperationalAirlineCode() != null) {
			maintainULDForm.setOperationalAirlineCode(
									uldVO.getOperationalAirlineCode());
		}
		if(uldVO.getCurrentStation() != null) {
			maintainULDForm.setCurrentStation(
									uldVO.getCurrentStation());
		}
		if(uldVO.getOverallStatus() != null) {
			maintainULDForm.setOverallStatus(
									uldVO.getOverallStatus());
		}
		if(uldVO.getCleanlinessStatus() != null) {
			maintainULDForm.setCleanlinessStatus(
									uldVO.getCleanlinessStatus());
		}
		if(uldVO.getOwnerStation() != null) {
			maintainULDForm.setOwnerStation(uldVO.getOwnerStation());
		}
		if(uldVO.getLocation() != null) {
			maintainULDForm.setLocation(uldVO.getLocation());
		}
		if(uldVO.getUldNature() != null) {
			maintainULDForm.setUldNature(uldVO.getUldNature());
		}
		if(uldVO.getFacilityType() != null) {
			maintainULDForm.setFacilityType(uldVO.getFacilityType());
		}
		if(uldVO.getDamageStatus() != null) {
			maintainULDForm.setDamageStatus(uldVO.getDamageStatus());
		}
		if(uldVO.getVendor() != null) {
			maintainULDForm.setVendor(uldVO.getVendor());
		}
		maintainULDForm.setTransitStatus(uldVO.getTransitStatus());
		if(uldVO.getManufacturer() != null) {
			maintainULDForm.setManufacturer(uldVO.getManufacturer());
		}
		if(uldVO.getUldSerialNumber() != null) {
			maintainULDForm.setUldSerialNumber(
											uldVO.getUldSerialNumber());
		}
		if(uldVO.getPurchaseDate() != null) {
			maintainULDForm.setPurchaseDate(
			TimeConvertor.toStringFormat(
					uldVO.getPurchaseDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		if(uldVO.getPurchaseInvoiceNumber() != null) {
			maintainULDForm.setPurchaseInvoiceNumber(
									uldVO.getPurchaseInvoiceNumber());
		}
		
		maintainULDForm.setUldPrice(Double.toString(uldVO.getDisplayUldPrice()));
		
		if(uldVO.getUldPriceUnit() != null) {
			maintainULDForm.setUldPriceUnit(uldVO.getUldPriceUnit());
		}
		
		maintainULDForm.setIataReplacementCost(
						Double.toString(uldVO.getDisplayIataReplacementCost()));
		
		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			maintainULDForm.setIataReplacementCostUnit(
									uldVO.getDisplayIataReplacementCostUnit());
		}
		
		maintainULDForm.setCurrentValue(
							Double.toString(uldVO.getDisplayCurrentValue()));
		
		
		if(uldVO.getCurrentValueUnit() != null) {
			maintainULDForm.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		}
		
		
	}
    private void clearForm(MaintainULDForm maintainULDForm) {
		maintainULDForm.setUldNumber("");
		maintainULDForm.setUldType("");
		maintainULDForm.setOperationalAirlineCode("");
		maintainULDForm.setUldContour("");
		maintainULDForm.setDisplayTareWeight("");
		//maintainULDForm.setDisplayTareWeightUnit("");
		maintainULDForm.setDisplayStructuralWeight("");
		maintainULDForm.setDisplayStructuralWeightUnit("");
		maintainULDForm.setDisplayBaseLength("");
		maintainULDForm.setDisplayBaseWidth("");
		maintainULDForm.setDisplayBaseHeight("");
		maintainULDForm.setDisplayDimensionUnit("");
		maintainULDForm.setOwnerAirlineCode("");
		maintainULDForm.setCurrentStation("");
		maintainULDForm.setOwnerStation("");
		maintainULDForm.setLocation("");
		maintainULDForm.setUldNature("");		
		maintainULDForm.setFacilityType("");
		maintainULDForm.setVendor("");
		maintainULDForm.setTransitStatus("");
		maintainULDForm.setManufacturer("");
		maintainULDForm.setUldSerialNumber("");
		maintainULDForm.setPurchaseDate("");
		maintainULDForm.setPurchaseInvoiceNumber("");
		maintainULDForm.setUldPrice("");
		maintainULDForm.setUldPriceUnit("");
		maintainULDForm.setIataReplacementCost("");
		maintainULDForm.setIataReplacementCostUnit("");
		maintainULDForm.setCurrentValue("");
		maintainULDForm.setCurrentValueUnit("");
		maintainULDForm.setTotalNoofUlds("");
		maintainULDForm.setRemarks("");
		maintainULDForm.setOverallStatus("O");
    	maintainULDForm.setDamageStatus("N");
    	maintainULDForm.setCleanlinessStatus("C");
		maintainULDForm.setCreateMultiple(false);
		
	}
    private ErrorVO validateULD(ULDVO uldVo,String compCode,String airportCode,boolean isAirlineUser) {
//    	find whether airport is a ULD handling GHA
    	
    	AreaDelegate areaDelegate =new AreaDelegate();	
    	AirportVO airportVO=null;
    	Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
    	ErrorVO err=null;
    	try {
    		airportVO =	areaDelegate.findAirportDetails(compCode,airportCode);
    	} catch (BusinessDelegateException e) {
    	e.getMessage();
    	excep = handleDelegateException(e);
    	}
    	log.log(Log.FINE, "airportVO---------------->", airportVO);
		boolean isGHA=false;
    	if(isAirlineUser){
    		isGHA=false;
    	}else
    	{
    		isGHA=true;
    	}
    	
    	boolean isAirlineGHA=false;
    	if(airportVO!=null)
    	{
    		if(airportVO.getUsedAirportVO() != null && airportVO.getUsedAirportVO().isUldGHAFlag()){
    			isAirlineGHA=true;
    		}else{
    			isAirlineGHA=false;
    		}
    	}		
    	log.log(Log.FINE, "isGHA----------------->", isGHA);
		log.log(Log.FINE, "isAirlineGHA-------------------->", isAirlineGHA);
		if(isGHA || isAirlineGHA){
    		Collection<ULDValidationVO> validationvo = new ArrayList<ULDValidationVO>();
    		ULDValidationVO vo=new ULDValidationVO();
    		vo.setUldNumber(uldVo.getUldNumber());
    		vo.setCompanyCode(compCode);
    		validationvo.add(vo);
//    	to check whether ULD is in warehouse
    		log.log(Log.FINE, "\n\n\n\n ****INSIDE WH VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafterwh = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().validateULDForWarehouseOccupancy(validationvo);    			
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafterwh = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafterwh != null && errorsafterwh.size() > 0) {
    			   			
    			for (ErrorVO error : errorsafterwh) {
    				if (ULD_IS_OCCUPIED_AT_WAREHOUSE.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_IS_OCCUPIED_AT_WAREHOUSE"); 
    					
						
	    				return new ErrorVO("uld.defaults.uldisoccupiedatwarehouse", new Object[] { uldN });
    				}
    			
    		}}
    		
    	
//    	to check whether ULD is operational anywhere
    	
    		log.log(Log.FINE, "\n\n\n\n ****INSIDE opr VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafteropr = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().checkForULDInOperation(validationvo);		
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafteropr = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafteropr != null && errorsafteropr.size() > 0) {
    			log.log(Log.FINE, "\n\n\n\n ****ERROR PRESENT******");    			
    			for (ErrorVO error : errorsafteropr) {
    				if (ULD_EXISTS_AT_AIRPORT.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_AT_AIRPORT"); 
						
	    				return new ErrorVO("operations.flthandling.uldexistsatairport", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_LOADPLAN.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_LOADPLAN");
						
	    				return new ErrorVO("operations.flthandling.uldexistsinloadplan", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_MANIFEST.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_MANIFEST");
						
	    				return new ErrorVO("operations.flthandling.uldexistsinmanifest", new Object[] { uldN });
    				}
    			
    		}}
    	}
    	return err;
    }

}
