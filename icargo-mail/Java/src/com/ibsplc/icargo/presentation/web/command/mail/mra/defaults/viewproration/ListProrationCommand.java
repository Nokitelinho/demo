/*
 * ListProrationCommand.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2554
 *
 */
public class ListProrationCommand extends BaseCommand{


	private  Log log = LogFactory.getLogger("MRA DEFAULTS VIEW PRORATION");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String BLANK = "";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";

	private static final String LIST_SUCCESS = "list_success";
	private static final String VALIDATION_FAILURE = "validation_failure";
	private static final String NO_DATA_FOUND = "no_data_found";
	private static final String DSNPOPUP_SCREENID ="mailtracking.mra.defaults.dsnselectpopup";
	private static final String FROM_RATEAUDITDETAILS ="fromRateAuditDetails";
	private static final String SCREENPRO_ID = "mailtracking.mra.defaults.prorationlog";

	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String FROM_MANUALPRORATION ="ManualProration";
	private static final String FROM_INTERLINEBILLING ="interlinebilling";
	private static final String FROM_DESPATCHENQUIRY ="fromDespatchEnquiry";
	private static final String FROM_CRAPOSTINGDETAILS="fromCraPostingDetails";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String PAYFLAG_ONETIME = "mailtracking.mra.defaults.payflag";
	private static final String SECTORSTATUS_ONETIME = "mailtracking.mra.proration.sectorstatus";
	private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit";
	
	
	
	
	//Flags
	private static final String AIRLINE="A";
	private static final String GPA="G";
	private static final String RETENTION="T";
	private static final String PAYABLE="P";
	private static final String RECEIVABLE="R";
	private static final String ENABLE="E";
	private static final String VOID_MAILBGS = "mailtracking.mra.defaults.manualproration.msg.err.voidmailbags";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";//Added by A-6991 for ICRD-208114
	private static final String MODULE = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
    //A-8331
	private static final String LIST_FAILURE ="list_failure" ;
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");


		MRAViewProrationForm form=(MRAViewProrationForm)invocationContext.screenModel;

		 DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE_NAME,DSNPOPUP_SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();

