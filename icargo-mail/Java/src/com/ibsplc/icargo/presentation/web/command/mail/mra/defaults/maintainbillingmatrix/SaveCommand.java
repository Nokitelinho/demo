/*
 * SaveCommand.java Created on Dec 19, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2398
 * 
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking SaveCommand");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String DUPLICATE_EXISTS = "duplicate_exists";

	private static final String STA_NEW = "NEW";

	private static final String STA_NEW_VAL = "N";

	private static final String IS_MODIFIED = "Y";

	private static final String NO_BLGLINES = "mailtracking.mra.defaults.maintainbillingmatrix.nobillinglines";

	private static final String DATE_VALUE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.err.datevaluenull";

	private static final String DATE_VALUES_INVALID = "mailtracking.mra.defaults.maintainbillingmatrix.err.invaliddatevalues";

	private static final String DUP_BILLINGLINE = "mailtracking.mra.defaults.duplicatebillingline";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Map<String, String> billingMatrixStatusMap = new HashMap<String, String>();
		for (Collection<OneTimeVO> oneTimeVos : session.getOneTimeVOs()
				.values()) {
			for (OneTimeVO oneTimeVo : oneTimeVos) {
				if (STATUS_ONETIME.equals(oneTimeVo.getFieldType())) {
					billingMatrixStatusMap.put(oneTimeVo.getFieldValue(),
							oneTimeVo.getFieldDescription());
					billingMatrixStatusMap.put(oneTimeVo.getFieldDescription(),
							oneTimeVo.getFieldValue());
				}
			}
		}
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		BillingMatrixVO sessionVO = null;
		Collection<ErrorVO> errors = null;
		BillingMatrixVO formBlgMatrixVO = null;
		errors = validateForm(companyCode, form);
		boolean isError = true;
		int opflgCount=0;
		if (session.getBillingLineDetails() == null
				|| session.getBillingLineDetails().size() == 0) {
			ErrorVO err = new ErrorVO(NO_BLGLINES);
			errors.add(err);
		} else {
			Page<BillingLineVO> arr = session.getBillingLineDetails();
			log.log(Log.INFO, "arr-----", arr);
			for (BillingLineVO lineVO : arr) {
				
				/***
				 * @author a-3447 for Bug -26271 Starts 
				 */
				
				if(arr.size()>=1 && AbstractVO.OPERATION_FLAG_DELETE.equals(lineVO.getOperationFlag())){
					isError = false;	
				}
				
				/***
				 * @author a-3447 for Bug -26271 Ends 
				 */
				
				log.log(Log.INFO, "lineVO", lineVO.getOperationFlag());
				if (!AbstractVO.OPERATION_FLAG_DELETE.equals(lineVO
						.getOperationFlag())) {
					isError = false;
				}
			}
			
			if (isError) {
				ErrorVO err = new ErrorVO(NO_BLGLINES);
				errors.add(err);
			}
		}
		if (errors.size() == 0) {
			log.log(Log.INFO, "VO in session--->>", session.getBillingMatrixVO());
			if (session.getBillingMatrixVO() != null) {
				sessionVO = session.getBillingMatrixVO();
				if (IS_MODIFIED.equals(form.getIsModified())) {
					formBlgMatrixVO = populateBillingMatrixVO(form, session);
					if (!BillingMatrixVO.OPERATION_FLAG_INSERT.equals(session
							.getBillingMatrixVO().getOperationFlag())) {
						formBlgMatrixVO
								.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
						
						/***
						 * @author a-3447 for Bug -26271 Starts 
						 */
						
						if(session.getBillingLineDetails()!=null){
						for(BillingLineVO lineVo: session.getBillingLineDetails()){
							
							if("D".equals(lineVo.getOperationFlag())){
								opflgCount++;
							}
															
						}
						}
						if(opflgCount==session.getBillingLineDetails().size())
						{
							formBlgMatrixVO.setOperationFlag("D");	
							
						}
						
						
						/**added by 
						 * @author a-3447 for  Ends
						 * 
						 */
						
						
						
						/**
						 * Added for optimistic locking 
						 */
						formBlgMatrixVO.setLastUpdatedUser(session.getBillingMatrixVO().getLastUpdatedUser());
						formBlgMatrixVO.setLastUpdatedTime(session.getBillingMatrixVO().getLastUpdatedTime());
						
					}
					log.log(Log.INFO, "**********OPO flag--->", formBlgMatrixVO.getOperationFlag());
				} else {
					formBlgMatrixVO = populateBillingMatrixVO(form, session);
					formBlgMatrixVO
							.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
					log.log(Log.INFO, "**********OPO flag--->", formBlgMatrixVO.getOperationFlag());
				}
				log.log(Log.FINE, "Modified MatrixVO being saved.....",
						formBlgMatrixVO.getOperationFlag());
				if (STA_NEW.equals(form.getStatus())) {
					formBlgMatrixVO.setBillingMatrixStatus(STA_NEW_VAL);
				} else {
					formBlgMatrixVO
							.setBillingMatrixStatus(billingMatrixStatusMap
									.get(sessionVO.getBillingMatrixStatus()));
				}
				log.log(1, form.getFormStatusFlag());
			} else {
				log.log(1, "No vo in session");
			}
			try {
				log
						.log(Log.FINE,
								"<<---------------*****************---------------------------->>");
				log.log(Log.FINE,
						"The matrix vo to be saved/modified is---->>",
						formBlgMatrixVO);
				if (formBlgMatrixVO.getBillingLineVOs() != null) {
					for (BillingLineVO bilgLineVO : formBlgMatrixVO
							.getBillingLineVOs()) {
						log
								.log(
										Log.FINE,
										"Thebilling line vo to be saved/modified is---->>",
										bilgLineVO);
					}
				}
				log
						.log(Log.FINE,
								"<<---------------********************---------------------------->>");
				MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();

				/*
				 * To change status of Billing Matrix & the associated billing
				 * lines(if required)
				 */

				if (session.getBillingMatrixVO() != null) {
					if (session.getBillingMatrixVO().getStatusChanged()
							&& !AbstractVO.OPERATION_FLAG_INSERT
									.equals(formBlgMatrixVO.getOperationFlag())) {
						ArrayList<BillingMatrixVO> list = new ArrayList<BillingMatrixVO>();
						list.add(formBlgMatrixVO);
						log.log(Log.INFO, "List to be saved--->", list);
						delegate.saveBillingLineStatus(list, null);
					}
				}
				/*
				 * Saves the Billing Matrix being created or modified.
				 */
				delegate.saveBillingMatrix(formBlgMatrixVO);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
				log.log(1, "Error--------->***********");
				invocationContext.addError(new ErrorVO(DUP_BILLINGLINE));
				invocationContext.target = DUPLICATE_EXISTS;
				return;
			}
		} else {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		form.setIdValue(null);
		form.setBlgMatrixID(null);
		form.setDescription(null);
		form.setValidFrom(null);
		form.setValidTo(null);
		form.setStatus(null);
		// form.setPostalAdmin(null);
		form.setFormStatusFlag(null);
		form.setSelectedIndex(null);
		form.setLastPageNum("0");
		form.setDisplayPage("1");
		session.setBillingMatrixVO(null);
		session.setBillingLineDetails(null);
		session.setIndexMap(null);
		session.setOneTimeVOs(null);
		//session.removeBillingMatrixFilterVO();
		log.exiting(CLASS_NAME, "execute");
		form.setDisableActive("N");
		log.log(Log.FINE, "DisableActive", form.getDisableActive());
		ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.datasavedsuccesfully");
		errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
		errors.add(errorVO);
		invocationContext.addAllError(errors);
		form.setNotSave("N");
		invocationContext.target = SAVE_SUCCESS;
	}

	private BillingMatrixVO populateBillingMatrixVO(BillingMatrixForm form,
			MaintainBillingMatrixSession session) {
		BillingMatrixVO billingMatrixVO = new BillingMatrixVO();
		log.log(1, "In populate.......");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		billingMatrixVO.setCompanyCode(companyCode);
		if (form.getBlgMatrixID() != null
				&& form.getBlgMatrixID().trim().length() > 0) {
			billingMatrixVO.setBillingMatrixId(form.getBlgMatrixID().trim()
					.toUpperCase());
		}
		if (form.getDescription() != null
				&& form.getDescription().trim().length() > 0) {
			billingMatrixVO.setDescription(form.getDescription().trim());
		}
		if (form.getValidFrom() != null
				&& form.getValidFrom().trim().length() > 0) {
			billingMatrixVO.setValidityStartDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, false).setDate(form
					.getValidFrom().trim()));
		}
		if (form.getValidTo() != null && form.getValidTo().trim().length() > 0) {
			billingMatrixVO.setValidityEndDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, false).setDate(form
					.getValidTo().trim()));
		}
		if (session.getBillingLineDetails() != null
				&& session.getBillingLineDetails().size() > 0) {
			/** Added by Indu* */
			ArrayList<BillingLineVO> arrbillingLineVOs = new ArrayList<BillingLineVO>(
					session.getBillingLineDetails());
			int siz = arrbillingLineVOs.size();
			for (int i = 0; i < siz; i++) {
				arrbillingLineVOs.get(i).setBillingMatrixId(
						form.getBlgMatrixID());
			}
		for(BillingLineVO lineVo: session.getBillingLineDetails()){
			if(lineVo.getOperationFlag()!=null && session.getBillingLineDetails()!=null){
			if(("D").equals(lineVo.getOperationFlag()) && session.getBillingLineDetails().size()>0){
				billingMatrixVO.setOperationFlag("D");
			}
			
				else{
					
					billingMatrixVO.setOperationFlag(session.getBillingMatrixVO().getOperationFlag());
				}
			}
		}
		log.log(Log.INFO, "billingMatrixVO---", billingMatrixVO.getOperationFlag());
			/** Added by Indu ends* */
			billingMatrixVO.setBillingLineVOs(session.getBillingLineDetails());
			int count = 0;
			for (BillingLineVO billingLineVO : session.getBillingLineDetails()) {
				if (AbstractVO.OPERATION_FLAG_UPDATE.equals(billingLineVO
						.getBillingLineStatus())
						|| AbstractVO.OPERATION_FLAG_INSERT
								.equals(billingLineVO.getBillingLineStatus())
						|| AbstractVO.OPERATION_FLAG_DELETE
								.equals(billingLineVO.getBillingLineStatus())) {
					billingMatrixVO
							.setBillingMatrixStatus(AbstractVO.OPERATION_FLAG_UPDATE);
				}
				if (!AbstractVO.OPERATION_FLAG_DELETE.equals(billingLineVO
						.getBillingLineStatus())) {
					count++;
				}
				log.log(Log.INFO, "Line VOs are-------->>", billingLineVO);
			}
			billingMatrixVO.setTotalBillinglines(count);
			log.log(Log.INFO,
					"BillingLineVOs obtained from session are....---->>",
					session.getBillingLineDetails());
		} else {
			log.log(1, "No #### BillingLineVOs obtained from session are....");
			billingMatrixVO.setTotalBillinglines(0);
		}
		return billingMatrixVO;
	}

	/**
	 * 
	 * @param companyCode
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(String companyCode,
			BillingMatrixForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("validate form", "Error");
		if (form.getValidFrom() == null
				|| form.getValidFrom().trim().length() == 0
				|| form.getValidTo() == null
				|| form.getValidTo().trim().length() == 0) {
			ErrorVO error = new ErrorVO(DATE_VALUE_NULL);
			errors.add(error);
			log.log(Log.INFO, "Error", error.toString());
		} else {
			LocalDate startDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			startDate.setDate(form.getValidFrom());
			LocalDate endDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			endDate.setDate(form.getValidTo());
			if (endDate.before(startDate)) {
				ErrorVO error = new ErrorVO(DATE_VALUES_INVALID);
				errors.add(error);
				log.log(Log.INFO, "Error", error.toString());
			}
		}
		log.exiting("validate form", "Error");
		return errors;
	}
}
