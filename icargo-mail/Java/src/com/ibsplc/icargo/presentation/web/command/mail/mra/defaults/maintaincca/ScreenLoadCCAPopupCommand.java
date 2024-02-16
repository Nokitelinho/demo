/*
 * ScreenLoadCCAPopupCommand.java Created on JULY 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * 
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

/**
 * @author A-3447
 * 
 */

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3447
 * This command class obtains the screenload details for the  UnassignTask popup
 * screen. The onetimeValues are obtained and set to the session.
 * The default values are set to the form. 
 */
/*
 * Revision History
 * Revision      Date            Author          Description
 *==============================================================================
 *  0.1     21-7-2008     A-3447		 	 For Coding
 * 
 * =============================================================================
*/
public class ScreenLoadCCAPopupCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCCAPopUpCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaincca";

	private static final String SCREEN_SUCCESS = "screenload_success";
		
	/**@author A-3447
	 * execute method for handling the screenload action 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		MaintainCCASession maintainCCASession=getScreenSession(MODULE_NAME,SCREEN_ID);		
		//maintainCCASession.setCCAdetailsVOs(null); 
		
		Collection<CCAdetailsVO> ccAdetailsVOs=maintainCCASession.getCCAdetailsVOs();
		for(CCAdetailsVO ccAdetailsVO:ccAdetailsVOs ){
		if(ccAdetailsVO!=null){
			if("A".equals(ccAdetailsVO.getCcaStatus())){
				//Added for bug ICRD 17514 
				if(ccAdetailsVO.getRevChgGrossWeight()!=null && ccAdetailsVO.getChgGrossWeight()!=null){
					if(ccAdetailsVO.getRevChgGrossWeight().getAmount()==0){
						ccAdetailsVO.getRevChgGrossWeight().setAmount(-ccAdetailsVO.getChgGrossWeight().getAmount());
						if(ccAdetailsVO.getRevTax()==0){
							ccAdetailsVO.setRevTax(-(ccAdetailsVO.getTax()));
						}


					}
				}
			}
			//ccAdetailsVOs.add(ccAdetailsVO);
		}
}
		maintainCCASession.getCCAdetailsVOs();
		maintainCCASession.setCCAdetailsVOs(ccAdetailsVOs);
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting("ScreenLoadCCAPopup","execute");
	}
	 
	
}
