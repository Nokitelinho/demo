package com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer.embargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer.embargo.EmbargoValidationCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	23-Jul-2018	:	Draft
 */

public class EmbargoValidationCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL.OPERATIONS.COMMAND");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
	
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
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){
			log.log(Log.INFO,"Found errors in invocation context.RETURNING>>>>>>>>>>>>>>>");
			if(invocationContext.target == null){
				invocationContext.target = TARGET;
			}
			return;
		}
		
		TransferContainerForm transferContainerForm = 
	    		(TransferContainerForm)invocationContext.screenModel;
	    TransferContainerSession transferContainerSession = 
	    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
	    
	    CheckEmbargoSession embargoSession =  getScreenSession(MODULE_NAME_EMG, SCREEN_ID_EMG);
	    Collection<ContainerVO> selectedContainerVOs = transferContainerSession.getSelectedContainerVOs();
	    Collection<EmbargoDetailsVO> embargoDetailVos = null;
	    embargoSession.setEmbargos(null);
	    Collection<ContainerDetailsVO> containerDetailsVOsExisting=null;
	    containerDetailsVOsExisting=makeContainerDetailsVO(selectedContainerVOs);
	    Collection<ContainerDetailsVO> containerVOsToTransfer = new ArrayList<ContainerDetailsVO>();
		 if(containerDetailsVOsExisting != null && containerDetailsVOsExisting.size() >0){ 
			    try {
			    	containerVOsToTransfer = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOsExisting);     
				}catch (BusinessDelegateException businessDelegateException) {
					log   
							.log(
									Log.SEVERE,
									"BusinessDelegateException---findMailbagsInContainerForManifest",
									businessDelegateException.getMessage());
				}
			    }
		containerVOsToTransfer=setMailBagsForTransffer(containerVOsToTransfer,containerDetailsVOsExisting);   
	    if(containerVOsToTransfer != null && containerVOsToTransfer.size() >0){     
	    	embargoDetailVos = checkEmbargoForMail(containerVOsToTransfer);
	    	if (embargoDetailVos != null && embargoDetailVos.size() > 0) {
		    	embargoSession.setEmbargos(embargoDetailVos);
		    	transferContainerForm.setEmbargoFlag(EMBARGO_EXISTS);
		    	transferContainerForm.setCloseFlag("N");
		    	embargoSession.setContinueAction("mailtracking.defaults.searchcontainer.closesearchcontainer");
		    	log.log(Log.INFO, "Inside EmbargoDetails Collection size>0");
		    	invocationContext.target =TARGET;
				return;
	    	}
	    }   
		
	    transferContainerForm.setCloseFlag("Y");  
		invocationContext.target = TARGET;
		return;
	}
	
	/**
	 * 
	 * 	Method		:	EmbargoValidationCommand.checkEmbargoForMail
	 *	Added by 	:	A-8164 on 23-Jul-2018
	 * 	Used for 	:	ICRD-271652  
	 *	Parameters	:	@param containerVOsToTransfer
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<EmbargoDetailsVO>
	 */
	private Collection<EmbargoDetailsVO> checkEmbargoForMail(Collection<ContainerDetailsVO> containerVOsToTransfer) {
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

		if (containerVOsToTransfer != null && containerVOsToTransfer.size() > 0) {

			for (ContainerDetailsVO containerDetailsVO : containerVOsToTransfer) {

				if (containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() > 0) {
					for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {

						if (mailbagVO.getCarrierCode() == null) {
							mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
						}

						ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
						shipmentDetailsVO.setCompanyCode(containerDetailsVO.getCarrierCode());
						shipmentDetailsVO.setOoe(mailbagVO.getOoe());
						shipmentDetailsVO.setDoe(mailbagVO.getDoe());
						shipmentDetailsVO.setOrgStation(mailbagVO.getOoe().substring(2, 5));
						shipmentDetailsVO.setDstStation(mailbagVO.getDoe().substring(2, 5));
						shipmentDetailsVO.setOrgCountry(mailbagVO.getOoe().substring(0, 2));
						shipmentDetailsVO.setDstCountry(mailbagVO.getDoe().substring(0, 2));
						shipmentDetailsVO.setShipmentID(mailbagVO.getMailbagId());
						mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
						shipmentDetailsVO.setShipmentDate(mailbagVO.getScannedDate());
						String orgPaCod = null;
						String dstPaCod = null;

						Collection<String> mailsubclassGrp = new ArrayList<String>();
						try {
							orgPaCod = new MailTrackingDefaultsDelegate()
									.findPAForOfficeOfExchange(containerDetailsVO.getCarrierCode(), mailbagVO.getOoe());
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
							log.log(Log.SEVERE, "\n\n Excepption while checking for embargo. ---------------------->");
						}
						try {
							dstPaCod = new MailTrackingDefaultsDelegate()
									.findPAForOfficeOfExchange(containerDetailsVO.getCarrierCode(), mailbagVO.getDoe());
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
							log.log(Log.SEVERE, "\n\n Excepption while checking for embargo. ---------------------->");
						}

						shipmentDetailsVO.setOrgPaCod(orgPaCod);
						shipmentDetailsVO.setDstPaCod(dstPaCod);

						String mailBagId = mailbagVO.getMailbagId();

						detailsMap = populateDetailsMapforMail(mailBagId, containerDetailsVO.getCarrierCode());
						StringBuilder flightNumber = new StringBuilder();
						flightNumber.append(mailbagVO.getCarrierCode());
						if (mailbagVO.getFlightNumber() != null) {
							flightNumber.append("~").append(mailbagVO.getFlightNumber());
						}
						// for flight and carrier embargo
						flightNumberOrg.add(flightNumber.toString());
						flightNumberVia.add(flightNumber.toString());
						flightNumberDst.add(flightNumber.toString());
						carrierOrg.add(mailbagVO.getCarrierCode());
						carrierDst.add(mailbagVO.getCarrierCode());
						carrierVia.add(mailbagVO.getCarrierCode());
						detailsMap.put("CARORG", carrierOrg);
						detailsMap.put("CARVIA", carrierVia);
						detailsMap.put("CARDST", carrierDst);
						detailsMap.put("FLTNUMORG", flightNumberOrg);
						detailsMap.put("FLTNUMVIA", flightNumberVia);
						detailsMap.put("FLTNUMDST", flightNumberDst);

						if (detailsMap != null && detailsMap.size() > 0) {
							shipmentDetailsVO.setMap(detailsMap);
						}
						shipmentDetailsVO.setUserLocaleNeeded(true);
						shipmentDetailsVO.setApplicableTransaction("MALARR");
						shipmentDetailsVOs.add(shipmentDetailsVO);
					}

				}

			}
		}

			Collection<EmbargoDetailsVO> embargoDetails = null;
			if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) { 
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
	 * 
	 * 	Method		:	EmbargoValidationCommand.populateDetailsMapforMail
	 *	Added by 	:	A-8164 on 23-Jul-2018
	 * 	Used for 	:	ICRD-271652
	 *	Parameters	:	@param mailBagId
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<String>>
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
		
		
		 /**
		  * 
		  * Method		:	EmbargoValidationCommand.makeContainerDetailsVO
		  *	Added by 	:	A-8164 on 23-Jul-2018
		  * Used for 	:	ICRD-271652
		  *	Parameters	:	@param reasgnContainerVOs
		  *	Parameters	:	@return 
		  *	Return type	: 	Collection<ContainerDetailsVO>
		  */
		private Collection<ContainerDetailsVO> makeContainerDetailsVO(Collection<ContainerVO> reasgnContainerVOs) {
 			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
			for (ContainerVO containerVO :reasgnContainerVOs ) {
				//IF uld then only ---alert also !delivered flag check needed?
				if("U".equals(containerVO.getType()) && containerVO.getPol() != null ){
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
				containerDetailsVO.setCarrierId(containerVO.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setPol(containerVO.getPol());
				containerDetailsVO.setCarrierId(containerVO.getCarrierId()); 
				containerDetailsVO.setCarrierCode(containerVO.getCarrierCode()); 
				containerDetailsVOs.add(containerDetailsVO); 
			 }
			}
			return containerDetailsVOs;
		}
		
		
		/**
		 * 
		 * 	Method		:	EmbargoValidationCommand.setMailBagsForTransffer
		 *	Added by 	:	A-8164 on 23-Jul-2018
		 * 	Used for 	:	ICRD-271652
		 *	Parameters	:	@param containerVOsToTransfer
		 *	Parameters	:	@param containerDetailsVOsExisting
		 *	Parameters	:	@return 
		 *	Return type	: 	Collection<ContainerDetailsVO>
		 */
		private Collection<ContainerDetailsVO> setMailBagsForTransffer(
				Collection<ContainerDetailsVO> containerVOsToTransfer,Collection<ContainerDetailsVO> containerDetailsVOsExisting) { 
			for(ContainerDetailsVO containerDetailsVoWithDSN:containerVOsToTransfer){ 
				for(ContainerDetailsVO containerDetailsVoWithoutDSN:containerDetailsVOsExisting){       
					if(containerDetailsVoWithDSN.getContainerNumber()
							.equals(containerDetailsVoWithoutDSN.getContainerNumber())){        
						containerDetailsVoWithDSN.setFlightNumber(
								containerDetailsVoWithoutDSN.getFlightNumber());     
						containerDetailsVoWithDSN.setFlightSequenceNumber(
								containerDetailsVoWithoutDSN.getFlightSequenceNumber()); 
						containerDetailsVoWithDSN.setSegmentSerialNumber(
								containerDetailsVoWithoutDSN.getSegmentSerialNumber());
						containerDetailsVoWithDSN.setCarrierId(
								containerDetailsVoWithoutDSN.getCarrierId()); 
						containerDetailsVoWithDSN.setCarrierCode(
								containerDetailsVoWithoutDSN.getCarrierCode()); 
					}
				}
			}
			for(ContainerDetailsVO containerDetailsVO:containerVOsToTransfer){   
				Collection<MailbagVO> mailbagsForTransffer=new ArrayList<MailbagVO>();
				if(null!=containerDetailsVO.getDsnVOs()){					
					Collection<MailbagVO> mailbagsStore=null;
					for(DSNVO dsnVO:containerDetailsVO.getDsnVOs()){
						try {
							dsnVO.setFlightNumber(containerDetailsVO.getFlightNumber());
							dsnVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());       
							dsnVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
							dsnVO.setCarrierId(containerDetailsVO.getCarrierId());  
							mailbagsStore=new MailTrackingDefaultsDelegate().findDSNMailbags(dsnVO); 
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
						}
						for(MailbagVO mailbagVO:mailbagsStore){
							mailbagsForTransffer.add(mailbagVO);
						}
					}
				}	
				containerDetailsVO.setMailDetails(mailbagsForTransffer);
			}
			return containerVOsToTransfer; 
		}	

} 
