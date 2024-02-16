/*
 * ScreenloadReturnULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ReturnULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1496
 * 
 */
public class ScreenloadReturnULDTransactionCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";

	private static final String TXNNATURE_ONETIME = "uld.defaults.TxnNature";

	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";

	private static final String ACCESSCODE_ONETIME = "uld.defaults.accessoryCode";

	private static final String TXNSTATUS_ONETIME = "uld.defaults.transactionStatus";

	private static final String PAGE_URL = "fromScmUldReconcile";
	
private static final String AIRLINE = "A";
	
	private static final String AGENT = "G";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenloadReturnULDTransactionCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String stationCode = logonAttributes.getAirportCode();
		String userId = logonAttributes.getUserId();

		ReturnULDTransactionForm returnULDTransactionForm = (ReturnULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		if("ucrprintclose".equals(returnULDTransactionForm.getPageurl())){
			log.log(Log.INFO,"inside after UCR print");
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
		}
		returnULDTransactionForm.setPageurl("");
		if ((("fromulderrorlog").equals(listULDTransactionSession.getPageURL())
						|| ("fromulderrorlogforflight").equals(listULDTransactionSession.getPageURL()) || PAGE_URL
						.equals(listULDTransactionSession.getPageURL()))) {
			listULDTransactionSession.setCompanyCode(companyCode);
			listULDTransactionSession.setStationCode(stationCode);
			listULDTransactionSession.setUserId(userId);

			HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(companyCode);

			Collection<OneTimeVO> txnTypes = oneTimeValues.get(TXNTYPE_ONETIME);
			log.log(Log.FINE, "****txnTypes  OneTime******", txnTypes);
			listULDTransactionSession.setTxnTypes(txnTypes);

			Collection<OneTimeVO> txnNatures = oneTimeValues
					.get(TXNNATURE_ONETIME);
			log.log(Log.FINE, "****txnNatures  OneTime******", txnNatures);
			listULDTransactionSession.setTxnNatures(txnNatures);

			Collection<OneTimeVO> partyTypes = oneTimeValues
					.get(PARTYTYPE_ONETIME);
			log.log(Log.FINE, "****partyTypes  OneTime******", partyTypes);
			listULDTransactionSession.setPartyTypes(partyTypes);

			Collection<OneTimeVO> accessCodes = oneTimeValues
					.get(ACCESSCODE_ONETIME);
			log.log(Log.FINE, "****accessCodes  OneTime******", accessCodes);
			listULDTransactionSession.setAccessoryCodes(accessCodes);

			Collection<OneTimeVO> txnStatus = oneTimeValues
					.get(TXNSTATUS_ONETIME);
			log.log(Log.FINE, "****txnStatus  OneTime******", txnStatus);
			listULDTransactionSession.setTxnStatus(txnStatus);
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO = listULDTransactionSession
					.getULDFlightMessageReconcileDetailsVO();
			TransactionListVO transactionListVO = listULDTransactionSession
					.getReturnTransactionListVO();
			
			String rDate = "";
			

			if (("fromulderrorlog").equals(listULDTransactionSession.getPageURL())) {
				transactionListVO
						.setReturnStationCode(uldFlightMessageReconcileDetailsVO
								.getAirportCode());
				returnULDTransactionForm.setPageurl("fromulderrorlog");
			}
			if (("fromulderrorlogforflight").equals(listULDTransactionSession.getPageURL())) {
				transactionListVO
						.setReturnStationCode(uldFlightMessageReconcileDetailsVO
								.getAirportCode());
				returnULDTransactionForm.setPageurl("fromulderrorlog");
			}

			if (listULDTransactionSession.getPageURL() != null
					&& PAGE_URL.equals(listULDTransactionSession.getPageURL())) {
				transactionListVO
						.setReturnStationCode(listULDTransactionSession
								.getULDSCMReconcileDetailsVO().getAirportCode());
				/*LocalDate returnDate = new LocalDate(transactionListVO
						.getReturnStationCode(),
						Location.ARP, true);
				log.log(Log.FINE, "Rteurn Transaction Date------------>"
						+ returnDate);
				transactionListVO.setStrRetDate(returnDate
						.toDisplayDateOnlyFormat());
				
				transactionListVO.setStrRetTime(returnDate
						.toDisplayTimeOnlyFormat().substring(0,5));*/
				
				
				returnULDTransactionForm.setPageurl(PAGE_URL); 
			}
			log.log(Log.INFO, "transactionListVO.getReturnStationCode() ---> ",
					transactionListVO.getReturnStationCode());
			LocalDate ldte = new LocalDate(transactionListVO
					.getReturnStationCode(), Location.ARP, true);
			log.log(Log.INFO, "ldte ---> ", ldte);
			transactionListVO.setReturnDate(ldte);
			if (transactionListVO.getReturnDate() != null) {
				rDate = TimeConvertor.toStringFormat(transactionListVO
						.getReturnDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
				transactionListVO.setStrRetDate(rDate);
				transactionListVO.setStrRetTime(transactionListVO.getReturnDate().toDisplayTimeOnlyFormat().substring(0,5));
			}
			listULDTransactionSession
					.setReturnTransactionListVO(transactionListVO);
			log.log(Log.INFO,
					"listULDTransactionSession.getTransactionListVO() ---> ",
					listULDTransactionSession
							.getReturnTransactionListVO());
			log.log(Log.INFO, "listULDTransactionSession.getTxnTypes() ---> ",
					listULDTransactionSession.getTxnTypes());
			listULDTransactionSession.setPageURL(null);

			

		} else {
			listULDTransactionSession
					.setULDFlightMessageReconcileDetailsVO(null);
			listULDTransactionSession.setPageURL(null);
		}
		Collection<ULDTransactionDetailsVO> uldTxnVos=listULDTransactionSession.getReturnTransactionListVO().getUldTransactionsDetails();
		log
				.log(Log.FINE, "****in screenload return uldTxnVos******",
						uldTxnVos);
		//		 Preet- This is to retain values in session for UCR printing starts
		Collection<String> uldNumbersForNavigation = new ArrayList<String>();
		Collection<Integer> txnNumbersForNavigation = new ArrayList<Integer>();
		for(ULDTransactionDetailsVO vo:uldTxnVos){
			uldNumbersForNavigation.add(vo.getUldNumber());	
			txnNumbersForNavigation.add(vo.getTransactionRefNumber());
		}
		listULDTransactionSession.setUldNumbersSelected(uldNumbersForNavigation);			
		listULDTransactionSession.setTxnNumbersSelected(txnNumbersForNavigation);
		log.log(Log.ALL, "setUldNumbersSelectedddddddddddd----",
				listULDTransactionSession.getUldNumbersSelected());
		//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts 
		String crn="";
		String crnNumber = null;
		int loancount = 0;	
		HashMap<String,Integer> crnMap = new HashMap<String,Integer>();
		log.log(Log.ALL, "setUldNumbersSelectedddddddddddd----", uldTxnVos.size());
		for (ULDTransactionDetailsVO uldTxnVo : uldTxnVos) {
			if ("L".equals(uldTxnVo.getTransactionType())) {
				crn= uldTxnVo.getControlReceiptNumber();
			}else if ("B".equals(uldTxnVo.getTransactionType())) {
				crn = crnNumber;				
			}
			AirlineValidationVO toOwnerVO = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			String toPrtyCode = uldTxnVo.getToPartyCode();
			String airlineID = null;
			
			// In case of Return Loan --> no auto generation of CRN num required 
			if ("L".equals(uldTxnVo.getTransactionType()) && (AIRLINE.equals(uldTxnVo.getPartyType()))) {
				log.log(Log.INFO,"inside laon transaction");
				/*if (crn != null && crn.length() > 0) {
					uldTxnVo.setControlReceiptNumberPrefix(crn.substring(0, 4));
					uldTxnVo.setCrnToDisplay(crn.substring(4, crn.length()));
				}*/ //else {
					if (uldTxnVo.getToPartyCode() != null) {
						if (toPrtyCode != null && !("".equals(toPrtyCode))) {
							try {
								toOwnerVO = new AirlineDelegate()
										.validateAlphaCode(logonAttributes
												.getCompanyCode(), toPrtyCode
												.toUpperCase());
							} catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
						}

					}
					
					if (toOwnerVO != null) {
						log.log(Log.INFO, "neric code", toOwnerVO.getNumericCode());
						if(!(crnMap.containsKey(toOwnerVO.getNumericCode()))){
							log.log(Log.INFO, "yesneric code", toOwnerVO.getNumericCode());
							crnMap.put(toOwnerVO.getNumericCode(),0);
							loancount=0;
						}else{
							int x=crnMap.get(toOwnerVO.getNumericCode());
							log.log(Log.INFO, "elseneric code", x);
							++x;
							if(x > 2){
								x=0;
							}
							crnMap.remove(toOwnerVO.getNumericCode());
							crnMap.put(toOwnerVO.getNumericCode(),x);
							log.log(Log.INFO, "elseneric code", x);
							loancount=x;
						}
						//airlineID = toOwnerVO.getNumericCode() + "-"+loancount;
						airlineID = new StringBuilder(toOwnerVO.getNumericCode()).append("-").append(loancount).toString();
						uldTxnVo.setControlReceiptNumberPrefix(airlineID);
						uldTxnVo.setCrnToDisplay("");
						
					}
					
						
				//}
			//In case of Return Borrow --> CRN Number is auto generated 
			} else if ("B".equals(uldTxnVo.getTransactionType())&& (AIRLINE.equals(uldTxnVo.getPartyType()))) {
				String controlRecieptNumber = null;
				if (crn == null) {
					log.log(Log.ALL, "crn number is null");
					try {
						controlRecieptNumber = new ULDDefaultsDelegate()
								.findCRNForULDTransaction(logonAttributes
										.getCompanyCode(),uldTxnVo.getToPartyCode());
						if (controlRecieptNumber != null
								&& controlRecieptNumber.length() > 0) {

							String number = controlRecieptNumber.substring(
									controlRecieptNumber.length() - 7,
									controlRecieptNumber.length());
							//airlineID = new StringBuilder(controlRecieptNumber.substring(0,4)).append("0").toString();
							airlineID = new StringBuilder(controlRecieptNumber.substring(0,5)).toString();
							log.log(Log.ALL, "airlineID is===", airlineID);
							uldTxnVo.setControlReceiptNumberPrefix(airlineID);
							uldTxnVo
									.setCrnToDisplay(new StringBuilder(number).toString());
							crnNumber = new StringBuilder(airlineID).append(
									number).toString();
							log.log(Log.INFO, "CRN number", crnNumber);
						}

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				} else {
					log.log(Log.FINE, "crn issssssssssss", crn);
					String crnNumberPart = crn.substring(4,crn.length());
					log.log(Log.INFO, "crnNumberPart", crnNumberPart);
					int count = Integer.parseInt(crnNumberPart.substring(0, 1));
					String number = crnNumberPart.substring(1, crnNumberPart
							.length());
					
					log.log(Log.FINE, "count is123", count);
					if (count < 2) {
						count++;
						airlineID = new StringBuilder(crn.substring(0,4)).toString();
					} else {
						try {
							controlRecieptNumber = new ULDDefaultsDelegate()
									.findCRNForULDTransaction(logonAttributes
											.getCompanyCode(),uldTxnVo.getToPartyCode());
							log.log(Log.INFO, "control ref no",
									controlRecieptNumber);
							if (controlRecieptNumber != null && controlRecieptNumber.length() > 0) {
								// airlineID=crn.substring(0,crn.length()-7);
								//airlineID = new StringBuilder(controlRecieptNumber.substring(0,4)).append("0").toString();
								airlineID = new StringBuilder(controlRecieptNumber.substring(0,4)).toString();
								number = controlRecieptNumber.substring(controlRecieptNumber.length() - 7, controlRecieptNumber
										.length());
								count=0;
								// String s3=new StringBuilder(airlineID).append("0").append(number).toString();
							}
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
					}					
					log.log(Log.INFO, "airline ifd", airlineID);
					log.log(Log.FINE, "number", number);
					uldTxnVo.setControlReceiptNumberPrefix(airlineID+count);
					uldTxnVo.setCrnToDisplay(number);
					crnNumber=new StringBuilder(airlineID).append(count).append(number).toString();
					log.log(Log.INFO, "CRN number", crnNumber);
				}
		    
			}
			else if (AGENT.equals(uldTxnVo.getPartyType())){
				String agentCrnNumber=uldTxnVo.getControlReceiptNumber();
				if (agentCrnNumber != null && agentCrnNumber.trim().length() > 4) {
					uldTxnVo.setControlReceiptNumberPrefix(agentCrnNumber.substring(0, 4));
					uldTxnVo.setCrnToDisplay(agentCrnNumber.substring(4, agentCrnNumber.length()));
				} 
			}
			// Added by Preet on 16th Apr for ULD 334 starts
			else{
				String othersCrnNumber=uldTxnVo.getControlReceiptNumber();
				if (othersCrnNumber != null && othersCrnNumber.trim().length() > 4) {
					uldTxnVo.setControlReceiptNumberPrefix(othersCrnNumber.substring(0, 4));
					uldTxnVo.setCrnToDisplay(othersCrnNumber.substring(4, othersCrnNumber.length()));
				} 
			}
			// Added by Preet on 16th Apr for ULD 334 ends
		}	
		listULDTransactionSession.getReturnTransactionListVO().setUldTransactionsDetails(uldTxnVos);
		log.log(Log.FINE, "updates vals", listULDTransactionSession.getReturnTransactionListVO().getUldTransactionsDetails());
		log.log(Log.FINE, "****in screenload return******",
				listULDTransactionSession.getReturnTransactionListVO());
		returnULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		// maintainULDForm.setStatusFlag("screenload");
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting("ScreenloadReturnULDTransactionCommand", "execute");

	}

	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(
			String companyCode) {
		log.entering("ScreenLoadCommand", "getOneTimeValues");

		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand", "getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(TXNTYPE_ONETIME);
		parameterTypes.add(TXNNATURE_ONETIME);
		parameterTypes.add(PARTYTYPE_ONETIME);
		parameterTypes.add(ACCESSCODE_ONETIME);
		parameterTypes.add(TXNSTATUS_ONETIME);

		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

}
