/*
 * PartnerCarrierDetailsCommand.java Created on MAR 18, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class PartnerCarrierDetailsCommand extends BaseCommand {

	private static final String SUCCESS = "success";
	private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {

		log.log(Log.FINE, "\n\n in PartnerCarrierDetailsCommand----------> \n\n");

		PartnerCarriersForm partnerCarriersForm =(PartnerCarriersForm)invocationContext.screenModel;
		PartnerCarrierSession partnerCarrierSession = getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		ArrayList<PartnerCarrierVO> partnerCarrierVOs = partnerCarrierSession.getPartnerCarrierVOs();
		if (partnerCarrierVOs == null) {
			partnerCarrierVOs = new ArrayList<PartnerCarrierVO>();
		}
		
		partnerCarrierVOs = updatePartnerCarrierVOs(partnerCarrierVOs,partnerCarriersForm,logonAttributes);
		partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
		partnerCarriersForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;	

	}
	/**
	 * Method to update the partnerCarrierVOs in session
	 * @param partnerCarrierVOs
	 * @param partnerCarriersForm
	 * @param logonAttributes
	 * @return
	 */
	private ArrayList<PartnerCarrierVO> updatePartnerCarrierVOs(ArrayList<PartnerCarrierVO> partnerCarrierVOs,
			PartnerCarriersForm partnerCarriersForm,LogonAttributes logonAttributes) {

		log.entering("PartnerCarrierDetailsCommand","updatePartnerCarrierVOs");

		String[] oprflags = partnerCarriersForm.getOperationFlag();

		int size = 0;
		if(partnerCarrierVOs != null && partnerCarrierVOs.size() > 0){
			size = partnerCarrierVOs.size();
		}
		Collection<PartnerCarrierVO> newPartnerCarrierVOs = new ArrayList<PartnerCarrierVO>();
		for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
					partnerCarrierVO.setCompanyCode(logonAttributes.getCompanyCode());
					partnerCarrierVO.setAirportCode(partnerCarriersForm.getAirport().toUpperCase().trim());
					partnerCarrierVO.setOwnCarrierCode(logonAttributes.getOwnAirlineCode());
					partnerCarrierVO.setPartnerCarrierCode(partnerCarriersForm.getPartnerCarrierCode()[index].toUpperCase().trim());
					partnerCarrierVO.setPartnerCarrierName(partnerCarriersForm.getPartnerCarrierName()[index]);
					partnerCarrierVO.setOperationFlag(partnerCarriersForm.getOperationFlag()[index]);
					partnerCarrierVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					newPartnerCarrierVOs.add(partnerCarrierVO);
				}
			}else{
				int count = 0;
				if(partnerCarrierVOs != null && partnerCarrierVOs.size() > 0){
					for(PartnerCarrierVO partnerCarrierVO:partnerCarrierVOs){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
								partnerCarrierVO.setPartnerCarrierCode(partnerCarriersForm.getPartnerCarrierCode()[index].toUpperCase().trim());
								partnerCarrierVO.setPartnerCarrierName(partnerCarriersForm.getPartnerCarrierName()[index]);
								if("N".equals(oprflags[index])){
									partnerCarrierVO.setOperationFlag(null);
								}else{
									partnerCarrierVO.setOperationFlag(oprflags[index]);
								}
								newPartnerCarrierVOs.add(partnerCarrierVO);
							}
						}
						count++;
					}
				}
			}
		}
		log.log(Log.FINE, "Updated partnerCarrierVOs------------> ",
				newPartnerCarrierVOs);
		log.exiting("PartnerCarrierDetailsCommand","updatePartnerCarrierVOs");

		return (ArrayList<PartnerCarrierVO>)newPartnerCarrierVOs;    	
	}

}
