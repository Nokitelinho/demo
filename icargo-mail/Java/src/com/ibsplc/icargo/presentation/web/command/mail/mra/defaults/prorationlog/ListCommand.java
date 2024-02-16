
	/*
	 * ListCommand.java Created on Sep 18, 2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services (P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;

	import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	* Command class for listing 
	*
	* Revision History
	*
	* Version      Date           Author          		    Description
	*
	*  0.1         Sep 03, 2008    A-3229		 		   Initial draft
	*/
	public class ListCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */
		
		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
		
		
		private static final String CLASS_NAME = "ListCommand";
		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
		
		private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";
		
		private static final String LIST_SUCCESS = "list_success";

		private static final String LIST_FAILURE = "list_failure";
		
		private static final String TRGPNT="mailtracking.mra.proration.triggerpoint";
		
		private static final String BASE_CURRENCY = "shared.station.basecurrency";
		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			
			ProrationLogSession prorationLogSession=(ProrationLogSession)getScreenSession(MODULE_NAME, SCREEN_ID);
			
			MRAProrationLogForm mraProrationLogForm=(MRAProrationLogForm)invocationContext.screenModel;
			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
			Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
			DSNFilterVO dsnFilterVO=null;
			log.log(Log.INFO, "prorationLogSession.getDSNFilterVO()-----",
					prorationLogSession.getDSNFilterVO());
			if(prorationLogSession.getDSNFilterVO()!=null 
					&& (mraProrationLogForm.getDsn()== null|| mraProrationLogForm.getDsn().trim().length()<=0)){
				log.log(Log.INFO, "INSIDE IF----", prorationLogSession.getDSNFilterVO());
				dsnFilterVO=prorationLogSession.getDSNFilterVO();
			}else{
				log.log(log.FINE,"INSIDE ELSE----");
				dsnFilterVO = new DSNFilterVO();	
			   DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
			
			
			log.log(Log.INFO, "POPUPSESSION------------------", popUpSession.getSelectedDespatchDetails());
					DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
					log.log(Log.INFO, "inside list command popupvo ", popUpVO);
					if(popUpVO!=null){
						mraProrationLogForm.setDsn(popUpVO.getBlgBasis());
						mraProrationLogForm.setBillingBasis(popUpVO.getBlgBasis());
						mraProrationLogForm.setCsgDocumentNumber(popUpVO.getCsgdocnum());
						mraProrationLogForm.setCsgSequenceNumber(String.valueOf(popUpVO.getCsgseqnum()));
						mraProrationLogForm.setPoaCode(popUpVO.getGpaCode());
						mraProrationLogForm.setCompanyCode(popUpVO.getCompanyCode());
						//mraProrationLogForm.setSerialNumber(String.valueOf(popUpVO.getSerialNumber()));
					}
			
					
					//To set values for filtervo
					dsnFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				
					if(!("".equals(mraProrationLogForm.getBillingBasis()))){
						dsnFilterVO.setBlgBasis(mraProrationLogForm.getBillingBasis());
					}
					if(!("".equals(mraProrationLogForm.getCsgDocumentNumber()))){
						dsnFilterVO.setCsgDocumentNumber(mraProrationLogForm.getCsgDocumentNumber());
					}

					if(!("".equals(mraProrationLogForm.getCsgSequenceNumber()))){
						dsnFilterVO.setCsgSequenceNumber(Integer.parseInt(mraProrationLogForm.getCsgSequenceNumber()));
					}

					if(!("".equals(mraProrationLogForm.getPoaCode()))){
						dsnFilterVO.setPoaCode(mraProrationLogForm.getPoaCode());
					}
					/*if(!("".equals(mraProrationLogForm.getSerialNumber()))){
						dsnFilterVO.setSerialNumber(Integer.parseInt(mraProrationLogForm.getSerialNumber()));
					}*/
					if(!("".equals(mraProrationLogForm.getDsn()))){
						dsnFilterVO.setDsn(mraProrationLogForm.getDsn());
						
					}
				}
				if(dsnFilterVO!=null){
					prorationLogSession.setDSNFilterVO(dsnFilterVO);
				}
		
					//MONEY IMPL

					ArrayList<String> stationParameterCodes = new ArrayList<String>();
					HashMap<String, String> stationParameters = new HashMap<String, String>();
					stationParameterCodes.add(BASE_CURRENCY);

					try {
						stationParameters = (HashMap<String, String>) (new AreaDelegate()
								.findStationParametersByCode(logonAttributes
										.getCompanyCode(),
										logonAttributes.getStationCode(),
										stationParameterCodes));
						dsnFilterVO.setBaseCurrency(stationParameters
								.get(BASE_CURRENCY));

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}

					String baseCurrency = stationParameters.get(BASE_CURRENCY);
					
					// ONE TIME DETAILS FOR TRIGGER POINT
					
			    	SharedDefaultsDelegate sharedDefaultsDelegate = 
						new SharedDefaultsDelegate();
			    	
			    	Collection<String> triggerPointValue = new ArrayList<String>();
			    	triggerPointValue.add(TRGPNT);
			      	
					Map<String, Collection<OneTimeVO>> oneTimeValues = null;
					try {
						oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
								logonAttributes.getCompanyCode(), 
								triggerPointValue);
					} catch (BusinessDelegateException e) {
						errors = handleDelegateException(e); 
					}
			    	
					
					
					if(oneTimeValues.get(TRGPNT)!=null){
					prorationLogSession.setTriggerPoints(new ArrayList<OneTimeVO>(oneTimeValues.get(TRGPNT)));
					log.log(Log.INFO, "one time value----------", oneTimeValues.get(TRGPNT));
					}
					
					log.log(Log.FINE,
							"dsnFilterVO before server call Server is----->",
							dsnFilterVO);
					//server call 
			        Collection<MailProrationLogVO> mailProrationLogVOs = null;
			        try{
			        	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			        	mailProrationLogVOs = mailTrackingMRADelegate.findProrationLogDetails(dsnFilterVO);
							log.log(Log.FINE,
									"mailProrationLogVOs from Server is----->",
									mailProrationLogVOs);
										
						}catch(BusinessDelegateException businessDelegateException){
					    		log.log(Log.FINE,"inside try...caught businessDelegateException");
					        	businessDelegateException.getMessage();
					        	handleDelegateException(businessDelegateException);
						}  		
						if(mailProrationLogVOs!=null){
						if(prorationLogSession.getTriggerPoints()!=null){	
						for(MailProrationLogVO mailProrationLogVO:mailProrationLogVOs){
							for(OneTimeVO oneTimeVO:prorationLogSession.getTriggerPoints()){
								if(oneTimeVO.getFieldValue().equalsIgnoreCase(mailProrationLogVO.getTriggerPoint())){
									mailProrationLogVO.setTriggerPoint(oneTimeVO.getFieldDescription());
								}
							}
							
						}
						}
						prorationLogSession.setMailProrationLogVOs(mailProrationLogVOs);
						
						
						
						/*CHANGE DONE BY INDU FOR JOINING THE VERSION FIELD STARTS**/
						LinkedHashMap<String, Collection<MailProrationLogVO>> mailProrationLogVO = null;
						mailProrationLogVO  = new LinkedHashMap<String, Collection<MailProrationLogVO>>();
						for(MailProrationLogVO logVO:mailProrationLogVOs){
						   String version=String.valueOf(logVO.getVersionNo());
							String keyForCompare = version;
							boolean isAccountName = mailProrationLogVO.containsKey(keyForCompare);
				   			log.log(Log.INFO, "isAccountName present-->",
									isAccountName);
							if(isAccountName){
				   				Collection<MailProrationLogVO> collnExists = mailProrationLogVO.get(keyForCompare);
				   				collnExists.add(logVO);
				   			}else{
				   				Collection<MailProrationLogVO> collnToAdd = new ArrayList<MailProrationLogVO>();
				   				collnToAdd.add(logVO);
				   				mailProrationLogVO.put(keyForCompare,collnToAdd);
				   			}
						}
						
						prorationLogSession.setMailProrationLogVOMap(mailProrationLogVO);
						/*CHANGE DONE BY INDU FOR JOINING THE VERSION FIELD ENDS**/
						
						
						
						}
						
						/*
						 * Validate for client errors. The method 
						 * will check for mandatory fields
						 */
						
						errors = validateForm(mraProrationLogForm);
						
						if(mailProrationLogVOs == null ||mailProrationLogVOs.size() == 0){
								log.log(Log.FINE,"!!!inside resultList== null");
								ErrorVO errorVO = new ErrorVO(
										"mailtracking.mra.defaults.prorationlog.msg.err.nologdetails");
								errorVO.setErrorDisplayType(ERROR);
								errors.add(errorVO);						
								//removeFormValues(mraProrationLogForm);
								prorationLogSession.removeMailProrationLogVOs();
							   //prorationLogSession.setDSNFilterVO(null);
								prorationLogSession.setTriggerPoints(null);
								mraProrationLogForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
									
						}
						
						if(errors != null && errors.size() > 0){
							log.log(Log.FINE,"!!!inside errors!= null");
						   //removeFormValues(mraProrationLogForm);
							prorationLogSession.removeMailProrationLogVOs();
							prorationLogSession.removeMailProrationLogVOMap();
							prorationLogSession.setTriggerPoints(null);
							//prorationLogSession.setDSNFilterVO(null);
							invocationContext.addAllError(errors);					
							invocationContext.target = LIST_FAILURE;
						}else{
							log.log(Log.FINE,"!!!inside resultList!= null");	
							
							mraProrationLogForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							invocationContext.target = LIST_SUCCESS;		       
										
				
				}
		
					log.exiting(CLASS_NAME, "execute");
		}
		/**
		 * method to validate form for client side errors
		 * @param mraProrationLogForm
		 * @return
		 */
		public Collection<ErrorVO> validateForm(MRAProrationLogForm mraProrationLogForm){
			
			Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
			ErrorVO error = null;
			
			String dsn=mraProrationLogForm.getDsn();
			if("".equals(dsn)){
				
			    error=new ErrorVO("mailtracking.mra.defaults.prorationlog.msg.err.dsnmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			
			
			return errors;
		}
		
		private void removeFormValues(MRAProrationLogForm mraProrationLogForm){
			mraProrationLogForm.setDsn("");
			
		}
		
		
		
	}



