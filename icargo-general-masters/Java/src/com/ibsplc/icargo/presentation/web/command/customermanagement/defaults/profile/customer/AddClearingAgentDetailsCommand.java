package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

;
/**
 * 
 * @author A-2046
 * 
 */
public class AddClearingAgentDetailsCommand extends BaseCommand {

	private static final String ADD_SUCCESS = "add_success";

	private static final String ADD_FAILURE = "add_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	private static final String BLANK = "";

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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
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
		Collection<CustomerContactVO> keyContactDetailVOs = customerVO
				.getCustomerContactVOs();
		if (keyContactDetailVOs != null && keyContactDetailVOs.size() > 0) {
			SaveCustomerCommand command = new SaveCustomerCommand();
			errors = command.updateKeyContactDetails(
					(ArrayList<CustomerContactVO>) keyContactDetailVOs, form,
					customerVO);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ADD_FAILURE;
			return;
		}
		Collection<CustomerAgentVO> clearingAgentVOs = customerVO
				.getCustomerAgentVOs();
		String[] code = form.getAgentCode();
		String[] agentStation = form.getAgentStation();
		String[] remarks = form.getAgentRemarks();
		String[] checkedExport = form.getCheckedExport().split(",");
		log.log(Log.FINE, "checkedExport.length------>" + checkedExport.length);
		String[] checkedImport = form.getCheckedImport().split(",");
		String[] checkedSales = form.getCheckedSales().split(",");
		String[] opFlag = form.getOperationAgentFlag();
		String[] agentScc = form.getAgentScc();
		
		if (clearingAgentVOs == null) {
			clearingAgentVOs = new ArrayList<CustomerAgentVO>();
		}
		if (clearingAgentVOs != null && clearingAgentVOs.size() > 0) {
			int index = 0;
			int checkIndex = 0;
			clearingAgentVOs = (ArrayList<CustomerAgentVO>) customerVO
					.getCustomerAgentVOs();
			for (CustomerAgentVO agentVO : clearingAgentVOs) {
				int noOfOccurance = 0;
				if (code != null && code.length > 0) {
					log.log(Log.FINE, "code length------------------->"
							+ code.length);

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
										agentStation[index])
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

						agentVO.setStationCode(agentStation[index].toUpperCase());
						agentVO.setRemarks(remarks[index]);
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
						agentVO.setScc(agentScc[checkIndex]);
						log.log(Log.FINE, "dxport value----------->"
								+ checkedExport[checkIndex]);
						log.log(Log.FINE, "dxport value----------->"
								+ checkedImport[checkIndex]);
						log.log(Log.FINE, "dxport value----------->"
								+ checkedSales[checkIndex]);

						checkIndex++;
					}

					if (code != null) {
						log.log(Log.FINE, "\n\ncode length------>" + code.length);
						for (int i=0;i<code.length;i++) {
							if (!OPERATION_FLAG_DELETE.equals(agentVO
									.getOperationFlag())&& !OPERATION_FLAG_DELETE.equals(opFlag[i])) {

								if (code[i].equalsIgnoreCase(agentVO
										.getAgentCode())) {
									noOfOccurance++;
								}
							}
						}
					}
					log.log(Log.FINE, "no of occurance------------------>"
							+ noOfOccurance);
					if (noOfOccurance > 1) {
						error = new ErrorVO(
								"customermanagement.defaults.customerregn.msg.err.agentdupcode");
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = ADD_FAILURE;
						return;
					}
					index++;
				}
			}

		}
		CustomerAgentVO newAgentVO = new CustomerAgentVO();
		newAgentVO.setOperationFlag(OPERATION_FLAG_INSERT);
		newAgentVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		newAgentVO.setStationCode(BLANK);
		newAgentVO.setAgentCode(BLANK);
		newAgentVO.setRemarks(BLANK);
		clearingAgentVOs.add(newAgentVO);
		customerVO.setCustomerAgentVOs(clearingAgentVOs);
		session.setCustomerVO(customerVO);
		invocationContext.target = ADD_SUCCESS;

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
