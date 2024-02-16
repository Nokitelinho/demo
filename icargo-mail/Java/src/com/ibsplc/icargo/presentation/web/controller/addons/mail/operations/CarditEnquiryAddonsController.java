package com.ibsplc.icargo.presentation.web.controller.addons.mail.operations;


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
@RequestMapping("addons/mail/operations/carditenquiry")
public class CarditEnquiryAddonsController extends AbstractActionController<CarditEnquiryModel>{

	@Resource(name="carditEnquiryResourceBundles")
	ICargoResourceBundle carditEnquiryResourceBundles;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		
		return carditEnquiryResourceBundles;
	}

	
	@RequestMapping("/detachAWB")
	public @ResponseBody ResponseVO detachAWB(@RequestBody CarditEnquiryModel carditEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.carditenquiry.detachAWB",carditEnquiryModel);
	}
}
