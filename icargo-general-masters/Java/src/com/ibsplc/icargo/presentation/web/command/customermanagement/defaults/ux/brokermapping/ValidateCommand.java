package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.scc.SCCDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.PoaMappingDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ValidateCommand extends AbstractCommand{
	private static final String GENPOA="General POA";
	private static final String SPLPOA="Special POA";
	private static final String VALIDATE_SUCCESS = "VALIDATE_SUCCESS";
	private static final String INVALID_DESTINATION = "Invalid Destination";
	private static final String INVALID_ORIGIN = "Invalid Orgin";
	private static final String INVALID_SCC = "Invalid Scc";
	private static final String SINGLE_POA="Single POA";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		ResponseVO responseVO= new ResponseVO();
		BrokerMappingModel brokerMappingModel =(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		List<ErrorVO> errors=null;
		PoaMappingDetails validateConsigneeDetails=brokerMappingModel.getValidateConsigneeDetails();
		errors=validatePoaDetails(validateConsigneeDetails,logonAttributes.getCompanyCode());
		
		if(errors.isEmpty() ){
			if(validateConsigneeDetails.isPoaFlag()){
				validateConsigneeDetails(actionContext, responseVO, logonAttributes, validateConsigneeDetails);
			}
			else{
				responseVO.setStatus("validate_broker_sucess");
				actionContext.setResponseVO(responseVO);
			}
		}else{
			StringBuilder error=new StringBuilder();
			error=error.append("Invalid ");
			for(ErrorVO err:errors)
			{
				if(err.getErrorCode()!=null && INVALID_DESTINATION.equalsIgnoreCase(err.getErrorCode())){
					error.append("Destination,");
				}
				else if(err.getErrorCode()!=null && INVALID_ORIGIN.equalsIgnoreCase(err.getErrorCode())){
					error.append("Origin,");
				}
				else if(err.getErrorCode()!=null && INVALID_SCC.equalsIgnoreCase(err.getErrorCode())){
					error.append("SCC,");
				}else{
					error.append("");
				}
				
			}
			String errorCode=error.substring(0, error.length()-1);
			List<ErrorVO> results = new ArrayList<>();
			ErrorVO err=new ErrorVO(errorCode);
    		results.add(err);
    		responseVO.setResults(results);
    		actionContext.setResponseVO(responseVO);
		}
	}

	protected void validateConsigneeDetails(ActionContext actionContext, ResponseVO responseVO,
			LogonAttributes logonAttributes, PoaMappingDetails validateConsigneeDetails) {
		CustomerFilterVO filterVO = new CustomerFilterVO();
		CustomerVO customerVO = new CustomerVO();
		filterVO.setCustomerCode(validateConsigneeDetails.getAgentCode());
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setSource("MNGPOA");
		CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
		ErrorVO error = null;
		try{
			customerVO = delegate.listCustomer(filterVO);
			
		}catch(BusinessDelegateException ex){
			handleDelegateException(ex);
		}
		String poaType="";
		if((validateConsigneeDetails.getOrginInclude()!=null && validateConsigneeDetails.getOrginInclude().length>0)
				|| (validateConsigneeDetails.getOrginExclude()!=null && validateConsigneeDetails.getOrginExclude().length>0)
				||(validateConsigneeDetails.getSccCodeExclude()!=null && validateConsigneeDetails.getSccCodeExclude().length>0)
				||(validateConsigneeDetails.getSccCodeInclude()!=null && validateConsigneeDetails.getSccCodeInclude().length>0)){
			poaType=SPLPOA;
		}
		else{
			poaType=GENPOA;
		}
		if(customerVO != null && customerVO.getCustomerAgentVOs()!=null && !customerVO.getCustomerAgentVOs().isEmpty() ){
			error=validateSpeacialParameters(customerVO,validateConsigneeDetails,poaType);
			
		}
		if(error!=null){
			List<ErrorVO> results = new ArrayList<>();
			results.add(error);
			responseVO.setResults(results);
		}
		else{
			
			responseVO.setStatus(VALIDATE_SUCCESS);
		}
		actionContext.setResponseVO(responseVO);
	}
	
	private List<ErrorVO> validatePoaDetails(PoaMappingDetails validateConsigneeDetails, String companyCode) {
		ErrorVO error=null;
		List<ErrorVO> errorVOs = new ArrayList<>();
		if(validateConsigneeDetails.getDestination().length>0){
			Collection<String> destination = convertToCollection(validateConsigneeDetails.getDestination());
			error=validateDestination(destination,companyCode);
			if(error!=null){
				errorVOs.add(error);
			}
		}
		if(validateConsigneeDetails.getSccCodeExclude().length>0 || validateConsigneeDetails.getSccCodeInclude().length>0){
			
			String[] joinScc=Stream.concat(Arrays.stream(validateConsigneeDetails.getSccCodeExclude()), Arrays.stream(validateConsigneeDetails.getSccCodeInclude()))
                    .toArray(String[]::new);
			Collection<String> sccCodes = convertToCollection(joinScc);
			error=validateSccCode(sccCodes,companyCode);
			if(error!=null){
				errorVOs.add(error);
			}
		}
		if(validateConsigneeDetails.getOrginExclude().length>0 || validateConsigneeDetails.getOrginInclude().length>0){
			String[] joinOrigin=Stream.concat(Arrays.stream(validateConsigneeDetails.getOrginExclude()), Arrays.stream(validateConsigneeDetails.getOrginInclude()))
                    .toArray(String[]::new);
			Collection<String> origin = convertToCollection(joinOrigin);
			error=validateOrgin(origin,companyCode);
			if(error!=null){
				errorVOs.add(error);
			}
		}
		
		return errorVOs;
	}
	
	private ErrorVO validateOrgin(Collection<String> origin, String companyCode) {
		ErrorVO error=null;
		Map<String,AirportValidationVO> airportValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			airportValidationVO = delegate.validateAirportCodes(companyCode,origin);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		if(airportValidationVO==null){
			error=new ErrorVO(INVALID_ORIGIN);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		}
		return error;
	}
	
	private ErrorVO validateSccCode(Collection<String> sccCodes, String companyCode) {
		ErrorVO error=null;
		Collection<SCCValidationVO> sccMap = null;
		try {
			SCCDelegate sccDelegate = new SCCDelegate();
			sccMap =sccDelegate.validateSCCCodes(companyCode,sccCodes); 
									
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		if(sccMap==null){
			error=new ErrorVO(INVALID_SCC);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		}
		return error;
	}
	
	private ErrorVO validateDestination(Collection<String> destination, String companyCode) {
		ErrorVO error=null;
		Map<String,AirportValidationVO> airportValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			airportValidationVO = delegate.validateAirportCodes(companyCode,destination);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		if(airportValidationVO==null){
			error=new ErrorVO(INVALID_DESTINATION);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		}
		return error;
	}
	private Collection<String> convertToCollection(String[] stringArry) {
		Collection<String> stringCol=new ArrayList<>();
		for(String stringOb:stringArry){
			stringCol.add(stringOb.trim());
		}
		return stringCol;
	}
	
	private ErrorVO validateSpeacialParameters(CustomerVO customerVO, PoaMappingDetails validateConsigneeDetails,String poaType) {
		ErrorVO error = null;
		for(CustomerAgentVO cutomerAgtVO:customerVO.getCustomerAgentVOs())
		{
			if(!SINGLE_POA.equalsIgnoreCase(cutomerAgtVO.getPoaType())){
				if(cutomerAgtVO.getDestination().equalsIgnoreCase(String.join(",", validateConsigneeDetails.getDestination()))){
					if(GENPOA.equalsIgnoreCase(poaType)){
						error= new ErrorVO("POA already available for the consignee with the different broker");
						
					}
					if(SPLPOA.equalsIgnoreCase(poaType)){
						error=validateSpecialPoaDetails(cutomerAgtVO,validateConsigneeDetails);
					}
					
				}
				if(error!= null){
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					break;
				}
			}
		}
		return error;
	}
	private ErrorVO validateSpecialPoaDetails(CustomerAgentVO cutomerAgtVO,
			PoaMappingDetails validateConsigneeDetails) {

		ErrorVO error = null;
		if(cutomerAgtVO.getScc()==null && cutomerAgtVO.getExcludedScc() ==null && cutomerAgtVO.getExcludedOrigins() ==null && cutomerAgtVO.getIncludedOrigins() ==null){
			error= new ErrorVO("General POA already exists with other broker. Special POA cannot be created");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			
		}
		else{
     		boolean validationPassed=false;
			String sccCodeInclude=String.join(",",validateConsigneeDetails.getSccCodeInclude());
			String sccCodeExclude=String.join(",",validateConsigneeDetails.getSccCodeExclude());
			String originInclude=String.join(",", validateConsigneeDetails.getOrginInclude());
			String originExclude=String.join(",", validateConsigneeDetails.getOrginExclude());
			
			String[] sccCodeIncludeAgtVO={};
			String[] sccCodeExcludeAgtVO={};
			String[] originIncludeAgtVO={};
			String[] originExcludeAgtVO={};
			if(cutomerAgtVO.getScc()!=null){
				sccCodeIncludeAgtVO=cutomerAgtVO.getScc().split(",");
			}
			if(cutomerAgtVO.getExcludedScc()!=null){
				sccCodeExcludeAgtVO=cutomerAgtVO.getExcludedScc().split(",");
			}
			if(cutomerAgtVO.getIncludedOrigins()!=null){
				originIncludeAgtVO=cutomerAgtVO.getIncludedOrigins().split(",");
			}
			if(cutomerAgtVO.getExcludedOrigins()!=null){
				originExcludeAgtVO=cutomerAgtVO.getExcludedOrigins().split(",");
			}
			
			
			boolean sccError=validateSCC(sccCodeInclude,sccCodeExclude,sccCodeIncludeAgtVO,sccCodeExcludeAgtVO);
			boolean originError=validateOrigin(originInclude,originExclude,originIncludeAgtVO,originExcludeAgtVO);
			if(!originError || !sccError){
            	validationPassed=true;
            }
			
			if(!validationPassed){
				error= new ErrorVO("Special POA already exists with other broker for same parameters");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
			}
		}
	   return error;
	}
	
	private boolean validateOrigin(String originInclude, String originExclude, String[] originIncludeAgtVO,
			String[] originExcludeAgtVO) {
		boolean validateOrigin=false;
		if(originInclude.isEmpty() && originIncludeAgtVO.length==0){
			validateOrigin=true;
		}else if(originInclude.isEmpty() && originIncludeAgtVO.length>0){
			validateOrigin=validateCombination(originExclude,originIncludeAgtVO);
		}else if(!originInclude.isEmpty() && originIncludeAgtVO.length==0){
			validateOrigin=validateCombination(String.join(",", originExcludeAgtVO),originInclude.split(","));
		}else{
			if(!originInclude.isEmpty() && originIncludeAgtVO.length>0){
				validateOrigin=checkForSplParameters(originIncludeAgtVO,originInclude);
			}
		}
		return validateOrigin;
	}

	private boolean validateSCC(String sccCodeInclude, String sccCodeExclude,
			String[] sccCodeIncludeAgtVO, String[] sccCodeExcludeAgtVO) {
		boolean validateScc=false;
		if(sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length==0){
			validateScc=true;
		}else if(sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length>0){
			validateScc=validateCombination(sccCodeExclude,sccCodeIncludeAgtVO);
		}else if(!sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length==0){
			validateScc=validateCombination(String.join(",", sccCodeExcludeAgtVO),sccCodeInclude.split(","));
		}else{
			if(!sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length>0){
				validateScc=checkForSplParameters(sccCodeIncludeAgtVO,sccCodeInclude);
			}
		}
		return validateScc;
	}

	private boolean validateCombination(String param, String[] paramArry) {
		boolean validationPassed=false;
		if(!param.isEmpty() && paramArry.length>0){
			for(String pramValue:paramArry){
				if(! param.contains(pramValue)){
					validationPassed=true;
					break;
				}
			}
		}else{
			validationPassed=true;
		}
		return validationPassed;
	}
	
	@Deprecated
	private boolean validateSpeacialParameters(String[] paramArray, String param) {
		boolean validateFlg=false;
		if((paramArray.length==0 && param==null) 
				|| (paramArray.length>0 && param==null)
				|| (paramArray.length==0 && param!=null)
				|| checkForSplParameters(paramArray,param)){
			validateFlg=true;
			}
		return validateFlg;
	}
	@Deprecated
	private boolean validateSpeacialParametersForExclusion(String[] paramArray, String param) {
		boolean validateFlg=false;
		if((paramArray.length==0 && param==null) 
				|| checkForSplParameters(paramArray,param)){
			validateFlg=true;
			}
		return validateFlg;
	}
	
	private boolean checkForSplParameters(String[] parameterArray, String parameter) {
		boolean check=false;
		if(parameterArray.length>0 && parameter != null){
			for(String param:parameterArray){
				if(parameter.contains(param)){
					check=true;
					break;
				}
			}
		}
		return check;
		
	}
	
}
