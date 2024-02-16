package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
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
public class DeleteKeyContactDetailsCommand extends BaseCommand {

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
		String[] status = form.getCheckedStatus().split(",");
		String[] primaryContactStatus = form.getPrimaryContact().split(",");
		String[] remarks = form.getContactRemarks();
		if (keyContactDetailVOs != null && keyContactDetailVOs.size() > 0) {
			int index = 0;
			int checkIndex = 0;
			keyContactDetailVOs = (ArrayList<CustomerContactVO>) customerVO
					.getCustomerContactVOs();
			for (CustomerContactVO contactVO : keyContactDetailVOs) {
				/*
				 * if(index != code.length-1 && code.length-1>0){
				 * if(!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())){
				 * if(code[code.length-1].equals(contactVO.getContactCustomerCode())){
				 * error = new
				 * ErrorVO("customermanagement.defaults.customerregn.msg.err.dupcode");
				 * errors.add(error); invocationContext.addAllError(errors);
				 * invocationContext.target = ADD_FAILURE; return; } } }
				 */
				int noOfOccurance = 0;
				if (code != null) {
					log.log(Log.FINE, "code length------------------->"+ code.length);
					log.log(Log.FINE, "\n\ncode length------>" + code.length);
					for (String codeValue : code) {
						if (codeValue
								.equals(contactVO.getContactCustomerCode())) {
							noOfOccurance++;
						}
					}
					/*
					 * log.log(Log.FINE,"no of
					 * occurance------------------>"+noOfOccurance);
					 * if(noOfOccurance > 1){ error = new
					 * ErrorVO("customermanagement.defaults.customerregn.msg.err.dupcode");
					 * errors.add(error); invocationContext.addAllError(errors);
					 * invocationContext.target = DELETE_FAILURE; return; }
					 */
					if (!OPERATION_FLAG_INSERT.equals(contactVO.getOperationFlag())
							&& !OPERATION_FLAG_DELETE.equals(contactVO
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
								|| hasValueChanged(contactVO.getFax(), fax[index])
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
								|| hasValueChanged(contactVO.getPrimaryUserFlag(),
										primaryContactStatus[checkIndex])) {
							if (contactVO.getOperationFlag() == null) {
								contactVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}
					}

					if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())) {
						contactVO.setContactCustomerCode(code[index]);
						contactVO.setCustomerDesignation(designation[index]);
						contactVO.setEmailAddress(email[index]);
						contactVO.setFax(fax[index]);
						contactVO.setMobile(mobile[index]);
						contactVO.setRemarks(remarks[index]);
						contactVO.setTelephone(telephone[index]);
						contactVO.setSiteAddress(sita[index]);
						contactVO.setActiveStatus(status[checkIndex]);
						contactVO
								.setPrimaryUserFlag(primaryContactStatus[checkIndex]);
						contactVO.setCustomerName(name[index]);
						checkIndex++;
					}
					index++;
				}
			}

			int indexVal = 0;
			Collection<CustomerContactVO> contactVOsToRemove = new ArrayList<CustomerContactVO>();
			String[] selected = form.getSelectedContactDetails();
			for (CustomerContactVO custContactVO : keyContactDetailVOs) {
				for (int i = 0; i < selected.length; i++) {
					if (indexVal == Integer.parseInt(selected[i])) {
						if (OPERATION_FLAG_INSERT.equals(custContactVO
								.getOperationFlag())) {
							contactVOsToRemove.add(custContactVO);
						} else {
							custContactVO
									.setOperationFlag(OPERATION_FLAG_DELETE);
							customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						}

					}
				}
				indexVal++;
			}
			log.log(Log.FINE, "contact vos to be removed_-------------->"
					+ contactVOsToRemove);

			if (contactVOsToRemove != null && contactVOsToRemove.size() > 0) {
				keyContactDetailVOs.removeAll(contactVOsToRemove);
			}
			log.log(Log.FINE, "contact vos after removal-------->"
					+ keyContactDetailVOs);
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
