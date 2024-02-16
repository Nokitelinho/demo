/*
 * SaveCommand.java Created on Mar 30, 2007
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
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class SaveCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "SaveCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String SAVE_SUCCESS="save_success";	
	private static final String NEW_SLA_OPTION_FLAG = "N";
	private static final String LINK_STATUS = "Y";
	private static final String INVALIDCURRENCY = 
		"mailtracking.mra.defaults.maintainmailsla.err.invalidcurrency";
	
	
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
		String operationFlagParent = maintainMailSLAForm.getOperationFlagParent();
		String currency = maintainMailSLAForm.getCurrency();
		
		
		
		CurrencyDelegate currencyDelegate  = new CurrencyDelegate();
		if(currency != null && currency.trim().length()>0){
			log.log(Log.INFO,"\n <<<<<<--------currency not null--------->>>>>>");
			try{
				currencyDelegate.validateCurrency(companyCode,currency);
			}
			catch(BusinessDelegateException e){
				log.log(Log.INFO,"\n <<<<<<-------- Vaidation Failed--------->>>>>>");
				ErrorVO error =  new ErrorVO(INVALIDCURRENCY);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);				
				invocationContext.addError(error);
				invocationContext.target=SAVE_SUCCESS;
				return;				
			}			
		}
		
		MailSLAVO mailSLAVo  = maintainMailSLASession.getMailSLAVo();
		Collection<MailSLADetailsVO> detailsVosFromSession = mailSLAVo.getMailSLADetailsVos();
		MailSLADetailsVO mailSLADetailsVo = null;
		Collection<MailSLADetailsVO> tempCollection= new ArrayList<MailSLADetailsVO>();			
		
		mailSLAVo.setDescription(maintainMailSLAForm.getDescription());
		mailSLAVo.setCurrency(maintainMailSLAForm.getCurrency());
		mailSLAVo.setSlaId(slaId);
		
		log.log(Log.INFO, "\n <<<<<<-------- MailSLAVO --------->>>>>>",
				mailSLAVo);
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
		int countForDeletedVos = 0;
		int countForInsertVos=0;
		if(!(detailsVosFromSession == null || detailsVosFromSession.size()==0)){
			
			mailSLAVo.setOperationFlag(operationFlagParent);
			
			for(MailSLADetailsVO detailsVO : detailsVosFromSession){
				if(MailSLADetailsVO.OPERATION_FLAG_DELETE.equals(detailsVO.getOperationFlag())){
					log.log(Log.INFO,
							"\n <----------VO TO BE DELETED-------->",
							detailsVO);
					tempCollection.add(detailsVO);
					
					++countForDeletedVos;
				}
				if(MailSLADetailsVO.OPERATION_FLAG_INSERT.equals(detailsVO.getOperationFlag())){
					log.log(Log.INFO,"\n <---------OPRATION FLAG INSERT --------->");				
					++countForInsertVos;
				}
			}
			
			if(countForDeletedVos == detailsVosFromSession.size()){
				mailSLAVo.setOperationFlag(MailSLAVO.OPERATION_FLAG_DELETE);
			}
			else if(countForInsertVos == detailsVosFromSession.size()){
				mailSLAVo.setOperationFlag(MailSLAVO.OPERATION_FLAG_INSERT);
			}else if(countForDeletedVos >0 || countForInsertVos >0){
				mailSLAVo.setOperationFlag(MailSLAVO.OPERATION_FLAG_UPDATE);				
			}
			
			mailSLAVo.setMailSLADetailsVos(tempCollection);
			log.log(Log.INFO, "\n <----------FINAL VO TO SAVE-------->",
					mailSLAVo);
			MailTrackingMRADelegate delegate =  new MailTrackingMRADelegate();
			try{
				delegate.saveMailSla(mailSLAVo);
			}catch(BusinessDelegateException e){
				handleDelegateException(e);
			}
		}
		maintainMailSLAForm.setSlaId(null);
		maintainMailSLAForm.setDescription(null);
		maintainMailSLAForm.setCurrency(null);
		maintainMailSLASession.removeMailSLAVo();
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		maintainMailSLAForm.setNewSLAOptionFlag(NEW_SLA_OPTION_FLAG);
		invocationContext.target=SAVE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	
}
