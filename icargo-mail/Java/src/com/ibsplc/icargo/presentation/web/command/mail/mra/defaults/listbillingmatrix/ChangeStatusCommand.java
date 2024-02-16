/*
 * ChangeStatusCommand.java created on Mar 1, 2007
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm;
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

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String SCREEN_SUCCESS = "changestatus_success";

	private static final String SCREEN_FAILURE = "changestatus_failure";
	
	private static final String STA_NEW = "N";
	
	private static final String STA_ACT = "A";
		
	private static final String STA_INA = "I";
	
	private static final String STA_EXP = "E";
	
	private static final String STA_CAN = "C";
	
	private static final String STA_CHG_NEW_INACAN =
		"mailtracking.mra.defaults.listbillingmatrix.statusnewtoinactive";
	
	private static final String STA_CHG_EXP_CANACTINA =
		"mailtracking.mra.defaults.listbillingmatrix.statusexptoact";
	
	private static final String STA_CHG_CANINAACT = 
		"mailtracking.mra.defaults.listbillingmatrix.statuscantoina";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListBillingMatrixForm form=(ListBillingMatrixForm)invocationContext.screenModel;
		ListBillingMatrixSession session=getScreenSession(MODULE_NAME,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<BillingMatrixVO> vos = 
			session.getBillingMatrixVOs();
		String [] strIndexes = null;
		if(form.getSelectedIndexes()!= null) {
			strIndexes = form.getSelectedIndexes().split(",");
		}
		String newStatus = form.getChangedStatus();
		log.log(Log.INFO, "New Status value---->>", newStatus);
		log
				.log(Log.INFO, "Selected Indexes are--->", form.getSelectedIndexes());
		if( newStatus != null && newStatus.trim().length() > 0 ){
			if(vos != null && vos.size() > 0) {
				if(strIndexes != null && strIndexes.length > 0){
					for(String strStatus : strIndexes){
						String prevStatus = vos.get(
								Integer.parseInt(strStatus)).getBillingMatrixStatus();
						errors.addAll(
								validateStatusChanges(prevStatus, newStatus));
					}
				
				}
			}
			if(errors.size() == 0){
				if(strIndexes != null && strIndexes.length > 0){
					for(String strStatus : strIndexes){
							int index = Integer.parseInt(strStatus);
							session.getBillingMatrixVOs().
								get(index).setBillingMatrixStatus(newStatus);
							session.getBillingMatrixVOs().
							get(index).setOperationFlag("U");
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
		if(STA_NEW.equals(prevStatus) && (
				(STA_INA.equals(chgStatus)) || (STA_CAN.equals(chgStatus)) )){
			log.log(1,"New...to inactive.............");
			ErrorVO err = new ErrorVO(STA_CHG_NEW_INACAN);
			errors.add(err);
		}
		else
		if(STA_EXP.equals(prevStatus) && ((STA_CAN.equals(chgStatus)) 
				|| (STA_ACT.equals(chgStatus)) || (STA_INA.equals(chgStatus)))){
			log.log(1,"expired to can................");
			ErrorVO err = new ErrorVO(STA_CHG_EXP_CANACTINA);
			errors.add(err);
		}
		else
		if(STA_CAN.equals(prevStatus) &&(
				(STA_INA.equals(chgStatus)) ||(STA_ACT.equals(chgStatus)) ||
				(STA_CAN.equals(chgStatus))) ){
			log.log(1,"can to inactive................");
			ErrorVO err = new ErrorVO(STA_CHG_CANINAACT);
			errors.add(err);
		}
		
		
	return errors;
	}
}
