/**
 *FindMCALovCommand.java Created on May 25-2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MCALovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class FindMCALovCommand extends BaseCommand{

	/*
	 * Logger for the Mailtracking Mra
	 */
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA");
	 
	private static final String CLASS_NAME = "FindMCALovCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.mcalov";

	private static final String SCREEN_SUCCESS = "screenload_success";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MCALovForm mcaLovForm = (MCALovForm) invocationContext.screenModel;
		Page<CCAdetailsVO> ccAdetailsVOs = new Page<CCAdetailsVO>(new ArrayList<CCAdetailsVO>(),0,0,0,0,0,false);
		CCAdetailsVO ccAdetailFilterVO=new CCAdetailsVO();
		ccAdetailFilterVO=populateCCAdetailFilterVO(mcaLovForm);
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		try {
			if(!(("Y").equals(mcaLovForm.getMultiselect()))){
				mcaLovForm.setSelectedValues("");
			}
			ccAdetailsVOs=mailTrackingMRADelegate.findMCALov(ccAdetailFilterVO, Integer.parseInt(mcaLovForm.getDisplayPage()));
		} catch (BusinessDelegateException e) {

			log.log(Log.FINE,  "BusinessDelegateException");
		} catch (NumberFormatException e) {

			log.log(Log.FINE,  "NumberFormatException");
		}
		mcaLovForm.setMcaLovPage(ccAdetailsVOs);
		invocationContext.target=SCREEN_SUCCESS;

	}
	/**
	 * 
	 * @param maintainCCAForm
	 * @return
	 */
	private CCAdetailsVO populateCCAdetailFilterVO(
			MCALovForm mcaLovForm) {
		CCAdetailsVO ccAdetailFilterVO=new CCAdetailsVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		ccAdetailFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		ccAdetailFilterVO.setCcaRefNumber(mcaLovForm.getCcaNum());
		ccAdetailFilterVO.setOriginCode(mcaLovForm.getOrigin());
		ccAdetailFilterVO.setDestnCode(mcaLovForm.getDestination());
		ccAdetailFilterVO.setCategoryCode(mcaLovForm.getCategory());
		ccAdetailFilterVO.setSubClass(mcaLovForm.getSubclass());
		ccAdetailFilterVO.setYear(mcaLovForm.getYear());
		ccAdetailFilterVO.setDsnNo(mcaLovForm.getDsnNumber());
		return ccAdetailFilterVO;
	}

}
