/*
 * ChangeStatusCommand.java created on Mar 1, 2007
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
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

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

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
		BillingMatrixForm form=(BillingMatrixForm)invocationContext.screenModel;
		MaintainBillingMatrixSession session=getScreenSession(MODULE_NAME,SCREENID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		BillingMatrixVO vo = 
			session.getBillingMatrixVO();
				
		String newStatus = form.getChangedStatus();
		log.log(Log.INFO, "New Status value---->>", newStatus);
		form.setCanClose("N");
		if( newStatus != null && newStatus.trim().length() > 0 ){
			if(vo != null ){
			
					String prevStatus = vo.getBillingMatrixStatus();
					errors.addAll(validateStatusChanges(prevStatus, newStatus));
				
			
			}
			if(errors.size() == 0){
						String oldStatus = session.getBillingMatrixVO().
													getBillingMatrixStatus();
						Page<BillingLineVO> lineVos = session.getBillingLineDetails();
						if(lineVos != null && lineVos.size() > 0){
						
						if(STA_NEW.equals(oldStatus) && STA_ACT.equals(newStatus)){
							for(BillingLineVO lineVO : lineVos){
								if(STA_NEW.equals(lineVO.getBillingLineStatus())){
									lineVO.setBillingLineStatus(STA_ACT);
								}
							}
						}else
						if(STA_INA.equals(oldStatus) && STA_ACT.equals(newStatus)){
							for(BillingLineVO lineVO : lineVos){
								if(STA_INA.equals(lineVO.getBillingLineStatus())){
									lineVO.setBillingLineStatus(STA_ACT);
								}
							}
						}
						else
						if(STA_ACT.equals(oldStatus) && STA_INA.equals(newStatus)){
							for(BillingLineVO lineVO : lineVos){
								{
									lineVO.setBillingLineStatus(STA_INA);
								}
							}
						}else
						if(STA_CAN.equals(newStatus)) {
							for(BillingLineVO lineVO : lineVos){
								{
									lineVO.setBillingLineStatus(STA_CAN);
								}
						log.log(Log.INFO,
								"Changed page of line vos are*** :---->",
								lineVos);
						session.setBillingLineDetails(lineVos);
						}
						}
						
						}
						session.getBillingMatrixVO().
							setBillingMatrixStatus(newStatus);
						session.getBillingMatrixVO().setStatusChanged(true);
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
