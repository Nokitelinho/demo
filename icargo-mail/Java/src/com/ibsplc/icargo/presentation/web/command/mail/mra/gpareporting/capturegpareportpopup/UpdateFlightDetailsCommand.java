/*
 * UpdateFlightDetailsCommand.java Created on Feb 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Feb 27, 2007			a-2257		Created
 */
public class UpdateFlightDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "UpdateFlightDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";

	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		CaptureGPAReportSession session =
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);
		CaptureGPAReportForm form =
			(CaptureGPAReportForm)invocationContext.screenModel;

		GPAReportingDetailsVO gpaReportingDetailsVO = session.getSelectedGPAReportingDetailsVO();

		Collection<GPAReportingFlightDetailsVO> gpaFlightDetailsVOs
					= gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs();
		String[] carriercode = form.getFlightCarrierCode();
		String[] fltno = form.getFlightNumber();
		String[] carriageFrm = form.getCarriageFrom();
		String[] carriageTo = form.getCarriageTo();

		int length=0;

		if(gpaFlightDetailsVOs!=null && gpaFlightDetailsVOs.size()>0){
			for(GPAReportingFlightDetailsVO fltDetailsVO : gpaFlightDetailsVOs){

				if(!GPAReportingFlightDetailsVO.OPERATION_FLAG_DELETE.equals(fltDetailsVO.getOperationFlag())){
				log.log(Log.FINE, "updating the fltDetailsVO");
				if(carriercode[length] != null
					&& carriercode[length].trim().length() > 0) {
					log.log(Log.FINE, "carriercode", carriercode, length);
					if (!carriercode[length]
							.equalsIgnoreCase(fltDetailsVO.getFlightCarrierCode())) {
						fltDetailsVO.setFlightCarrierCode(carriercode[length].toUpperCase());
						if(!GPAReportingFlightDetailsVO.OPERATION_FLAG_INSERT.equals(fltDetailsVO.getOperationFlag())){
							fltDetailsVO.setOperationFlag(GPAReportingFlightDetailsVO.OPERATION_FLAG_UPDATE);
						}
					}
				}

				if(fltno[length] != null
					&& fltno[length].trim().length() > 0) {
					log.log(Log.FINE, "fltno[length]", fltno, length);
					if (!fltno[length]
							.equalsIgnoreCase(fltDetailsVO.getFlightNumber())) {
						fltDetailsVO.setFlightNumber(fltno[length]);
						if(!GPAReportingFlightDetailsVO.OPERATION_FLAG_INSERT.equals(fltDetailsVO.getOperationFlag())){
							fltDetailsVO.setOperationFlag(GPAReportingFlightDetailsVO.OPERATION_FLAG_UPDATE);
						}
					}
				}

				if(carriageFrm[length] != null
					&& carriageFrm[length].trim().length() > 0) {
					log.log(Log.FINE, "carriageFrm[length]", carriageFrm,
							length);
					if (!carriageFrm[length]
							.equalsIgnoreCase(fltDetailsVO.getCarriageFrom())) {
						fltDetailsVO.setCarriageFrom(carriageFrm[length].toUpperCase());
						if(!GPAReportingFlightDetailsVO.OPERATION_FLAG_INSERT.equals(fltDetailsVO.getOperationFlag())){
							fltDetailsVO.setOperationFlag(GPAReportingFlightDetailsVO.OPERATION_FLAG_UPDATE);
						}
					}
				}

				if(carriageTo[length] != null
					&& carriageTo[length].trim().length() > 0) {
					log.log(Log.FINE, "carriageTo[length]", carriageTo, length);
					if (!carriageTo[length]
							.equalsIgnoreCase(fltDetailsVO.getCarriageTo())) {
						fltDetailsVO.setCarriageTo(carriageTo[length].toUpperCase());
						if(!GPAReportingFlightDetailsVO.OPERATION_FLAG_INSERT.equals(fltDetailsVO.getOperationFlag())){
							fltDetailsVO.setOperationFlag(GPAReportingFlightDetailsVO.OPERATION_FLAG_UPDATE);
						}
					}
				}

				length++;
				}
			}
			
			Collection<ErrorVO> errors = validateDetails(gpaFlightDetailsVOs);
			invocationContext.addAllError(errors);
		}
		
		log.log(Log.FINE, "final gpaFlightDetailsVOs", gpaFlightDetailsVOs);
		gpaReportingDetailsVO.setGpaReportingFlightDetailsVOs(gpaFlightDetailsVOs);
		invocationContext.target =ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		return;

	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 12, 2007, a-2257
	 * @param gpaFlightDetailsVOs
	 * @return
	 */
	private Collection<ErrorVO> validateDetails(Collection<GPAReportingFlightDetailsVO> gpaFlightDetailsVOs) {
		log.entering(CLASS_NAME, "validateDetails");
		
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();		
		String companyCode = logonAttributes.getCompanyCode();			
		
		for(GPAReportingFlightDetailsVO gpaReptFltDetailsVO : gpaFlightDetailsVOs){
			if(gpaReptFltDetailsVO.getCarriageFrom()==null
					|| gpaReptFltDetailsVO.getCarriageFrom().trim().length()==0){
				log.log(Log.INFO,"getCarriageFrom null");
				
				errors.add(new ErrorVO("mailtracking.mra.gpareporting.capturegpareportpopup.carriagefrommandatory"));
			}else{
				log.log(Log.INFO,"getCarriageFrom not null");
					if(validateCarriageFrom(companyCode, gpaReptFltDetailsVO.getCarriageFrom())!=null){
						errors.add(validateCarriageFrom(companyCode, gpaReptFltDetailsVO.getCarriageFrom()));
					}
				
			}
			if(gpaReptFltDetailsVO.getCarriageTo()==null
					|| gpaReptFltDetailsVO.getCarriageTo().trim().length()==0){
				log.log(Log.INFO,"getCarriageTo null");
				
				errors.add(new ErrorVO("mailtracking.mra.gpareporting.capturegpareportpopup.carriagetomandatory"));
			}else{
				log.log(Log.INFO,"getCarriageTo not null");
				if(validateCarriageTo(companyCode, gpaReptFltDetailsVO.getCarriageTo())!=null){
					errors.add(validateCarriageTo(companyCode, gpaReptFltDetailsVO.getCarriageTo()));
				}
			}
		}
		log.exiting(CLASS_NAME, "validateDetails");
		return errors;
	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 12, 2007, a-2257
	 * @param companyCode
	 * @param carriageFrom
	 * @return
	 */
	private ErrorVO validateCarriageFrom(String companyCode, String carriageFrom) {
		log.entering(CLASS_NAME, "validateCarriageFrom");
		
		ErrorVO errorVO=null;
		
		AreaValidationVO carriageFromVO = null;
		try {	
			carriageFromVO = new AreaDelegate().validateStation(companyCode, carriageFrom);
		
		} catch (BusinessDelegateException businessDelegateException) {					
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);					
		}
		if(carriageFromVO==null){					
			
			errorVO =new ErrorVO("mailtracking.mra.gpareporting.capturegpareportpopup.invalidcarriagefrom");
		}
		log.exiting(CLASS_NAME, "validateCarriageFrom");
		return errorVO;
	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 12, 2007, a-2257
	 * @param companyCode
	 * @param carriageTo
	 * @return
	 */
	private ErrorVO validateCarriageTo(String companyCode, String carriageTo) {
		log.entering(CLASS_NAME, "validateCarriageTo");
		
		ErrorVO errorVO=null;
		
		AreaValidationVO carriageToVO = null;
		try {	
			carriageToVO = new AreaDelegate().validateStation(companyCode, carriageTo);
		
		} catch (BusinessDelegateException businessDelegateException) {					
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);					
		}
		if(carriageToVO==null){					
			
			errorVO =new ErrorVO("mailtracking.mra.gpareporting.capturegpareportpopup.invalidcarriageto");
		}
		log.exiting(CLASS_NAME, "validateCarriageTo");
		return errorVO;
	}
}
