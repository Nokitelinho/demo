package com.ibsplc.icargo.presentation.web.controller.addons.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Controller
@RequestMapping("addons/mail/operations/mailawbbooking")
public class MailAwbBookingController extends AbstractActionController<MailAwbBookingModel>  {
	
	@Resource(name="mailAwbBookingPopupResourceBundle")
	ICargoResourceBundle mailAwbBookingPopupResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailAwbBookingPopupResourceBundle;
	}
	
	@RequestMapping("/screenloadMailAwbBooking")
	public @ResponseBody ResponseVO screenloadMailAwbBooking(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel)  
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.screenload",mailAwbBookingPopupModel);
	}
	
	@RequestMapping("/listMailAwbBooking")
	public @ResponseBody ResponseVO listMailAwbBooking(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel)
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.list",mailAwbBookingPopupModel);
	}
	
	@RequestMapping("/attachAwb")
	public @ResponseBody ResponseVO attachMailAwb(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.attachMailAWB",mailAwbBookingPopupModel);     
	}

	@RequestMapping("/listLoadPlanBookings")
	public @ResponseBody ResponseVO listLoadPlanBookings(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.listLoadPlanBookings",mailAwbBookingPopupModel);     
	}
	
	@RequestMapping("/attachLoadPlanAwb")
	public @ResponseBody ResponseVO attachLoadPlanAwb(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.attachLoadPlanAwb",mailAwbBookingPopupModel);     
	}

	@RequestMapping("/listManifestBookings")
	public @ResponseBody ResponseVO listManifestBookings(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.listManifestBookings",mailAwbBookingPopupModel);     
	}
	@RequestMapping("/attachManifestAwb")
	public @ResponseBody ResponseVO attachManifestAwb(@RequestBody MailAwbBookingModel mailAwbBookingPopupModel) 
		throws BusinessDelegateException, SystemException{
		return performAction("addons.mail.operations.mailawbbooking.attachManifestAwb",mailAwbBookingPopupModel);     
	}
}
