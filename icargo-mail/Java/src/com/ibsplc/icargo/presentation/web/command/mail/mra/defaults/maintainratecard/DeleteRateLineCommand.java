/*
 * DeleteRateLineCommand.java Created on Jan 22, 2007
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
public class DeleteRateLineCommand extends BaseCommand{
private Log log = LogFactory.getLogger("DateRateLine Command");

	private static final String CLASS_NAME = "DeleteCommand";

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
		log.entering("DeleteRateLineCommand","execute");
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
   		ArrayList<RateLineVO> ratelinevos=null;
   		if(session.getRateLineDetails()!=null && session.getRateLineDetails().size()>0){
   			ratelinevos=new ArrayList<RateLineVO>(session.getRateLineDetails());
   		}
   		String index[]=null;
   		if(form.getCheck()!=null ){
   			index=form.getCheck();
   		}
   		
		
		for(int i=index.length-1;i>-1;i--)
		{
			
			int ind=Integer.parseInt(index[i]);
			RateLineVO ratelinevo=null;
			ratelinevo=ratelinevos.get(ind);
			


			if(ratelinevo.getOperationFlag()!=null && ratelinevo.getOperationFlag().trim().length()>0 &&
					ratelinevo.getOperationFlag().equals(RateLineVO.OPERATION_FLAG_INSERT))
			{

				
				ratelinevos.remove(ind);
				log.log(Log.FINE, "ratelinevocol after  delete", ratelinevos);
			}
			else if ((ratelinevo.getOperationFlag()==null)||(("U").equals(ratelinevo.getOperationFlag())))
			{
				
				RateLineVO ratevo=null;
				ratevo=ratelinevo;
				ratelinevos.remove(ratelinevo);
				ratevo.setOperationFlag(RateLineVO.OPERATION_FLAG_DELETE);
				ratelinevos.add(ratevo);
				log.log(Log.FINE, "ratelinevocol after  delete", ratelinevos);
			}
		 }
		
        int size=ratelinevos.size();
        Page<RateLineVO> ratelinepage=new Page<RateLineVO>(ratelinevos,1,0,size,0,0,false);
		session.setRateLineDetails(ratelinepage);
   	   		invocationContext.target = ACTION_SUCCESS;
   			log.exiting("DeleteRateLine", "execute");


	}

}
