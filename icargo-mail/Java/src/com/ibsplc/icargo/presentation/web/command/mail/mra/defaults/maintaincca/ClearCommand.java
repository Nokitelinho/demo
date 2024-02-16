/*
 * ClearCommand.java Created on July 23-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

/**
 * @author A-3447
 */
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;

import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * 
 */

public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "--ClearCommand--";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * target action
	 */
	private static final String CLEAR_SUCCESS = "clear_success";

	/**
	 * for clearing Fields
	 * 
	 */
	private static final String BLANK="";
	/**
	 * DSN POPUP SCREENID
	 */
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	
	private static final String YES = "Y";
	
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;		
		maintainCCASession.removeCCAdetailsVO();
		maintainCCASession.removeMaintainCCAFilterVO();
		maintainCCASession.removeCCAdetailsVOs();
		maintainCCASession.removeCCARefNumbers();
		maintainCCASession.removeRateAuditVO();
		maintainCCASession.removeUsrCCANumFlg();
		maintainCCASession.setFromScreen(BLANK); // as part of ICRD-132548
		maintainCCAForm.setAutoratedFlag("N");
		maintainCCAForm.setAirlineCode(BLANK);
		maintainCCAForm.setIssueDate(BLANK);
		maintainCCAForm.setCcaNum(BLANK);
		maintainCCAForm.setCcaStatus(BLANK);
		maintainCCAForm.setDsnNumber(BLANK);
		maintainCCAForm.setDsnDate(BLANK);
		maintainCCAForm.setConDocNo(BLANK);
		maintainCCAForm.setOrigin(BLANK);
		maintainCCAForm.setDestination(BLANK);
		maintainCCAForm.setOriginCode(BLANK);
		maintainCCAForm.setDestnCode(BLANK);
		maintainCCAForm.setCategory(BLANK);
		maintainCCAForm.setSubclass(BLANK);
		maintainCCAForm.setIssueParty("ARL");
		maintainCCAForm.setReason1(BLANK);
		maintainCCAForm.setReason2(BLANK);
		maintainCCAForm.setReason3(BLANK);
		maintainCCAForm.setReason4(BLANK);
		maintainCCAForm.setReason5(BLANK);
		maintainCCAForm.setBilfrmdate(BLANK);
		maintainCCAForm.setBiltodate(BLANK);
		maintainCCAForm.setUsrCCANumFlg(BLANK);
		maintainCCAForm.setDsnPopupFlag(FLAG_NO);
		maintainCCAForm.setPopupon(BLANK);
		maintainCCAForm.setShowpopUP(BLANK);
		maintainCCAForm.setRevGpaCode(BLANK);
		maintainCCAForm.setRevCurCode(BLANK);
		maintainCCAForm.setCcaPresent(BLANK);
		if(!YES.equals(maintainCCAForm.getAfterSave())){
			maintainCCAForm.setFromScreen(BLANK);
		}else{
			maintainCCAForm.setAfterSave(BLANK);
		}
		maintainCCAForm.setGpaCode(BLANK);
		maintainCCAForm.setDsnNumber(BLANK);
		maintainCCAForm.setAirlineCode(BLANK);
		maintainCCAForm.setRateAuditedFlag(BLANK);
		maintainCCAForm.setIsAutoMca(BLANK);   //Added by A-7540
		if("NAVIGATIONSAVE".equals(maintainCCAForm.getAfterSave())){
			maintainCCAForm.setFromScreen("listbilling");
			String info = "mailtracking.mra.defaults.maintaincca.autosaveinfo";
			invocationContext.addError(new ErrorVO(info));
		}
		else if("NAVIGATIONSAVESUCCESS".equals(maintainCCAForm.getAfterSave()))
		{
			maintainCCAForm.setFromScreen("listbillingentriesuxopenPopUp");
		//	String info = "mailtracking.mra.defaults.maintaincca.autosaveinfo";
			//invocationContext.addError(new ErrorVO(info));
		}
		//maintainCCAForm.setFromScreen(BLANK); 
		maintainCCAForm.setRevisedRate(BLANK);//Added by A-7929 as part of ICRD-132548
		maintainCCAForm.setRate(BLANK);
		String info =  "";
		if(maintainCCASession.getStatusinfo()!=null){			
			if("DELETE".equals(maintainCCASession.getStatusinfo())){
				info = "mailtracking.mra.defaults.maintaincca.deleteinfo";
				invocationContext.addError(new ErrorVO(info));
			}else if("UPDATE".equals(maintainCCASession.getStatusinfo())){
				info = "mailtracking.mra.defaults.maintaincca.updateinfo";
				invocationContext.addError(new ErrorVO(info));
			}else if("INSERT".equals(maintainCCASession.getStatusinfo())){
				if(!"INVALID".equals(maintainCCASession.getRefNoToDisp())){
					Object[] obj = {maintainCCASession.getRefNoToDisp()};
					info = "mailtracking.mra.defaults.maintaincca.saveinfo";
					invocationContext.addError(new ErrorVO(info,obj));
				}				
			}
			//Added as part of ICRD-329873
			else if("C".equals(maintainCCASession.getStatusinfo())){
				info = "mailtracking.mra.defaults.maintaincca.accepted";
				invocationContext.addError(new ErrorVO(info));
			}
		}
		maintainCCAForm.setReasonCheck(null); 
		Collection<CRAParameterVO> cRAParameterVOs = maintainCCASession.getCRAParameterVOs();
		if(cRAParameterVOs != null && !cRAParameterVOs.isEmpty()){
			for(CRAParameterVO cRAParameterVO : cRAParameterVOs){
				cRAParameterVO.setParameter(null);						
			}
		}
		maintainCCASession.removeStatusinfo();
		maintainCCASession.removeSurchargeCCAdetailsVOs();
		maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
	}
}
