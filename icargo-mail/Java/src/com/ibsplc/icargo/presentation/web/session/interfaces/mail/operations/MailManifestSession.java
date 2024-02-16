/*
 * MailManifestSession.java Created on Jul 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

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
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1876
 *
 */
public interface MailManifestSession extends ScreenSession {

	/**
	 * The setter method for MailManifestVO
	 * @param mailManifestVO
	 */
	public void setMailManifestVO(MailManifestVO mailManifestVO);
    /**
     * The getter method for MailManifestVO
     * @return MailManifestVO
     */
    public MailManifestVO getMailManifestVO();
    
	/**
	 * Method for getting FlightValidationVO from session
	 * @return flightValidationVO
	 */
	public FlightValidationVO  getFlightValidationVO();
	/**
	 * Method for setting FlightValidationVO to session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	
	/**
	 * The setter method for OperationalFlightVO
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
    /**
     * The getter method for OperationalFlightVO
     * @return OperationalFlightVO
     */
    public OperationalFlightVO getOperationalFlightVO();
	
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO);
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO();
    
    /**
	 * The setter method for AWBDetailVO
	 * @param awbDetailVO
	 */
	public void setAWBDetailVO(AWBDetailVO awbDetailVO);
    /**
     * The getter method for AWBDetailVO
     * @return AWBDetailVO
     */
    public AWBDetailVO getAWBDetailVO();
    /**
	 * The setter method for SystemParameters
	 * @param awbDetailVO
	 */
    public void setSystemParameters(
    		HashMap<String, String> systemParameters);
    /**
	 * The getter method for SystemParameters
	 * @return HashMap
	 */
    public HashMap<String, String> getSystemParameters();   
    
    /**
     * This method is used to set onetime values to the session
     * @param weightCodes - Collection<OneTimeVO>
     */
    
  
    
    
	public void setWeightCodes(Collection<OneTimeVO> weightCodes);
   
    /**
     * This method returns the onetime vos
     * @return ONETIME_WEIGHTCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getWeightCodes();
    
    /**
     * This method is used to set onetime values to the session
     * @param ONETIME_SHIPMENTDESC - Collection<OneTimeVO>
     */
	public void setShipmentDescription(Collection<OneTimeVO> shipmentDescription);
   
    /**
     * This method returns the onetime vos
     * @return ONETIME_SHIPMENTDESC - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getShipmentDescription();
	
	
	/**
     * This method returns the message status
     * @return String
     */
	public String getMessageStatus();
	
	/**
     * This method is used to set the message status
     * @param String
     */
	public void setMessageStatus(String status);
	
	/**
     * This method returns the agentCode
     * @return String
     */
	public String getAgentCode();
	
	/**
     * This method is used to set the agentCode
     * @param String
     */
	public void setAgentCode(String agentCode);
	
	  /**
     * This method is used to set printTypes values to the session
     * @param printTypes - Collection<String>
     */
	public void setPrintTypes(Collection<String> printTypes);
   
    /**
     * This method returns the printTypes vos
     * @return printTypes - Collection<String>
     */
	public Collection<String> getPrintTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	/**
	 * The setter method for containerTypes
	 * @param containerTypes
	 */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes);
		
	/**
     * The getter method for containerTypes
     * @return Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes() ;
	/**
	 * The setter method for containerVO
	 * @param containerVO
	 */
	public void setContainerVO(ContainerVO containerVO);
		
	/**
     * The getter method for containerVO
     * @return ContainerVO
     */
	public ContainerVO getContainerVO() ;
	/**
	 * The setter method for consignmentDocumentVO
	 * @param consignmentDocumentVO
	 */
	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO);
	
    /**
     * The getter method for ConsignmentDocumentVO
     * @return ConsignmentDocumentVO
     */
    public ConsignmentDocumentVO getConsignmentDocumentVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
    /**
	 * The setter method for ConsignmentFilterVO
	 * @param ConsignmentFilterVO
	 */
	public void setConsignmentFilterVO(ConsignmentFilterVO consignmentFilterVO);
	
    /**
     * The getter method for ConsignmentFilterVO
     * @return ConsignmentFilterVO
     */
    public ConsignmentFilterVO getConsignmentFilterVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeType - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeType();
	/**
     * This method is used to set Collection<MailInConsignmentVO> to the session
     * @param Collection<MailInConsignmentVO>- Collection<MailInConsignmentVO>
     */
	public void setMailVOs(Collection<MailInConsignmentVO> mailVOs);

	/**
     * This method returns the Collection<MailInConsignmentVO>
     * @return Collection<MailInConsignmentVO> - Collection<MailInConsignmentVO>
     */
	public Collection<MailInConsignmentVO> getMailVOs();
	
	   //Added by A-7531 as part of CR ICRD-197299 starts
	/**
     * This method is used to set onetime values to the session
     * @param ONETIME_MAIL_STATUS- Collection<OneTimeVO>
     */
	public void setMailStatus(Collection<OneTimeVO> mailStatus);
	
	 /**
     * This method returns the onetime vos
     * @return ONETIME_MAIL_STATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailStatus();
	
	  //Added by A-7531 as part of CR ICRD-197299 ends
	/**

	 *

	 *remove MailVOs

	 */

	public void removeMailVOs() ;

	
	
}

