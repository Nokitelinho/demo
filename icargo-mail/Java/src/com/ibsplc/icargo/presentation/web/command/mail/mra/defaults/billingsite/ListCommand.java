/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingsite.ListCommand.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingsite.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class ListCommand extends BaseCommand{
	
	/** The log. */
	private Log log = LogFactory.getLogger("Billing Site Master ListCommand");
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/** The Constant SCREEN_ID. */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.billingsitemaster";
	
	/** The Constant ACTION_SUCCESS. */
	private static final String ACTION_SUCCESS = "screenload_success";
	
	private static final String NO_RESULTS_FOUND="mailtracking.mra.defaults.billingsite.noresultsfound";
	
	private static final String ENTER_BILLINGSITECODE="mailtracking.mra.defaults.billingsite.enterbillingsitecode";
	
	private static final String MRA = "MRA";
	
	private static final String LIST_FLAG = "List";
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-5219 on 17-Nov-2013
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	
		log.entering("ScreenLoadCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		BillingSiteMasterForm billingSiteMasterForm = (BillingSiteMasterForm) invocationContext.screenModel;
		BillingSiteSession billingSiteSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		BillingSiteFilterVO billingSiteFilterVO=new BillingSiteFilterVO();
		
		//added the if condition for CR ICRD-76222(for Billing Site master,while clicking Process Task from Message Inbox screen, 
		//system should navigate to Billing Site Master screen and List with the billing site code.
		if("WRKFLWMSGINB".equals(billingSiteSession.getFromParentScreen()) && billingSiteSession.getBillingSiteVO() != null){
			billingSiteFilterVO.setBillingSiteCode(billingSiteSession.getBillingSiteVO().getBillingSiteCode());
			billingSiteFilterVO.setCompanyCode(billingSiteSession.getBillingSiteVO().getCompanyCode());
			billingSiteMasterForm.setBillingSiteCode(billingSiteSession.getBillingSiteVO().getBillingSiteCode());
			billingSiteSession.removeFromParentScreen();
			billingSiteSession.removeBillingSiteVO();
		}
		if(billingSiteMasterForm.getBillingSiteCode()==null || billingSiteMasterForm.getBillingSiteCode().trim().length()==0){
			errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.wanttocreate");
			errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
   			invocationContext.target=ACTION_SUCCESS;
			return;
		}		
	
		billingSiteFilterVO.setBillingSiteCode(billingSiteMasterForm.getBillingSiteCode());
		billingSiteFilterVO.setBillingSite(billingSiteMasterForm.getBillingSite());
		billingSiteFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		
		
		Collection<BillingSiteVO> billingSiteVOs=null;
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		
		try{
			billingSiteVOs = mailTrackingMRADelegate.findBillingSiteDetails(billingSiteFilterVO);
			if(billingSiteVOs!=null && billingSiteVOs.size()>0){
				Object[] billingSiteVO=billingSiteVOs.toArray();
				billingSiteSession.setBillingSiteVO((BillingSiteVO)billingSiteVO[0]);
				billingSiteSession.setBillingSiteGPACountiresVOs(((BillingSiteVO)billingSiteVO[0]).getBillingSiteGPACountriesVO());
				billingSiteSession.setBillingSiteBankDetailsVOs(((BillingSiteVO)billingSiteVO[0]).getBillingSiteBankDetailsVO());
				BillingSiteVO billingSiteVO1=(BillingSiteVO)billingSiteVO[0];
				String toDate=billingSiteVO1.getToDate().toDisplayDateOnlyFormat();
				String currentDate=DateUtilities.getCurrentDate("dd-MMM-yyyy");
				if (DateUtilities.isLessThan(toDate, currentDate, "dd-MMM-yyyy")){
					billingSiteMasterForm.setSiteExpired("YES");
				}
				billingSiteMasterForm.setCheckFlag(LIST_FLAG);
				billingSiteMasterForm.setStatus("N");
				invocationContext.target = ACTION_SUCCESS;
				}
				else{
					Object[] obj= new Object[1];
					obj[0]=billingSiteMasterForm.getBillingSiteCode();
					errorVO=new ErrorVO(NO_RESULTS_FOUND,obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(errorVO);
					
		   			invocationContext.addAllError(errors);
		   			invocationContext.target=ACTION_SUCCESS;
					
				}
		}
		
		catch(Exception exception){
			log.log(Log.INFO, "ERRORS>>>>>>>", exception.getMessage());	
		}
	
		log.exiting("ScreenLoadCommand", "execute");
	}

}
