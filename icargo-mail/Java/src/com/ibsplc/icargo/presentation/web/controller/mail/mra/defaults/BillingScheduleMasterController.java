package com.ibsplc.icargo.presentation.web.controller.mail.mra.defaults;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleFilterModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * @author A-9498
 *
 */
@Controller
@RequestMapping("mail/mra/defaults/billingschedulemaster")
public class BillingScheduleMasterController extends AbstractActionController<BillingScheduleMasterModel> {
	
	@Resource(name="mailbillingScheduleMasterResourceBundle")
	ICargoResourceBundle BillingScheduleMaster;
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return BillingScheduleMaster;
	}

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadBillingSchedulemaster(@RequestBody BillingScheduleMasterModel billingScheduleMasterModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.billingScheduleMaster.screenload",billingScheduleMasterModel);
	}

	@RequestMapping("/list")
	public @ResponseBody ResponseVO screenloadExecute(@RequestBody BillingScheduleMasterModel billingScheduleMasterModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.billingScheduleMaster.list",billingScheduleMasterModel);
	}
	@RequestMapping("/save")
	public @ResponseBody ResponseVO saveBillingSchedulemaster(@RequestBody BillingScheduleMasterModel billingScheduleMasterModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.mra.defaults.ux.billingScheduleMaster.save",
				billingScheduleMasterModel);
	}
	@RequestMapping("/validate")
	public @ResponseBody ResponseVO validateBillilgPeriod(@RequestBody BillingScheduleMasterModel billingScheduleMasterModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.billingScheduleMaster.validate",billingScheduleMasterModel);
	}

}
