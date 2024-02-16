package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ArriveMailCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	17-Nov-2018		:	Draft
 */
public class ArriveMailCommand extends AbstractCommand {
	

	private static final String FLIGHT ="FLT";
	private  static final String SUCCESS_MESSAGE="mail.operations.err.arrivesuccess";
	private static final String EMBARGO_EXIST="mail.operations.err.embargoexists";
	private static final String EMBARGO_EXISTS = "Embargo Exists";
	private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS ArriveMailCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ArriveMailCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		HashMap<String,ContainerDetailsVO> containerDetailsMap=
				new HashMap<String,ContainerDetailsVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>(); 
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		
		
		ArrayList<ContainerDetails> containerDetailsCollection=
				mailinboundModel.getContainerDetailsCollection();
		 Collection<ContainerDetailsVO> containerDetailsVos=null;
		 Collection<ContainerDetailsVO> selectedContainerVos=
				 new ArrayList<ContainerDetailsVO>();
		 
		
		
		MailArrivalVO mailArrivalVO=null;
		
		Collection<ErrorVO> embargoErrors = new ArrayList<>();
    	CheckEmbargoSession checkEmbargoSession = getScreenSession("reco.defaults", "reco.defaults.ux.checkembargo");
		MailArrivalVO mailArrivalDetailsVO = (MailArrivalVO)actionContext.getAttribute("mailArrivalDetails");
		List<ErrorVO> existingerrors = actionContext.getErrors();
		boolean embargoInfo= false;
		if(CollectionUtils.isNotEmpty(existingerrors) && CollectionUtils.isNotEmpty(mailArrivalDetailsVO.getEmbargoDetails()))
	    {
	    	  Iterator errorsList = existingerrors.iterator();
	    	  while (errorsList.hasNext()) {
	        	  ErrorVO errorVO = (ErrorVO)(errorsList).next();
	        	  if (EMBARGO_EXIST.equals(errorVO.getErrorCode()))
		          {
	        		  if(MailConstantsVO.INFO.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
		        		  embargoInfo=true;
		        		  errorsList.remove();
		        	  	}else{
			        		  checkEmbargoSession.setEmbargos(mailArrivalDetailsVO.getEmbargoDetails());
			        		  if(MailConstantsVO.WARNING.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
			        			  checkEmbargoSession.setContinueAction("mail.operations.ux.mailinbound.arrivemail");
			        		  }
		        			  responseVO.setStatus("embargo");
			        		  errorsList.remove();
			        	  } 
		          	} 
	    	  } 
	    	  if(!embargoInfo) {
	    		  actionContext.setResponseVO(responseVO);
		          return;
	    	  }
	    	  else{
	    		ErrorVO displayErrorVO = new ErrorVO(EMBARGO_EXISTS);
	  			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
	  			embargoErrors.add(displayErrorVO);
	    	  }
	   }
	    else if(CollectionUtils.isNotEmpty(existingerrors)) {
	          return;
	    }
		
