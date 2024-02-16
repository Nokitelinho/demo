/*
 * CaptureCN66SessionImpl.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2408
 *
 */
public class CaptureCN66SessionImpl extends AbstractScreenSession implements CaptureCN66Session {

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_CN66DETAILS="cn66details";
	private static final String KEY_CN66DETAILSMAP="cn66detailsmap";
	private static final String KEY_CN66DETAILSMODIFIEDMAP="cn66detailsmodifiedmap";
	private static final String KEY_MODIFIEDCN66DETAILS="modifiedcn66details";
	private static final String KEY_PREVIOUSCN66DETAILS="previouscn66details";
	private static final String KEY_KEYVALUES="keyvalues";
	private static final String KEY_PARENTID="parentid";
	private static final String KEY_CN66FILTER="cn66filtervo";
	private static final String KEY_SCREENSTATUS="screenstatus"; 
	private static final String KEY_CN66DETAILSVO="cn66DetailsVo";
	private static final String KEY_ARLC66DTLVOS="cn66DetailsVos";
	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";
	private static final String KEY_ONETIMERI = "oneTimeRI";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
    private static final  String KEY_VOLUMEROUNDINGVO = "volumeRounding";
	private static final  String KEY_WEIGHTROUNDINGVO = "weightRounding";
	 /**
     * @return
     */
    @Override
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**

    *

    * @return

    */

    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

