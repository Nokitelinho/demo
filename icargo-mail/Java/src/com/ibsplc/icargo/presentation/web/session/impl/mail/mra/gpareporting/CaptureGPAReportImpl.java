/*
 * CaptureGPAReportImpl.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *  
 * @author A-2257
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 21, 2007			a-2257		Created
 */
public class CaptureGPAReportImpl extends AbstractScreenSession implements
							CaptureGPAReportSession {

	private static final String MODULE_NAME = "mailtraking.mra";
	
	
	private static final String SCREEN_ID = "mailtracking.mra.gpareporting.capturegpareport";
	/**
	 * Constant for the session variable 
	 */
	private static final String KEY_GPAREPRTFILTERVO = "gpaReportingFilterVO";
	
	private static final String KEY_GPAREPORTDETAILSVO = "gpaReportingDetailsVO";
	
	private static final String KEY_GPAREPORTDETAILSVOPAGE = "gpaReportingDetailsPage";
	
	//private static final String KEY_GPAREPORTDETAILSVOCOLL = "gpaReportingDetailsVOs";
	
	private static final String KEY_GPAREPORTDETAILSVOSMODIFIED = "modifiedDetailsVOs";
	
	private static final String KEY_PARENTSCREENFLAG = "parentScreenID";
	
	private static final String KEY_MAILSTATUS = "mailstatus";
	
	private static final String KEY_MAILCATEGORY = "mailcategory";
	
	private static final String KEY_HIGHESTNUM_ONETIME = "highestnumbermail";
	
	private static final String KEY_REGORINSIND_ONETIME = "registeredorinsuredcode";
	
	private static final String KEY_INDEX = "index";
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
		
	/**
	 * @return screenID 
	 */
    public String getScreenID() {

        return SCREEN_ID;
    }
    /**
     * 
     * TODO Purpose
     * Feb 21, 2007, a-2257
     * @return
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 * @return
	 */
	public GPAReportingFilterVO getGPAReportingFilterVO (){
		return getAttribute(KEY_GPAREPRTFILTERVO);
	}
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 * @param gpaReportingFilterVO
	 */
	public void setGPAReportingFilterVO(GPAReportingFilterVO gpaReportingFilterVO) {
		setAttribute(KEY_GPAREPRTFILTERVO,gpaReportingFilterVO);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 */
	public void removeGPAReportingFilterVO () {
		// TODO Auto-generated method stub
		removeAttribute(KEY_GPAREPRTFILTERVO);
	}
	
    
//   /**
//    * 
//    * To get the collection of AWBs modified,inserted or deleted after getting from server
//    * Nov 21, 2006, A-2257
//    * @return
//    * @see com.ibsplc.icargo.presentation.web.session.interfaces.cra.airlinebilling.inward.CaptureInvoiceDetailsSession#getAwbInInvoiceVOs()
//    */
//	public Collection<GPAReportingDetailsVO> getGPAReportingDetailsVOs(){
//		return (Collection<GPAReportingDetailsVO>)getAttribute(KEY_GPAREPORTDETAILSVOCOLL);
//	}
//	
//	/**
//	 * 
//	 * TODO Purpose
//	 * Feb 21, 2007, a-2257
//	 * @param gpaReportingDetailsVOs
//	 */
//	public void setGPAReportingDetailsVOs(Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs) {
//		setAttribute(KEY_GPAREPORTDETAILSVOCOLL,(ArrayList<GPAReportingDetailsVO>) gpaReportingDetailsVOs);
//	}
//	
//	/**
//	 * 
//	 * TODO Purpose
//	 * Feb 21, 2007, a-2257
//	 */
//	public void removeGPAReportingDetailsVOs() {
//		// TODO Auto-generated method stub
//		removeAttribute(KEY_GPAREPORTDETAILSVOCOLL);
//	}
	 /**
	  * 
	  * TODO Purpose
	  * Feb 21, 2007, a-2257
	  * @return
	  */
	public Page<GPAReportingDetailsVO> getGPAReportingDetailsPage(){
		return (Page<GPAReportingDetailsVO>)getAttribute(KEY_GPAREPORTDETAILSVOPAGE);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 * @param gpaReportingDetailsPage
	 */
	public void setGPAReportingDetailsPage(Page<GPAReportingDetailsVO> gpaReportingDetailsPage){
		setAttribute(KEY_GPAREPORTDETAILSVOPAGE,(Page<GPAReportingDetailsVO>) gpaReportingDetailsPage);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 */
	public void removeGPAReportingDetailsPage() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_GPAREPORTDETAILSVOPAGE);
	}
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 * @return
	 */	
	public Collection<GPAReportingDetailsVO> getModifiedGPAReportingDetailsVOs(){
		return (Collection<GPAReportingDetailsVO>)getAttribute(KEY_GPAREPORTDETAILSVOSMODIFIED);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 * @param modifiedDetailsVOs
	 */
	public void setModifiedGPAReportingDetailsVOs(Collection<GPAReportingDetailsVO> modifiedDetailsVOs){
		setAttribute(KEY_GPAREPORTDETAILSVOSMODIFIED,(ArrayList<GPAReportingDetailsVO>) modifiedDetailsVOs);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#removeModifiedGPAReportingDetailsVOs()
	 */
	public void removeModifiedGPAReportingDetailsVOs() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_GPAREPORTDETAILSVOSMODIFIED);
	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @return
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#getSelectedGPAReportingDetailsVO()
	 */
	public GPAReportingDetailsVO getSelectedGPAReportingDetailsVO(){
		return (GPAReportingDetailsVO)getAttribute(KEY_GPAREPORTDETAILSVO);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param gpaReportingDetailsVO
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#setSelectedGPAReportingDetailsVO(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO)
	 */
	public void setSelectedGPAReportingDetailsVO(GPAReportingDetailsVO gpaReportingDetailsVO){
		setAttribute(KEY_GPAREPORTDETAILSVO,(GPAReportingDetailsVO) gpaReportingDetailsVO);
	}
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 21, 2007, a-2257
	 */
	public void removeSelectedGPAReportingDetailsVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_GPAREPORTDETAILSVO);
	}
	
	/**
     * @return KEY_FLOWNINDEX HashMap <String, String>
     */
	public HashMap <String, String> getIndexMap() {
		return getAttribute(KEY_INDEX);
	}
	/**
     * @param indexmap HashMap <String, String>
     */
	public void setIndexMap(HashMap <String, String> indexmap) {
		setAttribute(KEY_INDEX, indexmap);
	}

	/**
     * @param key String
     */
	public void removeIndexMap(String key) {
		removeAttribute(KEY_INDEX);
	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 16, 2007, a-2257
	 * @return
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#getMailStatus()
	 */
	public ArrayList<OneTimeVO> getMailStatus() {
		return getAttribute(KEY_MAILSTATUS);
	}
	
	public void setMailStatus(ArrayList<OneTimeVO> mailStatus){
		// TODO Auto-generated method stub
		setAttribute(KEY_MAILSTATUS,mailStatus);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 9, 2007, a-2257
	 * @return
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#getMailCategory()
	 */
	public ArrayList<OneTimeVO> getMailCategory() {
		return getAttribute(KEY_MAILCATEGORY);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 9, 2007, a-2257
	 * @param mailCategory
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#setMailCategory(java.util.ArrayList)
	 */
	public void setMailCategory(ArrayList<OneTimeVO> mailCategory){
		// TODO Auto-generated method stub
		setAttribute(KEY_MAILCATEGORY,mailCategory);
	}
	
	public ArrayList<OneTimeVO> getHeighestNum() {
		return getAttribute(KEY_HIGHESTNUM_ONETIME);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 9, 2007, a-2257
	 * @param mailCategory
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#setMailCategory(java.util.ArrayList)
	 */
	public void setHeighestNum(ArrayList<OneTimeVO> heighestNum){
		// TODO Auto-generated method stub
		setAttribute(KEY_HIGHESTNUM_ONETIME,heighestNum);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @return
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#getRegOrInsInd()
	 */
	public ArrayList<OneTimeVO> getRegOrInsInd() {
		return getAttribute(KEY_REGORINSIND_ONETIME);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @param regOrInsInd
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession#setRegOrInsInd(java.util.ArrayList)
	 */
	public void setRegOrInsInd(ArrayList<OneTimeVO> regOrInsInd){
		// TODO Auto-generated method stub
		setAttribute(KEY_REGORINSIND_ONETIME,regOrInsInd);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 4, 2007, a-2257
	 * @return
	 */
	public String getParentScreenFlag(){
		return getAttribute(KEY_PARENTSCREENFLAG);
	}
	/*
	 * 
	 */
	public void setParentScreenFlag(String parentScreenFlag){
		setAttribute(KEY_PARENTSCREENFLAG, parentScreenFlag);
	}
	/**
	 * 
	 * TODO Purpose
	 * Apr 4, 2007, a-2257
	 */
	public void removeParentScreenFlag(){
		removeAttribute(KEY_PARENTSCREENFLAG);
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
}
