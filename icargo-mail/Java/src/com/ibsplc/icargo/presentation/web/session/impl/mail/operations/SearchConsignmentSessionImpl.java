/*
 *  SearchConsignmentSessionImpl.java Created on Jul 15, 2006
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
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-4826
 *
 */
public class SearchConsignmentSessionImpl extends AbstractScreenSession
implements  SearchConsignmentSession{

	private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
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
	//Added by A-5220 for ICRD-21098 starts
	public static final String KEY_TOTAL_RECORD_COUNT="mailtracking.defaults.searchconsignment.totalRecordCount";
	//Added by A-5220 for ICRD-21098 ends
	
	//Added by A-7531 for ICRD-192536
	private static final String ONETIME_SHIPMENT_STATUS = "bookingStatus";
	private static final String LIST_MAL_COLLECTION = "mailBookingDetailsVOs";
    private static final String BOOKING_MAL_COLLECTION="mailBookingDetailsVO";
    private static final String SELECT_MAL_COLLECTION="selectedMailbagVO";
    
	private static final String TOTAL_PCS = "totalPcs";//added by A-8061 for ICRD-233692
	private static final String TOTAL_WEIGHT = "totalWeight";//added by A-8061 for ICRD-233692
	
	
	
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
	//Added by A-5220 for ICRD-21098 starts
	public void setTotalRecordCount(int toralRecordCount) {
		// TODO Auto-generated method stub
		setAttribute(KEY_TOTAL_RECORD_COUNT, toralRecordCount);
	}
	public int getTotalRecordCount() {
		// TODO Auto-generated method stub
		return (Integer)getAttribute(KEY_TOTAL_RECORD_COUNT);
	}
	//Added by A-5220 for ICRD-21098 ends
	
	
	//Added by A-7531 for ICRD-192536
	public Collection<OneTimeVO> getBookingStatus() {
		return (Collection<OneTimeVO>) getAttribute(ONETIME_SHIPMENT_STATUS);
	}

	/**
	 * This method is used to set the Collection of OneTimeVO in the Session
	 * 
	 * @param Collection
	 *            <OneTimeVO>
	 */
	public void setBookingStatus(Collection<OneTimeVO> bookingStatus) {
		setAttribute(ONETIME_SHIPMENT_STATUS, (ArrayList<OneTimeVO>) bookingStatus);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession#getMailBookingDetails()
	 *	Added by 			: A-7531 on 06-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */

	public Page<MailBookingDetailVO> getMailBookingDetailsVOs() {
		
		return (Page<MailBookingDetailVO>)getAttribute(LIST_MAL_COLLECTION);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession#setMailBookingDetails(java.util.Collection)
	 *	Added by 			: A-7531 on 06-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailVOs 
	 */
	
	public void setMailBookingDetailsVOs(
			Page<MailBookingDetailVO> mailBookingDetailsVOs) {
		setAttribute(LIST_MAL_COLLECTION,(Page<MailBookingDetailVO>)mailBookingDetailsVOs);
		
	}
	

	
    /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession#getMailBookingDetailsVO()
     *	Added by 			: A-7531 on 19-Sep-2017
     * 	Used for 	:
     *	Parameters	:	@return
     */
    public Collection<MailBookingDetailVO> getMailBookingDetailsVO() {
		
		return (Collection<MailBookingDetailVO>)getAttribute(BOOKING_MAL_COLLECTION);
	}
    
    public void setMailBookingDetailsVO(
			Collection<MailBookingDetailVO> mailBookingDetailsVO) {
		setAttribute(BOOKING_MAL_COLLECTION,(ArrayList<MailBookingDetailVO>) mailBookingDetailsVO);
		
	}
    
    
 public Collection<MailbagVO> getselectedMailbagVOs() {
		
		return (Collection<MailbagVO>)getAttribute(SELECT_MAL_COLLECTION);
	}
    
    public void setselectedMailbagVOs(
			Collection<MailbagVO> selectedMailbagVO) {
		setAttribute(SELECT_MAL_COLLECTION,(ArrayList<MailbagVO>) selectedMailbagVO);
		
	}

    /**
     * @author A-8061
     */
	public Measure getTotalWeight() {
		
		return (Measure) getAttribute(TOTAL_WEIGHT);
	}

	/**
	 * @author A-8061
	 */
	public void setTotalWeight(Measure totalWeight) {
		
		setAttribute(TOTAL_WEIGHT,(Measure)totalWeight);
	}

	/**
	 * @author A-8061
	 */
	public Integer getTotalPcs()
	  {
	        return (Integer)getAttribute(TOTAL_PCS);
	  }
	  
	/**
	 *@author A-8061
	 */
	 public void setTotalPcs(int totalPcs)
	  {
	        setAttribute(TOTAL_PCS, Integer.valueOf(totalPcs));
	  }


    
}
