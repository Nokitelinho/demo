package com.ibsplc.icargo.presentation.web.controller.xaddons.lh.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.xaddons.lh.mail.operations.HbaMarkingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Controller
@RequestMapping("xaddons/mail/operations/hbamarking")
public class HbaMarkingActionController extends AbstractActionController<HbaMarkingModel>{

	@Resource(name="hbaMarkingResourceBundle")
	ICargoResourceBundle hbaMarkingResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return hbaMarkingResourceBundle;
	}
	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenload(@RequestBody HbaMarkingModel hbaMarkingModel)  
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.hbamarking.screenload",hbaMarkingModel);
	}
	
	@RequestMapping("/markHba")
	public @ResponseBody ResponseVO markHba(@RequestBody HbaMarkingModel hbaMarkingModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.hbaMarking.save",hbaMarkingModel);
	}
	
}
