/*
 * ReservationController.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.BlacklistedRangeExistsException;
import com.ibsplc.icargo.business.stockcontrol.defaults.InvalidStockHolderException;
import com.ibsplc.icargo.business.stockcontrol.defaults.RangeExistsException;
import com.ibsplc.icargo.business.stockcontrol.defaults.Stock;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockControlDefaultsBusinessException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockController;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockNotFoundException;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.DocumentTypeProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 * 
 */
public class ReservationController {
	private Log log = LogFactory.getLogger("RESERVATION LISTING");
	private static final String STOCK_DEFAULTS_DOCUMENTTYPE = "stock.reserveawb.defaultdocumentType";
	private static final String STOCK_DEFAULTS_STOCKHOLDERCODE = "stock.defaults.defaultstockholdercodeforcto";
	private static final String STOCK_DEFAULTS_DOCUMENTSTATUS = "stockcontrol.reservation.cancelstatus";
	private static final String STOCK_DEFAULTS_EXPIRYPERIOD = "stockcontrol.reservation.expiryperiod";

	private static final String REPORT_ID="RPTLST030";
	
	private static final String REPORT_ID_RSVAWB = "RPTOPR033";
	
	/**
	 * This method used to reserve the AWB,there are 2 ways which AWB can be
	 * reserved 
	 * 1)General Mode 
	 * 2)Specific Mode 
	 * 
	 * Steps to be Followed while reserving the AWB 
	 * 1)Find the corresponding Stock object for company,airport,airline 
	 * and the stockholder( which will be identifed by the systemparameter),
	 * using the method findStockForAirline  
	 * 2)check the no of AWBs to be reserved can be availed from the stock. 
	 * if not throw StockNotExistsException 
	 * 3)check whether the airline stock is blocked ,if so throw an exception 
	 * 4)get the corresponding airline entity. if its specific mode, 
	 * for the number of AWBs get the range and call mod function
	 * using the checkdigit of airline, append the result with range 
	 * and deplete the stock range. 
	 * 6)check whether the AWB is blocked or not. 
	 * 7)For general Mode,pick the document from the existing range 
	 * 8)For each doument Number to be reserved,create ReservationVO and 
	 * persist the value 
	 * 9)After resevation ,reduce the stock level of the airline. 
	 * call depleteStock in StockHolder Business class and 
	 * update the stock Business Object. 
	 * 10)Check for the Re-Order level,if re-order level falls below 
	 * the limit,throw a warning message
	 * 
	 * @param reserveAWBVO
	 * @return
	 * @throws SystemException
	 * @throws CreateException
	 * @throws FinderException
	 * @throws StockControlDefaultsBusinessException
	 * @throws RangeExistsException
	 * @throws BlacklistedRangeExistsException
	 * @throws StockNotFoundException
	 * @throws InvalidStockHolderException
	 * @throws ReservationException
	 */
	public ReserveAWBVO reserveDocument(ReserveAWBVO reserveAWBVO)
		throws SystemException, CreateException, FinderException, 
			StockControlDefaultsBusinessException, RangeExistsException,
			BlacklistedRangeExistsException, StockNotFoundException,
			InvalidStockHolderException, ReservationException{
		
		log.log(Log.INFO,"ENTRY");

		Collection<String> systemParameterCodes = new ArrayList<String>();
		Map<String, String> syParMap = null;
		
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		Collection<String> parameterCodes = new ArrayList<String>();
		Map<String, String> parMap = null;

		
		int checkDigit = 0;
		StockFilterVO stockFilterVO = new StockFilterVO();
		stockFilterVO.setCompanyCode(reserveAWBVO.getCompanyCode());
		stockFilterVO.setAirlineIdentifier(reserveAWBVO.getAirlineIdentifier());
		
		
		
		systemParameterCodes.add(STOCK_DEFAULTS_DOCUMENTTYPE);
		syParMap = Stock.findSystemParameterByCodes(systemParameterCodes);
		
		parameterCodes.add(STOCK_DEFAULTS_STOCKHOLDERCODE);
		parMap = Stock.findAirportParametersByCode(logon.getCompanyCode(), 
				logon.getAirportCode(), parameterCodes);
		
		if(parMap == null || parMap.get(STOCK_DEFAULTS_STOCKHOLDERCODE) == null){
			log.log(Log.SEVERE,"STOCK_DEFAULTS_STOCKHOLDERCODE NOT FND");
			log.log(Log.SEVERE,"so... throwing STOCK_NOTEXISTS");
			throw new ReservationException(ReservationException.STOCK_NOTEXISTS,
					new Object[]{reserveAWBVO.getAirlineCode(),reserveAWBVO.getDocumentSubType()});
			
		}
		String stockHolderCode = parMap.get(STOCK_DEFAULTS_STOCKHOLDERCODE).toString();
		String documentType = null;
		if(reserveAWBVO.getDocumentType() == null){
			documentType = syParMap.get(STOCK_DEFAULTS_DOCUMENTTYPE);
		}else{
			documentType = reserveAWBVO.getDocumentType();
		}
		
		stockFilterVO.setStockHolderCode(stockHolderCode);
		
		stockFilterVO.setDocumentType(documentType);

		stockFilterVO.setDocumentSubType(reserveAWBVO.getDocumentSubType());

		log.log(Log.INFO, "stockHolderCode : ", stockHolderCode);
		log.log(Log.INFO, "documentType : ", documentType);
		StockAllocationVO stockAllocationVO = new StockController().findStockForAirline(stockFilterVO);
		
		if(stockAllocationVO == null || stockAllocationVO.getRanges() == null){
			log.log(Log.SEVERE, "NO STOCK FOUND for Airline: ", reserveAWBVO.getAirlineCode());
			throw new ReservationException(ReservationException.STOCK_NOTEXISTS,
					new Object[]{reserveAWBVO.getAirlineCode(),reserveAWBVO.getDocumentSubType()});
		}
/*		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		stockAllocationVO.setCompanyCode(reserveAWBVO.getCompanyCode());
		stockAllocationVO.setAirlineIdentifier(reserveAWBVO.getAirlineIdentifier());
		//stockAllocationVO.setConfirmationRequired(reserveAWBVO.getc);
		stockAllocationVO.setDocumentSubType(reserveAWBVO.getDocumentSubType());
		stockAllocationVO.setDocumentType(reserveAWBVO.getDocumentType());
		stockAllocationVO.setLastUpdateUser(reserveAWBVO.getLastUpdateUser());
		stockAllocationVO.setRemarks(reserveAWBVO.getRemarks());
		stockAllocationVO.setStockHolderCode(stockHolderCode);
		//stockAllocationVO.set
*/		
		
		if(reserveAWBVO.getDocumentSubType() == null){
			log.log(Log.FINE,"setting docSubType in ReserveAWBVO=======");
			reserveAWBVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
		}
		
		AirlineValidationVO airlineValidationVO = null;
		
		try {
			// to get the checkDigit
			airlineValidationVO = new SharedAirlineProxy().findAirline(reserveAWBVO.getCompanyCode(),reserveAWBVO.getAirlineIdentifier());			
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		
		if(airlineValidationVO != null){
			checkDigit = airlineValidationVO.getAwbCheckDigit();
		}
		
		log.log(Log.INFO, "checkDigit in airlineValidationVO :", checkDigit);
		// this is to validate the specific awbs, which throws exceptions
		// if invalid awbs are  present
		if(reserveAWBVO.getSpecificDocNumbers() != null){
			new StockController().validateSpecificAWBs(reserveAWBVO,stockHolderCode,checkDigit);
		}
		
		if(reserveAWBVO.isGeneralMode() && reserveAWBVO.getNumberOfDocuments() > 0){
			if(checkDigit > 0){
				Collection<String> documents = null;
				documents = new StockController().reserveGeneralDocuments(
						stockAllocationVO,reserveAWBVO,checkDigit);
				
				reserveAWBVO.setDocumentNumbers(documents);
			}			
		}else {
			if(reserveAWBVO.getSpecificDocNumbers() != null){
				reserveAWBVO.setDocumentNumbers(reserveAWBVO.getSpecificDocNumbers());
			}
		}
		
//		Page<BlacklistStockVO> blackListedStocks = null;
//		blackListedStocks = new StockController().findBlacklistedStock(stockFilterVO,1);
		
		//To be reviewed	Black listed stocks not considered till now
		
		log.log(Log.INFO, "documentNumbers : ", reserveAWBVO.getDocumentNumbers());
		Collection<RangeVO> rangesForDepletion = null;
		
		if(reserveAWBVO.getDocumentNumbers() != null){
			rangesForDepletion = new ArrayList<RangeVO>();
			for(String documentNumber : reserveAWBVO.getDocumentNumbers()){
				
				ReservationVO reservationVO = new ReservationVO();
				reservationVO.setCompanyCode(reserveAWBVO.getCompanyCode());
				reservationVO.setAirlineCode(reserveAWBVO.getAirlineCode());
				reservationVO.setAirlineIdentifier(reserveAWBVO.getAirlineIdentifier());
				reservationVO.setAirportCode(reserveAWBVO.getAirportCode());
				reservationVO.setCustomerCode(reserveAWBVO.getCustomerCode());
				reservationVO.setDocumentNumber(documentNumber);
				reservationVO.setDocumentType(reserveAWBVO.getDocumentType());
				reservationVO.setDocumentSubType(reserveAWBVO.getDocumentSubType());
				reservationVO.setExpiryDate(reserveAWBVO.getExpiryDate());
				reservationVO.setLastUpdateUser(reserveAWBVO.getLastUpdateUser());
				reservationVO.setLastUpdateTime(reserveAWBVO.getLastUpdateTime());
				reservationVO.setReservationDate(reserveAWBVO.getReservationDate());
				reservationVO.setReservationRemarks(reserveAWBVO.getRemarks());
				reservationVO.setShipmentPrefix(reserveAWBVO.getShipmentPrefix());

				log.log(Log.FINER,
						"--going to create reservation with reservationvo",
						reservationVO);
				Reservation reservation = new Reservation(reservationVO);

				/*
				 * for auditing reservation
				 */
				if(reserveAWBVO.getSpecificDocNumbers() != null && reserveAWBVO.getSpecificDocNumbers().contains(documentNumber)){
					auditReserveAWB(reservation,reservationVO,true);
				}else{
					auditReserveAWB(reservation,reservationVO,false);
				}
				/*
				 * auditing of reservation ends here
				 */
				
				RangeVO rangeVO = new RangeVO();
				rangeVO.setCompanyCode(reservationVO.getCompanyCode());
				rangeVO.setAirlineIdentifier(reservationVO.getAirlineIdentifier());
				rangeVO.setDocumentType(reservationVO.getDocumentType());
				rangeVO.setDocumentSubType(reservationVO.getDocumentSubType());
				rangeVO.setStockAcceptanceDate(reservationVO.getLastUpdateTime());
				
				boolean hasCheckDigit = true;
				hasCheckDigit = new StockController().validateCheckDigitFlag(
											rangeVO.getCompanyCode(), 
											rangeVO.getDocumentType(), 
											rangeVO.getDocumentSubType());
				if(hasCheckDigit){
					rangeVO.setStartRange(documentNumber.substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT));
				}else{
					rangeVO.setStartRange(documentNumber);
				}
				
				rangeVO.setEndRange(rangeVO.getStartRange());
				rangesForDepletion.add(rangeVO);
				
			}
			
			if(rangesForDepletion != null){
				Collection<RangeVO> updatedRangesForDepletion = null;
				
				log.log(Log.INFO, "oldRangesForDepletion : ",
						rangesForDepletion);
				log.log(Log.INFO, "updatedRangesForDepletion : ",
						updatedRangesForDepletion);
				stockAllocationVO.setConfirmationRequired(false);
				stockAllocationVO.setAirportCode(reserveAWBVO.getAirportCode());
				stockAllocationVO.setStockHolderCode(stockHolderCode);
				stockAllocationVO.setStockControlFor(stockHolderCode);
				stockAllocationVO.setRanges(rangesForDepletion);				
				stockAllocationVO.setNewStockFlag(false);
				StockAllocationVO stkAllVO = new StockController().allocateStock(stockAllocationVO);
				if(stkAllVO != null && stkAllVO.isHasMinReorderLevel()){
					reserveAWBVO.setHasMinReorderLevel(true);
				}
			}
		}
		
		log.log(Log.INFO, "RETURN", reserveAWBVO);
		return reserveAWBVO;
	}

