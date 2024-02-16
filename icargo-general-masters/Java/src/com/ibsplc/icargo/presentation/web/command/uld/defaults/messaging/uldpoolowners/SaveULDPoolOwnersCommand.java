/*
 * SaveULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.uldpoolowners;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDPoolOwnersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class SaveULDPoolOwnersCommand extends BaseCommand {

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String BLANK = "";

	private static final String SKIP_ADD = "NA";

    //Added by A-2412
	private static final String OPERATION_FLAG_NOOP = "NOOP";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
 
		log.entering("Save Command", "ULD POOL OWNERS");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineValidationVO airlineValidationVO = null;
		ULDPoolOwnerVO poolOwnerVO = session.getUldPoolOwnerVO();
		Collection<ULDPoolOwnerDetailsVO> detailsVOs = null;
		
		
		/* added for add/delete implementation */

		String[] airlineCode = form.getAirlineOwn();
		String[] flightNumber = form.getFlightOwn();
		String[] fromDate = form.getFromDate();
		String[] toDate1 = form.getToDate();
		//String[] opFlag = form.getOperationFlag();
		String[] opFlags=form.getHiddenOperationFlag();
		String  sequenceNumber="0";
		//int count=0;

		 

		ArrayList<ULDPoolOwnerDetailsVO> ownerDetailsVOs = 
			poolOwnerVO.getPoolDetailVOs() != null? new ArrayList<ULDPoolOwnerDetailsVO>(poolOwnerVO.getPoolDetailVOs()):
				new ArrayList<ULDPoolOwnerDetailsVO>();
		
			
				
	// Commented By A-2412 as aprt of Add/Delete change
			
	/*	if(opFlags != null && opFlags.length > 1){
			
				if(!ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT.equals(			
					 	 session.getUldPoolOwnerVO().getHiddenOperationFlag()))
			{
				 
				session.getUldPoolOwnerVO().setHiddenOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
			}
				
					for(int index=0;index<opFlags.length-1;index++){
						log.log(Log.FINE,"Current Operation Flag------>"+opFlags[index]);
				
					
				//	for new insert
				if(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT.equals(opFlags[index])
					&& !SKIP_ADD.equals(opFlags[index])){
				opFlags[index] = SKIP_ADD;
				// The ULDPoolOwnerDetailsVO to be added 


				ULDPoolOwnerDetailsVO poolOwnerDetailsVO = new ULDPoolOwnerDetailsVO();
				form.setHiddenOperationFlag(opFlags);
				form.setOperationFlag(opFlags);
				log.log(Log.FINE,"After adding to the form Current Operation Flag------>"+opFlags);
			
				poolOwnerDetailsVO.setHiddenOperationFlag(
						ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);

				
			

                poolOwnerDetailsVO.setCompanyCode(companyCode);
                poolOwnerDetailsVO.setSequenceNumber(sequenceNumber);
				poolOwnerDetailsVO.setPolSequenceNumber(sequenceNumber);
                poolOwnerDetailsVO.setPolAirlineCode(airlineCode[index]);

				poolOwnerDetailsVO.setPolFligthNumber(flightNumber[index]);
				
				poolOwnerDetailsVO.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);

				if (fromDate[index] != null
						&& fromDate[index].trim().length() > 0) {
					LocalDate polFromDate = new LocalDate(
							getApplicationSession().getLogonVO()
									.getAirportCode(), Location.ARP, false);

					polFromDate.setDate(fromDate[index]);
					log.log(Log.FINE,"pol from date--------------->"+polFromDate);
					poolOwnerDetailsVO.setFromDate(polFromDate);
				}

				if (toDate1[index] != null
						&& toDate1[index].trim().length() > 0) {
					LocalDate polToDate = new LocalDate(
							getApplicationSession().getLogonVO()
									.getAirportCode(), Location.ARP, false);

					polToDate.setDate(toDate1[index]);
					poolOwnerDetailsVO.setToDate(polToDate);

				}
				log.log(Log.FINE,"be4 add"+ownerDetailsVOs);	
				if(poolOwnerDetailsVO!=null){
					if(!ownerDetailsVOs.contains(poolOwnerDetailsVO)){
						ownerDetailsVOs.add(poolOwnerDetailsVO);
						log.log(Log.FINE,"does not contain");						
					}
					else{
						log.log(Log.FINE,"contains");
					}
				log.log(Log.FINE,"the values of ownerDetailsVOs are " +ownerDetailsVOs);
				}
			poolOwnerVO.setPoolDetailVOs(ownerDetailsVOs);
				
				
			}
			
		}
			// added for add/delete implementation 
			
			 detailsVOs = poolOwnerVO.getPoolDetailVOs();
			
			if (detailsVOs != null && detailsVOs.size() > 0) {
				updateULDPoolOwnerDetails(ownerDetailsVOs, form, poolOwnerVO);
			}
			deleteULDPoolOwnerDetails(ownerDetailsVOs, form, poolOwnerVO);
		}*/
		
	    // Added by A-2412 as part of Add / Delete change
			
			if(opFlags != null && opFlags.length > 1){
				int count=ownerDetailsVOs.size();				
				
				for(int i = count - 1 ; i >= 0; i--) {    
					
					log.log(Log.FINE, "opFlags--------------->>>>", opFlags, i);
					if(OPERATION_FLAG_NOOP.equals(opFlags[i])) {
							ownerDetailsVOs.remove(i);						
					}
					else {
						ULDPoolOwnerDetailsVO poolOwnerDetailsVO =  ownerDetailsVOs.get(i);	
						    poolOwnerDetailsVO.setCompanyCode(companyCode);
			               // poolOwnerDetailsVO.setSequenceNumber(sequenceNumber);
						  //poolOwnerDetailsVO.setPolSequenceNumber(sequenceNumber);
			                poolOwnerDetailsVO.setPolAirlineCode(airlineCode[i]);
							poolOwnerDetailsVO.setPolFligthNumber(flightNumber[i]);
							if (fromDate[i] != null
									&& fromDate[i].trim().length() > 0) {
								LocalDate polFromDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polFromDate.setDate(fromDate[i]);
								log.log(Log.FINE,
										"pol from date--------------->",
										polFromDate);
								poolOwnerDetailsVO.setFromDate(polFromDate);
							}

							if (toDate1[i] != null
									&& toDate1[i].trim().length() > 0) {
								LocalDate polToDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polToDate.setDate(toDate1[i]);
								poolOwnerDetailsVO.setToDate(polToDate);

							}	
							poolOwnerDetailsVO.setOperationFlag(opFlags[i]);							
					}
	    		}
				
				for(int i = count ; i < opFlags.length - 1; i++) {
					
					log.log(Log.FINE, "opFlags--------------->", opFlags, i);
					if(!OPERATION_FLAG_NOOP.equals(opFlags[i])) {
						ULDPoolOwnerDetailsVO poolOwnerDetailsVO  = new ULDPoolOwnerDetailsVO();
						 poolOwnerDetailsVO.setCompanyCode(companyCode);
			               poolOwnerDetailsVO.setSequenceNumber(sequenceNumber);
						   poolOwnerDetailsVO.setPolSequenceNumber(sequenceNumber);
			                poolOwnerDetailsVO.setPolAirlineCode(airlineCode[i]);
							poolOwnerDetailsVO.setPolFligthNumber(flightNumber[i]);
							//poolOwnerDetailsVO.setFlightCarrierIdentifier(flightCarrierIdentifier)
							if (fromDate[i] != null
									&& fromDate[i].trim().length() > 0) {
								LocalDate polFromDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polFromDate.setDate(fromDate[i]);
								log.log(Log.FINE,
										"pol from date--------------->",
										polFromDate);
								poolOwnerDetailsVO.setFromDate(polFromDate);
							}

							if (toDate1[i] != null
									&& toDate1[i].trim().length() > 0) {
								LocalDate polToDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polToDate.setDate(toDate1[i]);
								poolOwnerDetailsVO.setToDate(polToDate);

							}	
							poolOwnerDetailsVO.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);
							
							log.log(Log.FINE,
									"poolOwnerDetailsVO--------------->",
									poolOwnerDetailsVO);
							ownerDetailsVOs.add(poolOwnerDetailsVO);
					}
				}
				
				poolOwnerVO.setPoolDetailVOs(ownerDetailsVOs);				
				session.setUldPoolOwnerVO(poolOwnerVO);
				
			}
			log.log(Log.FINE, "ownerDetailsVOs--------------->",
					ownerDetailsVOs);
			log.log(Log.FINE, "poolOwnerVO--------------->", poolOwnerVO);
			if(opFlags != null && opFlags.length > 1){
				int count=ownerDetailsVOs.size();				
				
				for(int i = count - 1 ; i >= 0; i--) {    
					
					log.log(Log.FINE, "opFlags--------------->>>>", opFlags, i);
					if(OPERATION_FLAG_NOOP.equals(opFlags[i])) {
							ownerDetailsVOs.remove(i);						
					}
					else {
						ULDPoolOwnerDetailsVO poolOwnerDetailsVO =  ownerDetailsVOs.get(i);	
						    poolOwnerDetailsVO.setCompanyCode(companyCode);
			               // poolOwnerDetailsVO.setSequenceNumber(sequenceNumber);
						  //poolOwnerDetailsVO.setPolSequenceNumber(sequenceNumber);
			                poolOwnerDetailsVO.setPolAirlineCode(airlineCode[i]);
							poolOwnerDetailsVO.setPolFligthNumber(flightNumber[i]);
							if (fromDate[i] != null
									&& fromDate[i].trim().length() > 0) {
								LocalDate polFromDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polFromDate.setDate(fromDate[i]);
								log.log(Log.FINE,
										"pol from date--------------->",
										polFromDate);
								poolOwnerDetailsVO.setFromDate(polFromDate);
							}

							if (toDate1[i] != null
									&& toDate1[i].trim().length() > 0) {
								LocalDate polToDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polToDate.setDate(toDate1[i]);
								poolOwnerDetailsVO.setToDate(polToDate);

							}	
							poolOwnerDetailsVO.setOperationFlag(opFlags[i]);							
					}
	    		}
				
				for(int i = count ; i < opFlags.length - 1; i++) {
					
					log.log(Log.FINE, "opFlags--------------->", opFlags, i);
					if(!OPERATION_FLAG_NOOP.equals(opFlags[i])) {
						ULDPoolOwnerDetailsVO poolOwnerDetailsVO  = new ULDPoolOwnerDetailsVO();
						 poolOwnerDetailsVO.setCompanyCode(companyCode);
			               poolOwnerDetailsVO.setSequenceNumber(sequenceNumber);
						   poolOwnerDetailsVO.setPolSequenceNumber(sequenceNumber);
			                poolOwnerDetailsVO.setPolAirlineCode(airlineCode[i]);
							poolOwnerDetailsVO.setPolFligthNumber(flightNumber[i]);
							//poolOwnerDetailsVO.setFlightCarrierIdentifier(flightCarrierIdentifier)
							if (fromDate[i] != null
									&& fromDate[i].trim().length() > 0) {
								LocalDate polFromDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polFromDate.setDate(fromDate[i]);
								log.log(Log.FINE,
										"pol from date--------------->",
										polFromDate);
								poolOwnerDetailsVO.setFromDate(polFromDate);
							}

							if (toDate1[i] != null
									&& toDate1[i].trim().length() > 0) {
								LocalDate polToDate = new LocalDate(
										getApplicationSession().getLogonVO()
												.getAirportCode(), Location.ARP, false);

								polToDate.setDate(toDate1[i]);
								poolOwnerDetailsVO.setToDate(polToDate);

							}	
							poolOwnerDetailsVO.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);
							
							log.log(Log.FINE,
									"poolOwnerDetailsVO--------------->",
									poolOwnerDetailsVO);
							ownerDetailsVOs.add(poolOwnerDetailsVO);
					}
				}
				
				poolOwnerVO.setPoolDetailVOs(ownerDetailsVOs);				
				session.setUldPoolOwnerVO(poolOwnerVO);
				
			}
			log.log(Log.FINE, "ownerDetailsVOs--------------->",
					ownerDetailsVOs);
			log.log(Log.FINE, "poolOwnerVO--------------->", poolOwnerVO);
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		
		
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			if (validateAirlineCodes(form, companyCode, poolOwnerVO) != null) {
				errors.add(new ErrorVO("uld.defaults.uldpoolowners.msg.err.invalidairline",
						new Object[] { form.getAirline().toUpperCase() }));
						log.log(Log.ALL,"invalidddd airline ");
			}
		}

		if(form.getOrigin()!=null && form.getOrigin().trim().length()>0){
			if(validateAirportCodes(form.getOrigin().toUpperCase(),companyCode)!=null){
				errors.add(new ErrorVO("uld.defaults.uldpoolowners.msg.err.invalidorigin"));
			}
		}

		if(form.getDestination()!=null && form.getDestination().trim().length()>0){
			if(validateAirportCodes(form.getDestination().toUpperCase(),companyCode)!=null){
				errors.add(new ErrorVO("uld.defaults.uldpoolowners.msg.err.invaliddestn"));
			}
		}

		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=SAVE_FAILURE;
			log.log(Log.ALL, "errors are :-- ", invocationContext.getErrors());
			return;
		}
		
		
		
		if (ownerDetailsVOs != null && ownerDetailsVOs.size() > 0) {
			for(ULDPoolOwnerDetailsVO uldpoolOwnerDetailsVO : ownerDetailsVOs){
				log.log(Log.FINE,"Before VlaidateAirlineCode------------>");
				errors =validateAirlineCode(uldpoolOwnerDetailsVO, form, companyCode);
				if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
					/*errors = validateDateFields(detailsVOs, form, applicationSession);
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}*/
					/*errors = validateEndDates(detailsVOs);
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}*/
				}

			}

		//Added by A-2412
		detailsVOs = ownerDetailsVOs;
		
		
		if (detailsVOs != null && detailsVOs.size() > 0)
		{
			// validateAirlineCode(detailsVOs, form, companyCode);

			/*errors = validateDateFields(detailsVOs, form, applicationSession);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}*/
			errors = validateEndDates(detailsVOs);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}
		populateOwnerVO(form, poolOwnerVO);

		try {
			airlineValidationVO = airlineDelegate.validateAlphaCode(
					companyCode, form.getAirline().toUpperCase());

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			errors = new ArrayList<ErrorVO>();
			Object[] obj = { form.getAirline().toUpperCase() };
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.invalidairline", obj);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		poolOwnerVO.setFlightCarrierIdentifier(airlineValidationVO
				.getAirlineIdentifier());
		
		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDPoolOwnerDetailsVO detailsVO : detailsVOs) {
				detailsVO.setFlightCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		if (!ULDPoolOwnerVO.OPERATION_FLAG_INSERT.equals(poolOwnerVO
				.getOperationFlag())) {
			poolOwnerVO.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_UPDATE);
		}



		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ULDPoolOwnerVO> poolOwnerVOs = new ArrayList<ULDPoolOwnerVO>();
		poolOwnerVOs.add(poolOwnerVO);
		log.log(Log.FINE, "Pool Owner Vos to server------------->",
				poolOwnerVOs);
		//Added By Sreekumar S as a part of implementing validation - Pool Ariline cannot be same as Own Airline
		//Earlier this validation was in Script only , Changed as a part of Bug reported -Bug_ID:2031
		int flag = 0;
		if(poolOwnerVOs != null && poolOwnerVOs.size() > 0){
			for(ULDPoolOwnerVO uLDPoolOwnerVO : poolOwnerVOs){
				if(uLDPoolOwnerVO.getPoolDetailVOs() != null && uLDPoolOwnerVO.getPoolDetailVOs().size() > 0){
					for( ULDPoolOwnerDetailsVO ULDPoolOwnerDetailsVO : uLDPoolOwnerVO.getPoolDetailVOs()){
						if (form.getAirline() != null && form.getAirline().trim().length() > 0){
							if(ULDPoolOwnerDetailsVO.getPolAirlineCode().equalsIgnoreCase(form.getAirline())){
								flag = 1;
							}
						}
					}
				}
			}
		}
		ErrorVO err = null;
		if(flag == 1){
			err = new ErrorVO("uld.defaults.uldpoolowners.msg.err.poolairlinesameasownairline");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(err);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		//Added By Sreekumar S as a part of implementing validation - ENDS
		
		
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			
			delegate.saveULDPoolOwner(poolOwnerVOs);
			
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			exception = handleDelegateException(ex);
		}

		if(exception !=null && exception.size()>0){
			invocationContext.addAllError(exception);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		else
		{
		form.setAirline(BLANK);
		form.setOrigin(BLANK);
		form.setDestination(BLANK);
		form.setFlightNumber(BLANK);
		form.setLinkStatus(BLANK);
		session.setFlightValidationVOSession(null);
		ULDPoolOwnerVO newPoolOwnerVO = new ULDPoolOwnerVO();
		newPoolOwnerVO.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_INSERT);
		newPoolOwnerVO.setCompanyCode(companyCode);
		session.setUldPoolOwnerVO(newPoolOwnerVO);
		
		ErrorVO error = new ErrorVO("uld.defaults.poolowners.savedsuccessfully");
     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
     	errors = new ArrayList<ErrorVO>();
     	errors.add(error);
     	invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
	}
	}

	/**
	 *
	 * @param form
	 * @param poolOwnerVO
	 */
	private void populateOwnerVO(ULDPoolOwnersForm form,
			ULDPoolOwnerVO poolOwnerVO) {
		poolOwnerVO.setDestination(form.getDestination().toUpperCase());
		poolOwnerVO.setOrigin(form.getOrigin().toUpperCase());
		poolOwnerVO.setFlightNumber(form.getFlightNumber().toUpperCase());

	}

	/**
	 *
	 * @param poolOwnerDetailsVOs
	 * @param form
	 * @param poolOwnerVO
	 */

	/*public void updateULDPoolOwnerDetails(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form, ULDPoolOwnerVO poolOwnerVO) {
		String[] polAirline = form.getAirlineOwn();
		String[] polFlights = form.getFlightOwn();
		String[] fromDate = form.getFromDate();
		String[] toDate = form.getToDate();
		int index = 0;
		for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
			if (detailsVO.getOperationFlag() == null) {
				if (hasValueChanged(detailsVO.getPolAirlineCode(),
						polAirline[index].toUpperCase())
						|| hasValueChanged(detailsVO.getPolFligthNumber(),
								polFlights[index].toUpperCase())
						|| hasValueChanged(detailsVO.getFromDate()
								.toDisplayDateOnlyFormat(), fromDate[index])
						|| hasValueChanged(detailsVO.getToDate()
								.toDisplayDateOnlyFormat(), toDate[index])) {
					detailsVO
							.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
					poolOwnerVO
							.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
				}
			}

			if (!ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE.equals(detailsVO
					.getOperationFlag())) {
				detailsVO.setPolAirlineCode(polAirline[index].toUpperCase());
				detailsVO.setPolFligthNumber(polFlights[index].toUpperCase());
				if (fromDate[index] != null
						&& fromDate[index].trim().length() > 0) {
					LocalDate polFromDate = new LocalDate(
							getApplicationSession().getLogonVO()
									.getAirportCode(), Location.ARP, false);
					polFromDate.setDate(fromDate[index]);
					detailsVO.setFromDate(polFromDate);
				}

				if (toDate[index] != null && toDate[index].trim().length() > 0) {
					LocalDate polToDate = new LocalDate(getApplicationSession()
							.getLogonVO().getAirportCode(), Location.ARP, false);
					polToDate.setDate(toDate[index]);
					detailsVO.setToDate(polToDate);

				}
			}

			index++;
		}

	}*/
	public void updateULDPoolOwnerDetails(
			ArrayList<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form, ULDPoolOwnerVO poolOwnerVO){
		String[] airline=form.getAirlineOwn();
		String[] flight=form.getFlightOwn();
		String[] fromDate=form.getFromDate();
		String[] toDate=form.getToDate();
		String[] hiddenOpFlag=form.getHiddenOperationFlag();
		String[] opFlag=form.getOperationFlag();
		int count=0;
		if (hiddenOpFlag != null && hiddenOpFlag.length > 1) {
			log.log(Log.INFO, "poolOwnerDetailsVOs .size", poolOwnerDetailsVOs.size());
		for (int index = 0; index < hiddenOpFlag.length - 1; index++) {
			log.log(Log.FINE, "Current hidden Operation Flag------>",
					hiddenOpFlag, index);
			if ("NOOP".equals(hiddenOpFlag[index])) {
				continue;
			}
			ULDPoolOwnerDetailsVO uldpoolOwnervo=poolOwnerDetailsVOs.get(count);
				
		if (ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE
				.equals(hiddenOpFlag[index])) {
			log.log(Log.FINE, "Setting Operation Flag Update------>",
					hiddenOpFlag, index);
			uldpoolOwnervo.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
			uldpoolOwnervo.setHiddenOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
		} else if (ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE
				.equals(hiddenOpFlag[index])) {
			uldpoolOwnervo .setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE);
			uldpoolOwnervo .setHiddenOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE);
		}
		uldpoolOwnervo.setPolAirlineCode(airline[index]);
		uldpoolOwnervo.setPolFligthNumber(flight[index]);
		if (fromDate[index] != null
				&& fromDate[index].trim().length() > 0) {
			LocalDate polFromDate = new LocalDate(
					getApplicationSession().getLogonVO()
							.getAirportCode(), Location.ARP, false);

			polFromDate.setDate(fromDate[index]);
			log.log(Log.FINE, "pol from date(Update)--------------->",
					polFromDate);
			uldpoolOwnervo.setFromDate(polFromDate);
		}

		if (toDate[index] != null
				&& toDate[index].trim().length() > 0) {
			LocalDate polToDate = new LocalDate(
					getApplicationSession().getLogonVO()
							.getAirportCode(), Location.ARP, false);

			polToDate.setDate(toDate[index]);
			uldpoolOwnervo.setToDate(polToDate);
		}
		count++;
		}
		poolOwnerVO.setPoolDetailVOs(poolOwnerDetailsVOs);
		log
				.log(
						Log.ALL,
						"after updateULDPoolOwnerDetails values are ------------------->>>>",
						poolOwnerDetailsVOs);
	}
	}

