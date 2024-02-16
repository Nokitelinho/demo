package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AddMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.EmbargoValidationCommand.java
 *	Version		:	Name	:	Date			:	     Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	21-Dec-2018		:	Draft
 */
public class EmbargoValidationCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private Log log = LogFactory.getLogger("OPERATIONS EmbargoValidationCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("TransferMailCommand", "execute");
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		ContainerDetails containerDetails=mailinboundModel.getContainerDetail();
		if(null==containerDetailsCollection||containerDetailsCollection.size()==0){
			containerDetailsCollection=new ArrayList<ContainerDetails>();
			if(null!=containerDetails){
				containerDetailsCollection.add(containerDetails);
			}
			else{
				actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
				return;
			}
		}
		String embargoFlag=mailinboundModel.getEmbargoFlag();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		
		ContainerDetailsVO containerDetailsVO=
				new ContainerDetailsVO();
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		
		MailArrivalVO mailArrivalVO =null;
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO=null;
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
			
		mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		
		ArrayList<AddMailbag> addMailbags= mailinboundModel.getAddMailbags(); 
		Collection<MailbagVO> newMailbagVOs = null;
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		if(mailinboundModel.getAddMailbags()!=null){
			try{
		    	 newMailbagVOs =
		    			 populateMailBagVos(addMailbags,containerDetailsVO,mailinboundDetails);
		    	}
		    	 catch (Exception e) {
		    		 actionContext.addError(new ErrorVO("Invalid Mailbags"));
					return;
		 		}
		    	containerDetailsVO.setMailDetails(newMailbagVOs);
		    	containerDetailsVosSelected.add(containerDetailsVO);
			
		}else{
		if(null!=containerDetails){
			if(null!=mailArrivalVO){
				for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVO.getContainerDetails()){
					if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
						containerDetailsVO=containerDetailsVOIterate;
						break;
								
					}
				}
				dsnDetailsMap.clear();
				if(null!=containerDetails.getDsnDetailsCollection()&&
						containerDetails.getDsnDetailsCollection().size()>0){
					for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
						String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
								.append(dsnvo.getDestinationExchangeOffice())
									.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
										.append(dsnvo.getYear()).toString();
						dsnDetailsMap.put(dsnKey, dsnvo);	
					}
					Collection<DSNVO> selectedDsnVos=
							 new ArrayList<DSNVO>();
					selectedDsnVos.clear();
					for(DSNDetails dsnDetails:containerDetails.getDsnDetailsCollection()){
						String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
								.append(dsnDetails.getDestinationExchangeOffice())
									.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
										.append(dsnDetails.getYear()).toString();
						if(dsnDetailsMap.containsKey(dsnKey)){
							selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
						}
					}
				
					containerDetailsVO.setDsnVOs(selectedDsnVos);
				
				}
				else if(null!=containerDetails.getMailBagDetailsCollection()&&containerDetails.getMailBagDetailsCollection().size()>0){
					mailDetailsMap.clear();
					for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
						String mailKey=mailbagVO.getMailbagId();
						mailDetailsMap.put(mailKey, mailbagVO);	
					}
					Collection<MailbagVO> selectedMailBagVos=
							 new ArrayList<MailbagVO>();
					selectedMailBagVos.clear();
					for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
						String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
								.append(dsnvo.getDestinationExchangeOffice())
									.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
										.append(dsnvo.getYear()).toString();
						dsnDetailsMap.put(dsnKey, dsnvo);	
					}
					Collection<DSNVO> selectedDsnVos=
							 new ArrayList<DSNVO>();
					selectedDsnVos.clear();
					
					for(MailBagDetails mailBagDetails:containerDetails.getMailBagDetailsCollection()){
						if(mailBagDetails != null){
						String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
								.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
								.append(mailBagDetails.getYear()).toString();
						if(mailDetailsMap.containsKey(mailBagDetails.getMailBagId())){
							selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailBagId()));
						}
						if(dsnDetailsMap.containsKey(mailBagKey)){
							selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));
						}
						}
					}
					containerDetailsVO.setDsnVOs(selectedDsnVos);
					containerDetailsVO.setMailDetails(selectedMailBagVos);
					
				}
			
			}
			containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			containerDetailsVosSelected.add(containerDetailsVO);
		}
		else{
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVos){
				containerDetailsVoMap.put(
						containerDetailsVOIterate.getContainerNumber(), containerDetailsVOIterate);
			}
			for(ContainerDetails containerDetail:containerDetailsCollection){
				
				if(containerDetailsVoMap.containsKey(containerDetail.getContainerno())){
					containerDetailsVoMap.get(containerDetail.getContainerno())
									.setCarrierCode(operationalFlightVO.getCarrierCode());
					containerDetailsVoMap.get(containerDetail.getContainerno())
						.setCompanyCode(logonAttributes.getCompanyCode());
					containerDetailsVosSelected.add(
							containerDetailsVoMap.get(containerDetail.getContainerno()));
				}
			}
		}
	}
		
		if(null!=containerDetailsVosSelected&&containerDetailsVosSelected.size()>0 && mailinboundModel.getPopupAction() == null ){
			embargoDetailVos = checkEmbargoForMail(containerDetailsVosSelected,embargoFlag);
		}
		
		if(null!=embargoDetailVos){    
			actionContext.setAttribute("embargoFlag", "true");
			mailArrivalVO.setEmbargoDetails(embargoDetailVos);
			actionContext.setAttribute("mailArrivalDetails",mailArrivalVO);
			errors.add(new ErrorVO("mail.operations.err.embargoexists"));
		    actionContext.addAllError((List<ErrorVO>) errors);
			return;
			
		}
		Collection<EmbargoDetailsVO> embargoDetailVosEmpty = 
				new ArrayList<EmbargoDetailsVO>();
		actionContext.setAttribute("embargoFlag", "false");   
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		
	
	}
	
	public Collection<EmbargoDetailsVO>  checkEmbargoForMail(Collection<ContainerDetailsVO> containerDetailsVosSelected, String embargoFlag) {
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
	    LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
	    
	    if(containerDetailsVosSelected!=null && containerDetailsVosSelected.size() > 0){
	    
	    for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected)	{
	    	
		if(containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() >0){
		for(MailbagVO mailbagVO :containerDetailsVO.getMailDetails() ){
			if(mailbagVO.getMailbagId()!=null) {
			
			if(mailbagVO.getCarrierCode()==null){
				mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
			}
			
			ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
			shipmentDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
			shipmentDetailsVO.setOoe(mailbagVO.getOoe());
			shipmentDetailsVO.setDoe(mailbagVO.getDoe());
			Collection<String> officeOfExchanges=new ArrayList<String>();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			String originOE = mailbagVO.getOoe();
			String destOE   = mailbagVO.getDoe();
			officeOfExchanges.add(originOE);
			officeOfExchanges.add(destOE); 
			try {
				groupedOECityArpCodes=new MailTrackingDefaultsDelegate()     
						.findCityAndAirportForOE(mailbagVO.getCompanyCode(), officeOfExchanges);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException); 
			}
			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
				for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {  
					if(cityAndArpForOE.size() == 3) {
						if(originOE != null && originOE.length() > 0 && originOE.equals(cityAndArpForOE.get(0))) {
							shipmentDetailsVO.setOrgStation(cityAndArpForOE.get(2)); 
						}
						if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
							shipmentDetailsVO.setDstStation(cityAndArpForOE.get(2)); 
						}
					} 
				}
			}else{
			shipmentDetailsVO.setOrgStation(mailbagVO.getOoe().substring(2,5));
			shipmentDetailsVO.setDstStation(mailbagVO.getDoe().substring(2,5));
			}
			shipmentDetailsVO.setOrgCountry(mailbagVO.getOoe().substring(0,2));
			shipmentDetailsVO.setDstCountry(mailbagVO.getDoe().substring(0, 2));
			shipmentDetailsVO.setShipmentID(mailbagVO.getMailbagId());
			mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
			//shipmentDetailsVO.setOrgCntGrp(orgCntGrp)
			//shipmentDetailsVO.setDstCntGrp(dstCntGrp)
			//shipmentDetailsVO.setOrgArpGrp(orgArpGrp)
			//shipmentDetailsVO.setDstArpGrp(dstArpGrp)
			shipmentDetailsVO.setShipmentDate(mailbagVO.getScannedDate()!=null?mailbagVO.getScannedDate():new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
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
			if(embargoFlag!=null) {
				shipmentDetailsVO.setApplicableTransaction("MALTRA");
			}
			}}
		
		}    
		
	    }
	    }
		
		
		Map<String, DespatchDetailsVO> despathdetailsvoMap = new HashMap<String, DespatchDetailsVO>();
		
	    if(containerDetailsVosSelected!=null && containerDetailsVosSelected.size() > 0){
	    for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected)	{
	    	
	    	
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
	
	private Collection<MailbagVO> populateMailBagVos(
			ArrayList<AddMailbag> addMailbags, ContainerDetailsVO containerDetailsVO, MailinboundDetails mailinboundDetails) throws SystemException {
		
		Collection<MailbagVO> newMailbagVOs = 
				new ArrayList<>();
        LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		for(AddMailbag addMailbag:addMailbags){
			if(null!=addMailbag.getOperationFlag()){ 
				MailbagVO newMailbagVO = new MailbagVO();
				newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
		    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
		    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED); 
		    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);  
		    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
		    	newMailbagVO.setCarrierCode(mailinboundDetails.getCarrierCode());
		    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
		    	newMailbagVO.setAcceptanceFlag("N");
		    	newMailbagVO.setArrivedFlag("Y"); 
		    	newMailbagVO.setFlightDate((new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false).setDate(mailinboundDetails.getFlightDate().split(" ")[0])));
		    	newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		    	
		    	newMailbagVO.setOperationalFlag(addMailbag.getOperationFlag()); 
				newMailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
				newMailbagVO.setDisplayLabel("N");
				newMailbagVO.setMailCompanyCode(addMailbag.getMailCompanyCode());
				
				MailbagVO populatednewMailBagVo = populateMailBagInfo(addMailbag,newMailbagVO);
	
				newMailbagVOs.add(populatednewMailBagVo);
		
			}
		}
		return newMailbagVOs;
	}

	private MailbagVO populateMailBagInfo(AddMailbag addMailbag, MailbagVO newMailbagVO) throws SystemException {
        LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		if(!StringUtils.isBlank(addMailbag.getOoe())) {
			newMailbagVO.setOoe(addMailbag.getOoe().toUpperCase());
		}

		if(addMailbag.getMailSequenceNumber()!= 0) {
			newMailbagVO.setMailSequenceNumber(addMailbag.getMailSequenceNumber());
		}
		if(!StringUtils.isBlank(addMailbag.getDoe())) {
			newMailbagVO.setDoe(addMailbag.getDoe() .toUpperCase());
		}


		if(!StringUtils.isBlank(addMailbag.getMailCategoryCode())) {
			newMailbagVO.setMailCategoryCode(addMailbag.getMailCategoryCode());
		}


		if(!StringUtils.isBlank(addMailbag.getMailSubclass())) {
			newMailbagVO.setMailSubclass(addMailbag.getMailSubclass().toUpperCase());
			newMailbagVO.setMailClass(newMailbagVO.getMailSubclass().substring(0,1));
		}

	
		if(!StringUtils.isBlank(addMailbag.getMailbagId())) {
			newMailbagVO.setMailbagId(addMailbag.getMailbagId());
		}


		if(!StringUtils.isBlank(addMailbag.getScannedDate())){
			
				String scanDT=null;
				LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true); 
				if(addMailbag.getScannedTime().length()==8){
					scanDT = new StringBuilder(addMailbag.getScannedDate()).append(" ") 
							.append(addMailbag.getScannedTime()).toString();
				}
				else{
					scanDT = new StringBuilder(addMailbag.getScannedDate()).append(" ") 
						.append(addMailbag.getScannedTime()).append(":00").toString();
				}
				newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));  
			
		}

		newMailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,true));
		
		return newMailbagVO;
	}

}