	/**
	 * 
	 */
/*	private void validateReOrderLevel() {
	}
*/
	/**
	 * 
	 * @param reservationFilterVO
	 * @return Collection<ReservationVO>
	 * @throws SystemException
	 */
	public Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws SystemException {
		return Reservation.findReservationListing(reservationFilterVO);
	}

	/**
	 * @param reservationVO
	 * @throws SystemException
	 * @throws ReservationException
	 */
	public void extendReservation(Collection<ReservationVO> reservationVO)
			throws SystemException, ReservationException {
		// find the ReservationBo,call the update method in ReservationBO
		for (ReservationVO reservationvo : reservationVO) {
			if (OPERATION_FLAG_UPDATE.equals(reservationvo.getOperationFlag())) {
				Reservation extendreservation = Reservation.find(
								reservationvo.getCompanyCode(), 
								reservationvo.getAirlineIdentifier(),
								reservationvo.getAirportCode(), 
								reservationvo.getDocumentNumber());
				
				// auditing extend reservation
				auditExtendReservation(extendreservation,reservationvo);
				
				extendreservation.setReservationRemarks(reservationvo.getReservationRemarks());
				extendreservation.setExpiryDate(reservationvo.getExpiryDate());
				log.log(Log.INFO, "reservation---------->", reservationvo);
			}
		}
	}

