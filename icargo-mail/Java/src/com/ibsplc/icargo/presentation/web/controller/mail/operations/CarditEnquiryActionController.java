package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/carditenquiry")
public class CarditEnquiryActionController extends AbstractActionController<CarditEnquiryModel>{

	@Resource(name="carditEnquiryResourceBundle")
	ICargoResourceBundle carditEnquiryResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		// TODO Auto-generated method stub
		return carditEnquiryResourceBundle;
	}
	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadCarditEnquiry(@RequestBody CarditEnquiryModel carditEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditenquiry.screenload",carditEnquiryModel);
	}
	
	@RequestMapping("/list")
	public @ResponseBody ResponseVO listCarditEnquiry(@RequestBody CarditEnquiryModel carditEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditenquiry.listcarditenquiry",carditEnquiryModel);
	}
	@RequestMapping("/sendResdit")
	public @ResponseBody ResponseVO sendResdit(@RequestBody CarditEnquiryModel carditEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditenquiry.sendResdit",carditEnquiryModel);
	}

}
