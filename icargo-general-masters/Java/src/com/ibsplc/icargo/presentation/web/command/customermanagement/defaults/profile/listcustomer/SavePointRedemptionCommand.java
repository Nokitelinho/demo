package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

;
/**
 * 
 * @author A-2052
 * 
 */
public class SavePointRedemptionCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";


	private Log log = LogFactory.getLogger("SavePointRedemptionCommand");

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SavePointRedemptionCommand", "ENTER");
		ListCustomerForm actionForm = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		ArrayList<CustomerContactPointsVO> customerContactPointsVOs = session
				.getCustomerContactPointsVOs() != null ? new ArrayList<CustomerContactPointsVO>(
				session.getCustomerContactPointsVOs())
				: new ArrayList<CustomerContactPointsVO>();
		log.log(Log.FINE, "\n\n\n\n customerContactPointsVOs ---> ",
				customerContactPointsVOs);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		Collection<CustomerContactPointsVO> customerContactPtsVOs = new ArrayList<CustomerContactPointsVO>();
		if (customerContactPointsVOs != null
				&& customerContactPointsVOs.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n INSIDE LOOP ");
			String[] service = actionForm.getServicePointRedemption();
			String[] point = actionForm.getPointsRedemption();
			String[] redeemedTo = actionForm.getRedeemedTo();

			/*
			 * double currentPointsToBeRedeemed = Double.parseDouble(actionForm
			 * .getPointsRedmdTo()); /* if (BLANK.equals(point[point.length -
			 * 1])) { point[point.length - 1] = "0.0"; } if
			 * (Double.parseDouble(point[point.length - 1]) >
			 * currentPointsToBeRedeemed) { error = new ErrorVO(
			 * "customermanagement.defaults.customerlisting.msg.err.cannotredeempoints");
			 * errors.add(error); invocationContext.addAllError(errors);
			 * invocationContext.target = SCREENLOAD_SUCCESS; return; }
			 */

			int index = 0;
			for (CustomerContactPointsVO customerContactPointsVO : customerContactPointsVOs) {

				if ((customerContactPointsVO.getOperationFlag() != null && !customerContactPointsVO
						.getOperationFlag().equals(
								AbstractVO.OPERATION_FLAG_DELETE))
						|| customerContactPointsVO.getOperationFlag() == null) {

					if (!customerContactPointsVO.getService().equals(
							service[index])
							|| !customerContactPointsVO
									.getCustomerContactCode().equals(
											redeemedTo[index])
							|| customerContactPointsVO.getPoints() != Double
									.parseDouble(point[index])) {
						if (customerContactPointsVO.getOperationFlag() == null) {
							customerContactPointsVO
									.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
						}
					}
					if (service[index] != null
							&& service[index].trim().length() != 0) {
						customerContactPointsVO.setService(service[index]
								.toUpperCase());
					}
					if (redeemedTo[index] != null
							&& redeemedTo[index].trim().length() != 0) {
						customerContactPointsVO
								.setCustomerContactCode(redeemedTo[index]);
					}
					if (point[index] != null
							&& point[index].trim().length() != 0) {
						customerContactPointsVO.setPoints(Double
								.parseDouble(point[index]));
					}
				}
				customerContactPtsVOs.add(customerContactPointsVO);
				index++;
			}
		}

		double listedPoints = Double.parseDouble(session.getListedPoints());

		log.log(Log.FINE, "listed points---------------->", listedPoints);
		double totalPoints = 0.0;
		for (CustomerContactPointsVO pointsVO : customerContactPointsVOs) {
			if (!OPERATION_FLAG_DELETE.equals(pointsVO.getOperationFlag())) {
				totalPoints += pointsVO.getPoints();
			}
		}
		log.log(Log.FINE, "total points---------------->", totalPoints);
		double originalPtsToBeRedeemed = Double.parseDouble(session
				.getPointsForValidation());
		if (listedPoints + originalPtsToBeRedeemed < totalPoints) {
			error = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.maxpointsinvalid");
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;

		}
		session.setCustomerContactPointsVOs(customerContactPtsVOs);
		String custCode = actionForm.getCustomerCodePointRdmd();
		log.log(Log.FINE, "custCode-------->>", custCode);
		log.log(Log.FINE, "session.getCustomerContactPointsVOs()--------->>>",
				session.getCustomerContactPointsVOs());
		Collection<CustomerContactPointsVO> col = session
				.getCustomerContactPointsVOs();
		if (col != null) {
			for (CustomerContactPointsVO vo : col) {
				vo.setCustomerCode(custCode);
			}
		}
		log.log(Log.FINE, "vos before setting to delegate ---->>>>>>>>>",
				customerContactPtsVOs);
		try {
			delegate.saveCustomerContactPoints(customerContactPtsVOs);
		} catch (BusinessDelegateException e) {
			// To be reviewed Auto-generated catch block
//printStackTrrace()();
			handleDelegateException(e);
		}
		log.log(Log.FINE, "sessions setting to null...........");
		session.removeService();
		session.setCustomerContactPointsVOs(null);
		session.setCustomerCodes(null);
		session.setCustomerNames(null);
		actionForm.setFlagPointRedemption("pointRedemtion");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
