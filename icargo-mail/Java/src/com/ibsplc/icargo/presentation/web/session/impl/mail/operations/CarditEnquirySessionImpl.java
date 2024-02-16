/*
 *  CarditEnquirySessionImpl.java Created on Jul 15, 2006
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

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2001
 *
 */
public class  CarditEnquirySessionImpl extends AbstractScreenSession
        implements  CarditEnquirySession{

	private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";
	private static final String MODULE_NAME = "mail.operations";

	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	/**
	 * Constant for the session variable carditEnquiryVO
	 */
	private static final String KEY_CARDITENQUIRYVO  = "carditEnquiryVO ";
	private static final String KEY_INDEXMAP = "indexMap";
	private static final String MAILBAGVO_COLL="mailbagcollection";	
	private static final String KEY_CONTAINERVOS = "containercollection";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationVO";
	private static final String KEY_CARDITENQUIRYFILTERVO  = "carditEnquiryFilterVO ";
	private static final String KEY_MAILBAGVOS = "listmailbagcollection";
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
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)
									getAttribute(KEY_ONETIME_VO);
	}
	
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
						HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);
	}
	
	/**
	 * The setter method for carditEnquiryVO
	 * @param carditEnquiryVO
	 */
    public void setCarditEnquiryVO (CarditEnquiryVO carditEnquiryVO) {
    	setAttribute(KEY_CARDITENQUIRYVO,carditEnquiryVO);
    }
    /**
     * The getter method for carditEnquiryVO
     * @return carditEnquiryVO
     */
    public CarditEnquiryVO  getCarditEnquiryVO () {
    	return getAttribute(KEY_CARDITENQUIRYVO);
    }
     
    public void removeCarditEnquiryVO() {
    	removeAttribute(KEY_CARDITENQUIRYVO);
    }

    
	public void setMailbagVOsCollection(Page<MailbagVO> mailbagvos) {
		setAttribute(MAILBAGVO_COLL,(Page<MailbagVO>)mailbagvos);
	}

	
	public Page<MailbagVO> getMailbagVOsCollection() {
		return (Page<MailbagVO>)getAttribute(MAILBAGVO_COLL);
	}
	
	/**
	 * This method returns the indexMap
	 * @return indexMap - HashMap<String, String>
	 */
	public HashMap<String, String> getIndexMap(){
		return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
	}
	
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setIndexMap(HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}

	public Collection<ContainerVO> getContainerVOs() {		
		return (Collection<ContainerVO>)getAttribute(KEY_CONTAINERVOS);
	}

	public void setContainerVOs(Collection<ContainerVO> containerVOs) {
		setAttribute(KEY_CONTAINERVOS,(ArrayList<ContainerVO>)containerVOs);
		
	}

	public FlightValidationVO getFlightValidationVO() {
		return (FlightValidationVO)getAttribute(KEY_FLIGHTVALIDATIONVO);
	}

	public void setFlightValidationVO(FlightValidationVO flightValidationVO) {
		setAttribute(KEY_FLIGHTVALIDATIONVO,(FlightValidationVO)flightValidationVO);
		
	}
	
	/**
	 * The setter method for carditEnquiryVO
	 * @param carditEnquiryVO
	 */
    public void setCarditEnquiryFilterVO (CarditEnquiryFilterVO carditEnquiryFilterVO) {
    	setAttribute(KEY_CARDITENQUIRYFILTERVO,carditEnquiryFilterVO);
    }
    /**
     * The getter method for carditEnquiryVO
     * @return carditEnquiryVO
     */
    public CarditEnquiryFilterVO  getCarditEnquiryFilterVO() {
    	return getAttribute(KEY_CARDITENQUIRYFILTERVO);
    }
	
    public Collection<MailbagVO> getMailBagVOsForListing() {		
		return (Collection<MailbagVO>)getAttribute(KEY_MAILBAGVOS);
	}

	public void setMailBagVOsForListing(Collection<MailbagVO> mailbagvos) {
		setAttribute(KEY_MAILBAGVOS,(ArrayList<MailbagVO>)mailbagvos);
		
	}
}
