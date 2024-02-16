package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
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
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
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
	private static final String PAWBASSCONENAB ="PAWBASSCONENAB";
	private static final String PAWBPARMVALYES ="YES";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ScreenLoadAttachAwbCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		OutboundModel outboundModel = 
				(OutboundModel) actionContext.getScreenModel();
		Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetVO = new ArrayList<ContainerDetailsVO>();
		ArrayList<MailbagVO>selectedMailbagsVOs=null;
		ArrayList<MailbagVO> selectedMailbagVOSelected =null;
		ArrayList<MailbagVO> mailbagVOs =null;
		ArrayList<DSNVO> dsnVos=null;
		AttachAwbDetails attachAwbDetails= new AttachAwbDetails();
		ContainerDetails containerDetails =null;
		ContainerDetailsVO containerDetailsVO = null;
		Collection<Mailbag> selectedMailbags = null;
		ArrayList<ContainerDetails> containerDetailsCollection= 
				outboundModel.getContainerDetailsCollection();
		ArrayList<DespatchDetails> dsnList = outboundModel.getDespatchDetailsList();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		if(containerDetailsCollection != null) {
		containerDetails = containerDetailsCollection.get(0);
		containerDetails.setFromContainerTab(outboundModel.isFromContainerTab());
		containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
		containers.add(containerDetailsVO);
		try{
			containerDetVO=new MailTrackingDefaultsDelegate().findMailbagsInContainer(containers);
		}catch(BusinessDelegateException businessDelegateException){
  	      actionContext.addAllError(handleDelegateException(businessDelegateException));
		}
		if (containerDetails != null && containerDetails.getMailBagDetailsCollection() != null) {
			selectedMailbags = containerDetails.getMailBagDetailsCollection();
			if(selectedMailbags!=null){
			selectedMailbagVOSelected = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags,logonAttributes);	
			for(MailbagVO mailbagVO:selectedMailbagVOSelected){
				if(mailbagVO.getDocumentNumber()!=null){
					actionContext
					.addError(new ErrorVO(
							"mailtracking.defaults.attachawb.msg.warn.alreadyattached"));
					return;
			}
			
				mailDetailsMap.put(mailbagVO.getMailbagId(), mailbagVO);	
			}
		  }
		}
		if(containerDetVO!=null){
			 boolean flag =false;
			 for(ContainerDetailsVO containersDetailsVO :containerDetVO) { 
				 mailbagVOs=  (ArrayList)containersDetailsVO.getMailDetails();
					 
				
														
				 containersDetailsVO.setDsnVOs(dsnVos);
			 }
		}
		if(dsnList != null) {
			dsnVos=(ArrayList)MailOutboundModelConverter.constructDSNVOs(dsnList);
		}
		else {
			 for(ContainerDetailsVO containerDetsVO :containers) {
				 dsnVos=(ArrayList)new MailTrackingDefaultsDelegate().getDSNsForContainer(containerDetsVO);
			}
		}
		}
		if(selectedMailbagVOSelected==null){
		 
		 if (dsnVos != null && dsnVos.size() > 0) {
 			for (int i = 0; i < dsnVos.size(); i++) {
 				if (dsnVos.get(i).getDsn() != null){
 				if (dsnVos.get(i).getMasterDocumentNumber() != null
 						&& dsnVos.get(i).getSequenceNumber() > 0) {
 					actionContext
 							.addError(new ErrorVO(
 									"mailtracking.defaults.attachawb.msg.warn.alreadyattached"));
 					return;
 				}
 				  
     } else {
    	 actionContext.addError(new ErrorVO("No mailbags"));
	     return;
			     }
 		 }
      } else {
    	 actionContext.addError(new ErrorVO("No mailbags"));
	     return;
      }
     
	 }
		int primaryKeyLen =dsnVos.size();
		if(selectedMailbagVOSelected!=null&&selectedMailbagVOSelected.size()>0){
			primaryKeyLen =selectedMailbagVOSelected.size();
		}
        Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		int cnt=0;
		int count = 0;
        String[] orgArr = new String[primaryKeyLen];
        String[] destArr = new String[primaryKeyLen];
        if(selectedMailbagVOSelected!=null &&mailbagVOs!=null&&mailbagVOs.size()>0){
    		for(MailbagVO selectedMailbag:mailbagVOs){ 
 			if(mailDetailsMap.containsKey(selectedMailbag.getMailbagId())){
    			orgArr[cnt] = selectedMailbag.getOoe();
    			destArr[cnt] = selectedMailbag.getDoe();
    			cnt++;
 			  }
    		 }
    		}
        else if (dsnVos != null && dsnVos.size() != 0) {
        	for (DSNVO dsnVO : dsnVos) {
        			orgArr[cnt] = dsnVO.getOriginExchangeOffice();
        			destArr[cnt] = dsnVO.getDestinationExchangeOffice();
        			newDsnVOs.add(dsnVO);
        			cnt++;
       	  	}
       	}
        Collection<String> originColl = new ArrayList<String>();
        for(int i=0;i<orgArr.length;i++){
        	if(orgArr[i]!=null && !orgArr[i].isEmpty() && !originColl.contains(orgArr[i])){
        		originColl.add(orgArr[i]);
        	}
        }
	    try {
				/*
			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
			     * the inner collection contains the values in the order :
			     * 0.OFFICE OF EXCHANGE
			     * 1.CITY NEAR TO OE
			     * 2.NEAREST AIRPORT TO CITY
			     */
				groupedOECityArpCodes = 
					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
							logonAttributes.getCompanyCode(), originColl);
			}catch (BusinessDelegateException businessDelegateException) {
        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
	  	}
        int flag = 0;
        int errorFlag = 0;
        String airport = "";		
        String originOE=null;
		if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
			for(String ooe : originColl){
				for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
					if(cityAndArpForOE.size() == 3 && ooe.equals(cityAndArpForOE.get(0))) {
						if(flag == 0) { 
							airport = cityAndArpForOE.get(2);
							originOE=cityAndArpForOE.get(0);
							flag = 1;
						}else{
							if(!airport.equals(cityAndArpForOE.get(2))){
								errorFlag = 1;
								break;
							}
						}
						break;
					}			
				}
				if(errorFlag == 1) {
					break;
				}
			}
		}
      //  mailManifestForm.setPol(airport);
        if(errorFlag == 1){
    	actionContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.differentairports"));
    	return;
        }else{
	        Collection<String> destnColl = new ArrayList<String>();
	        for(int i=0;i<destArr.length;i++){
	        	if(destArr[i]!=null && !destArr[i].isEmpty()&& !destnColl.contains(destArr[i])){
	        		destnColl.add(destArr[i]);
	        	}
	        }
	        groupedOECityArpCodes = null;
		    try {
				/*
			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
			     * the inner collection contains the values in the order :
			     * 0.OFFICE OF EXCHANGE
			     * 1.CITY NEAR TO OE
			     * 2.NEAREST AIRPORT TO CITY
			     */
				groupedOECityArpCodes = 
					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
							logonAttributes.getCompanyCode(), destnColl);
			}catch (BusinessDelegateException businessDelegateException) {
	        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
		  	}
	        flag = 0;
	        errorFlag = 0;
	        airport = "";	
			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
				for(String doe : destnColl){
					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
						if(cityAndArpForOE.size() == 3 && doe.equals(cityAndArpForOE.get(0))) {
							if(flag == 0) { 
								airport = cityAndArpForOE.get(2);
								flag = 1;
							}else{
								if(!airport.equals(cityAndArpForOE.get(2))){
									errorFlag = 1;
									break;
								}
							}
							break;
						}			
					}
					if(errorFlag == 1) {
						break;
					}
				}
			}
	      //  mailManifestForm.setPou(airport);
	        if(errorFlag == 1){
	        	actionContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.differentairports"));
	        	return;
	        }else{
	        	String agentCode=findAgentCodeForPA(logonAttributes.getCompanyCode(),originOE);
	        	if(agentCode!=null&&agentCode.trim().length()>0){
				     //   containerDtlsVO.setDsnVOs(newDsnVOs);
				     //   mailManifestSession.setContainerDetailsVO(containerDtlsVO);
		        //mailManifestSession.setAgentCode(agentCode);
				      //  mailManifestForm.setOpenAttachAWB(FLAG_YES);
	        }else{
	        	actionContext.addError(new ErrorVO("mailtracking.defaults.attachawb.agentcodenotmapped"));
        			//invocationContext.target = TARGET;
        			return;
		        }
	        }
        }
       // invocationContext.target = TARGET;
    	log.exiting("AttachAWBCommand","execute");
		ResponseVO responseVO = new ResponseVO();
		if(dsnVos!=null && dsnVos.size()>0){
			if(selectedMailbagVOSelected==null){
			boolean isValid=false;
			isValid = isAlreadyAttached(dsnVos);
			if (!isValid) {
				actionContext.addError(new ErrorVO(ALREADY_ATTACHED));
				return;
			}
			//isValid=validateOriginDestination(dsnVos,logonAttributes);
			if (!isValid) {
				actionContext.addError(new ErrorVO(DIFFERENT_ORG_DST));
				return;
			}
			}
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
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
			Collection<ErrorVO> errors= null;
			Map<String, String> parameters = null;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	    	try {
	    		parameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
	    	}catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
			}
	    	try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), fieldTypes);
	    	     // systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
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
			String originOExchange=null;
			String agentCode=null;
			String destOE=null;
			if (dsnVos != null && dsnVos.size() > 0) {
				for (DSNVO dsnVO : dsnVos) {
					if(selectedMailbagVOSelected==null){
					originOExchange=dsnVO.getOriginExchangeOffice();
					destOE=dsnVO.getDestinationExchangeOffice();
					}
					if (mailbagVOs != null && mailbagVOs.size() > 0) {
							selectedMailbagsVOs = constructSelectedMailbagVOs(mailbagVOs, dsnVO);
							if (selectedMailbagsVOs != null && selectedMailbagsVOs.size() > 0) {
								 if(selectedMailbagVOSelected==null){
							bags = bags + selectedMailbagsVOs.size();
								}
							    for(MailbagVO selectedMailbag:selectedMailbagsVOs){     
							 if(selectedMailbagVOSelected!=null){
			 						if(mailDetailsMap.containsKey(selectedMailbag.getMailbagId())){
			 							bags = bags + 1;
							     Measure DSNbagWt= new Measure(UnitConstants.MAIL_WGT
											,selectedMailbag.getWeight().getSystemValue());  
								bagWts = bagWts + DSNbagWt.getRoundedDisplayValue();	 
									    originOExchange=selectedMailbag.getOoe();
									    destOE=selectedMailbag.getDoe();
			 					     	}
			 						}
							 else{
			 						   Measure DSNbagWt= new Measure(UnitConstants.MAIL_WGT
												,selectedMailbag.getWeight().getSystemValue());  
									bagWts = bagWts + DSNbagWt.getRoundedDisplayValue();
							    }	
							    }
								Collection<String> officeOfExchanges=new ArrayList<String>();//Added by A-8164 for ICRD-272337 starts
								Collection<ArrayList<String>> groupedOriginECityArpCodes = null;
								if(originOExchange != null && originOExchange.length() > 0){
								officeOfExchanges.add(originOExchange);
								}
								if(destOE != null && destOE.length() > 0){
								officeOfExchanges.add(destOE); 
								}
								try {
									groupedOriginECityArpCodes=new MailTrackingDefaultsDelegate()     
											.findCityAndAirportForOE(logonAttributes.getCompanyCode(), officeOfExchanges);
								} catch (BusinessDelegateException businessDelegateException) {
									handleDelegateException(businessDelegateException); 
								}
								if(groupedOriginECityArpCodes != null && groupedOriginECityArpCodes.size() > 0) {
									for(ArrayList<String> cityAndArpForOE : groupedOriginECityArpCodes) {  
										if(cityAndArpForOE.size() == 3) {
											if(originOExchange != null && originOExchange.length() > 0 && originOExchange.equals(cityAndArpForOE.get(0))) {
												attachAwbDetails.setOrigin(cityAndArpForOE.get(2)); 
											}
											if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
												attachAwbDetails.setDestination(cityAndArpForOE.get(2)); 
											}
										} 
									}
								}	//Added by A-8164 for ICRD-272337 ends
								//mailManifestForm.setOrigin(selectedMailbagsVOs.get(0).getPol()); //Commented by A-8164 for ICRD-272337
								//mailManifestForm.setDestination(selectedMailbagsVOs.get(0).getPou()); 
						}
					}
				}
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
				if(selectedMailbagVOSelected!=null){
					if (mailbagVOs != null && mailbagVOs.size() > 0) {
						List<Mailbag> despatchDetails = new ArrayList<Mailbag>();
					    for(MailbagVO selectedMailbag:mailbagVOs){ 
			 						if(mailDetailsMap.containsKey(selectedMailbag.getMailbagId())){
			 						    Mailbag mailbag= null; 
			 						     mailbag=MailOutboundModelConverter.constructMailbagDetails(selectedMailbag,containerDetailsVO);
			 						      despatchDetails.add(mailbag);
			 						}
			 						containerDetails.setMailBagDetailsCollection(despatchDetails);
			 		      }
					 }
				}
				else{
			List<DespatchDetails> despatchDetails= MailOutboundModelConverter.constructDesptachDetails(dsnVos, containerDetailsVO);
			         containerDetails.setDesptachDetailsCollection(despatchDetails);
				}
			attachAwbDetails.setStandardPieces((bags));
			attachAwbDetails.setStdWeightMeasure(new Measure(UnitConstants.MAIL_WGT,0.0
					,bagWts,stationWeightUnit));

			attachAwbDetails.setStandardWeight(attachAwbDetails.getStdWeightMeasure().getRoundedDisplayValue());
			agentCode=findAgentCodeForPA(logonAttributes.getCompanyCode(),originOE);
			attachAwbDetails.setAgentCode(agentCode);
			attachAwbDetails.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
		//	containerDetails.setMailBagDetailsCollection(selectedMailbags);
			//containerDetails.setDesptachDetailsCollection(despatchDetails);
			ArrayList<ContainerDetails> containerDetailsCollectionToSave=
					new ArrayList<ContainerDetails>();
			containerDetailsCollectionToSave.add(containerDetails);
			outboundModel.setContainerDetailsCollection(containerDetailsCollectionToSave);
			outboundModel.setAttachAwbDetails(attachAwbDetails);
			outboundModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
			ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
			result.add(outboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
	    	log.exiting("ScreenLoadAttachAwbCommand","execute");
			}
	    	//return;
		}
	//	actionContext.addError(new ErrorVO("No mail bags in container to attach"));                         
	//	return;       
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
	private ArrayList<MailbagVO> constructSelectedMailbagVOs(ArrayList<MailbagVO> mailBagDetailsCollection,
			DSNVO dsnVO) {
		 ArrayList<MailbagVO> selectedMailBagDetailsCollection=
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
			HashMap<String,String> originDestinationMap = new HashMap<String,String>();
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
