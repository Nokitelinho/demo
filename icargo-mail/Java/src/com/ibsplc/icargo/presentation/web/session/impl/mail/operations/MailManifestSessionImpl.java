/*
 * MailManifestSessionImpl.java Created on Jul 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;

/**
 * @author A-1876
 *
 */
public class MailManifestSessionImpl extends AbstractScreenSession
        implements MailManifestSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_MAILMANIFESTVO = "mailManifestVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_CONTAINERDETAILSVO = "containerDetailsVO";
	private static final String KEY_AWBDETAILVO = "awbDetailVO";
	private static final String KEY_SYSTEMPARAMETERS = "systemParameters";
	private static final String ONETIME_WEIGHTCODE = "weightcodes";
	private static final String KEY_MSGSTAT = "messagestat";
	private static final String KEY_AGTCOD = "agentcode";
	private static final String PRINT_TYPES = "printTypes";
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String ONETIME_CONTAINERTYPES = "onetime_containertypes";
	private static final String ONETIME_SHIPMENTDESC = "shipmentDescription";
	private static final String CONTAINERVO = "containerVO";
	private static final String KEY_CONSIGNMENTDOCUMENTVO = "consignmentDocumentVO";
	private static final String KEY_CONSIGNMENTFILTERVO = "consignmentFilterVO";
	private static final String KEY_ONETIMETYPE = "oneTimeType";
	private static final String KEY_MAILVOS="mailVOs";
	   //Added by A-7531 as part of CR ICRD-197299
	private static final String ONETIME_MAIL_STATUS="mailStatus";
		
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 * The setter method for MailManifestVO
	 * @param mailManifestVO
	 */
    public void setMailManifestVO(MailManifestVO mailManifestVO) {
    	setAttribute(KEY_MAILMANIFESTVO,mailManifestVO);
    }
    /**
     * The getter method for MailManifestVO
     * @return mailManifestVO
     */
    public MailManifestVO getMailManifestVO() {
    	return getAttribute(KEY_MAILMANIFESTVO);
    }
        
	/**
     * This method is used to get the flightValidationVO from the session
     * @return flightValidationVO
     */
	public FlightValidationVO getFlightValidationVO(){
	    return getAttribute(KEY_FLIGHTVALIDATIONVO);
	}
	
	/**
	 * This method is used to set the flightValidationVO in session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO  flightValidationVO) {
	    setAttribute(KEY_FLIGHTVALIDATIONVO, flightValidationVO);
	}
	
	/**
     * This method is used to get the operationalFlightVO from the session
     * @return operationalFlightVO
     */
	public OperationalFlightVO getOperationalFlightVO(){
	    return getAttribute(KEY_OPERATIONALFLIGHTVO);
	}
	
	/**
	 * This method is used to set the operationalFlightVO in session
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO  operationalFlightVO) {
	    setAttribute(KEY_OPERATIONALFLIGHTVO, operationalFlightVO);
	}
	
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
    public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
    	setAttribute(KEY_CONTAINERDETAILSVO,containerDetailsVO);
    }
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO() {
    	return getAttribute(KEY_CONTAINERDETAILSVO);
    }

    /**
	 * The setter method for AWBDetailVO
	 * @param awbDetailVO
	 */
	public void setAWBDetailVO(AWBDetailVO awbDetailVO) {
		setAttribute(KEY_AWBDETAILVO,awbDetailVO);
	}
    /**
     * The getter method for AWBDetailVO
     * @return AWBDetailVO
     */
    public AWBDetailVO getAWBDetailVO() {
    	return getAttribute(KEY_AWBDETAILVO);
    }
    
    /**
	 * The setter method for SystemParameters
	 * @param systemParameters
	 */
    public void setSystemParameters(
    		HashMap<String, String> systemParameters) {
    	setAttribute(KEY_SYSTEMPARAMETERS, systemParameters);
    }
    /**
	 * The getter method for SystemParameters
	 * @return HashMap
	 */
    public HashMap<String, String> getSystemParameters() {
        return getAttribute(KEY_SYSTEMPARAMETERS);
    }
    
    /**
     * This method is used to set onetime values to the session
     * @param weightCodes - Collection<OneTimeVO>
     */
	public void setWeightCodes(Collection<OneTimeVO> weightCodes) {
		setAttribute(ONETIME_WEIGHTCODE,(ArrayList<OneTimeVO>)weightCodes);
	}
   
    /**
     * This method returns the onetime vos
     * @return ONETIME_WEIGHTCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getWeightCodes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_WEIGHTCODE);
	}
    
    /**
     * This method is used to set onetime values to the session
     * @param shipmentDescription - Collection<OneTimeVO>
     */
	public void setShipmentDescription(Collection<OneTimeVO> shipmentDescription){
		setAttribute(ONETIME_SHIPMENTDESC,(ArrayList<OneTimeVO>)shipmentDescription);
	}
   
    /**
     * This method returns the onetime vos
     * @return ONETIME_SHIPMENTDESC - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getShipmentDescription() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_SHIPMENTDESC);
	}
	
	/**
     * This method returns the message status
     * @return String
     */
	public String getMessageStatus() {
		return (String)getAttribute(KEY_MSGSTAT);
	}
	
	/**
     * This method is used to set the message status
     * @param String
     */
	public void setMessageStatus(String status) {
		setAttribute(KEY_MSGSTAT,(String)status);
	}
	
	/**
     * This method returns the agentCode
     * @return String
     */
	public String getAgentCode() {
		return (String)getAttribute(KEY_AGTCOD);
	}
	
	/**
     * This method is used to set the agentCode
     * @param String
     */
	public void setAgentCode(String agentCode) {
		setAttribute(KEY_AGTCOD,(String)agentCode);
	}
	
    /**
     * This method is used to set printTypes values to the session
     * @param PRINT_TYPES - Collection<String>
     */
	public void setPrintTypes(Collection<String> printTypes) {
		setAttribute(PRINT_TYPES,(ArrayList<String>)printTypes);
	}
   
    /**
     * This method returns the printTypes vos
     * @return printTypes - Collection<String>
     */
	public Collection<String> getPrintTypes() {
		return (Collection<String>)getAttribute(PRINT_TYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes) {
		setAttribute(ONETIME_CONTAINERTYPES,(ArrayList<OneTimeVO>)containerTypes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CONTAINERTYPES);
	}
	/**
     * This method is used to set containerVO values to the session
     * @param ContainerVO - Collection<ContainerVO>
     */
	public void setContainerVO(ContainerVO containerVO) {
		setAttribute(CONTAINERVO,(ContainerVO)containerVO);
	}
	/**
     * This method returns the containerVO
     * @return CONTAINERVO - Collection<ContainerVO>
     */
	public ContainerVO getContainerVO() {
		return (ContainerVO)getAttribute(CONTAINERVO);
	}
	/**
	 * The setter method for ConsignmentDocumentVO
	 * @param consignmentDocumentVO
	 */
    public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO) {
    	setAttribute(KEY_CONSIGNMENTDOCUMENTVO,consignmentDocumentVO);
    }
    /**
     * The getter method for ConsignmentDocumentVO
     * @return ConsignmentDocumentVO
     */
    public ConsignmentDocumentVO getConsignmentDocumentVO() {
    	return getAttribute(KEY_CONSIGNMENTDOCUMENTVO);
    }
    /**
	 * The setter method for ConsignmentFilterVO
	 * @param consignmentFilterVO
	 */
    public void setConsignmentFilterVO(ConsignmentFilterVO consignmentFilterVO) {
    	setAttribute(KEY_CONSIGNMENTFILTERVO,consignmentFilterVO);
    }
    /**
     * The getter method for ConsignmentFilterVO
     * @return consignmentFilterVO
     */
    public ConsignmentFilterVO getConsignmentFilterVO() {
    	return getAttribute(KEY_CONSIGNMENTFILTERVO);
    }  
    /**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType) {
		setAttribute(KEY_ONETIMETYPE,(ArrayList<OneTimeVO>)oneTimeType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMETYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMETYPE);
	}
	 /**
     * This method is used to set Collection<MailInConsignmentVO> to the session
     * @param Collection<MailInConsignmentVO>
     */
	public void setMailVOs(Collection<MailInConsignmentVO> mailVOs) {
		setAttribute(KEY_MAILVOS,(ArrayList<MailInConsignmentVO>)mailVOs);
	}

	/**
     * This method returns the Collection<MailInConsignmentVO>
     * @return Collection<MailInConsignmentVO>
     */
	public Collection<MailInConsignmentVO> getMailVOs(){
		return (Collection<MailInConsignmentVO>)getAttribute(KEY_MAILVOS);
	}
	
	
	
	   //Added by A-7531 as part of CR ICRD-197299 starts   
	
	/**
     * This method is used to set onetime values to the session
     * @param mailStatus - Collection<OneTimeVO>
     */
		@Override
	public void setMailStatus(Collection<OneTimeVO> mailStatus) {
     	 setAttribute(ONETIME_MAIL_STATUS,(ArrayList<OneTimeVO>)mailStatus);
	}
		/**
	     * This method returns the onetime vos
	     * @return ONETIME_MAIL_STATUS- Collection<OneTimeVO>
	     */
	@Override
	public Collection<OneTimeVO> getMailStatus() {
			
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAIL_STATUS);
			
	}
	

	   //Added by A-7531 as part of CR ICRD-197299 ends
	/**

	 *

	 *remove MailVOs

	 */

	public void removeMailVOs() {

		removeAttribute(KEY_MAILVOS);

	}
	
	
	
}
