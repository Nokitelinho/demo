/*
 * MailAcceptanceSession.java Created on Jun 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

/**
 * @author A-1876
 *
 */
public interface MailAcceptanceSession extends ScreenSession {

	/**
	 * The setter method for mailAcceptanceVO
	 * @param mailAcceptanceVO
	 */
	public void setMailAcceptanceVO(MailAcceptanceVO mailAcceptanceVO);
    /**
     * The getter method for MailAcceptanceVO
     * @return MailAcceptanceVO
     */
    public MailAcceptanceVO getMailAcceptanceVO();
    
    /**
	 * The setter method for existingMailbagVOS
	 * @param existingMailbagVO
	 */
	public void setExistingMailbagVO(Collection<ExistingMailbagVO> existingMailbagVOS);
    /**
     * The getter method for existingMailbagVOS
     * @return Collection<ExistingMailbagVO>
     */
    public Collection<ExistingMailbagVO> getExistingMailbagVO();
    
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
     * This method is used to set ContainerDetailsVOs to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs);

	/**
     * This method returns the ContainerDetails vos
     * @return containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs();
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
     * This method is used to set onetime values to the session
     * @param oneTimeMailClass - Collection<OneTimeVO>
     */
	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeMailClass - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailClass();
        
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
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRSN - Collection<OneTimeVO>
     */
	public void setOneTimeRSN(Collection<OneTimeVO> oneTimeRSN);

	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeRSN - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRSN();
	
	/**
     * This method is used to set warehouse values to the session
     * @param warehouse - Collection<WarehouseVO>
     */
	public void setWarehouse(Collection<WarehouseVO> warehouse);

	/**
     * This method returns the onetime vos
     * @return ONETIME_Warehouse - Collection<WarehouseVO>
     */
	public Collection<WarehouseVO> getWarehouse();
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode);
	
	/**
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs();
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs);
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
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeContainerType - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType();
	
	/**
     * This method is used to set pous values to the session
     * @param pous - Collection<String>
     */
	public void setPous(Collection<String> pous);

	/**
     * This method returns the pous
     * @return Collection<String>
     */
	public Collection<String> getPous();
	
	/**
     * This method is used to set Consignment values to the session
     * @param consgn - String<String>
     */
	
	public String getConsgnValues();
	
	/**
     * This method returns the consgn
     * @return String<String>
     */
	
	public void setConsgnValues(String consgn);
	
	/**
     * This method is used to set Pao values to the session
     * @param pao - String<String>
     */
	public String getPaoValues();

	/**
     * This method returns the pao
     * @return String<String>
     */
	public void setPaoValues(String pao);
	
	/**
     * This method is used to set inventoryparameter values to the session
     * @param inventoryparameter - String<String>
     */
	public String getInventoryparameter();

	/**
     * This method returns the inventoryparameter
     * @return String<String>
     */
	public void setInventoryparameter(String inventoryparameter);
	/**
     * This method is used to set onetime values to the session
     * @param flightStatus - Collection<OneTimeVO>
     */
	public void setOneTimeFlightStatus(Collection<OneTimeVO> oneTimeFlightStatus);
	/**
     * This method returns the onetime vos
     * @return  - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeFlightStatus();
	
	/**
     * This method returns the MailCommidityCode
     * @return  - String
     */
	public String getMailCommidityCode();

	/**
     * This method set the MailCommidityCode
     * @return  - String
     */
	public void setMailCommidityCode(String mailCommidityCode);
	
	
	/**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * @return KEY_VOLUMEROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getVolumeRoundingVO() ;
    /**
     * @param key
     */
    public void removeVolumeRoundingVO(String key) ;
    
    
    /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() ;
    /**
     * @param key
     */
    public void removeWeightRoundingVO(String key) ;
    
    /**
	 * @return Returns the MailDetailVO.
	 */
    public MailbagVO getCurrentMailDetail();
    /**
	 * @param mailDetailVO
	 */
    public void setCurrentMailDetail(MailbagVO mailDetailVO);

    /**
	 * @return Returns the ArrayList<MailDetailVO>.
     */
	public ArrayList<MailbagVO> getSelectedMailDetailsVOs();

    /**
	 * @param MailDetailVO
	 */
	public void setSelectedMailDetailsVOs(ArrayList<MailbagVO> mailDetailVOs);
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeCompanyCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCompanyCode();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCompanyCode - Collection<OneTimeVO>
     */
	public void setOneTimeCompanyCode(Collection<OneTimeVO> oneTimeCompanyCode);
	
	/**
	 * @return Returns the MailDetailVO.
	 */
    public MailbagVO getUnmodifiedMailDetail();
    /**
	 * @param mailDetailVO
	 */
    public void setUnmodifiedMailDetail(MailbagVO mailDetailVO);
	
}

