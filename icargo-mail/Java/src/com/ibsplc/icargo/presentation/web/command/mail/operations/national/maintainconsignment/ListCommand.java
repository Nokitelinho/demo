/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3817
 *
 */
public class ListCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET = "success";
	private static final String OUTBOUND = "O";
	private static final String MAIL_POSTAL_CODE ="mailtracking.defaults.national.postalcode";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	   
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		consignmentSession.setConsignmentDocumentVO(null);
		consignmentForm.setNoconDocNo("");
		consignmentForm.setNoError("");
		LogonAttributes  logonAttributes = getApplicationSession().getLogonVO();
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		consignmentSession.setWeightRoundingVO(unitRoundingVO);				
		setUnitComponent(logonAttributes.getStationCode(),consignmentSession);	
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 errors= invocationContext.getErrors();
		 String flag= "Y";
		//Added as part of bug-fix -icrd-12637 by A-4810
		 if (errors != null && errors.size() > 0) {
			   flag= "N";
			//invocationContext.addAllError(errors);
			 //consignmentForm.setNoError("N");
			 //consignmentForm.setConsignmentOriginFlag("Y");
			//invocationContext.target = "validate_failure";
			//return;
	        }
		if(consignmentForm.getConDocNo() == null || consignmentForm.getConDocNo().trim().length() ==0){
			//ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.noconsignmentno");			
			//errorVOs.add(errorVO);
			//invocationContext.addAllError(errorVOs);
			//Added as part of bug-fix -icrd-18645 by A-4810
			consignmentForm.setMailCategory("");
			consignmentForm.setNoconDocNo("Y");
			//Added as part of bug-fix -icrd-18661 by A-4810
			consignmentForm.setOrigin(logonAttributes.getAirportCode());
			consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET;
			return;
		}
		ConsignmentDocumentVO consignmentDocumentVO = null;
		//LogonAttributes  logonAttributes = getApplicationSession().getLogonVO();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setConsignmentNumber(consignmentForm.getConDocNo());
		consignmentFilterVO.setOperationMode(OUTBOUND);
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setAirportCode(logonAttributes.getAirportCode());
		AirportValidationVO airportValidationVO = null;
		if(consignmentForm.getOrigin() != null && consignmentForm.getOrigin().trim().length() >0){
			airportValidationVO = validateairport(logonAttributes.getCompanyCode(), consignmentForm.getOrigin());
			if(airportValidationVO == null){
				errorVOs.add(new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{consignmentForm.getOrigin()}));
				consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(errorVOs);
				invocationContext.target = TARGET;
				return;
			 }
			consignmentFilterVO.setConsigmentOrigin(consignmentForm.getOrigin());
		}
		
		if(consignmentForm.getDestination() != null && consignmentForm.getDestination().trim().length() >0){
			airportValidationVO = validateairport(logonAttributes.getCompanyCode(), consignmentForm.getDestination());
			if(airportValidationVO == null){
				errorVOs.add(new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{consignmentForm.getDestination()}));
				invocationContext.addAllError(errorVOs);
				consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = TARGET;
				return;
			}
			consignmentFilterVO.setConsignmentDestination(consignmentForm.getDestination());
		}
		
		
		
		Collection<String> codes = new ArrayList<String>();
		codes.add(MAIL_POSTAL_CODE);
		Map<String,String> parMap = populateSystemParameters(codes);
		if(parMap != null){
			consignmentFilterVO.setPaCode(parMap.get(MAIL_POSTAL_CODE));
		}
		//Added as part of bug-fix icrd-12686 for setting the flag to acknowledge the call is from list command.
		consignmentFilterVO.setListflag(true);
		try{
		consignmentDocumentVO  =(ConsignmentDocumentVO) new MailTrackingDefaultsDelegate().findNationalConsignmentDetails(consignmentFilterVO);
		}catch(BusinessDelegateException businessDelegateException){
			errorVOs = handleDelegateException(businessDelegateException);
		}
		if(consignmentDocumentVO == null){
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.noconsignmentdetails");
			errorVOs.add(errorVO);
			consignmentForm.setOrigin(logonAttributes.getAirportCode());
			consignmentForm.setConsignmentOriginFlag("Y");
			//Added as part of bug-fix -icrd-18645 by A-4810
			consignmentForm.setMailCategory("");
			if(("N").equals(flag))
			 {
				consignmentForm.setConsignmentOriginFlag("");
			 }
			consignmentForm.setScreenLoadFlag("N");
			consignmentForm.setDataFlag("N");
		

		}else{
			if(("N").equals(flag))
			{
				consignmentForm.setConsignmentOriginFlag("");
			}
			//Added by a-4810 as part of bug fix:13541
			if(logonAttributes.getAirportCode().equals(consignmentDocumentVO.getOrgin())){
				//consignmentForm.setConsignmentOriginFlag("Y");
				 consignmentForm.setDataFlag("Y");
			}else{
				consignmentForm.setConsignmentOriginFlag("N");
				consignmentForm.setDataFlag("N");
			}
			 filterRoutingDetails(consignmentDocumentVO);
			 consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
			//consignmentForm.setDataFlag("Y");

		}
		consignmentForm.setScreenLoadFlag("N");
		consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.addAllError(errorVOs);
		invocationContext.target = TARGET;
		
	}
	
	/**
	 * 
	 * @param codes
	 * @return
	 */
	private Map<String,String> populateSystemParameters(Collection<String> codes){
		Map<String,String> parMap = null;
		try{
		parMap  = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return parMap;
		
	}
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 */
	private void filterRoutingDetails(ConsignmentDocumentVO consignmentDocumentVO){
		if(consignmentDocumentVO != null &&
				consignmentDocumentVO.getRoutingInConsignmentVOs() != null && consignmentDocumentVO.getRoutingInConsignmentVOs().size() >0){
			Collection<RoutingInConsignmentVO> routingInConsignmentVOsToRemove = new ArrayList<RoutingInConsignmentVO>();
			
			for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()){
				if(consignmentDocumentVO.getAirportCode().equals(routingInConsignmentVO.getPol())){
					routingInConsignmentVOsToRemove.add(routingInConsignmentVO);					
				}
			}
			if(routingInConsignmentVOsToRemove != null){
				consignmentDocumentVO.getRoutingInConsignmentVOs().removeAll(routingInConsignmentVOsToRemove);
			}
			
		}		
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @return
	 */
	private AirportValidationVO validateairport(String companyCode,String airportCode){
		AirportValidationVO   airportValidationVO = null;
		try{
			airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return airportValidationVO;
	}
	private void setUnitComponent(String stationCode,
			ConsignmentSession consignmentSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT,UnitConstants.WEIGHT_MAIL_UNIT);
			
					
			
			consignmentSession.setWeightRoundingVO(unitRoundingVO);			
			
		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
	}
}