		if(null==containerDetailsCollection||containerDetailsCollection.isEmpty()){
			actionContext.addError(new ErrorVO("mail.operations.mailarrival.nomailbagstoarrive"));
			return;
		}
		
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
		
		
		
		
		/*
		 * 
		 * For getting the selected container details
		 */
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
			containerDetailsMap.put(containerDetailsVO.getContainerNumber(), containerDetailsVO);
		}
		
		for(ContainerDetails containerDetails:containerDetailsCollection){
			//selectedContainerVos.clear();    
			
			if(containerDetailsMap.containsKey(containerDetails.getContainerno())){
				ContainerDetailsVO containerDetailsVO=
						containerDetailsMap.get(containerDetails.getContainerno());
				mailDetailsMap.clear();  
				for(MailbagVO mailbagVO:containerDetailsMap.get(containerDetails.getContainerno()).getMailDetails()){
					String mailKey=mailbagVO.getMailbagId();   
					mailDetailsMap.put(mailKey, mailbagVO);	     
				}
				Collection<MailbagVO> selectedMailBagVos=
						 new ArrayList<MailbagVO>();
				selectedMailBagVos.clear();
				if(null!=containerDetails.getDsnDetailsCollection()&&containerDetails.getDsnDetailsCollection().size()>0){
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
						
						for(MailbagVO mailbagVO:containerDetailsMap.get(containerDetails.getContainerno()).getMailDetails()){
							String mailKey=new StringBuilder(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getOoe())
									.append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass())
									.append(mailbagVO.getYear()).toString();
							if(mailKey.equals(dsnKey)){
								selectedMailBagVos.add(mailbagVO);
							}
						}
						if(dsnDetailsMap.containsKey(dsnKey)){
							selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
						}
					}
					containerDetailsMap.get(containerDetails.getContainerno()).setDsnVOs(selectedDsnVos);
				}
				else if(null!=containerDetails.getMailBagDetailsCollection()&&containerDetails.getMailBagDetailsCollection().size()>0){
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
					containerDetailsMap.get(containerDetails.getContainerno()).setDsnVOs(selectedDsnVos);
				}
				else{
					selectedMailBagVos.addAll(containerDetailsMap.get(containerDetails.getContainerno()).getMailDetails());
				}
				containerDetailsMap.get(containerDetails.getContainerno()).setMailDetails(selectedMailBagVos);
				selectedContainerVos.add(containerDetailsMap.get(containerDetails.getContainerno()));

			}
		}
		
		for(ContainerDetailsVO containerVO:selectedContainerVos){
			
			ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerVO.getDsnVOs();
			Collection<MailbagVO> mailbagVOs= containerVO.getMailDetails();
			containerVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false)); 
			for(MailbagVO mail: mailbagVOs){
				DSNVO dsnvo=new DSNVO();
				for(DSNVO dsnvoIterate:dsnVos){
					if(mail.getMailSequenceNumber()==dsnvoIterate.getMailSequenceNumber()){
						dsnvo=dsnvoIterate;
						break;
					}
				}
				String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).
						append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).
							toString();
				String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).
						append(mail.getFromFlightSequenceNumber()).
								append(mail.getFromFightNumber()).toString();
				
				if(!Flight1.equals(Flight2)){
					if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())||
							MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
									MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())) && 
										logonAttributes.getAirportCode().equals(mail.getScannedPort())){
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
						return;
					}
				}
				
				/*if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())){
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
					return;
				}*/  
				
				if(dsnvo.getReceivedBags()>0 && dsnvo.getReceivedWeight().getRoundedSystemValue()>0.0){
					if((dsnvo.getPrevReceivedBags()==dsnvo.getReceivedBags())&&(dsnvo.getPrevReceivedWeight()==dsnvo.getReceivedWeight())){
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.despatchalreadydelivered"));
					return;
					}
				}
	
			}
			
		}
		
		for (ContainerDetailsVO containerDetailsVO : selectedContainerVos) {     

			HashMap<String, DSNVO> dsnMapMailbag = new HashMap<String, DSNVO>();
			ArrayList<DSNVO> mainDSNVOs = (ArrayList<DSNVO>) containerDetailsVO.getDsnVOs();
			if (containerDetailsVO.getDsnVOs() == null || containerDetailsVO.getDsnVOs().size() == 0) {
				containerDetailsVO.setOperationFlag("U");
			}
			
			for(DSNVO dsnVO:mainDSNVOs ){
				String dsnpk = dsnVO.getOriginExchangeOffice()
				           +dsnVO.getDestinationExchangeOffice()
				           +dsnVO.getMailCategoryCode()
				           +dsnVO.getMailSubclass()
				           +dsnVO.getDsn()
				           +dsnVO.getYear();
						 dsnMapMailbag.put(dsnpk,dsnVO);
			}
			
			Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
			 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
				for(DespatchDetailsVO despatchVO:despatchDetailsVOs){

					String despatchpk = despatchVO.getOriginOfficeOfExchange()
					           + despatchVO.getDestinationOfficeOfExchange()
							   + despatchVO.getMailCategoryCode()
					           + despatchVO.getMailSubclass()
					           + despatchVO.getDsn()
					           + despatchVO.getYear();
					
					DSNVO dsnVO = dsnMapMailbag.get(despatchpk);
					if(dsnVO != null) {
						if(despatchVO.getReceivedBags() == 0){
							if(!"I".equals(despatchVO.getOperationalFlag())){
								despatchVO.setOperationalFlag("U");
					    	}
							if(!"I".equals(dsnVO.getOperationFlag())){
					    		dsnVO.setOperationFlag("U"); 
					    	}
							if(!"I".equals(containerDetailsVO.getOperationFlag())){ 
								containerDetailsVO.setOperationFlag("U");
								containerDetailsVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false));
								
					    	}
							dsnVO.setReceivedBags(dsnVO.getReceivedBags()
									 + (despatchVO.getAcceptedBags() - despatchVO.getReceivedBags()));
							
							Measure despWt;
							try {
								despWt = Measure.subtractMeasureValues(despatchVO.getAcceptedWeight(),  despatchVO.getReceivedWeight());
								try {
									dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(), despWt));
								} catch (UnitException e) {
									log.log(Log.SEVERE,"UnitException", e.getMessage());
								}
							} catch (UnitException e) {
								log.log(Log.SEVERE,"UnitException", e.getMessage());
							}
							
							despatchVO.setReceivedBags(despatchVO.getAcceptedBags());
							despatchVO.setReceivedWeight(despatchVO.getAcceptedWeight());
							despatchVO.setReceivedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						}
						
						dsnMapMailbag.put(despatchpk,dsnVO);
					}
				}
			 }
			 
			Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
			
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbagVO : mailbagVOs) {

					String mailpk = mailbagVO.getOoe() + mailbagVO.getDoe() + mailbagVO.getMailCategoryCode()
							+ mailbagVO.getMailSubclass() + mailbagVO.getDespatchSerialNumber() + mailbagVO.getYear();

					DSNVO dsnVO = dsnMapMailbag.get(mailpk);

					if (dsnVO != null) {

						if (!"Y".equals(mailbagVO.getArrivedFlag())) {
							if (!"I".equals(mailbagVO.getOperationalFlag())) {
								mailbagVO.setOperationalFlag("U");
							}
							if (!"I".equals(dsnVO.getOperationFlag())) {
								dsnVO.setOperationFlag("U");
							}
							if (!"I".equals(containerDetailsVO.getOperationFlag())) {
								containerDetailsVO.setOperationFlag("U");
								containerDetailsVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false));
							}

							/*
							 * Commented for absence of the embargo check
							 * 
							 */

							/*
							 * if(embargoMailMap.size() > 0 &&
							 * embargoMailMap.containsKey(mailbagVO.getMailbagId
							 * ())){ mailbagVO.setArrivedFlag("N"); } else{
							 */
							mailbagVO.setArrivedFlag("Y");
							// }

							if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())){
							mailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);	
							}
							mailbagVO.setScannedPort(logonAttributes.getAirportCode());
							mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
							mailbagVO.setScannedDate(
									new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
							mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
							// screen id not set
							dsnVO.setReceivedBags(dsnVO.getReceivedBags() + 1);
							try {
								dsnVO.setReceivedWeight(
										Measure.addMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
							} catch (UnitException e) {
								log.log(Log.SEVERE, "UnitException", e.getMessage());
							}

						}
						dsnMapMailbag.put(mailpk, dsnVO);
					}
			
					if(mailinboundModel.getTransactionLevel()!=null && !MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) { 
						mailbagVO.setTransactionLevel(mailinboundModel.getTransactionLevel());
					}
				
				}
			}
			
			Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
			for(String key:dsnMapMailbag.keySet()){
				DSNVO dsnVO = dsnMapMailbag.get(key);
				newDSNVOs.add(dsnVO);
			}

			ArrayList<DSNVO> oldDSNVOs = (ArrayList<DSNVO>) containerDetailsVO.getDsnVOs();
			int accBags = 0;
			double accWgt = 0;
			int rcvedBags = 0;
			double rcvedWgt = 0;
			if(newDSNVOs != null && newDSNVOs.size() > 0){
				for(DSNVO dsnVO1:newDSNVOs){
					String outerpk = dsnVO1.getOriginExchangeOffice()
					   +dsnVO1.getDestinationExchangeOffice()
					   +dsnVO1.getMailCategoryCode()
					   +dsnVO1.getMailSubclass()
			           +dsnVO1.getDsn()+dsnVO1.getYear();
					int flag = 0;

						for(DSNVO dsnVO2:oldDSNVOs){

							String innerpk = dsnVO2.getOriginExchangeOffice()
							   +dsnVO2.getDestinationExchangeOffice()
							   +dsnVO2.getMailCategoryCode()
							   +dsnVO2.getMailSubclass()
					           +dsnVO2.getDsn()+dsnVO2.getYear();
							if(outerpk.equals(innerpk)){
								if(!"I".equals(dsnVO2.getOperationFlag())){
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
					if(flag == 1){
						flag = 0;
					}
				}
			}

			if(oldDSNVOs != null && oldDSNVOs.size() > 0){
				for(DSNVO dsnVO : oldDSNVOs){
					if(dsnVO.getReceivedBags() > 0) {
						rcvedBags = rcvedBags + dsnVO.getReceivedBags();
						rcvedWgt = rcvedWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();
					}
				}
			}

			containerDetailsVO.setReceivedBags(rcvedBags);
			Measure wgt=new Measure(UnitConstants.MAIL_WGT, rcvedWgt);
			containerDetailsVO.setReceivedWeight(wgt);

			
		}
		

		
		
		
		
		if(selectedContainerVos != null && selectedContainerVos.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:selectedContainerVos){
    			containerDtlsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
    		}
    	}
		
		mailArrivalVO.setContainerDetails(selectedContainerVos);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setMailSource("MTK064");
		// Mail source not set in mailarrival
		if(!mailinboundModel.isScreenWarning()&&doSecurityScreeningValidations(mailArrivalVO,actionContext,logonAttributes)){
			return;  
		}
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);

		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		
		try {
			new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO, locks);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		
		if (CollectionUtils.isNotEmpty(embargoErrors)) {
    		for(ErrorVO error: embargoErrors){
    			actionContext.addError(error);
    			}
    		return;
		}
		
		ArrayList<MailinboundModel> result=
				new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
	    responseVO.setStatus("success");
	    if (CollectionUtils.isNotEmpty(embargoErrors)) {
	    	for(ErrorVO error: embargoErrors){
    			actionContext.addError(error);
    			}
	    }else{
	    ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error); 
	    }
	    actionContext.setResponseVO(responseVO);
	}
	
	 private Collection<LockVO> prepareLocksForSave(
	    		MailArrivalVO mailArrivalVO) {
	    	log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
			LogonAttributes logonAttributes =  getLogonAttribute();
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
	    		    			//lock.setScreenId(SCREEN_ID);
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
	 
	 /**
		 * @author A-8353
		 * @param mailbagVO
		 * @param mailAcceptanceVO
		 * @param flightCarrierFlag
		 * @param actionContext 
		 * @param logonAttributes 
		 * @param errors 
		 * @param outboundModel 
		 * @param warningMap 
		 * @throws BusinessDelegateException 
		 */
		private boolean doSecurityScreeningValidations(MailArrivalVO mailArrivalVO, 
				ActionContext actionContext, LogonAttributes logonAttributes) throws BusinessDelegateException {
				for(ContainerDetailsVO containerDetailsVO:mailArrivalVO.getContainerDetails()){
					if(containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()){
						for(MailbagVO mailbagVo:containerDetailsVO.getMailDetails()){
							SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
							Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
							securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
						securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ARRIVED);
							securityScreeningValidationFilterVO.setFlightType(mailArrivalVO.getFlightType());
							securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
							securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
							securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
							securityScreeningValidationFilterVO.setMailbagId(mailbagVo.getMailbagId());
							securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
							if (securityScreeningValidationVOs!=null &&! securityScreeningValidationVOs.isEmpty()){
								for( SecurityScreeningValidationVO securityScreeningValidationVO:securityScreeningValidationVOs){
									if( checkForWarningOrError(mailbagVo, actionContext, securityScreeningValidationVO)){
										return true;
									}
								}
							}
						}
					}
				}

			return false;
		}
		/**
		 * @author A-8353
		 * @param mailbagVO
		 * @param actionContext
		 * @param existigWarningMap
		 * @param securityScreeningValidationVO
		 * @return
		 */
		private boolean checkForWarningOrError(MailbagVO mailbagVO, ActionContext actionContext, SecurityScreeningValidationVO securityScreeningValidationVO) {
			if ("W".equals(securityScreeningValidationVO
					.getErrorType())) {
				List<ErrorVO> warningErrors = new ArrayList<>();
				ErrorVO warningError = new ErrorVO(
						SECURITY_SCREENING_WARNING,
						new Object[]{mailbagVO.getMailbagId()});
				warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
				warningErrors.add(warningError);
				ResponseVO responseVO = new ResponseVO();
				responseVO.setStatus("security");
				actionContext.setResponseVO(responseVO);
				actionContext.addAllError(warningErrors); 
				return true;
			}
			if ("E".equals(securityScreeningValidationVO
					.getErrorType())) {
				actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
						new Object[]{mailbagVO.getMailbagId()}));
				ResponseVO responseVO = new ResponseVO();
				responseVO.setStatus("security");
				actionContext.setResponseVO(responseVO);

				return true;
			}
			return false;
	    }
	

}
