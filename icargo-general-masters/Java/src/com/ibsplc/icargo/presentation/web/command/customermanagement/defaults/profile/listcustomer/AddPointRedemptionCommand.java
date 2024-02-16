package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2052
 * 
 */
public class AddPointRedemptionCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("AddPointRedemptionCommand");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddPointRedemptionCommand", "ENTER");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();
		ListCustomerForm actionForm = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ArrayList<CustomerContactPointsVO> customerContactPointsVOs = session
				.getCustomerContactPointsVOs() != null ? new ArrayList<CustomerContactPointsVO>(
				session.getCustomerContactPointsVOs())
				: new ArrayList<CustomerContactPointsVO>();
		log.log(Log.FINE, "\n\n\n\n customerContactPointsVOs ---> ",
				customerContactPointsVOs);
		Collection<CustomerContactPointsVO> customerContactPtsVOs = new ArrayList<CustomerContactPointsVO>();
		if (customerContactPointsVOs != null
				&& customerContactPointsVOs.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n INSIDE LOOP ");
			String[] service = actionForm.getServicePointRedemption();
			String[] point = actionForm.getPointsRedemption();
			String[] redeemedTo = actionForm.getRedeemedTo();
			double currentPointsToBeRedeemed = Double.parseDouble(actionForm
					.getPointsRedmdTo());
			log.log(Log.FINE, "\n\n\nPOINTS TO BE REDEEMED------------------>",
					currentPointsToBeRedeemed);
			log.log(Log.FINE, "\n\n\nlast added point---------------->", point,
					point.length);
			/*
			 * if (BLANK.equals(point[point.length - 1])) { point[point.length -
			 * 1] = "0.0"; } if (Double.parseDouble(point[point.length - 1]) >
			 * currentPointsToBeRedeemed) { Object[] points = new Object[1];
			 * points[0] = currentPointsToBeRedeemed; error = new ErrorVO(
			 * "customermanagement.defaults.customerlisting.msg.err.cannotredeempoints",
			 * points); errors.add(error);
			 * invocationContext.addAllError(errors); invocationContext.target =
			 * SCREENLOAD_SUCCESS; return;
			 *  }
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
		log.log(Log.FINE,
				"\n\n\nsession.getListedPoints()---------------------->",
				session.getListedPoints());
		double listedPoints = Double.parseDouble(session.getListedPoints());
		double originalPtsToBeRedeemed = Double.parseDouble(session
				.getPointsForValidation());
		log.log(Log.FINE, "listed points---------------->", listedPoints);
		double totalPoints = 0.0;

		for (CustomerContactPointsVO pointsVO : customerContactPointsVOs) {
			if (!OPERATION_FLAG_DELETE.equals(pointsVO.getOperationFlag())) {
				totalPoints += pointsVO.getPoints();
			}
		}

		log.log(Log.FINE, "originalPtsToBeRedeemed---------------->",
				originalPtsToBeRedeemed);
		log.log(Log.FINE, "total points---------------->", totalPoints);
		if (listedPoints + originalPtsToBeRedeemed < totalPoints) {
			error = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.maxpointsinvalid");
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;

		}

		double points = 0.0;
		for (CustomerContactPointsVO contactVO : customerContactPtsVOs) {
			if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())) {
				points += contactVO.getPoints();
			}
		}

		double pointsAccruded = Double.parseDouble(actionForm
				.getPointsAccruded());
		double newPointsToBeRedeemed = pointsAccruded - points;
		actionForm.setPointsRedmdTo(String.valueOf(newPointsToBeRedeemed));

		log.log(Log.FINE, "Current total points redeemed*********", points);
		log.log(Log.FINE, "New points to be redeemed*********",
				newPointsToBeRedeemed);
		CustomerContactPointsVO newCustomerContactPointsVO = new CustomerContactPointsVO();
		newCustomerContactPointsVO
				.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
		newCustomerContactPointsVO.setCompanyCode(compCode);
		newCustomerContactPointsVO.setService("");
		newCustomerContactPointsVO.setCustomerContactCode("");
		newCustomerContactPointsVO.setPoints(0.0);
		customerContactPtsVOs.add(newCustomerContactPointsVO);
		log
				.log(
						Log.FINE,
						"customerContactPtsVOs before setting to session---------->>>>>>>",
						customerContactPtsVOs);
		session.setCustomerContactPointsVOs(customerContactPtsVOs);
		log.exiting("AddPointRedemptionCommand", "EXIT");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
