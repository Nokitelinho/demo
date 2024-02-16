package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.ViewMailBagIdSuggestCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	14-Sep-2018		:	Draft
 */
public class ViewMailBagIdSuggestCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	private static final String SUCCESS = "suggest_success";
	private Log log = LogFactory.getLogger("ViewMailBagIdSuggestCommand");
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the next ViewMailBagIdSuggestCommand----------> \n\n");
		MailBagHistoryUxForm mailBagHistoryForm = (MailBagHistoryUxForm) invocationContext.screenModel;
		MailBagHistorySession mailBagHistorySession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		/*if(null==mailBagHistorySession.getMailBagVos()||
				mailBagHistorySession.getMailBagVos().isEmpty()){   
			//Modified by A-7540 for ICRD-319921  
			if(null!=mailBagHistoryForm.getTotalViewRecords()&&   
					mailBagHistoryForm.getMailBagVOs() != null &&
					!mailBagHistoryForm.getMailBagVOs().equals("")){*/
		if(null!=mailBagHistoryForm.getTotalViewRecords()&&   
				!mailBagHistoryForm.getTotalViewRecords().equals("")){
			ArrayList<MailbagVO> mailBagVOs=new ArrayList<MailbagVO>();
				for(String mailbagID:mailBagHistoryForm.getTotalViewRecords().split("-")){ 
					if(mailbagID!=null&&!mailbagID.equals("")){
						MailbagVO mailbagVO=new MailbagVO();  
						mailbagVO.setMailbagId(mailbagID);  
						mailBagVOs.add(mailbagVO);  
					}
				} 
				mailBagHistorySession.setMailBagVos(mailBagVOs); 		
			}
			else{
				ArrayList<MailbagVO> mailBagVOs=mailBagHistorySession.getMailBagVos();
			MailbagVO mailbagVO=new MailbagVO();
			for(MailbagVO vo:mailBagVOs){
				if(!(vo.getMailbagId()).equals(mailBagHistoryForm.getMailbagId())){
			mailbagVO.setMailbagId(mailBagHistoryForm.getMailbagId());
			mailBagVOs.add(mailbagVO);
			}
			}
			mailBagHistorySession.setMailBagVos(mailBagVOs);
			}
		//}
		
		invocationContext.target =SUCCESS;     

	}
}
