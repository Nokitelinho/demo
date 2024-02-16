/*
 * EmbargoValidationCommand.java Created on Dec 22 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail.embargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;
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
	private static final String SCREEN_ID = "mailtracking.defaults.transfermail";
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
    	TransferMailSession transferMailSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		CheckEmbargoSession embargoSession =  getScreenSession(MODULE_NAME_EMG, SCREEN_ID_EMG);
		Collection<ContainerVO> containerDetailsVOsExisting = transferMailSession.getContainerVOs();
		Collection<ContainerVO> newVo;
		newVo= containerDetailsVOsExisting;
	//	MailArrivalVO mailArrivalVO = transferMailSession.getMailArrivalVO();
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		embargoSession.setEmbargos(null);
		//Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		Collection<MailbagVO> mailbagVOs = transferMailSession.getMailbagVOs();
		//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			if(mailbagVOs != null) {
				//mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			embargoDetailVos = checkEmbargoForMail(mailbagVOs);
		}		
			TransferMailForm transferMailForm =
    		(TransferMailForm)invocationContext.screenModel;	

		if (embargoDetailVos != null && embargoDetailVos.size() > 0) {
			log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
				if (transferMailForm.isCanCheckEmbargo()) {
					embargoSession.setEmbargos(embargoDetailVos);
					embargoSession.setContinueAction("mailtracking.defaults.transfermail.CloseMailTransferCommand");
					log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
					transferMailForm.setEmbargoFlag(EMBARGO_EXISTS);
					transferMailForm.setCloseFlag("N");
					//transferMailSession.setContainerVOs(containerDetailsVOsExisting);
					//mailArrivalVO.setContainerDetails(containerDetailsVOs);
					//transferMailSession.setContainerDetailsVOs(containerDetailsVOs);
					//transferMailSession.setMailArrivalVO(mailArrivalVO);
					invocationContext.target = TARGET;
					return;
				}
			else{
				embargoSession.setEmbargos(null);
			}
	}
		else{
			//updating in main screen VOs
		/*	Collection<ContainerDetailsVO> newContainerDetailsVOs = transferMailSession.getContainerDetailsVOs();
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
			}*/
			//
			/*mailArrivalVO.setContainerDetails(newVOs);
					transferMailSession.setMailArrivalVO(mailArrivalVO);
			transferMailSession.setContainerDetailsVOs(null);
			transferMailSession.setContainerDetailsVO(null);*/
			transferMailForm.setCloseFlag("Y");
			invocationContext.target = TARGET;
			return;
		}
	
		
	}
	/**
	 * 
	 * @param containerDetailsVO
	 * @return
	 */
	public Collection<EmbargoDetailsVO>  checkEmbargoForMail(Collection<MailbagVO> mailbagVOs) {
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = null;
		Map<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
		  Set<String> flightNumberOrg = new HashSet<String>();
		    Set<String> flightNumberDst = new HashSet<String>();
		    Set<String> flightNumberVia = new HashSet<String>();
		    Set<String> carrierOrg = new HashSet<String>();
		    Set<String> carrierDst = new HashSet<String>();
		    Set<String> carrierVia = new HashSet<String>();
		if(mailbagVOs != null && mailbagVOs.size() >0){
		for(MailbagVO mailbagVO :mailbagVOs){
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
		
			 StringBuilder flightNumber = new StringBuilder();
			 flightNumber.append(mailbagVO.getCarrierCode());
			  if (mailbagVO.getFlightNumber() != null) {
		            flightNumber.append("~").append(
		            		mailbagVO.getFlightNumber());
		          }
			
			try {
				 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),mailbagVO.getOoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}
			try {
				dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),mailbagVO.getDoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}

			shipmentDetailsVO.setOrgPaCod(orgPaCod);
			shipmentDetailsVO.setDstPaCod(dstPaCod);
			
			detailsMap = populateDetailsMapforMail(mailbagVO);
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
			shipmentDetailsVO.setApplicableTransaction("MALTRA");
			shipmentDetailsVOs.add(shipmentDetailsVO);
			}
		
		}  
		TransferMailSession transferMailSession =
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
		Map<String, DespatchDetailsVO> despathdetailsvoMap = new HashMap<String, DespatchDetailsVO>();
		if(transferMailSession.getDespatchDetailsVOs() != null && transferMailSession.getDespatchDetailsVOs().size() >0){
			newDespatchVOs = transferMailSession.getDespatchDetailsVOs();
			for(DespatchDetailsVO  despatchDetailsVO : newDespatchVOs){
				ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
				shipmentDetailsVO.setOoe(despatchDetailsVO.getOriginOfficeOfExchange());
				shipmentDetailsVO.setDoe(despatchDetailsVO.getDestinationOfficeOfExchange());
				shipmentDetailsVO.setOrgStation(despatchDetailsVO.getOriginOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setDstStation(despatchDetailsVO.getDestinationOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setOrgCountry(despatchDetailsVO.getOriginOfficeOfExchange().substring(0,2));
				shipmentDetailsVO.setDstCountry(despatchDetailsVO.getDestinationOfficeOfExchange().substring(0, 2));
				shipmentDetailsVO.setCompanyCode(despatchDetailsVO.getCompanyCode());
				String mailId = new StringBuilder()
		            .append(despatchDetailsVO.getOriginOfficeOfExchange())
		            .append(despatchDetailsVO.getDestinationOfficeOfExchange())
					.append(despatchDetailsVO.getMailCategoryCode())
					.append(despatchDetailsVO.getMailSubclass())
					.append(despatchDetailsVO.getYear())
					.append(despatchDetailsVO.getDsn()).toString();
				shipmentDetailsVO.setShipmentID(mailId);
				despathdetailsvoMap.put(mailId, despatchDetailsVO);
				shipmentDetailsVO.setApplicableTransaction("MALTRA");
				shipmentDetailsVO.setShipmentDate(despatchDetailsVO.getAcceptedDate());
				detailsMap = populateDetailsMapforDespatch(despatchDetailsVO);
				if(detailsMap != null && detailsMap.size()>0){
					shipmentDetailsVO.setMap(detailsMap);
				}
			  	String orgPaCod = null;
				String dstPaCod =  null;
				try {
					 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(despatchDetailsVO.getCompanyCode(),shipmentDetailsVO.getOoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				}
				try {
					dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(despatchDetailsVO.getCompanyCode(),shipmentDetailsVO.getDoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				} 
				shipmentDetailsVO.setOrgPaCod(orgPaCod);
				shipmentDetailsVO.setDstPaCod(dstPaCod);
				//added for ICRD-223091 by A-7815
				shipmentDetailsVO.setUserLocaleNeeded(true);
				shipmentDetailsVOs.add(shipmentDetailsVO);
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
	public Map<String, Collection<String>> populateDetailsMapforMail(MailbagVO mailbagVO) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass =new ArrayList<String>();
		Collection<String> mailsubclass =new ArrayList<String>();
		Collection<String> mailcat =new ArrayList<String>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp =new ArrayList<String>();
		String subClassGrp =  null;
		if (mailbagVO != null) {
				detailsMap = new HashMap<String, Collection<String>>();
				mailcat.add(mailbagVO.getMailCategoryCode());
				mailsubclass.add(mailbagVO.getMailSubclass());
				mailclass.add(mailbagVO.getMailClass());
				detailsMap.put("MALCLS", mailclass);
				detailsMap.put("MALSUBCLS",mailsubclass);
				detailsMap.put("MALCAT",mailcat);
				try {
					mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(mailbagVO.getCompanyCode(),mailbagVO.getMailSubclass());
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
		return detailsMap;
	}
	/**
	 * 
	 * 	Method		:	EmbargoValidationCommand.populateDetailsMapforDespatch
	 *	Added by 	:	A-6245 on 23-Apr-2018
	 * 	Used for 	:	Populate Map using Despatch details
	 * 					Modified existing method to map from mailbag id, as substring of mailbag id is
	 * 					not valid for non upu mails
	 *	Parameters	:	@param despatchDetailsVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<String>>
	 */
	public Map<String, Collection<String>> populateDetailsMapforDespatch(
			DespatchDetailsVO despatchDetailsVO) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass = new ArrayList<String>();
		Collection<String> mailsubclass = new ArrayList<String>();
		Collection<String> mailcat = new ArrayList<String>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp = new ArrayList<String>();
		String subClassGrp = null;
		if (despatchDetailsVO != null) {
			detailsMap = new HashMap<String, Collection<String>>();
			mailcat.add(despatchDetailsVO.getMailCategoryCode());
			mailsubclass.add(despatchDetailsVO.getMailSubclass());
			mailclass.add(despatchDetailsVO.getMailClass());
			detailsMap.put("MALCLS", mailclass);
			detailsMap.put("MALSUBCLS", mailsubclass);
			detailsMap.put("MALCAT", mailcat);
			try {
				mailSubClassVOs = new MailTrackingDefaultsDelegate()
						.findMailSubClassCodes(despatchDetailsVO.getCompanyCode(),despatchDetailsVO.getMailSubclass());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
				subClassGrp = mailSubClassVOs.iterator().next()
						.getSubClassGroup();
				if (subClassGrp != null) {
					mailsubclassGrp.add(subClassGrp);
					detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
				}
			}

		}
		return detailsMap;
	}
}
