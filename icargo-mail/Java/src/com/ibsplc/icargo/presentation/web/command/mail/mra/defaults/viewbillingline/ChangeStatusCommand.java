/*
 * ChangeStatusCommand.java created on Mar 1, 2007
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class ChangeStatusCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String SCREEN_SUCCESS = "changestatus_success";

	private static final String SCREEN_FAILURE = "changestatus_failure";
	
	private static final String STA_NEW = "N";
	
	private static final String STA_ACT = "A";
		
	private static final String STA_INA = "I";
	
	private static final String STA_EXP = "E";
	
	private static final String STA_CAN = "C";
	
	private static final String OPFLAG_UPD = "U";
	
	private static final String STA_CHG_ACT_ACT =
		"mailtracking.mra.defaults.viewbillingline.statusacttoact";
	
	private static final String STA_CHG_CAN_ACT =
		"mailtracking.mra.defaults.viewbillingline.statuscantoact";
	
	private static final String STA_CHG_EXP_ACT = 
		"mailtracking.mra.defaults.viewbillingline.statusexptoact";
	
	private static final String STA_CHG_INA_INA =
		"mailtracking.mra.defaults.viewbillingline.statusinatoina";
		
	private static final String STA_CHG_CAN_INA =
		"mailtracking.mra.defaults.viewbillingline.statuscantoina";
	
	private static final String STA_CHG_NEW_INA =
		"mailtracking.mra.defaults.viewbillingline.statusnewtoina";
	
	private static final String STA_CHG_EXP_INA = 
		"mailtracking.mra.defaults.viewbillingline.statusexptoina";
	
	private static final String STA_CHG_NEW_CAN =
		"mailtracking.mra.defaults.viewbillingline.statusnewtocan";
		
	private static final String STA_CHG_CAN_CAN =
		"mailtracking.mra.defaults.viewbillingline.statuscantocan";
		
	private static final String STA_CHG_CAN_EXP =
		"mailtracking.mra.defaults.viewbillingline.statuscantoexp";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ViewBillingLineForm form=(ViewBillingLineForm)invocationContext.screenModel;
		ViewBillingLineSession session=getScreenSession(MODULE_NAME,SCREENID);
		form.setCanClose("N");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<BillingLineVO> vos = 
			session.getBillingLineDetails();
		String [] strIndexes = null;
		//log.log(1,"Selected indexes--->>>"+form.getCheckboxes());
		if(form.getSelectedIndexes()!= null) {
			strIndexes = form.getSelectedIndexes().split(",");
		}
		String newStatus = form.getPopupStatus();
		log.log(Log.INFO, "New Status value---->>", newStatus);
		log
				.log(Log.INFO, "Selected Indexes are--->", form.getSelectedIndexes());
		if( newStatus != null && newStatus.trim().length() > 0 ){
			if(vos != null && vos.size() > 0) {
				if(strIndexes != null && strIndexes.length > 0){
					for(String strStatus : strIndexes){
						String prevStatus = vos.get(
								Integer.parseInt(strStatus)).getBillingLineStatus();
						errors.addAll(
								validateStatusChanges(prevStatus, newStatus));
					}
				
				}
			}
			if(errors.size() == 0){
				if(strIndexes != null && strIndexes.length > 0){
					for(String strStatus : strIndexes){
							int index = Integer.parseInt(strStatus);
							log.log(Log.INFO, "***********", strStatus);
							session.getBillingLineDetails().
								get(index).setBillingLineStatus(newStatus);
							session.getBillingLineDetails().
							get(index).setOperationFlag(OPFLAG_UPD);
						}
					}
				form.setCanClose("Y");
			}
			else {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREEN_FAILURE;
				return;
			}
			
		
				
		}
		
		log.log(Log.INFO, "Errors size-->>", errors.size());
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	
	private Collection<ErrorVO> validateStatusChanges(String prevStatus, String chgStatus){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(STA_ACT.equals(prevStatus) && (STA_ACT.equals(chgStatus))){
			log.log(1,"active...to active.............");
			ErrorVO err = new ErrorVO(STA_CHG_ACT_ACT);
			errors.add(err);
		}
		else
		if(STA_CAN.equals(prevStatus) && (STA_ACT.equals(chgStatus)) ){
			log.log(1,"can to act................");
			ErrorVO err = new ErrorVO(STA_CHG_CAN_ACT);
			errors.add(err);
		}
		else
		if(STA_EXP.equals(prevStatus) && (STA_ACT.equals(chgStatus))  ){
			log.log(1,"exp to active................");
			ErrorVO err = new ErrorVO(STA_CHG_EXP_ACT);
			errors.add(err);
		}
		else if(STA_INA.equals(prevStatus) && (STA_INA.equals(chgStatus))  ){
			log.log(1,"inactive to inactive................");
			ErrorVO err = new ErrorVO(STA_CHG_INA_INA);
			errors.add(err);
		}
		else if(STA_CAN.equals(prevStatus) && (STA_INA.equals(chgStatus))  ){
			log.log(1,"cancel to inactive................");
			ErrorVO err = new ErrorVO(STA_CHG_CAN_INA);
			errors.add(err);
		}	
		else if(STA_EXP.equals(prevStatus) && (STA_INA.equals(chgStatus))  ){
			log.log(1,"expired  to inactive................");
			ErrorVO err = new ErrorVO(STA_CHG_EXP_INA);
			errors.add(err);
		}	
		else if(STA_NEW.equals(prevStatus) && (STA_INA.equals(chgStatus))  ){
			log.log(1,"new to inactive................");
			ErrorVO err = new ErrorVO(STA_CHG_NEW_INA);
			errors.add(err);
		}	
		else if(STA_NEW.equals(prevStatus) && (STA_CAN.equals(chgStatus))  ){
			log.log(1,"new to cancel................");
			ErrorVO err = new ErrorVO(STA_CHG_NEW_CAN);
			errors.add(err);
		}	
		else if(STA_CAN.equals(prevStatus) && (STA_CAN.equals(chgStatus))  ){
			log.log(1,"cancel to cancel................");
			ErrorVO err = new ErrorVO(STA_CHG_CAN_CAN);
			errors.add(err);
		}	
		else if(STA_CAN.equals(prevStatus) && (STA_EXP.equals(chgStatus))  ){
			log.log(1,"cancel to expired................");
			ErrorVO err = new ErrorVO(STA_CHG_CAN_EXP);
			errors.add(err);
		}	
	return errors;
	}
}