public void deleteULDPoolOwnerDetails(ArrayList<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form,ULDPoolOwnerVO poolOwnerVO)	{
	ArrayList<ULDPoolOwnerDetailsVO> uldpoolvo=new ArrayList<ULDPoolOwnerDetailsVO>();
	
	String[] hiddenOpFlag=form.getHiddenOperationFlag();
	int count=0;
	if (hiddenOpFlag != null && hiddenOpFlag.length > 1) {
		for (int index = 0; index < hiddenOpFlag.length - 1; index++) {
			if (index == poolOwnerDetailsVOs.size()) {
				break;
			}
			ULDPoolOwnerDetailsVO uldpoolOwnervo=poolOwnerDetailsVOs.get(count);
			log.log(Log.FINE, "details of vo", uldpoolOwnervo);
			if ("NOOP".equals(hiddenOpFlag[index])) {
				if (ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT
						.equals(poolOwnerDetailsVOs.get(index).getHiddenOperationFlag())) {
					uldpoolvo.add(poolOwnerDetailsVOs.get(index));
					
					log.log(Log.FINE, "the uldpoolvo from delete:", uldpoolvo);
				}
			}
			count++;
			log.log(Log.FINE, "the count value after deleting the record ",
					count);
		}
		
	}
	if (uldpoolvo != null
			&& uldpoolvo.size() > 0) {
		log.log(Log.FINE, "Removing ULDPoolOwnerDetails ");
		log.log(Log.FINE, "Before Removing ULDPoolOwnerDetails ", uldpoolvo);
		//poolOwnerDetailsVOs.removeAll(uldpoolvo);
		poolOwnerVO.setPoolDetailVOs(poolOwnerDetailsVOs);
		log.log(Log.FINE, "Removing ULDPoolOwnerDetails------>",
				poolOwnerDetailsVOs);
	}

}
	

	
	/**
	 *
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equalsIgnoreCase(formValue)) {
				return false;
			}

		}
		return true;
	}

	/**
	 *
	 * @param poolOwnerDetailsVOs
	 * @param form
	 * @param companyCode
	 * @return
	 */

	private Collection<ErrorVO> validateAirlineCode(
			ULDPoolOwnerDetailsVO currentDetailsVO,
			ULDPoolOwnersForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log
				.log(
						Log.FINE,
						"Selected airline code----------------------------------------------------------->",
						currentDetailsVO.getPolAirlineCode());
		if (!ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE
				.equals(currentDetailsVO.getOperationFlag())) {
			String currentAirlineCode = currentDetailsVO.getPolAirlineCode().toUpperCase();
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, currentAirlineCode);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (airlineValidationVO != null) {
				currentDetailsVO.setPolAirlineIdentifier(airlineValidationVO
						.getAirlineIdentifier());
									}
		}
		return errors;
	}

	/**
	 *
	 * @param poolOwnerDetailsVOs
	 * @param form
	 * @param appSession
	 * @return
	 */

	private Collection<ErrorVO> validateDateFields(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form, ApplicationSession appSession) {
		String[] airlineCodes = form.getAirlineOwn();
		String[] flightNumbers = form.getFlightOwn();
		String[] fromDates = form.getFromDate();
		String[] toDates = form.getToDate();
		String[] opFlag = form.getOperationFlag();


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		for (int i = 0; i < airlineCodes.length; i++) {
			int index = 0;
			for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
				if (index != i) {
					log.log(Log.FINE, "fromDateForm------------------>",
							fromDates, i);
					log.log(Log.FINE, "toDateForm-------------->", toDates, i);
					LocalDate fromDateForm = (new LocalDate(appSession
							.getLogonVO().getAirportCode(), Location.ARP, false))
							.setDate(fromDates[i]);
					LocalDate toDateForm = (new LocalDate(appSession
							.getLogonVO().getAirportCode(), Location.ARP, false))
							.setDate(toDates[i]);
					log.log(Log.FINE, "fromDateForm------------------>",
							fromDateForm);
					log.log(Log.FINE, "toDateForm-------------->", toDateForm);
					if (!OPERATION_FLAG_DELETE.equals(opFlag[i])
							&& !OPERATION_FLAG_DELETE.equals(detailsVO
									.getOperationFlag())) {
						if (detailsVO.getPolAirlineCode().equals(
								airlineCodes[i].toUpperCase())
								&& detailsVO.getPolFligthNumber().equals(
										flightNumbers[i].toUpperCase())) {

							if (!detailsVO.getFromDate().isGreaterThan(
									toDateForm)
									&& !detailsVO.getToDate().isLesserThan(
											fromDateForm)) {
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.daterangeexists");
								errors.add(error);
								return errors;
							}

						}

					}

				}
				index++;
			}

		}
		return errors;
	}

	/**
	 *
	 * @param poolOwnerDetailsVOs
	 * @return
	 */
	private Collection<ErrorVO> validateEndDates(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		  LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		  LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
			if (detailsVO.getFromDate().isGreaterThan(detailsVO.getToDate())) {
				error = new ErrorVO(
						"uld.defaults.invalidenddates");
				errors.add(error);
			}
			//newly added for NCA bug fix starts
			if(detailsVO.getFromDate()!=null){
				fromDate.setDate(detailsVO.getFromDate().toDisplayDateOnlyFormat());
			if(currentDate.isGreaterThan(fromDate)){
				error = new ErrorVO(
				"uld.defaults.poolowners.msg.err.fromdatebeforecurrentdate");
		errors.add(error);
			}
			}
			//newly added for NCA bug fix ends
		}
		return errors;
	}

	/**
	 *
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ULDPoolOwnersForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if (form.getAirline() == null || form.getAirline().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterairline");
			errors.add(error);
		}

		if (form.getFlightNumber() == null
				|| form.getFlightNumber().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterflightno");
			errors.add(error);
		}

		if (form.getOrigin() == null || form.getOrigin().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterorigin");
			errors.add(error);
		}
		if (form.getDestination() == null
				|| form.getDestination().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.uldpoolowners.msg.err.enterdestn");
			errors.add(error);
		}
		return errors;
	}

	/**
	 *
	 * @param station
	 * @param companyCode
	 * @return
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			String companyCode) {
		log.entering("ListCommand", "validateAirportCodes");
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(companyCode, station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
		return errors;
	}

	/**
	 *
	 * @param form
	 * @param companyCode
	 * @param poolOwnerVO
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCodes(ULDPoolOwnersForm form,
			String companyCode, ULDPoolOwnerVO poolOwnerVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, form.getAirline().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				poolOwnerVO.setFlightCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}
}
