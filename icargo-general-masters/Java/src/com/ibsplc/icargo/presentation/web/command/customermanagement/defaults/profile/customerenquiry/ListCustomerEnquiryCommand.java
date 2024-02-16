/*
 * ListCustomerEnquiryCommand.java Created on Jul 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceDetailsVO;
import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceFilterVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.CharterOperationsVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.ListCharterOperationsFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingFilterVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimFilterVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimListVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageHistoryVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageListFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestFilterVO;
import com.ibsplc.icargo.business.tariff.freight.vo.BKBasisVO;
import com.ibsplc.icargo.business.tariff.freight.vo.ClassRateBasisVO;
import com.ibsplc.icargo.business.tariff.freight.vo.RateLineParameterVO;
import com.ibsplc.icargo.business.tariff.freight.vo.RateLineVO;
import com.ibsplc.icargo.business.tariff.freight.vo.TariffFilterVO;
import com.ibsplc.icargo.business.tariff.freight.vo.ULDBasisHolderVO;
import com.ibsplc.icargo.business.tariff.freight.vo.ULDSlabVO;
import com.ibsplc.icargo.business.tariff.freight.vo.WeightBreakBasisHolderVO;
import com.ibsplc.icargo.business.tariff.freight.vo.WeightSlabVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.CustomerEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.tariff.freight.MaintainRateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.CustomerEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author a-2883
 *
 */
