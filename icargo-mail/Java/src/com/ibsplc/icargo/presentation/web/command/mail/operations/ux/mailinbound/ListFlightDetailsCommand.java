package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;  
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListFlightDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation``
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	25-Sep-2018		:	Draft
 */
public class ListFlightDetailsCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String INBOUND = "I";
	private Log log = LogFactory.getLogger("OPERATIONS ListFlightDetailsCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ListFlightDetailsCommand", "execute");
	
		LogonAttributes logonAttributes =   
				(LogonAttributes) getLogonAttribute();
		MailinboundModel mailinboundModel =  
				(MailinboundModel) actionContext.getScreenModel();
		MailinboundFilter mailinboundFilter = 
				(MailinboundFilter)mailinboundModel.getMailinboundFilter(); 
		ResponseVO responseVO = new ResponseVO();  
		MailArrivalVO mailArrivalVO = new MailArrivalVO(); 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<MailArrivalVO> mailArrivalVos=null;
		ArrayList<MailinboundDetails> mailinboundDetailsCollection=null;
		Page<ContainerDetailsVO> containerDetailsVOs=  
				new Page<ContainerDetailsVO>();
		Page<MailbagVO> mailbagVOs=
				new Page<MailbagVO>();
		Page<DSNVO> dsnvos=
				new Page<DSNVO>();
		
		if(null==mailinboundFilter.getPort()||
				("".equals(mailinboundFilter.getPort().trim()))){
			mailinboundFilter.setPort(logonAttributes.getAirportCode()); 
			
		}
		//if(null!=mailinboundFilter.getFlightnumber()){
			/*if(mailinboundFilter.getFlightnumber().getFlightNumber()==null || 
					("".equals(mailinboundFilter.getFlightnumber().getFlightNumber().trim()))||
							mailinboundFilter.getFlightnumber().getCarrierCode()==null || 
									("".equals(mailinboundFilter.getFlightnumber().getCarrierCode()))){
					actionContext.addError(new ErrorVO("mail.operations.mailinbound.flightincomplete"));
					return;		
			}
			else if(mailinboundFilter.getFlightnumber().getFlightDate()==null||
					("".equals(mailinboundFilter.getFlightnumber().getFlightDate().trim()))){
				actionContext.addError(new ErrorVO("mail.operations.mailinbound.flightdatemandatory"));
				return;
			}  */
		//}
		
		if(null==mailinboundFilter.getFlightnumber()||  
				(mailinboundFilter.getPort() == null || ("".equals(mailinboundFilter.getPort().trim())))){ 
			if(null!=mailinboundFilter.getFlightnumber()){
				if(mailinboundFilter.getFlightnumber().getFlightNumber()==null ||  
						("".equals(mailinboundFilter.getFlightnumber().getFlightNumber().trim()))|| 
								(mailinboundFilter.getFlightnumber().getFlightDate()== null ||
									("".equals(mailinboundFilter.getFlightnumber().getFlightDate().trim())))||
											(mailinboundFilter.getPort() == null ||
													("".equals(mailinboundFilter.getPort().trim())))){
					actionContext.addError(new ErrorVO("mail.operations.mailinbound.mandatorycomboabsent"));
					return;
				}
			}
			else if(mailinboundFilter.getToDate()==null||("".equals(mailinboundFilter.getToDate().trim()))
					||mailinboundFilter.getFromDate()==null||("".equals(mailinboundFilter.getFromDate().trim()))||
						mailinboundFilter.getPort()==null||("".equals(mailinboundFilter.getPort().trim()))){
				actionContext.addError(new ErrorVO("mail.operations.mailinbound.mandatorycomboabsent"));
				return;
			}
				
		}
		

		
		mailArrivalVO=populateFilterVo(mailinboundFilter);
		
		if(null!=mailArrivalVO){
			mailinboundModel.setMailArrivalVO(mailArrivalVO);
			try {    
				mailArrivalVos=new MailTrackingDefaultsDelegate().listFlightDetails(mailArrivalVO);
				
			}catch (BusinessDelegateException businessDelegateException) {    
	  			businessDelegateException.getMessageVO().getErrors();
  				errors = handleDelegateException(businessDelegateException); 
  			}   
			   
			if(mailArrivalVos==null){
				actionContext.addError(new ErrorVO("mail.operations.err.noflightdetails"));
				return;
			}
			
			
			MailArrivalFilterVO mailArrivalFilterVO=
					new MailArrivalFilterVO();
			if(mailArrivalVos.size()>0){
				
				MailArrivalVO arrivalVO=null;
				arrivalVO=mailArrivalVos.iterator().next();
				mailArrivalFilterVO.setCompanyCode(arrivalVO.getCompanyCode());
				mailArrivalFilterVO.setCarrierId(arrivalVO.getCarrierId());
				mailArrivalFilterVO.setFlightNumber(arrivalVO.getFlightNumber());
				mailArrivalFilterVO.setFlightSequenceNumber(arrivalVO.getFlightSequenceNumber());
				mailArrivalFilterVO.setPou(arrivalVO.getAirportCode()); 
				mailArrivalFilterVO.setPageNumber(1);
				mailArrivalFilterVO.setDefaultPageSize(Integer.parseInt(mailinboundFilter.getPageSize()));   
				
				try {
					containerDetailsVOs = 
							new MailTrackingDefaultsDelegate().findArrivedContainersForInbound(mailArrivalFilterVO);
				}catch (BusinessDelegateException businessDelegateException) {
					errors=handleDelegateException(businessDelegateException); 
				}
				
				if(null!=containerDetailsVOs&&containerDetailsVOs.size()>0){
					
					mailArrivalFilterVO.setContainerNumber(containerDetailsVOs.iterator().next().getContainerNumber()); 
					mailArrivalFilterVO.setContainerType(containerDetailsVOs.iterator().next().getContainerType());
					if(containerDetailsVOs.iterator().next().getPol()!=null && containerDetailsVOs.iterator().next().getPol().trim().length()>0) {
						mailArrivalFilterVO.setPol(containerDetailsVOs.iterator().next().getPol());
					}
					
					try {
						mailbagVOs = 
								new MailTrackingDefaultsDelegate().findArrivedMailbagsForInbound(mailArrivalFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {
						errors=handleDelegateException(businessDelegateException);
					}
					
					try {
						dsnvos = 
								new MailTrackingDefaultsDelegate().findArrivedDsnsForInbound(mailArrivalFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {
						errors=handleDelegateException(businessDelegateException);
				}
				
			}
				else{
					MailinboundModel mailinboundModelToSave=
							 new MailinboundModel();
					if(null!=mailArrivalVos){
						 mailinboundDetailsCollection=MailInboundModelConverter.convertMailArrivalVosToModel(mailArrivalVos);
						 PageResult<MailinboundDetails> flightDetailsPageList = new PageResult(mailArrivalVos, mailinboundDetailsCollection);
						 mailinboundModelToSave.setMailinboundDetailsCollectionPage(flightDetailsPageList);
						 MailinboundDetails mailinboundDetails=flightDetailsPageList.getResults().get(0);
						 mailinboundModel.setMailinboundDetails(mailinboundDetails); 
						 mailinboundModelToSave.setMailinboundDetails(mailinboundDetails);
					 }
					 ArrayList<MailinboundModel> result=
							 new ArrayList<MailinboundModel>();  
					 mailinboundModelToSave.setMailinboundFilter(mailinboundFilter); 
					 result.add(mailinboundModelToSave);    
					 responseVO.setResults(result);  
				     responseVO.setStatus("success");
				     actionContext.setResponseVO(responseVO); 
				     return;  
					
				}
			
		}
		
		 if (errors != null && errors.size() > 0) {
			 	mailinboundModel.setMailArrivalVO(mailArrivalVO);
	    		actionContext.addAllError((List<ErrorVO>) errors); 
	    	    return;
		 }else{
			 String carrierCode=null;
			 MailinboundModel mailinboundModelToSave=
					 new MailinboundModel();
			 if(null!=mailArrivalVos){
				 mailinboundDetailsCollection=MailInboundModelConverter.convertMailArrivalVosToModel(mailArrivalVos);
				 PageResult<MailinboundDetails> flightDetailsPageList = new PageResult(mailArrivalVos, mailinboundDetailsCollection);
				 mailinboundModelToSave.setMailinboundDetailsCollectionPage(flightDetailsPageList);
				 MailinboundDetails mailinboundDetails=flightDetailsPageList.getResults().get(0);
				 carrierCode=mailinboundDetails.getCarrierCode();
				 mailinboundModel.setMailinboundDetails(mailinboundDetails); 
				 mailinboundModelToSave.setMailinboundDetails(mailinboundDetails);
			 }
			 if(null!=containerDetailsVOs){
				 PageResult<ContainerDetails> containerDetailsPageList = MailInboundModelConverter.convertContainerDetailsToModel(containerDetailsVOs,carrierCode);
				 mailinboundModelToSave.setContainerDetailsCollectionPage(containerDetailsPageList);
			 }
			 if(null!=mailbagVOs){
				 PageResult<MailBagDetails> mailDetailsPageList = MailInboundModelConverter.convertMailBagDetailsToModel(mailbagVOs);
				 mailinboundModelToSave.setMailBagDetailsCollectionPage(mailDetailsPageList);
			 }
			 if(null!=dsnvos){
			 PageResult<DSNDetails> dsnDetailsPageList = MailInboundModelConverter.convertDsnDetailsToModel(dsnvos);
			 mailinboundModelToSave.setDsnDetailsCollectionPage(dsnDetailsPageList);
			 }
			    
			 mailinboundModelToSave.setMailinboundFilter(mailinboundFilter); 
			 mailinboundModel.setMailArrivalVOs(mailArrivalVos);
			 ArrayList<MailinboundModel> result=
					 new ArrayList<MailinboundModel>();
			 
			 LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				String dateToSave=date.toDisplayDateOnlyFormat();
				String time=date.toDisplayTimeOnlyFormat(true);
				mailinboundModelToSave.setCurrentDate(dateToSave);
				mailinboundModelToSave.setCurrentTime(time);
				
			 
			 
			 
			  
			 result.add(mailinboundModelToSave);    
			 responseVO.setResults(result);  
		     responseVO.setStatus("success");
		     actionContext.setResponseVO(responseVO);
			 
		 }
		}
		
		
		
		
		
	}
	
	

	private MailArrivalVO populateFilterVo(MailinboundFilter mailinboundFilter){
		
 		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(null!=mailinboundFilter.getFlightnumber()){
			if(mailinboundFilter.getFlightnumber().getCarrierCode()!=null &&
					!("".equals(mailinboundFilter.getFlightnumber().getCarrierCode().trim()))){
				mailArrivalVO.setFlightCarrierCode(
		    			mailinboundFilter.getFlightnumber().getCarrierCode().toUpperCase());
				if(mailinboundFilter.getFlightnumber().getFlightNumber()!=null &&
						!("".equals(mailinboundFilter.getFlightnumber().getFlightNumber().trim()))){
					
					mailArrivalVO.setFlightNumber(
							mailinboundFilter.getFlightnumber().getFlightNumber());
			    	/*mailArrivalVO.setFlightCarrierCode(
			    			mailinboundFilter.getFlightnumber().getCarrierCode().toUpperCase());*/
				}
			}
		}
		/*else{
			mailArrivalVO.setFlightCarrierCode(
	    			logonAttributes.getCompanyCode()); 
		}*/
		
		LocalDate date;
		
		if(mailinboundFilter.getPort() != null && 
				!("".equals(mailinboundFilter.getPort().trim()))){ 
			mailArrivalVO.setAirportCode(mailinboundFilter.getPort());    
			date = new LocalDate(mailinboundFilter.getPort(),Location.ARP,false);  
		}
		else{
			mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
			date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		}
		if(null!=mailinboundFilter.getFlightnumber())
			if(mailinboundFilter.getFlightnumber().getFlightDate()!=null &&  
						!("".equals(mailinboundFilter.getFlightnumber().getFlightDate().trim()))){
				mailArrivalVO.setFlightDate(date.setDate(
						mailinboundFilter.getFlightnumber().getFlightDate()));
			}
		if(mailinboundFilter.getMailstatus() != null && 
				!("".equals(mailinboundFilter.getMailstatus().trim()))){
			mailArrivalVO.setFlightStatus(mailinboundFilter.getMailstatus()); 
		}
		if(mailinboundFilter.getTransfercarrier() != null && 
				!("".equals(mailinboundFilter.getTransfercarrier().trim()))){
			mailArrivalVO.setTransferCarrier(mailinboundFilter.getTransfercarrier());
		}
		if(mailinboundFilter.getPa() != null && 
				!("".equals(mailinboundFilter.getPa().trim()))){
			mailArrivalVO.setArrivalPA(mailinboundFilter.getPa());
		}
		if(mailinboundFilter.getFromDate() != null && 
				!("".equals(mailinboundFilter.getFromDate().trim()))){
			 if(mailinboundFilter.getFromTime()!=null && mailinboundFilter.getFromTime().trim().length() > 0){
				   String fromDT=null;
				   fromDT = new StringBuilder(mailinboundFilter.getFromDate()).append(" ") 
							.append(mailinboundFilter.getFromTime()).append(":00").toString();
				   mailArrivalVO.setFromDate(((new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false).setDateAndTime(fromDT))));
			   }else{
			mailArrivalVO.setFromDate((new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(mailinboundFilter.getFromDate())));
		}
		}
		if(mailinboundFilter.getToDate() != null && 
				!("".equals(mailinboundFilter.getToDate().trim()))){
			if(mailinboundFilter.getToTime()!=null && mailinboundFilter.getToTime().trim().length() > 0){
				   String toDT=null;
				   toDT = new StringBuilder(mailinboundFilter.getToDate()).append(" ") 
							.append(mailinboundFilter.getToTime()).append(":00").toString();
				   mailArrivalVO.setToDate(((new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false).setDateAndTime(toDT))));
			   }else{
			mailArrivalVO.setToDate((new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(mailinboundFilter.getToDate()))); 
			   }
		}
		if(mailinboundFilter.getPageNumber()==null|| 
				("".equals(mailinboundFilter.getPageNumber().trim()))){
			mailArrivalVO.setPageNumber(1);
		}
		else{
			mailArrivalVO.setPageNumber(Integer.parseInt(mailinboundFilter.getPageNumber()));
		}
		if(mailinboundFilter.getPageSize()==null|| 
				("".equals(mailinboundFilter.getPageSize().trim()))){
			mailArrivalVO.setDefaultPageSize(10);
		}
		else{
			mailArrivalVO.setDefaultPageSize(Integer.parseInt(mailinboundFilter.getPageSize()));
		}
		
		//Added by A-8464 for ICRD-328502
		if(mailinboundFilter.isMailFlightChecked()){
			mailArrivalVO.setMailFlightChecked(mailinboundFilter.isMailFlightChecked());
		}
		if(mailinboundFilter.getPol()!=null){
			mailArrivalVO.setPol(mailinboundFilter.getPol());
		}
		
		//added by A-7815 as part of IASCB-36551 
		mailArrivalVO.setOperatingReference(mailinboundFilter.getOperatingReference());
		return mailArrivalVO;
		
	}

}
