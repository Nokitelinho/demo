/*
 * UpdateSessionCommand.java Created on Mar 26, 2018
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.UpdateSessionCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	13-Mar-2018	:	Draft
 */
public class UpdateSessionCommand extends BaseCommand {
	private static final String SCREENID = "uld.defaults.updatemultipleulddetails";
	  private static final String MODULE = "uld.defaults";
	  private static final String OVERALL_STATUS = "uld.defaults.overallStatus";
	  private static final String DAMAGE_STATUS = "uld.defaults.damageStatus";
	  private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8176 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		this.log.entering("UpdateSessionCommand", "UpdateSessionCommand");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREENID);
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Map<String, Collection<OneTimeVO>> oneTimeMap = null;
    	Collection<String> codes = new ArrayList<String>();
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
		Collection<ULDDamageRepairDetailsVO> uldRepairDamageVos =updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs();
		int count=0;
		for (ULDDamageRepairDetailsVO uLDDamageRepairDetailsVO :uldRepairDamageVos) {
			if (uLDDamageRepairDetailsVO.getDamageIndex() ==count) {
			uLDDamageRepairDetailsVO.setUldNumber(updateMultipleULDDetailsForm.getUldNumbers()[count]!=null ? updateMultipleULDDetailsForm.getUldNumbers()[count] :"");
			uLDDamageRepairDetailsVO.setOverallStatus(updateMultipleULDDetailsForm.getOperationalStatus()[count]!=null ? updateMultipleULDDetailsForm.getOperationalStatus()[count]:"");
			uLDDamageRepairDetailsVO.setDamageStatus(updateMultipleULDDetailsForm.getDamagedStatus()[count]!=null ?updateMultipleULDDetailsForm.getDamagedStatus()[count]:"");
			count++;
			uLDDamageRepairDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
			}

}
		 updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(uldRepairDamageVos);
		 updateMultipleULDDetailsForm.setStatusFlag("session_update");
		 invocationContext.target="action_savesuccess";
		  this.log.exiting("UpdateSessionCommand", "UpdateSessionCommand");
			
	}    
}