package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import java.io.Serializable;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mail/operations/consignmentsecuritydeclaration")
public class ConsignmentSecurityController extends AbstractActionController<ConsignmentScreeningModel>
{

  @Resource(name="consignmentsecuritydeclarationResourceBundle")
  ICargoResourceBundle consignmentsecuritydeclarationResourceBundle;

  @Override
  public ICargoResourceBundle getResourceBundle()
  {
    return consignmentsecuritydeclarationResourceBundle;
  }

  @RequestMapping("/screenloadScreeningDetails")
  public @ResponseBody ResponseVO screenloadScreeningDetails(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.screenloadScreeningDetails",
      consignmentscreeningmodel);
  }

  @RequestMapping("/listScreeningDetails")
  public @ResponseBody ResponseVO listScreeningDetails(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.listScreeningDetails", 
      consignmentscreeningmodel);
  }

  @RequestMapping("/saveScreeningDetails")
  public @ResponseBody ResponseVO saveScreeningDetails(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.saveScreeningDetails",
      consignmentscreeningmodel);
  }
	@RequestMapping("/deleteScreeningDetails")
	public @ResponseBody ResponseVO deleteScreeningDetails(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignmentsecuritydeclaration.deleteScreeningDetails",
				consignmentscreeningmodel); 
	}
  @RequestMapping("/printScreeningDetails")
  public @ResponseBody ResponseVO printScreeningDetails(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.printScreeningDetails",
      consignmentscreeningmodel);
  }
  @RequestMapping("/additionalsecurityinfo")
  public @ResponseBody ResponseVO additionalSecurityInfo(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.additionalSecurityInfo",
      consignmentscreeningmodel);
  }
  @RequestMapping("/saveSecurityStatusCode")
  public @ResponseBody ResponseVO saveSecurityStatusCode(@RequestBody ConsignmentScreeningModel consignmentscreeningmodel) throws BusinessDelegateException, SystemException {
    return performAction("mail.operations.ux.consignmentsecuritydeclaration.saveSecurityStatusCode",
      consignmentscreeningmodel);
  }
}