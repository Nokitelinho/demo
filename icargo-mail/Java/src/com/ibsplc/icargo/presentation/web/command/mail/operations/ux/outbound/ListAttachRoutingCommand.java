package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachRoutingDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentDocument;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListAttachRoutingCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	20-Nov-2018		:	Draft
 */
public class ListAttachRoutingCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailoubound";
	private static final String CONSGNMNT_TYPE="mailtracking.defaults.consignmentdocument.type";
	private static final String STATUS_NO_RESULTS = "mailtracking.defaults.consignment.status.noresultsfound";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachRoutingCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		log.entering("validateAttachRoutingCommand","execute");
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    List<ErrorVO> errors = null;
     	Page<MailInConsignmentVO> mailbagpage=null;
     	AttachRoutingDetails attachRoutingDetails = outboundModel.getAttachRoutingDetails();
 		
	       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
			 
			  
		   
		   
    	
    	
    
    	errors = validateForm(attachRoutingDetails,logonAttributes);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
			//mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		}else{ 
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(attachRoutingDetails.getConsignemntNumber().toUpperCase());
			consignmentFilterVO.setPaCode(attachRoutingDetails.getPaCode().toUpperCase());
			consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
			consignmentFilterVO.setPageNumber(1);
			
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate().
				        findConsignmentDocumentDetails(consignmentFilterVO);
				
						
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			ConsignmentDocument consignmentDocument = MailOutboundModelConverter.constructConsignmentDocument(consignmentDocumentVO);
						
			log.log(Log.FINE, "consignmentDocumentVO ===>>>>",
					consignmentDocumentVO);
			if(consignmentDocumentVO == null) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setOperationFlag("I");
				actionContext.addError(new ErrorVO(STATUS_NO_RESULTS));
				log.log(Log.FINE, "consignmentDocumentVO IS NULL");
				return;
			}else {
				consignmentDocumentVO.setOperationFlag("U");

				log.log(Log.FINE, "consignmentDocumentVO IS not NULL",
						consignmentDocumentVO);
				int totalRecords = 0;
				if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
					//if(mailInConsignmentVOs!=null && mailInConsignmentVOs.size()>0){
						//consignmentDocumentVO.getMailInConsignmentcollVOs().addAll(mailInConsignmentVOs);
					//}
				}else {
					//consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
				}
			}
			
			if(null!=consignmentDocumentVO.getRoutingInConsignmentVOs()&&
					consignmentDocumentVO.getRoutingInConsignmentVOs().size()>0){
				attachRoutingDetails=
						populateAttachRoutingDetails(consignmentDocumentVO.getRoutingInConsignmentVOs());
				
			}
			attachRoutingDetails.setConsignemntNumber(consignmentDocumentVO.getConsignmentNumber());
			attachRoutingDetails.setPaCode(consignmentDocumentVO.getPaCode());
			attachRoutingDetails.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toDisplayDateOnlyFormat());
			attachRoutingDetails.setConsignmentType(consignmentDocumentVO.getType());
			attachRoutingDetails.setRemarks(consignmentDocumentVO.getRemarks());
			consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentDocumentVO.setConsignmentNumber(
					attachRoutingDetails.getConsignemntNumber().toUpperCase());
			consignmentDocumentVO.setPaCode(
					attachRoutingDetails.getPaCode().toUpperCase());
	outboundModel.setOneTimeValues(MailOutboundModelConverter.constructOneTimeValues(oneTimes));
	outboundModel.setAttachRoutingDetails(attachRoutingDetails); 
	ResponseVO responseVO = new ResponseVO();
	ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
	//outboundModel.setConsignmentDocumentVO(consignmentDocumentVO);
	result.add(outboundModel);
	responseVO.setResults(result);
	responseVO.setStatus("success");
	actionContext.setResponseVO(responseVO);
		}
	}
	

	/**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private List<ErrorVO> validateForm(AttachRoutingDetails attachRoutingDetails,LogonAttributes logonAttributes) {
		
		String conDocNo = attachRoutingDetails.getConsignemntNumber();
		String paCode = attachRoutingDetails.getPaCode();
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}else{
//    	validate PA code
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
									logonAttributes.getCompanyCode(),paCode.toUpperCase());	  			
		   			if(postalAdministrationVO == null) {
		  				Object[] obj = {paCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.invalid",obj));
		  			}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	/**
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}   
	private AttachRoutingDetails populateAttachRoutingDetails(
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
		ArrayList<OnwardRouting> onwardRoutings=
				new ArrayList<OnwardRouting>();
		AttachRoutingDetails attachRoutingDetails=
				new AttachRoutingDetails();
		for(RoutingInConsignmentVO routingInConsignmentVO:routingInConsignmentVOs){
			OnwardRouting onwardRouting=new OnwardRouting();
			if(null!=routingInConsignmentVO.getOnwardCarrierCode()){
				onwardRouting.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
			}
			if(null!=routingInConsignmentVO.getOnwardFlightDate()){
				onwardRouting.setFlightDate(
						routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
			}
			if(null!=routingInConsignmentVO.getOnwardFlightNumber()){
				onwardRouting.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			}
			if(null!=routingInConsignmentVO.getPol()){
				onwardRouting.setPol(routingInConsignmentVO.getPol());
			}
			if(null!=routingInConsignmentVO.getPou()){
				onwardRouting.setPou(routingInConsignmentVO.getPou());
			}
			onwardRouting.setRoutingSerialNumber(routingInConsignmentVO.getRoutingSerialNumber());	
			onwardRouting.setOperationalStatus(MailConstantsVO.MRA_STATUS_UPDATE);
			onwardRoutings.add(onwardRouting);
		}
		attachRoutingDetails.setOnwardRouting(onwardRoutings);
		return attachRoutingDetails;
	}   
}
