/**
 * ReturnCommand.java Created on January 16, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReturnDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author a-4823
 *
 */
public class ReturnCommand extends BaseCommand{

	//private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.national.return";
	private static final String SCREEN_ID_ASSIGN = "mailtracking.defaults.national.assignmailbag";

	private static final String RETURN_REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String RT = "RETURN";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID_ASSIGN);
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationcontext.screenModel;	
		ReturnDispatchSession returnSession = getScreenSession( MODULE_NAME, SCREEN_ID);
		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();		
		String [] selectedRows  = assignMailBagForm.getRowId();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Collection<DSNVO> dsnVOSToReturn = new ArrayList<DSNVO>();
		ContainerDetailsVO containerDetailsVO = mailManifestVO.getContainerDetails().iterator().next();
		containerDetailsVO.setCarrierCode(mailManifestVO.getFlightCarrierCode());
		List<DSNVO> dsnList = (List<DSNVO>)mailManifestVO.getContainerDetails().iterator().next().getDsnVOs();
		DSNVO dsnvo = null;
		if(selectedRows != null && selectedRows.length >0){
			for(String row : selectedRows){
				if(row != null && row.trim().length() >0){
					dsnvo = dsnList.get(Integer.parseInt(row));
					dsnvo.setFlightNumber(containerDetailsVO.getFlightNumber());
					dsnvo.setCarrierId(containerDetailsVO.getCarrierId());
					dsnvo.setCarrierCode(containerDetailsVO.getCarrierCode());
					dsnvo.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
					dsnvo.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
					dsnvo.setPou(containerDetailsVO.getPou());
					dsnvo.setPol(containerDetailsVO.getPol());
					
					dsnVOSToReturn.add(dsnList.get(Integer.parseInt(row)));
				}

			}
			
			validateDespatchesForReturn(dsnVOSToReturn, errorVOs);
			//Added code to return while error is thrown bug fix for icrd-18355 by a-4810
			if(errorVOs  != null && errorVOs.size() >0){
				invocationcontext.addAllError(errorVOs);
				invocationcontext.target = SCREENLOAD_SUCCESS;
				return;
				
			}
			
			returnSession.setSelectedDSNVO(dsnVOSToReturn);
			assignMailBagForm.setPopupFlag(RT);
			invocationcontext.target = SCREENLOAD_SUCCESS;


		}
	}
	/**
	 * 
	 * @param dsnVOSToReturn
	 * @param errorVOs
	 * This method is used for checking whether origin of the consignment is same as the login port,then only the despatch can be returned.
	 */
	private void validateDespatchesForReturn(Collection<DSNVO> dsnVOSToReturn,Collection<ErrorVO> errorVOs){
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO(); 
		for(DSNVO dsnvo : dsnVOSToReturn){
			if(!dsnvo.getOriginExchangeOffice().substring(2, 5).equals(logonAttributes.getAirportCode())){
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.assignmailbag.error.despatchescannotbereturned");
				errorVOs.add(errorVO);
				return;
			}
			
		}
		
	}
	

}
