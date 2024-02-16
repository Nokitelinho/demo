package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class UpdateMCAWorkflowCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("UpdateMCAWorkflowCommand");
	private static final String WORFLOW_MODULE_NAME = "workflow.defaults";
	private static final String WORKFLOW_SCREEN_ID = "workflow.defaults.messageinbox";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaincca";
	
	private static final String WORKFLOW_SUCCESS = "workflow_success";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String BASED_ON_RULES = "R";
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";

	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";
	/**
	 * Added by A-6991 for ICRD-208114
	 * Parameter code for system parameter -Override Rounding value in MRA
	 */
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("updatemcaworkflowcommand", "updatemcaworkflowcommand");
		MRAMaintainCCAForm maintainCCAForm = (
				MRAMaintainCCAForm )invocationContext.screenModel;
		
		MessageInboxSession messageInboxSession = 
				getScreenSession(WORFLOW_MODULE_NAME, WORKFLOW_SCREEN_ID);
		
		MaintainCCASession maintainCCASession = 
	    		(MaintainCCASession) getScreenSession(MODULE_NAME,SCREEN_ID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		if(messageInboxSession.getParameterMap() != null){
			Set<String> keys = messageInboxSession.getParameterMap().keySet();
			String value = null;
			MaintainCCAFilterVO filterVO = new MaintainCCAFilterVO();
			for(String key : keys){
				value = messageInboxSession.getParameterMap().get(key);
				if(ParameterConstantsVO.WRKFLO_PARCOD_MCAREFNUM.equals(key)){
					maintainCCAForm.setCcaNum(value);
					filterVO.setCcaReferenceNumber(value);
				}
				else if (ParameterConstantsVO.WRKFLO_PARCOD_CMPCOD.equals(key)){
					
					filterVO.setCompanyCode(value);
					
				}             
					
					
					
				}
			maintainCCASession.setCCAFilterVO(filterVO);
				
			}
		maintainCCAForm.setUsrCCANumFlg("");
		maintainCCAForm.setRateAuditedFlag("N");
		maintainCCAForm.setPrivilegeFlag(NO);
		try {
			if(checkWorkFlowEnabled()){
				maintainCCAForm.setPrivilegeFlag(YES);
			}
		} catch (SystemException e) {

			log.log(Log.FINE,  "Sys.Excptn ");
		}
		if(maintainCCASession.getCCAdetailsVOs()==null ||maintainCCASession.getCCAdetailsVOs().size()<=0){
			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			maintainCCAForm.setIssueDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		}
		//Added by A-7540
		 maintainCCAForm.setIsAutoMca(NO);  
		if("listbillingentries".equals(maintainCCASession.getFromScreen())){
			maintainCCAForm.setIsAutoMca(YES);
		}
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
		if(craParameterVOs!=null&&craParameterVOs.size()>0){
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
		
		invocationContext.target = WORKFLOW_SUCCESS;
		
	}
	
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		
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
	    	
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

	    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
	    	
	    	return systemparameterTypes;
	      }
	}
	