	/**
	 * @param reservations
	 * @throws SystemException
	 * @throws RemoveException
	 * @throws ReservationException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void cancelReservation(Collection<ReservationVO> reservations)
			throws SystemException, RemoveException, ReservationException, 
					StockControlDefaultsBusinessException {

		log.log(Log.INFO,"ENTRY");
		Collection<String> systemParameterCodes = new ArrayList<String>();
		Map<String, String> sysParMap = null;
		
		Collection<String> parameterCodes = new ArrayList<String>();
		Map<String, String> parMap = null;
		
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		systemParameterCodes.add(STOCK_DEFAULTS_DOCUMENTSTATUS);
		
		//	added for getting the document type for allocating the cancelled one
		parameterCodes.add(STOCK_DEFAULTS_STOCKHOLDERCODE);
		systemParameterCodes.add(STOCK_DEFAULTS_DOCUMENTTYPE);
			
		sysParMap = Stock.findSystemParameterByCodes(systemParameterCodes);
		String status = sysParMap.get(STOCK_DEFAULTS_DOCUMENTSTATUS).toString();
		
		String documentType = sysParMap.get(STOCK_DEFAULTS_DOCUMENTTYPE).toString();
		
		parMap = Stock.findAirportParametersByCode(logon.getCompanyCode(), 
				logon.getAirportCode(), parameterCodes);
		String stockHolderCode = parMap.get(STOCK_DEFAULTS_STOCKHOLDERCODE).toString();
		
		log.log(Log.INFO, "status---------->", status);
		log.log(Log.INFO, "reservations---------->", reservations);
		for (ReservationVO reservationVO : reservations) {
			if (OPERATION_FLAG_UPDATE.equals(reservationVO.getOperationFlag())) {
				//cancelAWB(reservation,status,stockHolderCode,documentType);
				Reservation cancelreservation =Reservation.find(reservationVO.getCompanyCode(),
						reservationVO.getAirlineIdentifier(),
						reservationVO.getAirportCode(),
						reservationVO.getDocumentNumber());
				cancelreservation.remove();
/*				cancelreservation.setReservationRemarks(reservationVO.getReservationRemarks());
				cancelreservation.setDocumentStatus(status);
				log.log(Log.INFO, "reservationVO---------->" + reservationVO);
*/				
				
				
				// audit the cancelled awb
				auditCancelReservation(cancelreservation,reservationVO);
				
				

