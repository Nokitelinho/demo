/*
 * UpdateDsnDetailsCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class UpdateDsnDetailsCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_ADD = "invoke_add";
   private static final String TARGET_DELETE = "invoke_delete";
   private static final String TARGET_SAVE = "invoke_save";
   private static final String TARGET_LINK = "invoke_link";
   private static final String TARGET_FAILURE = "update_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("UpdateDsnDetailsCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

    	Collection<DamagedDSNVO> damagedDsnVOs = 
    		returnDsnSession.getDamagedDsnVOs();
    	//added by a-4810 for icrd-21867 since the screenstatusflag can only accept values screenload, view and detail.
    	String nextAction = returnDsnForm.getActionStatusFlag();
		
		int currentVoIndex = returnDsnForm.getCurrentPage();
		log.log(Log.INFO, "currentpage:-->", currentVoIndex);
		// validating & updating the vo in current index 
		int index = 1;
		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = null;
		for (DamagedDSNVO vo : damagedDsnVOs) {
			
			if (index == currentVoIndex) {				
				damagedDsnDetailVOs = vo.getDamagedDsnDetailVOs();
				if (damagedDsnDetailVOs != null) {
					
					damagedDsnDetailVOs = updateCurrentVO(
							damagedDsnDetailVOs,
							returnDsnForm,
							logonAttributes,
							returnDsnSession);
					
					/*errors = validateCurrentVO(
							damagedDsnDetailVOs,
							returnDsnForm,
							returnDsnSession);*/
					
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = TARGET_FAILURE;
						if ("LINK".equals(nextAction)) {
							returnDsnForm.setDisplayPage(
									returnDsnForm.getCurrentPage());
							returnDsnForm.setLastPage(
									returnDsnSession.getDamagedDsnVOs().size());
						}
						return;
					}
					
					boolean isReturned = false;
					if (damagedDsnDetailVOs != null) {
						for (DamagedDSNDetailVO dsndetailvo : damagedDsnDetailVOs) {
							if (dsndetailvo.getReturnedBags() != dsndetailvo.getLatestReturnedBags()
									|| dsndetailvo.getReturnedWeight() != dsndetailvo.getLatestReturnedWeight()) {
								isReturned = true;
								break;
							}
						}
					}
															
					if (isReturned) {
						int totalReturnBags = 0;
						double totalReturnWt = 0;
						for(DamagedDSNDetailVO dsnDetailVO : damagedDsnDetailVOs) {
							totalReturnBags = totalReturnBags + dsnDetailVO.getLatestReturnedBags();
							//totalReturnWt = totalReturnWt + dsnDetailVO.getLatestReturnedWeight(); 
							totalReturnWt = totalReturnWt + dsnDetailVO.getLatestReturnedWeight().getRoundedSystemValue();//added by A-7371 
						}
						log.log(Log.INFO, "TotalReturnBags:-->",
								totalReturnBags);
						log.log(Log.INFO, "TotalReturnWt:-->", totalReturnWt);
						vo.setLatestReturnedBags(totalReturnBags);
						//vo.setLatestReturnedWeight(totalReturnWt);
						vo.setLatestReturnedWeight(new Measure(UnitConstants.MAIL_WGT,totalReturnWt));//added by A-7371
					}
				}
				else {
					damagedDsnDetailVOs = new ArrayList<DamagedDSNDetailVO>();
				}
															
				vo.setDamagedDsnDetailVOs(damagedDsnDetailVOs);
				break;
			}										
			index++;
		}
		
		log.log(Log.FINE, "After updating DamagedDsnVOs------------> ",
				damagedDsnVOs);
		returnDsnSession.setDamagedDsnVOs(damagedDsnVOs);
    					
		if ("ADD".equals(nextAction)) {
			invocationContext.target = TARGET_ADD;
		}
		else if ("DELETE".equals(nextAction)) {
			invocationContext.target = TARGET_DELETE;
		}
		else if ("SAVE".equals(nextAction)) {
			invocationContext.target = TARGET_SAVE;
		}
		else if ("LINK".equals(nextAction)) {
			invocationContext.target = TARGET_LINK;
		}
		
       	log.exiting("UpdateDsnDetailsCommand","execute");
    	
    }
    /**
     * Method used to validate current VOs
     * @param damagedDsnDetailVOs
     * @param returnDsnForm
     * @param returnDsnSession
     * @return
     */
    private Collection<ErrorVO> validateCurrentVO(
    		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs,
    		ReturnDsnForm returnDsnForm,
    		ReturnDsnSession returnDsnSession) {
    	
    	log.entering("UpdateDsnDetailsCommand","validateCurrentVO");
    	
    	Collection<ErrorVO> validationErrors = new ArrayList<ErrorVO>();
    	
    	String[] damageCodes = returnDsnForm.getDamageCode();
    	String[] damagedbags = returnDsnForm.getDamagedBags();
    	String[] damagedweights = returnDsnForm.getDamagedWeight();
    	String[] returnedbags = returnDsnForm.getReturnedBags();
    	String[] returnedweights = returnDsnForm.getReturnedWeight();
    	   	
    	if (damageCodes != null) {
    		int index = 0;
    		for(DamagedDSNDetailVO dsnDetailVO : damagedDsnDetailVOs) {
    			Object[] obj = {handleDamageCodeDescription(returnDsnSession,damageCodes[index])};
    			if (damagedbags[index] != null && !"".equals(damagedbags[index])) {
    				if (dsnDetailVO.getReturnedBags() > Integer.parseInt(damagedbags[index])) {    					
    					ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.greaterDamagedBags",obj);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
    				}
    			}
    			if (damagedweights[index] != null && !"".equals(damagedweights[index])) {
    				//if (dsnDetailVO.getReturnedWeight() > Double.parseDouble(damagedweights[index])) {
    				if (dsnDetailVO.getReturnedWeight().getRoundedSystemValue() > Double.parseDouble(damagedweights[index])) {//added by A-7371
    					ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.greaterDamagedWt",obj);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
    				}
    			}  
    			if (returnedbags[index] != null && !"".equals(returnedbags[index])) {
    				if (dsnDetailVO.getReturnedBags() > Integer.parseInt(returnedbags[index])) {
    					ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.greaterReturnedBags",obj);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
    				}
    			}
    			if (returnedweights[index] != null && !"".equals(returnedweights[index])) {
    				//if (dsnDetailVO.getReturnedWeight() > Double.parseDouble(returnedweights[index])) {
    				if (dsnDetailVO.getReturnedWeight().getRoundedSystemValue() > Double.parseDouble(returnedweights[index])) {//added by A-7371
    					ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.greaterReturnedWt",obj);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
    				}
    			}  
    			  			
    			index++;
    		}
    	}
    	
    	log.exiting("UpdateDsnDetailsCommand","validateCurrentVO");
    	return validationErrors;
    } 
    
    /**
     * Method to update the DamagedMailbagVOs in session
     * @param damagedDsnDetailVOs
     * @param returnDsnForm
     * @param logonAttributes
     * @param returnDsnSession
     * @return
     */
    private Collection<DamagedDSNDetailVO> updateCurrentVO(
    		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs,
    		ReturnDsnForm returnDsnForm,
    		LogonAttributes logonAttributes,
    		ReturnDsnSession returnDsnSession) {
    	
    	log.entering("UpdateDsnDetailsCommand","updateCurrentVO");
    	
    	String[] damageCodes = returnDsnForm.getDamageCode();
    	String[] damageRemarks = returnDsnForm.getDamageRemarks();
    	String[] damagedbags = returnDsnForm.getDamagedBags();
    	String[] damagedweights = returnDsnForm.getDamagedWeight();
    	String[] returnedbags = returnDsnForm.getReturnedBags();
    	String[] returnedweights = returnDsnForm.getReturnedWeight();
    	   	
    	if (damageCodes != null) {
    		int index = 0;
    		for(DamagedDSNDetailVO dsnDetailVO : damagedDsnDetailVOs) {
    			dsnDetailVO.setDamageCode(damageCodes[index]);    			
    			if (damagedbags[index] != null && !"".equals(damagedbags[index])) {
    				dsnDetailVO.setLatestDamagedBags(Integer.parseInt(damagedbags[index]));
    			}
    			if (damagedweights[index] != null && !"".equals(damagedweights[index])) {
    				//dsnDetailVO.setLatestDamagedWeight(Double.parseDouble(damagedweights[index]));
    				dsnDetailVO.setLatestDamagedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(damagedweights[index])));//added by A-7371
    			}  
    			if (returnedbags[index] != null && !"".equals(returnedbags[index])) {
    				dsnDetailVO.setLatestReturnedBags(Integer.parseInt(returnedbags[index]));
    			}
    			if (returnedweights[index] != null && !"".equals(returnedweights[index])) {
    				//dsnDetailVO.setLatestReturnedWeight(Double.parseDouble(returnedweights[index]));
    				dsnDetailVO.setLatestReturnedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(returnedweights[index])));//added by A-7371
    			}  
    			
    			// UPDATING THE OPERATION FLAG
    			if (!DamagedDSNDetailVO.OPERATION_FLAG_INSERT.equals(dsnDetailVO.getOperationFlag())
    					&& !DamagedDSNDetailVO.OPERATION_FLAG_DELETE.equals(dsnDetailVO.getOperationFlag())
    					&& !DamagedDSNDetailVO.OPERATION_FLAG_UPDATE.equals(dsnDetailVO.getOperationFlag())) {
    				    				    				
    				if (dsnDetailVO.getRemarks() != null) {
    					dsnDetailVO.setRemarks("");
    				}
    				
    				if (dsnDetailVO.getDamagedBags() != dsnDetailVO.getLatestDamagedBags()
        					|| dsnDetailVO.getDamagedWeight() != dsnDetailVO.getLatestDamagedWeight()
        					|| dsnDetailVO.getReturnedBags() != dsnDetailVO.getLatestReturnedBags()
        					|| dsnDetailVO.getReturnedWeight() != dsnDetailVO.getLatestReturnedWeight()
        					|| !dsnDetailVO.getReturnedPaCode().equals(returnDsnForm.getPostalAdmin())
        					|| !damageRemarks[index].equals(dsnDetailVO.getRemarks())) {
    					log.log(Log.FINE,"Flag U ------------> ");
        				dsnDetailVO.setOperationFlag(DamagedDSNDetailVO.OPERATION_FLAG_UPDATE);
        			}
    			}
    			
    			dsnDetailVO.setRemarks(damageRemarks[index]); 	
    			
    			/*if (returnDsnForm.isReturnDsn()) {
    				dsnDetailVO.setReturnedBags(dsnDetailVO.getDamagedBags());
    				dsnDetailVO.setReturnedWeight(dsnDetailVO.getDamagedWeight());
				}*/
    			dsnDetailVO.setDamageDescription(
    					handleDamageCodeDescription(returnDsnSession,dsnDetailVO.getDamageCode()));	
    			
    			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    			dsnDetailVO.setDamageDate(currentdate);
    			dsnDetailVO.setDamageUser(logonAttributes.getUserId().toUpperCase());    
    			dsnDetailVO.setReturnedPaCode(returnDsnForm.getPostalAdmin());    
    			   			    			
    			index++;
    		}
    	}
    	
    	log.log(Log.FINE, "Updated DamagedDSNDetailVOs------------> ",
				damagedDsnDetailVOs);
		log.exiting("UpdateDsnDetailsCommand","updateCurrentVO");
    	return damagedDsnDetailVOs;
    } 
    /**
     * Method is used to get the onetime description
     * @param returnDsnSession
     * @param damagecode
     * @return
     */
    private String handleDamageCodeDescription(
    		ReturnDsnSession returnDsnSession,
    		String damagecode) {
    	
    	String damageDesc = "";
    	
    	Collection<OneTimeVO> damageCodes = returnDsnSession.getOneTimeDamageCodes();
    	for (OneTimeVO vo : damageCodes) {
    		if (vo.getFieldValue().equals(damagecode)) {
    			damageDesc = vo.getFieldDescription();
    			break;
    		}
    	}
    	
    	return damageDesc;    	
    }
}
