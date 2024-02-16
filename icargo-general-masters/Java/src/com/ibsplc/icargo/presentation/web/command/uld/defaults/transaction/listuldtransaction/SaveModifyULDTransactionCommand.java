/*
 * SaveModifyULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *@author 
 */
public class SaveModifyULDTransactionCommand  extends BaseCommand {

	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAMET = "uld.defaults";
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_IDT = "uld.defaults.loanborrowuld";
	
	private static final String PTYTYP_AGT ="G";
	
	private static final String PTYTYP_ARL ="A";
	
	private static final String TO_BE_INVOICED ="R";
	
	private static final String TO_BE_RETURNED ="T";
	private static final String ULD_COUNT_PER_UCR = "shared.airline.uldCountPerUCR";

	/*
	 * target String if success
	 */
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String INVALID_LEASE_END_DATE = "uld.defaults.loanBorrowDetailsEnquiry.invalidleaseenddate";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenloadModifyULDTransactionCommand","execute");

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    			ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAMET, SCREEN_IDT);
       Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		ULDDefaultsDelegate uldDefaultsDelegate =
			   new ULDDefaultsDelegate();
//Added by nisha for pagination...		
int displayPage = Integer.parseInt(listULDTransactionForm.getModDisplayPage());
		log.log(Log.INFO, "Display Page in nanigate", listULDTransactionForm.getModDisplayPage());
		int currentPage = Integer.parseInt(listULDTransactionForm.getModCurrentPage());
    	log.log(Log.INFO, "Display Page in getModCurrentPage",
				listULDTransactionForm.getModCurrentPage());
		//ULDTransactionDetailsVO uLDTxnDetailsVO = listULDTransactionSession.getULDTransactionDetailsVO();
		TransactionListVO transactionListVO = listULDTransactionSession
				.getTransactionListVO();
		ArrayList<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>(
				loanBorrowULDSession.getTransactionVO().getUldTransactionDetailsVOs());
		ULDTransactionDetailsVO uLDTxnDetailsVO = uldTxnDetailsVOs.get(currentPage-1);
		log.log(Log.INFO, "uLDTxnDetailsVO ==========", uLDTxnDetailsVO);
		if(!("").equalsIgnoreCase(listULDTransactionForm.getModDuration()) && listULDTransactionForm.getModULDNo() != null){
			uLDTxnDetailsVO.setTransationPeriod(listULDTransactionForm.getModDuration());
	   }else {
		    uLDTxnDetailsVO.setTransationPeriod("0");
	   }
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				&& listULDTransactionForm.getTxnStation() != null) {
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getTxnStation()) == null) {
				uLDTxnDetailsVO.setTransactionStationCode(listULDTransactionForm
						.getTxnStation().toUpperCase());
			} else {
				uLDTxnDetailsVO.setTransactionStationCode(listULDTransactionForm
						.getTxnStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.txnstation.invalid");
				errorVO.setErrorDisplayType(ERROR);
				invocationContext.target = SAVE_FAILURE;				
				invocationContext.addError(errorVO);
				return;
			}
			
			
		}
		LocalDate ldte = new LocalDate(uLDTxnDetailsVO.getTransactionStationCode(),Location.ARP,true);
		
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getModTxnDate()) && listULDTransactionForm.getModTxnDate() != null){
    		// Added by Preet for bug ULD 71 starts 
    		if(!("").equalsIgnoreCase(listULDTransactionForm.getModTxnTime()) && listULDTransactionForm.getModTxnTime() != null){
    			StringBuilder txndat = new StringBuilder();    			
    			txndat.append(listULDTransactionForm.getModTxnDate()).append(" ").append(listULDTransactionForm.getModTxnTime()).append(":00");    			
    			uLDTxnDetailsVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
    		}else{
    			uLDTxnDetailsVO.setTransactionDate(ldte.setDate(listULDTransactionForm.getModTxnDate()));
    		}
    		//Added by Preet for bug ULD 71 ends
    		//uLDTxnDetailsVO.setTransactionDate(ldte.setDate(listULDTransactionForm.getModTxnDate()));
    		uLDTxnDetailsVO.setStrTxnDate(listULDTransactionForm.getModTxnDate());
    		uLDTxnDetailsVO.setStrTxnTime(listULDTransactionForm.getModTxnTime());
	    }else {
	    	uLDTxnDetailsVO.setStrTxnDate("");
	    }
		
	  if(!("").equalsIgnoreCase(listULDTransactionForm.getModTxnRemarks()) && listULDTransactionForm.getModTxnRemarks() != null){
		uLDTxnDetailsVO.setTransactionRemark(listULDTransactionForm.getModTxnRemarks());
	  }
	  
	  /* Added by a-2412 on 6 th Nov for newly added fields CRN and Condition code*/

	  if (listULDTransactionForm.getModCRN() == null
				|| listULDTransactionForm.getModCRN().length() == 0) {

			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.modify.crn.mandatory");
			errorVO.setErrorDisplayType(ERROR);
			invocationContext.target = SAVE_FAILURE;
			listULDTransactionSession.setCtrlRcptNoPrefix(listULDTransactionForm.getModCrnPrefix());
			listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm.getModCRN());
			invocationContext.addError(errorVO);
			return;
		}


	  if(!("").equalsIgnoreCase(listULDTransactionForm.getModUldCondition()) && listULDTransactionForm.getModUldCondition() != null){
			uLDTxnDetailsVO.setUldConditionCode((listULDTransactionForm.getModUldCondition()));
	  }
	  if(listULDTransactionForm.getModAwbNumber()!=null && listULDTransactionForm.getModAwbNumber().trim().length()>0){
		  log.log(Log.INFO, "listULDTransactionForm.getModAwbNumber()-->",
				listULDTransactionForm.getModAwbNumber());
		uLDTxnDetailsVO.setAwbNumber(listULDTransactionForm.getModAwbNumber().trim());
	  }
	  if("Y".equals(listULDTransactionForm.getModLoaded())){
		  uLDTxnDetailsVO.setEmptyStatus("N");
	  }else{
		  uLDTxnDetailsVO.setEmptyStatus("Y");
	  }
		// added by a-3278 for CR QF1015 on 08Jul08 starts
		
		if (!("").equalsIgnoreCase(listULDTransactionForm.getDesStation())
				&& listULDTransactionForm.getDesStation() != null) {
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getDesStation()) == null) {
				uLDTxnDetailsVO.setTxStationCode(listULDTransactionForm
						.getDesStation().toUpperCase());
			} else {
				uLDTxnDetailsVO.setTxStationCode(listULDTransactionForm
						.getDesStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.desstation.invalid");
				errorVO.setErrorDisplayType(ERROR);
				invocationContext.target = SAVE_FAILURE;				
				invocationContext.addError(errorVO);
				return;
			}			
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {			
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getReturnStation()) == null) {
				uLDTxnDetailsVO.setReturnStationCode(listULDTransactionForm
						.getReturnStation().toUpperCase());
			} else {
				uLDTxnDetailsVO.setReturnStationCode(listULDTransactionForm
						.getReturnStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.rtnstation.invalid");
				errorVO.setErrorDisplayType(ERROR);
				invocationContext.target = SAVE_FAILURE;				
				invocationContext.addError(errorVO);
				return;
			}

		} 
		LocalDate ldate = null;
		if(uLDTxnDetailsVO.getReturnStationCode() != null && uLDTxnDetailsVO.getReturnStationCode().length() >0){
		 ldate = new LocalDate(uLDTxnDetailsVO.getReturnStationCode(),
				Location.ARP, true);
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getModRtnDate())
				&& listULDTransactionForm.getModRtnDate() != null) {
			if (!("").equalsIgnoreCase(listULDTransactionForm.getModRtnTime())
					&& listULDTransactionForm.getModRtnTime() != null) {
				StringBuilder retdat = new StringBuilder();
				retdat.append(listULDTransactionForm.getModRtnDate()).append(
						" ").append(listULDTransactionForm.getModRtnTime())
						.append(":00");
				uLDTxnDetailsVO.setReturnDate(ldate.setDateAndTime(retdat
						.toString()));
			} else {
				uLDTxnDetailsVO.setReturnDate(ldate
						.setDate(listULDTransactionForm.getModRtnDate()));
			}
			uLDTxnDetailsVO.setStrRetDate(listULDTransactionForm
					.getModRtnDate());
			uLDTxnDetailsVO.setStrRetTime(listULDTransactionForm
					.getModRtnTime());
		} else {
			uLDTxnDetailsVO.setStrRetDate("");
			uLDTxnDetailsVO.setReturnDate(null);
		}
		
		if (!("").equalsIgnoreCase(listULDTransactionForm.getRtnRemarks())
				&& listULDTransactionForm.getRtnRemarks() != null) {
			uLDTxnDetailsVO.setReturnRemark(listULDTransactionForm
					.getRtnRemarks());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getRtndemurrage())
				&& listULDTransactionForm.getRtndemurrage() != null) {
			uLDTxnDetailsVO.setDemurrageAmount(Double
					.parseDouble(listULDTransactionForm.getRtndemurrage()));
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getRtntaxes())
				&& listULDTransactionForm.getRtntaxes() != null) {
			uLDTxnDetailsVO.setTaxes(Double.parseDouble(listULDTransactionForm
					.getRtntaxes()));
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getRtnwaived())
				&& listULDTransactionForm.getRtnwaived() != null) {
			uLDTxnDetailsVO.setWaived(Double.parseDouble(listULDTransactionForm
					.getRtnwaived()));
		}
		LocalDate lseEndDate = null;
		if(Objects.nonNull(listULDTransactionForm.getModLseEndDate())){
			lseEndDate = (!listULDTransactionForm.getModLseEndDate().isEmpty()) 
					? (new LocalDate(uLDTxnDetailsVO.getTxStationCode(), Location.ARP,true).setDate(listULDTransactionForm.getModLseEndDate())) : null;
		}
		boolean isInvalidLeaseDate = false; 
		if(Objects.nonNull(lseEndDate) &&
				! lseEndDate.isGreaterThan(new LocalDate(uLDTxnDetailsVO.getTxStationCode(), Location.ARP,false))){
			isInvalidLeaseDate = true;
			ErrorVO errorVO = new ErrorVO(INVALID_LEASE_END_DATE, new Object[]{uLDTxnDetailsVO.getUldNumber().trim().toUpperCase()});
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		if(Objects.nonNull(lseEndDate) && !isInvalidLeaseDate){
			uLDTxnDetailsVO.setLeaseEndDate(new LocalDate(uLDTxnDetailsVO.getTxStationCode(), Location.ARP,true).setDate(listULDTransactionForm.getModLseEndDate()));
			uLDTxnDetailsVO.setStrLseEndDate(listULDTransactionForm.getModLseEndDate());
	   }else{
		    uLDTxnDetailsVO.setLeaseEndDate(null);
			uLDTxnDetailsVO.setStrLseEndDate("");
	   }
		
		//added by a-3278 for bug 53469 on 10Jul09
		boolean isCrnChanged=false;
		if(!("").equalsIgnoreCase(listULDTransactionForm.getModCRN()) && listULDTransactionForm.getModCRN() != null){
			  String crnPrefix=listULDTransactionForm.getModCrnPrefix();
			  String crn=listULDTransactionForm.getModCRN();
			  // Modified for the ICRD-16045 Author: A-5125 on jul25th2012
			  String crnToSet=new StringBuffer(crnPrefix).append("-").append(crn).toString();
			  if(crn.equals(listULDTransactionSession.getCtrlRcptNo())){
				  isCrnChanged=false;
			  }else{
				  isCrnChanged=true;

					/* * if-else chk by a-3278 for 28897 on 06Jan09
					 * a new CRN is maintained to save the latest and the old CRN seperately
					 * in case of return the crn is updated in the returnCRN field*/

				  if (TO_BE_RETURNED.equals(uLDTxnDetailsVO
							.getTransactionStatus())
							&& uLDTxnDetailsVO.getReturnDate() == null) {
						uLDTxnDetailsVO.setControlReceiptNumber(crnToSet);
					} else {
						uLDTxnDetailsVO.setReturnCRN(crnToSet);
					}
			  }

		  }
		// bug 53469 on 10Jul09 ends
		// added by a-3278 for CR QF1015 on 07Jul08 ends

		log.log(Log.FINE, "uLDTxnDetailsVO***final", uLDTxnDetailsVO);
		log.log(Log.INFO, "uldTxnDetailsVOs 4m  loanBorrowULDSession =>>>>>>=",
				uldTxnDetailsVOs);
		Collection<ULDTransactionDetailsVO> uldTxnDtlsVOs = new ArrayList<ULDTransactionDetailsVO>();

		int errorFlag = 0;
		LocalDate currentdate = new LocalDate(uLDTxnDetailsVO.getTransactionStationCode(),Location.ARP,true);
		
		
					if(uLDTxnDetailsVO.getTransactionDate().isGreaterThan(currentdate)) {
						errorFlag = 1;
					}
					if(uLDTxnDetailsVO.getReturnDate()!= null){
					if(uLDTxnDetailsVO.getTransactionDate().isGreaterThan(uLDTxnDetailsVO.getReturnDate())) {
						errorFlag = 2;
					}
					}					
	
		
		if (TO_BE_INVOICED.equals(uLDTxnDetailsVO.getTransactionStatus())
				&& uLDTxnDetailsVO.getReturnDate() == null) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.modify.rtndate.mandatory");
			errorVO.setErrorDisplayType(ERROR);
			invocationContext.target = SAVE_FAILURE;
			uLDTxnDetailsVO.setStrRetDate(listULDTransactionForm
					.getModRtnDate());
			invocationContext.addError(errorVO);
			return;
		}
		//Commented as part of ICRD-3027 by A-3767 on 19Jul11
	/*	if(errorFlag == 1) {
			ErrorVO error = new ErrorVO(
			"uld.defaults.loanborrowmodify.txndategreaterthancurrentdate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			listULDTransactionForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	        invocationContext.target =SAVE_SUCCESS;
	        return;
		}	*/	
		if (errorFlag == 2) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanborrowreturn.txndategreaterthanreturndate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			listULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SAVE_SUCCESS;
			return;
		}

		
		 /*
		 * Construct lock vos for implicit locking
		 */
		 
		
			// added by a-3278 for QF1015 on 19Jul08
			boolean returnFlag = false;
			log.log(Log.INFO, "listULDTransactionForm.getModTxnType()",
					uLDTxnDetailsVO.getTransactionType());
			//uLDTxnDetailsVO.setTransactionType(listULDTransactionForm.getModTxnType());
			if (TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus() ) 
					&& uLDTxnDetailsVO.getReturnDate() == null
					|| TO_BE_INVOICED.equals(uLDTxnDetailsVO.getTransactionStatus())
					&& uLDTxnDetailsVO.getReturnDate() != null) {
				if(TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus() ) ){
					
						uLDTxnDetailsVO.setDemurrageAmount(0.0);
						uLDTxnDetailsVO.setDemurrageRate(0.0);
						uLDTxnDetailsVO.setWaived(0.0);
						uLDTxnDetailsVO.setTaxes(0.0);
						uLDTxnDetailsVO.setTotal(0.0);
					
				}else{
					
					uLDTxnDetailsVO.setTotal((uLDTxnDetailsVO.getDemurrageAmount()
									+ uLDTxnDetailsVO.getTaxes() + uLDTxnDetailsVO.getOtherCharges())
									- uLDTxnDetailsVO.getWaived());
					
				}
				returnFlag = false;
				uLDTxnDetailsVO.setReturn(returnFlag);
				
			
				
			}
			if (TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus())
					&& uLDTxnDetailsVO.getReturnDate() != null) {
					uLDTxnDetailsVO
							.setTransactionStatus(ULDTransactionDetailsVO.TO_BE_INVOICED);
					uLDTxnDetailsVO
							.setTotal((uLDTxnDetailsVO.getDemurrageAmount()
									+ uLDTxnDetailsVO.getTaxes() + uLDTxnDetailsVO.getOtherCharges())
									- uLDTxnDetailsVO.getWaived());
					uLDTxnDetailsVO.setReturnedBy(uLDTxnDetailsVO.getToPartyCode());
					uLDTxnDetailsVO.setCurrOwnerCode(uLDTxnDetailsVO.getFromPartyIdentifier());
					//uldTxnDtlsVOs.add(txnVO);
				
				returnFlag = true;
				uLDTxnDetailsVO.setReturn(returnFlag);
				
				
			}
			// added for QF1015 on 19Jul08 ends
			TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		boolean returnReq = false;
		if(transactionVO!=null && transactionVO.getUldTransactionDetailsVOs()!=null){
			transactionVO.setOperationalFlag("U");
			transactionListVO.setUldTransactionsDetails(new ArrayList<ULDTransactionDetailsVO>());
			transactionListVO.setAccessoryTransactions(null);
	ArrayList<ULDTransactionDetailsVO> txnsRemoved = new ArrayList<ULDTransactionDetailsVO>();
			for(ULDTransactionDetailsVO vo:transactionVO.getUldTransactionDetailsVOs()){
				if(vo.isReturn()){
					transactionListVO.setReturnStationCode(vo.getReturnStationCode());
					transactionListVO.setReturnDate(vo.getReturnDate());
					transactionListVO.setStrRetDate(vo.getStrRetDate());
					transactionListVO.setStrRetTime(vo.getStrRetTime());
					transactionListVO.setTransactionType(vo.getTransactionType());
					transactionListVO.getUldTransactionsDetails().add(vo);
					txnsRemoved.add(vo);
					returnReq = true;
				}
			}
			 log.log(Log.INFO, "Final Transaction VO for save", transactionVO);
			TransactionVO transactionVOCRN = new TransactionVO();
				try {
					BeanHelper.copyProperties(transactionVOCRN, transactionVO) ;
				} catch (SystemException e) {
					log.log(Log.INFO,"Bean Excepton");
				}
				ArrayList<ULDTransactionDetailsVO> crntxns = new ArrayList<ULDTransactionDetailsVO>();
				crntxns.add(uLDTxnDetailsVO);
				transactionVOCRN.setUldTransactionDetailsVOs(crntxns);
			if(isCrnChanged){
				 Collection<String> crnNums=null;
					try {
						String companyCode=logonAttributes.getCompanyCode();
						crnNums = uldDefaultsDelegate.checkForDuplicateCRN(companyCode,transactionVOCRN);
						log.log(Log.FINE, "crnNums returned------------",
								crnNums);
					} catch (BusinessDelegateException ex) {
						errors=handleDelegateException(ex);
						ex.getMessage();
					}
					
					if (crnNums != null && crnNums.size() > 0 ) {
						StringBuffer duplicates = new StringBuffer("");
						for (String duplicate : crnNums) {
							if (("").equals(duplicates.toString())) {
								duplicates.append(duplicate);
							} else {
								duplicates.append(" , ");
								duplicates.append(duplicate);
							}
						}
						ErrorVO error = new ErrorVO(
								"uld.defaults.loanBorrowULD.msg.err.duplicateCRNexists",
								new Object[] { duplicates.toString() });
						errors.add(error);
						invocationContext.addAllError(errors);
						listULDTransactionSession.setCtrlRcptNoPrefix(listULDTransactionForm.getModCrnPrefix());
						listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm.getModCRN());					
						invocationContext.target = SAVE_FAILURE;
						return;
					}				
			 }
			 boolean prefixFlag=false;
			 for (ULDTransactionDetailsVO transactionDetailsVO : transactionVOCRN.getUldTransactionDetailsVOs()) {
				
				 // Added for bug 104125 bt A-2521. Implemented as LoanBorrow Entry screen
				 prefixFlag = validateCRNFormat (transactionDetailsVO);
				 //Added by A-2052 for the bug 103585 starts
				 if(prefixFlag){
					 ErrorVO error = new ErrorVO(
					 	"uld.defaults.loanBorrowULD.msg.err.incorrectCRNprefix");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
						listULDTransactionSession.setCtrlRcptNoPrefix(listULDTransactionForm.getModCrnPrefix());
						listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm.getModCRN());
						invocationContext.target = SAVE_FAILURE;						
						return;
				 }
			}
			 //Added by A-2052 for the bug 103585 ends
			/* Addition by A-2412 on 6 th Nov for duplicate CRN chq ends*/
			if(txnsRemoved !=null && txnsRemoved.size()>0){
				transactionVO.getUldTransactionDetailsVOs().removeAll(txnsRemoved);
			}
		}
		
		Collection<LockVO> locks = prepareLocksForSave(transactionVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
			try {
				log.log(Log.FINE, "transactionVO B4 SAVE=========>>",
						transactionVO);
				log.log(Log.FINE, "transactionListVO B4 SAVE=========>>",
						transactionListVO);
				// added for QF1015 on 19Jul08
				if(returnReq){
				uldDefaultsDelegate.saveReturnTransaction(transactionListVO,
						locks);
				}			
				errors = uldDefaultsDelegate.saveULDTransaction(transactionVO,
						locks);
		
			// added for QF1015 on 19Jul08 ends
			     }catch (BusinessDelegateException businessDelegateException) {
				  businessDelegateException.getMessage();
		          errors = handleDelegateException(businessDelegateException);
			     }
			     if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						loanBorrowULDSession.setTransactionVO(transactionVO);
				}else {
						listULDTransactionForm.setCloseModifyFlag(FLAG_YES);
						loanBorrowULDSession.setTransactionVO(null);
						listULDTransactionSession.setTransactionListVO(null);
				}

		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target =SAVE_SUCCESS;

    }
	/*
	 * Added by Ayswarya
	 */
	private Collection<LockVO> prepareLocksForSave(
			TransactionVO transactionVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", transactionVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
			for (ULDTransactionDetailsVO uldVO : uldDetailsVOs) {
				ULDLockVO lock = new ULDLockVO();
				lock.setAction(LockConstants.ACTION_LOANBORROWULD);
				lock.setClientType(ClientType.WEB);
				lock.setCompanyCode(logonAttributes.getCompanyCode());
				lock.setScreenId(SCREEN_ID);
				lock.setStationCode(logonAttributes.getStationCode());
				lock.setUldNumber(uldVO.getUldNumber());
				lock.setRemarks(uldVO.getUldNumber());
				lock.setDescription(uldVO.getUldNumber());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}
		return locks;
	}
	//added by a-3278 for QF1015 on 22Jul08
	private Collection<ErrorVO> validateAirportCode(ListULDTransactionForm form,
			String companyCode, String airport) {
		log.log(Log.INFO, "Airport inside airport validation", airport);
		Collection<ErrorVO> errors = null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;

		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), airport.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			log.log(Log.INFO, "Errors", errors);
		}
		return errors;

	}
	//a-3278 ends
	
	/**
	 * Validates the CRN number pefix. 
	 * for agent it can be 0 - 9 and for airline it can as configured 
	 * in system parameter .
	 * @param ULDTransactionDetailsVO
	 * @return boolean
	 */
	private boolean validateCRNFormat(
			ULDTransactionDetailsVO transactionDetailsVO) {

		log.entering("validateCRNFormat", "validateCRNFormat");
		boolean prefixFlag = false;
		int countPerUCR = 0;
		String fromPrtyCode = transactionDetailsVO.getFromPartyCode();
		String crnNum = transactionDetailsVO.getControlReceiptNumber();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Commented as part of ICRD-3027 by A-3767 on 19Jul11
		/*String uldCountPerUCR = "shared.airline.uldCountPerUCR";
		
		if (crnNum == null)
			return prefixFlag;

		if (PTYTYP_AGT.equals(transactionDetailsVO.getPartyType())) {
			countPerUCR = 9;

		} else {
			
			try {
				AirlineDelegate airlineDelegate = new AirlineDelegate();
				AirlineValidationVO airlineValidationVO = null;
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						transactionDetailsVO.getCompanyCode(), fromPrtyCode);
								
				Collection<String> parameterCodes = new ArrayList<String>();
				Map<String, String> airlineParameterMap = null;
				parameterCodes.add(uldCountPerUCR);
				airlineParameterMap = airlineDelegate
						.findAirlineParametersByCode(
								transactionDetailsVO.getCompanyCode(), 
								airlineValidationVO.getAirlineIdentifier(), parameterCodes);
				
				if (airlineParameterMap != null
						&& !airlineParameterMap.isEmpty()) {

					String value = airlineParameterMap.get(uldCountPerUCR);
					if (value != null && value.trim().length() > 0) {
						countPerUCR = Integer.parseInt(value) - 1;
					}
				}
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		//}
			//if(countPerUCR<=0)*/
		//Added for ICRD-3027 by A-3767 on 19jul11
		countPerUCR=getUldCountperUCR(logonAttributes.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier());	
		log.log(Log.INFO, "crnNum ----------->>>", crnNum);
		String numPart = crnNum.substring(4, 5);
		log.log(Log.INFO, "NUMBER PART IS ----------->>", numPart);
		try {
			int num = Integer.parseInt(String.valueOf(numPart));
			if (num > countPerUCR) {
				prefixFlag = true;
			}
		} catch (NumberFormatException e) {
			prefixFlag = true;
			log.log(Log.INFO, "NumberFormatexception caught");
		}

		log.exiting("validateCRNFormat", "validateCRNFormat");
		return prefixFlag;
	}
	
	/**
	 * Added by A-4072 as part of CR ICRD-192300 for getting number of ULDs to to print in UCR report.
	 * and this count is used for generating crn number.
	 * @param cmpCode
	 * @param ownAirlineIdr
	 * @return
	 */
	private int getUldCountperUCR(String cmpCode,int ownAirlineIdr){
		int uldCountPerUCR =3;//As per IATA default is 3
		try{
			Collection<String> parameterCodes = new ArrayList<String>();
			Map<String,String> airlineParameterMap = null;
			parameterCodes.add(ULD_COUNT_PER_UCR);
			airlineParameterMap = new AirlineDelegate().findAirlineParametersByCode(cmpCode, ownAirlineIdr, parameterCodes);
			if(airlineParameterMap != null && !airlineParameterMap.isEmpty()){
				String value = airlineParameterMap.get(ULD_COUNT_PER_UCR);
				if(value != null && value.trim().length() > 0){
					uldCountPerUCR = Integer.parseInt(value);
				}
			}
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE, "*****in the exception in findAirlineParameter"+businessDelegateException.getMessage());
		}		
		return uldCountPerUCR;
	}

}
