/*
 * ValidateDeliveryCommand.java Created on Jun 30 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ValidateDeliveryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS= "success";
	private static final String TARGET_FAIL= "failure";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";


	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("ValidateDeliveryCommand","execute");

		MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID);		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();		
		String[] selectedContainer = mailArrivalForm.getSelectContainer();
		String[] childContainer = mailArrivalForm.getChildContainer();

		String airport = logonAttributes.getAirportCode();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<String> does=new ArrayList<String>();
		Integer errorFlag = 0;
		ContainerDetailsVO containerDetailsVO = null;
		ArrayList<ContainerDetailsVO> containerDtlsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		if(containerDtlsVOs != null && containerDtlsVOs.size() > 0){
			for(ContainerDetailsVO containerDtlsVO:containerDtlsVOs){
				containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			}
		}
		if(selectedContainer!= null || childContainer!=null ){
			if(selectedContainer!=null){

				for(int i=0;i<selectedContainer.length;i++){    			
					containerDetailsVO =  containerDtlsVOs.get(Integer.parseInt(selectedContainer[i]));
					Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
					if(mailbags !=null){
						for(MailbagVO mailbagVO :mailbags ){
							log.log(Log.FINE, "VALIDATING SELECTEDCONTAINER MAIL");
		         			if(!does.contains(mailbagVO.getDoe())){
								does.add(mailbagVO.getDoe());
		         			}
						} 						
					}
					Collection<DespatchDetailsVO> despatches = containerDetailsVO.getDesptachDetailsVOs();
					if(despatches !=null){
						for(DespatchDetailsVO despatchVO :despatches ){
							log.log(Log.FINE, "VALIDATING SELECTEDCONTAINER DESPATCH");
		         			if(!does.contains(despatchVO.getDestinationOfficeOfExchange())){
		    					does.add(despatchVO.getDestinationOfficeOfExchange());
		         			}
						}  
					}						
				}
				errorFlag=validateDOEs(does,companyCode,airport);
			}else{
				String selectedDSN[] = mailArrivalForm.getChildContainer();
				String selectMainContainer = mailArrivalForm.getSelectMainContainer();
				String selectedDSNs=selectedDSN[0];
				String[] primaryKey = selectedDSNs.split(",");   	
				ContainerDetailsVO containerDtlsVO= containerDtlsVOs.get(Integer.parseInt(selectMainContainer));
				Collection<DSNVO> dsnVOs = containerDtlsVO.getDsnVOs();
				for (int i=0;i<primaryKey.length;i++){
					String pKey = primaryKey[i].split("~")[1];
					DSNVO dsnVO=(DSNVO) new ArrayList<DSNVO>(containerDtlsVO.getDsnVOs()).get(Integer.parseInt(pKey));

					String innerpk = dsnVO.getOriginExchangeOffice()
					+dsnVO.getDestinationExchangeOffice()
					+dsnVO.getMailCategoryCode()
					+dsnVO.getMailSubclass()
					+dsnVO.getDsn()
					+dsnVO.getYear();
					log.log(Log.FINE, "@#$#$#%$#$dsnVO ::", dsnVO);
					if(("Y").equalsIgnoreCase(dsnVO.getPltEnableFlag())){
						if(containerDtlsVO.getMailDetails()!=null){
							for(MailbagVO mailbagVO : containerDtlsVO.getMailDetails()){

								String outerpk = mailbagVO.getOoe()
								+mailbagVO.getDoe()
								+mailbagVO.getMailCategoryCode()
								+mailbagVO.getMailSubclass()
								+mailbagVO.getDespatchSerialNumber()
								+mailbagVO.getYear();

								if(innerpk.equals(outerpk)){		
									if(!does.contains(mailbagVO.getDoe())){
										does.add(mailbagVO.getDoe());
									}
								}
							} 
							log.log(Log.FINE, "VALIDATING MAILBAG");
							errorFlag=validateDOEs(does,companyCode,airport);
						}
					}else{
						for(DespatchDetailsVO despatchDetailsVO : containerDtlsVO.getDesptachDetailsVOs()){
							String outpk = despatchDetailsVO.getOriginOfficeOfExchange()
							+despatchDetailsVO.getDestinationOfficeOfExchange()
							+despatchDetailsVO.getMailCategoryCode()
							+despatchDetailsVO.getMailSubclass()
							+despatchDetailsVO.getDsn()
							+despatchDetailsVO.getYear();
							if(innerpk.equals(outpk)){
			         			if(!does.contains(despatchDetailsVO.getDestinationOfficeOfExchange())){
			    					does.add(despatchDetailsVO.getDestinationOfficeOfExchange());
			         			}
							}
						} 
						log.log(Log.FINE, "VALIDATING DISPATCH");
						errorFlag=validateDOEs(does,companyCode,airport);
					}



				}	
			}
		}else{
			for(ContainerDetailsVO containerDtlsVO : containerDtlsVOs){
				for(MailbagVO mailbagVO :containerDtlsVO.getMailDetails()){
					log.log(Log.FINE, "VALIDATING ALL MAILBAG");
         			if(!does.contains(mailbagVO.getDoe())){
						does.add(mailbagVO.getDoe());
         			}
				}
				for(DespatchDetailsVO despatchDetailsVO : containerDtlsVO.getDesptachDetailsVOs()){
					log.log(Log.FINE, "VALIDATING ALL DISPATCH");
         			if(!does.contains(despatchDetailsVO.getDestinationOfficeOfExchange())){
    					does.add(despatchDetailsVO.getDestinationOfficeOfExchange());
         			}
				}
			}
			errorFlag=validateDOEs(does,companyCode,airport);
		}
		if(errorFlag == 1){
			log.log(Log.INFO,"<<----DOE of Mailbag/Dispatches is NOT SAME as that of the current Airport --->>");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.cannotdeliveratthisport",new Object[]{logonAttributes.getAirportCode()}));
			mailArrivalForm.setChkFlag("");
			mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_FAIL;
			return;
		} 
		mailArrivalForm.setChkFlag("showChangeScanTimeScreen");	
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);	
		invocationContext.target = TARGET_SUCCESS;		
		log.exiting("ValidateDeliveryCommand","execute");

	}
	/**
	 * validateDOEs will validate the DOE's of Mailbags/Dispatches to know whether 
	 * the Current Airport is matching with the DOE for Deliver.
	 * @param does
	 * @param companyCode
	 * @param airport
	 * @return
	 */
	private Integer validateDOEs(Collection<String> does,String companyCode,String airport){
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		Integer errorFlag = 0;
		if(does != null && does.size()!=0){
			try {
 				/*
 			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
 			     * the inner collection contains the values in the order :
 			     * 0.OFFICE OF EXCHANGE
 			     * 1.CITY NEAR TO OE
 			     * 2.NEAREST AIRPORT TO CITY
 			     */
 				groupedOECityArpCodes = 
 					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(companyCode, does);
 			}catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
			}
 			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
 				for(String doe : does){
 					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
 						if(cityAndArpForOE.size() == 3 && 
 								doe.equals(cityAndArpForOE.get(0)) && 
 								airport.equals(cityAndArpForOE.get(2))){
 							errorFlag = 1;
 							break;
 						}			
 					}
 					if(errorFlag == 1) {
 						break;
 					}
 				}
 			}								
		}
		return errorFlag;
	}

}