		Collection<ProrationDetailsVO> prorationDetailsVO =new ArrayList<ProrationDetailsVO>();
		MRAViewProrationSession session = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);
		//Added by A-7540 for ICRD-236156
		ListInterlineBillingEntriesSession listInterlineBillingEntriesSession = (ListInterlineBillingEntriesSession) getScreenSession(
 				MODULE, SCREEN_ID);
		ProrationLogSession prorationLogSession=(ProrationLogSession)getScreenSession(MODULE_NAME, SCREENPRO_ID);

		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		ProrationFilterVO	prorationFilterVO = new ProrationFilterVO();
		// MONEY IMPL

		boolean contractCurrenySame=true;
		String previousCurCode=null;
		String currentCurCode=null;
		Map<String, String> results =  new HashMap<String, String>();
		Collection<String> codes = new ArrayList<String>();
      	codes.add(BASE_CURRENCY);
		try {
      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
      	} catch(BusinessDelegateException businessDelegateException) {
      		handleDelegateException(businessDelegateException);
      	}
		String baseCurrency = results.get(BASE_CURRENCY);
		String contractCurrency = "";//Added by A-5219 for ICRD-258240 
		prorationFilterVO.setBaseCurrency(baseCurrency);
			if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){

				invocationContext.addAllError(errors);
				invocationContext.target = VALIDATION_FAILURE;
				return;
			}




		log.log(Log.INFO, "FromScreen-------------->", form.getFromScreen());
		if(form.getFromScreen()!=null &&
				(FROM_RATEAUDITDETAILS.equals(form.getFromScreen()) ||
				FROM_MANUALPRORATION.equals(form.getFromScreen())||
				FROM_INTERLINEBILLING.equals(form.getFromScreen()) ||
				//FROM_CRAPOSTINGDETAILS.equals(form.getFromScreen()) ||
				FROM_DESPATCHENQUIRY.equals(form.getFromScreen()))){

			 prorationFilterVO=session.getProrationFilterVO();
			 
			 log.log(Log.INFO, "prorationFilterVO-------------->",
					prorationFilterVO);
			form.setDispatch(prorationFilterVO.getBillingBasis());
			  form.setConDocNo(prorationFilterVO.getConsigneeDocumentNumber());
			  prorationFilterVO.setBaseCurrency(baseCurrency);
			if(prorationFilterVO.getFlightDate()!=null){
				form.setFlightDate(prorationFilterVO.getFlightDate().toDisplayDateOnlyFormat());
				}

			if(prorationFilterVO.getCarrierCode()!=null){
				form.setCarrierCode(prorationFilterVO.getCarrierCode());
				}
			else{
				form.setCarrierCode(BLANK);
			  }

			if(prorationFilterVO.getFlightNumber()!=null){
				form.setFlightNo(prorationFilterVO.getFlightNumber());
				}
			else{
				form.setFlightNo(BLANK);
			  }
		}else if("PRORATIONLOG".equalsIgnoreCase(form.getFromScreen())){
			//Screen invoked from ProrationLog

			MailProrationLogVO mailProrationLogVO=prorationLogSession.getSelectedDespatchDetails();
			Collection<ProrationDetailsVO> prorationDetailVOs =new ArrayList<ProrationDetailsVO>();
			String dsn=form.getDsn();
			prorationFilterVO = new ProrationFilterVO();
			//to set filter vo
			if(mailProrationLogVO!=null){

				prorationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				prorationFilterVO.setBillingBasis(mailProrationLogVO.getBillingBasis());
				prorationFilterVO.setConsigneeDocumentNumber(mailProrationLogVO.getCsgDocumentNumber());
				prorationFilterVO.setConsigneeSequenceNumber(mailProrationLogVO.getCsgSequenceNumber());
				prorationFilterVO.setPoaCode(mailProrationLogVO.getPoaCode());
				prorationFilterVO.setSerialNumber(mailProrationLogVO.getSerialNumber());
				prorationFilterVO.setSequenceNumber(mailProrationLogVO.getVersionNo());
				prorationFilterVO.setBaseCurrency(baseCurrency);
				prorationFilterVO.setMailSquenceNumber(mailProrationLogVO.getMailSequenceNumber());//added by A-7371 as part of  ICRD-257532

			}
			session.setProrationFilterVO(prorationFilterVO);

			//server call
			try {
				prorationDetailVOs=new MailTrackingMRADelegate().viewProrationLogDetails(prorationFilterVO);
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}

			log.log(Log.INFO, "prorationDetailVOs---->", prorationDetailVOs);
			if(prorationDetailVOs!=null){
				session.setProrationVOs((ArrayList<ProrationDetailsVO>)prorationDetailVOs);
				for(ProrationDetailsVO detailsVO:prorationDetailVOs) {
					contractCurrency = 	detailsVO.getCtrCurrencyCode()!=null ? detailsVO.getCtrCurrencyCode(): baseCurrency ;//Added by A-5219 for ICRD-258240
				form.setDispatch(dsn);
				form.setConDocNo(mailProrationLogVO.getCsgDocumentNumber());
				form.setOrigin(detailsVO.getOriginExchangeOffice());
				form.setDest(detailsVO.getDestinationExchangeOffice());
				form.setGpa(detailsVO.getPostalAuthorityCode());
				form.setGpaName(detailsVO.getPostalAuthorityName());
				form.setCategory(detailsVO.getMailCategoryCode());
				form.setSubClass(detailsVO.getMailSubclass());
				form.setTotWt(detailsVO.getWeight()+"");
				form.setRsn(detailsVO.getRsn());
				form.setDsn(detailsVO.getDsn());
				form.setDisplayWeightUnit(detailsVO.getDisplayWeightUnit());
				}
			}

		}
		else{
			if(dSNPopUpSession.getSelectedDespatchDetails()!=null){

				prorationFilterVO.setDespatchSerialNumber(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());
				prorationFilterVO.setCompanyCode(dSNPopUpSession.getSelectedDespatchDetails().getCompanyCode());
				prorationFilterVO.setBillingBasis(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());
				prorationFilterVO.setConsigneeDocumentNumber(dSNPopUpSession.getSelectedDespatchDetails().getCsgdocnum());
				prorationFilterVO.setConsigneeSequenceNumber(dSNPopUpSession.getSelectedDespatchDetails().getCsgseqnum());
				prorationFilterVO.setPoaCode(dSNPopUpSession.getSelectedDespatchDetails().getGpaCode());
				prorationFilterVO.setMailSquenceNumber(dSNPopUpSession.getSelectedDespatchDetails().getMailSequenceNumber());//added by A-7371 
				form.setDispatch(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());
				form.setConDocNo(dSNPopUpSession.getSelectedDespatchDetails().getCsgdocnum());

			    session.setProrationFilterVO(prorationFilterVO);
			log.log(Log.FINE, "prorationFilterVO----->>>", prorationFilterVO);
			}
		}

			/*
			 * Validate for client errors. The method will check for mandatory fields
			 */

			errors = validateForm(form);

			if (errors != null && errors.size() > 0) {
				form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(errors);
				invocationContext.target = VALIDATION_FAILURE;
				return;
			}

