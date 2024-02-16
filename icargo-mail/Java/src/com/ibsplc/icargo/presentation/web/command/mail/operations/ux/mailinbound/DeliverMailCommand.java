package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DeliverMailDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.DeliverMailCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	01-Nov-2018		:	Draft
 */
public class DeliverMailCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String CONTAINER_LEVEL="container";
	private static final String MAILBAG_LEVEL="mailbag";
	private static final String DSN_LEVEL="dsn";
	private static final String FLIGHT ="FLT";
	private static final String CONST_OP="U";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL="mail.operation.destinationforcarditmissingdomesticmailbag";
	 private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	private Log log = LogFactory.getLogger("MAIL OPERATIONS DeliverMailCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		DeliverMailDetails deliverMailDetails=
				(DeliverMailDetails) mailinboundModel.getDeliverMailDetails();
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		
		
		String operationLevel= mailinboundModel.getOperationLevel();
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
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
		
		mailArrivalVO.setFoundResditSent(true);
			
			String scanDate = new StringBuilder(deliverMailDetails.getDate()).append(" ")
					.append(deliverMailDetails.getTime()).append(":00").toString();
			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);

			mailArrivalVO.setScanDate(sd.setDateAndTime(scanDate, false));
			if(deliverMailDetails.getRemarks()!=null && deliverMailDetails.getRemarks().trim().length()>0) {
				mailArrivalVO.setDeliveryRemarks(deliverMailDetails.getRemarks());
			}
			for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
				containerDetailsVoMap.put(
						containerDetailsVO.getContainerNumber(), containerDetailsVO);
			}
			
			for(ContainerDetails containerDetail:containerDetailsCollection){
				
				if(containerDetailsVoMap.containsKey(containerDetail.getContainerno())){
					mailDetailsMap.clear();
					ContainerDetailsVO containerDetailsVO=
							containerDetailsVoMap.get(containerDetail.getContainerno());
					for(MailbagVO mailbagVO:containerDetailsVoMap.get(containerDetail.getContainerno()).getMailDetails()){
						String mailKey=mailbagVO.getMailbagId();
						mailDetailsMap.put(mailKey, mailbagVO);	
					}
					Collection<MailbagVO> selectedMailBagVos=
							 new ArrayList<MailbagVO>();
					selectedMailBagVos.clear();
					if(null!=containerDetail.getDsnDetailsCollection()&&containerDetail.getDsnDetailsCollection().size()>0){
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						Collection<DSNVO> selectedDsnVos=
								 new ArrayList<DSNVO>();
						selectedDsnVos.clear();
						for(DSNDetails dsnDetails:containerDetail.getDsnDetailsCollection()){
							String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
									.append(dsnDetails.getDestinationExchangeOffice())
										.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
											.append(dsnDetails.getYear()).toString();
							
							for(MailbagVO mailbagVO:containerDetailsVoMap.get(containerDetail.getContainerno()).getMailDetails()){
								String mailKey=new StringBuilder(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getOoe())
										.append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass())
										.append(mailbagVO.getYear()).toString();
								if(mailKey.equals(dsnKey)&&!(FLAG_YES.equals(mailbagVO.getDeliveredFlag())||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus()))){
									selectedMailBagVos.add(mailbagVO);
								}
							}  
							if(dsnDetailsMap.containsKey(dsnKey)){
								selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
							}
						}
						containerDetailsVoMap.get(containerDetail.getContainerno()).setDsnVOs(selectedDsnVos);
					}
					else if(null!=containerDetail.getMailBagDetailsCollection()&&containerDetail.getMailBagDetailsCollection().size()>0){
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						Collection<DSNVO> selectedDsnVos=
								 new ArrayList<DSNVO>();
						selectedDsnVos.clear();
						for(MailBagDetails mailBagDetails:containerDetail.getMailBagDetailsCollection()){
							String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
									.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
									.append(mailBagDetails.getYear()).toString();   
							
							if(mailDetailsMap.containsKey(mailBagDetails.getMailBagId())
								&&!(FLAG_YES.equals(mailDetailsMap.get(mailBagDetails.getMailBagId()).getDeliveredFlag())
								||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailDetailsMap.get(mailBagDetails.getMailBagId()).getMailStatus()))){  
								if(isValidMailtag(mailBagDetails.getMailBagId())){
								MailbagVO mailbagVoAfterUpd=null;
								mailbagVoAfterUpd=updateDestinationForCarditMissingDomMail(mailDetailsMap.get(mailBagDetails.getMailBagId()),logonAttributes);
								selectedMailBagVos.add(mailbagVoAfterUpd); 
								}
								else{
								selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailBagId())); 
							} 
							} 
							if(dsnDetailsMap.containsKey(mailBagKey)){
								selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));
							}
						}
						containerDetailsVoMap.get(containerDetail.getContainerno()).setDsnVOs(selectedDsnVos);
					}
					else{
						Collection<MailbagVO>  filteredSelectedMailBagVos= containerDetailsVoMap.get(containerDetail.getContainerno()).getMailDetails().stream().
								filter(mailbagVO->!(FLAG_YES.equals(mailbagVO.getDeliveredFlag())
								||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus()))).collect(Collectors.toList());
						if(filteredSelectedMailBagVos!=null&&!filteredSelectedMailBagVos.isEmpty()){
							Collection<MailbagVO>  updatedSelectedMailBagVos= new ArrayList<>();
							for(MailbagVO mailbag:filteredSelectedMailBagVos){
								if(isValidMailtag(mailbag.getMailbagId())){
									MailbagVO mailbagVoAfterUpd=null;
									mailbagVoAfterUpd=updateDestinationForCarditMissingDomMail(mailbag,logonAttributes);
									updatedSelectedMailBagVos.add(mailbagVoAfterUpd); 
									}
								else{
									updatedSelectedMailBagVos.add(mailbag);
								}
							}	
						selectedMailBagVos.addAll(updatedSelectedMailBagVos);
						}
					}
					containerDetailsVoMap.get(containerDetail.getContainerno()).setMailDetails(selectedMailBagVos);
					containerDetailsVosSelected.add(containerDetailsVoMap.get(containerDetail.getContainerno()));
				}
			}
			
			
			
			if(containerDetailsVosSelected != null && containerDetailsVosSelected.size() > 0){
	    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVosSelected){
	    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
	    			
	    			for(MailbagVO transactionLevelVO : containerDtlsVO.getMailDetails()) {
	    				if((!transactionLevelVO.getContainerType().isEmpty()) && (!MailConstantsVO.BULK_TYPE.equals(transactionLevelVO.getContainerType())) && 
	    						  mailinboundModel.getTransactionLevel()!=null && (!mailinboundModel.getTransactionLevel().isEmpty())) {
	    					
	    					transactionLevelVO.setTransactionLevel(mailinboundModel.getTransactionLevel());
	    				}
	    			}
	    		}
	    	}
			
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVosSelected) {
				
				ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>) containerDetailsVO.getDsnVOs();
				if (dsnVos != null && dsnVos.size() > 0) {
					for (DSNVO dsnVO : dsnVos) {
						if (FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
							
							Collection<MailbagVO> mailbagVOs = 
									containerDetailsVO.getMailDetails();
							for (MailbagVO mail : mailbagVOs) {
								
								String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId())
														.append(mail.getFlightSequenceNumber())
																.append(mail.getFlightNumber()).toString();
								String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId())
														.append(mail.getFromFlightSequenceNumber())
																.append(mail.getFromFightNumber()).toString();
								
								if (dsnVO.getDsn().equals(mail.getDespatchSerialNumber())) {
									
									if ((FLAG_YES.equals(mail.getDeliveredFlag()))
											|| (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()))) {
										/*actionContext.addError(new ErrorVO(
												"mailtracking.defaults.mailarrival.mailbagalreadydelivered"));

										return;*/
										
									} else if (!Flight1.equals(Flight2)) {
										
										if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus()) 
												&& logonAttributes.getAirportCode().equals(mail.getScannedPort()))||
													MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
														MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())||
															MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())){
											
										
											if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())){
												actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
											}
											else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus()) &&
													logonAttributes.getAirportCode().equals(mail.getScannedPort())) {
												actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadyarrivedinanotherflight"));
											}else{
												actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
											}
											
											return;									
											
										}
									}
								}
							}
						}
					}
				}
				
				containerDetailsVO.setOperationFlag(CONST_OP);					
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if(mailbags !=null){
	      			for(MailbagVO mailbagVO :mailbags ){
	  					mailbagVO.setOperationalFlag(CONST_OP);
	  					//mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
	  					mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
	  					mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
	  							Location.NONE, true));
	  					mailbagVO.setLatestScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
	  					//mailbagVO.setDeliveredFlag("Y"); 
	       			}  
				}
				containerDetailsVO.setMailDetails(mailbags);
				Collection<DespatchDetailsVO> despatches = containerDetailsVO.getDesptachDetailsVOs();
				if(despatches !=null){
	      			for(DespatchDetailsVO despatchVO :despatches ){
	      				despatchVO.setOperationalFlag(CONST_OP);
	       			}  
				}
				
			}
	  //below code will check whether user has privilege to deliver selected mailbags 
	  //start here
		ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add("mail.operations.deliveryrestictedpacodes");
		Map<String, String> systemParameterMap=null;
		String deliveryRestictedPACodes = "";
		List<String> restictedPACodes = new ArrayList<>();
			try {
				systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.INFO,"businessDelegateException");
			}
		if(systemParameterMap!=null && systemParameterMap.containsKey("mail.operations.deliveryrestictedpacodes") &&
				!"N".equals(systemParameterMap.get("mail.operations.deliveryrestictedpacodes")));
		{
			deliveryRestictedPACodes = systemParameterMap.get("mail.operations.deliveryrestictedpacodes");
		}
		if(deliveryRestictedPACodes!=null && deliveryRestictedPACodes.trim().length()>0) {
		restictedPACodes = Arrays.asList(deliveryRestictedPACodes.split(",")) ;
		}
		Map<String,Boolean> paCodeDeliveryPrvlg = new HashMap<>();
		StringBuilder privilegeCode = null;
		//dynamically construct privilege code
		for(String paCode:restictedPACodes) {
			privilegeCode = new StringBuilder("mail.operations.mailbagdeliveryprivilege.").append(paCode);
			paCodeDeliveryPrvlg.put(paCode, hasPrivilege(privilegeCode.toString()));
		}
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVosSelected) {
			Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
			if(mailbags !=null){
      			for(MailbagVO mailbagVO :mailbags ){
      				if(mailbagVO.getPaCode()!=null && paCodeDeliveryPrvlg.containsKey(mailbagVO.getPaCode())) {
      					if(!paCodeDeliveryPrvlg.get(mailbagVO.getPaCode())) {
      						actionContext.addError(new ErrorVO("User does not have deliver mail privileges"));
      						return;
      					}
      				}
      			}
			}
			
			
		}
		//end here
		mailArrivalVO.setContainerDetails(containerDetailsVosSelected);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setDeliveryNeeded(true);
		if(!mailinboundModel.isScreenWarning()&&doSecurityScreeningValidations(mailArrivalVO,actionContext,logonAttributes)){
			return;  
		}
		log.log(Log.FINE, "Going To Save ...in command...DeliverMail...",
				mailArrivalVO);
		try {
		    new MailTrackingDefaultsDelegate().deliverMailbags(mailArrivalVO);
        }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	}
    	if (errors != null && errors.size() > 0) {
    		Collection<ContainerDetailsVO> containerDtlsVOsCopy=mailArrivalVO.getContainerDetails();          
    		for(ContainerDetailsVO containerDetailsVOForIterate:containerDtlsVOsCopy){
    			containerDetailsVOForIterate.setOperationFlag("");
    			Collection<MailbagVO> mails=containerDetailsVOForIterate.getMailDetails();                 
    			for(MailbagVO mail:mails){ 
    				mail.setOperationalFlag("");  
    			}
    		}
    		
    		actionContext.addAllError((List<ErrorVO>) errors);	
    		return;
	
		}
    	
    	ResponseVO responseVO = new ResponseVO();	
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
    	mailinboundModel.setMailArrivalVO(mailArrivalVO);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO("mail.operations.succ.deliversuccess");       
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
        actionContext.setResponseVO(responseVO);
		
	}

	public boolean hasPrivilege(String privilegeCode){
        boolean hasPrivilege = false;
        SecurityAgent agent = null;
        try {
        	agent = SecurityAgent.getInstance();
			hasPrivilege = agent.checkPrivilegeForAction(privilegeCode);
		} catch (SystemException e) {
			hasPrivilege = false;
		}
        return hasPrivilege;
    }
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @return
	 * @throws BusinessDelegateException 
	 */
	private MailbagVO updateDestinationForCarditMissingDomMail(MailbagVO mailbagVO,LogonAttributes logonAttributes) throws BusinessDelegateException {
		String destForCdtMissingDomMail=null;
		destForCdtMissingDomMail=findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		if(destForCdtMissingDomMail!=null &&!"NA".equals(destForCdtMissingDomMail) && 
		  mailbagVO.getMailDestination()!=null&&destForCdtMissingDomMail.equals(mailbagVO.getMailDestination())){
			mailbagVO.setDestination(logonAttributes.getAirportCode());
			mailbagVO.setMailDestination(logonAttributes.getAirportCode());
			mailbagVO.setNeedDestUpdOnDlv(true);     
			String doe=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForCarditMissingDomMail(logonAttributes.getCompanyCode(),logonAttributes.getAirportCode());
			if(doe!=null){
				mailbagVO.setDoe(doe);
			}
		}
		return mailbagVO;
	}
	/**
	 * @author A-8353
	 * @param syspar
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String findSystemParameterValue(String syspar) throws BusinessDelegateException
	{
	String sysparValue = null;
	ArrayList<String> systemParameters = new ArrayList<String>();
	systemParameters.add(syspar);
	Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
		.findSystemParameterByCodes(systemParameters);
	log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
	if (systemParameterMap != null) {
	sysparValue = systemParameterMap.get(syspar);
	}
	return sysparValue;
	}
	/**
	 * 
	 * @param mailbagId
	 * @param checkDomMail
	 * @return
	 * @throws BusinessDelegateException
	 */
	private boolean isValidMailtag(String mailbagId) throws BusinessDelegateException
	{
		boolean valid=false;
		int mailtagLength=mailbagId!=null?mailbagId.trim().length():0;
		String systemParameterValue=null; 
		systemParameterValue=findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
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
						Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
						securityScreeningValidationVOs = findSecurityScreeningValidationForDelivery(mailArrivalVO,
								logonAttributes, mailbagVo, securityScreeningValidationVOs);
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
	 * @param mailArrivalVO
	 * @param logonAttributes
	 * @param mailbagVo
	 * @param securityScreeningValidationVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	private Collection<SecurityScreeningValidationVO> findSecurityScreeningValidationForDelivery(
			MailArrivalVO mailArrivalVO, LogonAttributes logonAttributes, MailbagVO mailbagVo,
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs) throws BusinessDelegateException {
		if(!FLAG_YES.equals(mailbagVo.getArrivedFlag())){
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
			securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ARRIVED);
			securityScreeningValidationFilterVO.setFlightType(mailArrivalVO.getFlightType());
			securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
			securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
			securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
			securityScreeningValidationFilterVO.setMailbagId(mailbagVo.getMailbagId());
			securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
		}
		return securityScreeningValidationVOs;
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
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus("security");
			actionContext.setResponseVO(responseVO);
			actionContext.addAllError(warningErrors); 
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
