/*
 * AttachAWBScreenLoadCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AttachAWBScreenLoadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
   private static final String SCREEN_ID_MAIL_ARRIVAL="mailtracking.defaults.mailarrival";
   private static final String IMPORT_MANIFEST="MTK007";
   private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
   private static final String WEIGHT_CODE = "shared.defaults.weightUnitCodes";
   private static final String CATEGORY_CODE = "mailtracking.defaults.mailcategory";
   private static final String CONTAINER_TYPE = "mailtracking.defaults.containertype";
   //Added by A-7531 as part of CR ICRD-197299
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String ALLOW_NON_STANDARD_AWB = "operations.shipment.allownonstandardawb";

    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AttachAWBScreenLoadCommand","execute");
    	  
    	MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID_MAIL_ARRIVAL);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	
		//added by a-7871 for ICRD-262855 for setting systemparaters  starts--
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
		if(mailManifestSession.getSystemParameters()==null)
		{
			Collection<ErrorVO> errors= null;
		Map<String, String> parameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
    	try {
    		parameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);

    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	log.log(Log.INFO, "parameters----->>", parameters);
		if (parameters != null) {
    		mailManifestSession.setSystemParameters((HashMap<String, String>)parameters);
		}
		}
		//added by a-7871 for ICRD-262855  ends--
		Map<String,String> parametersMap = mailManifestSession.getSystemParameters();
		 log
				.log(Log.FINE, "The values in the Paramater Map are ",
						parametersMap);
		if(parametersMap!=null && parametersMap.size()>0){
			   mailManifestForm.setShipmentDesc(parametersMap.get(MailConstantsVO.SHIPMENTDESCRIPTION_FORAWB));
			   log.log(Log.FINE, "The Shipment Description for  the AWB",
					mailManifestForm.getShipmentDesc());
		 }
		/*
		 * Commodity Validation
		 */ 
		String cmpcod = logonAttributes.getCompanyCode();
		String commodityCode=null;
		if(mailManifestSession.getSystemParameters()!=null){
			commodityCode = mailManifestSession.getSystemParameters().get(MAIL_COMMODITY_SYS);
		}else{
			commodityCode=mailArrivalSession.getMailCommidityCode();
		}
		Collection<String> commodites = new ArrayList<String>();
		if(commodityCode!=null && commodityCode.trim().length()>0) {
			commodites.add(commodityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(cmpcod, commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

			if(densityMap !=null && densityMap.size()>0){
				CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
				mailManifestForm.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
				mailManifestForm.setShipmentDesc(commodityValidationVO.getCommodityDesc());
			}
		}		
		
		ContainerDetailsVO containerDtlsVO =null;
		if(mailManifestSession.getContainerDetailsVO()!=null){
			containerDtlsVO=mailManifestSession.getContainerDetailsVO();
		}else{
			containerDtlsVO=mailArrivalSession.getContainerDetailsVO();
		}
		ArrayList<DSNVO> dsnVOs = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
		Collection<MailbagVO> mailbagVOs = containerDtlsVO.getMailDetails();
		ArrayList<MailbagVO>selectedMailbagsVOs=null;
		int bags = 0;
		double bagWts = 0;
		String displayUnit=null;
		String originOE=null;
		String agentCode=null;//added by a-7871 for ICRD-262855
		switch (mailManifestForm.getFromScreen()) {
		case IMPORT_MANIFEST:
			
			{
			if (dsnVOs != null && dsnVOs.size() > 0) {
				for (DSNVO dsnVO : dsnVOs) {
					originOE=dsnVO.getOriginExchangeOffice();
					if (mailbagVOs != null && mailbagVOs.size() > 0) {
							selectedMailbagsVOs = constructSelectedMailbagVOs(mailbagVOs, dsnVO);
							if (selectedMailbagsVOs != null && selectedMailbagsVOs.size() > 0) {
							bags = bags + selectedMailbagsVOs.size();
								bagWts = bagWts + selectedMailbagsVOs.get(0).getWeight().getRoundedDisplayValue();	
								displayUnit=selectedMailbagsVOs.get(0).getWeight().getDisplayUnit();
								Collection<String> officeOfExchanges=new ArrayList<String>();//Added by A-8164 for ICRD-272337 starts
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
												mailManifestForm.setOrigin(cityAndArpForOE.get(2)); 
											}
											if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
												mailManifestForm.setDestination(cityAndArpForOE.get(2)); 
											}
										} 
									}
								}	//Added by A-8164 for ICRD-272337 ends
								displayUnit=selectedMailbagsVOs.iterator().next().getWeight().getDisplayUnit();
								//mailManifestForm.setOrigin(selectedMailbagsVOs.get(0).getPol()); //Commented by A-8164 for ICRD-272337
								//mailManifestForm.setDestination(selectedMailbagsVOs.get(0).getPou()); 
						}
					}
				}
			}
			mailManifestForm.setStdPieces(bags);
			mailManifestForm.setStdWeight(bagWts);
			agentCode=findAgentCodeForPA(logonAttributes.getCompanyCode(),originOE);//added by a-7871 for ICRD-262855
			mailManifestForm.setAgentCode(agentCode);//added by a-7871 for ICRD-262855
			mailManifestSession.setAgentCode(agentCode);//added by a-7871 for ICRD-262855
			mailManifestSession.setContainerDetailsVO(containerDtlsVO);//added by a-7871 for ICRD-262855

			break;
			}
		default:
		if (dsnVOs != null && dsnVOs.size() != 0) {
        	for (DSNVO dsnVO : dsnVOs) {
        		bags = bags + dsnVO.getBags();
        		//bagWts = bagWts + dsnVO.getWeight();
        		bagWts = bagWts + dsnVO.getWeight().getRoundedDisplayValue();  //Added by A-7550
       	  	}
        	displayUnit=dsnVOs.iterator().next().getWeight().getDisplayUnit();
       	}
		mailManifestForm.setStdPieces(bags);
		mailManifestForm.setStdWeight(bagWts);
		}
		mailManifestForm.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
		mailManifestForm.setWeightStandard(displayUnit);
		invocationContext.target = TARGET;
    	log.exiting("AttachAWBScreenLoadCommand","execute");
    	
    }
    /**
     * 
     * 	Method		:	AttachAWBScreenLoadCommand.constructSelectedMailbagVOs
     *	Added by 	:	U-1267 on 10-Nov-2017
     * 	Used for 	:	ICRD-211205
     *	Parameters	:	@param mailbagVOs
     *	Parameters	:	@param dsnVO
     *	Parameters	:	@return 
     *	Return type	: 	ArrayList<MailbagVO>
     */
    private ArrayList<MailbagVO> constructSelectedMailbagVOs(Collection<MailbagVO>mailbagVOs,DSNVO dsnVO){
    	ArrayList<MailbagVO> selectedMailbagVOs=new ArrayList<MailbagVO>();
		StringBuilder dsnKey = new StringBuilder();
		dsnKey.append(dsnVO.getOriginExchangeOffice())
				.append(dsnVO.getDestinationExchangeOffice())
				.append(dsnVO.getMailCategoryCode())
				.append(dsnVO.getMailSubclass()).append(dsnVO.getYear())
				.append(dsnVO.getDsn());
		for(MailbagVO mailbagVO:mailbagVOs){
			StringBuilder mailKey = new StringBuilder();
			mailKey.append(mailbagVO.getOoe())
			.append(mailbagVO.getDoe())
			.append(mailbagVO.getMailCategoryCode())
			.append(mailbagVO.getMailSubclass()).append(mailbagVO.getYear())
			.append(mailbagVO.getDespatchSerialNumber());
			if(dsnKey.toString().equals(mailKey.toString())){
				selectedMailbagVOs.add(mailbagVO);
				break;
			}
		}
    	return selectedMailbagVOs;
    }
	  /**
     * 
     * 	Method		:	AttachAWBCommand.findAgentCodeForPA
     *	Added by 	:	U-1267 on 07-Nov-2017
     * 	Used for 	:	ICRD-211205
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param originOE
     *	Parameters	:	@return 
     *	Return type	: 	String
     */
	private String findAgentCodeForPA(String companyCode,
			String originOE) {
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
       
}
