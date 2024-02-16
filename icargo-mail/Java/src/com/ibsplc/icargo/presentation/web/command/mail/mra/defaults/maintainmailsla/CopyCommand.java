/*
 * CopyCommand.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class CopyCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "CopyCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String COPY_SUCCESS="copy_success";
	private static final String CALL_POPUP="Y";
	private static final String NEW_SLA_OPTION_FLAG = "N";
	private static final String LINK_STATUS = "N";
	
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		maintainMailSLAForm.setNewSLAOptionFlag(NEW_SLA_OPTION_FLAG);
		
		String companyCode = logonAttributes.getCompanyCode();
		String slaId = maintainMailSLAForm.getSlaId();
		String[] mailCategory = maintainMailSLAForm.getMailCategory();
		String[] serviceTime= maintainMailSLAForm.getServiceTime();
		String[] alertTime = maintainMailSLAForm.getAlertTime();
		String[] chaserTime = maintainMailSLAForm.getChaserTime();
		String[] chaserFrequency = maintainMailSLAForm.getChaserFrequency();
		String[] maxNumberOfChasers = maintainMailSLAForm.getMaxNumberOfChasers();
		String[] claimRate = maintainMailSLAForm.getClaimRate();		
		String[] rowId = maintainMailSLAForm.getRowId();
		
		MailSLADetailsVO mailSLADetailsVo = null;
		Collection<MailSLADetailsVO> tempCollection= new ArrayList<MailSLADetailsVO>();			
		
		for(String selectedRow : rowId){				
			int index = Integer.parseInt(selectedRow);
			log.log(Log.INFO, " Selected Row is-----> ", index);
			mailSLADetailsVo =  new MailSLADetailsVO();
			mailSLADetailsVo.setCompanyCode(companyCode);
			mailSLADetailsVo.setSlaId(slaId);
			mailSLADetailsVo.setMailCategory(mailCategory[index]);
			mailSLADetailsVo.setServiceTime(Integer.parseInt(serviceTime[index]));
			mailSLADetailsVo.setAlertTime(Integer.parseInt(alertTime[index]));
			mailSLADetailsVo.setChaserTime(Integer.parseInt(chaserTime[index]));
			mailSLADetailsVo.setChaserFrequency(Integer.parseInt(chaserFrequency[index]));
			mailSLADetailsVo.setMaxNumberOfChasers(Integer.parseInt(maxNumberOfChasers[index]));
			mailSLADetailsVo.setClaimRate(Double.parseDouble(claimRate[index]));
			mailSLADetailsVo.setOperationFlag(MailSLADetailsVO.OPERATION_FLAG_INSERT);
			tempCollection.add(mailSLADetailsVo);
		
		}
		log.log(Log.INFO, " VOs to be copied-----> ", tempCollection);
		maintainMailSLASession.setMailSLADetailsVOs(tempCollection);
		maintainMailSLAForm.setCallPopup(CALL_POPUP);
		
		invocationContext.target=COPY_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}

}
