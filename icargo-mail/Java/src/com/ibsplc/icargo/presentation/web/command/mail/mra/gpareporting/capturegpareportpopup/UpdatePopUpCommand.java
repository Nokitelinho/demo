/*
 * UpdatePopUpCommand.java Created on Feb 24, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Feb 24, 2007 a-2257 Created
 */
public class UpdatePopUpCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "UpdatePopUpCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	private static final String BLANK = "";

	/*
	 * Constant to identify create action
	 */
	private static final String ADD = "ADD";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";

	private static final String MAIL_SUBCLASS_LIKE_U = "U";

	private static final String MAIL_SUBCLASS_LC = "LC";

	private static final String MAIL_SUBCLASS_CP = "CP";

	/**
	 * 
	 * TODO Purpose Mar 11, 2007, a-2257
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		CaptureGPAReportSession session = (CaptureGPAReportSession) getScreenSession(
				MODULE_NAME, SCREENID);
		CaptureGPAReportForm form = (CaptureGPAReportForm) invocationContext.screenModel;

		session.setSelectedGPAReportingDetailsVO(updateFromPopUp(form, session
				.getSelectedGPAReportingDetailsVO()));
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
			
				errors = checkMandatory(session	.getSelectedGPAReportingDetailsVO(), form);
	
		if (errors != null && errors.size() > 0) {

			invocationContext.addAllError(errors);
		}

		log.log(Log.INFO, "session.getModifiedVOs()-->", session.getModifiedGPAReportingDetailsVOs());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * 
	 * TODO Purpose Mar 11, 2007, a-2257
	 * 
	 * @param form
	 * @param gpaReportingDetailsVO
	 * @return
	 */
	public GPAReportingDetailsVO updateFromPopUp(CaptureGPAReportForm form,
			GPAReportingDetailsVO gpaReportingDetailsVO) {

		log.entering(CLASS_NAME, "updateFromPopUp");

		boolean isGpaUpdated = false;

		LocalDate date = new LocalDate(getApplicationSession().getLogonVO()
				.getStationCode(), Location.STN, false);

		if (form.getDate() != null && form.getDate().trim().length() > 0) {

			log.log(Log.INFO, "form.getDate() not null");
			//log.log(Log.INFO, "form.getDate() "+form.getDate());
			//log.log(Log.INFO, "date in VO "+gpaReportingDetailsVO.getDsnDateForDisplay());
			//log.log(Log.INFO, "operation flg in VO "+gpaReportingDetailsVO.getOperationFlag());
			if (DateUtilities.isValidDate(form.getDate(), "dd-MMM-yyyy")) {

				date.setDate(form.getDate());
				//log.log(Log.INFO, "date from"+date.toDisplayFormat());
				//log.log(Log.INFO, "dsn date vo"+gpaReportingDetailsVO.getDsnDate().toDisplayFormat());
							  if(gpaReportingDetailsVO.getDsnDate()!=null){
								  if(!date.toDisplayFormat().equals(
											gpaReportingDetailsVO.getDsnDate().toDisplayFormat())){
									        isGpaUpdated = true;
								  		}
							  	}  
				
					
				gpaReportingDetailsVO.setDsnDate(date);
				

			}
		} else {
			gpaReportingDetailsVO.setDsnDate(null);
		}

		/**
		 * Setting date to String date field
		 */
		if (gpaReportingDetailsVO.getDsnDate() != null) {
			gpaReportingDetailsVO.setDsnDateForDisplay(gpaReportingDetailsVO
					.getDsnDate().toDisplayDateOnlyFormat());
			isGpaUpdated = true;
		}

		// if (form.getMailBag() != null
		// && form.getMailBag().trim().length() > 0) {
		// log.log(Log.INFO,"form.getMailBag() not null");
		// if
		// (!form.getMailBag().equals(gpaReportingDetailsVO.getBillingBasis()))
		// {
		// gpaReportingDetailsVO.setBillingBasis(form.getMailBag().toUpperCase());
		// isGpaUpdated = true;
		// }
		// }else{
		// gpaReportingDetailsVO.setBillingBasis("");
		// }

		if (form.getOriginOE() != null
				&& form.getOriginOE().trim().length() > 0) {
			log
					.log(Log.INFO, "form.getOriginOE() not null", form.getOriginOE());
			if (!form.getOriginOE().equals(
					gpaReportingDetailsVO.getOriginOfficeExchange())) {
				log.log(Log.INFO, "form.getOriginOE() not equal");
				gpaReportingDetailsVO.setOriginOfficeExchange(form
						.getOriginOE().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setOriginOfficeExchange("");
		}

		if (form.getDestinationOE() != null
				&& form.getDestinationOE().trim().length() > 0) {
			log.log(Log.INFO, "form.getDestinationOE() not null");
			if (!form.getDestinationOE().equals(
					gpaReportingDetailsVO.getDestinationOfficeExchange())) {
				gpaReportingDetailsVO.setDestinationOfficeExchange(form
						.getDestinationOE().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setDestinationOfficeExchange("");
		}
		if (form.getMailCategory() != null
				&& form.getMailCategory().trim().length() > 0) {
			log.log(Log.INFO, "form.getMailCategory() not null");
			if (!form.getMailCategory().equals(
					gpaReportingDetailsVO.getMailCategory())) {
				gpaReportingDetailsVO.setMailCategory(form.getMailCategory()
						.toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setMailCategory("");
		}

		if (form.getMailSubClass() != null
				&& form.getMailSubClass().trim().length() > 0) {
			log.log(Log.INFO, "form.getActualMailSubClass() not null");
			if (!form.getMailSubClass().equals(
					gpaReportingDetailsVO.getActualMailSubClass())) {
				gpaReportingDetailsVO.setActualMailSubClass(form
						.getMailSubClass().toUpperCase());
				if (form.getMailSubClass().toUpperCase().startsWith(
						MAIL_SUBCLASS_LIKE_U)) {
					gpaReportingDetailsVO.setMailSubClass(MAIL_SUBCLASS_LC);
				} else {
					gpaReportingDetailsVO.setMailSubClass(MAIL_SUBCLASS_CP);
				}
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setActualMailSubClass("");
		}

		if (form.getYear() != null && form.getYear().trim().length() > 0) {
			log.log(Log.INFO, "form.getYear() not null");
			if (!form.getYear().equals(gpaReportingDetailsVO.getYear())) {
				gpaReportingDetailsVO.setYear(form.getYear().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setYear("");
		}

		if (form.getDsn() != null && form.getDsn().trim().length() > 0) {
			log.log(Log.INFO, "form.getDsn() not null");
			if (!form.getDsn().equals(gpaReportingDetailsVO.getDsnNumber())) {
				gpaReportingDetailsVO.setDsnNumber(form.getDsn().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setDsnNumber("");
		}

		if (form.getReceptacleSerialNum() != null
				&& form.getReceptacleSerialNum().trim().length() > 0) {
			log.log(Log.INFO, "form.getReceptacleSerialNum() not null");
			if (!form.getReceptacleSerialNum().equals(
					gpaReportingDetailsVO.getReceptacleSerialNumber())) {
				gpaReportingDetailsVO.setReceptacleSerialNumber(form
						.getReceptacleSerialNum().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setReceptacleSerialNumber("");
		}
		if (form.getHighestNumberedRec() != null
				&& form.getHighestNumberedRec().trim().length() > 0) {
			log.log(Log.INFO, "form.getHighestNumberedRec() not null");
			if (!form.getHighestNumberedRec().equals(
					gpaReportingDetailsVO.getHighestNumberedReceptacle())) {
				gpaReportingDetailsVO.setHighestNumberedReceptacle(form
						.getHighestNumberedRec().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setHighestNumberedReceptacle("");
		}
		if (form.getRegisteredOrInsuredInd() != null
				&& form.getRegisteredOrInsuredInd().trim().length() > 0) {
			log.log(Log.INFO, "form.getRegisteredOrInsuredInd() not null");
			if (!form.getRegisteredOrInsuredInd().equals(
					gpaReportingDetailsVO.getRegisteredOrInsuredIndicator())) {
				gpaReportingDetailsVO.setRegisteredOrInsuredIndicator(form
						.getRegisteredOrInsuredInd().toUpperCase());
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setRegisteredOrInsuredIndicator("");
		}

		if (form.getNoOfMailBag() != null
				&& form.getNoOfMailBag().trim().length() > 0) {
			log.log(Log.INFO, "form.getNoOfMailBag() not null");
			if (Integer.parseInt(form.getNoOfMailBag()) != gpaReportingDetailsVO
					.getNoOfMailBags()) {
				gpaReportingDetailsVO.setNoOfMailBags(Integer.parseInt(form
						.getNoOfMailBag()));
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setNoOfMailBags(0);
		}

		if (form.getWeight() != null && form.getWeight().trim().length() > 0) {
			log.log(Log.INFO, "form.getWeight() not null");
			if (Double.parseDouble(form.getWeight()) != gpaReportingDetailsVO
					.getWeight()) {
				gpaReportingDetailsVO.setWeight(Double.parseDouble(form
						.getWeight()));
				isGpaUpdated = true;
			}
			// weight=form.getWeight();
		} else {
			gpaReportingDetailsVO.setWeight(0);
		}
		if (form.getRate() != null && form.getRate().trim().length() > 0) {
			log.log(Log.INFO, "form.getRate() not null");
			if (Double.parseDouble(form.getRate()) != gpaReportingDetailsVO
					.getRate()) {
				gpaReportingDetailsVO.setRate(Double
						.parseDouble(form.getRate()));
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setRate(0);
		}
		if (form.getAmount() != null && form.getAmount().trim().length() > 0) {
			try{
			if(form.getCurrencyCode()!=null &&form.getCurrencyCode().trim().length()>0 ){
				Money amt=CurrencyHelper.getMoney(form.getCurrencyCode());
				
			log.log(Log.INFO, "form.getAmount() not null");
			if(gpaReportingDetailsVO.getAmount()!=null){
			if (Double.parseDouble(form.getAmount()) != gpaReportingDetailsVO
					.getAmount().getRoundedAmount()) {
				amt.setAmount(Double.parseDouble(form.getAmount()));
				gpaReportingDetailsVO.setAmount(amt);
				isGpaUpdated = true;
			}
			}else{
				amt.setAmount(Double.parseDouble(form.getAmount()));
				gpaReportingDetailsVO.setAmount(amt);
				isGpaUpdated = true;
			}
			}
			}catch(CurrencyException e){
				e.getErrorCode();
			}
			
			
		} else {
			gpaReportingDetailsVO.setAmount(null);
		}
		if (form.getTax() != null && form.getTax().trim().length() > 0) {
			log.log(Log.INFO, "form.getOriginOE() not null");
			if (Double.parseDouble(form.getTax()) != gpaReportingDetailsVO
					.getTax()) {
				gpaReportingDetailsVO.setTax(Double.parseDouble(form.getTax()));
				isGpaUpdated = true;
			}
		} else {
			gpaReportingDetailsVO.setTax(0);
		}
		if (form.getTotal() != null && form.getTotal().trim().length() > 0) {
			log.log(Log.INFO, "form.getTotal() not null");
			try{
			if(form.getCurrencyCode()!=null &&form.getCurrencyCode().trim().length()>0 ){
				Money totalAmt=CurrencyHelper.getMoney(form.getCurrencyCode());
				
			if(gpaReportingDetailsVO
					.getTotal()!=null){
			if (Double.parseDouble(form.getTotal()) != gpaReportingDetailsVO
					.getTotal().getRoundedAmount()) {
				totalAmt.setAmount(Double.parseDouble(form.getTotal()));
				gpaReportingDetailsVO.setTotal(totalAmt);
				isGpaUpdated = true;
			}
			}else{
				totalAmt.setAmount(Double.parseDouble(form.getTotal()));
				gpaReportingDetailsVO.setTotal(totalAmt);
				isGpaUpdated = true;
			}
		}
			}catch(CurrencyException e){
				e.getErrorCode();
			}
			
		} else {
			gpaReportingDetailsVO.setTax(0);
		}

		if (isGpaUpdated) {
			if (!GPAReportingDetailsVO.OPERATION_FLAG_INSERT
					.equals(gpaReportingDetailsVO.getOperationFlag())) {
				gpaReportingDetailsVO
						.setOperationFlag(GPAReportingDetailsVO.OPERATION_FLAG_UPDATE);
			}
		}

		if (form.getPopUpStatusFlag() != null
				&& form.getPopUpStatusFlag().trim().length() > 0) {

			log.log(Log.FINE, "Inside first if");

			if (ADD.equals(form.getPopUpStatusFlag())) {

				log.log(Log.FINE, "Inside sec if");

				/*
				 * Forming mailIdr
				 */
				
				if(gpaReportingDetailsVO.getBillingBasis()==null){
				StringBuffer mailBag = new StringBuffer("");

				if (gpaReportingDetailsVO.getOriginOfficeExchange() != null
						&& gpaReportingDetailsVO.getOriginOfficeExchange()
								.trim().length() > 0) {
					mailBag.append(gpaReportingDetailsVO
							.getOriginOfficeExchange());
				}
				if (gpaReportingDetailsVO.getDestinationOfficeExchange() != null
						&& gpaReportingDetailsVO.getDestinationOfficeExchange()
								.trim().length() > 0) {
					mailBag.append(gpaReportingDetailsVO
							.getDestinationOfficeExchange());
				}
				if (gpaReportingDetailsVO.getMailCategory() != null
						&& gpaReportingDetailsVO.getMailCategory().trim()
								.length() > 0) {
					mailBag.append(gpaReportingDetailsVO.getMailCategory());
				}
				if (gpaReportingDetailsVO.getMailSubClass() != null
						&& gpaReportingDetailsVO.getMailSubClass().trim()
								.length() > 0) {
					mailBag.append(gpaReportingDetailsVO.getMailSubClass());
				}
				if (gpaReportingDetailsVO.getYear() != null
						&& gpaReportingDetailsVO.getYear().trim().length() > 0) {
					mailBag.append(gpaReportingDetailsVO.getYear());
				}
				if (gpaReportingDetailsVO.getDsnNumber() != null
						&& gpaReportingDetailsVO.getDsnNumber().trim().length() > 0) {
					mailBag.append(gpaReportingDetailsVO.getDsnNumber());
				}

				if (form.getBasistype() != null
						&& form.getBasistype().trim().length() > 0) {

					if ("M".equals(form.getBasistype())) {

						log.log(Log.INFO, "inside add");
						if (gpaReportingDetailsVO.getReceptacleSerialNumber() != null
								&& gpaReportingDetailsVO
										.getReceptacleSerialNumber().trim()
										.length() > 0) {
							mailBag.append(gpaReportingDetailsVO
									.getReceptacleSerialNumber());
						}
						if (gpaReportingDetailsVO
								.getHighestNumberedReceptacle() != null
								&& gpaReportingDetailsVO
										.getHighestNumberedReceptacle().trim()
										.length() > 0) {
							mailBag.append(gpaReportingDetailsVO
									.getHighestNumberedReceptacle());
						}
						if (gpaReportingDetailsVO
								.getRegisteredOrInsuredIndicator() != null
								&& gpaReportingDetailsVO
										.getRegisteredOrInsuredIndicator()
										.trim().length() > 0) {
							mailBag.append(gpaReportingDetailsVO
									.getRegisteredOrInsuredIndicator());
						}
						if (gpaReportingDetailsVO.getWeight() != 0) {
							// mailBag.append(gpaReportingDetailsVO.getWeight());
							String weightString = getWeightString(gpaReportingDetailsVO
									.getWeight());

							mailBag.append(weightString);
						}
					}
				}

				log.log(Log.INFO, "mailBag", mailBag);
				gpaReportingDetailsVO.setBillingBasis(mailBag.toString());

				}
				
				log.log(Log.INFO, "Billing Basis--", gpaReportingDetailsVO.getBillingBasis());
			}
			
		}

		log.exiting(CLASS_NAME, "updateFromPopUp");
		return gpaReportingDetailsVO;
	}

	/**
	 * 
	 * TODO Purpose Mar 12, 2007, a-2257
	 * 
	 * @param gpaReportingDetailsVO
	 * @param form
	 * @return
	 */
	public Collection<ErrorVO> checkMandatory(
			GPAReportingDetailsVO gpaReportingDetailsVO,
			CaptureGPAReportForm form) {
		log.entering(CLASS_NAME, "checkMandatory");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if (gpaReportingDetailsVO.getDsnDate() == null) {

			log.log(Log.INFO,
					"**************Inside dsnDate Error**************");
			error = new ErrorVO(
					"mailtraking.mra.gpareport.capturegpareportpopup.err.dsndate.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (form.getBasistype() != null
				&& form.getBasistype().trim().length() > 0) {
			log.log(Log.INFO, "form.getBasisType not null");
			if (("M").equals(form.getBasistype())) {
				log.log(Log.INFO, "form.getBasisType = M**********");

				if (gpaReportingDetailsVO.getReceptacleSerialNumber() == null
						|| (BLANK.equals(gpaReportingDetailsVO
								.getReceptacleSerialNumber()))) {

					log.log(Log.INFO,
							"**************Inside 111 Error**************");
					error = new ErrorVO(
							"mailtraking.mra.gpareport.capturegpareportpopup.rsn.mandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

				if (gpaReportingDetailsVO.getHighestNumberedReceptacle() == null
						|| (BLANK.equals(gpaReportingDetailsVO
								.getHighestNumberedReceptacle()))) {

					log.log(Log.INFO,
							"**************Inside 22 Error**************");
					error = new ErrorVO(
							"mailtraking.mra.gpareport.capturegpareportpopup.hn.mandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

				if (gpaReportingDetailsVO.getRegisteredOrInsuredIndicator() == null
						|| (BLANK.equals(gpaReportingDetailsVO
								.getRegisteredOrInsuredIndicator()))) {

					log.log(Log.INFO,
							"**************Inside 33 Error**************");
					error = new ErrorVO(
							"mailtraking.mra.gpareport.capturegpareportpopup.ri.mandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

			}

			if (gpaReportingDetailsVO.getOriginOfficeExchange() == null
					|| (BLANK.equals(gpaReportingDetailsVO
							.getOriginOfficeExchange()))) {

				log.log(Log.INFO, "**************Inside 2 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.originofficeofexch.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getDestinationOfficeExchange() == null
					|| (BLANK.equals(gpaReportingDetailsVO
							.getDestinationOfficeExchange()))) {

				log.log(Log.INFO, "**************Inside 3 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.destofficeofexch.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getMailCategory() == null
					|| (BLANK.equals(gpaReportingDetailsVO.getMailCategory()))) {

				log.log(Log.INFO, "**************Inside 4 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.mailcategory.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getActualMailSubClass() == null
					|| (BLANK.equals(gpaReportingDetailsVO
							.getActualMailSubClass()))) {

				log.log(Log.INFO, "**************Inside 5 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.mailsubclass.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getYear() == null
					|| (BLANK.equals(gpaReportingDetailsVO.getYear()))) {

				log.log(Log.INFO, "**************Inside 6 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.year.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getDsnNumber() == null
					|| (BLANK.equals(gpaReportingDetailsVO.getDsnNumber()))) {

				log.log(Log.INFO, "**************Inside 7 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.dsn.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (gpaReportingDetailsVO.getWeight() == 0) {

				log.log(Log.INFO, "**************Inside 8 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.weight.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

			if (gpaReportingDetailsVO.getNoOfMailBags() == 0) {

				log
						.log(Log.INFO,
								"**************Inside 10 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.noofmailbags.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			/*if (gpaReportingDetailsVO.getAmount() == null) {

				log
						.log(Log.INFO,
								"**************Inside 11 Error**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareportpopup.amount.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}*/
		}
		log.exiting(CLASS_NAME, "checkMandatory");
		return errors;
	}

	/**
	 * 
	 * TO get 4 char weight Sting to append to mail Idr Apr 17, 2007, a-2257
	 * 
	 * @param weight
	 * @return
	 */
	public String getWeightString(double weight) {
		log.entering(CLASS_NAME, "getWeightString");
		// double f = 412.4;
		String formatString = "000.0";

		String weightString = "";

		Format format = NumberFormat.getNumberInstance();

		((DecimalFormat) format).applyPattern(formatString);

		weightString = format.format(weight).replace(".", "");
		log.log(Log.INFO, "weightString****", weightString);
		log.exiting(CLASS_NAME, "getWeightString");
		return weightString;
	}
}
