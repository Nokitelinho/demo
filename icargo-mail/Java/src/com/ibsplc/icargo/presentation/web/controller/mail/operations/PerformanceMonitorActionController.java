package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailPerformanceMonitorModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Controller
@RequestMapping("mail/operations/mailperformancemonitor")
public class PerformanceMonitorActionController extends
AbstractActionController<MailPerformanceMonitorModel> {

@Resource(name = "mailPerformanceResourceBundle")
ICargoResourceBundle mailPerformanceResourceBundle;

public ICargoResourceBundle getResourceBundle() {
return mailPerformanceResourceBundle;
}

@RequestMapping("/screenload")
public @ResponseBody
ResponseVO screenloadOutbound(@RequestBody MailPerformanceMonitorModel mailPerformanceMonitorModel)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.fetchdetailsonscreenload",
			mailPerformanceMonitorModel);
}

@RequestMapping("/list")
public @ResponseBody
ResponseVO listOutbound(@RequestBody MailPerformanceMonitorModel mailPerformanceMonitorModel)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformance.list",
			mailPerformanceMonitorModel);
	   }

@RequestMapping("/listMissingOriginScan")
public @ResponseBody
ResponseVO listMissingOriginScan(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listMissingOriginScan",
			model);
}

@RequestMapping("/listMissingDeliveryScan")
public @ResponseBody
ResponseVO listMissingDeliveryScan(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listMissingDeliveryScan",
			model);
}

@RequestMapping("/listMissingbothscan")
public @ResponseBody
ResponseVO listMissingbothScan(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listMissingbothScan",
			model);
}
@RequestMapping("/listOnTimeMailbags")
public @ResponseBody
ResponseVO listOnTimeMailbags(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listOnTimeMailbags",
			model);
}

@RequestMapping("/listRaisedMailbags")
public @ResponseBody
ResponseVO listRaisedMailbags(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listRaisedMailbags",
			model);
}

@RequestMapping("/listApprovedMailbags")
public @ResponseBody
ResponseVO listApprovedMailbags(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listApprovedMailbags",
			model);
}

@RequestMapping("/listDeniedMailbags")
public @ResponseBody
ResponseVO listDeniedMailbags(@RequestBody MailPerformanceMonitorModel model)
		throws BusinessDelegateException, SystemException {
	return performAction("mail.operations.ux.mailperformancemonitor.listDeniedMailbags",
			model);
}


}