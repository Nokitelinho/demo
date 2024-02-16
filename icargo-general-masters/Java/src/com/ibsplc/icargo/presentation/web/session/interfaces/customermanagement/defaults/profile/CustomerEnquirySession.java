/*
 * CustomerEnquirySession.java Created on Jul 02, 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceDetailsVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.CharterOperationsVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimListVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageHistoryVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.vo.RateLineVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2883
 * 
 */
public interface CustomerEnquirySession extends ScreenSession {

	public String getScreenID();
	public String getModuleName();
	
	/**
     * 
     * @param listVOs
     */
	void setListClaimDisplayPage(Page<ClaimListVO> listVOs);

	/**
	 * 
	 * @return
	 */
	Page<ClaimListVO> getListClaimDisplayPage();
	/**
     * 
     * @param listVOs
     */
	void setListTerminalDisplayPage(Page<InvoiceDetailsVO> listVOs);

	/**
	 * 
	 * @return 
	 */
	Page<InvoiceDetailsVO> getListTerminalDisplayPage();
	/**
     * 
     * @param  listVOs
     */
	void setListBookingDisplayPage(Page<BookingVO> listVOs);

	/**
	 * 
	 * @return
	 */
    Page<BookingVO> getListBookingDisplayPage();
    /**
     * 
     * @param listVOs
     */
    void setListStockDisplayPage(Page<StockDetailsVO> listVOs);

	/**
	 * 
	 * @return
	 */
    Page<StockDetailsVO> getListStockDisplayPage();
    /**
     * 
     * @param stockVO
     */
    void setStockDetailsDisplayPage(StockDetailsVO stockVO);

	/**
	 * 
	 * @return
	 */
    StockDetailsVO getStockDetailsDisplayPage();
    /**
     * 
     * @param listVOs
     */
    void setListMessageDisplayPage(Page<MessageHistoryVO> listVOs);

	/**
	 * 
	 * @return
	 */
    Page<MessageHistoryVO> getListMessageDisplayPage();
    /**
     * 
     * @param listVOs
     */
    void setListAllotmentDisplayPage(Page<CustomerAllotmentEnquiryVO> listVOs);

	/**
	 * 
	 * @return
	 */
    Page<CustomerAllotmentEnquiryVO> getListAllotmentDisplayPage();
    /**
     * 
     * @param listVOs
     */
    void setListSpotDisplayPage(Page<SpotRateRequestDetailsVO> listVOs);

	/**
	 * 
	 * @return
	 */
    Page<SpotRateRequestDetailsVO> getListSpotDisplayPage();
    /**
     * 
     * @param listVOs
     */
	void setListInvoiceDisplayPage(Page<InvoiceDetailsVO> listVOs);

	/**
	 * 
	 * @return
	 */
	Page<InvoiceDetailsVO> getListInvoiceDisplayPage();
	/**
     * 
     * @param listVOs
     */
	void setListContractDisplayPage(Page<RateLineVO> listVOs);

	/**
	 * 
	 * @return
	 */
	Page<RateLineVO> getListContractDisplayPage();
	/**
     * 
     * @param listVOs
     */
	void setListCharterDisplayPage(Page<CharterOperationsVO> listVOs);

	/**
	 * 
	 * @return
	 */
	Page<CharterOperationsVO> getListCharterDisplayPage();
	/**
     * 
     * @param type
     */
	void setEnquiryType(String type);

	/**
	 * 
	 * @return
	 */
	public String getEnquiryType();
	

	/**
	 * 
	 * @return
	 */
	public HashMap<String,String> getIndexMap();
	/**
     * 
     * @param indexMap
     */
	public void setIndexMap(HashMap<String,String> indexMap);
	/**
     * 
     * @param EnquiryDetails
     */
	void setEnquiryDetails(CustomerVO customerVO);

	/**
	 * 
	 * @return
	 */
	CustomerVO getEnquiryDetails();
	/**
     * 
     * @param form
     */
	void setEnquiryBookingList(String enquiryBookingList);

	/**
	 * 
	 * @return
	 */
	String getEnquiryBookingList();
	
	/**
     * 
     * @param form
     */
	void setEnquirySpotList(String enquirySpotList);

	/**
	 * 
	 * @return
	 */
	String getEnquirySpotList();
	

	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getClaimReason();
	/**
     * 
     * @param onetime
     */
	public void setClaimReason(Collection <OneTimeVO> onetime);
	

	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getClaimNature();
	/**
     * 
     * @param onetime
     */
	public void setClaimNature(Collection <OneTimeVO> onetime);
	

	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getClaimStatus();
	/**
     * 
     * @param onetime
     */
	public void setClaimStatus(Collection <OneTimeVO> onetime);
	

	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getCapacityStatus();
	/**
     * 
     * @param onetime
     */
	public void setCapacityStatus(Collection <OneTimeVO> onetime);
	
}
