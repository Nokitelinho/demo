package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/carditdsnenquiry")
public class CarditDsnEnquiryActionController extends AbstractActionController<CarditDsnEnquiryModel> {
	
	@Resource(name="carditDSNEnquiryResourceBundle")
	ICargoResourceBundle carditDSNEnquiryResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return carditDSNEnquiryResourceBundle;
	}
	
	@RequestMapping("/screenloadCarditDsnEnquiry")
	public @ResponseBody ResponseVO screenloadCarditEnquiry(@RequestBody CarditDsnEnquiryModel carditDsnEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditdsnenquiry.screenload",carditDsnEnquiryModel);
	}
	
	@RequestMapping("/listCarditDsnEnquiry")
	public @ResponseBody ResponseVO listCarditEnquiry(@RequestBody CarditDsnEnquiryModel carditDsnEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditdsnenquiry.listcarditdsnenquiry",carditDsnEnquiryModel);
	}
	
	@RequestMapping("/listMailbagsforDsns")
	public @ResponseBody ResponseVO listMailbagsforDsns(@RequestBody CarditDsnEnquiryModel carditDsnEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditdsnenquiry.listMailbagsforDsns",carditDsnEnquiryModel);
	}
	
	@RequestMapping("/detachAwb")
	public @ResponseBody ResponseVO detachAwb(@RequestBody CarditDsnEnquiryModel carditDsnEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.carditdsnenquiry.detachAwb",carditDsnEnquiryModel);
	}

}
