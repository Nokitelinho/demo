package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.util.Iterator;
public class SaveMailAcceptanceCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String EMBARGO_EXIST="mailtracking.defaults.embargoexists";
	   private static final String LAT_VIOLATED_WAR = "mailtracking.defaults.war.latvalidation";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
			List<ErrorVO> errors = new ArrayList<>();
	    	Collection<ScannedMailDetailsVO> scannedMailDetailsVOS = null;
	    	MailAcceptanceVO mailAcceptanceVO = (MailAcceptanceVO)actionContext.getAttribute("mailAcceptanceDetails");
	    	ContainerDetailsVO containerDetailsVO = (ContainerDetailsVO)actionContext.getAttribute("containerDetails");
	    	CheckEmbargoSession checkEmbargoSession = getScreenSession("reco.defaults", "reco.defaults.ux.checkembargo");
	    	ResponseVO responseVO = new ResponseVO();
	    	boolean embargoInfo= false;
		OutboundModel outboundModel = new OutboundModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		 List<OutboundModel> results = new ArrayList();
			String assignTo = (String)actionContext.getAttribute("flightCarrierFlag");
			List<ErrorVO> existingerrors = actionContext.getErrors();
			if(CollectionUtils.isNotEmpty(existingerrors) && mailAcceptanceVO !=null && CollectionUtils.isNotEmpty(mailAcceptanceVO.getEmbargoDetails()))
		    {
		    	  Iterator errorsList = existingerrors.iterator();
		    	  while (errorsList.hasNext()) {
		        	  ErrorVO errorVO = (ErrorVO)(errorsList).next();
			          if (EMBARGO_EXIST.equals(errorVO.getErrorCode()) && "W".equals(mailAcceptanceVO.getEmbargoDetails().iterator().next().getEmbargoLevel()))
			          {
			            checkEmbargoSession.setEmbargos(mailAcceptanceVO.getEmbargoDetails());
			            checkEmbargoSession.setContinueAction("mail.operations.ux.outbound.addMailbags");
			            responseVO.setStatus("embargo");
			            errorsList.remove();
			          }
			          if (EMBARGO_EXIST.equals(errorVO.getErrorCode()) && "I".equals(mailAcceptanceVO.getEmbargoDetails().iterator().next().getEmbargoLevel()))
			          {
			        	  embargoInfo=true;
			        	  errorsList.remove();
			          }
					  if (EMBARGO_EXIST.equals(errorVO.getErrorCode()) && "E".equals(mailAcceptanceVO.getEmbargoDetails().iterator().next().getEmbargoLevel()))
			          {
			            checkEmbargoSession.setEmbargos(mailAcceptanceVO.getEmbargoDetails());
			            checkEmbargoSession.setContinueAction("mail.operations.ux.outbound.addMailbags");
			            responseVO.setStatus("embargo");
			            errorsList.remove();
			          }
		       } 
		    	  // if embargo configured as error, warning, return. If embargo configured as info, continue the save
		    	  if(!embargoInfo) {
		    	   actionContext.setResponseVO(responseVO);
		           return;
		    	  }
		    	  else{
		    		ErrorVO displayErrorVO = new ErrorVO(EMBARGO_EXIST);
		  			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
		  			errors.add(displayErrorVO);
		    	  }
		   }
		    else if(CollectionUtils.isNotEmpty(existingerrors)) {
		          return;
		    }
			if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
				mailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());			
			}else{
					mailAcceptanceVO.setInventory(false);	
			}
			
			Collection<ContainerDetailsVO> containerDtlsVOs = new ArrayList<ContainerDetailsVO>();
			containerDtlsVOs.add(containerDetailsVO);
			mailAcceptanceVO.setContainerDetails(containerDtlsVOs);
			mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
			mailAcceptanceVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
			if(mailAcceptanceVO.getContainerDetails()!=null && mailAcceptanceVO.getContainerDetails().size()>0){
				for (ContainerDetailsVO conDetVO : mailAcceptanceVO.getContainerDetails()){					
					if(MailConstantsVO.FLAG_YES.equals(conDetVO.getPaBuiltFlag())){
						if(conDetVO.getMailDetails()!=null && conDetVO.getMailDetails().size()>0){
							for(MailbagVO mail : conDetVO.getMailDetails()){
								mail.setPaBuiltFlag(conDetVO.getPaBuiltFlag());
							}
						}
					}
					conDetVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);	
					/*if(!CONST_FLIGHT.equals(assignTo)){
						conDetVO.setPou(conDetVO.getDestination());
					}*/ //Commented by A-6991 for ICRD-209424
				}
			}
			updateDeletedMailbs(mailAcceptanceVO);
			//mailAcceptanceVO.setMailSource(SCREEN_NUMERICAL_ID);
			log.log(Log.FINE, "Going To Save ...in command", mailAcceptanceVO);
		//	mailAcceptanceSession.setExistingMailbagVO(null);
			List<ErrorVO> warningErrors = new ArrayList<>();
			  try {
				  scannedMailDetailsVOS= new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (CollectionUtils.isNotEmpty(errors)) {
	    		if(errors.size()==1 && (EMBARGO_EXIST).equals(errors.iterator().next().getErrorCode())){
		    		responseVO.setStatus("success_embargo");
	    		}
	    		 if (errors.size() == 1 && (LAT_VIOLATED_WAR).equals(errors.iterator().next().getErrorCode())) {
				for (ErrorVO vo : errors) {
					if (LAT_VIOLATED_WAR.equals(vo.getErrorCode())) {
						ErrorVO warningError = new ErrorVO(vo.getErrorCode());
						warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
						warningErrors.add(warningError);
						actionContext.addAllError(warningErrors);
						return;
					}
				}

			}
	    		actionContext.setResponseVO(responseVO);
	    		actionContext.addAllError(errors);
	    		return;
	    	  }
		if (scannedMailDetailsVOS != null && scannedMailDetailsVOS.size() > 0) {
			log.log(Log.FINE, "scannedMailDetailsVOS!=null && scannedMailDetailsVOS.size()>0");
			ScannedMailDetailsVO scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>) scannedMailDetailsVOS)
					.get(0);
			if (scannedMailDetailsVO.getExistingMailbagVOS() != null
					&& (scannedMailDetailsVO.getExistingMailbagVOS().size()) > 0) {
				log.log(Log.FINE, "scannedMailDetailsVO.getExistingMailbagVOS()!=null ");
				Collection<ExistingMailbagVO> existingMailbagVOS = scannedMailDetailsVO.getExistingMailbagVOS();
				Collection<Mailbag> exMailbags= MailOperationsModelConverter.constructExistingMailbags(existingMailbagVOS);
				outboundModel.setExistingMailbags(exMailbags);
				outboundModel.setExistingMailbagFlag("Y");
				outboundModel.setMailbags((ArrayList)containerDetailsVO.getMailDetails());
			}
		} else {
	    	responseVO.setStatus("success");
	    	  }
		results.add(outboundModel);
        responseVO.setResults(results);
            actionContext.setResponseVO(responseVO);
	    }
	    
	    /**
	     * 
	     * @param mailAcceptanceVO
	     */
	    public void updateDeletedMailbs(MailAcceptanceVO mailAcceptanceVO){
	    	Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
			if(containerDetailsVOs != null && !containerDetailsVOs.isEmpty()){
				for(ContainerDetailsVO conVO : containerDetailsVOs){
					//if(conVO.isMailModifyflag()){
						Collection<MailbagVO> mailVOs =  conVO.getMailDetails();
				    	Collection<MailbagVO> delMails = conVO.getDeletedMailDetails();
				    	if(delMails != null && !delMails.isEmpty()){
			    			for(MailbagVO delMail : delMails){
			    				if(MailbagVO.OPERATION_FLAG_DELETE.equals(delMail.getOperationalFlag())){
			    					mailVOs.add(delMail);
			    				}
				    		}
				    	}
				    	if(mailVOs != null && !mailVOs.isEmpty()) {
			    			for(MailbagVO mail : mailVOs){
			    				if(MailbagVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag()) && mail.getMailSequenceNumber()<=0){//mail sequence number check added as part of IASCB-52624 by A-8672 to handle the insert from deviation and other panels
			    					mail.setMailSequenceNumber(0);
			    				}
				    		}
				    	}
				    	//}
					//}
				}
			}	
	    }
}
