package com.ibsplc.icargo.presentation.web.controller.mail.mra.receivablemanagement.listsettlementbatch;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.ListSettlementBatchModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Controller
@RequestMapping("mail/mra/receivablemanagement/listsettlementbatch")
public class ListSettlementBatchActionController extends AbstractActionController<ListSettlementBatchModel>{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");



	@Resource(name="listSettlementBatchResourceBundle")
	ICargoResourceBundle listSettlementBatchResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		log.log(Log.FINE, "Inside  getResourceBundle");
		return listSettlementBatchResourceBundle;
	}

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadListSettlementBatch(@RequestBody ListSettlementBatchModel listSettlementBatchModel)
			throws BusinessDelegateException, SystemException{
		log.log(Log.FINE, "Inside  screenloadListSettlementBatch");
		return performAction("mail.mra.ux.listsettlementbatch.screenload",listSettlementBatchModel);
	}

	@RequestMapping("/listsettlementbatches")
	public @ResponseBody ResponseVO listSettlementbatches(@RequestBody ListSettlementBatchModel listSettlementBatchModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.ux.listsettlementbatch.listsettlementbatches",listSettlementBatchModel);
	}

	@RequestMapping("/listsettlementbatchdetails")
	public @ResponseBody ResponseVO listsettlementbatchdetails(@RequestBody ListSettlementBatchModel listSettlementBatchModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.ux.listsettlementbatch.listsettlementbatchdetails",listSettlementBatchModel);
	}
	@RequestMapping("/clearbatch")
	public @ResponseBody ResponseVO clearbatch(@RequestBody ListSettlementBatchModel listSettlementBatchModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.ux.listsettlementbatch.clearbatch",listSettlementBatchModel);
	}  

}
