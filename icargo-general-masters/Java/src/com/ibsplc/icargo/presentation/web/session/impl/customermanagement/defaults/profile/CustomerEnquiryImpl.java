/*
 * CustomerEnquiryImpl.java Created on Jul 02, 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.profile;

import java.util.ArrayList;
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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.CustomerEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2883
 * 
 */
public class CustomerEnquiryImpl extends AbstractScreenSession implements
		CustomerEnquirySession {

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";

	private static final String LISTCLAIM = "listClaim";
	private static final String LISTSPOT = "listSpot";
	private static final String LISTTERMINAL = "listTerminal";	
	private static final String LISTBOOKING = "listbooking";
	private static final String LISTALLOTMENT = "listallotment";
	private static final String LISTINVOICE = "listinvoice";
	private static final String LISTMESSAGE = "listmessage";
	private static final String LISTSTOCK = "liststock";
	private static final String STOCKDETAILS = "stockdetails";
	private static final String LISTCONTRACT = "listcontract";
	private static final String LISTCHARTER = "listcharter";
	private static final String ENQUIRYTYPE = "enquirytype";
	private static final String INDEXMAP = "indexmap";
	private static final String ENQUIRY = "enquirydetails";
	//private static final String ENQUIRY_FORM = "enquiryform";
	private static final String ENQUIRYBOOKINGLIST= "enquiryBookingList";
	private static final String ENQUIRYSPOTLIST = "enquirySpotList";
	private static final String CLAIM_REASON = "claimreason";
	private static final String CLAIM_NATURE = "claimnature";
	private static final String CLAIM_STATUS = "claimstatus";
	private static final String CAPACITY_STATUS = "capacitystatus";
	/**
	 * return screen id
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * get module name
	 */
	public String getModuleName() {
		return MODULENAME;
	}

	
   /**
	* @return Returns the ULDListVO.
	*/
	public Page<ClaimListVO> getListClaimDisplayPage() {
	    return getAttribute(LISTCLAIM);
	}
	
   /**
	 * @param uldListVOs The uldListVOs to set.
	 */
	
	public void setListClaimDisplayPage(Page<ClaimListVO> listVOs) {
		setAttribute(LISTCLAIM,listVOs);
	}

	
	 /**
	* @return Returns the ULDListVO.
	*/
	public Page<SpotRateRequestDetailsVO> getListSpotDisplayPage() {
	    return getAttribute(LISTSPOT);
	}
	
   /**
	 * @param uldListVOs The uldListVOs to set.
	 */
	
	public void setListSpotDisplayPage(Page<SpotRateRequestDetailsVO> listVOs) {
		setAttribute(LISTSPOT,listVOs);
	}
	
	 /**
	* @return Returns the ULDListVO.
	*/
	public Page<InvoiceDetailsVO> getListTerminalDisplayPage() {
	    return getAttribute(LISTTERMINAL);
	}
	
   /**
	 * @param uldListVOs The uldListVOs to set.
	 */
	
	public void setListTerminalDisplayPage(Page<InvoiceDetailsVO> listVOs) {
		setAttribute(LISTTERMINAL,listVOs);
	}

	public Page<BookingVO> getListBookingDisplayPage() {
		 return getAttribute(LISTBOOKING);
	}
	
	public void setListBookingDisplayPage(Page<BookingVO> listVOs) {
		setAttribute(LISTBOOKING,listVOs);
	}

	public Page<CustomerAllotmentEnquiryVO> getListAllotmentDisplayPage() {
		return getAttribute(LISTALLOTMENT);
	}

	public void setListAllotmentDisplayPage(Page<CustomerAllotmentEnquiryVO> listVOs) {
		setAttribute(LISTALLOTMENT,listVOs);
	}


	public Page<InvoiceDetailsVO> getListInvoiceDisplayPage() {
		return getAttribute(LISTINVOICE);
	}
	
	public void setListInvoiceDisplayPage(Page<InvoiceDetailsVO> listVOs) {
		setAttribute(LISTINVOICE,listVOs);
	}


	
	public Page<MessageHistoryVO> getListMessageDisplayPage() {
		return getAttribute(LISTMESSAGE);
	}

	public void setListMessageDisplayPage(Page<MessageHistoryVO> listVOs) {
		setAttribute(LISTMESSAGE,listVOs);
		
	}
	
	public Page<StockDetailsVO> getListStockDisplayPage() {
		return getAttribute(LISTSTOCK);
	}

	public void setListStockDisplayPage(Page<StockDetailsVO> listVOs) {
		setAttribute(LISTSTOCK,listVOs);
		
	}
	
	public Page<RateLineVO> getListContractDisplayPage() {
		return getAttribute(LISTCONTRACT);
	}

	public void setListContractDisplayPage(Page<RateLineVO> listVOs) {
		setAttribute(LISTCONTRACT,listVOs);
	}
	
	public StockDetailsVO getStockDetailsDisplayPage() {
		return getAttribute(STOCKDETAILS);
	}

	public void setStockDetailsDisplayPage(StockDetailsVO stockVO) {
		setAttribute(STOCKDETAILS,stockVO);
	}

	public Page<CharterOperationsVO> getListCharterDisplayPage() {
		return getAttribute(LISTCHARTER);
	}

	public void setListCharterDisplayPage(Page<CharterOperationsVO> listVOs) {
		setAttribute(LISTCHARTER,listVOs);
		
	}

	public String getEnquiryType() {
		return getAttribute(ENQUIRYTYPE);
	}

	public void setEnquiryType(String type) {
		setAttribute(ENQUIRYTYPE,type);
		
	}

	public HashMap<String, String> getIndexMap() {
		return getAttribute(INDEXMAP);
	}

	public void setIndexMap(HashMap<String, String> indexMap) {
		setAttribute(INDEXMAP,indexMap);
		
	}

	public CustomerVO getEnquiryDetails() {
		return getAttribute(ENQUIRY);
	}

	public void setEnquiryDetails(CustomerVO customerVO) {
		setAttribute(ENQUIRY,customerVO);
		
	}

	public String getEnquiryBookingList() {
		return getAttribute(ENQUIRYBOOKINGLIST);
	}

	public void setEnquiryBookingList(String enquiryBookingList) {
		setAttribute(ENQUIRYBOOKINGLIST,enquiryBookingList);
	}
	
	public String getEnquirySpotList() {
		return getAttribute(ENQUIRYSPOTLIST);
	}

	public void setEnquirySpotList(String enquirySpotList) {
		setAttribute(ENQUIRYSPOTLIST,enquirySpotList);
	}

	public void setClaimNature(Collection<OneTimeVO> onetime) {
		setAttribute(CLAIM_NATURE,(ArrayList<OneTimeVO>) onetime);
		
	}
	
	public Collection<OneTimeVO> getClaimNature() {
		return (Collection<OneTimeVO>) getAttribute(CLAIM_NATURE);
	}

	
	public void setClaimReason(Collection<OneTimeVO> onetime) {
		setAttribute(CLAIM_REASON,(ArrayList<OneTimeVO>) onetime);
	}

	public Collection<OneTimeVO> getClaimReason() {
		return (Collection<OneTimeVO>) getAttribute(CLAIM_REASON);
	}
	
	public void setClaimStatus(Collection<OneTimeVO> onetime) {
		setAttribute(CLAIM_STATUS,(ArrayList<OneTimeVO>) onetime);
	}

	public Collection<OneTimeVO> getClaimStatus() {
		return (Collection<OneTimeVO>) getAttribute(CLAIM_STATUS);
	}
	
	public void setCapacityStatus(Collection<OneTimeVO> onetime) {
		setAttribute(CAPACITY_STATUS,(ArrayList<OneTimeVO>) onetime);
	}

	public Collection<OneTimeVO> getCapacityStatus() {
		return (Collection<OneTimeVO>) getAttribute(CAPACITY_STATUS);
	}
	
	
	
	
}
