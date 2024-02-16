/*
 * MailAcceptanceSessionImpl.java Created on Jun 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;

/**
 * @author A-1876
 *
 */
public class MailAcceptanceSessionImpl extends AbstractScreenSession
        implements MailAcceptanceSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_MAILACCEPTANCEVO = "mailAcceptanceVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_ONETIMEMAILCMPCODE = "oneTimeCompanyCode";
	private static final String KEY_CONTAINERDETAILSVOS = "containerDetailsVOs";
	private static final String KEY_CONTAINERDETAILSVO = "containerDetailsVO";
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMEMAILCLASS = "oneTimeMailClass";
	private static final String KEY_ONETIMEFLIGHTSTATUS = "oneTimeFlightStatus";
	private static final String KEY_ONETIMERSN = "oneTimeRSN";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_ONETIMEDAMAGECODE = "oneTimeDamageCode";
	private static final String KEY_WAREHOUSE = "warehouse";
	private static final String KEY_MSGSTAT = "messagestat";
	private static final String DAMAGEMAILBAGVOS = "DamageMailBagVOs";
	private static final String KEY_POUS = "pous";
	private static final String KEY_ONETIMECONTAINERTYPE = "oneTimeContainerType";
	private static final String KEY_INVENTORYPARAMETER = "inventoryParameter";
	private static final String KEY_EXISTINGMAILBAGVO = "existingMailBagVO";
	private static final String KEY_MAILCOMMIDITYCODE = "mailcommiditycode";
	

	private static final String KEY_CONSGN = "consgn";
	private static final String KEY_POA = "poa";
	  /*
	 * Key for the WGT
	 */
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
	private static final String KEY_VOLUMEROUNDINGVO = "rounding_vol_vo";
	
	private static final String KEY_MAILDETAIL="currentmaildet";
	private static final String KEY_SELECTED_MAILDETAIL_VOS="selectedmailvos";
	private static final String KEY_UNMODIFIED_MAILDETAIL="unmodifiedmaildet";
	
	
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
	 * The setter method for MailAcceptanceVO
	 * @param mailAcceptanceVO
	 */
    public void setMailAcceptanceVO(MailAcceptanceVO mailAcceptanceVO) {
    	setAttribute(KEY_MAILACCEPTANCEVO,mailAcceptanceVO);
    }
    /**
     * The getter method for mailAcceptanceVO
     * @return mailAcceptanceVO
     */
    public MailAcceptanceVO getMailAcceptanceVO() {
    	return getAttribute(KEY_MAILACCEPTANCEVO);
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
     * This method is used to set ContainerDetailsVOs values to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		setAttribute(KEY_CONTAINERDETAILSVOS,(ArrayList<ContainerDetailsVO>)containerDetailsVOs);
	}

	/**
     * This method returns the ContainerDetailsVOs
     * @return KEY_CONTAINERDETAILSVOS - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
		return (Collection<ContainerDetailsVO>)getAttribute(KEY_CONTAINERDETAILSVOS);
	}
    
	/**
     * This method is used to set onetime values to the session
     * @param warehouse - Collection<WarehouseVO>
     */
	public void setWarehouse(Collection<WarehouseVO> warehouse) {
		setAttribute(KEY_WAREHOUSE,(ArrayList<WarehouseVO>)warehouse);
	}

	/**
     * This method returns the WarehouseVO
     * @return KEY_WAREHOUSE - Collection<WarehouseVO>
     */
	public Collection<WarehouseVO> getWarehouse() {
		return (Collection<WarehouseVO>)getAttribute(KEY_WAREHOUSE);
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
     * @param oneTimeMailClass - Collection<OneTimeVO>
     */
	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass) {
		setAttribute(KEY_ONETIMEMAILCLASS,(ArrayList<OneTimeVO>)oneTimeMailClass);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEMAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailClass() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILCLASS);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRSN - Collection<OneTimeVO>
     */
	public void setOneTimeRSN(Collection<OneTimeVO> oneTimeRSN) {
		setAttribute(KEY_ONETIMERSN,(ArrayList<OneTimeVO>)oneTimeRSN);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERSN - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRSN() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERSN);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHNI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEDAMAGECODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode) {
		setAttribute(KEY_ONETIMEDAMAGECODE,(ArrayList<OneTimeVO>)oneTimeDamageCode);
	}
	
	/**
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs() {
		return (Collection<DamagedMailbagVO>)getAttribute(DAMAGEMAILBAGVOS);
	}
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs) {
		setAttribute(DAMAGEMAILBAGVOS,(ArrayList<DamagedMailbagVO>)damagedMailbagVOs);
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
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType) {
		setAttribute(KEY_ONETIMECONTAINERTYPE,(ArrayList<OneTimeVO>)oneTimeContainerType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECONTAINERTYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECONTAINERTYPE);
	}
	
	/**
     * This method is used to set pous to the session
     * @param pous - Collection<String>
     */
	public void setPous(Collection<String> pous) {
		setAttribute(KEY_POUS,(ArrayList<String>)pous);
	}

	/**
     * This method returns the pous
     * @return KEY_POUS - Collection<String>
     */
	public Collection<String> getPous() {
		return (Collection<String>)getAttribute(KEY_POUS);
	}
	
	/**
     * This method is used to set Consignment values to the session
     * @param KEY_CONSGN - Collection<String>
     */
	public String getConsgnValues() {
		return (String)getAttribute(KEY_CONSGN);
	}

	public void setConsgnValues(String consgn) {
		setAttribute(KEY_CONSGN,(String)consgn);
	}

	public String getPaoValues() {
		return (String)getAttribute(KEY_POA);
	}

	public void setPaoValues(String pao) {
		setAttribute(KEY_POA,(String)pao);

		
	}
	
	public String getInventoryparameter() {
		return (String)getAttribute(KEY_INVENTORYPARAMETER);
	}

	public void setInventoryparameter(String inventoryparameter) {
		setAttribute(KEY_INVENTORYPARAMETER,(String)inventoryparameter);

		
	}

	public Collection<ExistingMailbagVO>getExistingMailbagVO(){
		
		return (Collection<ExistingMailbagVO>)getAttribute(KEY_EXISTINGMAILBAGVO);
	}

	public void setExistingMailbagVO(Collection<ExistingMailbagVO> existingMailbagVOS) {
		
		setAttribute(KEY_EXISTINGMAILBAGVO,(ArrayList<ExistingMailbagVO>)existingMailbagVOS);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param flightStatus - Collection<OneTimeVO>
     */
	public void setOneTimeFlightStatus(Collection<OneTimeVO> oneTimeFlightStatus) {
		setAttribute(KEY_ONETIMEFLIGHTSTATUS,(ArrayList<OneTimeVO>)oneTimeFlightStatus);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEFLIGHTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeFlightStatus() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEFLIGHTSTATUS);
	}
	
	/**
	 * Return the MailCommidityCode
	 * @return
	 */
	public String getMailCommidityCode() {
		return (String)getAttribute(KEY_MAILCOMMIDITYCODE);
	}

	/**
	 * Set the MailCommidityCode
	 * @param mailCommidityCode
	 */
	public void setMailCommidityCode(String mailCommidityCode) {
		setAttribute(KEY_MAILCOMMIDITYCODE,(String)mailCommidityCode);

		
	}
	
	
	/**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_VOLUMEROUNDINGVO, unitRoundingVO);
    }

    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getVolumeRoundingVO() {
    	return getAttribute(KEY_VOLUMEROUNDINGVO);
    }

    /**
     * @param key String
     */
    public void removeVolumeRoundingVO(String key) {
    }  
    
    
    /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
    }

    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() {
    	return getAttribute(KEY_WEIGHTROUNDINGVO);
    }

    /**
     * @param key String
     */
    public void removeWeightRoundingVO(String key) {
    }    
	
    /**
	 * @return Returns the MailDetailVO.
	 */
    public MailbagVO getCurrentMailDetail(){
    	return ((MailbagVO) getAttribute(KEY_MAILDETAIL));
    }
    /**
	 * @param MailDetailVO
	 */
    public void setCurrentMailDetail(MailbagVO mailDetailVO){
    	setAttribute(KEY_MAILDETAIL,
    			(MailbagVO) mailDetailVO);
    }

    /**
	 * @return Returns the ArrayList<MailDetailVO>.
     */
	public ArrayList<MailbagVO> getSelectedMailDetailsVOs() {
    	return ((ArrayList<MailbagVO>) getAttribute(KEY_SELECTED_MAILDETAIL_VOS));
	}

    /**
	 * @param mailDetailVOs
	 */
	public void setSelectedMailDetailsVOs(ArrayList<MailbagVO> mailDetailVOs) {
    	setAttribute(KEY_SELECTED_MAILDETAIL_VOS,(ArrayList<MailbagVO>)mailDetailVOs);
	}
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCompanyCode() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILCMPCODE);
	}

	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeCompanyCode(Collection<OneTimeVO> oneTimeCompanyCode) {
		setAttribute(KEY_ONETIMEMAILCMPCODE,(ArrayList<OneTimeVO>)oneTimeCompanyCode);
	}
	
	/**
	 * @return Returns the MailDetailVO.
	 */
    public MailbagVO getUnmodifiedMailDetail(){
    	return ((MailbagVO) getAttribute(KEY_UNMODIFIED_MAILDETAIL));
    }
    /**
	 * @param MailDetailVO
	 */
    public void setUnmodifiedMailDetail(MailbagVO mailDetailVO){
    	setAttribute(KEY_UNMODIFIED_MAILDETAIL,
    			(MailbagVO) mailDetailVO);
    }
}
