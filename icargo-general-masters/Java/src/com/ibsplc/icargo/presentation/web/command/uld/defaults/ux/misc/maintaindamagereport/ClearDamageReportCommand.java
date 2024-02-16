package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
//import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.ClearDamageReportCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class ClearDamageReportCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain DamageReport");
	private static final String MODULE = "uld.defaults";
	/*
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.overallStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		maintainDamageReportForm.setPicturePresent("false");
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(
				MODULE, SCREENID);
		clearForm(maintainDamageReportForm);
		maintainDamageReportSession.setULDDamagePictureVO(null);
		maintainDamageReportSession.setSavedULDDamageVO(null);
		maintainDamageReportSession.setULDDamageVO(null);
		maintainDamageReportSession.setULDRepairVOs(null);
		maintainDamageReportSession.setULDDamageVOs(null);
		maintainDamageReportSession.setRefNo(null);
		maintainDamageReportSession.setDamageImageMap(null);
		maintainDamageReportSession.getDamageImageMap();
		// added by saritha
		maintainDamageReportSession.removeUldNumber();

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(compCode,
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		maintainDamageReportSession
				.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		maintainDamageReportForm.setScreenStatusValue("SCREENLOAD");
		// maintainDamageReportForm.setScreenStatusFlag("SCREENLOAD");
		invocationContext.target = CLEAR_SUCCESS;

	}

	private void clearForm(MaintainDamageReportForm maintainDamageReportForm) {

		maintainDamageReportForm.setUldNumber("");
		maintainDamageReportForm.setSupervisor("");
		maintainDamageReportForm.setTotAmt("");
		//maintainDamageReportForm.setRemarks("");
		maintainDamageReportForm.setInvRep("");
		maintainDamageReportForm.setDamageStatus("");
	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ClearDamageReportCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(DAMAGESTATUS_ONETIME);
		parameterTypes.add(OVERALLSTATUS_ONETIME);
		parameterTypes.add(REPAIRSTATUS_ONETIME);

		log.exiting("ClearDamageReportCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

}
