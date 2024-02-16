package com.ibsplc.icargo.presentation.web.controller.mail.mra.gpabilling;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GeneratePASSBillingFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/mra/gpabilling/generatepassbillingfile")
public class GeneratePASSBillingFileController extends AbstractActionController<GeneratePASSBillingFileModel> {
	
	@Resource(name="generatepassbillingfileResourceBundle")
	  ICargoResourceBundle generatepassbillingfileResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return generatepassbillingfileResourceBundle;
	}

	@RequestMapping("/screenload")
	  public @ResponseBody ResponseVO screenloadGeneratePass(@RequestBody GeneratePASSBillingFileModel generatepassbillingfilemodel) throws BusinessDelegateException, SystemException {
	    return performAction("mail.mra.gpabilling.ux.generatepassbillingfile.screenload",
	    		generatepassbillingfilemodel);
	  }
	
	@RequestMapping("/generate")
	  public @ResponseBody ResponseVO generatePass(@RequestBody GeneratePASSBillingFileModel generatepassbillingfilemodel) throws BusinessDelegateException, SystemException {
	    return performAction("mail.mra.gpabilling.ux.generatepassbillingfile.generate",
	    		generatepassbillingfilemodel);
	  }
	
	@RequestMapping("/populateperiod")
	  public @ResponseBody ResponseVO populatePeriodAndDate(@RequestBody GeneratePASSBillingFileModel generatepassbillingfilemodel) throws BusinessDelegateException, SystemException {
	    return performAction("mail.mra.gpabilling.ux.generatepassbillingfile.populateperiod",
	    		generatepassbillingfilemodel);
	  }
	
}
