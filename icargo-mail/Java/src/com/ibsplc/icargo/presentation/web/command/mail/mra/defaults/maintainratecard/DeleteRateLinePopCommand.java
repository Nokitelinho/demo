/*
 * DeleteRateLinePopCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2391
 *
 */
public class DeleteRateLinePopCommand  extends BaseCommand{
	private Log log = LogFactory.getLogger("Delete ScreenloadCommand");
	private static final String CLASS_NAME = "Delete ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("DeleteRateLinePopCommand","execute");
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
   		String index[]=form.getPopCheck();   	
   		ArrayList<RateLineVO> ratelinevos=null;
   		String origin[]=form.getPopupOrigin();
   		String destn[]=form.getPopupDestn();

   		int size=0;
   		
   		if((session.getNewRateLineDetails()!=null)&&(session.getNewRateLineDetails().size()>0))
   		{

   			ratelinevos=new ArrayList<RateLineVO>(session.getNewRateLineDetails());
   			int siz=ratelinevos.size();
   			for(int i=0;i<siz;i++){
   				ratelinevos.get(i).setOrigin(origin[i]);
   				ratelinevos.get(i).setDestination(destn[i]);
   			}
   		}
		
		for(int i=index.length-1;i>-1;i--){
			
			int ind=Integer.parseInt(index[i]);
			ratelinevos.remove(ind);
			log.log(Log.FINE, "ratelinevos****after ordinary delete",
					ratelinevos);

		}
		
		size=ratelinevos.size();
		
		Page<RateLineVO> ratelinevopage =new Page<RateLineVO>(ratelinevos,1,0,size,0,0,false);
   		session.setNewRateLineDetails(ratelinevopage);
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting("DeleteRateLine", "execute");
    }

}
