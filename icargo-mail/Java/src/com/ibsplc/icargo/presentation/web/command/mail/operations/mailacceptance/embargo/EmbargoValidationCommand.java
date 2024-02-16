/**
 * EmbargoValidationCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance.embargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class EmbargoValidationCommand.
 *
 * @author A-5991
 * Command Class to perform embargo check .
 * Embargo Check moved to this seperate command as part of command orchestration implementation
 */
public class EmbargoValidationCommand extends BaseCommand{

	/** The log. */
	private Log log = LogFactory.getLogger("MAIL.OPERATIONS.COMMAND");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
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


	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
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
    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		CheckEmbargoSession embargoSession =  getScreenSession(MODULE_NAME_EMG, SCREEN_ID_EMG);
		ContainerDetailsVO containerDetailsVOsExisting = mailAcceptanceSession.getContainerDetailsVO();
		ContainerDetailsVO newVo = new ContainerDetailsVO();
		newVo= containerDetailsVOsExisting;
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		embargoSession.setEmbargos(null);
		//Modified by A-4810 for icrd-90664
		Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
		//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			if(containerDetailsVO != null) {
			containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			embargoDetailVos = checkEmbargoForMail(containerDetailsVO);
		}		
		MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)invocationContext.screenModel;	

		if (embargoDetailVos != null && embargoDetailVos.size() > 0) {
			log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
				if (mailAcceptanceForm.isCanCheckEmbargo()) {
					embargoSession.setEmbargos(embargoDetailVos);
					embargoSession.setContinueAction("mailtracking.defaults.mailacceptance.closemailAcceptance");
					log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
					mailAcceptanceForm.setEmbargoFlag(EMBARGO_EXISTS);
					mailAcceptanceForm.setPopupCloseFlag("N");
					mailAcceptanceSession.setContainerDetailsVO(containerDetailsVOsExisting);
					//mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
					//mailAcceptanceSession.setContainerDetailsVOs(containerDetailsVOs);
					//mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
					invocationContext.target = TARGET;
					return;
				}
			else{
				embargoSession.setEmbargos(null);
			}
	}
		else{
			//Added by A-4810 for icrd-90664
			//updating in main screen VOs
			Collection<ContainerDetailsVO> newContainerDetailsVOs = mailAcceptanceSession.getContainerDetailsVOs();
			Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
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
			mailAcceptanceVO.setContainerDetails(newVOs);
					mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
			mailAcceptanceSession.setContainerDetailsVOs(null);
			mailAcceptanceSession.setContainerDetailsVO(null);
			mailAcceptanceForm.setPopupCloseFlag("Y");
			invocationContext.target = TARGET;
			return;
		}
	}
	
	/**
	 * 
	 * @param containerDetailsVO
	 * @return
	 */
	public Collection<EmbargoDetailsVO>  checkEmbargoForMail(ContainerDetailsVO containerDetailsVO) {
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = null;
		Map<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
		if(containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() >0){
		for(MailbagVO mailbagVO :containerDetailsVO.getMailDetails() ){
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
			
			detailsMap = populateDetailsMapforMail(mailBagId,containerDetailsVO.getCompanyCode());
			if(detailsMap != null && detailsMap.size()>0){
				shipmentDetailsVO.setMap(detailsMap);
			}
			//added for ICRD-223091 by A-7815
			shipmentDetailsVO.setUserLocaleNeeded(true);
			shipmentDetailsVO.setApplicableTransaction("MALACP");//modified by a-7871
			shipmentDetailsVOs.add(shipmentDetailsVO);
			}
		
		}    
		Map<String, DespatchDetailsVO> despathdetailsvoMap = new HashMap<String, DespatchDetailsVO>();
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
				shipmentDetailsVO.setApplicableTransaction("MALACP");//modified by a-7871
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
	
	
}
