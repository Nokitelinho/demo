/*
 * MaintainDamageReportSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1347
 *
 */
public class MaintainDamageReportSessionImpl extends AbstractScreenSession
		implements MaintainDamageReportSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	/*
	 * Key for oneTimeValues
	 */
	private static final String KEY_ONETIMEVALUES
		= "uld_defaults_maintaindmg_onetimevalues";
	
	private static final String KEY_ULDDMG = "uldDamageRepairDetailsVO";
	private static final String KEY_SAVULDDMG = "ULDDmgRepairDetailsVO";
	private static final String KEY_ULDDMGS = "uldDamageVO";
	private static final String KEY_ULDREPS = "uldRepairVO";
	private static final String KEY_CURRENCIES = "currencies";
	private static final String KEY_REFNO = "ref_no";
	private static final String KEY_ULDNUMBERS="uldNumbers";
	private static final String KEY_PAGEURL="pageurl";
	private static final String KEY_DAMAGEPICVO = "ULDDamagePictureVO";
	private static final String KEY_FORPIC = "pictureURL";
	private static final String KEY_RECONCILEVO = "ReconcileVO";
	private static final String KEY_FORPICSESSION = "PicSession";
	
	//Added by Saritha
	private static final String KEY_ULDNUM = "uldnumberkey";
	private static final String KEY_SCREENID = "screenidkey";
	
	private static final String DAMAGETOTAL = "damageTotalPoint";
	private static final String DAMAGE_SYSPAR = "damagesyspar";
	//Added by Tarun on 24Mar08 for AirNZ_CR_418
	private static final String CURRENCY="currency";
	//added by jisha for QF1022
	private static final String LIST_DETAILS="uldDamageRepairDetailsVOPage";//corrected by A-5505 for the bug ICRD-123522  
	private static final String KEY_DAMAGEREPAIRFILTERVO = "UldDmgRprFilterVO";
	private static final String LIST_DAMAGECHECKLISTVO="uldDamageCheckListDetailsVO";
	//Added by A-3415 for ICRD-113953.
	private static final String NONOPERATIONAL_DAMAGE = "nonOperationalDamageCodes";
	

	/**
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
	 * Method to set the 
	 * ULDDamageRepairDetailsVO
	 * @return ULDDamageRepairDetailsVO
	 */
	public ULDDamageRepairDetailsVO getULDDamageVO() {
		return (ULDDamageRepairDetailsVO)getAttribute(KEY_ULDDMG);
	}
	
	/**
	 * @param uldDamageVO
	 */
	public void setULDDamageVO(ULDDamageRepairDetailsVO uldDamageVO) {
		 setAttribute(KEY_ULDDMG,uldDamageVO);
	}
	
	   /**
	 * Method to set the 
	 * ULDDamageRepairDetailsVO
	 * @return ULDDamageRepairDetailsVO
	 */
	public ULDDamageRepairDetailsVO getSavedULDDamageVO() {
		return (ULDDamageRepairDetailsVO)getAttribute(KEY_SAVULDDMG);
	}
	/**
	 * Method to set the 
	 * ULDDamageRepairDetailsVO
	 * @param uldDamageVO
	 */

	public void setSavedULDDamageVO(ULDDamageRepairDetailsVO uldDamageVO) {
		 setAttribute(KEY_SAVULDDMG,uldDamageVO);
	}
	
    /**
     * 
     * @return
     */
   public Collection<ULDDamageVO> getULDDamageVOs(){
	   return (Collection<ULDDamageVO>) 
	   		getAttribute(KEY_ULDDMGS);
   }
   /**
    * 
    * @param uldDamageVOs
    */
   public void setULDDamageVOs(Collection<ULDDamageVO> uldDamageVOs){
	   setAttribute(
			   KEY_ULDDMGS, (ArrayList<ULDDamageVO>)uldDamageVOs);
   }
   /**
    * 
    * @return
    */
  public Collection<ULDRepairVO> getULDRepairVOs(){
	   return (Collection<ULDRepairVO>) 
	   		getAttribute(KEY_ULDREPS);
  }
  /**
   * 
   * @param uldRepairVOs
   */
  public void setULDRepairVOs(Collection<ULDRepairVO> uldRepairVOs){
	   setAttribute(
			   KEY_ULDREPS, (ArrayList<ULDRepairVO>)uldRepairVOs);
  }
   
	/** (non-Javadoc)
     * @return oneTimeValues(HashMap<String, Collection<OneTimeVO>>)
     */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
        return getAttribute(KEY_ONETIMEVALUES);
    }


    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.operations.interfaces.AcceptanceSessionInterface
     * #setOneTimeValues(java.util.HashMap<java.lang.String, com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO>)
     * @param oneTimeValues
     */
    public void setOneTimeValues(
    		HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
    	setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
    }
    /**
	 * @return Returns the currencies.
	 */
	public ArrayList<CurrencyVO> getCurrencies() {
		return getAttribute(KEY_CURRENCIES);
	}

	/**
	 * @param currencies The currencies to set.
	 */
	public void setCurrencies(ArrayList<CurrencyVO> currencies) {
		setAttribute(KEY_CURRENCIES,currencies);
	}
	
	   /**
	 * @return Returns the currencies.
	 */
	public ArrayList<String> getRefNo() {
		return getAttribute(KEY_REFNO);
	}

	/**
	 * @param refNo
	 */
	public void setRefNo(ArrayList<String> refNo) {
		setAttribute(KEY_REFNO,refNo);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getUldNumbers(){
		return getAttribute(KEY_ULDNUMBERS);
	}
	/**
	 * @param uldNumbers
	 */
	public void setUldNumbers(ArrayList<String> uldNumbers){
		setAttribute(KEY_ULDNUMBERS,uldNumbers);
	}
	/**
	 * @return
	 */
	public String getPageURL(){
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl){
		setAttribute(KEY_PAGEURL,pageUrl);
	}
	/**
	 * @param uldDamagePictureVO
	 */
	public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO) {
		setAttribute(KEY_DAMAGEPICVO, uldDamagePictureVO);
	}
	/**
	 * @return
	 */
	public ULDDamagePictureVO getULDDamagePictureVO() {
		return getAttribute(KEY_DAMAGEPICVO);
	}
	/**
	 * 
	 */
	public void removeULDDamagePictureVO() {
		removeAttribute(KEY_DAMAGEPICVO);
	}
	/**
	 * @return
	 */
	public String getForPic(){
		return getAttribute(KEY_FORPIC);
	}
	/**
	 * @param pic
	 */
	public void setForPic(String pic){
		setAttribute(KEY_FORPIC,pic);
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
	public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO) {
		setAttribute(KEY_RECONCILEVO,uldFlightMessageReconcileDetailsVO);
	}
	
	/**
	 * @return 
	 */
	public String getUldNumber(){
		return getAttribute(KEY_ULDNUM);
	}
	
	/**
	 * 
	 */
	public void setUldNumber(String uldNumber){
		setAttribute(KEY_ULDNUM, uldNumber);
	}
	
	
	public void removeUldNumber(){
		removeAttribute(KEY_ULDNUM);
	}
	/**
	 * @return 
	 */
	public String getParentScreenId(){
		return getAttribute(KEY_SCREENID);
	}
	/**
	 * 
	 */
	public void setParentScreenId(String screenId){
		setAttribute(KEY_SCREENID, screenId);
	}
	/**
	 * 
	 */
	public void removeParentScreenId(){
		removeAttribute(KEY_SCREENID);
	}

	/**
	 * 
	 */
	public String getDefaultCurrency() {
		return getAttribute(CURRENCY);
	}
	/**
	 * @return 	
	 */
	public void setDefaultCurrency(String currency) {
		setAttribute(CURRENCY, currency);
		
	}
	/**
	 * @author a-3093
	 * @return Returns the kEY_DAMAGEREPAIRFILTERVO.
	 */
	public UldDmgRprFilterVO getUldDmgRprFilterVO() {
		return getAttribute(KEY_DAMAGEREPAIRFILTERVO);
				
	}
	
	/**
	 * @author a-3093
     * @return
     */

	public void setUldDmgRprFilterVO(UldDmgRprFilterVO uldRepairFilterVO) {
		setAttribute(KEY_DAMAGEREPAIRFILTERVO, uldRepairFilterVO);
		// To be reviewed Auto-generated method stub
		
	}
	/**
	 * @author a-3093
	 */
	public Page<ULDRepairDetailsListVO> getULDDamageRepairDetailsVOs(){
    	return (Page<ULDRepairDetailsListVO>)getAttribute(LIST_DETAILS);
    }
	/**
	 * @author a-3093
     * This method sets the ULDRepairDetailsListVO in session
     * @param paramCode
     */
	public void setULDDamageRepairDetailsVOs(Page<ULDRepairDetailsListVO> paramCode){
		setAttribute(LIST_DETAILS, (Page<ULDRepairDetailsListVO>)paramCode);
	}
	/**
	 * @author a-3093
     * This method removes the ULDRepairDetailsListVO in session
     */
	public void removeULDDamageRepairDetailsVOs(){
		removeAttribute(LIST_DETAILS);
	}
	
	/**
	 * @author a-3093
	 */
	public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO(){
    	return (Collection<ULDDamageChecklistVO>)getAttribute(LIST_DAMAGECHECKLISTVO);
    }
	/**
	 * @author a-3093
     * This method sets the ULDRepairDetailsListVO in session
     * @param paramCode
     */
	public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> damageCheckListVO){
		setAttribute(LIST_DAMAGECHECKLISTVO, (ArrayList<ULDDamageChecklistVO>)damageCheckListVO);
	}
	/**
	 * @author a-3093
     * This method removes the ULDRepairDetailsListVO in session
     */
	public void removeULDDamageChecklistVO(){
		removeAttribute(LIST_DAMAGECHECKLISTVO);
	}

	public int getDamageTotalPoint() {
		// To be reviewed Auto-generated method stub
		return (Integer)getAttribute(DAMAGETOTAL);
	}

	public int getTotalDamagePoints() {
		// To be reviewed Auto-generated method stub
		return (Integer)getAttribute(DAMAGE_SYSPAR);
	}

	public void setDamageTotalPoint(int damageTotalPoints) {
		setAttribute(DAMAGETOTAL, damageTotalPoints);
		
	}

	public void setTotalDamagePoints(int totalDamagePoints) {
		
		setAttribute(DAMAGE_SYSPAR, totalDamagePoints);
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
