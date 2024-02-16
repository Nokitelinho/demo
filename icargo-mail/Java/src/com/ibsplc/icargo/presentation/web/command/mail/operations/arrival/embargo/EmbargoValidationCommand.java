/*
 * EmbargoValidationCommand.java Created on Dec 22 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.embargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;





/**
 * The Class EmbargoValidationCommand.
 *
 * @author A-7871
 * Command Class to perform embargo check .
 * 
 */
public class EmbargoValidationCommand extends BaseCommand {

	
	/** The log. */
	private Log log = LogFactory.getLogger("MAIL.OPERATIONS.COMMAND");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	/*
	 * The module and screenID for the embargo
	 */
	/** The Constant MODULE_NAME_EMG. */
	private static final String MODULE_NAME_EMG = "reco.defaults";

	/** The Constant SCREEN_ID_EMG. */
	private static final String SCREEN_ID_EMG = "reco.defaults.checkembargo";
	/*
	 * Flag used for Embargo Check and calling the Show Embargo Screen
	 *
	 */
	private static final String EMBARGO_EXISTS = "embargo_exists";
	private static final String TARGET = "success";
	private static final String  POPUP= "popup";
	private static final String MAIN = "main";
	private static final String EMBARGO_LEVEL_ERROR = "E";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {


		if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){
			log.log(Log.INFO,"Found errors in invocation context.RETURNING>>>>>>>>>>>>>>>");
			//mailAcceptanceForm.setPopupCloseFlag("N"); needed
			if(invocationContext.target == null){
				invocationContext.target = TARGET;
			}
			return;
		}
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		CheckEmbargoSession embargoSession =  getScreenSession(MODULE_NAME_EMG, SCREEN_ID_EMG);
		ContainerDetailsVO containerDetailsVOsExisting = mailArrivalSession.getContainerDetailsVO();
		ContainerDetailsVO newVo = new ContainerDetailsVO();
		newVo= containerDetailsVOsExisting;
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		embargoSession.setEmbargos(null);
		//Collection<ContainerDetailsVO> containerDetailsVO = mailArrivalVO.getContainerDetails();
		MailArrivalForm mailArrivalForm =
	    		(MailArrivalForm)invocationContext.screenModel;	
		//A-8061 ICRD-263340 Begin
		ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		String[] selectedContainers = mailArrivalForm.getSelectContainer();
		ArrayList<ContainerDetailsVO> selectedContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		String fromScreen =(mailArrivalForm.getFromScreen()==null)?"" : mailArrivalForm.getFromScreen();
		HashMap<String,String> embargoMailMap = new HashMap<String,String>();

		FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();

		if(MAIN.equals(fromScreen)){
			if(selectedContainers!=null){
				for(int i=0;i<selectedContainers.length;i++){
				ContainerDetailsVO selectedContainerDetailsVO = containerDetailsVOs.get(Integer.parseInt(selectedContainers[i].split("~")[0]));
				if(selectedContainerDetailsVO.getCarrierCode()==null){
					selectedContainerDetailsVO.setCarrierCode(flightValidationVO.getCarrierCode());
					selectedContainerDetailsVO.setCarrierCode(mailArrivalForm.getFlightCarrierCode());
				}
				selectedContainerDetailsVOs.add(selectedContainerDetailsVO);
				}
			}
			embargoDetailVos = checkEmbargoForMail(selectedContainerDetailsVOs);
		}		
		else if(POPUP.equals(fromScreen)){
			ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
			//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
				if(containerDetailsVO != null) {
				containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				containerDetailsVO.setCarrierCode(flightValidationVO.getCarrierCode());
				selectedContainerDetailsVOs.add(containerDetailsVO);
				embargoDetailVos = checkEmbargoForMail(selectedContainerDetailsVOs);
			}		
		}

		//A-8061 added for ICRD-263340 END

