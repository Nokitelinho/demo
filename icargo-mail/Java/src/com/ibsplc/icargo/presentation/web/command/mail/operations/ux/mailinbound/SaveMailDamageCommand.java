package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.SaveMailDamageCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	11-Jan-2019		:	Draft
 */
public class SaveMailDamageCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS SaveMailDamageCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("SaveMailDamageCommand", "execute");
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		ScreenSession  screenSession = getScreenSession();   
		HashMap fileUploadMap = (HashMap)screenSession.getFromScreenSessionMap("fileUploadMap");
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<DamagedMailbag> damagedMailBagCollection=
				mailinboundModel.getDamagedMailBagCollection();
		Collection<DamagedMailbagVO> damagedMailbagVOs=
				new ArrayList<DamagedMailbagVO>();
		Collection<MailAttachmentVO> mailDamageAttachmentVOs=null;
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		ArrayList<MailinboundModel> results = new ArrayList<MailinboundModel>();
		ResponseVO responseVO = new ResponseVO();
		ContainerDetailsVO containerDetailsVO=null;
		Collection<MailbagVO> mailbagVOs=null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ContainerDetails containerDetails=mailinboundModel.getContainerDetail();
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				containerDetails.getMailBagDetailsCollection();
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		OperationalFlightVO operationalFlightVO=null;
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
		if(null!=containerDetailsVos&&containerDetailsVos.size()>0){
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVos){
				if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
					containerDetailsVO=containerDetailsVOIterate;
					break;
							
				}
			}
			
			mailDetailsMap.clear();
			for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				String mailKey=mailbagVO.getMailbagId();
				mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				mailDetailsMap.put(mailKey, mailbagVO);	
			}
			Collection<MailbagVO> selectedMailBagVos=
					 new ArrayList<MailbagVO>();
			selectedMailBagVos.clear();
			if(null!=mailBagDetailsCollection&&mailBagDetailsCollection.size()>0){
				for(MailBagDetails mailBagDetailsIterate:mailBagDetailsCollection){
					String mailbagKey=new StringBuilder(mailBagDetailsIterate.getDSN()).append(mailBagDetailsIterate.getOoe())
							.append(mailBagDetailsIterate.getDoe()).append(mailBagDetailsIterate.getCategory()).append(mailBagDetailsIterate.getSubClass())
							.append(mailBagDetailsIterate.getYear()).toString();
					if(mailDetailsMap.containsKey(mailBagDetailsIterate.getMailBagId())){
						selectedMailBagVos.add(mailDetailsMap.get(mailBagDetailsIterate.getMailBagId()));
					}
				}
				containerDetailsVO.setMailDetails(selectedMailBagVos);
			}
		}
		
		if(null!=damagedMailBagCollection){
			
			for(DamagedMailbag damagedMailbag : damagedMailBagCollection){
				if(damagedMailbag.getDamageCode()==null){
					ErrorVO errorVO = new ErrorVO("Please specify damages");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
			}
			
			damagedMailbagVOs=
					MailInboundModelConverter.populateDamagedMailbagVOs(damagedMailBagCollection, logonAttributes,mailinboundModel.getOneTimeValues());
			mailDamageAttachmentVOs = MailInboundModelConverter.constructDamagedAttachmentVOs(damagedMailBagCollection,fileUploadMap,
					logonAttributes);
			for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_UPDATE);
				mailbagVO.setDamagedMailbags(damagedMailbagVOs); 
				mailbagVO.setDamageFlag(MailbagVO.FLAG_YES);
				mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
							Location.NONE, true));
				mailbagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				if(containerDetailsVO.getContainerType().equals(MailConstantsVO.BULK_TYPE)){
					mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				}
				mailbagVO.setAttachments(mailDamageAttachmentVOs);
			}		
			
		
			
		
			try {
				mailTrackingDefaultsDelegate.saveDamageDetailsForMailbag(containerDetailsVO.getMailDetails());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
		}
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.mailbagnotfound"));
		}
		
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO("mail.operations.succ.damagecapturesuccess");      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
		actionContext.setResponseVO(responseVO);
		log.exiting("SaveMailDamageCommand", "execute");
		
	}

}
