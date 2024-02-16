/*
 * AddExtraULDCommand.java Created on AUG 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to ADD Extra ULD
 * 
 * @author A-1862
 */
public class AddExtraULDCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String ADD_SUCCESS = "addRow_success";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddExtraULDCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);

		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.FINE,
				"\n\n\n\n generateSCMSession.getExtraUld() BEFORE ADD ---> ",
				generateSCMSession.getSystemStock());
		ArrayList<ULDVO> extraULDs = generateSCMSession.getSystemStock() != null ? new ArrayList<ULDVO>(generateSCMSession.getSystemStock())
				: new ArrayList<ULDVO>();

		log.log(Log.FINE, "\n\n\n\n extraULDs---> ", extraULDs);
		if (extraULDs != null && extraULDs.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n INSIDE LOOP ");
			String[] extraULDForm = generateSCMForm.getExtrauld();

			int index = 0;
			for (ULDVO uldVO : extraULDs) {
				uldVO.setUldNumber(extraULDForm[index].toUpperCase());
				index++;
			}
		}

		log.log(Log.FINE, "\n\n\n\n extraULDs BEFORE ADD AFTER UPDATE   ---> ",
				extraULDs);
		Collection<String> invalidUlds = null;

		invalidUlds = validateULDFormat(companyCode, extraULDs);
		ErrorVO error = null;
		
		if (invalidUlds != null &&  invalidUlds.size() > 0) {
			int size=invalidUlds.size();
			for (int i = 0; i < size; i++) {
				error = new ErrorVO(
						"uld.defaults.ucminout.msg.err.invaliduldformat",
						new Object[] { ((ArrayList<String>) invalidUlds).get(i) });
				errors.add(error);
			}

			invocationContext.addAllError(errors);
			Page<ULDVO> ULDpage = new Page<ULDVO>(extraULDs,0,0,0,0,0,false);
			generateSCMSession.setSystemStock(ULDpage);
			invocationContext.target = ADD_SUCCESS;
			return;
		}
		errors=checkDuplicateULDs(extraULDs);
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=ADD_SUCCESS;
			return;
		}
		ULDVO uldVO = new ULDVO();
		uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
		uldVO.setUldNumber("");
		extraULDs.add(uldVO);

		log.log(Log.FINE, "\n\n\n\n extraULDs AFETR ADD  ---> ", extraULDs);
		Page<ULDVO> ULDpage = new Page<ULDVO>(extraULDs,0,0,0,0,0,false);
		generateSCMSession.setSystemStock(ULDpage);
		invocationContext.target = ADD_SUCCESS;

	}

	/**
	 * 
	 * @param companyCode
	 * @param uldVOs
	 * @return
	 */
	public Collection<String> validateULDFormat(String companyCode,
			Collection<ULDVO> uldVOs) {
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<String> uldNumbers = new ArrayList<String>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		for (ULDVO uldVO : uldVOs) {
			uldNumbers.add(uldVO.getUldNumber());
		}
		Collection<String> invalidUlds = null;
		try {
			invalidUlds = delegate.validateMultipleULDFormats(companyCode,
					uldNumbers);
		} catch (BusinessDelegateException ex) {
			log.log(Log.FINE, "\n\n\ninside handle delegatwe exception");
			ex.getMessage();
			errors = handleDelegateException(ex);
		}
		return invalidUlds;

	}
	/**
	 * 
	 * @param uldVOs
	 * @return
	 */
	private  Collection<ErrorVO> checkDuplicateULDs(Collection<ULDVO> uldVOs) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		for (ULDVO uldVO1 : uldVOs) {
			int noOfOccurance=0;
			String uldNumber = uldVO1.getUldNumber(); 
			for (ULDVO uldVO2 : uldVOs) {
					if(uldVO2.getUldNumber().equals(uldNumber)){
						noOfOccurance++;
					}
					if(noOfOccurance>1){
						ErrorVO error=new ErrorVO("uld.defaults.messaging.generatescm.duplicateuldsexist");
						errors.add(error);
						return errors;
					}
			}
		}
		return errors;
	}

}
