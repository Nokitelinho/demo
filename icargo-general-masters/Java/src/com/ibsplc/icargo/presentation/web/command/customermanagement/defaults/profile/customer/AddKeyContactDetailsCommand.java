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

/**
 * 
 * @author A-2046
 * 
 */
public class AddKeyContactDetailsCommand extends BaseCommand {

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
		SaveCustomerCommand command = new SaveCustomerCommand();
		Collection<CustomerAgentVO> clearingAgentVOs = customerVO
				.getCustomerAgentVOs();
		if (clearingAgentVOs != null && clearingAgentVOs.size() > 0) {
			errors = command.updateClearingAgentDetails(
					(ArrayList<CustomerAgentVO>) clearingAgentVOs, form,
					customerVO);
		}
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "************inside errors**********");
			invocationContext.addAllError(errors);
			invocationContext.target = ADD_FAILURE;
			return;
		}
		Collection<CustomerContactVO> keyContactDetailVOs = customerVO
				.getCustomerContactVOs();
		String[] code = form.getContactCode();
		String[] name = form.getContactName();
		String[] designation = form.getContactDesignation();
		String[] telephone = form.getContactTelephone();
		String[] mobile = form.getContactMobile();
		String[] fax = form.getContactFax();
		String[] sita = form.getContactFax();
		String[] email = form.getContactEmail();
		String[] remarks = form.getContactRemarks();
		String[] status = form.getCheckedStatus().split(",");
		String[] primaryContactStatus = form.getPrimaryContact().split(",");
		String[] opFlag = form.getOperationContactFlag();

		if (keyContactDetailVOs == null) {
			keyContactDetailVOs = new ArrayList<CustomerContactVO>();
		} else if (keyContactDetailVOs != null
				&& keyContactDetailVOs.size() > 0) {
			keyContactDetailVOs = (ArrayList<CustomerContactVO>) customerVO
					.getCustomerContactVOs();
			int index = 0;
			int checkIndex = 0;
			for (CustomerContactVO contactVO : keyContactDetailVOs) {
				int noOfOccurance = 0;
				/*
				 * if(code != null){ log.log(Log.FINE,"\n\ncode
				 * length------>"+code.length); /* for(String codeValue:code){
				 * if(codeValue.equals(contactVO.getContactCustomerCode())){
				 * noOfOccurance++; } } } log.log(Log.FINE,"no of
				 * occurance------------------>"+noOfOccurance);
				 * if(noOfOccurance > 1){ error = new
				 * ErrorVO("customermanagement.defaults.customerregn.msg.err.dupcode");
				 * errors.add(error); invocationContext.addAllError(errors);
				 * invocationContext.target = ADD_FAILURE; return; }
				 */

				if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())) {
					if (!OPERATION_FLAG_INSERT.equals(contactVO
							.getOperationFlag())) {
						if (hasValueChanged(contactVO.getContactCustomerCode(),
								code[index])
								|| hasValueChanged(contactVO
										.getCustomerDesignation(),
										designation[index])
								|| hasValueChanged(contactVO.getTelephone(),
										telephone[index])
								|| hasValueChanged(contactVO.getMobile(),
										mobile[index])
								|| hasValueChanged(contactVO.getFax(),
										fax[index])
								|| hasValueChanged(contactVO.getSiteAddress(),
										sita[index])
								|| hasValueChanged(contactVO.getEmailAddress(),
										email[index])
								|| hasValueChanged(contactVO.getRemarks(),
										remarks[index])
								|| hasValueChanged(contactVO.getCustomerName(),
										name[index])
								|| hasValueChanged(contactVO.getActiveStatus(),
										status[checkIndex])
								|| hasValueChanged(contactVO
										.getPrimaryUserFlag(),
										primaryContactStatus[checkIndex])) {
							if (contactVO.getOperationFlag() == null) {
								contactVO
										.setOperationFlag(OPERATION_FLAG_UPDATE);
								if (!OPERATION_FLAG_INSERT.equals(customerVO
										.getOperationFlag())) {
									customerVO
											.setOperationFlag(OPERATION_FLAG_UPDATE);
								}
							}
						}

					}
				}
				if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())) {
					if (code[index] != null && code[index].trim().length() > 0) {
						contactVO.setContactCustomerCode(code[index]
								.toUpperCase());
					}
					if (designation[index] != null
							&& designation[index].trim().length() > 0) {
						contactVO.setCustomerDesignation(designation[index]
								.toUpperCase());
					}
					contactVO.setEmailAddress(email[index]);
					contactVO.setFax(fax[index]);
					contactVO.setMobile(mobile[index]);
					contactVO.setRemarks(remarks[index]);
					contactVO.setTelephone(telephone[index]);
					contactVO.setSiteAddress(sita[index]);
					if (name[index] != null && name[index].trim().length() > 0) {
						contactVO.setCustomerName(name[index].toUpperCase());
					}
					if ("A".equals(status[checkIndex])) {
						contactVO.setActiveStatus("A");
					} else {
						contactVO.setActiveStatus("I");
					}
					if ("Y".equals(primaryContactStatus[checkIndex])) {
						contactVO.setPrimaryUserFlag("Y");
					} else {
						contactVO.setPrimaryUserFlag("N");
					}
					checkIndex++;
				}

				if (code != null) {
					log.log(Log.FINE, "\n\ncode length------>" + code.length);
					for (int i = 0; i < code.length; i++) {
						if (!OPERATION_FLAG_DELETE.equals(contactVO
								.getOperationFlag())
								&& !OPERATION_FLAG_DELETE.equals(opFlag[i])) {
							if (code[i].equalsIgnoreCase(contactVO
									.getContactCustomerCode())) {
								noOfOccurance++;
							}
						}
					}
				}
				log.log(Log.FINE, "no of occurance------------------>"
						+ noOfOccurance);
				if (noOfOccurance > 1) {
					error = new ErrorVO(
							"customermanagement.defaults.customerregn.msg.err.dupcode");
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = ADD_FAILURE;
					return;
				}

				index++;

			}

		}
		CustomerContactVO newContactVO = new CustomerContactVO();
		newContactVO.setOperationFlag(OPERATION_FLAG_INSERT);
		newContactVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		// newContactVO.setActiveStatus(BLANK);
		newContactVO.setCustomerName(BLANK);
		newContactVO.setContactCustomerCode(BLANK);
		newContactVO.setCustomerDesignation(BLANK);
		newContactVO.setEmailAddress(BLANK);
		newContactVO.setFax(BLANK);
		newContactVO.setMobile(BLANK);
		newContactVO.setRemarks(BLANK);
		newContactVO.setTelephone(BLANK);
		newContactVO.setSiteAddress(BLANK);
		keyContactDetailVOs.add(newContactVO);
		customerVO.setCustomerContactVOs(keyContactDetailVOs);
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