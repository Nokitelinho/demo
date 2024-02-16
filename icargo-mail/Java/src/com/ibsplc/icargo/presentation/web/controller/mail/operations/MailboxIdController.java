package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailboxIdModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/mailboxId")
public class MailboxIdController extends AbstractActionController<MailboxIdModel> {
	
	@Resource(name="mailboxIdResourceBundle")
	ICargoResourceBundle mailboxIdResourceBundle;
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailboxIdResourceBundle;
	}

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadMailboxId(@RequestBody MailboxIdModel mailboxIdmodel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailboxId.screenload",mailboxIdmodel);
	}
	
	@RequestMapping("/list")
	public @ResponseBody ResponseVO listMailboxId(@RequestBody MailboxIdModel mailboxIdmodel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailboxId.list",
				mailboxIdmodel);
	}
	
	@RequestMapping("/save")
	public @ResponseBody ResponseVO saveMailboxId(@RequestBody MailboxIdModel mailboxIdmodel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailboxId.save",
				mailboxIdmodel);
	}
	
}
