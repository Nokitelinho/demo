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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Controller
@RequestMapping("addons/mail/operations/listflight")
public class ListFlightActionController extends AbstractActionController<MailAwbBookingModel>{

	private Log log = LogFactory.getLogger("ADDONSMAIL"); 
	@Resource(name="listFlightResourceBundle")
	ICargoResourceBundle listFlightResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return listFlightResourceBundle;
	}
	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadListFlights(@RequestBody MailAwbBookingModel mailAwbBookingModel)
		throws BusinessDelegateException, SystemException{
		this.log.entering("ListFlightActionController", "execute");
		return performAction("addons.mail.operations.listflight.screenload",mailAwbBookingModel);
	}
	
	@RequestMapping("/ok/bookedflights")
	public @ResponseBody ResponseVO okBookedFlights(@RequestBody MailAwbBookingModel mailAwbBookingModel)
		throws BusinessDelegateException, SystemException{
		this.log.entering("ListFlightActionController", "execute");
		return performAction("addons.mail.operations.listflight.okbookedflights",mailAwbBookingModel);
	}
}
