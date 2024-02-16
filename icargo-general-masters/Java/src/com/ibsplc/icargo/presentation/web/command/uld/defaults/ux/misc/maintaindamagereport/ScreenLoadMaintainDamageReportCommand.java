package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.ScreenLoadMaintainDamageReportCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class ScreenLoadMaintainDamageReportCommand extends BaseCommand {

	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("Maintain Damage Report");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	private static final String LIST_SUCCESS = "list_success";
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String DAMAGES = "D";
	private static final String OPERATION_STATUS = "L";
	private static final String REPAIR_STATUS = "R";
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	private static final String DAMAGECODE_ONETIME = "uld.defaults.damagecode";
	private static final String POSITION_ONETIME = "uld.defaults.position";
	private static final String SEVERITY_ONETIME = "uld.defaults.damageseverity";
	private static final String REPAIRHEAD_ONETIME = "uld.defaults.repairhead";
	private static final String SECTION_ONETIME="uld.defaults.section";
	private static final String ULD_LIST_SUCCESS="uld_list_success";
	//Added by A-8368 as part of user story - IASCB-35533
	private static final String POINTOFNOTICE_ONETIME = "operations.shipment.pointofnotice";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(MODULE, SCREENID);
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		if (maintainDamageReportSession.getUldNumber() != null
				&& maintainDamageReportSession.getUldNumber().trim().length() > 0
				&& maintainDamageReportSession.getParentScreenId() != null
				&& maintainDamageReportSession.getParentScreenId().length() > 0) {
			log.log(Log.INFO, "\n\nParentScreenId -->",	maintainDamageReportSession.getParentScreenId());
			invocationContext.target = LIST_SUCCESS;
			return;
		} //Commented out by A-7390 for ICRD-243752
		/*else if("OPR335".equalsIgnoreCase(maintainDamageReportForm.getFromScreen()) && maintainDamageReportForm.getUldNumber()!=null){
			log.log(Log.INFO, "\n\nParentScreenId -->",	maintainDamageReportSession.getParentScreenId());
			
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<OneTimeVO> oneTimeVOs = null;
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try {
				oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(compCode,getOneTimeParameterTypes());
				log.log(Log.FINE, "The onetimevalues are from MaintainScreenLoad", oneTimeValues.get(OVERALLSTATUS_ONETIME));
				oneTimeVOs=oneTimeValues.get(SECTION_ONETIME);
				} catch (BusinessDelegateException e) {
				e.getMessage();
				exception = handleDelegateException(e);
			}
			maintainDamageReportSession.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
			
			invocationContext.target = LIST_SUCCESS;
			return; 
		} */else {
			maintainDamageReportSession.removeAllAttributes();
		}

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<OneTimeVO> oneTimeVOs = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(compCode,getOneTimeParameterTypes());
			log.log(Log.FINE, "The onetimevalues are from MaintainScreenLoad", oneTimeValues.get(OVERALLSTATUS_ONETIME));
			oneTimeVOs=oneTimeValues.get(SECTION_ONETIME);
			} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		maintainDamageReportSession.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		// Added by A-3415 for ICRD-113953 Starts
		Collection<String> systemparameterCodes = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
		try {
			map = sharedDefaultsDelegate.findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		}
		if (map != null) {
			maintainDamageReportSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
		}
		// Added by A-3415 for ICRD-113953 Ends
		populateCurrency(maintainDamageReportSession);
		maintainDamageReportForm.setDamageStatus("");
		if (maintainDamageReportForm.getOverallStatus() == null) {
			maintainDamageReportForm.setOverallStatus(OPERATION_STATUS);
		}
		// targetFormName.overStatus.value

		maintainDamageReportForm.setOverStatus(OPERATION_STATUS);
		// maintainDamageReportForm.setRepairStatus(REPAIR_STATUS); //Commented
		// by A-7131 for ICRD-218735
		maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		maintainDamageReportForm.setScreenStatusValue("SCREENLOAD");
		String section= null;
		if(oneTimeVOs!=null && oneTimeVOs.size()>0){
			maintainDamageReportForm.setSections(oneTimeVOs.iterator().next().getFieldValue());
			section= oneTimeVOs.iterator().next().getFieldValue();
		}		
		ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		try {	        	
			damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate.listULDDamageChecklistMaster(applicationSession.getLogonVO().getCompanyCode(),section);
			log.log(Log.FINE,"damageChecklistVOs getting from delegate---------#######>",damageChecklistVOs);        	
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors= handleDelegateException(e);
		} 
		if(damageChecklistVOs!=null && damageChecklistVOs.size()>0){
			maintainDamageReportSession.setULDDamageChecklistVO(damageChecklistVOs);
		}
		/*Added by A-7390 for ICRD-243752. Specific invocation target for 
		 * OPR335 have been moved here so that all one time params are set and session data is set
		 */
		if ("OPR335".equals(maintainDamageReportForm.getFromScreen())
				&& maintainDamageReportForm.getUldNumber() != null) {
			invocationContext.target = LIST_SUCCESS;
			return;
		} 
		
		else if ("ULD006".equals(maintainDamageReportForm.getFromScreen())
				&& maintainDamageReportForm.getUldNumber() != null) {
			invocationContext.target = ULD_LIST_SUCCESS;
			return;
		}
		
		else {
			invocationContext.target = SCREENLOAD_SUCCESS;
		}
	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(DAMAGESTATUS_ONETIME);
		parameterTypes.add(OVERALLSTATUS_ONETIME);
		parameterTypes.add(REPAIRSTATUS_ONETIME);
		parameterTypes.add(DAMAGECODE_ONETIME);
		parameterTypes.add(POSITION_ONETIME);
		parameterTypes.add(SEVERITY_ONETIME);
		parameterTypes.add(REPAIRHEAD_ONETIME);
		parameterTypes.add(SECTION_ONETIME);
		//Added by A-8368 as part of user story -    IASCB-35533
		parameterTypes.add(POINTOFNOTICE_ONETIME);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

	private void populateCurrency(MaintainDamageReportSession maintainDamageReportSession) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ArrayList<CurrencyVO> currencies = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			currencies = (ArrayList<CurrencyVO>) new CurrencyDelegate().findAllCurrencyCodes(logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			exception = handleDelegateException(businessDelegateException);
		}
		maintainDamageReportSession.setCurrencies(currencies);
	}

}
