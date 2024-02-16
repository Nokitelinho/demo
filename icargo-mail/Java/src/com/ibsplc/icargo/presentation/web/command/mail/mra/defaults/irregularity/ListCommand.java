
	/*
	 * ListCommand.java Created on Sep 26, 2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services (P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.irregularity;

	import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
	import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.HashMap;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityDetailsVO;
    import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
    import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;

	import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
    import com.ibsplc.icargo.framework.util.time.LocalDate;
    import com.ibsplc.icargo.framework.util.time.Location;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;

	import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
    import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
    import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
    import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.IrregularitySession;

    import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAIrregularityForm;


	import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

	import com.ibsplc.xibase.server.framework.vo.ErrorVO;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	* Command class for listing 
	*
	* Revision History
	*
	* Version      Date           Author          		    Description
	*
	*  0.1         Sep 26, 2008    A-3229		 		   Initial draft
	*/
	public class ListCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */
		
		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
		
		
		private static final String CLASS_NAME = "ListCommand";
		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.irregularity";
		
		private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";
		
		private static final String LIST_SUCCESS = "list_success";

		private static final String LIST_FAILURE = "list_failure";
		
		private static final String IRPSTATUS="mailtracking.mra.irpstatus";
		
		private static final String BASE_CURRENCY = "shared.station.basecurrency";
		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			
			IrregularitySession irregularitysession=(IrregularitySession)getScreenSession(MODULE_NAME, SCREEN_ID);
			
			MRAIrregularityForm irregularityForm=(MRAIrregularityForm)invocationContext.screenModel;
			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
				
			MRAIrregularityFilterVO irregularityFilterVO=new MRAIrregularityFilterVO();
			Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		
			//To get the form values
		    String fromDate=irregularityForm.getFromDate();
			String toDate=irregularityForm.getToDate();
			String effectiveDate=irregularityForm.getEffectiveDate();	
			
			
			//To fetch DSN pks
			DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
			log.log(Log.INFO, "POPUPSESSION------------------", popUpSession.getSelectedDespatchDetails());
			DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
			log.log(Log.INFO, "inside list command popupvo ", popUpVO);
			if(popUpVO!=null){
				irregularityForm.setDsn(popUpVO.getDsn());	
				irregularityForm.setBillingBasis(popUpVO.getBlgBasis());
				irregularityForm.setCsgDocumentNumber(popUpVO.getCsgdocnum());
				irregularityForm.setCsgSequenceNumber(String.valueOf(popUpVO.getCsgseqnum()));
				irregularityForm.setPoaCode(popUpVO.getGpaCode());
				
			}
			
					//To set values for filtervo
			        irregularityFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			        irregularityFilterVO.setSubSystem("M");
			        //mandatory
			        if(irregularityForm.getToDate()!=null && irregularityForm.getToDate().trim().length()>0){
			        	LocalDate dateToSet=new LocalDate(logonAttributes.getStationCode(),Location.STN,true);
						dateToSet.setDate(toDate);
						irregularityFilterVO.setToDate(dateToSet);
					}
			        //for DSN 
			    	if(irregularityForm.getBillingBasis()!=null && irregularityForm.getBillingBasis().trim().length()>0){
						irregularityFilterVO.setBillingBasis(irregularityForm.getBillingBasis());
					}
			    	if(irregularityForm.getCsgDocumentNumber()!=null && irregularityForm.getCsgDocumentNumber().trim().length()>0){
						irregularityFilterVO.setCsgDocumentNumber(irregularityForm.getCsgDocumentNumber());
					}
			    	if(irregularityForm.getCsgSequenceNumber()!=null && irregularityForm.getCsgSequenceNumber().trim().length()>0){
						irregularityFilterVO.setCsgSequenceNumber(Integer.parseInt(irregularityForm.getCsgSequenceNumber()));
					}
			    	if(irregularityForm.getPoaCode()!=null && irregularityForm.getPoaCode().trim().length()>0){
						irregularityFilterVO.setPoaCode(irregularityForm.getPoaCode());
					}
					if(irregularityForm.getFromDate()!=null &&irregularityForm.getFromDate().trim().length()>0){
						LocalDate frmDate=new LocalDate(logonAttributes.getStationCode(),Location.STN,true);
						frmDate.setDate(fromDate);
						irregularityFilterVO.setFromDate(frmDate);
					}
					
					
					//other filters		
					if(irregularityForm.getIrpStatus()!=null && irregularityForm.getIrpStatus().trim().length()>0){
						irregularityFilterVO.setIrpStatus(irregularityForm.getIrpStatus());
					}
					if(irregularityForm.getOffloadStation()!=null && irregularityForm.getOffloadStation().trim().length()>0){
						irregularityFilterVO.setOffloadStation(irregularityForm.getOffloadStation());
					}
					if(irregularityForm.getOrigin()!=null &&irregularityForm.getOrigin().trim().length()>0 ){
						irregularityFilterVO.setOrigin(irregularityForm.getOrigin());
					}
					if(irregularityForm.getDestination()!=null && irregularityForm.getDestination().trim().length()>0 ){
						irregularityFilterVO.setOrigin(irregularityForm.getOrigin());
						irregularityFilterVO.setDestination(irregularityForm.getDestination());
					}
					if(irregularityForm.getDsn()!=null && irregularityForm.getDsn().trim().length()>0){
						irregularityFilterVO.setDsn(irregularityForm.getDsn());
					}
					if(irregularityForm.getEffectiveDate()!=null && irregularityForm.getEffectiveDate().trim().length()>0){
						LocalDate effDate=new LocalDate(logonAttributes.getStationCode(),Location.STN,true);
						effDate.setDate(effectiveDate);
						irregularityFilterVO.setEffectiveDate(effDate);
					}
					
					//MONEY IMPL

					ArrayList<String> stationParameterCodes = new ArrayList<String>();
					HashMap<String, String> stationParameters = new HashMap<String, String>();
					stationParameterCodes.add(BASE_CURRENCY);

					try {
						stationParameters = (HashMap<String, String>) (new AreaDelegate()
								.findStationParametersByCode(logonAttributes
										.getCompanyCode(),
										logonAttributes.getStationCode(),
										stationParameterCodes));
						irregularityFilterVO.setBaseCurrency(stationParameters
								.get(BASE_CURRENCY));

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}

					
					String baseCurrency = stationParameters.get(BASE_CURRENCY);
					/*
					 * Validate for client errors. The method 
					 * will check for mandatory fields
					 */
					
					errors = validateForm(irregularityForm, irregularityFilterVO);
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = LIST_FAILURE;
						return;
					}
		
				
					irregularitysession.setIrregularityFilterVO(irregularityFilterVO);				
					log.log(Log.INFO, "irregularityFilterVO----------",
							irregularitysession.getIrregularityFilterVO());
					log.log(Log.INFO, "onetime----------", irregularitysession.getIrpStatus());
						//Server Call
						MailTrackingMRADelegate mraDefaultsDelegate=new MailTrackingMRADelegate();
						Collection<MRAIrregularityVO> irregularityVOs = null;
						try {
							irregularityVOs=mraDefaultsDelegate.viewIrregularityDetails(irregularityFilterVO);
							log.log(Log.FINE,
									"irregularityVOs from Server is----->",
									irregularityVOs);
							
						}catch(BusinessDelegateException businessDelegateException){
					    		log.log(Log.FINE,"inside try...caught businessDelegateException");
					        	businessDelegateException.getMessage();
					        	handleDelegateException(businessDelegateException);
						}  		
						irregularitysession.setIrregularityVOs(irregularityVOs);
						
						
						if(irregularityVOs == null ||irregularityVOs.size() == 0){
								log.log(Log.FINE,"!!!inside resultList== null");
								ErrorVO errorVO = new ErrorVO(
										"mailtracking.mra.defaults.irregularity.msg.err.nodetailsfound");
								errorVO.setErrorDisplayType(ERROR);
								errors.add(errorVO);						
							//	removeFormValues(irregularityForm);
								irregularitysession.removeIrregularityVOs();
								//irregularityForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
								
									
						}
						if (errors != null && errors.size() > 0) {
							log.log(Log.FINE, "!!!inside errors!= null");
							invocationContext.addAllError(errors);
							invocationContext.target = LIST_FAILURE;
						} else {
							log.log(Log.FINE, "!!!inside resultList!= null");
							
							
							ArrayList<MRAIrregularityVO> irregularityVOsCol=new ArrayList<MRAIrregularityVO>(irregularityVOs);
							int siz=irregularityVOsCol.size();
							log.log(Log.FINE, "!!!siz ", siz);
							for(MRAIrregularityVO  retvo:irregularityVOsCol){
								if(retvo.getFlightDetails()!=null){
									for(MRAIrregularityDetailsVO  detvo:retvo.getFlightDetails()){
									 if(("-1").equals(detvo.getRescheduledFlightNumber())){
										 detvo.setRescheduledFlightNumber(" ");
									 }
									}
								}
							}
							MRAIrregularityVO vo=irregularityVOsCol.get(siz-1);
							log.log(Log.FINE, "!!!vo ", vo);
							irregularityForm.setTotal(String.valueOf(vo.getTotal().getAmount()));
							irregularityForm.setTotalFreightCharges(String.valueOf(vo.getTotalFreightCharges().getAmount()));
							
							log.log(Log.FINE, "!!!getTotal ", irregularityForm.getTotal());
							log.log(Log.FINE, "!!!setTotalFreightCharges ",
									irregularityForm.getTotalFreightCharges());
							irregularityForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							invocationContext.target = LIST_SUCCESS;

						}
					
						log.exiting(CLASS_NAME, "execute");
		}
		/**
		 * method to validate form for client side errors
		 * @param mraProrationLogForm
		 * @return
		 */
		
			
		private Collection<ErrorVO> validateForm(MRAIrregularityForm form,
				MRAIrregularityFilterVO filterVO) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			String companyCode = getApplicationSession().getLogonVO()
					.getCompanyCode();
			
			String toDate=form.getToDate();
			if("".equals(toDate) && toDate.trim().length() == 0){
				error=new ErrorVO("mailtracking.mra.defaults.irregularity.msg.err.todatemandatory");
				error.setErrorDisplayType(ERROR);
				errors.add(error);
			
			}
			if(filterVO.getFromDate()!=null && filterVO.getToDate()!=null){
			if(filterVO.getToDate().isLesserThan(filterVO.getFromDate())){
				
				error=new ErrorVO("mailtracking.mra.defaults.irregularity.msg.err.fromdateexceedstodate");
				error.setErrorDisplayType(ERROR);
				errors.add(error);
				
			}
			}
			//if (form.getOrigin() != "" || form.getOrigin().trim().length() != 0){
			if ( form.getOrigin() != null && form.getOrigin().trim().length() > 0){
				log.log(Log.FINE, "origin inside first if------------->", form.getOrigin());
				if (validateStation(form.getOrigin().toUpperCase(),
					companyCode) == null) {
					log.log(Log.FINE, "origin inside second if-------------->",
							form.getOrigin());
					filterVO.setOrigin(form.getOrigin().toUpperCase());	
				}
				else{
				error = new ErrorVO(
						"mailtracking.mra.defaults.irregularity.msg.err.invalidorigin");
				errors.add(error);
				}
			}
		
			if ( form.getDestination() != null && form.getDestination().trim().length() > 0){
				log.log(Log.FINE, "destination inside first if------------->",
						form.getDestination());
				if (validateStation(form.getDestination().toUpperCase(),
					companyCode) == null) {
					log.log(Log.FINE,
							"destination inside second if-------------->", form.getDestination());
					filterVO.setDestination(form.getDestination().toUpperCase());	
				}
				
				else{
				error = new ErrorVO(
						"mailtracking.mra.defaults.irregularity.msg.err.invaliddestination");
				errors.add(error);
				}
			}
			if (form.getOffloadStation() != null && form.getOffloadStation().trim().length() > 0){
				log.log(Log.FINE, "origin inside first if------------->", form.getOffloadStation());
				if (validateStation(form.getOffloadStation().toUpperCase(),
					companyCode) == null) {
					log.log(Log.FINE, "origin inside second if-------------->",
							form.getOffloadStation());
					filterVO.setOrigin(form.getOffloadStation().toUpperCase());	
				}
				else{
				error = new ErrorVO(
						"mailtracking.mra.defaults.irregularity.msg.err.invalidoffloadstation");
				errors.add(error);
				}
			}
		
			return errors;

		}

		/**
		 * 
		 * @param station
		 * @param companyCode
		 * @return
		 */
		public Collection<ErrorVO> validateStation(String station,
				String companyCode) {
			log.entering("ListCommand", "validateStation");
			Collection<ErrorVO> errors = null;
			try {
				AreaDelegate delegate = new AreaDelegate();
				delegate.validateStation(companyCode, station);

			} catch (BusinessDelegateException e) {
				e.getMessage();
				errors = handleDelegateException(e);
			}
			log.exiting("ListCommand", "validateStation");
			return errors;
		}

		private void removeFormValues(MRAIrregularityForm irregularityForm){
		   irregularityForm.setToDate("");
		   irregularityForm.setDsn("");
		   irregularityForm.setFromDate("");
		  
		}
	}