//		if(form.getFlightNo()!=null && ! form.getFlightNo().equals("")){
//
//			   flightValidationVO=validateFlight(form,invocationContext,logonAttributes);
//
//			   if(flightValidationVO!=null){
//
//					prorationFilterVO.setFlightNumber(form.getFlightNo());
//					prorationFilterVO.setFlightDate(convertToDate(form.getFlightDate()));
//					prorationFilterVO.setFlightCarrierIdentifier(flightValidationVO.getFlightCarrierId());
//					prorationFilterVO.setFlightSeqNumber(flightValidationVO.getFlightSequenceNumber());
//
//				    }
//		}
//
//		if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){
//
//			invocationContext.addAllError(errors);
//			invocationContext.target = VALIDATION_FAILURE;
//			return;
//		}



			try {
				prorationDetailsVO=new MailTrackingMRADelegate().listProrationDetails(prorationFilterVO);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			log.log(Log.INFO, "prorationDetailsVO---->", prorationDetailsVO);
			Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
			/**
			 * Populating the sector value for Surcharge popup sector dropdown
			 */
			//Added by Manish for IASCB-22714
			boolean isOalExists = false;
			boolean isOalAdj = false;
			if(prorationDetailsVO!=null){
				for(ProrationDetailsVO vo:prorationDetailsVO){
					if(!logonAttributes.getOwnAirlineCode().equals(vo.getCarrierCode()))
							isOalExists = true;
				}
			}
			populateSectorDetails(prorationDetailsVO, session);
			session.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);

			Collection<ProrationDetailsVO> primaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
			Collection<ProrationDetailsVO> secondaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
			ArrayList<ProrationDetailsVO> primaryDetailsVOsAfterOrdering=new ArrayList<ProrationDetailsVO>();

			if(prorationDetailsVO!=null){
				for(ProrationDetailsVO vo:prorationDetailsVO){
				
				
					if(MRAConstantsVO.VOIDED.equals(vo.getBlgSta()))
					{
						
						ErrorVO errorVO = new ErrorVO(VOID_MAILBGS);
						errors = new ArrayList<ErrorVO>();
						errors.add(errorVO);
						
						 invocationContext.addAllError(errors);
						 session.removePrimaryProrationVOs();
						 session.removeSecondaryProrationVOs();
						 session.removeSectorDetails();
						 form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
						 invocationContext.target = LIST_FAILURE;
						 return;
						    
						
					}
					contractCurrency = vo.getCtrCurrencyCode()!=null ? vo.getCtrCurrencyCode(): baseCurrency ;//Added by A-5219 for ICRD-258240
					if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
						if(!(RETENTION.equals(vo.getPayableFlag()))){
							primaryDetailsVOs.add(vo);
						}

					}
                     //Condition modified for icrd-99928
					if(GPA.equals(vo.getGpaarlBillingFlag())){
						if(RECEIVABLE.equals(vo.getPayableFlag())){
							primaryDetailsVOs.add(vo);
						}else if(RETENTION.equals(vo.getPayableFlag()) && !GPA.equals(vo.getCodeShareIndicator()) && isOalExists && !isOalAdj){//Added by Manish for IASCB-22714
							primaryDetailsVOs.add(vo);
							isOalAdj = true;
							continue;
						}

					}
					if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
						if(RETENTION.equals(vo.getPayableFlag())){
							secondaryDetailsVOs.add(vo);
						}

					}
					if(GPA.equals(vo.getGpaarlBillingFlag())){
						if(RETENTION.equals(vo.getPayableFlag())){
							secondaryDetailsVOs.add(vo);
						}

					}
					if(("".equals(vo.getGpaarlBillingFlag()))||(vo.getGpaarlBillingFlag()==null) ){

						if(RETENTION.equals(vo.getPayableFlag())){
							secondaryDetailsVOs.add(vo);
							primaryDetailsVOs.add(vo);
						}

					}
					if(RETENTION.equals(vo.getPayableFlag())){
						if(vo.getFlightNumber() != null){
							form.setCarrierCode(vo.getCarrierCode());
							form.setFlightNo(vo.getFlightNumber());
							form.setFlightDate(vo.getFlightDate().toDisplayDateOnlyFormat());
						}else{
							form.setCarrierCode(null);
							form.setFlightNo(null);
							form.setFlightDate(null);
						}
						prorationFilterVO.setCarrierCode(vo.getCarrierCode());
						prorationFilterVO.setFlightNumber(vo.getFlightNumber());
						prorationFilterVO.setFlightDate(vo.getFlightDate());
					}
				}
  
				form.setFormStatusFlag(ENABLE);
			}
			
			
			
			/*if("VD".equals(prorationDetailsVO.) ){
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.voidmailbags");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				}*/
			ArrayList<ProrationDetailsVO> priTrecs =new ArrayList<ProrationDetailsVO>();
			ArrayList<ProrationDetailsVO> priTrecsToremove =new ArrayList<ProrationDetailsVO>();
			ArrayList<ProrationDetailsVO> newProrationVOs =new ArrayList<ProrationDetailsVO>();

			//Added by A-6991 for ICRD-208114
			//Modified by A-7540 for ICRD-236156
			String overrideRounding= "N";
			if(listInterlineBillingEntriesSession.getSystemparametres() != null &&
					!listInterlineBillingEntriesSession.getSystemparametres().isEmpty()){
				overrideRounding = ((HashMap<String,String>)listInterlineBillingEntriesSession.getSystemparametres()).get(SYS_PAR_OVERRIDE_ROUNDING);
			}
			
			Map<String, String> systemParameterValues=null;
	 		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			if(listInterlineBillingEntriesSession.getSystemparametres()!=null 
			&&listInterlineBillingEntriesSession.getSystemparametres().size()>0 &&
			((HashMap<String,String>)listInterlineBillingEntriesSession.getSystemparametres()).get(SYS_PAR_OVERRIDE_ROUNDING)!=null &&
					((HashMap<String,String>)listInterlineBillingEntriesSession.getSystemparametres()).get(SYS_PAR_OVERRIDE_ROUNDING).trim().length()>0){
			 overrideRounding=((HashMap<String,String>)listInterlineBillingEntriesSession.getSystemparametres()).get(SYS_PAR_OVERRIDE_ROUNDING);
			}else{//added by A-7371 as part of ICRD-252986
				try {
					 systemParameterValues=sharedDefaultsDelegate.findSystemParameterByCodes(getSystemParameterTypes());
				} catch (BusinessDelegateException e) {
					handleDelegateException( e );
				}
				overrideRounding=systemParameterValues.get(SYS_PAR_OVERRIDE_ROUNDING);
			}
			Money totalInUsd = null;
			Money totalInBase = null;
			Money totalInSdr = null;
			Money totalInCtr = null;
			Money totalSurUsd = null;//added by a-7531 for icrd-258454
			Money totalSurBase = null;
			Money totalSurSdr = null;
			Money totalSurCtr = null;

				try {
					totalInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
					totalInUsd.setAmount(0.0);
					totalInBase = CurrencyHelper.getMoney(baseCurrency);
					totalInBase.setAmount(0.0);
					totalInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
					totalInSdr.setAmount(0.0);
				 	totalInCtr=CurrencyHelper.getMoney(contractCurrency);//modified by A-5219 for ICRD-258240
				    totalInCtr.setAmount(0.0);
				    totalSurUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);//added by a-7531 for icrd-258454
				    totalSurUsd.setAmount(0.0);
				    totalSurBase = CurrencyHelper.getMoney(baseCurrency);
				    totalSurBase.setAmount(0.0);
				    totalSurSdr = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
				    totalSurSdr.setAmount(0.0);
				    totalSurCtr = CurrencyHelper.getMoney(contractCurrency);
				    totalSurCtr.setAmount(0.0);
				} catch (CurrencyException e) {
					e.getErrorCode();
				}
				 double totalInUsdSumPri=0;
	             double totalInBaseSumPri=0;
	             
				if(primaryDetailsVOs!=null && primaryDetailsVOs.size()>0){
					//Modified by Manish for IASCB-22714
					double totalInUsdSum =0;
					double totalInBaseSum =0;
					double totalInSdrSum =0;
					double totalInCtrSum =0;
					double totalSurUsdSum =0;//added by a-7531 for icrd-258454
					double totalSurBaseSum =0;
					double totalSurSdrSum =0;
					double totalSurCtrSum =0;
					for (ProrationDetailsVO priDetailsVO : primaryDetailsVOs) {

						if(RETENTION.equals(priDetailsVO.getPayableFlag())){
								priTrecs.add(priDetailsVO);
						}
						 //Added by A-6991 for ICRD-208114
						if(!ProrationDetailsVO.FLAG_NO.equalsIgnoreCase(overrideRounding))
						{
							
							
							
							totalInUsdSum +=priDetailsVO
									.getProrationAmtInUsd().getAmount();
							totalInBaseSum +=priDetailsVO
									.getProrationAmtInBaseCurr().getAmount();
							totalInSdrSum +=priDetailsVO.getProrationAmtInSdr().getAmount();
							if(totalInCtr.getCurrencyCode().equals(priDetailsVO.getCtrCurrencyCode())){//MODIFIED FOR ICRD-285940
							totalInCtrSum +=priDetailsVO.getProratedAmtInCtrCur().getAmount();
							    form.setCurrencyChangeFlag("Y");
								}
								else{
									form.setCurrencyChangeFlag("N");//MODIFIED FOR ICRD-285940
								}
							
							totalSurUsdSum +=priDetailsVO.getSurProrationAmtInUsd().getAmount();//added by a-7531   for icrd-258454
							totalSurBaseSum +=priDetailsVO.getSurProrationAmtInBaseCurr().getAmount();
							totalSurSdrSum +=priDetailsVO.getSurProrationAmtInSdr().getAmount();
							if(totalSurCtr.getCurrencyCode().equals(priDetailsVO.getCtrCurrencyCode())){//MODIFIED FOR ICRD-285940
							totalSurCtrSum +=priDetailsVO.getSurProratedAmtInCtrCur().getAmount();
							    form.setCurrencyChangeFlag("Y");
								}
								else{
									form.setCurrencyChangeFlag("N");
								}
							
							
							totalInUsd.setAmount(totalInUsdSum);
							totalInBase.setAmount(totalInBaseSum);
							totalInSdr.setAmount(totalInSdrSum);
						    totalInCtr.setAmount(totalInCtrSum);
						    totalSurUsd.setAmount(totalSurUsdSum);//added by a-7531	 for icrd-258454
							totalSurBase.setAmount(totalSurBaseSum);
							totalSurSdr.setAmount(totalSurSdrSum);
							totalSurCtr.setAmount(totalSurCtrSum);
						}
						else{
							//Modified by a-7540
							totalInUsdSumPri = totalInUsdSumPri + priDetailsVO.getProrationAmtInUsd().getAmount();
							totalInBaseSumPri =totalInBaseSumPri + priDetailsVO 
									.getProrationAmtInBaseCurr().getAmount();
						totalInSdr=totalInSdr.plusEquals(priDetailsVO.getProrationAmtInSdr());
						if(totalInCtr.getCurrencyCode().equals(priDetailsVO.getCtrCurrencyCode())){
					    totalInCtr=totalInCtr.plusEquals(priDetailsVO.getProratedAmtInCtrCur());
					    form.setCurrencyChangeFlag("Y");
						}
						else{
							form.setCurrencyChangeFlag("N");
						}
 					    totalSurUsd=totalSurUsd.plusEquals(priDetailsVO.getSurProrationAmtInUsd());//added by a-7531   for icrd-258454
					    totalSurBase =totalSurBase.plusEquals(priDetailsVO.getSurProrationAmtInBaseCurr());
					    totalSurSdr =totalSurSdr.plusEquals(priDetailsVO.getSurProrationAmtInSdr());
						if(totalSurCtr.getCurrencyCode().equals(priDetailsVO.getCtrCurrencyCode())){//MODIFIED FOR ICRD-285940
					    totalSurCtr =totalSurCtr.plusEquals(priDetailsVO.getSurProratedAmtInCtrCur());
					    form.setCurrencyChangeFlag("Y");
						}
						else{
							form.setCurrencyChangeFlag("N");
						}
						totalInUsd.setAmount(totalInUsdSumPri);
					    totalInBase.setAmount(totalInBaseSumPri);
					}
						
					}
					form.setTotalInUsdForPri(String
							.valueOf(totalInUsd.getAmount()));
					form.setTotalInBasForPri(String
							.valueOf(totalInBase.getAmount()));
					form.setTotalInSdrForPri(String.valueOf(totalInSdr.getAmount()));
					form.setTotalInCurForPri(String.valueOf(totalInCtr.getAmount()));
					form.setTotalSurchgInUsdForPri(String.valueOf(totalSurUsd.getAmount()));	//added by a-7531   for icrd-258454
					form.setTotalSurchgInBasForPri(String.valueOf(totalSurBase.getAmount()));
					form.setTotalSurchgInSdrForPri(String.valueOf(totalSurSdr.getAmount()));
					form.setTotalSurchgInCurForPri(String.valueOf(totalSurCtr.getAmount())); //added by a-7531 for icrd- 273674
					
					
				}




				Money totalTrecInUsd = null;
				Money totalTrecInBase = null;
				Money totalTrecInSdr = null;
				Money totalTrecInCtr = null;
				Money totalTrecSurUsd = null;//added by a-7531   for icrd-258454
				Money totalTrecSurBase = null;
				Money totalTrecSurSdr = null;
				Money totalTrecSurCtr = null;

				try {
					totalTrecInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
					totalTrecInUsd.setAmount(0.0);
					totalTrecInBase = CurrencyHelper.getMoney(baseCurrency);
					totalTrecInBase.setAmount(0.0);
					totalTrecInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
					totalTrecInSdr.setAmount(0.0);
					totalTrecInCtr=CurrencyHelper.getMoney(contractCurrency);//modified by A-5219 for ICRD-258240
					totalTrecInCtr.setAmount(0.0);
					totalTrecSurUsd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);//added by a-7531   for icrd-258454
					totalTrecSurUsd.setAmount(0.0);
					totalTrecSurBase=CurrencyHelper.getMoney(baseCurrency);
					totalTrecSurBase.setAmount(0.0);
					totalTrecSurSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
					totalTrecSurSdr.setAmount(0.0);
					totalTrecSurCtr=CurrencyHelper.getMoney(contractCurrency);
					totalTrecSurCtr.setAmount(0.0);
					

				} catch (CurrencyException e) {
					e.getErrorCode();
				}

				/*
				 * To group  primary retention records for multi sector flight
				 */
				String tOrgin ="";
				String tDestn ="";
				int profactor=0;
				int siz=0;
				if(priTrecs!=null && priTrecs.size() >1){
					siz=priTrecs.size()-1;
					tOrgin = priTrecs.get(0).getSectorFrom();
					tDestn = priTrecs.get(0).getSectorTo();
					profactor= priTrecs.get(0).getProrationFactor();
					newProrationVOs.add( priTrecs.get(0));
					for(int i =0;i<siz;i++){
						String secto =  priTrecs.get(i).getSectorTo();
						String nxtsecfrm = priTrecs.get(i+1).getSectorFrom();
						if(secto.equals(nxtsecfrm)){
							tDestn = priTrecs.get(i+1).getSectorTo();
							profactor=profactor+priTrecs.get(i+1).getProrationFactor();
							priTrecsToremove.add(priTrecs.get(i));
							priTrecsToremove.add(priTrecs.get(i+1));

							//totalTrecInUsd.setAmount((priTrecs.get(i).getProrationAmtInUsd().getAmount())+(priTrecs.get(i+1).getProrationAmtInUsd().getAmount()));
							//totalTrecInBase.setAmount((priTrecs.get(i).getProrationAmtInBaseCurr().getAmount())+(priTrecs.get(i+1).getProrationAmtInBaseCurr().getAmount()));
							//totalTrecInSdr.setAmount((priTrecs.get(i).getProrationAmtInSdr().getAmount())+(priTrecs.get(i+1).getProrationAmtInSdr().getAmount()));
							//totalTrecInCtr.setAmount((priTrecs.get(i).getProratedAmtInCtrCur().getAmount())+(priTrecs.get(i+1).getProratedAmtInCtrCur().getAmount()));
						}
					}
					for(int i =0;i<=siz;i++){
						if(priTrecs.get(i).getProrationAmtInUsd()!=null){
							totalTrecInUsd.plusEquals(priTrecs.get(i).getProrationAmtInUsd());
						}
						if(priTrecs.get(i).getProrationAmtInBaseCurr()!=null){
							totalTrecInBase.plusEquals(priTrecs.get(i).getProrationAmtInBaseCurr());
						}
						if(priTrecs.get(i).getProrationAmtInSdr()!=null){
							totalTrecInSdr.plusEquals(priTrecs.get(i).getProrationAmtInSdr());
						}
						if(priTrecs.get(i).getProratedAmtInCtrCur()!=null){
							totalTrecInCtr.plusEquals(priTrecs.get(i).getProratedAmtInCtrCur());
						}
						if(priTrecs.get(i).getSurProrationAmtInUsd()!=null){//added by a-7531   for icrd-258454
							totalTrecSurUsd.plusEquals(priTrecs.get(i).getSurProrationAmtInUsd());
						}
						if(priTrecs.get(i).getSurProrationAmtInBaseCurr()!=null){
							totalTrecSurBase.plusEquals(priTrecs.get(i).getSurProrationAmtInBaseCurr());
						}
						if(priTrecs.get(i).getSurProrationAmtInSdr()!=null){
							totalTrecSurSdr.plusEquals(priTrecs.get(i).getSurProrationAmtInSdr());
						}
						if(priTrecs.get(i).getSurProratedAmtInCtrCur()!=null){
							totalTrecSurCtr.plusEquals(priTrecs.get(i).getSurProratedAmtInCtrCur());
						}
					}
				}

				ProrationDetailsVO priProrationVO = null;

				if(priTrecsToremove!=null && priTrecsToremove.size()>0){

					primaryDetailsVOs.removeAll(priTrecsToremove);

					for(ProrationDetailsVO newProrationVO :newProrationVOs){

				
						
							
						priProrationVO =  new ProrationDetailsVO();
						priProrationVO.setSectorFrom(tOrgin);
						priProrationVO.setSectorTo(tDestn);
						priProrationVO.setProrationFactor(profactor);
						priProrationVO.setPayableFlag("T");
						priProrationVO.setProrationPercentage(100);
						priProrationVO.setNumberOfPieces(newProrationVO.getNumberOfPieces());
						priProrationVO.setWeight(newProrationVO.getWeight());
						priProrationVO.setProrationType(newProrationVO.getProrationType());
						priProrationVO.setCarrierCode(newProrationVO.getCarrierCode());
						priProrationVO.setProrationAmtInUsd(totalTrecInUsd);
						priProrationVO.setProrationAmtInBaseCurr(totalTrecInBase);
						priProrationVO.setProrationAmtInSdr(totalTrecInSdr);
						priProrationVO.setProratedAmtInCtrCur(totalTrecInCtr);
						priProrationVO.setSurProrationAmtInUsd(totalTrecSurUsd);//added by a-7531   for icrd-258454
						priProrationVO.setSurProrationAmtInBaseCurr(totalTrecSurBase);
						priProrationVO.setSurProrationAmtInSdr(totalTrecSurSdr);
						priProrationVO.setSurProratedAmtInCtrCur(totalTrecSurCtr);
						priProrationVO.setSectorStatus(newProrationVO.getSectorStatus());
						priProrationVO.setGpaarlBillingFlag("A");
						priProrationVO.setCtrCurrencyCode(newProrationVO.getCtrCurrencyCode());
						primaryDetailsVOs.add(priProrationVO);
						
					}
				}
				// for bug 36649 anz start
				for(ProrationDetailsVO newProrationVO :primaryDetailsVOs){
					currentCurCode=newProrationVO.getCtrCurrencyCode();
					log.log(Log.INFO, "\n\n\n currentCurCode-----> ",
							currentCurCode);
					log.log(Log.INFO, "\n\n\n previousCurCode-----> ",
							previousCurCode);
					if(previousCurCode!=null && !currentCurCode.equals(previousCurCode)){
						contractCurrenySame=false;
					}
					previousCurCode=currentCurCode;
					
				}
				if(!contractCurrenySame){
					form.setTotalInCurForPri("");
				}
				//for bug 36649 anz end
				
				for(ProrationDetailsVO prorationVOforOrdering:primaryDetailsVOs){
					if(RETENTION.equals(prorationVOforOrdering.getPayableFlag())){
						primaryDetailsVOsAfterOrdering.add(prorationVOforOrdering);
					}
				}
				for(ProrationDetailsVO prorationVO:primaryDetailsVOs){
					if(!RETENTION.equals(prorationVO.getPayableFlag())){
						primaryDetailsVOsAfterOrdering.add(prorationVO);
					}
				}
			log.log(Log.INFO, "\n\n\n\ncontractCurrenySame-----> ",
					contractCurrenySame);
			session.setPrimaryProrationVOs(primaryDetailsVOsAfterOrdering);


			
			totalInUsd.setAmount(0.0);
			totalInBase.setAmount(0.0);
			totalInSdr.setAmount(0.0);
			 totalInCtr.setAmount(0.0);
			 totalSurUsd.setAmount(0.0);//added by a-7531   for icrd-258454
		     totalSurBase.setAmount(0.0);
			 totalSurSdr.setAmount(0.0);
			 totalSurCtr.setAmount(0.0);
			 double totalInUsdSumSec =0;
			 double totalInBaseSumSec =0;
				if(secondaryDetailsVOs!=null && secondaryDetailsVOs.size()>0){
					
					double totalInUsdSum =0;
					double totalInBaseSum =0;
					double totalInSdrSum =0;
					double totalInCtrSum =0;
					double totalSurUsdSum =0;//added by a-7531   for icrd-258454
					double totalSurBaseSum =0;
					double totalSurSdrSum =0;
					double totalSurCtrSum =0;
					
					for (ProrationDetailsVO secDetailsVO : secondaryDetailsVOs) {
                 //Added by A-6991 for ICRD-208114
						if(!ProrationDetailsVO.FLAG_NO.equalsIgnoreCase(overrideRounding))
						{																			
							totalInUsdSum +=secDetailsVO
									.getProrationAmtInUsd().getAmount();
							totalInBaseSum +=secDetailsVO
									.getProrationAmtInBaseCurr().getAmount();
							totalInSdrSum +=secDetailsVO.getProrationAmtInSdr().getAmount();
							if(totalInCtr.getCurrencyCode().equals(secDetailsVO.getCtrCurrencyCode())){
							totalInCtrSum +=secDetailsVO.getProratedAmtInCtrCur().getAmount();
							 form.setCurrencyChangeFlag("Y");	
							}else{
								  form.setCurrencyChangeFlag("N");	
							}
							totalSurUsdSum +=secDetailsVO.getSurProrationAmtInUsd().getAmount();//added by a-7531   for icrd-258454
							totalSurBaseSum +=secDetailsVO.getSurProrationAmtInBaseCurr().getAmount();
							totalSurSdrSum +=secDetailsVO.getSurProrationAmtInSdr().getAmount();
							if(totalSurCtr.getCurrencyCode().equals(secDetailsVO.getCtrCurrencyCode())){
							totalSurCtrSum +=secDetailsVO.getSurProratedAmtInCtrCur().getAmount();
								 form.setCurrencyChangeFlag("Y");	
								}else{
									  form.setCurrencyChangeFlag("N");	
								}
							

							totalInUsd.setAmount(totalInUsdSum);
							totalInBase.setAmount(totalInBaseSum);
							totalInSdr.setAmount(totalInSdrSum);
						    totalInCtr.setAmount(totalInCtrSum);
						    totalSurUsd.setAmount(totalSurUsdSum); //added by a-7531   for icrd-258454
						    totalSurBase.setAmount(totalSurBaseSum);
							totalSurSdr.setAmount(totalSurSdrSum);
							totalSurCtr.setAmount(totalSurCtrSum);

						}
						else{
							//Modified by a-7540
							totalInUsdSumSec = totalInUsdSumSec + secDetailsVO.getProrationAmtInUsd().getAmount();
							totalInBaseSumSec =totalInBaseSumSec + secDetailsVO
									.getProrationAmtInBaseCurr().getAmount();
						totalInSdr=totalInSdr.plusEqualsRoundedValue(secDetailsVO.getProrationAmtInSdr());
						if(totalInCtr.getCurrencyCode().equals(secDetailsVO.getCtrCurrencyCode())){
					    totalInCtr=totalInCtr.plusEquals(secDetailsVO.getProratedAmtInCtrCur());
					   	
						}
					    totalSurUsd=totalSurUsd.plusEquals(secDetailsVO.getSurProrationAmtInUsd());//added by a-7531   for icrd-258454
					    totalSurBase=totalSurBase.plusEquals(secDetailsVO.getSurProrationAmtInBaseCurr());
					    totalSurSdr=totalSurSdr.plusEquals(secDetailsVO.getSurProrationAmtInSdr());
					    if(totalSurCtr.getCurrencyCode().equals(secDetailsVO.getCtrCurrencyCode())){
					    totalSurCtr=totalSurCtr.plusEquals(secDetailsVO.getSurProratedAmtInCtrCur());
					    totalInUsd.setAmount(totalInUsdSumSec);
					    totalInBase.setAmount(totalInBaseSumSec);
					    }
						
						}
					}
					form.setTotalInUsdForSec(String
							.valueOf(totalInUsd.getAmount()));
					form.setTotalInBasForSec(String
							.valueOf(totalInBase.getAmount()));
					form.setTotalInSdrForSec(String.valueOf(totalInSdr.getAmount()));
					form.setTotalInCurForSec(String.valueOf(totalInCtr.getAmount()));
					form.setTotalSurchgInUsdForSec(String.valueOf(totalSurUsd.getAmount()));//added by a-7531   for icrd-258454
					form.setTotalSurchgInBasForSec(String.valueOf(totalSurBase.getAmount()));
					form.setTotalSurchgInSdrForSec(String.valueOf(totalSurSdr.getAmount()));
					form.setTotalSurchgInCurForSec(String.valueOf(totalSurCtr.getAmount()));
				}


			session.setSecondaryProrationVOs(secondaryDetailsVOs);


			ProrationDetailsVO prorationVO=new ProrationDetailsVO();
			try{
			prorationVO = prorationDetailsVO.iterator().next();
			
			}
			catch(NoSuchElementException e){

				invocationContext.addError(new ErrorVO("mailtracking.mra.defaults.noprorationfound"));
				invocationContext.addAllError(errors);
				invocationContext.target = NO_DATA_FOUND;
				return;

			}
		
			form.setOrigin(prorationVO.getOriginExchangeOffice());
			form.setDest(prorationVO.getDestinationExchangeOffice());
			form.setGpa(prorationVO.getPostalAuthorityCode());
			form.setGpaName(prorationVO.getPostalAuthorityName());
			form.setCategory(prorationVO.getMailCategoryCode());
			form.setSubClass(prorationVO.getMailSubclass());
			form.setTotWt(prorationVO.getWeight()+"");
			form.setRsn(prorationVO.getRsn());
			form.setDsn(prorationVO.getDsn());

			if (prorationVO.getDisplayWeightUnit() != null && oneTimeValues != null && !oneTimeValues.isEmpty()
					&& oneTimeValues.get(WEIGHT_UNIT_ONETIME) != null
					&& !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty()) {
				for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(prorationVO.getDisplayWeightUnit())) {
						form.setDisplayWeightUnit(oneTimeVO.getFieldDescription());
					}
				}
			}
			

			session.setProrationVOs((ArrayList<ProrationDetailsVO>)prorationDetailsVO);

		if(session.getProrationFilterVO()!=null){




			    if(flightValidationVO!=null){

				prorationFilterVO.setFlightNumber(form.getFlightNo());
				prorationFilterVO.setFlightDate(convertToDate(form.getFlightDate()));
				prorationFilterVO.setFlightCarrierIdentifier(flightValidationVO.getFlightCarrierId());
				prorationFilterVO.setFlightSeqNumber(flightValidationVO.getFlightSequenceNumber());

			    }
			   
		invocationContext.target=LIST_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
		form.setBaseCurrency(baseCurrency);

}


	public Collection<ErrorVO> validateForm(
			MRAViewProrationForm viewProrationForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if(viewProrationForm.getFlightDate()!=null && viewProrationForm.getFlightDate().length()>0){
			if("".equals(viewProrationForm.getCarrierCode()) ||"".equals(viewProrationForm.getFlightNo())){
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightdetails");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				}
			}

			if(viewProrationForm.getCarrierCode()!=null && viewProrationForm.getCarrierCode().length()>0 &&
					viewProrationForm.getFlightNo()!=null && 	viewProrationForm.getFlightNo().length()>0 &&
					(((viewProrationForm.getFlightDate()==null)) || (("".equals(viewProrationForm.getFlightDate().trim()))))){
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightdate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				}

			if(viewProrationForm.getCarrierCode()!=null && viewProrationForm.getCarrierCode().length()>0 &&
					(viewProrationForm.getFlightNo()==null ||"".equals(viewProrationForm.getFlightNo()))) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightno");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				}

			if(viewProrationForm.getFlightNo()!=null && viewProrationForm.getFlightNo().length()>0 &&
					(viewProrationForm.getCarrierCode()==null ||"".equals(viewProrationForm.getCarrierCode()))) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.entercaridr");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				}


		return errors;
	}


	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}



	/**
	 *
	 * @param form
	 * @param invocationContext
	 * @param logonAttributes
	 * @return
	 */
	private FlightValidationVO validateFlight(
			MRAViewProrationForm form,	InvocationContext invocationContext,
			LogonAttributes logonAttributes) {
		log.entering("ListMCommand", "validateFlight");
		Collection<ErrorVO> errors = null;
		FlightValidationVO flightValidationVO = null;
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();

		/*
		 * Validate the carrier code and obtain the carrierId
		 */

		int carrierId = validateCarrierCode(form, invocationContext,
				logonAttributes);

		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size() > 0) {
			log.log(Log.FINE, "\n\n--- Invalid Carrier Code ---\n\n");
			return flightValidationVO;
		}
		/*
		 * Populate the flightFilterVO
		 */
		FlightFilterVO flightFilterVO =	populateFlightFilterVO(form,
				logonAttributes);
		flightFilterVO.setFlightCarrierId(carrierId);

		log.log(Log.INFO, "flightFilterVO ---> ", flightFilterVO);
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			flightValidationVOs =
				delegate.validateFlight(flightFilterVO);
		} catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}

		/*
		 * If the flight validationVO is null
		 */
		if(errors == null && flightValidationVOs.size() == 0){
			StringBuffer flight = new StringBuffer();
			flight.append(form.getCarrierCode().toUpperCase())
				  .append(form.getFlightNo().toUpperCase());

			invocationContext.addError(new ErrorVO(
					"mailtracking.mra.defaults.flightinvalid",
					new String[]{flight.toString(),
							form.getFlightDate()}));


			return flightValidationVO;
		}
		/*
		 * If no error and flightValidationVOs is not null
		 */
		if(flightValidationVOs != null && flightValidationVOs.size() > 0){

				flightValidationVO = (
					(ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
			}

		log.exiting("ListCommand", "validateFlight");

		return flightValidationVO;

		}




	/**
	 *
	 * @param form
	 * @param logonAttributes
	 * @return
	 */
		private FlightFilterVO populateFlightFilterVO(
				MRAViewProrationForm form,
				LogonAttributes logonAttributes){
			log.entering("ListCommand", "populateFlightFilterVO");

			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setCarrierCode(form.getCarrierCode().toUpperCase());
			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			flightFilterVO.setFlightNumber(form.getFlightNo().toUpperCase());

			LocalDate flightDate = new LocalDate(
					logonAttributes.getAirportCode(),Location.ARP,false);
			flightFilterVO.setFlightDate(
						flightDate.setDate(form.getFlightDate()));

			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);

			log.exiting("ListCommand", "populateFlightFilterVO");
			return flightFilterVO;
		}


		/**
		 *
		 * @param form
		 * @param invocationContext
		 * @param logonAttributes
		 * @return
		 */
		private int validateCarrierCode(MRAViewProrationForm form,
				InvocationContext invocationContext,
				LogonAttributes logonAttributes) {
			log.entering("ListCommand", "validateCarrierCode");
			int carrierId = 0;
			AirlineValidationVO airlineValidationVO = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();

			try {
				airlineValidationVO =
					airlineDelegate.validateAlphaCode(
							logonAttributes.getCompanyCode(), form.getCarrierCode().toUpperCase());
			}
			catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "airlineValidationVO ---> ", airlineValidationVO);
			if(airlineValidationVO == null) {
				invocationContext.addError(
						new ErrorVO("mailtracking.mra.defaults.invalidcarriercode",
						new String[]{form.getCarrierCode().toUpperCase()}));
			} else {
				carrierId = airlineValidationVO.getAirlineIdentifier();
			}

			log.log(Log.FINE, "carrierId ---> ", carrierId);
			log.exiting("ListCommand", "validateCarrierCode");
			return carrierId;
		}
		/**
		 *
		 * @param companyCode
		 * @return
		 */
		private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
			log.entering(CLASS_NAME,"fetchOneTimeDetails");
			Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
			Collection<String> oneTimeList=new ArrayList<String>();
			oneTimeList.add(PAYFLAG_ONETIME);
			oneTimeList.add(SECTORSTATUS_ONETIME);
			oneTimeList.add(WEIGHT_UNIT_ONETIME);

			SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
						oneTimeList);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			log.exiting(CLASS_NAME,"fetchOneTimeDetails");
			return hashMap;
		}
		/**
		 * 
		 * @author A-5255
		 * @param prorationDetailsVOs
		 * @param session
		 */
		private void populateSectorDetails(
				Collection<ProrationDetailsVO> prorationDetailsVOs,
			MRAViewProrationSession session) {
		Map<String, String> sectorMap = new HashMap<String, String>();
		if (prorationDetailsVOs != null && prorationDetailsVOs.size()>0) {
			for (ProrationDetailsVO prorationDetailsVO : prorationDetailsVOs) {
				//if(!"T".equals(prorationDetailsVO.getPayableFlag())){//Commented for ICRD-139963
				sectorMap.put(prorationDetailsVO.getSerialNumber() + "",
						prorationDetailsVO.getSectorFrom() + "-"
								+ prorationDetailsVO.getSectorTo());
				//}
			}
			session.setSectorDetails((HashMap<String, String>) sectorMap);
		}
	}
	/**
	 * @author A-7371
	 * @return systemparameterTypes
	 */
		 private Collection<String> getSystemParameterTypes(){
		    	log.entering("RefreshCommand", "getSystemParameterTypes");
		    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

		    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
		    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
		    	return systemparameterTypes;
			}

}
