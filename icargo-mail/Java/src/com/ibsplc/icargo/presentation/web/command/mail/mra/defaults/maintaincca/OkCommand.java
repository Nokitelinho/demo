/* OkCommand.java Created on July-21-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * @author A-3447
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

/**
 * @author A-3447
 */
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3447
 * 
 */
public class OkCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "OKCommand";

	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaincca";

	/**
	 * target action
	 */
	private static final String OK_SUCCESS = "ok_success";

	private static final String OK_FAILURE = "ok_failure";
	private static final String DELETED ="D";
	private static final String ERR_DELETE = "mailtracking.mra.maintaincca.selectedmcadeleted";
	/**@author A-3447
	 * Execute method 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		MaintainCCASession maintainCCASession =
			 (MaintainCCASession) getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CCAdetailsVO ccaDetailsVO=new CCAdetailsVO();
		Collection<CCAdetailsVO> ccadetails = new ArrayList<CCAdetailsVO>();
		ErrorVO displayErrorVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MaintainCCAFilterVO maintainCCAFilterVO=maintainCCASession.getMaintainCCAFilterVO();
		Collection<String> ccaRefNumbers=maintainCCASession.getCCARefNumbers();
		String counter = maintainCCAForm.getSelectedRow();
		
		String ccaReferenceNumber = ((ArrayList<String>)ccaRefNumbers).get(Integer.parseInt(counter));
		//Added by A-4809 for ICRD-144068 starts
		ccadetails = maintainCCASession.getCCAdetailsVOs();
		if(ccadetails!=null && !ccadetails.isEmpty()){
		ccaDetailsVO = ((ArrayList<CCAdetailsVO>)ccadetails).get(Integer.parseInt(counter));
			if(ccaDetailsVO.getCcaRefNumber().equals(ccaReferenceNumber)){
				if(DELETED.equals(ccaDetailsVO.getCcaStatus())){
					displayErrorVO = new ErrorVO(ERR_DELETE);
					displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(displayErrorVO);
					invocationContext.addAllError(errors);
					maintainCCAForm.setPopupon(CCAdetailsVO.FLAG_NO); 
					invocationContext.target = OK_FAILURE;
					return;
				}else{
					ccaDetailsVO=new CCAdetailsVO();
				}
			}else{
				ccaDetailsVO=new CCAdetailsVO();
			}
		}
		// Added by A-4809 for ICRD-144068 ends 
		maintainCCAFilterVO.setCcaReferenceNumber(ccaReferenceNumber);
		maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
		ccaDetailsVO.setCcaRefNumber(ccaReferenceNumber);
		maintainCCASession.setCCAdetailsVO(ccaDetailsVO);
		
		invocationContext.target = OK_SUCCESS;
		log.exiting("OkCommand", "execute");

	}

}
