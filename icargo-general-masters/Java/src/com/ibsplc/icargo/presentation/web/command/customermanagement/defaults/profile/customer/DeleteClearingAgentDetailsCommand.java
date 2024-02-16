package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteClearingAgentDetailsCommand extends BaseCommand {

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("CUSTOMER");
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);
		CustomerVO customerVO = session.getCustomerVO();
		if ("on".equals(form.getGlobalCustomer())) {
			customerVO.setGlobalCustomerFlag("Y");

		} else {
			customerVO.setGlobalCustomerFlag("N");
		}
		if (customerVO.getGlobalCustomerFlag() != null
				&& "Y".equals(customerVO.getGlobalCustomerFlag())) {
			form.setGlobalCustomer("on");
		}
		Collection<CustomerAgentVO> clearingAgentVOs = customerVO
				.getCustomerAgentVOs();
		String[] code = form.getAgentCode();
		String[] agetnStation = form.getAgentStation();
		String[] remarks = form.getAgentRemarks();
		String[] checkedExport = form.getCheckedExport().split(",");
		log.log(Log.FINE, "checkedExport.length------>" + checkedExport.length);
		String[] checkedImport = form.getCheckedImport().split(",");
		String[] checkedSales = form.getCheckedSales().split(",");
		String[] agentScc = form.getAgentScc();
		if (clearingAgentVOs != null && clearingAgentVOs.size() > 0) {
			int index = 0;
			int checkIndex = 0;
			for (CustomerAgentVO agentVO : clearingAgentVOs) {
				/*
				 * int noOfOccurance = 0; log.log(Log.FINE, "code
				 * length------------------->" + code.length); /* if (code !=
				 * null) { log.log(Log.FINE, "\n\ncode length------>" +
				 * code.length); for (String codeValue : code) { if
				 * (codeValue.equals(agentVO.getAgentCode())) { noOfOccurance++; } } }
				 * log.log(Log.FINE, "no of occurance------------------>" +
				 * noOfOccurance); /* if(noOfOccurance > 1){ error = new
				 * ErrorVO("customermanagement.defaults.customerregn.msg.err.agentdupcode");
				 * errors.add(error); invocationContext.addAllError(errors);
				 * invocationContext.target = DELETE_FAILURE; return; }
				 */
				if (!OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag())
						&& !OPERATION_FLAG_DELETE.equals(agentVO
								.getOperationFlag())) {

					log.log(Log.FINE, "dxport value--**--------->"
							+ checkedExport[checkIndex]);
					log.log(Log.FINE, "dxport value---**-------->"
							+ checkedImport[checkIndex]);
					log.log(Log.FINE, "dxport value----**------->"
							+ checkedSales[checkIndex]);
					log.log(Log.FINE, "agentVO-----**------>" + agentVO);

					if (hasValueChanged(agentVO.getAgentCode(), code[index])
							|| hasValueChanged(agentVO.getRemarks(),
									remarks[index])
							|| hasValueChanged(agentVO.getStationCode(),
									agetnStation[index])
							|| hasValueChanged(agentVO.getExportFlag(),
									checkedExport[index])
							|| hasValueChanged(agentVO.getImportFlag(),
									checkedImport[index])
							|| hasValueChanged(agentVO.getSalesFlag(),
									checkedSales[index]))

					{
						if (agentVO.getOperationFlag() == null) {
							agentVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							if (!OPERATION_FLAG_INSERT.equals(customerVO
									.getOperationFlag())) {
								customerVO
										.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}

					}
				}

				if (!OPERATION_FLAG_DELETE.equals(agentVO.getOperationFlag())) {
					if (code[index] != null && code[index].trim().length() > 0) {
						agentVO.setAgentCode(code[index].toUpperCase());
					}
					agentVO.setRemarks(remarks[index]);
					agentVO.setStationCode(agetnStation[index].toUpperCase());

					if ("Y".equals(checkedExport[checkIndex])) {

						agentVO.setExportFlag("Y");

					} else {
						agentVO.setExportFlag("N");
					}
					if ("Y".equals(checkedImport[checkIndex])) {

						agentVO.setImportFlag("Y");

					} else {
						agentVO.setImportFlag("N");
					}
					if ("Y".equals(checkedSales[checkIndex])) {

						agentVO.setSalesFlag("Y");

					} else {
						agentVO.setSalesFlag("N");
					}
					agentVO.setScc(agentScc[index]);
					log.log(Log.FINE, "dxport value----------->"
							+ checkedExport[checkIndex]);
					log.log(Log.FINE, "dxport value----------->"
							+ checkedImport[checkIndex]);
					log.log(Log.FINE, "dxport value----------->"
							+ checkedSales[checkIndex]);

					checkIndex++;
				}
				index++;

			}

			int indexVal = 0;
			Collection<CustomerAgentVO> clearingAgentVOsToRemove = new ArrayList<CustomerAgentVO>();
			String[] selected = form.getSelectedClearingAgentDetails();
			if (selected != null) {
				log.log(Log.FINE, "selected length_-------------------->"+ selected.length);
				for (CustomerAgentVO clearingAgentVO : clearingAgentVOs) {
					for (int i = 0; i < selected.length; i++) {
						log.log(Log.FINE, "selected index---------->"
								+ selected[i]);
						if (indexVal == Integer.parseInt(selected[i])) {

							if (OPERATION_FLAG_INSERT.equals(clearingAgentVO
									.getOperationFlag())) {
								clearingAgentVOsToRemove.add(clearingAgentVO);
							} else {
								clearingAgentVO
										.setOperationFlag(OPERATION_FLAG_DELETE);
								if (!OPERATION_FLAG_INSERT.equals(customerVO
										.getOperationFlag())) {
									customerVO
											.setOperationFlag(OPERATION_FLAG_UPDATE);
								}
							}
						}
					}
					indexVal++;
				}
				log.log(Log.FINE, "vos to be removed---------->"
						+ clearingAgentVOsToRemove);

				if (clearingAgentVOsToRemove != null
						&& clearingAgentVOsToRemove.size() > 0) {
					clearingAgentVOs.removeAll(clearingAgentVOsToRemove);
				}
			}

		}

		invocationContext.target = DELETE_SUCCESS;

	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equals(formValue)) {
				return false;
			}

		}
		return true;
	}
}
