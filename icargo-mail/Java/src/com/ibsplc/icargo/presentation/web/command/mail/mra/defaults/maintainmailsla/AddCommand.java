/*
 * AddCommand.java Created on Apr 3, 2007
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
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
public class AddCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String ADD_SUCCESS= "add_success";
	private static final String CLASS_NAME= "AddCommand";
	private static final String BLANK = "";
	/**
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME,"execute");  
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		
		String companyCode = logonAttributes.getCompanyCode();
		String slaId = maintainMailSLAForm.getSlaId();
		String[] mailCategory = maintainMailSLAForm.getMailCategory();
		String[] serviceTime= maintainMailSLAForm.getServiceTime();
		String[] alertTime = maintainMailSLAForm.getAlertTime();
		String[] chaserTime = maintainMailSLAForm.getChaserTime();
		String[] chaserFrequency = maintainMailSLAForm.getChaserFrequency();
		String[] maxNumberOfChasers = maintainMailSLAForm.getMaxNumberOfChasers();
		String[] claimRate = maintainMailSLAForm.getClaimRate();
		String[] serialNumber = maintainMailSLAForm.getSerialNumber();
		String[] operationFlag = maintainMailSLAForm.getOperationFlag();
		
		MailSLAVO mailSLAVo  = maintainMailSLASession.getMailSLAVo();
		MailSLADetailsVO mailSLADetailsVo = null;
		Collection<MailSLADetailsVO> tempCollection= new ArrayList<MailSLADetailsVO>();			
		
		if(mailSLAVo == null){
			log.log(Log.INFO,"\n <----------MailSLAVO NOT in Session-------->");
			mailSLAVo =  new MailSLAVO();
			mailSLAVo.setCompanyCode(companyCode);
			mailSLAVo.setSlaId(slaId);
			mailSLAVo.setOperationFlag(MailSLAVO.OPERATION_FLAG_INSERT);
			mailSLAVo.setMailSLADetailsVos(tempCollection);
		}		
		mailSLAVo.setDescription(maintainMailSLAForm.getDescription());
		mailSLAVo.setCurrency(maintainMailSLAForm.getCurrency());
		
		mailSLADetailsVo = new MailSLADetailsVO();
		mailSLADetailsVo.setOperationFlag(MailSLADetailsVO.OPERATION_FLAG_INSERT);
		mailSLADetailsVo.setCompanyCode(companyCode);
		mailSLADetailsVo.setMailCategory(BLANK);
		tempCollection.add(mailSLADetailsVo);
		
		//String[] rowId = maintainMailSLAForm.getRowId();
		
		if(operationFlag != null){
			log.log(Log.INFO,"\n <----------operationFlag NOT NULL-------->");
			int index = 0;
			for(String flag : operationFlag){
				log.log(Log.INFO, "\n <----------operationFlag-------->", flag);
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
				mailSLADetailsVo.setOperationFlag(flag);
				mailSLADetailsVo.setSerialNumber(Integer.parseInt(serialNumber[index]));
				tempCollection.add(mailSLADetailsVo);
				++index;
			}
		}
		for(MailSLADetailsVO detailsVO : mailSLAVo.getMailSLADetailsVos()){
			if(MailSLADetailsVO.OPERATION_FLAG_DELETE.equals(detailsVO.getOperationFlag())){
				log.log(Log.INFO, "\n <----------VO TO BE DELETED-------->",
						detailsVO);
				tempCollection.add(detailsVO);
			}
		}	
		mailSLAVo.setMailSLADetailsVos(tempCollection);
		maintainMailSLASession.setMailSLAVo(mailSLAVo);
		log.log(Log.INFO, "\n <----------FINAL VO TO SERVER-------->",
				mailSLAVo);
		invocationContext.target=ADD_SUCCESS;		
		log.exiting(CLASS_NAME, "execute");	
			
	}
	
	

}
