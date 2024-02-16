package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * Java file : com.ibsplc.icargo.presentation.web.command.mail.operations.ux.
 * forcemajeurerequest.DeleteCommand.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-8527 :
 * 01-Dec-2018 : Draft
 */
public class DeleteCommand  extends BaseCommand {

private static final String MODULE_NAME = "mail.operations";
private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
private static final String TARGET = "delete_success";
private static final String TARGET_FAIL = "delete_failure";
private Log log = LogFactory.getLogger("Mail Operations force majeure request");


@Override
public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
	log.log(Log.FINE,
			"\n\n in the list command of New tab force Majeure Request----------> \n\n");
	ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
	invocationContext.target = TARGET;
	ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	ForceMajeureRequestFilterVO forceMajeureRequestFilterVO = new ForceMajeureRequestFilterVO();
	String companyCode = logonAttributes.getCompanyCode();
	String []deleterow=forceMajeureRequestForm.getCheckSel();
	//String []deleteArray=forceMajeureRequestForm.getCheckall();
	int displaypage=1;
	if(forceMajeureRequestForm.getForceid()!=null&& forceMajeureRequestForm.getForceid().trim().length()>0){
		forceMajeureRequestFilterVO.setForceMajeureID(forceMajeureRequestForm.getForceid());
		}
		forceMajeureRequestFilterVO.setCompanyCode(companyCode);
		if(forceMajeureRequestForm.getActionFlag()!=null && forceMajeureRequestForm.getActionFlag().trim().length()>0 ){
			forceMajeureRequestForm.setActionFlag(forceMajeureRequestForm.getActionFlag());
			}
		if(forceMajeureRequestForm.getDefaultPageSize()!=null && forceMajeureRequestForm.getDefaultPageSize().trim().length()>0){
			forceMajeureRequestFilterVO.setDefaultPageSize(Integer.parseInt(forceMajeureRequestForm.getDefaultPageSize()));
		}
		if(forceMajeureRequestForm.getDisplayPage()!=null&& forceMajeureRequestForm.getDisplayPage().trim().length()>0){
			displaypage=Integer.parseInt(forceMajeureRequestForm.getDisplayPage());
		}
	Page <ForceMajeureRequestVO> forceMajeureRequestVOs=forceMajeureRequestSession.getReqforcemajeurevos();
	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	Collection<ForceMajeureRequestVO> deleterecords = new ArrayList<ForceMajeureRequestVO>();
	if(deleterow!=null && deleterow.length>0){
		for(String arr:deleterow){
		deleterecords.add(forceMajeureRequestVOs.get(Integer.parseInt(arr)));
		}
	}
	/*if(deleteArray!=null && deleteArray.length>0){
	for(String arr:deleteArray){
		deleterecords.add(forceMajeureRequestVOs.get(Integer.parseInt(arr)));	
	}
	}*/
	try {
	delegate.deleteForceMajeureRequest(deleterecords);
	forceMajeureRequestSession.setFilterParamValues(forceMajeureRequestFilterVO);
	} catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessage();
		invocationContext.target = TARGET_FAIL;
	}
}
}
