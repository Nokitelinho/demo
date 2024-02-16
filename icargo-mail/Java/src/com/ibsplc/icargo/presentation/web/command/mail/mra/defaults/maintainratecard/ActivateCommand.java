/*
 * ActivateCommand.java Created on Aug 6, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-2391
 * 
 */
public class ActivateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ActivateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String RATELINE_EXISTS="mailtracking.mra.defaults.ratelinesexist";
	
	private static final String DETAILS_FAILURE= "details_failure";
	
	boolean isBulkActivation=false;//Added by a-7871 for ICRD-223130
	
	private static final String BULK_ACTIVATION = "ALL";//added by a-7871 for ICRD-223130
		
	private static final String CONFRM_MSG="mailtracking.mra.defaults.maintainupuratecard.bulkactivationconfirmation";
	
	private static final String MRA ="M";//added by a-7871 for ICRD-223130
	
	private static final String REMARK ="UPU Rateline bulk activation Initiated";//added by a-7871 for ICRD-223130
	
	private static final String UPU ="UPU";//added by a-7871 for ICRD-223130
	
	private static final String I ="I";//added by a-7871 for ICRD-223130

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
		MaintainUPURateCardSession session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = null;
		Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
		//GenerateInvoiceFilterVO generateInvoiceFilterVO =new GenerateInvoiceFilterVO();
		InvoiceTransactionLogVO invoiceTransactionLogVO= new InvoiceTransactionLogVO();
		if(BULK_ACTIVATION.equals(form.getSelectedIndexes()))//Added by a-7871 for ICRD-223130
		{
			isBulkActivation=true;
			rateLineVOs.addAll(session.getRateLineDetails());
			invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType(UPU);
			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	   		invoiceTransactionLogVO.setPeriodFrom(session.getRateCardDetails().getValidityStartDate());
	   		invoiceTransactionLogVO.setPeriodTo(session.getRateCardDetails().getValidityEndDate());
	   		invoiceTransactionLogVO.setInvoiceGenerationStatus(I);
	   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
			invoiceTransactionLogVO.setRemarks(REMARK);
			invoiceTransactionLogVO.setSubSystem(MRA);
			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
			try {
				invoiceTransactionLogVO=new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				log.log(Log.INFO,e);
			}
			/*generateInvoiceFilterVO.setInvoiceLogSerialNumber(invoiceTransactionLogVO.getSerialNumber());
			generateInvoiceFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());*/
			rateLineVOs.iterator().next().setTxnLogSerialNum(invoiceTransactionLogVO.getSerialNumber());
			rateLineVOs.iterator().next().setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
		    	/*log.log(Log.SEVERE, " generateInvoiceFilterVO before-- " + generateInvoiceFilterVO);
		    	try {
					new MailTrackingMRADelegate().generateInvoiceTK(generateInvoiceFilterVO);
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			*/
		}
		else
		{	
		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		for (String index : selectedIndexes) {
			rateLineVOs.add(session.getRateLineDetails().get(
					Integer.parseInt(index)));
		}
		}
		try {
			new MailTrackingMRADelegate().activateRateLines(rateLineVOs,isBulkActivation);
		}  catch (BusinessDelegateException businessDelegateException) {
			//errors = handleDelegateException(businessDelegateException);
			errors=handleDelegateException(businessDelegateException);	
			errors = handleErrorMessage(errors);	
			//handles error data from server
			invocationContext.addAllError(errors);
			invocationContext.target = DETAILS_FAILURE;
			return;
		}
		if(isBulkActivation)// Added by a-7871 for ICRD-223130
		{
		ErrorVO error = null; 
		Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
		error = new ErrorVO(CONFRM_MSG);
        error.setErrorDisplayType(ErrorDisplayType.INFO);
        saveerrors.add(error);
		invocationContext.addAllError(saveerrors);
		}
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
		 * Creates errorvo collection to be displayed in screen
		 * 
		 * @param errors
		 * @return finErrors 		 
		 * */
		private Collection<ErrorVO> handleErrorMessage(Collection<ErrorVO> errors){
			
			log.entering(CLASS_NAME,"handleErrorMessage");
			
			Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();
			
			if(errors != null && errors.size() > 0){
				
				for(ErrorVO error:errors){
					
					RateLineErrorVO[] rateLineErrorVos = 
						new RateLineErrorVO[error.getErrorData() == null ? 0: error.getErrorData().length];
					
					if(RATELINE_EXISTS.equals(error.getErrorCode())){
						
						System.arraycopy(error.getErrorData(),0,
								rateLineErrorVos,0,error.getErrorData().length);
						
						for(RateLineErrorVO rateLineErrorVO :rateLineErrorVos){
							
							log.log(Log.INFO, "printing the errorVOS>>>>>>>>>",
									rateLineErrorVO);
							finErrors.add(new ErrorVO(RATELINE_EXISTS,
									new String[]{rateLineErrorVO.getNewRateCardID(),
									rateLineErrorVO.getCurrentRateCardID(),
									rateLineErrorVO.getOrigin(),
									rateLineErrorVO.getDestination(),
									rateLineErrorVO.getCurrentValidityStartDate().toDisplayFormat(),
									rateLineErrorVO.getCurrentValidityEndDate().toDisplayFormat()}
							));
						}
					}
				}
			}
				
			log.exiting(CLASS_NAME,"handleErrorMessage");
			return finErrors;
		}	


}
