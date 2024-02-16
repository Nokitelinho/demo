/*
 * UpdateCommand.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailContractsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class UpdateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "Update Command";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailcontracts";
	private static final String ACTION_SUCCESS = "update_success";
	private static final String BLANK="";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	ArrayList<MailContractDetailsVO> mailDetailsVO=null;
	
	String[] origin=form.getOriginCode();
	String[] destination=form.getDestinationCode();
	String[] accdep=form.getAcceptanceToDeparture();
	String[] arrdel=form.getArrivalToDelivery();
	String[] remarks=form.getRemarks();
	
	

	if(session.getMailContractDetails()!=null && session.getMailContractDetails().size()>0){
		mailDetailsVO=new ArrayList<MailContractDetailsVO>(session.getMailContractDetails());
		int mailSize=mailDetailsVO.size();
		for(int i=0;i<mailSize;i++){
			if(!(OPERATION_FLAG_DELETE.equals(mailDetailsVO.get(i).getOperationFlag()))){
			
				if(mailDetailsVO.get(i).getOriginCode()!=null || !(BLANK.equals(origin[i]))){
			if(!(origin[i].equals(mailDetailsVO.get(i).getOriginCode()))){
				mailDetailsVO.get(i).setOriginCode(origin[i]);
				if(!(OPERATION_FLAG_INSERT.equals(mailDetailsVO.get(i).getOperationFlag()))) {
					mailDetailsVO.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
				}
				if(mailDetailsVO.get(i).getDestinationCode()!=null || !(BLANK.equals(destination[i]))){	
			if(!(destination[i].equals(mailDetailsVO.get(i).getDestinationCode()))){
				mailDetailsVO.get(i).setDestinationCode(destination[i]);
				if(!(OPERATION_FLAG_INSERT.equals(mailDetailsVO.get(i).getOperationFlag()))) {
					mailDetailsVO.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
				}
				if(mailDetailsVO.get(i).getAcceptanceToDeparture()!=null || !(BLANK.equals(accdep[i]))){
			if(!(accdep[i].equals(mailDetailsVO.get(i).getAcceptanceToDeparture()))){
				mailDetailsVO.get(i).setAcceptanceToDeparture(accdep[i]);
				if(!(OPERATION_FLAG_INSERT.equals(mailDetailsVO.get(i).getOperationFlag()))) {
					mailDetailsVO.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
				}
				if(mailDetailsVO.get(i).getArrivalToDelivery()!=null || !(BLANK.equals(arrdel[i]))){
			if(!(arrdel[i].equals(mailDetailsVO.get(i).getArrivalToDelivery()))){
				mailDetailsVO.get(i).setArrivalToDelivery(arrdel[i]);
				if(!(OPERATION_FLAG_INSERT.equals(mailDetailsVO.get(i).getOperationFlag()))) {
					mailDetailsVO.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
				}
				if(mailDetailsVO.get(i).getRemarks()!=null || !(BLANK.equals(remarks[i]))){
			if(!(remarks[i].equals(mailDetailsVO.get(i).getRemarks()))){
				mailDetailsVO.get(i).setRemarks(remarks[i]);
				if(!(OPERATION_FLAG_INSERT.equals(mailDetailsVO.get(i).getOperationFlag()))) {
					mailDetailsVO.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
				}
			}
		}
		session.setMailContractDetails(mailDetailsVO);
	}
	
	
	
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
}