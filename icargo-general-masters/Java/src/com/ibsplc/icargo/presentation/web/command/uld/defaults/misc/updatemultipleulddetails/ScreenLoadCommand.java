/*
 * ScreenLoadCommand.java Created on March 13, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	13-Mar-2018	:	Draft
 */
public class ScreenLoadCommand extends BaseCommand {

	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	private static final String LIST_SUCCESS = "list_success";
	/**
	 * Screen Id of maintain damage report screen
	 */


	
	private static final String OVERALL_STATUS = "uld.defaults.overallStatus";
	private static final String DAMAGE_STATUS = "uld.defaults.damageStatus";
	private final String SCREENID = "uld.defaults.updatemultipleulddetails";
	

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenloadCommand", "INSIDE ScreenloadCommand");
    	UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREENID);
    	Collection<ULDDamageRepairDetailsVO> multipledamagevos =updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs();
    	Map<String, Collection<OneTimeVO>> oneTimeMap = null;
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<String> codes = new ArrayList<String>();
		LocalDate ldate= new LocalDate(applicationSessionImpl.getLogonVO().getAirportCode(),Location.ARP,false);
		updateMultipleULDDetailsSession.setReportedDate(ldate);
		updateMultipleULDDetailsSession.setReportedAirport(applicationSessionImpl.getLogonVO().getAirportCode());
		codes.add(OVERALL_STATUS);
		codes.add(DAMAGE_STATUS);
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), codes);
		} catch (BusinessDelegateException businessDelegateException) {
			
			businessDelegateException.printStackTrace();
		}
		if(oneTimeMap != null){
			updateMultipleULDDetailsSession.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>)oneTimeMap);
		}
		
		if(!"action_damagesuccess".equals(updateMultipleULDDetailsForm.getStatusFlag())){
		updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(null);
		ArrayList<ULDDamageRepairDetailsVO> uldvos = new ArrayList<ULDDamageRepairDetailsVO>(25);
		uldvos.clear();
		LocalDate localdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP, false);
		String localDateString=localdate.toString();
		ULDDamageRepairDetailsVO uldvo = null;
		for(int i = 0;i<25;i++){
			uldvo = new ULDDamageRepairDetailsVO();
			uldvo.setCompanyCode(logonAttributes.getCompanyCode());
			uldvo.setLastUpdatedUser(logonAttributes.getUserId());
		  //  uldvo.setLastUpdatedTime(localdate.setDate(localDateString));
			uldvo.setDamageIndex(i);
			uldvos.add(uldvo);
		}
		updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(uldvos);
		}
		else{
		updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(multipledamagevos);
		}
		/*else{
			updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs());
		}
		*/
		updateMultipleULDDetailsForm.setStatusFlag("action_screenload");
		log.exiting("ScreenloadCommand", "EXITING ScreenloadCommand");
		invocationContext.target="screenload_success";
    }
}
