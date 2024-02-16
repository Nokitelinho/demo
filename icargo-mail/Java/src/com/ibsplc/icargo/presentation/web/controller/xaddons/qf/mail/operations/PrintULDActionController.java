package com.ibsplc.icargo.presentation.web.controller.xaddons.qf.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Controller
@RequestMapping("xaddons/mail/operations")
public class PrintULDActionController extends AbstractActionController<ListContainerModel>{

	@Resource(name="printuldResourceBundle")
	ICargoResourceBundle printuldResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return printuldResourceBundle;
	}
	
	@RequestMapping("/printuldtag")
	public @ResponseBody
	ResponseVO printULDTag(@RequestBody ListContainerModel listContainer)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.printuldtag",
				listContainer);
	}
	
}