/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1747
 *
 */
public class DeleteRowCommand extends BaseCommand {

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();
		Collection<String> awbTypes = session.getAWBTypes();
		String expDate = form.getExpiryDate();
		ReserveAWBVO reserveAWBVO = new ReserveAWBVO();
		String[] rowId = form.getRowId();
		String[] awbs = form.getAwbNumber();
		String[] awp = form.getAwbPrefix();
		String[] awbNum = new String[awbs.length];
		Collection<String> awbvals = new ArrayList<String>();
		if (rowId != null && awbs != null) {
			for (int i = 0; i < rowId.length; i++) {
				awbs[Integer.valueOf(rowId[i])] = "D";
			}
		}
		for (String awb : awbs) {
			if (!("D").equals(awb)) {
				awbvals.add(awb);
			}
		}
		reserveAWBVO.setSpecificDocNumbers(awbvals);
		reserveAWBVO.setShipmentPrefix(awp[0]);
		session.setReserveAWBVO(reserveAWBVO);
		session.setAWBTypes(awbTypes);
		form.setExpiryDate(expDate);
		invocationContext.target = "deleterow_success";
	}

}
