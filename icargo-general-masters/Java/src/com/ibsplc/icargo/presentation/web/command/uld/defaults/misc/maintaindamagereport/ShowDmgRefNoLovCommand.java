/*
 * ShowDmgRefNoLovCommand.java Created on Feb 07,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.
										defaults.misc.maintaindamagereport;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageRefNoLovSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up ShowDmgRefNoLovCommand
 * 
 * @author A-1862
 */

public class ShowDmgRefNoLovCommand extends BaseCommand {
    
	/**
	 * Logger for Maintain DamageReport
	 */
	private Log log = LogFactory.getLogger("Maintain DamageReport");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	
	private static final String LOV_SUCCESS = "lov_success";
   
    
    private static final String MODULELOV = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENIDLOV =
		"uld.defaults.damagerefnolov";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    		
		

		MaintainDamageReportSession maintainDamageReportSession =
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);		
		MaintainDamageReportForm maintainDamageReportForm =
			(MaintainDamageReportForm)invocationContext.screenModel;
		log.log(Log.FINE,
				"\n\n maintainDamageReportForm.getStatusFlag() ---> ",
				maintainDamageReportForm.getStatusFlag());
			ArrayList<ULDDamageVO> uldDamageVOs=new ArrayList<ULDDamageVO>
			(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
    		Page<ULDDamageReferenceNumberLovVO> pg = new Page<ULDDamageReferenceNumberLovVO>
    		(new ArrayList<ULDDamageReferenceNumberLovVO>(),0,0,0,0,0,false);
    		ArrayList<String> refnos=new ArrayList<String>();
    		
    		
    		for(ULDDamageVO uldDamageVO : uldDamageVOs) {
    			if((uldDamageVO.getOperationFlag()!=null && !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE) 
    					&& !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT)) ||
    					(uldDamageVO.getOperationFlag()==null))
    			{
    				//ULDDamageReferenceNumberLovVO uldDamageReferenceNumberLovVO=new ULDDamageReferenceNumberLovVO();
    				//uldDamageReferenceNumberLovVO.setDamageReferenceNumber(uldDamageVO.getDamageReferenceNumber());
    				//uldDamageReferenceNumberLovVO.setUldNumber(maintainDamageReportSession.getULDDamageVO().getUldNumber());
    				  if(!refnos.contains(String.valueOf(uldDamageVO.getDamageReferenceNumber())))
    				  {
    					  refnos.add(String.valueOf(uldDamageVO.getDamageReferenceNumber()));
    					  ULDDamageReferenceNumberLovVO uldDamageReferenceNumberLovVO=new ULDDamageReferenceNumberLovVO();
    					  uldDamageReferenceNumberLovVO.setDamageReferenceNumber(uldDamageVO.getDamageReferenceNumber());
    	    			  uldDamageReferenceNumberLovVO.setUldNumber(maintainDamageReportSession.getULDDamageVO().getUldNumber());
    	    			  pg.add(uldDamageReferenceNumberLovVO);
    				 }
    				
    			}
	    		}
    		
    		
    		DamageRefNoLovSession session = getScreenSession(MODULELOV, SCREENIDLOV);
    		session.setULDDamageReferenceNumberLovVOs(pg);
    		if(("uld_def_add_rep").equals(maintainDamageReportForm.getStatusFlag()))
    		{
    		session.setParentScreenId("ADDREPAIR");
    		} else {
				session.setParentScreenId("MODREPAIR");
			}
    		
    		
		
		invocationContext.target = LOV_SUCCESS;
        
    }
 
}
