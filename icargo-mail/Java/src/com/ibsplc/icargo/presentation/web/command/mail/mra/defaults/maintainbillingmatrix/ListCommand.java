/*
 * ListCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";

	private static final String KEY_BILLED_SECTOR = "mailtracking.mra.billingSector";

	private static final String CONST_ENABLE = "ENABLE";

	private static final String IS_MODIFIED = "Y";

	private static final String BLG_MATRIX_ID_NULL = "mailtracking.mra.defaults.maintainblgmax.err.blgmtxidnull";

	private static final String BLG_MTX_NOTPRESENT = "mailtracking.mra.defaults.maintainbillingmatrix.matrixidnotpresent";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;

		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Map<String, String> billingMatrixStatusMap = new HashMap<String, String>();
		for (Collection<OneTimeVO> oneTimeVos : session.getOneTimeVOs()
				.values()) {
			for (OneTimeVO oneTimeVo : oneTimeVos) {
				if (STATUS_ONETIME.equals(oneTimeVo.getFieldType())) {
					billingMatrixStatusMap.put(oneTimeVo.getFieldValue(),
							oneTimeVo.getFieldDescription());
				}
			}
		}
		BillingMatrixVO billingMatrixVO = null;
		BillingMatrixFilterVO blgMatrixFilterVO = null;
		if(session.getBillingMatrixFilterVO()!=null){
		 blgMatrixFilterVO=session.getBillingMatrixFilterVO();
		form.setBlgMatrixID(blgMatrixFilterVO.getBillingMatrixId());
		log.log(Log.FINE, "BlgMatrixID.in form.", form.getBlgMatrixID());
		
		}
		else{
		 blgMatrixFilterVO = new BillingMatrixFilterVO();
		}
		blgMatrixFilterVO.setCompanyCode(companyCode);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors.addAll(setOneTimeValues(session));
		form.setIsModified("");
		
		if(form.getGpaCode()!=null &&(form.getGpaCode()).trim().length()>0){
			/*
				* Validate the GpaCode  and obtain the GpaCode
			*/
			errors=new ArrayList<ErrorVO>();
			 errors = validateGpaCode(form,
						logonAttributes);
			 log.log(Log.FINE, "errors", errors);
			if(errors!=null&& errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = LISTDETAILS_FAILURE;
				return;
				
			}
			else{
				
				blgMatrixFilterVO.setPoaCode(form.getGpaCode());
				
			}
			
		}
		
		if (form.getBlgMatrixID() != null
				&& form.getBlgMatrixID().trim().length() > 0) {
			blgMatrixFilterVO.setBillingMatrixId(form.getBlgMatrixID().trim()
					.toUpperCase());
			session.setBillingMatrixFilterVO(blgMatrixFilterVO);
			try {
				billingMatrixVO = new MailTrackingMRADelegate()
						.findBillingMatrixDetails(blgMatrixFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
		} else {
			ErrorVO errorVO = new ErrorVO(BLG_MATRIX_ID_NULL);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			log.log(Log.INFO, "Error", errorVO);
		}
		
		if (errors.size() == 0) {
			log.log(Log.FINE, "vo returned....", billingMatrixVO);
			if (billingMatrixVO != null) {
				billingMatrixVO.setBillingMatrixStatus(billingMatrixStatusMap
						.get(billingMatrixVO.getBillingMatrixStatus()));
				session.setBillingMatrixVO(billingMatrixVO);
				form.setBlgMatrixID(billingMatrixVO.getBillingMatrixId());
				form.setStatus(billingMatrixVO.getBillingMatrixStatus());
				form.setDescription(billingMatrixVO.getDescription());
				form.setValidFrom(billingMatrixVO.getValidityStartDate()
						.toDisplayDateOnlyFormat());
				form.setValidTo(billingMatrixVO.getValidityEndDate()
						.toDisplayDateOnlyFormat());
				form.setDisableActive("N");
				form.setIsModified(IS_MODIFIED);
			} else {
				billingMatrixVO = new BillingMatrixVO();
				if (form.getBlgMatrixID() != null
						&& form.getBlgMatrixID().trim().length() > 0) {
					billingMatrixVO.setBillingMatrixId(form.getBlgMatrixID()
							.trim().toUpperCase());
				}
				billingMatrixVO
						.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				form.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				session.setBillingLineDetails(null);
				session.setIndexMap(null);
				form.setDescription("");
				form.setValidFrom("");
				form.setValidTo("");
				form.setDisableActive("");
				log.log(Log.FINE, "No VOs returned");
				ErrorVO conformMessage = new ErrorVO(BLG_MTX_NOTPRESENT);
				errors.add(conformMessage);
			}
			session.setBillingMatrixVO(billingMatrixVO);
			log.exiting(CLASS_NAME, "execute");
			if (errors.size() > 0) {
				invocationContext.addAllError(errors);
			}
			form.setFormStatusFlag(CONST_ENABLE);
			if (form.getBlgMatrixID() != null
					&& form.getBlgMatrixID().trim().length() > 0) {
				form.setIdValue(form.getBlgMatrixID());
			}
			form
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			form.setDisableButton("Y");
			log.log(Log.FINE, "disablebutton", form.getDisableButton());
			invocationContext.target = LISTDETAILS_SUCCESS;
		} else {
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
		return;
	}

	/**
	 *
	 * @param session
	 * @return
	 */
	private Collection<ErrorVO> setOneTimeValues(
			MaintainBillingMatrixSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		/** setting OneTime hashmap to session */
		session
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);
		return errors;
	}
	
	/**
	 * Method to validate GpaCode
	 * @param BillingMatrixForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(BillingMatrixForm form,
			LogonAttributes logonAttributes) {
		
		String gpaCode=form.getGpaCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(gpaCode != null || ("".equals(gpaCode.trim()))){
			
//    	validate PA code
	  	log.log(Log.FINE, "Going To validate GPA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),gpaCode.toUpperCase());
					log.log(Log.FINE, "postalAdministrationVO",
							postalAdministrationVO);
					if(postalAdministrationVO == null) {
		  				Object[] obj = {gpaCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.mra.defaults.gpacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
}
