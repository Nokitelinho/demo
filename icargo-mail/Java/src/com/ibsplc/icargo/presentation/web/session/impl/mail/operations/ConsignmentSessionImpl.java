/*
 *  ConsignmentSessionImpl.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentMailPopUpVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;

/**
 * @author A-1876
 *
 */
public class  ConsignmentSessionImpl extends AbstractScreenSession
        implements  ConsignmentSession {

	private static final String SCREEN_ID = "mailtracking.defaults.consignment";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_CONSIGNMENTDOCUMENTVO = "consignmentDocumentVO";
	private static final String KEY_CONSIGNMENTFILTERVO = "consignmentFilterVO";
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMETYPE = "oneTimeType";
	private static final String KEY_ONETIMEMAILCLASS = "oneTimeMailClass";
	private static final String KEY_ONETIMERSN = "oneTimeRSN";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_ONETIMESUBTYPE = "oneTimeSubType";
	//added by a7531 for icrd 192557
	private static final String KEY_NEW_MAP = "new_hasmap";
	
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
	
	private static final String  TOTALRECORDS = "totalRecords";
	//added by a7531 for icrd 192557
	private static final String KEY_CONSIGNMENTPOPUPVO = "consignmentMailPopUpVO";
	
	//added by a-7871
	private static final String KEY_SELECTEDINDEX = "selectedIndexes";
		
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
	 * The setter method for ConsignmentDocumentVO
	 * @param consignmentDocumentVO
	 */
    public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO) {
    	setAttribute(KEY_CONSIGNMENTDOCUMENTVO,consignmentDocumentVO);
    }
    /**
     * The getter method for ConsignmentDocumentVO
     * @return ConsignmentDocumentVO
     */
    public ConsignmentDocumentVO getConsignmentDocumentVO() {
    	return getAttribute(KEY_CONSIGNMENTDOCUMENTVO);
    }
    /**
	 * The setter method for ConsignmentFilterVO
	 * @param consignmentFilterVO
	 */
    public void setConsignmentFilterVO(ConsignmentFilterVO consignmentFilterVO) {
    	setAttribute(KEY_CONSIGNMENTFILTERVO,consignmentFilterVO);
    }
    /**
     * The getter method for ConsignmentFilterVO
     * @return consignmentFilterVO
     */
    public ConsignmentFilterVO getConsignmentFilterVO() {
    	return getAttribute(KEY_CONSIGNMENTFILTERVO);
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
     * @param oneTimeType - Collection<OneTimeVO>
     */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType) {
		setAttribute(KEY_ONETIMETYPE,(ArrayList<OneTimeVO>)oneTimeType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMETYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMETYPE);
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
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
	public void setOneTimeSubType(Collection<OneTimeVO> oneTimeSubType) {
		setAttribute(KEY_ONETIMESUBTYPE,(ArrayList<OneTimeVO>)oneTimeSubType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMETYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeSubType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMESUBTYPE);
	}
	
	//added by a7531 for icrd 192557
	public  HashMap<String, ConsignmentMailPopUpVO> getMultipleMailDetailsMap(){
		return (HashMap<String, ConsignmentMailPopUpVO>)getAttribute(KEY_NEW_MAP);
	}
	

	public void setMultipleMailDetailsMap(HashMap<String, ConsignmentMailPopUpVO> multipleMailDetailsMap){
		setAttribute(KEY_NEW_MAP, (HashMap<String, ConsignmentMailPopUpVO>) multipleMailDetailsMap);
	}
	//added by a7531 for icrd 192557

	 public void  setConsignmentMailPopUpVO(ConsignmentMailPopUpVO consignmentMailPopUpVO) {
	    	setAttribute(KEY_CONSIGNMENTPOPUPVO,consignmentMailPopUpVO);
	    }
	    /**
	     * The getter method for ConsignmentDocumentVO
	     * @return ConsignmentDocumentVO
	     */
	    public ConsignmentMailPopUpVO getConsignmentMailPopUpVO() {
	    	return getAttribute(KEY_CONSIGNMENTPOPUPVO);
	    }
	    
	    //added by a-7871 for ICRD-234913 starts----

		@Override
		public String getSelectedIndexes() {
			// TODO Auto-generated method stub
			return getAttribute(KEY_SELECTEDINDEX);
		}

		@Override
		public void setSelectedIndexes(String SelectedIndexes) {
			// TODO Auto-generated method stub
			setAttribute(KEY_SELECTEDINDEX,SelectedIndexes);
		}
		  //added by a-7871 for ICRD-234913 ends ----
}
