/*
 * ListCommand.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copyrate;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyRateSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyRateForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-2280
 *
 */
public class ListCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYRATE");
	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyrate";
	private static final String RATECARD_MANDATORY="mailtracking.mra.defaults.copyrate.err.ratecardidmadatory";
	private static final String LIST_FAILURE = "list_failure";
	private static final String LIST_SUCCESS="list_success";
	private static final String CAPTURE="capture";

	private static final String RATECARD_NOTEXISTS ="mailtracking.mra.defaults.copyrate.msg.wrn.ratecardnotexists";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CopyRateForm copyRateForm=(CopyRateForm)invocationContext.screenModel;
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		CopyRateSession copyRateSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		copyRateForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		RateCardVO rateCardVO=null;
		copyRateForm.setScreenMode("");
		if(copyRateForm.getRateCardId()==null || copyRateForm.getRateCardId().trim().length()==0){
			ErrorVO errVO=new ErrorVO(RATECARD_MANDATORY);
			errVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errVO);
			invocationContext.target=LIST_FAILURE;
			copyRateForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			return;
		}
		else{
			String rateCardId=copyRateForm.getRateCardId().toUpperCase();
			int pageNum=1;
			try {
				copyRateSession.removeRateCardVO();
				
				rateCardVO=delegate.findRateCardDetails(logonAttributes.getCompanyCode(),rateCardId,pageNum);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if(rateCardVO==null){
				copyRateForm.setScreenMode(CAPTURE);
				ErrorVO errVO=new ErrorVO(RATECARD_NOTEXISTS);
				errVO.setErrorDisplayType(ErrorDisplayType.WARNING);
				invocationContext.addError(errVO);
                copyRateForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				copyRateForm.setRateCardId(rateCardId);
				copyRateForm.setValidFrom("");
				copyRateForm.setValidTo("");
			}
			else{
				log.log(Log.INFO, "\n\nRate Card VO-->", rateCardVO);
				copyRateForm.setScreenMode("list");

				copyRateForm.setRateCardId(rateCardVO.getRateCardID());
				String validFrm="";
				String validTo="";
				if(rateCardVO.getValidityStartDate()!=null){
					validFrm=TimeConvertor.toStringFormat(rateCardVO.getValidityStartDate(),logonAttributes.getDateFormat());
				}
				if(rateCardVO.getValidityEndDate()!=null){
					validTo=TimeConvertor.toStringFormat(rateCardVO.getValidityEndDate(),logonAttributes.getDateFormat());
				}
				log.log(Log.INFO, "valid frm-->", validFrm);
				copyRateForm.setValidFrom(validFrm);
				copyRateForm.setValidTo(validTo);
				rateCardVO.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
				copyRateSession.setRateCardVO(rateCardVO);

			}


		}
		invocationContext.target=LIST_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}

}
