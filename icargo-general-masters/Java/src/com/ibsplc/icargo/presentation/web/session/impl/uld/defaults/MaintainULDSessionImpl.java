/*
 * MaintainULDSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;

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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;

/**
 * @author A-1347
 * 
 */
public class MaintainULDSessionImpl extends AbstractScreenSession implements
		MaintainULDSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.maintainuld";

	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	private static final String KEY_ULDVO = "uLDVO";

	private static final String KEY_MULTIPLEULDVOS = "uLDMultipleVOs";

	private static final String KEY_MULTIPLEULDS = "uldNumbers";

	private static final String KEY_MULTIPLEULDSSAVED = "uldNumbersSaved";

	private static final String KEY_ULDNUMBERSFORNAVIGATION = "uldNosForNavigation";

	private static final String KEY_CURRENCIES = "currencies";

	private static final String KEY_ULDNAVIGATIONVO = "uldNavigationVO";

	private static final String KEY_TEMPULDVO = "temporaryUldVO";

	private static final String KEY_LOCATION = "LocationVO";

	private static final String KEY_RECONCILEVO = "ReconcileVO";

	private static final String KEY_SCMRECONCILEVO = "SCMReconcileVO";

	private static final String KEY_PAGEURL = "pageurl";
	
	private static final String KEY_ULDGROUPCODE = "uldGroupCode";
	
	private static final String KEY_WEIGHTVO = "weightunitroundingvo";
	//Added by A-3415 for ICRD-113953.
	private static final String NONOPERATIONAL_DAMAGE = "nonOperationalDamageCodes";
	
	

	/**
	 * @return Returns the temporaryUldVO.
	 */
	public ULDVO getTemporaryUldVO() {
		return getAttribute(KEY_TEMPULDVO);
	}

	/**
	 * @param temporaryUldVO
	 *            The temporaryUldVO to set.
	 */
	public void setTemporaryUldVO(ULDVO temporaryUldVO) {
		setAttribute(KEY_TEMPULDVO, temporaryUldVO);
	}

	/**
	 * @return Returns the uLDMultipleVOs.
	 */
	public HashMap<String, ULDVO> getULDMultipleVOs() {
		return getAttribute(KEY_MULTIPLEULDVOS);
	}

	/**
	 * @param multipleVOs
	 *            The uLDMultipleVOs to set.
	 */
	public void setULDMultipleVOs(HashMap<String, ULDVO> multipleVOs) {
		setAttribute(KEY_MULTIPLEULDVOS, multipleVOs);
	}

	/**
	 * @return Returns the uldNosForNavigation.
	 */
	public ArrayList<String> getUldNosForNavigation() {
		return (ArrayList<String>) getAttribute(KEY_ULDNUMBERSFORNAVIGATION);
	}

	/**
	 * @param uldNosForNavigation
	 *            The uldNosForNavigation to set.
	 */
	public void setUldNosForNavigation(ArrayList<String> uldNosForNavigation) {
		setAttribute(KEY_ULDNUMBERSFORNAVIGATION,
				(ArrayList<String>) uldNosForNavigation);
	}

	/**
	 * @return Returns the uldNumbersSaved.
	 */
	public ArrayList<String> getUldNumbersSaved() {
		return (ArrayList<String>) getAttribute(KEY_MULTIPLEULDSSAVED);
	}

	/**
	 * @param uldNumbersSaved
	 *            The uldNumbersSaved to set.
	 */
	public void setUldNumbersSaved(ArrayList<String> uldNumbersSaved) {
		setAttribute(KEY_MULTIPLEULDSSAVED, (ArrayList<String>) uldNumbersSaved);
	}

	/**
	 * @return Returns the uldNumbers.
	 */
	public ArrayList<String> getUldNumbers() {
		return (ArrayList<String>) getAttribute(KEY_MULTIPLEULDS);
	}

	/**
	 * @param uldNumbers
	 *            The uldNumbers to set.
	 */
	public void setUldNumbers(ArrayList<String> uldNumbers) {
		setAttribute(KEY_MULTIPLEULDS, (ArrayList<String>) uldNumbers);
	}

	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}

	/**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}

	/**
	 * @return Returns the uldvo.
	 */
	public ULDVO getULDVO() {
		return getAttribute(KEY_ULDVO);
	}

	/**
	 * @param uldVO
	 *            The uldvo to set.
	 * 
	 */
	public void setULDVO(ULDVO uldVO) {
		setAttribute(KEY_ULDVO, uldVO);
	}

	/**
	 * @return Returns the currencies.
	 */
	public ArrayList<CurrencyVO> getCurrencies() {
		return getAttribute(KEY_CURRENCIES);
	}

	/**
	 * @param currencies
	 *            The currencies to set.
	 */
	public void setCurrencies(ArrayList<CurrencyVO> currencies) {
		setAttribute(KEY_CURRENCIES, currencies);
	}

	/**
	 * @return Returns the uldNavigationVO.
	 */
	public ULDNavigationVO getUldNavigationVO() {
		return getAttribute(KEY_ULDNAVIGATIONVO);
	}

	/**
	 * @param uldNavigationVO
	 *            The uldNavigationVO to set.
	 */
	public void setUldNavigationVO(ULDNavigationVO uldNavigationVO) {
		setAttribute(KEY_ULDNAVIGATIONVO, uldNavigationVO);
	}
