package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AddMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.AddMailbagCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	01-Nov-2018		:	Draft
 */
public class AddMailbagCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS AddMailbagCommand");
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";//added by A-8353 for ICRD-274933
	private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private static final String INT_REGEX = "[0-9]+";
	private static final String SPECIAL_CHARACTER_REGEX="[A-Za-z0-9]+";
	private static final String EMBARGO_EXIST="mail.operations.err.embargoexists";
	private static final String EMBARGO_EXISTS = "Embargo Exists";
	private static final String INBOUND = "I";
	private static final String FLIGHT_VALIDATION = "mail.operations.ignoretobeactionedflightvalidation";
	private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("AddMailbagCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();      
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO =null;
		ContainerDetails containerDetails = 
				(ContainerDetails) mailinboundModel.getContainerDetail();
		Collection<ContainerDetailsVO> containerDetailsVos=null;   
		ArrayList<AddMailbag> addMailbags=
				(ArrayList<AddMailbag>) mailinboundModel.getAddMailbags(); 
		MailArrivalVO mailArrivalVO =null;

		ResponseVO responseVO = new ResponseVO();
		
		MailTrackingDefaultsDelegate defaultsDelegate =
				new MailTrackingDefaultsDelegate();
		ContainerDetailsVO containerDetailsVO=
				new ContainerDetailsVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<ErrorVO> embargoErrors = new ArrayList<ErrorVO>();
		
    	CheckEmbargoSession checkEmbargoSession = getScreenSession("reco.defaults", "reco.defaults.ux.checkembargo");
		MailArrivalVO mailArrivalDetailsVO = (MailArrivalVO)actionContext.getAttribute("mailArrivalDetails");
		List<ErrorVO> existingerrors = actionContext.getErrors();
		boolean embargoInfo= false;
		if(CollectionUtils.isNotEmpty(existingerrors) && CollectionUtils.isNotEmpty(mailArrivalDetailsVO.getEmbargoDetails()))
	    {
	    	  Iterator errorsList = existingerrors.iterator();
	    	  while (errorsList.hasNext()) {
	        	  ErrorVO errorVO = (ErrorVO)(errorsList).next();
	        	  if (EMBARGO_EXIST.equals(errorVO.getErrorCode()))
		          {
	        		  if(MailConstantsVO.INFO.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
		        		  embargoInfo=true;
		        		  errorsList.remove();
		        	  	}else{
			        		  checkEmbargoSession.setEmbargos(mailArrivalDetailsVO.getEmbargoDetails());
			        		  if(MailConstantsVO.WARNING.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
			        			  checkEmbargoSession.setContinueAction("mail.operations.ux.mailinbound.addmailbags");
			        		  }
		        			  responseVO.setStatus("embargo");
			        		  errorsList.remove();
			        	  } 
		          	} 
	    	  } 
	    	  if(!embargoInfo) {
	    		  actionContext.setResponseVO(responseVO);
		           return;
	    	  }
	    	  else{
	    		ErrorVO displayErrorVO = new ErrorVO(EMBARGO_EXISTS);
	  			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
	  			embargoErrors.add(displayErrorVO);
	    	  }
	   }
	    else if(CollectionUtils.isNotEmpty(existingerrors)) {
	          return;
	    }
		
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
		for(ContainerDetailsVO containerDetailsVoIterate:containerDetailsVos){
			if(containerDetailsVoIterate.getContainerNumber().equals(
					containerDetails.getContainerno())){
				containerDetailsVO=containerDetailsVoIterate;
				break;	
			}
		}
		
		if(containerDetailsVO.getContainerNumber()==null 
				||containerDetailsVO.getContainerNumber().trim().length()==0){ 
			actionContext.addError(new ErrorVO(
					"Container not present in the selected flight. Please relist"));
	  		return;
		}
		
		//containerDetailsVO.setContainerNumber(mailArrivalForm.getContainerNo().trim().toUpperCase());
    	containerDetailsVO.setPol(containerDetails.getPol());
    	containerDetailsVO.setContainerType(containerDetails.getContainerType());
    	if(containerDetails!=null && containerDetails.isPaBuiltCheck()){
    		containerDetailsVO.setPaBuiltFlag("Y");
    		}
    		else{
    		containerDetailsVO.setPaBuiltFlag("N");
    		}
    	//containerDetailsVO.setPaBuiltFlag();
    	
    	Collection<DespatchDetailsVO> despatchDetailsVOs = 
    			containerDetailsVO.getDesptachDetailsVOs();
    	Collection<MailbagVO> mailbagVOs = 
    			containerDetailsVO.getMailDetails();
    	Collection<MailbagVO> newMailbagVOs =new ArrayList<>();
    	
    	try{
    	 newMailbagVOs =
    			populateMailBagVos(addMailbags,containerDetailsVO,mailinboundDetails);
    	}
    	 catch (Exception e) {
    		 actionContext.addError(new ErrorVO("Invalid Mailbags"));
			return;
 		}
    	containerDetailsVO.setMailDetails(newMailbagVOs);
    	containerDetailsVO.setOperationFlag("U");
    	
    	StringBuilder errMails = new StringBuilder();
    	
    	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
    		
			  for(MailbagVO newMailbagVO:newMailbagVOs) {
				  
				  /*
				   * For creating mailId.. using instead of Ajax
				   */
				  String mailId = new StringBuilder()
				            .append(newMailbagVO.getOoe())
				            .append(newMailbagVO.getDoe())
							.append(newMailbagVO.getMailCategoryCode())
							.append(newMailbagVO.getMailSubclass())
							.append(newMailbagVO.getYear())
							.append(newMailbagVO.getDespatchSerialNumber())
							.append(newMailbagVO.getReceptacleSerialNumber())
							.append(newMailbagVO.getHighestNumberedReceptacle())
							.append(newMailbagVO.getRegisteredOrInsuredIndicator())
							.append(newMailbagVO.getStrWeight()).toString();
				  
				  //newMailbagVO.setMailbagId(mailId);
				  if((newMailbagVO.getMailbagId().trim().length()==29 && !validateMailTagFormat(newMailbagVO.getMailbagId()))||!isValidMailtag(newMailbagVO.getMailbagId(),false)||newMailbagVO.getStrWeight()==null){
						actionContext.addError(new ErrorVO("Invalid Mailbag:"+newMailbagVO.getMailbagId()));
						return;
					}
				  if(newMailbagVO.getLatestScannedDate()==null){
						LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				    	newMailbagVO.setLatestScannedDate(currentDate);
				  }
				  
				if (newMailbagVO.getOperationalFlag() != null) {
					if (MailConstantsVO.FLAG_YES.equals(newMailbagVO.getDeliveredFlag())
							&& MailConstantsVO.FLAG_YES.equals(newMailbagVO.getTransferFlag())) {
						errMails.append(newMailbagVO.getMailbagId()).append(", ");
					}
				}
				
				if (newMailbagVO.getDamageFlag() != null && "Y".equals(
						newMailbagVO.getDamageFlag()) && newMailbagVO.getOperationalFlag() != 
							null && ("I".equals(newMailbagVO.getOperationalFlag()))) {
					
					if (newMailbagVO.getDamagedMailbags() == null || 
							newMailbagVO.getDamagedMailbags().size() == 0) {
						actionContext.addError(new ErrorVO(
								"mailtracking.defaults.err.reasonfordamagemandatory"));
				  		return;
					}
				}
				
				if(newMailbagVO.getUndoArrivalFlag()!=null &&
						MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
			    	if(newMailbagVO.getFlightSequenceNumber()!=newMailbagVO.getFromFlightSequenceNumber()||
			    			!(newMailbagVO.getFromFightNumber().equals(newMailbagVO.getFlightNumber())) ||
			    					MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(newMailbagVO.getMailStatus())
			    							||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(newMailbagVO.getMailStatus())) {
			    		newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
			    		newMailbagVO.setOperationalFlag(null);
			    		newMailbagVO.setUndoArrivalFlag(null);      
			    		if(!MailConstantsVO.MAIL_STATUS_ARRIVED.equals(newMailbagVO.getMailStatus())){
			    			actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailtransferredordelivered"));                         
			    			return;       
			    		} 
				
			    	}
				}
				
				if (newMailbagVO.getUndoArrivalFlag() != null
						&& MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
					if ("I".equals(newMailbagVO.getMraStatus())) {
						newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
						newMailbagVO.setOperationalFlag(null);
						newMailbagVO.setUndoArrivalFlag(null);
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
						return;

					}
				}
				
				if (newMailbagVO.getMailCompanyCode() != null && newMailbagVO.getMailCompanyCode().trim().length() > 0
						&& newMailbagVO.getOperationalFlag() != null
						&& ("I".equals(newMailbagVO.getOperationalFlag()))) {
					boolean cmpCodecaptured = false;
					String cmpCodeString = newMailbagVO.getAccepted();
					if (cmpCodeString != null && cmpCodeString.trim().length() > 0 && cmpCodeString.contains("-")) {
						String[] cmpCodes = cmpCodeString.split("-");
						String mailCmpCode = cmpCodes[1];
						if ("MALCMPCOD".equals(cmpCodes[0]) && mailCmpCode != null
								&& mailCmpCode.equals(newMailbagVO.getMailCompanyCode())) {
							cmpCodecaptured = true;

						}

						if ((MailConstantsVO.FLAG_NO.equals(newMailbagVO.getArrivedFlag())
								|| (newMailbagVO.getScannedDate() == null)) && (!cmpCodecaptured)
								&& !MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
							actionContext.addError(
									new ErrorVO("mailtracking.defaults.err.arrivalismandatoryforcompanycodecapture"));
							return;
						}
					}
				}
				if(newMailbagVO.getUndoArrivalFlag()!=null && 
						MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
			    	if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(newMailbagVO.getMailStatus()) &&
			    			MailConstantsVO.FLAG_NO.equals(newMailbagVO.getArrivedFlag())) {
			    		newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
			    		newMailbagVO.setOperationalFlag(null);
			    		newMailbagVO.setScannedDate(null); 
			    	}     
			    }
				  int errorFlag=1;
					if(newMailbagVO.getMailOrigin()!=null&&newMailbagVO.getMailOrigin().trim().length()>0){
						try {
							areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), newMailbagVO.getMailOrigin().toUpperCase());
						} catch (BusinessDelegateException businessDelegateException) {
							errorFlag = 1;
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && !errors.isEmpty() && errorFlag == 1) {
							Object[] obj = { newMailbagVO.getMailOrigin().toUpperCase(),newMailbagVO.getMailbagId() };
							actionContext.addError(new ErrorVO("mail.operation.invalidorigin", obj));
							return;
						}
					}
					if(newMailbagVO.getMailDestination()!=null&&newMailbagVO.getMailDestination().trim().length()>0){
						try {
							areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), newMailbagVO.getMailDestination().toUpperCase());
						} catch (BusinessDelegateException businessDelegateException) {
							errorFlag = 1;
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && !errors.isEmpty() && errorFlag == 1) {
							Object[] obj = { newMailbagVO.getMailDestination().toUpperCase(),newMailbagVO.getMailbagId() };
							actionContext.addError(new ErrorVO("mail.operation.invaliddestination", obj));
							return;
						}
					}
					newMailbagVO.setOrigin(newMailbagVO.getMailOrigin());
					newMailbagVO.setMailOrigin(newMailbagVO.getMailOrigin());
					newMailbagVO.setDestination(newMailbagVO.getMailDestination());
					newMailbagVO.setMailDestination(newMailbagVO.getMailDestination());
					if (newMailbagVO.getMailOrigin()!=null&&newMailbagVO.getMailDestination()!=null&&newMailbagVO.getMailOrigin().equals(newMailbagVO.getMailDestination())) {
						actionContext.addError(new ErrorVO("mail.operation.sameorigindest",
						new Object[] { newMailbagVO.getMailbagId() }));
						return;
					}
					if(isValidMailtag(newMailbagVO.getMailbagId(),true)
						&&newMailbagVO.getMailDestination()!=null&&newMailbagVO.getMailDestination().trim().length()>0&&newMailbagVO.getMailOrigin()!=null&&newMailbagVO.getMailOrigin().trim().length()>0){
						String ooe=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForCarditMissingDomMail(logonAttributes.getCompanyCode(),newMailbagVO.getMailOrigin());
						String doe=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForCarditMissingDomMail(logonAttributes.getCompanyCode(),newMailbagVO.getMailDestination());
						if(ooe!=null){
							newMailbagVO.setOoe(ooe);
						}
						if(doe!=null){
							newMailbagVO.setDoe(doe);  
						}
						if (ooe!=null&&doe!=null&&ooe.equals(doe)) {
				
							actionContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
							new Object[] { newMailbagVO.getMailbagId() }));
							return;
						}
					}
			  }
			  
			  if(errMails.length() > 0) {
				  actionContext.addError(
						  new ErrorVO("mailtracking.defaults.err.transferredmail",
			   				new Object[]{errMails.substring(0, errMails.length() - 2)}));
			  }
			}
			containerDetailsVO.setMailDetails(newMailbagVOs);
	
			/**
			 * Validate mailbags
			 */
			try {
				new MailTrackingDefaultsDelegate().validateMailBags(containerDetailsVO.getMailDetails());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
 	  	
 	  	  
 	  	  /**
 	  	   * To validate OOE and DOE
 	  	   */
 	  	  
		int sameOE = 0;
		Collection<MailbagVO> mailbagVOs1 = containerDetailsVO.getMailDetails();
		if (mailbagVOs1 != null && mailbagVOs1.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs1) {
				String ooe = mailbagVO.getOoe();
				String doe = mailbagVO.getDoe();
				if (ooe!=null&&ooe.trim().length() == 6) {
					if (doe!=null&&doe.trim().length() == 6) {  
						if (ooe.equals(doe)) {
							sameOE = 1;
							actionContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
									new Object[] { mailbagVO.getMailbagId() }));
						}
					}
				}
			}
		}
		if (sameOE == 1) {
			return;
		}
		
		/**
		 * To validate scan date
		 */
		
		int scandate = 0;
		Collection<MailbagVO> mailbagsVOs = 
				containerDetailsVO.getMailDetails();
		
		if(mailbagsVOs != null && mailbagsVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagsVOs){

				if (mailbagVO.getScannedDate() == null && MailbagVO.FLAG_YES.equals(
						mailbagVO.getArrivedFlag())) {
					scandate = 1;
					actionContext.addError(new ErrorVO(
							"mailtracking.defaults.mailacceptance.scandatemandatory"));
				}
	
				else if (mailbagVO.getScannedDate() != null && MailbagVO.FLAG_NO.equals(
						mailbagVO.getArrivedFlag())&& 
							! MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
					scandate = 1;
					actionContext.addError(new ErrorVO(
							"mailtracking.defaults.mailacceptance.receivedflagmandatory"));
				}

				else if("N".equals(mailbagVO.getArrivedFlag()) &&
						"Y".equals(mailbagVO.getDamageFlag())&&
								mailbagVO.getOperationalFlag()!=null){   
					scandate =1;
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.receivedflagmandatoryfordamage"));
					break;
				} else if((MailConstantsVO.FLAG_NO.equals(mailbagVO.getArrivedFlag())
						||(mailbagVO.getScannedDate() == null))
							&& MailConstantsVO.FLAG_NO.equals(mailbagVO.getDamageFlag())
								&& mailbagVO.getOperationalFlag()!=null && 
									!MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
					mailbagVO.setOperationalFlag(null);
				} else if (mailbagVO.getScannedDate() == null
						&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())
							&& mailbagVO.getOperationalFlag()!=null) {
					scandate = 1;
					actionContext.addError(new ErrorVO(
					"mailtracking.defaults.mailacceptance.scandatemandatory"));
				}
				
				


			}			
		}
		if(scandate==1){
	  		return;
		}
		
		/**
		 * For finding duplicate mail bags
		 */
		Collection<MailbagVO> firstMailbagVOs = containerDetailsVO.getMailDetails();
	  	Collection<MailbagVO> secMailbagVOs = mailbagVOs;
	  	int duplicate = 0;
	  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
	  		int count = 0;
	  			for(MailbagVO secMailbagVO:secMailbagVOs){
	  			for(MailbagVO fstMailbagVO:firstMailbagVOs){
	  				if(secMailbagVO.getMailbagId()!= null){
	  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
	  					count++;
	  				}
	  			}else
	  		  	{
	  		  		containerDetailsVO.setMailDetails(newMailbagVOs);
	  		  	}
	  		}		
	  			if(count == 1){
	  				duplicate = 1;
	  				break;
	  			}
	  			//count = 0;
	  		}
	  		
	  	}

	  	if(duplicate == 1){
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.msg.err.duplicatemailbag"));
	  		return;
	  	}
	  	
	  	
	  	/**
	  	 * For finding duplicate mailbag across containers
	  	 */
	  	
	  	int duplicateAcross = 0;
	  	String container = "";
	  	String mailId = "";
    	if(containerDetailsVos != null && containerDetailsVos.size() > 0){
    		if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
    			for(MailbagVO fstMailbagVO:firstMailbagVOs){
    				for(ContainerDetailsVO popupVO:containerDetailsVos){
    					if(fstMailbagVO.getContainerNumber()!=null&&popupVO.getContainerNumber()!=null
    							&&!fstMailbagVO.getContainerNumber().equals(popupVO.getContainerNumber())){ 
    						Collection<MailbagVO> secMailVOs = popupVO.getMailDetails();
    						if(secMailVOs != null && secMailVOs.size() > 0){
    							for(MailbagVO secMailbagVO:secMailVOs){
    								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())&&(
    										"Y".equals(secMailbagVO.getArrivedFlag()) && "Y".equals(fstMailbagVO.getArrivedFlag()))){
    									duplicateAcross = 1;
    									container = popupVO.getContainerNumber();
    									mailId = fstMailbagVO.getMailbagId();
    									fstMailbagVO.setScannedDate(null);
    									fstMailbagVO.setOperationalFlag(null);
    									fstMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
    									fstMailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
    									fstMailbagVO.setDeliveryNeeded(false);                    
    								}
    							}
    						}
    					}
    				}
    			}
			}
		} 
    	if(duplicateAcross == 1){
    		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.msg.err.duplicatemailbagacross",
    				new Object[]{container,mailId}));
	  		return;
	  	}
    	
    	
		/**
		 * Constructing DSNVOs
		 */
		
    	containerDetailsVO = makeDSNVOs(containerDetailsVO, logonAttributes);
		
		/**
		 * Validate DSNVOs
		 */
		try {
			new MailTrackingDefaultsDelegate().validateDSNs(containerDetailsVO.getDsnVOs());
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		
		Collection<ContainerDetailsVO> containerDetailsVOsCollection = new ArrayList<ContainerDetailsVO>();
		containerDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,true));
		containerDetailsVO.setUldLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,true));
		containerDetailsVOsCollection.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containerDetailsVOsCollection);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		mailArrivalVO.setMailSource("MTK064");
		FlightValidationVO flightValidationVO = findFlightValidationVO(operationalFlightVO,logonAttributes);
		if(flightValidationVO != null){ 
		  boolean isToBeActioned = flightValidationVO.isTBADueRouteChange()||FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus());
		  isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
		      if ((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))) {
				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),flightValidationVO.getFlightDate()};
				ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.err.flightintbcortba",obj);
				actionContext.addError(errorVO);
				return;
			}
		      if(!mailinboundModel.isScreenWarning()&&doSecurityScreeningValidations(mailArrivalVO,actionContext,logonAttributes)){
					return;
				}
		/**
		 * For constructing lock Vos
		 */
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);

		/**
		 * Save mail Arrival Details
		 */
		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		try {
			new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO, locks);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {
    			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		if (CollectionUtils.isNotEmpty(embargoErrors)) {
	    		responseVO.setStatus("success");
	    		actionContext.setResponseVO(responseVO);
	    		for(ErrorVO error: embargoErrors){
	    			actionContext.addError(error);
	    		}
			return;
		}
		

		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO("mail.operations.succ.mailbagaddsuccess");       
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
		actionContext.setResponseVO(responseVO);
		log.exiting("AddMailbagCommand", "execute");}
    	
	  	
	  	
			  
				
    	}
    	
    
		


		
		
		
	

	/**
	 * 
	 * 	Method		:	AddMailbagCommand.populateMailBagVos
	 *	Added by 	:	A-8164 on 19-Oct-2018
	 * 	Used for 	:	populate mailbagvos for save
	 *	Parameters	:	@param addMailbags
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param mailinboundDetails
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailbagVO>
	 */
	private Collection<MailbagVO> populateMailBagVos(
			ArrayList<AddMailbag> addMailbags, ContainerDetailsVO containerDetailsVO, MailinboundDetails mailinboundDetails) {
		
		Collection<MailbagVO> newMailbagVOs = 
				new ArrayList<MailbagVO>();
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		AreaDelegate areaDelegate = new AreaDelegate();    
        Map stationParameters = null; 
		String stationCode = logonAttributes.getStationCode();	
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STNPAR_DEFUNIT_VOL);
		try {
			stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
		} catch (BusinessDelegateException e1) {
			e1.getMessage();
		} 
		String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);  
		for(AddMailbag addMailbag:addMailbags){
			if(null!=addMailbag.getOperationFlag()){ 
				MailbagVO newMailbagVO = new MailbagVO();
				newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
		    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
		    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED); 
		    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);  
		    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
		    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
		    	newMailbagVO.setAcceptanceFlag("N");
		    	newMailbagVO.setArrivedFlag("Y"); 
		    	newMailbagVO.setFlightDate((new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false).setDate(mailinboundDetails.getFlightDate().split(" ")[0])));
		    	newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		    	if(containerDetailsVO.getPou()!=null && containerDetailsVO.getPou().trim().length()>0)
		    	{
		    	newMailbagVO.setPou(containerDetailsVO.getPou());
		    	}
		    	else{
		    		newMailbagVO.setPou(logonAttributes.getAirportCode());
		    		containerDetailsVO.setPou(logonAttributes.getAirportCode()); 
		    	}
		    	
		    	newMailbagVO.setOperationalFlag(addMailbag.getOperationFlag()); 
				newMailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
				newMailbagVO.setDisplayLabel("N");
				newMailbagVO.setMailCompanyCode(addMailbag.getMailCompanyCode());
	
				if(addMailbag.getOoe()!= null &&
						!("".equals(addMailbag.getOoe()))) {
					newMailbagVO.setOoe(addMailbag.getOoe().toUpperCase());
				}
	
				if(addMailbag.getMailSequenceNumber()!= 0) {
					newMailbagVO.setMailSequenceNumber(addMailbag.getMailSequenceNumber());
				}
				if(addMailbag.getDoe() != null &&
						!("".equals(addMailbag.getDoe() ))) {
					newMailbagVO.setDoe(addMailbag.getDoe() .toUpperCase());
				}
		
		
				if(addMailbag.getMailCategoryCode() != null &&
						!("".equals(addMailbag.getMailCategoryCode()))) {
					newMailbagVO.setMailCategoryCode(addMailbag.getMailCategoryCode());
				}
	
	
				if(addMailbag.getMailSubclass() != null &&
						!("".equals(addMailbag.getMailSubclass()))) {
					newMailbagVO.setMailSubclass(addMailbag.getMailSubclass().toUpperCase());
					newMailbagVO.setMailClass(newMailbagVO.getMailSubclass().substring(0,1));
				}
	
	
				if(addMailbag.getYear() != null &&
						!("".equals(addMailbag.getYear()))) {
					newMailbagVO.setYear(Integer.parseInt(addMailbag.getYear()));
				}
	
	
				if(addMailbag.getDespatchSerialNumber()!= null &&
						!("".equals(addMailbag.getDespatchSerialNumber()))) {
					newMailbagVO.setDespatchSerialNumber(
							addMailbag.getDespatchSerialNumber().toUpperCase());
				}
	
		
				if(addMailbag.getReceptacleSerialNumber() != null &&
						!("".equals(addMailbag.getReceptacleSerialNumber()))) {
					newMailbagVO.setReceptacleSerialNumber(
							addMailbag.getReceptacleSerialNumber().toUpperCase());
				}
	
			
				if(addMailbag.getHighestNumberedReceptacle() != null &&
						!("".equals(addMailbag.getHighestNumberedReceptacle() ))) {
					newMailbagVO.setHighestNumberedReceptacle(
							addMailbag.getHighestNumberedReceptacle() );
				}
	
	
				if(addMailbag.getRegisteredOrInsuredIndicator() != null &&
						!("".equals(addMailbag.getRegisteredOrInsuredIndicator()))) {
					newMailbagVO.setRegisteredOrInsuredIndicator(
							addMailbag.getRegisteredOrInsuredIndicator());
				}
	
			
				if(addMailbag.getMailbagId() != null &&
						!("".equals(addMailbag.getMailbagId()))) {
					newMailbagVO.setMailbagId(addMailbag.getMailbagId());
				}
	
			
				if(addMailbag.getWeight() != null && 
						!("".equals(addMailbag.getWeight()))) {
					Measure strWt=new Measure(UnitConstants.MAIL_WGT,
							0.0,Double.parseDouble(addMailbag.getWeight()),addMailbag.getWeightUnit());
					newMailbagVO.setStrWeight(strWt);
					newMailbagVO.setWeight(strWt);
				
				}
	
		
				if(addMailbag.getVolume() != null && 
						!("".equals(addMailbag.getVolume()))) {
					newMailbagVO.setVolume(new Measure(UnitConstants.VOLUME,0.0,
							Double.parseDouble(addMailbag.getVolume()),stationVolumeUnit)); 		
					}
		
	
				if((addMailbag.getScannedDate() != null &&
						!("".equals(addMailbag.getScannedDate())))){
					
						String scanDT=null;
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true); 
						if(addMailbag.getScannedTime().length()==8){
							scanDT = new StringBuilder(addMailbag.getScannedDate()).append(" ") 
									.append(addMailbag.getScannedTime()).toString();
						}
						else{
							scanDT = new StringBuilder(addMailbag.getScannedDate()).append(" ") 
								.append(addMailbag.getScannedTime()).append(":00").toString();
						}
						newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));  
					
				}
				
	
				if(addMailbag.getDmgd() != null && !("".equals(addMailbag.getDmgd()))) {
					newMailbagVO.setDamageFlag(addMailbag.getDmgd());
				}
				if(addMailbag.getMailOrigin() != null && addMailbag.getMailOrigin().trim().length()>0) {
					newMailbagVO.setMailOrigin(addMailbag.getMailOrigin());
				}
				if(addMailbag.getMailDestination() != null && addMailbag.getMailDestination().trim().length()>0) {
					newMailbagVO.setMailDestination(addMailbag.getMailDestination());
				}
				else{
					newMailbagVO.setDamageFlag("N");
				}
	
	
	
				if(addMailbag.getRcvd() != null && !("".equals(addMailbag.getRcvd()))) {
					newMailbagVO.setArrivedFlag(addMailbag.getRcvd());
				}
				else{
					newMailbagVO.setArrivedFlag("Y");
				}
	
	
	
				if(addMailbag.getDlvd() != null && !("".equals(addMailbag.getDlvd()))) {
					newMailbagVO.setDeliveredFlag(addMailbag.getDlvd());
				}
				else{
					newMailbagVO.setDeliveredFlag("N");
				}
				
	
				if(addMailbag.getCompanyCode() != null ) {
	
					newMailbagVO.setMailCompanyCode(addMailbag.getCompanyCode());
				}
	
	
	
				if(addMailbag.getSealNumber() != null && !("".equals(addMailbag.getSealNumber() ))) {
					newMailbagVO.setSealNumber(addMailbag.getSealNumber() );
				}
	
	
				if(addMailbag.getArrivalSealNo() != null && !("".equals(addMailbag.getArrivalSealNo()))) {
					newMailbagVO.setArrivalSealNumber(addMailbag.getArrivalSealNo());
				}
	
			
	
				if(addMailbag.getRemarks() != null && !("".equals(addMailbag.getRemarks()))) {
					newMailbagVO.setMailRemarks(addMailbag.getRemarks());
				}
				
				newMailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
						Location.NONE,true));
				
				
				newMailbagVOs.add(newMailbagVO);
		
			}
		}

		
		
		return newMailbagVOs;
	}
	
	
	/**
	 * 
	 * 	Method		:	AddMailbagCommand.makeDSNVOs
	 *	Added by 	:	A-8164 on 20-Oct-2018
	 * 	Used for 	:	Making DSNVOs
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	ContainerDetailsVO
	 */
	 public ContainerDetailsVO  makeDSNVOs(ContainerDetailsVO containerDetailsVO,
	    		LogonAttributes logonAttributes){
		 
		 if(containerDetailsVO != null ){

					HashMap<String,DSNVO> dsnMapDespatch = new HashMap<String,DSNVO>();
					HashMap<String,String> despatchMap = new HashMap<String,String>();
					HashMap<String,String> mailMap = new HashMap<String,String>();
					HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
					Collection<DSNVO> mainDSNVOs = containerDetailsVO.getDsnVOs();
					if(mainDSNVOs != null && mainDSNVOs.size() > 0){
						for(DSNVO dsnVO:mainDSNVOs){
							if("Y".equals(dsnVO.getPltEnableFlag())){
								if(!"I".equals(dsnVO.getOperationFlag())){
								String dsnpk = dsnVO.getOriginExchangeOffice()
						           +dsnVO.getDestinationExchangeOffice()
	
						           +dsnVO.getMailCategoryCode()
						           +dsnVO.getMailSubclass()
						           +dsnVO.getDsn()
						           +dsnVO.getYear();
								dsnMapMailbag.put(dsnpk,dsnVO);
								}
							}
					    }
					}
	
				int numBags = 0;
				double bagWgt = 0;
				int dlvBags = 0;
				double dlvWgt = 0;
				Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagVOs) {

						String outerpk = mailbagVO.getOoe() + mailbagVO.getDoe()

								+ mailbagVO.getMailCategoryCode() + mailbagVO.getMailSubclass()
								+ mailbagVO.getDespatchSerialNumber() + mailbagVO.getYear();
						if(mailMap.get(outerpk) == null){
						if(dsnMapMailbag.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							if(mailbagVO.getMailSubclass()!=null && !("".equals(mailbagVO.getMailSubclass()))){
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							}
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag("Y");
							dsnVO.setOperationFlag("I");
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
	
							+ innerVO.getMailCategoryCode()
					           +(innerVO.getMailSubclass())
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								if("Y".equals(innerVO.getArrivedFlag())){
									dsnVO.setReceivedBags(numBags + 1);   
	
									try {
										Measure bagWeight=new Measure(UnitConstants.MAIL_WGT ,bagWgt);
										if(bagWeight!=null&&innerVO.getWeight()!=null&&
												!bagWeight.getDisplayUnit().equals(innerVO.getWeight().getDisplayUnit())){
											bagWeight=new Measure(UnitConstants.MAIL_WGT,bagWgt,0.0,innerVO.getWeight().getDisplayUnit());
										}
										dsnVO.setReceivedWeight(Measure.addMeasureValues(bagWeight,
												innerVO.getWeight()));
									} catch (UnitException e) {
	
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}		
	
									bagWgt = dsnVO.getReceivedWeight().getRoundedSystemValue(); 
	
								}
								if("Y".equals(innerVO.getDeliveredFlag())){
									dsnVO.setDeliveredBags(dlvBags + 1);
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(
												new Measure(UnitConstants.MAIL_WGT,dlvWgt),  innerVO.getWeight()));
									} catch (UnitException e) {
		
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}
									dlvBags = dsnVO.getDeliveredBags();
	
									dlvWgt = dsnVO.getDeliveredWeight().getRoundedSystemValue();
	
								}
							}
						}
						dsnMapMailbag.put(outerpk,dsnVO);
						}else{
							DSNVO dsnVO = dsnMapMailbag.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
	
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(mailbagVOs.size() > 0){
								for(MailbagVO mbagVO:mailbagVOs){
								String mailpk = mbagVO.getOoe()+mbagVO.getDoe()
	
								   + mbagVO.getMailCategoryCode()
						           + mbagVO.getMailSubclass()
						           + mbagVO.getDespatchSerialNumber()+mbagVO.getYear();
									    if(dsnpk.equals(mailpk)){
									    	if("I".equals(mbagVO.getOperationalFlag())
									    			|| "U".equals(mbagVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag("U");
									    	}
											if("Y".equals(mbagVO.getArrivedFlag()) ){
												numBags = numBags + 1;
												if(mbagVO.getWeight()!=null){
	
												bagWgt = bagWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
				
											if("Y".equals(mbagVO.getDamageFlag())){									
												dsnVO.setOperationFlag("U");
										    }
				
											if("Y".equals(mbagVO.getDeliveredFlag())){
												dlvBags = dlvBags + 1;
												if(mbagVO.getWeight()!=null){
												dlvWgt = dlvWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
										}
								}
	
								if(dsnVO.getReceivedBags()!= numBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= bagWgt){
										dsnVO.setOperationFlag("U");
								}
								dsnVO.setReceivedBags(numBags);
			
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, bagWgt));
	
								if(dsnVO.getDeliveredBags()!= dlvBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= dlvWgt){
									
											dsnVO.setOperationFlag("U");
									}
								dsnVO.setDeliveredBags(dlvBags);
	
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, dlvWgt));
								dsnMapMailbag.put(outerpk,dsnVO);
							}
						  }
						mailMap.put(outerpk,outerpk);
						}
						numBags = 0;
						bagWgt = 0;
						dlvBags = 0;
						dlvWgt = 0;
					}
				}
	
				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMapDespatch.keySet()){
					DSNVO dsnVO = dsnMapDespatch.get(key);
					newDSNVOs.add(dsnVO);
				}
				for(String key:dsnMapMailbag.keySet()){
					DSNVO dsnVO = dsnMapMailbag.get(key);
					if(newDSNVOs.size() == 0){
					   newDSNVOs = new ArrayList<DSNVO>();
					}
					newDSNVOs.add(dsnVO);
				}
		
				Collection<DSNVO> oldDSNVOs = containerDetailsVO.getDsnVOs();
				int accBags = 0;
				double accWgt = 0;
				int rcvedBags = 0;
				double rcvedWgt = 0;
				if(newDSNVOs.size() > 0){
					for(DSNVO dsnVO1:newDSNVOs){
						String outerpk = dsnVO1.getOriginExchangeOffice()
						   +dsnVO1.getDestinationExchangeOffice()
						   +dsnVO1.getMailCategoryCode()
						   +dsnVO1.getMailSubclass()
				           +dsnVO1.getDsn()+dsnVO1.getYear();
						int flag = 0;
						if(oldDSNVOs != null && oldDSNVOs.size() > 0){
							for(DSNVO dsnVO2:oldDSNVOs){
								String innerpk = dsnVO2.getOriginExchangeOffice()
								   +dsnVO2.getDestinationExchangeOffice()
								   +dsnVO2.getMailCategoryCode()
								   +dsnVO2.getMailSubclass()
						           +dsnVO2.getDsn()+dsnVO2.getYear();
								if(outerpk.equals(innerpk)){
									if(!"I".equals(dsnVO2.getOperationFlag())){
										dsnVO1.setPrevBagCount(dsnVO2.getPrevBagCount());
										dsnVO1.setPrevBagWeight(dsnVO2.getPrevBagWeight());
		                                dsnVO1.setPrevReceivedBags(dsnVO2.getPrevReceivedBags());
		                                dsnVO1.setPrevReceivedWeight(dsnVO2.getPrevReceivedWeight());
		                                dsnVO1.setPrevDeliveredBags(dsnVO2.getPrevDeliveredBags());
		                                dsnVO1.setPrevDeliveredWeight(dsnVO2.getPrevDeliveredWeight());
									}
									flag = 1;
								}
							}
						}
						if(flag == 1){
							flag = 0;
						}
						accBags = accBags + dsnVO1.getBags();
						if(dsnVO1.getWeight()!=null){
						accWgt = accWgt + dsnVO1.getWeight().getRoundedSystemValue();
						}
						rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
						if(null!=dsnVO1.getReceivedWeight())
							rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight().getRoundedSystemValue(); 
					}
				}
				containerDetailsVO.setTotalBags(accBags);
				containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, accWgt)); 
				containerDetailsVO.setReceivedBags(rcvedBags);
				containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvedWgt)); 
				containerDetailsVO.setDsnVOs(newDSNVOs);
			
		 }

	return containerDetailsVO;
	 }
	 
	 /**
	  * 
	  * 	Method		:	AddMailbagCommand.prepareLocksForSave
	  *		Added by 	:	A-8164 on 20-Oct-2018
	  * 	Used for 	:	For constructing locks
	  *		Parameters	:	@param mailArrivalVO
	  *		Parameters	:	@return 
	  *		Return type	: 	Collection<LockVO>
	  */
	private Collection<LockVO> prepareLocksForSave(
			MailArrivalVO mailArrivalVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs =
				mailArrivalVO.getContainerDetails();

		if (containerDetailsVOs != null &&
				containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO conVO : containerDetailsVOs) {
				if (conVO.getOperationFlag() != null && 
						conVO.getOperationFlag().trim().length() > 0) {
					ArrayList<MailbagVO> mailbagvos = 
							new ArrayList<MailbagVO>(conVO.getMailDetails());
					
					if (mailbagvos != null && mailbagvos.size() > 0) {
						for (MailbagVO bagvo : mailbagvos) {
							if (bagvo.getOperationalFlag() != null &&
									bagvo.getOperationalFlag().trim().length() > 0) {
								ULDLockVO lock = new ULDLockVO();
								lock.setAction(LockConstants.ACTION_MAILARRIVAL);
								lock.setClientType(ClientType.WEB);
								lock.setCompanyCode(logonAttributes.getCompanyCode());
								lock.setScreenId(SCREEN_ID);
								lock.setStationCode(logonAttributes.getStationCode());
								if (bagvo.getContainerForInventory() != null) {
									lock.setUldNumber(bagvo.getContainerForInventory());
									lock.setDescription(bagvo.getContainerForInventory());
									lock.setRemarks(bagvo.getContainerForInventory());
									log.log(Log.FINE, "\n lock------->>", lock);
									locks.add(lock);
								}
							}

						}
					}

				}
			}

		}
		return locks;
	}
	private boolean isValidMailtag(String mailbagId,boolean checkDomMail) throws BusinessDelegateException
	{
		boolean valid=false;
		int mailtagLength = mailbagId!=null?mailbagId.trim().length():0;
		String systemParameterValue=null; 
		ArrayList<String> systemParameters = new ArrayList<String>();
	    systemParameters.add(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate() 
			     	.findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			systemParameterValue = systemParameterMap.get(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		}
		if(mailtagLength==29 && mailbagId.matches(SPECIAL_CHARACTER_REGEX) &&!checkDomMail){
			return true;
		}
		if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
		{
		 String[] systemParameterVal = systemParameterValue.split(","); 
	        for (String a : systemParameterVal) 
	        {
	        	if(Integer.valueOf(a)==mailtagLength)
	        	{
	        		valid=true;
	        		break;
	        	}
	        }
		}
		return valid;
	}
private boolean validateMailTagFormat(String mailbagId){
	boolean isValid=false;
	String mailYr=mailbagId.substring(15,16);
	String mailDSN=mailbagId.substring(16,20);
	String mailRSN=mailbagId.substring(20,23);
	String mailHNI=mailbagId.substring(23,24);
	String mailRI=mailbagId.substring(24,25);
	String mailWt=mailbagId.substring(25,29);
	  if(   mailYr.matches(INT_REGEX) && mailYr.length()==1&&
			mailDSN.matches(INT_REGEX) && mailDSN.length()==4&&
			mailRSN.matches(INT_REGEX) && mailRSN.length()==3&&
			mailHNI.matches(INT_REGEX) && mailHNI.length()==1&&
			mailRI.matches(INT_REGEX) && mailRI.length()==1&&
			mailWt.matches(INT_REGEX) && mailWt.length()==4){
		isValid=true;
	}
	return isValid;
	}
private FlightFilterVO handleFlightFilterVO(
		 OperationalFlightVO operationalFlightVO,
		LogonAttributes logonAttributes) {
	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	flightFilterVO.setStation(operationalFlightVO.getPou());
	flightFilterVO.setDirection(INBOUND);
	flightFilterVO.setActiveAlone(false);
	if (operationalFlightVO.getFlightDate() != null) {
		flightFilterVO.setStringFlightDate(operationalFlightVO.getFlightDate().toString().substring(0, 11));
		if (operationalFlightVO.getFlightDate() != null ) {
			flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
		}
	}
	return flightFilterVO;
}
private FlightValidationVO findFlightValidationVO(OperationalFlightVO operationalFlightVO, LogonAttributes logonAttributes) {
	FlightFilterVO flightFilterVO = null;
	FlightValidationVO flightValidationVO = new FlightValidationVO();
	if (operationalFlightVO.getFlightNumber() != null && operationalFlightVO.getFlightNumber().trim().length() > 0) {
		Collection<FlightValidationVO> flightValidationVOs =  new ArrayList<>();
		try {
			flightFilterVO = handleFlightFilterVO(operationalFlightVO, logonAttributes);
			flightFilterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
			flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		if (flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					BeanHelper.copyProperties(flightValidationVO, flightValidVO);
				}
			} catch (SystemException systemException) {
				log.log(Log.FINE,systemException );
				systemException.getMessage();
			}
		}
		return flightValidationVO;
	}
	return flightValidationVO;
}
private boolean canIgnoreToBeActionedCheck() {
	Collection<String> parameterCodes = new ArrayList<>();
	parameterCodes.add(FLIGHT_VALIDATION);
	Map<String, String> systemParameters = null;
	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	try {
		systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
	} catch (BusinessDelegateException e) {
		log.log(Log.INFO, e);
	}
	if(systemParameters!=null && systemParameters.containsKey(FLIGHT_VALIDATION)) {
		return "Y".equals(systemParameters.get(FLIGHT_VALIDATION));
	}
	return false;
	}
