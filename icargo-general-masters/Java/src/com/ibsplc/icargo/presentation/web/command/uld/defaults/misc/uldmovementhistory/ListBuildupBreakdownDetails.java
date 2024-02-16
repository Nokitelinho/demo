/*
 * ListBuildupBreakdownDetails.java Created on  10 June,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */ 
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
//import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
//import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
//import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * This command class is invoked on the 
 * 	list button the ULDMovementHistory screen
 * 
 * @author A-3093
 */
public class ListBuildupBreakdownDetails extends BaseCommand {

	private Log log = LogFactory.getLogger("ListDamageReportCommand");

	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";

	private static final String SCREENID = "uld.defaults.misc.listuldmovement";

	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		ListULDMovementForm form = (ListULDMovementForm) invocationContext.screenModel;
		ListULDMovementSession session = getScreenSession(MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 ListULDMovementSession listUldMovementSession = 
	 			getScreenSession(MODULE_NAME, SCREENID);
		ULDMovementFilterVO filterVO = listUldMovementSession.getUldMovementFilterVO();
		if(filterVO ==null){
			filterVO = new ULDMovementFilterVO();
		}
		
		//errors = validateForm(form, session, companyCode);
		
		filterVO.setCompanyCode(companyCode);
		filterVO.setUldNumber(form.getUldNumber().toUpperCase());

		log.log(Log.INFO, "form returned ---->", form);
		session.setUldMovementFilterVO(filterVO);
		
		/*if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			session.setOperationalULDAuditVO(null);
			invocationContext.target = LIST_FAILURE;
			return;
		}*/

		Page<OperationalULDAuditVO> operationalULDAuditVO = new Page<OperationalULDAuditVO>(
				new ArrayList<OperationalULDAuditVO>(), 0, 0, 0, 0, 0, false);
		
		String displayPage = null;

		displayPage = form.getDisplayPage();

		if ("1".equals(form.getDisplayPageFlag())) {
			displayPage = "1";
			form.setDisplayPageFlag("");
		}
		log.log(Log.INFO, "form.getDisplayPage()--------- ", form.getDisplayPage());
		int pageNumber = Integer.parseInt(displayPage);
		filterVO.setPageNumber(pageNumber);
				
		ErrorVO error = null;
		/*if(form.getUldNumber()==null ||
				form.getUldNumber().trim().length() == 0)
		{
			error = new ErrorVO("uld.defaults.listuldmovement.uldno.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/
		//ULDValidationVO uldValidationVO = new ULDValidationVO();
		try {

			operationalULDAuditVO = new ULDDefaultsDelegate()
					.listBuildupBreakdowndetails(filterVO);
			//uldValidationVO = new ULDDefaultsDelegate().validateULD(companyCode,form.getUldNumber().toUpperCase());

			log.log(Log.INFO, "repairDetailVOs returned ---->",
					operationalULDAuditVO);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage(); 
			
		}
		/*if(uldValidationVO == null){
			//Object[] obj = { "uldValidationVO" };
			
			ErrorVO err = new ErrorVO(
			"uld.defaults.uldintmvthistory.msg.err.validuld"); 
				errors.add(err);
				invocationContext.addAllError(errors);
				session.setOperationalULDAuditVO(null);
				invocationContext.target = LIST_FAILURE;
				return;
		}*/
		if(operationalULDAuditVO==null || operationalULDAuditVO.size()== 0)
		{
			ErrorVO err = new ErrorVO(
			"uld.defaults.nouldmovementbuiltupbreakdowndetailsexists"); 
				errors.add(err);
				invocationContext.addAllError(errors);
				session.setOperationalULDAuditVO(null);
				invocationContext.target = LIST_FAILURE;
				return;
		}
		session.setOperationalULDAuditVO(operationalULDAuditVO);
		
		
		log.log(Log.INFO, "session returned ---->", session.getOperationalULDAuditVO());
		invocationContext.target = LIST_SUCCESS;
	}
	
	

}
