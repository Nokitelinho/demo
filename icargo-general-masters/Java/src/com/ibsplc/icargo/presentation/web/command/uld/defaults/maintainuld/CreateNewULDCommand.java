/*
 * CreateNewULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for creating a new uld
 *
 * @author A-2001
 */
public class CreateNewULDCommand extends BaseCommand {

	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String CREATEULD_SUCCESS = "createuld_success";
	private static final String CREATEULD_FAILURE = "createuld_failure";
    private Log log = LogFactory.getLogger("CreateNewULDCommand");

    /**
     * @param invocationContext
     * @return
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		/*
		 * Obtain the logonAttributes
		 */
    	log.entering("CreateNewULDgfhgCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		boolean isFromPopup = false;
		MaintainULDSession maintainULDSessionImpl =
			(MaintainULDSession)getScreenSession(MODULE, SCREENID);
		MaintainULDForm maintainULDForm =
						(MaintainULDForm) invocationContext.screenModel;
		ULDTypeVO uldTypeVO = null;
		ULDVO uldVO = new ULDVO();
	    Collection<ErrorVO> errors = null;
	    
	    
	    String contour= null;
		try{
		 ULDDelegate uldDelegate=new ULDDelegate();
		 String uldno = maintainULDForm.getUldNumber();
		
		 if(uldno!=null){
			 String uldtyp = uldno.substring(0, 3);
			 log.log(Log.FINE, "!!!!!ULD TYPE SREE1", uldtyp);
		contour = uldDelegate.findULDContour(logonAttributes.getCompanyCode(),uldtyp);
		    log.log(Log.FINE, "!!!!!ULD Contour SREE2", contour);
		 }	
		}		
		catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,
					"!!!!!ULD Contour not found in SHRULDTYPMST for ULD TYPE ",
					maintainULDForm.getUldContour());
		}
		
	    
	    
	    
	    
	    if(maintainULDForm.getUldType() != null &&
	    		maintainULDForm.getUldType().trim().length() > 0) {
	    	log.log(Log.INFO,"inside nsxfsdfsdfew");
	    	isFromPopup = true;
	    	if(maintainULDSessionImpl.getUldNumbersSaved() != null &&
	    			maintainULDSessionImpl.getUldNumbersSaved().size() > 0) {
	    		maintainULDForm.setTotalNoofUlds(
		    	  Integer.toString(maintainULDSessionImpl.getUldNumbersSaved().size()));
		   	}
	    	try {
	    		if(maintainULDForm.getUldNumber() != null) {
	    			uldTypeVO = new ULDDefaultsDelegate().findULDTypeStructuralDetails(
							 logonAttributes.getCompanyCode(),
							 maintainULDForm.getUldNumber().toUpperCase()) ;
	    		}
	    		else {
	    			uldTypeVO = new ULDDefaultsDelegate().findULDTypeStructuralDetails(
							 logonAttributes.getCompanyCode(),
							 maintainULDForm.getUldType().toUpperCase()) ;
	    		}
				
			}
			catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
	    }
	    else {
		    log.log(Log.INFO,"inside new");
	    	uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
	    	uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
	    	//change by nisha starts for airline code from ULD validation
	    	//splitUldNumber(uldVO);
	    	try {
	    		String uldNumber = uldVO.getUldNumber();
	    		if(uldNumber!=null && uldNumber.trim().length()>0){
		    	uldVO.setUldType(uldNumber.substring(0,3).toUpperCase());
		    	String arlDtl=new ULDDefaultsDelegate().findOwnerCode(logonAttributes.getCompanyCode() ,
	    			uldNumber.substring(uldNumber.length()-2),uldNumber.substring(uldNumber.length()-3));
		    	log.log(Log.INFO, "airlineDetails****", arlDtl);
		    	if(arlDtl ==null || arlDtl.isEmpty()){						
		    		ErrorVO error = new ErrorVO("shared.airline.invalidairline", new
					  Object[]{uldNumber});  
		    		if(errors==null)
		    			{
		    			errors = new ArrayList<ErrorVO>();                
		    			}                
					 errors.add(error);         						
				}else{
				String arldtlArray[] = arlDtl.split("~");
		    	boolean isTwoAlpha=false;
		    	
					if ("2".equals(arldtlArray[0])) {
						isTwoAlpha=true;
					} else {
						isTwoAlpha=false;
					}
			    	if(isTwoAlpha){
			    		uldVO.setOwnerAirlineCode(uldNumber.substring(uldNumber.length()-2));
			    	}else{
			    		uldVO.setOwnerAirlineCode(uldNumber.substring(uldNumber.length()-3));
			    	}
			    	log.log(Log.INFO, "uldVO****", uldVO);
	    		}
//	    		change by nisha ends
		    	if(maintainULDForm.getUldNumber() != null) {
		    		uldTypeVO = new ULDDefaultsDelegate().findULDTypeStructuralDetails(
							 logonAttributes.getCompanyCode(),
							 maintainULDForm.getUldNumber().toUpperCase()) ;
		    	}
		    	else {
		    		uldTypeVO = new ULDDefaultsDelegate().findULDTypeStructuralDetails(
							 logonAttributes.getCompanyCode(),
							 uldVO.getUldType().toUpperCase()) ;
		    	}
		    	
				}
				
			}
			catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
	    }
	    //A-1950 starts for setting ULDGroupCode
	    uldVO.setUldGroupCode(uldTypeVO.getUldGroupCode());
	    maintainULDSessionImpl.setUldGroupCode(uldTypeVO.getUldGroupCode());
	    log.log(Log.INFO, "%%%%%%%%% GrooupCode", uldTypeVO.getUldGroupCode());
		log.log(Log.INFO, "%%%%%%%%% isFromPopup", isFromPopup);
		//A-1950 ends
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = CREATEULD_FAILURE;
				return;
		}
		
		uldVO.setUldContour(contour);
		if(!isFromPopup) {

			uldVO.setUldGroupCode(uldTypeVO.getUldGroupCode());
			maintainULDSessionImpl.setULDVO(uldVO);
			maintainULDForm.setOperationalAirlineCode(
					uldVO.getOwnerAirlineCode());
			maintainULDForm.setOperationalOwnerAirlineCode(
					uldVO.getOwnerAirlineCode());
			/*if("".equals(maintainULDForm.getOperationalAirlineCode())){
			maintainULDForm.setOperationalAirlineCode(
					uldVO.getOwnerAirlineCode());
			}else{
				if(!(uldVO.getOwnerAirlineCode().equals(maintainULDForm.getOperationalAirlineCode()))){
					log.log(Log.INFO,"uldairlinecode"+uldVO.getOwnerAirlineCode());
					ErrorVO error =new ErrorVO("uld.defaults.maintainuld.err.cannotcreateuld");
					error.setErrorData(new Object[]{uldVO.getOwnerAirlineCode()});
					invocationContext.addError(error);
					invocationContext.target = CREATEULD_FAILURE;
					return;
				}
			}*/
			/*if("".equals(maintainULDForm.getOperationalAirlineCode())){
			maintainULDForm.setOperationalAirlineCode(
					uldVO.getOwnerAirlineCode());
			}else{
				if(!(uldVO.getOwnerAirlineCode().equals(maintainULDForm.getOperationalAirlineCode()))){
					log.log(Log.INFO,"uldairlinecode"+uldVO.getOwnerAirlineCode());
					ErrorVO error =new ErrorVO("uld.defaults.maintainuld.err.cannotcreateuld");
					error.setErrorData(new Object[]{uldVO.getOwnerAirlineCode()});
					invocationContext.addError(error);
					invocationContext.target = CREATEULD_FAILURE;
					return;
				}
			}*/

		}
		else {
			maintainULDForm.setOperationalAirlineCode(
					maintainULDForm.getOwnerAirlineCode());
			maintainULDForm.setOperationalOwnerAirlineCode(
					maintainULDForm.getOwnerAirlineCode());
		}
		AreaDelegate areaDelegate = new AreaDelegate();
    	String defCur="";
		try {
			defCur = areaDelegate.defaultCurrencyForStation(
					getApplicationSession().getLogonVO().getCompanyCode(),
					getApplicationSession().getLogonVO().getStationCode());
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		log.log(Log.INFO, "%%%%%%%%defcur", defCur);
		maintainULDForm.setCurrentStation(
				logonAttributes.getAirportCode().toUpperCase());
		maintainULDForm.setOwnerStation(
				logonAttributes.getAirportCode().toUpperCase());
		maintainULDForm.setUldPriceUnit(defCur);
    	log.log(Log.INFO, "%%%%%%%%defcur ------->>", defCur);
		maintainULDForm.setCurrentValueUnit(defCur);
		maintainULDForm.setUldPriceUnit(defCur);
    	log.log(Log.INFO, "%%%%%%%%defcur ------->>", defCur);
		maintainULDForm.setCurrentValueUnit(defCur);

    	loadMaintainULDForm(uldTypeVO,maintainULDForm);
    	maintainULDForm.setStatusFlag("createNewUld");
    	maintainULDForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	log.exiting("CreateNewULDCommand", "execute");
    	invocationContext.target = CREATEULD_SUCCESS;
    }

    /**
     *
     * @param uldTypeVO
     * @param maintainULDForm
     */
  	private void loadMaintainULDForm(ULDTypeVO uldTypeVO,
  								MaintainULDForm maintainULDForm) {

		if(uldTypeVO.getCtrCode() != null) {
			maintainULDForm.setUldContour(uldTypeVO.getCtrCode());
		}
		maintainULDForm.setDisplayTareWeight(Double.toString(uldTypeVO.getTareWt()!=null?uldTypeVO.getTareWt().getRoundedDisplayValue():0.0));
		maintainULDForm.setTareWtMeasure(uldTypeVO.getTareWt());
		/*if(uldTypeVO.getTareWtUnit() != null) {
			maintainULDForm.setDisplayTareWeightUnit(
										uldTypeVO.getTareWtUnit());
		}*/
		maintainULDForm.setDisplayStructuralWeight(Double.toString(uldTypeVO.getStructuralWtLmt()!=null?uldTypeVO.getStructuralWtLmt().getRoundedDisplayValue():0.0));
		maintainULDForm.setStructWeightMeasure(uldTypeVO.getStructuralWtLmt());
		if(uldTypeVO.getStructuralWtLmt() != null) {
			maintainULDForm.setDisplayStructuralWeightUnit(uldTypeVO.getStructuralWtLmt().getDisplayUnit());
		}
		
		maintainULDForm.setBaseLengthMeasure(uldTypeVO.getBaseDimLength());
		maintainULDForm.setDisplayBaseLength(Double.toString(uldTypeVO.getBaseDimLength()!=null?uldTypeVO.getBaseDimLength().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseWidthMeasure(uldTypeVO.getBaseDimWidth());
		maintainULDForm.setDisplayBaseWidth(Double.toString(uldTypeVO.getBaseDimWidth()!=null?uldTypeVO.getBaseDimWidth().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseHeightMeasure(uldTypeVO.getBaseDimHeight());
		maintainULDForm.setDisplayBaseHeight(Double.toString(uldTypeVO.getBaseDimHeight()!=null?uldTypeVO.getBaseDimHeight().getRoundedDisplayValue():0.0));

		if(uldTypeVO.getBaseDimHeight() != null) {
			maintainULDForm.setDisplayDimensionUnit(uldTypeVO.getBaseDimHeight().getDisplayUnit());
		}
		
		log.log(Log.INFO, "%%%%%%%%defcur", uldTypeVO.getIATARepCostUnit());
		maintainULDForm.setIataReplacementCost(String.valueOf(uldTypeVO.getIATARepCost()));
		maintainULDForm.setIataReplacementCostUnit(uldTypeVO.getIATARepCostUnit());
	
	}

	private void splitUldNumber(ULDVO uldVO) {
		String uldNumber = uldVO.getUldNumber();
		int startIndex = 0;
		int endIndex = 0;
		char charAtfourthPosition = uldNumber.charAt(3);
		char charAtlastthirdPosition = uldNumber.charAt(uldNumber.length()-3);
		try {
			Integer.parseInt(String.valueOf(charAtfourthPosition));
			startIndex = 3;
		}
		catch(NumberFormatException e) {
			startIndex = 4;
		}

		try {
			Integer.parseInt(String.valueOf(charAtlastthirdPosition));
			endIndex = uldNumber.length()-2;
		}
		catch(NumberFormatException e) {
			endIndex = uldNumber.length()-3;
		}

		uldVO.setUldType(uldNumber.substring(0,startIndex).toUpperCase());
		uldVO.setOwnerAirlineCode(
				uldNumber.substring(endIndex,uldNumber.length()).toUpperCase());
	}
}
