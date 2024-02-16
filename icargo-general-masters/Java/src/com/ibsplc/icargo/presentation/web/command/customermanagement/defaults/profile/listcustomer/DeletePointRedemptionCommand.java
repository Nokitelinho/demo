package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
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
public class DeletePointRedemptionCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String OPERATION_FLAG_INS_DEL = "operation_flg_insert_delete";

	private Log log = LogFactory.getLogger("DeletePointRedemptionCommand");

	private static final String BLANK = "";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeletePointRedemptionCommand", "ENTER");
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
		String[] rowIds = actionForm.getSelectedRows();
		log.log(Log.FINE, "\n\n rowIds----------->", rowIds);
		if (customerContactPointsVOs != null
				&& customerContactPointsVOs.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n INSIDE LOOP ");
			String[] service = actionForm.getServicePointRedemption();
			String[] point = actionForm.getPointsRedemption();
			String[] redeemedTo = actionForm.getRedeemedTo();
			/*
			 * double currentPointsToBeRedeemed = Double.parseDouble(actionForm
			 * .getPointsRedmdTo());
			 *  /* if (BLANK.equals(point[point.length - 1])) {
			 * point[point.length - 1] = "0.0"; } if
			 * (Double.parseDouble(point[point.length - 1]) >
			 * currentPointsToBeRedeemed) { Object[] points = new Object[1];
			 * points[0] = currentPointsToBeRedeemed; error = new ErrorVO(
			 * "customermanagement.defaults.customerlisting.msg.err.cannotredeempoints",
			 * points); errors.add(error);
			 * invocationContext.addAllError(errors); invocationContext.target =
			 * SCREENLOAD_SUCCESS; return; }
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

		ArrayList<CustomerContactPointsVO> customerContactPointsvotmp = new ArrayList<CustomerContactPointsVO>();

		log.log(Log.FINE, "\n\n customerContactPointsvotmp--------->>>",
				customerContactPointsvotmp);
		log.log(Log.FINE, "\n\n customerContactPtsVOs-------->>>>",
				customerContactPtsVOs);
		if (rowIds != null) {
			int index = 0;
			for (CustomerContactPointsVO customerContactPointsvo : customerContactPtsVOs) {
				for (int i = 0; i < rowIds.length; i++) {
					if (index == Integer.parseInt(rowIds[i])) {

						log
								.log(
										Log.FINE,
										"\n\n\n\n customerContactPointsvo inside for loop ---> ",
										customerContactPointsvo);
						log
								.log(
										Log.FINE,
										"\n\n\n\n customerContactPointsvo.getOperationalFlag() inside for loop ---> ",
										customerContactPointsvo
												.getOperationFlag());
						if (customerContactPointsvo.getOperationFlag() != null
								&& !customerContactPointsvo
										.getOperationFlag()
										.equals(
												AbstractVO.OPERATION_FLAG_INSERT)) {
							customerContactPointsvo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (customerContactPointsvo.getOperationFlag() == null) {
							customerContactPointsvo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (customerContactPointsvo.getOperationFlag() != null
								&& customerContactPointsvo
										.getOperationFlag()
										.equals(
												AbstractVO.OPERATION_FLAG_INSERT)) {
							customerContactPointsvo
									.setOperationFlag(OPERATION_FLAG_INS_DEL);
						}
						log
								.log(
										Log.FINE,
										"\n\n\n\n customerContactPointsvo.getOperationFlag() after change ---> ",
										customerContactPointsvo
												.getOperationFlag());
					}
				}
				index++;
			}
		}
		for (CustomerContactPointsVO customerContactPointsVOTmp : customerContactPtsVOs) {
			log
					.log(
							Log.FINE,
							"\n\n\n\n customerContactPointsVOTmp.getOperationalFlag() before change ---> ",
							customerContactPointsVOTmp.getOperationFlag());
			if (customerContactPointsVOTmp.getOperationFlag() != null
					&& !customerContactPointsVOTmp.getOperationFlag().equals(
							OPERATION_FLAG_INS_DEL)) {
				customerContactPointsvotmp.add(customerContactPointsVOTmp);
			}
			if (customerContactPointsVOTmp.getOperationFlag() == null) {
				customerContactPointsvotmp.add(customerContactPointsVOTmp);
			}
			log
					.log(
							Log.FINE,
							"\n\n\n\n customerContactPointsVOTmp.getOperationalFlag() after change ---> ",
							customerContactPointsVOTmp.getOperationFlag());
		}

		double points = 0.0;
		for (CustomerContactPointsVO contactVO : customerContactPointsvotmp) {
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
		log.log(Log.FINE, "customerContactPointsvotmp--------->>>>",
				customerContactPointsvotmp);
		session.setCustomerContactPointsVOs(customerContactPointsvotmp);
		log.exiting("DeletePointRedemptionCommand", "EXIT");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
