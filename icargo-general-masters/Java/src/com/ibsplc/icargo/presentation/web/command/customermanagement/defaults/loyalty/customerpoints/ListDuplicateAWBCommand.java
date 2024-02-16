package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.customerpoints;

import java.util.ArrayList;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.operations.shipment.DuplicateAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.operations.shipment.DuplicateAWBActionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-2052
 *
 */
public class ListDuplicateAWBCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("LIST CUSTOMER POINTS");

    private static final String MODULENAME = "customermanagement.defaults";

    private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

	private static final String MODULE_AWB = "operations.shipment";

	/*
	 * The ScreenID for Duplicate AWB screen
	 */
	private static final String SCREEN_ID_DAWB = "operations.shipment.duplicateawb";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String LIST_SUCCESS =	"list_success";

	private static final String AWB_VIEWCUSTOMERPOINTS =	"action_viewcustomerpoints";
//	private static final String ACTION_DNREGISTER = "action_regclaims";


	/**
	 * Exceute method which executes actions correponding
	 * to ListDuplicateAWB command in captureHAWB
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListDuplicateAWBCommand","execute");

		ListCustomerPointsSession session = getScreenSession(MODULENAME,SCREENID);
		DuplicateAWBSession duplicateAWBSession
				= (DuplicateAWBSession) getScreenSession(MODULE_AWB,SCREEN_ID_DAWB);
	/**
	 * Obtain the logonAttributes
	 */
		DuplicateAWBActionForm actionForm
				= (DuplicateAWBActionForm)invocationContext.screenModel;
		String[] selectedShipmentVO = actionForm.getRowId();
		log.log(Log.FINE, "selectedShipmentVO ---> ", selectedShipmentVO);
		ArrayList<ShipmentVO> shipmentVOs =
					(ArrayList<ShipmentVO>)duplicateAWBSession.getShipmentVOs();
		log.log(Log.FINE, "shipmentVOs---> ", shipmentVOs);
		ShipmentVO shipmentVO = shipmentVOs.get(Integer.parseInt(selectedShipmentVO[0]));
		shipmentVOs.clear();
		shipmentVOs.add(shipmentVO);
		session.setShipmentVOs(shipmentVOs);
		actionForm.setStatusFlag(AWB_VIEWCUSTOMERPOINTS);
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListDuplicateAWBCommand","execute");

	}
}
