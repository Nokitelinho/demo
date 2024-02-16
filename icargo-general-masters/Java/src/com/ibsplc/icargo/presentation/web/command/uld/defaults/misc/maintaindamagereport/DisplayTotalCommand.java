/*
 * DisplayTotalCommand.java Created on Feb 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
													maintaindamagereport;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for displaying amount
 *
 * @author A-1862
 */
public class DisplayTotalCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	private static final String DISPLAY_SUCCESS = "display_success";
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    			
    	MaintainDamageReportForm maintainDamageReportForm = 
    		(MaintainDamageReportForm)invocationContext.screenModel;
    	MaintainDamageReportSession maintainDamageReportSession = 
    			getScreenSession(MODULE, SCREENID);
    
// 		for populating details from main screen into the session-START
	    ULDDamageRepairDetailsVO uldDamageRepairDetailsVO =
    		maintainDamageReportSession.getULDDamageVO() != null ?
			maintainDamageReportSession.getULDDamageVO() :
			new ULDDamageRepairDetailsVO();
		if (uldDamageRepairDetailsVO != null ){			
				uldDamageRepairDetailsVO.setDamageStatus
								(maintainDamageReportForm.getDamageStatus());
				uldDamageRepairDetailsVO.setOverallStatus
								(maintainDamageReportForm.getOverallStatus());
				uldDamageRepairDetailsVO.setRepairStatus
								(maintainDamageReportForm.getRepairStatus());
				uldDamageRepairDetailsVO.setSupervisor
									(maintainDamageReportForm.getSupervisor());
				uldDamageRepairDetailsVO.setInvestigationReport
									(maintainDamageReportForm.getInvRep());

		}
		maintainDamageReportForm.setScreenReloadStatus("reload");
//		 for populating details from main screen into the session-END
		
    	ArrayList<ULDRepairVO> uLDRepairVOs = 
       		maintainDamageReportSession.getULDDamageVO().
       										getUldRepairVOs() != null ?
   			new ArrayList<ULDRepairVO>
           	(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs()) : 
   			new ArrayList<ULDRepairVO>();
    	if(uLDRepairVOs==null || uLDRepairVOs.size()==0)
    	{
    		
    		ErrorVO error = null;
    		error = new ErrorVO(
			 "uld.defaults.maintainDmgRep.msg.err.repairdetailsnotpresent");
    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		invocationContext.addError(error);    		
    	}else
    	{
    		double totalAmt=0.0;
    		for(ULDRepairVO uldRepairVO:uLDRepairVOs){
    			totalAmt=totalAmt+uldRepairVO.getDisplayAmount();
    	}
    		
    		maintainDamageReportForm.setTotAmt(String.valueOf(totalAmt));
    	}
    	
    	invocationContext.target = DISPLAY_SUCCESS;
    }

	
}
