package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * SaveCommand
 * 
 * @author A-4452
 * 
 */
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class SaveCommand extends BaseCommand {
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS "); 
	private static final String SAVE_SUCCESS = "save_success";	
	private static final String SAVE_FAILURE = "save_failure";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String BLANK = "";

	
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering("Save Despatch Routing Carrier ","execute");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		MRARoutingCarrierForm  mraRoutingCarrierForm = (MRARoutingCarrierForm)invocationContext.screenModel;
		MRARoutingCarrierSession routingCarriersession = getScreenSession(MODULE_NAME,SCREEN_ID); 
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();		
    	ArrayList<RoutingCarrierVO> routingCarrierVOs= (ArrayList<RoutingCarrierVO>)routingCarriersession.getRoutingCarrierVOs();
    	Collection<RoutingCarrierVO> routingCarrierVOss=new ArrayList<RoutingCarrierVO>();
    	
		//Added by A-4809 for Bug ICMN-3307
    	updateMRARoutingCarrierSession(routingCarriersession,mraRoutingCarrierForm);
    	//Added by A-4809 for Bug ICMN-3315 ---Starts
    	RoutingCarrierFilterVO routingCarrierFilterVO=new RoutingCarrierFilterVO();	
    	routingCarrierFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	Collection<ErrorVO> errorVOs = null;
    	errorVOs = validateDate(mraRoutingCarrierForm);
    	if(errorVOs !=null && errorVOs.size()>0){
    		invocationContext.addAllError(errorVOs);
    		invocationContext.target = SAVE_FAILURE;
    		return;  
    	}
    	//Added by A-4809 for Bug ICMN-3315 ---Ends  
		String[] origincode=null;
		String[] destcode=null;
		String[] ownSectorFrm=null;
		String[] ownSectorTo=null;
		String[] oalSectorFrm=null;
		String[] oalSectorTo=null;
		String[] carrierCode=null;
		String[] validFrom=null;
		String[] validTo=null;
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] hiddenOpFlag = mraRoutingCarrierForm.getHiddenOpFlag();
		
			origincode = mraRoutingCarrierForm.getOrigincode();	
			destcode = mraRoutingCarrierForm.getDestcode();
			ownSectorFrm = mraRoutingCarrierForm.getOwnSectorFrm();
			ownSectorTo = mraRoutingCarrierForm.getOwnSectorTo();
			oalSectorFrm = mraRoutingCarrierForm.getOalSectorFrm();
			oalSectorTo = mraRoutingCarrierForm.getOalSectorTo();
			carrierCode = mraRoutingCarrierForm.getCarriercode();
			validFrom = mraRoutingCarrierForm.getValidFrom();
			validTo = mraRoutingCarrierForm.getValidTo();
			
			//Added by A-4809 for BUG ICMN-3367 to avoid duplicate records .....Starts
			 try{
				 routingCarrierVOss=mailTrackingMRADelegate.findRoutingCarrierDetails(routingCarrierFilterVO);
			 }catch(BusinessDelegateException businessDelegateException){
		    		log.log(Log.FINE,"inside try...caught businessDelegateException");
		        //	businessDelegateException.printStackTrace();
		        	handleDelegateException(businessDelegateException);
			}
			 for(RoutingCarrierVO routingCarrierVo :routingCarrierVOs){
				 if(routingCarrierVo.getOperationFlag().equals(RoutingCarrierVO.OPERATION_FLAG_INSERT)){
					 RoutingCarrierVO rtgCarVo = new RoutingCarrierVO();
					 try {
						BeanUtils.copyProperties(rtgCarVo, routingCarrierVo);
					} catch (IllegalAccessException e) {	
						log.log(Log.FINE,  "IllegalAccessException");
					} catch (InvocationTargetException e) {		
						log.log(Log.FINE,  "InvocationTargetException");
					}
					 routingCarrierVOss.add(routingCarrierVo);
				 }
			 }
			//Added by A-4809 for BUG ICMN-3367  .....Ends
        if(hiddenOpFlag!=null && hiddenOpFlag.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlag){
        		if(!"NOOP".equals(opflag)){
        			RoutingCarrierVO routingCarrierVO = null;
			        if("U".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);
			        	routingCarrierVO.setOperationFlag("U");
			        }else if("D".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);
			        	routingCarrierVO.setOperationFlag("D");
			        }else if("I".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);
			        	routingCarrierVO.setOperationFlag("I"); 			        	
			        }
			        
			        Collection<ErrorVO> errs=null;
			       
			        //Validating origin city codes......................			        
			        if(origincode!=null && origincode.length>0){
			        	Collection<String> cityCodes = new ArrayList<String>();
			        	 for (int i = 0; i < origincode.length; i++){    

			        		 if(!cityCodes.contains(origincode[i])){
			        			 cityCodes.add(origincode[i]);
			        		 } 
			        	 }
			        		
				        errs= validateCityCodes(logonAttributes.getCompanyCode(),
				        		cityCodes);
						if (errs != null && errs.size() > 0) {
							log.log(Log.INFO,
									"Erros in OALSectorTO.................",
									errs.size());
							routingCarrierVO.setOriginCity(origincode[count]);
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.mra.defaults.despatchrouting.msg.info.invalidorgcity");
							log
									.log(
											Log.INFO,
											"Erros in carrier code code......................",
											errs.size());
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = SAVE_FAILURE;    			
			    			return;							
						}/*else{
							 routingCarrierVO.setOriginCity(origincode[count]);
						}*/
			        }
			       
					
					 //Validating destination city codes......................		
			        if(destcode!=null && destcode.length>0){
			        	Collection<String> cityCodes = new ArrayList<String>();
			        	 for (int i = 0; i < destcode.length; i++){
			        		 /*Added by A-4809 for Bug ICMN-3315
					            * To check if origin and destination are same
					            * */ 
					        if(origincode[i].equals(destcode[i])){
					        	ErrorVO error = new ErrorVO(
					        			"mailtracking.mra.defaults.dispatchrouting.err.sameorgdst");
					        	error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
				    			invocationContext.addAllError(errors);      
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;		
					        }
					        
					        //Added by A-4809 for Bug ICMN-3315 ---Ends
					        else{
			        		 if(!cityCodes.contains(destcode[i]))
				        		 {
				        		 cityCodes.add(destcode[i]);
					        }
			        	 }
			        	 }
			        		 
						      errs = validateCityCodes(logonAttributes.getCompanyCode(),
						    		  cityCodes);
							if (errs != null && errs.size() > 0) {
								log.log(Log.INFO,
										"Erros in OALSectorTO...............",
										errs.size());
								routingCarrierVO.setDestCity(destcode[count]);
								ErrorVO errorVO = new ErrorVO(
										"mailtracking.mra.defaults.despatchrouting.msg.info.invaliddstcity");
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
				    			invocationContext.addAllError(errors);
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;				 				
							}/*else{
								routingCarrierVO.setDestCity(destcode[count]);
							}*/
			        }
			          
			        //Validating OWNSectorFrom.....................	
		        	if(ownSectorFrm!=null && ownSectorFrm.length>0){
		        		Collection<String> ownSectorfrm = new ArrayList<String>();
		        		 for (int i = 0; i < ownSectorFrm.length; i++){
		        			 if(!ownSectorfrm.contains(ownSectorFrm[i])){
		        				 ownSectorfrm.add(ownSectorFrm[i]);
		        			 }		        			
		        		 }
		        			 
		        		 
		        		 errs = validateAirportCodes(logonAttributes.getCompanyCode(), ownSectorfrm);
				 			if(errs!=null ){				 				

				 				log.log(Log.INFO,
										"Erros in OALSectorTO...............",
										errs.size());
								routingCarrierVO.setOwnSectorFrm(ownSectorFrm[count]);
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.defaults.despatchrouting.msg.info.invalidownSectorfrm");
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
				    			invocationContext.addAllError(errors);
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;				 				
				 			}/*else{
				 				routingCarrierVO.setOwnSectorFrm(ownSectorFrm[count]);
				 			}*/
		        	}
		        	
		        	 //Validating OWNSectorTo.....................	
		        	if(ownSectorTo!=null && ownSectorTo.length>0){
		        		Collection<String> ownSectorto = new ArrayList<String>();
		        		 for (int i = 0; i < ownSectorTo.length; i++){
		        			 if(!ownSectorto.contains(ownSectorTo[i])){
		        				 ownSectorto.add(ownSectorTo[i]);
		        			 }
		        		 }  
		        			       
		        		 
		        		 errs = validateAirportCodes(logonAttributes.getCompanyCode(), ownSectorto);
				 			if(errs!=null ){
				 				
								log.log(Log.INFO,
										"Erros in OALSectorTO...............",
										errs.size());
								routingCarrierVO.setOwnSectorTo(ownSectorTo[count]);
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.defaults.despatchrouting.msg.info.invalidownSectorto");
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
				    			invocationContext.addAllError(errors);
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;									
				 			}/*else{
				 				routingCarrierVO.setOwnSectorTo(ownSectorTo[count]);
				 			}*/
		        	}
		        	
		        	 //Validating OALSectorFrom......................	
		        	if(oalSectorFrm!=null && oalSectorFrm.length>0){
		        		Collection<String> oalSectorfrm = new ArrayList<String>();
		        		 for (int i = 0; i < oalSectorFrm.length; i++){
		        			 if(!oalSectorfrm.contains(oalSectorFrm[i])){
		        				 oalSectorfrm.add(oalSectorFrm[i]);
		        			 }
		        		 }
		        			 
		        		 
		        		 errs = validateAirportCodes(logonAttributes.getCompanyCode(), oalSectorfrm);
				 			if(errs!=null ){				 				

				 				log.log(Log.INFO,
										"Erros in OALSectorTO...............",
										errs.size());
								routingCarrierVO.setOalSectorFrm(oalSectorFrm[count]);
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.defaults.despatchrouting.msg.info.invalidoalSectorfrm");
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
				    			invocationContext.addAllError(errors);
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;	
				 			}/*else{
				 				routingCarrierVO.setOalSectorFrm(oalSectorFrm[count]);
				 			}*/
		        	}
		        	
		        	 //Validating  OALSectorTO......................	
		        	if(oalSectorTo!=null && oalSectorTo.length>0){
		        		Collection<String> oalSectorto = new ArrayList<String>();
		        		 for (int i = 0; i < oalSectorTo.length; i++){
		        			 if(!oalSectorto.contains(oalSectorTo[i])){
		        				 oalSectorto.add(oalSectorTo[i]);
		        			 }
		        		 }
		        		 errs = validateAirportCodes(logonAttributes.getCompanyCode(), oalSectorto);
				 			if(errs!=null ){				 				
 								log.log(Log.INFO,
										"Erros in OALSectorTO...............",
										errs.size());
								routingCarrierVO.setOalSectorTo(oalSectorTo[count]);
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.defaults.despatchrouting.msg.info.invalidoalSectorto");
								log
										.log(
												Log.INFO,
												"Erros in carrier code code......................",
												errs.size());
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
				    			invocationContext.addAllError(errors);
				    			invocationContext.target = SAVE_FAILURE;    			
				    			return;									
								   
				 			}/*else{
				 				routingCarrierVO.setOalSectorTo(oalSectorTo[count]);
				 			}*/
		        	}
		        	
		        	 //Validating carrier codes......................	
		        	if(carrierCode!=null && carrierCode.length>0){
		        		Collection<String> carrierCodes = new ArrayList<String>();
		        		 for (int i = 0; i < carrierCode.length; i++){
		       //Added by A-4809 for checking if the carrier code entered is other airline......Starts
		        if(carrierCode[i].equals(logonAttributes.getCompanyCode())){
		        	ErrorVO error = new ErrorVO(
		        			"mailtracking.mra.defaults.dispatchrouting.err.owncarriercode");
		        	error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
	    			invocationContext.addAllError(errors);      
	    			invocationContext.target = SAVE_FAILURE;    			
	    			return;		
		        }    
		        //Added by A-4809 for checking if the carrier code entered is other airline......Ends
		        else{	 
		        	if(!carrierCodes.contains(carrierCode[i])){
		        				 carrierCodes.add(carrierCode[i]);
		        			 }
		        		 }
		        		 }		 
			        	 errs = validateAirlineCode(logonAttributes.getCompanyCode(),carrierCodes);
			 			if(errs!=null ){
			 				routingCarrierVO.setCarrier(carrierCode[count]);
			 				ErrorVO errorVO = new ErrorVO(
							"mailtracking.mra.defaults.despatchrouting.msg.info.invalidcarrierCodes");
							log
									.log(
											Log.INFO,
											"Erros in carrier code code......................",
											errs.size());
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = SAVE_FAILURE;    			
			    			return;		
			 			}/*else{
			 				routingCarrierVO.setCarrier(carrierCode[count]);
			 			}*/
			        	
		        	}
		        	
		        	
		        	
		        	
		        	if(validFrom!=null && !BLANK.equals(validFrom[count]) && validFrom.length>0){
		        		routingCarrierVO.setValidFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validFrom[count]));
		        	}else{
		        		routingCarrierVO.setValidFrom(null);
		        	}
		        	if(validTo!=null && !BLANK.equals(validTo[count]) && validTo.length>0){
		        		routingCarrierVO.setValidTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validTo[count]));
		        	}else{
		        		routingCarrierVO.setValidTo(null);
		        	}
		        	
		        	routingCarrierVO.setCompanyCode(routingCarrierVO.getCompanyCode());
		        	routingCarrierVO.setSequenceNumber(routingCarrierVO.getSequenceNumber());
		        	
		        	}
			        count++;
			       
        		}
       
        	
        	

        	Boolean isdateOverlap=true;
        	
        	try {
        		isdateOverlap=isRoutingCarrierParameterSame(routingCarrierVOss);
			} catch (SystemException e) {
				
			//	e.printStackTrace();
			}
			if(isdateOverlap){
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.mra.defaults.duplicateentries");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = SAVE_FAILURE;    			
    			return;		
			}
        	}	        
        	  
		try {
			mailTrackingMRADelegate.saveRoutingCarrierDetails(routingCarrierVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}
			if(errors == null || errors.size() == 0){
				errors = new ArrayList<ErrorVO>();
				errors.add(new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.info.datasavedsuccessfully"));
				
			}
			invocationContext.addAllError(errors);
			mraRoutingCarrierForm.setOriginCity("");
			mraRoutingCarrierForm.setDestCity("");
			mraRoutingCarrierForm.setCarrier("");
			mraRoutingCarrierForm.setValidFromDate("");
			mraRoutingCarrierForm.setValidToDate("");   
			routingCarriersession.setRoutingCarrierVOs(null);		
			invocationContext.target = SAVE_SUCCESS;
		
	}
	
	
	
	/**
	 * @author A-4809
	 * @param mraRoutingCarrierForm
	 */
	private Collection<ErrorVO> validateDate(MRARoutingCarrierForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String[] validFrom=form.getValidFrom();
		String[] validTo=form.getValidTo();
		for(int i=0;i<form.getValidFrom().length;i++){
		if(!validFrom[i].equals(validTo[i])){

    		if (!DateUtilities.isLessThan(validFrom[i] , validTo[i],
    		"dd-MMM-yyyy")) {
    			error = new ErrorVO(
    			"mailtracking.mra.defaults.dispatchrouting.err.fromDateGreaterThanToDate");
    			error.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(error);    
    		}
    	} 
		}
	return errors;	
	}



	/**
	 * @author A-4809
	 * method to save routingcarrierVos into Session
	 * @param routingCarriersession
	 * @param mraRoutingCarrierForm
	 */
	private void updateMRARoutingCarrierSession(MRARoutingCarrierSession routingCarriersession,
			MRARoutingCarrierForm mraRoutingCarrierForm) {
		log.log(Log.FINE, "***************updating session");
		String[] hiddenOpFlag = mraRoutingCarrierForm.getHiddenOpFlag();
		ArrayList<RoutingCarrierVO> routingCarrierVOs= (ArrayList<RoutingCarrierVO>)routingCarriersession.getRoutingCarrierVOs();
		String[] origincode=mraRoutingCarrierForm.getOrigincode();
		String[] destcode=mraRoutingCarrierForm.getDestcode();
		String[] ownSectorFrm=mraRoutingCarrierForm.getOwnSectorFrm();
		String[] ownSectorTo=mraRoutingCarrierForm.getOwnSectorTo();
		String[] oalSectorFrm=mraRoutingCarrierForm.getOalSectorFrm();
		String[] oalSectorTo=mraRoutingCarrierForm.getOalSectorTo();
		String[] carrierCode=mraRoutingCarrierForm.getCarriercode();
		String[] validFrom=mraRoutingCarrierForm.getValidFrom();
		String[] validTo=mraRoutingCarrierForm.getValidTo();
		
        if(hiddenOpFlag!=null && hiddenOpFlag.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlag){     
        		if(!"NOOP".equals(opflag)){
        			RoutingCarrierVO routingCarrierVO = null;
			        if("U".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);
			        	routingCarrierVO.setOperationFlag("U");
			        }else if("D".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);  
			        	routingCarrierVO.setOperationFlag("D");
			        }else if("I".equals(opflag)){
			        	routingCarrierVO = routingCarrierVOs.get(count);
			        	routingCarrierVO.setOperationFlag("I"); 			        	
			        }     
			        routingCarrierVO.setOriginCity(origincode[count]);
			        routingCarrierVO.setDestCity(destcode[count]);
			        routingCarrierVO.setOwnSectorFrm(ownSectorFrm[count]);
			        routingCarrierVO.setOwnSectorTo(ownSectorTo[count]);
			        routingCarrierVO.setOalSectorFrm(oalSectorFrm[count]);
			        routingCarrierVO.setOalSectorTo(oalSectorTo[count]);
			        routingCarrierVO.setCarrier(carrierCode[count]);
			        if(validFrom[count]!=null && !BLANK.equals(validFrom[count]) && validFrom[count].length()>0){
			        routingCarrierVO.setValidFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validFrom[count]));
			        }else{
		        		routingCarrierVO.setValidFrom(null);
		        	}
			        
			        if(validTo[count]!=null && !BLANK.equals(validTo[count]) && validTo[count].length()>0){  
			        routingCarrierVO.setValidTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validTo[count]));
			        }else{
		        		routingCarrierVO.setValidTo(null);
		        	}
        		}
        		count++;
        	}
        	}	routingCarriersession.setRoutingCarrierVOs(routingCarrierVOs);
	}



	private Collection<ErrorVO> validateCityCodes(String companyCode,
			Collection<String> cityCodes) {
		AreaDelegate areaDelegate = new AreaDelegate();
		//Collection<ErrorVO> errors = null;
		log.log(Log.FINE, "***************Chkng city code");

		try {
			areaDelegate.validateCityCodes(companyCode, cityCodes);
			log.log(Log.FINE, "***************\nOver Chkng cityCodes code");
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside SaveCommand validateCityCodes");
			return handleDelegateException(businessDelegateException);
		}
		return null;

	}   
	
	
	
	private Collection<ErrorVO> validateAirlineCode(String companyCode,
			Collection<String> carrierCodes) {
		AirlineValidationVO airlineValidationVo = null;		
		for (String carrierCode : carrierCodes) {
			try {

				AirlineDelegate delegate = new AirlineDelegate();
				airlineValidationVo = delegate.validateAlphaCode(
						companyCode, carrierCode.toUpperCase().trim());
			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);				
			}	
			
		}
		return null;
	}
	

	private Collection<ErrorVO> validateAirportCodes(String companyCode,
			Collection<String> ownSectorfrm) {
		Map<String, CityVO> validCodes = new HashMap<String, CityVO>();	
			try {

				AreaDelegate areaDelegate = new AreaDelegate();	
				//Modified by A-4809 for bug ICMN-3324
				validCodes = areaDelegate.validateCityCodes(companyCode, ownSectorfrm);
			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);				
			}
		
		return null;
	}
	
	
	
	/**
	 *
	 *
	 * Checks whether the Billing line contains same parameter in same date range
	 *
	 * @param billingLineVos
	 * @throws SystemException
	 * @author A-3434
	 *
	 */
	public Boolean isRoutingCarrierParameterSame(Collection<RoutingCarrierVO> routingCarrierVOs)
			throws SystemException {
		log.entering("SaveCommand", "isRoutingCarrierParameterSame");

		HashMap<String, RoutingCarrierVO> routingCarrierVoforChk = null;
		routingCarrierVoforChk = new HashMap<String, RoutingCarrierVO>();
		Boolean isParameterSame=false;
		String firstParameter="";
		for (RoutingCarrierVO routingCarrierVOforCompare : routingCarrierVOs) {
			log.log(Log.INFO, "billingLineVoforCompare.. ",
					routingCarrierVOforCompare);
			String parameter="";
//Added by A-4809 for BUG ICMN-3368			
if(!RoutingCarrierVO.OPERATION_FLAG_DELETE.equals(routingCarrierVOforCompare.getOperationFlag())){
		LocalDate fromDate = routingCarrierVOforCompare.getValidFrom();
		LocalDate toDate = routingCarrierVOforCompare.getValidTo();
		
		
			//String parameterValue = routingCarrierVOforCompare.getOriginCity();
			parameter = parameter.concat(routingCarrierVOforCompare.getOriginCity()
					.concat(routingCarrierVOforCompare.getDestCity())
					.concat(routingCarrierVOforCompare.getOwnSectorFrm())
					.concat(routingCarrierVOforCompare.getOwnSectorTo())
					.concat(routingCarrierVOforCompare.getOalSectorFrm())   
					.concat(routingCarrierVOforCompare.getOalSectorTo()));
			
			firstParameter = parameter;
		boolean isPresent = routingCarrierVoforChk.containsKey(firstParameter);
		if (isPresent) {
			log.log(Log.INFO, "INSIDE PRESENT>>>>>>>>>>>>>>>>>>>>. ");
			RoutingCarrierVO routingCarrierVoInMap = routingCarrierVoforChk
					.get(firstParameter);
			LocalDate startDate = routingCarrierVoInMap.getValidFrom();
			LocalDate endDate = routingCarrierVoInMap.getValidTo();
			Boolean isdateOverlap=true;
	
		log.log(Log.INFO, "firstParameter.. ", firstParameter);
		log.log(Log.INFO, "billingLineVoInMap.. ", routingCarrierVoInMap);
		if(fromDate != null && toDate != null ){
		isdateOverlap=(((startDate.isGreaterThan(toDate))&&
		(fromDate.isLesserThan(startDate)))||
		((toDate.isGreaterThan(startDate))&&
				(endDate.isLesserThan(fromDate))));
		}
		if (parameter.equals(firstParameter)&& !isdateOverlap ){
				log
					.log(Log.INFO,
							"INSIDE IF BEFORE THROWING EXCEPTION>>>>>>>>>>>>>>>>>>>>. ");
			isParameterSame=true;
			//ErrorVO error = new ErrorVO("mailtracking.mra.defaults.duplicateparameter");
			//errors.add(error);
		}

		}
		else {
			// Collection<BillingLineVO>
			firstParameter=parameter;
			log.log(Log.INFO, "PUTTING IN MAP>>>>>>>>>>>>>>>>>>>>. ");		
			routingCarrierVoforChk.put(firstParameter, routingCarrierVOforCompare);
		}

		log.exiting("SaveCommand", "isRoutingCarrierParameterSame");
		}
		}
		return isParameterSame;

	}
	

	
}
