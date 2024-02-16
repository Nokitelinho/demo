/*
 * UpdateAttachRoutingCommand.java Created on Jul 1 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class UpdateAttachRoutingCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	private static final String TARGET = "success";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("UpdateAttachRoutingCommand","execute");
		
		MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
		MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ConsignmentDocumentVO consignmentDocumentVO = mailManifestSession.getConsignmentDocumentVO();
		
		String conDocNo = mailManifestForm.getConDocNo();
		String paCode = mailManifestForm.getPaCode();
		String direction = mailManifestForm.getDirection();
		String conDate = mailManifestForm.getConDate();
		String type = mailManifestForm.getContype();
		String remarks = mailManifestForm.getRemarks();
		if (conDocNo != null && conDocNo.trim().length() > 0) {
			consignmentDocumentVO.setConsignmentNumber(conDocNo.toUpperCase());
		}
		if (paCode != null && paCode.trim().length() > 0) {
			consignmentDocumentVO.setPaCode(paCode.toUpperCase());
		}
		if (direction != null && direction.trim().length() > 0) {
			consignmentDocumentVO.setOperation(direction);
		}
		LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		if (conDate != null && conDate.trim().length() > 0) {
			consignmentDocumentVO.setConsignmentDate(cd.setDate(conDate));
		}
		if (type != null && type.trim().length() > 0) {
			consignmentDocumentVO.setType(type);
		}
		if (remarks != null && remarks.trim().length() > 0) {
			consignmentDocumentVO.setRemarks(remarks);
		}
		
		Collection<RoutingInConsignmentVO> routingVOs =  consignmentDocumentVO.getRoutingInConsignmentVOs();
		
		/*
		 * For Routing Details
		 */
		String[] flightCarrierCode = mailManifestForm.getFlightRouteCarrierCode();
		String[] flightNumber = mailManifestForm.getFlightRouteNumber();
		String[] depDate = mailManifestForm.getDepRouteDate();
		String[] pou = mailManifestForm.getPouRoute();
		String[] pol = mailManifestForm.getPolRoute();
		
		String[] routeOpFlag = mailManifestForm.getRouteOpFlag();
		
		int size = 0;
		
		if(routingVOs != null && routingVOs.size() > 0){
			size = routingVOs.size();
		}
		Collection<RoutingInConsignmentVO> newRoutingVOs = new ArrayList<RoutingInConsignmentVO>();
		for(int index=0; index<routeOpFlag.length;index++){
			if(index >= size){
				if(!"NOOP".equals(routeOpFlag[index])){
					RoutingInConsignmentVO newRoutingVO = new RoutingInConsignmentVO();
					newRoutingVO.setOperationFlag(routeOpFlag[index]);
					if(flightCarrierCode != null) {
						if(flightCarrierCode[index] != null && !("".equals(flightCarrierCode[index]))) {
							if(!"I".equals(newRoutingVO.getOperationFlag())){
								if(!flightCarrierCode[index].equals(newRoutingVO.getOnwardCarrierCode())){
									newRoutingVO.setOperationFlag("U");
								}
							}
							newRoutingVO.setOnwardCarrierCode(flightCarrierCode[index].toUpperCase());
						}
					}
					if(flightNumber != null) {
						if(flightNumber[index] != null && !("".equals(flightNumber[index]))) {
							if(!"I".equals(newRoutingVO.getOperationFlag())){
								if(!flightNumber[index].equals(newRoutingVO.getOnwardFlightNumber())){
									newRoutingVO.setOperationFlag("U");
								}
							}
							newRoutingVO.setOnwardFlightNumber(flightNumber[index].toUpperCase());
						}
					}
					if(pol != null) {
						if(pol[index] != null && !("".equals(pol[index]))) {
							if(!"I".equals(newRoutingVO.getOperationFlag())){
								if(!pol[index].equals(newRoutingVO.getPol())){
									newRoutingVO.setOperationFlag("U");
								}
							}
							newRoutingVO.setPol(pol[index].toUpperCase());
						}
					}
					if(pou != null) {
						if(pou[index] != null && !("".equals(pou[index]))) {
							if(!"I".equals(newRoutingVO.getOperationFlag())){
								if(!pou[index].equals(newRoutingVO.getPou())){
									newRoutingVO.setOperationFlag("U");
								}
							}
							newRoutingVO.setPou(pou[index].toUpperCase());
						}
					}
					
					if(depDate != null) {
						if(depDate[index] != null && !("".equals(depDate[index]))) {
							String fltdt = "";
							if(newRoutingVO.getOnwardFlightDate() != null){
								fltdt = newRoutingVO.getOnwardFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT);
							}
							if(!"I".equals(newRoutingVO.getOperationFlag())){
								if(!depDate[index].equals(fltdt)){
									newRoutingVO.setOperationFlag("U");
								}
							}
							LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
							newRoutingVO.setOnwardFlightDate(sd.setDate(depDate[index]));
						}
					}
					
					newRoutingVOs.add(newRoutingVO);
				}
			}else{
				int count = 0;
				if(routingVOs != null && routingVOs.size() > 0) {
					for(RoutingInConsignmentVO newRoutingVO:routingVOs) {
						if(count == index){
							if(!"NOOP".equals(routeOpFlag[index])){
								if("N".equals(routeOpFlag[index])){
									newRoutingVO.setOperationFlag(null);
								}else{
									newRoutingVO.setOperationFlag(routeOpFlag[index]);
								}
								if(flightCarrierCode != null) {
									if(flightCarrierCode[index] != null && !("".equals(flightCarrierCode[index]))) {
										if(!"I".equals(newRoutingVO.getOperationFlag())){
											if(!flightCarrierCode[index].equals(newRoutingVO.getOnwardCarrierCode())){
												if(!"D".equals(newRoutingVO.getOperationFlag())){
													newRoutingVO.setOperationFlag("U");
												}
											}
										}
										newRoutingVO.setOnwardCarrierCode(flightCarrierCode[index].toUpperCase());
									}
									else
									{
										newRoutingVO.setOnwardCarrierCode("");
									}
								}
								if(flightNumber != null) {
									if(flightNumber[index] != null && !("".equals(flightNumber[index]))) {
										if(!"I".equals(newRoutingVO.getOperationFlag())){
											if(!flightNumber[index].equals(newRoutingVO.getOnwardFlightNumber())){
												if(!"D".equals(newRoutingVO.getOperationFlag())){
													newRoutingVO.setOperationFlag("U");
												}
											}
										}
										newRoutingVO.setOnwardFlightNumber(flightNumber[index].toUpperCase());
									}
									else
									{
										newRoutingVO.setOnwardFlightNumber("");
									}
								}
								if(pol != null) {
									if(pol[index] != null && !("".equals(pol[index]))) {
										if(!"I".equals(newRoutingVO.getOperationFlag())){
											if(!pol[index].equals(newRoutingVO.getPol())){
												if(!"D".equals(newRoutingVO.getOperationFlag())){
													newRoutingVO.setOperationFlag("U");
												}
											}
										}
										newRoutingVO.setPol(pol[index].toUpperCase());
									}
									else
									{
										newRoutingVO.setPol("");
									}
								}
								if(pou != null) {
									if(pou[index] != null && !("".equals(pou[index]))) {
										if(!"I".equals(newRoutingVO.getOperationFlag())){
											if(!pou[index].equals(newRoutingVO.getPou())){
												if(!"D".equals(newRoutingVO.getOperationFlag())){
													newRoutingVO.setOperationFlag("U");
												}
											}
										}
										newRoutingVO.setPou(pou[index].toUpperCase());
									}
									else
									{
										newRoutingVO.setPou("");
									}
								}
								
								if(depDate != null) {
									if(depDate[index] != null && !("".equals(depDate[index]))) {
										if(!"I".equals(newRoutingVO.getOperationFlag())){
											if(!depDate[index].equals(newRoutingVO.getOnwardFlightDate().toDisplayFormat())){
												if(!"D".equals(newRoutingVO.getOperationFlag())){
													newRoutingVO.setOperationFlag("U");
												}
											}
										}
										LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
										newRoutingVO.setOnwardFlightDate(sd.setDate(depDate[index]));
									}
								}
								newRoutingVOs.add(newRoutingVO);
							}
						}
						count++;
					}
				}
			}
		}
		
		
		
		consignmentDocumentVO.setRoutingInConsignmentVOs(newRoutingVOs);
		
		mailManifestSession.setConsignmentDocumentVO(consignmentDocumentVO);
		
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = TARGET;
		
		log.exiting("UpdateConsignmentCommand","execute");
		
	}
	
}
