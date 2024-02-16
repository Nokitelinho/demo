/*
 * SearchConsignmentSession.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;

import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-4826
 *
 */
public interface SearchConsignmentSession extends ScreenSession {

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	/**
	 * The setter method for carditEnquiryVO
	 * @param carditEnquiryVO
	 */
    public void setCarditEnquiryVO (CarditEnquiryVO carditEnquiryVO);
    /**
     * The getter method for carditEnquiryVO
     * @return carditEnquiryVO
     */
    public CarditEnquiryVO  getCarditEnquiryVO ();
     
    public void removeCarditEnquiryVO();

	public void setMailbagVOsCollection(Page<MailbagVO> mailbagvos);
	
	public Page<MailbagVO> getMailbagVOsCollection();
	
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();
    
    /**
     * The getter method for containerVOs
     * @return containerVOs
     */
    public Collection<ContainerVO> getContainerVOs();
    /**
     * The setter method for containerVOs
     * @param containerVOs
     */
    public void setContainerVOs(Collection<ContainerVO> containerVOs);
    /**
     * The getter method for flightValidationVO
     * @return flightValidationVO
     */
    public FlightValidationVO getFlightValidationVO();
    /**
     * The setter method for flightValidationVO
     * @param flightValidationVO
     */
    public void setFlightValidationVO (FlightValidationVO flightValidationVO);
    /**
	 * The setter method for carditEnquiryVO
	 * @param carditEnquiryVO
	 */
    public void setCarditEnquiryFilterVO (CarditEnquiryFilterVO carditEnquiryFilterVO);
    /**
     * The getter method for carditEnquiryVO
     * @return carditEnquiryVO
     */
    public CarditEnquiryFilterVO  getCarditEnquiryFilterVO ();
    
    /**
     * The getter method for MailbagVOs
     * @return MailbagVOs
     */
    public Collection<MailbagVO> getMailBagVOsForListing();
    /**
     * The setter method for MailbagVOs
     * @param MailbagVOs
     */
    public void setMailBagVOsForListing(Collection<MailbagVO> mailBagVOs);

    //Added by A-5220 for ICRD-21098 starts
    void setTotalRecordCount(int toralRecordCount);
	int getTotalRecordCount();
    //Added by A-5220 for ICRD-21098 starts
	
	//Added by A-7531 for ICRD-192536
	
	public Collection<OneTimeVO> getBookingStatus();
	/**
	 * 
	 * @param shipmentStatus
	 */
	public void setBookingStatus(Collection<OneTimeVO> bookingStatus);
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.getMailBookingDetails
	 *	Added by 	:	A-7531 on 17-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingDetailVO>
	 */
	public Page<MailBookingDetailVO> getMailBookingDetailsVOs();//modified by A-7371 for ICRD-228233
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.setMailBagVOsForListing
	 *	Added by 	:	A-7531 on 17-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBagVOs 
	 *	Return type	: 	void
	 */
	public void setMailBookingDetailsVOs(Page<MailBookingDetailVO> mailBookingDetailsVOs);
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.getMailBookingDetailsVO
	 *	Added by 	:	A-7531 on 19-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingDetailVO>
	 */
	public Collection<MailBookingDetailVO> getMailBookingDetailsVO();
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.setMailBookingDetailsVO
	 *	Added by 	:	A-7531 on 19-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailsVO 
	 *	Return type	: 	void
	 */
	public void setMailBookingDetailsVO(Collection<MailBookingDetailVO> mailBookingDetailsVO);
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.getselectedMailbagVOsForAWBAttach
	 *	Added by 	:	A-7531 on 21-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailbagVO>
	 */
	public Collection<MailbagVO> getselectedMailbagVOs();
	/**
	 * 
	 * 	Method		:	SearchConsignmentSession.setselectedMailbagVOsForAWBAttach
	 *	Added by 	:	A-7531 on 21-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailbagVO 
	 *	Return type	: 	void
	 */
	public void setselectedMailbagVOs(Collection<MailbagVO> selectedMailbagVO);
	
	
    /**
     * @author A-8061
	 * @return the totalWeight
	 */
	public Measure getTotalWeight();

	/**
	 * @author A-8061
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(Measure totalWeight);
	
	/**
	 * @author A-8061
	 * @return
	 */
	public Integer getTotalPcs();
	/**
	 * @author A-8061
	 * @param totalPcs
	 */
	
	public void setTotalPcs(int totalPcs);
	
}