    return getAttribute(KEY_ONETIME_VOS);

    }
    /**

    *

    * @param oneTimeVOs

    */

    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

    }
    /**

    *

    *remove onetimes

    */

    public void removeOneTimeVOs() {

    removeAttribute(KEY_ONETIME_VOS);

    }
    /**
	 * @return
	 */
    public ArrayList<AirlineCN66DetailsVO> getCn66Details(){
    	return (ArrayList<AirlineCN66DetailsVO>)getAttribute(KEY_CN66DETAILS);
    }
    /**
	 * @param cn66Details
	 */
    public void setCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details){
    	setAttribute(KEY_CN66DETAILS, (ArrayList<AirlineCN66DetailsVO>)cn66Details);
    }
    /**
	 * 
	 */
    public void removeCn66Details(){
    	removeAttribute(KEY_CN66DETAILS);
    }
    /**
	 * @return
	 */
	public HashMap<String, Collection<AirlineCN66DetailsVO>> getCn66DetailsMap(){
		return (HashMap<String, Collection<AirlineCN66DetailsVO>>)getAttribute(KEY_CN66DETAILSMAP);
	}
	/**
	 * @param cn66details
	 */
	public void setCn66DetailsMap(HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details){
		setAttribute(KEY_CN66DETAILSMAP, (HashMap<String, Collection<AirlineCN66DetailsVO>>)cn66details);
	}
	/**
	 * 
	 */
	public void removeCn66DetailsMap(){
		removeAttribute(KEY_CN66DETAILSMAP);
	}
	/**
	 * @return
	 */
	public HashMap<String, Collection<AirlineCN66DetailsVO>> getCn66DetailsModifiedMap(){
		return (HashMap<String, Collection<AirlineCN66DetailsVO>>)getAttribute(KEY_CN66DETAILSMODIFIEDMAP);
	}
	/**
	 * @param cn66details
	 */
	public void setCn66DetailsModifiedMap(HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details){
		setAttribute(KEY_CN66DETAILSMODIFIEDMAP, (HashMap<String, Collection<AirlineCN66DetailsVO>>)cn66details);
	}
	/**
	 * 
	 */
	public void removeCn66DetailsModifiedMap(){
		removeAttribute(KEY_CN66DETAILSMODIFIEDMAP);
	}
	/**
	 * @return
	 */
	public ArrayList<AirlineCN66DetailsVO> getPreviousCn66Details(){
		return (ArrayList<AirlineCN66DetailsVO>)getAttribute(KEY_PREVIOUSCN66DETAILS);
	}
	/**
	 * @param cn66Details
	 */
	public void setPreviousCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details){
		setAttribute(KEY_PREVIOUSCN66DETAILS, (ArrayList<AirlineCN66DetailsVO>)cn66Details);
	}
	/**
	 * remove
	 */
	public void removePreviousCn66Details(){
		removeAttribute(KEY_PREVIOUSCN66DETAILS);
	}
	/**
	 * @return
	 */
	public ArrayList<AirlineCN66DetailsVO> getModifiedCn66Details(){
		return (ArrayList<AirlineCN66DetailsVO>)getAttribute(KEY_MODIFIEDCN66DETAILS);
	}
	/**
	 * @param cn66Details
	 */
	public void setModifiedCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details){
		setAttribute(KEY_MODIFIEDCN66DETAILS, (ArrayList<AirlineCN66DetailsVO>)cn66Details);
	}
	/**
	 * remove
	 */
	public void removemodifiedCn66Details(){
		removeAttribute(KEY_MODIFIEDCN66DETAILS);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getKeyValues(){
		return (ArrayList<String>)getAttribute(KEY_KEYVALUES);
	}
	/**
	 * @param keyValues
	 */
	public void setKeyValues(ArrayList<String> keyValues){
		setAttribute(KEY_KEYVALUES,(ArrayList<String>)keyValues);
	}
	/**
	 * 
	 */
	public void removeKeyValues(){
		removeAttribute(KEY_KEYVALUES);
	}
	
	/**
	 * @return
	 */
	public String getParentId(){
		return getAttribute(KEY_PARENTID);
	}
	/**
	 * @param parentId
	 */
	public void setParentId(String parentId){
		setAttribute(KEY_PARENTID,parentId);
	}
	/**
	 * 
	 */
	public void removeParentId(){
		removeAttribute(KEY_PARENTID);
	}
	/**
	 * @return
	 */
	public AirlineCN66DetailsFilterVO getCn66FilterVO(){
		return (AirlineCN66DetailsFilterVO)getAttribute(KEY_CN66FILTER);
	}
	/**
	 * @param filterVO
	 */
	public void setCn66FilterVO(AirlineCN66DetailsFilterVO filterVO){
		setAttribute(KEY_CN66FILTER,(AirlineCN66DetailsFilterVO)filterVO);
	}
	/**
	 * 
	 */
	public void removeCn66FilterVO(){
		removeAttribute(KEY_CN66FILTER);
	}
	/**
	 * @return
	 */
	public String getPresentScreenStatus(){
		return getAttribute(KEY_SCREENSTATUS);
	}
	/**
	 * @param status
	 */
	public void setPresentScreenStatus(String status){
		setAttribute(KEY_SCREENSTATUS,status);
	}
	/**
	 * 
	 */
	public void removePresentScreenStatus(){
		removeAttribute(KEY_SCREENSTATUS);
	}
	/**
	 * @return
	 */
	public AirlineCN66DetailsVO getAirlineCN66DetailsVO(){
		return (AirlineCN66DetailsVO)getAttribute(KEY_CN66DETAILSVO);
	}
	/**
	 * @param filterVO
	 */
	public void setAirlineCN66DetailsVO(AirlineCN66DetailsVO airlineCN66DetailsVO){
		setAttribute(KEY_CN66DETAILSVO,(AirlineCN66DetailsVO)airlineCN66DetailsVO);
	}
	/**
	 * 
	 */
	public void removeAirlineCN66DetailsVO(){
		removeAttribute(KEY_CN66DETAILSVO);
	}
	 public Page<AirlineCN66DetailsVO> getAirlineCN66DetailsVOs() {
		
		return (Page<AirlineCN66DetailsVO>)this.getAttribute(KEY_ARLC66DTLVOS);
	}

	public void setAirlineCN66DetailsVOs(Page<AirlineCN66DetailsVO> airlineCN66DetailsVOs) {
		this.setAttribute(KEY_ARLC66DTLVOS,(Page<AirlineCN66DetailsVO>)airlineCN66DetailsVOs);
	}
	 public void removeAirlineCN66DetailsVOs(){
		  removeAttribute(KEY_ARLC66DTLVOS);
	}
	 public HashMap<String, String> getSystemparametres()
		{
			return getAttribute(KEY_SYSPARAMETERS);
		}
		public void setSystemparametres(HashMap<String, String> sysparameters)
		{
			setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}
		  public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI)
		  {
		    setAttribute(KEY_ONETIMERI, (ArrayList)oneTimeRI);
		  }
		  public Collection<OneTimeVO> getOneTimeRI()
		  {
		    return ((Collection)getAttribute(KEY_ONETIMERI));
		  }
		  public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI)
		  {
		    setAttribute(KEY_ONETIMEHNI, (ArrayList)oneTimeHNI);
		  }
		  public Collection<OneTimeVO> getOneTimeHNI()
		  {
		    return ((Collection)getAttribute(KEY_ONETIMEHNI));
		  }
		/**
		 * @param unitRoundingVO
		 */
	 	public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_VOLUMEROUNDINGVO, unitRoundingVO);
	    }
	 	/**
	 	 * @return unitRoundingVO
	 	 */
	    public UnitRoundingVO getVolumeRoundingVO() {
	    	return getAttribute(KEY_VOLUMEROUNDINGVO);
	    }
	    /**
	     * Remove unitRoundingVO
	     */
	    public void removeVolumeRoundingVO() {   
	    	removeAttribute(KEY_VOLUMEROUNDINGVO);
	    }  
	    /**
	     * @param unitRoundingVO
	     */
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
	    }
	    /**
	     * @return unitRoundingVO
	     */
	    public UnitRoundingVO getWeightRoundingVO() {
	    	return getAttribute(KEY_WEIGHTROUNDINGVO);
	    }
	    /**
	     * Remove unitRoundingVO
	     */
	    public void removeWeightRoundingVO() {
	    	removeAttribute(KEY_WEIGHTROUNDINGVO);
	    }  
}
