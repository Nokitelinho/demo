/*
 * ScreenLoadCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ScreenLoadCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";

	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String BASED_ON_RULES = "R";
	/**
	 * Added by A-6991 for ICRD-208114
	 * Parameter code for system parameter -Override Rounding value in MRA
	 */
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		 
		 
		MaintainCCASession maintainCCASession=getScreenSession(MODULE_NAME,SCREENID);
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		if(!"listbillingentriesux".equals(maintainCCASession.getFromScreen())){
		maintainCCASession.removeAllAttributes();
		}   
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		
		maintainCCAForm.setUsrCCANumFlg("");
		maintainCCAForm.setRateAuditedFlag("N");
		maintainCCAForm.setPrivilegeFlag(NO);
		
		
		//Added by A-7929 as part of icrd-132548 starts---
		if("listbillingentriesux".equals(maintainCCASession.getFromScreen())){ 
		CCAdetailsVO cCAdetailsVO = maintainCCASession.getCCAdetailsVO();
		if(cCAdetailsVO.getBillingStatus() != null && cCAdetailsVO.getBillingStatus().trim().length() >0){
		maintainCCAForm.setBillingStatus(cCAdetailsVO.getBillingStatus());
		cCAdetailsVO.setChgGrossWeight(null);//added for ICRD-278381
		cCAdetailsVO.setOtherChgGrossWgt(null);//added for ICRD-278381
		maintainCCAForm.setRevGpaCode(cCAdetailsVO.getGpaCode());
		cCAdetailsVO.setCcaType("A");
		cCAdetailsVO.setAutoMca(YES);
		maintainCCAForm.setRevCurCode(cCAdetailsVO.getRevContCurCode());
		}
		}
		//added by A-7929 ends here

		try { 
			if(checkWorkFlowEnabled()){
				maintainCCAForm.setPrivilegeFlag(YES);
			}
		} catch (SystemException e) {

			log.log(Log.FINE,  "Sys.Excptn ");
		}
		if(maintainCCASession.getCCAdetailsVOs()==null ||maintainCCASession.getCCAdetailsVOs().isEmpty()){
			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			maintainCCAForm.setIssueDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		}
		//Added by A-7540
		 maintainCCAForm.setIsAutoMca(NO);  
		if("listbillingentries".equals(maintainCCASession.getFromScreen())|| "listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setIsAutoMca(YES);
		}
		//Added by A-6991
		Map<String, String> systemParameterValues = null;
		try {
			/** getting collections of OneTimeVOs */
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
			handleDelegateException( e );
		}
		//Added for IASCB-858 starts
 		Collection <CRAParameterVO> craParameterVOs=null;
		String systemParCodes = "";
		systemParCodes="MCA";
		try {
			craParameterVOs=new MailTrackingMRADelegate().findReasonCodes(logonAttributes.getCompanyCode(),systemParCodes);
		} catch (BusinessDelegateException e) {
			
			handleDelegateException( e );
		}
		if(craParameterVOs!=null&&!craParameterVOs.isEmpty()){
			maintainCCASession.setCRAParameterVOs(craParameterVOs);
		}
		//Added for IASCB-858 ends
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
		maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		maintainCCASession.setSystemparametres((HashMap<String, String>)systemParameterValues);
		log.log(Log.FINE, "onetimes----->", maintainCCASession.getOneTimeVOs());
		maintainCCAForm.setAutoratedFlag("N");		
		setUnitComponent(logonAttributes.getStationCode(),maintainCCASession);
		maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		//Added by A-7929 as part if icrd-132548
		if("listbillingentries".equals(maintainCCASession.getFromScreen())||"listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(CCATYPE_ONETIME);
		oneTimeList.add(ISSUINGPARTY_ONETIME);
		oneTimeList.add(CCASTATUS_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);
		oneTimeList.add("mailtracking.mra.surchargeChargeHead");
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}

	/**
	 * @author A-2554
	 * @param stationCode
	 * @param maintainCCASession
	 */
	private void setUnitComponent(String stationCode,
			MaintainCCASession maintainCCASession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "\n\n<----STATION CODE IS----------->",
					stationCode);
			//Modified unit rounding to kilogram by A-4809 as part of BUG ICRD-75622
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_KILOGRAM);		
			log.log(Log.FINE,
					"\n\n<----UNIT ROUNDING VO FOR WEIGHT IN SESSION--->",
					unitRoundingVO);
			maintainCCASession.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			log.log(Log.FINE,
					"\n\n<----UNIT ROUNDING VO FOR VOLUME IN SESSION--->",
					unitRoundingVO);
			maintainCCASession.setVolumeRoundingVO(unitRoundingVO);

		}catch(UnitException unitException) {
			unitException.getErrorCode();
		}

	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private boolean checkWorkFlowEnabled() throws SystemException {
		Boolean workFlowEnabled=true;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			if(!(systemParameters.containsValue(YES) || systemParameters.containsValue(BASED_ON_RULES)) ){//Modified For IASCB-2373
				workFlowEnabled=false;
			}
		}
		return workFlowEnabled;
	}
/**
 * 
 * 	Method		:	ScreenLoadCommand.getSystemParameterTypes
 *	Added by 	:	A-6991 on 07-Jun-2017
 * 	Used for 	:   ICRD-208114
 *	Parameters	:	@return 
 *	Return type	: 	Collection<String>
 */
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering(CLASS_NAME, "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

	    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
	    	log.exiting(CLASS_NAME, "getSystemParameterTypes");
	    	return systemparameterTypes;
	      }
}