/**
 * @return
 */
	public Collection<ULDAirportLocationVO> getFacilityTypes() {
		return (Collection<ULDAirportLocationVO>) getAttribute(KEY_LOCATION);
	}
	/**
	 * @param uldStockConfigVOs
	 */
	public void setFacilityTypes(
			Collection<ULDAirportLocationVO> uldStockConfigVOs) {
		setAttribute(KEY_LOCATION,
				(ArrayList<ULDAirportLocationVO>) uldStockConfigVOs);
	}
/**
 * 
 *
 */
	public void removeFacilityTypes() {
		removeAttribute(KEY_LOCATION);
	}
	/**
	 * @return
	 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO() {
		return getAttribute(KEY_RECONCILEVO);
	}
	/**
	 * @param uldFlightMessageReconcileDetailsVO
	 */
	public void setULDFlightMessageReconcileDetailsVO(
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO) {
		setAttribute(KEY_RECONCILEVO, uldFlightMessageReconcileDetailsVO);
	}
	/**
	 * @return
	 */
	public String getPageURL() {
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl) {
		setAttribute(KEY_PAGEURL, pageUrl);
	}
	/**
	 * @param scmReconcileDetailsVO
	 */
	public void setSCMReconcileDetailsVO(ULDSCMReconcileDetailsVO scmReconcileDetailsVO){
		setAttribute(KEY_SCMRECONCILEVO,scmReconcileDetailsVO);
	}
	/**
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getSCMReconcileDetailsVO(){
		return getAttribute(KEY_SCMRECONCILEVO);
	}
	public String getUldGroupCode() {
		return getAttribute(KEY_ULDGROUPCODE);
	}
	public void setUldGroupCode(String uldGroupCode) {
		setAttribute(KEY_ULDGROUPCODE, uldGroupCode);
	}
	
	
	 /**
 	 * Sets the weight vo.
 	 *
 	 * @param unitRoundingVO the new weight vo
 	 */
    public void setWeightVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_WEIGHTVO, unitRoundingVO);
    }

    /**
     * Gets the weight vo.
     *
     * @return KEY_WEIGHTVO veightVO
     */
    public UnitRoundingVO getWeightVO() {
    	return getAttribute(KEY_WEIGHTVO);
    }
    /*
	* Added by A-3415 for ICRD-113953.
	*/
	public void setNonOperationalDamageCodes(String nonOperationalDamageCodes) {
		setAttribute(NONOPERATIONAL_DAMAGE, nonOperationalDamageCodes);		
	}	
	public String getNonOperationalDamageCodes() {
		return (String)getAttribute(NONOPERATIONAL_DAMAGE);
	}
}
