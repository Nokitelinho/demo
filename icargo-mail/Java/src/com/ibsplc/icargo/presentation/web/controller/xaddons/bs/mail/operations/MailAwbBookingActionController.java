package com.ibsplc.icargo.presentation.web.controller.xaddons.bs.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.MailAwbBookingPopupModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Controller
@RequestMapping("xaddons/bs/operations/mailawbbooking")
public class MailAwbBookingActionController extends AbstractActionController<MailAwbBookingPopupModel>  {
	
	@Resource(name="mailAwbBookingResourceBundle")
	ICargoResourceBundle mailAwbBookingResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailAwbBookingResourceBundle;
	}
	
	@RequestMapping("/screenloadMailAwbBooking")
	public @ResponseBody ResponseVO screenloadMailAwbBooking(@RequestBody MailAwbBookingPopupModel mailAwbBookingPopupModel)  
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailawbbooking.screenload",mailAwbBookingPopupModel);
	}
	
	@RequestMapping("/listMailAwbBooking")
	public @ResponseBody ResponseVO listMailAwbBooking(@RequestBody MailAwbBookingPopupModel mailAwbBookingPopupModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailawbbooking.list",mailAwbBookingPopupModel);
	}
	
	@RequestMapping("/attachAwb")
	public @ResponseBody ResponseVO attachMailAwb(@RequestBody MailAwbBookingPopupModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailawbbooking.attachMailAWB",mailAwbBookingPopupModel);     
	}

}
