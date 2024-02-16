package com.ibsplc.icargo.presentation.web.controller.mail.operations;
import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailContainerListModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/mailcontainerlist")
public class MailContainerListActionController extends AbstractActionController<MailContainerListModel>{



	@Resource(name="mailcontainerlistResourceBundle")
	ICargoResourceBundle mailcontainerlistResourceBundle;
	

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailcontainerlistResourceBundle;
	}
	
	
	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadMailContainerList(@RequestBody MailContainerListModel mailcontainerlist)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailcontainerlist.mailcontainerlistscreenload",mailcontainerlist);
	}


	@RequestMapping("/list")
	public @ResponseBody ResponseVO listMailContainerList(@RequestBody MailContainerListModel mailcontainerlist)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailcontainerlist.listmailcontainer",mailcontainerlist);
	}
	
	@RequestMapping("/assignToLoadPlan")
	public @ResponseBody ResponseVO selectMailContainerList(@RequestBody MailContainerListModel mailcontainerlist)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailcontainerlist.assignmailcontainertoloadplan",mailcontainerlist);
	}


}

