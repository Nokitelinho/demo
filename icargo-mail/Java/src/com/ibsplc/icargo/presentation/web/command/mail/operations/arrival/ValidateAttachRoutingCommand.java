/*
 * ValidateAttachRoutingCommand.java Created on Jun 30 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5991
 * ValidateAttachRoutingCommand
 * extends BaseCommand
 */
public class ValidateAttachRoutingCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS = "screenload_success";
	private static final String TARGET_FAILURE = "screenload_failure";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID_MANIFEST = "mailtracking.defaults.mailmanifest";
	private static final String SCREEN_ID_ARRIVAL = "mailtracking.defaults.mailarrival";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenloadAttachRoutingCommand","execute");
		
		MailArrivalForm  mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
		MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID_MANIFEST);
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID_ARRIVAL);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//ApplicationSessionImpl applicationSession = getApplicationSession();
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDtlsDummyVO = new ContainerDetailsVO();
		/*routing available check and consignment number diff check starts*/
		int routingAvl = 0;
		int csgDocNumDiff = 0;
		int paCodeDiff = 0;
		String csgDocNum = "";
		String paCode = "";
		mailArrivalForm.setCsgDocNumForRouting("");
		mailArrivalForm.setPaCodeForRouting("");
		/*
		 * Selected Container(s)if any
		 */
		if (mailArrivalForm.getSelectContainer()!=null && mailArrivalForm.getSelectContainer().length>0){
			String[] parentCont = mailArrivalForm.getParentContainer().split("-");
			int parentSize=parentCont.length;
			for(int i=0;i<parentSize;i++){
				if(!("").equals(parentCont[i])){
					ContainerDetailsVO containerDetailsVO = 
						((ArrayList<ContainerDetailsVO>)mailArrivalVO.getContainerDetails()).get(Integer.parseInt(parentCont[i]));
					
					if(!containerDetailsVOs.contains(containerDetailsVO)){
						containerDetailsVOs.add(containerDetailsVO);
					}
				}
			}
		}
		
		if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){
			for(ContainerDetailsVO  selContVos :containerDetailsVOs){
				if(selContVos.getDsnVOs()!=null && selContVos.getDsnVOs().size()>0){
					for(DSNVO dsnVO:selContVos.getDsnVOs()){
						if(dsnVO.getCsgDocNum()!=null && 
								dsnVO.getCsgDocNum().length() > 0 &&
								dsnVO.getPaCode() != null &&
								dsnVO.getPaCode().trim().length() > 0){
							if(csgDocNum != null && csgDocNum.trim().length() == 0 ){
								csgDocNum = dsnVO.getCsgDocNum();
							}else{
								if(!csgDocNum.equals(dsnVO.getCsgDocNum())){
									csgDocNumDiff ++;
									break;
								}
							}
							if(paCode != null && paCode.trim().length() == 0 ){
								paCode = dsnVO.getPaCode();
							}else{
								if(!paCode.equals(dsnVO.getPaCode())){
									paCodeDiff ++;
									break;
								}
							}
						}
						
						if(dsnVO.getRoutingAvl()!=null && dsnVO.getRoutingAvl().length()>0){
							if("Y".equals(dsnVO.getRoutingAvl())){
								routingAvl ++;
								break;
							}
						}
					}
					if(csgDocNumDiff > 0 || routingAvl > 0 || paCodeDiff > 0){
						break;
					}
				}
			}
		}
		
		/*
		 * Selected dsns if any
		 */
		if (csgDocNumDiff == 0 && routingAvl == 0 && paCodeDiff == 0){
			if (mailArrivalForm.getChildContainer()!=null && mailArrivalForm.getChildContainer().length>0){
				String[] childDSN = mailArrivalForm.getSelectChild().split(",");
				int childSize=childDSN.length;
				ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
				Collection<DSNVO> dsnVos = new ArrayList<DSNVO>();
				for(int i=0;i<childSize;i++){
					if(!("").equals(childDSN[i]) &&  !("").equals(childDSN[i].split("~")[0])){				
						containerDtlsVO = ((ArrayList<ContainerDetailsVO>)mailArrivalVO.getContainerDetails())
						.get(Integer.parseInt(childDSN[i].split("~")[0]));	
						if(containerDtlsVO != null &&
								(!containerDtlsVO.getContainerNumber().equals(containerDtlsDummyVO.getContainerNumber()))) {
							containerDtlsDummyVO = new ContainerDetailsVO();
							try {
								BeanHelper.copyProperties(containerDtlsDummyVO,containerDtlsVO);
							} catch (SystemException systemException) {
								log.log(Log.FINE, "BeanHelper.copyProperties FAILED !!!! ");
							}    	    	
							dsnVos = new ArrayList<DSNVO>();
							containerDtlsDummyVO.setDsnVOs(dsnVos);
							containerDetailsVOs.add(containerDtlsDummyVO);
						}
						/*
						 * "containerDtlsVO" is the Object Of "mailManifestVO.getContainerDetails" itself 
						 * so this will throw false if "containerDetailsVOs" does not contains this Object.
						 * And new "containerDtlsDummyVO" can be added, since this is for adding the 
						 * DSNVOs to the collection "dsnVos" indirectly. 
						 */
						if(!containerDetailsVOs.contains(containerDtlsVO)){
							if((childDSN[i].split("~")[1]).trim().length() > 0 && containerDtlsVO != null) {
								DSNVO dsn = ((ArrayList<DSNVO>)containerDtlsVO.getDsnVOs()).get(
										Integer.parseInt(childDSN[i].split("~")[1]));
								if(dsn.getRoutingAvl()!=null && dsn.getRoutingAvl().length()>0){
									if("Y".equals(dsn.getRoutingAvl())){
										routingAvl ++;
										break;
									}
								}
								if(dsn.getCsgDocNum()!=null && 
										dsn.getCsgDocNum().length() > 0 &&
										dsn.getPaCode() != null &&
										dsn.getPaCode().trim().length() > 0){
									if("".equals(csgDocNum)){
										csgDocNum = dsn.getCsgDocNum();
									}else{
										if(!csgDocNum.equals(dsn.getCsgDocNum())){
											csgDocNumDiff ++;
											break;
										}
									}
									if(paCode != null && paCode.trim().length() == 0 ){
										paCode = dsn.getPaCode();
									}else{
										if(!paCode.equals(dsn.getPaCode())){
											paCodeDiff ++;
											break;
										}
									}
								}
								dsn.setCompanyCode(containerDtlsVO.getCompanyCode());
								dsn.setContainerNumber(containerDtlsVO.getContainerNumber());
								dsn.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());	
								dsnVos.add(dsn);
							}
						}
					}
				}
			}
			if (csgDocNumDiff == 0 && routingAvl == 0 && paCodeDiff == 0){
				mailArrivalForm.setCsgDocNumForRouting(csgDocNum);
				mailArrivalForm.setPaCodeForRouting(paCode);
			}
		}
		if(routingAvl > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.routingavailable");
			errors.add(error);
			invocationContext.addAllError(errors);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		if(csgDocNumDiff > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgdocnumdiff");
			errors.add(error);
			invocationContext.addAllError(errors);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		if(paCodeDiff > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgpacodediff");
			errors.add(error);
			invocationContext.addAllError(errors);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		mailManifestSession.setMailVOs(createMailInConsignmentVOs(containerDetailsVOs));
		mailArrivalForm.setAttachRouting("OK");
		invocationContext.target = TARGET_SUCCESS;
		return;
	}
	/**
	 * createMailInConsignmentVOs
	 * @param containerDetailsVOs
	 * @return createMailInConsignmentVOs
	 */
	public Collection<MailInConsignmentVO> createMailInConsignmentVOs(
			Collection<ContainerDetailsVO> containerDetailsVOs){
		log.entering("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
				if(dsnVOs != null && dsnVOs.size() > 0){
					dsnVOs.addAll(containerDetailsVO.getDsnVOs());
				}else{
					dsnVOs = containerDetailsVO.getDsnVOs();
					
				}
			}
		}
		Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		Collection<String> dsnKey = new ArrayList<String>();
		
		if (dsnVOs != null && dsnVOs.size() != 0) {
			for (DSNVO dsnVO : dsnVOs) {
				String dsnpk = dsnVO.getOriginExchangeOffice()
				+dsnVO.getDestinationExchangeOffice()
				+dsnVO.getMailCategoryCode()
				+dsnVO.getMailSubclass()
				+dsnVO.getDsn()
				+dsnVO.getYear();
				if(!dsnKey.contains(dsnpk)){
					dsnKey.add(dsnpk);
				}		
				if(!newDsnVOs.contains(dsnVO)){
					newDsnVOs.add(dsnVO);
				}
			}
		}
		
		try {
			containerDetailsVOs = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOs);
		}catch (BusinessDelegateException businessDelegateException) {
			log
					.log(
							Log.SEVERE,
							"BusinessDelegateException---findMailbagsInContainerForManifest",
							businessDelegateException.getMessage());
		}
		for(ContainerDetailsVO contVO : containerDetailsVOs){
			if(contVO.getDsnVOs() != null && contVO.getDsnVOs().size() > 0){
				for(DSNVO dsn : contVO.getDsnVOs()){
					String dsnPKKey  = dsn.getOriginExchangeOffice()
					+dsn.getDestinationExchangeOffice()
					+dsn.getMailCategoryCode()
					+dsn.getMailSubclass()
					+dsn.getDsn()
					+dsn.getYear();
					for(String key : dsnKey){						
						if(key.equals(dsnPKKey)){
							if(dsn.getMailbags() != null && dsn.getMailbags().size() > 0){
								for(MailbagVO bagVO : dsn.getMailbags()){
									mailVOs.add(createMailInConsignmentVO(bagVO));
								}		
							}
							if(contVO.getDesptachDetailsVOs() != null && 
									contVO.getDesptachDetailsVOs().size() > 0){
								for(DespatchDetailsVO despatchVO : contVO.getDesptachDetailsVOs()){
									
									String despatchKey  = despatchVO.getOriginOfficeOfExchange()
									+despatchVO.getDestinationOfficeOfExchange()
									+despatchVO.getMailCategoryCode()
									+despatchVO.getMailSubclass()
									+despatchVO.getDsn()
									+despatchVO.getYear();
									if(key.equals(despatchKey)){
										mailVOs.add(createMailInConsignmentVO(despatchVO));
									}
								}
							}
						}						
					}
				}
			}
		}
		log.exiting("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		return mailVOs;
	} 
	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(MailbagVO bagVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setDsn(bagVO.getDespatchSerialNumber());
		mailInConsignmentVO.setOriginExchangeOffice(bagVO.getOoe());
		mailInConsignmentVO.setDestinationExchangeOffice(bagVO.getDoe());
		mailInConsignmentVO.setMailClass(bagVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(bagVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(bagVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(bagVO.getYear());
		mailInConsignmentVO.setStatedBags(1);
		mailInConsignmentVO.setStatedWeight(bagVO.getWeight());
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		mailInConsignmentVO.setReceptacleSerialNumber(bagVO.getReceptacleSerialNumber());
		mailInConsignmentVO.setMailId(bagVO.getMailbagId());
		mailInConsignmentVO.setHighestNumberedReceptacle(bagVO.getHighestNumberedReceptacle());
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(bagVO.getRegisteredOrInsuredIndicator());
		return mailInConsignmentVO;
	}   
	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(DespatchDetailsVO despatchVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setConsignmentNumber(despatchVO.getConsignmentNumber());
		mailInConsignmentVO.setPaCode(despatchVO.getPaCode());
		mailInConsignmentVO.setDsn(despatchVO.getDsn());
		mailInConsignmentVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
		mailInConsignmentVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
		mailInConsignmentVO.setMailClass(despatchVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(despatchVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(despatchVO.getYear());
		if(despatchVO.getStatedBags() > 0){
			//This is a manifested despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getStatedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getStatedWeight());
		}else{
			//This is a found despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getReceivedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getReceivedWeight());
		}
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		return mailInConsignmentVO;
	}   
	  
}
