/*
 * ClearScreenCommand.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;



import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
/**
 * The command to clear Maintain Product Screen
 * @author A-1754
 *
 */
public class ClearScreenCommand extends BaseCommand {

	private static final String RESTRICT_FLAG = "Restrict";
	private static final String NEW_STATUS = "New";
	private static final String SAVE_MODE="save";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearScreenCommand","execute");
		MaintainProductForm form= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleClearAction(session,form);
		invocationContext.target = "screenload_success";
		log.exiting("ClearScreenCommand","execute");

	}



	/**
	 * Function to  Clear the screen
	 * @author A-1754
	 * @param session
	 * @param form
	 */
 private void handleClearAction(MaintainProductSessionInterface session,
			MaintainProductForm form){
	 log.entering("ClearScreenCommand","handleClearAction");
	 session.setProductTransportModeVOs(null);
	 session.setProductPriorityVOs(null);
	 session.setProductSccVOs(null);
	 session.setProductServiceVOs(null);
	 session.setProductEventVOs(null);
	 session.setProductCommodityVOs(null);
	 session.setProductSegmentVOs(null);
	 session.setProductStationVOs(null);
	 session.setProductCustGrpVOs(null);
	 session.setProductParameterVOs(null);
	 session.setProductVO(null);
	// form.setProductStatus("");
	 form.setProductCode("");
	 form.setHandlingInfo("");
	 form.setDetailDesc("");
	 form.setProductDesc("");
	 form.setProductCategory("");
	 //form.setIcon("");
	 //form.setProductStatus("");
	 form.setRemarks("");
	 form.setRestrictedTermsCheck(null);
	 form.setCommodityStatus(RESTRICT_FLAG);
	 form.setSegmentStatus(RESTRICT_FLAG);
	 form.setOriginStatus(RESTRICT_FLAG);
	 form.setDestinationStatus(RESTRICT_FLAG);
	 form.setCustGroupStatus(RESTRICT_FLAG);
	 form.setOverrideCapacity("");
 	form.setRateDefined(false);
 	form.setCoolProduct(false);
 	form.setDisplayInPortal(true);   
 	form.setProductPriority("");
	form.setMinVolume("0.0");
	form.setMaxVolume("0.0");
	form.setMinWeight("0.0");
	form.setMaxWeight("0.0");
	form.setAddRestriction("");
	form.setWeightUnit("");
	form.setVolumeUnit("");
	form.setProductName("");
	String fromDateString=DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
	form.setStartDate(fromDateString);
	form.setProductStatus(NEW_STATUS);
	form.setEndDate("");
	form.setMode(SAVE_MODE);
	form.setTab("1");
	form.setBookingMand(false);
	form.setProactiveMilestoneEnabled(false);

	form.setDocType("");
	form.setSubType("");
	form.setMaxDimension("0.0");
	form.setMinDimension("0.0");
	form.setDimensionUnit("");
	log.exiting("ClearScreenCommand","exehandleClearActioncute");

 }
}


