package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.operations.shipment.DuplicateAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.operations.shipment.DuplicateAWBActionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ListDuplicateAWBCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String MODULE_AWB = "operations.shipment";

	/*
	 * The ScreenID for Duplicate AWB screen
	 */
	private static final String SCREEN_ID_DAWB = "operations.shipment.duplicateawb";
	
	private static final String ACTION_STATUS = "customerlisting_points";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * Exceute method which executes actions correponding to ListDuplicateAWB
	 * command in captureHAWB
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListDuplicateAWBCommand", "execute");

		ListCustomerSession session = (ListCustomerSession) getScreenSession(
				MODULE, SCREENID);
		DuplicateAWBSession duplicateAWBSession = (DuplicateAWBSession) getScreenSession(
				MODULE_AWB, SCREEN_ID_DAWB);
		DuplicateAWBActionForm actionForm = (DuplicateAWBActionForm) invocationContext.screenModel;
		String[] selectedShipmentVO = actionForm.getRowId();
		log.log(Log.FINE, "selectedShipmentVO ---> ", selectedShipmentVO);
		ArrayList<ShipmentVO> shipmentVOs = (ArrayList<ShipmentVO>) duplicateAWBSession
				.getShipmentVOs();
		log.log(Log.FINE, "shipmentVOs---> ", shipmentVOs);
		ShipmentVO shipmentVO = shipmentVOs.get(Integer
				.parseInt(selectedShipmentVO[0]));
		shipmentVOs.clear();
		shipmentVOs.add(shipmentVO);
		session.setShipmentVOs(shipmentVOs);
		actionForm.setStatusFlag(ACTION_STATUS);
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListDuplicateAWBCommand", "execute");

	}
}
