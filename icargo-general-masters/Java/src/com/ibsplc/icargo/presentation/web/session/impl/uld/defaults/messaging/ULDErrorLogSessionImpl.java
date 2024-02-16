/*
 * ULDErrorLogSessionImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1862
 *
 */
public class ULDErrorLogSessionImpl extends AbstractScreenSession
		implements ULDErrorLogSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.ulderrorlog";
		
	private static final String KEY_PAGEURL="pageurl";	
	private static final String KEY_FORPIC = "pictureURL";
	private static final String LIST_DETAILS = "ULDFlightMessageReconcileDetailsVO";
	private static final String KEY_FILTERDETAILS = "filterDetails";
	private static final String KEY_RECONCILEVO = "ReconcileVO";
	private static final String KEY_UCMNO="ucmno";
	private static final String KEY_POU="pou";
	private static final String KEY_FLTVALVO="flightvalidationvo";
	private static final String KEY_CONTENT="content";
	
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
	public Page<ULDFlightMessageReconcileDetailsVO> getULDFlightMessageReconcileDetailsVOs(){
    	return (Page<ULDFlightMessageReconcileDetailsVO>)getAttribute(LIST_DETAILS);
    }
	/**
	 * @param paramCode
	 */
	public void setULDFlightMessageReconcileDetailsVOs(Page<ULDFlightMessageReconcileDetailsVO> paramCode){
		setAttribute(LIST_DETAILS, (Page<ULDFlightMessageReconcileDetailsVO>)paramCode);
	}
	/**
	 * 
	 *
	 */
	public void removeULDFlightMessageReconcileDetailsVOs(){
		removeAttribute(LIST_DETAILS);
	}
	/**
	 * @return
	 */
	public FlightFilterMessageVO getFlightFilterMessageVOSession() {
		return getAttribute(KEY_FILTERDETAILS);
	}
	/**
	 * @param flightFilterMessageVO
	 */
	public void setFlightFilterMessageVOSession(
			FlightFilterMessageVO flightFilterMessageVO) {
		setAttribute(KEY_FILTERDETAILS, flightFilterMessageVO);
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
//	newly added starts
	/**
	 * @param ucmNos
	 */
	public void setUcmNumberValues(Collection<String> ucmNos){
		setAttribute(KEY_UCMNO, (ArrayList<String>) ucmNos);
	}
	
	/**
	 * @return
	 */
	public ArrayList<String> getUcmNumberValues(){
		return (ArrayList<String>)getAttribute(KEY_UCMNO);
	}
	/**
	 * 
	 */
	public void removeUcmNumberValues(){
		removeAttribute(KEY_UCMNO);
	}
	
	/**
	 * @param ucmNos
	 */
	public void setPouValues(Collection<String> ucmNos){
		setAttribute(KEY_POU, (ArrayList<String>) ucmNos);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getPouValues(){
		return (ArrayList<String>)getAttribute(KEY_POU);
	}
	/**
	 * 
	 */
	public void removePouValues(){
		removeAttribute(KEY_POU);
	}
	
	/**
	 * @return
	 */
	public FlightValidationVO getFlightValidationVO(){
		return getAttribute(KEY_FLTVALVO);
	}
	/**
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO){
		setAttribute(KEY_FLTVALVO,flightValidationVO);
	}
	/**
	 * 
	 */
	public void removeFlightValidationVO(){
		removeAttribute(KEY_FLTVALVO);
	}
	//newly added ends
	
	 public ArrayList<OneTimeVO> getContent(){
		 return getAttribute(KEY_CONTENT);
	 }
	    
	 public void setContent(ArrayList<OneTimeVO> content){
		 setAttribute(KEY_CONTENT, content);
	 }
}
