package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/consignment")
public class MaintainConsignmentController extends AbstractActionController<MaintainConsignmentModel> {

	@Resource(name = "consignmentResourceBundle")
	ICargoResourceBundle consignmentResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		// TODO Auto-generated method stub
		return consignmentResourceBundle;
	}

	@RequestMapping("/list")
	public @ResponseBody ResponseVO listConsignment(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.listconsignment", maintainConsignmentModel);
	}
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadConsignment(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.screenload", maintainConsignmentModel);
	}
	@RequestMapping("/deleteConsignment")
	public @ResponseBody ResponseVO deleteConsignment(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.deleteConsignment", maintainConsignmentModel);
	}
	@RequestMapping("/printconsignment")
	public @ResponseBody ResponseVO printConsignment(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.printConsignment", maintainConsignmentModel);
	}
	@RequestMapping("/printmailtag")
	public @ResponseBody ResponseVO printmailTag(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.printmailtag", maintainConsignmentModel);
	}
	@RequestMapping("/save")
	public @ResponseBody ResponseVO save(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.save", maintainConsignmentModel);
	}
	//Added by A-8527 for IASCB-57081
	@RequestMapping("/fetcharpcodes")
	public @ResponseBody ResponseVO fetcharpcodes(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.fetcharpcodes", maintainConsignmentModel);
	}
	@RequestMapping("/printconsignmentsummary")
	public @ResponseBody ResponseVO printConsignmentSummary(@RequestBody MaintainConsignmentModel maintainConsignmentModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.consignment.printConsignmentSummary", maintainConsignmentModel);
	}
	

}
