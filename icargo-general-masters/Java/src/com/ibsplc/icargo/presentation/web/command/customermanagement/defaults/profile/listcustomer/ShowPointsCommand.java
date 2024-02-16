package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ShowPointsCommand extends BaseCommand {

	private static final String SHOW_SUCCESS = "show_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customerlisting", "ADD COMMAND");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		AirWayBillLoyaltyProgramFilterVO awbFilterVO = session.getAwbFilterVO();
		if (form.getLoyaltyProgrammePtAcc() != null
				&& form.getLoyaltyProgrammePtAcc().trim().length() > 0) {
			if (awbFilterVO != null) {
				if( "ALL".equals(form.getLoyaltyProgrammePtAcc())){
				awbFilterVO
						.setLoyaltyProgramee("");
				}else{
					awbFilterVO
					.setLoyaltyProgramee(form.getLoyaltyProgrammePtAcc());
				}
			}
		}/*
			 * else { error = new ErrorVO(
			 * "customermanagement.defaults.customerlisting.msg.err.noloyaltyselected");
			 * errors.add(error); invocationContext.addAllError(errors);
			 * invocationContext.target = SHOW_SUCCESS; return;
			 *  }
			 */
		ArrayList<AirWayBillLoyaltyProgramVO> loyaltyVOs = null;
		try {
			loyaltyVOs = (ArrayList<AirWayBillLoyaltyProgramVO>) delegate
					.showPoints(awbFilterVO);
		} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			handleDelegateException(ex);

		}

		session.setAwbLoyaltyVos(loyaltyVOs);
		invocationContext.target = SHOW_SUCCESS;
	}
}
