package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.TransferDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.TransferMailCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	25-Nov-2018		:	Draft
 */
public class TransferMailCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	private Log log = LogFactory.getLogger("OPERATIONS TransferMailCommand");
	private CommodityValidationVO commodityValidationVO = null;
	 private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
	 private static final String AUTOARRIVALENABLEDPAS="mail.operations.autoarrivalenabledPAs";
	 private static final String FUNPNTS_TRA  = "TRA";
	 private static final String OPERATION_FLAG_INSERT = "I";
	 private static final String OPERATION_FLAG_UPDATE = "U";
	 private static final String EMBARGO_EXISTS = "Embargo Exists";
	 private static final String EMBARGO_EXIST="mail.operations.err.embargoexists";
	 private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	 private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";  
	 private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		
		this.log.entering("TransferMailCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		TransferDetails transferDetails = 
				(TransferDetails) mailinboundModel.getTransferDetails();
		OperationalFlightVO operationalFlightVO=null;
		MailArrivalVO mailArrivalVO=null;
		Collection<ErrorVO> errors = null;
		ContainerDetailsVO containerDetailsVO=
				new ContainerDetailsVO();
		ContainerDetails containerDetails=mailinboundModel.getContainerDetail();
		ArrayList<DSNDetails> dsnDetailsCollection=
				containerDetails.getDsnDetailsCollection();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		OperationalFlightVO flightVO=
				new OperationalFlightVO();
		ContainerDetailsVO detailsVO=  
				new ContainerDetailsVO();

		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		//Added by A-7540
		MailArrivalVO newMailArrivalVo = mailinboundModel.getMailArrivalVO();
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		//systemParameters.add(AUTOARRIVALENABLEDPAS);
		boolean enableAutoArrival = false;

		Collection<ErrorVO> embargoErrors = new ArrayList<>();
		MailArrivalVO mailArrivalDetailsVO = (MailArrivalVO)actionContext.getAttribute("mailArrivalDetails");
		List<ErrorVO> existingerrors = actionContext.getErrors();
		if(CollectionUtils.isNotEmpty(existingerrors) && CollectionUtils.isNotEmpty(mailArrivalDetailsVO.getEmbargoDetails()))
	    {
	    	  Iterator errorsList = existingerrors.iterator();
	    	  while (errorsList.hasNext()) {
	        	  ErrorVO errorVO = (ErrorVO)(errorsList).next();
	        	  if (EMBARGO_EXIST.equals(errorVO.getErrorCode()))
		          {
	        		  if(MailConstantsVO.INFO.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
		        		  errorsList.remove();
		        		  ErrorVO displayErrorVO = new ErrorVO(EMBARGO_EXISTS);
		  	  			  displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
		  	  			  embargoErrors.add(displayErrorVO);
		  	  			  mailinboundModel.setEmbargoInfo(true);
	        		}
	        	}
	    	 }
	    }else if(CollectionUtils.isNotEmpty(existingerrors)) {
	          return;
	    }
			
		
	Map<String, String> systemParameterMap=null;
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException businessDelegateException) {
			Collection<ErrorVO> errorVOs = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"");
		}	
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		
		if(null!=mailArrivalVO){
			mailArrivalVO.setArrivedUser(logonAttributes.getUserId());
			mailArrivalVO.setMailSource("MTK064");
			for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVO.getContainerDetails()){
				if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
					containerDetailsVO=containerDetailsVOIterate;
					if(!OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())){ 
						containerDetailsVO.setOperationFlag(OPERATION_FLAG_UPDATE);
					}
					break;
							
				}
			}
			dsnDetailsMap.clear();
			if(null!=dsnDetailsCollection&&dsnDetailsCollection.size()>0){
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice()).append(dsnvo.getMailClass())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
				selectedDsnVos.clear();
				for(DSNDetails dsnDetails:containerDetails.getDsnDetailsCollection()){
					String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
							.append(dsnDetails.getDestinationExchangeOffice()).append(dsnDetails.getMailClass())
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
					//Added by A-7540
					mailbagVO.setInventoryContainer(containerDetailsVO.getContainerNumber());
					mailbagVO.setInventoryContainerType(containerDetailsVO.getContainerType());

					mailDetailsMap.put(mailKey, mailbagVO);	
				}
				selectedMailBagVos.clear();
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					String sysparfunctionpoints = null;
					//String autoArrEnabledPAs= null;
					
					if (systemParameterMap != null) {
						sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
						//autoArrEnabledPAs=systemParameterMap.get(AUTOARRIVALENABLEDPAS);
					}
					if(sysparfunctionpoints!=null &&
							sysparfunctionpoints.contains(FUNPNTS_TRA)
							){
						enableAutoArrival = true;						  
					}
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
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
				Collection<DSNVO> dsnvos= new LinkedHashSet <DSNVO>();//To remove dulplicates from selectedDsnVos
				dsnvos.addAll(selectedDsnVos);
				selectedDsnVos.clear();
				selectedDsnVos.addAll(dsnvos);
				containerDetailsVO.setDsnVOs(selectedDsnVos);
				containerDetailsVO.setMailDetails(selectedMailBagVos);
				
			}
		
		}
		ContainerDetailsVO containerDetailsToBeSaved=null;
		
		//if("FLIGHT".equals(transferDetails.getReassignedto())){  //Commented as part of IASCB-34119
			/*ContainerDetails containerDetailsToBeSaved=
					transferDetails.getContainerDetails();*/
		if(transferDetails.getContainerDetailsVO()!=null) {   //Added as part of IASCB-34119
			containerDetailsToBeSaved=transferDetails.getContainerDetailsVO();  	
		}	
		if("FLIGHT".equals(transferDetails.getReassignedto())){  //Added as part of IASCB-34119
		MailinboundDetails flightDetails = 
				(MailinboundDetails)transferDetails.getMailinboundDetails();
		
			
		flightVO= 
				MailInboundModelConverter.constructOperationalFlightVo(flightDetails, logonAttributes);
			/*MailArrivalVO mailArrivalVoToBeSaved=
				new MailArrivalVO();
		try {
			mailArrivalVoToBeSaved = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(flightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(mailArrivalVoToBeSaved!=null){
			if(mailArrivalVoToBeSaved.getContainerDetails()!=null){
				for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVoToBeSaved.getContainerDetails()){
					if(containerDetailsVOIterate.getContainerNumber().equals(containerDetailsToBeSaved.getContainerno())){
						detailsVO=containerDetailsVOIterate;
					}
				}
			}
		}
		
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
			
			}*/
		}
		
		ContainerVO containerVO=new ContainerVO();
		String mailCommidityCode = null;
		Collection<String> commodites = new ArrayList<String>();
		Collection<String> codes = new ArrayList<String>();
    	codes.add(MAIL_COMMODITY_SYS);
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	
    	if(results != null && results.size() > 0) {
    		mailCommidityCode = results.get(MAIL_COMMODITY_SYS);
    	}
    	
    	if(mailCommidityCode!=null && mailCommidityCode.trim().length()>0) {
			commodites.add(mailCommidityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(logonAttributes.getCompanyCode(), commodites);
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}
			if(densityMap !=null && densityMap.size()>0){
				commodityValidationVO = densityMap.get(mailCommidityCode);
				log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
			}
		}
    	
    	errors = new ArrayList<ErrorVO>();
    	if(transferDetails.getScanDate()==null && ("").equals(transferDetails.getScanTime())){
			actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
 	   		return; 
		}
		if(transferDetails.getScanTime()==null ||("").equals(transferDetails.getScanTime())){
			actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
 	   		return; 
		}
		
		String scanDate= new StringBuilder().append(transferDetails.getScanDate()).append(" ").
				append(transferDetails.getScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
    	if("FLIGHT".equals(transferDetails.getReassignedto())){
    		
    		 Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
    		 try {
				  containerVOs = new MailTrackingDefaultsDelegate().findFlightAssignedContainers(flightVO);  
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		actionContext.addAllError((List<ErrorVO>) errors);
	    		return;
	    	  }
	    	  for(ContainerVO containerVO2:containerVOs){
	    		  if(containerVO2.getContainerNumber()
	    				  .equals(containerDetailsToBeSaved.getContainerNumber())){
	    			  containerVO=containerVO2;
	    			  break;
	    		  }
	    	  }
    		

			containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			containerVO.setOperationTime(scanDat);
                if(MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())){    
                	actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
	   				new Object[]{containerVO.getContainerNumber()}));
	  				return;
	  	
		}
    	}
    	
    	
    	String assignTo = transferDetails.getReassignedto();
    	
    	
		MailArrivalVO newMailArrivalVO=mailArrivalVO;
		ArrayList<ContainerDetailsVO> containerDetailsVOs = 
				(ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		ArrayList<MailbagVO> mailbagVOs=new ArrayList<MailbagVO>();
		ArrayList<DespatchDetailsVO> despatchDetailsVOs=new ArrayList<DespatchDetailsVO>();
		
		if("FLIGHT".equals(assignTo)){
			
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVOs){
    			if(containerDetailsVOIterate.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
    			for(DSNVO dsnVO:containerDetailsVOIterate.getDsnVOs()){
    				
    				String innerpk = dsnVO.getOriginExchangeOffice()
    						   +dsnVO.getDestinationExchangeOffice()
    						   +dsnVO.getMailCategoryCode()
    				           +dsnVO.getMailSubclass()
    				           +dsnVO.getDsn()
    				           +dsnVO.getYear();
    				
    				if (containerDetailsVOIterate.getMailDetails() != null 
    						&& containerDetailsVOIterate.getMailDetails().size() > 0) {
 					   for(MailbagVO mailbagvo:containerDetailsVOIterate.getMailDetails()){
 						   
 						  String outerpk = mailbagvo.getOoe()
 								    +mailbagvo.getDoe()
 						           	+mailbagvo.getMailCategoryCode()
 						           	+mailbagvo.getMailSubclass()
 						            +mailbagvo.getDespatchSerialNumber()
 						            +mailbagvo.getYear();
 								if(innerpk.equals(outerpk) && !("Y").equals(mailbagvo.getTransferFlag())
 										&& !("I").equals(mailbagvo.getOperationalFlag())){
 									
 									mailbagvo.setScannedPort(logonAttributes.getAirportCode());
 									mailbagvo.setScannedUser(logonAttributes.getUserId().toUpperCase());
 									mailbagvo.setScannedDate(scanDat);
 									mailbagvo.setCarrierCode(operationalFlightVO.getCarrierCode());
 									mailbagvo.setFlightNumber(operationalFlightVO.getFlightNumber());
 									mailbagvo.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());        
 									mailbagvo.setFlightDate(operationalFlightVO.getFlightDate());
 									mailbagvo.setVolume(new Measure(UnitConstants.VOLUME,
 											getScaledVolume(mailbagvo.getWeight().getRoundedDisplayValue())));
 									mailbagvo.setUbrNumber(dsnVO.getUbrNumber());
 									mailbagvo.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
 									mailbagvo.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
 									mailbagvo.setMailSource("MTK064");
 	
 									mailbagvo.setOperationalFlag(OPERATION_FLAG_UPDATE);
 									if(enableAutoArrival){
 									mailbagvo.setArrivedFlag(MailConstantsVO.FLAG_YES);
 									mailbagvo.setAutoArriveMail(MailConstantsVO.FLAG_YES);
 									}
 								
 								if(mailbagvo.getInventoryContainer()==null)   {  
									/**
									 * Error message changed as part of bug ICRD-97075
									 * Inventory container number is null for mailbags which are not arrived.
									 * Under a dsn if one mailbag is arrived and other accepted, this error msg will be thrown
									 * For transfer all mailbags shud be arrived.Changing the error message to 
									 * All mailbags in dsn should be arrived for transfer
									 * Change done by A-4809 as discussed with Santhi K
									 */

									actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.allmailbagsneedtobearrived"));  
						 	   	   	return;
								} 
 								
 								mailbagVOs.add(mailbagvo);
 								
 							}
 					   }
    				
    				}
    				
    				if (containerDetailsVOIterate.getDesptachDetailsVOs() != null 
    						&& containerDetailsVOIterate.getDesptachDetailsVOs().size() > 0) {
					    for(DespatchDetailsVO despatchDetails:containerDetailsVOIterate.getDesptachDetailsVOs()){
							String outerpk = despatchDetails.getOriginOfficeOfExchange()
					           		+despatchDetails.getDestinationOfficeOfExchange()
					           		+despatchDetails.getMailCategoryCode()
					           		+despatchDetails.getMailSubclass()
					           		+despatchDetails.getDsn()
					           		+despatchDetails.getYear();
							if(innerpk.equals(outerpk) && !("Y").equals(despatchDetails.getTransferFlag())
									&& !("I").equals(despatchDetails.getOperationalFlag())){
								despatchDetails.setCompanyCode(containerDetailsVOIterate.getCompanyCode());
								despatchDetails.setCarrierId(containerDetailsVOIterate.getCarrierId());
								despatchDetails.setFlightNumber(containerDetailsVOIterate.getFlightNumber());
								despatchDetails.setFlightSequenceNumber(containerDetailsVOIterate.getFlightSequenceNumber());
								despatchDetails.setLegSerialNumber(containerDetailsVOIterate.getLegSerialNumber());
								despatchDetails.setSegmentSerialNumber(containerDetailsVOIterate.getSegmentSerialNumber());
								despatchDetails.setContainerType(containerDetailsVOIterate.getContainerType());
								despatchDetails.setCarrierCode(operationalFlightVO.getCarrierCode());
								despatchDetails.setFlightDate(operationalFlightVO.getFlightDate());
								despatchDetails.setPrevAcceptedBags(0);
								despatchDetails.setPrevAcceptedWeight(new Measure(UnitConstants.VOLUME,0.0D));
								despatchDetails.setStatedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetails.getStatedWeight().getRoundedSystemValue())));
			    				despatchDetails.setAcceptedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetails.getAcceptedWeight().getRoundedSystemValue())));
								despatchDetails.setUbrNumber(dsnVO.getUbrNumber());
								despatchDetails.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
								despatchDetails.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());

		    				   despatchDetailsVOs.add(despatchDetails);
							}
						 }		
					  }	
    			  }
    				
    		
			
				containerVO.setFromCarrier(operationalFlightVO.getCarrierCode());
				containerVO.setFromFltNum(operationalFlightVO.getFlightNumber());

				/*String frmDate = new StringBuilder().append(operationalFlightVO.getFlightDate()).append(" ")
						.append("00:00").append(":00").toString();
				LocalDate frmDat = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				frmDat.setDateAndTime(frmDate);*/
				if(!mailinboundModel.isScreenWarning()&&doSecurityScreeningValidations(mailbagVOs,actionContext,logonAttributes,operationalFlightVO,assignTo,containerVO)){
					return;  
				}
				containerVO.setFromFltDat(operationalFlightVO.getFlightDate());
				TransferManifestVO transferManifestVO = null;

				if(enableAutoArrival){			
					autoArrivalForMailbags(mailArrivalVO);					
				}
				try {
					transferManifestVO = new MailTrackingDefaultsDelegate().transferMail(despatchDetailsVOs, mailbagVOs,
							containerVO, "Y");       
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			
		}
			}
		}
		
		else{
			
			AirlineValidationVO airlineValidationVO = null;
    		String carrier = transferDetails.getCarrier().trim().toUpperCase();   

    		if (carrier != null && !"".equals(carrier)) {        		
    			try {        			
    				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
    						logonAttributes.getCompanyCode(),carrier);

    			}catch (BusinessDelegateException businessDelegateException) {
    				errors = handleDelegateException(businessDelegateException);
    			}
    			if (errors != null && errors.size() > 0) {            			
    				Object[] obj = {carrier};
    				actionContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
    				return;
    			}
    		}
    		
    		operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    		Collection<MailbagVO> newMailbagVOs =  new ArrayList<MailbagVO>();
    		Collection<MailbagVO> tempMailbagVOs =  new ArrayList<MailbagVO>();
    		
    		ContainerVO   tocontainerVO = new ContainerVO();
    		
    		tocontainerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    		tocontainerVO.setCarrierCode(carrier);
    		tocontainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
    		tocontainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    		tocontainerVO.setOperationTime(scanDat);
    		tocontainerVO.setAssignedPort(logonAttributes.getAirportCode());
    		tocontainerVO.setCompanyCode(logonAttributes.getCompanyCode());
           //Added as part of IASCB-34119
    		if(containerDetailsToBeSaved!=null){
    			tocontainerVO = constructContainerVO(tocontainerVO,containerDetailsToBeSaved);
    			
    		}
    		//added as part of IASCB-65495
    		if(logonAttributes.getAirportCode().equals(tocontainerVO.getFinalDestination())) {
    			actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.destncurrentarp"));
			    return;
    		}
    		
    		for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVOs){
    			if(containerDetailsVOIterate.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
    			for(DSNVO dsnVO:containerDetailsVOIterate.getDsnVOs()){
    				
    				String innerpk = dsnVO.getOriginExchangeOffice()
    		    			+dsnVO.getDestinationExchangeOffice()
    		    			+dsnVO.getMailCategoryCode()
    		    			+dsnVO.getMailSubclass()
    		    			+dsnVO.getDsn()
    		    			+dsnVO.getYear();
    				
    		    	Collection<MailbagVO> totMailbagVOs = containerDetailsVOIterate.getMailDetails();
    		    	if (totMailbagVOs != null && totMailbagVOs.size() > 0) {
	    				for(MailbagVO mailbagvo:totMailbagVOs){
	    					String outerpk = mailbagvo.getOoe()
	    					+mailbagvo.getDoe()
	    					+mailbagvo.getMailCategoryCode()
	    					+mailbagvo.getMailSubclass()
	    					+mailbagvo.getDespatchSerialNumber()
	    					+mailbagvo.getYear();
	    					
	    					if(innerpk.equals(outerpk) && !("Y").equals(mailbagvo.getTransferFlag())
	    							&& !("I").equals(mailbagvo.getOperationalFlag())){
	    						mailbagvo.setCarrierCode(newMailArrivalVO.getFlightCarrierCode());
	    						mailbagvo.setCompanyCode(logonAttributes.getCompanyCode());
	    						mailbagvo.setFlightNumber(operationalFlightVO.getFlightNumber());
	    						mailbagvo.setFlightDate(operationalFlightVO.getFlightDate());
	    						mailbagvo.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
	    						mailbagvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	    						mailbagvo.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
	    						mailbagvo.setScannedPort(logonAttributes.getAirportCode());
	    						mailbagvo.setScannedUser(logonAttributes.getUserId().trim().toUpperCase());
	    						mailbagvo.setUldNumber(containerDetailsVOIterate.getContainerNumber());
	    						mailbagvo.setScannedDate(scanDat);
	    						mailbagvo.setUbrNumber(dsnVO.getUbrNumber());
	    						mailbagvo.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
	    						mailbagvo.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
	    						mailbagvo.setMailRemarks(transferDetails.getRemarks());
	    						mailbagvo.setMailSource("MTK064");
								mailbagvo.setOperationalFlag(OPERATION_FLAG_UPDATE);
								if(enableAutoArrival){
									mailbagvo.setArrivedFlag(MailConstantsVO.FLAG_YES);
									mailbagvo.setAutoArriveMail(MailConstantsVO.FLAG_YES);
								}
	    						if(mailbagvo.getInventoryContainer()==null)   
								{									/**
									 * Error message changed as part of bug ICRD-97075
									 * Inventory container number is null for mailbags which are not arrived.
									 * Under a dsn if one mailbag is arrived and other accepted, this error msg will be thrown
									 * For transfer all mailbags shud be arrived.Changing the error message to 
									 * All mailbags in dsn should be arrived for transfer
									 * Change done by A-4809 as discussed with Santhi K
									 */
									actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.allmailbagsneedtobearrived"));  
						 	   	   	return;
								}
	    						
	    						tempMailbagVOs.add(mailbagvo);
	    					}
	    				}
    		    	}
    		    	
    		    	if (containerDetailsVOIterate.getDesptachDetailsVOs() != null && containerDetailsVOIterate.getDesptachDetailsVOs().size() > 0) {
	    				for(DespatchDetailsVO despatchDetails:containerDetailsVO.getDesptachDetailsVOs()){
	    					String outerpk = despatchDetails.getOriginOfficeOfExchange()
	    					+despatchDetails.getDestinationOfficeOfExchange()
	    					+despatchDetails.getMailCategoryCode()
	    					+despatchDetails.getMailSubclass()
	    					+despatchDetails.getDsn()
	    					+despatchDetails.getYear();
	    					if(innerpk.equals(outerpk) && !("Y").equals(despatchDetails.getTransferFlag())
	    							&& !("I").equals(despatchDetails.getOperationalFlag())){
	    						despatchDetails.setCompanyCode(containerDetailsVOIterate.getCompanyCode());
	    						despatchDetails.setCarrierId(containerDetailsVOIterate.getCarrierId());
	    						despatchDetails.setFlightNumber(containerDetailsVOIterate.getFlightNumber());
	    						despatchDetails.setFlightSequenceNumber(containerDetailsVOIterate.getFlightSequenceNumber());
	    						despatchDetails.setLegSerialNumber(containerDetailsVOIterate.getLegSerialNumber());
	    						despatchDetails.setSegmentSerialNumber(containerDetailsVOIterate.getSegmentSerialNumber());
	    						despatchDetails.setContainerType(containerDetailsVOIterate.getContainerType());
	    						despatchDetails.setCarrierCode(newMailArrivalVO.getFlightCarrierCode());
								despatchDetails.setFlightDate(operationalFlightVO.getFlightDate());
	    						despatchDetails.setUbrNumber(dsnVO.getUbrNumber());
	    						despatchDetails.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
	    						despatchDetails.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
	    						despatchDetailsVOs.add(despatchDetails);		
	    					}
	    				}		
	    			}
    			}
	    		
    			if(tempMailbagVOs != null && tempMailbagVOs.size() > 0) {
	    			newMailbagVOs.addAll(tempMailbagVOs);
	    		}
    			if(!mailinboundModel.isScreenWarning()&&doSecurityScreeningValidations(newMailbagVOs,actionContext,logonAttributes,operationalFlightVO,assignTo, tocontainerVO)){
					return;  
				}
    			TransferManifestVO transferManifestVO=null; 
    			if(enableAutoArrival){
    				
    				autoArrivalForMailbags(mailArrivalVO);
    			}
    			try {
	    			transferManifestVO=new MailTrackingDefaultsDelegate().transferMail(despatchDetailsVOs,newMailbagVOs,tocontainerVO,"Y");	    			
	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		if (errors != null && errors.size() > 0) {
	    			actionContext.addAllError((List<ErrorVO>) errors);
	    			return;
	    		}
	    		
	    		//Added as part of ICRD-343290 start
	    		if (containerDetailsToBeSaved == null) {
	    			mailinboundModel.setTransferManifestVO(transferManifestVO);
	    		}
	    		
	    	/*	if(containerDetailsToBeSaved==null || null ==containerDetailsToBeSaved.getDestination())  
	    		{
	    	    mailinboundModel.setTransferManifestVO(transferManifestVO);
	    		}*/
	    	    
    		}
		}
    		
		}
    	
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
	    responseVO.setResults(result);
		responseVO.setStatus("success");
		if (CollectionUtils.isNotEmpty(embargoErrors)) {
	    	for(ErrorVO error: embargoErrors){
    			actionContext.addError(error);
    			}
	    }else{
		ErrorVO error = new ErrorVO("mail.operations.succ.transfermailsuccess");      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
		}
		actionContext.setResponseVO(responseVO);
		
		
	}
		
	/**
	 * 	For IASCB-34119
	 * @param tocontainerVO
	 * @param containerDetailsToBeSaved
	 * @return
	 */
		
		
	private ContainerVO constructContainerVO(ContainerVO tocontainerVO, ContainerDetailsVO containerDetailsToBeSaved) {
		
		tocontainerVO.setContainerNumber(containerDetailsToBeSaved.getContainerNumber());
		tocontainerVO.setType(containerDetailsToBeSaved.getContainerType());
		tocontainerVO.setRemarks(containerDetailsToBeSaved.getRemarks());
		tocontainerVO.setPaBuiltFlag(containerDetailsToBeSaved.getPaBuiltFlag());	
		tocontainerVO.setFlightNumber(containerDetailsToBeSaved.getFlightNumber());
		tocontainerVO.setFlightSequenceNumber(containerDetailsToBeSaved.getFlightSequenceNumber());
		tocontainerVO.setSegmentSerialNumber(containerDetailsToBeSaved.getSegmentSerialNumber());		
		tocontainerVO.setLegSerialNumber(containerDetailsToBeSaved.getLegSerialNumber());			
		tocontainerVO.setPou(containerDetailsToBeSaved.getPou());
		tocontainerVO.setPol(containerDetailsToBeSaved.getPol());		
		tocontainerVO.setOffloadFlag(containerDetailsToBeSaved.getOffloadFlag());
		tocontainerVO.setArrivedStatus(containerDetailsToBeSaved.getArrivedStatus());		
		tocontainerVO.setTransferFlag(containerDetailsToBeSaved.getTransferFlag());		
		tocontainerVO.setFlightStatus(containerDetailsToBeSaved.getFlightStatus());
		tocontainerVO.setDeliveredStatus(containerDetailsToBeSaved.getDeliveredStatus());
		tocontainerVO.setIntact(containerDetailsToBeSaved.getIntact());	
		tocontainerVO.setTransitFlag(containerDetailsToBeSaved.getTransitFlag());
		tocontainerVO.setReleasedFlag(containerDetailsToBeSaved.getReleasedFlag());	
		tocontainerVO.setContentId(containerDetailsToBeSaved.getContentId()); 		
		tocontainerVO.setFinalDestination(containerDetailsToBeSaved.getDestination());
		//tocontainerVO.setBags(containerDetailsToBeSaved.getBa);
		tocontainerVO.setAssignedUser(containerDetailsToBeSaved.getAssignedUser());
		tocontainerVO.setFlightDate(containerDetailsToBeSaved.getFlightDate());  			  			   		
		tocontainerVO.setAssignedDate(containerDetailsToBeSaved.getAssignedDate());
		tocontainerVO.setActualWeight(containerDetailsToBeSaved.getActualWeight());
		tocontainerVO.setLastUpdateTime(containerDetailsToBeSaved.getLastUpdateTime()); 
		tocontainerVO.setULDLastUpdateTime(containerDetailsToBeSaved.getUldLastUpdateTime());
		return tocontainerVO;
	}
		
		
		
		

	/**
	 * @author A-7540
	 * @param mailArrivalVO
	 * 
	 */
	private void autoArrivalForMailbags(MailArrivalVO mailArrivalVO)
			{
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ErrorVO> errors = null;
		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		  try {
			    new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO,locks);
	          }catch (BusinessDelegateException businessDelegateException) {
	        	  errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		 // actionContext.addAllError((List<ErrorVO>) errors);
	    		  //mailinboundModel.setMailArrivalVO(mailArrivalVO);										    		
	    		return ;
	    	  }		
	}
	private double getScaledVolume(double value) {
    	double volume = 0.0;
    	
    	if(commodityValidationVO != null && commodityValidationVO.getDensityFactor() > 0) {
    		volume=value / commodityValidationVO.getDensityFactor();
    		
    		if(volume < 0.01){
				volume = 0.01;
			   }
    		   		   		
    	}
    	return volume;
    }
	 /**
		 * @author A-8353
		 * @param mailbagVO
		 * @param mailAcceptanceVO
		 * @param flightCarrierFlag
		 * @param actionContext 
		 * @param logonAttributes 
	 * @param operationalFlightVO 
	 * @param assignTo 
	 * @param containerVO 
		 * @param errors 
		 * @param outboundModel 
		 * @param warningMap 
		 * @throws BusinessDelegateException 
		 */
		private boolean doSecurityScreeningValidations(Collection<MailbagVO> newMailbagVOs, 
				ActionContext actionContext, LogonAttributes logonAttributes,OperationalFlightVO operationalFlightVO, String assignTo, ContainerVO containerVO) throws BusinessDelegateException {
					if(newMailbagVOs != null && !newMailbagVOs.isEmpty()){
						for(MailbagVO mailbagVo:newMailbagVOs){
							SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
							Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
							securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
					if("Flight".equalsIgnoreCase(assignTo)){
							securityScreeningValidationFilterVO.setApplicableTransaction("ASG");
							securityScreeningValidationFilterVO.setTransistAirport(containerVO.getPou());
							}
							else{
								securityScreeningValidationFilterVO.setApplicableTransaction("ACP");	
							}
					        securityScreeningValidationFilterVO.setAppRegValReq(true);
							securityScreeningValidationFilterVO.setFlightType(operationalFlightVO.getFlightType());
							securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
							securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
							securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
							securityScreeningValidationFilterVO.setMailbagId(mailbagVo.getMailbagId());
							securityScreeningValidationFilterVO.setSubClass(mailbagVo.getMailSubclass());
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
				if ("AR".equals(securityScreeningValidationVO
						.getValidationType())){
					actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
				}
				else{
				actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
						new Object[]{mailbagVO.getMailbagId()}));
				}
				ResponseVO responseVO = new ResponseVO();
				responseVO.setStatus("security");
				actionContext.setResponseVO(responseVO);

				return true;
			}
			return false;
		}
	
}
