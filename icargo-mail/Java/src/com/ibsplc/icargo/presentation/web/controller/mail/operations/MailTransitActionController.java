package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailTransitModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/mailtransitoverview")
public class MailTransitActionController extends AbstractActionController<MailTransitModel>{

	@Resource(name="mailtransitResourceBundle")
	private ICargoResourceBundle mailtransitResourceBundle;
	
	@RequestMapping("/fetchDetailsOnScreenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(
			@RequestBody MailTransitModel mailTransitModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailtransitoverview.fetchdetailsonscreenload",mailTransitModel);
	}
	
	
	@RequestMapping("/fetchMailTransitDetailsForEnquiry")
	public @ResponseBody ResponseVO fetchMailTransitDetailsForEnquiry(
			@RequestBody MailTransitModel mailTransitModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailtransitoverview.fetchmailtransitdetailsforenquiry",mailTransitModel);
	}
	
	@RequestMapping("/fetchMailTransitCapacityDetails")
	public @ResponseBody ResponseVO fetchMailTransitCapacityDetails(
			@RequestBody MailTransitModel mailTransitModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailtransitoverview.fetchmailtransitcapacitydetails",mailTransitModel);
	}
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailtransitResourceBundle;
	}

}
