/*
 * MaintainULDSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNavigationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

/**
 * @author A-1347
 *
 */
public interface MaintainULDSession extends ScreenSession {
	    
    
    /**
     * 
     * @param uldVO
     */
    void setULDVO(ULDVO uldVO);
	
	/**
	 * 
	 * @return ULDVO
	 */		
    ULDVO getULDVO();
    
    /**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	/**
	 * 
	 * @return Returns the uldNumbers
	 */
    ArrayList<String> getUldNumbers();
    
    /**
     * 
     * @return Returns the multiple uld vos
     */
    HashMap<String,ULDVO> getULDMultipleVOs();
    
    /**
     * 
     * @param multipleVOs
     */
    void setULDMultipleVOs(HashMap<String,ULDVO> multipleVOs);
    
    /**
     * 
     * @return Returns the uld numbers used for navigation
     */
    ArrayList<String>  getUldNosForNavigation();
    
    /**
     * 
     * @param uldNosForNavigation
     */
    void setUldNosForNavigation(ArrayList<String> uldNosForNavigation);
    
    /**
     * 
     * @param uldNumbers
     */
    void setUldNumbers(ArrayList<String> uldNumbers);
    

	/**
	 * @return Returns the uldNumbersSaved.
	 */
	ArrayList<String> getUldNumbersSaved();

	/**
	 * @param uldNumbersSaved The uldNumbersSaved to set.
	 */
	void setUldNumbersSaved(ArrayList<String> uldNumbersSaved);
	
	/**
	 * @return Returns the currencies.
	 */
	ArrayList<CurrencyVO> getCurrencies();

	/**
	 * @param currencies The currencies to set.
	 */
	void setCurrencies(ArrayList<CurrencyVO> currencies);
	
	/**
	 * @return Returns the temporaryUldVO.
	 */
	public ULDVO getTemporaryUldVO();

	/**
	 * @param temporaryUldVO The temporaryUldVO to set.
	 */
	public void setTemporaryUldVO(ULDVO temporaryUldVO);
	/**
	 * @return Returns the uldNavigationVO.
	 */
	public ULDNavigationVO getUldNavigationVO();

	/**
	 * @param uldNavigationVO The uldNavigationVO to set.
	 */
	public void setUldNavigationVO(ULDNavigationVO uldNavigationVO);
	/**
	 * 
	 * @return
	 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
 * 
 * @param uldFlightMessageReconcileDetailsVO
 */
	public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);
	/**
	 * 
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getSCMReconcileDetailsVO();
	/**
	 * 
	 * @param scmReconcileDetailsVO
	 */
	public void setSCMReconcileDetailsVO(ULDSCMReconcileDetailsVO scmReconcileDetailsVO);
	/**
	 * 
	 * @return
	 */
	
	public Collection<ULDAirportLocationVO> getFacilityTypes();
/**
 * 
 * @param uldStockConfigVOs
 */
	public void setFacilityTypes(Collection<ULDAirportLocationVO> uldStockConfigVOs);
	/**
	 * 
	 * @return
	 */
	public String getPageURL();
/**
 * 
 * @param pageurl
 */
	public void setPageURL(String pageurl);

	public String getUldGroupCode();
	public void setUldGroupCode(String uldGroupCode);
	
	
	/**
	 * Sets the weight vo.
	 *
	 * @param unitRoundingVO the new weight vo
	 */
	void setWeightVO(UnitRoundingVO unitRoundingVO);

	/**
	 * Gets the weight vo.
	 *
	 * @return KEY_WEIGHTVO weightVO
	 */
	UnitRoundingVO getWeightVO();

   /*
	* Added by A-3415 for ICRD-113953.
	*/
    public void setNonOperationalDamageCodes(String nonOperationalDamageCodes);
    public String getNonOperationalDamageCodes();


}
