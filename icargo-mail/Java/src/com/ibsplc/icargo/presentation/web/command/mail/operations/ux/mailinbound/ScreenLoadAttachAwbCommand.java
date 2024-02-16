package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ScreenLoadAttachAwbCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	30-Oct-2018		:	Draft
 */
public class ScreenLoadAttachAwbCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String ALREADY_ATTACHED="mailtracking.defaults.attachawb.msg.err.mailbagsalreadyattached";
	private static final String DIFFERENT_ORG_DST="mailtracking.defaults.attachawb.msg.err.differentairports";
	private static final String ALLOW_NON_STANDARD_AWB = "operations.shipment.allownonstandardawb";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String WEIGHT_CODE = "shared.defaults.weightUnitCodes";
	private static final String CATEGORY_CODE = "mailtracking.defaults.mailcategory";
	private static final String CONTAINER_TYPE = "mailtracking.defaults.containertype";
	private static final String STNPAR_DEFUNIT_WEIGHT  = "station.defaults.unit.weight" ;
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ScreenLoadAttachAwbCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ContainerDetails containerDetails =null;
		Collection<DSNVO> dsnVos=null; 
		ArrayList<ContainerDetails> containerDetailsCollection= 
				 mailinboundModel.getContainerDetailsCollection();
		containerDetails = 
					containerDetailsCollection.get(0); 
		AttachAwbDetails attachAwbDetails=
				new AttachAwbDetails();
		Collection<MailbagVO> mailbagVOs=null;
		Collection<MailbagVO> selectedMailbagVos=null;
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				containerDetails.getMailBagDetailsCollection();
		ArrayList<DSNDetails> dsnDetailsCollection=
				containerDetails.getDsnDetailsCollection();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		ContainerDetailsVO containerDetailsVO=
				new ContainerDetailsVO();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		boolean isMailbagAction=false;
		
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		   
//added by a-7779 for IASCB-37324- commented the changes asper Soncy's comment in IASCB-37324 
	/*	if(mailBagDetailsCollection!=null && mailBagDetailsCollection.size()>0){
			for(MailBagDetails mailBagDetails : mailBagDetailsCollection){
				if("Delivered".equals(mailBagDetails.getMailBagStatus())){
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydeliveredfortransfer"));
					return; 
				}
			}
		}*/
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
		if(containerDetailsVos!=null){
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVos){
				if(containerDetailsVOIterate.getContainerNumber().equals(containerDetails.getContainerno())){
					containerDetailsVO=containerDetailsVOIterate;
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
				dsnVos=selectedDsnVos;
				containerDetailsVO.setDsnVOs(selectedDsnVos);  
				mailbagVOs=containerDetailsVO.getMailDetails();
			}
			else if(null!=mailBagDetailsCollection&&mailBagDetailsCollection.size()>0){ 
				mailDetailsMap.clear();
				isMailbagAction=true;
				for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
					String mailKey=mailbagVO.getMailbagId();
					mailDetailsMap.put(mailKey, mailbagVO);	
				}
				selectedMailBagVos.clear();
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
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
				Collection<DSNVO> dsnvos= new LinkedHashSet <DSNVO>();//To remove dulplicates from selectedDsnVos
				dsnvos.addAll(selectedDsnVos);
				selectedDsnVos.clear();
				selectedDsnVos.addAll(dsnvos);
				containerDetailsVO.setDsnVOs(selectedDsnVos); 
				dsnVos=selectedDsnVos;
				mailbagVOs=selectedMailBagVos;
				
			}
			else{ 
				dsnVos=containerDetailsVO.getDsnVOs();
				mailbagVOs=containerDetailsVO.getMailDetails();
			}
		
			
			
		}  
		
		
	
		if(dsnVos!=null && dsnVos.size()>0){
			boolean isValid=true;
			if(!isMailbagAction)
				isValid = isAlreadyAttached(dsnVos);
			else{
				if (mailbagVOs != null && mailbagVOs.size() > 0) { 
					for(MailbagVO mailbagVO:mailbagVOs){
						if (mailbagVO.getDocumentNumber() != null
								&& mailbagVO.getSequenceNumber() > 0) {
							isValid=false;
							break; 
						}
					}
				} 
			}
			if (!isValid) {
				actionContext.addError(new ErrorVO(ALREADY_ATTACHED));
				return;
			}
			
			isValid=validateOriginDestination(dsnVos,logonAttributes);
			if (!isValid) {
				actionContext.addError(new ErrorVO(DIFFERENT_ORG_DST));
				return;
			}
			
			Collection<String> parameterTypes = new ArrayList<String>();
			parameterTypes.add(STOCK_REQ_PARAMETER);
			parameterTypes.add(MailConstantsVO.SHIPMENTDESCRIPTION_FORAWB);
			parameterTypes.add(MAIL_COMMODITY_SYS);
			parameterTypes.add(ALLOW_NON_STANDARD_AWB);
			
			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add(WEIGHT_CODE);
			fieldTypes.add(CATEGORY_CODE);
			fieldTypes.add(CONTAINER_TYPE);
			fieldTypes.add(MAIL_STATUS);
			
			errors= null;
			Map<String, String> parameters = null;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	    	try {
	    		parameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);

	    	}catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
			}
	    	
	    	Map<String,String> parametersMap =(HashMap<String, String>)parameters;
	    	attachAwbDetails.setSystemParameters((HashMap<String, String>) parametersMap);
	    	if(parametersMap!=null && parametersMap.size()>0){
				   attachAwbDetails.setShipmentDescription(parametersMap.get(MailConstantsVO.SHIPMENTDESCRIPTION_FORAWB));
				   log.log(Log.FINE, "The Shipment Description for  the AWB",
						   attachAwbDetails.getShipmentDescription());
				   
			 }
	    	
	    	String cmpcod = logonAttributes.getCompanyCode();
			String commodityCode=parametersMap.get(MAIL_COMMODITY_SYS);
			Collection<String> commodites = new ArrayList<String>();
			if(commodityCode!=null && commodityCode.trim().length()>0) {
				commodites.add(commodityCode);
				Map<String,CommodityValidationVO> densityMap = null;
				CommodityDelegate  commodityDelegate = new CommodityDelegate();
				
				try {
					densityMap = commodityDelegate.validateCommodityCodes(cmpcod, commodites);
				} catch (BusinessDelegateException e) {
					e.getMessage();
				}

				if(densityMap !=null && densityMap.size()>0){
					CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
					log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
					attachAwbDetails.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
					attachAwbDetails.setShipmentDescription(commodityValidationVO.getCommodityDesc());
				}
				
				
			}
			
			int bags = 0; 
			double bagWts = 0;
			String originOE=null;
			String agentCode=null;
			if(!isMailbagAction){
				if (dsnVos != null && dsnVos.size() > 0) {
					for (DSNVO dsnVO : dsnVos) {
						originOE=dsnVO.getOriginExchangeOffice();
						if (mailbagVOs != null && mailbagVOs.size() > 0) {
							selectedMailbagVos = constructSelectedMailbagVOs(mailbagVOs, dsnVO);
							if (selectedMailbagVos != null && selectedMailbagVos.size() > 0) {
								bags = bags + selectedMailbagVos.size();
								for(MailbagVO mailbagVO:selectedMailbagVos)
									bagWts = bagWts + 
											mailbagVO.getWeight().getRoundedSystemValue();	  
								Collection<String> officeOfExchanges=new ArrayList<String>();
								Collection<ArrayList<String>> groupedOECityArpCodes = null;
								String destOE=dsnVO.getDestinationExchangeOffice();
								officeOfExchanges.add(originOE);
								officeOfExchanges.add(destOE); 
								try {
									groupedOECityArpCodes=new MailTrackingDefaultsDelegate()     
											.findCityAndAirportForOE(logonAttributes.getCompanyCode(), officeOfExchanges);
								} catch (BusinessDelegateException businessDelegateException) {
									handleDelegateException(businessDelegateException); 
								}
								if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
									for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {  
										if(cityAndArpForOE.size() == 3) {
											if(originOE != null && originOE.length() > 0 && originOE.equals(cityAndArpForOE.get(0))) {
												attachAwbDetails.setOrigin(cityAndArpForOE.get(2)); 
											}
											if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
												attachAwbDetails.setDestination(cityAndArpForOE.get(2)); 
											}
										} 
									}
								}
							}
						}
					}
				}
			}
			else{
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					selectedMailbagVos = mailbagVOs;
					if (selectedMailbagVos != null && selectedMailbagVos.size() > 0) {
						originOE=selectedMailbagVos.iterator().next().getOoe();
						bags = bags + selectedMailbagVos.size();
						for(MailbagVO mailbagVO:selectedMailbagVos)
							bagWts = bagWts + 
									mailbagVO.getWeight().getRoundedSystemValue();	  
						Collection<String> officeOfExchanges=new ArrayList<String>();
						Collection<ArrayList<String>> groupedOECityArpCodes = null;
						String destOE=selectedMailbagVos.iterator().next().getDoe();
						officeOfExchanges.add(originOE);
						officeOfExchanges.add(destOE); 
						try {
							groupedOECityArpCodes=new MailTrackingDefaultsDelegate()     
									.findCityAndAirportForOE(logonAttributes.getCompanyCode(), officeOfExchanges);
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException); 
						}
						if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
							for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {  
								if(cityAndArpForOE.size() == 3) {
									if(originOE != null && originOE.length() > 0 && originOE.equals(cityAndArpForOE.get(0))) {
										attachAwbDetails.setOrigin(cityAndArpForOE.get(2)); 
									}
									if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
										attachAwbDetails.setDestination(cityAndArpForOE.get(2)); 
									}
								} 
							}
						}
					}
				}
			}
			attachAwbDetails.setStandardPieces(Integer.toString(bags));
			AreaDelegate areaDelegate = new AreaDelegate();  
			Collection<String> parameterCodes = new ArrayList<String>();
			String stationCode = logonAttributes.getStationCode();	
	        Map stationParameters = null; 
			//List<Mailbag> selectedMailbags= MailOutboundModelConverter.constructMailbagDetails(selectedMailbagsVOs, containerDetailsVO);
			parameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
			try {
				stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
			} catch (BusinessDelegateException e1) {
				e1.getMessage();
			} 
			String stationWeightUnit = (String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT ); 
			attachAwbDetails.setStdWeightMeasure(new Measure(UnitConstants.MAIL_WGT,0.0
					,bagWts,stationWeightUnit));
			attachAwbDetails.setStandardWeight(attachAwbDetails.getStdWeightMeasure().getRoundedDisplayValue());
			agentCode=findAgentCodeForPA(logonAttributes.getCompanyCode(),originOE);
			attachAwbDetails.setAgentCode(agentCode);
			
			attachAwbDetails.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
			//containerDetails.setMailBagDetailsCollection(selectedMailBagDetailsCollection);
			ArrayList<ContainerDetails> containerDetailsCollectionToSave=
					new ArrayList<ContainerDetails>();
			containerDetailsCollectionToSave.add(containerDetails);
			mailinboundModel.setContainerDetailsCollection(containerDetailsCollectionToSave);
			mailinboundModel.setAttachAwbDetails(attachAwbDetails);
			ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
			result.add(mailinboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
	    	log.exiting("ScreenLoadAttachAwbCommand","execute");
	    	return;
	    			
		}
		
		actionContext.addError(new ErrorVO("mail.operations.err.mailbagnotfound"));                         
		return;       

		
		
		
	}


	private String findAgentCodeForPA(String companyCode, String originOE) {
		 String paCode = null;
			String agentCode = null;
			if (originOE != null && originOE.trim().length() > 0) {
				try {
					paCode = new MailTrackingDefaultsDelegate()
							.findPAForOfficeOfExchange(companyCode, originOE);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
			}
			if (paCode != null && paCode.trim().length() > 0) {
				try {
					agentCode = new MailTrackingDefaultsDelegate()
							.findAgentCodeForPA(companyCode, paCode);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
			}

			return agentCode;
	}


	private Collection<MailbagVO> constructSelectedMailbagVOs(Collection<MailbagVO> mailBagDetailsCollection,
			DSNVO dsnVO) {
		Collection<MailbagVO> selectedMailBagDetailsCollection=
				 new ArrayList<MailbagVO>();
			StringBuilder dsnKey = new StringBuilder();
			dsnKey.append(dsnVO.getOriginExchangeOffice())
					.append(dsnVO.getDestinationExchangeOffice())
					.append(dsnVO.getMailCategoryCode())
					.append(dsnVO.getMailSubclass()).append(dsnVO.getYear())
					.append(dsnVO.getDsn());
			for(MailbagVO mailBagDetails:mailBagDetailsCollection){
				StringBuilder mailKey = new StringBuilder();
				mailKey.append(mailBagDetails.getOoe())
				.append(mailBagDetails.getDoe())
				.append(mailBagDetails.getMailCategoryCode())
				.append(mailBagDetails.getMailSubclass()).append(mailBagDetails.getYear())
				.append(mailBagDetails.getDespatchSerialNumber());
				if(dsnKey.toString().equals(mailKey.toString())){
					selectedMailBagDetailsCollection.add(mailBagDetails);
				
				}
			}
	    	return selectedMailBagDetailsCollection;
	}


	private boolean isAlreadyAttached(Collection<DSNVO>dsnVOs){
		   boolean isValid=true;
		if (dsnVOs != null && dsnVOs.size() > 0) {
			for(DSNVO dsnVO:dsnVOs){
				if (dsnVO.getMasterDocumentNumber() != null
						&& dsnVO.getSequenceNumber() > 0) {
					isValid=false;
					break;
				}
			}
		}
		return isValid;
	   }
	
	 private boolean validateOriginDestination(Collection<DSNVO>dsnVOs,LogonAttributes logonAttributes){
		  boolean isValid=false;
		  Collection<String> officeOfExchanges=new ArrayList<String>();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			HashMap<String,String> originDestinationMap = new HashMap<>();
		  if (dsnVOs != null && dsnVOs.size() > 0) {
			  for(DSNVO dsnVO:dsnVOs){
				  officeOfExchanges.add(dsnVO.getOriginExchangeOffice());
				  officeOfExchanges.add(dsnVO.getDestinationExchangeOffice());
			  }
			  try {
				  groupedOECityArpCodes=
					  new MailTrackingDefaultsDelegate().findCityAndAirportForOE(logonAttributes.getCompanyCode(), officeOfExchanges);
				}catch (BusinessDelegateException businessDelegateException) {
		        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
					log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
					
			  	}
				if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
					if (cityAndArpForOE.size() == 3) {
						if (!originDestinationMap.containsKey(cityAndArpForOE.get(2))) {
							originDestinationMap.put(cityAndArpForOE.get(2),
									cityAndArpForOE.get(0));
						}
					}
					}
				}
			if(originDestinationMap!=null&&originDestinationMap.size()==2){
				isValid=true;
			}	
		  }
		  return isValid;
	  }

}