		if (embargoDetailVos != null && embargoDetailVos.size() > 0) {
			log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
				if (mailArrivalForm.isCanCheckEmbargo()) {
					embargoSession.setEmbargos(embargoDetailVos);
					embargoSession.setContinueAction("mailtracking.defaults.mailarrival.closemailarrival");
					log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
					mailArrivalForm.setEmbargoFlag(EMBARGO_EXISTS);
					mailArrivalForm.setPopupCloseFlag("N");
					 if(POPUP.equals(fromScreen)){
						if(containerDetailsVOsExisting!=null){
				    			for(EmbargoDetailsVO embargoDetailsVO : embargoDetailVos){
				    				if(EMBARGO_LEVEL_ERROR.equals(embargoDetailsVO.getEmbargoLevel())){
				    					embargoMailMap.put(embargoDetailsVO.getShipmentID(), embargoDetailsVO.getShipmentID());
				    				}
				    			}
						for(MailbagVO mailbagVO :containerDetailsVOsExisting.getMailDetails()){
							if(embargoMailMap.size() > 0 && embargoMailMap.containsKey(mailbagVO.getMailbagId())&&"Y".equals(mailbagVO.getArrivedFlag())){
								mailbagVO.setArrivedFlag("N");
							}
						}
						}
					mailArrivalSession.setContainerDetailsVO(containerDetailsVOsExisting);
					 }
					//mailArrivalVO.setContainerDetails(containerDetailsVOs);
					//mailArrivalSession.setContainerDetailsVOs(containerDetailsVOs);
					//mailArrivalSession.setMailArrivalVO(mailArrivalVO);
					invocationContext.target = TARGET;
					return;
				}
			else{
				embargoSession.setEmbargos(null);
			}
		}
		else{
			
			 if(POPUP.equals(fromScreen)){
			//updating in main screen VOs
			Collection<ContainerDetailsVO> newContainerDetailsVOs = mailArrivalSession.getContainerDetailsVOs();
			Collection<ContainerDetailsVO> contDetailsVOs = mailArrivalVO.getContainerDetails();
			log.log(Log.FINE, "contDetailsVOs ...in mainscreen", contDetailsVOs);
			Collection<ContainerDetailsVO> newVOs = new ArrayList<ContainerDetailsVO>();
			int flag = 0;
			if(contDetailsVOs != null && contDetailsVOs.size() > 0){
			  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
				  if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
					for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
					  if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
						  newVOs.add(popupVO);
						  flag = 1;
					  }
			        }
				  }
				  if(flag == 1){
					  flag = 0;
				  }else{
					  newVOs.add(mainscreenVO);
				  }
			  }
			}
			log.log(Log.FINE, "newVOs ...in first", newVOs);
			flag = 0;
			if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
			      if(contDetailsVOs != null && contDetailsVOs.size() > 0){
				  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
					   if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
							  flag = 1;
					   }
				    }
				  }
				  if(flag == 0){
					  newVOs.add(popupVO);
				  }else{
					  flag = 0;
				  }
			  }
			}
			//
			mailArrivalVO.setContainerDetails(newVOs);
					mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			/*mailArrivalSession.setContainerDetailsVOs(null);
			mailArrivalSession.setContainerDetailsVO(null);*/
			mailArrivalForm.setPopupCloseFlag("Y");
			 }
			
			invocationContext.target = TARGET;
			return;
		}
	
		
	}
	/**
	 * 
	 * @param containerDetailsVO
	 * @return
	 */
	public Collection<EmbargoDetailsVO>  checkEmbargoForMail(ArrayList<ContainerDetailsVO> containerDetailsVOs) {
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = null;
		Map<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
	    Set flightNumberOrg = new HashSet();
	    Set flightNumberDst = new HashSet();
	    Set flightNumberVia = new HashSet();
	    Set carrierOrg = new HashSet();
	    Set carrierDst = new HashSet();
	    Set carrierVia = new HashSet();
	    
	    
	    if(containerDetailsVOs!=null && containerDetailsVOs.size() > 0){
	    
	    for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs)	{
	    	
		if(containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() >0){
		for(MailbagVO mailbagVO :containerDetailsVO.getMailDetails() ){
			
			if(mailbagVO.getCarrierCode()==null){
				mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
			}
			
			ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
			shipmentDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
			shipmentDetailsVO.setOoe(mailbagVO.getOoe());
			shipmentDetailsVO.setDoe(mailbagVO.getDoe());
			shipmentDetailsVO.setOrgStation(mailbagVO.getOoe().substring(2,5));
			shipmentDetailsVO.setDstStation(mailbagVO.getDoe().substring(2,5));
			shipmentDetailsVO.setOrgCountry(mailbagVO.getOoe().substring(0,2));
			shipmentDetailsVO.setDstCountry(mailbagVO.getDoe().substring(0, 2));
			shipmentDetailsVO.setShipmentID(mailbagVO.getMailbagId());
			mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
			//shipmentDetailsVO.setOrgCntGrp(orgCntGrp)
			//shipmentDetailsVO.setDstCntGrp(dstCntGrp)
			//shipmentDetailsVO.setOrgArpGrp(orgArpGrp)
			//shipmentDetailsVO.setDstArpGrp(dstArpGrp)
			shipmentDetailsVO.setShipmentDate(mailbagVO.getScannedDate());
			//shipmentDetailsVO.setMap(map)
			String orgPaCod = null;
			String dstPaCod =  null;
		
			Collection<String> mailsubclassGrp =new ArrayList<String>();
			try {
				 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),mailbagVO.getOoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}
			try {
				dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),mailbagVO.getDoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}

			shipmentDetailsVO.setOrgPaCod(orgPaCod);
			shipmentDetailsVO.setDstPaCod(dstPaCod);
			
			
			String mailBagId = mailbagVO.getMailbagId();
			
			detailsMap = populateDetailsMapforMail(mailbagVO);
			 StringBuilder flightNumber = new StringBuilder();
			 flightNumber.append(mailbagVO.getCarrierCode());
			  if (mailbagVO.getFlightNumber() != null) {
		            flightNumber.append("~").append(
		            		mailbagVO.getFlightNumber());
		          }
			  //for flight and carrier embargo
			  flightNumberOrg.add(flightNumber.toString());
			  flightNumberVia.add(flightNumber.toString());
			  flightNumberDst.add(flightNumber.toString());
			  carrierOrg.add(mailbagVO.getCarrierCode());
			carrierDst.add(mailbagVO.getCarrierCode());
			 carrierVia.add(mailbagVO.getCarrierCode());
			 detailsMap.put("CARORG", 
				        carrierOrg);
			 detailsMap.put("CARVIA", 
					 carrierVia);
			 detailsMap.put("CARDST", 
					 carrierDst);
			 detailsMap.put("FLTNUMORG", 
					 flightNumberOrg);
			 detailsMap.put("FLTNUMVIA", 
					 flightNumberVia);
			 detailsMap.put("FLTNUMDST", 
					 flightNumberDst);

			  
			if(detailsMap != null && detailsMap.size()>0){
				shipmentDetailsVO.setMap(detailsMap);
			}
			shipmentDetailsVO.setUserLocaleNeeded(true);
			shipmentDetailsVO.setApplicableTransaction("MALARR");
			shipmentDetailsVOs.add(shipmentDetailsVO);
			}
		
		}    
		
	    }
	    }
		
		
		Map<String, DespatchDetailsVO> despathdetailsvoMap = new HashMap<String, DespatchDetailsVO>();
		
	    if(containerDetailsVOs!=null && containerDetailsVOs.size() > 0){
	    for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs)	{
	    	
	    	
		if(containerDetailsVO.getDesptachDetailsVOs() != null && containerDetailsVO.getDesptachDetailsVOs().size() >0){
			newDespatchVOs = containerDetailsVO.getDesptachDetailsVOs();
			for(DespatchDetailsVO  despatchDetailsVO : newDespatchVOs){
				ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
				shipmentDetailsVO.setOoe(despatchDetailsVO.getOriginOfficeOfExchange());
				shipmentDetailsVO.setDoe(despatchDetailsVO.getDestinationOfficeOfExchange());
				shipmentDetailsVO.setOrgStation(despatchDetailsVO.getOriginOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setDstStation(despatchDetailsVO.getDestinationOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setOrgCountry(despatchDetailsVO.getOriginOfficeOfExchange().substring(0,2));
				shipmentDetailsVO.setDstCountry(despatchDetailsVO.getDestinationOfficeOfExchange().substring(0, 2));
				shipmentDetailsVO.setCompanyCode(containerDetailsVO.getCompanyCode());
				String mailId = new StringBuilder()
		            .append(despatchDetailsVO.getOriginOfficeOfExchange())
		            .append(despatchDetailsVO.getDestinationOfficeOfExchange())
					.append(despatchDetailsVO.getMailCategoryCode())
					.append(despatchDetailsVO.getMailSubclass())
					.append(despatchDetailsVO.getYear())
					.append(despatchDetailsVO.getDsn()).toString();
				shipmentDetailsVO.setShipmentID(mailId);
				despathdetailsvoMap.put(mailId, despatchDetailsVO);
				shipmentDetailsVO.setApplicableTransaction("MALARR");
				shipmentDetailsVO.setShipmentDate(despatchDetailsVO.getAcceptedDate());
				detailsMap = populateDetailsMapforMail(mailId,containerDetailsVO.getCompanyCode());
				if(detailsMap != null && detailsMap.size()>0){
					shipmentDetailsVO.setMap(detailsMap);
				}
			  	String orgPaCod = null;
				String dstPaCod =  null;
				try {
					 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),shipmentDetailsVO.getOoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				}
				try {
					dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),shipmentDetailsVO.getDoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				} 
				shipmentDetailsVO.setOrgPaCod(orgPaCod);
				shipmentDetailsVO.setDstPaCod(dstPaCod);
				shipmentDetailsVO.setUserLocaleNeeded(true);
				shipmentDetailsVOs.add(shipmentDetailsVO);
			 }
       }
		
		}
		}
		
		//ShipmentDetailsVO shipmentDetailsVo = new ShipmentDetailsVO();
		Collection<EmbargoDetailsVO> embargoDetails = null;
		if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) {
			// shipmentDetailsVo.setMailBagVOs(newMailbagVOs);
			try {
				embargoDetails = new MailTrackingDefaultsDelegate()
						.checkEmbargoForMail(shipmentDetailsVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log.log(Log.SEVERE,
						"\n\n Excepption while checking for embargo. ---------------------->");
			}
		}
		log.log(Log.INFO, "EmbargoDetails Collection ", embargoDetails);
		return embargoDetails;
	}