				/*
				 * added by sinoob
				 * 
				 * to allocate stock which is cancelled
				 * 
				 */
				StockAllocationVO stockAllocationVO = new StockAllocationVO();
				stockAllocationVO.setRanges(new ArrayList<RangeVO>());
				stockAllocationVO.setCompanyCode(cancelreservation.getReservationPK().getCompanyCode());
				stockAllocationVO.setAirlineIdentifier(cancelreservation.getReservationPK().getAirlineIdentifier());
				stockAllocationVO.setDocumentSubType(cancelreservation.getDocumentType());
				stockAllocationVO.setDocumentType(documentType);
				
				
				stockAllocationVO.setConfirmationRequired(false);
				stockAllocationVO.setStockHolderCode(stockHolderCode);
				//stockAllocationVO.setStockControlFor(stockHolderCode);
				
				stockAllocationVO.setNewStockFlag(true);

				RangeVO rangeVO = new RangeVO();
				rangeVO.setCompanyCode(stockAllocationVO.getCompanyCode());
				rangeVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
				rangeVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
				rangeVO.setDocumentType(stockAllocationVO.getDocumentType());
				String documentNumber = reservationVO.getDocumentNumber().substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT);
				rangeVO.setStartRange(documentNumber);
				rangeVO.setEndRange(documentNumber);
				rangeVO.setStockHolderCode(stockHolderCode);
				rangeVO.setStockAcceptanceDate(reservationVO.getLastUpdateTime());
				stockAllocationVO.getRanges().add(rangeVO);
				
				log.log(Log.INFO, "StockAllocationVO :", stockAllocationVO);
				try {
					new StockController().allocateStock(stockAllocationVO);
				} catch (StockNotFoundException stockNotFoundException) {
					throw new ReservationException(stockNotFoundException);
				} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
					throw new ReservationException(blacklistedRangeExistsException);
				} catch (RangeExistsException rangeExistsException) {
					throw new ReservationException(rangeExistsException);
				} catch (InvalidStockHolderException invalidStockHolderException) {
					throw new ReservationException(invalidStockHolderException);
				} catch (FinderException finderException) {
					throw new SystemException(finderException.getErrorCode(),finderException);
				}
				
				// added by Sinoob ends
			}
			
		}
		log.log(Log.INFO,"RETURN");
	}

	/**
	 * @param reservationVO
	 * @param status
	 * @param stockHolderCode
	 * @param documentType
	 * @throws SystemException
	 * @throws ReservationException
	 * @throws StockControlDefaultsBusinessException
	 */