public class ListCustomerEnquiryCommand extends BaseCommand{

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String BOOKING = "bookingdetails";
	private static final String CLAIM = "claimdetails";
	private static final String STOCK = "stockdetails";
	private static final String MESSAGING = "messagingdetails";
	private static final String ALLOTMENT = "allotmentdetails";
	private static final String SPOT = "spotrates";
	private static final String CONTRACT = "contractrates";
	private static final String TERMINAL = "terminalhandling";
	private static final String CHARTER = "charter";
	private static final String SELECT = "select";
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String STOCK_HOLDER ="stockcontrol.defaults.stockholdernotfound";
	private static final String YES ="Y";
	private static final String TARIFF_SCREENID = "tariff.freight.maintainratecard";
	private static final String TARIFF_MODULE = "tariff.freight";
	private static final String PERCENTAGE_OPERATORS = "tariff.freight.percentageoperator";
	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering("CUSTOMERMANAGEMENT", "ListCustomerEnquiryCommand");
		CustomerEnquiryForm form = (CustomerEnquiryForm)invocationContext.screenModel;
		log.log(Log.FINE, " \n formTransactionType", form.getEnquiryType());
		Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		
		CustomerVO customerVO = null;
		if(session.getEnquiryDetails() != null){
			customerVO = session.getEnquiryDetails();
			form.setCustomerCode(customerVO.getCustomerCode());
			form.setFromDate(customerVO.getFromDate());
			form.setToDate(customerVO.getToDate());
		}
		LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, true);
		LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, true);
	
		
		
		//validating whether the enquirytype is as previous for pagination
		int nAbsoluteIndex = 0;
		if(session.getEnquiryType() != null &&
				form.getEnquiryType().equals(session.getEnquiryType())){
			if (session.getIndexMap() != null) {
				indexMap = session.getIndexMap();
			} else {
				indexMap = new HashMap<String, String>();
				indexMap.put("1", "1");
			}
			
			String strAbsoluteIndex = (String) indexMap.get(form
					.getDisplayPageNum());
			form.setAbsoluteIndex(strAbsoluteIndex);
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
			if(BOOKING.equals(session.getEnquiryType())){
				form.setEnquiryBookingList(YES);
			}
			
			if(SPOT.equals(session.getEnquiryType())){
				form.setEnquirySpotList(YES);
			}
			
			clearSession();
		}else{
			form.setDisplayPageNum("1");
			form.setAbsoluteIndex("1");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
			clearSession();
		}
		session.setEnquiryType(form.getEnquiryType());
		
		
		
		if(form.getEnquiryType() != null 
				&& BOOKING.equals(form.getEnquiryType())){
			//booking
			
			BookingFilterVO bookingFilterVO = new BookingFilterVO();
			Page<BookingVO> pageBooking = null;
			if(form.getOrgin() != null && 
					form.getOrgin().trim().length()>0){
				bookingFilterVO.setOrigin(form.getOrgin().toUpperCase());
				errors = validateStationCode(form);
			}
			if(errors.size()>0){
				invocationContext.addAllError(errors);
			}
			if(form.getDestination() != null && 
					form.getDestination().trim().length()>0){
				bookingFilterVO.setDestination(form.getDestination().toUpperCase());
				errors = validateStationCode(form);
			}
			if(errors.size()>0){
				invocationContext.addAllError(errors);
			}
			if(form.getCommodity() != null && 
					form.getCommodity().trim().length()>0){
				bookingFilterVO.setCommodityCode(form.getCommodity().toUpperCase());
				errors = validateCommodityvalues(form.getCommodity().toUpperCase());
			}
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
				invocationContext.target = BOOKING;
				return;
			}
			//LocalDate bookingFrom = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			//LocalDate bookingTo = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			bookingFilterVO.setEndDate(toDate.setDate(form.getToDate()));
			bookingFilterVO.setStartDate(fromDate.setDate(form.getFromDate()));
			bookingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			bookingFilterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
			log.log(Log.FINE, " \n ##########insbook$@@@%%#######/####", form.getEnquiryBookingList());
			//form.setEnquiryBookingList("Y");
			if(form.getEnquiryBookingList() !=null &&
					AbstractVO.FLAG_YES.equals(form.getEnquiryBookingList())){
				form.setEnquiryBookingList(null);
				//pagination
				if(form.getAbsoluteIndex() != null){
					bookingFilterVO.setAbsoluteIndex(Integer.parseInt(form
		    				.getAbsoluteIndex()));
				}else{
					bookingFilterVO.setAbsoluteIndex(1);
				}
				if(form.getDisplayPageNum() != null){
					bookingFilterVO.setPageNumber(Integer.parseInt(form
		    				.getDisplayPageNum()));
				}else{
					bookingFilterVO.setPageNumber(1);
				}
				log.log(Log.FINE, " \n ##########insbook$@@@%%######****##",
						bookingFilterVO);
				try {
					pageBooking = delegate.findBookings(bookingFilterVO);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if(errors.size()> 0){
					invocationContext.addAllError(errors);
				}else{
					if(pageBooking !=null && pageBooking.size() >0){
						session.setListBookingDisplayPage(pageBooking);
						finalMap = indexMap;
						if (session.getListBookingDisplayPage() != null) {
							finalMap = buildIndexMapForBooking(indexMap, session.getListBookingDisplayPage());
							session.setIndexMap(finalMap);
						}
					}else{
						errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
						invocationContext.addAllError(errors); 
					}
				}
			}else{
				form.setEnquiryBookingList(null);
				session.setListBookingDisplayPage(null);
			}
			
			log.log(Log.FINE, " \n ##########pageBooking############");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = BOOKING;
			return;
		}else if(form.getEnquiryType() != null 
				&& CLAIM.equals(form.getEnquiryType())){
			//claimdetails
			ClaimFilterVO claimFilterVO = new ClaimFilterVO();
			Page<ClaimListVO> pageClaim = null;
			claimFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			claimFilterVO.setClaimantCode(form.getCustomerCode().toUpperCase());
			//claimFilterVO.setClaimantCode("C1");
			claimFilterVO.setToDate(toDate.setDate(form.getToDate()));
			claimFilterVO.setFromDate(fromDate.setDate(form.getFromDate()));
			//pagination
			if(form.getAbsoluteIndex() != null){
				claimFilterVO.setAbsoluteIndex(Integer.parseInt(form
	    				.getAbsoluteIndex()));
			}else{
				claimFilterVO.setAbsoluteIndex(1);
			}
			if(form.getDisplayPageNum() != null){
				claimFilterVO.setPageNumber(Integer.parseInt(form
	    				.getDisplayPageNum()));
	    	}else{
	    		claimFilterVO.setPageNumber(1);
	    	}
			log.log(Log.FINE, " \n ****insidetsClaimFilterVO", claimFilterVO);
			try {
				pageClaim = delegate.findClaimList(claimFilterVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				if(pageClaim != null && pageClaim.size() > 0){
					session.setListClaimDisplayPage(pageClaim);
					finalMap = indexMap;
					if (session.getListClaimDisplayPage() != null) {
						finalMap = buildIndexMapForClaims(indexMap, session.getListClaimDisplayPage());
						session.setIndexMap(finalMap);
					}
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
			}
			log.log(Log.FINE, " \n ###########pageClaim###########");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = CLAIM;
			return;
		}else if(form.getEnquiryType() != null 
				&& STOCK.equals(form.getEnquiryType())){
			Collection<ErrorVO> stockerrors =new ArrayList<ErrorVO>();
			StockDetailsVO stockDetailsVO = new StockDetailsVO();
			StockDetailsFilterVO stockDetailsFilterVO = new StockDetailsFilterVO();
			stockDetailsFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			stockDetailsFilterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
			//stockDetailsFilterVO.setCustomerCode(customerVO.getAgentCode());
			stockDetailsFilterVO.setEndDate(toDate.setDate(form.getToDate()));
			stockDetailsFilterVO.setStartDate(fromDate.setDate(form.getFromDate()));
			log.log(Log.FINE, " \n ****insideStockDetails", stockDetailsVO);
			try {
				stockDetailsVO = delegate
						.findCustomerStockDetails(stockDetailsFilterVO);
			} catch (BusinessDelegateException e) {
				stockerrors = handleDelegateException(e);
			}
			if(stockerrors.size()> 0){
				for(ErrorVO vo : stockerrors){
					if(STOCK_HOLDER.equals(vo.getErrorCode())){
						errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.invalidstockholder"));
					}
				}
				invocationContext.addAllError(errors);
			}else{
				if(stockDetailsVO != null ){
					session.setStockDetailsDisplayPage(stockDetailsVO);
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
			}
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = STOCK;
			return;
		}else if(form.getEnquiryType() != null 
				&& MESSAGING.equals(form.getEnquiryType())){
			Page<MessageHistoryVO>  pageMessage = null;		//Changed by A-5204 for bug ICRD-20499
			MessageListFilterVO  messageListFilterVO  = new MessageListFilterVO ();
			
			messageListFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			messageListFilterVO.setParticipantName(form.getCustomerCode().toUpperCase());   //Changed by A-5204 for bug ICRD-20499
			messageListFilterVO.setParticipantType(BaseMessageVO.PARTY_TYPE_AGENT);		   //Changed by A-5204 for bug ICRD-20499
			messageListFilterVO.setToDateTime(toDate.setDate(form.getToDate()));
			messageListFilterVO.setFromDateTime(fromDate.setDate(form.getFromDate()));
			//pagination
			if(form.getAbsoluteIndex() != null){
				messageListFilterVO.setAbsoluteIndex(Integer.parseInt(form
	    				.getAbsoluteIndex()));
			}else{
				messageListFilterVO.setAbsoluteIndex(1);
			}
			if(form.getDisplayPageNum() != null){
				messageListFilterVO.setPageNumber(Integer.parseInt(form
	    				.getDisplayPageNum()));
			}else{
				messageListFilterVO.setPageNumber(1);
			}
			log.log(Log.FINE, " \n ****inside**MESSAGING", messageListFilterVO);
			Collection<String> address=null;			//Changed by A-5204 for bug ICRD-20499
			try {
				pageMessage = delegate
						.findMessageForCustomers(messageListFilterVO,address);	 //Changed by A-5204 for bug ICRD-20499
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}			
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				if(pageMessage != null && pageMessage.size() > 0){
					session.setListMessageDisplayPage(pageMessage);
					finalMap = indexMap;
					if (session.getListMessageDisplayPage() != null) {
						finalMap = buildIndexMapForMessaging(indexMap, session.getListMessageDisplayPage());
						session.setIndexMap(finalMap);
					}
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
			}
			log.log(Log.FINE, " \n ###########pageMessageret@@c###########",
					pageMessage.size());
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = MESSAGING;
			return;
		}else if(form.getEnquiryType() != null 
				&& ALLOTMENT.equals(form.getEnquiryType())){
			CustomerAllotmentEnquiryFilterVO filterVO = new CustomerAllotmentEnquiryFilterVO();
			Page<CustomerAllotmentEnquiryVO> pageAllotment = null;
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
			filterVO.setToDate(toDate.setDate(form.getToDate()));
			filterVO.setFromDate(fromDate.setDate(form.getFromDate()));
			filterVO.setAbsoluteIndex(Integer.parseInt(form.getAbsoluteIndex()));
			if(form.getDisplayPageNum() != null){
				filterVO.setPageNumber(Integer.parseInt(form
	    				.getDisplayPageNum()));
			}else{
				filterVO.setPageNumber(1);
			}
			log.log(Log.FINE, " \n ****inside*ALLOTMENTxxxxxx", filterVO);
			try {
				pageAllotment =  delegate.findCustomerEnquiryDetails(filterVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}	
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				if(pageAllotment != null && pageAllotment.size() > 0){
					session.setListAllotmentDisplayPage(pageAllotment);
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
			}
			log.log(Log.FINE, " \n #######allot###############");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = ALLOTMENT;
			return;
		}else if(form.getEnquiryType() != null 
				&& SPOT.equals(form.getEnquiryType())){
			log.log(Log.FINE, "@#@#@###yyyy", form.getEnquirySpotList());
			if(form.getEnquirySpotList() != null && 
					YES.equals(form.getEnquirySpotList())){
				form.setEnquirySpotList(null);
				Page<SpotRateRequestDetailsVO> pageSpot = null;
				SpotRateRequestFilterVO  spotFilterVO = new  SpotRateRequestFilterVO();
				spotFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				spotFilterVO.setCustomerCode(form.getAgentCode().toUpperCase());
				//spotFilterVO.setToDate(toDate.setDate(form.getToDate()));
				//spotFilterVO.setFromDate(fromDate.setDate(form.getFromDate()));
				if(form.getDisplayPageNum() != null){
					spotFilterVO.setPageNumber(Integer.parseInt(form
		    				.getDisplayPageNum()));
		    	}
				log.log(Log.FINE, " \n ****inside33spotrates", spotFilterVO);
				try {
					pageSpot = delegate
							.findSpotRateRequestsByFilter(spotFilterVO);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if(errors.size()> 0){
					invocationContext.addAllError(errors);
				}else{
					if(pageSpot != null && pageSpot.size() >0){
						session.setListSpotDisplayPage(pageSpot);
					}else{
						errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
						invocationContext.addAllError(errors); 
					}
				}
			}
			
			log.log(Log.FINE, " \n #######pageSpot###############");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = SPOT;
			return;
		}else if(form.getEnquiryType() != null 
				&& CONTRACT.equals(form.getEnquiryType())){
			log.log(Log.FINE, " \n ****inside*CONTRACT");
			TariffFilterVO tariffFilterVO =new TariffFilterVO();
			Page<RateLineVO> pageContract = null;
			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			tariffFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			tariffFilterVO.setCurrentDate(currentdate);
			tariffFilterVO.setStartDate(fromDate);
			tariffFilterVO.setEndDate(toDate);
			
			//pagination
			if(form.getAbsoluteIndex() != null){
				tariffFilterVO.setAbsoluteIndex(Integer.parseInt(form
	    				.getAbsoluteIndex()));
			}else{
				tariffFilterVO.setAbsoluteIndex(1);
			}
			if(form.getDisplayPageNum() != null){
				tariffFilterVO.setPageNumber(Integer.parseInt(form
	    				.getDisplayPageNum()));
			}else{
				tariffFilterVO.setPageNumber(1);
			}
			log.log(Log.FINE, "Tarunxxxxxxxxxxxxxx", tariffFilterVO);
			try {
				pageContract = delegate
						.findRateLineDetailsByFilter(tariffFilterVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}	
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				pageContract =  updateRateLineVO(pageContract);
				if(pageContract != null && pageContract.size() >0){
					session.setListContractDisplayPage(pageContract);
					finalMap = indexMap;
					if (session.getListContractDisplayPage() != null) {
						finalMap = buildIndexMapForContract(indexMap, session.getListContractDisplayPage());
						session.setIndexMap(finalMap);
					}
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
				
			}
			log.log(Log.FINE, " \n #######pageContract###############");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = CONTRACT;
			return;
		}else if(form.getEnquiryType() != null 
				&& TERMINAL.equals(form.getEnquiryType())){
			//terminalhandling
			log.log(Log.FINE, " \n ****inside*terminalhandling");
			InvoiceFilterVO invoiceFilterVO = new InvoiceFilterVO();
			Page<InvoiceDetailsVO> pageTerminal = null;
			invoiceFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceFilterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
			invoiceFilterVO.setToDate(toDate.setDate(form.getToDate()));
			invoiceFilterVO.setFromDate(fromDate.setDate(form.getFromDate()));
			form.setAirportCode(customerVO.getAirportCode());
			//form.setAirportCode("SYD");
			if(form.getAirportCode() != null &&
					form.getAirportCode().trim().length()>0){
				invoiceFilterVO.setStationCode(form.getAirportCode());
			}else{
				errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.airporcodenull"));
				invocationContext.addAllError(errors);
				invocationContext.target = TERMINAL;
				return;
			}
			if(form.getDisplayPageNum() != null){
				invoiceFilterVO.setPageNumber(Integer.parseInt(form
	    				.getDisplayPageNum()));
	    	}
			invoiceFilterVO.setAbsoluteIndex(Integer.parseInt(form.getAbsoluteIndex()));
			try {
				pageTerminal = delegate.listInvoices(invoiceFilterVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				if(pageTerminal != null && pageTerminal.size() >0){
					session.setListTerminalDisplayPage(pageTerminal);
				}else{
					errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
					invocationContext.addAllError(errors); 
				}
				
			}
			log.log(Log.FINE, " \n #######pageTerminal###############");
			session.setEnquiryBookingList(form.getEnquiryBookingList());
			session.setEnquirySpotList(form.getEnquirySpotList());
			invocationContext.target = TERMINAL;
			return;
		}else if(form.getEnquiryType() != null 
				&& CHARTER.equals(form.getEnquiryType())){
			log.log(Log.FINE, " \n ****inside*CHARTER");
			ListCharterOperationsFilterVO capFilterVO = new ListCharterOperationsFilterVO();
			capFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			capFilterVO.setCustomerCode(form.getCustomerCode().toUpperCase()); 
			capFilterVO.setToDate(toDate.setDate(form.getToDate()));
			capFilterVO.setFromDate(fromDate.setDate(form.getFromDate()));
			capFilterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNum()));
			Page<CharterOperationsVO> pageCharter = null;
			try {
				pageCharter = delegate.findCharterForCustomer(capFilterVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors.size()> 0){
				invocationContext.addAllError(errors);
			}else{
				if (pageCharter != null) {
					if(pageCharter.size() > 0){
						session.setListCharterDisplayPage(pageCharter);
					}else{
						if(pageCharter != null && pageCharter.size() >0){
							session.setListCharterDisplayPage(null);
						}else{
							errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
							invocationContext.addAllError(errors); 
						}
					}
				}
			}
			log.log(Log.FINE, " \n #######CHARTER###############");
			invocationContext.target = CHARTER;
			return;
		}else if(form.getEnquiryType() != null 
				&& SELECT.equals(form.getEnquiryType())){
			invocationContext.target = SELECT;
			return;
		}
		session.setEnquiryBookingList(form.getEnquiryBookingList());
		session.setEnquirySpotList(form.getEnquirySpotList());
		invocationContext.target = SUCCESS;
		
	}

	private Collection<ErrorVO> validateStationCode(
			CustomerEnquiryForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirportValidationVO airportValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getStation()
					.toUpperCase());
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}
	
	 private Collection<ErrorVO> validateCommodityvalues(String formcommodity) {
			log.entering("ListEnquiryCommand", "validateCommodity");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			Collection<String> commodities = new ArrayList<String>();
				commodities.add(formcommodity);
			HashMap<String,CommodityValidationVO> commodityMap = null;
			if (formcommodity != null && formcommodity.trim().length() > 0) {
			log.log(Log.FINE, " \n commodities  ----------> ", commodities);
			try {
				CommodityDelegate commodityDelegate = new CommodityDelegate();
				commodityMap = (HashMap<String,CommodityValidationVO>)
						commodityDelegate.validateCommodityCodes(
								logonAttributes.getCompanyCode(),commodities);
			} catch (BusinessDelegateException businessDelegateException) {
				errorVOs = handleDelegateException(businessDelegateException);
			}
			
			}
			return errorVOs;
		}
	 
		private Collection<ErrorVO> validateDates(
				CustomerEnquiryForm form) {
			log.entering("ListCallingHistoryCommand", "validateDates");
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			if(form.getFromDate() !=null && form.getToDate() != null){
				if ((!("").equals(form.getFromDate()))
						&& (!("").equals(form.getToDate()))) {
					if (!form.getFromDate().equals(form.getToDate())) {
						if (DateUtilities.isGreaterThan(form.getFromDate(), form
								.getToDate(), "dd-MMM-yyyy")) {
							ErrorVO errorVO = new ErrorVO(
									"customermanagement.defaults.custenq.msg.err.fromdategreaterthantodate");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
						}
					}
				}
			}
			
			log.exiting("ListCallingHistoryCommand", "validateDates");
			return errors;
		}
		
	private HashMap buildIndexMapForMessaging(HashMap indexMap,
			Page<MessageHistoryVO> messageVOPage) {
		HashMap existingMap = indexMap;
		String indexPage = String.valueOf((messageVOPage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(messageVOPage
					.getAbsoluteIndex()));
		}

		return existingMap;
	}
	
	
	private HashMap buildIndexMapForBooking(HashMap indexMap,
			Page<BookingVO> bookingVOVOPage) {
		HashMap existingMap = indexMap;
		String indexPage = String.valueOf((bookingVOVOPage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(bookingVOVOPage
					.getAbsoluteIndex()));
		}

		return existingMap;
	}
	
	private HashMap buildIndexMapForContract(HashMap indexMap,
			Page<RateLineVO> rateVOPage) {
		HashMap existingMap = indexMap;
		String indexPage = String.valueOf((rateVOPage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(rateVOPage
					.getAbsoluteIndex()));
		}

		return existingMap;
	}
	
	private HashMap buildIndexMapForClaims(HashMap indexMap,
			Page<ClaimListVO> claimVOPage) {
		HashMap existingMap = indexMap;
		String indexPage = String.valueOf((claimVOPage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(claimVOPage
					.getAbsoluteIndex()));
		}

		return existingMap;
	}
	
	private void clearSession(){
		
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		session.setListBookingDisplayPage(null);
		session.setListClaimDisplayPage(null);
		session.setListStockDisplayPage(null);
		session.setListMessageDisplayPage(null);
		session.setListAllotmentDisplayPage(null);
		session.setListSpotDisplayPage(null);
		session.setListContractDisplayPage(null);
		session.setListTerminalDisplayPage(null);
		session.setListCharterDisplayPage(null);
		session.setIndexMap(null);
		
	}
	
	
	public Page<RateLineVO> updateRateLineVO(Page<RateLineVO>  rateLineVos){
		log.log(Log.FINE, "\n Inside UpdateRateLineVO");
		MaintainRateCardSession maintainRateCardSession =
			(MaintainRateCardSession) getScreenSession(TARIFF_MODULE, TARIFF_SCREENID);
		rateLineVos = new Page<RateLineVO>(new ArrayList<RateLineVO>(),0,0,0,0,0,false);
		HashMap<String,Collection<String>> tariffParameterCodes = new HashMap<String,Collection<String>>();
		/*	
			ArrayList<TariffParameterVO> tariffParameters = new ArrayList<TariffParameterVO>();
			TariffParameterVO tariffParameterVO = null;
			TariffVO tariffVO = maintainRateCardSession.getTariffVO();
			for(TariffParameterVO globalParameterVO : maintainRateCardSession.getTariffParameterVOs()){
			tariffParameterVO = null;
			if(tariffVO.getTariffParameterVOs()!=null){
				for(TariffParameterVO tariffParameter : tariffVO.getTariffParameterVOs()){
					if(tariffParameter.getParameterCode().equals(globalParameterVO.getParameterCode())){
						tariffParameterVO = tariffParameter;
					}
					tariffParameterCodes.put(tariffParameter.getParameterCode(),
							tariffParameter.getParameterValues());
				}
			}
			if(tariffParameterVO!=null){
				tariffParameters.add(tariffParameterVO);
			}
			else{
				tariffParameterVO = new TariffParameterVO();
				tariffParameterVO.setCompanyCode(globalParameterVO.getCompanyCode());
				tariffParameterVO.setParameterCode(globalParameterVO.getParameterCode());
				tariffParameterVO.setParameterDescription(globalParameterVO.getParameterDescription());
				tariffParameters.add(tariffParameterVO);
			}
			}
			maintainRateCardSession.setTariffParameterCodes(tariffParameterCodes);
			tariffVO.setTariffParameterVOs(tariffParameters);
		
		*/
		ArrayList<String> rateCharges = null;
		ArrayList<String> chargeTypes = null;
		HashMap<String, Collection<String>> basisVales = null;
		ArrayList<RateLineParameterVO> rateLineParameters = null;
		for(RateLineVO rateLineVO : rateLineVos){
			rateLineParameters = new ArrayList<RateLineParameterVO>();
			chargeTypes = new ArrayList<String>();
			rateCharges = new ArrayList<String>();
			basisVales = new HashMap<String, Collection<String>>();
			if("BKSYS".equals(rateLineVO.getRatingBasisCode())){
				BKBasisVO bkBasisVO = (BKBasisVO) rateLineVO.getRatingBasis();
				chargeTypes.add("BC");
				rateCharges.add(String.valueOf(bkBasisVO.getBaseCharge()));
				chargeTypes.add("R");
				rateCharges.add(String.valueOf(bkBasisVO.getRatePerKg()));
			}
			else if("WGTBRK".equals(rateLineVO.getRatingBasisCode())){
				WeightBreakBasisHolderVO weightBreakBasisHolderVO =
					(WeightBreakBasisHolderVO) rateLineVO.getRatingBasis();
				log.log(Log.INFO, "rateLineVO============",
						weightBreakBasisHolderVO.getMinimumCharge());
				if(weightBreakBasisHolderVO.getMinimumCharge()>=0.0){
					log.log(Log.INFO, "rateLineVO============",
							weightBreakBasisHolderVO.getMinimumCharge());
					chargeTypes.add("M");
					rateCharges.add(String.valueOf(weightBreakBasisHolderVO.getMinimumCharge()));
				}
				/*
				 * Modified by sudhin on 13-Nov-2007
				 * for diplaying normal rate even if
				 * minimum chagre is not specified
				 */
				if(weightBreakBasisHolderVO.getNormalRate()>=0.0){
					chargeTypes.add("N");
					rateCharges.add(String.valueOf(weightBreakBasisHolderVO.getNormalRate()));
				}
				if(weightBreakBasisHolderVO.getWeightSlabVOs()!=null){
					for(WeightSlabVO weightSlabVO : weightBreakBasisHolderVO.getWeightSlabVOs()){
						chargeTypes.add("+ "+String.valueOf(weightSlabVO.getFromWeight()));
						rateCharges.add(String.valueOf(weightSlabVO.getRate()));
					}
				}
			}
			else if(rateLineVO.getRatingBasisCode().startsWith("CLS")){
				if(maintainRateCardSession.getPercentageOperators()== null){
					fetchPercentageOperatorsDetails();
				}
				ArrayList<OneTimeVO> percentageOperators = maintainRateCardSession.getPercentageOperators();
				ClassRateBasisVO classRateBasisVO = (ClassRateBasisVO) rateLineVO.getRatingBasis();
				for(OneTimeVO oneTimeVO : percentageOperators){
					if(oneTimeVO.getFieldValue().equals(classRateBasisVO.getPercentageOperator())){
						classRateBasisVO.setPercentageOperator(oneTimeVO.getFieldDescription());
					}
				}
				chargeTypes.add(classRateBasisVO.getPercentageOperator());
				
				/*
				 * modified by sudhin on 29-Oct-2007
				 * for NCA CR (class rating cr)
				 */
				/*String chargeWithType = new StringBuffer(String.valueOf(classRateBasisVO
						.getPercentageValue()))
				.append(" (").append(String.valueOf(classRateBasisVO
						.getRatingOn())).append(
						")").toString();
				rateCharges.add(chargeWithType);*/
			}
			else if("PVTCHG".equals(rateLineVO.getRatingBasisCode())){
				ULDBasisHolderVO uldBasisHolderVO = (ULDBasisHolderVO) rateLineVO.getRatingBasis();
				ArrayList<String> uldCategories = new ArrayList<String>();
				ArrayList<String> uldCodes = new ArrayList<String>();
				if(uldBasisHolderVO.getOverFlowRate()>=0){
					chargeTypes.add("O");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getOverFlowRate()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getFirstOverPivotRate()>=0){
					chargeTypes.add("C");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getFirstOverPivotRate()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getSecondOverPivotRate()>=0){
					chargeTypes.add("E");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getSecondOverPivotRate()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getThirdOverPivotRate()>=0){
					chargeTypes.add("G");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getThirdOverPivotRate()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getMinimumCharge()>=0){
					chargeTypes.add("M");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getMinimumCharge()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getNormalRate()>=0){
					chargeTypes.add("N");
					rateCharges.add(String.valueOf(uldBasisHolderVO.getNormalRate()));
					uldCategories.add("");
					uldCodes.add("");
				}
				if(uldBasisHolderVO.getUldSlabVOs()!=null){
					for(ULDSlabVO uldSlabVO : uldBasisHolderVO.getUldSlabVOs()){
						if("Q".equals(uldSlabVO.getUldChargeCode())){
							uldCategories.add("");
							uldCodes.add("");
							chargeTypes.add(new StringBuilder("+ ").append(String.valueOf(uldSlabVO.getUldWeight())).toString());
						}
						else{
							chargeTypes.add(new StringBuilder(uldSlabVO.getUldChargeCode()).
								append(" - ").append(
										uldSlabVO.getUldWeight()).toString());
							if(!"GEN".equals(uldSlabVO.getUldCategory())){
								uldCategories.add(uldSlabVO.getUldCategory());
								uldCodes.add(uldSlabVO.getUldCode());
							}
							else{
								uldCategories.add("");
								uldCodes.add("");
							}
						}
						rateCharges.add(String.valueOf(uldSlabVO.getUldRate()));
					}
					basisVales.put("uldCategories",uldCategories);
					basisVales.put("uldCodes",uldCodes);
				}
			}
			basisVales.put("chargeTypes",chargeTypes);
			basisVales.put("rateCharges",rateCharges);
			rateLineVO.setBasisValues(basisVales);
			for(RateLineParameterVO rateLineParameterVO : rateLineVO.getRateLineParameterVOs()){
				if(tariffParameterCodes.containsKey(rateLineParameterVO.getParameterCode())){
					ArrayList<String> tariffParametervalues = (ArrayList<String>)tariffParameterCodes.get(rateLineParameterVO.getParameterCode());
					if(!rateLineParameterVO.getParameterValues().toString().equals(tariffParametervalues.toString())){
						rateLineParameters.add(rateLineParameterVO);
					}
				}
				else{
					rateLineParameters.add(rateLineParameterVO);
				}
			}
			rateLineVO.setRateLineParameterVOs(rateLineParameters);
			if (!RateLineVO.MANUAL_RATE.equals(rateLineVO.getRateLineSource())) {
				rateLineVO.setRateLineSource(RateLineVO.TACT_RATE);
			}
			log.log(Log.INFO, "rateLineVO============", rateLineVO);
		}
		//maintainRateCardSession.setRateLineVOs(rateLineVos);
		return rateLineVos;
	}
	
	
	private void  fetchPercentageOperatorsDetails(){
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainRateCardSession session =
			(MaintainRateCardSession) getScreenSession(TARIFF_MODULE, TARIFF_SCREENID);
		Map<String,Collection<OneTimeVO>> hashMap = new 
		HashMap<String,Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(PERCENTAGE_OPERATORS);
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try{
			hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
			
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		Collection<OneTimeVO> percentage = hashMap.get(PERCENTAGE_OPERATORS);
		session.setPercentageOperators(new ArrayList<OneTimeVO>(hashMap
				.get(PERCENTAGE_OPERATORS)));
	}
	
	
}