/**
 * @author A-4810
 * @param mailBagId
 * @param companyCode
 * @return
 */
	public Map<String, Collection<String>> populateDetailsMapforMail(String mailBagId, String companyCode) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass =new ArrayList<String>();
		Collection<String> mailsubclass =new ArrayList<String>();
		Collection<String> mailcat =new ArrayList<String>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp =new ArrayList<String>();
		String subClassGrp =  null;
		if (mailBagId != null && mailBagId.trim().length() > 0) {
			if(mailBagId.length() >19) {
				detailsMap = new HashMap<String, Collection<String>>();
				mailcat.add(mailBagId.substring(12, 13));
				mailsubclass.add(mailBagId.substring(13, 15));
				mailclass.add(mailBagId.substring(13, 15).substring(0, 1));
				detailsMap.put("MALCLS", mailclass);
				detailsMap.put("MALSUBCLS",mailsubclass);
				detailsMap.put("MALCAT",mailcat);
				try {
					mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(companyCode,mailBagId.substring(13, 15));
				}
				catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if(mailSubClassVOs != null && mailSubClassVOs.size()>0){
					subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
					if(subClassGrp != null) {
						mailsubclassGrp.add(subClassGrp);
						detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
						
					}
				}
			
			}
		}
		return detailsMap;
	}
	
	public Map<String, Collection<String>> populateDetailsMapforMail(MailbagVO mailbagVO) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass = new ArrayList<String>();
		Collection<String> mailsubclass = new ArrayList<String>();
		Collection<String> mailcat = new ArrayList<String>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp = new ArrayList<String>();
		String subClassGrp = null;
		if (mailbagVO != null) {
			detailsMap = new HashMap<String, Collection<String>>();
			mailcat.add(mailbagVO.getMailCategoryCode());
			mailsubclass.add(mailbagVO.getMailSubclass());
			mailclass.add(mailbagVO.getMailClass());
			detailsMap.put("MALCLS", mailclass);
			detailsMap.put("MALSUBCLS", mailsubclass);
			detailsMap.put("MALCAT", mailcat);
			try {
				mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(mailbagVO.getCompanyCode(),
						mailbagVO.getMailSubclass());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
				subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
				if (subClassGrp != null) {
					mailsubclassGrp.add(subClassGrp);
					detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
				}
			}

		}
		return detailsMap;
	}
}
