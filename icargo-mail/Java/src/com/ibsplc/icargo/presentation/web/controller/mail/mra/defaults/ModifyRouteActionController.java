package com.ibsplc.icargo.presentation.web.controller.mail.mra.defaults;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.ModifyRouteModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/mra/defaults/modifyroute")
public class ModifyRouteActionController extends AbstractActionController<ModifyRouteModel> {
	
	@Resource(name="modifyRouteResourceBundle")
	ICargoResourceBundle modifyRouteResourceBundle;
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return modifyRouteResourceBundle;
	}

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadModifyRoute(@RequestBody ModifyRouteModel modifyRouteModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.modifyroute.screenload",modifyRouteModel);
	}
	
	@RequestMapping("/execute")
	public @ResponseBody ResponseVO screenloadExecute(@RequestBody ModifyRouteModel modifyRouteModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.modifyroute.execute",modifyRouteModel);
	}
	

}
