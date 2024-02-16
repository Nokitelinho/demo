package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ExistingMailbagsReassignOKCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
    OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	ContainerDetailsVO containerDetailsVO = null;
	ContainerDetails containerDetails = null;
    containerDetails=outboundModel.getSelectedContainer();
    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
	containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	containerDetailsVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	MailAcceptance mailAcceptance = outboundModel.getMailAcceptance();
	MailAcceptanceVO mailAcceptanceVO = MailOutboundModelConverter.constructMailAcceptanceVO(mailAcceptance, logonAttributes);
	containerDetails=outboundModel.getSelectedContainer();
	containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    List<Mailbag> addedmailbags = outboundModel.getMailbags();
    Collection<Mailbag> existingMailbagsList = outboundModel.getExistingMailbags();
    Collection<Mailbag> existingMailbags = new ArrayList<Mailbag>();
    String[] reassign = outboundModel.getReassignFlag();
    if(reassign!=null && reassign.length>0){
 	for(int i=0; i<reassign.length;i++){
 		existingMailbags.add(((ArrayList<Mailbag>)existingMailbagsList).get(Integer.parseInt(reassign[i])));
 	}
    }
    
	if( existingMailbags != null && existingMailbags.size() > 0){
		for(Mailbag existingMailbag:existingMailbags){
					if( addedmailbags != null && addedmailbags.size()>0){
						for(Mailbag mailbag:addedmailbags){
							if ("I".equals(mailbag.getOperationalFlag())) {
								if(existingMailbag.getMailbagId().equals(mailbag.getMailbagId())){
									mailbag.setCarrierCode(existingMailbag.getCarrierCode());
									//mailbag.setFlightDate(existingMailbag.getFlightDate());
									mailbag.setFlightNumber(existingMailbag.getFlightNumber());
									mailbag.setFlightSequenceNumber(existingMailbag.getFlightSequenceNumber());
									mailbag.setLegSerialNumber(existingMailbag.getLegSerialNumber());
									mailbag.setSegmentSerialNumber(existingMailbag.getSegmentSerialNumber());
									mailbag.setPol(existingMailbag.getPol());
									mailbag.setPou(existingMailbag.getPou());
									mailbag.setReassignFlag("Y");
									mailbag.setMailbagId(existingMailbag.getMailbagId());
									mailbag.setCarrierId(existingMailbag.getCarrierId());
									if(!mailAcceptanceVO.isFromDeviationList()) {
										mailbag.setScannedPort(existingMailbag.getCurrentAirport());
									} 
									mailbag.setContainerNumber(existingMailbag.getContainerNumber());
									mailbag.setContainerType(existingMailbag.getContainerType());
									mailbag.setFinalDestination(existingMailbag.getFinalDestination());
									mailbag.setFromSegmentSerialNumber(existingMailbag.getSegmentSerialNumber());
									mailbag.setUldNumber(existingMailbag.getContainerNumber());
									mailbag.setUbrNumber(existingMailbag.getUbrNumber());
									mailbag.setBookingLastUpdateTime(existingMailbag.getBookingLastUpdateTime());
									mailbag.setBookingFlightDetailLastUpdTime(existingMailbag.getBookingFlightDetailLastUpdTime());
									mailbag.setMailSequenceNumber(existingMailbag.getMailSequenceNumber());

									//newMailbags.add(mailbag);
								}
							}
						}
					}
				
		}
	}
	
	/**
	 * To remove VOs which are not selected for Reassign
	 */

	if(existingMailbags !=null && existingMailbags.size()>0){
		existingMailbagsList.removeAll(existingMailbags);
	}

/*	
 * commented as a part of IASCB-39444
 * 
 * if(existingMailbagsList!= null && existingMailbagsList.size()>0){    
		for(Mailbag existingMail : existingMailbagsList){
					if( addedmailbags != null && addedmailbags.size()>0){
						Collection<Mailbag> mailbagsToRemove = new ArrayList<Mailbag>();
						for(Mailbag mailbag:addedmailbags){
							if ("I".equals(mailbag.getOperationalFlag())) {
								if(existingMail.getMailbagId().equals(mailbag.getMailbagId())){
									mailbagsToRemove.add(mailbag);

									
								}
							}
						}
						addedmailbags.removeAll(mailbagsToRemove);
					}
		    }
	}*/
	  
	List<MailbagVO> mailbagVOs = new ArrayList<>();
	for(Mailbag mailbags :addedmailbags) {
		mailbagVOs.add(MailOutboundModelConverter.constructMailbagVO(mailbags, logonAttributes));
	}
	
	containerDetailsVO.setMailDetails(mailbagVOs);
	List<ContainerDetailsVO> containerVOs = new ArrayList<>();
	containerVOs.add(containerDetailsVO);
	mailAcceptanceVO.setDuplicateMailOverride("Y");
	actionContext.setAttribute("containerDetails", containerDetailsVO);
    actionContext.setAttribute("mailAcceptanceDetails", mailAcceptanceVO);
    actionContext.setAttribute("flightCarrierFlag", outboundModel.getFlightCarrierflag());
   
// DefaultScreenSessionImpl screenSession= getScreenSession();
	}
}
