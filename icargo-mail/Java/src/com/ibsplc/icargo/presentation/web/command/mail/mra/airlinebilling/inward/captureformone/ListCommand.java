/*
 * ListCommand.java created on Jul 28, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class ListCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA airlinebilling formone");

	private static final String CLASS_NAME = "ListCommand";

	private static final String SCREEN_SUCCESS = "list_success";
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";

	private static final String NO_RESULTS = "mailtracking.mra.airlinebilling.inward.captureformone.noresultsfound";

	private static final String LIST_FAILURE = "list_failure";
	private static final String CLRPRD_MANDATORY = "mailtracking.mra.airlinebilling.inward.captureformone.clearanceprdmandatory";
	private static final String ARLCOD_MANDATORY = "mailtracking.mra.airlinebilling.inward.captureformone.airlinecodemandatory";
	private static final String ARLCOD_INVALID = "mailtracking.mra.airlinebilling.inward.captureformone.airlinecodeinvalid";
	private static final String ARLNO_INVALID = "mailtracking.mra.airlinebilling.inward.captureformone.airlinenoinvalid";
	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.inward.captureformone.clrprdinvalid";

	/**
	 * INVOICE_STATUS one time
	 */

	private static final String INVOICE_STATUS = "mailtracking.mra.despatchstatus";


	/**
	 * INVOICE_FORM1_STATUS one time
	 */
	private static final String INVOICE_FORM1_STATUS = "mra.airlinebilling.inward.captureformone.invoiceformonestatus";

	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureMailFormOneForm captureFormOneForm=(CaptureMailFormOneForm)invocationContext.screenModel;
		log.log(log.INFO,"form obtained");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CaptureFormOneSession captureFormOneSession=getScreenSession(MODULE_NAME,SCREENID);
		log.log(log.INFO,"session obtained");
		FormOneVO formOneVO=new FormOneVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(INVOICE_STATUS);
		parameterTypes.add(INVOICE_FORM1_STATUS);
		AirlineValidationVO airlineValidationVO = null;
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(
					logonAttributes.getCompanyCode(), parameterTypes);
			log.log(Log.FINE, "One Time Values%%---", oneTimeValues);

		} catch (BusinessDelegateException e) {
			handleDelegateException(e);

		}
		if (oneTimeValues != null) {
			captureFormOneSession.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		}
		if(captureFormOneSession.getFormOneVO()==null){



			if(("").equals(captureFormOneForm.getClearancePeriod()) &&(captureFormOneForm.getClearancePeriod().trim().length()==0)){
				ErrorVO err=new ErrorVO(CLRPRD_MANDATORY);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);

			}
			else{
				log.log(log.INFO,"inside ClearancePeriod");			 
				log.log(log.INFO,"inside ClearancePeriod");
				IATACalendarVO    iatacalendarvo = null;
				IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
				iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
				iatacalendarfiltervo.setClearancePeriod(captureFormOneForm.getClearancePeriod());

				try{
					
					iatacalendarvo = delegate.validateClearancePeriod(iatacalendarfiltervo);
					log.log(Log.INFO,
							"iatacalendarvo obtained from delegate call",
							iatacalendarvo);
				}
				
				catch (BusinessDelegateException e) {
					errors=handleDelegateException(e);
				}


				if(iatacalendarvo!=null ){
					log.log(Log.INFO, "iatacalendarvos not null ",
							iatacalendarvo);

				}
				else{
					log.log(log.INFO,"iatacalendarvo null");
					ErrorVO err=new ErrorVO(CLRPRD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}


				/* IATACalendarVO iatacalendarvo = null;
		    Collection<IATACalendarVO>  iatacalendarvos = new ArrayList<IATACalendarVO>();
            IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
            iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
            iatacalendarfiltervo.setClearancePeriod(captureFormOneForm.getClearancePeriod());
            ArrayList<IATACalendarVO> iATACalendarVOOneArrayList = new ArrayList<IATACalendarVO>(
            		iatacalendarvos);

            try{
			CRAAirlineBillingProxy proxy=new CRAAirlineBillingProxy();
			iatacalendarvos = proxy.validateClearancePeriod(iatacalendarfiltervo);
			iatacalendarvo = iATACalendarVOOneArrayList.get(0);
			 log.log(log.INFO,"iatacalendarvo obtained" + iATACalendarVOOneArrayList.get(0));
            }
            catch(ProxyException proxyException){
            	log.log(log.INFO,"proxyException obtained");

            }
            catch(SystemException systemException){
            	log.log(log.INFO,"SystemException obtained");
            }
            if(iatacalendarvo==null){
            	 log.log(log.INFO,"iatacalendarvo null");
            	ErrorVO err=new ErrorVO(CLRPRD_INVALID);
    			err.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(err);
            }
            else{
            	log.log(log.INFO,"iatacalendarvo!=null");
            }
		}*/
			}
			if(("").equals(captureFormOneForm.getAirlineCode()) &&(captureFormOneForm.getAirlineCode().trim().length()==0)){
				captureFormOneForm.setLinkDisable("Y");
				ErrorVO err=new ErrorVO(ARLCOD_MANDATORY);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);

			}
			else{
				AirlineDelegate airlineDelegate = new AirlineDelegate();
				
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(
							getApplicationSession().getLogonVO()
							.getCompanyCode(), captureFormOneForm.getAirlineCode()
							.toUpperCase());
				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if(airlineValidationVO!=null){
					if(captureFormOneForm.getAirlineNo()!=null){
						if(("").equals(captureFormOneForm.getAirlineNo()) &&(captureFormOneForm.getAirlineNo().trim().length()==0)){
							captureFormOneForm.setAirlineNo(airlineValidationVO.getNumericCode());
							
							
							
						}
						else{
							/*if((captureFormOneForm.getAirlineNo())!=airlineValidationVO.getNumericCode() ){
								captureFormOneForm.setLinkDisable("Y");
								ErrorVO err=new ErrorVO(ARLNO_INVALID);
								err.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(err);
							}
							else{*/
								captureFormOneForm.setAirlineNo(airlineValidationVO.getNumericCode());
								formOneVO.setAirlineNumber(airlineValidationVO.getNumericCode());
								
							//}
						}
					}
				}
				else{
					captureFormOneForm.setLinkDisable("Y");
					ErrorVO err=new ErrorVO(ARLCOD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}
			}
			log.log(log.INFO,"getAirlineCode obtained");
			if(errors!=null && errors.size()>0){
				captureFormOneSession.removeFormOneInvVOs();
				captureFormOneForm.setListFlag("N");
				// removeFormValues(captureFormOneForm);
				invocationContext.addAllError(errors);
				invocationContext.target=LIST_FAILURE;
				return;
			}


			if(captureFormOneForm.getInvoiceStatus()!=null){
				
				formOneVO.setInvStatus(captureFormOneForm.getInvoiceStatus());
			}
			formOneVO.setCompanyCode(logonAttributes.getCompanyCode());
			formOneVO.setClearancePeriod(captureFormOneForm.getClearancePeriod());
			formOneVO.setAirlineIdr(airlineValidationVO.getAirlineIdentifier());
			formOneVO.setAirlineCode(captureFormOneForm.getAirlineCode());
			if(!("").equals(captureFormOneForm.getInvoiceStatus()) && (captureFormOneForm.getInvoiceStatus().trim().length()==0)){
				formOneVO.setInvStatus(captureFormOneForm.getInvoiceStatus());
			}
			
			captureFormOneSession.setFormOneVO(formOneVO);
		}


		else{
			formOneVO=captureFormOneSession.getFormOneVO();

		}
		
		FormOneVO  formOneRetVO=null;
		log.log(Log.INFO, "formOneVO IN LISTCOMMAND ", formOneVO);
		try {
			formOneRetVO=delegate.listFormOneDetails(formOneVO);
		} catch (BusinessDelegateException e) {
			errors=handleDelegateException(e);
		}
		log.log(log.INFO,"delegate obtained");
		try{
			if(formOneRetVO!=null && formOneRetVO.getInvoiceInFormOneVOs()!=null && formOneRetVO.getInvoiceInFormOneVOs().size()>0 ){
				log.log(Log.INFO, "formOneRetVO!=null ", formOneRetVO);
				if(formOneRetVO.getBillingCurrency()!=null){
					captureFormOneForm.setLinkDisable("N");
					if(!("").equals(formOneRetVO.getBillingCurrency()) && (formOneRetVO.getBillingCurrency().trim().length()>0)){
						captureFormOneForm.setBlgCurCode(formOneRetVO.getBillingCurrency());
						Money money1 = CurrencyHelper.getMoney(formOneRetVO.getBillingCurrency());
						money1.setAmount(0.0D);
						if(formOneRetVO.getBillingTotalAmt()!=null){
							money1.plusEquals(Math.round(formOneRetVO.getBillingTotalAmt().getAmount()));		
						}
						Money money2 = CurrencyHelper.getMoney(formOneRetVO.getBillingCurrency());
						money2.setAmount(0.0D);
						if(formOneRetVO.getMissAmount()!=null){
							money2.plusEquals(Math.round(formOneRetVO.getMissAmount().getAmount()));
						}
						captureFormOneForm.setNetUsdAmountMoney(money1);
						captureFormOneForm.setNetMiscAmountMoney(money2);
					}
				}


				if(formOneRetVO.getInvoiceInFormOneVOs()!=null && formOneRetVO.getInvoiceInFormOneVOs().size()>0){
					ArrayList<InvoiceInFormOneVO>formOneInvVOss = new ArrayList<InvoiceInFormOneVO>(formOneRetVO.getInvoiceInFormOneVOs());
					int siz=formOneInvVOss.size();
					for(int i=0;i<siz;i++){
						formOneInvVOss.get(i).setLastUpdateTime(formOneRetVO.getLastUpdateTime());
					}
					
					oneTimeMap = captureFormOneSession.getOneTimeMap();
					Collection<OneTimeVO> invoiceStatus = oneTimeMap
					.get(INVOICE_STATUS);
					Collection<OneTimeVO> invform1Status = oneTimeMap
					.get(INVOICE_FORM1_STATUS);
					log.log(Log.FINE, "INVOICE_FORM1_STATUS", invform1Status);
					log.log(Log.FINE, "INVOICE_STATUS", invoiceStatus);
					if(invoiceStatus!=null&&invform1Status!=null){
						
						for (InvoiceInFormOneVO invoiceInFormOneVO : formOneInvVOss) {
							log.log(Log.FINE,
									"invoiceInFormOneVO.setInvStatus",
									invoiceInFormOneVO);
							for (OneTimeVO form1InvStatus : invform1Status) {
								//log.log(Log.FINE, "Form one" +invoiceInFormOneVO.getFormOneStatus()+form1InvStatus.getFieldValue());
									if (invoiceInFormOneVO.getFormOneStatus() != null) {					
										if (invoiceInFormOneVO.getFormOneStatus().equals(
												form1InvStatus.getFieldValue())) {
											invoiceInFormOneVO.setInvFormoneStatusdisplay(form1InvStatus
													.getFieldDescription());
											invoiceInFormOneVO.setFormOneStatus(form1InvStatus.getFieldValue());
											log
													.log(
															Log.FINE,
															"	invoiceInFormOneVO",
															invoiceInFormOneVO.getFormOneStatus());

										}
									
								}
							}
						}
						
						for (InvoiceInFormOneVO invoiceInFormOneVo : formOneInvVOss) {
							log.log(Log.FINE, "	invoice status--- ",
									invoiceInFormOneVo);
								if(invoiceStatus!=null){
									
									
									
							for (OneTimeVO invStatus : invoiceStatus) {
								//log.log(Log.FINE, "	-Invoice-" + invoiceInFormOneVo.getInvStatus()+invStatus.getFieldValue());
									if (invoiceInFormOneVo.getInvStatus() != null) {					
										if (invoiceInFormOneVo.getInvStatus().equals(
												invStatus.getFieldValue())) {
											invoiceInFormOneVo.setInvStatusdisplay(invStatus
													.getFieldDescription());
											invoiceInFormOneVo.setInvStatus(invStatus.getFieldValue());
											
log.log(Log.FINE, "--",
													invoiceInFormOneVo.getInvStatus());

										}
									
								}
							}
						}
						
								/**
								 * @author a-3447 for bug 37300 for disabling Screen Modification during Processed or Exceptional invoices
								 */
								
								if (invoiceInFormOneVo.getInvStatus() != null) {
								if("P".equals(invoiceInFormOneVo.getInvStatus())||"E".equals(invoiceInFormOneVo.getInvStatus())){									
									log.log(Log.FINE, "inside processed Invoices");
									captureFormOneForm.setProcessedFlag("Y");										
								}
								else {
									
									captureFormOneForm.setProcessedFlag("N");	
								}
								}
						
						
					}
						
						
					}						
					
					captureFormOneSession.setFormOneInvVOs(formOneInvVOss);	
					log.log(Log.FINE, "captureFormOneSession",
							captureFormOneSession.getFormOneInvVOs());
					formOneVO=captureFormOneSession.getFormOneVO();
					log.log(Log.FINE, "captureFormOneSession---", formOneVO);
					if(formOneVO!=null){
						captureFormOneForm.setClearancePeriod(formOneVO.getClearancePeriod());
						captureFormOneForm.setAirlineCode(formOneVO.getAirlineCode());
						if(String.valueOf(formOneVO.getAirlineIdr())!=null){
							captureFormOneForm.setAirlineNo(formOneVO.getAirlineNumber());
						}}
					captureFormOneForm.setListFlag("Y");
					invocationContext.target=SCREEN_SUCCESS;
				}

			}
			else{

				captureFormOneForm.setLinkDisable("N");
				captureFormOneForm.setListFlag("N");
				//removeFormValues(captureFormOneForm);
				captureFormOneSession.removeFormOneInvVOs();
				Money money = CurrencyHelper.getMoney("USD");
				money.setAmount(0.0D);
				captureFormOneForm.setNetUsdAmountMoney(money);
				captureFormOneForm.setNetMiscAmountMoney(money);
				ErrorVO err=new ErrorVO(NO_RESULTS);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				invocationContext.addAllError(errors);
				invocationContext.target=LIST_FAILURE;

			}	
		}
		catch(CurrencyException e){
			e.getErrorCode();
		}

		log.exiting(CLASS_NAME,"execute");
	}
	/*private void removeFormValues(CaptureFormOneForm captureFormOneForm){

		captureFormOneForm.setInvoiceStatus("");

	}*/

}
