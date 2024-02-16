/*
 * DespatchEnqSession.java Created on jun 25,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-2391
 *
 */
public interface DespatchEnqSession extends ScreenSession {
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	/**
	 * 
	 * @param rateLineVOs
	 */
	public void setGPABillingDtls(Collection<GPABillingDetailsVO> rateLineVOs);
	/**
	 * 
	 * @return
	 */
	public Collection<GPABillingDetailsVO> getGPABillingDtls() ;
	/**
	 * 
	 *
	 */
	public void removeGPABillingDtls();
	
	/**
	 * The setter method for UnaccountedDispatchesFilterVO
	 * @param UnaccountedDispatchesFilterVO
	 */
	public void setDispatchFilterVO(
			DSNPopUpVO dsnPopUpVO);
    /**
     * The getter method for UnaccountedDispatchesFilterVO
     * @return UnaccountedDispatchesFilterVO
     */
    public DSNPopUpVO getDispatchFilterVO();

    
    public Collection<AirlineBillingDetailVO> getAirlineBillingDetailsVO();
	
	public void setAirlineBillingDetailsVO(Collection<AirlineBillingDetailVO> airlineBillingDetails);
	
	/**
	 * 
	 * @param sectorRevenueDetailsVOs to set
	 */
	public void setFlownDetails(Collection<SectorRevenueDetailsVO> sectorRevenueDetailsVOs);
	/**
	 * 
	 * @return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> getFlownDetails() ;
	/**
	 * 
	 *method to remove sectorRevenueDetailsVO from session
	 */
	public void removeFlownDetails();
	
	/**
	 * 
	 * @param setDespatchEnquiryVO to set
	 */
	public void setDespatchEnquiryVO(DespatchEnquiryVO despatchEnquiryVO);
	/**
	 * 
	 * @return DespatchEnquiryVO
	 */
	public DespatchEnquiryVO getDespatchEnquiryVO() ;
	
	/**
	 * 
	 * @param mraAccountingVOs to set
	 */
	public void setAccountingDetails(Page<MRAAccountingVO> accountingDetailVOs);
	/**
	 * 
	 * @return Page<MRAAccountingVO>
	 */
	public Page<MRAAccountingVO> getAccountingDetails() ;
	/**
	 * 
	 *method to remove mraAccountingVOs from session
	 */
	public void removeAccountingDetails();
	
	/**
	 * 
	 * @param uspsReportingVOs to set
	 */
	public void setUSPSReportingDetails(Collection<USPSReportingVO> uspsReportingVOs);
	/**
	 * 
	 * @return Collection<USPSReportingVO>
	 */
	public Collection<USPSReportingVO> getUSPSReportingDetails() ;
	/**
	 * 
	 *method to remove uspsReportingVOs from session
	 */
	public void removeUSPSReportingDetails();
	
	/**
	 * @return ArrayList<OneTimeVO> 
	 */
	public ArrayList<OneTimeVO> getFunctionPoint();
	/**
	 * @param functionPoint 
	 */
	public void setFunctionPoint(ArrayList<OneTimeVO> functionPoint);
	/**
	 * method to remove functionPoint from Session
	 */
	public void removeFunctionPoint();
	
	/**
	 * This method is used to set indexMap to the session
	 *  
	 */
	void setIndexMap(HashMap indexMap);
	/**
	 * This method returns the hashmap
	 * 
	 * @return HashMap - HashMap<String,String>
	 */
	
	HashMap getIndexMap();
	/**
	 * 
	 * @param outstandingBalanceVOs to set
	 */
	public void setOutStandingBalances(Collection<OutstandingBalanceVO> outstandingBalanceVOs);
	/**
	 * 
	 * @return Collection<OutstandingBalanceVO>
	 */
	public Collection<OutstandingBalanceVO> getOutStandingBalances() ;
	/**
	 * 
	 *method to remove OutstandingBalanceVO from session
	 */
	public void removeOutStandingBalances();
}
