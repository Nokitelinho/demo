/*
 * DeleteCommand.java Created on Apr 3, 2007
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class DeleteCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String DELETE_SUCCESS= "delete_success";
	private static final String CLASS_NAME= "DeleteCommand";
	private static final String OPERATION_FLAG_NOT_MODIFIED = "N";
	//private static final String BLANK = "";
	/**
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering(CLASS_NAME,"execute");  
		
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		
		String[] rowId = maintainMailSLAForm.getRowId();
		
		MailSLAVO mailSLAVo  = maintainMailSLASession.getMailSLAVo();
		Collection<MailSLADetailsVO> newDetailsVOs= new ArrayList<MailSLADetailsVO>();			
		Collection<MailSLADetailsVO> detailsVosFromSession = mailSLAVo.getMailSLADetailsVos();
		mailSLAVo.setDescription(maintainMailSLAForm.getDescription());
		mailSLAVo.setCurrency(maintainMailSLAForm.getCurrency());		
		
		
		for(String selectedRow : rowId){			
			int index = 0;
			int selectedIndex = Integer.parseInt(selectedRow);
			log.log(Log.INFO, " Selected Row is-----> ", selectedIndex);
			for(MailSLADetailsVO details : detailsVosFromSession ){
				if(selectedIndex == index){
					log.log(Log.INFO, "\n\n\n\n The Index-->", index);
					if(MailSLADetailsVO.OPERATION_FLAG_INSERT.equals(details.getOperationFlag())){
						log
								.log(
										Log.INFO,
										"\n<---------VO TO BE REMOVED FROM SESSION---------->",
										details);
						newDetailsVOs.add(details);
					}
					else if(!MailSLADetailsVO.OPERATION_FLAG_UPDATE.equals(details.getOperationFlag())
							&& OPERATION_FLAG_NOT_MODIFIED.equals(details.getOperationFlag())){
						log
								.log(
										Log.INFO,
										"\n<---------Operation Flag to be set to DELETE for------->",
										details);
						details.setOperationFlag(MailSLADetailsVO.OPERATION_FLAG_DELETE);
					}
				}				
				++index;				
			}	
		}
		if(newDetailsVOs.size()>0 && newDetailsVOs!=null){
			log.log(Log.INFO,"\n\n\n\n <---The Temp if condition enter --->");
			detailsVosFromSession.removeAll(newDetailsVOs);
		}	
		
		mailSLAVo.setMailSLADetailsVos(detailsVosFromSession);
		maintainMailSLASession.setMailSLAVo(mailSLAVo);
		log.log(Log.INFO, "\n <----------FINAL VO TO SERVER-------->",
				mailSLAVo);
		invocationContext.target=DELETE_SUCCESS;		
		log.exiting(CLASS_NAME, "execute");	
		
		
	}
	
}