/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailAcceptanceVO
	 * @param flightCarrierFlag
	 * @param actionContext 
	 * @param logonAttributes 
	 * @param errors 
	 * @param outboundModel 
	 * @param warningMap 
	 * @throws BusinessDelegateException 
	 */
	private boolean doSecurityScreeningValidations(MailArrivalVO mailArrivalVO, 
			ActionContext actionContext, LogonAttributes logonAttributes) throws BusinessDelegateException {
			for(ContainerDetailsVO containerDetailsVO:mailArrivalVO.getContainerDetails()){
				if(containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()){
					for(MailbagVO mailbagVo:containerDetailsVO.getMailDetails()){
						SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
						Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
						securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
						securityScreeningValidationFilterVO.setApplicableTransaction("ARR");
						securityScreeningValidationFilterVO.setFlightType(mailArrivalVO.getFlightType());
						securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
						securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
						securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
						securityScreeningValidationFilterVO.setMailbagId(mailbagVo.getMailbagId());
						securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
						if (securityScreeningValidationVOs!=null &&! securityScreeningValidationVOs.isEmpty()){
							for( SecurityScreeningValidationVO securityScreeningValidationVO:securityScreeningValidationVOs){
								if( checkForWarningOrError(mailbagVo, actionContext, securityScreeningValidationVO)){
									return true;
								}
							}
						}
					}
				}
		}
		return false;
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param actionContext
	 * @param existigWarningMap
	 * @param securityScreeningValidationVO
	 * @return
	 */
	private boolean checkForWarningOrError(MailbagVO mailbagVO, ActionContext actionContext, SecurityScreeningValidationVO securityScreeningValidationVO) {
		if ("W".equals(securityScreeningValidationVO
				.getErrorType())) {
			List<ErrorVO> warningErrors = new ArrayList<>();
			ErrorVO warningError = new ErrorVO(
					SECURITY_SCREENING_WARNING,
					new Object[]{mailbagVO.getMailbagId()});
			warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			warningErrors.add(warningError);
			actionContext.addAllError(warningErrors); 
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus("security");
			actionContext.setResponseVO(responseVO);
			return true;
		}
		if ("E".equals(securityScreeningValidationVO
				.getErrorType())) {
			actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
					new Object[]{mailbagVO.getMailbagId()}));
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus("security");
			actionContext.setResponseVO(responseVO);
			return true;
		}
		return false;
	}


}