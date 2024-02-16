/*
 * DeleteULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.uldpoolowners;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDPoolOwnersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteULDPoolOwnersCommand extends BaseCommand {

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Delete Command", "ULD POOL OWNERS");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;		
		
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);

		Collection<ULDPoolOwnerDetailsVO> poolDetailVOs = session
				.getUldPoolOwnerVO().getPoolDetailVOs();
		if (poolDetailVOs != null && poolDetailVOs.size() > 0) {
			updateULDPoolOwnerDetails(poolDetailVOs, form,session.getUldPoolOwnerVO());
		}
		Collection<ULDPoolOwnerDetailsVO> vosToRemove = new ArrayList<ULDPoolOwnerDetailsVO>();
		String[] checked = form.getSelectedRows();
		int index = 0;
		for (ULDPoolOwnerDetailsVO detailsVO : poolDetailVOs) {
			for (int i = 0; i < checked.length; i++) {
				if (index == Integer.parseInt(checked[i])) {
					if (OPERATION_FLAG_INSERT.equals(detailsVO
							.getOperationFlag())) {
						vosToRemove.add(detailsVO);
					} else {
						detailsVO.setOperationFlag(OPERATION_FLAG_DELETE);
					}
				}
			}
			index++;
		}
		log.log(Log.FINE, "vos to remove------------->", vosToRemove);
		if (vosToRemove != null && vosToRemove.size() > 0) {
			poolDetailVOs.removeAll(vosToRemove);
		}
		log
				.log(Log.FINE, "\n\n\nVOS ------------------------->",
						poolDetailVOs);
		session.getUldPoolOwnerVO().setPoolDetailVOs(poolDetailVOs);
		invocationContext.target = DELETE_SUCCESS;
	}
	
/**
 * 
 * @param poolOwnerDetailsVOs
 * @param form
 * @param poolOwnerVO
 */
	public void updateULDPoolOwnerDetails(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form,ULDPoolOwnerVO poolOwnerVO) {
		String[] polAirline = form.getAirlineOwn();
		String[] polFlights = form.getFlightOwn();
		String[] fromDate = form.getFromDate();
		String[] toDate = form.getToDate();
		int index = 0;
		for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
			if (detailsVO.getOperationFlag() == null) {
				if (hasValueChanged(detailsVO.getPolAirlineCode(),
						polAirline[index].toUpperCase())
						|| hasValueChanged(detailsVO.getPolFligthNumber(),
								polFlights[index].toUpperCase())
						|| hasValueChanged(detailsVO.getFromDate()
								.toDisplayDateOnlyFormat(), fromDate[index])
						|| hasValueChanged(detailsVO.getToDate()
								.toDisplayDateOnlyFormat(), toDate[index])) {
					detailsVO
							.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
					poolOwnerVO.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
				}
			}
				if (!ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE
						.equals(detailsVO.getOperationFlag())) {
					detailsVO
							.setPolAirlineCode(polAirline[index].toUpperCase());
					detailsVO.setPolFligthNumber(polFlights[index]
							.toUpperCase());
					if (fromDate[index] != null
							&& fromDate[index].trim().length() > 0) {
						LocalDate polFromDate = new LocalDate(
								getApplicationSession().getLogonVO()
										.getAirportCode(), Location.ARP, false);
						polFromDate.setDate(fromDate[index]);
						detailsVO.setFromDate(polFromDate);
					}

					if (toDate[index] != null
							&& toDate[index].trim().length() > 0) {
						LocalDate polToDate = new LocalDate(
								getApplicationSession().getLogonVO()
										.getAirportCode(), Location.ARP, false);
						polToDate.setDate(toDate[index]);
						detailsVO.setToDate(polToDate);

					}
				}
		
			index++;
		}

	}
	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equalsIgnoreCase(formValue)) {
				return false;
			}

		}
		return true;
	}

}
