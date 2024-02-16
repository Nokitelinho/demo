package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ChangeContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.SaveMailChangeCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	26-Nov-2018		:	Draft
 */
public class SaveMailChangeCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String MAILBAGS_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";
	private static final String ERROR_TRANSFERRED_OR_DELIVERED ="mailtracking.defaults.err.mailAlreadyTransferedOrDelivered";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS SaveMailChangeCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ChangeContainerDetails changeContainerDetails= 
				(ChangeContainerDetails) mailinboundModel.getChangeContainerDetails();
		ContainerDetails containerDetailsToBeSaved=
				changeContainerDetails.getContainerDetail();
		MailArrivalVO mailArrivalVoToBeSaved =null;
		MailArrivalVO mailArrivalVO = null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<MailArrivalVO> mailArrivalVOs=
				new ArrayList<MailArrivalVO>();
		Collection<ContainerDetailsVO> containerDetailsVOsToBesaved=null;
		ContainerDetailsVO containerDetailsVOToBesaved=null;
		ContainerDetailsVO containerDetailVo=null;
		Collection<DSNVO> newDSNVOs=null;
		Collection<MailbagVO>newMailbagVOs=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ContainerDetails containerDetail=
				mailinboundModel.getContainerDetail();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		
		MailinboundDetails flightDetailsToBeSaved=
				changeContainerDetails.getFlightDetail();
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
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVos){
				if(containerDetailsVOIterate.getContainerNumber()
						.equals(containerDetail.getContainerno())){
					containerDetailVo=containerDetailsVOIterate;
					break;
				}
			}
			mailDetailsMap.clear();
			for(MailbagVO mailbagVO:containerDetailVo.getMailDetails()){
				String mailKey=mailbagVO.getMailbagId();
				mailDetailsMap.put(mailKey, mailbagVO);	
			}
			selectedMailBagVos.clear();
			if(null!=containerDetail.getMailBagDetailsCollection()){
				if(null!=containerDetail.getMailBagDetailsCollection() 
						&&containerDetail.getMailBagDetailsCollection().size()>0){
					for(DSNVO dsnvo:containerDetailVo.getDsnVOs()){
						String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
								.append(dsnvo.getDestinationExchangeOffice())
									.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
										.append(dsnvo.getYear()).toString();
						dsnDetailsMap.put(dsnKey, dsnvo);	
					}
					for(MailBagDetails mailBagDetailsIterate:containerDetail.getMailBagDetailsCollection()){
						String mailbagKey=new StringBuilder(mailBagDetailsIterate.getDSN()).append(mailBagDetailsIterate.getOoe())
								.append(mailBagDetailsIterate.getDoe()).append(mailBagDetailsIterate.getCategory()).append(mailBagDetailsIterate.getSubClass())
								.append(mailBagDetailsIterate.getYear()).toString();
						if(mailDetailsMap.containsKey(mailBagDetailsIterate.getMailBagId())){
							mailDetailsMap.get(mailBagDetailsIterate.getMailBagId()).setUndoArrivalFlag(MailConstantsVO.FLAG_YES); 
							selectedMailBagVos.add(mailDetailsMap.get(mailBagDetailsIterate.getMailBagId()));  
						}
						if(dsnDetailsMap.containsKey(mailbagKey)){
							selectedDsnVos.add(dsnDetailsMap.get(mailbagKey));
						}
					}
					containerDetailVo.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					containerDetailVo.setUndoArrivalFlag("DSN");
					containerDetailVo.setMailDetails(selectedMailBagVos);
					containerDetailVo.setDsnVOs(selectedDsnVos);
				}
			}
		}else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(flightDetailsToBeSaved, logonAttributes);
		try {
			mailArrivalVoToBeSaved = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		
		if(null!=mailArrivalVoToBeSaved){
			containerDetailsVOsToBesaved=mailArrivalVoToBeSaved.getContainerDetails();
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVOsToBesaved){ 
				if(containerDetailsVOIterate.getContainerNumber()
						.equals(containerDetailsToBeSaved.getContainerno())){
					containerDetailsVOToBesaved=containerDetailsVOIterate;
					break;
				}
			}
			
		}else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		containerDetailsVos.clear();
		containerDetailsVos.add(containerDetailVo);
		mailArrivalVO.setContainerDetails(containerDetailsVos);
		newDSNVOs=containerDetailsVOToBesaved.getDsnVOs();
		newMailbagVOs=containerDetailsVOToBesaved.getMailDetails();
		
		
		
		
		/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}*/
		
		mailArrivalVO.setChangeFlightFlag(MailConstantsVO.FLAG_NO);
		
		mailArrivalVOs.add(mailArrivalVO);
		
		String scanDate= new StringBuilder().append(changeContainerDetails.getDate()).append(" ")
				 .append(changeContainerDetails.getTime()).append(":00").toString();
     	LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
     	scanDat.setDateAndTime(scanDate);
     	
     	containerDetailsVOToBesaved.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
     	for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
     		for(DSNVO oldDSNVO:containerDetailsVO.getDsnVOs()){
     			DSNVO newDSNVO=new DSNVO();
     			try{
    				BeanHelper.copyProperties(newDSNVO, oldDSNVO);
    				}catch(SystemException e){
    					 e.getMessage();
    				}
     			newDSNVO.setContainerNumber(containerDetailsVOToBesaved.getContainerNumber());
				newDSNVO.setContainerType(containerDetailsVOToBesaved.getContainerType());
				newDSNVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				newDSNVO.setDestination(containerDetailsVOToBesaved.getDestination());
				newDSNVO.setLegSerialNumber(containerDetailsVOToBesaved.getLegSerialNumber());
				newDSNVO.setFlightNumber(containerDetailsVOToBesaved.getFlightNumber());
				newDSNVO.setFlightSequenceNumber(containerDetailsVOToBesaved.getFlightSequenceNumber());
				newDSNVO.setSegmentSerialNumber(containerDetailsVOToBesaved.getSegmentSerialNumber());
				newDSNVO.setReceivedDate(scanDat);
				newDSNVOs.add(newDSNVO);
				containerDetailsVOToBesaved.setDsnVOs(newDSNVOs); 
     		}
     		for(MailbagVO oldMailbagVO:containerDetailsVO.getMailDetails()){
     			if((MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getArrivedFlag()))&&
						!(MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getDeliveredFlag())||
						MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(oldMailbagVO.getMailStatus())
						||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(oldMailbagVO.getMailStatus()))){
     				MailbagVO newMailbagVO=new MailbagVO();
					try{
						BeanHelper.copyProperties(newMailbagVO, oldMailbagVO);
						}catch(SystemException e){
							 e.getMessage();
						}
	     			newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	newMailbagVO.setContainerNumber(containerDetailsVOToBesaved.getContainerNumber());
			    	newMailbagVO.setCarrierCode(containerDetailsVOToBesaved.getCarrierCode());
			    	newMailbagVO.setFlightDate(containerDetailsVOToBesaved.getFlightDate());
			    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			    	newMailbagVO.setScannedDate(scanDat);
			    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
			    	newMailbagVO.setCarrierId(containerDetailsVOToBesaved.getCarrierId());
			    	newMailbagVO.setFlightNumber(containerDetailsVOToBesaved.getFlightNumber());
			    	newMailbagVO.setFlightSequenceNumber(containerDetailsVOToBesaved.getFlightSequenceNumber());
			    	newMailbagVO.setFlightDate(operationalFlightVO.getFlightDate());
			    	newMailbagVO.setSegmentSerialNumber(containerDetailsVOToBesaved.getSegmentSerialNumber());
			    	newMailbagVO.setUldNumber(containerDetailsVOToBesaved.getContainerNumber());
			    	newMailbagVO.setContainerType(containerDetailsVOToBesaved.getContainerType());
			    	newMailbagVO.setPou(containerDetailsVOToBesaved.getPou());
			    	newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_NO);
			    	newMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_FLAG_INSERT);
			    	newMailbagVOs.add(newMailbagVO);
			    	containerDetailsVOToBesaved.setMailDetails(newMailbagVOs);
			    	
     			}
     		}
     		
     	}
     	
     	
     	
     	containerDetailsVOToBesaved = makeDSNVOs(containerDetailsVOToBesaved,logonAttributes);
     	containerDetailsVOsToBesaved.clear();
     	containerDetailsVOsToBesaved.add(containerDetailsVOToBesaved); 
     	mailArrivalVoToBeSaved.setContainerDetails(containerDetailsVOsToBesaved);
     	mailArrivalVoToBeSaved.setScanDate(scanDat);
     	mailArrivalVoToBeSaved.setArrivedUser(logonAttributes.getUserId().toUpperCase());
     	mailArrivalVoToBeSaved.setChangeFlightFlag(MailConstantsVO.FLAG_YES);
     	
     	mailArrivalVoToBeSaved.setArrivedUser(logonAttributes.getUserId().toUpperCase());
     	mailArrivalVoToBeSaved.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
     	mailArrivalVoToBeSaved.setFlightNumber(operationalFlightVO.getFlightNumber());
     	mailArrivalVoToBeSaved.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
     	mailArrivalVoToBeSaved.setArrivalDate(operationalFlightVO.getFlightDate());
     	mailArrivalVoToBeSaved.setCarrierId(operationalFlightVO.getCarrierId());
     	mailArrivalVoToBeSaved.setCompanyCode(operationalFlightVO.getCompanyCode());
     	mailArrivalVoToBeSaved.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
     	mailArrivalVoToBeSaved.setPol(operationalFlightVO.getPol());
     	mailArrivalVoToBeSaved.setPou(operationalFlightVO.getPou());
     	mailArrivalVoToBeSaved.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
     	mailArrivalVoToBeSaved.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
     	mailArrivalVOs.add(mailArrivalVoToBeSaved);
     	
     	Collection<LockVO> locks = prepareLocksForSave(mailArrivalVoToBeSaved);
     	
     	if (locks == null || locks.size() == 0) {
  			locks = null;
  		}
  		  try {
  		    new MailTrackingDefaultsDelegate().saveChangeFlightDetails(mailArrivalVOs,locks);
            }catch (BusinessDelegateException businessDelegateException) {
      			errors = handleDelegateException(businessDelegateException);
      	  }
  		  if (errors != null && errors.size() > 0) {
  				for(ErrorVO err : errors){
  					if(ERROR_TRANSFERRED_OR_DELIVERED.equalsIgnoreCase(err.getErrorCode())){
  						ErrorVO error = new ErrorVO(MAILBAGS_TRANSFERRED_OR_DELIVERED);
  						error.setErrorDisplayType(ErrorDisplayType.ERROR);
  						actionContext.addError(error);
  					}else{
  						actionContext.addAllError((List<ErrorVO>) errors);
  					}
  				}

  	    		return;
      	  }
     	
  		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		mailinboundModel.setMailArrivalVO(mailArrivalVO);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO("mail.operations.succ.changeflightsuccess");      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
		actionContext.setResponseVO(responseVO);
	}
	
	

	private ContainerDetailsVO makeDSNVOs(ContainerDetailsVO popupVO,
			LogonAttributes logonAttributes) {


		HashMap<String,DSNVO> dsnMapDespatch = new HashMap<String,DSNVO>();
		HashMap<String,String> despatchMap = new HashMap<String,String>();
		Collection<DSNVO> mainDSNVOs = popupVO.getDsnVOs();
		if(mainDSNVOs != null && mainDSNVOs.size() > 0){
			for(DSNVO dsnVO:mainDSNVOs){
				if(!MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
					if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
					String dsnpk = dsnVO.getOriginExchangeOffice()
			           +dsnVO.getDestinationExchangeOffice()
			           +dsnVO.getMailCategoryCode()
			           +dsnVO.getMailSubclass()
			           +dsnVO.getDsn()
			           +dsnVO.getYear();
					dsnMapDespatch.put(dsnpk,dsnVO);
					}
				}
		    }
		}

		int rcvdBags = 0;
		double rcvdWgt = 0;
		int delvdBags = 0;
		double delvdWgt = 0;
		Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
		 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
			for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
				String outerpk = despatchVO.getOriginOfficeOfExchange()
				           +despatchVO.getDestinationOfficeOfExchange()
				           +despatchVO.getMailCategoryCode()
				           +despatchVO.getMailSubclass()
				           +despatchVO.getDsn()
				           +despatchVO.getYear();

				if(despatchMap.get(outerpk) == null){
				if(dsnMapDespatch.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(despatchVO.getDsn());
					dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
					dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
					dsnVO.setMailClass(despatchVO.getMailClass());
					dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
					dsnVO.setMailSubclass(despatchVO.getMailSubclass());
					dsnVO.setYear(despatchVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_NO);
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				for(DespatchDetailsVO innerVO:despatchDetailsVOs){
					String innerpk = innerVO.getOriginOfficeOfExchange()
			           +innerVO.getDestinationOfficeOfExchange()
			           +innerVO.getMailCategoryCode()
			           +innerVO.getMailSubclass()
			           +innerVO.getDsn()
			           +innerVO.getYear();
					if(outerpk.equals(innerpk)){
						rcvdBags = rcvdBags + innerVO.getReceivedBags();
						rcvdWgt = rcvdWgt + innerVO.getReceivedWeight().getRoundedSystemValue();
						delvdBags = delvdBags + innerVO.getDeliveredBags();
						delvdWgt = delvdWgt + innerVO.getDeliveredWeight().getRoundedSystemValue();
					}
				}
				dsnVO.setReceivedBags(rcvdBags);
				dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rcvdWgt));
				dsnVO.setDeliveredBags(delvdBags);
				dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,delvdWgt));
				dsnMapDespatch.put(outerpk,dsnVO);

				}else{
					DSNVO dsnVO = dsnMapDespatch.get(outerpk);
					String dsnpk = dsnVO.getOriginExchangeOffice()
			           +dsnVO.getDestinationExchangeOffice()
			           +dsnVO.getMailCategoryCode()
			           +dsnVO.getMailSubclass()
			           +dsnVO.getDsn()
			           +dsnVO.getYear();
					if(despatchDetailsVOs.size() > 0){
						for(DespatchDetailsVO dsptchVO:despatchDetailsVOs){
						String despatchpk = dsptchVO.getOriginOfficeOfExchange()
						           +dsptchVO.getDestinationOfficeOfExchange()
						           +dsptchVO.getMailCategoryCode()
						           +dsptchVO.getMailSubclass()
						           +dsptchVO.getDsn()
						           +dsptchVO.getYear();
							    if(dsnpk.equals(despatchpk)){
							    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsptchVO.getOperationalFlag())){
							    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							    	}
									rcvdBags = rcvdBags + despatchVO.getReceivedBags();
									rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight().getRoundedSystemValue();
									delvdBags = delvdBags + despatchVO.getDeliveredBags();
									delvdWgt = delvdWgt + despatchVO.getDeliveredWeight().getRoundedSystemValue();
								}
						}
						if(dsnVO.getReceivedBags()!= rcvdBags
							|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= rcvdWgt
							|| dsnVO.getDeliveredBags()!= delvdBags
							|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= delvdWgt){
							if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
								dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							}
						}
						dsnVO.setReceivedBags(rcvdBags);
						dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rcvdWgt));
						dsnVO.setDeliveredBags(delvdBags);
						dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,delvdWgt));
						dsnMapDespatch.put(outerpk,dsnVO);
					}
				  }
				despatchMap.put(outerpk,outerpk);
				}
				rcvdBags = 0;
				rcvdWgt = 0;
				delvdBags = 0;
				delvdWgt = 0;
				}
			}


		 /**
		  * For Mail Bag Details
		  */

		 HashMap<String,String> mailMap = new HashMap<String,String>();
		 HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
			if(mainDSNVOs != null && mainDSNVOs.size() > 0){
				for(DSNVO dsnVO:mainDSNVOs){
					if(MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
						if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
						String dsnpk = dsnVO.getOriginExchangeOffice()
				           +dsnVO.getDestinationExchangeOffice()
				           +dsnVO.getMailCategoryCode()
				           +dsnVO.getMailSubclass()
				           +dsnVO.getDsn()
				           +dsnVO.getYear();
						dsnMapMailbag.put(dsnpk,dsnVO);
						}
					}
			    }
			}

		int numBags = 0;
		double bagWgt = 0;
		int dlvBags = 0;
		double dlvWgt = 0;
		 Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
		 if(mailbagVOs != null && mailbagVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagVOs){

				String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
						   + mailbagVO.getMailCategoryCode()
				           + mailbagVO.getMailSubclass()
				           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
				if(mailMap.get(outerpk) == null){
				if(dsnMapMailbag.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					dsnVO.setYear(mailbagVO.getYear());
					dsnVO.setWeight(mailbagVO.getWeight()); 
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				for(MailbagVO innerVO:mailbagVOs){
					String innerpk = innerVO.getOoe()+innerVO.getDoe()
					+ innerVO.getMailCategoryCode()
			           +(innerVO.getMailSubclass())
			           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
					if(outerpk.equals(innerpk)){
						if(MailConstantsVO.FLAG_YES.equals(innerVO.getArrivedFlag())){
							dsnVO.setReceivedBags(numBags + 1);
							try {
								dsnVO.setReceivedWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,bagWgt)
										, innerVO.getWeight()));
							} catch (UnitException e) {
								log.log(Log.SEVERE,"UnitException", e.getMessage());
							}
							numBags= dsnVO.getReceivedBags();
							bagWgt = dsnVO.getReceivedWeight().getRoundedSystemValue();
						}
						if(MailConstantsVO.FLAG_YES.equals(innerVO.getDeliveredFlag())){
							dsnVO.setDeliveredBags(dlvBags + 1);
							try {
								dsnVO.setDeliveredWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,dlvWgt),
										innerVO.getWeight()));
							} catch (UnitException e) {
								log.log(Log.SEVERE,"UnitException", e.getMessage());
							}
							dlvBags = dsnVO.getDeliveredBags();
							dlvWgt = dsnVO.getDeliveredWeight().getRoundedSystemValue();
						}
					}
				}
				dsnMapMailbag.put(outerpk,dsnVO);
				}else{
					DSNVO dsnVO = dsnMapMailbag.get(outerpk);
					String dsnpk = dsnVO.getOriginExchangeOffice()
			           +dsnVO.getDestinationExchangeOffice()
			           +dsnVO.getMailCategoryCode()
			           +dsnVO.getMailSubclass()
			           +dsnVO.getDsn()
			           +dsnVO.getYear();
					if(mailbagVOs.size() > 0){
						for(MailbagVO mbagVO:mailbagVOs){
						String mailpk = mbagVO.getOoe()+mbagVO.getDoe()
						   + mbagVO.getMailCategoryCode()
				           + mbagVO.getMailSubclass()
				           + mbagVO.getDespatchSerialNumber()+mbagVO.getYear();
							    if(dsnpk.equals(mailpk)){
							    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mbagVO.getOperationalFlag())
							    			|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mbagVO.getOperationalFlag())){
							    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							    	}
									if(MailConstantsVO.FLAG_YES.equals(mbagVO.getArrivedFlag()) ){
										numBags = numBags + 1;
										bagWgt = bagWgt + mbagVO.getWeight().getRoundedSystemValue();
									}
									if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDamageFlag())){
										dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								    }
									if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDeliveredFlag())){
										dlvBags = dlvBags + 1;
										dlvWgt = dlvWgt + mbagVO.getWeight().getRoundedSystemValue();
									}
								}
						}
						if(dsnVO.getReceivedBags()!= numBags
								|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= bagWgt){
								dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
						}
						dsnVO.setReceivedBags(numBags);
						dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));

						if(dsnVO.getDeliveredBags()!= dlvBags
								|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= dlvWgt){
									dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							}
						dsnVO.setDeliveredBags(dlvBags);
						dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,dlvWgt));

						dsnMapMailbag.put(outerpk,dsnVO);
					}
				  }
				mailMap.put(outerpk,outerpk);
				}
				numBags = 0;
				bagWgt = 0;
				dlvBags = 0;
				dlvWgt = 0;
			}
		}

		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		for(String key:dsnMapDespatch.keySet()){
			DSNVO dsnVO = dsnMapDespatch.get(key);
			newDSNVOs.add(dsnVO);
		}
		for(String key:dsnMapMailbag.keySet()){
			DSNVO dsnVO = dsnMapMailbag.get(key);
			if(newDSNVOs.size() == 0){
			   newDSNVOs = new ArrayList<DSNVO>();
			}
			newDSNVOs.add(dsnVO);
		}

		Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
		int accBags = 0;
		double accWgt = 0;
		int rcvedBags = 0;
		double rcvedWgt = 0;
		if(newDSNVOs.size() > 0){
			for(DSNVO dsnVO1:newDSNVOs){
				String outerpk = dsnVO1.getOriginExchangeOffice()
				   +dsnVO1.getDestinationExchangeOffice()
				   +dsnVO1.getMailCategoryCode()
				   +dsnVO1.getMailSubclass()
		           +dsnVO1.getDsn()+dsnVO1.getYear();
				int flag = 0;
				if(oldDSNVOs != null && oldDSNVOs.size() > 0){
					for(DSNVO dsnVO2:oldDSNVOs){
						String innerpk = dsnVO2.getOriginExchangeOffice()
						   +dsnVO2.getDestinationExchangeOffice()
						   +dsnVO2.getMailCategoryCode()
						   +dsnVO2.getMailSubclass()
				           +dsnVO2.getDsn()+dsnVO2.getYear();
						if(outerpk.equals(innerpk)){
							if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO2.getOperationFlag())){
								dsnVO1.setPrevBagCount(dsnVO2.getPrevBagCount());
								dsnVO1.setPrevBagWeight(dsnVO2.getPrevBagWeight());
                                dsnVO1.setPrevReceivedBags(dsnVO2.getPrevReceivedBags());
                                dsnVO1.setPrevReceivedWeight(dsnVO2.getPrevReceivedWeight());
                                dsnVO1.setPrevDeliveredBags(dsnVO2.getPrevDeliveredBags());
                                dsnVO1.setPrevDeliveredWeight(dsnVO2.getPrevDeliveredWeight());
							}
							flag = 1;
						}
					}
				}
				if(flag == 1){
					flag = 0;
				}
				accBags = accBags + dsnVO1.getBags();
				accWgt = accWgt + dsnVO1.getWeight().getRoundedSystemValue();
				rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
				rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight().getRoundedSystemValue();
			}
		}
		popupVO.setTotalBags(accBags);
		popupVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,accWgt));
		popupVO.setReceivedBags(rcvedBags);
		popupVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rcvedWgt));
		popupVO.setDsnVOs(newDSNVOs);
	
		return popupVO;
	}
	
	
	 private Collection<LockVO> prepareLocksForSave(
	    		MailArrivalVO mailArrivalVO) {
	    	log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	Collection<LockVO> locks = new ArrayList<LockVO>();
	    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();

	    	if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
	    		for (ContainerDetailsVO conVO : containerDetailsVOs) {
	    			if(conVO.getOperationFlag()!=null && conVO.getOperationFlag().trim().length()>0){
	    				ArrayList<MailbagVO> mailbagvos=new ArrayList<MailbagVO>(conVO.getMailDetails());
	    				if(mailbagvos!=null && mailbagvos.size()>0){
	    				for(MailbagVO bagvo:mailbagvos){
	    					if(bagvo.getOperationalFlag()!=null && bagvo.getOperationalFlag().trim().length()>0){
	    						ULDLockVO lock = new ULDLockVO();
	    		    			lock.setAction(LockConstants.ACTION_MAILARRIVAL);
	    		    			lock.setClientType(ClientType.WEB);
	    		    			lock.setCompanyCode(logonAttributes.getCompanyCode());
	    		    			lock.setScreenId(SCREEN_ID);
	    		    			lock.setStationCode(logonAttributes.getStationCode());
	    		    			if(bagvo.getContainerForInventory() != null){
	    		    				lock.setUldNumber(bagvo.getContainerForInventory());
	    		    				lock.setDescription(bagvo.getContainerForInventory());
	    		    				lock.setRemarks(bagvo.getContainerForInventory());
	    		    			log.log(Log.FINE, "\n lock------->>", lock);
								locks.add(lock);
	    					    }
	    					}

	    					}
	    			}

	    		}
	    	}

	    }
	    	return locks;
	    }

}