/*	private void cancelAWB(ReservationVO reservationVO, String status,
						String stockHolderCode, String documentType)
			throws SystemException,ReservationException, StockControlDefaultsBusinessException{

		Reservation cancelreservation =Reservation.find(reservationVO.getCompanyCode(),
					reservationVO.getAirlineIdentifier(),
					reservationVO.getAirportCode(),
					reservationVO.getDocumentNumber());
		reservation.setReservationRemarks(reservationVO.getReservationRemarks());
		reservation.setDocumentStatus(status);
		log.log(Log.INFO, "reservation---------->" + reservation);

		
		 * added by sinoob
		 * 
		 * to allocate stock which is cancelled
		 * 
		 
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		stockAllocationVO.setRanges(new ArrayList<RangeVO>());
		stockAllocationVO.setCompanyCode(cancelreservation.getReservationPK().companyCode);
		stockAllocationVO.setAirlineIdentifier(cancelreservation.getReservationPK().airlineIdentifier);
		stockAllocationVO.setDocumentSubType(cancelreservation.getDocumentType());
		stockAllocationVO.setDocumentType(documentType);
		
		
		stockAllocationVO.setConfirmationRequired(false);
		stockAllocationVO.setStockHolderCode(stockHolderCode);
		//stockAllocationVO.setStockControlFor(stockHolderCode);
		
		stockAllocationVO.setNewStockFlag(true);

		RangeVO rangeVO = new RangeVO();
		rangeVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		rangeVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
		rangeVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
		rangeVO.setDocumentType(stockAllocationVO.getDocumentType());
		String documentNumber = reservationVO.getDocumentNumber().substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT);
		rangeVO.setStartRange(documentNumber);
		rangeVO.setEndRange(documentNumber);
		rangeVO.setStockHolderCode(stockHolderCode);
		
		stockAllocationVO.getRanges().add(rangeVO);
		
		log.log(Log.INFO, "StockAllocationVO :" + stockAllocationVO);
		
		try {
			new StockController().allocateStock(stockAllocationVO);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new ReservationException(stockNotFoundException);
		} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
			throw new ReservationException(blacklistedRangeExistsException);
		} catch (RangeExistsException rangeExistsException) {
			throw new ReservationException(rangeExistsException);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new ReservationException(invalidStockHolderException);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
	}
*/
	

	/**
	 * @param reservationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoveException
	 * @throws StockControlDefaultsBusinessException
	 * @throws ReservationException
	 */
	public void expireReservations(ReservationFilterVO reservationFilterVO) 
		throws SystemException, RemoveException, StockControlDefaultsBusinessException,
				ReservationException{
		int expiryPeriod = 0;

		Collection<String> systemParameterCodes = new ArrayList<String>();
		Map<String, String> sysParMap = null;
		
		systemParameterCodes.add(STOCK_DEFAULTS_EXPIRYPERIOD);
		systemParameterCodes.add(STOCK_DEFAULTS_DOCUMENTSTATUS);
		
		systemParameterCodes.add(STOCK_DEFAULTS_DOCUMENTTYPE);
			
		sysParMap = Stock.findSystemParameterByCodes(systemParameterCodes);
		
		expiryPeriod = Integer.parseInt(sysParMap.get(STOCK_DEFAULTS_EXPIRYPERIOD));
		
		Collection<ReservationVO> reservations = Reservation.findExpiredReserveAwbs(reservationFilterVO, expiryPeriod);
		
		if(reservations != null){
				cancelReservation(reservations);
		}
	}
	
	private ReservationAuditVO retrieveReservationAuditVO(Reservation reservation, ReservationAuditVO reservationAuditVO){
		log.log(Log.INFO,"ENTRY");
		reservationAuditVO.setCompanyCode(reservation.getReservationPK().getCompanyCode());
		reservationAuditVO.setAirportCode(reservation.getReservationPK().getAirportCode());
		reservationAuditVO.setAirlineIdentifier(reservation.getReservationPK().getAirlineIdentifier());
		reservationAuditVO.setDocumentNumber(reservation.getReservationPK().getDocumentNumber());
		log.log(Log.INFO,"RETURN");
		return reservationAuditVO;
	}
	
	
	/*
	 * for auditing the reservations
	 */
	private void auditReserveAWB(Reservation reservation, ReservationVO reservationVO, boolean isSpecificMode) throws SystemException{
		log.log(Log.INFO,"reached before auditing reserveAWB...");
		StringBuffer additionalInfo = new StringBuffer();
		ReservationAuditVO reservationAuditVO = new ReservationAuditVO(ReservationAuditVO.MODULENAME,ReservationAuditVO.SUBMODULENAME,ReservationAuditVO.ENTITYNAME);
		//reservationAuditVO = (ReservationAuditVO)AuditUtils.populateAuditDetails(reservationAuditVO,reservation,true);
		reservationAuditVO = retrieveReservationAuditVO(reservation,reservationAuditVO);
		reservationAuditVO.setShipmentPrefix(reservationVO.getShipmentPrefix());
		reservationAuditVO.setLastUpdateUser(reservationVO.getLastUpdateUser());
		//reservationAuditVO.setEntityclassName("com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationAudit");
		reservationAuditVO.setActionCode(ReservationAuditVO.CREATE_ACTION);
		additionalInfo = additionalInfo.append(ReservationAuditVO.RESERVE_AWB).append("-");
		if(isSpecificMode){
			additionalInfo.append("SPECIFIC MODE");
		}else{
			additionalInfo.append("GENERAL MODE");
		}
		reservationAuditVO.setAdditionalInformation(additionalInfo.toString());
		reservationAuditVO.setAuditRemarks(ReservationAuditVO.ACTION_RESERVE);
		log.log(Log.INFO,"reached before performAudit of reserveAWB...");
		AuditUtils.performAudit(reservationAuditVO);
		log.log(Log.INFO,"reached after performAudit of reserveAWB...");		
	}
	
	/*
	 * for auditing the cancel reservation
	 */
	private void auditCancelReservation(Reservation reservation,ReservationVO reservationVO) throws SystemException{
		log.log(Log.INFO,"reached before auditing cancel reservation...");
		StringBuffer additionalInfo = new StringBuffer();
		ReservationAuditVO reservationAuditVO = new ReservationAuditVO(ReservationAuditVO.MODULENAME,ReservationAuditVO.SUBMODULENAME,ReservationAuditVO.ENTITYNAME);
		//reservationAuditVO = (ReservationAuditVO)AuditUtils.populateAuditDetails(reservationAuditVO,reservation,true);
		reservationAuditVO = retrieveReservationAuditVO(reservation, reservationAuditVO);
		reservationAuditVO.setShipmentPrefix(reservationVO.getShipmentPrefix());
		reservationAuditVO.setLastUpdateUser(reservationVO.getLastUpdateUser());
		//reservationAuditVO.setEntityclassName("com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationAudit");
		reservationAuditVO.setActionCode(ReservationAuditVO.DELETE_ACTION);
		reservationAuditVO.setAuditRemarks(ReservationAuditVO.ACTION_CANCEL);
		additionalInfo = additionalInfo.append(ReservationAuditVO.CANCEL_AWB);
		reservationAuditVO.setAdditionalInformation(additionalInfo.toString());
		log.log(Log.INFO,"reached before performAudit of cancel reservation...");
		AuditUtils.performAudit(reservationAuditVO);
		log.log(Log.INFO,"reached after performAudit of cancel reservation...");
	}
	
	private void auditExtendReservation(Reservation reservation, ReservationVO reservationVO) throws SystemException{
		log.log(Log.INFO,"reached before auditing extend reservation...");
		StringBuffer additionalInfo = new StringBuffer();
		ReservationAuditVO reservationAuditVO = new ReservationAuditVO(ReservationAuditVO.MODULENAME,ReservationAuditVO.SUBMODULENAME,ReservationAuditVO.ENTITYNAME);
		//reservationAuditVO = (ReservationAuditVO)AuditUtils.populateAuditDetails(reservationAuditVO,reservation,true);
		reservationAuditVO = retrieveReservationAuditVO(reservation, reservationAuditVO);
		reservationAuditVO.setShipmentPrefix(reservationVO.getShipmentPrefix());
		reservationAuditVO.setLastUpdateUser(reservationVO.getLastUpdateUser());
		//reservationAuditVO.setEntityclassName("com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationAudit");
		reservationAuditVO.setActionCode(ReservationAuditVO.UPDATE_ACTION);
		reservationAuditVO.setAuditRemarks(ReservationAuditVO.ACTION_EXTEND);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		additionalInfo = additionalInfo.append(ReservationAuditVO.EXTEND_AWB)
										.append(" - ")
										.append("Old Date:")
										.append(df.format(reservation.getExpiryDate().getTime()))
										.append(" - ")
										.append("New Date:")
										.append(df.format(reservationVO.getExpiryDate().getTime()));
										
		reservationAuditVO.setAdditionalInformation(additionalInfo.toString());
		log.log(Log.INFO,"reached before performAudit of extend reservation...");
		AuditUtils.performAudit(reservationAuditVO);
		log.log(Log.INFO,"reached after performAudit of extend reservation...");
	}

	/**
	 * @author a-1863
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> generateReservationListReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException {
		log.entering("StockController","generateReservationListReport");
		List<Object> objLst = reportSpec.getFilterValues();
		ReservationFilterVO reservationFilterVO = (ReservationFilterVO)objLst.get(0);
		ReservationFilterVO filterVOForReport = (ReservationFilterVO)objLst.get(1);
		Collection<String> sysParamList = new ArrayList<String>();
		Map<String, String> sysParMap = null;
		sysParamList.add(STOCK_DEFAULTS_DOCUMENTTYPE);
		try{
			sysParMap = new SharedDefaultsProxy().findSystemParameterByCodes(sysParamList);
		}catch(ProxyException exception){
			throw new SystemException(exception.getMessage());
		}

		String documentType = sysParMap.get(STOCK_DEFAULTS_DOCUMENTTYPE).toString();
		com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO documentFilterVO 
					= new com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO();
	    documentFilterVO.setCompanyCode(reservationFilterVO.getCompanyCode());
	    documentFilterVO.setDocumentCode(documentType);
		Collection<DocumentVO> doctype = null;
		log.log(Log.INFO,"going to call findDocumentDetails");
		try{
			doctype = new DocumentTypeProxy().findDocumentDetails(documentFilterVO);
		}catch(ProxyException exception){
			throw new SystemException(exception.getMessage());
		}
		if(reservationFilterVO.getDocumentType()!=null && 
				 reservationFilterVO.getDocumentType().trim().length()>0) {
           for(DocumentVO vo:doctype){
			   if(vo.getDocumentSubTypeDes().equals(reservationFilterVO.getDocumentType())){
				   reservationFilterVO.setDocumentType(vo.getDocumentSubType());					   
			   }
		   }
		}	
		
		log.log(Log.INFO,"going to call validateAlphaCode");
		if(reservationFilterVO.getAirlineCode()!=null && reservationFilterVO.getAirlineCode().length()>0){
			try{
				AirlineValidationVO airlineVO = new SharedAirlineProxy().
					validateAlphaCode(reservationFilterVO.getCompanyCode(),reservationFilterVO.getAirlineCode());
				if(airlineVO!=null){
					reservationFilterVO.setAirlineIdentifier(new Integer(airlineVO
							.getAirlineIdentifier()));
				}
			}catch(ProxyException exception){
				throw new SystemException(exception.getMessage());
			}	
		}
		Collection<ReservationVO> reservationVOs = findReservationListing(reservationFilterVO);
		
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[]{"airlineCode","customerCode","documentType","reservationFromDate",
			  "reservationToDate","expiryFromDate","expiryToDate"});
	    reportSpec.addParameterMetaData(parameterMetaData);
	    reportSpec.addParameter(filterVOForReport);
	    ReportMetaData reportMetaData = new ReportMetaData();
	    reportMetaData.setColumnNames(new String[]{"SHPPFX","DOCNUM","SUBTYP","CUSCOD","RSVDAT",
			  "EXPDAT","RMK"});
	    reportMetaData.setFieldNames(new String[]
	    {"shipmentPrefix","documentNumber","documentType","customerCode","reservationDate","expiryDate",			    
			    "reservationRemarks"});
	    reportSpec.setReportMetaData(reportMetaData);
	    reportSpec.setData(reservationVOs);
	    reportSpec.addExtraInfo(doctype);
	    reportSpec.setReportId(REPORT_ID);

		
		return ReportAgent.generateReport(reportSpec);
	}	
	
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> generateReserveAWBReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException {
		log.entering("ReservationController","generateReserveAWBReport");
		ReserveAWBVO reserveAWBVO = (ReserveAWBVO)reportSpec.getFilterValues().iterator().next();
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "airlineCode",
				"customerCode", "", "lastUpdateUser", "", "expiryDate",
				"documentSubType" });
		reportSpec.addParameterMetaData(parameterMetaData);
		log.log(Log.FINE, "\n\n\n----------AFTER addParameterMetaData---");

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CSHTYP", "RCPNUM" });
		reportSpec.setReportMetaData(reportMetaData);
		log.log(Log.FINE, "\n\n\n----------AFTER setReportMetaData---");
		reportSpec.addExtraInfo(reserveAWBVO);
		reportSpec.setReportId(REPORT_ID_RSVAWB);
		return ReportAgent.generateReport(reportSpec);
	}
	
}
