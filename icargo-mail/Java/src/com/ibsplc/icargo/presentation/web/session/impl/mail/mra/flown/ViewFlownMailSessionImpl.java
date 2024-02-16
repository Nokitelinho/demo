/*
 * ViewFlownMailSessionImpl.java Created on Feb 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.flown;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;

/**
 * @author a-2449
 *s
 */
public class ViewFlownMailSessionImpl extends AbstractScreenSession 
implements ViewFlownMailSession{
	
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	private static final String MODULE_CONSTANT = "mailtracking.mra.flown";
	
	private static final String KEY_ONETIME_VO = "onetimevalues";
	
	private static final String KEY_FLOWNMAILSEGMENT = "flownmailsegmentvo";
	
	private static final String KEY_FLOWNMAILFILTERVO = "flownmailfilter";
	
	private static final String KEY_FLIGHTDETAILS = "flightvalidationvo";
	
	private static final String KEY_SEGMENTDETAILS = "flightmailsegmantvos";
	
	private static final String KEY_LISTFILTER = "flownmailfiltervo";
	
	
	
	/**
	 * @return Returns the ScreenId
	 */
	public String getScreenID() {
		return SCREENID;
	}
	
	
	/**
	 * @return Returns the Module name
	 */
	public String getModuleName() {
		return MODULE_CONSTANT;
	}
	
	
	/**
	 * @return HashMap<String, Collection<OneTimeVO>>
	 */
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){
		
		return getAttribute(KEY_ONETIME_VO);
		
	}
	
	
	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){
		
		setAttribute(KEY_ONETIME_VO, oneTimeVOs);
		
	}
	
	
	/**
	 * @param flownMailSegmentVO
	 */
	public void setFlownMailSegmentVO(FlownMailSegmentVO flownMailSegmentVO) {
		setAttribute(KEY_FLOWNMAILSEGMENT,flownMailSegmentVO);
		
	}
	
	/**
	 * @return FlownMailSegmentVO
	 */
	public FlownMailSegmentVO getFlownMailSegmentVO() {
		
		return getAttribute(KEY_FLOWNMAILSEGMENT);
		
	}
	/**
	 * 
	 * remove folwnMailSegmentVO
	 */
	
	
	public void removeFlownMailSegmentVO() {
		removeAttribute(KEY_FLOWNMAILSEGMENT);
		
	}
	
	
	/**
	 * @param flownMailFilterVO
	 */
	public void setFilter(FlownMailFilterVO flownMailFilterVO){
		setAttribute(KEY_FLOWNMAILFILTERVO,flownMailFilterVO);
	}
	
	/**
	 * @return FlownMailFilterVO
	 */
	public FlownMailFilterVO getFilter(){
		return getAttribute(KEY_FLOWNMAILFILTERVO);
	}
	
	
	/**
	 * @param flightValidationVO
	 */
	public void setFlightDetails(FlightValidationVO flightValidationVO){
		setAttribute(KEY_FLIGHTDETAILS,flightValidationVO);
	}
	
	
	/**
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightDetails(){
		return getAttribute(KEY_FLIGHTDETAILS);
	}
	
	
	
	/**
	 * 
	 * remove flightValidationVO
	 */
	public void removeFlightDetails(){
		removeAttribute(KEY_FLIGHTDETAILS);
	}
	
	
	/**
	 * @param flownMailSegmentVOs
	 */
	public void setSegmentDetails(Collection<FlownMailSegmentVO> flownMailSegmentVOs){
		setAttribute(KEY_SEGMENTDETAILS,
				(ArrayList<FlownMailSegmentVO>)flownMailSegmentVOs);
	}
	
	/**
	 * @return Collection<FlownMailSegmentVO>
	 */
	public Collection<FlownMailSegmentVO> getSegmentDetails(){
		return (Collection<FlownMailSegmentVO>)getAttribute(KEY_SEGMENTDETAILS);
	}
	
	/**
	 * 
	 * remove folwnMailSegmentVO
	 */
	public void removeSegmentDetails(){
		removeAttribute(KEY_SEGMENTDETAILS);
	}
	
	/**
	 * @return FlownMailFilterVO
	 */
	public FlownMailFilterVO getListFilterDetails(){
		return getAttribute(KEY_LISTFILTER);
	}
	
	/**
	 * @param FlownMailFilterVO
	 */
	public void setListFilterDetails(FlownMailFilterVO flownMailFilterVO){
		setAttribute(KEY_LISTFILTER, flownMailFilterVO);
	}
	
	/**
	 * 
	 * remove FlownmailFilterVO
	 */
	public void removeListFilterDetails(){
		removeAttribute(KEY_LISTFILTER);
	}
	
}
