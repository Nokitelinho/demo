package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearCustomerCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ScreenLoadPointRedemptionCommand");
// THE BELOW CONSTANT ADDED BY A-5219 FOR ICRD-18283
	private static final String OFF = "off";
/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("Clear Command", "ENTER");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		form.setCustCode(BLANK);
		// CODE ADDED BY A-5219 FOR ICRD-18283 START
		form.setLocationType(BLANK);
		form.setLocationValue(BLANK); 
		form.setAgent(OFF);
		form.setCassAgent(OFF);
		form.setCustomer(OFF); 
		form.setCccollector(OFF);
		form.setExpiringBefore(BLANK);
		// CODE ADDED BY A-5219 END
		form.setCustStation(BLANK);
		form.setStatus(BLANK);
		form.setLoyaltyName(BLANK);
		form.setCustType(BLANK);
		session.setCustomerCode(BLANK);
		form.setCanRedeem(BLANK);
		session.setAwbFilterVO(null);
		session.setAwbLoyaltyVos(null);
		session.setCustomerCodes(null);
		session.setCustomerContactVOs(null);
		session.setCustomerNames(null);
		session.setCustomerContactPointsVOs(null);
		session.setCustomerVOs(null);
		session.setLoyaltyPrograms(null);
		session.setShipmentVOs(null);
		session.setListFilterVO(null);
		form.setDisplayPageNum("1");
		form.setCloseStatus(BLANK);
		form.setInternalAccountHolder(BLANK);//Added by A-7534 for ICRD-228903
		invocationContext.target = CLEAR_SUCCESS;
		
	}
}
